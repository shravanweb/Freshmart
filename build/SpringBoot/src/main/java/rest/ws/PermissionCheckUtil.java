package rest.ws;

import d3e.core.CurrentUser;
import d3e.core.SchemaConstants;
import models.AnonymousUser;
import models.User;

public class PermissionCheckUtil {
  public static boolean canCreate(int userTypeIdx, int objTypeIdx) {
    switch (userTypeIdx) {
      case SchemaConstants.AnonymousUser:
        {
          AnonymousUser user = ((AnonymousUser) CurrentUser.get());
          switch (objTypeIdx) {
            case SchemaConstants.AnonymousUser:
              {
                return true;
              }
          }
          break;
        }
      case SchemaConstants.User:
        {
          User user = ((User) CurrentUser.get());
          switch (objTypeIdx) {
            case SchemaConstants.Organization:
              {
                return true;
              }
            case SchemaConstants.Store:
              {
                return true;
              }
            case SchemaConstants.Warehouse:
              {
                return true;
              }
            case SchemaConstants.UnitOfMeasure:
              {
                return true;
              }
            case SchemaConstants.ProductCategory:
              {
                return true;
              }
            case SchemaConstants.Product:
              {
                return true;
              }
            case SchemaConstants.StockBatch:
              {
                return true;
              }
            case SchemaConstants.Vendor:
              {
                return true;
              }
            case SchemaConstants.SupplierContact:
              {
                return true;
              }
            case SchemaConstants.PurchaseOrder:
              {
                return true;
              }
            case SchemaConstants.PurchaseOrderLine:
              {
                return true;
              }
            case SchemaConstants.GoodsReceipt:
              {
                return true;
              }
            case SchemaConstants.GoodsReceiptLine:
              {
                return true;
              }
            case SchemaConstants.StockTransfer:
              {
                return true;
              }
            case SchemaConstants.StockTransferLine:
              {
                return true;
              }
            case SchemaConstants.InventoryAdjustment:
              {
                return true;
              }
            case SchemaConstants.InventoryAdjustmentLine:
              {
                return true;
              }
            case SchemaConstants.SalesOrder:
              {
                return true;
              }
            case SchemaConstants.SalesOrderLine:
              {
                return true;
              }
            case SchemaConstants.SalesReturn:
              {
                return true;
              }
            case SchemaConstants.SalesReturnLine:
              {
                return true;
              }
            case SchemaConstants.UserProfile:
              {
                return true;
              }
            case SchemaConstants.UserRole:
              {
                return true;
              }
            case SchemaConstants.UserInvitation:
              {
                return true;
              }
            case SchemaConstants.NotificationTemplate:
              {
                return true;
              }
          }
          break;
        }
    }
    return false;
  }

  public static boolean canUpdate(int userTypeIdx, int objTypeIdx) {
    switch (userTypeIdx) {
      case SchemaConstants.AnonymousUser:
        {
          AnonymousUser user = ((AnonymousUser) CurrentUser.get());
          switch (objTypeIdx) {
            case SchemaConstants.AnonymousUser:
              {
                return true;
              }
          }
          break;
        }
      case SchemaConstants.User:
        {
          User user = ((User) CurrentUser.get());
          switch (objTypeIdx) {
            case SchemaConstants.User:
              {
                return true;
              }
            case SchemaConstants.Organization:
              {
                return true;
              }
            case SchemaConstants.Store:
              {
                return true;
              }
            case SchemaConstants.Warehouse:
              {
                return true;
              }
            case SchemaConstants.UnitOfMeasure:
              {
                return true;
              }
            case SchemaConstants.ProductCategory:
              {
                return true;
              }
            case SchemaConstants.Product:
              {
                return true;
              }
            case SchemaConstants.WarehouseStock:
              {
                return true;
              }
            case SchemaConstants.StockBatch:
              {
                return true;
              }
            case SchemaConstants.Vendor:
              {
                return true;
              }
            case SchemaConstants.SupplierContact:
              {
                return true;
              }
            case SchemaConstants.PurchaseOrder:
              {
                return true;
              }
            case SchemaConstants.PurchaseOrderLine:
              {
                return true;
              }
            case SchemaConstants.GoodsReceipt:
              {
                return true;
              }
            case SchemaConstants.GoodsReceiptLine:
              {
                return true;
              }
            case SchemaConstants.StockTransfer:
              {
                return true;
              }
            case SchemaConstants.StockTransferLine:
              {
                return true;
              }
            case SchemaConstants.InventoryAdjustment:
              {
                return true;
              }
            case SchemaConstants.InventoryAdjustmentLine:
              {
                return true;
              }
            case SchemaConstants.SalesOrder:
              {
                return true;
              }
            case SchemaConstants.SalesOrderLine:
              {
                return true;
              }
            case SchemaConstants.SalesReturn:
              {
                return true;
              }
            case SchemaConstants.SalesReturnLine:
              {
                return true;
              }
            case SchemaConstants.StockAlert:
              {
                return true;
              }
            case SchemaConstants.UserProfile:
              {
                return true;
              }
            case SchemaConstants.UserRole:
              {
                return true;
              }
            case SchemaConstants.UserInvitation:
              {
                return true;
              }
            case SchemaConstants.InAppNotification:
              {
                return true;
              }
            case SchemaConstants.NotificationTemplate:
              {
                return true;
              }
          }
          break;
        }
    }
    return false;
  }

  public static boolean canDelete(int userTypeIdx, int objTypeIdx) {
    switch (userTypeIdx) {
      case SchemaConstants.AnonymousUser:
        {
          AnonymousUser user = ((AnonymousUser) CurrentUser.get());
          switch (objTypeIdx) {
            case SchemaConstants.AnonymousUser:
              {
                return true;
              }
          }
          break;
        }
      case SchemaConstants.User:
        {
          User user = ((User) CurrentUser.get());
          switch (objTypeIdx) {
            case SchemaConstants.Store:
              {
                return true;
              }
            case SchemaConstants.Warehouse:
              {
                return true;
              }
            case SchemaConstants.UnitOfMeasure:
              {
                return true;
              }
            case SchemaConstants.ProductCategory:
              {
                return true;
              }
            case SchemaConstants.Product:
              {
                return true;
              }
            case SchemaConstants.StockBatch:
              {
                return true;
              }
            case SchemaConstants.Vendor:
              {
                return true;
              }
            case SchemaConstants.SupplierContact:
              {
                return true;
              }
            case SchemaConstants.PurchaseOrder:
              {
                return true;
              }
            case SchemaConstants.PurchaseOrderLine:
              {
                return true;
              }
            case SchemaConstants.GoodsReceipt:
              {
                return true;
              }
            case SchemaConstants.GoodsReceiptLine:
              {
                return true;
              }
            case SchemaConstants.StockTransfer:
              {
                return true;
              }
            case SchemaConstants.StockTransferLine:
              {
                return true;
              }
            case SchemaConstants.InventoryAdjustment:
              {
                return true;
              }
            case SchemaConstants.InventoryAdjustmentLine:
              {
                return true;
              }
            case SchemaConstants.SalesOrder:
              {
                return true;
              }
            case SchemaConstants.SalesOrderLine:
              {
                return true;
              }
            case SchemaConstants.SalesReturn:
              {
                return true;
              }
            case SchemaConstants.SalesReturnLine:
              {
                return true;
              }
            case SchemaConstants.UserProfile:
              {
                return true;
              }
            case SchemaConstants.UserInvitation:
              {
                return true;
              }
            case SchemaConstants.NotificationTemplate:
              {
                return true;
              }
          }
          break;
        }
    }
    return false;
  }

  public static boolean canReadDataQuery(int userTypeIdx, int dqTypeIdx) {
    switch (userTypeIdx) {
      case SchemaConstants.User:
        {
          switch (dqTypeIdx) {
            case SchemaConstants.AllAuditLogs:
              {
                return true;
              }
            case SchemaConstants.AllGoodsReceipts:
              {
                return true;
              }
            case SchemaConstants.AllInAppNotifications:
              {
                return true;
              }
            case SchemaConstants.AllInventoryAdjustments:
              {
                return true;
              }
            case SchemaConstants.AllInventoryMovements:
              {
                return true;
              }
            case SchemaConstants.AllOrganizations:
              {
                return true;
              }
            case SchemaConstants.AllProductCategories:
              {
                return true;
              }
            case SchemaConstants.AllProducts:
              {
                return true;
              }
            case SchemaConstants.AllPurchaseOrders:
              {
                return true;
              }
            case SchemaConstants.AllSalesOrders:
              {
                return true;
              }
            case SchemaConstants.AllSalesReturns:
              {
                return true;
              }
            case SchemaConstants.AllStockAlerts:
              {
                return true;
              }
            case SchemaConstants.AllStockBatches:
              {
                return true;
              }
            case SchemaConstants.AllStockTransfers:
              {
                return true;
              }
            case SchemaConstants.AllStores:
              {
                return true;
              }
            case SchemaConstants.AllSuppliers:
              {
                return true;
              }
            case SchemaConstants.AllUnitOfMeasures:
              {
                return true;
              }
            case SchemaConstants.AllUserInvitations:
              {
                return true;
              }
            case SchemaConstants.AllUserProfiles:
              {
                return true;
              }
            case SchemaConstants.AllWarehouses:
              {
                return true;
              }
            case SchemaConstants.DashboardMetrics:
              {
                return true;
              }
            case SchemaConstants.ExpiringBatches:
              {
                return true;
              }
            case SchemaConstants.GoodsReceiptItem:
              {
                return true;
              }
            case SchemaConstants.InventoryMovementsByDateRange:
              {
                return true;
              }
            case SchemaConstants.LowStockItems:
              {
                return true;
              }
            case SchemaConstants.MovementReportRows:
              {
                return true;
              }
            case SchemaConstants.OrganizationItem:
              {
                return true;
              }
            case SchemaConstants.OutOfStockItems:
              {
                return true;
              }
            case SchemaConstants.ProductItem:
              {
                return true;
              }
            case SchemaConstants.ProductSearch:
              {
                return true;
              }
            case SchemaConstants.ProductsByCategory:
              {
                return true;
              }
            case SchemaConstants.PurchaseOrderItem:
              {
                return true;
              }
            case SchemaConstants.PurchaseOrdersByStatus:
              {
                return true;
              }
            case SchemaConstants.SalesOrderItem:
              {
                return true;
              }
            case SchemaConstants.SalesOrdersByStore:
              {
                return true;
              }
            case SchemaConstants.StockTransferItem:
              {
                return true;
              }
            case SchemaConstants.StockValuationReport:
              {
                return true;
              }
            case SchemaConstants.StoreItem:
              {
                return true;
              }
            case SchemaConstants.SupplierItem:
              {
                return true;
              }
            case SchemaConstants.UnreadNotificationCount:
              {
                return true;
              }
            case SchemaConstants.UserProfileByUser:
              {
                return true;
              }
            case SchemaConstants.WarehouseStockByProduct:
              {
                return true;
              }
            case SchemaConstants.WarehouseStockByWarehouse:
              {
                return true;
              }
            case SchemaConstants.WarehousesByStore:
              {
                return true;
              }
          }
        }
    }
    return false;
  }
}
