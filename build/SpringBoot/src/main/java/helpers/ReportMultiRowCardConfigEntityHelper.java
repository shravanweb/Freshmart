package helpers;

import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportMultiRowCardConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportMultiRowCardConfig")
public class ReportMultiRowCardConfigEntityHelper<T extends ReportMultiRowCardConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportMultiRowCardConfig newInstance() {
    return new ReportMultiRowCardConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    long valuesIndex = 0l;
    for (ReportField obj : entity.getValues()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("values", null, valuesIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("values", null, valuesIndex++));
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
    return ((T) provider.get().find(SchemaConstants.ReportMultiRowCardConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getValues()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    for (ReportField obj : entity.getValues()) {
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
