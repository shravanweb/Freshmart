package classes;

public class ProductSearchIn {
  public long organization;
  public String searchTerm;
  public long category;
  public ProductStatus status;

  public ProductSearchIn() {}

  public ProductSearchIn(
      long category, long organization, String searchTerm, ProductStatus status) {
    this.organization = organization;
    this.searchTerm = searchTerm;
    this.category = category;
    this.status = status;
  }
}
