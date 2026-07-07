package helpers;

import classes.AdjustmentReason;
import classes.AdjustmentStatus;
import java.time.LocalDate;
import models.InventoryAdjustment;
import models.InventoryAdjustmentLine;
import models.Organization;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.InventoryAdjustmentLineRepository;
import repository.jpa.InventoryAdjustmentRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("InventoryAdjustment")
public class InventoryAdjustmentEntityHelper<T extends InventoryAdjustment>
    implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private InventoryAdjustmentRepository inventoryAdjustmentRepository;
  @Autowired private InventoryAdjustmentLineRepository inventoryAdjustmentLineRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public InventoryAdjustment newInstance() {
    return new InventoryAdjustment();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldAdjustmentNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getAdjustmentNumber();
    if (it == null) {
      validationContext.addFieldError("adjustmentNumber", "adjustmentNumber is required.");
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

  public void validateFieldAdjustmentDate(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDate it = entity.getAdjustmentDate();
    if (it == null) {
      validationContext.addFieldError("adjustmentDate", "adjustmentDate is required.");
      return;
    }
  }

  public void validateFieldReason(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    AdjustmentReason it = entity.getReason();
    if (it == null) {
      validationContext.addFieldError("reason", "reason is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    AdjustmentStatus it = entity.getStatus();
    if (it == null) {
      validationContext.addFieldError("status", "status is required.");
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
    validateFieldAdjustmentNumber(entity, validationContext, onCreate, onUpdate);
    validateFieldWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldAdjustmentDate(entity, validationContext, onCreate, onUpdate);
    validateFieldReason(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void setDefaultStatus(T entity) {
    if (entity.getStatus() != AdjustmentStatus.Draft) {
      return;
    }
    entity.setStatus(AdjustmentStatus.Draft);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) inventoryAdjustmentRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteInventoryAdjustmentInInventoryAdjustmentLine(
      T entity, EntityValidationContext deletionContext) {
    for (InventoryAdjustmentLine obj :
        this.inventoryAdjustmentLineRepository.getByInventoryAdjustment(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteInventoryAdjustmentInInventoryAdjustmentLine(entity, deletionContext);
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
