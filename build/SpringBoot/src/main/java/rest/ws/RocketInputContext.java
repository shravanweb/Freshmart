package rest.ws;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import classes.Blob;
import d3e.core.DFile;
import d3e.core.ListExt;
import d3e.core.Geolocation;
import gqltosql.schema.DField;
import gqltosql.schema.DModel;
import gqltosql.schema.DModelType;
import gqltosql.schema.FieldType;
import store.DBObject;
import store.EntityHelper;
import store.EntityHelperService;

public class RocketInputContext {

	private RocketMessage msg;
	Template template;
	private Map<Long, DBObject> localCache = new HashMap<>();
	private EntityHelperService helperService;
	private long localId;

	public RocketInputContext(EntityHelperService helperService, Template template, RocketMessage msg) {
		this.helperService = helperService;
		this.template = template;
		this.msg = msg;
	}

	public long readLong() {
		long l = msg.readLong();
		return l;
	}

	public void writeObjectList(List<?> list) {
		if (list == null) {
			return;
		}
		int size = list.size();
		msg.writeInt(size);
		for (Object obj : list) {
			writeObject((DBObject) obj);
		}
	}

	public void writeObject(DBObject obj) {
		if (obj == null) {
			// Log.info("w obj: null");
			msg.writeInt(-1);
			return;
		}
		int typeIdx = template.toClientTypeIdx(obj._typeIdx());
		// Log.info("w obj: " + typeIdx + " " + obj._type());
		msg.writeInt(typeIdx);
		TemplateType tt = template.getType(typeIdx);
		if (!tt.getModel().isEmbedded()) {
			if (obj.getId() == 0l) {
				obj.setId(nextId());
			}
			msg.writeLong(obj.getId());
		}
		while (tt != null) {
			writeProperties(obj, tt);
			tt = tt.getParentType();
		}
		msg.writeInt(-1);
	}

	private long nextId() {
		--localId;
		return localId;
	}

	private void writeProperties(DBObject obj, TemplateType tt) {
		DField<?, ?>[] fields = tt.getFields();
		int i = tt.getParentClientCount();
		for (DField df : fields) {
			// Log.info("w field: " + i + " " + df.getName());
			if (df instanceof UnknownField) {
				i++;
				continue;
			}
			FieldType ft = df.getType();
			Object val = df.getValue(obj);
			if (df.getReference() != null && df.getReference().isEmbedded() && val == null) {
				i++;
				continue;
			}
			msg.writeInt(i++);
			if (val == null) {
				// Log.info("w null: ");
				msg.writeInt(-1);
				continue;
			}
			switch (ft) {
			case Primitive:
				writePrimitive(val, df);
				break;
			case PrimitiveCollection:
				List vals = (List) val;
				// Log.info("w primitive List: " + vals.size());
				msg.writeInt(vals.size());
				for (Object o : vals) {
					writePrimitive(o, df);
				}
				break;
			case Reference:
				DModel ref = df.getReference();
				if (ref.isEmbedded()) {
					writeEmbedded((DBObject) val, df.getReference());
				} else if (df.isChild()) {
					writeObject((DBObject) val);
				} else if (ref.getType().equals("DFile")) {
					writeDFile(msg, template, (DFile) val);
				} else {
					DBObject o = (DBObject) val;
					if(o.getId() == 0l) {
						writeObject(o);
					} else {
						writeRef(o);
					}
				}
				break;
			case InverseCollection:
			case ReferenceCollection:
				List coll = new ArrayList<>((List) val);
				// Log.info("w ref List: " + coll.size());
				msg.writeInt(coll.size());
				for (Object o : coll) {
					if (df.isChild()) {
						writeObject((DBObject) o);
					} else if (df.getReference().getType().equals("DFile")) {
						writeDFile(msg, template, (DFile) o);
					} else {
						DBObject d = (DBObject) o;
						if(d.getId() == 0l) {
							writeObject(d);
						} else {
							writeRef(d);
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}

	private void writeEmbedded(DBObject obj, DModel ref) {
		if (obj == null) {
			msg.writeInt(template.toClientTypeIdx(ref.getIndex()));
			msg.writeInt(-1);
			return;
		}
		writeObject(obj);
	}

	public void writeRef(DBObject obj) {
		if (obj == null) {
			// Log.info("w ref: null");
			msg.writeInt(-1);
			return;
		}
		int typeIdx = template.toClientTypeIdx(obj._typeIdx());
		msg.writeInt(typeIdx);
		// Log.info("w ref: " + typeIdx + " " + obj._type());
		if (obj.getId() == 0l) {
			obj.setId(nextId());
		}
		msg.writeLong(obj.getId());
		msg.writeInt(-1);
	}

	private void writePrimitive(Object val, DField df) {
		msg.writePrimitiveField(val, df, template);
	}

	public static void writeDFile(RocketMessage msg, Template template, DFile val) {
		msg.writeString(val.getId());
		msg.writeString(val.getName());
		msg.writeLong(val.getSize());
		msg.writeString(val.getMimeType());
	}

	private void readEmbedded(Object obj) {
		TemplateType tt = readType();
		if (tt == null) {
			// Log.info("r emb: null");
			return;
		}
		// Log.info("r emb: " + tt.getModel().getType());
		readObjectProperties(obj, tt);
	}

	public <T> List<T> readObjectCollection() {
		long num = readLong();
		List result = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			T obj = readObject();
			result.add(obj);
		}
		return result;
	}

	public <T> T readObject() {
		TemplateType tt = readType();
		return readObject(tt);
	}

	@SuppressWarnings("unchecked")
	public <T> T readObject(TemplateType tt) {

		if (tt == null) {
			// Log.info("r obj: null");
			return null;
		}
		long id = readLong();
		// Log.info("r obj: " + id);
		Object obj = null;
		if (id <= 0) {
			obj = localCache.get(id);
		} else if (tt.getModel().getModelType() != DModelType.STRUCT) {
			EntityHelper<?> entity;
			if (tt.getModel().getExternal() == null) {
				entity = helperService.get(tt.getModel().getType());
			} else {
				entity = helperService.getExternalSystem(tt.getModel().getExternal())
						.getHelper(tt.getModel().getType());
			}
			obj = entity.getById(id);
		}
		if (obj == null) {
			obj = tt.getModel().newInstance();
			if (obj instanceof DBObject) {
				DBObject dbobj = (DBObject) obj;
				if (id < 0) {
					localCache.put(id, dbobj);
				}
				dbobj.setLocalId(id);
			}
		}
		readObjectProperties(obj, tt);
		return (T) obj;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void readObjectProperties(Object obj, TemplateType tt) {
		while (true) {
			int fi = msg.readInt();
			if (fi < 0) {
				break;
			}
			DField df = tt.getField(fi);
			// Log.info("r field: " + fi + " " + df.getName());
			FieldType ft = df.getType();
			df.getValue(obj); // Just unproxy the object. This will help in creating documented objects
			switch (ft) {
			case Primitive:
				Object val = readPrimitive(df);
				df.setValue(obj, val);
				break;
			case PrimitiveCollection:
				List vals = readPrimitiveCollection(df, (List) df.getValue(obj));
				df.setValue(obj, vals);
				break;
			case Reference:
				if (df.getReference().isEmbedded()) {
					readEmbedded(df.getValue(obj));
				} else if (df.getReference().getType().equals("DFile")) {
					df.setValue(obj, readDFile());
				} else {
					df.setValue(obj, readObject());
				}
				break;
			case ReferenceCollection: {
				List colls;
				if (df.getReference().getType().equals("DFile")) {
					colls = readDFileCollection();
				} else {
					colls = readReferenceCollection((List) df.getValue(obj));
				}
				df.setValue(obj, colls);
			}
				break;
			case InverseCollection:
				throw new RuntimeException("Can not read InverseCollectgion: " + df.getName());
			default:
				break;
			}
		}
	}

	private List readColl(List old, Supplier itemReader) {
		int size = msg.readInt();
		// Log.info("r coll: " + size);
		if (size < 0) {
			old = new ArrayList<>(old);
			size = -size;
			for (int i = 0; i < size; i++) {
				int idx = msg.readInt();
				if (idx < 0) {
					idx = -idx;
					idx--;
					old.remove(idx);
				} else {
					idx--;
					Object obj = itemReader.get();
					if (idx >= old.size()) {
						old.add(obj);
					} else {
						old.add(idx, obj);
					}
				}
			}
			return old;
		} else {
			List colls = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				colls.add(itemReader.get());
			}
			return colls;
		}
	}

	private List readReferenceCollection(List old) {
		return readColl(old, () -> readObject());
	}

	private List readPrimitiveCollection(DField field, List old) {
		return readColl(old, () -> readPrimitive(field));
	}

	private Object readPrimitive(DField df) {
		return msg.readPrimitive(df, template);
	}

	public <T> T readEnum() {
		TemplateType type = readType();
		if (type == null) {
			// Log.info("r enum: null");
			return null;
		}
		int fidx = msg.readInt();
		DField<?, ?> field = type.getField(fidx);
		// Log.info("r enum: " + field.getName());
		return (T) field.getValue(null);
	}

	public <T extends Enum> void writeEnum(int enumType, T val) {
		int clientTypeIdx = template.toClientTypeIdx(enumType);
		msg.writeInt(clientTypeIdx);
		TemplateType type = template.getType(clientTypeIdx);
		DField<?, ?> field = type.getModel().getField(val.name());
		int clientIdx = type.toClientIdx(field.getIndex());
		msg.writeInt(clientIdx);
	}

	public double readDouble() {
		double d = msg.readDouble();
		// Log.info("r double: " + d);
		return d;
	}

	public List<Double> readDoubleCollection() {
		return readColl(ListExt.List(), () -> readDouble());
	}

	public boolean readBoolean() {
		boolean b = msg.readBoolean();
		// Log.info("r bool: " + b);
		return b;
	}

	public List<Boolean> readBooleanCollection() {
		return readColl(ListExt.List(), () -> readBoolean());
	}

	public DFile readDFile() {
		return msg.readDFile();
	}

	public List<DFile> readDFileCollection() {
		return readColl(ListExt.List(), () -> readDFile());
	}

	public void readGeolocationCollection() {
		readColl(ListExt.List(), () -> readGeolocation());
	}

	public TemplateType readType() {
		int type = msg.readInt();
		if (type == -1) {
			return null;
		}
		return template.getType(type);
	}

	public String readString() {
		String s = msg.readString();
		return s;
	}

	public List<String> readStringCollection() {
		return readColl(ListExt.List(), () -> readString());
	}

	public void writeStringList(List<String> list) {
		msg.writeStringList(list);
	}

	public void writeBoolean(boolean val) {
		msg.writeBoolean(val);
	}

	public void writeBooleanList(List<Boolean> list) {
		msg.writeBooleanList(list);
	}

	public void writeDFile(DFile val) {
		msg.writeDFile(val);
	}

	public void writeString(String str) {
		msg.writeString(str);
	}

	public void writeLong(long val) {
		msg.writeLong(val);
	}

	public void writeLongList(List<Long> val) {
		msg.writeLongList(val);
	}

	public void writeDouble(double val) {
		msg.writeDouble(val);
	}

	public void writeDoubleList(List<Double> list) {
		msg.writeDoubleList(list);
	}

	public LocalDate readLocalDate() {
		return msg.readDate();
	}

	public List<LocalDate> readLocalDateCollection() {
		return readColl(ListExt.List(), () -> readLocalDate());
	}

	public void writeLocalDate(LocalDate date) {
		msg.writeLocalDate(date);
	}

	public void writeLocalDateList(List<LocalDate> list) {
		msg.writeLocalDateList(list);
	}

	public LocalDateTime readDateTime() {
		return msg.readDateTime();
	}

	public LocalDateTime readLocalDateTime() {
		return readDateTime();
	}

	public List<LocalDateTime> readLocalDateTimeCollection() {
		return readColl(ListExt.List(), () -> readLocalDateTime());
	}

	public LocalTime readTime() {
		return msg.readTime();
	}

	public LocalTime readLocalTime() {
		return readTime();
	}

	public Geolocation readGeolocation() {
		return msg.readGeolocation();
	}

	public List<LocalTime> readLocalTimeCollection() {
		return readColl(ListExt.List(), () -> readLocalTime());
	}

	public Duration readDuration() {
		long micros = msg.readLong();
		return Duration.ofNanos(micros * 1000);
	}

	public List<Duration> readDurationCollection() {
		return readColl(ListExt.List(), () -> readDuration());
	}

	public void writeBlob(Blob changes) {
		msg.writeBlob(changes);
	}

	public void writeBlobList(List<Blob> list) {
		msg.writeBlobList(list);
	}

	public Blob readBlob() {
		return msg.readBlob();
	}

	public List<Blob> readBlobCollection() {
		return readColl(ListExt.List(), () -> readBlob());
	}

	public List<Long> readLongCollection() {
		return readColl(ListExt.List(), () -> readLong());
	}

	public void writeLocalDateTime(LocalDateTime result) {
		msg.writeLocalDateTime(result);
	}

	public void writeLocalDateTimeList(List<LocalDateTime> result) {
		msg.writeLocalDateTimeList(result);
	}

	public void writeLocalTime(LocalTime result) {
		msg.writeLocalTime(result);
	}

	public void writeLocalTimeList(List<LocalTime> result) {
		msg.writeLocalTimeList(result);
	}

	public void writeDuration(Duration result) {
		msg.writeDuration(result);
	}

	public void writeDurationList(List<Duration> result) {
		msg.writeDurationList(result);
	}

	public void writeDFileList(List<DFile> result) {
		msg.writeDFileList(result);
	}

	public void writeGeolocation(Geolocation result) {
		msg.writeGeolocation(result);
	}

	public void writeGeolocationList(List<Geolocation> result) {
		msg.writeGeolocationList(result);
	}
}
