package rest;

import d3e.core.D3ESubscription;
import d3e.core.D3ESubscriptionEvent;
import d3e.core.ListExt;
import d3e.core.Log;
import gqltosql.GqlToSql;
import gqltosql.SqlRow;
import gqltosql.schema.GraphQLDataFetcher;
import gqltosql.schema.IModelSchema;
import graphql.language.Field;
import io.reactivex.rxjava3.core.Flowable;
import java.util.HashMap;
import java.util.List;
import lists.AllAuditLogsSubscriptionHelper;
import lists.AllGoodsReceiptsSubscriptionHelper;
import lists.AllInAppNotificationsSubscriptionHelper;
import lists.AllInventoryAdjustmentsSubscriptionHelper;
import lists.AllInventoryMovementsSubscriptionHelper;
import lists.AllOrganizationsSubscriptionHelper;
import lists.AllProductCategoriesSubscriptionHelper;
import lists.AllProductsSubscriptionHelper;
import lists.AllPurchaseOrdersSubscriptionHelper;
import lists.AllSalesOrdersSubscriptionHelper;
import lists.AllSalesReturnsSubscriptionHelper;
import lists.AllStockAlertsSubscriptionHelper;
import lists.AllStockBatchesSubscriptionHelper;
import lists.AllStockTransfersSubscriptionHelper;
import lists.AllStoresSubscriptionHelper;
import lists.AllSuppliersSubscriptionHelper;
import lists.AllUnitOfMeasuresSubscriptionHelper;
import lists.AllUserInvitationsSubscriptionHelper;
import lists.AllUserProfilesSubscriptionHelper;
import lists.AllWarehousesSubscriptionHelper;
import lists.DashboardMetricsSubscriptionHelper;
import lists.DataQueryChange;
import lists.ExpiringBatchesSubscriptionHelper;
import lists.GoodsReceiptItemSubscriptionHelper;
import lists.InventoryMovementsByDateRangeSubscriptionHelper;
import lists.LowStockItemsSubscriptionHelper;
import lists.MovementReportRowsSubscriptionHelper;
import lists.OrganizationItemSubscriptionHelper;
import lists.OutOfStockItemsSubscriptionHelper;
import lists.ProductItemSubscriptionHelper;
import lists.ProductSearchSubscriptionHelper;
import lists.ProductsByCategorySubscriptionHelper;
import lists.PurchaseOrderItemSubscriptionHelper;
import lists.PurchaseOrdersByStatusSubscriptionHelper;
import lists.SalesOrderItemSubscriptionHelper;
import lists.SalesOrdersByStoreSubscriptionHelper;
import lists.StockTransferItemSubscriptionHelper;
import lists.StockValuationReportSubscriptionHelper;
import lists.StoreItemSubscriptionHelper;
import lists.SupplierItemSubscriptionHelper;
import lists.UnreadNotificationCountSubscriptionHelper;
import lists.UserProfileByUserSubscriptionHelper;
import lists.WarehouseStockByProductSubscriptionHelper;
import lists.WarehouseStockByWarehouseSubscriptionHelper;
import lists.WarehousesByStoreSubscriptionHelper;
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
import models.UserProfileByUserRequest;
import models.WarehouseStockByProductRequest;
import models.WarehouseStockByWarehouseRequest;
import models.WarehousesByStoreRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import store.DatabaseObject;
import store.EntityHelperService;
import store.EntityMutator;

@Component
public class NativeSubscription extends AbstractQueryService {
  @Autowired private EntityMutator mutator;
  @Autowired private ObjectFactory<EntityHelperService> helperService;
  @Autowired private D3ESubscription subscription;
  @Autowired private IModelSchema schema;
  @Autowired private GqlToSql gqltosql;
  @Autowired private ObjectFactory<AllAuditLogsSubscriptionHelper> allAuditLogs;
  @Autowired private ObjectFactory<AllGoodsReceiptsSubscriptionHelper> allGoodsReceipts;
  @Autowired private ObjectFactory<AllInAppNotificationsSubscriptionHelper> allInAppNotifications;

  @Autowired
  private ObjectFactory<AllInventoryAdjustmentsSubscriptionHelper> allInventoryAdjustments;

  @Autowired private ObjectFactory<AllInventoryMovementsSubscriptionHelper> allInventoryMovements;
  @Autowired private ObjectFactory<AllOrganizationsSubscriptionHelper> allOrganizations;
  @Autowired private ObjectFactory<AllProductCategoriesSubscriptionHelper> allProductCategories;
  @Autowired private ObjectFactory<AllProductsSubscriptionHelper> allProducts;
  @Autowired private ObjectFactory<AllPurchaseOrdersSubscriptionHelper> allPurchaseOrders;
  @Autowired private ObjectFactory<AllSalesOrdersSubscriptionHelper> allSalesOrders;
  @Autowired private ObjectFactory<AllSalesReturnsSubscriptionHelper> allSalesReturns;
  @Autowired private ObjectFactory<AllStockAlertsSubscriptionHelper> allStockAlerts;
  @Autowired private ObjectFactory<AllStockBatchesSubscriptionHelper> allStockBatches;
  @Autowired private ObjectFactory<AllStockTransfersSubscriptionHelper> allStockTransfers;
  @Autowired private ObjectFactory<AllStoresSubscriptionHelper> allStores;
  @Autowired private ObjectFactory<AllSuppliersSubscriptionHelper> allSuppliers;
  @Autowired private ObjectFactory<AllUnitOfMeasuresSubscriptionHelper> allUnitOfMeasures;
  @Autowired private ObjectFactory<AllUserInvitationsSubscriptionHelper> allUserInvitations;
  @Autowired private ObjectFactory<AllUserProfilesSubscriptionHelper> allUserProfiles;
  @Autowired private ObjectFactory<AllWarehousesSubscriptionHelper> allWarehouses;
  @Autowired private ObjectFactory<DashboardMetricsSubscriptionHelper> dashboardMetrics;
  @Autowired private ObjectFactory<ExpiringBatchesSubscriptionHelper> expiringBatches;
  @Autowired private ObjectFactory<GoodsReceiptItemSubscriptionHelper> goodsReceiptItem;

  @Autowired
  private ObjectFactory<InventoryMovementsByDateRangeSubscriptionHelper>
      inventoryMovementsByDateRange;

  @Autowired private ObjectFactory<LowStockItemsSubscriptionHelper> lowStockItems;
  @Autowired private ObjectFactory<MovementReportRowsSubscriptionHelper> movementReportRows;
  @Autowired private ObjectFactory<OrganizationItemSubscriptionHelper> organizationItem;
  @Autowired private ObjectFactory<OutOfStockItemsSubscriptionHelper> outOfStockItems;
  @Autowired private ObjectFactory<ProductItemSubscriptionHelper> productItem;
  @Autowired private ObjectFactory<ProductSearchSubscriptionHelper> productSearch;
  @Autowired private ObjectFactory<ProductsByCategorySubscriptionHelper> productsByCategory;
  @Autowired private ObjectFactory<PurchaseOrderItemSubscriptionHelper> purchaseOrderItem;
  @Autowired private ObjectFactory<PurchaseOrdersByStatusSubscriptionHelper> purchaseOrdersByStatus;
  @Autowired private ObjectFactory<SalesOrderItemSubscriptionHelper> salesOrderItem;
  @Autowired private ObjectFactory<SalesOrdersByStoreSubscriptionHelper> salesOrdersByStore;
  @Autowired private ObjectFactory<StockTransferItemSubscriptionHelper> stockTransferItem;
  @Autowired private ObjectFactory<StockValuationReportSubscriptionHelper> stockValuationReport;
  @Autowired private ObjectFactory<StoreItemSubscriptionHelper> storeItem;
  @Autowired private ObjectFactory<SupplierItemSubscriptionHelper> supplierItem;

  @Autowired
  private ObjectFactory<UnreadNotificationCountSubscriptionHelper> unreadNotificationCount;

  @Autowired private ObjectFactory<UserProfileByUserSubscriptionHelper> userProfileByUser;

  @Autowired
  private ObjectFactory<WarehouseStockByProductSubscriptionHelper> warehouseStockByProduct;

  @Autowired
  private ObjectFactory<WarehouseStockByWarehouseSubscriptionHelper> warehouseStockByWarehouse;

  @Autowired private ObjectFactory<WarehousesByStoreSubscriptionHelper> warehousesByStore;

  public Flowable<JSONObject> subscribe(JSONObject req) throws Exception {
    List<Field> fields = parseFields(req);
    Field field = fields.get(0);
    JSONObject variables = req.getJSONObject("variables");
    return executeOperation(field, variables);
  }

  private JSONObject fromDataQueryDataChange(DataQueryChange<?> event, Field field) {
    JSONObject data = new JSONObject();
    JSONObject opData = new JSONObject();
    try {
      opData.put("changeType", event.changeType.name());
      opData.put("path", event.path);
      opData.put("data", event.data);
      opData.put("position", event.index);
      data.put(field.getName(), opData);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return data;
  }

  private <T> JSONObject fromD3ESubscriptionEventExternal(
      D3ESubscriptionEvent<T> event, Field field, String type) {
    JSONObject data = new JSONObject();
    JSONObject opData = new JSONObject();
    try {
      opData.put("changeType", event.changeType.name());
      opData.put(
          "model",
          new GraphQLDataFetcher(schema).fetch(inspect(field, "model"), type, event.model));
      data.put(field.getName(), opData);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return data;
  }

  private <T> JSONObject fromD3ESubscriptionEvent(
      D3ESubscriptionEvent<T> event, Field field, String type) {
    JSONObject data = new JSONObject();
    JSONObject opData = new JSONObject();
    try {
      opData.put("changeType", event.changeType.name());
      if (event.model instanceof DatabaseObject) {
        long id = ((DatabaseObject) event.model).getId();
        SqlRow row = new SqlRow();
        row.put("id", id);
        opData.put("model", row);
        gqltosql.execute(type, inspect(field, "model"), ListExt.asList(row));
      }
      data.put(field.getName(), opData);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return data;
  }

  protected Flowable<JSONObject> executeOperation(Field field, JSONObject variables)
      throws Exception {
    GraphQLInputContext ctx =
        new ArgumentInputContext(
            field.getArguments(),
            helperService.getObject(),
            new HashMap<>(),
            new HashMap<>(),
            variables,
            schema);
    Log.info("Subscription: " + field.getName());
    switch (field.getName()) {
      case "onAnonymousUserChangeEvent":
        {
          return subscription
              .onAnonymousUserChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "AnonymousUser"));
        }
      case "onAnonymousUserChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onAnonymousUserChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "AnonymousUser"));
        }
      case "onAuditLogChangeEvent":
        {
          return subscription
              .onAuditLogChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "AuditLog"));
        }
      case "onAuditLogChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onAuditLogChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "AuditLog"));
        }
      case "onBaseUserChangeEvent":
        {
          return subscription
              .onBaseUserChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "BaseUser"));
        }
      case "onBaseUserChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onBaseUserChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "BaseUser"));
        }
      case "onBaseUserDevicesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onUserDeviceChangeEvent()
              .filter((e) -> e.model.getUser() != null && e.model.getUser().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserDevice"));
        }
      case "onBaseUserSessionChangeEvent":
        {
          return subscription
              .onBaseUserSessionChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "BaseUserSession"));
        }
      case "onBaseUserSessionChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onBaseUserSessionChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "BaseUserSession"));
        }
      case "onGoodsReceiptChangeEvent":
        {
          return subscription
              .onGoodsReceiptChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "GoodsReceipt"));
        }
      case "onGoodsReceiptChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onGoodsReceiptChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "GoodsReceipt"));
        }
      case "onGoodsReceiptLinesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onGoodsReceiptLineChangeEvent()
              .filter(
                  (e) ->
                      e.model.getGoodsReceipt() != null && e.model.getGoodsReceipt().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "GoodsReceiptLine"));
        }
      case "onGoodsReceiptLineChangeEvent":
        {
          return subscription
              .onGoodsReceiptLineChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "GoodsReceiptLine"));
        }
      case "onGoodsReceiptLineChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onGoodsReceiptLineChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "GoodsReceiptLine"));
        }
      case "onInAppNotificationChangeEvent":
        {
          return subscription
              .onInAppNotificationChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InAppNotification"));
        }
      case "onInAppNotificationChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onInAppNotificationChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InAppNotification"));
        }
      case "onInventoryAdjustmentChangeEvent":
        {
          return subscription
              .onInventoryAdjustmentChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InventoryAdjustment"));
        }
      case "onInventoryAdjustmentChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onInventoryAdjustmentChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InventoryAdjustment"));
        }
      case "onInventoryAdjustmentLinesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onInventoryAdjustmentLineChangeEvent()
              .filter(
                  (e) ->
                      e.model.getInventoryAdjustment() != null
                          && e.model.getInventoryAdjustment().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InventoryAdjustmentLine"));
        }
      case "onInventoryAdjustmentLineChangeEvent":
        {
          return subscription
              .onInventoryAdjustmentLineChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InventoryAdjustmentLine"));
        }
      case "onInventoryAdjustmentLineChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onInventoryAdjustmentLineChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InventoryAdjustmentLine"));
        }
      case "onInventoryMovementChangeEvent":
        {
          return subscription
              .onInventoryMovementChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InventoryMovement"));
        }
      case "onInventoryMovementChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onInventoryMovementChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "InventoryMovement"));
        }
      case "onNotificationTemplateChangeEvent":
        {
          return subscription
              .onNotificationTemplateChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "NotificationTemplate"));
        }
      case "onNotificationTemplateChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onNotificationTemplateChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "NotificationTemplate"));
        }
      case "onOneTimePasswordChangeEvent":
        {
          return subscription
              .onOneTimePasswordChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "OneTimePassword"));
        }
      case "onOneTimePasswordChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onOneTimePasswordChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "OneTimePassword"));
        }
      case "onOrganizationChangeEvent":
        {
          return subscription
              .onOrganizationChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Organization"));
        }
      case "onOrganizationChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onOrganizationChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Organization"));
        }
      case "onProductChangeEvent":
        {
          return subscription
              .onProductChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Product"));
        }
      case "onProductChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onProductChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Product"));
        }
      case "onProductWarehouseStocksFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onWarehouseStockChangeEvent()
              .filter((e) -> e.model.getProduct() != null && e.model.getProduct().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "WarehouseStock"));
        }
      case "onProductBatchesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onStockBatchChangeEvent()
              .filter((e) -> e.model.getProduct() != null && e.model.getProduct().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockBatch"));
        }
      case "onProductCategoryChangeEvent":
        {
          return subscription
              .onProductCategoryChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "ProductCategory"));
        }
      case "onProductCategoryChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onProductCategoryChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "ProductCategory"));
        }
      case "onProductCategoryChildCategoriesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onProductCategoryChangeEvent()
              .filter(
                  (e) ->
                      e.model.getParentCategory() != null
                          && e.model.getParentCategory().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "ProductCategory"));
        }
      case "onProductCategoryProductsFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onProductChangeEvent()
              .filter((e) -> e.model.getCategory() != null && e.model.getCategory().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Product"));
        }
      case "onPurchaseOrderChangeEvent":
        {
          return subscription
              .onPurchaseOrderChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "PurchaseOrder"));
        }
      case "onPurchaseOrderChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onPurchaseOrderChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "PurchaseOrder"));
        }
      case "onPurchaseOrderLinesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onPurchaseOrderLineChangeEvent()
              .filter(
                  (e) ->
                      e.model.getPurchaseOrder() != null
                          && e.model.getPurchaseOrder().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "PurchaseOrderLine"));
        }
      case "onPurchaseOrderLineChangeEvent":
        {
          return subscription
              .onPurchaseOrderLineChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "PurchaseOrderLine"));
        }
      case "onPurchaseOrderLineChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onPurchaseOrderLineChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "PurchaseOrderLine"));
        }
      case "onPushNotificationChangeEvent":
        {
          return subscription
              .onPushNotificationChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "PushNotification"));
        }
      case "onPushNotificationChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onPushNotificationChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "PushNotification"));
        }
      case "onReportChangeEvent":
        {
          return subscription
              .onReportChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Report"));
        }
      case "onReportChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onReportChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Report"));
        }
      case "onSalesOrderChangeEvent":
        {
          return subscription
              .onSalesOrderChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesOrder"));
        }
      case "onSalesOrderChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onSalesOrderChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesOrder"));
        }
      case "onSalesOrderLinesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onSalesOrderLineChangeEvent()
              .filter(
                  (e) -> e.model.getSalesOrder() != null && e.model.getSalesOrder().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesOrderLine"));
        }
      case "onSalesOrderLineChangeEvent":
        {
          return subscription
              .onSalesOrderLineChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesOrderLine"));
        }
      case "onSalesOrderLineChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onSalesOrderLineChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesOrderLine"));
        }
      case "onSalesReturnChangeEvent":
        {
          return subscription
              .onSalesReturnChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesReturn"));
        }
      case "onSalesReturnChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onSalesReturnChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesReturn"));
        }
      case "onSalesReturnLinesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onSalesReturnLineChangeEvent()
              .filter(
                  (e) -> e.model.getSalesReturn() != null && e.model.getSalesReturn().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesReturnLine"));
        }
      case "onSalesReturnLineChangeEvent":
        {
          return subscription
              .onSalesReturnLineChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesReturnLine"));
        }
      case "onSalesReturnLineChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onSalesReturnLineChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SalesReturnLine"));
        }
      case "onStockAlertChangeEvent":
        {
          return subscription
              .onStockAlertChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockAlert"));
        }
      case "onStockAlertChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onStockAlertChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockAlert"));
        }
      case "onStockBatchChangeEvent":
        {
          return subscription
              .onStockBatchChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockBatch"));
        }
      case "onStockBatchChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onStockBatchChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockBatch"));
        }
      case "onStockTransferChangeEvent":
        {
          return subscription
              .onStockTransferChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockTransfer"));
        }
      case "onStockTransferChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onStockTransferChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockTransfer"));
        }
      case "onStockTransferLinesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onStockTransferLineChangeEvent()
              .filter(
                  (e) ->
                      e.model.getStockTransfer() != null
                          && e.model.getStockTransfer().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockTransferLine"));
        }
      case "onStockTransferLineChangeEvent":
        {
          return subscription
              .onStockTransferLineChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockTransferLine"));
        }
      case "onStockTransferLineChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onStockTransferLineChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "StockTransferLine"));
        }
      case "onStoreChangeEvent":
        {
          return subscription
              .onStoreChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Store"));
        }
      case "onStoreChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onStoreChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Store"));
        }
      case "onStoreWarehousesFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onWarehouseChangeEvent()
              .filter((e) -> e.model.getStore() != null && e.model.getStore().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Warehouse"));
        }
      case "onSupplierContactChangeEvent":
        {
          return subscription
              .onSupplierContactChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SupplierContact"));
        }
      case "onSupplierContactChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onSupplierContactChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SupplierContact"));
        }
      case "onUnitOfMeasureChangeEvent":
        {
          return subscription
              .onUnitOfMeasureChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UnitOfMeasure"));
        }
      case "onUnitOfMeasureChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onUnitOfMeasureChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UnitOfMeasure"));
        }
      case "onUserChangeEvent":
        {
          return subscription
              .onUserChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "User"));
        }
      case "onUserChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onUserChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "User"));
        }
      case "onUserDeviceChangeEvent":
        {
          return subscription
              .onUserDeviceChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserDevice"));
        }
      case "onUserDeviceChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onUserDeviceChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserDevice"));
        }
      case "onUserInvitationChangeEvent":
        {
          return subscription
              .onUserInvitationChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserInvitation"));
        }
      case "onUserInvitationChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onUserInvitationChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserInvitation"));
        }
      case "onUserLoginRecordChangeEvent":
        {
          return subscription
              .onUserLoginRecordChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserLoginRecord"));
        }
      case "onUserLoginRecordChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onUserLoginRecordChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserLoginRecord"));
        }
      case "onUserProfileChangeEvent":
        {
          return subscription
              .onUserProfileChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserProfile"));
        }
      case "onUserProfileChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onUserProfileChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserProfile"));
        }
      case "onUserRoleChangeEvent":
        {
          return subscription
              .onUserRoleChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserRole"));
        }
      case "onUserRoleChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onUserRoleChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "UserRole"));
        }
      case "onVendorChangeEvent":
        {
          return subscription
              .onVendorChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Vendor"));
        }
      case "onVendorChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onVendorChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Vendor"));
        }
      case "onVendorContactsFieldChange":
        {
          Long id = ctx.readLong("id");
          return subscription
              .onSupplierContactChangeEvent()
              .filter((e) -> e.model.getVendor() != null && e.model.getVendor().getId() == id)
              .map((e) -> fromD3ESubscriptionEvent(e, field, "SupplierContact"));
        }
      case "onVerificationDataChangeEvent":
        {
          return subscription
              .onVerificationDataChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "VerificationData"));
        }
      case "onVerificationDataChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onVerificationDataChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "VerificationData"));
        }
      case "onWarehouseChangeEvent":
        {
          return subscription
              .onWarehouseChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Warehouse"));
        }
      case "onWarehouseChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onWarehouseChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "Warehouse"));
        }
      case "onWarehouseStockChangeEvent":
        {
          return subscription
              .onWarehouseStockChangeEvent()
              .map((e) -> fromD3ESubscriptionEvent(e, field, "WarehouseStock"));
        }
      case "onWarehouseStockChangeEventById":
        {
          List<Long> ids = ctx.readLongColl("ids");
          return subscription
              .onWarehouseStockChangeEvent()
              .filter((e) -> ids.contains(e.model.getId()))
              .map((e) -> fromD3ESubscriptionEvent(e, field, "WarehouseStock"));
        }
      case "onAllAuditLogsChange":
        {
          AllAuditLogsRequest req = ctx.readChild("in", "AllAuditLogsRequest");
          return allAuditLogs
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllGoodsReceiptsChange":
        {
          AllGoodsReceiptsRequest req = ctx.readChild("in", "AllGoodsReceiptsRequest");
          return allGoodsReceipts
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllInAppNotificationsChange":
        {
          AllInAppNotificationsRequest req = ctx.readChild("in", "AllInAppNotificationsRequest");
          return allInAppNotifications
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllInventoryAdjustmentsChange":
        {
          AllInventoryAdjustmentsRequest req =
              ctx.readChild("in", "AllInventoryAdjustmentsRequest");
          return allInventoryAdjustments
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllInventoryMovementsChange":
        {
          AllInventoryMovementsRequest req = ctx.readChild("in", "AllInventoryMovementsRequest");
          return allInventoryMovements
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllOrganizationsChange":
        {
          return allOrganizations
              .getObject()
              .subscribe(inspect(field, "data.items"))
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllProductCategoriesChange":
        {
          AllProductCategoriesRequest req = ctx.readChild("in", "AllProductCategoriesRequest");
          return allProductCategories
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllProductsChange":
        {
          AllProductsRequest req = ctx.readChild("in", "AllProductsRequest");
          return allProducts
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllPurchaseOrdersChange":
        {
          AllPurchaseOrdersRequest req = ctx.readChild("in", "AllPurchaseOrdersRequest");
          return allPurchaseOrders
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllSalesOrdersChange":
        {
          AllSalesOrdersRequest req = ctx.readChild("in", "AllSalesOrdersRequest");
          return allSalesOrders
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllSalesReturnsChange":
        {
          AllSalesReturnsRequest req = ctx.readChild("in", "AllSalesReturnsRequest");
          return allSalesReturns
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllStockAlertsChange":
        {
          AllStockAlertsRequest req = ctx.readChild("in", "AllStockAlertsRequest");
          return allStockAlerts
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllStockBatchesChange":
        {
          AllStockBatchesRequest req = ctx.readChild("in", "AllStockBatchesRequest");
          return allStockBatches
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllStockTransfersChange":
        {
          AllStockTransfersRequest req = ctx.readChild("in", "AllStockTransfersRequest");
          return allStockTransfers
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllStoresChange":
        {
          AllStoresRequest req = ctx.readChild("in", "AllStoresRequest");
          return allStores
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllSuppliersChange":
        {
          AllSuppliersRequest req = ctx.readChild("in", "AllSuppliersRequest");
          return allSuppliers
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllUnitOfMeasuresChange":
        {
          AllUnitOfMeasuresRequest req = ctx.readChild("in", "AllUnitOfMeasuresRequest");
          return allUnitOfMeasures
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllUserInvitationsChange":
        {
          AllUserInvitationsRequest req = ctx.readChild("in", "AllUserInvitationsRequest");
          return allUserInvitations
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllUserProfilesChange":
        {
          AllUserProfilesRequest req = ctx.readChild("in", "AllUserProfilesRequest");
          return allUserProfiles
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onAllWarehousesChange":
        {
          AllWarehousesRequest req = ctx.readChild("in", "AllWarehousesRequest");
          return allWarehouses
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onDashboardMetricsChange":
        {
          DashboardMetricsRequest req = ctx.readChild("in", "DashboardMetricsRequest");
          return dashboardMetrics
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onExpiringBatchesChange":
        {
          ExpiringBatchesRequest req = ctx.readChild("in", "ExpiringBatchesRequest");
          return expiringBatches
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onGoodsReceiptItemChange":
        {
          GoodsReceiptItemRequest req = ctx.readChild("in", "GoodsReceiptItemRequest");
          return goodsReceiptItem
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onInventoryMovementsByDateRangeChange":
        {
          InventoryMovementsByDateRangeRequest req =
              ctx.readChild("in", "InventoryMovementsByDateRangeRequest");
          return inventoryMovementsByDateRange
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onLowStockItemsChange":
        {
          LowStockItemsRequest req = ctx.readChild("in", "LowStockItemsRequest");
          return lowStockItems
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onMovementReportRowsChange":
        {
          MovementReportRowsRequest req = ctx.readChild("in", "MovementReportRowsRequest");
          return movementReportRows
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onOrganizationItemChange":
        {
          OrganizationItemRequest req = ctx.readChild("in", "OrganizationItemRequest");
          return organizationItem
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onOutOfStockItemsChange":
        {
          OutOfStockItemsRequest req = ctx.readChild("in", "OutOfStockItemsRequest");
          return outOfStockItems
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onProductItemChange":
        {
          ProductItemRequest req = ctx.readChild("in", "ProductItemRequest");
          return productItem
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onProductSearchChange":
        {
          ProductSearchRequest req = ctx.readChild("in", "ProductSearchRequest");
          return productSearch
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onProductsByCategoryChange":
        {
          ProductsByCategoryRequest req = ctx.readChild("in", "ProductsByCategoryRequest");
          return productsByCategory
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onPurchaseOrderItemChange":
        {
          PurchaseOrderItemRequest req = ctx.readChild("in", "PurchaseOrderItemRequest");
          return purchaseOrderItem
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onPurchaseOrdersByStatusChange":
        {
          PurchaseOrdersByStatusRequest req = ctx.readChild("in", "PurchaseOrdersByStatusRequest");
          return purchaseOrdersByStatus
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onSalesOrderItemChange":
        {
          SalesOrderItemRequest req = ctx.readChild("in", "SalesOrderItemRequest");
          return salesOrderItem
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onSalesOrdersByStoreChange":
        {
          SalesOrdersByStoreRequest req = ctx.readChild("in", "SalesOrdersByStoreRequest");
          return salesOrdersByStore
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onStockTransferItemChange":
        {
          StockTransferItemRequest req = ctx.readChild("in", "StockTransferItemRequest");
          return stockTransferItem
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onStockValuationReportChange":
        {
          StockValuationReportRequest req = ctx.readChild("in", "StockValuationReportRequest");
          return stockValuationReport
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onStoreItemChange":
        {
          StoreItemRequest req = ctx.readChild("in", "StoreItemRequest");
          return storeItem
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onSupplierItemChange":
        {
          SupplierItemRequest req = ctx.readChild("in", "SupplierItemRequest");
          return supplierItem
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onUnreadNotificationCountChange":
        {
          UnreadNotificationCountRequest req =
              ctx.readChild("in", "UnreadNotificationCountRequest");
          return unreadNotificationCount
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onUserProfileByUserChange":
        {
          UserProfileByUserRequest req = ctx.readChild("in", "UserProfileByUserRequest");
          return userProfileByUser
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onWarehouseStockByProductChange":
        {
          WarehouseStockByProductRequest req =
              ctx.readChild("in", "WarehouseStockByProductRequest");
          return warehouseStockByProduct
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onWarehouseStockByWarehouseChange":
        {
          WarehouseStockByWarehouseRequest req =
              ctx.readChild("in", "WarehouseStockByWarehouseRequest");
          return warehouseStockByWarehouse
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
      case "onWarehousesByStoreChange":
        {
          WarehousesByStoreRequest req = ctx.readChild("in", "WarehousesByStoreRequest");
          return warehousesByStore
              .getObject()
              .subscribe(inspect(field, "data.items"), req)
              .map((e) -> fromDataQueryDataChange(e, field));
        }
    }
    Log.info("Subscription Not found");
    return null;
  }
}
