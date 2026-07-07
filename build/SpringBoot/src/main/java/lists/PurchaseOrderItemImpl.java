package lists;

import classes.PurchaseOrderItem;
import classes.PurchaseOrderItemIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.PurchaseOrder;
import models.PurchaseOrderItemRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.PurchaseOrderRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class PurchaseOrderItemImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;

  public PurchaseOrderItemRequest inputToRequest(PurchaseOrderItemIn inputs) {
    PurchaseOrderItemRequest request = new PurchaseOrderItemRequest();
    request.setPurchaseOrder(purchaseOrderRepository.findById(inputs.purchaseOrder));
    return request;
  }

  public PurchaseOrderItem get(PurchaseOrderItemIn inputs) {
    PurchaseOrderItemRequest request = inputToRequest(inputs);
    return get(request);
  }

  public PurchaseOrderItem get(PurchaseOrderItemRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    return getAsStruct(rows);
  }

  public PurchaseOrderItem getAsStruct(List<NativeObj> rows) {
    List<PurchaseOrder> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(0), SchemaConstants.PurchaseOrder));
    }
    PurchaseOrderItem wrap = new PurchaseOrderItem();
    wrap.setItems(result);
    return wrap;
  }

  public JSONObject getAsJson(Field field, PurchaseOrderItemIn inputs) throws Exception {
    PurchaseOrderItemRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, PurchaseOrderItemRequest request) throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public JSONObject getAsJson(Field field, List<NativeObj> rows) throws Exception {
    JSONArray array = new JSONArray();
    List<SqlRow> sqlDecl0 = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      array.put(NativeSqlUtil.getJSONObject(_r1, sqlDecl0));
    }
    gqlToSql.execute("PurchaseOrder", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, PurchaseOrderItemRequest request)
      throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public OutObject getAsJson(gqltosql2.Field field, List<NativeObj> rows) throws Exception {
    OutObjectList array = new OutObjectList();
    OutObjectList sqlDecl0 = new OutObjectList();
    for (NativeObj _r1 : rows) {
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.PurchaseOrder, sqlDecl0));
    }
    gqlToSql2.execute("PurchaseOrder", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.PurchaseOrderItem);
    result.add("items", array);
    return result;
  }

  public List<NativeObj> getNativeResult(PurchaseOrderItemRequest request) {
    String sql = "select a._id a0 from _purchase_order a where a._id = :param_0";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getPurchaseOrder());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 0);
    return result;
  }
}
