package helpers;

import models.Organization;
import models.Product;
import models.Warehouse;
import models.WarehouseStock;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.ProductRepository;
import repository.jpa.WarehouseStockRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("WarehouseStock")
public class WarehouseStockEntityHelper<T extends WarehouseStock> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private WarehouseStockRepository warehouseStockRepository;
  @Autowired private ProductRepository productRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public WarehouseStock newInstance() {
    return new WarehouseStock();
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

  public void validateFieldQuantityOnHand(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getQuantityOnHand();
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
    validateFieldQuantityOnHand(entity, validationContext, onCreate, onUpdate);
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

  public void setDefaultQuantityReserved(T entity) {
    if (entity.getQuantityReserved() != 0.0d) {
      return;
    }
    entity.setQuantityReserved(0.0d);
  }

  public void setDefaultAverageCost(T entity) {
    if (entity.getAverageCost() != 0.0d) {
      return;
    }
    entity.setAverageCost(0.0d);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) warehouseStockRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultQuantityOnHand(entity);
    this.setDefaultQuantityReserved(entity);
    this.setDefaultAverageCost(entity);
  }

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getProduct() != null) {
      entity.getProduct().removeFromWarehouseStocks(entity);
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
