package models;

import classes.ReportCellType;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.Database;
import store.DatabaseObject;
import store.ICloneable;

public class ReportCell extends DatabaseObject {
  public static final int _TYPE = 0;
  public static final int _X = 1;
  public static final int _Y = 2;
  public static final int _STYLE = 3;
  public static final int _VALUE = 4;
  private ReportCellType type = ReportCellType.Data;
  private long x = 0l;
  private long y = 0l;
  private transient ReportCellStyle style;
  private String value;
  private Report masterReport;
  private transient ReportCell old;

  public ReportCell() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportCell;
  }

  @Override
  public String _type() {
    return "ReportCell";
  }

  @Override
  public int _fieldsCount() {
    return 5;
  }

  public DatabaseObject _masterObject() {
    DatabaseObject master = super._masterObject();
    if (master != null) {
      return master;
    }
    if (masterReport != null) {
      return masterReport;
    }
    return null;
  }

  public void _setMasterObject(DBObject master) {
    super._setMasterObject(master);
    if (master instanceof Report) {
      masterReport = ((Report) master);
    }
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    if (style != null) {
      visitor.accept(style);
      style.setMasterReportCell(this);
      style.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    if (style != null) {
      visitor.accept(style);
      style.visitChildren(visitor);
    }
  }

  public void updateFlat(DatabaseObject obj) {
    super.updateFlat(obj);
    if (masterReport != null) {
      masterReport.updateFlat(obj);
    }
  }

  public ReportCellType getType() {
    _checkProxy();
    return this.type;
  }

  public void setType(ReportCellType type) {
    _checkProxy();
    if (Objects.equals(this.type, type)) {
      return;
    }
    fieldChanged(_TYPE, this.type, type);
    this.type = type;
  }

  public long getX() {
    _checkProxy();
    return this.x;
  }

  public void setX(long x) {
    _checkProxy();
    if (Objects.equals(this.x, x)) {
      return;
    }
    fieldChanged(_X, this.x, x);
    this.x = x;
  }

  public long getY() {
    _checkProxy();
    return this.y;
  }

  public void setY(long y) {
    _checkProxy();
    if (Objects.equals(this.y, y)) {
      return;
    }
    fieldChanged(_Y, this.y, y);
    this.y = y;
  }

  public ReportCellStyle getStyle() {
    _checkProxy();
    return this.style;
  }

  public void setStyle(ReportCellStyle style) {
    _checkProxy();
    if (Objects.equals(this.style, style)) {
      if (this.style != null) {
        this.style._updateChanges();
      }
      return;
    }
    fieldChanged(_STYLE, this.style, style);
    this.style = style;
    if (this.style != null) {
      this.style.setMasterReportCell(this);
      this.style._setChildIdx(_STYLE);
      this.style._updateChanges();
    }
  }

  public String getValue() {
    _checkProxy();
    return this.value;
  }

  public void setValue(String value) {
    _checkProxy();
    if (Objects.equals(this.value, value)) {
      return;
    }
    fieldChanged(_VALUE, this.value, value);
    this.value = value;
  }

  public Report getMasterReport() {
    return this.masterReport;
  }

  public void setMasterReport(Report masterReport) {
    this.masterReport = masterReport;
  }

  public ReportCell getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportCell) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    if (this.getStyle() != null) {
      this.getStyle().recordOld(ctx);
    }
  }

  public String displayName() {
    return "ReportCell";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportCell && super.equals(a);
  }

  public ReportCell deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChild(style);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportCell _obj = ((ReportCell) dbObj);
    _obj.setType(type);
    _obj.setX(x);
    _obj.setY(y);
    ctx.cloneChild(style, (v) -> _obj.setStyle(v));
    _obj.setValue(value);
  }

  public ReportCell cloneInstance(ReportCell cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportCell();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setType(this.getType());
    cloneObj.setX(this.getX());
    cloneObj.setY(this.getY());
    cloneObj.setStyle(this.getStyle() == null ? null : this.getStyle().cloneInstance(null));
    cloneObj.setValue(this.getValue());
    return cloneObj;
  }

  public ReportCell createNewInstance() {
    return new ReportCell();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCreatableReferences(_refs, this.style);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _STYLE:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
    }
  }
}
