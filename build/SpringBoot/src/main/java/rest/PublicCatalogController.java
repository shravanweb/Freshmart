package rest;

import classes.AllProductCategories;
import classes.AllProducts;
import classes.AllStores;
import classes.EntityStatus;
import classes.ProductStatus;
import d3e.core.RestControllerBase;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lists.AllProductCategoriesImpl;
import lists.AllProductsImpl;
import lists.AllStoresImpl;
import models.AllProductCategoriesRequest;
import models.AllProductsRequest;
import models.AllStoresRequest;
import models.Organization;
import models.Product;
import models.ProductCategory;
import models.Store;
import models.WarehouseStock;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.jpa.OrganizationRepository;
import repository.jpa.ProductRepository;
import repository.jpa.WarehouseStockRepository;

@RestController
public class PublicCatalogController extends RestControllerBase {
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private AllProductCategoriesImpl allProductCategoriesImpl;
  @Autowired private AllProductsImpl allProductsImpl;
  @Autowired private AllStoresImpl allStoresImpl;
  @Autowired private ProductRepository productRepository;
  @Autowired private WarehouseStockRepository warehouseStockRepository;
  @Autowired private CategoryImageStore categoryImageStore;
  @Autowired private ProductImageStore productImageStore;

  private double getAvailableQuantity(Product product) {
    List<WarehouseStock> stocks = warehouseStockRepository.getByProduct(product);
    double total = 0.0d;
    for (WarehouseStock stock : stocks) {
      total += stock.getQuantityAvailable();
    }
    return total;
  }

  private Organization resolveFreshMartOrganization() {
    Organization organization = organizationRepository.getByCode("FMRG");
    if (organization == null) {
      organization = organizationRepository.getByName("FreshMart Retail Group");
    }
    return organization;
  }

  private JSONObject toPublicProductJson(Product product) {
    JSONObject item = new JSONObject();
    item.put("id", product.getId());
    item.put("sku", product.getSku() != null ? product.getSku() : "");
    item.put("name", product.getName() != null ? product.getName() : "");
    item.put(
        "description",
        product.getDescription() != null ? product.getDescription() : "");
    item.put("barcode", product.getBarcode() != null ? product.getBarcode() : "");
    item.put("sellingPrice", product.getSellingPrice());
    item.put("purchasePrice", product.getPurchasePrice());
    item.put("status", product.getStatus().name());

    List<String> productImageUrls = productImageStore.listImageUrls(product.getId());
    if (!productImageUrls.isEmpty()) {
      JSONArray imageUrls = new JSONArray();
      for (String imageUrl : productImageUrls) {
        imageUrls.put(imageUrl);
      }
      item.put("imageUrls", imageUrls);
      item.put("imageUrl", productImageUrls.get(0));
    }

    ProductCategory category = product.getCategory();
    if (category != null) {
      item.put("categoryId", category.getId());
      item.put("categoryName", category.getName());
      item.put("categoryCode", category.getCode());
      if (productImageUrls.isEmpty()) {
        String imageUrl = categoryImageStore.resolveImageUrl(category.getCode());
        if (imageUrl != null) {
          item.put("imageUrl", imageUrl);
        }
      }
    }

    if (product.getBaseUom() != null) {
      item.put("uomSymbol", product.getBaseUom().getSymbol());
      item.put("uomName", product.getBaseUom().getName());
    }

    item.put("quantityAvailable", getAvailableQuantity(product));

    return item;
  }

  @GetMapping("/api/public/categories")
  public void getPublicCategories() {
    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      markNotFound();
      return;
    }

    AllProductCategoriesRequest request = new AllProductCategoriesRequest();
    request.setOrganization(organization);
    AllProductCategories result = allProductCategoriesImpl.get(request);

    JSONArray items = new JSONArray();
    List<ProductCategory> categories =
        result.getItems() != null ? result.getItems() : new ArrayList<>();
    for (ProductCategory category : categories) {
      if (category.getStatus() != EntityStatus.Active) {
        continue;
      }
      JSONObject item = new JSONObject();
      item.put("id", category.getId());
      item.put("name", category.getName());
      item.put("code", category.getCode());
      item.put(
          "description",
          category.getDescription() != null ? category.getDescription() : "");
      item.put("status", category.getStatus().name());
      String imageUrl = categoryImageStore.resolveImageUrl(category.getCode());
      if (imageUrl != null) {
        item.put("imageUrl", imageUrl);
      }
      items.put(item);
    }

    JSONObject response = new JSONObject();
    response.put("items", items);
    writeJsonToResponse(response.toString());
  }

  @GetMapping("/api/public/products")
  public void getPublicProducts(@RequestParam(required = false) Long categoryId) {
    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      markNotFound();
      return;
    }

    AllProductsRequest request = new AllProductsRequest();
    request.setOrganization(organization);
    AllProducts result = allProductsImpl.get(request);

    JSONArray items = new JSONArray();
    List<Product> products = result.getItems() != null ? result.getItems() : new ArrayList<>();
    for (Product product : products) {
      if (product.getStatus() != ProductStatus.Active) {
        continue;
      }
      if (categoryId != null
          && (product.getCategory() == null
              || !categoryId.equals(product.getCategory().getId()))) {
        continue;
      }
      items.put(toPublicProductJson(product));
    }

    JSONObject response = new JSONObject();
    response.put("items", items);
    writeJsonToResponse(response.toString());
  }

  @GetMapping("/api/public/products/{id}")
  public void getPublicProductById(@PathVariable("id") Long id) {
    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      markNotFound();
      return;
    }

    Product product = productRepository.findById(id);
    if (product == null
        || product.getStatus() != ProductStatus.Active
        || product.getOrganization() == null
        || organization.getId() != product.getOrganization().getId()) {
      markNotFound();
      return;
    }

    writeJsonToResponse(toPublicProductJson(product).toString());
  }

  @GetMapping("/api/public/stores")
  public void getPublicStores() {
    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      markNotFound();
      return;
    }

    AllStoresRequest request = new AllStoresRequest();
    request.setOrganization(organization);
    AllStores result = allStoresImpl.get(request);

    JSONArray items = new JSONArray();
    List<Store> stores = result.getItems() != null ? result.getItems() : new ArrayList<>();
    for (Store store : stores) {
      if (store.getStatus() != EntityStatus.Active) {
        continue;
      }
      JSONObject item = new JSONObject();
      item.put("id", store.getId());
      item.put("name", store.getName() != null ? store.getName() : "");
      item.put("code", store.getCode() != null ? store.getCode() : "");
      item.put("storeType", store.getStoreType() != null ? store.getStoreType().name() : "");
      item.put("address", store.getAddress() != null ? store.getAddress() : "");
      item.put("phone", store.getPhone() != null ? store.getPhone() : "");
      item.put("email", store.getEmail() != null ? store.getEmail() : "");
      item.put("status", store.getStatus().name());
      items.put(item);
    }

    JSONObject response = new JSONObject();
    response.put("items", items);
    writeJsonToResponse(response.toString());
  }

  @GetMapping("/api/public/products/new-arrivals")
  public void getNewArrivalProducts(@RequestParam(required = false, defaultValue = "24") int limit) {
    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      markNotFound();
      return;
    }

    AllProductsRequest request = new AllProductsRequest();
    request.setOrganization(organization);
    AllProducts result = allProductsImpl.get(request);

    List<Product> products = new ArrayList<>();
    List<Product> allItems = result.getItems() != null ? result.getItems() : new ArrayList<>();
    for (Product product : allItems) {
      if (product.getStatus() == ProductStatus.Active) {
        products.add(product);
      }
    }

    products.sort(
        Comparator.comparing(Product::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()))
            .thenComparing(Product::getId, Comparator.reverseOrder()));

    int maxItems = Math.max(1, Math.min(limit, 48));
    JSONArray items = new JSONArray();
    for (int i = 0; i < products.size() && i < maxItems; i++) {
      items.put(toPublicProductJson(products.get(i)));
    }

    JSONObject response = new JSONObject();
    response.put("items", items);
    writeJsonToResponse(response.toString());
  }
}
