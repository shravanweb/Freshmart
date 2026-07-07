package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.GoodsReceipt;
import models.GoodsReceiptLine;
import models.Organization;
import models.PurchaseOrder;
import models.User;
import models.Vendor;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class GoodsReceiptRepository extends AbstractD3ERepository<GoodsReceipt> {
  public int getTypeIndex() {
    return SchemaConstants.GoodsReceipt;
  }

  public List<GoodsReceipt> getByPurchaseOrder(PurchaseOrder purchaseOrder) {
    String queryStr =
        "SELECT a._id from _goods_receipt a where a._purchase_order_id = :purchaseOrder";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "purchaseOrder", purchaseOrder);
    return getAllXsByY(query);
  }

  public List<GoodsReceipt> getByVendor(Vendor vendor) {
    String queryStr = "SELECT a._id from _goods_receipt a where a._vendor_id = :vendor";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "vendor", vendor);
    return getAllXsByY(query);
  }

  public List<GoodsReceipt> getByWarehouse(Warehouse warehouse) {
    String queryStr = "SELECT a._id from _goods_receipt a where a._warehouse_id = :warehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouse", warehouse);
    return getAllXsByY(query);
  }

  public List<GoodsReceipt> getByReceivedBy(User receivedBy) {
    String queryStr = "SELECT a._id from _goods_receipt a where a._received_by_id = :receivedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "receivedBy", receivedBy);
    return getAllXsByY(query);
  }

  public List<GoodsReceipt> findByLines(GoodsReceiptLine lines) {
    String queryStr = "SELECT a._goods_receipt_id from _goods_receipt_line a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "lines", lines);
    return getAllXsByY(query);
  }

  public List<GoodsReceipt> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _goods_receipt a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
