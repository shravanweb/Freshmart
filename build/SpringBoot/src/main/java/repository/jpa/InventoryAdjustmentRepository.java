package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.InventoryAdjustment;
import models.InventoryAdjustmentLine;
import models.Organization;
import models.User;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class InventoryAdjustmentRepository extends AbstractD3ERepository<InventoryAdjustment> {
  public int getTypeIndex() {
    return SchemaConstants.InventoryAdjustment;
  }

  public List<InventoryAdjustment> getByWarehouse(Warehouse warehouse) {
    String queryStr =
        "SELECT a._id from _inventory_adjustment a where a._warehouse_id = :warehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouse", warehouse);
    return getAllXsByY(query);
  }

  public List<InventoryAdjustment> getByAdjustedBy(User adjustedBy) {
    String queryStr =
        "SELECT a._id from _inventory_adjustment a where a._adjusted_by_id = :adjustedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "adjustedBy", adjustedBy);
    return getAllXsByY(query);
  }

  public List<InventoryAdjustment> findByLines(InventoryAdjustmentLine lines) {
    String queryStr = "SELECT a._inventory_adjustment_id from _inventory_adjustment_line a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "lines", lines);
    return getAllXsByY(query);
  }

  public List<InventoryAdjustment> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _inventory_adjustment a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
