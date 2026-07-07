package helpers;

import classes.ReportBarChartType;
import d3e.core.SchemaConstants;
import models.ReportBarChartConfig;
import models.ReportField;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportBarChartConfig")
public class ReportBarChartConfigEntityHelper<T extends ReportBarChartConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportBarChartConfig newInstance() {
    return new ReportBarChartConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    ReportBarChartType it = entity.getType();
    if (it == null) {
      validationContext.addFieldError("type", "type is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    validateFieldType(entity, validationContext, onCreate, onUpdate);
    long xAxesIndex = 0l;
    for (ReportField obj : entity.getXAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("xAxes", null, xAxesIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("xAxes", null, xAxesIndex++));
      }
    }
    long yAxesIndex = 0l;
    for (ReportField obj : entity.getYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("yAxes", null, yAxesIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("yAxes", null, yAxesIndex++));
      }
    }
    if (entity.getLegend() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLegend());
      if (onCreate) {
        helper.validateOnCreate(entity.getLegend(), validationContext.child("legend", null, -1l));
      } else {
        helper.validateOnUpdate(entity.getLegend(), validationContext.child("legend", null, -1l));
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
    return ((T) provider.get().find(SchemaConstants.ReportBarChartConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getXAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    if (entity.getLegend() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLegend());
      helper.setDefaults(entity.getLegend());
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
    for (ReportField obj : entity.getYAxes()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    if (entity.getLegend() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLegend());
      helper.compute(entity.getLegend());
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
