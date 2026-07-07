package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class AllSalesReturnsRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  private Organization organization;

  public AllSalesReturnsRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.AllSalesReturnsRequest;
  }

  @Override
  public String _type() {
    return "AllSalesReturnsRequest";
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
    return "AllSalesReturnsRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof AllSalesReturnsRequest && super.equals(a);
  }

  public AllSalesReturnsRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    AllSalesReturnsRequest _obj = ((AllSalesReturnsRequest) dbObj);
    _obj.setOrganization(organization);
  }

  public AllSalesReturnsRequest cloneInstance(AllSalesReturnsRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new AllSalesReturnsRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public AllSalesReturnsRequest createNewInstance() {
    return new AllSalesReturnsRequest();
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
