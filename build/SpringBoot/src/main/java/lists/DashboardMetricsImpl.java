package lists;

import classes.DashboardMetrics;
import classes.DashboardMetricsIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.DashboardMetricsRequest;
import models.WarehouseStock;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OrganizationRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class DashboardMetricsImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private OrganizationRepository organizationRepository;

  public DashboardMetricsRequest inputToRequest(DashboardMetricsIn inputs) {
    DashboardMetricsRequest request = new DashboardMetricsRequest();
    request.setOrganization(organizationRepository.findById(inputs.organization));
    return request;
  }

  public DashboardMetrics get(DashboardMetricsIn inputs) {
    DashboardMetricsRequest request = inputToRequest(inputs);
    return get(request);
  }

  public DashboardMetrics get(DashboardMetricsRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    return getAsStruct(rows);
  }

  public DashboardMetrics getAsStruct(List<NativeObj> rows) {
    List<WarehouseStock> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(2), SchemaConstants.WarehouseStock));
    }
    DashboardMetrics wrap = new DashboardMetrics();
    wrap.setItems(result);
    return wrap;
  }

  public JSONObject getAsJson(Field field, DashboardMetricsIn inputs) throws Exception {
    DashboardMetricsRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, DashboardMetricsRequest request) throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public JSONObject getAsJson(Field field, List<NativeObj> rows) throws Exception {
    JSONArray array = new JSONArray();
    List<SqlRow> sqlDecl0 = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      array.put(NativeSqlUtil.getJSONObject(_r1, sqlDecl0));
    }
    gqlToSql.execute("WarehouseStock", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, DashboardMetricsRequest request)
      throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public OutObject getAsJson(gqltosql2.Field field, List<NativeObj> rows) throws Exception {
    OutObjectList array = new OutObjectList();
    OutObjectList sqlDecl0 = new OutObjectList();
    for (NativeObj _r1 : rows) {
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.WarehouseStock, sqlDecl0));
    }
    gqlToSql2.execute("WarehouseStock", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.DashboardMetrics);
    result.add("items", array);
    return result;
  }

  public List<NativeObj> getNativeResult(DashboardMetricsRequest request) {
    String sql =
        "select a._organization_id a0, a._stock_value a1, a._id a2 from _warehouse_stock a where a._organization_id = :param_0 order by a._stock_value desc";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 2);
    return result;
  }
}
