package models;

import classes.ReportPieChartType;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import store.D3EPersistanceList;
import store.DBObject;
import store.Database;
import store.DatabaseObject;
import store.ICloneable;

public class ReportPieChartConfig extends ReportBaseConfig {
  public static final int _TYPE = 0;
  public static final int _LEGEND = 1;
  public static final int _VALUES = 2;
  public static final int _DETAILS = 3;
  public static final int _TOOLTIPS = 4;
  @NotNull private ReportPieChartType type = ReportPieChartType.Pie;
  private transient List<ReportField> legend = D3EPersistanceList.child(this, _LEGEND);
  private transient List<ReportField> values = D3EPersistanceList.child(this, _VALUES);
  private transient List<ReportField> details = D3EPersistanceList.child(this, _DETAILS);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportPieChartConfig old;

  public ReportPieChartConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportPieChartConfig;
  }

  @Override
  public String _type() {
    return "ReportPieChartConfig";
  }

  @Override
  public int _fieldsCount() {
    return 5;
  }

  public void addToLegend(ReportField val, long index) {
    if (index == -1) {
      this.legend.add(val);
    } else {
      this.legend.add(((int) index), val);
    }
  }

  public void removeFromLegend(ReportField val) {
    val._clearChildIdx();
    this.legend.remove(val);
  }

  public void addToValues(ReportField val, long index) {
    if (index == -1) {
      this.values.add(val);
    } else {
      this.values.add(((int) index), val);
    }
  }

  public void removeFromValues(ReportField val) {
    val._clearChildIdx();
    this.values.remove(val);
  }

  public void addToDetails(ReportField val, long index) {
    if (index == -1) {
      this.details.add(val);
    } else {
      this.details.add(((int) index), val);
    }
  }

  public void removeFromDetails(ReportField val) {
    val._clearChildIdx();
    this.details.remove(val);
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
    for (ReportField obj : this.getLegend()) {
      visitor.accept(obj);
      obj.setMasterReportPieChartConfig(this);
      obj._setChildIdx(_LEGEND);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getValues()) {
      visitor.accept(obj);
      obj.setMasterReportPieChartConfig(this);
      obj._setChildIdx(_VALUES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getDetails()) {
      visitor.accept(obj);
      obj.setMasterReportPieChartConfig(this);
      obj._setChildIdx(_DETAILS);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportPieChartConfig(this);
      obj._setChildIdx(_TOOLTIPS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getLegend()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getValues()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getDetails()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public ReportPieChartType getType() {
    _checkProxy();
    return this.type;
  }

  public void setType(ReportPieChartType type) {
    _checkProxy();
    if (Objects.equals(this.type, type)) {
      return;
    }
    fieldChanged(_TYPE, this.type, type);
    this.type = type;
  }

  public List<ReportField> getLegend() {
    return this.legend;
  }

  public void setLegend(List<ReportField> legend) {
    if (Objects.equals(this.legend, legend)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.legend).setAll(legend);
  }

  public List<ReportField> getValues() {
    return this.values;
  }

  public void setValues(List<ReportField> values) {
    if (Objects.equals(this.values, values)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.values).setAll(values);
  }

  public List<ReportField> getDetails() {
    return this.details;
  }

  public void setDetails(List<ReportField> details) {
    if (Objects.equals(this.details, details)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.details).setAll(details);
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

  public ReportPieChartConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportPieChartConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getLegend().forEach((one) -> one.recordOld(ctx));
    this.getValues().forEach((one) -> one.recordOld(ctx));
    this.getDetails().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportPieChartConfig && super.equals(a);
  }

  public ReportPieChartConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(legend);
    ctx.collectChilds(values);
    ctx.collectChilds(details);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportPieChartConfig _obj = ((ReportPieChartConfig) dbObj);
    _obj.setType(type);
    ctx.cloneChildList(legend, (v) -> _obj.setLegend(v));
    ctx.cloneChildList(values, (v) -> _obj.setValues(v));
    ctx.cloneChildList(details, (v) -> _obj.setDetails(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportPieChartConfig cloneInstance(ReportPieChartConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportPieChartConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setType(this.getType());
    cloneObj.setLegend(
        this.getLegend().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setValues(
        this.getValues().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setDetails(
        this.getDetails().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTooltips(
        this.getTooltips().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportPieChartConfig createNewInstance() {
    return new ReportPieChartConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.legend);
    Database.collectCollctionCreatableReferences(_refs, this.values);
    Database.collectCollctionCreatableReferences(_refs, this.details);
    Database.collectCollctionCreatableReferences(_refs, this.tooltips);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _LEGEND:
        {
          this.childCollFieldChanged(_childIdx, set, this.legend);
          break;
        }
      case _VALUES:
        {
          this.childCollFieldChanged(_childIdx, set, this.values);
          break;
        }
      case _DETAILS:
        {
          this.childCollFieldChanged(_childIdx, set, this.details);
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
