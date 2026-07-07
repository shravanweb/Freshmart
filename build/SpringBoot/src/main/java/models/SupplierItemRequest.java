package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class SupplierItemRequest extends CreatableObject {
  public static final int _VENDOR = 0;
  private Vendor vendor;

  public SupplierItemRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.SupplierItemRequest;
  }

  @Override
  public String _type() {
    return "SupplierItemRequest";
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

  public Vendor getVendor() {
    _checkProxy();
    return this.vendor;
  }

  public void setVendor(Vendor vendor) {
    _checkProxy();
    if (Objects.equals(this.vendor, vendor)) {
      return;
    }
    fieldChanged(_VENDOR, this.vendor, vendor);
    this.vendor = vendor;
  }

  public String displayName() {
    return "SupplierItemRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof SupplierItemRequest && super.equals(a);
  }

  public SupplierItemRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    SupplierItemRequest _obj = ((SupplierItemRequest) dbObj);
    _obj.setVendor(vendor);
  }

  public SupplierItemRequest cloneInstance(SupplierItemRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new SupplierItemRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setVendor(this.getVendor());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public SupplierItemRequest createNewInstance() {
    return new SupplierItemRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.vendor);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
