package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class WarehouseStockByWarehouseRequest extends CreatableObject {
  public static final int _WAREHOUSE = 0;
  private Warehouse warehouse;

  public WarehouseStockByWarehouseRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.WarehouseStockByWarehouseRequest;
  }

  @Override
  public String _type() {
    return "WarehouseStockByWarehouseRequest";
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

  public String displayName() {
    return "WarehouseStockByWarehouseRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof WarehouseStockByWarehouseRequest && super.equals(a);
  }

  public WarehouseStockByWarehouseRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    WarehouseStockByWarehouseRequest _obj = ((WarehouseStockByWarehouseRequest) dbObj);
    _obj.setWarehouse(warehouse);
  }

  public WarehouseStockByWarehouseRequest cloneInstance(WarehouseStockByWarehouseRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new WarehouseStockByWarehouseRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setWarehouse(this.getWarehouse());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public WarehouseStockByWarehouseRequest createNewInstance() {
    return new WarehouseStockByWarehouseRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.warehouse);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
