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

public class ReportData extends CreatableObject {
  public static final int _SECTIONS = 0;
  public static final int _ROWS = 1;
  private List<ReportDataSection> sections = D3EPersistanceList.child(this, _SECTIONS);
  private List<ReportDataRow> rows = D3EPersistanceList.child(this, _ROWS);

  public ReportData() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportData;
  }

  @Override
  public String _type() {
    return "ReportData";
  }

  @Override
  public int _fieldsCount() {
    return 2;
  }

  public void addToSections(ReportDataSection val, long index) {
    if (index == -1) {
      this.sections.add(val);
    } else {
      this.sections.add(((int) index), val);
    }
  }

  public void removeFromSections(ReportDataSection val) {
    val._clearChildIdx();
    this.sections.remove(val);
  }

  public void addToRows(ReportDataRow val, long index) {
    if (index == -1) {
      this.rows.add(val);
    } else {
      this.rows.add(((int) index), val);
    }
  }

  public void removeFromRows(ReportDataRow val) {
    val._clearChildIdx();
    this.rows.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportDataSection obj : this.getSections()) {
      visitor.accept(obj);
      obj.setMasterReportData(this);
      obj._setChildIdx(_SECTIONS);
      obj.updateMasters(visitor);
    }
    for (ReportDataRow obj : this.getRows()) {
      visitor.accept(obj);
      obj.setMasterReportData(this);
      obj._setChildIdx(_ROWS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportDataSection obj : this.getSections()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportDataRow obj : this.getRows()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public List<ReportDataSection> getSections() {
    return this.sections;
  }

  public void setSections(List<ReportDataSection> sections) {
    if (Objects.equals(this.sections, sections)) {
      return;
    }
    ((D3EPersistanceList<ReportDataSection>) this.sections).setAll(sections);
  }

  public List<ReportDataRow> getRows() {
    return this.rows;
  }

  public void setRows(List<ReportDataRow> rows) {
    if (Objects.equals(this.rows, rows)) {
      return;
    }
    ((D3EPersistanceList<ReportDataRow>) this.rows).setAll(rows);
  }

  public String displayName() {
    return "ReportData";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportData && super.equals(a);
  }

  public ReportData deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(sections);
    ctx.collectChilds(rows);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportData _obj = ((ReportData) dbObj);
    ctx.cloneChildList(sections, (v) -> _obj.setSections(v));
    ctx.cloneChildList(rows, (v) -> _obj.setRows(v));
  }

  public ReportData cloneInstance(ReportData cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportData();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setSections(
        this.getSections().stream()
            .map((ReportDataSection colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setRows(
        this.getRows().stream()
            .map((ReportDataRow colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public ReportData createNewInstance() {
    return new ReportData();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.sections);
    Database.collectCollctionCreatableReferences(_refs, this.rows);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _SECTIONS:
        {
          this.childCollFieldChanged(_childIdx, set, this.sections);
          break;
        }
      case _ROWS:
        {
          this.childCollFieldChanged(_childIdx, set, this.rows);
          break;
        }
    }
  }
}
