package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.Product;
import models.StockAlert;
import models.User;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class StockAlertRepository extends AbstractD3ERepository<StockAlert> {
  public int getTypeIndex() {
    return SchemaConstants.StockAlert;
  }

  public List<StockAlert> getByWarehouse(Warehouse warehouse) {
    String queryStr = "SELECT a._id from _stock_alert a where a._warehouse_id = :warehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouse", warehouse);
    return getAllXsByY(query);
  }

  public List<StockAlert> getByProduct(Product product) {
    String queryStr = "SELECT a._id from _stock_alert a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<StockAlert> getByAcknowledgedBy(User acknowledgedBy) {
    String queryStr =
        "SELECT a._id from _stock_alert a where a._acknowledged_by_id = :acknowledgedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "acknowledgedBy", acknowledgedBy);
    return getAllXsByY(query);
  }

  public List<StockAlert> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _stock_alert a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
