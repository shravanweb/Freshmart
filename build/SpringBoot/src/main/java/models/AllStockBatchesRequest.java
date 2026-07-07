package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class AllStockBatchesRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  private Organization organization;

  public AllStockBatchesRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.AllStockBatchesRequest;
  }

  @Override
  public String _type() {
    return "AllStockBatchesRequest";
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

  public String displayName() {
    return "AllStockBatchesRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof AllStockBatchesRequest && super.equals(a);
  }

  public AllStockBatchesRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    AllStockBatchesRequest _obj = ((AllStockBatchesRequest) dbObj);
    _obj.setOrganization(organization);
  }

  public AllStockBatchesRequest cloneInstance(AllStockBatchesRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new AllStockBatchesRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public AllStockBatchesRequest createNewInstance() {
    return new AllStockBatchesRequest();
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
