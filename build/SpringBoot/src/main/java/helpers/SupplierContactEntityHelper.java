package helpers;

import models.SupplierContact;
import models.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.SupplierContactRepository;
import repository.jpa.VendorRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("SupplierContact")
public class SupplierContactEntityHelper<T extends SupplierContact> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private SupplierContactRepository supplierContactRepository;
  @Autowired private VendorRepository vendorRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public SupplierContact newInstance() {
    return new SupplierContact();
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

  public void validateFieldVendor(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Vendor it = entity.getVendor();
    if (it == null) {
      validationContext.addFieldError("vendor", "vendor is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldName(entity, validationContext, onCreate, onUpdate);
    validateFieldVendor(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) supplierContactRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getVendor() != null) {
      entity.getVendor().removeFromContacts(entity);
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
