package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.Product;
import models.ProductCategory;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class ProductCategoryRepository extends AbstractD3ERepository<ProductCategory> {
  public int getTypeIndex() {
    return SchemaConstants.ProductCategory;
  }

  public List<ProductCategory> getByParentCategory(ProductCategory parentCategory) {
    String queryStr =
        "SELECT a._id from _product_category a where a._parent_category_id = :parentCategory";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "parentCategory", parentCategory);
    return getAllXsByY(query);
  }

  public List<ProductCategory> findByChildCategories(ProductCategory childCategories) {
    String queryStr =
        "SELECT a._parent_category_id from _product_category a where a._id = :parentCategory";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "childCategories", childCategories);
    return getAllXsByY(query);
  }

  public List<ProductCategory> findByProducts(Product products) {
    String queryStr = "SELECT a._category_id from _product a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "products", products);
    return getAllXsByY(query);
  }

  public List<ProductCategory> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _product_category a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
