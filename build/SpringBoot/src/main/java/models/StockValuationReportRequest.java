package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class StockValuationReportRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  public static final int _WAREHOUSE = 1;
  private Organization organization;
  private Warehouse warehouse;

  public StockValuationReportRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.StockValuationReportRequest;
  }

  @Override
  public String _type() {
    return "StockValuationReportRequest";
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
    return "StockValuationReportRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof StockValuationReportRequest && super.equals(a);
  }

  public StockValuationReportRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    StockValuationReportRequest _obj = ((StockValuationReportRequest) dbObj);
    _obj.setOrganization(organization);
    _obj.setWarehouse(warehouse);
  }

  public StockValuationReportRequest cloneInstance(StockValuationReportRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new StockValuationReportRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    cloneObj.setWarehouse(this.getWarehouse());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public StockValuationReportRequest createNewInstance() {
    return new StockValuationReportRequest();
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
