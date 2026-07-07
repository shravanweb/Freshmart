package models;

import classes.ReportFieldFromType;
import classes.ReportRuleOperator;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ReportSingleRule extends ReportRule {
  public static final int _FIELD = 1;
  public static final int _TYPE = 2;
  public static final int _OPERATOR = 3;
  public static final int _VALUE1 = 4;
  public static final int _VALUE2 = 5;
  public static final int _FILTER = 6;
  public static final int _FIELDVALUE1 = 7;
  public static final int _FIELDVALUE2 = 8;
  public static final int _FIELDFROM = 9;
  private String field;
  private String type;
  private ReportRuleOperator operator = ReportRuleOperator.Equal;
  private String value1;
  private String value2;
  private transient ReportFilter filter;
  private String fieldValue1;
  private String fieldValue2;
  private ReportFieldFromType fieldFrom = ReportFieldFromType.Filter;
  private transient ReportSingleRule old;

  public ReportSingleRule() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportSingleRule;
  }

  @Override
  public String _type() {
    return "ReportSingleRule";
  }

  @Override
  public int _fieldsCount() {
    return 10;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getField() {
    _checkProxy();
    return this.field;
  }

  public void setField(String field) {
    _checkProxy();
    if (Objects.equals(this.field, field)) {
      return;
    }
    fieldChanged(_FIELD, this.field, field);
    this.field = field;
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

  public ReportRuleOperator getOperator() {
    _checkProxy();
    return this.operator;
  }

  public void setOperator(ReportRuleOperator operator) {
    _checkProxy();
    if (Objects.equals(this.operator, operator)) {
      return;
    }
    fieldChanged(_OPERATOR, this.operator, operator);
    this.operator = operator;
  }

  public String getValue1() {
    _checkProxy();
    return this.value1;
  }

  public void setValue1(String value1) {
    _checkProxy();
    if (Objects.equals(this.value1, value1)) {
      return;
    }
    fieldChanged(_VALUE1, this.value1, value1);
    this.value1 = value1;
  }

  public String getValue2() {
    _checkProxy();
    return this.value2;
  }

  public void setValue2(String value2) {
    _checkProxy();
    if (Objects.equals(this.value2, value2)) {
      return;
    }
    fieldChanged(_VALUE2, this.value2, value2);
    this.value2 = value2;
  }

  public ReportFilter getFilter() {
    _checkProxy();
    return this.filter;
  }

  public void setFilter(ReportFilter filter) {
    _checkProxy();
    if (Objects.equals(this.filter, filter)) {
      return;
    }
    fieldChanged(_FILTER, this.filter, filter);
    this.filter = filter;
  }

  public String getFieldValue1() {
    _checkProxy();
    return this.fieldValue1;
  }

  public void setFieldValue1(String fieldValue1) {
    _checkProxy();
    if (Objects.equals(this.fieldValue1, fieldValue1)) {
      return;
    }
    fieldChanged(_FIELDVALUE1, this.fieldValue1, fieldValue1);
    this.fieldValue1 = fieldValue1;
  }

  public String getFieldValue2() {
    _checkProxy();
    return this.fieldValue2;
  }

  public void setFieldValue2(String fieldValue2) {
    _checkProxy();
    if (Objects.equals(this.fieldValue2, fieldValue2)) {
      return;
    }
    fieldChanged(_FIELDVALUE2, this.fieldValue2, fieldValue2);
    this.fieldValue2 = fieldValue2;
  }

  public ReportFieldFromType getFieldFrom() {
    _checkProxy();
    return this.fieldFrom;
  }

  public void setFieldFrom(ReportFieldFromType fieldFrom) {
    _checkProxy();
    if (Objects.equals(this.fieldFrom, fieldFrom)) {
      return;
    }
    fieldChanged(_FIELDFROM, this.fieldFrom, fieldFrom);
    this.fieldFrom = fieldFrom;
  }

  public ReportSingleRule getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportSingleRule) old);
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportSingleRule && super.equals(a);
  }

  public ReportSingleRule deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportSingleRule _obj = ((ReportSingleRule) dbObj);
    _obj.setField(field);
    _obj.setType(type);
    _obj.setOperator(operator);
    _obj.setValue1(value1);
    _obj.setValue2(value2);
    _obj.setFilter(ctx.cloneRef(filter));
    _obj.setFieldValue1(fieldValue1);
    _obj.setFieldValue2(fieldValue2);
    _obj.setFieldFrom(fieldFrom);
  }

  public ReportSingleRule cloneInstance(ReportSingleRule cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportSingleRule();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setField(this.getField());
    cloneObj.setType(this.getType());
    cloneObj.setOperator(this.getOperator());
    cloneObj.setValue1(this.getValue1());
    cloneObj.setValue2(this.getValue2());
    cloneObj.setFilter(this.getFilter());
    cloneObj.setFieldValue1(this.getFieldValue1());
    cloneObj.setFieldValue2(this.getFieldValue2());
    cloneObj.setFieldFrom(this.getFieldFrom());
    return cloneObj;
  }

  public ReportSingleRule createNewInstance() {
    return new ReportSingleRule();
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
