package classes;

import java.util.List;

public enum ReportRuleOperator {
  Equal(
      d3e.core.ListExt.asList(
          "String", "Integer", "Double", "Object", "DateTime", "Date", "Time", "OptionSet")),
  NotEqual(
      d3e.core.ListExt.asList(
          "String", "Integer", "Double", "Object", "DateTime", "Date", "Time", "OptionSet")),
  Between(d3e.core.ListExt.asList("DateTime", "Date", "Time", "Integer", "Double")),
  IsIn(d3e.core.ListExt.asList("String", "Object", "OptionSet")),
  IsNotIn(d3e.core.ListExt.asList("String", "Object", "OptionSet")),
  GreaterThan(d3e.core.ListExt.asList("Integer", "Double", "DateTime", "Date", "Time")),
  LessThan(d3e.core.ListExt.asList("Integer", "Double", "DateTime", "Date", "Time")),
  GreaterThanorEqual(d3e.core.ListExt.asList("Integer", "Double", "DateTime", "Date", "Time")),
  LessThanorEqual(d3e.core.ListExt.asList("Integer", "Double", "DateTime", "Date", "Time")),
  Contains(d3e.core.ListExt.asList("String", "Double", "Object", "Integer")),
  NotContains(d3e.core.ListExt.asList("String", "Object", "Integer", "Double")),
  StartsWith(d3e.core.ListExt.asList("String", "Object")),
  EndsWith(d3e.core.ListExt.asList("String", "Object")),
  IsSet(
      d3e.core.ListExt.asList(
          "String", "Integer", "Double", "Object", "DateTime", "Date", "Time", "OptionSet")),
  IsNotSet(
      d3e.core.ListExt.asList(
          "String", "Integer", "Double", "Object", "DateTime", "Date", "Time", "OptionSet")),
  Match(d3e.core.ListExt.asList("Object")),
  NotMatch(d3e.core.ListExt.asList("Object")),
  Is(d3e.core.ListExt.asList("Boolean")),
  IsNot(d3e.core.ListExt.asList("Boolean")),
  IsWithin(d3e.core.ListExt.asList("DateTime", "Date", "Time"));
  private List<String> propType;

  private ReportRuleOperator(List<String> propType) {
    this.propType = propType;
  }

  public List<String> getPropType() {
    return this.propType;
  }
}
