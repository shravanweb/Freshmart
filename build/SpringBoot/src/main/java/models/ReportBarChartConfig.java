package models;

import classes.ReportBarChartType;
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

public class ReportBarChartConfig extends ReportBaseConfig {
  public static final int _TYPE = 0;
  public static final int _XAXES = 1;
  public static final int _YAXES = 2;
  public static final int _LEGEND = 3;
  public static final int _SMALLMULTIPLES = 4;
  public static final int _TOOLTIPS = 5;
  @NotNull private ReportBarChartType type = ReportBarChartType.Stacked;
  private transient List<ReportField> xAxes = D3EPersistanceList.child(this, _XAXES);
  private transient List<ReportField> yAxes = D3EPersistanceList.child(this, _YAXES);
  private transient ReportField legend;
  private transient List<ReportField> smallMultiples =
      D3EPersistanceList.child(this, _SMALLMULTIPLES);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportBarChartConfig old;

  public ReportBarChartConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportBarChartConfig;
  }

  @Override
  public String _type() {
    return "ReportBarChartConfig";
  }

  @Override
  public int _fieldsCount() {
    return 6;
  }

  public void addToXAxes(ReportField val, long index) {
    if (index == -1) {
      this.xAxes.add(val);
    } else {
      this.xAxes.add(((int) index), val);
    }
  }

  public void removeFromXAxes(ReportField val) {
    val._clearChildIdx();
    this.xAxes.remove(val);
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

  public void addToSmallMultiples(ReportField val, long index) {
    if (index == -1) {
      this.smallMultiples.add(val);
    } else {
      this.smallMultiples.add(((int) index), val);
    }
  }

  public void removeFromSmallMultiples(ReportField val) {
    val._clearChildIdx();
    this.smallMultiples.remove(val);
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
    for (ReportField obj : this.getXAxes()) {
      visitor.accept(obj);
      obj.setMasterReportBarChartConfig(this);
      obj._setChildIdx(_XAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getYAxes()) {
      visitor.accept(obj);
      obj.setMasterReportBarChartConfig(this);
      obj._setChildIdx(_YAXES);
      obj.updateMasters(visitor);
    }
    if (legend != null) {
      visitor.accept(legend);
      legend.setMasterReportBarChartConfig(this);
      legend.updateMasters(visitor);
    }
    for (ReportField obj : this.getSmallMultiples()) {
      visitor.accept(obj);
      obj.setMasterReportBarChartConfig(this);
      obj._setChildIdx(_SMALLMULTIPLES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportBarChartConfig(this);
      obj._setChildIdx(_TOOLTIPS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getXAxes()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getYAxes()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    if (legend != null) {
      visitor.accept(legend);
      legend.visitChildren(visitor);
    }
    for (ReportField obj : this.getSmallMultiples()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public ReportBarChartType getType() {
    _checkProxy();
    return this.type;
  }

  public void setType(ReportBarChartType type) {
    _checkProxy();
    if (Objects.equals(this.type, type)) {
      return;
    }
    fieldChanged(_TYPE, this.type, type);
    this.type = type;
  }

  public List<ReportField> getXAxes() {
    return this.xAxes;
  }

  public void setXAxes(List<ReportField> xAxes) {
    if (Objects.equals(this.xAxes, xAxes)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.xAxes).setAll(xAxes);
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

  public ReportField getLegend() {
    _checkProxy();
    return this.legend;
  }

  public void setLegend(ReportField legend) {
    _checkProxy();
    if (Objects.equals(this.legend, legend)) {
      if (this.legend != null) {
        this.legend._updateChanges();
      }
      return;
    }
    fieldChanged(_LEGEND, this.legend, legend);
    this.legend = legend;
    if (this.legend != null) {
      this.legend.setMasterReportBarChartConfig(this);
      this.legend._setChildIdx(_LEGEND);
      this.legend._updateChanges();
    }
  }

  public List<ReportField> getSmallMultiples() {
    return this.smallMultiples;
  }

  public void setSmallMultiples(List<ReportField> smallMultiples) {
    if (Objects.equals(this.smallMultiples, smallMultiples)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.smallMultiples).setAll(smallMultiples);
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

  public ReportBarChartConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportBarChartConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getXAxes().forEach((one) -> one.recordOld(ctx));
    this.getYAxes().forEach((one) -> one.recordOld(ctx));
    if (this.getLegend() != null) {
      this.getLegend().recordOld(ctx);
    }
    this.getSmallMultiples().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportBarChartConfig && super.equals(a);
  }

  public ReportBarChartConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(xAxes);
    ctx.collectChilds(yAxes);
    ctx.collectChild(legend);
    ctx.collectChilds(smallMultiples);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportBarChartConfig _obj = ((ReportBarChartConfig) dbObj);
    _obj.setType(type);
    ctx.cloneChildList(xAxes, (v) -> _obj.setXAxes(v));
    ctx.cloneChildList(yAxes, (v) -> _obj.setYAxes(v));
    ctx.cloneChild(legend, (v) -> _obj.setLegend(v));
    ctx.cloneChildList(smallMultiples, (v) -> _obj.setSmallMultiples(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportBarChartConfig cloneInstance(ReportBarChartConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportBarChartConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setType(this.getType());
    cloneObj.setXAxes(
        this.getXAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setYAxes(
        this.getYAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setLegend(this.getLegend() == null ? null : this.getLegend().cloneInstance(null));
    cloneObj.setSmallMultiples(
        this.getSmallMultiples().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTooltips(
        this.getTooltips().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportBarChartConfig createNewInstance() {
    return new ReportBarChartConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.xAxes);
    Database.collectCollctionCreatableReferences(_refs, this.yAxes);
    Database.collectCreatableReferences(_refs, this.legend);
    Database.collectCollctionCreatableReferences(_refs, this.smallMultiples);
    Database.collectCollctionCreatableReferences(_refs, this.tooltips);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _XAXES:
        {
          this.childCollFieldChanged(_childIdx, set, this.xAxes);
          break;
        }
      case _YAXES:
        {
          this.childCollFieldChanged(_childIdx, set, this.yAxes);
          break;
        }
      case _LEGEND:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
      case _SMALLMULTIPLES:
        {
          this.childCollFieldChanged(_childIdx, set, this.smallMultiples);
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
