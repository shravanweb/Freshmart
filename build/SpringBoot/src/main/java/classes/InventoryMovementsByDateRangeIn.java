package classes;

import java.time.LocalDateTime;

public class InventoryMovementsByDateRangeIn {
  public long organization;
  public LocalDateTime startDate;
  public LocalDateTime endDate;

  public InventoryMovementsByDateRangeIn() {}

  public InventoryMovementsByDateRangeIn(
      LocalDateTime endDate, long organization, LocalDateTime startDate) {
    this.organization = organization;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
