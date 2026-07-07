package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.Database;
import store.DatabaseObject;
import store.ICloneable;

public class ReportNamedCondition extends DatabaseObject {
  public static final int _NAME = 0;
  public static final int _CONDITION = 1;
  private String name;
  private transient ReportRule condition;
  private transient ReportNamedCondition old;

  public ReportNamedCondition() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportNamedCondition;
  }

  @Override
  public String _type() {
    return "ReportNamedCondition";
  }

  @Override
  public int _fieldsCount() {
    return 2;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    if (condition != null) {
      visitor.accept(condition);
      condition.setMasterReportNamedCondition(this);
      condition.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    if (condition != null) {
      visitor.accept(condition);
      condition.visitChildren(visitor);
    }
  }

  public String getName() {
    _checkProxy();
    return this.name;
  }

  public void setName(String name) {
    _checkProxy();
    if (Objects.equals(this.name, name)) {
      return;
    }
    fieldChanged(_NAME, this.name, name);
    this.name = name;
  }

  public ReportRule getCondition() {
    _checkProxy();
    return this.condition;
  }

  public void setCondition(ReportRule condition) {
    _checkProxy();
    if (Objects.equals(this.condition, condition)) {
      if (this.condition != null) {
        this.condition._updateChanges();
      }
      return;
    }
    fieldChanged(_CONDITION, this.condition, condition);
    this.condition = condition;
    if (this.condition != null) {
      this.condition.setMasterReportNamedCondition(this);
      this.condition._setChildIdx(_CONDITION);
      this.condition._updateChanges();
    }
  }

  public ReportNamedCondition getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportNamedCondition) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    if (this.getCondition() != null) {
      this.getCondition().recordOld(ctx);
    }
  }

  public String displayName() {
    return "ReportNamedCondition";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportNamedCondition && super.equals(a);
  }

  public ReportNamedCondition deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChild(condition);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportNamedCondition _obj = ((ReportNamedCondition) dbObj);
    _obj.setName(name);
    ctx.cloneChild(condition, (v) -> _obj.setCondition(v));
  }

  public ReportNamedCondition cloneInstance(ReportNamedCondition cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportNamedCondition();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setCondition(
        this.getCondition() == null ? null : this.getCondition().cloneInstance(null));
    return cloneObj;
  }

  public ReportNamedCondition createNewInstance() {
    return new ReportNamedCondition();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCreatableReferences(_refs, this.condition);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _CONDITION:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
    }
  }
}
