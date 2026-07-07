package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "GoodsReceiptLine")
@Entity
public class GoodsReceiptLine extends CreatableObject {
  public static final int _PRODUCT = 0;
  public static final int _RECEIVEDQUANTITY = 1;
  public static final int _UNITCOST = 2;
  public static final int _BATCHNUMBER = 3;
  public static final int _EXPIRYDATE = 4;
  public static final int _PURCHASEORDERLINE = 5;
  public static final int _GOODSRECEIPT = 6;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  private double receivedQuantity = 0.0d;
  private double unitCost = 0.0d;
  private String batchNumber;
  private LocalDate expiryDate;

  @ManyToOne(fetch = FetchType.LAZY)
  private PurchaseOrderLine purchaseOrderLine;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private GoodsReceipt goodsReceipt;

  public GoodsReceiptLine() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.GoodsReceiptLine;
  }

  @Override
  public String _type() {
    return "GoodsReceiptLine";
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

  public double getReceivedQuantity() {
    _checkProxy();
    return this.receivedQuantity;
  }

  public void setReceivedQuantity(double receivedQuantity) {
    _checkProxy();
    if (Objects.equals(this.receivedQuantity, receivedQuantity)) {
      return;
    }
    fieldChanged(_RECEIVEDQUANTITY, this.receivedQuantity, receivedQuantity);
    this.receivedQuantity = receivedQuantity;
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

  public PurchaseOrderLine getPurchaseOrderLine() {
    _checkProxy();
    return this.purchaseOrderLine;
  }

  public void setPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
    _checkProxy();
    if (Objects.equals(this.purchaseOrderLine, purchaseOrderLine)) {
      return;
    }
    fieldChanged(_PURCHASEORDERLINE, this.purchaseOrderLine, purchaseOrderLine);
    this.purchaseOrderLine = purchaseOrderLine;
  }

  public GoodsReceipt getGoodsReceipt() {
    _checkProxy();
    return this.goodsReceipt;
  }

  public void setGoodsReceipt(GoodsReceipt goodsReceipt) {
    _checkProxy();
    if (Objects.equals(this.goodsReceipt, goodsReceipt)) {
      return;
    }
    fieldChanged(_GOODSRECEIPT, this.goodsReceipt, goodsReceipt);
    if (!(isOld) && this.goodsReceipt != null) {
      this.goodsReceipt.removeFromLines(this);
    }
    this.goodsReceipt = goodsReceipt;
    if (!(isOld) && goodsReceipt != null && !(goodsReceipt.getLines().contains(this))) {
      goodsReceipt.addToLines(this, -1);
    }
  }

  public String displayName() {
    return this.getProduct().getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof GoodsReceiptLine && super.equals(a);
  }

  public GoodsReceiptLine deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    GoodsReceiptLine _obj = ((GoodsReceiptLine) dbObj);
    _obj.setProduct(product);
    _obj.setReceivedQuantity(receivedQuantity);
    _obj.setUnitCost(unitCost);
    _obj.setBatchNumber(batchNumber);
    _obj.setExpiryDate(expiryDate);
    _obj.setPurchaseOrderLine(purchaseOrderLine);
    _obj.setGoodsReceipt(goodsReceipt);
  }

  public GoodsReceiptLine cloneInstance(GoodsReceiptLine cloneObj) {
    if (cloneObj == null) {
      cloneObj = new GoodsReceiptLine();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setProduct(this.getProduct());
    cloneObj.setReceivedQuantity(this.getReceivedQuantity());
    cloneObj.setUnitCost(this.getUnitCost());
    cloneObj.setBatchNumber(this.getBatchNumber());
    cloneObj.setExpiryDate(this.getExpiryDate());
    cloneObj.setPurchaseOrderLine(this.getPurchaseOrderLine());
    cloneObj.setGoodsReceipt(this.getGoodsReceipt());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public GoodsReceiptLine createNewInstance() {
    return new GoodsReceiptLine();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.product);
    _refs.add(this.purchaseOrderLine);
    _refs.add(this.goodsReceipt);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
