package helpers;

import d3e.core.SchemaConstants;
import models.ReportCardConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportCardConfig")
public class ReportCardConfigEntityHelper<T extends ReportCardConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportCardConfig newInstance() {
    return new ReportCardConfig();
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
    return ((T) provider.get().find(SchemaConstants.ReportCardConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    if (entity.getValue() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getValue());
      helper.setDefaults(entity.getValue());
    }
  }

  @Override
  public void compute(T entity) {
    if (entity.getValue() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getValue());
      helper.compute(entity.getValue());
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
