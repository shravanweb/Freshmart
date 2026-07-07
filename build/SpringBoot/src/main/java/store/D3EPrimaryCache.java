package store;

import java.util.HashMap;
import java.util.Map;

import d3e.core.DFile;
import gqltosql.schema.IModelSchema;

public class D3EPrimaryCache {

	private Map<Integer, Map<Long, DatabaseObject>> data = new HashMap<>();
	private Map<String, DFile> files = new HashMap<>();
	private String repo;
	private IModelSchema schema;

	public D3EPrimaryCache(String repo, IModelSchema schema) {
		this.repo = repo;
		this.schema = schema;
	}

	public DatabaseObject get(int type, long id) {
		Map<Long, DatabaseObject> byType = data.get(type);
		if (byType == null) {
			return null;
		}
		return byType.get(id);
	}

	public void add(DatabaseObject ins, int type) {
		Map<Long, DatabaseObject> byType = data.get(type);
		if (byType == null) {
			byType = new HashMap<>();
			data.put(type, byType);
		}
		byType.put(ins.getId(), ins);
	}

	public DatabaseObject getOrCreate(int type, long id) {
		if (id == 0l) {
			return null;
		}
		long cid = schema.createCompoundId(type, id);
		DatabaseObject obj = get(type, cid);
		if (obj == null) {
			DatabaseObject ins= schema.createNewInstance(type, cid);
			add(ins, type);
			return ins;
		}
		return obj;
	}

	public DFile getOrCreateDFile(String id) {
		if (id == null) {
			return null;
		}
		DFile file = files.get(id);
		if (file == null) {
			file = new DFile();
			file.setId(id);
			file._markProxy(repo);
			files.put(id, file);
		}
		return file;
	}

}
