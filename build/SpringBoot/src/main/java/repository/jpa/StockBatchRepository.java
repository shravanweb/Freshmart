package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.Product;
import models.StockBatch;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class StockBatchRepository extends AbstractD3ERepository<StockBatch> {
  public int getTypeIndex() {
    return SchemaConstants.StockBatch;
  }

  public List<StockBatch> getByProduct(Product product) {
    String queryStr = "SELECT a._id from _stock_batch a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<StockBatch> getByWarehouse(Warehouse warehouse) {
    String queryStr = "SELECT a._id from _stock_batch a where a._warehouse_id = :warehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouse", warehouse);
    return getAllXsByY(query);
  }

  public List<StockBatch> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _stock_batch a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
