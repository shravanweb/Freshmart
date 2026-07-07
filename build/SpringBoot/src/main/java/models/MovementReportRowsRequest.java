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

public class MovementReportRowsRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  public static final int _STARTDATE = 1;
  public static final int _ENDDATE = 2;
  public static final int _WAREHOUSE = 3;
  private Organization organization;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Warehouse warehouse;

  public MovementReportRowsRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.MovementReportRowsRequest;
  }

  @Override
  public String _type() {
    return "MovementReportRowsRequest";
  }

  @Override
  public int _fieldsCount() {
    return 4;
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

  public Warehouse getWarehouse() {
    _checkProxy();
    return this.warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    _checkProxy();
    if (Objects.equals(this.warehouse, warehouse)) {
      return;
    }
    fieldChanged(_WAREHOUSE, this.warehouse, warehouse);
    this.warehouse = warehouse;
  }

  public String displayName() {
    return "MovementReportRowsRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof MovementReportRowsRequest && super.equals(a);
  }

  public MovementReportRowsRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    MovementReportRowsRequest _obj = ((MovementReportRowsRequest) dbObj);
    _obj.setOrganization(organization);
    _obj.setStartDate(startDate);
    _obj.setEndDate(endDate);
    _obj.setWarehouse(warehouse);
  }

  public MovementReportRowsRequest cloneInstance(MovementReportRowsRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new MovementReportRowsRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    cloneObj.setStartDate(this.getStartDate());
    cloneObj.setEndDate(this.getEndDate());
    cloneObj.setWarehouse(this.getWarehouse());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public MovementReportRowsRequest createNewInstance() {
    return new MovementReportRowsRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.organization);
    _refs.add(this.warehouse);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
