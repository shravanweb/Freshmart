package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;

public abstract class ReportBaseConfig extends DatabaseObject {
  private Report masterReport;

  public ReportBaseConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportBaseConfig;
  }

  @Override
  public String _type() {
    return "ReportBaseConfig";
  }

  @Override
  public int _fieldsCount() {
    return 0;
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
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public void updateFlat(DatabaseObject obj) {
    super.updateFlat(obj);
    if (masterReport != null) {
      masterReport.updateFlat(obj);
    }
  }

  public Report getMasterReport() {
    return this.masterReport;
  }

  public void setMasterReport(Report masterReport) {
    this.masterReport = masterReport;
  }

  public String displayName() {
    return "ReportBaseConfig";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportBaseConfig && super.equals(a);
  }

  public ReportBaseConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public ReportBaseConfig cloneInstance(ReportBaseConfig cloneObj) {
    super.cloneInstance(cloneObj);
    return cloneObj;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
