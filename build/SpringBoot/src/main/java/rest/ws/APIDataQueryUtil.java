package rest.ws;

import d3e.core.QueryProvider;
import d3e.core.SchemaConstants;
import models.AllAuditLogsRequest;
import models.AllDevicesRequest;
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
import models.UserDevicesRequest;
import models.UserProfileByUserRequest;
import models.VerificationDataByTokenRequest;
import models.WarehouseStockByProductRequest;
import models.WarehouseStockByWarehouseRequest;
import models.WarehousesByStoreRequest;
import store.DBObject;

public class APIDataQueryUtil {
  public static DBObject get(int type, Object input) {
    switch (type) {
      case SchemaConstants.AllAuditLogs:
        {
          return QueryProvider.get().getAllAuditLogs(((AllAuditLogsRequest) input));
        }
      case SchemaConstants.AllDevices:
        {
          return QueryProvider.get().getAllDevices(((AllDevicesRequest) input));
        }
      case SchemaConstants.AllGoodsReceipts:
        {
          return QueryProvider.get().getAllGoodsReceipts(((AllGoodsReceiptsRequest) input));
        }
      case SchemaConstants.AllInAppNotifications:
        {
          return QueryProvider.get()
              .getAllInAppNotifications(((AllInAppNotificationsRequest) input));
        }
      case SchemaConstants.AllInventoryAdjustments:
        {
          return QueryProvider.get()
              .getAllInventoryAdjustments(((AllInventoryAdjustmentsRequest) input));
        }
      case SchemaConstants.AllInventoryMovements:
        {
          return QueryProvider.get()
              .getAllInventoryMovements(((AllInventoryMovementsRequest) input));
        }
      case SchemaConstants.AllOrganizations:
        {
          return QueryProvider.get().getAllOrganizations();
        }
      case SchemaConstants.AllProductCategories:
        {
          return QueryProvider.get().getAllProductCategories(((AllProductCategoriesRequest) input));
        }
      case SchemaConstants.AllProducts:
        {
          return QueryProvider.get().getAllProducts(((AllProductsRequest) input));
        }
      case SchemaConstants.AllPurchaseOrders:
        {
          return QueryProvider.get().getAllPurchaseOrders(((AllPurchaseOrdersRequest) input));
        }
      case SchemaConstants.AllSalesOrders:
        {
          return QueryProvider.get().getAllSalesOrders(((AllSalesOrdersRequest) input));
        }
      case SchemaConstants.AllSalesReturns:
        {
          return QueryProvider.get().getAllSalesReturns(((AllSalesReturnsRequest) input));
        }
      case SchemaConstants.AllStockAlerts:
        {
          return QueryProvider.get().getAllStockAlerts(((AllStockAlertsRequest) input));
        }
      case SchemaConstants.AllStockBatches:
        {
          return QueryProvider.get().getAllStockBatches(((AllStockBatchesRequest) input));
        }
      case SchemaConstants.AllStockTransfers:
        {
          return QueryProvider.get().getAllStockTransfers(((AllStockTransfersRequest) input));
        }
      case SchemaConstants.AllStores:
        {
          return QueryProvider.get().getAllStores(((AllStoresRequest) input));
        }
      case SchemaConstants.AllSuppliers:
        {
          return QueryProvider.get().getAllSuppliers(((AllSuppliersRequest) input));
        }
      case SchemaConstants.AllUnitOfMeasures:
        {
          return QueryProvider.get().getAllUnitOfMeasures(((AllUnitOfMeasuresRequest) input));
        }
      case SchemaConstants.AllUserInvitations:
        {
          return QueryProvider.get().getAllUserInvitations(((AllUserInvitationsRequest) input));
        }
      case SchemaConstants.AllUserProfiles:
        {
          return QueryProvider.get().getAllUserProfiles(((AllUserProfilesRequest) input));
        }
      case SchemaConstants.AllWarehouses:
        {
          return QueryProvider.get().getAllWarehouses(((AllWarehousesRequest) input));
        }
      case SchemaConstants.DashboardMetrics:
        {
          return QueryProvider.get().getDashboardMetrics(((DashboardMetricsRequest) input));
        }
      case SchemaConstants.ExpiringBatches:
        {
          return QueryProvider.get().getExpiringBatches(((ExpiringBatchesRequest) input));
        }
      case SchemaConstants.GoodsReceiptItem:
        {
          return QueryProvider.get().getGoodsReceiptItem(((GoodsReceiptItemRequest) input));
        }
      case SchemaConstants.InventoryMovementsByDateRange:
        {
          return QueryProvider.get()
              .getInventoryMovementsByDateRange(((InventoryMovementsByDateRangeRequest) input));
        }
      case SchemaConstants.LowStockItems:
        {
          return QueryProvider.get().getLowStockItems(((LowStockItemsRequest) input));
        }
      case SchemaConstants.MovementReportRows:
        {
          return QueryProvider.get().getMovementReportRows(((MovementReportRowsRequest) input));
        }
      case SchemaConstants.OrganizationItem:
        {
          return QueryProvider.get().getOrganizationItem(((OrganizationItemRequest) input));
        }
      case SchemaConstants.OutOfStockItems:
        {
          return QueryProvider.get().getOutOfStockItems(((OutOfStockItemsRequest) input));
        }
      case SchemaConstants.ProductItem:
        {
          return QueryProvider.get().getProductItem(((ProductItemRequest) input));
        }
      case SchemaConstants.ProductSearch:
        {
          return QueryProvider.get().getProductSearch(((ProductSearchRequest) input));
        }
      case SchemaConstants.ProductsByCategory:
        {
          return QueryProvider.get().getProductsByCategory(((ProductsByCategoryRequest) input));
        }
      case SchemaConstants.PurchaseOrderItem:
        {
          return QueryProvider.get().getPurchaseOrderItem(((PurchaseOrderItemRequest) input));
        }
      case SchemaConstants.PurchaseOrdersByStatus:
        {
          return QueryProvider.get()
              .getPurchaseOrdersByStatus(((PurchaseOrdersByStatusRequest) input));
        }
      case SchemaConstants.SalesOrderItem:
        {
          return QueryProvider.get().getSalesOrderItem(((SalesOrderItemRequest) input));
        }
      case SchemaConstants.SalesOrdersByStore:
        {
          return QueryProvider.get().getSalesOrdersByStore(((SalesOrdersByStoreRequest) input));
        }
      case SchemaConstants.StockTransferItem:
        {
          return QueryProvider.get().getStockTransferItem(((StockTransferItemRequest) input));
        }
      case SchemaConstants.StockValuationReport:
        {
          return QueryProvider.get().getStockValuationReport(((StockValuationReportRequest) input));
        }
      case SchemaConstants.StoreItem:
        {
          return QueryProvider.get().getStoreItem(((StoreItemRequest) input));
        }
      case SchemaConstants.SupplierItem:
        {
          return QueryProvider.get().getSupplierItem(((SupplierItemRequest) input));
        }
      case SchemaConstants.UnreadNotificationCount:
        {
          return QueryProvider.get()
              .getUnreadNotificationCount(((UnreadNotificationCountRequest) input));
        }
      case SchemaConstants.UserDevices:
        {
          return QueryProvider.get().getUserDevices(((UserDevicesRequest) input));
        }
      case SchemaConstants.UserProfileByUser:
        {
          return QueryProvider.get().getUserProfileByUser(((UserProfileByUserRequest) input));
        }
      case SchemaConstants.VerificationDataByToken:
        {
          return QueryProvider.get()
              .getVerificationDataByToken(((VerificationDataByTokenRequest) input));
        }
      case SchemaConstants.WarehouseStockByProduct:
        {
          return QueryProvider.get()
              .getWarehouseStockByProduct(((WarehouseStockByProductRequest) input));
        }
      case SchemaConstants.WarehouseStockByWarehouse:
        {
          return QueryProvider.get()
              .getWarehouseStockByWarehouse(((WarehouseStockByWarehouseRequest) input));
        }
      case SchemaConstants.WarehousesByStore:
        {
          return QueryProvider.get().getWarehousesByStore(((WarehousesByStoreRequest) input));
        }
    }
    return null;
  }
}
