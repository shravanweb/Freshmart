package helpers;

import models.InventoryAdjustment;
import models.InventoryAdjustmentLine;
import models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.InventoryAdjustmentLineRepository;
import repository.jpa.InventoryAdjustmentRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("InventoryAdjustmentLine")
public class InventoryAdjustmentLineEntityHelper<T extends InventoryAdjustmentLine>
    implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private InventoryAdjustmentLineRepository inventoryAdjustmentLineRepository;
  @Autowired private InventoryAdjustmentRepository inventoryAdjustmentRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public InventoryAdjustmentLine newInstance() {
    return new InventoryAdjustmentLine();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldProduct(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Product it = entity.getProduct();
    if (it == null) {
      validationContext.addFieldError("product", "product is required.");
      return;
    }
  }

  public void validateFieldQuantityChange(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getQuantityChange();
  }

  public void validateFieldInventoryAdjustment(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    InventoryAdjustment it = entity.getInventoryAdjustment();
    if (it == null) {
      validationContext.addFieldError("inventoryAdjustment", "inventoryAdjustment is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldQuantityChange(entity, validationContext, onCreate, onUpdate);
    validateFieldInventoryAdjustment(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) inventoryAdjustmentLineRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getInventoryAdjustment() != null) {
      entity.getInventoryAdjustment().removeFromLines(entity);
    }
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
