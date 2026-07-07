package classes;

import java.time.LocalDateTime;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import store.Database;

public class PurchaseOrderService {
  public PurchaseOrderService() {}

  public static PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
    if (purchaseOrder == null) {
      throw new RuntimeException("PurchaseOrder cannot be null");
    }
    purchaseOrder.setCreatedAt(LocalDateTime.now());
    purchaseOrder.setStatus(PurchaseOrderStatus.Draft);
    Database.get().save(purchaseOrder);
    return purchaseOrder;
  }

  public static PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder) {
    if (purchaseOrder == null) {
      throw new RuntimeException("PurchaseOrder cannot be null");
    }
    Database.get().save(purchaseOrder);
    return purchaseOrder;
  }

  public static void deletePurchaseOrder(PurchaseOrder purchaseOrder) {
    if (purchaseOrder == null) {
      return;
    }
    Database.get().delete(purchaseOrder);
  }

  public static PurchaseOrder submitPurchaseOrder(PurchaseOrder purchaseOrder) {
    if (purchaseOrder == null) {
      throw new RuntimeException("PurchaseOrder cannot be null");
    }
    purchaseOrder.setStatus(PurchaseOrderStatus.Submitted);
    Database.get().save(purchaseOrder);
    return purchaseOrder;
  }

  public static PurchaseOrder cancelPurchaseOrder(PurchaseOrder purchaseOrder) {
    if (purchaseOrder == null) {
      throw new RuntimeException("PurchaseOrder cannot be null");
    }
    purchaseOrder.setStatus(PurchaseOrderStatus.Cancelled);
    Database.get().save(purchaseOrder);
    return purchaseOrder;
  }

  public static PurchaseOrderLine createPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
    if (purchaseOrderLine == null) {
      throw new RuntimeException("PurchaseOrderLine cannot be null");
    }
    purchaseOrderLine.setLineTotal(
        purchaseOrderLine.getOrderedQuantity() * purchaseOrderLine.getUnitPrice());
    Database.get().save(purchaseOrderLine);
    return purchaseOrderLine;
  }

  public static PurchaseOrderLine updatePurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
    if (purchaseOrderLine == null) {
      throw new RuntimeException("PurchaseOrderLine cannot be null");
    }
    purchaseOrderLine.setLineTotal(
        purchaseOrderLine.getOrderedQuantity() * purchaseOrderLine.getUnitPrice());
    Database.get().save(purchaseOrderLine);
    return purchaseOrderLine;
  }

  public static void deletePurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
    if (purchaseOrderLine == null) {
      return;
    }
    Database.get().delete(purchaseOrderLine);
  }
}
