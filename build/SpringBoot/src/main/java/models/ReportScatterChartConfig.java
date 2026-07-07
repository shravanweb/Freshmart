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

public class ReportScatterChartConfig extends ReportBaseConfig {
  public static final int _VALUES = 0;
  public static final int _XAXES = 1;
  public static final int _YAXES = 2;
  public static final int _SIZE = 3;
  public static final int _LEGENDS = 4;
  public static final int _PLAYAXIS = 5;
  public static final int _TOOLTIPS = 6;
  private transient List<ReportField> values = D3EPersistanceList.child(this, _VALUES);
  private transient List<ReportField> xAxes = D3EPersistanceList.child(this, _XAXES);
  private transient List<ReportField> yAxes = D3EPersistanceList.child(this, _YAXES);
  private transient List<ReportField> size = D3EPersistanceList.child(this, _SIZE);
  private transient List<ReportField> legends = D3EPersistanceList.child(this, _LEGENDS);
  private transient List<ReportField> playAxis = D3EPersistanceList.child(this, _PLAYAXIS);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportScatterChartConfig old;

  public ReportScatterChartConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportScatterChartConfig;
  }

  @Override
  public String _type() {
    return "ReportScatterChartConfig";
  }

  @Override
  public int _fieldsCount() {
    return 7;
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

  public void addToSize(ReportField val, long index) {
    if (index == -1) {
      this.size.add(val);
    } else {
      this.size.add(((int) index), val);
    }
  }

  public void removeFromSize(ReportField val) {
    val._clearChildIdx();
    this.size.remove(val);
  }

  public void addToLegends(ReportField val, long index) {
    if (index == -1) {
      this.legends.add(val);
    } else {
      this.legends.add(((int) index), val);
    }
  }

  public void removeFromLegends(ReportField val) {
    val._clearChildIdx();
    this.legends.remove(val);
  }

  public void addToPlayAxis(ReportField val, long index) {
    if (index == -1) {
      this.playAxis.add(val);
    } else {
      this.playAxis.add(((int) index), val);
    }
  }

  public void removeFromPlayAxis(ReportField val) {
    val._clearChildIdx();
    this.playAxis.remove(val);
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
    for (ReportField obj : this.getValues()) {
      visitor.accept(obj);
      obj.setMasterReportScatterChartConfig(this);
      obj._setChildIdx(_VALUES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getXAxes()) {
      visitor.accept(obj);
      obj.setMasterReportScatterChartConfig(this);
      obj._setChildIdx(_XAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getYAxes()) {
      visitor.accept(obj);
      obj.setMasterReportScatterChartConfig(this);
      obj._setChildIdx(_YAXES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getSize()) {
      visitor.accept(obj);
      obj.setMasterReportScatterChartConfig(this);
      obj._setChildIdx(_SIZE);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getLegends()) {
      visitor.accept(obj);
      obj.setMasterReportScatterChartConfig(this);
      obj._setChildIdx(_LEGENDS);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getPlayAxis()) {
      visitor.accept(obj);
      obj.setMasterReportScatterChartConfig(this);
      obj._setChildIdx(_PLAYAXIS);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportScatterChartConfig(this);
      obj._setChildIdx(_TOOLTIPS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getValues()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getXAxes()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getYAxes()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getSize()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getLegends()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getPlayAxis()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
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

  public List<ReportField> getSize() {
    return this.size;
  }

  public void setSize(List<ReportField> size) {
    if (Objects.equals(this.size, size)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.size).setAll(size);
  }

  public List<ReportField> getLegends() {
    return this.legends;
  }

  public void setLegends(List<ReportField> legends) {
    if (Objects.equals(this.legends, legends)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.legends).setAll(legends);
  }

  public List<ReportField> getPlayAxis() {
    return this.playAxis;
  }

  public void setPlayAxis(List<ReportField> playAxis) {
    if (Objects.equals(this.playAxis, playAxis)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.playAxis).setAll(playAxis);
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

  public ReportScatterChartConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportScatterChartConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getValues().forEach((one) -> one.recordOld(ctx));
    this.getXAxes().forEach((one) -> one.recordOld(ctx));
    this.getYAxes().forEach((one) -> one.recordOld(ctx));
    this.getSize().forEach((one) -> one.recordOld(ctx));
    this.getLegends().forEach((one) -> one.recordOld(ctx));
    this.getPlayAxis().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportScatterChartConfig && super.equals(a);
  }

  public ReportScatterChartConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(values);
    ctx.collectChilds(xAxes);
    ctx.collectChilds(yAxes);
    ctx.collectChilds(size);
    ctx.collectChilds(legends);
    ctx.collectChilds(playAxis);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportScatterChartConfig _obj = ((ReportScatterChartConfig) dbObj);
    ctx.cloneChildList(values, (v) -> _obj.setValues(v));
    ctx.cloneChildList(xAxes, (v) -> _obj.setXAxes(v));
    ctx.cloneChildList(yAxes, (v) -> _obj.setYAxes(v));
    ctx.cloneChildList(size, (v) -> _obj.setSize(v));
    ctx.cloneChildList(legends, (v) -> _obj.setLegends(v));
    ctx.cloneChildList(playAxis, (v) -> _obj.setPlayAxis(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportScatterChartConfig cloneInstance(ReportScatterChartConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportScatterChartConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setValues(
        this.getValues().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setXAxes(
        this.getXAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setYAxes(
        this.getYAxes().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setSize(
        this.getSize().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setLegends(
        this.getLegends().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setPlayAxis(
        this.getPlayAxis().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTooltips(
        this.getTooltips().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportScatterChartConfig createNewInstance() {
    return new ReportScatterChartConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.values);
    Database.collectCollctionCreatableReferences(_refs, this.xAxes);
    Database.collectCollctionCreatableReferences(_refs, this.yAxes);
    Database.collectCollctionCreatableReferences(_refs, this.size);
    Database.collectCollctionCreatableReferences(_refs, this.legends);
    Database.collectCollctionCreatableReferences(_refs, this.playAxis);
    Database.collectCollctionCreatableReferences(_refs, this.tooltips);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _VALUES:
        {
          this.childCollFieldChanged(_childIdx, set, this.values);
          break;
        }
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
      case _SIZE:
        {
          this.childCollFieldChanged(_childIdx, set, this.size);
          break;
        }
      case _LEGENDS:
        {
          this.childCollFieldChanged(_childIdx, set, this.legends);
          break;
        }
      case _PLAYAXIS:
        {
          this.childCollFieldChanged(_childIdx, set, this.playAxis);
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
