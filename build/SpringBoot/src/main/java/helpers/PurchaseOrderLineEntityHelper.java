package helpers;

import models.GoodsReceiptLine;
import models.Product;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import models.UnitOfMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.GoodsReceiptLineRepository;
import repository.jpa.PurchaseOrderLineRepository;
import repository.jpa.PurchaseOrderRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("PurchaseOrderLine")
public class PurchaseOrderLineEntityHelper<T extends PurchaseOrderLine> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private PurchaseOrderLineRepository purchaseOrderLineRepository;
  @Autowired private GoodsReceiptLineRepository goodsReceiptLineRepository;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public PurchaseOrderLine newInstance() {
    return new PurchaseOrderLine();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldLineNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    long it = entity.getLineNumber();
  }

  public void validateFieldProduct(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Product it = entity.getProduct();
    if (it == null) {
      validationContext.addFieldError("product", "product is required.");
      return;
    }
  }

  public void validateFieldOrderedQuantity(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getOrderedQuantity();
  }

  public void validateFieldUnitPrice(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    double it = entity.getUnitPrice();
  }

  public void validateFieldUom(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    UnitOfMeasure it = entity.getUom();
    if (it == null) {
      validationContext.addFieldError("uom", "uom is required.");
      return;
    }
  }

  public void validateFieldPurchaseOrder(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    PurchaseOrder it = entity.getPurchaseOrder();
    if (it == null) {
      validationContext.addFieldError("purchaseOrder", "purchaseOrder is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldLineNumber(entity, validationContext, onCreate, onUpdate);
    validateFieldProduct(entity, validationContext, onCreate, onUpdate);
    validateFieldOrderedQuantity(entity, validationContext, onCreate, onUpdate);
    validateFieldUnitPrice(entity, validationContext, onCreate, onUpdate);
    validateFieldUom(entity, validationContext, onCreate, onUpdate);
    validateFieldPurchaseOrder(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void setDefaultReceivedQuantity(T entity) {
    if (entity.getReceivedQuantity() != 0.0d) {
      return;
    }
    entity.setReceivedQuantity(0.0d);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) purchaseOrderLineRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultReceivedQuantity(entity);
  }

  @Override
  public void compute(T entity) {
    entity.setLineTotal(entity.getOrderedQuantity() * entity.getUnitPrice());
  }

  private void deletePurchaseOrderLineInGoodsReceiptLine(
      T entity, EntityValidationContext deletionContext) {
    for (models.GoodsReceiptLine goodsReceiptLine :
        this.goodsReceiptLineRepository.getByPurchaseOrderLine(entity)) {
      goodsReceiptLine.setPurchaseOrderLine(null);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deletePurchaseOrderLineInGoodsReceiptLine(entity, deletionContext);
    if (entity.getPurchaseOrder() != null) {
      entity.getPurchaseOrder().removeFromLines(entity);
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
