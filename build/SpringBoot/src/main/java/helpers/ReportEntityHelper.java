package helpers;

import models.Report;
import models.ReportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.ReportRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("Report")
public class ReportEntityHelper<T extends Report> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private ReportRepository reportRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public Report newInstance() {
    return new Report();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldModel(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getModel();
    if (it == null) {
      validationContext.addFieldError("model", "model is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldModel(entity, validationContext, onCreate, onUpdate);
    if (entity.getCells() != null) {
      ReportCellEntityHelper helper = mutator.getHelperByInstance(entity.getCells());
      if (onCreate) {
        helper.validateOnCreate(entity.getCells(), validationContext.child("cells", null, -1l));
      } else {
        helper.validateOnUpdate(entity.getCells(), validationContext.child("cells", null, -1l));
      }
    }
    if (entity.getConfig() != null) {
      ReportBaseConfigEntityHelper helper = mutator.getHelperByInstance(entity.getConfig());
      if (onCreate) {
        helper.validateOnCreate(entity.getConfig(), validationContext.child("config", null, -1l));
      } else {
        helper.validateOnUpdate(entity.getConfig(), validationContext.child("config", null, -1l));
      }
    }
    long filtersIndex = 0l;
    for (ReportFilter obj : entity.getFilters()) {
      ReportFilterEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("filters", null, filtersIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("filters", null, filtersIndex++));
      }
    }
    if (entity.getCriteria() != null) {
      ReportRuleSetEntityHelper helper = mutator.getHelperByInstance(entity.getCriteria());
      if (onCreate) {
        helper.validateOnCreate(
            entity.getCriteria(), validationContext.child("criteria", null, -1l));
      } else {
        helper.validateOnUpdate(
            entity.getCriteria(), validationContext.child("criteria", null, -1l));
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
    return id == 0l ? null : ((T) reportRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    if (entity.getCells() != null) {
      ReportCellEntityHelper helper = mutator.getHelperByInstance(entity.getCells());
      helper.setDefaults(entity.getCells());
    }
    if (entity.getConfig() != null) {
      ReportBaseConfigEntityHelper helper = mutator.getHelperByInstance(entity.getConfig());
      helper.setDefaults(entity.getConfig());
    }
    for (ReportFilter obj : entity.getFilters()) {
      ReportFilterEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    if (entity.getCriteria() != null) {
      ReportRuleSetEntityHelper helper = mutator.getHelperByInstance(entity.getCriteria());
      helper.setDefaults(entity.getCriteria());
    }
  }

  @Override
  public void compute(T entity) {
    if (entity.getCells() != null) {
      ReportCellEntityHelper helper = mutator.getHelperByInstance(entity.getCells());
      helper.compute(entity.getCells());
    }
    if (entity.getConfig() != null) {
      ReportBaseConfigEntityHelper helper = mutator.getHelperByInstance(entity.getConfig());
      helper.compute(entity.getConfig());
    }
    for (ReportFilter obj : entity.getFilters()) {
      ReportFilterEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    if (entity.getCriteria() != null) {
      ReportRuleSetEntityHelper helper = mutator.getHelperByInstance(entity.getCriteria());
      helper.compute(entity.getCriteria());
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
