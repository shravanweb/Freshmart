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

public class ReportTableConfig extends ReportBaseConfig {
  public static final int _COLUMNS = 0;
  private transient List<ReportField> columns = D3EPersistanceList.child(this, _COLUMNS);
  private transient ReportTableConfig old;

  public ReportTableConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportTableConfig;
  }

  @Override
  public String _type() {
    return "ReportTableConfig";
  }

  @Override
  public int _fieldsCount() {
    return 1;
  }

  public void addToColumns(ReportField val, long index) {
    if (index == -1) {
      this.columns.add(val);
    } else {
      this.columns.add(((int) index), val);
    }
  }

  public void removeFromColumns(ReportField val) {
    val._clearChildIdx();
    this.columns.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportField obj : this.getColumns()) {
      visitor.accept(obj);
      obj.setMasterReportTableConfig(this);
      obj._setChildIdx(_COLUMNS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getColumns()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public List<ReportField> getColumns() {
    return this.columns;
  }

  public void setColumns(List<ReportField> columns) {
    if (Objects.equals(this.columns, columns)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.columns).setAll(columns);
  }

  public ReportTableConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportTableConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getColumns().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportTableConfig && super.equals(a);
  }

  public ReportTableConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(columns);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportTableConfig _obj = ((ReportTableConfig) dbObj);
    ctx.cloneChildList(columns, (v) -> _obj.setColumns(v));
  }

  public ReportTableConfig cloneInstance(ReportTableConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportTableConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setColumns(
        this.getColumns().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportTableConfig createNewInstance() {
    return new ReportTableConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.columns);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _COLUMNS:
        {
          this.childCollFieldChanged(_childIdx, set, this.columns);
          break;
        }
      default:
        {
          super._handleChildChange(_childIdx, set);
        }
    }
  }
}
