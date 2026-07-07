package rest.ws;

import classes.AuditLogService;
import classes.FileService;
import classes.GoodsReceiptService;
import classes.InventoryAdjustmentService;
import classes.InventoryMovementService;
import classes.NotificationService;
import classes.OrganizationService;
import classes.ProductCategoryService;
import classes.ProductService;
import classes.PurchaseOrderService;
import classes.SalesOrderService;
import classes.SalesReturnService;
import classes.StockAlertService;
import classes.StockBatchService;
import classes.StockTransferService;
import classes.StoreService;
import classes.SupplierService;
import classes.UnitOfMeasureService;
import classes.UserManagementService;
import classes.WarehouseService;
import classes.WarehouseStockService;
import d3e.core.DFile;
import d3e.core.RPCConstants;
import d3e.core.UniqueChecker;
import models.AuditLog;
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
import models.UserInvitation;
import models.UserProfile;
import models.UserRole;
import models.Vendor;
import models.Warehouse;
import models.WarehouseStock;
import org.springframework.stereotype.Service;

@Service
public class RPCHandler {
  public void handle(int clsIdx, int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (clsIdx) {
      case RPCConstants.InventoryAdjustmentService:
        {
          handleInventoryAdjustmentService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.InventoryMovementService:
        {
          handleInventoryMovementService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.NotificationService:
        {
          handleNotificationService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.OrganizationService:
        {
          handleOrganizationService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.StockBatchService:
        {
          handleStockBatchService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.ProductCategoryService:
        {
          handleProductCategoryService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.ProductService:
        {
          handleProductService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.AuditLogService:
        {
          handleAuditLogService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.WarehouseService:
        {
          handleWarehouseService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.WarehouseStockService:
        {
          handleWarehouseStockService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.SalesOrderService:
        {
          handleSalesOrderService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.UnitOfMeasureService:
        {
          handleUnitOfMeasureService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.UserManagementService:
        {
          handleUserManagementService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.StockTransferService:
        {
          handleStockTransferService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.StoreService:
        {
          handleStoreService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.SalesReturnService:
        {
          handleSalesReturnService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.PurchaseOrderService:
        {
          handlePurchaseOrderService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.StockAlertService:
        {
          handleStockAlertService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.GoodsReceiptService:
        {
          handleGoodsReceiptService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.SupplierService:
        {
          handleSupplierService(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.UniqueChecker:
        {
          handleUniqueChecker(methodIdx, ctx, msg);
          break;
        }
      case RPCConstants.FileService:
        {
          handleFileService(methodIdx, ctx, msg);
          break;
        }
    }
  }

  private void handleInventoryAdjustmentService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.InventoryAdjustmentServiceCreateInventoryAdjustment:
        {
          InventoryAdjustment inventoryAdjustment = ctx.readObject();
          InventoryAdjustment result =
              InventoryAdjustmentService.createInventoryAdjustment(inventoryAdjustment);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.InventoryAdjustmentServiceUpdateInventoryAdjustment:
        {
          InventoryAdjustment inventoryAdjustment = ctx.readObject();
          InventoryAdjustment result =
              InventoryAdjustmentService.updateInventoryAdjustment(inventoryAdjustment);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.InventoryAdjustmentServiceDeleteInventoryAdjustment:
        {
          InventoryAdjustment inventoryAdjustment = ctx.readObject();
          InventoryAdjustmentService.deleteInventoryAdjustment(inventoryAdjustment);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.InventoryAdjustmentServiceConfirmInventoryAdjustment:
        {
          InventoryAdjustment inventoryAdjustment = ctx.readObject();
          InventoryAdjustment result =
              InventoryAdjustmentService.confirmInventoryAdjustment(inventoryAdjustment);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.InventoryAdjustmentServiceCreateInventoryAdjustmentLine:
        {
          InventoryAdjustmentLine inventoryAdjustmentLine = ctx.readObject();
          InventoryAdjustmentLine result =
              InventoryAdjustmentService.createInventoryAdjustmentLine(inventoryAdjustmentLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.InventoryAdjustmentServiceUpdateInventoryAdjustmentLine:
        {
          InventoryAdjustmentLine inventoryAdjustmentLine = ctx.readObject();
          InventoryAdjustmentLine result =
              InventoryAdjustmentService.updateInventoryAdjustmentLine(inventoryAdjustmentLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.InventoryAdjustmentServiceDeleteInventoryAdjustmentLine:
        {
          InventoryAdjustmentLine inventoryAdjustmentLine = ctx.readObject();
          InventoryAdjustmentService.deleteInventoryAdjustmentLine(inventoryAdjustmentLine);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleInventoryMovementService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.InventoryMovementServiceCreateInventoryMovement:
        {
          InventoryMovement inventoryMovement = ctx.readObject();
          InventoryMovement result =
              InventoryMovementService.createInventoryMovement(inventoryMovement);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.InventoryMovementServiceUpdateInventoryMovement:
        {
          InventoryMovement inventoryMovement = ctx.readObject();
          InventoryMovement result =
              InventoryMovementService.updateInventoryMovement(inventoryMovement);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.InventoryMovementServiceDeleteInventoryMovement:
        {
          InventoryMovement inventoryMovement = ctx.readObject();
          InventoryMovementService.deleteInventoryMovement(inventoryMovement);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleNotificationService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.NotificationServiceCreateInAppNotification:
        {
          InAppNotification inAppNotification = ctx.readObject();
          InAppNotification result = NotificationService.createInAppNotification(inAppNotification);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.NotificationServiceUpdateInAppNotification:
        {
          InAppNotification inAppNotification = ctx.readObject();
          InAppNotification result = NotificationService.updateInAppNotification(inAppNotification);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.NotificationServiceDeleteInAppNotification:
        {
          InAppNotification inAppNotification = ctx.readObject();
          NotificationService.deleteInAppNotification(inAppNotification);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.NotificationServiceCreateNotificationTemplate:
        {
          NotificationTemplate notificationTemplate = ctx.readObject();
          NotificationTemplate result =
              NotificationService.createNotificationTemplate(notificationTemplate);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.NotificationServiceUpdateNotificationTemplate:
        {
          NotificationTemplate notificationTemplate = ctx.readObject();
          NotificationTemplate result =
              NotificationService.updateNotificationTemplate(notificationTemplate);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.NotificationServiceDeleteNotificationTemplate:
        {
          NotificationTemplate notificationTemplate = ctx.readObject();
          NotificationService.deleteNotificationTemplate(notificationTemplate);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleOrganizationService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.OrganizationServiceCreateOrganization:
        {
          Organization organization = ctx.readObject();
          Organization result = OrganizationService.createOrganization(organization);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.OrganizationServiceUpdateOrganization:
        {
          Organization organization = ctx.readObject();
          Organization result = OrganizationService.updateOrganization(organization);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.OrganizationServiceDeleteOrganization:
        {
          Organization organization = ctx.readObject();
          OrganizationService.deleteOrganization(organization);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleStockBatchService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.StockBatchServiceCreateStockBatch:
        {
          StockBatch stockBatch = ctx.readObject();
          StockBatch result = StockBatchService.createStockBatch(stockBatch);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockBatchServiceUpdateStockBatch:
        {
          StockBatch stockBatch = ctx.readObject();
          StockBatch result = StockBatchService.updateStockBatch(stockBatch);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockBatchServiceDeleteStockBatch:
        {
          StockBatch stockBatch = ctx.readObject();
          StockBatchService.deleteStockBatch(stockBatch);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleProductCategoryService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.ProductCategoryServiceCreateProductCategory:
        {
          ProductCategory category = ctx.readObject();
          ProductCategory result = ProductCategoryService.createProductCategory(category);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.ProductCategoryServiceUpdateProductCategory:
        {
          ProductCategory category = ctx.readObject();
          ProductCategory result = ProductCategoryService.updateProductCategory(category);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.ProductCategoryServiceDeleteProductCategory:
        {
          ProductCategory category = ctx.readObject();
          ProductCategoryService.deleteProductCategory(category);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleProductService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.ProductServiceCreateProduct:
        {
          Product product = ctx.readObject();
          Product result = ProductService.createProduct(product);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.ProductServiceUpdateProduct:
        {
          Product product = ctx.readObject();
          Product result = ProductService.updateProduct(product);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.ProductServiceDeleteProduct:
        {
          Product product = ctx.readObject();
          ProductService.deleteProduct(product);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleAuditLogService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.AuditLogServiceCreateAuditLog:
        {
          AuditLog auditLog = ctx.readObject();
          AuditLog result = AuditLogService.createAuditLog(auditLog);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.AuditLogServiceUpdateAuditLog:
        {
          AuditLog auditLog = ctx.readObject();
          AuditLog result = AuditLogService.updateAuditLog(auditLog);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.AuditLogServiceDeleteAuditLog:
        {
          AuditLog auditLog = ctx.readObject();
          AuditLogService.deleteAuditLog(auditLog);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleWarehouseService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.WarehouseServiceCreateWarehouse:
        {
          Warehouse warehouse = ctx.readObject();
          Warehouse result = WarehouseService.createWarehouse(warehouse);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.WarehouseServiceUpdateWarehouse:
        {
          Warehouse warehouse = ctx.readObject();
          Warehouse result = WarehouseService.updateWarehouse(warehouse);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.WarehouseServiceDeleteWarehouse:
        {
          Warehouse warehouse = ctx.readObject();
          WarehouseService.deleteWarehouse(warehouse);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleWarehouseStockService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.WarehouseStockServiceCreateWarehouseStock:
        {
          WarehouseStock warehouseStock = ctx.readObject();
          WarehouseStock result = WarehouseStockService.createWarehouseStock(warehouseStock);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.WarehouseStockServiceUpdateWarehouseStock:
        {
          WarehouseStock warehouseStock = ctx.readObject();
          WarehouseStock result = WarehouseStockService.updateWarehouseStock(warehouseStock);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.WarehouseStockServiceDeleteWarehouseStock:
        {
          WarehouseStock warehouseStock = ctx.readObject();
          WarehouseStockService.deleteWarehouseStock(warehouseStock);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleSalesOrderService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.SalesOrderServiceCreateSalesOrder:
        {
          SalesOrder salesOrder = ctx.readObject();
          SalesOrder result = SalesOrderService.createSalesOrder(salesOrder);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesOrderServiceUpdateSalesOrder:
        {
          SalesOrder salesOrder = ctx.readObject();
          SalesOrder result = SalesOrderService.updateSalesOrder(salesOrder);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesOrderServiceDeleteSalesOrder:
        {
          SalesOrder salesOrder = ctx.readObject();
          SalesOrderService.deleteSalesOrder(salesOrder);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.SalesOrderServiceConfirmSalesOrder:
        {
          SalesOrder salesOrder = ctx.readObject();
          SalesOrder result = SalesOrderService.confirmSalesOrder(salesOrder);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesOrderServiceCancelSalesOrder:
        {
          SalesOrder salesOrder = ctx.readObject();
          SalesOrder result = SalesOrderService.cancelSalesOrder(salesOrder);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesOrderServiceCreateSalesOrderLine:
        {
          SalesOrderLine salesOrderLine = ctx.readObject();
          SalesOrderLine result = SalesOrderService.createSalesOrderLine(salesOrderLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesOrderServiceUpdateSalesOrderLine:
        {
          SalesOrderLine salesOrderLine = ctx.readObject();
          SalesOrderLine result = SalesOrderService.updateSalesOrderLine(salesOrderLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesOrderServiceDeleteSalesOrderLine:
        {
          SalesOrderLine salesOrderLine = ctx.readObject();
          SalesOrderService.deleteSalesOrderLine(salesOrderLine);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleUnitOfMeasureService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.UnitOfMeasureServiceCreateUnitOfMeasure:
        {
          UnitOfMeasure unitOfMeasure = ctx.readObject();
          UnitOfMeasure result = UnitOfMeasureService.createUnitOfMeasure(unitOfMeasure);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UnitOfMeasureServiceUpdateUnitOfMeasure:
        {
          UnitOfMeasure unitOfMeasure = ctx.readObject();
          UnitOfMeasure result = UnitOfMeasureService.updateUnitOfMeasure(unitOfMeasure);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UnitOfMeasureServiceDeleteUnitOfMeasure:
        {
          UnitOfMeasure unitOfMeasure = ctx.readObject();
          UnitOfMeasureService.deleteUnitOfMeasure(unitOfMeasure);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleUserManagementService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.UserManagementServiceCreateUser:
        {
          User user = ctx.readObject();
          User result = UserManagementService.createUser(user);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UserManagementServiceUpdateUser:
        {
          User user = ctx.readObject();
          User result = UserManagementService.updateUser(user);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UserManagementServiceDeleteUser:
        {
          User user = ctx.readObject();
          UserManagementService.deleteUser(user);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.UserManagementServiceCreateUserProfile:
        {
          UserProfile userProfile = ctx.readObject();
          UserProfile result = UserManagementService.createUserProfile(userProfile);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UserManagementServiceUpdateUserProfile:
        {
          UserProfile userProfile = ctx.readObject();
          UserProfile result = UserManagementService.updateUserProfile(userProfile);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UserManagementServiceDeleteUserProfile:
        {
          UserProfile userProfile = ctx.readObject();
          UserManagementService.deleteUserProfile(userProfile);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.UserManagementServiceCreateUserInvitation:
        {
          UserInvitation userInvitation = ctx.readObject();
          UserInvitation result = UserManagementService.createUserInvitation(userInvitation);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UserManagementServiceUpdateUserInvitation:
        {
          UserInvitation userInvitation = ctx.readObject();
          UserInvitation result = UserManagementService.updateUserInvitation(userInvitation);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UserManagementServiceDeleteUserInvitation:
        {
          UserInvitation userInvitation = ctx.readObject();
          UserManagementService.deleteUserInvitation(userInvitation);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.UserManagementServiceCreateUserRole:
        {
          UserRole userRole = ctx.readObject();
          UserRole result = UserManagementService.createUserRole(userRole);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UserManagementServiceUpdateUserRole:
        {
          UserRole userRole = ctx.readObject();
          UserRole result = UserManagementService.updateUserRole(userRole);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.UserManagementServiceDeleteUserRole:
        {
          UserRole userRole = ctx.readObject();
          UserManagementService.deleteUserRole(userRole);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleStockTransferService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.StockTransferServiceCreateStockTransfer:
        {
          StockTransfer stockTransfer = ctx.readObject();
          StockTransfer result = StockTransferService.createStockTransfer(stockTransfer);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockTransferServiceUpdateStockTransfer:
        {
          StockTransfer stockTransfer = ctx.readObject();
          StockTransfer result = StockTransferService.updateStockTransfer(stockTransfer);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockTransferServiceDeleteStockTransfer:
        {
          StockTransfer stockTransfer = ctx.readObject();
          StockTransferService.deleteStockTransfer(stockTransfer);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.StockTransferServiceSubmitStockTransfer:
        {
          StockTransfer stockTransfer = ctx.readObject();
          StockTransfer result = StockTransferService.submitStockTransfer(stockTransfer);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockTransferServiceCompleteStockTransfer:
        {
          StockTransfer stockTransfer = ctx.readObject();
          StockTransfer result = StockTransferService.completeStockTransfer(stockTransfer);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockTransferServiceCancelStockTransfer:
        {
          StockTransfer stockTransfer = ctx.readObject();
          StockTransfer result = StockTransferService.cancelStockTransfer(stockTransfer);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockTransferServiceCreateStockTransferLine:
        {
          StockTransferLine stockTransferLine = ctx.readObject();
          StockTransferLine result =
              StockTransferService.createStockTransferLine(stockTransferLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockTransferServiceUpdateStockTransferLine:
        {
          StockTransferLine stockTransferLine = ctx.readObject();
          StockTransferLine result =
              StockTransferService.updateStockTransferLine(stockTransferLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockTransferServiceDeleteStockTransferLine:
        {
          StockTransferLine stockTransferLine = ctx.readObject();
          StockTransferService.deleteStockTransferLine(stockTransferLine);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleStoreService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.StoreServiceCreateStore:
        {
          Store store = ctx.readObject();
          Store result = StoreService.createStore(store);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StoreServiceUpdateStore:
        {
          Store store = ctx.readObject();
          Store result = StoreService.updateStore(store);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StoreServiceDeleteStore:
        {
          Store store = ctx.readObject();
          StoreService.deleteStore(store);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleSalesReturnService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.SalesReturnServiceCreateSalesReturn:
        {
          SalesReturn salesReturn = ctx.readObject();
          SalesReturn result = SalesReturnService.createSalesReturn(salesReturn);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesReturnServiceUpdateSalesReturn:
        {
          SalesReturn salesReturn = ctx.readObject();
          SalesReturn result = SalesReturnService.updateSalesReturn(salesReturn);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesReturnServiceDeleteSalesReturn:
        {
          SalesReturn salesReturn = ctx.readObject();
          SalesReturnService.deleteSalesReturn(salesReturn);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.SalesReturnServiceConfirmSalesReturn:
        {
          SalesReturn salesReturn = ctx.readObject();
          SalesReturn result = SalesReturnService.confirmSalesReturn(salesReturn);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesReturnServiceCreateSalesReturnLine:
        {
          SalesReturnLine salesReturnLine = ctx.readObject();
          SalesReturnLine result = SalesReturnService.createSalesReturnLine(salesReturnLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesReturnServiceUpdateSalesReturnLine:
        {
          SalesReturnLine salesReturnLine = ctx.readObject();
          SalesReturnLine result = SalesReturnService.updateSalesReturnLine(salesReturnLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SalesReturnServiceDeleteSalesReturnLine:
        {
          SalesReturnLine salesReturnLine = ctx.readObject();
          SalesReturnService.deleteSalesReturnLine(salesReturnLine);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handlePurchaseOrderService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.PurchaseOrderServiceCreatePurchaseOrder:
        {
          PurchaseOrder purchaseOrder = ctx.readObject();
          PurchaseOrder result = PurchaseOrderService.createPurchaseOrder(purchaseOrder);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.PurchaseOrderServiceUpdatePurchaseOrder:
        {
          PurchaseOrder purchaseOrder = ctx.readObject();
          PurchaseOrder result = PurchaseOrderService.updatePurchaseOrder(purchaseOrder);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.PurchaseOrderServiceDeletePurchaseOrder:
        {
          PurchaseOrder purchaseOrder = ctx.readObject();
          PurchaseOrderService.deletePurchaseOrder(purchaseOrder);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.PurchaseOrderServiceSubmitPurchaseOrder:
        {
          PurchaseOrder purchaseOrder = ctx.readObject();
          PurchaseOrder result = PurchaseOrderService.submitPurchaseOrder(purchaseOrder);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.PurchaseOrderServiceCancelPurchaseOrder:
        {
          PurchaseOrder purchaseOrder = ctx.readObject();
          PurchaseOrder result = PurchaseOrderService.cancelPurchaseOrder(purchaseOrder);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.PurchaseOrderServiceCreatePurchaseOrderLine:
        {
          PurchaseOrderLine purchaseOrderLine = ctx.readObject();
          PurchaseOrderLine result =
              PurchaseOrderService.createPurchaseOrderLine(purchaseOrderLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.PurchaseOrderServiceUpdatePurchaseOrderLine:
        {
          PurchaseOrderLine purchaseOrderLine = ctx.readObject();
          PurchaseOrderLine result =
              PurchaseOrderService.updatePurchaseOrderLine(purchaseOrderLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.PurchaseOrderServiceDeletePurchaseOrderLine:
        {
          PurchaseOrderLine purchaseOrderLine = ctx.readObject();
          PurchaseOrderService.deletePurchaseOrderLine(purchaseOrderLine);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleStockAlertService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.StockAlertServiceCreateStockAlert:
        {
          StockAlert stockAlert = ctx.readObject();
          StockAlert result = StockAlertService.createStockAlert(stockAlert);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockAlertServiceUpdateStockAlert:
        {
          StockAlert stockAlert = ctx.readObject();
          StockAlert result = StockAlertService.updateStockAlert(stockAlert);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.StockAlertServiceDeleteStockAlert:
        {
          StockAlert stockAlert = ctx.readObject();
          StockAlertService.deleteStockAlert(stockAlert);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleGoodsReceiptService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.GoodsReceiptServiceCreateGoodsReceipt:
        {
          GoodsReceipt goodsReceipt = ctx.readObject();
          GoodsReceipt result = GoodsReceiptService.createGoodsReceipt(goodsReceipt);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.GoodsReceiptServiceUpdateGoodsReceipt:
        {
          GoodsReceipt goodsReceipt = ctx.readObject();
          GoodsReceipt result = GoodsReceiptService.updateGoodsReceipt(goodsReceipt);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.GoodsReceiptServiceDeleteGoodsReceipt:
        {
          GoodsReceipt goodsReceipt = ctx.readObject();
          GoodsReceiptService.deleteGoodsReceipt(goodsReceipt);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.GoodsReceiptServiceConfirmGoodsReceipt:
        {
          GoodsReceipt goodsReceipt = ctx.readObject();
          GoodsReceipt result = GoodsReceiptService.confirmGoodsReceipt(goodsReceipt);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.GoodsReceiptServiceCreateGoodsReceiptLine:
        {
          GoodsReceiptLine goodsReceiptLine = ctx.readObject();
          GoodsReceiptLine result = GoodsReceiptService.createGoodsReceiptLine(goodsReceiptLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.GoodsReceiptServiceUpdateGoodsReceiptLine:
        {
          GoodsReceiptLine goodsReceiptLine = ctx.readObject();
          GoodsReceiptLine result = GoodsReceiptService.updateGoodsReceiptLine(goodsReceiptLine);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.GoodsReceiptServiceDeleteGoodsReceiptLine:
        {
          GoodsReceiptLine goodsReceiptLine = ctx.readObject();
          GoodsReceiptService.deleteGoodsReceiptLine(goodsReceiptLine);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleSupplierService(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.SupplierServiceCreateSupplier:
        {
          Vendor supplier = ctx.readObject();
          Vendor result = SupplierService.createSupplier(supplier);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SupplierServiceUpdateSupplier:
        {
          Vendor supplier = ctx.readObject();
          Vendor result = SupplierService.updateSupplier(supplier);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SupplierServiceDeleteSupplier:
        {
          Vendor supplier = ctx.readObject();
          SupplierService.deleteSupplier(supplier);
          msg.writeByte(0);
          break;
        }
      case RPCConstants.SupplierServiceCreateSupplierContact:
        {
          SupplierContact supplierContact = ctx.readObject();
          SupplierContact result = SupplierService.createSupplierContact(supplierContact);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SupplierServiceUpdateSupplierContact:
        {
          SupplierContact supplierContact = ctx.readObject();
          SupplierContact result = SupplierService.updateSupplierContact(supplierContact);
          msg.writeByte(0);
          ctx.writeObject(result);
          break;
        }
      case RPCConstants.SupplierServiceDeleteSupplierContact:
        {
          SupplierContact supplierContact = ctx.readObject();
          SupplierService.deleteSupplierContact(supplierContact);
          msg.writeByte(0);
          break;
        }
    }
  }

  private void handleUniqueChecker(
      int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.UniqueCheckerCheckTokenUniqueInOneTimePassword:
        {
          OneTimePassword oneTimePassword = ctx.readObject();
          String token = ctx.readString();
          boolean result = UniqueChecker.checkTokenUniqueInOneTimePassword(oneTimePassword, token);
          msg.writeByte(0);
          ctx.writeBoolean(result);
          break;
        }
      case RPCConstants.UniqueCheckerCheckNameUniqueInOrganization:
        {
          Organization organization = ctx.readObject();
          String name = ctx.readString();
          boolean result = UniqueChecker.checkNameUniqueInOrganization(organization, name);
          msg.writeByte(0);
          ctx.writeBoolean(result);
          break;
        }
      case RPCConstants.UniqueCheckerCheckCodeUniqueInOrganization:
        {
          Organization organization = ctx.readObject();
          String code = ctx.readString();
          boolean result = UniqueChecker.checkCodeUniqueInOrganization(organization, code);
          msg.writeByte(0);
          ctx.writeBoolean(result);
          break;
        }
      case RPCConstants.UniqueCheckerCheckUserEmailUniqueInOrganization:
        {
          Organization organization = ctx.readObject();
          User user = ctx.readObject();
          String email = ctx.readString();
          boolean result =
              UniqueChecker.checkUserEmailUniqueInOrganization(organization, user, email);
          msg.writeByte(0);
          ctx.writeBoolean(result);
          break;
        }
      case RPCConstants.UniqueCheckerCheckUserPasswordUniqueInOrganization:
        {
          Organization organization = ctx.readObject();
          User user = ctx.readObject();
          String password = ctx.readString();
          boolean result =
              UniqueChecker.checkUserPasswordUniqueInOrganization(organization, user, password);
          msg.writeByte(0);
          ctx.writeBoolean(result);
          break;
        }
      case RPCConstants.UniqueCheckerCheckUserInvitationTokenUniqueInOrganization:
        {
          Organization organization = ctx.readObject();
          UserInvitation userInvitation = ctx.readObject();
          String token = ctx.readString();
          boolean result =
              UniqueChecker.checkUserInvitationTokenUniqueInOrganization(
                  organization, userInvitation, token);
          msg.writeByte(0);
          ctx.writeBoolean(result);
          break;
        }
      case RPCConstants.UniqueCheckerCheckUserProfileUserUniqueInOrganization:
        {
          Organization organization = ctx.readObject();
          UserProfile userProfile = ctx.readObject();
          User user = ctx.readObject();
          boolean result =
              UniqueChecker.checkUserProfileUserUniqueInOrganization(
                  organization, userProfile, user);
          msg.writeByte(0);
          ctx.writeBoolean(result);
          break;
        }
    }
  }

  private void handleFileService(int methodIdx, RocketInputContext ctx, rest.ws.RocketMessage msg) {
    switch (methodIdx) {
      case RPCConstants.FileServiceCreateTempFile:
        {
          String fullNameOrExtn = ctx.readString();
          boolean extnGiven = ctx.readBoolean();
          String content = ctx.readString();
          DFile result = FileService.createTempFile(fullNameOrExtn, extnGiven, content);
          msg.writeByte(0);
          ctx.writeDFile(result);
          break;
        }
    }
  }
}
