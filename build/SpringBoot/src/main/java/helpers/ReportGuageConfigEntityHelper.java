package helpers;

import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportGuageConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportGuageConfig")
public class ReportGuageConfigEntityHelper<T extends ReportGuageConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportGuageConfig newInstance() {
    return new ReportGuageConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    long valueIndex = 0l;
    for (ReportField obj : entity.getValue()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("value", null, valueIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("value", null, valueIndex++));
      }
    }
    long minIndex = 0l;
    for (ReportField obj : entity.getMin()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("min", null, minIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("min", null, minIndex++));
      }
    }
    long maxIndex = 0l;
    for (ReportField obj : entity.getMax()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("max", null, maxIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("max", null, maxIndex++));
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
    return ((T) provider.get().find(SchemaConstants.ReportGuageConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getValue()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getMin()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getMax()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getTarget()) {
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
    for (ReportField obj : entity.getValue()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getMin()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getMax()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getTarget()) {
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
