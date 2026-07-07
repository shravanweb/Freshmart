package classes;

import d3e.core.SchemaConstants;
import store.DBObject;

public class ReportFilterValue extends DBObject {
  public static final int _NAME = 0;
  public static final int _VALUE = 1;
  private long id;
  private String name;
  private String value;

  public ReportFilterValue() {}

  public ReportFilterValue(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    fieldChanged(_NAME, this.name, name);
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    fieldChanged(_VALUE, this.value, value);
    this.value = value;
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportFilterValue;
  }

  @Override
  public String _type() {
    return "ReportFilterValue";
  }

  @Override
  public int _fieldsCount() {
    return 2;
  }

  public void _convertToObjectRef() {}
}
