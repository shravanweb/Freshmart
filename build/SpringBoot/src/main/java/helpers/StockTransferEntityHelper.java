package helpers;

import classes.StockTransferStatus;
import java.time.LocalDate;
import models.Organization;
import models.StockTransfer;
import models.StockTransferLine;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.StockTransferLineRepository;
import repository.jpa.StockTransferRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("StockTransfer")
public class StockTransferEntityHelper<T extends StockTransfer> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private StockTransferRepository stockTransferRepository;
  @Autowired private StockTransferLineRepository stockTransferLineRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public StockTransfer newInstance() {
    return new StockTransfer();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldTransferNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getTransferNumber();
    if (it == null) {
      validationContext.addFieldError("transferNumber", "transferNumber is required.");
      return;
    }
  }

  public void validateFieldSourceWarehouse(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Warehouse it = entity.getSourceWarehouse();
    if (it == null) {
      validationContext.addFieldError("sourceWarehouse", "sourceWarehouse is required.");
      return;
    }
  }

  public void validateFieldDestinationWarehouse(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Warehouse it = entity.getDestinationWarehouse();
    if (it == null) {
      validationContext.addFieldError("destinationWarehouse", "destinationWarehouse is required.");
      return;
    }
  }

  public void validateFieldTransferDate(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDate it = entity.getTransferDate();
    if (it == null) {
      validationContext.addFieldError("transferDate", "transferDate is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    StockTransferStatus it = entity.getStatus();
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
    validateFieldTransferNumber(entity, validationContext, onCreate, onUpdate);
    validateFieldSourceWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldDestinationWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldTransferDate(entity, validationContext, onCreate, onUpdate);
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
    if (entity.getStatus() != StockTransferStatus.Draft) {
      return;
    }
    entity.setStatus(StockTransferStatus.Draft);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) stockTransferRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteStockTransferInStockTransferLine(
      T entity, EntityValidationContext deletionContext) {
    for (StockTransferLine obj : this.stockTransferLineRepository.getByStockTransfer(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteStockTransferInStockTransferLine(entity, deletionContext);
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
