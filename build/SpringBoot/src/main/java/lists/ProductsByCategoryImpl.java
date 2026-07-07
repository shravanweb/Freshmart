package lists;

import classes.ProductsByCategory;
import classes.ProductsByCategoryIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.Product;
import models.ProductsByCategoryRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OrganizationRepository;
import repository.jpa.ProductCategoryRepository;
import rest.AbstractQueryService;
import rest.ws.RocketQuery;
import store.D3EEntityManagerProvider;

@Service
public class ProductsByCategoryImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private ProductCategoryRepository productCategoryRepository;

  public ProductsByCategoryRequest inputToRequest(ProductsByCategoryIn inputs) {
    ProductsByCategoryRequest request = new ProductsByCategoryRequest();
    request.setOrganization(organizationRepository.findById(inputs.organization));
    request.setCategory(productCategoryRepository.findById(inputs.category));
    return request;
  }

  public ProductsByCategory get(ProductsByCategoryIn inputs) {
    ProductsByCategoryRequest request = inputToRequest(inputs);
    return get(request);
  }

  public ProductsByCategory get(ProductsByCategoryRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsStruct(rows, count);
  }

  public ProductsByCategory getAsStruct(List<NativeObj> rows, long count) {
    List<Product> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(3), SchemaConstants.Product));
    }
    ProductsByCategory wrap = new ProductsByCategory();
    wrap.setItems(result);
    wrap.setCount(count);
    return wrap;
  }

  public JSONObject getAsJson(Field field, ProductsByCategoryIn inputs) throws Exception {
    ProductsByCategoryRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, ProductsByCategoryRequest request) throws Exception {
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
    gqlToSql.execute("Product", AbstractQueryService.inspect(field, ""), sqlDecl0);
    JSONObject result = new JSONObject();
    result.put("items", array);
    result.put("count", count);
    return result;
  }

  public OutObject getAsJson(gqltosql2.Field field, ProductsByCategoryRequest request)
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
      array.add(NativeSqlUtil.getOutObject(_r1, SchemaConstants.Product, sqlDecl0));
    }
    gqlToSql2.execute("Product", RocketQuery.inspect2(field, ""), sqlDecl0);
    OutObject result = new OutObject();
    result.addType(SchemaConstants.ProductsByCategory);
    result.add("items", array);
    result.add("count", count);
    return result;
  }

  public List<NativeObj> getNativeResult(ProductsByCategoryRequest request) {
    String sql =
        "select a._organization_id a0, a._category_id a1, a._name a2, a._id a3 from _product a where a._organization_id = :param_0 and a._category_id = :param_1 order by a._name";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    setDatabaseObjectParameter(query, "param_1", request.getCategory());
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 3);
    return result;
  }

  public long getCountResult(ProductsByCategoryRequest request) {
    String sql =
        "select count(a._id) a0 from _product a where a._organization_id = :param_0 and a._category_id = :param_1";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    setDatabaseObjectParameter(query, "param_1", request.getCategory());
    this.logQuery(sql, query);
    return getCountResult(query);
  }
}
