package classes;

import java.time.LocalDateTime;
import models.StockBatch;
import store.Database;

public class StockBatchService {
  public StockBatchService() {}

  public static StockBatch createStockBatch(StockBatch stockBatch) {
    if (stockBatch == null) {
      throw new RuntimeException("StockBatch cannot be null");
    }
    stockBatch.setCreatedAt(LocalDateTime.now());
    Database.get().save(stockBatch);
    return stockBatch;
  }

  public static StockBatch updateStockBatch(StockBatch stockBatch) {
    if (stockBatch == null) {
      throw new RuntimeException("StockBatch cannot be null");
    }
    Database.get().save(stockBatch);
    return stockBatch;
  }

  public static void deleteStockBatch(StockBatch stockBatch) {
    if (stockBatch == null) {
      return;
    }
    Database.get().delete(stockBatch);
  }
}
