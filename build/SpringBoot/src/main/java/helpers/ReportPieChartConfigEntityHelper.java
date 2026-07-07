package helpers;

import classes.ReportPieChartType;
import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportPieChartConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportPieChartConfig")
public class ReportPieChartConfigEntityHelper<T extends ReportPieChartConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportPieChartConfig newInstance() {
    return new ReportPieChartConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    ReportPieChartType it = entity.getType();
    if (it == null) {
      validationContext.addFieldError("type", "type is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    validateFieldType(entity, validationContext, onCreate, onUpdate);
    long legendIndex = 0l;
    for (ReportField obj : entity.getLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("legend", null, legendIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("legend", null, legendIndex++));
      }
    }
    long valuesIndex = 0l;
    for (ReportField obj : entity.getValues()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("values", null, valuesIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("values", null, valuesIndex++));
      }
    }
    long detailsIndex = 0l;
    for (ReportField obj : entity.getDetails()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("details", null, detailsIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("details", null, detailsIndex++));
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
    return ((T) provider.get().find(SchemaConstants.ReportPieChartConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getValues()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getDetails()) {
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
    for (ReportField obj : entity.getLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getValues()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getDetails()) {
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
