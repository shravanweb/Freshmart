package helpers;

import classes.BatchStatus;
import models.Organization;
import models.Product;
import models.StockBatch;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.ProductRepository;
import repository.jpa.StockBatchRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("StockBatch")
public class StockBatchEntityHelper<T extends StockBatch> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private StockBatchRepository stockBatchRepository;
  @Autowired private ProductRepository productRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public StockBatch newInstance() {
    return new StockBatch();
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

  public void validateFieldWarehouse(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Warehouse it = entity.getWarehouse();
    if (it == null) {
      validationContext.addFieldError("warehouse", "warehouse is required.");
      return;
    }
  }

  public void validateFieldBatchNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getBatchNumber();
    if (it == null) {
      validationContext.addFieldError("batchNumber", "batchNumber is required.");
      return;
    }
  }

  public void validateFieldQuantityOnHand(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getQuantityOnHand();
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    BatchStatus it = entity.getStatus();
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
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldBatchNumber(entity, validationContext, onCreate, onUpdate);
    validateFieldQuantityOnHand(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void setDefaultQuantityOnHand(T entity) {
    if (entity.getQuantityOnHand() != 0.0d) {
      return;
    }
    entity.setQuantityOnHand(0.0d);
  }

  public void setDefaultUnitCost(T entity) {
    if (entity.getUnitCost() != 0.0d) {
      return;
    }
    entity.setUnitCost(0.0d);
  }

  public void setDefaultStatus(T entity) {
    if (entity.getStatus() != BatchStatus.Active) {
      return;
    }
    entity.setStatus(BatchStatus.Active);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) stockBatchRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultQuantityOnHand(entity);
    this.setDefaultUnitCost(entity);
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getProduct() != null) {
      entity.getProduct().removeFromBatches(entity);
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
