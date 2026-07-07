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

public class ReportMultiRowCardConfig extends ReportBaseConfig {
  public static final int _VALUES = 0;
  private transient List<ReportField> values = D3EPersistanceList.child(this, _VALUES);
  private transient ReportMultiRowCardConfig old;

  public ReportMultiRowCardConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportMultiRowCardConfig;
  }

  @Override
  public String _type() {
    return "ReportMultiRowCardConfig";
  }

  @Override
  public int _fieldsCount() {
    return 1;
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

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportField obj : this.getValues()) {
      visitor.accept(obj);
      obj.setMasterReportMultiRowCardConfig(this);
      obj._setChildIdx(_VALUES);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getValues()) {
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

  public ReportMultiRowCardConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportMultiRowCardConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getValues().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportMultiRowCardConfig && super.equals(a);
  }

  public ReportMultiRowCardConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(values);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportMultiRowCardConfig _obj = ((ReportMultiRowCardConfig) dbObj);
    ctx.cloneChildList(values, (v) -> _obj.setValues(v));
  }

  public ReportMultiRowCardConfig cloneInstance(ReportMultiRowCardConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportMultiRowCardConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setValues(
        this.getValues().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportMultiRowCardConfig createNewInstance() {
    return new ReportMultiRowCardConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.values);
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
      default:
        {
          super._handleChildChange(_childIdx, set);
        }
    }
  }
}
