package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.InventoryMovement;
import models.Organization;
import models.Product;
import models.SalesOrder;
import models.SalesOrderLine;
import models.Store;
import models.Warehouse;
import models.WarehouseStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.InventoryMovementRepository;
import repository.jpa.OrganizationRepository;
import repository.jpa.ProductRepository;
import repository.jpa.SalesOrderRepository;
import repository.jpa.StoreRepository;
import repository.jpa.WarehouseRepository;
import repository.jpa.WarehouseStockRepository;
import store.Database;

@Service
public class CustomerCheckoutService {
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private StoreRepository storeRepository;
  @Autowired private WarehouseRepository warehouseRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private WarehouseStockRepository warehouseStockRepository;
  @Autowired private SalesOrderRepository salesOrderRepository;
  @Autowired private InventoryMovementRepository inventoryMovementRepository;

  private volatile boolean stockBackfillDone = false;

  private Organization resolveFreshMartOrganization() {
    Organization organization = organizationRepository.getByCode("FMRG");
    if (organization == null) {
      organization = organizationRepository.getByName("FreshMart Retail Group");
    }
    return organization;
  }

  private WarehouseStock findWarehouseStock(Warehouse warehouse, Product product) {
    for (WarehouseStock stock : warehouseStockRepository.getByWarehouse(warehouse)) {
      if (stock.getProduct() != null && stock.getProduct().getId() == product.getId()) {
        return stock;
      }
    }
    return null;
  }

  private double getAvailableQuantity(Warehouse warehouse, Product product) {
    WarehouseStock stock = findWarehouseStock(warehouse, product);
    return stock != null ? stock.getQuantityAvailable() : 0.0d;
  }

  private void issueStock(
      Warehouse warehouse, Product product, double quantity, SalesOrder order, int lineIndex) {
    WarehouseStock stock = findWarehouseStock(warehouse, product);
    if (stock == null) {
      throw new RuntimeException("Stock record not found for product " + product.getId());
    }

    double newOnHand = stock.getQuantityOnHand() - quantity;
    if (newOnHand < 0) {
      throw new RuntimeException("Insufficient stock for product " + product.getId());
    }

    stock.setQuantityOnHand(newOnHand);
    WarehouseStockService.updateWarehouseStock(stock);

    InventoryMovement movement = new InventoryMovement();
    movement.setMovementNumber(order.getOrderNumber() + "-L" + lineIndex);
    movement.setWarehouse(warehouse);
    movement.setProduct(product);
    movement.setMovementType(MovementType.SalesIssue);
    movement.setQuantity(quantity);
    movement.setDirection(MovementDirection.Out);
    movement.setReferenceType(MovementReferenceType.SalesOrder);
    movement.setReferenceId(String.valueOf(order.getId()));
    movement.setBalanceAfter(newOnHand);
    movement.setMovementDate(LocalDateTime.now());
    movement.setOrganization(order.getOrganization());
    movement.setNotes("Customer order " + order.getOrderNumber());
    InventoryMovementService.createInventoryMovement(movement);
  }

  private boolean tryIssueStock(
      Warehouse warehouse, Product product, double quantity, SalesOrder order, int lineIndex) {
    try {
      issueStock(warehouse, product, quantity, order, lineIndex);
      return true;
    } catch (RuntimeException ex) {
      return false;
    }
  }

  private Store resolveDefaultStore(Organization organization) {
    List<Store> stores = storeRepository.getByOrganization(organization);
    for (Store store : stores) {
      if (store.getStatus() == EntityStatus.Active) {
        return store;
      }
    }
    return stores.isEmpty() ? null : stores.get(0);
  }

  private Warehouse pickWarehouse(List<Warehouse> warehouses) {
    if (warehouses == null || warehouses.isEmpty()) {
      return null;
    }
    for (Warehouse warehouse : warehouses) {
      if (warehouse.getStatus() == EntityStatus.Active && warehouse.isIsDefault()) {
        return warehouse;
      }
    }
    for (Warehouse warehouse : warehouses) {
      if (warehouse.getStatus() == EntityStatus.Active) {
        return warehouse;
      }
    }
    return warehouses.get(0);
  }

  private Warehouse resolveWarehouse(Organization organization, Store store) {
    if (store.getDefaultWarehouse() != null) {
      return store.getDefaultWarehouse();
    }

    Warehouse byStore = pickWarehouse(warehouseRepository.getByStore(store));
    if (byStore != null) {
      return byStore;
    }

    return pickWarehouse(warehouseRepository.getByOrganization(organization));
  }

  private String buildOrderNumber() {
    String suffix = String.valueOf(System.currentTimeMillis() % 1000000L);
    while (suffix.length() < 6) {
      suffix = "0" + suffix;
    }
    return "FM-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + suffix;
  }

  public Map<String, Object> placeOrder(
      String paymentMethod,
      String customerName,
      String customerPhone,
      String deliveryCity,
      String deliveryAddress,
      List<Map<String, Object>> items,
      double deliveryFee) {
    backfillMissingStockDeductions();

    Map<String, Object> result = new HashMap<>();
    List<String> errors = new ArrayList<>();

    if (!"cod".equalsIgnoreCase(paymentMethod)) {
      errors.add("Online payment is not available yet. Please choose Cash on Delivery.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    if (customerName == null || customerName.trim().isEmpty()) {
      errors.add("Customer name is required.");
    }
    if (customerPhone == null || customerPhone.trim().isEmpty()) {
      errors.add("Phone number is required.");
    }
    if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
      errors.add("Delivery address is required.");
    }
    if (items == null || items.isEmpty()) {
      errors.add("Cart is empty.");
    }

    if (!errors.isEmpty()) {
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      errors.add("Store is not configured.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    Store store = resolveDefaultStore(organization);
    if (store == null) {
      errors.add("No active store found.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    Warehouse warehouse = resolveWarehouse(organization, store);
    if (warehouse == null) {
      errors.add("No warehouse configured for delivery.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    double subtotal = 0.0d;
    List<SalesOrderLine> lines = new ArrayList<>();

    for (Map<String, Object> item : items) {
      long productId = Long.parseLong(String.valueOf(item.get("productId")));
      double quantity = Double.parseDouble(String.valueOf(item.get("quantity")));
      double unitPrice = Double.parseDouble(String.valueOf(item.get("unitPrice")));

      if (productId <= 0 || quantity <= 0) {
        errors.add("Invalid cart item.");
        continue;
      }

      Product product = productRepository.findById(productId);
      if (product == null || product.getStatus() != ProductStatus.Active) {
        errors.add("A product in your cart is no longer available.");
        continue;
      }

      double available = getAvailableQuantity(warehouse, product);
      if (quantity > available) {
        String name = product.getName() != null ? product.getName() : "Item";
        errors.add(name + " has only " + (int) available + " in stock.");
        continue;
      }

      double lineTotal = quantity * unitPrice;
      subtotal += lineTotal;

      SalesOrderLine line = new SalesOrderLine();
      line.setProduct(product);
      line.setQuantity(quantity);
      line.setUnitPrice(unitPrice);
      line.setDiscount(0.0d);
      line.setLineTotal(lineTotal);
      lines.add(line);
    }

    if (!errors.isEmpty()) {
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    double fee = Math.max(0.0d, deliveryFee);
    double totalAmount = subtotal + fee;

    String deliveryLabel = deliveryCity != null && !deliveryCity.trim().isEmpty()
        ? deliveryCity.trim()
        : "Delivery";
    String formattedCustomerName =
        customerName.trim()
            + " | "
            + deliveryLabel
            + ": "
            + deliveryAddress.trim();

    SalesOrder order = new SalesOrder();
    order.setOrganization(organization);
    order.setStore(store);
    order.setWarehouse(warehouse);
    order.setOrderNumber(buildOrderNumber());
    order.setOrderDate(LocalDateTime.now());
    order.setCustomerName(formattedCustomerName);
    order.setCustomerPhone(customerPhone.trim());
    order.setStatus(SalesOrderStatus.Confirmed);
    order.setSubtotal(subtotal);
    order.setDiscountAmount(0.0d);
    order.setTaxAmount(0.0d);
    order.setTotalAmount(totalAmount);
    order.setPaymentStatus(PaymentStatus.Unpaid);

    Database.get().save(order);

    int lineIndex = 1;
    for (SalesOrderLine line : lines) {
      line.setSalesOrder(order);
      Database.get().save(line);
      issueStock(warehouse, line.getProduct(), line.getQuantity(), order, lineIndex);
      lineIndex++;
    }

    result.put("success", true);
    result.put("errors", errors);
    result.put("orderId", order.getId());
    result.put("orderNumber", order.getOrderNumber());
    result.put("paymentMethod", "cod");
    result.put("totalAmount", totalAmount);
    result.put("deliveryFee", fee);
    return result;
  }

  private boolean hasStockMovement(SalesOrder order, Product product) {
    return inventoryMovementRepository.existsSalesIssueForOrder(order, product);
  }

  public void backfillMissingStockDeductions() {
    backfillMissingStockDeductions(false);
  }

  public void backfillMissingStockDeductions(boolean force) {
    if (stockBackfillDone && !force) {
      return;
    }

    Organization organization = resolveFreshMartOrganization();
    if (organization == null) {
      return;
    }

    List<SalesOrder> orders = new ArrayList<>(salesOrderRepository.getByOrganization(organization));
    orders.sort(
        Comparator.comparing(
            (SalesOrder order) ->
                order.getOrderDate() != null ? order.getOrderDate() : LocalDateTime.MIN));

    for (SalesOrder order : orders) {
      if (order.getStatus() != SalesOrderStatus.Confirmed) {
        continue;
      }
      if (order.getOrderNumber() == null || !order.getOrderNumber().startsWith("FM-")) {
        continue;
      }
      if (order.getWarehouse() == null) {
        continue;
      }

      int lineIndex = 1;
      for (SalesOrderLine line : order.getLines()) {
        if (line.getProduct() == null || line.getQuantity() <= 0) {
          lineIndex++;
          continue;
        }
        if (!hasStockMovement(order, line.getProduct())) {
          tryIssueStock(
              order.getWarehouse(), line.getProduct(), line.getQuantity(), order, lineIndex);
        }
        lineIndex++;
      }
    }

    stockBackfillDone = true;
  }
}
