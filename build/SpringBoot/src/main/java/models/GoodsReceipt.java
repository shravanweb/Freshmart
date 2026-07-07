package models;

import classes.GoodsReceiptStatus;
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

@SolrDocument(collection = "GoodsReceipt")
@Entity
public class GoodsReceipt extends CreatableObject {
  public static final int _RECEIPTNUMBER = 0;
  public static final int _PURCHASEORDER = 1;
  public static final int _VENDOR = 2;
  public static final int _WAREHOUSE = 3;
  public static final int _RECEIPTDATE = 4;
  public static final int _STATUS = 5;
  public static final int _NOTES = 6;
  public static final int _RECEIVEDBY = 7;
  public static final int _CREATEDAT = 8;
  public static final int _LINES = 9;
  public static final int _ORGANIZATION = 10;
  @NotNull private String receiptNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  private PurchaseOrder purchaseOrder;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Vendor vendor;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull private LocalDate receiptDate;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private GoodsReceiptStatus status = GoodsReceiptStatus.Draft;

  private String notes;

  @ManyToOne(fetch = FetchType.LAZY)
  private User receivedBy;

  private LocalDateTime createdAt;

  @ManyToMany(mappedBy = "goodsReceipt", cascade = CascadeType.REMOVE)
  private List<GoodsReceiptLine> lines = D3EPersistanceList.inverse(this, _LINES);

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient GoodsReceipt old;

  public GoodsReceipt() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.GoodsReceipt;
  }

  @Override
  public String _type() {
    return "GoodsReceipt";
  }

  @Override
  public int _fieldsCount() {
    return 11;
  }

  public void addToLines(GoodsReceiptLine val, long index) {
    if (index == -1) {
      this.lines.add(val);
    } else {
      this.lines.add(((int) index), val);
    }
  }

  public void removeFromLines(GoodsReceiptLine val) {
    this.lines.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getReceiptNumber() {
    _checkProxy();
    return this.receiptNumber;
  }

  public void setReceiptNumber(String receiptNumber) {
    _checkProxy();
    if (Objects.equals(this.receiptNumber, receiptNumber)) {
      return;
    }
    fieldChanged(_RECEIPTNUMBER, this.receiptNumber, receiptNumber);
    this.receiptNumber = receiptNumber;
  }

  public PurchaseOrder getPurchaseOrder() {
    _checkProxy();
    return this.purchaseOrder;
  }

  public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
    _checkProxy();
    if (Objects.equals(this.purchaseOrder, purchaseOrder)) {
      return;
    }
    fieldChanged(_PURCHASEORDER, this.purchaseOrder, purchaseOrder);
    this.purchaseOrder = purchaseOrder;
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

  public LocalDate getReceiptDate() {
    _checkProxy();
    return this.receiptDate;
  }

  public void setReceiptDate(LocalDate receiptDate) {
    _checkProxy();
    if (Objects.equals(this.receiptDate, receiptDate)) {
      return;
    }
    fieldChanged(_RECEIPTDATE, this.receiptDate, receiptDate);
    this.receiptDate = receiptDate;
  }

  public GoodsReceiptStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(GoodsReceiptStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
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

  public User getReceivedBy() {
    _checkProxy();
    return this.receivedBy;
  }

  public void setReceivedBy(User receivedBy) {
    _checkProxy();
    if (Objects.equals(this.receivedBy, receivedBy)) {
      return;
    }
    fieldChanged(_RECEIVEDBY, this.receivedBy, receivedBy);
    this.receivedBy = receivedBy;
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

  public List<GoodsReceiptLine> getLines() {
    return this.lines;
  }

  public void setLines(List<GoodsReceiptLine> lines) {
    if (Objects.equals(this.lines, lines)) {
      return;
    }
    ((D3EPersistanceList<GoodsReceiptLine>) this.lines).setAll(lines);
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

  public GoodsReceipt getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((GoodsReceipt) old);
  }

  public String displayName() {
    return this.getReceiptNumber();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof GoodsReceipt && super.equals(a);
  }

  public GoodsReceipt deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    GoodsReceipt _obj = ((GoodsReceipt) dbObj);
    _obj.setReceiptNumber(receiptNumber);
    _obj.setPurchaseOrder(purchaseOrder);
    _obj.setVendor(vendor);
    _obj.setWarehouse(warehouse);
    _obj.setReceiptDate(receiptDate);
    _obj.setStatus(status);
    _obj.setNotes(notes);
    _obj.setReceivedBy(receivedBy);
    _obj.setCreatedAt(createdAt);
    _obj.setLines(lines);
    _obj.setOrganization(organization);
  }

  public GoodsReceipt cloneInstance(GoodsReceipt cloneObj) {
    if (cloneObj == null) {
      cloneObj = new GoodsReceipt();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setReceiptNumber(this.getReceiptNumber());
    cloneObj.setPurchaseOrder(this.getPurchaseOrder());
    cloneObj.setVendor(this.getVendor());
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setReceiptDate(this.getReceiptDate());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setNotes(this.getNotes());
    cloneObj.setReceivedBy(this.getReceivedBy());
    cloneObj.setCreatedAt(this.getCreatedAt());
    cloneObj.setLines(new ArrayList<>(this.getLines()));
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public GoodsReceipt createNewInstance() {
    return new GoodsReceipt();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.purchaseOrder);
    _refs.add(this.vendor);
    _refs.add(this.warehouse);
    _refs.add(this.receivedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
