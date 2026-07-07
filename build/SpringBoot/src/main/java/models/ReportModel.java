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

public class ReportModel extends CreatableObject {
  public static final int _NAME = 0;
  public static final int _PROPERTIES = 1;
  private String name;
  private List<ReportProperty> properties = D3EPersistanceList.child(this, _PROPERTIES);

  public ReportModel() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportModel;
  }

  @Override
  public String _type() {
    return "ReportModel";
  }

  @Override
  public int _fieldsCount() {
    return 2;
  }

  public void addToProperties(ReportProperty val, long index) {
    if (index == -1) {
      this.properties.add(val);
    } else {
      this.properties.add(((int) index), val);
    }
  }

  public void removeFromProperties(ReportProperty val) {
    val._clearChildIdx();
    this.properties.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportProperty obj : this.getProperties()) {
      visitor.accept(obj);
      obj.setMasterReportModel(this);
      obj._setChildIdx(_PROPERTIES);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportProperty obj : this.getProperties()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public String getName() {
    _checkProxy();
    return this.name;
  }

  public void setName(String name) {
    _checkProxy();
    if (Objects.equals(this.name, name)) {
      return;
    }
    fieldChanged(_NAME, this.name, name);
    this.name = name;
  }

  public List<ReportProperty> getProperties() {
    return this.properties;
  }

  public void setProperties(List<ReportProperty> properties) {
    if (Objects.equals(this.properties, properties)) {
      return;
    }
    ((D3EPersistanceList<ReportProperty>) this.properties).setAll(properties);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportModel && super.equals(a);
  }

  public ReportModel deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(properties);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportModel _obj = ((ReportModel) dbObj);
    _obj.setName(name);
    ctx.cloneChildList(properties, (v) -> _obj.setProperties(v));
  }

  public ReportModel cloneInstance(ReportModel cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportModel();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setProperties(
        this.getProperties().stream()
            .map((ReportProperty colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public boolean transientModel() {
    return true;
  }

  public ReportModel createNewInstance() {
    return new ReportModel();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.properties);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _PROPERTIES:
        {
          this.childCollFieldChanged(_childIdx, set, this.properties);
          break;
        }
    }
  }
}
