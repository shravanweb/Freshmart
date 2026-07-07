package classes;

import java.time.LocalDateTime;
import models.StockTransfer;
import models.StockTransferLine;
import store.Database;

public class StockTransferService {
  public StockTransferService() {}

  public static StockTransfer createStockTransfer(StockTransfer stockTransfer) {
    if (stockTransfer == null) {
      throw new RuntimeException("StockTransfer cannot be null");
    }
    stockTransfer.setStatus(StockTransferStatus.Draft);
    Database.get().save(stockTransfer);
    return stockTransfer;
  }

  public static StockTransfer updateStockTransfer(StockTransfer stockTransfer) {
    if (stockTransfer == null) {
      throw new RuntimeException("StockTransfer cannot be null");
    }
    Database.get().save(stockTransfer);
    return stockTransfer;
  }

  public static void deleteStockTransfer(StockTransfer stockTransfer) {
    if (stockTransfer == null) {
      return;
    }
    Database.get().delete(stockTransfer);
  }

  public static StockTransfer submitStockTransfer(StockTransfer stockTransfer) {
    if (stockTransfer == null) {
      throw new RuntimeException("StockTransfer cannot be null");
    }
    stockTransfer.setStatus(StockTransferStatus.Submitted);
    Database.get().save(stockTransfer);
    return stockTransfer;
  }

  public static StockTransfer completeStockTransfer(StockTransfer stockTransfer) {
    if (stockTransfer == null) {
      throw new RuntimeException("StockTransfer cannot be null");
    }
    stockTransfer.setStatus(StockTransferStatus.Completed);
    stockTransfer.setReceivedAt(LocalDateTime.now());
    Database.get().save(stockTransfer);
    return stockTransfer;
  }

  public static StockTransfer cancelStockTransfer(StockTransfer stockTransfer) {
    if (stockTransfer == null) {
      throw new RuntimeException("StockTransfer cannot be null");
    }
    stockTransfer.setStatus(StockTransferStatus.Cancelled);
    Database.get().save(stockTransfer);
    return stockTransfer;
  }

  public static StockTransferLine createStockTransferLine(StockTransferLine stockTransferLine) {
    if (stockTransferLine == null) {
      throw new RuntimeException("StockTransferLine cannot be null");
    }
    Database.get().save(stockTransferLine);
    return stockTransferLine;
  }

  public static StockTransferLine updateStockTransferLine(StockTransferLine stockTransferLine) {
    if (stockTransferLine == null) {
      throw new RuntimeException("StockTransferLine cannot be null");
    }
    Database.get().save(stockTransferLine);
    return stockTransferLine;
  }

  public static void deleteStockTransferLine(StockTransferLine stockTransferLine) {
    if (stockTransferLine == null) {
      return;
    }
    Database.get().delete(stockTransferLine);
  }
}
