package classes;

import java.time.LocalDateTime;
import models.Warehouse;
import store.Database;

public class WarehouseService {
  public WarehouseService() {}

  public static Warehouse createWarehouse(Warehouse warehouse) {
    if (warehouse == null) {
      throw new RuntimeException("Warehouse cannot be null");
    }
    warehouse.setCreatedAt(LocalDateTime.now());
    warehouse.setUpdatedAt(LocalDateTime.now());
    Database.get().save(warehouse);
    return warehouse;
  }

  public static Warehouse updateWarehouse(Warehouse warehouse) {
    if (warehouse == null) {
      throw new RuntimeException("Warehouse cannot be null");
    }
    warehouse.setUpdatedAt(LocalDateTime.now());
    Database.get().save(warehouse);
    return warehouse;
  }

  public static void deleteWarehouse(Warehouse warehouse) {
    if (warehouse == null) {
      return;
    }
    Database.get().delete(warehouse);
  }
}
