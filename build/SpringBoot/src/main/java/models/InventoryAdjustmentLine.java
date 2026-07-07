package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "InventoryAdjustmentLine")
@Entity
public class InventoryAdjustmentLine extends CreatableObject {
  public static final int _PRODUCT = 0;
  public static final int _QUANTITYBEFORE = 1;
  public static final int _QUANTITYCHANGE = 2;
  public static final int _QUANTITYAFTER = 3;
  public static final int _BATCHNUMBER = 4;
  public static final int _UNITCOST = 5;
  public static final int _INVENTORYADJUSTMENT = 6;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  private double quantityBefore = 0.0d;
  private double quantityChange = 0.0d;
  private double quantityAfter = 0.0d;
  private String batchNumber;
  private double unitCost = 0.0d;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private InventoryAdjustment inventoryAdjustment;

  public InventoryAdjustmentLine() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.InventoryAdjustmentLine;
  }

  @Override
  public String _type() {
    return "InventoryAdjustmentLine";
  }

  @Override
  public int _fieldsCount() {
    return 7;
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
    this.product = product;
  }

  public double getQuantityBefore() {
    _checkProxy();
    return this.quantityBefore;
  }

  public void setQuantityBefore(double quantityBefore) {
    _checkProxy();
    if (Objects.equals(this.quantityBefore, quantityBefore)) {
      return;
    }
    fieldChanged(_QUANTITYBEFORE, this.quantityBefore, quantityBefore);
    this.quantityBefore = quantityBefore;
  }

  public double getQuantityChange() {
    _checkProxy();
    return this.quantityChange;
  }

  public void setQuantityChange(double quantityChange) {
    _checkProxy();
    if (Objects.equals(this.quantityChange, quantityChange)) {
      return;
    }
    fieldChanged(_QUANTITYCHANGE, this.quantityChange, quantityChange);
    this.quantityChange = quantityChange;
  }

  public double getQuantityAfter() {
    _checkProxy();
    return this.quantityAfter;
  }

  public void setQuantityAfter(double quantityAfter) {
    _checkProxy();
    if (Objects.equals(this.quantityAfter, quantityAfter)) {
      return;
    }
    fieldChanged(_QUANTITYAFTER, this.quantityAfter, quantityAfter);
    this.quantityAfter = quantityAfter;
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

  public InventoryAdjustment getInventoryAdjustment() {
    _checkProxy();
    return this.inventoryAdjustment;
  }

  public void setInventoryAdjustment(InventoryAdjustment inventoryAdjustment) {
    _checkProxy();
    if (Objects.equals(this.inventoryAdjustment, inventoryAdjustment)) {
      return;
    }
    fieldChanged(_INVENTORYADJUSTMENT, this.inventoryAdjustment, inventoryAdjustment);
    if (!(isOld) && this.inventoryAdjustment != null) {
      this.inventoryAdjustment.removeFromLines(this);
    }
    this.inventoryAdjustment = inventoryAdjustment;
    if (!(isOld)
        && inventoryAdjustment != null
        && !(inventoryAdjustment.getLines().contains(this))) {
      inventoryAdjustment.addToLines(this, -1);
    }
  }

  public String displayName() {
    return this.getProduct().getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof InventoryAdjustmentLine && super.equals(a);
  }

  public InventoryAdjustmentLine deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    InventoryAdjustmentLine _obj = ((InventoryAdjustmentLine) dbObj);
    _obj.setProduct(product);
    _obj.setQuantityBefore(quantityBefore);
    _obj.setQuantityChange(quantityChange);
    _obj.setQuantityAfter(quantityAfter);
    _obj.setBatchNumber(batchNumber);
    _obj.setUnitCost(unitCost);
    _obj.setInventoryAdjustment(inventoryAdjustment);
  }

  public InventoryAdjustmentLine cloneInstance(InventoryAdjustmentLine cloneObj) {
    if (cloneObj == null) {
      cloneObj = new InventoryAdjustmentLine();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setProduct(this.getProduct());
    cloneObj.setQuantityBefore(this.getQuantityBefore());
    cloneObj.setQuantityChange(this.getQuantityChange());
    cloneObj.setQuantityAfter(this.getQuantityAfter());
    cloneObj.setBatchNumber(this.getBatchNumber());
    cloneObj.setUnitCost(this.getUnitCost());
    cloneObj.setInventoryAdjustment(this.getInventoryAdjustment());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public InventoryAdjustmentLine createNewInstance() {
    return new InventoryAdjustmentLine();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.product);
    _refs.add(this.inventoryAdjustment);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
