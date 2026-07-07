package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class SalesOrderItemRequest extends CreatableObject {
  public static final int _SALESORDER = 0;
  private SalesOrder salesOrder;

  public SalesOrderItemRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.SalesOrderItemRequest;
  }

  @Override
  public String _type() {
    return "SalesOrderItemRequest";
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

  public SalesOrder getSalesOrder() {
    _checkProxy();
    return this.salesOrder;
  }

  public void setSalesOrder(SalesOrder salesOrder) {
    _checkProxy();
    if (Objects.equals(this.salesOrder, salesOrder)) {
      return;
    }
    fieldChanged(_SALESORDER, this.salesOrder, salesOrder);
    this.salesOrder = salesOrder;
  }

  public String displayName() {
    return "SalesOrderItemRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof SalesOrderItemRequest && super.equals(a);
  }

  public SalesOrderItemRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    SalesOrderItemRequest _obj = ((SalesOrderItemRequest) dbObj);
    _obj.setSalesOrder(salesOrder);
  }

  public SalesOrderItemRequest cloneInstance(SalesOrderItemRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new SalesOrderItemRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setSalesOrder(this.getSalesOrder());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public SalesOrderItemRequest createNewInstance() {
    return new SalesOrderItemRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.salesOrder);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
