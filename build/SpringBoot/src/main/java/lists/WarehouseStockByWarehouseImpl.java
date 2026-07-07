package lists;

import classes.WarehouseStockByWarehouse;
import classes.WarehouseStockByWarehouseIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.WarehouseStock;
import models.WarehouseStockByWarehouseRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.WarehouseRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class WarehouseStockByWarehouseImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private WarehouseRepository warehouseRepository;

  public WarehouseStockByWarehouseRequest inputToRequest(WarehouseStockByWarehouseIn inputs) {
    WarehouseStockByWarehouseRequest request = new WarehouseStockByWarehouseRequest();
    request.setWarehouse(warehouseRepository.findById(inputs.warehouse));
    return request;
  }

  public WarehouseStockByWarehouse get(WarehouseStockByWarehouseIn inputs) {
    WarehouseStockByWarehouseRequest request = inputToRequest(inputs);
    return get(request);
  }

  public WarehouseStockByWarehouse get(WarehouseStockByWarehouseRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsStruct(rows, count);
  }

  public WarehouseStockByWarehouse getAsStruct(List<NativeObj> rows, long count) {
    List<WarehouseStock> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(2), SchemaConstants.WarehouseStock));
    }
    WarehouseStockByWarehouse wrap = new WarehouseStockByWarehouse();
    wrap.setItems(result);
    wrap.setCount(count);
    return wrap;
  }

  public JSONObject getAsJson(Field field, WarehouseStockByWarehouseIn inputs) throws Exception {
    WarehouseStockByWarehouseRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, WarehouseStockByWarehouseRequest request)
      throws Exception {
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

  public OutObject getAsJson(gqltosql2.Field field, WarehouseStockByWarehouseRequest request)
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
    result.addType(SchemaConstants.WarehouseStockByWarehouse);
    result.add("items", array);
    result.add("count", count);
    return result;
  }

  public List<NativeObj> getNativeResult(WarehouseStockByWarehouseRequest request) {
    String sql =
        "select a._warehouse_id a0, a._quantity_on_hand a1, a._id a2 from _warehouse_stock a where a._warehouse_id = :param_0 order by a._quantity_on_hand desc";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getWarehouse());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 2);
    return result;
  }

  public long getCountResult(WarehouseStockByWarehouseRequest request) {
    String sql = "select count(a._id) a0 from _warehouse_stock a where a._warehouse_id = :param_0";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getWarehouse());
    this.logQuery(sql, query);
    return getCountResult(query);
  }
}
