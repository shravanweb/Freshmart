package models;

import classes.AlertStatus;
import classes.StockAlertType;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "StockAlert")
@Entity
public class StockAlert extends CreatableObject {
  public static final int _WAREHOUSE = 0;
  public static final int _PRODUCT = 1;
  public static final int _ALERTTYPE = 2;
  public static final int _CURRENTQUANTITY = 3;
  public static final int _THRESHOLD = 4;
  public static final int _EXPIRYDATE = 5;
  public static final int _STATUS = 6;
  public static final int _ACKNOWLEDGEDBY = 7;
  public static final int _ACKNOWLEDGEDAT = 8;
  public static final int _CREATEDAT = 9;
  public static final int _ORGANIZATION = 10;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private StockAlertType alertType = StockAlertType.LowStock;

  private double currentQuantity = 0.0d;
  private double threshold = 0.0d;
  private LocalDate expiryDate;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private AlertStatus status = AlertStatus.Open;

  @ManyToOne(fetch = FetchType.LAZY)
  private User acknowledgedBy;

  private LocalDateTime acknowledgedAt;
  private LocalDateTime createdAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient StockAlert old;

  public StockAlert() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.StockAlert;
  }

  @Override
  public String _type() {
    return "StockAlert";
  }

  @Override
  public int _fieldsCount() {
    return 11;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
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

  public Product getProduct() {
    _checkProxy();
    return this.product;
  }

  public void setProduct(Product product) {
    _checkProxy();
    if (Objects.equals(this.product, product)) {
      return;
    }
    fieldChanged(_PRODUCT, this.product, product);
    this.product = product;
  }

  public StockAlertType getAlertType() {
    _checkProxy();
    return this.alertType;
  }

  public void setAlertType(StockAlertType alertType) {
    _checkProxy();
    if (Objects.equals(this.alertType, alertType)) {
      return;
    }
    fieldChanged(_ALERTTYPE, this.alertType, alertType);
    this.alertType = alertType;
  }

  public double getCurrentQuantity() {
    _checkProxy();
    return this.currentQuantity;
  }

  public void setCurrentQuantity(double currentQuantity) {
    _checkProxy();
    if (Objects.equals(this.currentQuantity, currentQuantity)) {
      return;
    }
    fieldChanged(_CURRENTQUANTITY, this.currentQuantity, currentQuantity);
    this.currentQuantity = currentQuantity;
  }

  public double getThreshold() {
    _checkProxy();
    return this.threshold;
  }

  public void setThreshold(double threshold) {
    _checkProxy();
    if (Objects.equals(this.threshold, threshold)) {
      return;
    }
    fieldChanged(_THRESHOLD, this.threshold, threshold);
    this.threshold = threshold;
  }

  public LocalDate getExpiryDate() {
    _checkProxy();
    return this.expiryDate;
  }

  public void setExpiryDate(LocalDate expiryDate) {
    _checkProxy();
    if (Objects.equals(this.expiryDate, expiryDate)) {
      return;
    }
    fieldChanged(_EXPIRYDATE, this.expiryDate, expiryDate);
    this.expiryDate = expiryDate;
  }

  public AlertStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(AlertStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
  }

  public User getAcknowledgedBy() {
    _checkProxy();
    return this.acknowledgedBy;
  }

  public void setAcknowledgedBy(User acknowledgedBy) {
    _checkProxy();
    if (Objects.equals(this.acknowledgedBy, acknowledgedBy)) {
      return;
    }
    fieldChanged(_ACKNOWLEDGEDBY, this.acknowledgedBy, acknowledgedBy);
    this.acknowledgedBy = acknowledgedBy;
  }

  public LocalDateTime getAcknowledgedAt() {
    _checkProxy();
    return this.acknowledgedAt;
  }

  public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
    _checkProxy();
    if (Objects.equals(this.acknowledgedAt, acknowledgedAt)) {
      return;
    }
    fieldChanged(_ACKNOWLEDGEDAT, this.acknowledgedAt, acknowledgedAt);
    this.acknowledgedAt = acknowledgedAt;
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

  public StockAlert getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((StockAlert) old);
  }

  public String displayName() {
    return this.getProduct().getName() + " - " + this.getAlertType().name();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof StockAlert && super.equals(a);
  }

  public StockAlert deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    StockAlert _obj = ((StockAlert) dbObj);
    _obj.setWarehouse(warehouse);
    _obj.setProduct(product);
    _obj.setAlertType(alertType);
    _obj.setCurrentQuantity(currentQuantity);
    _obj.setThreshold(threshold);
    _obj.setExpiryDate(expiryDate);
    _obj.setStatus(status);
    _obj.setAcknowledgedBy(acknowledgedBy);
    _obj.setAcknowledgedAt(acknowledgedAt);
    _obj.setCreatedAt(createdAt);
    _obj.setOrganization(organization);
  }

  public StockAlert cloneInstance(StockAlert cloneObj) {
    if (cloneObj == null) {
      cloneObj = new StockAlert();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setProduct(this.getProduct());
    cloneObj.setAlertType(this.getAlertType());
    cloneObj.setCurrentQuantity(this.getCurrentQuantity());
    cloneObj.setThreshold(this.getThreshold());
    cloneObj.setExpiryDate(this.getExpiryDate());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setAcknowledgedBy(this.getAcknowledgedBy());
    cloneObj.setAcknowledgedAt(this.getAcknowledgedAt());
    cloneObj.setCreatedAt(this.getCreatedAt());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public StockAlert createNewInstance() {
    return new StockAlert();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.warehouse);
    _refs.add(this.product);
    _refs.add(this.acknowledgedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
