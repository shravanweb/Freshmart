package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ExpiringBatchesRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  public static final int _DAYSAHEAD = 1;
  private Organization organization;
  private long daysAhead = 0l;

  public ExpiringBatchesRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ExpiringBatchesRequest;
  }

  @Override
  public String _type() {
    return "ExpiringBatchesRequest";
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

  public long getDaysAhead() {
    _checkProxy();
    return this.daysAhead;
  }

  public void setDaysAhead(long daysAhead) {
    _checkProxy();
    if (Objects.equals(this.daysAhead, daysAhead)) {
      return;
    }
    fieldChanged(_DAYSAHEAD, this.daysAhead, daysAhead);
    this.daysAhead = daysAhead;
  }

  public String displayName() {
    return "ExpiringBatchesRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ExpiringBatchesRequest && super.equals(a);
  }

  public ExpiringBatchesRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ExpiringBatchesRequest _obj = ((ExpiringBatchesRequest) dbObj);
    _obj.setOrganization(organization);
    _obj.setDaysAhead(daysAhead);
  }

  public ExpiringBatchesRequest cloneInstance(ExpiringBatchesRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ExpiringBatchesRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    cloneObj.setDaysAhead(this.getDaysAhead());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public ExpiringBatchesRequest createNewInstance() {
    return new ExpiringBatchesRequest();
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
