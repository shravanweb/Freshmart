package classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Organization;
import models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OrganizationRepository;
import repository.jpa.ProductRepository;

@Service
public class VendorProductService {
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private ProductRepository productRepository;

  public List<Map<String, Object>> getProductsByVendor(long organizationId, long vendorId) {
    Organization organization = organizationRepository.findById(organizationId);
    if (organization == null) {
      return null;
    }

    List<Product> products = productRepository.getByVendor(organization, vendorId);
    products.sort(
        Comparator.comparing((Product product) -> product.getSku() != null ? product.getSku() : "")
            .thenComparing(product -> product.getName() != null ? product.getName() : ""));

    List<Map<String, Object>> items = new ArrayList<>();
    for (Product product : products) {
      if (product.getStatus() != ProductStatus.Active) {
        continue;
      }
      Map<String, Object> item = new HashMap<>();
      item.put("id", product.getId());
      item.put("sku", product.getSku() != null ? product.getSku() : "");
      item.put("name", product.getName() != null ? product.getName() : "");
      item.put(
          "description",
          product.getDescription() != null ? product.getDescription() : "");
      item.put("barcode", product.getBarcode() != null ? product.getBarcode() : "");
      item.put("purchasePrice", product.getPurchasePrice());
      item.put("sellingPrice", product.getSellingPrice());
      item.put("reorderLevel", product.getReorderLevel());
      item.put("reorderQuantity", product.getReorderQuantity());
      item.put("status", product.getStatus().name());
      if (product.getCategory() != null) {
        item.put("categoryId", product.getCategory().getId());
        item.put("categoryName", product.getCategory().getName());
      }
      items.add(item);
    }
    return items;
  }

  public Map<String, Object> assignProductsToVendor(
      long organizationId, long vendorId, List<Long> productIds) {
    Map<String, Object> result = new HashMap<>();
    List<String> errors = new ArrayList<>();

    Organization organization = organizationRepository.findById(organizationId);
    if (organization == null) {
      errors.add("Organization not found.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    if (vendorId <= 0) {
      errors.add("Supplier not found.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    productRepository.assignProductsToVendor(organization, vendorId, productIds);
    result.put("success", true);
    result.put("errors", errors);
    return result;
  }
}
