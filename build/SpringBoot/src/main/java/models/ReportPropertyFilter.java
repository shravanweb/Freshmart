package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ReportPropertyFilter extends ReportFilter {
  public static final int _NAME = 0;
  public static final int _PROPERTY = 1;
  public static final int _TYPE = 2;
  public static final int _ISENUM = 3;
  public static final int _ISREFERENCE = 4;
  public static final int _ALLOWMULTIPLE = 5;
  public static final int _APPLYRANGE = 6;
  private String name;
  private String property;
  private String type;
  private boolean isEnum = false;
  private boolean isReference = false;
  private boolean allowMultiple = false;
  private boolean applyRange = false;
  private transient ReportPropertyFilter old;

  public ReportPropertyFilter() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportPropertyFilter;
  }

  @Override
  public String _type() {
    return "ReportPropertyFilter";
  }

  @Override
  public int _fieldsCount() {
    return 7;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
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

  public boolean isAllowMultiple() {
    _checkProxy();
    return this.allowMultiple;
  }

  public void setAllowMultiple(boolean allowMultiple) {
    _checkProxy();
    if (Objects.equals(this.allowMultiple, allowMultiple)) {
      return;
    }
    fieldChanged(_ALLOWMULTIPLE, this.allowMultiple, allowMultiple);
    this.allowMultiple = allowMultiple;
  }

  public boolean isApplyRange() {
    _checkProxy();
    return this.applyRange;
  }

  public void setApplyRange(boolean applyRange) {
    _checkProxy();
    if (Objects.equals(this.applyRange, applyRange)) {
      return;
    }
    fieldChanged(_APPLYRANGE, this.applyRange, applyRange);
    this.applyRange = applyRange;
  }

  public ReportPropertyFilter getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportPropertyFilter) old);
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportPropertyFilter && super.equals(a);
  }

  public ReportPropertyFilter deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportPropertyFilter _obj = ((ReportPropertyFilter) dbObj);
    _obj.setName(name);
    _obj.setProperty(property);
    _obj.setType(type);
    _obj.setIsEnum(isEnum);
    _obj.setIsReference(isReference);
    _obj.setAllowMultiple(allowMultiple);
    _obj.setApplyRange(applyRange);
  }

  public ReportPropertyFilter cloneInstance(ReportPropertyFilter cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportPropertyFilter();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setProperty(this.getProperty());
    cloneObj.setType(this.getType());
    cloneObj.setIsEnum(this.isIsEnum());
    cloneObj.setIsReference(this.isIsReference());
    cloneObj.setAllowMultiple(this.isAllowMultiple());
    cloneObj.setApplyRange(this.isApplyRange());
    return cloneObj;
  }

  public ReportPropertyFilter createNewInstance() {
    return new ReportPropertyFilter();
  }

  public boolean needOldObject() {
    return true;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    super._handleChildChange(_childIdx, set);
  }
}
