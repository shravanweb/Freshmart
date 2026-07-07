package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.GoodsReceipt;
import models.GoodsReceiptLine;
import models.Product;
import models.PurchaseOrderLine;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class GoodsReceiptLineRepository extends AbstractD3ERepository<GoodsReceiptLine> {
  public int getTypeIndex() {
    return SchemaConstants.GoodsReceiptLine;
  }

  public List<GoodsReceiptLine> getByProduct(Product product) {
    String queryStr = "SELECT a._id from _goods_receipt_line a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<GoodsReceiptLine> getByPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
    String queryStr =
        "SELECT a._id from _goods_receipt_line a where a._purchase_order_line_id = :purchaseOrderLine";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "purchaseOrderLine", purchaseOrderLine);
    return getAllXsByY(query);
  }

  public List<GoodsReceiptLine> getByGoodsReceipt(GoodsReceipt goodsReceipt) {
    String queryStr =
        "SELECT a._id from _goods_receipt_line a where a._goods_receipt_id = :goodsReceipt";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "goodsReceipt", goodsReceipt);
    return getAllXsByY(query);
  }
}
