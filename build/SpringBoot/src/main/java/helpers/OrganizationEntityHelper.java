package helpers;

import classes.OrganizationStatus;
import java.time.LocalDateTime;
import models.AuditLog;
import models.GoodsReceipt;
import models.InAppNotification;
import models.InventoryAdjustment;
import models.InventoryMovement;
import models.NotificationTemplate;
import models.Organization;
import models.Product;
import models.ProductCategory;
import models.PurchaseOrder;
import models.SalesOrder;
import models.SalesReturn;
import models.StockAlert;
import models.StockBatch;
import models.StockTransfer;
import models.Store;
import models.UnitOfMeasure;
import models.User;
import models.UserInvitation;
import models.UserProfile;
import models.UserRole;
import models.Vendor;
import models.Warehouse;
import models.WarehouseStock;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.AuditLogRepository;
import repository.jpa.DFileRepository;
import repository.jpa.GoodsReceiptRepository;
import repository.jpa.InAppNotificationRepository;
import repository.jpa.InventoryAdjustmentRepository;
import repository.jpa.InventoryMovementRepository;
import repository.jpa.NotificationTemplateRepository;
import repository.jpa.OrganizationRepository;
import repository.jpa.ProductCategoryRepository;
import repository.jpa.ProductRepository;
import repository.jpa.PurchaseOrderRepository;
import repository.jpa.SalesOrderRepository;
import repository.jpa.SalesReturnRepository;
import repository.jpa.StockAlertRepository;
import repository.jpa.StockBatchRepository;
import repository.jpa.StockTransferRepository;
import repository.jpa.StoreRepository;
import repository.jpa.UnitOfMeasureRepository;
import repository.jpa.UserInvitationRepository;
import repository.jpa.UserProfileRepository;
import repository.jpa.UserRepository;
import repository.jpa.UserRoleRepository;
import repository.jpa.VendorRepository;
import repository.jpa.WarehouseRepository;
import repository.jpa.WarehouseStockRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("Organization")
public class OrganizationEntityHelper<T extends Organization> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private AuditLogRepository auditLogRepository;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;
  @Autowired private InAppNotificationRepository inAppNotificationRepository;
  @Autowired private InventoryAdjustmentRepository inventoryAdjustmentRepository;
  @Autowired private InventoryMovementRepository inventoryMovementRepository;
  @Autowired private NotificationTemplateRepository notificationTemplateRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private ProductCategoryRepository productCategoryRepository;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private SalesOrderRepository salesOrderRepository;
  @Autowired private SalesReturnRepository salesReturnRepository;
  @Autowired private StockAlertRepository stockAlertRepository;
  @Autowired private StockBatchRepository stockBatchRepository;
  @Autowired private StockTransferRepository stockTransferRepository;
  @Autowired private StoreRepository storeRepository;
  @Autowired private UnitOfMeasureRepository unitOfMeasureRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private UserInvitationRepository userInvitationRepository;
  @Autowired private UserProfileRepository userProfileRepository;
  @Autowired private UserRoleRepository userRoleRepository;
  @Autowired private VendorRepository vendorRepository;
  @Autowired private WarehouseRepository warehouseRepository;
  @Autowired private WarehouseStockRepository warehouseStockRepository;
  @Autowired private DFileRepository dFileRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public Organization newInstance() {
    return new Organization();
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

  public void validateFieldCurrency(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getCurrency();
    if (it == null) {
      validationContext.addFieldError("currency", "currency is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    OrganizationStatus it = entity.getStatus();
    if (it == null) {
      validationContext.addFieldError("status", "status is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldName(entity, validationContext, onCreate, onUpdate);
    validateFieldCode(entity, validationContext, onCreate, onUpdate);
    validateFieldCurrency(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldNameUnique(entity, validationContext);
    validateFieldCodeUnique(entity, validationContext);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void validateFieldNameUnique(T entity, EntityValidationContext validationContext) {
    if (!(organizationRepository.checkNameUnique(entity.getId(), entity.getName()))) {
      validationContext.addFieldError("name", "Given name already exists");
    }
    if (!(organizationRepository.checkCodeUnique(entity.getId(), entity.getCode()))) {
      validationContext.addFieldError("code", "Given code already exists");
    }
  }

  public void validateFieldCodeUnique(T entity, EntityValidationContext validationContext) {
    if (!(organizationRepository.checkNameUnique(entity.getId(), entity.getName()))) {
      validationContext.addFieldError("name", "Given name already exists");
    }
    if (!(organizationRepository.checkCodeUnique(entity.getId(), entity.getCode()))) {
      validationContext.addFieldError("code", "Given code already exists");
    }
  }

  public void setDefaultCurrency(T entity) {
    if (entity.getCurrency() != null && !(entity.getCurrency().isEmpty())) {
      return;
    }
    entity.setCurrency("USD");
  }

  public void setDefaultTimezone(T entity) {
    if (entity.getTimezone() != null && !(entity.getTimezone().isEmpty())) {
      return;
    }
    entity.setTimezone("UTC");
  }

  public void setDefaultStatus(T entity) {
    if (entity.getStatus() != OrganizationStatus.Active) {
      return;
    }
    entity.setStatus(OrganizationStatus.Active);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) organizationRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultCurrency(entity);
    this.setDefaultTimezone(entity);
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteOrganizationInAuditLog(T entity, EntityValidationContext deletionContext) {
    for (AuditLog obj : this.auditLogRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInGoodsReceipt(T entity, EntityValidationContext deletionContext) {
    for (GoodsReceipt obj : this.goodsReceiptRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInInAppNotification(
      T entity, EntityValidationContext deletionContext) {
    for (InAppNotification obj : this.inAppNotificationRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInInventoryAdjustment(
      T entity, EntityValidationContext deletionContext) {
    for (InventoryAdjustment obj : this.inventoryAdjustmentRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInInventoryMovement(
      T entity, EntityValidationContext deletionContext) {
    for (InventoryMovement obj : this.inventoryMovementRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInNotificationTemplate(
      T entity, EntityValidationContext deletionContext) {
    for (NotificationTemplate obj : this.notificationTemplateRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInProduct(T entity, EntityValidationContext deletionContext) {
    for (Product obj : this.productRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInProductCategory(
      T entity, EntityValidationContext deletionContext) {
    for (ProductCategory obj : this.productCategoryRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInPurchaseOrder(
      T entity, EntityValidationContext deletionContext) {
    for (PurchaseOrder obj : this.purchaseOrderRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInSalesOrder(T entity, EntityValidationContext deletionContext) {
    for (SalesOrder obj : this.salesOrderRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInSalesReturn(T entity, EntityValidationContext deletionContext) {
    for (SalesReturn obj : this.salesReturnRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInStockAlert(T entity, EntityValidationContext deletionContext) {
    for (StockAlert obj : this.stockAlertRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInStockBatch(T entity, EntityValidationContext deletionContext) {
    for (StockBatch obj : this.stockBatchRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInStockTransfer(
      T entity, EntityValidationContext deletionContext) {
    for (StockTransfer obj : this.stockTransferRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInStore(T entity, EntityValidationContext deletionContext) {
    for (Store obj : this.storeRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInUnitOfMeasure(
      T entity, EntityValidationContext deletionContext) {
    for (UnitOfMeasure obj : this.unitOfMeasureRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInUser(T entity, EntityValidationContext deletionContext) {
    for (User obj : this.userRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInUserInvitation(
      T entity, EntityValidationContext deletionContext) {
    for (UserInvitation obj : this.userInvitationRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInUserProfile(T entity, EntityValidationContext deletionContext) {
    for (UserProfile obj : this.userProfileRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInUserRole(T entity, EntityValidationContext deletionContext) {
    for (UserRole obj : this.userRoleRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInVendor(T entity, EntityValidationContext deletionContext) {
    for (Vendor obj : this.vendorRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInWarehouse(T entity, EntityValidationContext deletionContext) {
    for (Warehouse obj : this.warehouseRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteOrganizationInWarehouseStock(
      T entity, EntityValidationContext deletionContext) {
    for (WarehouseStock obj : this.warehouseStockRepository.getByOrganization(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteOrganizationInAuditLog(entity, deletionContext);
    this.deleteOrganizationInGoodsReceipt(entity, deletionContext);
    this.deleteOrganizationInInAppNotification(entity, deletionContext);
    this.deleteOrganizationInInventoryAdjustment(entity, deletionContext);
    this.deleteOrganizationInInventoryMovement(entity, deletionContext);
    this.deleteOrganizationInNotificationTemplate(entity, deletionContext);
    this.deleteOrganizationInProduct(entity, deletionContext);
    this.deleteOrganizationInProductCategory(entity, deletionContext);
    this.deleteOrganizationInPurchaseOrder(entity, deletionContext);
    this.deleteOrganizationInSalesOrder(entity, deletionContext);
    this.deleteOrganizationInSalesReturn(entity, deletionContext);
    this.deleteOrganizationInStockAlert(entity, deletionContext);
    this.deleteOrganizationInStockBatch(entity, deletionContext);
    this.deleteOrganizationInStockTransfer(entity, deletionContext);
    this.deleteOrganizationInStore(entity, deletionContext);
    this.deleteOrganizationInUnitOfMeasure(entity, deletionContext);
    this.deleteOrganizationInUser(entity, deletionContext);
    this.deleteOrganizationInUserInvitation(entity, deletionContext);
    this.deleteOrganizationInUserProfile(entity, deletionContext);
    this.deleteOrganizationInUserRole(entity, deletionContext);
    this.deleteOrganizationInVendor(entity, deletionContext);
    this.deleteOrganizationInWarehouse(entity, deletionContext);
    this.deleteOrganizationInWarehouseStock(entity, deletionContext);
    return true;
  }

  public void performAction_SetTimestamps(Organization entity) {
    {
      if (entity.getCreatedAt() == null) {
        entity.setCreatedAt(LocalDateTime.now());
      }
      entity.setUpdatedAt(LocalDateTime.now());
    }
  }

  public void performOnCreateActions(Organization entity) {
    performOnCreateAndUpdateActions(entity);
  }

  public void performOnUpdateActions(Organization entity) {
    performOnCreateAndUpdateActions(entity);
  }

  public void performOnCreateAndUpdateActions(Organization entity) {
    performAction_SetTimestamps(entity);
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    performOnCreateActions(entity);
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    performOnUpdateActions(entity);
    return true;
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
