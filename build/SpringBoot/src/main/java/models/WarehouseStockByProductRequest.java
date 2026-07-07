package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class WarehouseStockByProductRequest extends CreatableObject {
  public static final int _PRODUCT = 0;
  private Product product;

  public WarehouseStockByProductRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.WarehouseStockByProductRequest;
  }

  @Override
  public String _type() {
    return "WarehouseStockByProductRequest";
  }

  @Override
  public int _fieldsCount() {
    return 1;
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

  public String displayName() {
    return "WarehouseStockByProductRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof WarehouseStockByProductRequest && super.equals(a);
  }

  public WarehouseStockByProductRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    WarehouseStockByProductRequest _obj = ((WarehouseStockByProductRequest) dbObj);
    _obj.setProduct(product);
  }

  public WarehouseStockByProductRequest cloneInstance(WarehouseStockByProductRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new WarehouseStockByProductRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setProduct(this.getProduct());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public WarehouseStockByProductRequest createNewInstance() {
    return new WarehouseStockByProductRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.product);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
