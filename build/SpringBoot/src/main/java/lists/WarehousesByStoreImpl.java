package lists;

import classes.WarehousesByStore;
import classes.WarehousesByStoreIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.Warehouse;
import models.WarehousesByStoreRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.StoreRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class WarehousesByStoreImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private StoreRepository storeRepository;

  public WarehousesByStoreRequest inputToRequest(WarehousesByStoreIn inputs) {
    WarehousesByStoreRequest request = new WarehousesByStoreRequest();
    request.setStore(storeRepository.findById(inputs.store));
    return request;
  }

  public WarehousesByStore get(WarehousesByStoreIn inputs) {
    WarehousesByStoreRequest request = inputToRequest(inputs);
    return get(request);
  }

  public WarehousesByStore get(WarehousesByStoreRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsStruct(rows, count);
  }

  public WarehousesByStore getAsStruct(List<NativeObj> rows, long count) {
    List<Warehouse> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(2), SchemaConstants.Warehouse));
    }
    WarehousesByStore wrap = new WarehousesByStore();
    wrap.setItems(result);
    wrap.setCount(count);
    return wrap;
  }

  public JSONObject getAsJson(Field field, WarehousesByStoreIn inputs) throws Exception {
    WarehousesByStoreRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, WarehousesByStoreRequest request) throws Exception {
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
    gqlToSql.execute("Warehouse", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    result.put("count", count);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, WarehousesByStoreRequest request)
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
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.Warehouse, sqlDecl0));
    }
    gqlToSql2.execute("Warehouse", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.WarehousesByStore);
    result.add("items", array);
    result.add("count", count);
    return result;
  }

  public List<NativeObj> getNativeResult(WarehousesByStoreRequest request) {
    String sql =
        "select a._store_id a0, a._name a1, a._id a2 from _warehouse a where a._store_id = :param_0 order by a._name";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getStore());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 2);
    return result;
  }

  public long getCountResult(WarehousesByStoreRequest request) {
    String sql = "select count(a._id) a0 from _warehouse a where a._store_id = :param_0";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getStore());
    this.logQuery(sql, query);
    return getCountResult(query);
  }
}
