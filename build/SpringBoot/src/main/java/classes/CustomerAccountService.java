package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import models.Organization;
import models.Product;
import models.SalesOrder;
import models.SalesOrderLine;
import models.User;
import models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OrganizationRepository;
import repository.jpa.SalesOrderLineRepository;
import repository.jpa.SalesOrderRepository;
import repository.jpa.UserProfileRepository;
import repository.jpa.UserRepository;
import store.Database;

@Service
public class CustomerAccountService {
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private SalesOrderRepository salesOrderRepository;
  @Autowired private SalesOrderLineRepository salesOrderLineRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private UserProfileRepository userProfileRepository;

  private Organization resolveFreshMartOrganization() {
    Organization organization = organizationRepository.getByCode("FMRG");
    if (organization == null) {
      organization = organizationRepository.getByName("FreshMart Retail Group");
    }
    return organization;
  }

  public Map<String, Object> getProfileByEmail(String email) {
    Map<String, Object> result = new HashMap<>();
    UserProfile profile = resolveProfileByEmail(email);
    if (profile == null) {
      return null;
    }
    result.put("displayName", profile.getDisplayName() != null ? profile.getDisplayName() : "");
    result.put("phone", profile.getPhone() != null ? profile.getPhone() : "");
    result.put("email", email != null ? email.trim().toLowerCase() : "");
    return result;
  }

  public Map<String, Object> updateProfileByEmail(
      String email, String displayName, String phone) {
    UserProfile profile = resolveProfileByEmail(email);
    if (profile == null) {
      return null;
    }
    if (displayName != null && !displayName.trim().isEmpty()) {
      profile.setDisplayName(displayName.trim());
    }
    if (phone != null) {
      profile.setPhone(phone.trim());
    }
    Database.get().save(profile);
    return getProfileByEmail(email);
  }

  public List<Map<String, Object>> getOrdersByEmail(String email) {
    UserProfile profile = resolveProfileByEmail(email);
    if (profile == null) {
      return new ArrayList<>();
    }

    String displayName = profile.getDisplayName() != null ? profile.getDisplayName().trim() : "";
    if (displayName.isEmpty()) {
      return new ArrayList<>();
    }

    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      return new ArrayList<>();
    }

    Map<Long, Map<String, Object>> byId = new LinkedHashMap<>();
    for (SalesOrder order :
        salesOrderRepository.getByCustomerDisplayName(organization, displayName)) {
      byId.put(order.getId(), toOrderSummary(order));
    }

    String profilePhone = profile.getPhone() != null ? profile.getPhone().trim() : "";
    if (!profilePhone.isEmpty()) {
      for (SalesOrder order :
          salesOrderRepository.getByCustomerPhone(organization, profilePhone)) {
        byId.putIfAbsent(order.getId(), toOrderSummary(order));
      }
    }

    return new ArrayList<>(byId.values());
  }

  public Map<String, Object> getOrderDetailForEmail(long orderId, String email) {
    if (email == null || email.trim().isEmpty() || orderId <= 0) {
      return null;
    }

    UserProfile profile = resolveProfileByEmail(email);
    if (profile == null) {
      return null;
    }

    SalesOrder order = salesOrderRepository.findById(orderId);
    if (order == null || !orderBelongsToProfile(order, profile)) {
      return null;
    }

    return toOrderDetail(order);
  }

  public List<Map<String, Object>> getOrdersByPhone(String phone) {
    List<Map<String, Object>> items = new ArrayList<>();
    if (phone == null || phone.trim().isEmpty()) {
      return items;
    }

    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      return items;
    }

    List<SalesOrder> orders =
        salesOrderRepository.getByCustomerPhone(organization, phone.trim());
    for (SalesOrder order : orders) {
      items.add(toOrderSummary(order));
    }
    return items;
  }

  public Map<String, Object> getOrderDetail(long orderId, String phone) {
    if (phone == null || phone.trim().isEmpty() || orderId <= 0) {
      return null;
    }

    SalesOrder order = salesOrderRepository.findById(orderId);
    if (order == null) {
      return null;
    }

    String orderPhone = order.getCustomerPhone() != null ? order.getCustomerPhone().trim() : "";
    if (!orderPhone.equals(phone.trim())) {
      return null;
    }

    return toOrderDetail(order);
  }

  private UserProfile resolveProfileByEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return null;
    }
    User user = userRepository.getByEmail(email.trim().toLowerCase());
    if (user == null) {
      return null;
    }
    return userProfileRepository.getByUser(user);
  }

  private boolean orderBelongsToProfile(SalesOrder order, UserProfile profile) {
    String displayName = profile.getDisplayName() != null ? profile.getDisplayName().trim() : "";
    String customerName = order.getCustomerName() != null ? order.getCustomerName().trim() : "";
    if (!displayName.isEmpty()
        && (customerName.equals(displayName) || customerName.startsWith(displayName + " |"))) {
      return true;
    }

    String profilePhone = profile.getPhone() != null ? profile.getPhone().trim() : "";
    String orderPhone = order.getCustomerPhone() != null ? order.getCustomerPhone().trim() : "";
    return !profilePhone.isEmpty() && profilePhone.equals(orderPhone);
  }

  private Map<String, Object> toOrderSummary(SalesOrder order) {
    Map<String, Object> item = new HashMap<>();
    item.put("id", order.getId());
    item.put("orderNumber", order.getOrderNumber() != null ? order.getOrderNumber() : "");
    item.put("orderDate", order.getOrderDate() != null ? order.getOrderDate().toString() : "");
    item.put("status", order.getStatus() != null ? order.getStatus().name() : "");
    item.put(
        "paymentStatus",
        order.getPaymentStatus() != null ? order.getPaymentStatus().name() : "");
    item.put("totalAmount", order.getTotalAmount());
    item.put("customerName", order.getCustomerName() != null ? order.getCustomerName() : "");
    return item;
  }

  private Map<String, Object> toOrderDetail(SalesOrder order) {
    Map<String, Object> result = new HashMap<>();
    String orderPhone = order.getCustomerPhone() != null ? order.getCustomerPhone().trim() : "";
    result.put("id", order.getId());
    result.put("orderNumber", order.getOrderNumber() != null ? order.getOrderNumber() : "");
    result.put("orderDate", order.getOrderDate() != null ? order.getOrderDate().toString() : "");
    result.put("status", order.getStatus() != null ? order.getStatus().name() : "");
    result.put(
        "paymentStatus",
        order.getPaymentStatus() != null ? order.getPaymentStatus().name() : "");
    result.put("subtotal", order.getSubtotal());
    result.put("totalAmount", order.getTotalAmount());
    result.put("customerName", order.getCustomerName() != null ? order.getCustomerName() : "");
    result.put("customerPhone", orderPhone);

    List<Map<String, Object>> lines = new ArrayList<>();
    for (SalesOrderLine line : salesOrderLineRepository.getBySalesOrder(order)) {
      Map<String, Object> row = new HashMap<>();
      Product product = line.getProduct();
      row.put("productId", product != null ? product.getId() : 0L);
      row.put("productName", product != null && product.getName() != null ? product.getName() : "");
      row.put("quantity", line.getQuantity());
      row.put("unitPrice", line.getUnitPrice());
      row.put("lineTotal", line.getLineTotal());
      lines.add(row);
    }
    result.put("lines", lines);
    return result;
  }
}
