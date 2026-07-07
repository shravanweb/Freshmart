package lists;

import classes.AllSalesOrders;
import classes.AllSalesOrdersIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.AllSalesOrdersRequest;
import models.SalesOrder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OrganizationRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class AllSalesOrdersImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private OrganizationRepository organizationRepository;

  public AllSalesOrdersRequest inputToRequest(AllSalesOrdersIn inputs) {
    AllSalesOrdersRequest request = new AllSalesOrdersRequest();
    request.setOrganization(organizationRepository.findById(inputs.organization));
    return request;
  }

  public AllSalesOrders get(AllSalesOrdersIn inputs) {
    AllSalesOrdersRequest request = inputToRequest(inputs);
    return get(request);
  }

  public AllSalesOrders get(AllSalesOrdersRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsStruct(rows, count);
  }

  public AllSalesOrders getAsStruct(List<NativeObj> rows, long count) {
    List<SalesOrder> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(2), SchemaConstants.SalesOrder));
    }
    AllSalesOrders wrap = new AllSalesOrders();
    wrap.setItems(result);
    wrap.setCount(count);
    return wrap;
  }

  public JSONObject getAsJson(Field field, AllSalesOrdersIn inputs) throws Exception {
    AllSalesOrdersRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, AllSalesOrdersRequest request) throws Exception {
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
    gqlToSql.execute("SalesOrder", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    result.put("count", count);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, AllSalesOrdersRequest request)
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
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.SalesOrder, sqlDecl0));
    }
    gqlToSql2.execute("SalesOrder", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.AllSalesOrders);
    result.add("items", array);
    result.add("count", count);
    return result;
  }

  public List<NativeObj> getNativeResult(AllSalesOrdersRequest request) {
    String sql =
        "select a._organization_id a0, a._order_date a1, a._id a2 from _sales_order a where a._organization_id = :param_0 order by a._order_date desc";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 2);
    return result;
  }

  public long getCountResult(AllSalesOrdersRequest request) {
    String sql = "select count(a._id) a0 from _sales_order a where a._organization_id = :param_0";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    this.logQuery(sql, query);
    return getCountResult(query);
  }
}
