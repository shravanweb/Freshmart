package lists;

import classes.StockValuationReport;
import classes.StockValuationReportIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.StockValuationReportRequest;
import models.WarehouseStock;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OrganizationRepository;
import repository.jpa.WarehouseRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class StockValuationReportImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private WarehouseRepository warehouseRepository;

  public StockValuationReportRequest inputToRequest(StockValuationReportIn inputs) {
    StockValuationReportRequest request = new StockValuationReportRequest();
    request.setOrganization(organizationRepository.findById(inputs.organization));
    request.setWarehouse(warehouseRepository.findById(inputs.warehouse));
    return request;
  }

  public StockValuationReport get(StockValuationReportIn inputs) {
    StockValuationReportRequest request = inputToRequest(inputs);
    return get(request);
  }

  public StockValuationReport get(StockValuationReportRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsStruct(rows, count);
  }

  public StockValuationReport getAsStruct(List<NativeObj> rows, long count) {
    List<WarehouseStock> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(3), SchemaConstants.WarehouseStock));
    }
    StockValuationReport wrap = new StockValuationReport();
    wrap.setItems(result);
    wrap.setCount(count);
    return wrap;
  }

  public JSONObject getAsJson(Field field, StockValuationReportIn inputs) throws Exception {
    StockValuationReportRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, StockValuationReportRequest request) throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsJson(field, rows, count);
  }

  public JSONObject getAsJson(Field field, List<NativeObj> rows, long count) throws Exception {
    JSONArray array = new JSONArray();
    List<SqlRow> sqlDecl0 = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      array.put(NativeSqlUtil.getJSONObject(_r1, sqlDecl0));
    }
    gqlToSql.execute("WarehouseStock", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    result.put("count", count);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, StockValuationReportRequest request)
      throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsJson(field, rows, count);
  }

  public OutObject getAsJson(gqltosql2.Field field, List<NativeObj> rows, long count)
      throws Exception {
    OutObjectList array = new OutObjectList();
    OutObjectList sqlDecl0 = new OutObjectList();
    for (NativeObj _r1 : rows) {
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.WarehouseStock, sqlDecl0));
    }
    gqlToSql2.execute("WarehouseStock", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.StockValuationReport);
    result.add("items", array);
    result.add("count", count);
    return result;
  }

  public List<NativeObj> getNativeResult(StockValuationReportRequest request) {
    String sql =
        "select a._organization_id a0, a._warehouse_id a1, a._stock_value a2, a._id a3 from _warehouse_stock a where a._organization_id = :param_0 and (:param_1 or a._warehouse_id = :param_2) order by a._stock_value desc";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    setBooleanParameter(query, "param_1", request.getWarehouse() == null);
    setDatabaseObjectParameter(query, "param_2", request.getWarehouse());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 3);
    return result;
  }

  public long getCountResult(StockValuationReportRequest request) {
    String sql =
        "select count(a._id) a0 from _warehouse_stock a where a._organization_id = :param_0 and (:param_1 or a._warehouse_id = :param_2)";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    setBooleanParameter(query, "param_1", request.getWarehouse() == null);
    setDatabaseObjectParameter(query, "param_2", request.getWarehouse());
    this.logQuery(sql, query);
    return getCountResult(query);
  }
}
