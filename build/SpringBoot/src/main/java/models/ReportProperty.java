package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ReportProperty extends DatabaseObject {
  public static final int _NAME = 0;
  public static final int _PROPERTY = 1;
  public static final int _TYPE = 2;
  public static final int _CHILD = 3;
  public static final int _COLLECTION = 4;
  public static final int _ISENUM = 5;
  public static final int _ISREFERENCE = 6;
  private String name;
  private String property;
  private String type;
  private boolean child = false;
  private boolean collection = false;
  private boolean isEnum = false;
  private boolean isReference = false;
  private ReportModel masterReportModel;
  private transient ReportProperty old;

  public ReportProperty() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportProperty;
  }

  @Override
  public String _type() {
    return "ReportProperty";
  }

  @Override
  public int _fieldsCount() {
    return 7;
  }

  public DatabaseObject _masterObject() {
    DatabaseObject master = super._masterObject();
    if (master != null) {
      return master;
    }
    if (masterReportModel != null) {
      return masterReportModel;
    }
    return null;
  }

  public void _setMasterObject(DBObject master) {
    super._setMasterObject(master);
    if (master instanceof ReportModel) {
      masterReportModel = ((ReportModel) master);
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
    if (masterReportModel != null) {
      masterReportModel.updateFlat(obj);
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

  public String getProperty() {
    _checkProxy();
    return this.property;
  }

  public void setProperty(String property) {
    _checkProxy();
    if (Objects.equals(this.property, property)) {
      return;
    }
    fieldChanged(_PROPERTY, this.property, property);
    this.property = property;
  }

  public String getType() {
    _checkProxy();
    return this.type;
  }

  public void setType(String type) {
    _checkProxy();
    if (Objects.equals(this.type, type)) {
      return;
    }
    fieldChanged(_TYPE, this.type, type);
    this.type = type;
  }

  public boolean isChild() {
    _checkProxy();
    return this.child;
  }

  public void setChild(boolean child) {
    _checkProxy();
    if (Objects.equals(this.child, child)) {
      return;
    }
    fieldChanged(_CHILD, this.child, child);
    this.child = child;
  }

  public boolean isCollection() {
    _checkProxy();
    return this.collection;
  }

  public void setCollection(boolean collection) {
    _checkProxy();
    if (Objects.equals(this.collection, collection)) {
      return;
    }
    fieldChanged(_COLLECTION, this.collection, collection);
    this.collection = collection;
  }

  public boolean isIsEnum() {
    _checkProxy();
    return this.isEnum;
  }

  public void setIsEnum(boolean isEnum) {
    _checkProxy();
    if (Objects.equals(this.isEnum, isEnum)) {
      return;
    }
    fieldChanged(_ISENUM, this.isEnum, isEnum);
    this.isEnum = isEnum;
  }

  public boolean isIsReference() {
    _checkProxy();
    return this.isReference;
  }

  public void setIsReference(boolean isReference) {
    _checkProxy();
    if (Objects.equals(this.isReference, isReference)) {
      return;
    }
    fieldChanged(_ISREFERENCE, this.isReference, isReference);
    this.isReference = isReference;
  }

  public ReportModel getMasterReportModel() {
    return this.masterReportModel;
  }

  public void setMasterReportModel(ReportModel masterReportModel) {
    this.masterReportModel = masterReportModel;
  }

  public ReportProperty getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportProperty) old);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportProperty && super.equals(a);
  }

  public ReportProperty deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportProperty _obj = ((ReportProperty) dbObj);
    _obj.setName(name);
    _obj.setProperty(property);
    _obj.setType(type);
    _obj.setChild(child);
    _obj.setCollection(collection);
    _obj.setIsEnum(isEnum);
    _obj.setIsReference(isReference);
  }

  public ReportProperty cloneInstance(ReportProperty cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportProperty();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setProperty(this.getProperty());
    cloneObj.setType(this.getType());
    cloneObj.setChild(this.isChild());
    cloneObj.setCollection(this.isCollection());
    cloneObj.setIsEnum(this.isIsEnum());
    cloneObj.setIsReference(this.isIsReference());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public boolean transientModel() {
    return true;
  }

  public ReportProperty createNewInstance() {
    return new ReportProperty();
  }

  public boolean needOldObject() {
    return true;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
