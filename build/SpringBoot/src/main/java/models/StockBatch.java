package models;

import classes.BatchStatus;
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

@SolrDocument(collection = "StockBatch")
@Entity
public class StockBatch extends CreatableObject {
  public static final int _PRODUCT = 0;
  public static final int _WAREHOUSE = 1;
  public static final int _BATCHNUMBER = 2;
  public static final int _QUANTITYONHAND = 3;
  public static final int _MANUFACTURINGDATE = 4;
  public static final int _EXPIRYDATE = 5;
  public static final int _UNITCOST = 6;
  public static final int _STATUS = 7;
  public static final int _CREATEDAT = 8;
  public static final int _ORGANIZATION = 9;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull private String batchNumber;
  private double quantityOnHand = 0.0d;
  private LocalDate manufacturingDate;
  private LocalDate expiryDate;
  private double unitCost = 0.0d;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private BatchStatus status = BatchStatus.Active;

  private LocalDateTime createdAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient StockBatch old;

  public StockBatch() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.StockBatch;
  }

  @Override
  public String _type() {
    return "StockBatch";
  }

  @Override
  public int _fieldsCount() {
    return 10;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
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
    if (!(isOld) && this.product != null) {
      this.product.removeFromBatches(this);
    }
    this.product = product;
    if (!(isOld) && product != null && !(product.getBatches().contains(this))) {
      product.addToBatches(this, -1);
    }
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

  public String getBatchNumber() {
    _checkProxy();
    return this.batchNumber;
  }

  public void setBatchNumber(String batchNumber) {
    _checkProxy();
    if (Objects.equals(this.batchNumber, batchNumber)) {
      return;
    }
    fieldChanged(_BATCHNUMBER, this.batchNumber, batchNumber);
    this.batchNumber = batchNumber;
  }

  public double getQuantityOnHand() {
    _checkProxy();
    return this.quantityOnHand;
  }

  public void setQuantityOnHand(double quantityOnHand) {
    _checkProxy();
    if (Objects.equals(this.quantityOnHand, quantityOnHand)) {
      return;
    }
    fieldChanged(_QUANTITYONHAND, this.quantityOnHand, quantityOnHand);
    this.quantityOnHand = quantityOnHand;
  }

  public LocalDate getManufacturingDate() {
    _checkProxy();
    return this.manufacturingDate;
  }

  public void setManufacturingDate(LocalDate manufacturingDate) {
    _checkProxy();
    if (Objects.equals(this.manufacturingDate, manufacturingDate)) {
      return;
    }
    fieldChanged(_MANUFACTURINGDATE, this.manufacturingDate, manufacturingDate);
    this.manufacturingDate = manufacturingDate;
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

  public double getUnitCost() {
    _checkProxy();
    return this.unitCost;
  }

  public void setUnitCost(double unitCost) {
    _checkProxy();
    if (Objects.equals(this.unitCost, unitCost)) {
      return;
    }
    fieldChanged(_UNITCOST, this.unitCost, unitCost);
    this.unitCost = unitCost;
  }

  public BatchStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(BatchStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
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

  public StockBatch getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((StockBatch) old);
  }

  public String displayName() {
    return this.getBatchNumber();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof StockBatch && super.equals(a);
  }

  public StockBatch deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    StockBatch _obj = ((StockBatch) dbObj);
    _obj.setProduct(product);
    _obj.setWarehouse(warehouse);
    _obj.setBatchNumber(batchNumber);
    _obj.setQuantityOnHand(quantityOnHand);
    _obj.setManufacturingDate(manufacturingDate);
    _obj.setExpiryDate(expiryDate);
    _obj.setUnitCost(unitCost);
    _obj.setStatus(status);
    _obj.setCreatedAt(createdAt);
    _obj.setOrganization(organization);
  }

  public StockBatch cloneInstance(StockBatch cloneObj) {
    if (cloneObj == null) {
      cloneObj = new StockBatch();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setProduct(this.getProduct());
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setBatchNumber(this.getBatchNumber());
    cloneObj.setQuantityOnHand(this.getQuantityOnHand());
    cloneObj.setManufacturingDate(this.getManufacturingDate());
    cloneObj.setExpiryDate(this.getExpiryDate());
    cloneObj.setUnitCost(this.getUnitCost());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setCreatedAt(this.getCreatedAt());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public StockBatch createNewInstance() {
    return new StockBatch();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.product);
    _refs.add(this.warehouse);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
