package models;

import classes.ReportLineAndAreaChartType;
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

public class ReportLineAndAreaChartConfig extends ReportBaseConfig {
  public static final int _TYPE = 0;
  public static final int _XAXES = 1;
  public static final int _YAXES = 2;
  public static final int _SECONDARYYAXES = 3;
  public static final int _LEGEND = 4;
  public static final int _SMALLMULTIPLES = 5;
  public static final int _TOOLTIPS = 6;
  @NotNull private ReportLineAndAreaChartType type = ReportLineAndAreaChartType.Line;
  private transient List<ReportField> xAxes = D3EPersistanceList.child(this, _XAXES);
  private transient List<ReportField> yAxes = D3EPersistanceList.child(this, _YAXES);
  private transient List<ReportField> secondaryYAxes =
      D3EPersistanceList.child(this, _SECONDARYYAXES);
  private transient List<ReportField> legend = D3EPersistanceList.child(this, _LEGEND);
  private transient List<ReportField> smallMultiples =
      D3EPersistanceList.child(this, _SMALLMULTIPLES);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportLineAndAreaChartConfig old;

  public ReportLineAndAreaChartConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportLineAndAreaChartConfig;
  }

  @Override
  public String _type() {
    return "ReportLineAndAreaChartConfig";
  }

  @Override
  public int _fieldsCount() {
    return 7;
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

  public void addToSecondaryYAxes(ReportField val, long index) {
    if (index == -1) {
      this.secondaryYAxes.add(val);
    } else {
      this.secondaryYAxes.add(((int) index), val);
    }
  }

  public void removeFromSecondaryYAxes(ReportField val) {
    val._clearChildIdx();
    this.secondaryYAxes.remove(val);
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
      obj.setMasterReportLineAndAreaChartConfig(this);
      obj._setChildIdx(_XAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getYAxes()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndAreaChartConfig(this);
      obj._setChildIdx(_YAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getSecondaryYAxes()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndAreaChartConfig(this);
      obj._setChildIdx(_SECONDARYYAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getLegend()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndAreaChartConfig(this);
      obj._setChildIdx(_LEGEND);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getSmallMultiples()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndAreaChartConfig(this);
      obj._setChildIdx(_SMALLMULTIPLES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndAreaChartConfig(this);
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
    for (ReportField obj : this.getSecondaryYAxes()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getLegend()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
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

  public ReportLineAndAreaChartType getType() {
    _checkProxy();
    return this.type;
  }

  public void setType(ReportLineAndAreaChartType type) {
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

  public List<ReportField> getSecondaryYAxes() {
    return this.secondaryYAxes;
  }

  public void setSecondaryYAxes(List<ReportField> secondaryYAxes) {
    if (Objects.equals(this.secondaryYAxes, secondaryYAxes)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.secondaryYAxes).setAll(secondaryYAxes);
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

  public ReportLineAndAreaChartConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportLineAndAreaChartConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getXAxes().forEach((one) -> one.recordOld(ctx));
    this.getYAxes().forEach((one) -> one.recordOld(ctx));
    this.getSecondaryYAxes().forEach((one) -> one.recordOld(ctx));
    this.getLegend().forEach((one) -> one.recordOld(ctx));
    this.getSmallMultiples().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportLineAndAreaChartConfig && super.equals(a);
  }

  public ReportLineAndAreaChartConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(xAxes);
    ctx.collectChilds(yAxes);
    ctx.collectChilds(secondaryYAxes);
    ctx.collectChilds(legend);
    ctx.collectChilds(smallMultiples);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportLineAndAreaChartConfig _obj = ((ReportLineAndAreaChartConfig) dbObj);
    _obj.setType(type);
    ctx.cloneChildList(xAxes, (v) -> _obj.setXAxes(v));
    ctx.cloneChildList(yAxes, (v) -> _obj.setYAxes(v));
    ctx.cloneChildList(secondaryYAxes, (v) -> _obj.setSecondaryYAxes(v));
    ctx.cloneChildList(legend, (v) -> _obj.setLegend(v));
    ctx.cloneChildList(smallMultiples, (v) -> _obj.setSmallMultiples(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportLineAndAreaChartConfig cloneInstance(ReportLineAndAreaChartConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportLineAndAreaChartConfig();
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
    cloneObj.setSecondaryYAxes(
        this.getSecondaryYAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setLegend(
        this.getLegend().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
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

  public ReportLineAndAreaChartConfig createNewInstance() {
    return new ReportLineAndAreaChartConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.xAxes);
    Database.collectCollctionCreatableReferences(_refs, this.yAxes);
    Database.collectCollctionCreatableReferences(_refs, this.secondaryYAxes);
    Database.collectCollctionCreatableReferences(_refs, this.legend);
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
      case _SECONDARYYAXES:
        {
          this.childCollFieldChanged(_childIdx, set, this.secondaryYAxes);
          break;
        }
      case _LEGEND:
        {
          this.childCollFieldChanged(_childIdx, set, this.legend);
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
