package helpers;

import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportKeyInfluencerConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportKeyInfluencerConfig")
public class ReportKeyInfluencerConfigEntityHelper<T extends ReportKeyInfluencerConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportKeyInfluencerConfig newInstance() {
    return new ReportKeyInfluencerConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    if (entity.getAnalyze() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getAnalyze());
      if (onCreate) {
        helper.validateOnCreate(entity.getAnalyze(), validationContext.child("analyze", null, -1l));
      } else {
        helper.validateOnUpdate(entity.getAnalyze(), validationContext.child("analyze", null, -1l));
      }
    }
    long eexplainByIndex = 0l;
    for (ReportField obj : entity.getEexplainBy()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(
            obj, validationContext.child("eexplainBy", null, eexplainByIndex++));
      } else {
        helper.validateOnUpdate(
            obj, validationContext.child("eexplainBy", null, eexplainByIndex++));
      }
    }
    long expandByIndex = 0l;
    for (ReportField obj : entity.getExpandBy()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("expandBy", null, expandByIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("expandBy", null, expandByIndex++));
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
    return ((T) provider.get().find(SchemaConstants.ReportKeyInfluencerConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    if (entity.getAnalyze() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getAnalyze());
      helper.setDefaults(entity.getAnalyze());
    }
    for (ReportField obj : entity.getEexplainBy()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getExpandBy()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    if (entity.getAnalyze() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getAnalyze());
      helper.compute(entity.getAnalyze());
    }
    for (ReportField obj : entity.getEexplainBy()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getExpandBy()) {
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
