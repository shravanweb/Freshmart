package helpers;

import models.Product;
import models.StockTransfer;
import models.StockTransferLine;
import models.UnitOfMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.StockTransferLineRepository;
import repository.jpa.StockTransferRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("StockTransferLine")
public class StockTransferLineEntityHelper<T extends StockTransferLine> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private StockTransferLineRepository stockTransferLineRepository;
  @Autowired private StockTransferRepository stockTransferRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public StockTransferLine newInstance() {
    return new StockTransferLine();
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

  public void validateFieldUom(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    UnitOfMeasure it = entity.getUom();
    if (it == null) {
      validationContext.addFieldError("uom", "uom is required.");
      return;
    }
  }

  public void validateFieldStockTransfer(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    StockTransfer it = entity.getStockTransfer();
    if (it == null) {
      validationContext.addFieldError("stockTransfer", "stockTransfer is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldQuantity(entity, validationContext, onCreate, onUpdate);
    validateFieldUom(entity, validationContext, onCreate, onUpdate);
    validateFieldStockTransfer(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) stockTransferLineRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getStockTransfer() != null) {
      entity.getStockTransfer().removeFromLines(entity);
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
