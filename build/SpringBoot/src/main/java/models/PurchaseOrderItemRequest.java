package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class PurchaseOrderItemRequest extends CreatableObject {
  public static final int _PURCHASEORDER = 0;
  private PurchaseOrder purchaseOrder;

  public PurchaseOrderItemRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.PurchaseOrderItemRequest;
  }

  @Override
  public String _type() {
    return "PurchaseOrderItemRequest";
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
    this.purchaseOrder = purchaseOrder;
  }

  public String displayName() {
    return "PurchaseOrderItemRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof PurchaseOrderItemRequest && super.equals(a);
  }

  public PurchaseOrderItemRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    PurchaseOrderItemRequest _obj = ((PurchaseOrderItemRequest) dbObj);
    _obj.setPurchaseOrder(purchaseOrder);
  }

  public PurchaseOrderItemRequest cloneInstance(PurchaseOrderItemRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new PurchaseOrderItemRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setPurchaseOrder(this.getPurchaseOrder());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public PurchaseOrderItemRequest createNewInstance() {
    return new PurchaseOrderItemRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.purchaseOrder);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
