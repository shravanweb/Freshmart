package helpers;

import d3e.core.SchemaConstants;
import models.ReportRule;
import org.springframework.beans.factory.annotation.Autowired;
import store.D3EEntityManagerProvider;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportRule")
public class ReportRuleEntityHelper<T extends ReportRule> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired protected D3EEntityManagerProvider provider;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {}

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return ((T) provider.get().find(SchemaConstants.ReportRule, id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  private void deleteParentInReportRule(T entity, EntityValidationContext deletionContext) {
    // TODO: ReportRule is a document model. Need to figure out how to check ReportRule by parent
    // for this method.
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteParentInReportRule(entity, deletionContext);
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    return true;
  }
}
