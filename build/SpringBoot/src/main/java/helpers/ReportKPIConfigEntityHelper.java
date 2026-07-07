package helpers;

import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportKPIConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportKPIConfig")
public class ReportKPIConfigEntityHelper<T extends ReportKPIConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportKPIConfig newInstance() {
    return new ReportKPIConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    if (entity.getValue() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getValue());
      if (onCreate) {
        helper.validateOnCreate(entity.getValue(), validationContext.child("value", null, -1l));
      } else {
        helper.validateOnUpdate(entity.getValue(), validationContext.child("value", null, -1l));
      }
    }
    long targetIndex = 0l;
    for (ReportField obj : entity.getTarget()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("target", null, targetIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("target", null, targetIndex++));
      }
    }
    if (entity.getTrend() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getTrend());
      if (onCreate) {
        helper.validateOnCreate(entity.getTrend(), validationContext.child("trend", null, -1l));
      } else {
        helper.validateOnUpdate(entity.getTrend(), validationContext.child("trend", null, -1l));
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
    return ((T) provider.get().find(SchemaConstants.ReportKPIConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    if (entity.getValue() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getValue());
      helper.setDefaults(entity.getValue());
    }
    for (ReportField obj : entity.getTarget()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    if (entity.getTrend() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getTrend());
      helper.setDefaults(entity.getTrend());
    }
  }

  @Override
  public void compute(T entity) {
    if (entity.getValue() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getValue());
      helper.compute(entity.getValue());
    }
    for (ReportField obj : entity.getTarget()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    if (entity.getTrend() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getTrend());
      helper.compute(entity.getTrend());
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
