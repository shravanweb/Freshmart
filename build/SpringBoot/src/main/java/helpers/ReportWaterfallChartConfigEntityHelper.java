package helpers;

import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportWaterfallChartConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportWaterfallChartConfig")
public class ReportWaterfallChartConfigEntityHelper<T extends ReportWaterfallChartConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportWaterfallChartConfig newInstance() {
    return new ReportWaterfallChartConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    long categoryIndex = 0l;
    for (ReportField obj : entity.getCategory()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("category", null, categoryIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("category", null, categoryIndex++));
      }
    }
    long breakdownFieldsIndex = 0l;
    for (ReportField obj : entity.getBreakdownFields()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(
            obj, validationContext.child("breakdownFields", null, breakdownFieldsIndex++));
      } else {
        helper.validateOnUpdate(
            obj, validationContext.child("breakdownFields", null, breakdownFieldsIndex++));
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
    return ((T) provider.get().find(SchemaConstants.ReportWaterfallChartConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getCategory()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getBreakdownFields()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getYAxes()) {
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
    for (ReportField obj : entity.getCategory()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getBreakdownFields()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getYAxes()) {
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
