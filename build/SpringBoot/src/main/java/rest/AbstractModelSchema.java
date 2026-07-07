package rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d3e.core.DFile;
import d3e.core.SchemaConstants;
import gqltosql.schema.DClazz;
import gqltosql.schema.DClazzMethod;
import gqltosql.schema.DField;
import gqltosql.schema.DModel;
import gqltosql.schema.DModelType;
import gqltosql.schema.DParam;
import gqltosql.schema.FieldPrimitiveType;
import gqltosql.schema.FieldType;
import gqltosql.schema.IModelSchema;
import jakarta.annotation.PostConstruct;
import store.D3EPersistanceList;
import store.DBChange;
import store.DBObject;
import store.DBSaveStatus;
import store.DatabaseObject;

public abstract class AbstractModelSchema implements IModelSchema {
	protected Map<String, DModel<?>> allTypes = new HashMap<>();
	private DModel<?>[] all;
	private Map<String, DClazz> allChannels = new HashMap<>();
	private DClazz[] channels;
	private Map<String, DClazz> allRPCs = new HashMap<>();
	private DClazz[] rpcs;

	@PostConstruct
	public void init() {
		createAllPrimitives();
		createAllEnums();
		createAllTables();
		addFields();
		computeHierarchyTypes();
		recordAllChannels();
		recordAllRPCs();
	}

	@Override
	public DModel<?> get(DBObject obj) {
		return getType(obj._typeIdx());
	}

	protected int getTotalCount() {
		return SchemaConstants._TOTAL_COUNT;
	}

	private void computeHierarchyTypes() {
		all = new DModel<?>[getTotalCount()];
		for (DModel m : allTypes.values()) {
			all[m.getIndex()] = m;
		}
		for (DModel m : all) {
			if (m == null) {
				continue;
			}
			computeHierarchyType(m);
		}
	}

	protected void computeHierarchyType(DModel m) {
		List<Integer> types = new ArrayList<Integer>();
		DModel temp = m;
		while (temp != null) {
			types.add(0, temp.getIndex());
			temp = temp.getParent();
		}
		addSubTypes(m, types);
		int[] allTypes = new int[types.size()];
		int index = 0;
		while (index < allTypes.length) {
			allTypes[index] = types.get(index);
			index++;
		}
		m.setAllTypes(allTypes);
	}

	private void addSubTypes(DModel model, List<Integer> types) {
		for (DModel m : all) {
			if (m != null && m.getParent() == model) {
				types.add(m.getIndex());
				addSubTypes(m, types);
			}
		}
	}

	protected abstract void addFields();

	protected abstract void createAllTables();

	protected abstract void createAllEnums();

	protected void recordAllChannels() {
	}

	protected void recordAllRPCs() {
	}

	public List<DModel<?>> getAllTypes() {
		return new ArrayList<>(allTypes.values());
	}

	public DModel<?> getType(String type) {
		return allTypes.get(type);
	}

	public DModel<?> getType(int index) {
		return all[index];
	}

	public <T> DModel<T> getType2(String type) {
		return ((DModel<T>) allTypes.get(type));
	}

	protected void addTable(DModel<?> model) {
		allTypes.put(model.getType(), model);
	}

	protected void addEnum(Class<? extends Enum<?>> enm, int index) {
		Enum<?>[] constants = enm.getEnumConstants();
		DModel<?> dm = new DModel<>(enm.getSimpleName(), index, constants.length, 0, null, DModelType.ENUM);
		int idx = 0;
		for (Enum<?> e : constants) {
			dm.addPrimitive(e.name(), idx++, null, FieldPrimitiveType.Enum, (s) -> e, null, null);
		}
		addTable(dm);
	}

	private void createAllPrimitives() {
		addTable(new DModel<>("void", SchemaConstants.Void, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("String", SchemaConstants.String, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("Integer", SchemaConstants.Integer, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("Double", SchemaConstants.Double, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("Boolean", SchemaConstants.Boolean, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("Date", SchemaConstants.Date, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("DateTime", SchemaConstants.DateTime, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("Time", SchemaConstants.Time, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("Duration", SchemaConstants.Duration, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("Blob", SchemaConstants.Blob, 0, 0, null, DModelType.PRIMITIVE));
		addTable(new DModel<>("Geolocation", SchemaConstants.Geolocation, 0, 0, null, DModelType.PRIMITIVE));
	}

	protected void addDFileFields() {
		DModel<DFile> m = getType2("DFile");
		m.addPrimitive("id", 0, "_id", FieldPrimitiveType.String, (s) -> s.getId(), (s, v) -> s.setId(v), null)
				.notNull();
		m.addPrimitive("name", 1, "_name", FieldPrimitiveType.String, (s) -> s.getName(), (s, v) -> s.setName(v), null);
		m.addPrimitive("size", 2, "_size", FieldPrimitiveType.Integer, (s) -> s.getSize(), (s, v) -> s.setSize(v), null)
				.notNull();
		m.addPrimitive("mimeType", 3, "_mime_type", FieldPrimitiveType.String, (s) -> s.getSize(),
				(s, v) -> s.setSize(v), null).notNull();
	}

	protected void recordNumChannels(int num) {
		this.channels = new DClazz[num];
	}

	protected void recordNumRPCs(int num) {
		this.rpcs = new DClazz[num];
	}

	protected DClazz addChannel(String name, int index, int numMsgs) {
		return addClazz(name, index, numMsgs, false);
	}

	protected DClazz addRPCClass(String name, int index, int numMsgs) {
		return addClazz(name, index, numMsgs, true);
	}

	private DClazz addClazz(String name, int index, int numMsgs, boolean rpc) {
		DClazz channel = new DClazz(name, index, numMsgs);
		if (rpc) {
			rpcs[index] = channel;
			allRPCs.put(name, channel);
		} else {
			channels[index] = channel;
			allChannels.put(name, channel);
		}
		return channel;
	}

	protected void populateChannel(DClazz channel, int msgIndex, String msgName, DParam... params) {
		channel.addMethod(msgIndex, new DClazzMethod(msgName, msgIndex, params));
	}

	protected void populateRPC(DClazz channel, int msgIndex, String msgName, DParam... params) {
		populateRPC(channel, msgIndex, msgName, -1, params);
	}

	protected void populateRPC(DClazz channel, int msgIndex, String msgName, int returnType, DParam... params) {
		populateRPC(channel, msgIndex, msgName, returnType, false, params);
	}

	protected void populateRPC(DClazz channel, int msgIndex, String msgName, int returnType, boolean returnColl,
			DParam... params) {
		channel.addMethod(msgIndex, new DClazzMethod(msgName, msgIndex, returnType, returnColl, params));
	}

	@Override
	public List<DClazz> getAllChannels() {
		return Arrays.asList(this.channels);
	}

	@Override
	public DClazz getChannel(String name) {
		return this.allChannels.get(name);
	}

	@Override
	public List<DClazz> getAllRPCs() {
		return Arrays.asList(this.rpcs);
	}

	@Override
	public DClazz getRPC(String name) {
		return this.allRPCs.get(name);
	}
	
	@Override
	public long getDatabaseId(DatabaseObject obj) {
		return obj.getId();
	}
	
	@Override
	public long extractCompoundId(long id) {
		return id;
	}
	
	@Override
	public DField<?, ?> getCollectionField(DModel<?> type, D3EPersistanceList<?> list) {
		return type.getField(list.getField());
	}

	@Override
	public long createCompoundId(int type, long id) {
		return id;
	}
	
	@Override
	public void assignObjectId(DModel<?> type, DatabaseObject obj, long id) {
		obj.setId(id);
	}
	
	@Override
	public DatabaseObject createNewInstance(int type, long id) {
		DModel<?> dm = getType(type);
		DatabaseObject ins = (DatabaseObject) dm.newInstance();
		ins.setId(id);
		if(!dm.isCreatable() && dm.isDocument()) {
			ins.setSaveStatus(DBSaveStatus.Saved);
			return ins;
		}
		markCollectionsAsProxy(dm, ins);
		ins.setSaveStatus(DBSaveStatus.Saved);
		ins.postLoad();
		ins._markProxy();
		return ins;
	}

	private void markCollectionsAsProxy(DModel<?> dm, DatabaseObject ins) {
		for (DField df : dm.getFields()) {
			FieldType type = df.getType();
			switch (type) {
			case InverseCollection:
			case PrimitiveCollection:
			case ReferenceCollection:
				List list = (List) df.getValue(ins);
				if (list instanceof D3EPersistanceList) {
					((D3EPersistanceList) list)._markProxy();
				}
			}
		}
		if (dm.getParent() != null) {
			markCollectionsAsProxy(dm.getParent(), ins);
		}
	}
	
	@Override
	public DBChange findChanges(DBObject _this) {
		return _this._changes();
	}
	
	@Override
	public void markInProxy(DatabaseObject obj, boolean inProxy) {
		if(inProxy) {
			obj._markInProxy();
		} else {
			obj._clearInProxy();
			obj._clearProxy();
		}
	}
}
