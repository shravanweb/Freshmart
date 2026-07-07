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

@SolrDocument(collection = "SalesReturnLine")
@Entity
public class SalesReturnLine extends CreatableObject {
  public static final int _PRODUCT = 0;
  public static final int _QUANTITY = 1;
  public static final int _UNITPRICE = 2;
  public static final int _LINETOTAL = 3;
  public static final int _BATCHNUMBER = 4;
  public static final int _SALESRETURN = 5;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  private double quantity = 0.0d;
  private double unitPrice = 0.0d;
  private double lineTotal = 0.0d;
  private String batchNumber;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private SalesReturn salesReturn;

  public SalesReturnLine() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.SalesReturnLine;
  }

  @Override
  public String _type() {
    return "SalesReturnLine";
  }

  @Override
  public int _fieldsCount() {
    return 6;
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

  public SalesReturn getSalesReturn() {
    _checkProxy();
    return this.salesReturn;
  }

  public void setSalesReturn(SalesReturn salesReturn) {
    _checkProxy();
    if (Objects.equals(this.salesReturn, salesReturn)) {
      return;
    }
    fieldChanged(_SALESRETURN, this.salesReturn, salesReturn);
    if (!(isOld) && this.salesReturn != null) {
      this.salesReturn.removeFromLines(this);
    }
    this.salesReturn = salesReturn;
    if (!(isOld) && salesReturn != null && !(salesReturn.getLines().contains(this))) {
      salesReturn.addToLines(this, -1);
    }
  }

  public String displayName() {
    return this.getProduct().getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof SalesReturnLine && super.equals(a);
  }

  public SalesReturnLine deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    SalesReturnLine _obj = ((SalesReturnLine) dbObj);
    _obj.setProduct(product);
    _obj.setQuantity(quantity);
    _obj.setUnitPrice(unitPrice);
    _obj.setLineTotal(lineTotal);
    _obj.setBatchNumber(batchNumber);
    _obj.setSalesReturn(salesReturn);
  }

  public SalesReturnLine cloneInstance(SalesReturnLine cloneObj) {
    if (cloneObj == null) {
      cloneObj = new SalesReturnLine();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setProduct(this.getProduct());
    cloneObj.setQuantity(this.getQuantity());
    cloneObj.setUnitPrice(this.getUnitPrice());
    cloneObj.setLineTotal(this.getLineTotal());
    cloneObj.setBatchNumber(this.getBatchNumber());
    cloneObj.setSalesReturn(this.getSalesReturn());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public SalesReturnLine createNewInstance() {
    return new SalesReturnLine();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.product);
    _refs.add(this.salesReturn);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
