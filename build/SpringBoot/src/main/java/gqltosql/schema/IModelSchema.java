package gqltosql.schema;

import java.util.List;

import store.D3EPersistanceList;
import store.DBChange;
import store.DBObject;
import store.DatabaseObject;

public interface IModelSchema {

	public List<DModel<?>> getAllTypes();

	public DModel<?> getType(String type);

	public DModel<?> getType(int index);
	
	public DModel<?> get(DBObject obj);

	public List<DClazz> getAllChannels();

	public DClazz getChannel(String name);

	public List<DClazz> getAllRPCs();

	public DClazz getRPC(String name);

	public long getDatabaseId(DatabaseObject obj);
	
	public DField<?, ?> getCollectionField(DModel<?> type, D3EPersistanceList<?> list);

	public long createCompoundId(int type, long id);

	public long extractCompoundId(long id);

	public DatabaseObject createNewInstance(int type, long id);

	public void assignObjectId(DModel<?> type, DatabaseObject obj, long id);

	public DBChange findChanges(DBObject _this);

	public void markInProxy(DatabaseObject obj, boolean inProxy);
}
