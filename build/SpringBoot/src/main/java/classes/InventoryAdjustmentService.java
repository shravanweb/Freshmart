package classes;

import models.InventoryAdjustment;
import models.InventoryAdjustmentLine;
import store.Database;

public class InventoryAdjustmentService {
  public InventoryAdjustmentService() {}

  public static InventoryAdjustment createInventoryAdjustment(
      InventoryAdjustment inventoryAdjustment) {
    if (inventoryAdjustment == null) {
      throw new RuntimeException("InventoryAdjustment cannot be null");
    }
    inventoryAdjustment.setStatus(AdjustmentStatus.Draft);
    Database.get().save(inventoryAdjustment);
    return inventoryAdjustment;
  }

  public static InventoryAdjustment updateInventoryAdjustment(
      InventoryAdjustment inventoryAdjustment) {
    if (inventoryAdjustment == null) {
      throw new RuntimeException("InventoryAdjustment cannot be null");
    }
    Database.get().save(inventoryAdjustment);
    return inventoryAdjustment;
  }

  public static void deleteInventoryAdjustment(InventoryAdjustment inventoryAdjustment) {
    if (inventoryAdjustment == null) {
      return;
    }
    Database.get().delete(inventoryAdjustment);
  }

  public static InventoryAdjustment confirmInventoryAdjustment(
      InventoryAdjustment inventoryAdjustment) {
    if (inventoryAdjustment == null) {
      throw new RuntimeException("InventoryAdjustment cannot be null");
    }
    inventoryAdjustment.setStatus(AdjustmentStatus.Confirmed);
    Database.get().save(inventoryAdjustment);
    return inventoryAdjustment;
  }

  public static InventoryAdjustmentLine createInventoryAdjustmentLine(
      InventoryAdjustmentLine inventoryAdjustmentLine) {
    if (inventoryAdjustmentLine == null) {
      throw new RuntimeException("InventoryAdjustmentLine cannot be null");
    }
    inventoryAdjustmentLine.setQuantityAfter(
        inventoryAdjustmentLine.getQuantityBefore() + inventoryAdjustmentLine.getQuantityChange());
    Database.get().save(inventoryAdjustmentLine);
    return inventoryAdjustmentLine;
  }

  public static InventoryAdjustmentLine updateInventoryAdjustmentLine(
      InventoryAdjustmentLine inventoryAdjustmentLine) {
    if (inventoryAdjustmentLine == null) {
      throw new RuntimeException("InventoryAdjustmentLine cannot be null");
    }
    inventoryAdjustmentLine.setQuantityAfter(
        inventoryAdjustmentLine.getQuantityBefore() + inventoryAdjustmentLine.getQuantityChange());
    Database.get().save(inventoryAdjustmentLine);
    return inventoryAdjustmentLine;
  }

  public static void deleteInventoryAdjustmentLine(
      InventoryAdjustmentLine inventoryAdjustmentLine) {
    if (inventoryAdjustmentLine == null) {
      return;
    }
    Database.get().delete(inventoryAdjustmentLine);
  }
}
