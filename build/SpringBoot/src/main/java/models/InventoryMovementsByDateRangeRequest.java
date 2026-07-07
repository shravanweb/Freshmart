package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class InventoryMovementsByDateRangeRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  public static final int _STARTDATE = 1;
  public static final int _ENDDATE = 2;
  private Organization organization;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public InventoryMovementsByDateRangeRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.InventoryMovementsByDateRangeRequest;
  }

  @Override
  public String _type() {
    return "InventoryMovementsByDateRangeRequest";
  }

  @Override
  public int _fieldsCount() {
    return 3;
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

  public LocalDateTime getStartDate() {
    _checkProxy();
    return this.startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    _checkProxy();
    if (Objects.equals(this.startDate, startDate)) {
      return;
    }
    fieldChanged(_STARTDATE, this.startDate, startDate);
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    _checkProxy();
    return this.endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    _checkProxy();
    if (Objects.equals(this.endDate, endDate)) {
      return;
    }
    fieldChanged(_ENDDATE, this.endDate, endDate);
    this.endDate = endDate;
  }

  public String displayName() {
    return "InventoryMovementsByDateRangeRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof InventoryMovementsByDateRangeRequest && super.equals(a);
  }

  public InventoryMovementsByDateRangeRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    InventoryMovementsByDateRangeRequest _obj = ((InventoryMovementsByDateRangeRequest) dbObj);
    _obj.setOrganization(organization);
    _obj.setStartDate(startDate);
    _obj.setEndDate(endDate);
  }

  public InventoryMovementsByDateRangeRequest cloneInstance(
      InventoryMovementsByDateRangeRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new InventoryMovementsByDateRangeRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    cloneObj.setStartDate(this.getStartDate());
    cloneObj.setEndDate(this.getEndDate());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public InventoryMovementsByDateRangeRequest createNewInstance() {
    return new InventoryMovementsByDateRangeRequest();
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
