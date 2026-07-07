package helpers;

import d3e.core.SchemaConstants;
import models.ReportCell;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.ReportRepository;
import store.D3EEntityManagerProvider;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportCell")
public class ReportCellEntityHelper<T extends ReportCell> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired protected D3EEntityManagerProvider provider;
  @Autowired private ReportRepository reportRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public ReportCell newInstance() {
    return new ReportCell();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    if (entity.getStyle() != null) {
      ReportCellStyleEntityHelper helper = mutator.getHelperByInstance(entity.getStyle());
      if (onCreate) {
        helper.validateOnCreate(entity.getStyle(), validationContext.child("style", null, -1l));
      } else {
        helper.validateOnUpdate(entity.getStyle(), validationContext.child("style", null, -1l));
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
    return ((T) provider.get().find(SchemaConstants.ReportCell, id));
  }

  @Override
  public void setDefaults(T entity) {
    if (entity.getStyle() != null) {
      ReportCellStyleEntityHelper helper = mutator.getHelperByInstance(entity.getStyle());
      helper.setDefaults(entity.getStyle());
    }
  }

  @Override
  public void compute(T entity) {
    if (entity.getStyle() != null) {
      ReportCellStyleEntityHelper helper = mutator.getHelperByInstance(entity.getStyle());
      helper.compute(entity.getStyle());
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    return true;
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
