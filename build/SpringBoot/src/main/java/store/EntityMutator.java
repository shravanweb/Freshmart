package store;

import java.util.Collection;

import d3e.core.DFile;

public interface EntityMutator {

	public void save(DatabaseObject obj, boolean internal);

	public void update(DatabaseObject obj, boolean internal);

	public void saveOrUpdate(DatabaseObject obj, boolean internal);

	public void finish();
	
	public void clear();

	public <T extends DatabaseObject> boolean delete(T obj, boolean internal);

	public <T extends DatabaseObject> void peformDeleteOrphan(Collection<T> oldList, Collection<T> newList);

	public <T extends DatabaseObject, H extends EntityHelper<T>> H getHelperByInstance(Object fullType);

	public void processOnLoad(Object entity, String repo);

	public void preUpdate(DatabaseObject obj);

	public void preDelete(DatabaseObject obj);

	public void markDirty(DatabaseObject obj, boolean inverse);

	public boolean isInDelete(Object obj, String repo);

	public void unproxy(DatabaseObject obj);

	public void unproxyCollection(D3EPersistanceList<?> list);

	public void unproxyDFile(DFile file, String repo);
}
