package models;

import classes.PaymentStatus;
import classes.SalesOrderStatus;
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

@SolrDocument(collection = "SalesOrder")
@Entity
public class SalesOrder extends CreatableObject {
  public static final int _STORE = 0;
  public static final int _WAREHOUSE = 1;
  public static final int _ORDERNUMBER = 2;
  public static final int _ORDERDATE = 3;
  public static final int _CUSTOMERNAME = 4;
  public static final int _CUSTOMERPHONE = 5;
  public static final int _STATUS = 6;
  public static final int _SUBTOTAL = 7;
  public static final int _DISCOUNTAMOUNT = 8;
  public static final int _TAXAMOUNT = 9;
  public static final int _TOTALAMOUNT = 10;
  public static final int _PAYMENTSTATUS = 11;
  public static final int _SOLDBY = 12;
  public static final int _LINES = 13;
  public static final int _ORGANIZATION = 14;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Store store;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull private String orderNumber;
  @NotNull private LocalDateTime orderDate;
  private String customerName;
  private String customerPhone;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private SalesOrderStatus status = SalesOrderStatus.Draft;

  private double subtotal = 0.0d;
  private double discountAmount = 0.0d;
  private double taxAmount = 0.0d;
  private double totalAmount = 0.0d;

  @Enumerated(jakarta.persistence.EnumType.STRING)
  private PaymentStatus paymentStatus = PaymentStatus.Unpaid;

  @ManyToOne(fetch = FetchType.LAZY)
  private User soldBy;

  @ManyToMany(mappedBy = "salesOrder", cascade = CascadeType.REMOVE)
  private List<SalesOrderLine> lines = D3EPersistanceList.inverse(this, _LINES);

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient SalesOrder old;

  public SalesOrder() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.SalesOrder;
  }

  @Override
  public String _type() {
    return "SalesOrder";
  }

  @Override
  public int _fieldsCount() {
    return 15;
  }

  public void addToLines(SalesOrderLine val, long index) {
    if (index == -1) {
      this.lines.add(val);
    } else {
      this.lines.add(((int) index), val);
    }
  }

  public void removeFromLines(SalesOrderLine val) {
    this.lines.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
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

  public String getOrderNumber() {
    _checkProxy();
    return this.orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    _checkProxy();
    if (Objects.equals(this.orderNumber, orderNumber)) {
      return;
    }
    fieldChanged(_ORDERNUMBER, this.orderNumber, orderNumber);
    this.orderNumber = orderNumber;
  }

  public LocalDateTime getOrderDate() {
    _checkProxy();
    return this.orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    _checkProxy();
    if (Objects.equals(this.orderDate, orderDate)) {
      return;
    }
    fieldChanged(_ORDERDATE, this.orderDate, orderDate);
    this.orderDate = orderDate;
  }

  public String getCustomerName() {
    _checkProxy();
    return this.customerName;
  }

  public void setCustomerName(String customerName) {
    _checkProxy();
    if (Objects.equals(this.customerName, customerName)) {
      return;
    }
    fieldChanged(_CUSTOMERNAME, this.customerName, customerName);
    this.customerName = customerName;
  }

  public String getCustomerPhone() {
    _checkProxy();
    return this.customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    _checkProxy();
    if (Objects.equals(this.customerPhone, customerPhone)) {
      return;
    }
    fieldChanged(_CUSTOMERPHONE, this.customerPhone, customerPhone);
    this.customerPhone = customerPhone;
  }

  public SalesOrderStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(SalesOrderStatus status) {
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

  public double getDiscountAmount() {
    _checkProxy();
    return this.discountAmount;
  }

  public void setDiscountAmount(double discountAmount) {
    _checkProxy();
    if (Objects.equals(this.discountAmount, discountAmount)) {
      return;
    }
    fieldChanged(_DISCOUNTAMOUNT, this.discountAmount, discountAmount);
    this.discountAmount = discountAmount;
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

  public PaymentStatus getPaymentStatus() {
    _checkProxy();
    return this.paymentStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    _checkProxy();
    if (Objects.equals(this.paymentStatus, paymentStatus)) {
      return;
    }
    fieldChanged(_PAYMENTSTATUS, this.paymentStatus, paymentStatus);
    this.paymentStatus = paymentStatus;
  }

  public User getSoldBy() {
    _checkProxy();
    return this.soldBy;
  }

  public void setSoldBy(User soldBy) {
    _checkProxy();
    if (Objects.equals(this.soldBy, soldBy)) {
      return;
    }
    fieldChanged(_SOLDBY, this.soldBy, soldBy);
    this.soldBy = soldBy;
  }

  public List<SalesOrderLine> getLines() {
    return this.lines;
  }

  public void setLines(List<SalesOrderLine> lines) {
    if (Objects.equals(this.lines, lines)) {
      return;
    }
    ((D3EPersistanceList<SalesOrderLine>) this.lines).setAll(lines);
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

  public SalesOrder getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((SalesOrder) old);
  }

  public String displayName() {
    return this.getOrderNumber();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof SalesOrder && super.equals(a);
  }

  public SalesOrder deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    SalesOrder _obj = ((SalesOrder) dbObj);
    _obj.setStore(store);
    _obj.setWarehouse(warehouse);
    _obj.setOrderNumber(orderNumber);
    _obj.setOrderDate(orderDate);
    _obj.setCustomerName(customerName);
    _obj.setCustomerPhone(customerPhone);
    _obj.setStatus(status);
    _obj.setSubtotal(subtotal);
    _obj.setDiscountAmount(discountAmount);
    _obj.setTaxAmount(taxAmount);
    _obj.setTotalAmount(totalAmount);
    _obj.setPaymentStatus(paymentStatus);
    _obj.setSoldBy(soldBy);
    _obj.setLines(lines);
    _obj.setOrganization(organization);
  }

  public SalesOrder cloneInstance(SalesOrder cloneObj) {
    if (cloneObj == null) {
      cloneObj = new SalesOrder();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setStore(this.getStore());
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setOrderNumber(this.getOrderNumber());
    cloneObj.setOrderDate(this.getOrderDate());
    cloneObj.setCustomerName(this.getCustomerName());
    cloneObj.setCustomerPhone(this.getCustomerPhone());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setSubtotal(this.getSubtotal());
    cloneObj.setDiscountAmount(this.getDiscountAmount());
    cloneObj.setTaxAmount(this.getTaxAmount());
    cloneObj.setTotalAmount(this.getTotalAmount());
    cloneObj.setPaymentStatus(this.getPaymentStatus());
    cloneObj.setSoldBy(this.getSoldBy());
    cloneObj.setLines(new ArrayList<>(this.getLines()));
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public SalesOrder createNewInstance() {
    return new SalesOrder();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.store);
    _refs.add(this.warehouse);
    _refs.add(this.soldBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
