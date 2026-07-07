package classes;

import java.time.LocalDateTime;

public class MovementReportRowsIn {
  public long organization;
  public LocalDateTime startDate;
  public LocalDateTime endDate;
  public long warehouse;

  public MovementReportRowsIn() {}

  public MovementReportRowsIn(
      LocalDateTime endDate, long organization, LocalDateTime startDate, long warehouse) {
    this.organization = organization;
    this.startDate = startDate;
    this.endDate = endDate;
    this.warehouse = warehouse;
  }
}
