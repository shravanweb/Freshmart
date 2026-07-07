package classes;

import models.SalesReturn;
import models.SalesReturnLine;
import store.Database;

public class SalesReturnService {
  public SalesReturnService() {}

  public static SalesReturn createSalesReturn(SalesReturn salesReturn) {
    if (salesReturn == null) {
      throw new RuntimeException("SalesReturn cannot be null");
    }
    salesReturn.setStatus(SalesReturnStatus.Draft);
    Database.get().save(salesReturn);
    return salesReturn;
  }

  public static SalesReturn updateSalesReturn(SalesReturn salesReturn) {
    if (salesReturn == null) {
      throw new RuntimeException("SalesReturn cannot be null");
    }
    Database.get().save(salesReturn);
    return salesReturn;
  }

  public static void deleteSalesReturn(SalesReturn salesReturn) {
    if (salesReturn == null) {
      return;
    }
    Database.get().delete(salesReturn);
  }

  public static SalesReturn confirmSalesReturn(SalesReturn salesReturn) {
    if (salesReturn == null) {
      throw new RuntimeException("SalesReturn cannot be null");
    }
    salesReturn.setStatus(SalesReturnStatus.Confirmed);
    Database.get().save(salesReturn);
    return salesReturn;
  }

  public static SalesReturnLine createSalesReturnLine(SalesReturnLine salesReturnLine) {
    if (salesReturnLine == null) {
      throw new RuntimeException("SalesReturnLine cannot be null");
    }
    salesReturnLine.setLineTotal(salesReturnLine.getQuantity() * salesReturnLine.getUnitPrice());
    Database.get().save(salesReturnLine);
    return salesReturnLine;
  }

  public static SalesReturnLine updateSalesReturnLine(SalesReturnLine salesReturnLine) {
    if (salesReturnLine == null) {
      throw new RuntimeException("SalesReturnLine cannot be null");
    }
    salesReturnLine.setLineTotal(salesReturnLine.getQuantity() * salesReturnLine.getUnitPrice());
    Database.get().save(salesReturnLine);
    return salesReturnLine;
  }

  public static void deleteSalesReturnLine(SalesReturnLine salesReturnLine) {
    if (salesReturnLine == null) {
      return;
    }
    Database.get().delete(salesReturnLine);
  }
}
