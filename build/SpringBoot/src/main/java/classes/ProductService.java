package classes;

import java.time.LocalDateTime;
import models.Product;
import store.Database;

public class ProductService {
  public ProductService() {}

  public static Product createProduct(Product product) {
    if (product == null) {
      throw new RuntimeException("Product cannot be null");
    }
    product.setCreatedAt(LocalDateTime.now());
    product.setUpdatedAt(LocalDateTime.now());
    Database.get().save(product);
    return product;
  }

  public static Product updateProduct(Product product) {
    if (product == null) {
      throw new RuntimeException("Product cannot be null");
    }
    product.setUpdatedAt(LocalDateTime.now());
    Database.get().save(product);
    return product;
  }

  public static void deleteProduct(Product product) {
    if (product == null) {
      return;
    }
    Database.get().delete(product);
  }
}
