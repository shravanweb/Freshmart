package helpers;

import d3e.core.SchemaConstants;
import models.ReportNamedCondition;
import org.springframework.beans.factory.annotation.Autowired;
import store.D3EEntityManagerProvider;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportNamedCondition")
public class ReportNamedConditionEntityHelper<T extends ReportNamedCondition>
    implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired protected D3EEntityManagerProvider provider;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public ReportNamedCondition newInstance() {
    return new ReportNamedCondition();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    if (entity.getCondition() != null) {
      ReportRuleEntityHelper helper = mutator.getHelperByInstance(entity.getCondition());
      if (onCreate) {
        helper.validateOnCreate(
            entity.getCondition(), validationContext.child("condition", null, -1l));
      } else {
        helper.validateOnUpdate(
            entity.getCondition(), validationContext.child("condition", null, -1l));
      }
    }
  }

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
    return ((T) provider.get().find(SchemaConstants.ReportNamedCondition, id));
  }

  @Override
  public void setDefaults(T entity) {
    if (entity.getCondition() != null) {
      ReportRuleEntityHelper helper = mutator.getHelperByInstance(entity.getCondition());
      helper.setDefaults(entity.getCondition());
    }
  }

  @Override
  public void compute(T entity) {
    if (entity.getCondition() != null) {
      ReportRuleEntityHelper helper = mutator.getHelperByInstance(entity.getCondition());
      helper.compute(entity.getCondition());
    }
  }

  private void deleteConditionsInReportNamedConditionFilter(
      T entity, EntityValidationContext deletionContext) {
    // TODO: ReportNamedConditionFilter is a document model. Need to figure out how to check
    // ReportNamedConditionFilter by conditions for this method.
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteConditionsInReportNamedConditionFilter(entity, deletionContext);
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
