package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import models.User;
import models.Vendor;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class PurchaseOrderRepository extends AbstractD3ERepository<PurchaseOrder> {
  public int getTypeIndex() {
    return SchemaConstants.PurchaseOrder;
  }

  public List<PurchaseOrder> getByVendor(Vendor vendor) {
    String queryStr = "SELECT a._id from _purchase_order a where a._vendor_id = :vendor";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "vendor", vendor);
    return getAllXsByY(query);
  }

  public List<PurchaseOrder> getByWarehouse(Warehouse warehouse) {
    String queryStr = "SELECT a._id from _purchase_order a where a._warehouse_id = :warehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouse", warehouse);
    return getAllXsByY(query);
  }

  public List<PurchaseOrder> getByCreatedBy(User createdBy) {
    String queryStr = "SELECT a._id from _purchase_order a where a._created_by_id = :createdBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "createdBy", createdBy);
    return getAllXsByY(query);
  }

  public List<PurchaseOrder> getByApprovedBy(User approvedBy) {
    String queryStr = "SELECT a._id from _purchase_order a where a._approved_by_id = :approvedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "approvedBy", approvedBy);
    return getAllXsByY(query);
  }

  public List<PurchaseOrder> findByLines(PurchaseOrderLine lines) {
    String queryStr = "SELECT a._purchase_order_id from _purchase_order_line a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "lines", lines);
    return getAllXsByY(query);
  }

  public List<PurchaseOrder> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _purchase_order a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
