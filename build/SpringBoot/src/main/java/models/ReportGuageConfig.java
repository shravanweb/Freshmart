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

public class ReportGuageConfig extends ReportBaseConfig {
  public static final int _VALUE = 0;
  public static final int _MIN = 1;
  public static final int _MAX = 2;
  public static final int _TARGET = 3;
  public static final int _TOOLTIPS = 4;
  private transient List<ReportField> value = D3EPersistanceList.child(this, _VALUE);
  private transient List<ReportField> min = D3EPersistanceList.child(this, _MIN);
  private transient List<ReportField> max = D3EPersistanceList.child(this, _MAX);
  private transient List<ReportField> target = D3EPersistanceList.child(this, _TARGET);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportGuageConfig old;

  public ReportGuageConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportGuageConfig;
  }

  @Override
  public String _type() {
    return "ReportGuageConfig";
  }

  @Override
  public int _fieldsCount() {
    return 5;
  }

  public void addToValue(ReportField val, long index) {
    if (index == -1) {
      this.value.add(val);
    } else {
      this.value.add(((int) index), val);
    }
  }

  public void removeFromValue(ReportField val) {
    val._clearChildIdx();
    this.value.remove(val);
  }

  public void addToMin(ReportField val, long index) {
    if (index == -1) {
      this.min.add(val);
    } else {
      this.min.add(((int) index), val);
    }
  }

  public void removeFromMin(ReportField val) {
    val._clearChildIdx();
    this.min.remove(val);
  }

  public void addToMax(ReportField val, long index) {
    if (index == -1) {
      this.max.add(val);
    } else {
      this.max.add(((int) index), val);
    }
  }

  public void removeFromMax(ReportField val) {
    val._clearChildIdx();
    this.max.remove(val);
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

  public void addToTooltips(ReportField val, long index) {
    if (index == -1) {
      this.tooltips.add(val);
    } else {
      this.tooltips.add(((int) index), val);
    }
  }

  public void removeFromTooltips(ReportField val) {
    val._clearChildIdx();
    this.tooltips.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportField obj : this.getValue()) {
      visitor.accept(obj);
      obj.setMasterReportGuageConfig(this);
      obj._setChildIdx(_VALUE);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getMin()) {
      visitor.accept(obj);
      obj.setMasterReportGuageConfig(this);
      obj._setChildIdx(_MIN);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getMax()) {
      visitor.accept(obj);
      obj.setMasterReportGuageConfig(this);
      obj._setChildIdx(_MAX);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTarget()) {
      visitor.accept(obj);
      obj.setMasterReportGuageConfig(this);
      obj._setChildIdx(_TARGET);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportGuageConfig(this);
      obj._setChildIdx(_TOOLTIPS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getValue()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getMin()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getMax()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getTarget()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public List<ReportField> getValue() {
    return this.value;
  }

  public void setValue(List<ReportField> value) {
    if (Objects.equals(this.value, value)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.value).setAll(value);
  }

  public List<ReportField> getMin() {
    return this.min;
  }

  public void setMin(List<ReportField> min) {
    if (Objects.equals(this.min, min)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.min).setAll(min);
  }

  public List<ReportField> getMax() {
    return this.max;
  }

  public void setMax(List<ReportField> max) {
    if (Objects.equals(this.max, max)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.max).setAll(max);
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

  public List<ReportField> getTooltips() {
    return this.tooltips;
  }

  public void setTooltips(List<ReportField> tooltips) {
    if (Objects.equals(this.tooltips, tooltips)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.tooltips).setAll(tooltips);
  }

  public ReportGuageConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportGuageConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getValue().forEach((one) -> one.recordOld(ctx));
    this.getMin().forEach((one) -> one.recordOld(ctx));
    this.getMax().forEach((one) -> one.recordOld(ctx));
    this.getTarget().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportGuageConfig && super.equals(a);
  }

  public ReportGuageConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(value);
    ctx.collectChilds(min);
    ctx.collectChilds(max);
    ctx.collectChilds(target);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportGuageConfig _obj = ((ReportGuageConfig) dbObj);
    ctx.cloneChildList(value, (v) -> _obj.setValue(v));
    ctx.cloneChildList(min, (v) -> _obj.setMin(v));
    ctx.cloneChildList(max, (v) -> _obj.setMax(v));
    ctx.cloneChildList(target, (v) -> _obj.setTarget(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportGuageConfig cloneInstance(ReportGuageConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportGuageConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setValue(
        this.getValue().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setMin(
        this.getMin().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setMax(
        this.getMax().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTarget(
        this.getTarget().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTooltips(
        this.getTooltips().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportGuageConfig createNewInstance() {
    return new ReportGuageConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.value);
    Database.collectCollctionCreatableReferences(_refs, this.min);
    Database.collectCollctionCreatableReferences(_refs, this.max);
    Database.collectCollctionCreatableReferences(_refs, this.target);
    Database.collectCollctionCreatableReferences(_refs, this.tooltips);
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
          this.childCollFieldChanged(_childIdx, set, this.value);
          break;
        }
      case _MIN:
        {
          this.childCollFieldChanged(_childIdx, set, this.min);
          break;
        }
      case _MAX:
        {
          this.childCollFieldChanged(_childIdx, set, this.max);
          break;
        }
      case _TARGET:
        {
          this.childCollFieldChanged(_childIdx, set, this.target);
          break;
        }
      case _TOOLTIPS:
        {
          this.childCollFieldChanged(_childIdx, set, this.tooltips);
          break;
        }
      default:
        {
          super._handleChildChange(_childIdx, set);
        }
    }
  }
}
