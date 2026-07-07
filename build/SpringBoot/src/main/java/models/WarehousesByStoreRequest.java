package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class WarehousesByStoreRequest extends CreatableObject {
  public static final int _STORE = 0;
  private Store store;

  public WarehousesByStoreRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.WarehousesByStoreRequest;
  }

  @Override
  public String _type() {
    return "WarehousesByStoreRequest";
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

  public Store getStore() {
    _checkProxy();
    return this.store;
  }

  public void setStore(Store store) {
    _checkProxy();
    if (Objects.equals(this.store, store)) {
      return;
    }
    fieldChanged(_STORE, this.store, store);
    this.store = store;
  }

  public String displayName() {
    return "WarehousesByStoreRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof WarehousesByStoreRequest && super.equals(a);
  }

  public WarehousesByStoreRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    WarehousesByStoreRequest _obj = ((WarehousesByStoreRequest) dbObj);
    _obj.setStore(store);
  }

  public WarehousesByStoreRequest cloneInstance(WarehousesByStoreRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new WarehousesByStoreRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setStore(this.getStore());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public WarehousesByStoreRequest createNewInstance() {
    return new WarehousesByStoreRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.store);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
