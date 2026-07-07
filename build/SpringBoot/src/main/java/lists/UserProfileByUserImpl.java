package lists;

import classes.UserProfileByUser;
import classes.UserProfileByUserIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.UserProfile;
import models.UserProfileByUserRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.UserRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class UserProfileByUserImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private UserRepository userRepository;

  public UserProfileByUserRequest inputToRequest(UserProfileByUserIn inputs) {
    UserProfileByUserRequest request = new UserProfileByUserRequest();
    request.setUser(userRepository.findById(inputs.user));
    return request;
  }

  public UserProfileByUser get(UserProfileByUserIn inputs) {
    UserProfileByUserRequest request = inputToRequest(inputs);
    return get(request);
  }

  public UserProfileByUser get(UserProfileByUserRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    return getAsStruct(rows);
  }

  public UserProfileByUser getAsStruct(List<NativeObj> rows) {
    List<UserProfile> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(1), SchemaConstants.UserProfile));
    }
    UserProfileByUser wrap = new UserProfileByUser();
    wrap.setItems(result);
    return wrap;
  }

  public JSONObject getAsJson(Field field, UserProfileByUserIn inputs) throws Exception {
    UserProfileByUserRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, UserProfileByUserRequest request) throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public JSONObject getAsJson(Field field, List<NativeObj> rows) throws Exception {
    JSONArray array = new JSONArray();
    List<SqlRow> sqlDecl0 = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      array.put(NativeSqlUtil.getJSONObject(_r1, sqlDecl0));
    }
    gqlToSql.execute("UserProfile", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, UserProfileByUserRequest request)
      throws Exception {
    List<NativeObj> rows = getNativeResult(request);
    return getAsJson(field, rows);
  }

  public OutObject getAsJson(gqltosql2.Field field, List<NativeObj> rows) throws Exception {
    OutObjectList array = new OutObjectList();
    OutObjectList sqlDecl0 = new OutObjectList();
    for (NativeObj _r1 : rows) {
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.UserProfile, sqlDecl0));
    }
    gqlToSql2.execute("UserProfile", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.UserProfileByUser);
    result.add("items", array);
    return result;
  }

  public List<NativeObj> getNativeResult(UserProfileByUserRequest request) {
    String sql = "select a._user_id a0, a._id a1 from _user_profile a where a._user_id = :param_0";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getUser());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 1);
    return result;
  }
}
