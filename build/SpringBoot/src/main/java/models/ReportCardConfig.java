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

public class ReportCardConfig extends ReportBaseConfig {
  public static final int _VALUE = 0;
  private transient ReportField value;
  private transient ReportCardConfig old;

  public ReportCardConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportCardConfig;
  }

  @Override
  public String _type() {
    return "ReportCardConfig";
  }

  @Override
  public int _fieldsCount() {
    return 1;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    if (value != null) {
      visitor.accept(value);
      value.setMasterReportCardConfig(this);
      value.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    if (value != null) {
      visitor.accept(value);
      value.visitChildren(visitor);
    }
  }

  public ReportField getValue() {
    _checkProxy();
    return this.value;
  }

  public void setValue(ReportField value) {
    _checkProxy();
    if (Objects.equals(this.value, value)) {
      if (this.value != null) {
        this.value._updateChanges();
      }
      return;
    }
    fieldChanged(_VALUE, this.value, value);
    this.value = value;
    if (this.value != null) {
      this.value.setMasterReportCardConfig(this);
      this.value._setChildIdx(_VALUE);
      this.value._updateChanges();
    }
  }

  public ReportCardConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportCardConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    if (this.getValue() != null) {
      this.getValue().recordOld(ctx);
    }
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportCardConfig && super.equals(a);
  }

  public ReportCardConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChild(value);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportCardConfig _obj = ((ReportCardConfig) dbObj);
    ctx.cloneChild(value, (v) -> _obj.setValue(v));
  }

  public ReportCardConfig cloneInstance(ReportCardConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportCardConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setValue(this.getValue() == null ? null : this.getValue().cloneInstance(null));
    return cloneObj;
  }

  public ReportCardConfig createNewInstance() {
    return new ReportCardConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCreatableReferences(_refs, this.value);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _VALUE:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
      default:
        {
          super._handleChildChange(_childIdx, set);
        }
    }
  }
}
