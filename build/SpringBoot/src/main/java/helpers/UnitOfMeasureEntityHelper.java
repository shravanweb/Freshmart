package helpers;

import classes.EntityStatus;
import classes.UomType;
import models.Organization;
import models.Product;
import models.PurchaseOrderLine;
import models.StockTransferLine;
import models.UnitOfMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.ProductRepository;
import repository.jpa.PurchaseOrderLineRepository;
import repository.jpa.StockTransferLineRepository;
import repository.jpa.UnitOfMeasureRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("UnitOfMeasure")
public class UnitOfMeasureEntityHelper<T extends UnitOfMeasure> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private UnitOfMeasureRepository unitOfMeasureRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private PurchaseOrderLineRepository purchaseOrderLineRepository;
  @Autowired private StockTransferLineRepository stockTransferLineRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public UnitOfMeasure newInstance() {
    return new UnitOfMeasure();
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

  public void validateFieldSymbol(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getSymbol();
    if (it == null) {
      validationContext.addFieldError("symbol", "symbol is required.");
      return;
    }
  }

  public void validateFieldUomType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    UomType it = entity.getUomType();
    if (it == null) {
      validationContext.addFieldError("uomType", "uomType is required.");
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
    validateFieldSymbol(entity, validationContext, onCreate, onUpdate);
    validateFieldUomType(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void setDefaultBaseFactor(T entity) {
    if (entity.getBaseFactor() != 0.0d) {
      return;
    }
    entity.setBaseFactor(1.0d);
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
    return id == 0l ? null : ((T) unitOfMeasureRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultBaseFactor(entity);
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteBaseUomInProduct(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.productRepository.getByBaseUom(entity))) {
      deletionContext.addEntityError(
          "This UnitOfMeasure cannot be deleted as it is being referred to by Product.");
    }
  }

  private void deleteUomInPurchaseOrderLine(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.purchaseOrderLineRepository.getByUom(entity))) {
      deletionContext.addEntityError(
          "This UnitOfMeasure cannot be deleted as it is being referred to by PurchaseOrderLine.");
    }
  }

  private void deleteUomInStockTransferLine(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.stockTransferLineRepository.getByUom(entity))) {
      deletionContext.addEntityError(
          "This UnitOfMeasure cannot be deleted as it is being referred to by StockTransferLine.");
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteBaseUomInProduct(entity, deletionContext);
    this.deleteUomInPurchaseOrderLine(entity, deletionContext);
    this.deleteUomInStockTransferLine(entity, deletionContext);
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
