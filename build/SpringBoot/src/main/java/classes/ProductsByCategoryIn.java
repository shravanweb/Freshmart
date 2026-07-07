package classes;

public class ProductsByCategoryIn {
  public long organization;
  public long category;

  public ProductsByCategoryIn() {}

  public ProductsByCategoryIn(long category, long organization) {
    this.organization = organization;
    this.category = category;
  }
}
