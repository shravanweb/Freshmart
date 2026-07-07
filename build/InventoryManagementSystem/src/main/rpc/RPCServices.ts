import StockTransferServiceClient from "./StockTransferServiceClient";
import StockBatchServiceClient from "./StockBatchServiceClient";
import PurchaseOrderServiceClient from "./PurchaseOrderServiceClient";
import InventoryAdjustmentServiceClient from "./InventoryAdjustmentServiceClient";
import NotificationServiceClient from "./NotificationServiceClient";
import SalesOrderServiceClient from "./SalesOrderServiceClient";
import StoreServiceClient from "./StoreServiceClient";
import SalesReturnServiceClient from "./SalesReturnServiceClient";
import WarehouseServiceClient from "./WarehouseServiceClient";
import UserManagementServiceClient from "./UserManagementServiceClient";
import WarehouseStockServiceClient from "./WarehouseStockServiceClient";
import AuditLogServiceClient from "./AuditLogServiceClient";
import StockAlertServiceClient from "./StockAlertServiceClient";
import ProductServiceClient from "./ProductServiceClient";
import FileServiceClient from "./FileServiceClient";
import SupplierServiceClient from "./SupplierServiceClient";
import GoodsReceiptServiceClient from "./GoodsReceiptServiceClient";
import OrganizationServiceClient from "./OrganizationServiceClient";
import InventoryMovementServiceClient from "./InventoryMovementServiceClient";
import ProductCategoryServiceClient from "./ProductCategoryServiceClient";
import UnitOfMeasureServiceClient from "./UnitOfMeasureServiceClient";
import UniqueCheckerClient from "./UniqueCheckerClient";

export default class RPCServices {
  private static _inventoryAdjustmentService: InventoryAdjustmentServiceClient;
  private static _inventoryMovementService: InventoryMovementServiceClient;
  private static _notificationService: NotificationServiceClient;
  private static _organizationService: OrganizationServiceClient;
  private static _stockBatchService: StockBatchServiceClient;
  private static _productCategoryService: ProductCategoryServiceClient;
  private static _productService: ProductServiceClient;
  private static _auditLogService: AuditLogServiceClient;
  private static _warehouseService: WarehouseServiceClient;
  private static _warehouseStockService: WarehouseStockServiceClient;
  private static _salesOrderService: SalesOrderServiceClient;
  private static _unitOfMeasureService: UnitOfMeasureServiceClient;
  private static _userManagementService: UserManagementServiceClient;
  private static _stockTransferService: StockTransferServiceClient;
  private static _storeService: StoreServiceClient;
  private static _salesReturnService: SalesReturnServiceClient;
  private static _purchaseOrderService: PurchaseOrderServiceClient;
  private static _stockAlertService: StockAlertServiceClient;
  private static _goodsReceiptService: GoodsReceiptServiceClient;
  private static _supplierService: SupplierServiceClient;
  private static _uniqueChecker: UniqueCheckerClient;
  private static _fileService: FileServiceClient;
  public static getInventoryAdjustmentService(): InventoryAdjustmentServiceClient {
    if (RPCServices._inventoryAdjustmentService == null) {
      RPCServices._inventoryAdjustmentService =
        new InventoryAdjustmentServiceClient();
    }

    return RPCServices._inventoryAdjustmentService;
  }
  public static getInventoryMovementService(): InventoryMovementServiceClient {
    if (RPCServices._inventoryMovementService == null) {
      RPCServices._inventoryMovementService =
        new InventoryMovementServiceClient();
    }

    return RPCServices._inventoryMovementService;
  }
  public static getNotificationService(): NotificationServiceClient {
    if (RPCServices._notificationService == null) {
      RPCServices._notificationService = new NotificationServiceClient();
    }

    return RPCServices._notificationService;
  }
  public static getOrganizationService(): OrganizationServiceClient {
    if (RPCServices._organizationService == null) {
      RPCServices._organizationService = new OrganizationServiceClient();
    }

    return RPCServices._organizationService;
  }
  public static getStockBatchService(): StockBatchServiceClient {
    if (RPCServices._stockBatchService == null) {
      RPCServices._stockBatchService = new StockBatchServiceClient();
    }

    return RPCServices._stockBatchService;
  }
  public static getProductCategoryService(): ProductCategoryServiceClient {
    if (RPCServices._productCategoryService == null) {
      RPCServices._productCategoryService = new ProductCategoryServiceClient();
    }

    return RPCServices._productCategoryService;
  }
  public static getProductService(): ProductServiceClient {
    if (RPCServices._productService == null) {
      RPCServices._productService = new ProductServiceClient();
    }

    return RPCServices._productService;
  }
  public static getAuditLogService(): AuditLogServiceClient {
    if (RPCServices._auditLogService == null) {
      RPCServices._auditLogService = new AuditLogServiceClient();
    }

    return RPCServices._auditLogService;
  }
  public static getWarehouseService(): WarehouseServiceClient {
    if (RPCServices._warehouseService == null) {
      RPCServices._warehouseService = new WarehouseServiceClient();
    }

    return RPCServices._warehouseService;
  }
  public static getWarehouseStockService(): WarehouseStockServiceClient {
    if (RPCServices._warehouseStockService == null) {
      RPCServices._warehouseStockService = new WarehouseStockServiceClient();
    }

    return RPCServices._warehouseStockService;
  }
  public static getSalesOrderService(): SalesOrderServiceClient {
    if (RPCServices._salesOrderService == null) {
      RPCServices._salesOrderService = new SalesOrderServiceClient();
    }

    return RPCServices._salesOrderService;
  }
  public static getUnitOfMeasureService(): UnitOfMeasureServiceClient {
    if (RPCServices._unitOfMeasureService == null) {
      RPCServices._unitOfMeasureService = new UnitOfMeasureServiceClient();
    }

    return RPCServices._unitOfMeasureService;
  }
  public static getUserManagementService(): UserManagementServiceClient {
    if (RPCServices._userManagementService == null) {
      RPCServices._userManagementService = new UserManagementServiceClient();
    }

    return RPCServices._userManagementService;
  }
  public static getStockTransferService(): StockTransferServiceClient {
    if (RPCServices._stockTransferService == null) {
      RPCServices._stockTransferService = new StockTransferServiceClient();
    }

    return RPCServices._stockTransferService;
  }
  public static getStoreService(): StoreServiceClient {
    if (RPCServices._storeService == null) {
      RPCServices._storeService = new StoreServiceClient();
    }

    return RPCServices._storeService;
  }
  public static getSalesReturnService(): SalesReturnServiceClient {
    if (RPCServices._salesReturnService == null) {
      RPCServices._salesReturnService = new SalesReturnServiceClient();
    }

    return RPCServices._salesReturnService;
  }
  public static getPurchaseOrderService(): PurchaseOrderServiceClient {
    if (RPCServices._purchaseOrderService == null) {
      RPCServices._purchaseOrderService = new PurchaseOrderServiceClient();
    }

    return RPCServices._purchaseOrderService;
  }
  public static getStockAlertService(): StockAlertServiceClient {
    if (RPCServices._stockAlertService == null) {
      RPCServices._stockAlertService = new StockAlertServiceClient();
    }

    return RPCServices._stockAlertService;
  }
  public static getGoodsReceiptService(): GoodsReceiptServiceClient {
    if (RPCServices._goodsReceiptService == null) {
      RPCServices._goodsReceiptService = new GoodsReceiptServiceClient();
    }

    return RPCServices._goodsReceiptService;
  }
  public static getSupplierService(): SupplierServiceClient {
    if (RPCServices._supplierService == null) {
      RPCServices._supplierService = new SupplierServiceClient();
    }

    return RPCServices._supplierService;
  }
  public static getUniqueChecker(): UniqueCheckerClient {
    if (RPCServices._uniqueChecker == null) {
      RPCServices._uniqueChecker = new UniqueCheckerClient();
    }

    return RPCServices._uniqueChecker;
  }
  public static getFileService(): FileServiceClient {
    if (RPCServices._fileService == null) {
      RPCServices._fileService = new FileServiceClient();
    }

    return RPCServices._fileService;
  }
}
