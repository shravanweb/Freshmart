package classes;

public class PurchaseOrdersByStatusIn {
  public long organization;
  public PurchaseOrderStatus status;

  public PurchaseOrdersByStatusIn() {}

  public PurchaseOrdersByStatusIn(long organization, PurchaseOrderStatus status) {
    this.organization = organization;
    this.status = status;
  }
}
