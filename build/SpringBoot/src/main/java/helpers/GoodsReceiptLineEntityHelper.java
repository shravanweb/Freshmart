package helpers;

import models.GoodsReceipt;
import models.GoodsReceiptLine;
import models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.GoodsReceiptLineRepository;
import repository.jpa.GoodsReceiptRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("GoodsReceiptLine")
public class GoodsReceiptLineEntityHelper<T extends GoodsReceiptLine> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private GoodsReceiptLineRepository goodsReceiptLineRepository;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public GoodsReceiptLine newInstance() {
    return new GoodsReceiptLine();
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

  public void validateFieldReceivedQuantity(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getReceivedQuantity();
  }

  public void validateFieldUnitCost(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getUnitCost();
  }

  public void validateFieldGoodsReceipt(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    GoodsReceipt it = entity.getGoodsReceipt();
    if (it == null) {
      validationContext.addFieldError("goodsReceipt", "goodsReceipt is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldReceivedQuantity(entity, validationContext, onCreate, onUpdate);
    validateFieldUnitCost(entity, validationContext, onCreate, onUpdate);
    validateFieldGoodsReceipt(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) goodsReceiptLineRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getGoodsReceipt() != null) {
      entity.getGoodsReceipt().removeFromLines(entity);
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
