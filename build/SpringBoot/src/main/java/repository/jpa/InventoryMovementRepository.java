package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.InventoryMovement;
import models.Organization;
import models.Product;
import models.User;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class InventoryMovementRepository extends AbstractD3ERepository<InventoryMovement> {
  public int getTypeIndex() {
    return SchemaConstants.InventoryMovement;
  }

  public List<InventoryMovement> getByWarehouse(Warehouse warehouse) {
    String queryStr = "SELECT a._id from _inventory_movement a where a._warehouse_id = :warehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouse", warehouse);
    return getAllXsByY(query);
  }

  public List<InventoryMovement> getByProduct(Product product) {
    String queryStr = "SELECT a._id from _inventory_movement a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<InventoryMovement> getByPerformedBy(User performedBy) {
    String queryStr =
        "SELECT a._id from _inventory_movement a where a._performed_by_id = :performedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "performedBy", performedBy);
    return getAllXsByY(query);
  }

  public List<InventoryMovement> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _inventory_movement a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }

  public boolean existsSalesIssueForOrder(models.SalesOrder order, Product product) {
    String queryStr =
        "SELECT count(a._id) from _inventory_movement a"
            + " where a._reference_type = 'SalesOrder'"
            + " and a._reference_id = :referenceId"
            + " and a._product_id = :product"
            + " and a._movement_type = 'SalesIssue'";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "referenceId", String.valueOf(order.getId()));
    QueryImplUtil.setParameter(query, "product", product);
    Number count = (Number) query.getSingleResult();
    return count != null && count.longValue() > 0;
  }
}
