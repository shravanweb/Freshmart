package d3e.core;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import jakarta.annotation.PostConstruct;
import models.AnonymousUser;
import models.AuditLog;
import models.BaseUser;
import models.BaseUserSession;
import models.GoodsReceipt;
import models.GoodsReceiptLine;
import models.InAppNotification;
import models.InventoryAdjustment;
import models.InventoryAdjustmentLine;
import models.InventoryMovement;
import models.NotificationTemplate;
import models.OneTimePassword;
import models.Organization;
import models.Product;
import models.ProductCategory;
import models.PurchaseOrder;
import models.PurchaseOrderLine;
import models.PushNotification;
import models.Report;
import models.SalesOrder;
import models.SalesOrderLine;
import models.SalesReturn;
import models.SalesReturnLine;
import models.StockAlert;
import models.StockBatch;
import models.StockTransfer;
import models.StockTransferLine;
import models.Store;
import models.SupplierContact;
import models.UnitOfMeasure;
import models.User;
import models.UserDevice;
import models.UserInvitation;
import models.UserLoginRecord;
import models.UserProfile;
import models.UserRole;
import models.Vendor;
import models.VerificationData;
import models.Warehouse;
import models.WarehouseStock;
import org.springframework.scheduling.annotation.Async;
import store.DataStoreEvent;

@org.springframework.stereotype.Service
public class D3ESubscription implements FlowableOnSubscribe<DataStoreEvent> {
  public ConnectableFlowable<DataStoreEvent> flowable;
  private FlowableEmitter<DataStoreEvent> emitter;

  @PostConstruct
  public void init() {
    this.flowable = Flowable.create(this, BackpressureStrategy.BUFFER).publish();
    this.flowable.connect();
    flowable.subscribe((a) -> {});
  }

  @Async
  public void handleContextStart(DataStoreEvent event) {
    this.emitter.onNext(event);
  }

  @Override
  public void subscribe(FlowableEmitter<DataStoreEvent> emitter) throws Throwable {
    this.emitter = emitter;
  }

  public Flowable<D3ESubscriptionEvent<AnonymousUser>> onAnonymousUserChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof AnonymousUser)
        .map(
            (e) -> {
              D3ESubscriptionEvent<AnonymousUser> event = new D3ESubscriptionEvent<>();
              event.model = ((AnonymousUser) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<AuditLog>> onAuditLogChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof AuditLog)
        .map(
            (e) -> {
              D3ESubscriptionEvent<AuditLog> event = new D3ESubscriptionEvent<>();
              event.model = ((AuditLog) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<BaseUser>> onBaseUserChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof BaseUser)
        .map(
            (e) -> {
              D3ESubscriptionEvent<BaseUser> event = new D3ESubscriptionEvent<>();
              event.model = ((BaseUser) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<BaseUserSession>> onBaseUserSessionChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof BaseUserSession)
        .map(
            (e) -> {
              D3ESubscriptionEvent<BaseUserSession> event = new D3ESubscriptionEvent<>();
              event.model = ((BaseUserSession) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<GoodsReceipt>> onGoodsReceiptChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof GoodsReceipt)
        .map(
            (e) -> {
              D3ESubscriptionEvent<GoodsReceipt> event = new D3ESubscriptionEvent<>();
              event.model = ((GoodsReceipt) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<GoodsReceiptLine>> onGoodsReceiptLineChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof GoodsReceiptLine)
        .map(
            (e) -> {
              D3ESubscriptionEvent<GoodsReceiptLine> event = new D3ESubscriptionEvent<>();
              event.model = ((GoodsReceiptLine) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<InAppNotification>> onInAppNotificationChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof InAppNotification)
        .map(
            (e) -> {
              D3ESubscriptionEvent<InAppNotification> event = new D3ESubscriptionEvent<>();
              event.model = ((InAppNotification) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<InventoryAdjustment>> onInventoryAdjustmentChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof InventoryAdjustment)
        .map(
            (e) -> {
              D3ESubscriptionEvent<InventoryAdjustment> event = new D3ESubscriptionEvent<>();
              event.model = ((InventoryAdjustment) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<InventoryAdjustmentLine>>
      onInventoryAdjustmentLineChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof InventoryAdjustmentLine)
        .map(
            (e) -> {
              D3ESubscriptionEvent<InventoryAdjustmentLine> event = new D3ESubscriptionEvent<>();
              event.model = ((InventoryAdjustmentLine) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<InventoryMovement>> onInventoryMovementChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof InventoryMovement)
        .map(
            (e) -> {
              D3ESubscriptionEvent<InventoryMovement> event = new D3ESubscriptionEvent<>();
              event.model = ((InventoryMovement) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<NotificationTemplate>> onNotificationTemplateChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof NotificationTemplate)
        .map(
            (e) -> {
              D3ESubscriptionEvent<NotificationTemplate> event = new D3ESubscriptionEvent<>();
              event.model = ((NotificationTemplate) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<OneTimePassword>> onOneTimePasswordChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof OneTimePassword)
        .map(
            (e) -> {
              D3ESubscriptionEvent<OneTimePassword> event = new D3ESubscriptionEvent<>();
              event.model = ((OneTimePassword) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<Organization>> onOrganizationChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof Organization)
        .map(
            (e) -> {
              D3ESubscriptionEvent<Organization> event = new D3ESubscriptionEvent<>();
              event.model = ((Organization) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<Product>> onProductChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof Product)
        .map(
            (e) -> {
              D3ESubscriptionEvent<Product> event = new D3ESubscriptionEvent<>();
              event.model = ((Product) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<ProductCategory>> onProductCategoryChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof ProductCategory)
        .map(
            (e) -> {
              D3ESubscriptionEvent<ProductCategory> event = new D3ESubscriptionEvent<>();
              event.model = ((ProductCategory) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<PurchaseOrder>> onPurchaseOrderChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof PurchaseOrder)
        .map(
            (e) -> {
              D3ESubscriptionEvent<PurchaseOrder> event = new D3ESubscriptionEvent<>();
              event.model = ((PurchaseOrder) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<PurchaseOrderLine>> onPurchaseOrderLineChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof PurchaseOrderLine)
        .map(
            (e) -> {
              D3ESubscriptionEvent<PurchaseOrderLine> event = new D3ESubscriptionEvent<>();
              event.model = ((PurchaseOrderLine) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<PushNotification>> onPushNotificationChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof PushNotification)
        .map(
            (e) -> {
              D3ESubscriptionEvent<PushNotification> event = new D3ESubscriptionEvent<>();
              event.model = ((PushNotification) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<Report>> onReportChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof Report)
        .map(
            (e) -> {
              D3ESubscriptionEvent<Report> event = new D3ESubscriptionEvent<>();
              event.model = ((Report) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<SalesOrder>> onSalesOrderChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof SalesOrder)
        .map(
            (e) -> {
              D3ESubscriptionEvent<SalesOrder> event = new D3ESubscriptionEvent<>();
              event.model = ((SalesOrder) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<SalesOrderLine>> onSalesOrderLineChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof SalesOrderLine)
        .map(
            (e) -> {
              D3ESubscriptionEvent<SalesOrderLine> event = new D3ESubscriptionEvent<>();
              event.model = ((SalesOrderLine) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<SalesReturn>> onSalesReturnChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof SalesReturn)
        .map(
            (e) -> {
              D3ESubscriptionEvent<SalesReturn> event = new D3ESubscriptionEvent<>();
              event.model = ((SalesReturn) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<SalesReturnLine>> onSalesReturnLineChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof SalesReturnLine)
        .map(
            (e) -> {
              D3ESubscriptionEvent<SalesReturnLine> event = new D3ESubscriptionEvent<>();
              event.model = ((SalesReturnLine) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<StockAlert>> onStockAlertChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof StockAlert)
        .map(
            (e) -> {
              D3ESubscriptionEvent<StockAlert> event = new D3ESubscriptionEvent<>();
              event.model = ((StockAlert) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<StockBatch>> onStockBatchChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof StockBatch)
        .map(
            (e) -> {
              D3ESubscriptionEvent<StockBatch> event = new D3ESubscriptionEvent<>();
              event.model = ((StockBatch) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<StockTransfer>> onStockTransferChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof StockTransfer)
        .map(
            (e) -> {
              D3ESubscriptionEvent<StockTransfer> event = new D3ESubscriptionEvent<>();
              event.model = ((StockTransfer) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<StockTransferLine>> onStockTransferLineChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof StockTransferLine)
        .map(
            (e) -> {
              D3ESubscriptionEvent<StockTransferLine> event = new D3ESubscriptionEvent<>();
              event.model = ((StockTransferLine) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<Store>> onStoreChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof Store)
        .map(
            (e) -> {
              D3ESubscriptionEvent<Store> event = new D3ESubscriptionEvent<>();
              event.model = ((Store) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<SupplierContact>> onSupplierContactChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof SupplierContact)
        .map(
            (e) -> {
              D3ESubscriptionEvent<SupplierContact> event = new D3ESubscriptionEvent<>();
              event.model = ((SupplierContact) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<UnitOfMeasure>> onUnitOfMeasureChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof UnitOfMeasure)
        .map(
            (e) -> {
              D3ESubscriptionEvent<UnitOfMeasure> event = new D3ESubscriptionEvent<>();
              event.model = ((UnitOfMeasure) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<User>> onUserChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof User)
        .map(
            (e) -> {
              D3ESubscriptionEvent<User> event = new D3ESubscriptionEvent<>();
              event.model = ((User) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<UserDevice>> onUserDeviceChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof UserDevice)
        .map(
            (e) -> {
              D3ESubscriptionEvent<UserDevice> event = new D3ESubscriptionEvent<>();
              event.model = ((UserDevice) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<UserInvitation>> onUserInvitationChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof UserInvitation)
        .map(
            (e) -> {
              D3ESubscriptionEvent<UserInvitation> event = new D3ESubscriptionEvent<>();
              event.model = ((UserInvitation) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<UserLoginRecord>> onUserLoginRecordChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof UserLoginRecord)
        .map(
            (e) -> {
              D3ESubscriptionEvent<UserLoginRecord> event = new D3ESubscriptionEvent<>();
              event.model = ((UserLoginRecord) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<UserProfile>> onUserProfileChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof UserProfile)
        .map(
            (e) -> {
              D3ESubscriptionEvent<UserProfile> event = new D3ESubscriptionEvent<>();
              event.model = ((UserProfile) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<UserRole>> onUserRoleChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof UserRole)
        .map(
            (e) -> {
              D3ESubscriptionEvent<UserRole> event = new D3ESubscriptionEvent<>();
              event.model = ((UserRole) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<Vendor>> onVendorChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof Vendor)
        .map(
            (e) -> {
              D3ESubscriptionEvent<Vendor> event = new D3ESubscriptionEvent<>();
              event.model = ((Vendor) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<VerificationData>> onVerificationDataChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof VerificationData)
        .map(
            (e) -> {
              D3ESubscriptionEvent<VerificationData> event = new D3ESubscriptionEvent<>();
              event.model = ((VerificationData) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<Warehouse>> onWarehouseChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof Warehouse)
        .map(
            (e) -> {
              D3ESubscriptionEvent<Warehouse> event = new D3ESubscriptionEvent<>();
              event.model = ((Warehouse) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }

  public Flowable<D3ESubscriptionEvent<WarehouseStock>> onWarehouseStockChangeEvent() {
    return this.flowable
        .filter((e) -> e.getEntity() instanceof WarehouseStock)
        .map(
            (e) -> {
              D3ESubscriptionEvent<WarehouseStock> event = new D3ESubscriptionEvent<>();
              event.model = ((WarehouseStock) e.getEntity());
              event.changeType = e.getType();
              return event;
            });
  }
}
