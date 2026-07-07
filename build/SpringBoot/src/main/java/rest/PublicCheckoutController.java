package rest;

import classes.CustomerCheckoutService;
import d3e.core.RestControllerBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicCheckoutController extends RestControllerBase {
  @Autowired private CustomerCheckoutService customerCheckoutService;

  @PostMapping("/api/public/checkout")
  public void checkout(@RequestBody String body) {
    JSONObject input = new JSONObject(body);
    String paymentMethod = input.optString("paymentMethod", "cod");
    String customerName = input.optString("customerName", "");
    String customerPhone = input.optString("customerPhone", "");
    String deliveryCity = input.optString("deliveryCity", "");
    String deliveryAddress = input.optString("deliveryAddress", "");
    double deliveryFee = input.optDouble("deliveryFee", 0.0d);

    JSONArray itemsJson = input.optJSONArray("items");
    List<Map<String, Object>> items = new ArrayList<>();
    if (itemsJson != null) {
      for (int i = 0; i < itemsJson.length(); i++) {
        JSONObject item = itemsJson.getJSONObject(i);
        Map<String, Object> row = new HashMap<>();
        row.put("productId", item.optLong("productId", 0L));
        row.put("quantity", item.optDouble("quantity", 0.0d));
        row.put("unitPrice", item.optDouble("unitPrice", 0.0d));
        items.add(row);
      }
    }

    Map<String, Object> result =
        customerCheckoutService.placeOrder(
            paymentMethod,
            customerName,
            customerPhone,
            deliveryCity,
            deliveryAddress,
            items,
            deliveryFee);

    JSONObject response = new JSONObject(result);
    writeJsonToResponse(response.toString());
  }

  @PostMapping("/api/public/maintenance/backfill-stock")
  public void backfillStock() {
    customerCheckoutService.backfillMissingStockDeductions(true);
    writeJsonToResponse("{\"success\":true}");
  }
}
