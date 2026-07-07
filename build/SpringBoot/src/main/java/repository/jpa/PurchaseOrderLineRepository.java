package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Product;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import models.UnitOfMeasure;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class PurchaseOrderLineRepository extends AbstractD3ERepository<PurchaseOrderLine> {
  public int getTypeIndex() {
    return SchemaConstants.PurchaseOrderLine;
  }

  public List<PurchaseOrderLine> getByProduct(Product product) {
    String queryStr = "SELECT a._id from _purchase_order_line a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<PurchaseOrderLine> getByUom(UnitOfMeasure uom) {
    String queryStr = "SELECT a._id from _purchase_order_line a where a._uom_id = :uom";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "uom", uom);
    return getAllXsByY(query);
  }

  public List<PurchaseOrderLine> getByPurchaseOrder(PurchaseOrder purchaseOrder) {
    String queryStr =
        "SELECT a._id from _purchase_order_line a where a._purchase_order_id = :purchaseOrder";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "purchaseOrder", purchaseOrder);
    return getAllXsByY(query);
  }
}
