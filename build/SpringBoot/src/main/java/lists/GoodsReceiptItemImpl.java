package lists;

import classes.GoodsReceiptItem;
import classes.GoodsReceiptItemIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.GoodsReceipt;
import models.GoodsReceiptItemRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.GoodsReceiptRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class GoodsReceiptItemImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;

  public GoodsReceiptItemRequest inputToRequest(GoodsReceiptItemIn inputs) {
    GoodsReceiptItemRequest request = new GoodsReceiptItemRequest();
    request.setGoodsReceipt(goodsReceiptRepository.findById(inputs.goodsReceipt));
    return request;
  }

  public GoodsReceiptItem get(GoodsReceiptItemIn inputs) {
    GoodsReceiptItemRequest request = inputToRequest(inputs);
    return get(request);
  }

  public GoodsReceiptItem get(GoodsReceiptItemRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    return getAsStruct(rows);
  }

  public GoodsReceiptItem getAsStruct(List<NativeObj> rows) {
    List<GoodsReceipt> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(0), SchemaConstants.GoodsReceipt));
    }
    GoodsReceiptItem wrap = new GoodsReceiptItem();
    wrap.setItems(result);
    return wrap;
  }

  public JSONObject getAsJson(Field field, GoodsReceiptItemIn inputs) throws Exception {
    GoodsReceiptItemRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, GoodsReceiptItemRequest request) throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public JSONObject getAsJson(Field field, List<NativeObj> rows) throws Exception {
    JSONArray array = new JSONArray();
    List<SqlRow> sqlDecl0 = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      array.put(NativeSqlUtil.getJSONObject(_r1, sqlDecl0));
    }
    gqlToSql.execute("GoodsReceipt", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, GoodsReceiptItemRequest request)
      throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public OutObject getAsJson(gqltosql2.Field field, List<NativeObj> rows) throws Exception {
    OutObjectList array = new OutObjectList();
    OutObjectList sqlDecl0 = new OutObjectList();
    for (NativeObj _r1 : rows) {
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.GoodsReceipt, sqlDecl0));
    }
    gqlToSql2.execute("GoodsReceipt", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.GoodsReceiptItem);
    result.add("items", array);
    return result;
  }

  public List<NativeObj> getNativeResult(GoodsReceiptItemRequest request) {
    String sql = "select a._id a0 from _goods_receipt a where a._id = :param_0";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getGoodsReceipt());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 0);
    return result;
  }
}
