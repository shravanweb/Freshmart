package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.Product;
import models.ProductCategory;
import models.StockBatch;
import models.UnitOfMeasure;
import models.WarehouseStock;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class ProductRepository extends AbstractD3ERepository<Product> {
  public int getTypeIndex() {
    return SchemaConstants.Product;
  }

  public List<Product> getByCategory(ProductCategory category) {
    String queryStr = "SELECT a._id from _product a where a._category_id = :category";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "category", category);
    return getAllXsByY(query);
  }

  public List<Product> getByBaseUom(UnitOfMeasure baseUom) {
    String queryStr = "SELECT a._id from _product a where a._base_uom_id = :baseUom";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "baseUom", baseUom);
    return getAllXsByY(query);
  }

  public List<Product> findByWarehouseStocks(WarehouseStock warehouseStocks) {
    String queryStr = "SELECT a._product_id from _warehouse_stock a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouseStocks", warehouseStocks);
    return getAllXsByY(query);
  }

  public List<Product> findByBatches(StockBatch batches) {
    String queryStr = "SELECT a._product_id from _stock_batch a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "batches", batches);
    return getAllXsByY(query);
  }

  public List<Product> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _product a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }

  public List<Product> getByVendor(Organization organization, long vendorId) {
    String queryStr =
        "SELECT a._id FROM _product a "
            + "WHERE a._organization_id = :organization "
            + "AND a._vendor_id = :vendor "
            + "AND a._status = 'Active'";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    QueryImplUtil.setParameter(query, "vendor", vendorId);
    return getAllXsByY(query);
  }

  public void assignProductsToVendor(
      Organization organization, long vendorId, List<Long> productIds) {
    String clearQuery =
        "UPDATE _product SET _vendor_id = NULL "
            + "WHERE _organization_id = :organization AND _vendor_id = :vendor";
    Query clear = em().createNativeQuery(clearQuery);
    QueryImplUtil.setParameter(clear, "organization", organization);
    QueryImplUtil.setParameter(clear, "vendor", vendorId);
    clear.executeUpdate();

    if (productIds == null) {
      return;
    }

    for (Long productId : productIds) {
      if (productId == null || productId <= 0) {
        continue;
      }
      String assignQuery =
          "UPDATE _product SET _vendor_id = :vendor "
              + "WHERE _id = :productId AND _organization_id = :organization";
      Query assign = em().createNativeQuery(assignQuery);
      QueryImplUtil.setParameter(assign, "organization", organization);
      QueryImplUtil.setParameter(assign, "vendor", vendorId);
      QueryImplUtil.setIntegerParameter(assign, "productId", productId);
      assign.executeUpdate();
    }
  }
}
