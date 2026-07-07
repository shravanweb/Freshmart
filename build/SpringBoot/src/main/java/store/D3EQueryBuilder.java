package store;

import java.time.Duration;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import d3e.core.D3EResourceHandler;
import d3e.core.DFile;
import d3e.core.Geolocation;
import d3e.core.ListExt;
import d3e.core.Log;
import d3e.core.SchemaConstants;
import gqltosql.schema.DDocCollField;
import gqltosql.schema.DDocField;
import gqltosql.schema.DEmbField;
import gqltosql.schema.DField;
import gqltosql.schema.DModel;
import gqltosql.schema.FieldPrimitiveType;
import gqltosql.schema.FieldType;
import gqltosql.schema.IModelSchema;
import gqltosql2.AliasGenerator;
import rest.JSONInputContext;
import store.D3EEntityManagerProvider.RowDocField;
import store.D3EEntityManagerProvider.RowField;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class D3EQueryBuilder {

	private IModelSchema schema;

	private D3EResourceHandler resourceHandler;

	@Autowired
	public D3EQueryBuilder(IModelSchema schema, D3EResourceHandler resourceHandler) {
		this.schema = schema;
		this.resourceHandler = resourceHandler;
	}
	
	public D3EQuery generateDFileCountUpdate(D3EQuery query, DFile file, int count) {
		query.query = "update _dfile set _count = _count + ? where _id = ?";
		query.args.add(count);
		query.args.add(file.getId());
		return query;
	}

	public D3EQuery generateCreateDFileQuery(D3EQuery query, DFile file) {
		query.query = "insert into _dfile(_id, _name, _size, _mime_type, _count) values (?, ?, ?, ?, 1)";
		query.args.add(file.getId());
		query.args.add(file.getName());
		query.args.add(file.getSize());
		query.args.add(file.getMimeType());
		return query;
	}

	public D3EQuery generateCreateQuery(D3EQuery query, DModel type, DatabaseObject _this) {
		List<String> cols = ListExt.List();
		List<String> params = ListExt.List();
		List<Object> args = ListExt.List();
		query.setObj(_this);

		cols.add("_id");
		params.add("?");
		args.add(_this);

		if (type.getParent() == null) {
			// SaveStatus will not be in inherited entities
			cols.add("_save_status");
			params.add("?");
			args.add(1);
		}

		addInsertColumns(query, type, _this, cols, params, args, "");
		ICustomFieldProcessor processor = CustomFieldService.get().getProcessor(type.getType());
		if (processor != null) {
			processor.insert(query, type, _this, cols, params, args);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("insert into ").append(type.getTableName()).append(" (").append(ListExt.join(cols, ", "))
				.append(") values (").append(ListExt.join(params, ", ")).append(")");
		query.setQuery(sb.toString());
		query.setArgs(args);

		if (type.getParent() != null) {
			generateCreateQuery(query.prev(), type.getParent(), _this);
		}
		return query;
	}

	private void addInsertColumns(D3EQuery query, DModel type, Object _this, List<String> cols, List<String> params,
			List<Object> args, String embeddedPrefix) {
		if (isCreatableDocument(type)) {
			createCreatableDoc(type, _this, cols, params, args);
			return;
		}
		for (DField field : type.getFields()) {
			if (field.isTransientField() || field.isDocField() || field.getType() == FieldType.InverseCollection) {
				continue;
			}
			DModel ref = field.getReference();
			if (ref != null && ref.isExternal()) {
				continue;
			}
			switch (field.getType()) {
			case Primitive:
				cols.add(embeddedPrefix + field.getColumnName());
				Object fieldValue = field.getValue(_this);
				FieldPrimitiveType pt = field.getPrimitiveType();
				if (pt == FieldPrimitiveType.Geolocation && fieldValue != null) {
					Geolocation loc = (Geolocation) fieldValue;
					params.add("ST_MakePoint(?, ?)::geography");
					args.add(loc.getLongitude());
					args.add(loc.getLatitude());
				} else {
					params.add("?");
					addPrimitiveArg(args, field, fieldValue);
				}
				break;
			case Reference:
				if (ref.getType().equals("DFile")) {
					Object value = field.getValue(_this);
					if (value != null) {
						cols.add(embeddedPrefix + field.getColumnName());
						params.add("?");
						DFile df = (DFile) value;
						checkAndSaveDFile(query, df);
						args.add(df);
					}
				} else if (ref.isDocument() && !ref.isCreatable()) {
					DDocField dc = (DDocField) field;
					DatabaseObject childDoc = (DatabaseObject) field.getValue(_this);
					if (childDoc == null) {
						continue;
					}

					// id
					cols.add(embeddedPrefix + dc.getIdColumn());
					params.add("?");
					args.add(schema.getDatabaseId(childDoc));

					// doc
					cols.add(embeddedPrefix + field.getColumnName());
					params.add("?");
					String json = JSONInputContext.toJsonString(childDoc, ref.getType(), schema);
					args.add(json);
				} else if (ref.isEmbedded()) {
					DEmbField em = (DEmbField) field;
					addInsertColumns(query, ref, field.getValue(_this), cols, params, args, em.getPrefix());
				} else {
					if (field.isChild()) {
						Object value = field.getValue(_this);
						if (value != null) {
							generateCreateQuery(query.prev(), ref, (DatabaseObject) value);
						}
					}
					cols.add(embeddedPrefix + field.getColumnName());
					params.add("?");
					args.add(field.getValue(_this));
				}
				break;
			case PrimitiveCollection:
				generatePrimitiveCollectionCreateQuery(query.next(), field, (List) field.getValue(_this), _this);
				break;
			case ReferenceCollection:
				if (ref.isDocument() && !ref.isCreatable()) {
					continue;
				}
				List values = (List) field.getValue(_this);
				if (field.isChild()) {
					for (Object v : values) {
						generateCreateQuery(query.prev(), field.getReference(), (DatabaseObject) v);
					}
				}
				generateReferenceCollectionCreateQuery(query.next(), field, values, _this);
				break;
			default:
				break;
			}
		}
	}

	private void createCreatableDoc(DModel type, Object _this, List<String> cols, List<String> params,
			List<Object> args) {
		String doc = creatableDocToJson(type, _this);
		String docColumn = getCreatableDocColumnName(type);
		cols.add(docColumn);
		params.add("?");
		args.add(doc);
	}

	private void updateCreatableDoc(DModel type, Object _this, List<String> updates, List<Object> args) {
		String doc = creatableDocToJson(type, _this);
		String update = getCreatableDocColumnName(type) + " = ?";
		updates.add(update);
		args.add(doc);
	}

	private String getCreatableDocColumnName(DModel type) {
		String docColumn = type.getTableName() + "_doc";
		return docColumn;
	}

	private String creatableDocToJson(DModel type, Object _this) {
		String doc = JSONInputContext.toJsonString((DatabaseObject) _this, type.getType(), schema);
		return doc;
	}

	private boolean isCreatableDocument(DModel type) {
		return type.isCreatable() && type.isDocument();
	}

	private DFile checkAndSaveDFile(D3EQuery query, DFile df) {
		if (!resourceHandler.isSaved(df)) {
			df = resourceHandler.save(df);
			generateCreateDFileQuery(query.prev(), df);
		} else {
			generateDFileCountUpdate(query.next(), df, 1);
		}
		return df;
	}

	public void addPrimitiveArg(List<Object> args, DField<?, ?> field, Object value) {
		if (value instanceof Enum<?>) {
			args.add(((Enum<?>) value).name());
		} else if (value instanceof Duration) {
			long millis;
			try {
				millis = ((Duration) value).toMillis();
			} catch (ArithmeticException e) {
				Log.printStackTrace(e);
				millis = 0;
			}
			args.add(millis);
		} else {
			args.add(value);
		}
	}

	private D3EQuery generateReferenceCollectionCreateQuery(D3EQuery query, DField<?, ?> field, List value, Object master) {
		if (value.isEmpty()) {
			return null;
		}
		// ref has to exist since this is a reference collection
		DModel<?> ref = field.getReference();
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ").append(field.getCollTableName(null));
		sb.append(" (").append(field.declType().toColumnName()).append(", ").append(field.getColumnName()).append(", ")
				.append(field.getColumnName().replaceAll("_?_id$", "_order")).append(") values ");

		List<Object> args = ListExt.List();
		int sz = value.size();
		for (int i = 0; i < sz; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			args.add(master);
			Object obj = value.get(i);
			if (ref.getIndex() == SchemaConstants.DFile) {
				DFile df = (DFile) obj;
				checkAndSaveDFile(query, df);
				obj = df;
			}
			args.add(obj);
			sb.append("( ?, ?, ").append(i).append(")");
		}
		query.setArgs(args);
		query.setQuery(sb.toString());
		return query;
	}

	private D3EQuery generatePrimitiveCollectionCreateQuery(D3EQuery query, DField<?, ?> field, List<?> value, Object master) {
		if (value.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ").append(field.getCollTableName(null));
		sb.append(" (").append(field.declType().toColumnName()).append(", ").append(field.getColumnName())
				.append(") values ");

		List<Object> args = ListExt.List();
		for (int i = 0; i < value.size(); i++) {
			if (i != 0) {
				sb.append(", ");
			}
			args.add(master);
			addPrimitiveArg(args, field, value.get(i));
			sb.append("(?, ?)");
		}
		query.setArgs(args);
		query.setQuery(sb.toString());
		return query;
	}

	public D3EQuery generateUpdateQuery(D3EQuery query, DModel type, DatabaseObject _this) {
		BitSet _changes = _this._changes().changes;
		if (_changes.isEmpty()) {
			Log.debug("No Changes found: " + _this.getId());
			return null;
		}
		List<String> updates = ListExt.List();
		List<Object> args = ListExt.List();

		addUpdateColumns(query, type, _this, updates, args, "");
		ICustomFieldProcessor processor = CustomFieldService.get().getProcessor(type.getType());
		if (processor != null) {
			processor.update(query, type, _this, updates, args);
		}

		if (!updates.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append("update ").append(type.getTableName()).append(" set ").append(ListExt.join(updates, ", "))
					.append(" where _id = ").append(schema.getDatabaseId(_this));
			query.setQuery(sb.toString());
			query.setArgs(args);
		}

		if (type.getParent() != null) {
			generateUpdateQuery(query.prev(), type.getParent(), _this);
		}
		return query;
	}

	private void addUpdateColumns(D3EQuery query, DModel type, DBObject _this, List<String> updates, List<Object> args,
			String embeddedPrefix) {
		DBChange ch = schema.findChanges(_this);
		BitSet changes = ch.changes;
		if (isCreatableDocument(type) && !changes.isEmpty()) {
			updateCreatableDoc(type, _this, updates, args);
			return;
		}
		for (DField field : type.getFields()) {
			if (field.isTransientField() || field.getType() == FieldType.InverseCollection
					|| !changes.get(field.getIndex())) {
				continue;
			}
			DModel ref = field.getReference();
			if (ref != null && ref.isExternal()) {
				continue;
			}
			switch (field.getType()) {
			case Primitive:
				Object fieldValue = field.getValue(_this);
				FieldPrimitiveType pt = field.getPrimitiveType();
				if (pt == FieldPrimitiveType.Geolocation && fieldValue != null) {
					Geolocation loc = (Geolocation) fieldValue;
					updates.add(embeddedPrefix + field.getColumnName() + " = ST_MakePoint(?, ?)::geography");
					args.add(loc.getLongitude());
					args.add(loc.getLatitude());
				} else {
					updates.add(embeddedPrefix + field.getColumnName() + " = ?");
					addPrimitiveArg(args, field, fieldValue);
				}
				break;
			case Reference:
				if (ref.getIndex() == SchemaConstants.DFile) {
					DFile oldChild = (DFile) ch.oldValues.get(field.getIndex());
					DFile newValue = (DFile) field.getValue(_this);
					updates.add(embeddedPrefix + field.getColumnName() + " = ?");
					if (newValue != null) {
						DFile df = (DFile) newValue;
						checkAndSaveDFile(query, df);
					}
					args.add(newValue);
					if(oldChild != null) {
						generateDFileCountUpdate(query.next(), oldChild, -1);
					}
				} else if (ref.isDocument()) {
					// Must be non-creatable, since creatable doc is covered above
					DatabaseObject childDoc = (DatabaseObject) field.getValue(_this);
					if (childDoc == null) {
						continue;
					}

					DDocField dc = (DDocField) field;
					updates.add(dc.getIdColumn() + " = ?");
					args.add(schema.getDatabaseId(childDoc));

					updates.add(field.getColumnName() + " = ?");
					String json = JSONInputContext.toJsonString(childDoc, ref.getType(), schema);
					args.add(json);
				} else if (ref.isEmbedded()) {
					DEmbField em = (DEmbField) field;
					addUpdateColumns(query, ref, (DBObject) field.getValue(_this), updates, args, em.getPrefix());
				} else {
					if (field.isChild()) {
						DatabaseObject oldChild = (DatabaseObject) ch.oldValues.get(field.getIndex());
						DatabaseObject newChild = (DatabaseObject) field.getValue(_this);
						if (!ch.oldValues.containsKey(field.getIndex()) || oldChild == newChild) {
							generateUpdateQuery(query.next(), field.getReference(), newChild);
							continue;
						}
						if (newChild != null) {
							generateCreateQuery(query.prev(), ref, (DatabaseObject) newChild);
						}
						if (oldChild != null) {
							generateDeleteQuery(query.prev(), ref, (DatabaseObject) oldChild);
						}
					}
					updates.add(embeddedPrefix + field.getColumnName() + " = ?");
					args.add(field.getValue(_this));
				}
				break;
			case PrimitiveCollection:
				if (type.isDocument()) {
					continue;
				}
				generatePrimitiveCollectionDeleteQuery(query.prev(), field, ListExt.asList(_this));
				generatePrimitiveCollectionCreateQuery(query.next(), field, (List) field.getValue(_this), _this);
				break;
			case ReferenceCollection:
				if (type.isDocument()) {
					continue;
				}
				if (ref.isDocument()) {
					continue;
				}
				generateReferenceCollectionDeleteQuery(query.prev(), field, ListExt.asList(_this));
				List values = (List) field.getValue(_this);
				List old = (List) ch.oldValues.get(field.getIndex());
				if (field.isChild()) {
					if (old != null) {
						List oldCopy = new ArrayList<>(old);
						for (Object v : values) {
							if (oldCopy.contains(v)) {
								generateUpdateQuery(query.next(), field.getReference(), (DatabaseObject) v);
								oldCopy.remove(v);
							} else {
								generateCreateQuery(query.prev(), field.getReference(), (DatabaseObject) v);
							}
						}
						if (!oldCopy.isEmpty()) {
							generateMultiDeleteQuery(query.prev(), oldCopy);
						}
					} else {
						for (Object v : values) {
							generateUpdateQuery(query.next(), field.getReference(), (DatabaseObject) v);
						}
					}
				}
				if (ref.getIndex() == SchemaConstants.DFile) {
					for (Object v : old) {
						generateDFileCountUpdate(query.next(), (DFile) v, -1);
					}
				}
				generateReferenceCollectionCreateQuery(query.next(), field, values, _this);
				break;
			default:
				break;
			}
		}
	}

	private D3EQuery generateReferenceCollectionDeleteQuery(D3EQuery query, DField<?, ?> field, List masterList) {
		return generatePrimitiveCollectionDeleteQuery(query, field, masterList);
	}

	private D3EQuery generatePrimitiveCollectionDeleteQuery(D3EQuery query, DField<?, ?> field, List masterList) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ").append(field.getCollTableName(null));
		sb.append(" where ");
		sb.append(field.declType().toColumnName()).append(" in (");
		sb.append(ListExt.join(ListExt.map(masterList, v -> schema.getDatabaseId((DatabaseObject) v)), ", "));
		sb.append(")");
		query.setArgs(new ArrayList<>());
		query.setQuery(sb.toString());
		return query;
	}

	public D3EQuery generateDeleteQuery(D3EQuery query, DModel<?> type, DatabaseObject _this) {
		addDeleteColumns(query, type, ListExt.asList(_this));
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ").append(type.getTableName()).append(" where _id = ")
				.append(schema.getDatabaseId(_this));
		query.setQuery(sb.toString());
		if (type.getParent() != null) {
			generateDeleteQuery(query.next(), type.getParent(), _this);
		}
		return query;
	}

	public D3EQuery generateMultiDeleteQuery(D3EQuery query, List<DatabaseObject> values) {
		if (values.isEmpty()) {
			return null;
		}
		// Group by types and it's parents;
		Map<Integer, List<DatabaseObject>> groupByTypes = new HashMap<>();
		for (DatabaseObject v : values) {
			DModel<?> type = schema.get(v);
			while (type != null) {
				List<DatabaseObject> list = groupByTypes.get(type.getIndex());
				if (list == null) {
					list = new ArrayList<>();
					groupByTypes.put(type.getIndex(), list);
				}
				list.add(v);
				type = type.getParent();
			}
		}
		groupByTypes.forEach((t, list) -> {
			DModel<?> type = schema.getType(t);
			addDeleteColumns(query, type, list);
			StringBuilder sb = new StringBuilder();
			sb.append("delete from ").append(type.getTableName()).append(" where _id in (");
			sb.append(ListExt.join(ListExt.map(list, v -> schema.getDatabaseId(v)), ", "));
			sb.append(")");
			D3EQuery q = query.prev();
			q.setQuery(sb.toString());
		});
		return query.pre;
	}

	private void addDeleteColumns(D3EQuery query, DModel<?> type, List multiValues) {
		for (DField field : type.getFields()) {
			if (field.isTransientField() || field.getType() == FieldType.InverseCollection) {
				continue;
			}
			switch (field.getType()) {
			case Reference:
				if (!field.getReference().isEmbedded() && field.isChild() && !field.getReference().isDocument()) {
					List childs = new ArrayList<>();
					for (Object o : multiValues) {
						Object value = field.getValue(o);
						if (value != null) {
							childs.add(value);
						}
					}
					generateMultiDeleteQuery(query.prev(), childs);
				}
				break;
			case PrimitiveCollection:
				if (type.isDocument()) {
					continue;
				}
				generatePrimitiveCollectionDeleteQuery(query.prev(), field, multiValues);
				break;
			case ReferenceCollection:
				if (type.isDocument()) {
					continue;
				}
				if (field.getReference().isDocument()) {
					continue;
				}
				generateReferenceCollectionDeleteQuery(query.prev(), field, multiValues);
				if (field.isChild()) {
					List childs = new ArrayList<>();
					for (Object o : multiValues) {
						Object value = field.getValue(o);
						if (value != null) {
							childs.addAll((List<DatabaseObject>) value);
						}
					}
					generateMultiDeleteQuery(query.prev(), childs);
				}
				break;
			default:
				break;
			}
		}

	}

	public String generateSelectAllQuery(DModel type, List<RowField> selectedFields, long id) {
		AliasGenerator ag = new AliasGenerator();
		String alias = ag.next();
		StringBuilder sb = generateSelectAllQueryInternal(ag, alias, type, selectedFields);
		sb.append(" where ").append(alias).append("._id = ").append(id);
		return sb.toString();
	}

	public String generateLoadAllQuery(DModel type, List<RowField> selectedFields, long offset, long limit) {
		AliasGenerator ag = new AliasGenerator();
		String alias = ag.next();
		StringBuilder sb = generateSelectAllQueryInternal(ag, alias, type, selectedFields);
		sb.append(" order by ").append(alias).append("._id desc ").append(" offset ").append(offset).append(" limit ")
				.append(limit);
		return sb.toString();
	}

	private StringBuilder generateSelectAllQueryInternal(AliasGenerator ag, String alias, DModel type,
			List<RowField> selectedFields) {
		StringBuilder sb = new StringBuilder();
		List<String> joins = new ArrayList<>();
		String idColumn = getTypeAndColumnSql(type, type, alias + "._id", joins, ag);
		sb.append("select ").append(idColumn);
		appendAllColumns(sb, type, selectedFields, joins, ag, alias, "");
		ICustomFieldProcessor processor = CustomFieldService.get().getProcessor(type.getType());
		if (processor != null) {
			// TODO processor.selectAll(sb, type, id, selectedFields, joins, ag, alias);
		}
		sb.append(" from ").append(type.getTableName()).append(" ").append(alias);
		for (String j : joins) {
			sb.append(" left join ").append(j);
		}
		return sb;
	}

	private void appendAllColumns(StringBuilder sb, DModel type, List<RowField> selectedFields, List<String> joins,
			AliasGenerator ag, String alias, String embeddedPrefix) {
		if (isCreatableDocument(type)) {
			sb.append(", ").append(alias).append(".").append(embeddedPrefix).append(getCreatableDocColumnName(type));
			selectedFields.add(new RowDocField(type));
			return;
		}
		DField[] fields = type.getFields();
		for (DField df : fields) {
			if (df.isTransientField() || df.isDocField()) {
				continue;
			}
			FieldType ft = df.getType();
			switch (ft) {
			case Primitive:
				String column = alias + "." + embeddedPrefix + df.getColumnName();
				if (df.getPrimitiveType() == FieldPrimitiveType.Geolocation) {
					sb.append(", ").append("(st_x(" + column + "::geometry) || ',' || st_y(" + column + "::geometry))");
				} else {
					sb.append(", ").append(column);
				}
				selectedFields.add(new RowField(df));
				break;
			case Reference:
				DModel ref = df.getReference();
				if (ref != null && ref.isExternal()) {
					continue;
				}
				if (ref.isDocument() && !ref.isCreatable()) {
					DDocField dc = (DDocField) df;
					String idCol = alias + '.' + embeddedPrefix + dc.getIdColumn();
					String typeAndIdSql = getTypeAndColumnSql(type, ref, idCol, joins, ag);
					sb.append(", ").append(typeAndIdSql);
					sb.append(", ").append(alias).append(".").append(embeddedPrefix).append(df.getColumnName());
					selectedFields.add(new RowField(df));
				} else if (ref.isEmbedded()) {
					List<RowField> subFields = new ArrayList<>();
					DEmbField em = (DEmbField) df;
					appendAllColumns(sb, ref, subFields, joins, ag, alias, em.getPrefix());
					selectedFields.add(new RowField(df, subFields));
				} else {
					sb.append(", ");
					sb.append(createRefColumn(type, df.getReference(),
							alias + "." + embeddedPrefix + df.getColumnName(), joins, ag));
					selectedFields.add(new RowField(df));
				}
				break;
			case PrimitiveCollection:
			case InverseCollection:
			case ReferenceCollection:
				break;
			default:
				break;
			}
		}
		DModel parent = type.getParent();
		if (parent != null) {
			String ja = ag.next();
			joins.add(parent.getTableName() + " " + ja + " on " + ja + "._id = " + alias + "._id");
			appendAllColumns(sb, parent, selectedFields, joins, ag, ja, embeddedPrefix);
		}
	}

	public String generateSelectCollectionQuery(DModel<?> type, DField<?, ?> field, long id) {
		StringBuilder b = new StringBuilder();
		List<String> joins = ListExt.List();
		AliasGenerator ag = new AliasGenerator();
		b.append("select ");
		switch (field.getType()) {
		case InverseCollection:
			String tableAlias = ag.next();
			ag.store(field.getReference().getTableName(), "", tableAlias);
			b.append(createRefColumn(type, field.getReference(), tableAlias + "._id", joins, ag));
			b.append(" from ");
			b.append(field.getReference().getTableName()).append(" ").append(tableAlias);
			break;
		case PrimitiveCollection:
			b.append(field.getColumnName());
			b.append(" from ");
			b.append(field.getCollTableName(null));
			break;
		case ReferenceCollection:
			String sql;
			if (field.getReference().isDocument() && !field.getReference().isCreatable()) {
				String docColumn = ((DDocCollField<?, ?>) field).getDocColumn();
				sql = createDocRefColumn(type, field.getReference(), field.getColumnName(), docColumn, joins, ag);
			} else {
				sql = createRefColumn(type, field.getReference(), field.getColumnName(), joins, ag);
			}
			b.append(sql);
			b.append(" from ");
			b.append(field.getCollTableName(null));
			break;
		default:
			break;
		}

		if (ListExt.isNotEmpty(joins)) {
			// Should never happen
			for (String j : joins) {
				b.append(" left join ").append(j);
			}
		}

		b.append(" where ");

		if(field.getReference() != null) {
			String al = ag.getAlias(field.getReference().getTableName(), "");
			if(al != null) {
				b.append(al).append(".");
			}
		}
		switch (field.getType()) {
		case InverseCollection:
			b.append(field.getColumnName());
			break;
		case PrimitiveCollection:
		case ReferenceCollection:
			b.append(type.toColumnName());
			break;
		default:
			break;
		}

		b.append(" = ").append(id);
		if (field.getType() == FieldType.ReferenceCollection) {
			b.append(" order by ");
			b.append(field.getColumnName().replaceAll("_?_id$", "_order"));
		}
		return b.toString();
	}

	private String createDocRefColumn(DModel<?> in, DModel<?> ref, String idColumn, String docColumn,
			List<String> joins, AliasGenerator ag) {
		String sql = getTypeAndColumnSql(in, ref, idColumn, joins, ag);
		return sql + ", " + docColumn;
	}

	public String createRefColumn(DModel<?> in, DModel<?> ref, String clm, List<String> joins, AliasGenerator ag) {
		if (ref.getIndex() == SchemaConstants.DFile) {
			return clm;
		} else {
			return getTypeAndColumnSql(in, ref, clm, joins, ag);
		}
	}

	private String getTypeAndColumnSql(DModel<?> refIn, DModel<?> ref, String clm, List<String> joins,
			AliasGenerator ag) {
		int[] types = ref.getAllTypes();
		if (types.length > 1) {
			DModel<?> mp = ref.getMostParent();
			String mpTableName = mp.getTableName();
			boolean mpAliasCreated = false;
			List<String[]> toJoin = ListExt.List();
			StringBuilder b = new StringBuilder();
			b.append("(case");
			for (int x = types.length - 1; x >= 0; x--) {
				int t = types[x];
				DModel<?> type = schema.getType(t);
				String name = type.getTableName();
				String tableAlias = ag.getAlias(name, clm);
				if (tableAlias == null) {
					// Never seen this table before. So make a join.
					tableAlias = ag.next();
					ag.store(name, clm, tableAlias);
					toJoin.add(new String[] {name, clm});
					mpAliasCreated = type == mp;
				}
				b.append(" when ").append(tableAlias).append("._id is not null then ").append(t);
			}
			b.append(" else -1 end)");

			String mpTableAlias = ag.getAlias(mpTableName, clm);
			if (mpAliasCreated) {
				String refAlias = ag.getAlias(refIn.getTableName(), "");
				if (refAlias == null) {
					// Unlikely to be executed
					refAlias = ag.next();
					ag.store(refIn.getTableName(), "", refAlias);
				}
				joins.add(mpTableName + " " + mpTableAlias + " on " + mpTableAlias + "._id = " + clm);
			}
			for (String[] name : toJoin) {
				if (name[0].equals(mp.getTableName())) {
					continue;
				}
				String tableAlias = ag.getAlias(name[0], name[1]);
				joins.add(name[0] + " " + tableAlias + " on " + tableAlias + "._id = " + clm);
			}
			return clm + ", " + b.toString();
		} else {
			return clm + ", " + ref.getIndex();
		}
	}

	public String generateSelectDFileQuery(DFile file) {
		return "select _id, _name, _size, _mime_type from _dfile where _id = '" + file.getId() + "'";
	}

	public String generateSelectWhere(DModel<?> dm, String field, Object val) {
		AliasGenerator ag = new AliasGenerator();
		String alias = ag.next();
		List<String> joins = ListExt.List();
		String clm = alias + "._id";
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(getTypeAndColumnSql(dm, dm, clm, joins, ag)).append(" from ")
				.append(dm.getTableName()).append(" ").append(alias);
		if (ListExt.isNotEmpty(joins)) {
			for (String j : joins) {
				sb.append(" left join ").append(j);
			}
		}
		String column;
		if (field.equals("id")) {
			column = "_id";
		} else {
			DField df = dm.getField(field);
			if (df == null) {
				throw new RuntimeException("Field " + field + " not found in " + dm.getType());
			}
			column = df.getColumnName();
		}
		sb.append(" where ").append(alias).append(".").append(column).append(" = :").append(field);
		return sb.toString();
	}
}
