package helpers;

import classes.EntityStatus;
import classes.WarehouseType;
import models.GoodsReceipt;
import models.InventoryAdjustment;
import models.InventoryMovement;
import models.Organization;
import models.PurchaseOrder;
import models.SalesOrder;
import models.SalesReturn;
import models.StockAlert;
import models.StockBatch;
import models.StockTransfer;
import models.Store;
import models.UserProfile;
import models.Warehouse;
import models.WarehouseStock;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.GoodsReceiptRepository;
import repository.jpa.InventoryAdjustmentRepository;
import repository.jpa.InventoryMovementRepository;
import repository.jpa.PurchaseOrderRepository;
import repository.jpa.SalesOrderRepository;
import repository.jpa.SalesReturnRepository;
import repository.jpa.StockAlertRepository;
import repository.jpa.StockBatchRepository;
import repository.jpa.StockTransferRepository;
import repository.jpa.StoreRepository;
import repository.jpa.UserProfileRepository;
import repository.jpa.WarehouseRepository;
import repository.jpa.WarehouseStockRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("Warehouse")
public class WarehouseEntityHelper<T extends Warehouse> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private WarehouseRepository warehouseRepository;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;
  @Autowired private InventoryAdjustmentRepository inventoryAdjustmentRepository;
  @Autowired private InventoryMovementRepository inventoryMovementRepository;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private SalesOrderRepository salesOrderRepository;
  @Autowired private SalesReturnRepository salesReturnRepository;
  @Autowired private StockAlertRepository stockAlertRepository;
  @Autowired private StockBatchRepository stockBatchRepository;
  @Autowired private StockTransferRepository stockTransferRepository;
  @Autowired private StoreRepository storeRepository;
  @Autowired private UserProfileRepository userProfileRepository;
  @Autowired private WarehouseStockRepository warehouseStockRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public Warehouse newInstance() {
    return new Warehouse();
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

  public void validateFieldWarehouseType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    WarehouseType it = entity.getWarehouseType();
    if (it == null) {
      validationContext.addFieldError("warehouseType", "warehouseType is required.");
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
    validateFieldWarehouseType(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) warehouseRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteWarehouseInGoodsReceipt(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.goodsReceiptRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by GoodsReceipt.");
    }
  }

  private void deleteWarehouseInInventoryAdjustment(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.inventoryAdjustmentRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by InventoryAdjustment.");
    }
  }

  private void deleteWarehouseInInventoryMovement(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.inventoryMovementRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by InventoryMovement.");
    }
  }

  private void deleteWarehouseInPurchaseOrder(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.purchaseOrderRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by PurchaseOrder.");
    }
  }

  private void deleteWarehouseInSalesOrder(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.salesOrderRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by SalesOrder.");
    }
  }

  private void deleteWarehouseInSalesReturn(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.salesReturnRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by SalesReturn.");
    }
  }

  private void deleteWarehouseInStockAlert(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.stockAlertRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by StockAlert.");
    }
  }

  private void deleteWarehouseInStockBatch(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.stockBatchRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by StockBatch.");
    }
  }

  private void deleteSourceWarehouseInStockTransfer(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.stockTransferRepository.getBySourceWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by StockTransfer.");
    }
  }

  private void deleteDestinationWarehouseInStockTransfer(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(
        this.stockTransferRepository.getByDestinationWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by StockTransfer.");
    }
  }

  private void deleteDefaultWarehouseInStore(T entity, EntityValidationContext deletionContext) {
    for (models.Store store : this.storeRepository.getByDefaultWarehouse(entity)) {
      store.setDefaultWarehouse(null);
    }
  }

  private void deleteAssignedWarehousesInUserProfile(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.userProfileRepository.findByAssignedWarehouses(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by UserProfile.");
    }
  }

  private void deleteWarehouseInWarehouseStock(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.warehouseStockRepository.getByWarehouse(entity))) {
      deletionContext.addEntityError(
          "This Warehouse cannot be deleted as it is being referred to by WarehouseStock.");
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteDefaultWarehouseInStore(entity, deletionContext);
    if (entity.getStore() != null) {
      entity.getStore().removeFromWarehouses(entity);
    }
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteWarehouseInGoodsReceipt(entity, deletionContext);
    this.deleteWarehouseInInventoryAdjustment(entity, deletionContext);
    this.deleteWarehouseInInventoryMovement(entity, deletionContext);
    this.deleteWarehouseInPurchaseOrder(entity, deletionContext);
    this.deleteWarehouseInSalesOrder(entity, deletionContext);
    this.deleteWarehouseInSalesReturn(entity, deletionContext);
    this.deleteWarehouseInStockAlert(entity, deletionContext);
    this.deleteWarehouseInStockBatch(entity, deletionContext);
    this.deleteSourceWarehouseInStockTransfer(entity, deletionContext);
    this.deleteDestinationWarehouseInStockTransfer(entity, deletionContext);
    this.deleteAssignedWarehousesInUserProfile(entity, deletionContext);
    this.deleteWarehouseInWarehouseStock(entity, deletionContext);
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
