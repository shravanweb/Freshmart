package classes;

import java.time.LocalDateTime;
import models.WarehouseStock;
import store.Database;

public class WarehouseStockService {
  public WarehouseStockService() {}

  public static WarehouseStock createWarehouseStock(WarehouseStock warehouseStock) {
    if (warehouseStock == null) {
      throw new RuntimeException("WarehouseStock cannot be null");
    }
    warehouseStock.setQuantityAvailable(
        warehouseStock.getQuantityOnHand() - warehouseStock.getQuantityReserved());
    warehouseStock.setStockValue(
        warehouseStock.getQuantityOnHand() * warehouseStock.getAverageCost());
    Database.get().save(warehouseStock);
    return warehouseStock;
  }

  public static WarehouseStock updateWarehouseStock(WarehouseStock warehouseStock) {
    if (warehouseStock == null) {
      throw new RuntimeException("WarehouseStock cannot be null");
    }
    warehouseStock.setQuantityAvailable(
        warehouseStock.getQuantityOnHand() - warehouseStock.getQuantityReserved());
    warehouseStock.setStockValue(
        warehouseStock.getQuantityOnHand() * warehouseStock.getAverageCost());
    warehouseStock.setLastMovementAt(LocalDateTime.now());
    Database.get().save(warehouseStock);
    return warehouseStock;
  }

  public static void deleteWarehouseStock(WarehouseStock warehouseStock) {
    if (warehouseStock == null) {
      return;
    }
    Database.get().delete(warehouseStock);
  }
}
