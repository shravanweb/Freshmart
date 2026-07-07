package rest.ws;

import classes.AllAuditLogs;
import classes.AllGoodsReceipts;
import classes.AllInAppNotifications;
import classes.AllInventoryAdjustments;
import classes.AllInventoryMovements;
import classes.AllOrganizations;
import classes.AllProductCategories;
import classes.AllProducts;
import classes.AllPurchaseOrders;
import classes.AllSalesOrders;
import classes.AllSalesReturns;
import classes.AllStockAlerts;
import classes.AllStockBatches;
import classes.AllStockTransfers;
import classes.AllStores;
import classes.AllSuppliers;
import classes.AllUnitOfMeasures;
import classes.AllUserInvitations;
import classes.AllUserProfiles;
import classes.AllWarehouses;
import classes.DashboardMetrics;
import classes.ExpiringBatches;
import classes.GoodsReceiptItem;
import classes.InventoryMovementsByDateRange;
import classes.LowStockItems;
import classes.MovementReportRows;
import classes.MutateResultStatus;
import classes.OrganizationItem;
import classes.OutOfStockItems;
import classes.ProductItem;
import classes.ProductSearch;
import classes.ProductsByCategory;
import classes.PurchaseOrderItem;
import classes.PurchaseOrdersByStatus;
import classes.SalesOrderItem;
import classes.SalesOrdersByStore;
import classes.StockTransferItem;
import classes.StockValuationReport;
import classes.StoreItem;
import classes.SupplierItem;
import classes.UnreadNotificationCount;
import classes.UserProfileByUser;
import classes.WarehouseStockByProduct;
import classes.WarehouseStockByWarehouse;
import classes.WarehousesByStore;
import d3e.core.CurrentUser;
import d3e.core.ListExt;
import d3e.core.Log;
import gqltosql2.Field;
import gqltosql2.GqlToSql;
import gqltosql2.OutObject;
import java.util.List;
import lists.AllAuditLogsChangeTracker;
import lists.AllAuditLogsImpl;
import lists.AllDevicesImpl;
import lists.AllGoodsReceiptsChangeTracker;
import lists.AllGoodsReceiptsImpl;
import lists.AllInAppNotificationsChangeTracker;
import lists.AllInAppNotificationsImpl;
import lists.AllInventoryAdjustmentsChangeTracker;
import lists.AllInventoryAdjustmentsImpl;
import lists.AllInventoryMovementsChangeTracker;
import lists.AllInventoryMovementsImpl;
import lists.AllOrganizationsChangeTracker;
import lists.AllOrganizationsImpl;
import lists.AllProductCategoriesChangeTracker;
import lists.AllProductCategoriesImpl;
import lists.AllProductsChangeTracker;
import lists.AllProductsImpl;
import lists.AllPurchaseOrdersChangeTracker;
import lists.AllPurchaseOrdersImpl;
import lists.AllSalesOrdersChangeTracker;
import lists.AllSalesOrdersImpl;
import lists.AllSalesReturnsChangeTracker;
import lists.AllSalesReturnsImpl;
import lists.AllStockAlertsChangeTracker;
import lists.AllStockAlertsImpl;
import lists.AllStockBatchesChangeTracker;
import lists.AllStockBatchesImpl;
import lists.AllStockTransfersChangeTracker;
import lists.AllStockTransfersImpl;
import lists.AllStoresChangeTracker;
import lists.AllStoresImpl;
import lists.AllSuppliersChangeTracker;
import lists.AllSuppliersImpl;
import lists.AllUnitOfMeasuresChangeTracker;
import lists.AllUnitOfMeasuresImpl;
import lists.AllUserInvitationsChangeTracker;
import lists.AllUserInvitationsImpl;
import lists.AllUserProfilesChangeTracker;
import lists.AllUserProfilesImpl;
import lists.AllWarehousesChangeTracker;
import lists.AllWarehousesImpl;
import lists.DashboardMetricsChangeTracker;
import lists.DashboardMetricsImpl;
import lists.ExpiringBatchesChangeTracker;
import lists.ExpiringBatchesImpl;
import lists.GoodsReceiptItemChangeTracker;
import lists.GoodsReceiptItemImpl;
import lists.InventoryMovementsByDateRangeChangeTracker;
import lists.InventoryMovementsByDateRangeImpl;
import lists.LowStockItemsChangeTracker;
import lists.LowStockItemsImpl;
import lists.MovementReportRowsChangeTracker;
import lists.MovementReportRowsImpl;
import lists.NativeObj;
import lists.OrganizationItemChangeTracker;
import lists.OrganizationItemImpl;
import lists.OutOfStockItemsChangeTracker;
import lists.OutOfStockItemsImpl;
import lists.ProductItemChangeTracker;
import lists.ProductItemImpl;
import lists.ProductSearchChangeTracker;
import lists.ProductSearchImpl;
import lists.ProductsByCategoryChangeTracker;
import lists.ProductsByCategoryImpl;
import lists.PurchaseOrderItemChangeTracker;
import lists.PurchaseOrderItemImpl;
import lists.PurchaseOrdersByStatusChangeTracker;
import lists.PurchaseOrdersByStatusImpl;
import lists.SalesOrderItemChangeTracker;
import lists.SalesOrderItemImpl;
import lists.SalesOrdersByStoreChangeTracker;
import lists.SalesOrdersByStoreImpl;
import lists.StockTransferItemChangeTracker;
import lists.StockTransferItemImpl;
import lists.StockValuationReportChangeTracker;
import lists.StockValuationReportImpl;
import lists.StoreItemChangeTracker;
import lists.StoreItemImpl;
import lists.SupplierItemChangeTracker;
import lists.SupplierItemImpl;
import lists.UnreadNotificationCountChangeTracker;
import lists.UnreadNotificationCountImpl;
import lists.UserDevicesImpl;
import lists.UserProfileByUserChangeTracker;
import lists.UserProfileByUserImpl;
import lists.VerificationDataByTokenImpl;
import lists.WarehouseStockByProductChangeTracker;
import lists.WarehouseStockByProductImpl;
import lists.WarehouseStockByWarehouseChangeTracker;
import lists.WarehouseStockByWarehouseImpl;
import lists.WarehousesByStoreChangeTracker;
import lists.WarehousesByStoreImpl;
import models.AllAuditLogsRequest;
import models.AllGoodsReceiptsRequest;
import models.AllInAppNotificationsRequest;
import models.AllInventoryAdjustmentsRequest;
import models.AllInventoryMovementsRequest;
import models.AllProductCategoriesRequest;
import models.AllProductsRequest;
import models.AllPurchaseOrdersRequest;
import models.AllSalesOrdersRequest;
import models.AllSalesReturnsRequest;
import models.AllStockAlertsRequest;
import models.AllStockBatchesRequest;
import models.AllStockTransfersRequest;
import models.AllStoresRequest;
import models.AllSuppliersRequest;
import models.AllUnitOfMeasuresRequest;
import models.AllUserInvitationsRequest;
import models.AllUserProfilesRequest;
import models.AllWarehousesRequest;
import models.BaseUser;
import models.DashboardMetricsRequest;
import models.ExpiringBatchesRequest;
import models.GoodsReceiptItemRequest;
import models.InventoryMovementsByDateRangeRequest;
import models.LowStockItemsRequest;
import models.MovementReportRowsRequest;
import models.OrganizationItemRequest;
import models.OutOfStockItemsRequest;
import models.ProductItemRequest;
import models.ProductSearchRequest;
import models.ProductsByCategoryRequest;
import models.PurchaseOrderItemRequest;
import models.PurchaseOrdersByStatusRequest;
import models.SalesOrderItemRequest;
import models.SalesOrdersByStoreRequest;
import models.StockTransferItemRequest;
import models.StockValuationReportRequest;
import models.StoreItemRequest;
import models.SupplierItemRequest;
import models.UnreadNotificationCountRequest;
import models.User;
import models.UserProfileByUserRequest;
import models.WarehouseStockByProductRequest;
import models.WarehouseStockByWarehouseRequest;
import models.WarehousesByStoreRequest;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.jpa.AnonymousUserRepository;
import repository.jpa.OneTimePasswordRepository;
import repository.jpa.UserRepository;
import security.AppSessionProvider;
import security.JwtTokenUtil;
import store.ValidationFailedException;

@Service
public class RocketQuery extends AbstractRocketQuery {
  @Autowired private GqlToSql gqlToSql;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private ObjectFactory<AppSessionProvider> provider;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private AnonymousUserRepository anonymousUserRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;
  @Autowired private AllDevicesImpl allDevicesImpl;
  @Autowired private UserDevicesImpl userDevicesImpl;
  @Autowired private VerificationDataByTokenImpl verificationDataByTokenImpl;
  @Autowired private AllAuditLogsImpl allAuditLogsImpl;
  @Autowired private AllGoodsReceiptsImpl allGoodsReceiptsImpl;
  @Autowired private AllInAppNotificationsImpl allInAppNotificationsImpl;
  @Autowired private AllInventoryAdjustmentsImpl allInventoryAdjustmentsImpl;
  @Autowired private AllInventoryMovementsImpl allInventoryMovementsImpl;
  @Autowired private AllOrganizationsImpl allOrganizationsImpl;
  @Autowired private AllProductCategoriesImpl allProductCategoriesImpl;
  @Autowired private AllProductsImpl allProductsImpl;
  @Autowired private AllPurchaseOrdersImpl allPurchaseOrdersImpl;
  @Autowired private AllSalesOrdersImpl allSalesOrdersImpl;
  @Autowired private AllSalesReturnsImpl allSalesReturnsImpl;
  @Autowired private AllStockAlertsImpl allStockAlertsImpl;
  @Autowired private AllStockBatchesImpl allStockBatchesImpl;
  @Autowired private AllStockTransfersImpl allStockTransfersImpl;
  @Autowired private AllStoresImpl allStoresImpl;
  @Autowired private AllSuppliersImpl allSuppliersImpl;
  @Autowired private AllUnitOfMeasuresImpl allUnitOfMeasuresImpl;
  @Autowired private AllUserInvitationsImpl allUserInvitationsImpl;
  @Autowired private AllUserProfilesImpl allUserProfilesImpl;
  @Autowired private AllWarehousesImpl allWarehousesImpl;
  @Autowired private DashboardMetricsImpl dashboardMetricsImpl;
  @Autowired private ExpiringBatchesImpl expiringBatchesImpl;
  @Autowired private GoodsReceiptItemImpl goodsReceiptItemImpl;
  @Autowired private InventoryMovementsByDateRangeImpl inventoryMovementsByDateRangeImpl;
  @Autowired private LowStockItemsImpl lowStockItemsImpl;
  @Autowired private MovementReportRowsImpl movementReportRowsImpl;
  @Autowired private OrganizationItemImpl organizationItemImpl;
  @Autowired private OutOfStockItemsImpl outOfStockItemsImpl;
  @Autowired private ProductItemImpl productItemImpl;
  @Autowired private ProductSearchImpl productSearchImpl;
  @Autowired private ProductsByCategoryImpl productsByCategoryImpl;
  @Autowired private PurchaseOrderItemImpl purchaseOrderItemImpl;
  @Autowired private PurchaseOrdersByStatusImpl purchaseOrdersByStatusImpl;
  @Autowired private SalesOrderItemImpl salesOrderItemImpl;
  @Autowired private SalesOrdersByStoreImpl salesOrdersByStoreImpl;
  @Autowired private StockTransferItemImpl stockTransferItemImpl;
  @Autowired private StockValuationReportImpl stockValuationReportImpl;
  @Autowired private StoreItemImpl storeItemImpl;
  @Autowired private SupplierItemImpl supplierItemImpl;
  @Autowired private UnreadNotificationCountImpl unreadNotificationCountImpl;
  @Autowired private UserProfileByUserImpl userProfileByUserImpl;
  @Autowired private WarehouseStockByProductImpl warehouseStockByProductImpl;
  @Autowired private WarehouseStockByWarehouseImpl warehouseStockByWarehouseImpl;
  @Autowired private WarehousesByStoreImpl warehousesByStoreImpl;
  @Autowired private DataChangeTracker dataChangeTracker;

  protected QueryResult executeOperation(
      String query, Field field, RocketInputContext ctx, boolean subscribed, ClientSession session)
      throws Exception {
    Log.displayGraphQL(query, query, null);
    BaseUser currentUser = CurrentUser.get();
    switch (query) {
      case "getAnonymousUserById":
        {
          OutObject one = gqlToSql.execute("AnonymousUser", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("AnonymousUser", false, one, tracker);
          }
          return singleResult("AnonymousUser", false, one);
        }
      case "getAuditLogById":
        {
          OutObject one = gqlToSql.execute("AuditLog", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("AuditLog", false, one, tracker);
          }
          return singleResult("AuditLog", false, one);
        }
      case "getGoodsReceiptById":
        {
          OutObject one = gqlToSql.execute("GoodsReceipt", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("GoodsReceipt", false, one, tracker);
          }
          return singleResult("GoodsReceipt", false, one);
        }
      case "getGoodsReceiptLineById":
        {
          OutObject one = gqlToSql.execute("GoodsReceiptLine", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("GoodsReceiptLine", false, one, tracker);
          }
          return singleResult("GoodsReceiptLine", false, one);
        }
      case "getInAppNotificationById":
        {
          OutObject one = gqlToSql.execute("InAppNotification", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("InAppNotification", false, one, tracker);
          }
          return singleResult("InAppNotification", false, one);
        }
      case "getInventoryAdjustmentById":
        {
          OutObject one = gqlToSql.execute("InventoryAdjustment", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("InventoryAdjustment", false, one, tracker);
          }
          return singleResult("InventoryAdjustment", false, one);
        }
      case "getInventoryAdjustmentLineById":
        {
          OutObject one = gqlToSql.execute("InventoryAdjustmentLine", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("InventoryAdjustmentLine", false, one, tracker);
          }
          return singleResult("InventoryAdjustmentLine", false, one);
        }
      case "getInventoryMovementById":
        {
          OutObject one = gqlToSql.execute("InventoryMovement", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("InventoryMovement", false, one, tracker);
          }
          return singleResult("InventoryMovement", false, one);
        }
      case "getNotificationTemplateById":
        {
          OutObject one = gqlToSql.execute("NotificationTemplate", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("NotificationTemplate", false, one, tracker);
          }
          return singleResult("NotificationTemplate", false, one);
        }
      case "getOneTimePasswordById":
        {
          OutObject one = gqlToSql.execute("OneTimePassword", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("OneTimePassword", false, one, tracker);
          }
          return singleResult("OneTimePassword", false, one);
        }
      case "getOrganizationById":
        {
          OutObject one = gqlToSql.execute("Organization", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("Organization", false, one, tracker);
          }
          return singleResult("Organization", false, one);
        }
      case "getProductById":
        {
          OutObject one = gqlToSql.execute("Product", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("Product", false, one, tracker);
          }
          return singleResult("Product", false, one);
        }
      case "getProductCategoryById":
        {
          OutObject one = gqlToSql.execute("ProductCategory", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("ProductCategory", false, one, tracker);
          }
          return singleResult("ProductCategory", false, one);
        }
      case "getPurchaseOrderById":
        {
          OutObject one = gqlToSql.execute("PurchaseOrder", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("PurchaseOrder", false, one, tracker);
          }
          return singleResult("PurchaseOrder", false, one);
        }
      case "getPurchaseOrderLineById":
        {
          OutObject one = gqlToSql.execute("PurchaseOrderLine", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("PurchaseOrderLine", false, one, tracker);
          }
          return singleResult("PurchaseOrderLine", false, one);
        }
      case "getPushNotificationById":
        {
          OutObject one = gqlToSql.execute("PushNotification", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("PushNotification", false, one, tracker);
          }
          return singleResult("PushNotification", false, one);
        }
      case "getReportById":
        {
          OutObject one = gqlToSql.execute("Report", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("Report", false, one, tracker);
          }
          return singleResult("Report", false, one);
        }
      case "getSalesOrderById":
        {
          OutObject one = gqlToSql.execute("SalesOrder", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("SalesOrder", false, one, tracker);
          }
          return singleResult("SalesOrder", false, one);
        }
      case "getSalesOrderLineById":
        {
          OutObject one = gqlToSql.execute("SalesOrderLine", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("SalesOrderLine", false, one, tracker);
          }
          return singleResult("SalesOrderLine", false, one);
        }
      case "getSalesReturnById":
        {
          OutObject one = gqlToSql.execute("SalesReturn", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("SalesReturn", false, one, tracker);
          }
          return singleResult("SalesReturn", false, one);
        }
      case "getSalesReturnLineById":
        {
          OutObject one = gqlToSql.execute("SalesReturnLine", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("SalesReturnLine", false, one, tracker);
          }
          return singleResult("SalesReturnLine", false, one);
        }
      case "getStockAlertById":
        {
          OutObject one = gqlToSql.execute("StockAlert", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("StockAlert", false, one, tracker);
          }
          return singleResult("StockAlert", false, one);
        }
      case "getStockBatchById":
        {
          OutObject one = gqlToSql.execute("StockBatch", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("StockBatch", false, one, tracker);
          }
          return singleResult("StockBatch", false, one);
        }
      case "getStockTransferById":
        {
          OutObject one = gqlToSql.execute("StockTransfer", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("StockTransfer", false, one, tracker);
          }
          return singleResult("StockTransfer", false, one);
        }
      case "getStockTransferLineById":
        {
          OutObject one = gqlToSql.execute("StockTransferLine", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("StockTransferLine", false, one, tracker);
          }
          return singleResult("StockTransferLine", false, one);
        }
      case "getStoreById":
        {
          OutObject one = gqlToSql.execute("Store", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("Store", false, one, tracker);
          }
          return singleResult("Store", false, one);
        }
      case "getSupplierContactById":
        {
          OutObject one = gqlToSql.execute("SupplierContact", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("SupplierContact", false, one, tracker);
          }
          return singleResult("SupplierContact", false, one);
        }
      case "getUnitOfMeasureById":
        {
          OutObject one = gqlToSql.execute("UnitOfMeasure", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("UnitOfMeasure", false, one, tracker);
          }
          return singleResult("UnitOfMeasure", false, one);
        }
      case "getUserById":
        {
          OutObject one = gqlToSql.execute("User", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("User", false, one, tracker);
          }
          return singleResult("User", false, one);
        }
      case "getUserDeviceById":
        {
          OutObject one = gqlToSql.execute("UserDevice", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("UserDevice", false, one, tracker);
          }
          return singleResult("UserDevice", false, one);
        }
      case "getUserInvitationById":
        {
          OutObject one = gqlToSql.execute("UserInvitation", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("UserInvitation", false, one, tracker);
          }
          return singleResult("UserInvitation", false, one);
        }
      case "getUserLoginRecordById":
        {
          OutObject one = gqlToSql.execute("UserLoginRecord", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("UserLoginRecord", false, one, tracker);
          }
          return singleResult("UserLoginRecord", false, one);
        }
      case "getUserProfileById":
        {
          OutObject one = gqlToSql.execute("UserProfile", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("UserProfile", false, one, tracker);
          }
          return singleResult("UserProfile", false, one);
        }
      case "getUserRoleById":
        {
          OutObject one = gqlToSql.execute("UserRole", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("UserRole", false, one, tracker);
          }
          return singleResult("UserRole", false, one);
        }
      case "getVendorById":
        {
          OutObject one = gqlToSql.execute("Vendor", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("Vendor", false, one, tracker);
          }
          return singleResult("Vendor", false, one);
        }
      case "getVerificationDataById":
        {
          OutObject one = gqlToSql.execute("VerificationData", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("VerificationData", false, one, tracker);
          }
          return singleResult("VerificationData", false, one);
        }
      case "getWarehouseById":
        {
          OutObject one = gqlToSql.execute("Warehouse", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("Warehouse", false, one, tracker);
          }
          return singleResult("Warehouse", false, one);
        }
      case "getWarehouseStockById":
        {
          OutObject one = gqlToSql.execute("WarehouseStock", field, ctx.readLong());
          if (subscribed && one != null) {
            OutObjectTracker tracker = new OutObjectTracker(dataChangeTracker, session, field);
            tracker.init(one);
            return singleResult("WarehouseStock", false, one, tracker);
          }
          return singleResult("WarehouseStock", false, one);
        }
      case "getAllAuditLogs":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllAuditLogsRequest req = ctx.readObject();
          List<NativeObj> rows = allAuditLogsImpl.getNativeResult(req);
          long count = allAuditLogsImpl.getCountResult(req);
          OutObject res = allAuditLogsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllAuditLogs resObj = allAuditLogsImpl.getAsStruct(rows, count);
            AllAuditLogsChangeTracker tracker =
                new AllAuditLogsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllAuditLogs", false, res, tracker);
          }
          return singleResult("AllAuditLogs", false, res);
        }
      case "getAllGoodsReceipts":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllGoodsReceiptsRequest req = ctx.readObject();
          List<NativeObj> rows = allGoodsReceiptsImpl.getNativeResult(req);
          long count = allGoodsReceiptsImpl.getCountResult(req);
          OutObject res = allGoodsReceiptsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllGoodsReceipts resObj = allGoodsReceiptsImpl.getAsStruct(rows, count);
            AllGoodsReceiptsChangeTracker tracker =
                new AllGoodsReceiptsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllGoodsReceipts", false, res, tracker);
          }
          return singleResult("AllGoodsReceipts", false, res);
        }
      case "getAllInAppNotifications":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllInAppNotificationsRequest req = ctx.readObject();
          List<NativeObj> rows = allInAppNotificationsImpl.getNativeResult(req);
          long count = allInAppNotificationsImpl.getCountResult(req);
          OutObject res =
              allInAppNotificationsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllInAppNotifications resObj = allInAppNotificationsImpl.getAsStruct(rows, count);
            AllInAppNotificationsChangeTracker tracker =
                new AllInAppNotificationsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllInAppNotifications", false, res, tracker);
          }
          return singleResult("AllInAppNotifications", false, res);
        }
      case "getAllInventoryAdjustments":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllInventoryAdjustmentsRequest req = ctx.readObject();
          List<NativeObj> rows = allInventoryAdjustmentsImpl.getNativeResult(req);
          long count = allInventoryAdjustmentsImpl.getCountResult(req);
          OutObject res =
              allInventoryAdjustmentsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllInventoryAdjustments resObj = allInventoryAdjustmentsImpl.getAsStruct(rows, count);
            AllInventoryAdjustmentsChangeTracker tracker =
                new AllInventoryAdjustmentsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllInventoryAdjustments", false, res, tracker);
          }
          return singleResult("AllInventoryAdjustments", false, res);
        }
      case "getAllInventoryMovements":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllInventoryMovementsRequest req = ctx.readObject();
          List<NativeObj> rows = allInventoryMovementsImpl.getNativeResult(req);
          long count = allInventoryMovementsImpl.getCountResult(req);
          OutObject res =
              allInventoryMovementsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllInventoryMovements resObj = allInventoryMovementsImpl.getAsStruct(rows, count);
            AllInventoryMovementsChangeTracker tracker =
                new AllInventoryMovementsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllInventoryMovements", false, res, tracker);
          }
          return singleResult("AllInventoryMovements", false, res);
        }
      case "getAllOrganizations":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          List<NativeObj> rows = allOrganizationsImpl.getNativeResult();
          long count = allOrganizationsImpl.getCountResult();
          OutObject res = allOrganizationsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllOrganizations resObj = allOrganizationsImpl.getAsStruct(rows, count);
            AllOrganizationsChangeTracker tracker =
                new AllOrganizationsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj);
            return singleResult("AllOrganizations", false, res, tracker);
          }
          return singleResult("AllOrganizations", false, res);
        }
      case "getAllProductCategories":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllProductCategoriesRequest req = ctx.readObject();
          List<NativeObj> rows = allProductCategoriesImpl.getNativeResult(req);
          long count = allProductCategoriesImpl.getCountResult(req);
          OutObject res = allProductCategoriesImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllProductCategories resObj = allProductCategoriesImpl.getAsStruct(rows, count);
            AllProductCategoriesChangeTracker tracker =
                new AllProductCategoriesChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllProductCategories", false, res, tracker);
          }
          return singleResult("AllProductCategories", false, res);
        }
      case "getAllProducts":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllProductsRequest req = ctx.readObject();
          List<NativeObj> rows = allProductsImpl.getNativeResult(req);
          long count = allProductsImpl.getCountResult(req);
          OutObject res = allProductsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllProducts resObj = allProductsImpl.getAsStruct(rows, count);
            AllProductsChangeTracker tracker =
                new AllProductsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllProducts", false, res, tracker);
          }
          return singleResult("AllProducts", false, res);
        }
      case "getAllPurchaseOrders":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllPurchaseOrdersRequest req = ctx.readObject();
          List<NativeObj> rows = allPurchaseOrdersImpl.getNativeResult(req);
          long count = allPurchaseOrdersImpl.getCountResult(req);
          OutObject res = allPurchaseOrdersImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllPurchaseOrders resObj = allPurchaseOrdersImpl.getAsStruct(rows, count);
            AllPurchaseOrdersChangeTracker tracker =
                new AllPurchaseOrdersChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllPurchaseOrders", false, res, tracker);
          }
          return singleResult("AllPurchaseOrders", false, res);
        }
      case "getAllSalesOrders":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllSalesOrdersRequest req = ctx.readObject();
          List<NativeObj> rows = allSalesOrdersImpl.getNativeResult(req);
          long count = allSalesOrdersImpl.getCountResult(req);
          OutObject res = allSalesOrdersImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllSalesOrders resObj = allSalesOrdersImpl.getAsStruct(rows, count);
            AllSalesOrdersChangeTracker tracker =
                new AllSalesOrdersChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllSalesOrders", false, res, tracker);
          }
          return singleResult("AllSalesOrders", false, res);
        }
      case "getAllSalesReturns":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllSalesReturnsRequest req = ctx.readObject();
          List<NativeObj> rows = allSalesReturnsImpl.getNativeResult(req);
          long count = allSalesReturnsImpl.getCountResult(req);
          OutObject res = allSalesReturnsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllSalesReturns resObj = allSalesReturnsImpl.getAsStruct(rows, count);
            AllSalesReturnsChangeTracker tracker =
                new AllSalesReturnsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllSalesReturns", false, res, tracker);
          }
          return singleResult("AllSalesReturns", false, res);
        }
      case "getAllStockAlerts":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllStockAlertsRequest req = ctx.readObject();
          List<NativeObj> rows = allStockAlertsImpl.getNativeResult(req);
          long count = allStockAlertsImpl.getCountResult(req);
          OutObject res = allStockAlertsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllStockAlerts resObj = allStockAlertsImpl.getAsStruct(rows, count);
            AllStockAlertsChangeTracker tracker =
                new AllStockAlertsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllStockAlerts", false, res, tracker);
          }
          return singleResult("AllStockAlerts", false, res);
        }
      case "getAllStockBatches":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllStockBatchesRequest req = ctx.readObject();
          List<NativeObj> rows = allStockBatchesImpl.getNativeResult(req);
          long count = allStockBatchesImpl.getCountResult(req);
          OutObject res = allStockBatchesImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllStockBatches resObj = allStockBatchesImpl.getAsStruct(rows, count);
            AllStockBatchesChangeTracker tracker =
                new AllStockBatchesChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllStockBatches", false, res, tracker);
          }
          return singleResult("AllStockBatches", false, res);
        }
      case "getAllStockTransfers":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllStockTransfersRequest req = ctx.readObject();
          List<NativeObj> rows = allStockTransfersImpl.getNativeResult(req);
          long count = allStockTransfersImpl.getCountResult(req);
          OutObject res = allStockTransfersImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllStockTransfers resObj = allStockTransfersImpl.getAsStruct(rows, count);
            AllStockTransfersChangeTracker tracker =
                new AllStockTransfersChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllStockTransfers", false, res, tracker);
          }
          return singleResult("AllStockTransfers", false, res);
        }
      case "getAllStores":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllStoresRequest req = ctx.readObject();
          List<NativeObj> rows = allStoresImpl.getNativeResult(req);
          long count = allStoresImpl.getCountResult(req);
          OutObject res = allStoresImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllStores resObj = allStoresImpl.getAsStruct(rows, count);
            AllStoresChangeTracker tracker =
                new AllStoresChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllStores", false, res, tracker);
          }
          return singleResult("AllStores", false, res);
        }
      case "getAllSuppliers":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllSuppliersRequest req = ctx.readObject();
          List<NativeObj> rows = allSuppliersImpl.getNativeResult(req);
          long count = allSuppliersImpl.getCountResult(req);
          OutObject res = allSuppliersImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllSuppliers resObj = allSuppliersImpl.getAsStruct(rows, count);
            AllSuppliersChangeTracker tracker =
                new AllSuppliersChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllSuppliers", false, res, tracker);
          }
          return singleResult("AllSuppliers", false, res);
        }
      case "getAllUnitOfMeasures":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllUnitOfMeasuresRequest req = ctx.readObject();
          List<NativeObj> rows = allUnitOfMeasuresImpl.getNativeResult(req);
          long count = allUnitOfMeasuresImpl.getCountResult(req);
          OutObject res = allUnitOfMeasuresImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllUnitOfMeasures resObj = allUnitOfMeasuresImpl.getAsStruct(rows, count);
            AllUnitOfMeasuresChangeTracker tracker =
                new AllUnitOfMeasuresChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllUnitOfMeasures", false, res, tracker);
          }
          return singleResult("AllUnitOfMeasures", false, res);
        }
      case "getAllUserInvitations":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllUserInvitationsRequest req = ctx.readObject();
          List<NativeObj> rows = allUserInvitationsImpl.getNativeResult(req);
          long count = allUserInvitationsImpl.getCountResult(req);
          OutObject res = allUserInvitationsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllUserInvitations resObj = allUserInvitationsImpl.getAsStruct(rows, count);
            AllUserInvitationsChangeTracker tracker =
                new AllUserInvitationsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllUserInvitations", false, res, tracker);
          }
          return singleResult("AllUserInvitations", false, res);
        }
      case "getAllUserProfiles":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllUserProfilesRequest req = ctx.readObject();
          List<NativeObj> rows = allUserProfilesImpl.getNativeResult(req);
          long count = allUserProfilesImpl.getCountResult(req);
          OutObject res = allUserProfilesImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllUserProfiles resObj = allUserProfilesImpl.getAsStruct(rows, count);
            AllUserProfilesChangeTracker tracker =
                new AllUserProfilesChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllUserProfiles", false, res, tracker);
          }
          return singleResult("AllUserProfiles", false, res);
        }
      case "getAllWarehouses":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllWarehousesRequest req = ctx.readObject();
          List<NativeObj> rows = allWarehousesImpl.getNativeResult(req);
          long count = allWarehousesImpl.getCountResult(req);
          OutObject res = allWarehousesImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            AllWarehouses resObj = allWarehousesImpl.getAsStruct(rows, count);
            AllWarehousesChangeTracker tracker =
                new AllWarehousesChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("AllWarehouses", false, res, tracker);
          }
          return singleResult("AllWarehouses", false, res);
        }
      case "getDashboardMetrics":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          DashboardMetricsRequest req = ctx.readObject();
          List<NativeObj> rows = dashboardMetricsImpl.getNativeResult(req);
          OutObject res = dashboardMetricsImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            DashboardMetrics resObj = dashboardMetricsImpl.getAsStruct(rows);
            DashboardMetricsChangeTracker tracker =
                new DashboardMetricsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("DashboardMetrics", false, res, tracker);
          }
          return singleResult("DashboardMetrics", false, res);
        }
      case "getExpiringBatches":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          ExpiringBatchesRequest req = ctx.readObject();
          List<NativeObj> rows = expiringBatchesImpl.getNativeResult(req);
          long count = expiringBatchesImpl.getCountResult(req);
          OutObject res = expiringBatchesImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            ExpiringBatches resObj = expiringBatchesImpl.getAsStruct(rows, count);
            ExpiringBatchesChangeTracker tracker =
                new ExpiringBatchesChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("ExpiringBatches", false, res, tracker);
          }
          return singleResult("ExpiringBatches", false, res);
        }
      case "getGoodsReceiptItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          GoodsReceiptItemRequest req = ctx.readObject();
          List<NativeObj> rows = goodsReceiptItemImpl.getNativeResult(req);
          OutObject res = goodsReceiptItemImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            GoodsReceiptItem resObj = goodsReceiptItemImpl.getAsStruct(rows);
            GoodsReceiptItemChangeTracker tracker =
                new GoodsReceiptItemChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("GoodsReceiptItem", false, res, tracker);
          }
          return singleResult("GoodsReceiptItem", false, res);
        }
      case "getInventoryMovementsByDateRange":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          InventoryMovementsByDateRangeRequest req = ctx.readObject();
          List<NativeObj> rows = inventoryMovementsByDateRangeImpl.getNativeResult(req);
          long count = inventoryMovementsByDateRangeImpl.getCountResult(req);
          OutObject res =
              inventoryMovementsByDateRangeImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            InventoryMovementsByDateRange resObj =
                inventoryMovementsByDateRangeImpl.getAsStruct(rows, count);
            InventoryMovementsByDateRangeChangeTracker tracker =
                new InventoryMovementsByDateRangeChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("InventoryMovementsByDateRange", false, res, tracker);
          }
          return singleResult("InventoryMovementsByDateRange", false, res);
        }
      case "getLowStockItems":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          LowStockItemsRequest req = ctx.readObject();
          List<NativeObj> rows = lowStockItemsImpl.getNativeResult(req);
          long count = lowStockItemsImpl.getCountResult(req);
          OutObject res = lowStockItemsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            LowStockItems resObj = lowStockItemsImpl.getAsStruct(rows, count);
            LowStockItemsChangeTracker tracker =
                new LowStockItemsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("LowStockItems", false, res, tracker);
          }
          return singleResult("LowStockItems", false, res);
        }
      case "getMovementReportRows":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          MovementReportRowsRequest req = ctx.readObject();
          List<NativeObj> rows = movementReportRowsImpl.getNativeResult(req);
          long count = movementReportRowsImpl.getCountResult(req);
          OutObject res = movementReportRowsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            MovementReportRows resObj = movementReportRowsImpl.getAsStruct(rows, count);
            MovementReportRowsChangeTracker tracker =
                new MovementReportRowsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("MovementReportRows", false, res, tracker);
          }
          return singleResult("MovementReportRows", false, res);
        }
      case "getOrganizationItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          OrganizationItemRequest req = ctx.readObject();
          List<NativeObj> rows = organizationItemImpl.getNativeResult(req);
          OutObject res = organizationItemImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            OrganizationItem resObj = organizationItemImpl.getAsStruct(rows);
            OrganizationItemChangeTracker tracker =
                new OrganizationItemChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("OrganizationItem", false, res, tracker);
          }
          return singleResult("OrganizationItem", false, res);
        }
      case "getOutOfStockItems":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          OutOfStockItemsRequest req = ctx.readObject();
          List<NativeObj> rows = outOfStockItemsImpl.getNativeResult(req);
          long count = outOfStockItemsImpl.getCountResult(req);
          OutObject res = outOfStockItemsImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            OutOfStockItems resObj = outOfStockItemsImpl.getAsStruct(rows, count);
            OutOfStockItemsChangeTracker tracker =
                new OutOfStockItemsChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("OutOfStockItems", false, res, tracker);
          }
          return singleResult("OutOfStockItems", false, res);
        }
      case "getProductItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          ProductItemRequest req = ctx.readObject();
          List<NativeObj> rows = productItemImpl.getNativeResult(req);
          OutObject res = productItemImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            ProductItem resObj = productItemImpl.getAsStruct(rows);
            ProductItemChangeTracker tracker =
                new ProductItemChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("ProductItem", false, res, tracker);
          }
          return singleResult("ProductItem", false, res);
        }
      case "getProductSearch":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          ProductSearchRequest req = ctx.readObject();
          List<NativeObj> rows = productSearchImpl.getNativeResult(req);
          long count = productSearchImpl.getCountResult(req);
          OutObject res = productSearchImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            ProductSearch resObj = productSearchImpl.getAsStruct(rows, count);
            ProductSearchChangeTracker tracker =
                new ProductSearchChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("ProductSearch", false, res, tracker);
          }
          return singleResult("ProductSearch", false, res);
        }
      case "getProductsByCategory":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          ProductsByCategoryRequest req = ctx.readObject();
          List<NativeObj> rows = productsByCategoryImpl.getNativeResult(req);
          long count = productsByCategoryImpl.getCountResult(req);
          OutObject res = productsByCategoryImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            ProductsByCategory resObj = productsByCategoryImpl.getAsStruct(rows, count);
            ProductsByCategoryChangeTracker tracker =
                new ProductsByCategoryChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("ProductsByCategory", false, res, tracker);
          }
          return singleResult("ProductsByCategory", false, res);
        }
      case "getPurchaseOrderItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          PurchaseOrderItemRequest req = ctx.readObject();
          List<NativeObj> rows = purchaseOrderItemImpl.getNativeResult(req);
          OutObject res = purchaseOrderItemImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            PurchaseOrderItem resObj = purchaseOrderItemImpl.getAsStruct(rows);
            PurchaseOrderItemChangeTracker tracker =
                new PurchaseOrderItemChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("PurchaseOrderItem", false, res, tracker);
          }
          return singleResult("PurchaseOrderItem", false, res);
        }
      case "getPurchaseOrdersByStatus":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          PurchaseOrdersByStatusRequest req = ctx.readObject();
          List<NativeObj> rows = purchaseOrdersByStatusImpl.getNativeResult(req);
          long count = purchaseOrdersByStatusImpl.getCountResult(req);
          OutObject res =
              purchaseOrdersByStatusImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            PurchaseOrdersByStatus resObj = purchaseOrdersByStatusImpl.getAsStruct(rows, count);
            PurchaseOrdersByStatusChangeTracker tracker =
                new PurchaseOrdersByStatusChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("PurchaseOrdersByStatus", false, res, tracker);
          }
          return singleResult("PurchaseOrdersByStatus", false, res);
        }
      case "getSalesOrderItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          SalesOrderItemRequest req = ctx.readObject();
          List<NativeObj> rows = salesOrderItemImpl.getNativeResult(req);
          OutObject res = salesOrderItemImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            SalesOrderItem resObj = salesOrderItemImpl.getAsStruct(rows);
            SalesOrderItemChangeTracker tracker =
                new SalesOrderItemChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("SalesOrderItem", false, res, tracker);
          }
          return singleResult("SalesOrderItem", false, res);
        }
      case "getSalesOrdersByStore":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          SalesOrdersByStoreRequest req = ctx.readObject();
          List<NativeObj> rows = salesOrdersByStoreImpl.getNativeResult(req);
          long count = salesOrdersByStoreImpl.getCountResult(req);
          OutObject res = salesOrdersByStoreImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            SalesOrdersByStore resObj = salesOrdersByStoreImpl.getAsStruct(rows, count);
            SalesOrdersByStoreChangeTracker tracker =
                new SalesOrdersByStoreChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("SalesOrdersByStore", false, res, tracker);
          }
          return singleResult("SalesOrdersByStore", false, res);
        }
      case "getStockTransferItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          StockTransferItemRequest req = ctx.readObject();
          List<NativeObj> rows = stockTransferItemImpl.getNativeResult(req);
          OutObject res = stockTransferItemImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            StockTransferItem resObj = stockTransferItemImpl.getAsStruct(rows);
            StockTransferItemChangeTracker tracker =
                new StockTransferItemChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("StockTransferItem", false, res, tracker);
          }
          return singleResult("StockTransferItem", false, res);
        }
      case "getStockValuationReport":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          StockValuationReportRequest req = ctx.readObject();
          List<NativeObj> rows = stockValuationReportImpl.getNativeResult(req);
          long count = stockValuationReportImpl.getCountResult(req);
          OutObject res = stockValuationReportImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            StockValuationReport resObj = stockValuationReportImpl.getAsStruct(rows, count);
            StockValuationReportChangeTracker tracker =
                new StockValuationReportChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("StockValuationReport", false, res, tracker);
          }
          return singleResult("StockValuationReport", false, res);
        }
      case "getStoreItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          StoreItemRequest req = ctx.readObject();
          List<NativeObj> rows = storeItemImpl.getNativeResult(req);
          OutObject res = storeItemImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            StoreItem resObj = storeItemImpl.getAsStruct(rows);
            StoreItemChangeTracker tracker =
                new StoreItemChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("StoreItem", false, res, tracker);
          }
          return singleResult("StoreItem", false, res);
        }
      case "getSupplierItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          SupplierItemRequest req = ctx.readObject();
          List<NativeObj> rows = supplierItemImpl.getNativeResult(req);
          OutObject res = supplierItemImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            SupplierItem resObj = supplierItemImpl.getAsStruct(rows);
            SupplierItemChangeTracker tracker =
                new SupplierItemChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("SupplierItem", false, res, tracker);
          }
          return singleResult("SupplierItem", false, res);
        }
      case "getUnreadNotificationCount":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          UnreadNotificationCountRequest req = ctx.readObject();
          List<NativeObj> rows = unreadNotificationCountImpl.getNativeResult(req);
          long count = unreadNotificationCountImpl.getCountResult(req);
          OutObject res =
              unreadNotificationCountImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            UnreadNotificationCount resObj = unreadNotificationCountImpl.getAsStruct(rows, count);
            UnreadNotificationCountChangeTracker tracker =
                new UnreadNotificationCountChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("UnreadNotificationCount", false, res, tracker);
          }
          return singleResult("UnreadNotificationCount", false, res);
        }
      case "getUserProfileByUser":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          UserProfileByUserRequest req = ctx.readObject();
          List<NativeObj> rows = userProfileByUserImpl.getNativeResult(req);
          OutObject res = userProfileByUserImpl.getAsJson(inspect2(field, "items"), rows);
          if (subscribed) {
            UserProfileByUser resObj = userProfileByUserImpl.getAsStruct(rows);
            UserProfileByUserChangeTracker tracker =
                new UserProfileByUserChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("UserProfileByUser", false, res, tracker);
          }
          return singleResult("UserProfileByUser", false, res);
        }
      case "getWarehouseStockByProduct":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          WarehouseStockByProductRequest req = ctx.readObject();
          List<NativeObj> rows = warehouseStockByProductImpl.getNativeResult(req);
          long count = warehouseStockByProductImpl.getCountResult(req);
          OutObject res =
              warehouseStockByProductImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            WarehouseStockByProduct resObj = warehouseStockByProductImpl.getAsStruct(rows, count);
            WarehouseStockByProductChangeTracker tracker =
                new WarehouseStockByProductChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("WarehouseStockByProduct", false, res, tracker);
          }
          return singleResult("WarehouseStockByProduct", false, res);
        }
      case "getWarehouseStockByWarehouse":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          WarehouseStockByWarehouseRequest req = ctx.readObject();
          List<NativeObj> rows = warehouseStockByWarehouseImpl.getNativeResult(req);
          long count = warehouseStockByWarehouseImpl.getCountResult(req);
          OutObject res =
              warehouseStockByWarehouseImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            WarehouseStockByWarehouse resObj =
                warehouseStockByWarehouseImpl.getAsStruct(rows, count);
            WarehouseStockByWarehouseChangeTracker tracker =
                new WarehouseStockByWarehouseChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("WarehouseStockByWarehouse", false, res, tracker);
          }
          return singleResult("WarehouseStockByWarehouse", false, res);
        }
      case "getWarehousesByStore":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          WarehousesByStoreRequest req = ctx.readObject();
          List<NativeObj> rows = warehousesByStoreImpl.getNativeResult(req);
          long count = warehousesByStoreImpl.getCountResult(req);
          OutObject res = warehousesByStoreImpl.getAsJson(inspect2(field, "items"), rows, count);
          if (subscribed) {
            WarehousesByStore resObj = warehousesByStoreImpl.getAsStruct(rows, count);
            WarehousesByStoreChangeTracker tracker =
                new WarehousesByStoreChangeTracker(dataChangeTracker, session, field);
            tracker.init(res, resObj, req);
            return singleResult("WarehousesByStore", false, res, tracker);
          }
          return singleResult("WarehousesByStore", false, res);
        }
      case "currentAnonymousUser":
        {
          return singleResult("AnonymousUser", false, provider.getObject().getAnonymousUser());
        }
      case "currentUser":
        {
          return singleResult("User", false, provider.getObject().getUser());
        }
    }
    Log.info("Query Not found");
    throw new ValidationFailedException(ListExt.asList("Invalid Query: " + query));
  }
}
