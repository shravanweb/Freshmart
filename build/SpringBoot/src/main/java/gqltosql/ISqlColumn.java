package gqltosql;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import gqltosql.schema.IModelSchema;
import store.IEntityManager;

public interface ISqlColumn {

	String getFieldName();

	void addColumn(SqlTable table, SqlQueryContext ctx);

	SqlAstNode getSubQuery();

	void updateSubField(Map<Long, SqlRow> parents, JSONArray all) throws Exception;

	void extractDeepFields(IEntityManager em, IModelSchema schema, String type, List<SqlRow> rows) throws Exception;
}
