package rest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import d3e.core.DFile;
import d3e.core.ListExt;
import gqltosql.schema.DField;
import gqltosql.schema.DFlatField;
import gqltosql.schema.DModel;
import gqltosql.schema.FieldType;
import gqltosql.schema.IModelSchema;
import store.DBObject;
import store.DatabaseObject;
import store.EntityHelperService;

public class JSONInputContext extends GraphQLInputContext implements IDocumentReader {

	private JSONObject json;
	private IModelSchema schema;

	public static <T> T fromJsonString(String doc, long id, String type, IModelSchema schema) {
		if (doc == null) {
			return null;
		}
		try {
			JSONObject json = new JSONObject(doc);
			json.put("id", id);
			JSONInputContext ctx = new JSONInputContext(json, EntityHelperService.getInstance(), null, null, schema);
			return ctx.readObject(type, true);
		} catch (JSONException e) {
			return null;
		}
	}

	public static <T> T parse(String doc, String type, IModelSchema schema) {
		if (doc == null) {
			return null;
		}
		try {
			JSONObject json = new JSONObject(doc);
			JSONInputContext ctx = new JSONInputContext(json, EntityHelperService.getInstance(), null, null, schema);
			return ctx.readObject(type, true);
		} catch (JSONException e) {
			return null;
		}
	}

	public static List parseColl(String doc, String type, IModelSchema schema) {
		if (doc == null) {
			return null;
		}
		try {
			JSONArray array = new JSONArray(doc);
			List result = ListExt.List();
			int sz = array.length();
			for (int i = 0; i < sz; i++) {
				JSONObject obj = array.getJSONObject(i);
				JSONInputContext ctx = new JSONInputContext(obj, EntityHelperService.getInstance(), null, null, schema);
				result.add(ctx.readObject(type, true));
			}
			return result;
		} catch (JSONException e) {
			return null;
		}
	}

	public static <T extends DBObject> T readToObjFromJson(T obj, String json, String type, IModelSchema schema) {
		if (json == null || type == null) {
			return null;
		}

		JSONObject jsonObj = new JSONObject(json);
		JSONInputContext ctx = new JSONInputContext(jsonObj, EntityHelperService.getInstance(), null, null, schema);
		DModel<?> model = schema.getType(type);
		ctx.readObjectProperties(obj, model);
		return obj;
	}

	public static String toJsonString(DatabaseObject obj, String type, IModelSchema schema) {
		if (obj == null) {
			return null;
		}
		JSONInputContext ctx = new JSONInputContext(new JSONObject(), EntityHelperService.getInstance(), null, null,
				schema);
		return ctx.toJsonString(obj, type);
	}

	public JSONInputContext(JSONObject json, EntityHelperService helperService, Map<Long, Object> inputObjectCache,
			Map<String, DFile> files, IModelSchema schema) {
		super(helperService, inputObjectCache, files);
		this.json = json;
		this.schema = schema;
	}

	@Override
	protected JSONInputContext createContext(String field) {
		try {
			if (json.isNull(field)) {
				return null;
			}
			JSONObject obj = json.getJSONObject(field);
			if (obj == null) {
				return null;
			}
			return createReadContext(obj);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean has(String field) {
		return json.has(field);
	}

	@Override
	public <T> T readRef(String field, String type) {
		try {
			if (json.isNull(field)) {
				return null;
			}
			Object obj = json.get(field);
			if (obj instanceof JSONObject) {
				GraphQLInputContext ctx = createContext(field);
				return ctx.readObject(type, false);
			} else if (obj == JSONObject.NULL) {
				return null;
			} else {
				return (T) readRef(helperService.get(type), json.getLong(field));
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	protected <T> T readObject(String type, boolean readFully) {
		T obj = super.readObject(type, readFully);
		if (obj instanceof DatabaseObject) {
			DatabaseObject dbObj = (DatabaseObject) obj;
			if (readFully) {
				readObjectProperties(dbObj, schema.getType(dbObj._typeIdx()));
				dbObj._clearProxy();
			}
		}
		return obj;
	}

	@SuppressWarnings("rawtypes")
	public void readObjectProperties(Object _this, DModel<?> type) {
		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			DField df = type.getField(key);
			if (df == null || df instanceof DFlatField) {
				continue;
			}
			
			FieldType ft = df.getType();
			switch (ft) {
			case Primitive:
				readPrimitive(df, _this, key);
				break;
			case PrimitiveCollection:
				readPrimitiveColl(df, _this, key);
				break;
			case Reference:
				if (df.getReference().isEmbedded()) {
					readEmbedded(key, df.getReference().getType(), df.getValue(_this));
				} else if (df.getReference().getType().equals("DFile")) {
					df.setValue(_this, readDFile(key));
				} else if (df.isChild()) {
					// Child
					JSONInputContext ctx = createContext(key);
					df.setValue(_this, ctx.readObject(df.getReference().getType(), true));
				} else {
					// Reference
					df.setValue(_this, readRef(key, df.getReference().getType()));
				}
				break;
			case ReferenceCollection:
				List colls;
				if (df.getReference().getType().equals("DFile")) {
					colls = readDFileColl(key);
				} else if (df.isChild()) {
					colls = readChildColl(key, df.getReference().getType());
				} else {
					colls = readRefColl(key, df.getReference().getType());
				}
				df.setValue(_this, colls);
				break;
			case InverseCollection:
				// TODO
				break;
			default:
				break;
			}
		}
	}

	@Override
	public <T> T readEmbedded(String field, String type, T exists) {
		JSONInputContext ctx = createContext(field);
		if (ctx == null) {
			return exists;
		}
		return ctx.readObject(type, true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void readPrimitive(DField df, Object _this, String field) {
		Object converted;
		switch (df.getPrimitiveType()) {
		case Boolean:
			converted = readBoolean(field);
			break;
		case DFile:
			converted = readDFile(field);
			break;
		case Date:
			converted = readDate(field);
			break;
		case DateTime:
			converted = readDateTime(field);
			break;
		case Double:
			converted = readDouble(field);
			break;
		case Duration:
			converted = readDuration(field);
			break;
		case Enum:
			converted = readEnum(field, df.getReference());
			break;
		case Integer:
			converted = readInteger(field);
			break;
		case String:
			converted = readString(field);
			break;
		case Time:
			converted = readTime(field);
			break;
		default:
			throw new RuntimeException("Unsupported type. " + df.getPrimitiveType());
		}
		df.setValue(_this, converted);
	}

	private Object readEnum(String field, DModel<?> enumType) {
		String name = json.getString(field);
		return readEnum(enumType, name);
	}

	private Object readEnum(DModel<?> type, String name) {
		DField<?, ?> field2 = type.getField(name);
		return field2.getValue(null);
	}

	@Override
	public <T> T readChild(String field, String type) {
		GraphQLInputContext ctx = createContext(field);
		if (ctx == null) {
			return null;
		}
		return ctx.readObject(type, true);
	}

	@Override
	public <T> T readUnion(String field, String type) {
		try {
			if (json.isNull(field)) {
				return null;
			}
			JSONObject obj = json.getJSONObject(field);
			String _type = obj.getString("__typeName");
			JSONInputContext ctx = createReadContext(obj.getJSONObject("value" + _type));
			return ctx.readObject(_type, true);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long readLong(String field) {
		try {
			if (json.isNull(field)) {
				return 0l;
			}
			return json.getLong(field);
		} catch (JSONException e) {
			return 0;
		}
	}

	@Override
	public String readString(String field) {
		try {
			if (json.isNull(field)) {
				return null;
			}
			return json.getString(field);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long readInteger(String field) {
		try {
			if (json.isNull(field)) {
				return 0l;
			}
			return json.getLong(field);
		} catch (JSONException e) {
			return 0;
		}
	}

	@Override
	public double readDouble(String field) {
		try {
			if (json.isNull(field)) {
				return 0.0;
			}
			return json.getDouble(field);
		} catch (JSONException e) {
			return 0.0;
		}
	}

	@Override
	public boolean readBoolean(String field) {
		try {
			if (json.isNull(field)) {
				return false;
			}
			return json.getBoolean(field);
		} catch (JSONException e) {
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void readPrimitiveColl(DField df, Object _this, String field) {
		Object converted;
		switch (df.getPrimitiveType()) {
		case Boolean:
			converted = readBooleanColl(field);
			break;
		case DFile:
			converted = readDFileColl(field);
			break;
		case Date:
			converted = readDateColl(field);
			break;
		case DateTime:
			converted = readDateTimeColl(field);
			break;
		case Double:
			converted = readDoubleColl(field);
			break;
		case Duration:
			converted = readDurationColl(field);
			break;
		case Enum:
			converted = readEnumColl(field, df.getReference());
			break;
		case Integer:
			converted = readIntegerColl(field);
			break;
		case String:
			converted = readStringColl(field);
			break;
		case Time:
			converted = readTimeColl(field);
			break;
		default:
			throw new RuntimeException("Unsupported type. " + df.getPrimitiveType());
		}
		df.setValue(_this, converted);
	}

	private Object readEnumColl(String field, DModel<?> enumType) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				String val = array.getString(i);
				res.add(readEnum(enumType, val));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private List<Duration> readDurationColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<Duration> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				long val = array.getLong(i);
				res.add(readDuration(val));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private List<Double> readDoubleColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<Double> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				res.add(array.getDouble(i));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private List<LocalDateTime> readDateTimeColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<LocalDateTime> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				Object val = array.get(i);
				res.add(readDateTimeFromObj(val));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private List<LocalDate> readDateColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<LocalDate> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				Object val = array.get(i);
				res.add(readDateFromObj(val));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private List<LocalTime> readTimeColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<LocalTime> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				Object val = array.get(i);
				res.add(readTimeFromObj(val));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private List<Boolean> readBooleanColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<Boolean> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				res.add(array.getBoolean(i));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Long> readLongColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<Long> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				res.add(array.getLong(i));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Long> readIntegerColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<Long> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				res.add(array.getLong(i));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> readStringColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<String> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				res.add(array.getString(i));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> List<T> readUnionColl(String field, String type) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<T> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				JSONObject obj = array.getJSONObject(i);
				String _type = obj.getString("__typeName");
				JSONInputContext ctx = createReadContext(obj.getJSONObject("value" + _type));
				res.add(ctx.readObject(_type, true));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> List<T> readChildColl(String field, String type) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<T> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				JSONInputContext ctx = createReadContext(array.getJSONObject(i));
				res.add(ctx.readObject(type, true));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	protected JSONInputContext createReadContext(JSONObject json) {
		return new JSONInputContext(json, helperService, inputObjectCache, files, schema);
	}

	@Override
	public <T> List<T> readRefColl(String field, String type) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<T> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				Object obj = array.get(i);
				if (obj instanceof JSONObject) {
					JSONInputContext ctx = createReadContext((JSONObject) obj);
					res.add(ctx.readObject(type, false));
				} else {
					res.add((T) readRef(helperService.get(type), array.getLong(i)));
				}
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T extends Enum<?>> List<T> readEnumColl(String field, Class<T> cls) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<T> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				res.add(readEnumInternal(array.getString(i), cls));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DFile> readDFileColl(String field) {
		try {
			JSONArray array = json.getJSONArray(field);
			int length = array.length();
			List<DFile> res = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				JSONInputContext ctx = createReadContext(array.getJSONObject(i));
				res.add(readDFileInternal(ctx));
			}
			return res;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Duration readDuration(String field) {
		try {
			Long millis = json.getLong(field);
			return readDuration(millis);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private Duration readDuration(long millis) {
		if (millis == 0) {
			return null;
		}
		return Duration.ofMillis(millis);
	}

	@Override
	public LocalDateTime readDateTime(String field) {
		try {
			Object obj = json.get(field);
			return readDateTimeFromObj(obj);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private LocalDateTime readDateTimeFromObj(Object obj) {
		if (obj instanceof String) {
			return LocalDateTime.parse((String) obj);
		} else {
			return null;
		}
	}

	@Override
	public LocalDate readDate(String field) {
		try {
			Object obj = json.get(field);
			return readDateFromObj(obj);

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private LocalDate readDateFromObj(Object obj) {
		if (obj instanceof String) {
			return LocalDate.parse((String) obj);
		} else {
			return null;
		}
	}

	@Override
	public LocalTime readTime(String field) {
		try {
			Object obj = json.get(field);
			return readTimeFromObj(obj);

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private LocalTime readTimeFromObj(Object obj) {
		if (obj instanceof String) {
			return LocalTime.parse((String) obj);
		} else {
			return null;
		}
	}

	public String toJsonString(DatabaseObject obj, String type) {
		return getObjAsJson(type, obj, true).toString();
	}

	public void writeBooleanColl(String field, List<Boolean> coll) {
		try {
			JSONArray array = new JSONArray();
			for (Boolean t : coll) {
				array.put(t);
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	public void writeDFileColl(String field, List<DFile> coll) {
		try {
			JSONArray array = new JSONArray();
			for (DFile t : coll) {
				array.put(getDFileJson(t));
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeLongColl(String field, List<Long> coll) {
		try {
			JSONArray array = new JSONArray();
			for (Long t : coll) {
				array.put(t);
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeStringColl(String field, List<String> coll) {
		try {
			JSONArray array = new JSONArray();
			for (String t : coll) {
				array.put(t);
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	public void writeDoubleColl(String field, List<Double> coll) {
		try {
			JSONArray array = new JSONArray();
			for (Double t : coll) {
				array.put(t);
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	public void writeDateColl(String field, List<LocalDate> coll) {
		try {
			JSONArray array = new JSONArray();
			for (LocalDate t : coll) {
				array.put(t.toString());
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	public void writeDateTimeColl(String field, List<LocalDateTime> coll) {
		try {
			JSONArray array = new JSONArray();
			for (LocalDateTime t : coll) {
				array.put(t.toString());
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	public void writeTimeColl(String field, List<LocalTime> coll) {
		try {
			JSONArray array = new JSONArray();
			for (LocalTime t : coll) {
				array.put(t.toString());
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	public void writeDurationColl(String field, List<Duration> coll) {
		try {
			JSONArray array = new JSONArray();
			for (Duration t : coll) {
				array.put(t.toMillis());
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	@Override
	public <T> void writeChildColl(String field, List<T> coll, String type) {
		try {
			JSONArray array = new JSONArray();
			for (T t : coll) {
				JSONInputContext ctx = createWriteContext();
				array.put(ctx.getObjAsJson(type, (DatabaseObject) t, true));
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	public <T extends DatabaseObject> void writeRefColl(String field, List<T> coll, String type) {
		if (coll.isEmpty()) {
			return;
		}
		try {
			JSONArray array = new JSONArray();
			for (T t : coll) {
				String realType = t.getClass().getSimpleName();
				JSONInputContext ctx = createWriteContext();
				if (!realType.equals(type)) {
					ctx.writeString("__typename", realType, type);
				}
				ctx.writeLong("id", t.getId(), 0);
				array.put(ctx.json);
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	@Override
	public <T extends DatabaseObject> void writeRef(String field, T obj, String type) {
		if (obj == null) {
			return;
		}
		JSONInputContext ctx = createWriteContext();
		json.put(field, ctx.getObjAsJson(type, obj, false));
	}

	@Override
	public <T> void writeUnionColl(String field, List<T> coll) {
		try {
			JSONArray array = new JSONArray();
			for (T t : coll) {
				JSONObject union = new JSONObject();
				String type = t.getClass().getSimpleName();
				union.put("__typename", type);
				JSONInputContext ctx = createWriteContext();
				union.put("value" + type, ctx.getObjAsJson(type, (DatabaseObject) t, true));
				array.put(union);
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	@Override
	public <T extends Enum<?>> void writeEnumColl(String field, List<T> coll) {
		try {
			JSONArray array = new JSONArray();
			for (T t : coll) {
				array.put(t.name());
			}
			json.put(field, array);
		} catch (JSONException e) {
		}
	}

	@Override
	public <T> void writeChild(String field, T obj, String type) {
		if (obj == null) {
			return;
		}
		try {
			JSONInputContext ctx = createWriteContext();
			json.put(field, ctx.getObjAsJson(type, (DatabaseObject) obj, true));
		} catch (JSONException e) {
		}
	}

	@Override
	public <T> void writeUnion(String field, T obj) {
		if (obj == null) {
			return;
		}
		try {
			String type = obj.getClass().getSimpleName();
			JSONInputContext ctx = createWriteContext();
			JSONObject union = new JSONObject();
			union.put("value" + type, ctx.getObjAsJson(type, (DatabaseObject) obj, true));
			json.put(field, union);
		} catch (JSONException e) {
		}
	}

	@Override
	public <T> void writeEmbedded(String field, T obj) {
		try {
			JSONInputContext ctx = createWriteContext();
			String type = obj.getClass().getSimpleName();
			ctx.writeObj(field, type, (DatabaseObject) obj);
			json.put(field, ctx.json);
		} catch (JSONException e) {
		}
	}

	private void writeObj(String field, String type, DatabaseObject obj) {
		if (obj == null) {
			return;
		}
		json.put(field, getObjAsJson(type, obj, true));
	}

	private JSONObject getObjAsJson(String type, DatabaseObject obj, boolean writeFull) {
		DModel<?> type2 = schema.getType(obj._type());
		String realType = type2.getType();
		JSONInputContext ctx = createWriteContext();
		if (!realType.equals(type)) {
			ctx.writeString("__typeName", realType, type);
		}
		if (shouldWriteId(type2)) {
			ctx.json.put("id", obj.getId());
		}
		if (writeFull) {
			ctx.writeObjProperties(obj, type2);
		}
		return ctx.json;
	}

	private boolean shouldWriteId(DModel<?> type) {
		return !type.isEmbedded();
	}

	private <T extends DatabaseObject> void writeObjProperties(T _this, DModel<?> type) {
		for (DField df : type.getFields()) {
			FieldType ft = df.getType();
			Object value = df.getValue(_this);
			if (value == null) {
				continue;
			}
			String field = df.getName();
			switch (ft) {
			case Primitive:
				writePrimitive(field, value, df);
				break;
			case PrimitiveCollection:
				writePrimitiveColl(field, (List) value, df);
				break;
			case Reference: {
				if (df.getReference().isEmbedded()) {
					writeChild(field, (T) value, df.getReference().getType());
				} else if (df.getReference().getType().equals("DFile")) {
					writeDFile(field, (DFile) value);
				} else if (df.isChild()) {
					// Child
					writeChild(field, (T) value, df.getReference().getType());
				} else {
					// Reference
					writeRef(field, (T) value, df.getReference().getType());
				}

			}
				break;
			case ReferenceCollection: {
				List list = (List) value;
				if (df.getReference().getType().equals("DFile")) {
					writeDFileColl(field, list);
				} else if (df.isChild()) {
					writeChildColl(field, list, df.getReference().getType());
				} else {
					writeRefColl(field, list, df.getReference().getType());
				}
			}
				break;
			case InverseCollection:
				// TODO
				break;
			default:
				break;
			}
		}
	}

	private void writePrimitive(String field, Object value, DField df) {
		switch (df.getPrimitiveType()) {
		case Boolean:
			writeBoolean(field, (boolean) value, false);
			break;
		case DFile:
			writeDFile(field, (DFile) value);
			break;
		case Date:
			writeDate(field, (LocalDate) value);
			break;
		case DateTime:
			writeDateTime(field, (LocalDateTime) value);
			break;
		case Double:
			writeDouble(field, (double) value, 0);
			break;
		case Duration:
			writeDuration(field, (Duration) value);
			break;
		case Enum:
			if (value instanceof String) {
				writeString(field, (String) value, null);
			} else {
				Enum<?> enm = (Enum<?>) value;
				String name = enm.name();
				writeString(field, name, null);
			}
			break;
		case Integer:
			writeInteger(field, (long) value, 0);
			break;
		case String:
			writeString(field, (String) value, null);
			break;
		case Time:
			writeTime(field, (LocalTime) value);
			break;
		default:
			break;
		}
	}

	private void writePrimitiveColl(String field, List value, DField df) {
		if (value == null) {
			return;
		}
		switch (df.getPrimitiveType()) {
		case Boolean:
			writeBooleanColl(field, value);
			break;
		case DFile:
			writeDFileColl(field, value);
			break;
		case Date:
			writeDateColl(field, value);
			break;
		case DateTime:
			writeDateTimeColl(field, value);
			break;
		case Double:
			writeDoubleColl(field, value);
			break;
		case Duration:
			writeDurationColl(field, value);
			break;
		case Enum:
			writeEnumColl(field, value);
			break;
		case Integer:
			writeLongColl(field, value);
			break;
		case String:
			writeStringColl(field, value);
			break;
		case Time:
			writeTimeColl(field, value);
			break;
		default:
			break;

		}
	}

	private JSONInputContext createWriteContext() {
		JSONInputContext ctx = new JSONInputContext(new JSONObject(), helperService, null, null, schema);
		return ctx;
	}

	@Override
	public <T extends Enum<?>> void writeEnum(String field, T val, T def) {
		if (val == null || val == def) {
			return;
		}
		try {
			json.put(field, val.name());
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeLong(String field, long val, long def) {
		try {
			if (val == def) {
				return;
			}
			json.put(field, val);
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeString(String field, String val, String def) {
		try {
			if (Objects.equals(val, def)) {
				return;
			}
			json.put(field, val);
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeInteger(String field, long val, long def) {
		try {
			if (val == def) {
				return;
			}
			json.put(field, val);
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeDouble(String field, double val, double def) {
		try {
			if (val == def) {
				return;
			}
			json.put(field, val);
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeBoolean(String field, boolean val, boolean def) {
		try {
			if (val == def) {
				return;
			}
			json.put(field, val);
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeDuration(String field, Duration val) {
		try {
			if (val == null) {
				return;
			}
			json.put(field, val.toMillis());
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeDateTime(String field, LocalDateTime val) {
		try {
			if (val == null) {
				return;
			}
			json.put(field, val.toString());
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeTime(String field, LocalTime val) {
		try {
			if (val == null) {
				return;
			}
			json.put(field, val.toString());
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeDate(String field, LocalDate val) {
		try {
			if (val == null) {
				return;
			}
			json.put(field, val.toString());
		} catch (JSONException e) {
		}
	}

	@Override
	public void writeDFile(String field, DFile val) {
		if (val == null) {
			return;
		}
		try {
			JSONObject obj = getDFileJson(val);
			json.put(field, obj);
		} catch (JSONException e) {
		}
	}

	private JSONObject getDFileJson(DFile val) {
		JSONObject obj = new JSONObject();
		obj.put("id", val.getId());
		obj.put("size", val.getSize());
		obj.put("name", val.getName());
		obj.put("mimeType", val.getMimeType());
		return obj;
	}
}
