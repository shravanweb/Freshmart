package classes;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import models.Product;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import models.Vendor;
import models.Warehouse;

final class PurchaseOrderPdfGenerator {
  private static final Color BRAND_ORANGE = new Color(232, 93, 4);
  private static final Color BRAND_DARK = new Color(45, 55, 72);
  private static final Color LIGHT_BG = new Color(248, 250, 252);
  private static final Color BORDER = new Color(226, 232, 240);
  private static final Color MUTED = new Color(100, 116, 139);

  private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BRAND_DARK);
  private static final Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 11, MUTED);
  private static final Font SECTION_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BRAND_DARK);
  private static final Font LABEL_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, MUTED);
  private static final Font VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, BRAND_DARK);
  private static final Font TABLE_HEADER_FONT =
      FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
  private static final Font TABLE_CELL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 9, BRAND_DARK);
  private static final Font FOOTER_FONT = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, MUTED);

  private PurchaseOrderPdfGenerator() {}

  static byte[] generate(
      PurchaseOrder purchaseOrder, Vendor vendor, List<PurchaseOrderLine> lines) {
    try {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      Document document = new Document(PageSize.LETTER, 40, 40, 48, 48);
      PdfWriter.getInstance(document, output);
      document.open();

      addHeader(document);
      addPoMetaTable(document, purchaseOrder);
      addPartyTables(document, vendor, purchaseOrder.getWarehouse());
      if (hasNotes(purchaseOrder)) {
        addNotesSection(document, purchaseOrder.getNotes().trim());
      }
      addLineItemsTable(document, lines);
      addFooter(document);

      document.close();
      return output.toByteArray();
    } catch (Exception exception) {
      throw new RuntimeException("Could not generate purchase order PDF.", exception);
    }
  }

  private static void addHeader(Document document) throws Exception {
    PdfPTable header = new PdfPTable(1);
    header.setWidthPercentage(100);
    header.setSpacingAfter(16f);

    PdfPCell brandBar = new PdfPCell(new Phrase("FreshMart Retail Group", TABLE_HEADER_FONT));
    brandBar.setBackgroundColor(BRAND_ORANGE);
    brandBar.setBorder(Rectangle.NO_BORDER);
    brandBar.setPaddingTop(10f);
    brandBar.setPaddingBottom(10f);
    brandBar.setPaddingLeft(14f);
    header.addCell(brandBar);

    PdfPCell titleCell = new PdfPCell();
    titleCell.setBorder(Rectangle.NO_BORDER);
    titleCell.setPaddingTop(14f);
    titleCell.setPaddingBottom(4f);
    titleCell.addElement(new Paragraph("PURCHASE ORDER", TITLE_FONT));
    titleCell.addElement(new Paragraph("Official procurement document", SUBTITLE_FONT));
    header.addCell(titleCell);

    document.add(header);
  }

  private static void addPoMetaTable(Document document, PurchaseOrder purchaseOrder)
      throws Exception {
    PdfPTable meta = new PdfPTable(4);
    meta.setWidthPercentage(100);
    meta.setWidths(new float[] {1.2f, 1.2f, 1.2f, 1.2f});
    meta.setSpacingAfter(18f);

    addMetaCell(meta, "PO Number", safe(purchaseOrder.getPoNumber()));
    addMetaCell(meta, "Order Date", formatDate(purchaseOrder.getOrderDate()));
    addMetaCell(
        meta,
        "Expected Delivery",
        purchaseOrder.getExpectedDeliveryDate() != null
            ? formatDate(purchaseOrder.getExpectedDeliveryDate())
            : "—");
    addMetaCell(meta, "Status", String.valueOf(purchaseOrder.getStatus()));

    document.add(meta);
  }

  private static void addPartyTables(Document document, Vendor vendor, Warehouse warehouse)
      throws Exception {
    PdfPTable parties = new PdfPTable(2);
    parties.setWidthPercentage(100);
    parties.setWidths(new float[] {1f, 1f});
    parties.setSpacingAfter(18f);

    parties.addCell(buildPartyCell("Supplier", buildSupplierLines(vendor)));
    parties.addCell(buildPartyCell("Deliver To", buildWarehouseLines(warehouse)));

    document.add(parties);
  }

  private static PdfPCell buildPartyCell(String title, Paragraph content) {
    PdfPCell cell = new PdfPCell();
    cell.setBackgroundColor(LIGHT_BG);
    cell.setBorderColor(BORDER);
    cell.setPadding(12f);
    cell.addElement(new Paragraph(title, SECTION_FONT));
    cell.addElement(content);
    return cell;
  }

  private static Paragraph buildSupplierLines(Vendor vendor) {
    Paragraph paragraph = new Paragraph();
    paragraph.setSpacingBefore(6f);
    paragraph.add(new Phrase(safe(vendor.getName()) + "\n", VALUE_FONT));
    if (vendor.getEmail() != null && !vendor.getEmail().trim().isEmpty()) {
      paragraph.add(new Phrase(vendor.getEmail().trim() + "\n", VALUE_FONT));
    }
    if (vendor.getPhone() != null && !vendor.getPhone().trim().isEmpty()) {
      paragraph.add(new Phrase(vendor.getPhone().trim(), VALUE_FONT));
    }
    return paragraph;
  }

  private static Paragraph buildWarehouseLines(Warehouse warehouse) {
    Paragraph paragraph = new Paragraph();
    paragraph.setSpacingBefore(6f);
    if (warehouse != null) {
      paragraph.add(new Phrase(safe(warehouse.getName()), VALUE_FONT));
    } else {
      paragraph.add(new Phrase("—", VALUE_FONT));
    }
    return paragraph;
  }

  private static void addNotesSection(Document document, String notes) throws Exception {
    PdfPTable notesTable = new PdfPTable(1);
    notesTable.setWidthPercentage(100);
    notesTable.setSpacingAfter(16f);

    PdfPCell cell = new PdfPCell();
    cell.setBackgroundColor(LIGHT_BG);
    cell.setBorderColor(BORDER);
    cell.setPadding(12f);
    cell.addElement(new Paragraph("Notes", SECTION_FONT));
    cell.addElement(new Paragraph(notes, VALUE_FONT));
    notesTable.addCell(cell);

    document.add(notesTable);
  }

  private static void addLineItemsTable(Document document, List<PurchaseOrderLine> lines)
      throws Exception {
    document.add(new Paragraph("Line Items", SECTION_FONT));

    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);
    table.setWidths(new float[] {0.5f, 3.2f, 0.8f, 1f, 1f});
    table.setSpacingBefore(8f);
    table.setSpacingAfter(12f);

    addHeaderCell(table, "#");
    addHeaderCell(table, "Product");
    addHeaderCell(table, "Qty");
    addHeaderCell(table, "Unit Price");
    addHeaderCell(table, "Line Total");

    double subtotal = 0.0d;
    boolean alternate = false;
    for (PurchaseOrderLine line : lines) {
      Product product = line.getProduct();
      String productLabel =
          product != null
              ? product.getSku() + " — " + product.getName()
              : "Line #" + line.getLineNumber();
      double lineTotal = resolveLineTotal(line);
      subtotal += lineTotal;
      Color rowBg = alternate ? LIGHT_BG : Color.WHITE;
      alternate = !alternate;

      addBodyCell(table, String.valueOf(line.getLineNumber()), rowBg, Element.ALIGN_CENTER);
      addBodyCell(table, productLabel, rowBg, Element.ALIGN_LEFT);
      addBodyCell(table, formatQty(line.getOrderedQuantity()), rowBg, Element.ALIGN_RIGHT);
      addBodyCell(table, formatMoney(line.getUnitPrice()), rowBg, Element.ALIGN_RIGHT);
      addBodyCell(table, formatMoney(lineTotal), rowBg, Element.ALIGN_RIGHT);
    }

    document.add(table);
    addTotalsTable(document, subtotal);
  }

  private static void addTotalsTable(Document document, double subtotal) throws Exception {
    PdfPTable totals = new PdfPTable(2);
    totals.setWidthPercentage(40);
    totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
    totals.setWidths(new float[] {1.4f, 1f});

    PdfPCell label = new PdfPCell(new Phrase("Subtotal", LABEL_FONT));
    label.setBorder(Rectangle.NO_BORDER);
    label.setHorizontalAlignment(Element.ALIGN_RIGHT);
    label.setPadding(6f);
    totals.addCell(label);

    PdfPCell value = new PdfPCell(new Phrase(formatMoney(subtotal), VALUE_FONT));
    value.setBorder(Rectangle.NO_BORDER);
    value.setHorizontalAlignment(Element.ALIGN_RIGHT);
    value.setPadding(6f);
    totals.addCell(value);

    document.add(totals);
  }

  private static void addFooter(Document document) throws Exception {
    Paragraph footer =
        new Paragraph(
            "Please confirm receipt of this purchase order at your earliest convenience.",
            FOOTER_FONT);
    footer.setSpacingBefore(24f);
    footer.setAlignment(Element.ALIGN_CENTER);
    document.add(footer);

    Paragraph signOff = new Paragraph("Regards,\nFreshMart Retail Group", VALUE_FONT);
    signOff.setSpacingBefore(18f);
    document.add(signOff);
  }

  private static void addMetaCell(PdfPTable table, String label, String value) {
    PdfPCell cell = new PdfPCell();
    cell.setBackgroundColor(LIGHT_BG);
    cell.setBorderColor(BORDER);
    cell.setPadding(10f);
    cell.addElement(new Paragraph(label, LABEL_FONT));
    cell.addElement(new Paragraph(value, VALUE_FONT));
    table.addCell(cell);
  }

  private static void addHeaderCell(PdfPTable table, String text) {
    PdfPCell cell = new PdfPCell(new Phrase(text, TABLE_HEADER_FONT));
    cell.setBackgroundColor(BRAND_DARK);
    cell.setBorderColor(BRAND_DARK);
    cell.setPadding(8f);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);
  }

  private static void addBodyCell(PdfPTable table, String text, Color bg, int alignment) {
    PdfPCell cell = new PdfPCell(new Phrase(text, TABLE_CELL_FONT));
    cell.setBackgroundColor(bg);
    cell.setBorderColor(BORDER);
    cell.setPadding(7f);
    cell.setHorizontalAlignment(alignment);
    table.addCell(cell);
  }

  private static double resolveLineTotal(PurchaseOrderLine line) {
    double storedTotal = line.getLineTotal();
    if (storedTotal > 0) {
      return storedTotal;
    }
    return line.getOrderedQuantity() * line.getUnitPrice();
  }

  private static boolean hasNotes(PurchaseOrder purchaseOrder) {
    return purchaseOrder.getNotes() != null && !purchaseOrder.getNotes().trim().isEmpty();
  }

  private static String formatDate(java.time.LocalDate date) {
    if (date == null) {
      return "—";
    }
    return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  private static String formatMoney(double amount) {
    return String.format("%.2f", amount);
  }

  private static String formatQty(double qty) {
    if (qty == Math.rint(qty)) {
      return String.format("%.0f", qty);
    }
    return String.format("%.2f", qty);
  }

  private static String safe(String value) {
    return value == null || value.trim().isEmpty() ? "—" : value.trim();
  }
}
