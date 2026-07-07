package helpers;

import models.Product;
import models.SalesReturn;
import models.SalesReturnLine;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.SalesReturnLineRepository;
import repository.jpa.SalesReturnRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("SalesReturnLine")
public class SalesReturnLineEntityHelper<T extends SalesReturnLine> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private SalesReturnLineRepository salesReturnLineRepository;
  @Autowired private SalesReturnRepository salesReturnRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public SalesReturnLine newInstance() {
    return new SalesReturnLine();
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

  public void validateFieldSalesReturn(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    SalesReturn it = entity.getSalesReturn();
    if (it == null) {
      validationContext.addFieldError("salesReturn", "salesReturn is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldQuantity(entity, validationContext, onCreate, onUpdate);
    validateFieldUnitPrice(entity, validationContext, onCreate, onUpdate);
    validateFieldSalesReturn(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) salesReturnLineRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getSalesReturn() != null) {
      entity.getSalesReturn().removeFromLines(entity);
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
