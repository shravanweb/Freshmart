package classes;

import models.ProductCategory;
import store.Database;

public class ProductCategoryService {
  public ProductCategoryService() {}

  public static ProductCategory createProductCategory(ProductCategory category) {
    if (category == null) {
      throw new RuntimeException("ProductCategory cannot be null");
    }
    Database.get().save(category);
    return category;
  }

  public static ProductCategory updateProductCategory(ProductCategory category) {
    if (category == null) {
      throw new RuntimeException("ProductCategory cannot be null");
    }
    Database.get().save(category);
    return category;
  }

  public static void deleteProductCategory(ProductCategory category) {
    if (category == null) {
      return;
    }
    Database.get().delete(category);
  }
}
