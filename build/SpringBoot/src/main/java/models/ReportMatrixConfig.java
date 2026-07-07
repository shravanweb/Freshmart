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

public class ReportMatrixConfig extends ReportBaseConfig {
  public static final int _COLUMNS = 0;
  public static final int _ROWS = 1;
  public static final int _VALUES = 2;
  private transient List<ReportField> columns = D3EPersistanceList.child(this, _COLUMNS);
  private transient List<ReportField> rows = D3EPersistanceList.child(this, _ROWS);
  private transient List<ReportField> values = D3EPersistanceList.child(this, _VALUES);
  private transient ReportMatrixConfig old;

  public ReportMatrixConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportMatrixConfig;
  }

  @Override
  public String _type() {
    return "ReportMatrixConfig";
  }

  @Override
  public int _fieldsCount() {
    return 3;
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

  public void addToRows(ReportField val, long index) {
    if (index == -1) {
      this.rows.add(val);
    } else {
      this.rows.add(((int) index), val);
    }
  }

  public void removeFromRows(ReportField val) {
    val._clearChildIdx();
    this.rows.remove(val);
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
    for (ReportField obj : this.getColumns()) {
      visitor.accept(obj);
      obj.setMasterReportMatrixConfig(this);
      obj._setChildIdx(_COLUMNS);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getRows()) {
      visitor.accept(obj);
      obj.setMasterReportMatrixConfig(this);
      obj._setChildIdx(_ROWS);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getValues()) {
      visitor.accept(obj);
      obj.setMasterReportMatrixConfig(this);
      obj._setChildIdx(_VALUES);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getColumns()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getRows()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getValues()) {
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

  public List<ReportField> getRows() {
    return this.rows;
  }

  public void setRows(List<ReportField> rows) {
    if (Objects.equals(this.rows, rows)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.rows).setAll(rows);
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

  public ReportMatrixConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportMatrixConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getColumns().forEach((one) -> one.recordOld(ctx));
    this.getRows().forEach((one) -> one.recordOld(ctx));
    this.getValues().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportMatrixConfig && super.equals(a);
  }

  public ReportMatrixConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(columns);
    ctx.collectChilds(rows);
    ctx.collectChilds(values);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportMatrixConfig _obj = ((ReportMatrixConfig) dbObj);
    ctx.cloneChildList(columns, (v) -> _obj.setColumns(v));
    ctx.cloneChildList(rows, (v) -> _obj.setRows(v));
    ctx.cloneChildList(values, (v) -> _obj.setValues(v));
  }

  public ReportMatrixConfig cloneInstance(ReportMatrixConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportMatrixConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setColumns(
        this.getColumns().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setRows(
        this.getRows().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setValues(
        this.getValues().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportMatrixConfig createNewInstance() {
    return new ReportMatrixConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.columns);
    Database.collectCollctionCreatableReferences(_refs, this.rows);
    Database.collectCollctionCreatableReferences(_refs, this.values);
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
      case _ROWS:
        {
          this.childCollFieldChanged(_childIdx, set, this.rows);
          break;
        }
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
