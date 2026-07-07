package models;

import classes.PurchaseOrderStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class PurchaseOrdersByStatusRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  public static final int _STATUS = 1;
  private Organization organization;
  private PurchaseOrderStatus status = PurchaseOrderStatus.Draft;

  public PurchaseOrdersByStatusRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.PurchaseOrdersByStatusRequest;
  }

  @Override
  public String _type() {
    return "PurchaseOrdersByStatusRequest";
  }

  @Override
  public int _fieldsCount() {
    return 2;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public Organization getOrganization() {
    _checkProxy();
    return this.organization;
  }

  public void setOrganization(Organization organization) {
    _checkProxy();
    if (Objects.equals(this.organization, organization)) {
      return;
    }
    fieldChanged(_ORGANIZATION, this.organization, organization);
    this.organization = organization;
  }

  public PurchaseOrderStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(PurchaseOrderStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
  }

  public String displayName() {
    return "PurchaseOrdersByStatusRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof PurchaseOrdersByStatusRequest && super.equals(a);
  }

  public PurchaseOrdersByStatusRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    PurchaseOrdersByStatusRequest _obj = ((PurchaseOrdersByStatusRequest) dbObj);
    _obj.setOrganization(organization);
    _obj.setStatus(status);
  }

  public PurchaseOrdersByStatusRequest cloneInstance(PurchaseOrdersByStatusRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new PurchaseOrdersByStatusRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    cloneObj.setStatus(this.getStatus());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public PurchaseOrdersByStatusRequest createNewInstance() {
    return new PurchaseOrdersByStatusRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
