package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ReportDataRow extends DatabaseObject {
  public static final int _ROW = 0;
  private List<String> row = D3EPersistanceList.primitive(this, _ROW);
  private ReportData masterReportData;
  private transient ReportDataRow old;

  public ReportDataRow() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportDataRow;
  }

  @Override
  public String _type() {
    return "ReportDataRow";
  }

  @Override
  public int _fieldsCount() {
    return 1;
  }

  public DatabaseObject _masterObject() {
    DatabaseObject master = super._masterObject();
    if (master != null) {
      return master;
    }
    if (masterReportData != null) {
      return masterReportData;
    }
    return null;
  }

  public void _setMasterObject(DBObject master) {
    super._setMasterObject(master);
    if (master instanceof ReportData) {
      masterReportData = ((ReportData) master);
    }
  }

  public void addToRow(String val, long index) {
    if (index == -1) {
      this.row.add(val);
    } else {
      this.row.add(((int) index), val);
    }
  }

  public void removeFromRow(String val) {
    this.row.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public void updateFlat(DatabaseObject obj) {
    super.updateFlat(obj);
    if (masterReportData != null) {
      masterReportData.updateFlat(obj);
    }
  }

  public List<String> getRow() {
    return this.row;
  }

  public void setRow(List<String> row) {
    if (Objects.equals(this.row, row)) {
      return;
    }
    ((D3EPersistanceList<String>) this.row).setAll(row);
  }

  public ReportData getMasterReportData() {
    return this.masterReportData;
  }

  public void setMasterReportData(ReportData masterReportData) {
    this.masterReportData = masterReportData;
  }

  public ReportDataRow getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportDataRow) old);
  }

  public String displayName() {
    return "ReportDataRow";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportDataRow && super.equals(a);
  }

  public ReportDataRow deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportDataRow _obj = ((ReportDataRow) dbObj);
    _obj.setRow(row);
  }

  public ReportDataRow cloneInstance(ReportDataRow cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportDataRow();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setRow(new ArrayList<>(this.getRow()));
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public ReportDataRow createNewInstance() {
    return new ReportDataRow();
  }

  public boolean needOldObject() {
    return true;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
