package store;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import d3e.core.DFile;
import d3e.core.Geolocation;
import d3e.core.ListExt;
import d3e.core.Log;
import d3e.core.SchemaConstants;
import gqltosql.schema.DDocField;
import gqltosql.schema.DField;
import gqltosql.schema.DModel;
import gqltosql.schema.FieldPrimitiveType;
import gqltosql.schema.FieldType;
import gqltosql.schema.IModelSchema;
import gqltosql2.AliasGenerator;
import jakarta.persistence.EntityNotFoundException;
import rest.JSONInputContext;

@Service
public class D3EEntityManagerProvider {

	private D3EQueryBuilder queryBuilder;
	private IModelSchema schema;
	private NamedParameterJdbcTemplate jdbcTemplate;

	private ThreadLocal<IEntityManager> entityManager = new ThreadLocal<>();

	private String repo;

	@Autowired
	public D3EEntityManagerProvider(D3EQueryBuilder queryBuilder, IModelSchema schema,
			NamedParameterJdbcTemplate jdbcTemplate) {
		this.queryBuilder = queryBuilder;
		this.schema = schema;
		this.jdbcTemplate = jdbcTemplate;
		this.repo = "system";
	}

	public boolean create(D3EPrimaryCache cache) {
		IEntityManager mgr = entityManager.get();
		if (mgr != null) {
			return false;
		}
		entityManager.set(new EntityManagerImpl(cache));
		return true;
	}

	public void clear() {
		entityManager.remove();
	}

	public IEntityManager get() {
		IEntityManager em = entityManager.get();
		if (em == null) {
			create(null);
			em = entityManager.get();
		}
		return em;
	}

	static class RowDocField extends RowField {
		public DModel type;

		public RowDocField(DModel type) {
			super(null);
			this.type = type;
		}
	}

	static class RowCustomField extends RowField {
		public ICustomFieldProcessor processor;
		public long customFieldId;

		public RowCustomField(ICustomFieldProcessor processor, long customFieldId, DField field) {
			super(field);
			this.processor = processor;
			this.customFieldId = customFieldId;
		}
	}

	static class RowField {
		DField field;
		List<RowField> subFields;

		public RowField(DField df) {
			this.field = df;
		}

		public RowField(DField df, List<RowField> subFields) {
			this.field = df;
			this.subFields = subFields;
		}
	}

	private class SimpleObjectMapper implements RowMapper<DatabaseObject> {
		private D3EPrimaryCache cache;

		public SimpleObjectMapper(D3EPrimaryCache cache) {
			this.cache = cache;
		}

		@Override
		public DatabaseObject mapRow(ResultSet rs, int rowNum) throws SQLException {
			int i = 1;
			long id = rs.getLong(i++);
			int refType = rs.getInt(i++);
			if (id == 0) {
				return null;
			}
			Object val = cache.getOrCreate(refType, id);
			return (DatabaseObject) val;
		}
	}

	private class SingleObjectMapper implements RowMapper<DatabaseObject> {

		private D3EPrimaryCache cache;
		private DModel dm;
		private List<RowField> selectedFields;
		private boolean unproxy;

		public SingleObjectMapper(D3EPrimaryCache cache, DModel dm, List<RowField> selectedFields, boolean unproxy) {
			this.cache = cache;
			this.dm = dm;
			this.selectedFields = selectedFields;
			this.unproxy = unproxy;
		}

		@Override
		public DatabaseObject mapRow(ResultSet rs, int rowNum) throws SQLException {
			int i = 1;
			long id = rs.getLong(i++);
			int type = rs.getInt(i++);
			if (id == 0) {
				return null;
			}
			DatabaseObject obj = cache.getOrCreate(type, id);
			if (unproxy) {
				schema.markInProxy(obj, true);
			}
			readObject(rs, i, obj, selectedFields);
			if (unproxy) {
				schema.markInProxy(obj, false);
			}
			return obj;
		}

		protected int readObject(ResultSet rs, int i, Object obj, List<RowField> fields) throws SQLException {
			for (RowField rf : fields) {
				if (rf instanceof RowCustomField) {
					RowCustomField cf = (RowCustomField) rf;
					i = cf.processor.readObject(rs, i, obj, cf.field, cf.customFieldId);
					continue;
				}
				if (rf instanceof RowDocField) {
					RowDocField df = (RowDocField) rf;
					String doc = rs.getString(i++);
					((DatabaseObject) obj)._setDoc(doc);
					continue;
				}
				DField df = rf.field;
				if (rf.subFields != null) { // Embedded
					i = readObject(rs, i, df.getValue(obj), rf.subFields);
					continue;
				}
				FieldType type = df.getType();
				switch (type) {
				case Primitive:
					Object pri = readPrimitive(df, rs, i);
					df.setValue(obj, pri);
					i++;
					break;
				case Reference:
					DModel ref = df.getReference();
					if (ref.getIndex() == SchemaConstants.DFile) {
						DFile file = cache.getOrCreateDFile(rs.getString(i++));
						df.setValue(obj, file);
					} else {
						if (ref.isDocument() && !ref.isCreatable()) {
							DDocField dc = (DDocField) df;
							long id = rs.getLong(i++);
							int refType = rs.getInt(i++);
							String doc = rs.getString(i++);
							if (id == 0) {
								continue;
							}
							Object val = cache.getOrCreate(refType, id);
							((DatabaseObject) val)._setDoc(doc);
						} else {
							long id = rs.getLong(i++);
							int refType = rs.getInt(i++);
							if (id == 0) {
								continue;
							}
							Object val = cache.getOrCreate(refType, id);
							df.setValue(obj, val);
						}
					}
					break;
				case InverseCollection:
				case PrimitiveCollection:
				case ReferenceCollection:
					break;
				default:
					break;

				}
			}
			return i;
		}
	}

	private class DFileMapper implements RowMapper<DFile> {

		private D3EPrimaryCache cache;
		private DFile file;

		public DFileMapper(D3EPrimaryCache cache, DFile file) {
			this.cache = cache;
			this.file = file;
		}

		@Override
		public DFile mapRow(ResultSet rs, int rowNum) throws SQLException {
			int i = 1;
			rs.getString(i++); // ID
			String name = rs.getString(i++);
			long size = rs.getLong(i++);
			String mimeType = rs.getString(i++);
			file.setName(name);
			file.setSize(size);
			file.setMimeType(mimeType);
			return file;
		}

	}

	private class CollectionMapper implements RowMapper<Object> {

		private D3EPrimaryCache cache;
		private DField field;

		public CollectionMapper(D3EPrimaryCache cache, DField field) {
			this.cache = cache;
			this.field = field;
		}

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			switch (field.getType()) {
			case PrimitiveCollection:
				return readPrimitive(field, rs, 1);
			case InverseCollection:
			case ReferenceCollection:
				if (field.getReference().getIndex() == SchemaConstants.DFile) {
					return cache.getOrCreateDFile(rs.getString(1));
				}
				long id = rs.getLong(1);
				int refType = rs.getInt(2);
				if (id == 0) {
					return null;
				}
				DatabaseObject obj = cache.getOrCreate(refType, id);
				if (field.getReference().isDocument() && !field.getReference().isCreatable()) {
					// Handling only non creatable document collection here because
					// creatable document collection would just be ids like normal references
					String doc = rs.getString(3);
					obj._setDoc(doc);
				}
				return obj;
			}
			return null;
		}
	}

	public Object readPrimitive(DField df, ResultSet rs, int i) throws SQLException {
		FieldPrimitiveType pt = df.getPrimitiveType();
		switch (pt) {
		case Boolean:
			return rs.getBoolean(i);
		case Date:
			Date date = rs.getDate(i);
			return date == null ? null : date.toLocalDate();
		case DateTime:
			Timestamp timestamp = rs.getTimestamp(i);
			return timestamp == null ? null : timestamp.toLocalDateTime();
		case Double:
			return rs.getDouble(i);
		case Duration:
			long millis = rs.getLong(i);
			return Duration.ofMillis(millis);
		case Enum:
			String str = rs.getString(i);
			DModel<?> enmType = df.getReference();
			DField<?, ?> field = enmType.getField(str);
			if (field == null) {
				field = enmType.getField(0);
			}
			Object val = field.getValue(null);
			return val;
		case Integer:
			return rs.getLong(i);
		case String:
			return rs.getString(i);
		case Time:
			Time time = rs.getTime(i);
			return time == null ? null : time.toLocalTime();
		case Geolocation:
			String loc = rs.getString(i);
			if(loc == null) {
				return null;
			} 
			String[] split = loc.split(",");
			return new Geolocation(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
		default:
			throw new UnsupportedOperationException();
		}
	}

	private class EntityManagerImpl implements IEntityManager {

		private D3EPrimaryCache cache;

		public EntityManagerImpl(D3EPrimaryCache cache) {
			if (cache == null) {
				cache = new D3EPrimaryCache(repo, schema);
			}
			this.cache = cache;
		}

		@Override
		public void createId(DatabaseObject obj) {
			DModel<?> type = schema.get(obj);
			if (obj.getId() == 0l) {
				long id = jdbcTemplate.getJdbcTemplate().queryForObject("select nextval('_d3e_sequence')",
						(rs, num) -> rs.getLong(1));
				Log.debug("NextSeq: " + id);
				schema.assignObjectId(type, obj, id);
			}
			for (DField df : type.getFields()) {
				if (df.isChild()) {
					DModel ref = df.getReference();
					if (!ref.isEmbedded()) {
						if (df.getType() == FieldType.Reference) {
							DatabaseObject child = (DatabaseObject) df.getValue(obj);
							if (child != null) {
								createId(child);
							}
						} else if (df.getType() == FieldType.ReferenceCollection) {
							List list = (List) df.getValue(obj);
							for (Object o : list) {
								createId((DatabaseObject) o);
							}
						}
					}
				}
			}
		}

		@Override
		public void persistFile(DFile file) {
			try {
				D3EQuery query = queryBuilder.generateCreateDFileQuery(D3EQuery.create(), file);
				execute(query);
			} catch (RuntimeException e) {
				throw new ValidationFailedException(e);
			}
		}

		@Override
		public void persist(DatabaseObject entity) {
			try {
				DModel<?> dm = schema.get(entity);
				if (dm.isTransient() || dm.isEmbedded() || dm.isExternal()) {
					return;
				}
				Log.debug("Persist Object: " + entity.getId());
				if (entity.getSaveStatus() == DBSaveStatus.New) {
					entity.setSaveStatus(DBSaveStatus.Saved);
					D3EQuery query = queryBuilder.generateCreateQuery(D3EQuery.create(),dm, entity);
					execute(query);
				} else {
					D3EQuery query = queryBuilder.generateUpdateQuery(D3EQuery.create(), dm, entity);
					execute(query);
				}
			} catch (RuntimeException e) {
				throw new ValidationFailedException(e);
			}
		}

		@Override
		public void delete(DatabaseObject entity) {
			try {
				if (entity.saveStatus == DBSaveStatus.New || entity.saveStatus == DBSaveStatus.Deleted) {
					return;
				}
				D3EQuery query = queryBuilder.generateDeleteQuery(D3EQuery.create(), schema.get(entity), entity);
				execute(query);
				entity.setSaveStatus(DBSaveStatus.Deleted);
			} catch (RuntimeException e) {
				throw new ValidationFailedException(e);
			}
		}

		@Override
		public <T> T find(int type, long id) {
			return (T) cache.getOrCreate(type, id);
		}

		@Override
		public <T> T getById(int type, long id) {
			DModel<?> dm = schema.getType(type);
			StringBuilder sb = new StringBuilder();
			sb.append("select _id");
			boolean isCreatableDoc = dm.isDocument() && dm.isCreatable();
			String docColName = dm.getTableName() + "_doc";
			if (isCreatableDoc) {
				sb.append(", ").append(docColName);
			}
			sb.append(" from ").append(dm.getTableName()).append(" where _id = ").append(id);
			String query = sb.toString();
			Log.debug("By Id: type: " + type + ", id: " + id + " , " + query);
			List<Map<String, Object>> list = jdbcTemplate.getJdbcTemplate().queryForList(query);
			if (list.isEmpty()) {
				return null;
			}
			DatabaseObject dbObj = cache.getOrCreate(dm.getIndex(), id);
			if (isCreatableDoc) {
				Map<String, Object> map = list.get(0);
				Object doc = map.get(docColName);
				dbObj._setDoc((String) doc);
			}
			return (T) dbObj;
		}

		@Override
		public <T> List<T> loadAll(int type, long size, long page) {
			DModel<?> dm = schema.getType(type);
			if (dm.isTransient()) {
				return ListExt.List();
			}
			List<RowField> selectedFields = new ArrayList<>();
			String query = queryBuilder.generateLoadAllQuery(dm, selectedFields, size * page, size);
			List<DatabaseObject> list = jdbcTemplate.getJdbcTemplate().query(query,
					new SingleObjectMapper(cache, dm, selectedFields, true));
			return (List<T>) list;
		}

		@Override
		public <T> List<T> findAll(int type) {
			DModel<?> dm = schema.getType(type);
			StringBuilder sb = new StringBuilder();
			List<String> joins = ListExt.List();
			AliasGenerator ag = new AliasGenerator();
			sb.append("select ").append(queryBuilder.createRefColumn(dm, dm, "_id", joins, ag)).append(" from ")
					.append(dm.getTableName());
			if (ListExt.isNotEmpty(joins)) {
				for (String j : joins) {
					sb.append(" left join ").append(j);
				}
			}
			String query = sb.toString();
			Log.debug("Find All: type: " + type + " , " + query);
			List list = jdbcTemplate.getJdbcTemplate().query(query, new SimpleObjectMapper(cache));
			return list;
		}

		private void execute(D3EQuery mainQuery) {
			if(mainQuery == null) {
				return;
			}
			mainQuery.queries.forEach((query) -> {
				if (query.query != null) {
					String q = query.query;
					List<Object> args = query.args;
					Log.info("Insert/Update: " + q);
					Object[] argsArray = new Object[args.size()];
					for (int i = 0; i < args.size(); i++) {
						Object arg = args.get(i);
						if (arg instanceof DatabaseObject) {
							long id = schema.getDatabaseId((DatabaseObject) arg);
							if (id == 0l) {
								throw new RuntimeException(
										"object references an unsaved instance - save the instance before flushing");
							}
							arg = id;
						}
						if (arg instanceof DFile) {
							String id = ((DFile) arg).getId();
							if (id == null || id.isEmpty()) {
								throw new RuntimeException(
										"object references an unsaved instance - save the instance before flushing");
							}
							arg = id;
						}
						argsArray[i] = arg;
					}
					jdbcTemplate.getJdbcTemplate().update(q, argsArray);
				} else {
					Log.debug("Query not found to execute");
				}
			});
		}

		@Override
		public Query createNativeQuery(String sql) {
			return new QueryImpl(cache, jdbcTemplate, sql);
		}

		@Override
		public void unproxy(DatabaseObject obj) {
			DModel<?> type = schema.get(obj);
			boolean doc = type.isDocument();
			long id = schema.getDatabaseId(obj);
			if (doc && obj._getDoc() != null) {
				readFromObjectDoc(obj, type, id);
				return;
			}
			List<RowField> selectedFields = new ArrayList<>();
			String query = queryBuilder.generateSelectAllQuery(type, selectedFields, id);
			// Log.info("Unproxy Object: " + obj.getId() + " : " + query);
			List<DatabaseObject> list = jdbcTemplate.getJdbcTemplate().query(query,
					new SingleObjectMapper(cache, type, selectedFields, false));
			if (list.isEmpty()) {
				Log.error("Entity not found:  " + type.getTableName() + " : " + id);
				throw new EntityNotFoundException();
			}
			if (doc) {
				// Read from doc string
				readFromObjectDoc(obj, type, id);
			}
		}

		private void readFromObjectDoc(DatabaseObject obj, DModel<?> type, long id) {
			JSONInputContext.fromJsonString(obj._getDoc(), id, type.getType(), schema);
		}

		@Override
		public void unproxyCollection(D3EPersistanceList<?> list) {
			DatabaseObject master = (DatabaseObject) list.getMaster();
			DModel<?> type = schema.get(master);
			DField<?, ?> field = schema.getCollectionField(type, list);
			if (field.isTransientField()) {
				list._unproxy(ListExt.List());
				return;
			}
			if (type.isDocument()) {
				// collection inside document
				// Need to unproxy master
				master._checkProxy();
				return;
			}
			long id = schema.getDatabaseId(master);
			String query = queryBuilder.generateSelectCollectionQuery(type, field, id);
			Log.info("Unproxy Collection: " + master.getId() + ", " + field.getName() + " : " + query);
			List<Object> result = jdbcTemplate.getJdbcTemplate().query(query, new CollectionMapper(cache, field));
			list._unproxy(result);
		}

		@Override
		public void unproxyDFile(DFile file) {
			String query = queryBuilder.generateSelectDFileQuery(file);
			Log.debug("Unproxy DFile: " + file.getId() + " : " + query);
			jdbcTemplate.getJdbcTemplate().query(query, new DFileMapper(cache, file));
		}

		@Override
		public D3EPrimaryCache getCache() {
			return cache;
		}

		@Override
		public <T> List<T> getByIds(int type, List<Long> ids) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
