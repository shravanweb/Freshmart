package helpers;

import classes.EntityStatus;
import models.GoodsReceipt;
import models.Organization;
import models.PurchaseOrder;
import models.SupplierContact;
import models.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.GoodsReceiptRepository;
import repository.jpa.PurchaseOrderRepository;
import repository.jpa.SupplierContactRepository;
import repository.jpa.VendorRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("Vendor")
public class VendorEntityHelper<T extends Vendor> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private VendorRepository vendorRepository;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private SupplierContactRepository supplierContactRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public Vendor newInstance() {
    return new Vendor();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldName(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getName();
    if (it == null) {
      validationContext.addFieldError("name", "name is required.");
      return;
    }
  }

  public void validateFieldCode(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getCode();
    if (it == null) {
      validationContext.addFieldError("code", "code is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    EntityStatus it = entity.getStatus();
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
    validateFieldName(entity, validationContext, onCreate, onUpdate);
    validateFieldCode(entity, validationContext, onCreate, onUpdate);
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
    if (entity.getStatus() != EntityStatus.Active) {
      return;
    }
    entity.setStatus(EntityStatus.Active);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) vendorRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteVendorInGoodsReceipt(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.goodsReceiptRepository.getByVendor(entity))) {
      deletionContext.addEntityError(
          "This Vendor cannot be deleted as it is being referred to by GoodsReceipt.");
    }
  }

  private void deleteVendorInPurchaseOrder(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.purchaseOrderRepository.getByVendor(entity))) {
      deletionContext.addEntityError(
          "This Vendor cannot be deleted as it is being referred to by PurchaseOrder.");
    }
  }

  private void deleteVendorInSupplierContact(T entity, EntityValidationContext deletionContext) {
    for (SupplierContact obj : this.supplierContactRepository.getByVendor(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteVendorInSupplierContact(entity, deletionContext);
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteVendorInGoodsReceipt(entity, deletionContext);
    this.deleteVendorInPurchaseOrder(entity, deletionContext);
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
