package helpers;

import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportMatrixConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportMatrixConfig")
public class ReportMatrixConfigEntityHelper<T extends ReportMatrixConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportMatrixConfig newInstance() {
    return new ReportMatrixConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    long columnsIndex = 0l;
    for (ReportField obj : entity.getColumns()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("columns", null, columnsIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("columns", null, columnsIndex++));
      }
    }
    long rowsIndex = 0l;
    for (ReportField obj : entity.getRows()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("rows", null, rowsIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("rows", null, rowsIndex++));
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
    return ((T) provider.get().find(SchemaConstants.ReportMatrixConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getColumns()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getRows()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getValues()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    for (ReportField obj : entity.getColumns()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getRows()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
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
