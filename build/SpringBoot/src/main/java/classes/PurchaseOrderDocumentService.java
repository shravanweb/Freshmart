package classes;

import d3e.core.DFile;
import d3e.core.D3ETempResourceHandler;
import java.io.ByteArrayInputStream;
import java.util.List;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import models.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderDocumentService {
  @Autowired private D3ETempResourceHandler tempResourceHandler;

  public DFile generatePurchaseOrderDocument(
      PurchaseOrder purchaseOrder, Vendor vendor, List<PurchaseOrderLine> lines) {
    try {
      byte[] pdfBytes = PurchaseOrderPdfGenerator.generate(purchaseOrder, vendor, lines);
      String fileName = safeFileName(purchaseOrder.getPoNumber()) + ".pdf";
      return tempResourceHandler.save(fileName, new ByteArrayInputStream(pdfBytes));
    } catch (Exception exception) {
      throw new RuntimeException("Could not generate purchase order document.", exception);
    }
  }

  private String safeFileName(String poNumber) {
    if (poNumber == null || poNumber.trim().isEmpty()) {
      return "purchase-order";
    }
    return poNumber.trim().replaceAll("[^A-Za-z0-9._-]+", "_");
  }
}
