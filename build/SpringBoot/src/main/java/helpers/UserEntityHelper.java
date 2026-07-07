package helpers;

import classes.AppUserRole;
import classes.EntityStatus;
import models.AuditLog;
import models.GoodsReceipt;
import models.InAppNotification;
import models.InventoryAdjustment;
import models.InventoryMovement;
import models.Organization;
import models.PurchaseOrder;
import models.SalesOrder;
import models.SalesReturn;
import models.StockAlert;
import models.StockTransfer;
import models.Store;
import models.User;
import models.UserInvitation;
import models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import repository.jpa.AuditLogRepository;
import repository.jpa.GoodsReceiptRepository;
import repository.jpa.InAppNotificationRepository;
import repository.jpa.InventoryAdjustmentRepository;
import repository.jpa.InventoryMovementRepository;
import repository.jpa.OrganizationRepository;
import repository.jpa.PurchaseOrderRepository;
import repository.jpa.SalesOrderRepository;
import repository.jpa.SalesReturnRepository;
import repository.jpa.StockAlertRepository;
import repository.jpa.StockTransferRepository;
import repository.jpa.StoreRepository;
import repository.jpa.UserInvitationRepository;
import repository.jpa.UserProfileRepository;
import repository.jpa.UserRepository;
import store.Database;
import store.EntityHelper;
import store.EntityValidationContext;
import java.util.Objects;

@org.springframework.stereotype.Service("User")
public class UserEntityHelper<T extends User> extends BaseUserEntityHelper<T> {
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserRepository userRepository;
  @Autowired private AuditLogRepository auditLogRepository;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;
  @Autowired private InAppNotificationRepository inAppNotificationRepository;
  @Autowired private InventoryAdjustmentRepository inventoryAdjustmentRepository;
  @Autowired private InventoryMovementRepository inventoryMovementRepository;
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private SalesOrderRepository salesOrderRepository;
  @Autowired private SalesReturnRepository salesReturnRepository;
  @Autowired private StockAlertRepository stockAlertRepository;
  @Autowired private StockTransferRepository stockTransferRepository;
  @Autowired private StoreRepository storeRepository;
  @Autowired private UserInvitationRepository userInvitationRepository;
  @Autowired private UserProfileRepository userProfileRepository;

  public User newInstance() {
    return new User();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldEmail(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getEmail();
    if (it == null) {
      validationContext.addFieldError("email", "email is required.");
      return;
    }
  }

  public void validateFieldPassword(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getPassword();
    if (it == null) {
      validationContext.addFieldError("password", "password is required.");
      return;
    }
  }

  public void validateFieldRole(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    AppUserRole it = entity.getRole();
    if (it == null) {
      validationContext.addFieldError("role", "role is required.");
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
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    validateFieldEmail(entity, validationContext, onCreate, onUpdate);
    validateFieldPassword(entity, validationContext, onCreate, onUpdate);
    validateFieldRole(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
    validateFieldEmailUnique(entity, validationContext);
    validateFieldPasswordUnique(entity, validationContext);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    super.validateOnCreate(entity, validationContext);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    super.validateOnUpdate(entity, validationContext);
  }

  public void validateFieldEmailUnique(T entity, EntityValidationContext validationContext) {
    if (entity.getOrganization() != null
        && !(userRepository.checkUserEmailUniqueInOrganization(
            entity.getOrganization().getId(), entity.getId(), entity.getEmail()))) {
      validationContext.addFieldError(
          "email", "Given email already exists in the same Organization");
    }
    if (entity.getOrganization() != null
        && !(userRepository.checkUserPasswordUniqueInOrganization(
            entity.getOrganization().getId(), entity.getId(), entity.getPassword()))) {
      validationContext.addFieldError(
          "password", "Given password already exists in the same Organization");
    }
  }

  public void validateFieldPasswordUnique(T entity, EntityValidationContext validationContext) {
    if (entity.getOrganization() != null
        && !(userRepository.checkUserEmailUniqueInOrganization(
            entity.getOrganization().getId(), entity.getId(), entity.getEmail()))) {
      validationContext.addFieldError(
          "email", "Given email already exists in the same Organization");
    }
    if (entity.getOrganization() != null
        && !(userRepository.checkUserPasswordUniqueInOrganization(
            entity.getOrganization().getId(), entity.getId(), entity.getPassword()))) {
      validationContext.addFieldError(
          "password", "Given password already exists in the same Organization");
    }
  }

  public void setDefaultRole(T entity) {
    if (entity.getRole() != AppUserRole.OrganizationAdmin) {
      return;
    }
    entity.setRole(AppUserRole.Viewer);
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
    return id == 0l ? null : ((T) userRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultRole(entity);
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deletePerformedByInAuditLog(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.auditLogRepository.getByPerformedBy(entity))) {
      deletionContext.addEntityError(
          "This User cannot be deleted as it is being referred to by AuditLog.");
    }
  }

  private void deleteReceivedByInGoodsReceipt(T entity, EntityValidationContext deletionContext) {
    for (models.GoodsReceipt goodsReceipt : this.goodsReceiptRepository.getByReceivedBy(entity)) {
      goodsReceipt.setReceivedBy(null);
    }
  }

  private void deleteRecipientInInAppNotification(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.inAppNotificationRepository.getByRecipient(entity))) {
      deletionContext.addEntityError(
          "This User cannot be deleted as it is being referred to by InAppNotification.");
    }
  }

  private void deleteAdjustedByInInventoryAdjustment(
      T entity, EntityValidationContext deletionContext) {
    for (models.InventoryAdjustment inventoryAdjustment :
        this.inventoryAdjustmentRepository.getByAdjustedBy(entity)) {
      inventoryAdjustment.setAdjustedBy(null);
    }
  }

  private void deletePerformedByInInventoryMovement(
      T entity, EntityValidationContext deletionContext) {
    for (models.InventoryMovement inventoryMovement :
        this.inventoryMovementRepository.getByPerformedBy(entity)) {
      inventoryMovement.setPerformedBy(null);
    }
  }

  private void deleteCreatedByInOrganization(T entity, EntityValidationContext deletionContext) {
    for (models.Organization organization : this.organizationRepository.getByCreatedBy(entity)) {
      organization.setCreatedBy(null);
    }
  }

  private void deleteCreatedByInPurchaseOrder(T entity, EntityValidationContext deletionContext) {
    for (models.PurchaseOrder purchaseOrder : this.purchaseOrderRepository.getByCreatedBy(entity)) {
      purchaseOrder.setCreatedBy(null);
    }
  }

  private void deleteApprovedByInPurchaseOrder(T entity, EntityValidationContext deletionContext) {
    for (models.PurchaseOrder purchaseOrder :
        this.purchaseOrderRepository.getByApprovedBy(entity)) {
      purchaseOrder.setApprovedBy(null);
    }
  }

  private void deleteSoldByInSalesOrder(T entity, EntityValidationContext deletionContext) {
    for (models.SalesOrder salesOrder : this.salesOrderRepository.getBySoldBy(entity)) {
      salesOrder.setSoldBy(null);
    }
  }

  private void deleteProcessedByInSalesReturn(T entity, EntityValidationContext deletionContext) {
    for (models.SalesReturn salesReturn : this.salesReturnRepository.getByProcessedBy(entity)) {
      salesReturn.setProcessedBy(null);
    }
  }

  private void deleteAcknowledgedByInStockAlert(T entity, EntityValidationContext deletionContext) {
    for (models.StockAlert stockAlert : this.stockAlertRepository.getByAcknowledgedBy(entity)) {
      stockAlert.setAcknowledgedBy(null);
    }
  }

  private void deleteRequestedByInStockTransfer(T entity, EntityValidationContext deletionContext) {
    for (models.StockTransfer stockTransfer :
        this.stockTransferRepository.getByRequestedBy(entity)) {
      stockTransfer.setRequestedBy(null);
    }
  }

  private void deleteApprovedByInStockTransfer(T entity, EntityValidationContext deletionContext) {
    for (models.StockTransfer stockTransfer :
        this.stockTransferRepository.getByApprovedBy(entity)) {
      stockTransfer.setApprovedBy(null);
    }
  }

  private void deleteManagerInStore(T entity, EntityValidationContext deletionContext) {
    for (models.Store store : this.storeRepository.getByManager(entity)) {
      store.setManager(null);
    }
  }

  private void deleteInvitedByInUserInvitation(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.userInvitationRepository.getByInvitedBy(entity))) {
      deletionContext.addEntityError(
          "This User cannot be deleted as it is being referred to by UserInvitation.");
    }
  }

  private void deleteUserInUserProfile(T entity, EntityValidationContext deletionContext) {
    if (this.userProfileRepository.getByUser(entity) != null) {
      deletionContext.addEntityError(
          "This User cannot be deleted as it is being referred to by UserProfile.");
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    super.onDelete(entity, internal, deletionContext);
    this.deleteReceivedByInGoodsReceipt(entity, deletionContext);
    this.deleteAdjustedByInInventoryAdjustment(entity, deletionContext);
    this.deletePerformedByInInventoryMovement(entity, deletionContext);
    this.deleteCreatedByInOrganization(entity, deletionContext);
    this.deleteCreatedByInPurchaseOrder(entity, deletionContext);
    this.deleteApprovedByInPurchaseOrder(entity, deletionContext);
    this.deleteSoldByInSalesOrder(entity, deletionContext);
    this.deleteProcessedByInSalesReturn(entity, deletionContext);
    this.deleteAcknowledgedByInStockAlert(entity, deletionContext);
    this.deleteRequestedByInStockTransfer(entity, deletionContext);
    this.deleteApprovedByInStockTransfer(entity, deletionContext);
    this.deleteManagerInStore(entity, deletionContext);
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deletePerformedByInAuditLog(entity, deletionContext);
    this.deleteRecipientInInAppNotification(entity, deletionContext);
    this.deleteInvitedByInUserInvitation(entity, deletionContext);
    this.deleteUserInUserProfile(entity, deletionContext);
  }

  private boolean isAlreadyEncoded(String password) {
    return password != null && password.startsWith("$2");
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    super.onCreate(entity, internal, context);
    if (entity.getPassword() != null && !isAlreadyEncoded(entity.getPassword())) {
      entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    }
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    super.onUpdate(entity, internal, context);
    if (entity._changes().changes.get(User._PASSWORD)
        && !isAlreadyEncoded(entity.getPassword())) {
      entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    }
    syncProfileAppRole(entity);
    return true;
  }

  private void syncProfileAppRole(T entity) {
    if (entity.getRole() == null) {
      return;
    }
    UserProfile profile = userProfileRepository.getByUser(entity);
    if (profile == null || Objects.equals(profile.getAppRole(), entity.getRole())) {
      return;
    }
    profile.setAppRole(entity.getRole());
    Database.get().save(profile);
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
