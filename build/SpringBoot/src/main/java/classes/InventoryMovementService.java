package classes;

import java.time.LocalDateTime;
import models.InventoryMovement;
import store.Database;

public class InventoryMovementService {
  public InventoryMovementService() {}

  public static InventoryMovement createInventoryMovement(InventoryMovement inventoryMovement) {
    if (inventoryMovement == null) {
      throw new RuntimeException("InventoryMovement cannot be null");
    }
    if (inventoryMovement.getMovementDate() == null) {
      inventoryMovement.setMovementDate(LocalDateTime.now());
    }
    Database.get().save(inventoryMovement);
    return inventoryMovement;
  }

  public static InventoryMovement updateInventoryMovement(InventoryMovement inventoryMovement) {
    if (inventoryMovement == null) {
      throw new RuntimeException("InventoryMovement cannot be null");
    }
    Database.get().save(inventoryMovement);
    return inventoryMovement;
  }

  public static void deleteInventoryMovement(InventoryMovement inventoryMovement) {
    if (inventoryMovement == null) {
      return;
    }
    Database.get().delete(inventoryMovement);
  }
}
