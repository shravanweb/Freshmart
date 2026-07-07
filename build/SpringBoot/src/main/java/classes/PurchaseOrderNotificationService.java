package classes;

import d3e.core.DFile;
import d3e.core.EmailService;
import d3e.core.ListExt;
import d3e.core.Log;
import java.io.IOException;
import jakarta.mail.MessagingException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.EmailMessage;
import models.Product;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import models.Vendor;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.PurchaseOrderLineRepository;
import repository.jpa.PurchaseOrderRepository;
import repository.jpa.VendorRepository;

@Service
public class PurchaseOrderNotificationService {
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private PurchaseOrderLineRepository purchaseOrderLineRepository;
  @Autowired private VendorRepository vendorRepository;
  @Autowired private EmailService emailService;
  @Autowired private PurchaseOrderDocumentService purchaseOrderDocumentService;

  public List<Map<String, Object>> getLinesForPurchaseOrder(long purchaseOrderId) {
    PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
    if (purchaseOrder == null) {
      return null;
    }

    List<PurchaseOrderLine> lines = purchaseOrderLineRepository.getByPurchaseOrder(purchaseOrder);
    List<Map<String, Object>> result = new ArrayList<>();
    for (PurchaseOrderLine line : lines) {
      Map<String, Object> item = new HashMap<>();
      item.put("id", line.getId());
      item.put("lineNumber", line.getLineNumber());
      item.put("orderedQuantity", line.getOrderedQuantity());
      item.put("unitPrice", line.getUnitPrice());
      Product product = line.getProduct();
      item.put("productId", product != null ? product.getId() : 0L);
      result.add(item);
    }
    result.sort(Comparator.comparingLong(row -> ((Number) row.get("lineNumber")).longValue()));
    return result;
  }

  public Map<String, Object> notifySupplier(long purchaseOrderId) {
    Map<String, Object> result = new HashMap<>();
    List<String> errors = new ArrayList<>();

    PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
    if (purchaseOrder == null) {
      errors.add("Purchase order not found.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    Vendor vendor = resolveVendor(purchaseOrder);
    if (vendor == null) {
      errors.add("Supplier not found on this purchase order.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    String supplierEmail = vendor.getEmail();
    if (supplierEmail == null || supplierEmail.trim().isEmpty()) {
      errors.add(
          "Supplier \""
              + vendor.getName()
              + "\" has no email address. Add an email on the supplier profile and try again.");
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }

    List<PurchaseOrderLine> lines = purchaseOrderLineRepository.getByPurchaseOrder(purchaseOrder);
    String subject = buildSubject(purchaseOrder, vendor);
    String body = buildBody(purchaseOrder, vendor, lines);

    try {
      Log.info(
          "Sending purchase order email for "
              + purchaseOrder.getPoNumber()
              + " to "
              + supplierEmail.trim());
      DFile attachment =
          purchaseOrderDocumentService.generatePurchaseOrderDocument(
              purchaseOrder, vendor, lines);

      EmailMessage message = new EmailMessage();
      message.setTo(ListExt.asList(supplierEmail.trim()));
      message.setSubject(subject);
      message.setBody(body);
      message.addToAttachments(attachment, -1);

      emailService.sendNow(message);

      String successMessage =
          "Purchase order "
              + purchaseOrder.getPoNumber()
              + " emailed to "
              + supplierEmail.trim()
              + " with generated PDF attachment.";
      result.put("success", true);
      result.put("message", successMessage);
      result.put("errors", errors);
      return result;
    } catch (MessagingException | IOException exception) {
      Log.printStackTrace(exception);
      errors.add("Failed to send email: " + exception.getMessage());
      result.put("success", false);
      result.put("errors", errors);
      return result;
    } catch (Exception exception) {
      Log.printStackTrace(exception);
      errors.add("Failed to send email: " + exception.getMessage());
      result.put("success", false);
      result.put("errors", errors);
      return result;
    }
  }

  private Vendor resolveVendor(PurchaseOrder purchaseOrder) {
    Vendor vendor = purchaseOrder.getVendor();
    if (vendor == null) {
      return null;
    }
    long vendorId = vendor.getId();
    if (vendorId > 0) {
      Vendor loaded = vendorRepository.findById(vendorId);
      if (loaded != null) {
        return loaded;
      }
    }
    return vendor;
  }

  private String buildSubject(PurchaseOrder purchaseOrder, Vendor vendor) {
    return "Purchase Order "
        + purchaseOrder.getPoNumber()
        + " — "
        + vendor.getName()
        + " (FreshMart)";
  }

  private String buildBody(
      PurchaseOrder purchaseOrder, Vendor vendor, List<PurchaseOrderLine> lines) {
    StringBuilder body = new StringBuilder();
    body.append("Dear ").append(vendor.getName()).append(",\n\n");
    body.append(
        "Please find the attached purchase order document for your review and confirmation.\n\n");
    body.append("PO Number: ").append(purchaseOrder.getPoNumber()).append("\n");
    body.append("Order Date: ").append(formatDate(purchaseOrder.getOrderDate())).append("\n");
    if (purchaseOrder.getExpectedDeliveryDate() != null) {
      body.append("Expected Delivery: ")
          .append(formatDate(purchaseOrder.getExpectedDeliveryDate()))
          .append("\n");
    }
    Warehouse warehouse = purchaseOrder.getWarehouse();
    if (warehouse != null) {
      body.append("Deliver To Warehouse: ").append(warehouse.getName()).append("\n");
    }
    body.append("Status: ").append(purchaseOrder.getStatus()).append("\n");
    body.append("Line Items: ").append(lines.size()).append("\n\n");

    if (purchaseOrder.getNotes() != null && !purchaseOrder.getNotes().trim().isEmpty()) {
      body.append("Notes:\n").append(purchaseOrder.getNotes().trim()).append("\n\n");
    }

    body.append("Please confirm receipt of this order.\n\n");
    body.append("Regards,\nFreshMart Retail Group\n");
    return body.toString();
  }

  private String formatDate(java.time.LocalDate date) {
    if (date == null) {
      return "—";
    }
    return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }
}
