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

@SolrDocument(collection = "StockTransferLine")
@Entity
public class StockTransferLine extends CreatableObject {
  public static final int _PRODUCT = 0;
  public static final int _QUANTITY = 1;
  public static final int _BATCHNUMBER = 2;
  public static final int _UOM = 3;
  public static final int _STOCKTRANSFER = 4;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  private double quantity = 0.0d;
  private String batchNumber;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private UnitOfMeasure uom;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private StockTransfer stockTransfer;

  public StockTransferLine() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.StockTransferLine;
  }

  @Override
  public String _type() {
    return "StockTransferLine";
  }

  @Override
  public int _fieldsCount() {
    return 5;
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

  public double getQuantity() {
    _checkProxy();
    return this.quantity;
  }

  public void setQuantity(double quantity) {
    _checkProxy();
    if (Objects.equals(this.quantity, quantity)) {
      return;
    }
    fieldChanged(_QUANTITY, this.quantity, quantity);
    this.quantity = quantity;
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

  public UnitOfMeasure getUom() {
    _checkProxy();
    return this.uom;
  }

  public void setUom(UnitOfMeasure uom) {
    _checkProxy();
    if (Objects.equals(this.uom, uom)) {
      return;
    }
    fieldChanged(_UOM, this.uom, uom);
    this.uom = uom;
  }

  public StockTransfer getStockTransfer() {
    _checkProxy();
    return this.stockTransfer;
  }

  public void setStockTransfer(StockTransfer stockTransfer) {
    _checkProxy();
    if (Objects.equals(this.stockTransfer, stockTransfer)) {
      return;
    }
    fieldChanged(_STOCKTRANSFER, this.stockTransfer, stockTransfer);
    if (!(isOld) && this.stockTransfer != null) {
      this.stockTransfer.removeFromLines(this);
    }
    this.stockTransfer = stockTransfer;
    if (!(isOld) && stockTransfer != null && !(stockTransfer.getLines().contains(this))) {
      stockTransfer.addToLines(this, -1);
    }
  }

  public String displayName() {
    return this.getProduct().getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof StockTransferLine && super.equals(a);
  }

  public StockTransferLine deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    StockTransferLine _obj = ((StockTransferLine) dbObj);
    _obj.setProduct(product);
    _obj.setQuantity(quantity);
    _obj.setBatchNumber(batchNumber);
    _obj.setUom(uom);
    _obj.setStockTransfer(stockTransfer);
  }

  public StockTransferLine cloneInstance(StockTransferLine cloneObj) {
    if (cloneObj == null) {
      cloneObj = new StockTransferLine();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setProduct(this.getProduct());
    cloneObj.setQuantity(this.getQuantity());
    cloneObj.setBatchNumber(this.getBatchNumber());
    cloneObj.setUom(this.getUom());
    cloneObj.setStockTransfer(this.getStockTransfer());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public StockTransferLine createNewInstance() {
    return new StockTransferLine();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.product);
    _refs.add(this.uom);
    _refs.add(this.stockTransfer);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
