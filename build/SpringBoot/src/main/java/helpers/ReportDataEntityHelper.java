package helpers;

import models.ReportData;
import models.ReportDataRow;
import models.ReportDataSection;
import org.springframework.beans.factory.annotation.Autowired;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportData")
public class ReportDataEntityHelper<T extends ReportData> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public ReportData newInstance() {
    return new ReportData();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    long sectionsIndex = 0l;
    for (ReportDataSection obj : entity.getSections()) {
      ReportDataSectionEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("sections", null, sectionsIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("sections", null, sectionsIndex++));
      }
    }
    long rowsIndex = 0l;
    for (ReportDataRow obj : entity.getRows()) {
      ReportDataRowEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("rows", null, rowsIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("rows", null, rowsIndex++));
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
    return null;
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportDataSection obj : entity.getSections()) {
      ReportDataSectionEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportDataRow obj : entity.getRows()) {
      ReportDataRowEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    for (ReportDataSection obj : entity.getSections()) {
      ReportDataSectionEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportDataRow obj : entity.getRows()) {
      ReportDataRowEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
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

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
