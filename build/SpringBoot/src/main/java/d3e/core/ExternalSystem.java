package d3e.core;

import java.util.List;

import graphql.schema.DataFetchingEnvironment;
import models.CreatableObject;
import rest.GraphQLInputContext;
import store.D3EPersistanceList;
import store.DatabaseObject;
import store.EntityHelper;
import store.IEntityManager;

public interface ExternalSystem {

	void save(CreatableObject obj, boolean internal);

	void delete(CreatableObject obj, boolean internal);

	<T> T getOne(String type, Long id);

	boolean unique(String checkIn, Long checkInId, String checkFor, Object value, String masterName, Long masterId);

	<T> T singleton(DataFetchingEnvironment env, Class<T> cls);

	<T> T create(GraphQLInputContext ctx, Class<T> cls);

	<T> T delete(Class<T> cls, String type, long gqlInputId);

	<T> T update(GraphQLInputContext ctx, Class<T> cls);
	
	<T> List<T> all(DataFetchingEnvironment env, Class<?> cls);

	void unproxy(DatabaseObject obj);

	void unproxyCollection(D3EPersistanceList<?> list);

	void unproxyDFile(DFile file);

	<T extends DatabaseObject, H extends EntityHelper<T>> H getHelperByInstance(Object fullType);

	<T extends DatabaseObject, H extends EntityHelper<T>> H getHelper(String fullType);

	IEntityManager getEntityManager();

	void createId(DatabaseObject obj);

	boolean trackDirty();
}
