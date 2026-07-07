package classes;

public class StockValuationReportIn {
  public long organization;
  public long warehouse;

  public StockValuationReportIn() {}

  public StockValuationReportIn(long organization, long warehouse) {
    this.organization = organization;
    this.warehouse = warehouse;
  }
}
