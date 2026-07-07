package lists;

import classes.ProductSearch;
import classes.ProductSearchIn;
import d3e.core.SchemaConstants;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql2.OutObject;
import gqltosql2.OutObjectList;
import graphql.language.Field;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import models.Product;
import models.ProductSearchRequest;
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
public class ProductSearchImpl extends AbsDataQueryImpl {
  @Autowired private D3EEntityManagerProvider em;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private gqltosql2.GqlToSql gqlToSql2;
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private ProductCategoryRepository productCategoryRepository;

  public ProductSearchRequest inputToRequest(ProductSearchIn inputs) {
    ProductSearchRequest request = new ProductSearchRequest();
    request.setOrganization(organizationRepository.findById(inputs.organization));
    request.setSearchTerm(inputs.searchTerm);
    request.setCategory(productCategoryRepository.findById(inputs.category));
    request.setStatus(inputs.status);
    return request;
  }

  public ProductSearch get(ProductSearchIn inputs) {
    ProductSearchRequest request = inputToRequest(inputs);
    return get(request);
  }

  public ProductSearch get(ProductSearchRequest request) {
    List<NativeObj> rows = getNativeResult(request);
    long count = getCountResult(request);
    return getAsStruct(rows, count);
  }

  public ProductSearch getAsStruct(List<NativeObj> rows, long count) {
    List<Product> result = new ArrayList<>();
    for (NativeObj _r1 : rows) {
      result.add(NativeSqlUtil.get(em.get(), _r1.getRef(6), SchemaConstants.Product));
    }
    ProductSearch wrap = new ProductSearch();
    wrap.setItems(result);
    wrap.setCount(count);
    return wrap;
  }

  public JSONObject getAsJson(Field field, ProductSearchIn inputs) throws Exception {
    ProductSearchRequest request = inputToRequest(inputs);
    return getAsJson(field, request);
  }

  public JSONObject getAsJson(Field field, ProductSearchRequest request) throws Exception {
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

  public OutObject getAsJson(gqltosql2.Field field, ProductSearchRequest request) throws Exception {
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
    result.addType(SchemaConstants.ProductSearch);
    result.add("items", array);
    result.add("count", count);
    return result;
  }

  public List<NativeObj> getNativeResult(ProductSearchRequest request) {
    String sql =
        "select a._organization_id a0, a._category_id a1, a._status a2, a._name a3, a._sku a4, a._barcode a5, a._id a6 from _product a where a._organization_id = :param_0 and (:param_1 or a._category_id = :param_2) and (:param_3 or a._status = :param_4) and (:param_5 or (a._name) like :param_6 or (a._sku) like :param_6 or (a._barcode is not null and (a._barcode) like :param_6)) order by a._name";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    setBooleanParameter(query, "param_1", request.getCategory() == null);
    setDatabaseObjectParameter(query, "param_2", request.getCategory());
    setBooleanParameter(query, "param_3", request.getStatus() == null);
    setEnumParameter(query, "param_4", request.getStatus());
    setBooleanParameter(
        query,
        "param_5",
        request.getSearchTerm() == null || Objects.equals(request.getSearchTerm(), ""));
    setStringParameter(query, "param_6", like(request.getSearchTerm()));
    this.logQuery(sql, query);
    List<NativeObj> result = NativeSqlUtil.createNativeObj(query.getResultList(), 6);
    return result;
  }

  public long getCountResult(ProductSearchRequest request) {
    String sql =
        "select count(a._id) a0 from _product a where a._organization_id = :param_0 and (:param_1 or a._category_id = :param_2) and (:param_3 or a._status = :param_4) and (:param_5 or (a._name) like :param_6 or (a._sku) like :param_6 or (a._barcode is not null and (a._barcode) like :param_6))";
    Query query = em.get().createNativeQuery(sql);
    setDatabaseObjectParameter(query, "param_0", request.getOrganization());
    setBooleanParameter(query, "param_1", request.getCategory() == null);
    setDatabaseObjectParameter(query, "param_2", request.getCategory());
    setBooleanParameter(query, "param_3", request.getStatus() == null);
    setEnumParameter(query, "param_4", request.getStatus());
    setBooleanParameter(
        query,
        "param_5",
        request.getSearchTerm() == null || Objects.equals(request.getSearchTerm(), ""));
    setStringParameter(query, "param_6", like(request.getSearchTerm()));
    this.logQuery(sql, query);
    return getCountResult(query);
  }
}
