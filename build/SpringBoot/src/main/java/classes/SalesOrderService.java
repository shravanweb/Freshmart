package classes;

import models.SalesOrder;
import models.SalesOrderLine;
import store.Database;

public class SalesOrderService {
  public SalesOrderService() {}

  public static SalesOrder createSalesOrder(SalesOrder salesOrder) {
    if (salesOrder == null) {
      throw new RuntimeException("SalesOrder cannot be null");
    }
    salesOrder.setStatus(SalesOrderStatus.Draft);
    Database.get().save(salesOrder);
    return salesOrder;
  }

  public static SalesOrder updateSalesOrder(SalesOrder salesOrder) {
    if (salesOrder == null) {
      throw new RuntimeException("SalesOrder cannot be null");
    }
    Database.get().save(salesOrder);
    return salesOrder;
  }

  public static void deleteSalesOrder(SalesOrder salesOrder) {
    if (salesOrder == null) {
      return;
    }
    Database.get().delete(salesOrder);
  }

  public static SalesOrder confirmSalesOrder(SalesOrder salesOrder) {
    if (salesOrder == null) {
      throw new RuntimeException("SalesOrder cannot be null");
    }
    salesOrder.setStatus(SalesOrderStatus.Confirmed);
    Database.get().save(salesOrder);
    return salesOrder;
  }

  public static SalesOrder cancelSalesOrder(SalesOrder salesOrder) {
    if (salesOrder == null) {
      throw new RuntimeException("SalesOrder cannot be null");
    }
    salesOrder.setStatus(SalesOrderStatus.Cancelled);
    Database.get().save(salesOrder);
    return salesOrder;
  }

  public static SalesOrderLine createSalesOrderLine(SalesOrderLine salesOrderLine) {
    if (salesOrderLine == null) {
      throw new RuntimeException("SalesOrderLine cannot be null");
    }
    salesOrderLine.setLineTotal(
        (salesOrderLine.getQuantity() * salesOrderLine.getUnitPrice())
            - salesOrderLine.getDiscount());
    Database.get().save(salesOrderLine);
    return salesOrderLine;
  }

  public static SalesOrderLine updateSalesOrderLine(SalesOrderLine salesOrderLine) {
    if (salesOrderLine == null) {
      throw new RuntimeException("SalesOrderLine cannot be null");
    }
    salesOrderLine.setLineTotal(
        (salesOrderLine.getQuantity() * salesOrderLine.getUnitPrice())
            - salesOrderLine.getDiscount());
    Database.get().save(salesOrderLine);
    return salesOrderLine;
  }

  public static void deleteSalesOrderLine(SalesOrderLine salesOrderLine) {
    if (salesOrderLine == null) {
      return;
    }
    Database.get().delete(salesOrderLine);
  }
}
