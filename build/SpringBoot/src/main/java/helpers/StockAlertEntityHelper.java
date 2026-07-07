package helpers;

import classes.AlertStatus;
import classes.StockAlertType;
import models.Organization;
import models.Product;
import models.StockAlert;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.StockAlertRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("StockAlert")
public class StockAlertEntityHelper<T extends StockAlert> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private StockAlertRepository stockAlertRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public StockAlert newInstance() {
    return new StockAlert();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

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

  public void validateFieldAlertType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    StockAlertType it = entity.getAlertType();
    if (it == null) {
      validationContext.addFieldError("alertType", "alertType is required.");
      return;
    }
  }

  public void validateFieldCurrentQuantity(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getCurrentQuantity();
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    AlertStatus it = entity.getStatus();
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
    validateFieldWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldAlertType(entity, validationContext, onCreate, onUpdate);
    validateFieldCurrentQuantity(entity, validationContext, onCreate, onUpdate);
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
    if (entity.getStatus() != AlertStatus.Open) {
      return;
    }
    entity.setStatus(AlertStatus.Open);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) stockAlertRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

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
