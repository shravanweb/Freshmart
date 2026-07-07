package models;

import d3e.core.CloneContext;
import d3e.core.IntegerExt;
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

@SolrDocument(collection = "PurchaseOrderLine")
@Entity
public class PurchaseOrderLine extends CreatableObject {
  public static final int _LINENUMBER = 0;
  public static final int _PRODUCT = 1;
  public static final int _ORDEREDQUANTITY = 2;
  public static final int _RECEIVEDQUANTITY = 3;
  public static final int _UNITPRICE = 4;
  public static final int _LINETOTAL = 5;
  public static final int _UOM = 6;
  public static final int _PURCHASEORDER = 7;
  private long lineNumber = 0l;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  private double orderedQuantity = 0.0d;
  private double receivedQuantity = 0.0d;
  private double unitPrice = 0.0d;
  private double lineTotal = 0.0d;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private UnitOfMeasure uom;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private PurchaseOrder purchaseOrder;

  public PurchaseOrderLine() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.PurchaseOrderLine;
  }

  @Override
  public String _type() {
    return "PurchaseOrderLine";
  }

  @Override
  public int _fieldsCount() {
    return 8;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public long getLineNumber() {
    _checkProxy();
    return this.lineNumber;
  }

  public void setLineNumber(long lineNumber) {
    _checkProxy();
    if (Objects.equals(this.lineNumber, lineNumber)) {
      return;
    }
    fieldChanged(_LINENUMBER, this.lineNumber, lineNumber);
    this.lineNumber = lineNumber;
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

  public double getOrderedQuantity() {
    _checkProxy();
    return this.orderedQuantity;
  }

  public void setOrderedQuantity(double orderedQuantity) {
    _checkProxy();
    if (Objects.equals(this.orderedQuantity, orderedQuantity)) {
      return;
    }
    fieldChanged(_ORDEREDQUANTITY, this.orderedQuantity, orderedQuantity);
    this.orderedQuantity = orderedQuantity;
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

  public double getUnitPrice() {
    _checkProxy();
    return this.unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    _checkProxy();
    if (Objects.equals(this.unitPrice, unitPrice)) {
      return;
    }
    fieldChanged(_UNITPRICE, this.unitPrice, unitPrice);
    this.unitPrice = unitPrice;
  }

  public double getLineTotal() {
    _checkProxy();
    return this.lineTotal;
  }

  public void setLineTotal(double lineTotal) {
    _checkProxy();
    if (Objects.equals(this.lineTotal, lineTotal)) {
      return;
    }
    fieldChanged(_LINETOTAL, this.lineTotal, lineTotal);
    this.lineTotal = lineTotal;
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
    if (!(isOld) && this.purchaseOrder != null) {
      this.purchaseOrder.removeFromLines(this);
    }
    this.purchaseOrder = purchaseOrder;
    if (!(isOld) && purchaseOrder != null && !(purchaseOrder.getLines().contains(this))) {
      purchaseOrder.addToLines(this, -1);
    }
  }

  public String displayName() {
    return IntegerExt.toString(this.getLineNumber());
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof PurchaseOrderLine && super.equals(a);
  }

  public PurchaseOrderLine deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    PurchaseOrderLine _obj = ((PurchaseOrderLine) dbObj);
    _obj.setLineNumber(lineNumber);
    _obj.setProduct(product);
    _obj.setOrderedQuantity(orderedQuantity);
    _obj.setReceivedQuantity(receivedQuantity);
    _obj.setUnitPrice(unitPrice);
    _obj.setLineTotal(lineTotal);
    _obj.setUom(uom);
    _obj.setPurchaseOrder(purchaseOrder);
  }

  public PurchaseOrderLine cloneInstance(PurchaseOrderLine cloneObj) {
    if (cloneObj == null) {
      cloneObj = new PurchaseOrderLine();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setLineNumber(this.getLineNumber());
    cloneObj.setProduct(this.getProduct());
    cloneObj.setOrderedQuantity(this.getOrderedQuantity());
    cloneObj.setReceivedQuantity(this.getReceivedQuantity());
    cloneObj.setUnitPrice(this.getUnitPrice());
    cloneObj.setLineTotal(this.getLineTotal());
    cloneObj.setUom(this.getUom());
    cloneObj.setPurchaseOrder(this.getPurchaseOrder());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public PurchaseOrderLine createNewInstance() {
    return new PurchaseOrderLine();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.product);
    _refs.add(this.uom);
    _refs.add(this.purchaseOrder);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
