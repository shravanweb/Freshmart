package classes;

public class ExpiringBatchesIn {
  public long organization;
  public long daysAhead;

  public ExpiringBatchesIn() {}

  public ExpiringBatchesIn(long daysAhead, long organization) {
    this.organization = organization;
    this.daysAhead = daysAhead;
  }
}
