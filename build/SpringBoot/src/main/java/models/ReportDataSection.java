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

public class ReportDataSection extends DatabaseObject {
  public static final int _HEADER = 0;
  public static final int _COLUMNS = 1;
  private String header;
  private List<String> columns = D3EPersistanceList.primitive(this, _COLUMNS);
  private ReportData masterReportData;
  private transient ReportDataSection old;

  public ReportDataSection() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportDataSection;
  }

  @Override
  public String _type() {
    return "ReportDataSection";
  }

  @Override
  public int _fieldsCount() {
    return 2;
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

  public void addToColumns(String val, long index) {
    if (index == -1) {
      this.columns.add(val);
    } else {
      this.columns.add(((int) index), val);
    }
  }

  public void removeFromColumns(String val) {
    this.columns.remove(val);
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

  public String getHeader() {
    _checkProxy();
    return this.header;
  }

  public void setHeader(String header) {
    _checkProxy();
    if (Objects.equals(this.header, header)) {
      return;
    }
    fieldChanged(_HEADER, this.header, header);
    this.header = header;
  }

  public List<String> getColumns() {
    return this.columns;
  }

  public void setColumns(List<String> columns) {
    if (Objects.equals(this.columns, columns)) {
      return;
    }
    ((D3EPersistanceList<String>) this.columns).setAll(columns);
  }

  public ReportData getMasterReportData() {
    return this.masterReportData;
  }

  public void setMasterReportData(ReportData masterReportData) {
    this.masterReportData = masterReportData;
  }

  public ReportDataSection getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportDataSection) old);
  }

  public String displayName() {
    return "ReportDataSection";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportDataSection && super.equals(a);
  }

  public ReportDataSection deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportDataSection _obj = ((ReportDataSection) dbObj);
    _obj.setHeader(header);
    _obj.setColumns(columns);
  }

  public ReportDataSection cloneInstance(ReportDataSection cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportDataSection();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setHeader(this.getHeader());
    cloneObj.setColumns(new ArrayList<>(this.getColumns()));
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public ReportDataSection createNewInstance() {
    return new ReportDataSection();
  }

  public boolean needOldObject() {
    return true;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
