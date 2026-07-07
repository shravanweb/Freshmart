package classes;

import java.time.LocalDateTime;
import models.GoodsReceipt;
import models.GoodsReceiptLine;
import store.Database;

public class GoodsReceiptService {
  public GoodsReceiptService() {}

  public static GoodsReceipt createGoodsReceipt(GoodsReceipt goodsReceipt) {
    if (goodsReceipt == null) {
      throw new RuntimeException("GoodsReceipt cannot be null");
    }
    goodsReceipt.setCreatedAt(LocalDateTime.now());
    goodsReceipt.setStatus(GoodsReceiptStatus.Draft);
    Database.get().save(goodsReceipt);
    return goodsReceipt;
  }

  public static GoodsReceipt updateGoodsReceipt(GoodsReceipt goodsReceipt) {
    if (goodsReceipt == null) {
      throw new RuntimeException("GoodsReceipt cannot be null");
    }
    Database.get().save(goodsReceipt);
    return goodsReceipt;
  }

  public static void deleteGoodsReceipt(GoodsReceipt goodsReceipt) {
    if (goodsReceipt == null) {
      return;
    }
    Database.get().delete(goodsReceipt);
  }

  public static GoodsReceipt confirmGoodsReceipt(GoodsReceipt goodsReceipt) {
    if (goodsReceipt == null) {
      throw new RuntimeException("GoodsReceipt cannot be null");
    }
    goodsReceipt.setStatus(GoodsReceiptStatus.Confirmed);
    Database.get().save(goodsReceipt);
    return goodsReceipt;
  }

  public static GoodsReceiptLine createGoodsReceiptLine(GoodsReceiptLine goodsReceiptLine) {
    if (goodsReceiptLine == null) {
      throw new RuntimeException("GoodsReceiptLine cannot be null");
    }
    Database.get().save(goodsReceiptLine);
    return goodsReceiptLine;
  }

  public static GoodsReceiptLine updateGoodsReceiptLine(GoodsReceiptLine goodsReceiptLine) {
    if (goodsReceiptLine == null) {
      throw new RuntimeException("GoodsReceiptLine cannot be null");
    }
    Database.get().save(goodsReceiptLine);
    return goodsReceiptLine;
  }

  public static void deleteGoodsReceiptLine(GoodsReceiptLine goodsReceiptLine) {
    if (goodsReceiptLine == null) {
      return;
    }
    Database.get().delete(goodsReceiptLine);
  }
}
