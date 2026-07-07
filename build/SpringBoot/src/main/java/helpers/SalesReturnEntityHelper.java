package helpers;

import classes.ReturnReason;
import classes.SalesReturnStatus;
import java.time.LocalDateTime;
import models.Organization;
import models.SalesReturn;
import models.SalesReturnLine;
import models.Store;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.SalesReturnLineRepository;
import repository.jpa.SalesReturnRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("SalesReturn")
public class SalesReturnEntityHelper<T extends SalesReturn> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private SalesReturnRepository salesReturnRepository;
  @Autowired private SalesReturnLineRepository salesReturnLineRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public SalesReturn newInstance() {
    return new SalesReturn();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldStore(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Store it = entity.getStore();
    if (it == null) {
      validationContext.addFieldError("store", "store is required.");
      return;
    }
  }

  public void validateFieldWarehouse(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Warehouse it = entity.getWarehouse();
    if (it == null) {
      validationContext.addFieldError("warehouse", "warehouse is required.");
      return;
    }
  }

  public void validateFieldReturnNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getReturnNumber();
    if (it == null) {
      validationContext.addFieldError("returnNumber", "returnNumber is required.");
      return;
    }
  }

  public void validateFieldReturnDate(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDateTime it = entity.getReturnDate();
    if (it == null) {
      validationContext.addFieldError("returnDate", "returnDate is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    SalesReturnStatus it = entity.getStatus();
    if (it == null) {
      validationContext.addFieldError("status", "status is required.");
      return;
    }
  }

  public void validateFieldReason(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    ReturnReason it = entity.getReason();
    if (it == null) {
      validationContext.addFieldError("reason", "reason is required.");
      return;
    }
  }

  public void validateFieldOrganization(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Organization it = entity.getOrganization();
    if (it == null) {
      validationContext.addFieldError("organization", "organization is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldStore(entity, validationContext, onCreate, onUpdate);
    validateFieldWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldReturnNumber(entity, validationContext, onCreate, onUpdate);
    validateFieldReturnDate(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldReason(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void setDefaultStatus(T entity) {
    if (entity.getStatus() != SalesReturnStatus.Draft) {
      return;
    }
    entity.setStatus(SalesReturnStatus.Draft);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) salesReturnRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteSalesReturnInSalesReturnLine(
      T entity, EntityValidationContext deletionContext) {
    for (SalesReturnLine obj : this.salesReturnLineRepository.getBySalesReturn(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteSalesReturnInSalesReturnLine(entity, deletionContext);
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
