package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import store.D3EPersistanceList;
import store.DBObject;
import store.Database;
import store.DatabaseObject;
import store.ICloneable;

public class ReportKPIConfig extends ReportBaseConfig {
  public static final int _VALUE = 0;
  public static final int _TARGET = 1;
  public static final int _TREND = 2;
  private transient ReportField value;
  private transient List<ReportField> target = D3EPersistanceList.child(this, _TARGET);
  private transient ReportField trend;
  private transient ReportKPIConfig old;

  public ReportKPIConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportKPIConfig;
  }

  @Override
  public String _type() {
    return "ReportKPIConfig";
  }

  @Override
  public int _fieldsCount() {
    return 3;
  }

  public void addToTarget(ReportField val, long index) {
    if (index == -1) {
      this.target.add(val);
    } else {
      this.target.add(((int) index), val);
    }
  }

  public void removeFromTarget(ReportField val) {
    val._clearChildIdx();
    this.target.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    if (value != null) {
      visitor.accept(value);
      value.setMasterReportKPIConfig(this);
      value.updateMasters(visitor);
    }
    for (ReportField obj : this.getTarget()) {
      visitor.accept(obj);
      obj.setMasterReportKPIConfig(this);
      obj._setChildIdx(_TARGET);
      obj.updateMasters(visitor);
    }
    if (trend != null) {
      visitor.accept(trend);
      trend.setMasterReportKPIConfig(this);
      trend.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    if (value != null) {
      visitor.accept(value);
      value.visitChildren(visitor);
    }
    for (ReportField obj : this.getTarget()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    if (trend != null) {
      visitor.accept(trend);
      trend.visitChildren(visitor);
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
      this.value.setMasterReportKPIConfig(this);
      this.value._setChildIdx(_VALUE);
      this.value._updateChanges();
    }
  }

  public List<ReportField> getTarget() {
    return this.target;
  }

  public void setTarget(List<ReportField> target) {
    if (Objects.equals(this.target, target)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.target).setAll(target);
  }

  public ReportField getTrend() {
    _checkProxy();
    return this.trend;
  }

  public void setTrend(ReportField trend) {
    _checkProxy();
    if (Objects.equals(this.trend, trend)) {
      if (this.trend != null) {
        this.trend._updateChanges();
      }
      return;
    }
    fieldChanged(_TREND, this.trend, trend);
    this.trend = trend;
    if (this.trend != null) {
      this.trend.setMasterReportKPIConfig(this);
      this.trend._setChildIdx(_TREND);
      this.trend._updateChanges();
    }
  }

  public ReportKPIConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportKPIConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    if (this.getValue() != null) {
      this.getValue().recordOld(ctx);
    }
    this.getTarget().forEach((one) -> one.recordOld(ctx));
    if (this.getTrend() != null) {
      this.getTrend().recordOld(ctx);
    }
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportKPIConfig && super.equals(a);
  }

  public ReportKPIConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChild(value);
    ctx.collectChilds(target);
    ctx.collectChild(trend);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportKPIConfig _obj = ((ReportKPIConfig) dbObj);
    ctx.cloneChild(value, (v) -> _obj.setValue(v));
    ctx.cloneChildList(target, (v) -> _obj.setTarget(v));
    ctx.cloneChild(trend, (v) -> _obj.setTrend(v));
  }

  public ReportKPIConfig cloneInstance(ReportKPIConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportKPIConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setValue(this.getValue() == null ? null : this.getValue().cloneInstance(null));
    cloneObj.setTarget(
        this.getTarget().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTrend(this.getTrend() == null ? null : this.getTrend().cloneInstance(null));
    return cloneObj;
  }

  public ReportKPIConfig createNewInstance() {
    return new ReportKPIConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCreatableReferences(_refs, this.value);
    Database.collectCollctionCreatableReferences(_refs, this.target);
    Database.collectCreatableReferences(_refs, this.trend);
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
      case _TARGET:
        {
          this.childCollFieldChanged(_childIdx, set, this.target);
          break;
        }
      case _TREND:
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
