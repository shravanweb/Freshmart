package helpers;

import d3e.core.SchemaConstants;
import models.ReportRule;
import models.ReportRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.ReportRepository;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportRuleSet")
public class ReportRuleSetEntityHelper<T extends ReportRuleSet> extends ReportRuleEntityHelper<T> {
  @Autowired private ReportRepository reportRepository;

  public ReportRuleSet newInstance() {
    return new ReportRuleSet();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    long rulesIndex = 0l;
    for (ReportRule obj : entity.getRules()) {
      ReportRuleEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("rules", null, rulesIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("rules", null, rulesIndex++));
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
    return ((T) provider.get().find(SchemaConstants.ReportRuleSet, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportRule obj : entity.getRules()) {
      ReportRuleEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    for (ReportRule obj : entity.getRules()) {
      ReportRuleEntityHelper helper = mutator.getHelperByInstance(obj);
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
