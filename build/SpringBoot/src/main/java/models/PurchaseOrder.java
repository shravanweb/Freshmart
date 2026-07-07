package models;

import classes.PurchaseOrderStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "PurchaseOrder")
@Entity
public class PurchaseOrder extends CreatableObject {
  public static final int _PONUMBER = 0;
  public static final int _VENDOR = 1;
  public static final int _WAREHOUSE = 2;
  public static final int _ORDERDATE = 3;
  public static final int _EXPECTEDDELIVERYDATE = 4;
  public static final int _STATUS = 5;
  public static final int _SUBTOTAL = 6;
  public static final int _TAXAMOUNT = 7;
  public static final int _TOTALAMOUNT = 8;
  public static final int _NOTES = 9;
  public static final int _CREATEDBY = 10;
  public static final int _APPROVEDBY = 11;
  public static final int _APPROVEDAT = 12;
  public static final int _CREATEDAT = 13;
  public static final int _LINES = 14;
  public static final int _ORGANIZATION = 15;
  @NotNull private String poNumber;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Vendor vendor;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull private LocalDate orderDate;
  private LocalDate expectedDeliveryDate;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private PurchaseOrderStatus status = PurchaseOrderStatus.Draft;

  private double subtotal = 0.0d;
  private double taxAmount = 0.0d;
  private double totalAmount = 0.0d;
  private String notes;

  @ManyToOne(fetch = FetchType.LAZY)
  private User createdBy;

  @ManyToOne(fetch = FetchType.LAZY)
  private User approvedBy;

  private LocalDateTime approvedAt;
  private LocalDateTime createdAt;

  @ManyToMany(mappedBy = "purchaseOrder", cascade = CascadeType.REMOVE)
  private List<PurchaseOrderLine> lines = D3EPersistanceList.inverse(this, _LINES);

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient PurchaseOrder old;

  public PurchaseOrder() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.PurchaseOrder;
  }

  @Override
  public String _type() {
    return "PurchaseOrder";
  }

  @Override
  public int _fieldsCount() {
    return 16;
  }

  public void addToLines(PurchaseOrderLine val, long index) {
    if (index == -1) {
      this.lines.add(val);
    } else {
      this.lines.add(((int) index), val);
    }
  }

  public void removeFromLines(PurchaseOrderLine val) {
    this.lines.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getPoNumber() {
    _checkProxy();
    return this.poNumber;
  }

  public void setPoNumber(String poNumber) {
    _checkProxy();
    if (Objects.equals(this.poNumber, poNumber)) {
      return;
    }
    fieldChanged(_PONUMBER, this.poNumber, poNumber);
    this.poNumber = poNumber;
  }

  public Vendor getVendor() {
    _checkProxy();
    return this.vendor;
  }

  public void setVendor(Vendor vendor) {
    _checkProxy();
    if (Objects.equals(this.vendor, vendor)) {
      return;
    }
    fieldChanged(_VENDOR, this.vendor, vendor);
    this.vendor = vendor;
  }

  public Warehouse getWarehouse() {
    _checkProxy();
    return this.warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    _checkProxy();
    if (Objects.equals(this.warehouse, warehouse)) {
      return;
    }
    fieldChanged(_WAREHOUSE, this.warehouse, warehouse);
    this.warehouse = warehouse;
  }

  public LocalDate getOrderDate() {
    _checkProxy();
    return this.orderDate;
  }

  public void setOrderDate(LocalDate orderDate) {
    _checkProxy();
    if (Objects.equals(this.orderDate, orderDate)) {
      return;
    }
    fieldChanged(_ORDERDATE, this.orderDate, orderDate);
    this.orderDate = orderDate;
  }

  public LocalDate getExpectedDeliveryDate() {
    _checkProxy();
    return this.expectedDeliveryDate;
  }

  public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
    _checkProxy();
    if (Objects.equals(this.expectedDeliveryDate, expectedDeliveryDate)) {
      return;
    }
    fieldChanged(_EXPECTEDDELIVERYDATE, this.expectedDeliveryDate, expectedDeliveryDate);
    this.expectedDeliveryDate = expectedDeliveryDate;
  }

  public PurchaseOrderStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(PurchaseOrderStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
  }

  public double getSubtotal() {
    _checkProxy();
    return this.subtotal;
  }

  public void setSubtotal(double subtotal) {
    _checkProxy();
    if (Objects.equals(this.subtotal, subtotal)) {
      return;
    }
    fieldChanged(_SUBTOTAL, this.subtotal, subtotal);
    this.subtotal = subtotal;
  }

  public double getTaxAmount() {
    _checkProxy();
    return this.taxAmount;
  }

  public void setTaxAmount(double taxAmount) {
    _checkProxy();
    if (Objects.equals(this.taxAmount, taxAmount)) {
      return;
    }
    fieldChanged(_TAXAMOUNT, this.taxAmount, taxAmount);
    this.taxAmount = taxAmount;
  }

  public double getTotalAmount() {
    _checkProxy();
    return this.totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    _checkProxy();
    if (Objects.equals(this.totalAmount, totalAmount)) {
      return;
    }
    fieldChanged(_TOTALAMOUNT, this.totalAmount, totalAmount);
    this.totalAmount = totalAmount;
  }

  public String getNotes() {
    _checkProxy();
    return this.notes;
  }

  public void setNotes(String notes) {
    _checkProxy();
    if (Objects.equals(this.notes, notes)) {
      return;
    }
    fieldChanged(_NOTES, this.notes, notes);
    this.notes = notes;
  }

  public User getCreatedBy() {
    _checkProxy();
    return this.createdBy;
  }

  public void setCreatedBy(User createdBy) {
    _checkProxy();
    if (Objects.equals(this.createdBy, createdBy)) {
      return;
    }
    fieldChanged(_CREATEDBY, this.createdBy, createdBy);
    this.createdBy = createdBy;
  }

  public User getApprovedBy() {
    _checkProxy();
    return this.approvedBy;
  }

  public void setApprovedBy(User approvedBy) {
    _checkProxy();
    if (Objects.equals(this.approvedBy, approvedBy)) {
      return;
    }
    fieldChanged(_APPROVEDBY, this.approvedBy, approvedBy);
    this.approvedBy = approvedBy;
  }

  public LocalDateTime getApprovedAt() {
    _checkProxy();
    return this.approvedAt;
  }

  public void setApprovedAt(LocalDateTime approvedAt) {
    _checkProxy();
    if (Objects.equals(this.approvedAt, approvedAt)) {
      return;
    }
    fieldChanged(_APPROVEDAT, this.approvedAt, approvedAt);
    this.approvedAt = approvedAt;
  }

  public LocalDateTime getCreatedAt() {
    _checkProxy();
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    _checkProxy();
    if (Objects.equals(this.createdAt, createdAt)) {
      return;
    }
    fieldChanged(_CREATEDAT, this.createdAt, createdAt);
    this.createdAt = createdAt;
  }

  public List<PurchaseOrderLine> getLines() {
    return this.lines;
  }

  public void setLines(List<PurchaseOrderLine> lines) {
    if (Objects.equals(this.lines, lines)) {
      return;
    }
    ((D3EPersistanceList<PurchaseOrderLine>) this.lines).setAll(lines);
  }

  public Organization getOrganization() {
    _checkProxy();
    return this.organization;
  }

  public void setOrganization(Organization organization) {
    _checkProxy();
    if (Objects.equals(this.organization, organization)) {
      return;
    }
    fieldChanged(_ORGANIZATION, this.organization, organization);
    this.organization = organization;
  }

  public PurchaseOrder getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((PurchaseOrder) old);
  }

  public String displayName() {
    return this.getPoNumber();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof PurchaseOrder && super.equals(a);
  }

  public PurchaseOrder deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    PurchaseOrder _obj = ((PurchaseOrder) dbObj);
    _obj.setPoNumber(poNumber);
    _obj.setVendor(vendor);
    _obj.setWarehouse(warehouse);
    _obj.setOrderDate(orderDate);
    _obj.setExpectedDeliveryDate(expectedDeliveryDate);
    _obj.setStatus(status);
    _obj.setSubtotal(subtotal);
    _obj.setTaxAmount(taxAmount);
    _obj.setTotalAmount(totalAmount);
    _obj.setNotes(notes);
    _obj.setCreatedBy(createdBy);
    _obj.setApprovedBy(approvedBy);
    _obj.setApprovedAt(approvedAt);
    _obj.setCreatedAt(createdAt);
    _obj.setLines(lines);
    _obj.setOrganization(organization);
  }

  public PurchaseOrder cloneInstance(PurchaseOrder cloneObj) {
    if (cloneObj == null) {
      cloneObj = new PurchaseOrder();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setPoNumber(this.getPoNumber());
    cloneObj.setVendor(this.getVendor());
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setOrderDate(this.getOrderDate());
    cloneObj.setExpectedDeliveryDate(this.getExpectedDeliveryDate());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setSubtotal(this.getSubtotal());
    cloneObj.setTaxAmount(this.getTaxAmount());
    cloneObj.setTotalAmount(this.getTotalAmount());
    cloneObj.setNotes(this.getNotes());
    cloneObj.setCreatedBy(this.getCreatedBy());
    cloneObj.setApprovedBy(this.getApprovedBy());
    cloneObj.setApprovedAt(this.getApprovedAt());
    cloneObj.setCreatedAt(this.getCreatedAt());
    cloneObj.setLines(new ArrayList<>(this.getLines()));
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public PurchaseOrder createNewInstance() {
    return new PurchaseOrder();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.vendor);
    _refs.add(this.warehouse);
    _refs.add(this.createdBy);
    _refs.add(this.approvedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
