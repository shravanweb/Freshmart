package helpers;

import classes.PurchaseOrderStatus;
import java.time.LocalDate;
import models.GoodsReceipt;
import models.Organization;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import models.Vendor;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.GoodsReceiptRepository;
import repository.jpa.PurchaseOrderLineRepository;
import repository.jpa.PurchaseOrderRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("PurchaseOrder")
public class PurchaseOrderEntityHelper<T extends PurchaseOrder> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;
  @Autowired private PurchaseOrderLineRepository purchaseOrderLineRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public PurchaseOrder newInstance() {
    return new PurchaseOrder();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldPoNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getPoNumber();
    if (it == null) {
      validationContext.addFieldError("poNumber", "poNumber is required.");
      return;
    }
  }

  public void validateFieldVendor(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Vendor it = entity.getVendor();
    if (it == null) {
      validationContext.addFieldError("vendor", "vendor is required.");
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

  public void validateFieldOrderDate(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDate it = entity.getOrderDate();
    if (it == null) {
      validationContext.addFieldError("orderDate", "orderDate is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    PurchaseOrderStatus it = entity.getStatus();
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
    validateFieldPoNumber(entity, validationContext, onCreate, onUpdate);
    validateFieldVendor(entity, validationContext, onCreate, onUpdate);
    validateFieldWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldOrderDate(entity, validationContext, onCreate, onUpdate);
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
    if (entity.getStatus() != PurchaseOrderStatus.Draft) {
      return;
    }
    entity.setStatus(PurchaseOrderStatus.Draft);
  }

  public void setDefaultTaxAmount(T entity) {
    if (entity.getTaxAmount() != 0.0d) {
      return;
    }
    entity.setTaxAmount(0.0d);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) purchaseOrderRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
    this.setDefaultTaxAmount(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deletePurchaseOrderInGoodsReceipt(
      T entity, EntityValidationContext deletionContext) {
    for (models.GoodsReceipt goodsReceipt :
        this.goodsReceiptRepository.getByPurchaseOrder(entity)) {
      goodsReceipt.setPurchaseOrder(null);
    }
  }

  private void deletePurchaseOrderInPurchaseOrderLine(
      T entity, EntityValidationContext deletionContext) {
    for (PurchaseOrderLine obj : this.purchaseOrderLineRepository.getByPurchaseOrder(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deletePurchaseOrderInGoodsReceipt(entity, deletionContext);
    this.deletePurchaseOrderInPurchaseOrderLine(entity, deletionContext);
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
