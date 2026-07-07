package helpers;

import classes.MovementDirection;
import classes.MovementReferenceType;
import classes.MovementType;
import java.time.LocalDateTime;
import models.InventoryMovement;
import models.Organization;
import models.Product;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.InventoryMovementRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("InventoryMovement")
public class InventoryMovementEntityHelper<T extends InventoryMovement> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private InventoryMovementRepository inventoryMovementRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public InventoryMovement newInstance() {
    return new InventoryMovement();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldMovementNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getMovementNumber();
    if (it == null) {
      validationContext.addFieldError("movementNumber", "movementNumber is required.");
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

  public void validateFieldProduct(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Product it = entity.getProduct();
    if (it == null) {
      validationContext.addFieldError("product", "product is required.");
      return;
    }
  }

  public void validateFieldMovementType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    MovementType it = entity.getMovementType();
    if (it == null) {
      validationContext.addFieldError("movementType", "movementType is required.");
      return;
    }
  }

  public void validateFieldQuantity(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getQuantity();
  }

  public void validateFieldDirection(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    MovementDirection it = entity.getDirection();
    if (it == null) {
      validationContext.addFieldError("direction", "direction is required.");
      return;
    }
  }

  public void validateFieldReferenceType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    MovementReferenceType it = entity.getReferenceType();
    if (it == null) {
      validationContext.addFieldError("referenceType", "referenceType is required.");
      return;
    }
  }

  public void validateFieldReferenceId(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getReferenceId();
    if (it == null) {
      validationContext.addFieldError("referenceId", "referenceId is required.");
      return;
    }
  }

  public void validateFieldBalanceAfter(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getBalanceAfter();
  }

  public void validateFieldMovementDate(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDateTime it = entity.getMovementDate();
    if (it == null) {
      validationContext.addFieldError("movementDate", "movementDate is required.");
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
    validateFieldMovementNumber(entity, validationContext, onCreate, onUpdate);
    validateFieldWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldMovementType(entity, validationContext, onCreate, onUpdate);
    validateFieldQuantity(entity, validationContext, onCreate, onUpdate);
    validateFieldDirection(entity, validationContext, onCreate, onUpdate);
    validateFieldReferenceType(entity, validationContext, onCreate, onUpdate);
    validateFieldReferenceId(entity, validationContext, onCreate, onUpdate);
    validateFieldBalanceAfter(entity, validationContext, onCreate, onUpdate);
    validateFieldMovementDate(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) inventoryMovementRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

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
