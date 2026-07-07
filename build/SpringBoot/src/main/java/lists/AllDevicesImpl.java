package lists;

import classes.AllDevices;
import classes.AllDevicesIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.AllDevicesRequest;
import models.UserDevice;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.BaseUserRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class AllDevicesImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private BaseUserRepository baseUserRepository;

  public AllDevicesRequest inputToRequest(AllDevicesIn inputs) {
    AllDevicesRequest request = new AllDevicesRequest();
    request.setUsers(baseUserRepository.findByIds(inputs.users));
    return request;
  }

  public AllDevices get(AllDevicesIn inputs) {
    AllDevicesRequest request = inputToRequest(inputs);
    return get(request);
  }

  public AllDevices get(AllDevicesRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    return getAsStruct(rows);
  }

  public AllDevices getAsStruct(List<NativeObj> rows) {
    List<UserDevice> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(0), SchemaConstants.UserDevice));
    }
    AllDevices wrap = new AllDevices();
    wrap.setItems(result);
    return wrap;
  }

  public JSONObject getAsJson(Field field, AllDevicesIn inputs) throws Exception {
    AllDevicesRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, AllDevicesRequest request) throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public JSONObject getAsJson(Field field, List<NativeObj> rows) throws Exception {
    JSONArray array = new JSONArray();
    List<SqlRow> sqlDecl0 = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      array.put(NativeSqlUtil.getJSONObject(_r1, sqlDecl0));
    }
    gqlToSql.execute("UserDevice", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, AllDevicesRequest request) throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public OutObject getAsJson(gqltosql2.Field field, List<NativeObj> rows) throws Exception {
    OutObjectList array = new OutObjectList();
    OutObjectList sqlDecl0 = new OutObjectList();
    for (NativeObj _r1 : rows) {
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.UserDevice, sqlDecl0));
    }
    gqlToSql2.execute("UserDevice", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.AllDevices);
    result.add("items", array);
    return result;
  }

  public List<NativeObj> getNativeResult(AllDevicesRequest request) {
    String sql = "select a._id a0 from _user_device a where a._user_id in (:param_0)";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectListParameter(query, "param_0", request.getUsers());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 0);
    return result;
  }
}
