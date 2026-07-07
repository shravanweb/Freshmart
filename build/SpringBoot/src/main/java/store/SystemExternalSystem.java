package store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import d3e.core.DFile;
import d3e.core.ExternalSystem;
import graphql.schema.DataFetchingEnvironment;
import models.CreatableObject;
import rest.GraphQLInputContext;

@Service("system")
public class SystemExternalSystem implements ExternalSystem {

	@Autowired
	private D3EEntityManagerProvider manager;

	@Autowired
	private EntityHelperService helperService;

	@Override
	public void createId(DatabaseObject obj) {
		IEntityManager manager = getEntityManager();
		manager.createId(obj);
	}

	@Override
	public void save(CreatableObject obj, boolean internal) {
	}

	@Override
	public void delete(CreatableObject obj, boolean internal) {
	}

	@Override
	public <T> T getOne(String type, Long id) {
		return null;
	}

	@Override
	public boolean unique(String checkIn, Long checkInId, String checkFor, Object value, String masterName,
			Long masterId) {
		return false;
	}

	@Override
	public <T> T singleton(DataFetchingEnvironment env, Class<T> cls) {
		return null;
	}

	@Override
	public <T> T create(GraphQLInputContext ctx, Class<T> cls) {
		return null;
	}

	@Override
	public <T> T delete(Class<T> cls, String trype, long gqlInputId) {
		return null;
	}

	@Override
	public <T> T update(GraphQLInputContext ctx, Class<T> cls) {
		return null;
	}

	@Override
	public <T> List<T> all(DataFetchingEnvironment env, Class<?> cls) {
		return null;
	}

	@Override
	public boolean trackDirty() {
		return true;
	}
	///////

	@Override
	public void unproxy(DatabaseObject obj) {
		getEntityManager().unproxy(obj);
	}

	@Override
	public void unproxyCollection(D3EPersistanceList<?> list) {
		getEntityManager().unproxyCollection(list);
	}

	@Override
	public void unproxyDFile(DFile file) {
		getEntityManager().unproxyDFile(file);
	}

	@Override
	public <T extends DatabaseObject, H extends EntityHelper<T>> H getHelperByInstance(Object fullType) {
		return (H) this.helperService.get(fullType.getClass().getSimpleName());
	}

	@Override
	public <T extends DatabaseObject, H extends EntityHelper<T>> H getHelper(String fullType) {
		return (H) this.helperService.get(fullType);
	}

	@Override
	public IEntityManager getEntityManager() {
		return manager.get();
	}
}
