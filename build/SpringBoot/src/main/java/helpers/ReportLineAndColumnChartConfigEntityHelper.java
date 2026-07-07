package helpers;

import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportLineAndColumnChartConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportLineAndColumnChartConfig")
public class ReportLineAndColumnChartConfigEntityHelper<T extends ReportLineAndColumnChartConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportLineAndColumnChartConfig newInstance() {
    return new ReportLineAndColumnChartConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    long xAxesIndex = 0l;
    for (ReportField obj : entity.getXAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("xAxes", null, xAxesIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("xAxes", null, xAxesIndex++));
      }
    }
    long columnYAxesIndex = 0l;
    for (ReportField obj : entity.getColumnYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(
            obj, validationContext.child("columnYAxes", null, columnYAxesIndex++));
      } else {
        helper.validateOnUpdate(
            obj, validationContext.child("columnYAxes", null, columnYAxesIndex++));
      }
    }
    long lineYAxesIndex = 0l;
    for (ReportField obj : entity.getLineYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("lineYAxes", null, lineYAxesIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("lineYAxes", null, lineYAxesIndex++));
      }
    }
    long columnLegendIndex = 0l;
    for (ReportField obj : entity.getColumnLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(
            obj, validationContext.child("columnLegend", null, columnLegendIndex++));
      } else {
        helper.validateOnUpdate(
            obj, validationContext.child("columnLegend", null, columnLegendIndex++));
      }
    }
    long smallMultiplesIndex = 0l;
    for (ReportField obj : entity.getSmallMultiples()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(
            obj, validationContext.child("smallMultiples", null, smallMultiplesIndex++));
      } else {
        helper.validateOnUpdate(
            obj, validationContext.child("smallMultiples", null, smallMultiplesIndex++));
      }
    }
    long tooltipsIndex = 0l;
    for (ReportField obj : entity.getTooltips()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("tooltips", null, tooltipsIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("tooltips", null, tooltipsIndex++));
      }
    }
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    super.validateOnCreate(entity, validationContext);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    super.validateOnUpdate(entity, validationContext);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return ((T) provider.get().find(SchemaConstants.ReportLineAndColumnChartConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getXAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getColumnYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getLineYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getColumnLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getSmallMultiples()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getTooltips()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    for (ReportField obj : entity.getXAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getColumnYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getLineYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getColumnLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getSmallMultiples()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getTooltips()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    super.onDelete(entity, internal, deletionContext);
    return true;
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    super.onCreate(entity, internal, context);
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    super.onUpdate(entity, internal, context);
    return true;
  }
}
