package rest;

import classes.PurchaseOrderNotificationService;
import d3e.core.RestControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrderController extends RestControllerBase {
  @Autowired private PurchaseOrderNotificationService purchaseOrderNotificationService;

  @GetMapping("/api/purchase-order/{id}/lines")
  public List<Map<String, Object>> getLines(@PathVariable("id") long purchaseOrderId) {
    List<Map<String, Object>> lines =
        purchaseOrderNotificationService.getLinesForPurchaseOrder(purchaseOrderId);
    if (lines == null) {
      markNotFound();
      return List.of();
    }
    return lines;
  }

  @PostMapping("/api/purchase-order/{id}/notify-supplier")
  public Map<String, Object> notifySupplier(@PathVariable("id") long purchaseOrderId) {
    return purchaseOrderNotificationService.notifySupplier(purchaseOrderId);
  }
}
