package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public abstract class ReportRule extends DatabaseObject {
  public static final int _PARENT = 0;
  private transient ReportRule parent;
  private ReportNamedCondition masterReportNamedCondition;
  private ReportRuleSet masterReportRuleSet;

  public ReportRule() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportRule;
  }

  @Override
  public String _type() {
    return "ReportRule";
  }

  @Override
  public int _fieldsCount() {
    return 1;
  }

  public DatabaseObject _masterObject() {
    DatabaseObject master = super._masterObject();
    if (master != null) {
      return master;
    }
    if (masterReportNamedCondition != null) {
      return masterReportNamedCondition;
    }
    if (masterReportRuleSet != null) {
      return masterReportRuleSet;
    }
    return null;
  }

  public void _setMasterObject(DBObject master) {
    super._setMasterObject(master);
    if (master instanceof ReportNamedCondition) {
      masterReportNamedCondition = ((ReportNamedCondition) master);
    }
    if (master instanceof ReportRuleSet) {
      masterReportRuleSet = ((ReportRuleSet) master);
    }
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public void updateFlat(DatabaseObject obj) {
    super.updateFlat(obj);
    if (masterReportNamedCondition != null) {
      masterReportNamedCondition.updateFlat(obj);
    }
    if (masterReportRuleSet != null) {
      masterReportRuleSet.updateFlat(obj);
    }
  }

  public ReportRule getParent() {
    _checkProxy();
    return this.parent;
  }

  public void setParent(ReportRule parent) {
    _checkProxy();
    if (Objects.equals(this.parent, parent)) {
      return;
    }
    fieldChanged(_PARENT, this.parent, parent);
    this.parent = parent;
  }

  public ReportNamedCondition getMasterReportNamedCondition() {
    return this.masterReportNamedCondition;
  }

  public void setMasterReportNamedCondition(ReportNamedCondition masterReportNamedCondition) {
    this.masterReportNamedCondition = masterReportNamedCondition;
  }

  public ReportRuleSet getMasterReportRuleSet() {
    return this.masterReportRuleSet;
  }

  public void setMasterReportRuleSet(ReportRuleSet masterReportRuleSet) {
    this.masterReportRuleSet = masterReportRuleSet;
  }

  public String displayName() {
    return "ReportRule";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportRule && super.equals(a);
  }

  public ReportRule deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportRule _obj = ((ReportRule) dbObj);
    _obj.setParent(ctx.cloneRef(parent));
  }

  public ReportRule cloneInstance(ReportRule cloneObj) {
    super.cloneInstance(cloneObj);
    cloneObj.setParent(this.getParent());
    return cloneObj;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
