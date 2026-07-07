package helpers;

import classes.GoodsReceiptStatus;
import java.time.LocalDate;
import models.GoodsReceipt;
import models.GoodsReceiptLine;
import models.Organization;
import models.Vendor;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.GoodsReceiptLineRepository;
import repository.jpa.GoodsReceiptRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("GoodsReceipt")
public class GoodsReceiptEntityHelper<T extends GoodsReceipt> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;
  @Autowired private GoodsReceiptLineRepository goodsReceiptLineRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public GoodsReceipt newInstance() {
    return new GoodsReceipt();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldReceiptNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getReceiptNumber();
    if (it == null) {
      validationContext.addFieldError("receiptNumber", "receiptNumber is required.");
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

  public void validateFieldReceiptDate(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDate it = entity.getReceiptDate();
    if (it == null) {
      validationContext.addFieldError("receiptDate", "receiptDate is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    GoodsReceiptStatus it = entity.getStatus();
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
    validateFieldReceiptNumber(entity, validationContext, onCreate, onUpdate);
    validateFieldVendor(entity, validationContext, onCreate, onUpdate);
    validateFieldWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldReceiptDate(entity, validationContext, onCreate, onUpdate);
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
    if (entity.getStatus() != GoodsReceiptStatus.Draft) {
      return;
    }
    entity.setStatus(GoodsReceiptStatus.Draft);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) goodsReceiptRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteGoodsReceiptInGoodsReceiptLine(
      T entity, EntityValidationContext deletionContext) {
    for (GoodsReceiptLine obj : this.goodsReceiptLineRepository.getByGoodsReceipt(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteGoodsReceiptInGoodsReceiptLine(entity, deletionContext);
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
