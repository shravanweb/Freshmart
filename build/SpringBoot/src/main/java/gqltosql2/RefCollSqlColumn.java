package gqltosql2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gqltosql.schema.IModelSchema;
import store.IEntityManager;

public class RefCollSqlColumn implements ISqlColumn {

	private SqlAstNode sub;
	private String field;

	public RefCollSqlColumn(SqlAstNode sub, String field) {
		this.sub = sub;
		this.field = field;
	}

	@Override
	public String getFieldName() {
		return field;
	}

	@Override
	public void addColumn(SqlTable table, SqlQueryContext ctx) {
	}

	@Override
	public SqlAstNode getSubQuery() {
		return sub;
	}

	@Override
	public void extractDeepFields(IEntityManager em, IModelSchema schema, int type, List<OutObject> rows)
			throws Exception {
	}

	@Override
	public void updateSubField(Map<Long, OutObject> parents, List<OutObject> all) throws Exception {
		Map<Long, OutObjectList> values = new HashMap<>();
		for (int i = 0; i < all.size(); i++) {
			OutObject obj = all.get(i);
			obj.getParents().forEach(parentId -> {
				OutObjectList val = values.get(parentId);
				if (val == null) {
					values.put(parentId, val = new OutObjectList());
				}
				val.add(obj);
			});
			obj.getParents().clear();
		}
		for (Map.Entry<Long, OutObject> e : parents.entrySet()) {
			OutObjectList val = values.get(e.getKey());
			if (val == null) {
				val = new OutObjectList();
			}
			e.getValue().addCollectionField(field, val);
		}
	}

	@Override
	public String toString() {
		return field;
	}
}
