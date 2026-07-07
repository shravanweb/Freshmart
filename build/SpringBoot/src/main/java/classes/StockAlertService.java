package classes;

import java.time.LocalDateTime;
import models.StockAlert;
import store.Database;

public class StockAlertService {
  public StockAlertService() {}

  public static StockAlert createStockAlert(StockAlert stockAlert) {
    if (stockAlert == null) {
      throw new RuntimeException("StockAlert cannot be null");
    }
    stockAlert.setCreatedAt(LocalDateTime.now());
    stockAlert.setStatus(AlertStatus.Open);
    Database.get().save(stockAlert);
    return stockAlert;
  }

  public static StockAlert updateStockAlert(StockAlert stockAlert) {
    if (stockAlert == null) {
      throw new RuntimeException("StockAlert cannot be null");
    }
    Database.get().save(stockAlert);
    return stockAlert;
  }

  public static void deleteStockAlert(StockAlert stockAlert) {
    if (stockAlert == null) {
      return;
    }
    Database.get().delete(stockAlert);
  }
}
