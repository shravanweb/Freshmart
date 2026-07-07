package lists;

import classes.ExpiringBatches;
import classes.ExpiringBatchesIn;
import d3e.core.DateTimeExt;
import d3e.core.DurationExt;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import models.ExpiringBatchesRequest;
import models.StockBatch;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OrganizationRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class ExpiringBatchesImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private OrganizationRepository organizationRepository;

  public ExpiringBatchesRequest inputToRequest(ExpiringBatchesIn inputs) {
    ExpiringBatchesRequest request = new ExpiringBatchesRequest();
    request.setOrganization(organizationRepository.findById(inputs.organization));
    request.setDaysAhead(inputs.daysAhead);
    return request;
  }

  public ExpiringBatches get(ExpiringBatchesIn inputs) {
    ExpiringBatchesRequest request = inputToRequest(inputs);
    return get(request);
  }

  public ExpiringBatches get(ExpiringBatchesRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsStruct(rows, count);
  }

  public ExpiringBatches getAsStruct(List<NativeObj> rows, long count) {
    List<StockBatch> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(2), SchemaConstants.StockBatch));
    }
    ExpiringBatches wrap = new ExpiringBatches();
    wrap.setItems(result);
    wrap.setCount(count);
    return wrap;
  }

  public JSONObject getAsJson(Field field, ExpiringBatchesIn inputs) throws Exception {
    ExpiringBatchesRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, ExpiringBatchesRequest request) throws Exception {
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
    gqlToSql.execute("StockBatch", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    result.put("count", count);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, ExpiringBatchesRequest request)
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
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.StockBatch, sqlDecl0));
    }
    gqlToSql2.execute("StockBatch", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.ExpiringBatches);
    result.add("items", array);
    result.add("count", count);
    return result;
  }

  public List<NativeObj> getNativeResult(ExpiringBatchesRequest request) {
    String sql =
        "select a._organization_id a0, a._expiry_date a1, a._id a2 from _stock_batch a where a._organization_id = :param_0 and a._expiry_date is not null and a._expiry_date <= :param_1 order by a._expiry_date";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    setDateParameter(
        query,
        "param_1",
        DateTimeExt.getDate(
            DateTimeExt.add(
                LocalDateTime.now(),
                DurationExt.Duration(request.getDaysAhead(), 0l, 0l, 0l, 0l, 0l))));
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 2);
    return result;
  }

  public long getCountResult(ExpiringBatchesRequest request) {
    String sql =
        "select count(a._id) a0 from _stock_batch a where a._organization_id = :param_0 and a._expiry_date is not null and a._expiry_date <= :param_1";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    setDateParameter(
        query,
        "param_1",
        DateTimeExt.getDate(
            DateTimeExt.add(
                LocalDateTime.now(),
                DurationExt.Duration(request.getDaysAhead(), 0l, 0l, 0l, 0l, 0l))));
    this.logQuery(sql, query);
    return getCountResult(query);
  }
}
