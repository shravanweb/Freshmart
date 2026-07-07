package models;

import classes.ReportLineAndColumnChartType;
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

public class ReportLineAndColumnChartConfig extends ReportBaseConfig {
  public static final int _TYPE = 0;
  public static final int _XAXES = 1;
  public static final int _COLUMNYAXES = 2;
  public static final int _LINEYAXES = 3;
  public static final int _COLUMNLEGEND = 4;
  public static final int _SMALLMULTIPLES = 5;
  public static final int _TOOLTIPS = 6;
  private ReportLineAndColumnChartType type = ReportLineAndColumnChartType.Stacked;
  private transient List<ReportField> xAxes = D3EPersistanceList.child(this, _XAXES);
  private transient List<ReportField> columnYAxes = D3EPersistanceList.child(this, _COLUMNYAXES);
  private transient List<ReportField> lineYAxes = D3EPersistanceList.child(this, _LINEYAXES);
  private transient List<ReportField> columnLegend = D3EPersistanceList.child(this, _COLUMNLEGEND);
  private transient List<ReportField> smallMultiples =
      D3EPersistanceList.child(this, _SMALLMULTIPLES);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportLineAndColumnChartConfig old;

  public ReportLineAndColumnChartConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportLineAndColumnChartConfig;
  }

  @Override
  public String _type() {
    return "ReportLineAndColumnChartConfig";
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

  public void addToColumnYAxes(ReportField val, long index) {
    if (index == -1) {
      this.columnYAxes.add(val);
    } else {
      this.columnYAxes.add(((int) index), val);
    }
  }

  public void removeFromColumnYAxes(ReportField val) {
    val._clearChildIdx();
    this.columnYAxes.remove(val);
  }

  public void addToLineYAxes(ReportField val, long index) {
    if (index == -1) {
      this.lineYAxes.add(val);
    } else {
      this.lineYAxes.add(((int) index), val);
    }
  }

  public void removeFromLineYAxes(ReportField val) {
    val._clearChildIdx();
    this.lineYAxes.remove(val);
  }

  public void addToColumnLegend(ReportField val, long index) {
    if (index == -1) {
      this.columnLegend.add(val);
    } else {
      this.columnLegend.add(((int) index), val);
    }
  }

  public void removeFromColumnLegend(ReportField val) {
    val._clearChildIdx();
    this.columnLegend.remove(val);
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
      obj.setMasterReportLineAndColumnChartConfig(this);
      obj._setChildIdx(_XAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getColumnYAxes()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndColumnChartConfig(this);
      obj._setChildIdx(_COLUMNYAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getLineYAxes()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndColumnChartConfig(this);
      obj._setChildIdx(_LINEYAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getColumnLegend()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndColumnChartConfig(this);
      obj._setChildIdx(_COLUMNLEGEND);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getSmallMultiples()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndColumnChartConfig(this);
      obj._setChildIdx(_SMALLMULTIPLES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportLineAndColumnChartConfig(this);
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
    for (ReportField obj : this.getColumnYAxes()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getLineYAxes()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getColumnLegend()) {
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

  public ReportLineAndColumnChartType getType() {
    _checkProxy();
    return this.type;
  }

  public void setType(ReportLineAndColumnChartType type) {
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

  public List<ReportField> getColumnYAxes() {
    return this.columnYAxes;
  }

  public void setColumnYAxes(List<ReportField> columnYAxes) {
    if (Objects.equals(this.columnYAxes, columnYAxes)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.columnYAxes).setAll(columnYAxes);
  }

  public List<ReportField> getLineYAxes() {
    return this.lineYAxes;
  }

  public void setLineYAxes(List<ReportField> lineYAxes) {
    if (Objects.equals(this.lineYAxes, lineYAxes)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.lineYAxes).setAll(lineYAxes);
  }

  public List<ReportField> getColumnLegend() {
    return this.columnLegend;
  }

  public void setColumnLegend(List<ReportField> columnLegend) {
    if (Objects.equals(this.columnLegend, columnLegend)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.columnLegend).setAll(columnLegend);
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

  public ReportLineAndColumnChartConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportLineAndColumnChartConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getXAxes().forEach((one) -> one.recordOld(ctx));
    this.getColumnYAxes().forEach((one) -> one.recordOld(ctx));
    this.getLineYAxes().forEach((one) -> one.recordOld(ctx));
    this.getColumnLegend().forEach((one) -> one.recordOld(ctx));
    this.getSmallMultiples().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportLineAndColumnChartConfig && super.equals(a);
  }

  public ReportLineAndColumnChartConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(xAxes);
    ctx.collectChilds(columnYAxes);
    ctx.collectChilds(lineYAxes);
    ctx.collectChilds(columnLegend);
    ctx.collectChilds(smallMultiples);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportLineAndColumnChartConfig _obj = ((ReportLineAndColumnChartConfig) dbObj);
    _obj.setType(type);
    ctx.cloneChildList(xAxes, (v) -> _obj.setXAxes(v));
    ctx.cloneChildList(columnYAxes, (v) -> _obj.setColumnYAxes(v));
    ctx.cloneChildList(lineYAxes, (v) -> _obj.setLineYAxes(v));
    ctx.cloneChildList(columnLegend, (v) -> _obj.setColumnLegend(v));
    ctx.cloneChildList(smallMultiples, (v) -> _obj.setSmallMultiples(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportLineAndColumnChartConfig cloneInstance(ReportLineAndColumnChartConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportLineAndColumnChartConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setType(this.getType());
    cloneObj.setXAxes(
        this.getXAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setColumnYAxes(
        this.getColumnYAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setLineYAxes(
        this.getLineYAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setColumnLegend(
        this.getColumnLegend().stream()
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

  public ReportLineAndColumnChartConfig createNewInstance() {
    return new ReportLineAndColumnChartConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.xAxes);
    Database.collectCollctionCreatableReferences(_refs, this.columnYAxes);
    Database.collectCollctionCreatableReferences(_refs, this.lineYAxes);
    Database.collectCollctionCreatableReferences(_refs, this.columnLegend);
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
      case _COLUMNYAXES:
        {
          this.childCollFieldChanged(_childIdx, set, this.columnYAxes);
          break;
        }
      case _LINEYAXES:
        {
          this.childCollFieldChanged(_childIdx, set, this.lineYAxes);
          break;
        }
      case _COLUMNLEGEND:
        {
          this.childCollFieldChanged(_childIdx, set, this.columnLegend);
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
