package rest;

import classes.VendorProductService;
import d3e.core.CurrentUser;
import d3e.core.RestControllerBase;
import java.util.List;
import java.util.Map;
import models.BaseUser;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductVendorController extends RestControllerBase {
  @Autowired private VendorProductService vendorProductService;

  private boolean isAuthorized() {
    BaseUser currentUser = CurrentUser.get();
    return currentUser instanceof User;
  }

  @GetMapping("/api/products-by-vendor")
  public void getProductsByVendor(
      @RequestParam("organizationId") Long organizationId,
      @RequestParam("vendorId") Long vendorId) {
    if (!isAuthorized()) {
      markForbidden();
      return;
    }
    if (organizationId == null
        || organizationId <= 0
        || vendorId == null
        || vendorId <= 0) {
      markBadRequest();
      return;
    }

    List<Map<String, Object>> products =
        vendorProductService.getProductsByVendor(organizationId, vendorId);
    if (products == null) {
      markNotFound();
      return;
    }

    JSONArray items = new JSONArray();
    for (Map<String, Object> product : products) {
      JSONObject item = new JSONObject(product);
      items.put(item);
    }

    JSONObject response = new JSONObject();
    response.put("items", items);
    writeJsonToResponse(response.toString());
  }

  @PutMapping("/api/vendor-products")
  public void assignProductsToVendor(@RequestBody String body) {
    if (!isAuthorized()) {
      markForbidden();
      return;
    }

    JSONObject input = new JSONObject(body);
    long organizationId = input.optLong("organizationId", 0L);
    long vendorId = input.optLong("vendorId", 0L);
    JSONArray productIdsJson = input.optJSONArray("productIds");
    List<Long> productIds = List.of();
    if (productIdsJson != null) {
      productIds =
          productIdsJson.toList().stream()
              .map(value -> Long.parseLong(String.valueOf(value)))
              .filter(id -> id > 0)
              .toList();
    }

    if (organizationId <= 0 || vendorId <= 0) {
      markBadRequest();
      return;
    }

    Map<String, Object> result =
        vendorProductService.assignProductsToVendor(organizationId, vendorId, productIds);
    JSONObject response = new JSONObject(result);
    writeJsonToResponse(response.toString());
  }
}
