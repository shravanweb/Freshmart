package classes;

import models.UnitOfMeasure;
import store.Database;

public class UnitOfMeasureService {
  public UnitOfMeasureService() {}

  public static UnitOfMeasure createUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
    if (unitOfMeasure == null) {
      throw new RuntimeException("UnitOfMeasure cannot be null");
    }
    Database.get().save(unitOfMeasure);
    return unitOfMeasure;
  }

  public static UnitOfMeasure updateUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
    if (unitOfMeasure == null) {
      throw new RuntimeException("UnitOfMeasure cannot be null");
    }
    Database.get().save(unitOfMeasure);
    return unitOfMeasure;
  }

  public static void deleteUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
    if (unitOfMeasure == null) {
      return;
    }
    Database.get().delete(unitOfMeasure);
  }
}
