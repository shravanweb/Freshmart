package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class StoreItemRequest extends CreatableObject {
  public static final int _STORE = 0;
  private Store store;

  public StoreItemRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.StoreItemRequest;
  }

  @Override
  public String _type() {
    return "StoreItemRequest";
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
    return "StoreItemRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof StoreItemRequest && super.equals(a);
  }

  public StoreItemRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    StoreItemRequest _obj = ((StoreItemRequest) dbObj);
    _obj.setStore(store);
  }

  public StoreItemRequest cloneInstance(StoreItemRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new StoreItemRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setStore(this.getStore());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public StoreItemRequest createNewInstance() {
    return new StoreItemRequest();
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
