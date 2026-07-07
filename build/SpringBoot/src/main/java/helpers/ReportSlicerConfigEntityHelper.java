package helpers;

import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportSlicerConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportSlicerConfig")
public class ReportSlicerConfigEntityHelper<T extends ReportSlicerConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportSlicerConfig newInstance() {
    return new ReportSlicerConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    long fieldsIndex = 0l;
    for (ReportField obj : entity.getFields()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("fields", null, fieldsIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("fields", null, fieldsIndex++));
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
    return ((T) provider.get().find(SchemaConstants.ReportSlicerConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getFields()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    for (ReportField obj : entity.getFields()) {
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
