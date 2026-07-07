package rest;

import classes.CustomerAccountService;
import d3e.core.RestControllerBase;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicAccountController extends RestControllerBase {
  @Autowired private CustomerAccountService customerAccountService;

  @GetMapping("/api/public/customer/orders")
  public void getCustomerOrders(@RequestParam("phone") String phone) {
    if (phone == null || phone.trim().isEmpty()) {
      markBadRequest();
      return;
    }

    List<Map<String, Object>> orders = customerAccountService.getOrdersByPhone(phone);
    writeOrderList(orders);
  }

  @GetMapping("/api/public/customer/orders-by-email")
  public void getCustomerOrdersByEmail(@RequestParam("email") String email) {
    if (email == null || email.trim().isEmpty()) {
      markBadRequest();
      return;
    }

    List<Map<String, Object>> orders = customerAccountService.getOrdersByEmail(email);
    writeOrderList(orders);
  }

  @GetMapping("/api/public/customer/profile")
  public void getCustomerProfile(@RequestParam("email") String email) {
    if (email == null || email.trim().isEmpty()) {
      markBadRequest();
      return;
    }

    Map<String, Object> profile = customerAccountService.getProfileByEmail(email);
    if (profile == null) {
      markNotFound();
      return;
    }

    writeJsonToResponse(new JSONObject(profile).toString());
  }

  @PostMapping("/api/public/customer/profile")
  public void updateCustomerProfile(@RequestBody String body) {
    JSONObject input = new JSONObject(body);
    String email = input.optString("email", "");
    String displayName = input.optString("displayName", "");
    String phone = input.optString("phone", "");
    if (email.trim().isEmpty()) {
      markBadRequest();
      return;
    }

    Map<String, Object> profile =
        customerAccountService.updateProfileByEmail(email, displayName, phone);
    if (profile == null) {
      markNotFound();
      return;
    }

    writeJsonToResponse(new JSONObject(profile).toString());
  }

  @GetMapping("/api/public/customer/orders/{id}")
  public void getCustomerOrderDetail(
      @PathVariable("id") long orderId, @RequestParam("phone") String phone) {
    if (phone == null || phone.trim().isEmpty() || orderId <= 0) {
      markBadRequest();
      return;
    }

    Map<String, Object> order = customerAccountService.getOrderDetail(orderId, phone);
    if (order == null) {
      markNotFound();
      return;
    }

    writeJsonToResponse(new JSONObject(order).toString());
  }

  @GetMapping("/api/public/customer/orders/{id}/by-email")
  public void getCustomerOrderDetailByEmail(
      @PathVariable("id") long orderId, @RequestParam("email") String email) {
    if (email == null || email.trim().isEmpty() || orderId <= 0) {
      markBadRequest();
      return;
    }

    Map<String, Object> order = customerAccountService.getOrderDetailForEmail(orderId, email);
    if (order == null) {
      markNotFound();
      return;
    }

    writeJsonToResponse(new JSONObject(order).toString());
  }

  private void writeOrderList(List<Map<String, Object>> orders) {
    JSONArray items = new JSONArray();
    for (Map<String, Object> order : orders) {
      items.put(new JSONObject(order));
    }

    JSONObject response = new JSONObject();
    response.put("items", items);
    writeJsonToResponse(response.toString());
  }
}
