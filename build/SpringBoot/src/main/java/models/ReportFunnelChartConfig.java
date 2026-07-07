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

public class ReportFunnelChartConfig extends ReportBaseConfig {
  public static final int _CATEGORYFIELDS = 0;
  public static final int _VALUES = 1;
  public static final int _TOOLTIPS = 2;
  private transient List<ReportField> categoryFields =
      D3EPersistanceList.child(this, _CATEGORYFIELDS);
  private transient List<ReportField> values = D3EPersistanceList.child(this, _VALUES);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportFunnelChartConfig old;

  public ReportFunnelChartConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportFunnelChartConfig;
  }

  @Override
  public String _type() {
    return "ReportFunnelChartConfig";
  }

  @Override
  public int _fieldsCount() {
    return 3;
  }

  public void addToCategoryFields(ReportField val, long index) {
    if (index == -1) {
      this.categoryFields.add(val);
    } else {
      this.categoryFields.add(((int) index), val);
    }
  }

  public void removeFromCategoryFields(ReportField val) {
    val._clearChildIdx();
    this.categoryFields.remove(val);
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
    for (ReportField obj : this.getCategoryFields()) {
      visitor.accept(obj);
      obj.setMasterReportFunnelChartConfig(this);
      obj._setChildIdx(_CATEGORYFIELDS);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getValues()) {
      visitor.accept(obj);
      obj.setMasterReportFunnelChartConfig(this);
      obj._setChildIdx(_VALUES);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportFunnelChartConfig(this);
      obj._setChildIdx(_TOOLTIPS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getCategoryFields()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getValues()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public List<ReportField> getCategoryFields() {
    return this.categoryFields;
  }

  public void setCategoryFields(List<ReportField> categoryFields) {
    if (Objects.equals(this.categoryFields, categoryFields)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.categoryFields).setAll(categoryFields);
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

  public List<ReportField> getTooltips() {
    return this.tooltips;
  }

  public void setTooltips(List<ReportField> tooltips) {
    if (Objects.equals(this.tooltips, tooltips)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.tooltips).setAll(tooltips);
  }

  public ReportFunnelChartConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportFunnelChartConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getCategoryFields().forEach((one) -> one.recordOld(ctx));
    this.getValues().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportFunnelChartConfig && super.equals(a);
  }

  public ReportFunnelChartConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(categoryFields);
    ctx.collectChilds(values);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportFunnelChartConfig _obj = ((ReportFunnelChartConfig) dbObj);
    ctx.cloneChildList(categoryFields, (v) -> _obj.setCategoryFields(v));
    ctx.cloneChildList(values, (v) -> _obj.setValues(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportFunnelChartConfig cloneInstance(ReportFunnelChartConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportFunnelChartConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setCategoryFields(
        this.getCategoryFields().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setValues(
        this.getValues().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTooltips(
        this.getTooltips().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportFunnelChartConfig createNewInstance() {
    return new ReportFunnelChartConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.categoryFields);
    Database.collectCollctionCreatableReferences(_refs, this.values);
    Database.collectCollctionCreatableReferences(_refs, this.tooltips);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _CATEGORYFIELDS:
        {
          this.childCollFieldChanged(_childIdx, set, this.categoryFields);
          break;
        }
      case _VALUES:
        {
          this.childCollFieldChanged(_childIdx, set, this.values);
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
