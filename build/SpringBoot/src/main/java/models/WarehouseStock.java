package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "WarehouseStock")
@Entity
public class WarehouseStock extends CreatableObject {
  public static final int _WAREHOUSE = 0;
  public static final int _PRODUCT = 1;
  public static final int _QUANTITYONHAND = 2;
  public static final int _QUANTITYRESERVED = 3;
  public static final int _QUANTITYAVAILABLE = 4;
  public static final int _AVERAGECOST = 5;
  public static final int _STOCKVALUE = 6;
  public static final int _LASTMOVEMENTAT = 7;
  public static final int _LOWSTOCKNOTIFIEDAT = 8;
  public static final int _ORGANIZATION = 9;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  private double quantityOnHand = 0.0d;
  private double quantityReserved = 0.0d;
  private double quantityAvailable = 0.0d;
  private double averageCost = 0.0d;
  private double stockValue = 0.0d;
  private LocalDateTime lastMovementAt;
  private LocalDateTime lowStockNotifiedAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient WarehouseStock old;

  public WarehouseStock() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.WarehouseStock;
  }

  @Override
  public String _type() {
    return "WarehouseStock";
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
    if (!(isOld) && this.product != null) {
      this.product.removeFromWarehouseStocks(this);
    }
    this.product = product;
    if (!(isOld) && product != null && !(product.getWarehouseStocks().contains(this))) {
      product.addToWarehouseStocks(this, -1);
    }
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

  public double getQuantityReserved() {
    _checkProxy();
    return this.quantityReserved;
  }

  public void setQuantityReserved(double quantityReserved) {
    _checkProxy();
    if (Objects.equals(this.quantityReserved, quantityReserved)) {
      return;
    }
    fieldChanged(_QUANTITYRESERVED, this.quantityReserved, quantityReserved);
    this.quantityReserved = quantityReserved;
  }

  public double getQuantityAvailable() {
    _checkProxy();
    return this.quantityAvailable;
  }

  public void setQuantityAvailable(double quantityAvailable) {
    _checkProxy();
    if (Objects.equals(this.quantityAvailable, quantityAvailable)) {
      return;
    }
    fieldChanged(_QUANTITYAVAILABLE, this.quantityAvailable, quantityAvailable);
    this.quantityAvailable = quantityAvailable;
  }

  public double getAverageCost() {
    _checkProxy();
    return this.averageCost;
  }

  public void setAverageCost(double averageCost) {
    _checkProxy();
    if (Objects.equals(this.averageCost, averageCost)) {
      return;
    }
    fieldChanged(_AVERAGECOST, this.averageCost, averageCost);
    this.averageCost = averageCost;
  }

  public double getStockValue() {
    _checkProxy();
    return this.stockValue;
  }

  public void setStockValue(double stockValue) {
    _checkProxy();
    if (Objects.equals(this.stockValue, stockValue)) {
      return;
    }
    fieldChanged(_STOCKVALUE, this.stockValue, stockValue);
    this.stockValue = stockValue;
  }

  public LocalDateTime getLastMovementAt() {
    _checkProxy();
    return this.lastMovementAt;
  }

  public void setLastMovementAt(LocalDateTime lastMovementAt) {
    _checkProxy();
    if (Objects.equals(this.lastMovementAt, lastMovementAt)) {
      return;
    }
    fieldChanged(_LASTMOVEMENTAT, this.lastMovementAt, lastMovementAt);
    this.lastMovementAt = lastMovementAt;
  }

  public LocalDateTime getLowStockNotifiedAt() {
    _checkProxy();
    return this.lowStockNotifiedAt;
  }

  public void setLowStockNotifiedAt(LocalDateTime lowStockNotifiedAt) {
    _checkProxy();
    if (Objects.equals(this.lowStockNotifiedAt, lowStockNotifiedAt)) {
      return;
    }
    fieldChanged(_LOWSTOCKNOTIFIEDAT, this.lowStockNotifiedAt, lowStockNotifiedAt);
    this.lowStockNotifiedAt = lowStockNotifiedAt;
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

  public WarehouseStock getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((WarehouseStock) old);
  }

  public String displayName() {
    return this.getProduct().getName() + " @ " + this.getWarehouse().getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof WarehouseStock && super.equals(a);
  }

  public WarehouseStock deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    WarehouseStock _obj = ((WarehouseStock) dbObj);
    _obj.setWarehouse(warehouse);
    _obj.setProduct(product);
    _obj.setQuantityOnHand(quantityOnHand);
    _obj.setQuantityReserved(quantityReserved);
    _obj.setQuantityAvailable(quantityAvailable);
    _obj.setAverageCost(averageCost);
    _obj.setStockValue(stockValue);
    _obj.setLastMovementAt(lastMovementAt);
    _obj.setLowStockNotifiedAt(lowStockNotifiedAt);
    _obj.setOrganization(organization);
  }

  public WarehouseStock cloneInstance(WarehouseStock cloneObj) {
    if (cloneObj == null) {
      cloneObj = new WarehouseStock();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setProduct(this.getProduct());
    cloneObj.setQuantityOnHand(this.getQuantityOnHand());
    cloneObj.setQuantityReserved(this.getQuantityReserved());
    cloneObj.setQuantityAvailable(this.getQuantityAvailable());
    cloneObj.setAverageCost(this.getAverageCost());
    cloneObj.setStockValue(this.getStockValue());
    cloneObj.setLastMovementAt(this.getLastMovementAt());
    cloneObj.setLowStockNotifiedAt(this.getLowStockNotifiedAt());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public WarehouseStock createNewInstance() {
    return new WarehouseStock();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.warehouse);
    _refs.add(this.product);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
