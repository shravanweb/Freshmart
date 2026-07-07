package helpers;

import models.Product;
import models.SalesOrder;
import models.SalesOrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.SalesOrderLineRepository;
import repository.jpa.SalesOrderRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("SalesOrderLine")
public class SalesOrderLineEntityHelper<T extends SalesOrderLine> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private SalesOrderLineRepository salesOrderLineRepository;
  @Autowired private SalesOrderRepository salesOrderRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public SalesOrderLine newInstance() {
    return new SalesOrderLine();
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

  public void validateFieldQuantity(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getQuantity();
  }

  public void validateFieldUnitPrice(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getUnitPrice();
  }

  public void validateFieldSalesOrder(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    SalesOrder it = entity.getSalesOrder();
    if (it == null) {
      validationContext.addFieldError("salesOrder", "salesOrder is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldQuantity(entity, validationContext, onCreate, onUpdate);
    validateFieldUnitPrice(entity, validationContext, onCreate, onUpdate);
    validateFieldSalesOrder(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void setDefaultDiscount(T entity) {
    if (entity.getDiscount() != 0.0d) {
      return;
    }
    entity.setDiscount(0.0d);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) salesOrderLineRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultDiscount(entity);
  }

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getSalesOrder() != null) {
      entity.getSalesOrder().removeFromLines(entity);
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
