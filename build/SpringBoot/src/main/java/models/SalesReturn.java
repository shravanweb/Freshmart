package models;

import classes.ReturnReason;
import classes.SalesReturnStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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

@SolrDocument(collection = "SalesReturn")
@Entity
public class SalesReturn extends CreatableObject {
  public static final int _SALESORDER = 0;
  public static final int _STORE = 1;
  public static final int _WAREHOUSE = 2;
  public static final int _RETURNNUMBER = 3;
  public static final int _RETURNDATE = 4;
  public static final int _STATUS = 5;
  public static final int _REASON = 6;
  public static final int _REFUNDAMOUNT = 7;
  public static final int _PROCESSEDBY = 8;
  public static final int _LINES = 9;
  public static final int _ORGANIZATION = 10;

  @ManyToOne(fetch = FetchType.LAZY)
  private SalesOrder salesOrder;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Store store;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull private String returnNumber;
  @NotNull private LocalDateTime returnDate;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private SalesReturnStatus status = SalesReturnStatus.Draft;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private ReturnReason reason = ReturnReason.Defective;

  private double refundAmount = 0.0d;

  @ManyToOne(fetch = FetchType.LAZY)
  private User processedBy;

  @ManyToMany(mappedBy = "salesReturn", cascade = CascadeType.REMOVE)
  private List<SalesReturnLine> lines = D3EPersistanceList.inverse(this, _LINES);

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient SalesReturn old;

  public SalesReturn() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.SalesReturn;
  }

  @Override
  public String _type() {
    return "SalesReturn";
  }

  @Override
  public int _fieldsCount() {
    return 11;
  }

  public void addToLines(SalesReturnLine val, long index) {
    if (index == -1) {
      this.lines.add(val);
    } else {
      this.lines.add(((int) index), val);
    }
  }

  public void removeFromLines(SalesReturnLine val) {
    this.lines.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public SalesOrder getSalesOrder() {
    _checkProxy();
    return this.salesOrder;
  }

  public void setSalesOrder(SalesOrder salesOrder) {
    _checkProxy();
    if (Objects.equals(this.salesOrder, salesOrder)) {
      return;
    }
    fieldChanged(_SALESORDER, this.salesOrder, salesOrder);
    this.salesOrder = salesOrder;
  }

  public Store getStore() {
    _checkProxy();
    return this.store;
  }

  public void setStore(Store store) {
    _checkProxy();
    if (Objects.equals(this.store, store)) {
      return;
    }
    fieldChanged(_STORE, this.store, store);
    this.store = store;
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

  public String getReturnNumber() {
    _checkProxy();
    return this.returnNumber;
  }

  public void setReturnNumber(String returnNumber) {
    _checkProxy();
    if (Objects.equals(this.returnNumber, returnNumber)) {
      return;
    }
    fieldChanged(_RETURNNUMBER, this.returnNumber, returnNumber);
    this.returnNumber = returnNumber;
  }

  public LocalDateTime getReturnDate() {
    _checkProxy();
    return this.returnDate;
  }

  public void setReturnDate(LocalDateTime returnDate) {
    _checkProxy();
    if (Objects.equals(this.returnDate, returnDate)) {
      return;
    }
    fieldChanged(_RETURNDATE, this.returnDate, returnDate);
    this.returnDate = returnDate;
  }

  public SalesReturnStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(SalesReturnStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
  }

  public ReturnReason getReason() {
    _checkProxy();
    return this.reason;
  }

  public void setReason(ReturnReason reason) {
    _checkProxy();
    if (Objects.equals(this.reason, reason)) {
      return;
    }
    fieldChanged(_REASON, this.reason, reason);
    this.reason = reason;
  }

  public double getRefundAmount() {
    _checkProxy();
    return this.refundAmount;
  }

  public void setRefundAmount(double refundAmount) {
    _checkProxy();
    if (Objects.equals(this.refundAmount, refundAmount)) {
      return;
    }
    fieldChanged(_REFUNDAMOUNT, this.refundAmount, refundAmount);
    this.refundAmount = refundAmount;
  }

  public User getProcessedBy() {
    _checkProxy();
    return this.processedBy;
  }

  public void setProcessedBy(User processedBy) {
    _checkProxy();
    if (Objects.equals(this.processedBy, processedBy)) {
      return;
    }
    fieldChanged(_PROCESSEDBY, this.processedBy, processedBy);
    this.processedBy = processedBy;
  }

  public List<SalesReturnLine> getLines() {
    return this.lines;
  }

  public void setLines(List<SalesReturnLine> lines) {
    if (Objects.equals(this.lines, lines)) {
      return;
    }
    ((D3EPersistanceList<SalesReturnLine>) this.lines).setAll(lines);
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

  public SalesReturn getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((SalesReturn) old);
  }

  public String displayName() {
    return this.getReturnNumber();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof SalesReturn && super.equals(a);
  }

  public SalesReturn deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    SalesReturn _obj = ((SalesReturn) dbObj);
    _obj.setSalesOrder(salesOrder);
    _obj.setStore(store);
    _obj.setWarehouse(warehouse);
    _obj.setReturnNumber(returnNumber);
    _obj.setReturnDate(returnDate);
    _obj.setStatus(status);
    _obj.setReason(reason);
    _obj.setRefundAmount(refundAmount);
    _obj.setProcessedBy(processedBy);
    _obj.setLines(lines);
    _obj.setOrganization(organization);
  }

  public SalesReturn cloneInstance(SalesReturn cloneObj) {
    if (cloneObj == null) {
      cloneObj = new SalesReturn();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setSalesOrder(this.getSalesOrder());
    cloneObj.setStore(this.getStore());
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setReturnNumber(this.getReturnNumber());
    cloneObj.setReturnDate(this.getReturnDate());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setReason(this.getReason());
    cloneObj.setRefundAmount(this.getRefundAmount());
    cloneObj.setProcessedBy(this.getProcessedBy());
    cloneObj.setLines(new ArrayList<>(this.getLines()));
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public SalesReturn createNewInstance() {
    return new SalesReturn();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.salesOrder);
    _refs.add(this.store);
    _refs.add(this.warehouse);
    _refs.add(this.processedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
