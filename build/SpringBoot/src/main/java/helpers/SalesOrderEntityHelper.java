package helpers;

import classes.PaymentStatus;
import classes.SalesOrderStatus;
import java.time.LocalDateTime;
import models.Organization;
import models.SalesOrder;
import models.SalesOrderLine;
import models.SalesReturn;
import models.Store;
import models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.SalesOrderLineRepository;
import repository.jpa.SalesOrderRepository;
import repository.jpa.SalesReturnRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("SalesOrder")
public class SalesOrderEntityHelper<T extends SalesOrder> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private SalesOrderRepository salesOrderRepository;
  @Autowired private SalesOrderLineRepository salesOrderLineRepository;
  @Autowired private SalesReturnRepository salesReturnRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public SalesOrder newInstance() {
    return new SalesOrder();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldStore(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Store it = entity.getStore();
    if (it == null) {
      validationContext.addFieldError("store", "store is required.");
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

  public void validateFieldOrderNumber(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getOrderNumber();
    if (it == null) {
      validationContext.addFieldError("orderNumber", "orderNumber is required.");
      return;
    }
  }

  public void validateFieldOrderDate(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDateTime it = entity.getOrderDate();
    if (it == null) {
      validationContext.addFieldError("orderDate", "orderDate is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    SalesOrderStatus it = entity.getStatus();
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
    validateFieldStore(entity, validationContext, onCreate, onUpdate);
    validateFieldWarehouse(entity, validationContext, onCreate, onUpdate);
    validateFieldOrderNumber(entity, validationContext, onCreate, onUpdate);
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
    if (entity.getStatus() != SalesOrderStatus.Draft) {
      return;
    }
    entity.setStatus(SalesOrderStatus.Draft);
  }

  public void setDefaultDiscountAmount(T entity) {
    if (entity.getDiscountAmount() != 0.0d) {
      return;
    }
    entity.setDiscountAmount(0.0d);
  }

  public void setDefaultTaxAmount(T entity) {
    if (entity.getTaxAmount() != 0.0d) {
      return;
    }
    entity.setTaxAmount(0.0d);
  }

  public void setDefaultPaymentStatus(T entity) {
    if (entity.getPaymentStatus() != PaymentStatus.Unpaid) {
      return;
    }
    entity.setPaymentStatus(PaymentStatus.Unpaid);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) salesOrderRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
    this.setDefaultDiscountAmount(entity);
    this.setDefaultTaxAmount(entity);
    this.setDefaultPaymentStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteSalesOrderInSalesOrderLine(T entity, EntityValidationContext deletionContext) {
    for (SalesOrderLine obj : this.salesOrderLineRepository.getBySalesOrder(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteSalesOrderInSalesReturn(T entity, EntityValidationContext deletionContext) {
    for (models.SalesReturn salesReturn : this.salesReturnRepository.getBySalesOrder(entity)) {
      salesReturn.setSalesOrder(null);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteSalesOrderInSalesOrderLine(entity, deletionContext);
    this.deleteSalesOrderInSalesReturn(entity, deletionContext);
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
