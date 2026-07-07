package classes;

import d3e.core.SchemaConstants;
import java.util.List;
import store.D3EPersistanceList;
import store.DBObject;

public class ReportInput extends DBObject {
  public static final int _NAME = 0;
  public static final int _VALUE = 1;
  public static final int _VALUES = 2;
  private long id;
  private String name;
  private String value;
  private List<String> values = new D3EPersistanceList<>(this, _VALUES);

  public ReportInput() {}

  public ReportInput(String name, String value, List<String> values) {
    this.name = name;
    this.value = value;
    this.values.addAll(values);
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

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    ((D3EPersistanceList<String>) this.values).setAll(values);
  }

  public void addToValues(String val, long index) {
    if (index == -1) {
      this.values.add(val);
    } else {
      this.values.add(((int) index), val);
    }
  }

  public void removeFromValues(String val) {
    this.values.remove(val);
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportInput;
  }

  @Override
  public String _type() {
    return "ReportInput";
  }

  @Override
  public int _fieldsCount() {
    return 3;
  }

  public void _convertToObjectRef() {}
}
