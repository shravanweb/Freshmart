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

public class ReportWaterfallChartConfig extends ReportBaseConfig {
  public static final int _CATEGORY = 0;
  public static final int _BREAKDOWNFIELDS = 1;
  public static final int _YAXES = 2;
  public static final int _TOOLTIPS = 3;
  private transient List<ReportField> category = D3EPersistanceList.child(this, _CATEGORY);
  private transient List<ReportField> breakdownFields =
      D3EPersistanceList.child(this, _BREAKDOWNFIELDS);
  private transient List<ReportField> yAxes = D3EPersistanceList.child(this, _YAXES);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportWaterfallChartConfig old;

  public ReportWaterfallChartConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportWaterfallChartConfig;
  }

  @Override
  public String _type() {
    return "ReportWaterfallChartConfig";
  }

  @Override
  public int _fieldsCount() {
    return 4;
  }

  public void addToCategory(ReportField val, long index) {
    if (index == -1) {
      this.category.add(val);
    } else {
      this.category.add(((int) index), val);
    }
  }

  public void removeFromCategory(ReportField val) {
    val._clearChildIdx();
    this.category.remove(val);
  }

  public void addToBreakdownFields(ReportField val, long index) {
    if (index == -1) {
      this.breakdownFields.add(val);
    } else {
      this.breakdownFields.add(((int) index), val);
    }
  }

  public void removeFromBreakdownFields(ReportField val) {
    val._clearChildIdx();
    this.breakdownFields.remove(val);
  }

  public void addToYAxes(ReportField val, long index) {
    if (index == -1) {
      this.yAxes.add(val);
    } else {
      this.yAxes.add(((int) index), val);
    }
  }

  public void removeFromYAxes(ReportField val) {
    val._clearChildIdx();
    this.yAxes.remove(val);
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
    for (ReportField obj : this.getCategory()) {
      visitor.accept(obj);
      obj.setMasterReportWaterfallChartConfig(this);
      obj._setChildIdx(_CATEGORY);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getBreakdownFields()) {
      visitor.accept(obj);
      obj.setMasterReportWaterfallChartConfig(this);
      obj._setChildIdx(_BREAKDOWNFIELDS);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getYAxes()) {
      visitor.accept(obj);
      obj.setMasterReportWaterfallChartConfig(this);
      obj._setChildIdx(_YAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportWaterfallChartConfig(this);
      obj._setChildIdx(_TOOLTIPS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getCategory()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getBreakdownFields()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getYAxes()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public List<ReportField> getCategory() {
    return this.category;
  }

  public void setCategory(List<ReportField> category) {
    if (Objects.equals(this.category, category)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.category).setAll(category);
  }

  public List<ReportField> getBreakdownFields() {
    return this.breakdownFields;
  }

  public void setBreakdownFields(List<ReportField> breakdownFields) {
    if (Objects.equals(this.breakdownFields, breakdownFields)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.breakdownFields).setAll(breakdownFields);
  }

  public List<ReportField> getYAxes() {
    return this.yAxes;
  }

  public void setYAxes(List<ReportField> yAxes) {
    if (Objects.equals(this.yAxes, yAxes)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.yAxes).setAll(yAxes);
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

  public ReportWaterfallChartConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportWaterfallChartConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getCategory().forEach((one) -> one.recordOld(ctx));
    this.getBreakdownFields().forEach((one) -> one.recordOld(ctx));
    this.getYAxes().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportWaterfallChartConfig && super.equals(a);
  }

  public ReportWaterfallChartConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(category);
    ctx.collectChilds(breakdownFields);
    ctx.collectChilds(yAxes);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportWaterfallChartConfig _obj = ((ReportWaterfallChartConfig) dbObj);
    ctx.cloneChildList(category, (v) -> _obj.setCategory(v));
    ctx.cloneChildList(breakdownFields, (v) -> _obj.setBreakdownFields(v));
    ctx.cloneChildList(yAxes, (v) -> _obj.setYAxes(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportWaterfallChartConfig cloneInstance(ReportWaterfallChartConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportWaterfallChartConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setCategory(
        this.getCategory().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setBreakdownFields(
        this.getBreakdownFields().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setYAxes(
        this.getYAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTooltips(
        this.getTooltips().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportWaterfallChartConfig createNewInstance() {
    return new ReportWaterfallChartConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.category);
    Database.collectCollctionCreatableReferences(_refs, this.breakdownFields);
    Database.collectCollctionCreatableReferences(_refs, this.yAxes);
    Database.collectCollctionCreatableReferences(_refs, this.tooltips);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _CATEGORY:
        {
          this.childCollFieldChanged(_childIdx, set, this.category);
          break;
        }
      case _BREAKDOWNFIELDS:
        {
          this.childCollFieldChanged(_childIdx, set, this.breakdownFields);
          break;
        }
      case _YAXES:
        {
          this.childCollFieldChanged(_childIdx, set, this.yAxes);
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
