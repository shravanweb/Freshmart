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

public class ReportSlicerConfig extends ReportBaseConfig {
  public static final int _FIELDS = 0;
  private transient List<ReportField> fields = D3EPersistanceList.child(this, _FIELDS);
  private transient ReportSlicerConfig old;

  public ReportSlicerConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportSlicerConfig;
  }

  @Override
  public String _type() {
    return "ReportSlicerConfig";
  }

  @Override
  public int _fieldsCount() {
    return 1;
  }

  public void addToFields(ReportField val, long index) {
    if (index == -1) {
      this.fields.add(val);
    } else {
      this.fields.add(((int) index), val);
    }
  }

  public void removeFromFields(ReportField val) {
    val._clearChildIdx();
    this.fields.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportField obj : this.getFields()) {
      visitor.accept(obj);
      obj.setMasterReportSlicerConfig(this);
      obj._setChildIdx(_FIELDS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getFields()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public List<ReportField> getFields() {
    return this.fields;
  }

  public void setFields(List<ReportField> fields) {
    if (Objects.equals(this.fields, fields)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.fields).setAll(fields);
  }

  public ReportSlicerConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportSlicerConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getFields().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportSlicerConfig && super.equals(a);
  }

  public ReportSlicerConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(fields);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportSlicerConfig _obj = ((ReportSlicerConfig) dbObj);
    ctx.cloneChildList(fields, (v) -> _obj.setFields(v));
  }

  public ReportSlicerConfig cloneInstance(ReportSlicerConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportSlicerConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setFields(
        this.getFields().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportSlicerConfig createNewInstance() {
    return new ReportSlicerConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.fields);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _FIELDS:
        {
          this.childCollFieldChanged(_childIdx, set, this.fields);
          break;
        }
      default:
        {
          super._handleChildChange(_childIdx, set);
        }
    }
  }
}
