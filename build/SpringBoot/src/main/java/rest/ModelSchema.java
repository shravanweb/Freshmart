package rest;

import classes.AdjustmentReason;
import classes.AdjustmentStatus;
import classes.AlertStatus;
import classes.AllAuditLogs;
import classes.AllDevices;
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
import classes.AppUserRole;
import classes.AuditAction;
import classes.BatchStatus;
import classes.ChangeEventType;
import classes.ChartLabelDisplay;
import classes.ChartTheme;
import classes.ChartValuePosition;
import classes.ColumnWidthType;
import classes.ConnectionStatus;
import classes.DashboardMetrics;
import classes.EntityStatus;
import classes.ExpiringBatches;
import classes.GoodsReceiptItem;
import classes.GoodsReceiptStatus;
import classes.GradientType;
import classes.IconType;
import classes.InventoryMovementsByDateRange;
import classes.InvitationStatus;
import classes.LoginResult;
import classes.LowStockItems;
import classes.MovementDirection;
import classes.MovementReferenceType;
import classes.MovementReportRows;
import classes.MovementType;
import classes.MutateResultStatus;
import classes.NotificationChannel;
import classes.NotificationType;
import classes.OrganizationItem;
import classes.OrganizationStatus;
import classes.OutOfStockItems;
import classes.PaymentStatus;
import classes.ProductItem;
import classes.ProductSearch;
import classes.ProductStatus;
import classes.ProductsByCategory;
import classes.PurchaseOrderItem;
import classes.PurchaseOrderStatus;
import classes.PurchaseOrdersByStatus;
import classes.ReportAggregateType;
import classes.ReportBarChartType;
import classes.ReportCellAllign;
import classes.ReportCellType;
import classes.ReportFieldFromType;
import classes.ReportFilterValue;
import classes.ReportInput;
import classes.ReportLineAndAreaChartType;
import classes.ReportLineAndColumnChartType;
import classes.ReportMapType;
import classes.ReportOutAttribute;
import classes.ReportOutCell;
import classes.ReportOutColumn;
import classes.ReportOutOption;
import classes.ReportOutRow;
import classes.ReportOutput;
import classes.ReportPieChartType;
import classes.ReportRuleOperator;
import classes.ResultStatus;
import classes.ReturnReason;
import classes.SalesOrderItem;
import classes.SalesOrderStatus;
import classes.SalesOrdersByStore;
import classes.SalesReturnStatus;
import classes.StockAlertType;
import classes.StockTransferItem;
import classes.StockTransferStatus;
import classes.StockValuationReport;
import classes.StoreItem;
import classes.StoreType;
import classes.SupplierItem;
import classes.TrackSizeType;
import classes.UnreadNotificationCount;
import classes.UomType;
import classes.UserDevices;
import classes.UserProfileByUser;
import classes.VerificationDataByToken;
import classes.WarehouseStockByProduct;
import classes.WarehouseStockByWarehouse;
import classes.WarehouseType;
import classes.WarehousesByStore;
import d3e.core.DFile;
import d3e.core.RPCConstants;
import d3e.core.SchemaConstants;
import gqltosql.schema.DClazz;
import gqltosql.schema.DModel;
import gqltosql.schema.DModelType;
import gqltosql.schema.FieldPrimitiveType;
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
import models.AnonymousUser;
import models.AuditLog;
import models.Avatar;
import models.BaseUser;
import models.BaseUserSession;
import models.ChangePasswordRequest;
import models.D3EImage;
import models.DashboardMetricsRequest;
import models.EmailMessage;
import models.ExpiringBatchesRequest;
import models.GoodsReceipt;
import models.GoodsReceiptItemRequest;
import models.GoodsReceiptLine;
import models.InAppNotification;
import models.InventoryAdjustment;
import models.InventoryAdjustmentLine;
import models.InventoryMovement;
import models.InventoryMovementsByDateRangeRequest;
import models.LowStockItemsRequest;
import models.MovementReportRowsRequest;
import models.NotificationTemplate;
import models.OneTimePassword;
import models.Organization;
import models.OrganizationItemRequest;
import models.OutOfStockItemsRequest;
import models.Product;
import models.ProductCategory;
import models.ProductItemRequest;
import models.ProductSearchRequest;
import models.ProductsByCategoryRequest;
import models.PurchaseOrder;
import models.PurchaseOrderItemRequest;
import models.PurchaseOrderLine;
import models.PurchaseOrdersByStatusRequest;
import models.PushNotification;
import models.Report;
import models.ReportBarChartConfig;
import models.ReportBaseConfig;
import models.ReportCardConfig;
import models.ReportCell;
import models.ReportCellStyle;
import models.ReportConfig;
import models.ReportConfigOption;
import models.ReportData;
import models.ReportDataRow;
import models.ReportDataSection;
import models.ReportField;
import models.ReportFilter;
import models.ReportFunnelChartConfig;
import models.ReportGuageConfig;
import models.ReportKPIConfig;
import models.ReportKeyInfluencerConfig;
import models.ReportLineAndAreaChartConfig;
import models.ReportLineAndColumnChartConfig;
import models.ReportMapConfig;
import models.ReportMatrixConfig;
import models.ReportModel;
import models.ReportMultiRowCardConfig;
import models.ReportNamedCondition;
import models.ReportNamedConditionFilter;
import models.ReportPieChartConfig;
import models.ReportProperty;
import models.ReportPropertyFilter;
import models.ReportRule;
import models.ReportRuleSet;
import models.ReportScatterChartConfig;
import models.ReportSingleRule;
import models.ReportSlicerConfig;
import models.ReportTableConfig;
import models.ReportWaterfallChartConfig;
import models.SMSMessage;
import models.SalesOrder;
import models.SalesOrderItemRequest;
import models.SalesOrderLine;
import models.SalesOrdersByStoreRequest;
import models.SalesReturn;
import models.SalesReturnLine;
import models.StockAlert;
import models.StockBatch;
import models.StockTransfer;
import models.StockTransferItemRequest;
import models.StockTransferLine;
import models.StockValuationReportRequest;
import models.Store;
import models.StoreItemRequest;
import models.SupplierContact;
import models.SupplierItemRequest;
import models.UnitOfMeasure;
import models.UnreadNotificationCountRequest;
import models.User;
import models.UserDevice;
import models.UserDevicesRequest;
import models.UserInvitation;
import models.UserLoginRecord;
import models.UserProfile;
import models.UserProfileByUserRequest;
import models.UserRole;
import models.Vendor;
import models.VerificationData;
import models.VerificationDataByTokenRequest;
import models.Warehouse;
import models.WarehouseStock;
import models.WarehouseStockByProductRequest;
import models.WarehouseStockByWarehouseRequest;
import models.WarehousesByStoreRequest;

@org.springframework.stereotype.Service
public class ModelSchema extends AbstractModelSchema {
  protected void createAllEnums() {
    addEnum(ConnectionStatus.class, SchemaConstants.ConnectionStatus);
    addEnum(MutateResultStatus.class, SchemaConstants.MutateResultStatus);
    addEnum(ReportFieldFromType.class, SchemaConstants.ReportFieldFromType);
    addEnum(ReportRuleOperator.class, SchemaConstants.ReportRuleOperator);
    addEnum(ReportBarChartType.class, SchemaConstants.ReportBarChartType);
    addEnum(ReportLineAndAreaChartType.class, SchemaConstants.ReportLineAndAreaChartType);
    addEnum(ReportLineAndColumnChartType.class, SchemaConstants.ReportLineAndColumnChartType);
    addEnum(ReportPieChartType.class, SchemaConstants.ReportPieChartType);
    addEnum(ReportMapType.class, SchemaConstants.ReportMapType);
    addEnum(ReportAggregateType.class, SchemaConstants.ReportAggregateType);
    addEnum(ReportCellAllign.class, SchemaConstants.ReportCellAllign);
    addEnum(ReportCellType.class, SchemaConstants.ReportCellType);
    addEnum(ChangeEventType.class, SchemaConstants.ChangeEventType);
    addEnum(ColumnWidthType.class, SchemaConstants.ColumnWidthType);
    addEnum(GradientType.class, SchemaConstants.GradientType);
    addEnum(TrackSizeType.class, SchemaConstants.TrackSizeType);
    addEnum(IconType.class, SchemaConstants.IconType);
    addEnum(ResultStatus.class, SchemaConstants.ResultStatus);
    addEnum(ChartLabelDisplay.class, SchemaConstants.ChartLabelDisplay);
    addEnum(ChartTheme.class, SchemaConstants.ChartTheme);
    addEnum(ChartValuePosition.class, SchemaConstants.ChartValuePosition);
    addEnum(AdjustmentReason.class, SchemaConstants.AdjustmentReason);
    addEnum(AdjustmentStatus.class, SchemaConstants.AdjustmentStatus);
    addEnum(AlertStatus.class, SchemaConstants.AlertStatus);
    addEnum(AppUserRole.class, SchemaConstants.AppUserRole);
    addEnum(AuditAction.class, SchemaConstants.AuditAction);
    addEnum(BatchStatus.class, SchemaConstants.BatchStatus);
    addEnum(EntityStatus.class, SchemaConstants.EntityStatus);
    addEnum(GoodsReceiptStatus.class, SchemaConstants.GoodsReceiptStatus);
    addEnum(InvitationStatus.class, SchemaConstants.InvitationStatus);
    addEnum(MovementDirection.class, SchemaConstants.MovementDirection);
    addEnum(MovementReferenceType.class, SchemaConstants.MovementReferenceType);
    addEnum(MovementType.class, SchemaConstants.MovementType);
    addEnum(NotificationChannel.class, SchemaConstants.NotificationChannel);
    addEnum(NotificationType.class, SchemaConstants.NotificationType);
    addEnum(OrganizationStatus.class, SchemaConstants.OrganizationStatus);
    addEnum(PaymentStatus.class, SchemaConstants.PaymentStatus);
    addEnum(ProductStatus.class, SchemaConstants.ProductStatus);
    addEnum(PurchaseOrderStatus.class, SchemaConstants.PurchaseOrderStatus);
    addEnum(ReturnReason.class, SchemaConstants.ReturnReason);
    addEnum(SalesOrderStatus.class, SchemaConstants.SalesOrderStatus);
    addEnum(SalesReturnStatus.class, SchemaConstants.SalesReturnStatus);
    addEnum(StockAlertType.class, SchemaConstants.StockAlertType);
    addEnum(StockTransferStatus.class, SchemaConstants.StockTransferStatus);
    addEnum(StoreType.class, SchemaConstants.StoreType);
    addEnum(UomType.class, SchemaConstants.UomType);
    addEnum(WarehouseType.class, SchemaConstants.WarehouseType);
  }

  protected void createAllTables() {
    addTable(
        new DModel<DFile>(
            "DFile", SchemaConstants.DFile, 4, 0, "_dfile", DModelType.MODEL, () -> new DFile()));
    addTable(
        new DModel<AllAuditLogsRequest>(
                "AllAuditLogsRequest",
                SchemaConstants.AllAuditLogsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllAuditLogsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllDevicesRequest>(
                "AllDevicesRequest",
                SchemaConstants.AllDevicesRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllDevicesRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllGoodsReceiptsRequest>(
                "AllGoodsReceiptsRequest",
                SchemaConstants.AllGoodsReceiptsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllGoodsReceiptsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllInAppNotificationsRequest>(
                "AllInAppNotificationsRequest",
                SchemaConstants.AllInAppNotificationsRequest,
                2,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllInAppNotificationsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllInventoryAdjustmentsRequest>(
                "AllInventoryAdjustmentsRequest",
                SchemaConstants.AllInventoryAdjustmentsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllInventoryAdjustmentsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllInventoryMovementsRequest>(
                "AllInventoryMovementsRequest",
                SchemaConstants.AllInventoryMovementsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllInventoryMovementsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllProductCategoriesRequest>(
                "AllProductCategoriesRequest",
                SchemaConstants.AllProductCategoriesRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllProductCategoriesRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllProductsRequest>(
                "AllProductsRequest",
                SchemaConstants.AllProductsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllProductsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllPurchaseOrdersRequest>(
                "AllPurchaseOrdersRequest",
                SchemaConstants.AllPurchaseOrdersRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllPurchaseOrdersRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllSalesOrdersRequest>(
                "AllSalesOrdersRequest",
                SchemaConstants.AllSalesOrdersRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllSalesOrdersRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllSalesReturnsRequest>(
                "AllSalesReturnsRequest",
                SchemaConstants.AllSalesReturnsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllSalesReturnsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllStockAlertsRequest>(
                "AllStockAlertsRequest",
                SchemaConstants.AllStockAlertsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllStockAlertsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllStockBatchesRequest>(
                "AllStockBatchesRequest",
                SchemaConstants.AllStockBatchesRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllStockBatchesRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllStockTransfersRequest>(
                "AllStockTransfersRequest",
                SchemaConstants.AllStockTransfersRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllStockTransfersRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllStoresRequest>(
                "AllStoresRequest",
                SchemaConstants.AllStoresRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllStoresRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllSuppliersRequest>(
                "AllSuppliersRequest",
                SchemaConstants.AllSuppliersRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllSuppliersRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllUnitOfMeasuresRequest>(
                "AllUnitOfMeasuresRequest",
                SchemaConstants.AllUnitOfMeasuresRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllUnitOfMeasuresRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllUserInvitationsRequest>(
                "AllUserInvitationsRequest",
                SchemaConstants.AllUserInvitationsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllUserInvitationsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllUserProfilesRequest>(
                "AllUserProfilesRequest",
                SchemaConstants.AllUserProfilesRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllUserProfilesRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AllWarehousesRequest>(
                "AllWarehousesRequest",
                SchemaConstants.AllWarehousesRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new AllWarehousesRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<AnonymousUser>(
                "AnonymousUser",
                SchemaConstants.AnonymousUser,
                0,
                3,
                "_anonymous_user",
                DModelType.MODEL,
                () -> new AnonymousUser())
            .creatable());
    addTable(
        new DModel<AuditLog>(
                "AuditLog",
                SchemaConstants.AuditLog,
                10,
                0,
                "_audit_log",
                DModelType.MODEL,
                () -> new AuditLog())
            .creatable());
    addTable(
        new DModel<Avatar>(
            "Avatar",
            SchemaConstants.Avatar,
            2,
            0,
            "_avatar",
            DModelType.MODEL,
            () -> new Avatar()));
    addTable(
        new DModel<BaseUser>(
                "BaseUser", SchemaConstants.BaseUser, 3, 0, "_base_user", DModelType.MODEL)
            .creatable());
    addTable(
        new DModel<BaseUserSession>(
                "BaseUserSession",
                SchemaConstants.BaseUserSession,
                1,
                0,
                "_base_user_session",
                DModelType.MODEL)
            .creatable());
    addTable(
        new DModel<ChangePasswordRequest>(
                "ChangePasswordRequest",
                SchemaConstants.ChangePasswordRequest,
                1,
                0,
                "_change_password_request",
                DModelType.MODEL,
                () -> new ChangePasswordRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<D3EImage>(
                "D3EImage",
                SchemaConstants.D3EImage,
                4,
                0,
                "_d3e_image",
                DModelType.MODEL,
                () -> new D3EImage())
            .emb());
    addTable(
        new DModel<DashboardMetricsRequest>(
                "DashboardMetricsRequest",
                SchemaConstants.DashboardMetricsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new DashboardMetricsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<EmailMessage>(
                "EmailMessage",
                SchemaConstants.EmailMessage,
                10,
                0,
                "_email_message",
                DModelType.MODEL,
                () -> new EmailMessage())
            .trans()
            .creatable());
    addTable(
        new DModel<ExpiringBatchesRequest>(
                "ExpiringBatchesRequest",
                SchemaConstants.ExpiringBatchesRequest,
                2,
                0,
                "null",
                DModelType.MODEL,
                () -> new ExpiringBatchesRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<GoodsReceipt>(
                "GoodsReceipt",
                SchemaConstants.GoodsReceipt,
                11,
                0,
                "_goods_receipt",
                DModelType.MODEL,
                () -> new GoodsReceipt())
            .creatable());
    addTable(
        new DModel<GoodsReceiptItemRequest>(
                "GoodsReceiptItemRequest",
                SchemaConstants.GoodsReceiptItemRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new GoodsReceiptItemRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<GoodsReceiptLine>(
                "GoodsReceiptLine",
                SchemaConstants.GoodsReceiptLine,
                7,
                0,
                "_goods_receipt_line",
                DModelType.MODEL,
                () -> new GoodsReceiptLine())
            .creatable());
    addTable(
        new DModel<InAppNotification>(
                "InAppNotification",
                SchemaConstants.InAppNotification,
                9,
                0,
                "_in_app_notification",
                DModelType.MODEL,
                () -> new InAppNotification())
            .creatable());
    addTable(
        new DModel<InventoryAdjustment>(
                "InventoryAdjustment",
                SchemaConstants.InventoryAdjustment,
                9,
                0,
                "_inventory_adjustment",
                DModelType.MODEL,
                () -> new InventoryAdjustment())
            .creatable());
    addTable(
        new DModel<InventoryAdjustmentLine>(
                "InventoryAdjustmentLine",
                SchemaConstants.InventoryAdjustmentLine,
                7,
                0,
                "_inventory_adjustment_line",
                DModelType.MODEL,
                () -> new InventoryAdjustmentLine())
            .creatable());
    addTable(
        new DModel<InventoryMovement>(
                "InventoryMovement",
                SchemaConstants.InventoryMovement,
                15,
                0,
                "_inventory_movement",
                DModelType.MODEL,
                () -> new InventoryMovement())
            .creatable());
    addTable(
        new DModel<InventoryMovementsByDateRangeRequest>(
                "InventoryMovementsByDateRangeRequest",
                SchemaConstants.InventoryMovementsByDateRangeRequest,
                3,
                0,
                "null",
                DModelType.MODEL,
                () -> new InventoryMovementsByDateRangeRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<LowStockItemsRequest>(
                "LowStockItemsRequest",
                SchemaConstants.LowStockItemsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new LowStockItemsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<MovementReportRowsRequest>(
                "MovementReportRowsRequest",
                SchemaConstants.MovementReportRowsRequest,
                4,
                0,
                "null",
                DModelType.MODEL,
                () -> new MovementReportRowsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<NotificationTemplate>(
                "NotificationTemplate",
                SchemaConstants.NotificationTemplate,
                6,
                0,
                "_notification_template",
                DModelType.MODEL,
                () -> new NotificationTemplate())
            .creatable());
    addTable(
        new DModel<OneTimePassword>(
                "OneTimePassword",
                SchemaConstants.OneTimePassword,
                9,
                0,
                "_one_time_password",
                DModelType.MODEL,
                () -> new OneTimePassword())
            .creatable());
    addTable(
        new DModel<Organization>(
                "Organization",
                SchemaConstants.Organization,
                14,
                0,
                "_organization",
                DModelType.MODEL,
                () -> new Organization())
            .creatable());
    addTable(
        new DModel<OrganizationItemRequest>(
                "OrganizationItemRequest",
                SchemaConstants.OrganizationItemRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new OrganizationItemRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<OutOfStockItemsRequest>(
                "OutOfStockItemsRequest",
                SchemaConstants.OutOfStockItemsRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new OutOfStockItemsRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<Product>(
                "Product",
                SchemaConstants.Product,
                20,
                0,
                "_product",
                DModelType.MODEL,
                () -> new Product())
            .creatable());
    addTable(
        new DModel<ProductCategory>(
                "ProductCategory",
                SchemaConstants.ProductCategory,
                8,
                0,
                "_product_category",
                DModelType.MODEL,
                () -> new ProductCategory())
            .creatable());
    addTable(
        new DModel<ProductItemRequest>(
                "ProductItemRequest",
                SchemaConstants.ProductItemRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new ProductItemRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<ProductSearchRequest>(
                "ProductSearchRequest",
                SchemaConstants.ProductSearchRequest,
                4,
                0,
                "null",
                DModelType.MODEL,
                () -> new ProductSearchRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<ProductsByCategoryRequest>(
                "ProductsByCategoryRequest",
                SchemaConstants.ProductsByCategoryRequest,
                2,
                0,
                "null",
                DModelType.MODEL,
                () -> new ProductsByCategoryRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<PurchaseOrder>(
                "PurchaseOrder",
                SchemaConstants.PurchaseOrder,
                16,
                0,
                "_purchase_order",
                DModelType.MODEL,
                () -> new PurchaseOrder())
            .creatable());
    addTable(
        new DModel<PurchaseOrderItemRequest>(
                "PurchaseOrderItemRequest",
                SchemaConstants.PurchaseOrderItemRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new PurchaseOrderItemRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<PurchaseOrderLine>(
                "PurchaseOrderLine",
                SchemaConstants.PurchaseOrderLine,
                8,
                0,
                "_purchase_order_line",
                DModelType.MODEL,
                () -> new PurchaseOrderLine())
            .creatable());
    addTable(
        new DModel<PurchaseOrdersByStatusRequest>(
                "PurchaseOrdersByStatusRequest",
                SchemaConstants.PurchaseOrdersByStatusRequest,
                2,
                0,
                "null",
                DModelType.MODEL,
                () -> new PurchaseOrdersByStatusRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<PushNotification>(
                "PushNotification",
                SchemaConstants.PushNotification,
                9,
                0,
                "_push_notification",
                DModelType.MODEL,
                () -> new PushNotification())
            .creatable());
    addTable(
        new DModel<Report>(
                "Report",
                SchemaConstants.Report,
                7,
                0,
                "_report",
                DModelType.MODEL,
                () -> new Report())
            .document()
            .creatable());
    addTable(
        new DModel<ReportBarChartConfig>(
                "ReportBarChartConfig",
                SchemaConstants.ReportBarChartConfig,
                6,
                0,
                "_report_bar_chart_config",
                DModelType.MODEL,
                () -> new ReportBarChartConfig())
            .document());
    addTable(
        new DModel<ReportBaseConfig>(
                "ReportBaseConfig",
                SchemaConstants.ReportBaseConfig,
                0,
                0,
                "_report_base_config",
                DModelType.MODEL)
            .document());
    addTable(
        new DModel<ReportCardConfig>(
                "ReportCardConfig",
                SchemaConstants.ReportCardConfig,
                1,
                0,
                "_report_card_config",
                DModelType.MODEL,
                () -> new ReportCardConfig())
            .document());
    addTable(
        new DModel<ReportCell>(
                "ReportCell",
                SchemaConstants.ReportCell,
                5,
                0,
                "_report_cell",
                DModelType.MODEL,
                () -> new ReportCell())
            .document());
    addTable(
        new DModel<ReportCellStyle>(
                "ReportCellStyle",
                SchemaConstants.ReportCellStyle,
                7,
                0,
                "_report_cell_style",
                DModelType.MODEL,
                () -> new ReportCellStyle())
            .document());
    addTable(
        new DModel<ReportConfig>(
            "ReportConfig",
            SchemaConstants.ReportConfig,
            2,
            0,
            "_report_config",
            DModelType.MODEL,
            () -> new ReportConfig()));
    addTable(
        new DModel<ReportConfigOption>(
            "ReportConfigOption",
            SchemaConstants.ReportConfigOption,
            2,
            0,
            "_report_config_option",
            DModelType.MODEL,
            () -> new ReportConfigOption()));
    addTable(
        new DModel<ReportData>(
                "ReportData",
                SchemaConstants.ReportData,
                2,
                0,
                "_report_data",
                DModelType.MODEL,
                () -> new ReportData())
            .trans()
            .creatable());
    addTable(
        new DModel<ReportDataRow>(
                "ReportDataRow",
                SchemaConstants.ReportDataRow,
                1,
                0,
                "_report_data_row",
                DModelType.MODEL,
                () -> new ReportDataRow())
            .trans());
    addTable(
        new DModel<ReportDataSection>(
                "ReportDataSection",
                SchemaConstants.ReportDataSection,
                2,
                0,
                "_report_data_section",
                DModelType.MODEL,
                () -> new ReportDataSection())
            .trans());
    addTable(
        new DModel<ReportField>(
                "ReportField",
                SchemaConstants.ReportField,
                3,
                0,
                "_report_field",
                DModelType.MODEL,
                () -> new ReportField())
            .document());
    addTable(
        new DModel<ReportFilter>(
                "ReportFilter",
                SchemaConstants.ReportFilter,
                0,
                0,
                "_report_filter",
                DModelType.MODEL)
            .document());
    addTable(
        new DModel<ReportFunnelChartConfig>(
                "ReportFunnelChartConfig",
                SchemaConstants.ReportFunnelChartConfig,
                3,
                0,
                "_report_funnel_chart_config",
                DModelType.MODEL,
                () -> new ReportFunnelChartConfig())
            .document());
    addTable(
        new DModel<ReportGuageConfig>(
                "ReportGuageConfig",
                SchemaConstants.ReportGuageConfig,
                5,
                0,
                "_report_guage_config",
                DModelType.MODEL,
                () -> new ReportGuageConfig())
            .document());
    addTable(
        new DModel<ReportKPIConfig>(
                "ReportKPIConfig",
                SchemaConstants.ReportKPIConfig,
                3,
                0,
                "_report_kpi_config",
                DModelType.MODEL,
                () -> new ReportKPIConfig())
            .document());
    addTable(
        new DModel<ReportKeyInfluencerConfig>(
                "ReportKeyInfluencerConfig",
                SchemaConstants.ReportKeyInfluencerConfig,
                3,
                0,
                "_report_key_influencer_config",
                DModelType.MODEL,
                () -> new ReportKeyInfluencerConfig())
            .document());
    addTable(
        new DModel<ReportLineAndAreaChartConfig>(
                "ReportLineAndAreaChartConfig",
                SchemaConstants.ReportLineAndAreaChartConfig,
                7,
                0,
                "_report_line_and_area_chart_config",
                DModelType.MODEL,
                () -> new ReportLineAndAreaChartConfig())
            .document());
    addTable(
        new DModel<ReportLineAndColumnChartConfig>(
                "ReportLineAndColumnChartConfig",
                SchemaConstants.ReportLineAndColumnChartConfig,
                7,
                0,
                "_report_line_and_column_chart_config",
                DModelType.MODEL,
                () -> new ReportLineAndColumnChartConfig())
            .document());
    addTable(
        new DModel<ReportMapConfig>(
                "ReportMapConfig",
                SchemaConstants.ReportMapConfig,
                7,
                0,
                "_report_map_config",
                DModelType.MODEL,
                () -> new ReportMapConfig())
            .document());
    addTable(
        new DModel<ReportMatrixConfig>(
                "ReportMatrixConfig",
                SchemaConstants.ReportMatrixConfig,
                3,
                0,
                "_report_matrix_config",
                DModelType.MODEL,
                () -> new ReportMatrixConfig())
            .document());
    addTable(
        new DModel<ReportModel>(
                "ReportModel",
                SchemaConstants.ReportModel,
                2,
                0,
                "_report_model",
                DModelType.MODEL,
                () -> new ReportModel())
            .trans()
            .creatable());
    addTable(
        new DModel<ReportMultiRowCardConfig>(
                "ReportMultiRowCardConfig",
                SchemaConstants.ReportMultiRowCardConfig,
                1,
                0,
                "_report_multi_row_card_config",
                DModelType.MODEL,
                () -> new ReportMultiRowCardConfig())
            .document());
    addTable(
        new DModel<ReportNamedCondition>(
                "ReportNamedCondition",
                SchemaConstants.ReportNamedCondition,
                2,
                0,
                "_report_named_condition",
                DModelType.MODEL,
                () -> new ReportNamedCondition())
            .document());
    addTable(
        new DModel<ReportNamedConditionFilter>(
                "ReportNamedConditionFilter",
                SchemaConstants.ReportNamedConditionFilter,
                2,
                0,
                "_report_named_condition_filter",
                DModelType.MODEL,
                () -> new ReportNamedConditionFilter())
            .document());
    addTable(
        new DModel<ReportPieChartConfig>(
                "ReportPieChartConfig",
                SchemaConstants.ReportPieChartConfig,
                5,
                0,
                "_report_pie_chart_config",
                DModelType.MODEL,
                () -> new ReportPieChartConfig())
            .document());
    addTable(
        new DModel<ReportProperty>(
                "ReportProperty",
                SchemaConstants.ReportProperty,
                7,
                0,
                "_report_property",
                DModelType.MODEL,
                () -> new ReportProperty())
            .trans());
    addTable(
        new DModel<ReportPropertyFilter>(
                "ReportPropertyFilter",
                SchemaConstants.ReportPropertyFilter,
                7,
                0,
                "_report_property_filter",
                DModelType.MODEL,
                () -> new ReportPropertyFilter())
            .document());
    addTable(
        new DModel<ReportRule>(
                "ReportRule", SchemaConstants.ReportRule, 1, 0, "_report_rule", DModelType.MODEL)
            .document());
    addTable(
        new DModel<ReportRuleSet>(
                "ReportRuleSet",
                SchemaConstants.ReportRuleSet,
                2,
                1,
                "_report_rule_set",
                DModelType.MODEL,
                () -> new ReportRuleSet())
            .document());
    addTable(
        new DModel<ReportScatterChartConfig>(
                "ReportScatterChartConfig",
                SchemaConstants.ReportScatterChartConfig,
                7,
                0,
                "_report_scatter_chart_config",
                DModelType.MODEL,
                () -> new ReportScatterChartConfig())
            .document());
    addTable(
        new DModel<ReportSingleRule>(
                "ReportSingleRule",
                SchemaConstants.ReportSingleRule,
                9,
                1,
                "_report_single_rule",
                DModelType.MODEL,
                () -> new ReportSingleRule())
            .document());
    addTable(
        new DModel<ReportSlicerConfig>(
                "ReportSlicerConfig",
                SchemaConstants.ReportSlicerConfig,
                1,
                0,
                "_report_slicer_config",
                DModelType.MODEL,
                () -> new ReportSlicerConfig())
            .document());
    addTable(
        new DModel<ReportTableConfig>(
                "ReportTableConfig",
                SchemaConstants.ReportTableConfig,
                1,
                0,
                "_report_table_config",
                DModelType.MODEL,
                () -> new ReportTableConfig())
            .document());
    addTable(
        new DModel<ReportWaterfallChartConfig>(
                "ReportWaterfallChartConfig",
                SchemaConstants.ReportWaterfallChartConfig,
                4,
                0,
                "_report_waterfall_chart_config",
                DModelType.MODEL,
                () -> new ReportWaterfallChartConfig())
            .document());
    addTable(
        new DModel<SMSMessage>(
                "SMSMessage",
                SchemaConstants.SMSMessage,
                5,
                0,
                "_sms_message",
                DModelType.MODEL,
                () -> new SMSMessage())
            .trans()
            .creatable());
    addTable(
        new DModel<SalesOrder>(
                "SalesOrder",
                SchemaConstants.SalesOrder,
                15,
                0,
                "_sales_order",
                DModelType.MODEL,
                () -> new SalesOrder())
            .creatable());
    addTable(
        new DModel<SalesOrderItemRequest>(
                "SalesOrderItemRequest",
                SchemaConstants.SalesOrderItemRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new SalesOrderItemRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<SalesOrderLine>(
                "SalesOrderLine",
                SchemaConstants.SalesOrderLine,
                7,
                0,
                "_sales_order_line",
                DModelType.MODEL,
                () -> new SalesOrderLine())
            .creatable());
    addTable(
        new DModel<SalesOrdersByStoreRequest>(
                "SalesOrdersByStoreRequest",
                SchemaConstants.SalesOrdersByStoreRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new SalesOrdersByStoreRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<SalesReturn>(
                "SalesReturn",
                SchemaConstants.SalesReturn,
                11,
                0,
                "_sales_return",
                DModelType.MODEL,
                () -> new SalesReturn())
            .creatable());
    addTable(
        new DModel<SalesReturnLine>(
                "SalesReturnLine",
                SchemaConstants.SalesReturnLine,
                6,
                0,
                "_sales_return_line",
                DModelType.MODEL,
                () -> new SalesReturnLine())
            .creatable());
    addTable(
        new DModel<StockAlert>(
                "StockAlert",
                SchemaConstants.StockAlert,
                11,
                0,
                "_stock_alert",
                DModelType.MODEL,
                () -> new StockAlert())
            .creatable());
    addTable(
        new DModel<StockBatch>(
                "StockBatch",
                SchemaConstants.StockBatch,
                10,
                0,
                "_stock_batch",
                DModelType.MODEL,
                () -> new StockBatch())
            .creatable());
    addTable(
        new DModel<StockTransfer>(
                "StockTransfer",
                SchemaConstants.StockTransfer,
                12,
                0,
                "_stock_transfer",
                DModelType.MODEL,
                () -> new StockTransfer())
            .creatable());
    addTable(
        new DModel<StockTransferItemRequest>(
                "StockTransferItemRequest",
                SchemaConstants.StockTransferItemRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new StockTransferItemRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<StockTransferLine>(
                "StockTransferLine",
                SchemaConstants.StockTransferLine,
                5,
                0,
                "_stock_transfer_line",
                DModelType.MODEL,
                () -> new StockTransferLine())
            .creatable());
    addTable(
        new DModel<StockValuationReportRequest>(
                "StockValuationReportRequest",
                SchemaConstants.StockValuationReportRequest,
                2,
                0,
                "null",
                DModelType.MODEL,
                () -> new StockValuationReportRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<Store>(
                "Store",
                SchemaConstants.Store,
                13,
                0,
                "_store",
                DModelType.MODEL,
                () -> new Store())
            .creatable());
    addTable(
        new DModel<StoreItemRequest>(
                "StoreItemRequest",
                SchemaConstants.StoreItemRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new StoreItemRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<SupplierContact>(
                "SupplierContact",
                SchemaConstants.SupplierContact,
                5,
                0,
                "_supplier_contact",
                DModelType.MODEL,
                () -> new SupplierContact())
            .creatable());
    addTable(
        new DModel<SupplierItemRequest>(
                "SupplierItemRequest",
                SchemaConstants.SupplierItemRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new SupplierItemRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<UnitOfMeasure>(
                "UnitOfMeasure",
                SchemaConstants.UnitOfMeasure,
                6,
                0,
                "_unit_of_measure",
                DModelType.MODEL,
                () -> new UnitOfMeasure())
            .creatable());
    addTable(
        new DModel<UnreadNotificationCountRequest>(
                "UnreadNotificationCountRequest",
                SchemaConstants.UnreadNotificationCountRequest,
                2,
                0,
                "null",
                DModelType.MODEL,
                () -> new UnreadNotificationCountRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<User>(
                "User", SchemaConstants.User, 5, 3, "_user", DModelType.MODEL, () -> new User())
            .creatable());
    addTable(
        new DModel<UserDevice>(
                "UserDevice",
                SchemaConstants.UserDevice,
                2,
                0,
                "_user_device",
                DModelType.MODEL,
                () -> new UserDevice())
            .creatable());
    addTable(
        new DModel<UserDevicesRequest>(
                "UserDevicesRequest",
                SchemaConstants.UserDevicesRequest,
                2,
                0,
                "null",
                DModelType.MODEL,
                () -> new UserDevicesRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<UserInvitation>(
                "UserInvitation",
                SchemaConstants.UserInvitation,
                8,
                0,
                "_user_invitation",
                DModelType.MODEL,
                () -> new UserInvitation())
            .creatable());
    addTable(
        new DModel<UserLoginRecord>(
                "UserLoginRecord",
                SchemaConstants.UserLoginRecord,
                8,
                0,
                "_user_login_record",
                DModelType.MODEL,
                () -> new UserLoginRecord())
            .creatable());
    addTable(
        new DModel<UserProfile>(
                "UserProfile",
                SchemaConstants.UserProfile,
                10,
                0,
                "_user_profile",
                DModelType.MODEL,
                () -> new UserProfile())
            .creatable());
    addTable(
        new DModel<UserProfileByUserRequest>(
                "UserProfileByUserRequest",
                SchemaConstants.UserProfileByUserRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new UserProfileByUserRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<UserRole>(
                "UserRole",
                SchemaConstants.UserRole,
                6,
                0,
                "_user_role",
                DModelType.MODEL,
                () -> new UserRole())
            .creatable());
    addTable(
        new DModel<Vendor>(
                "Vendor",
                SchemaConstants.Vendor,
                13,
                0,
                "_vendor",
                DModelType.MODEL,
                () -> new Vendor())
            .creatable());
    addTable(
        new DModel<VerificationData>(
                "VerificationData",
                SchemaConstants.VerificationData,
                6,
                0,
                "_verification_data",
                DModelType.MODEL,
                () -> new VerificationData())
            .creatable());
    addTable(
        new DModel<VerificationDataByTokenRequest>(
                "VerificationDataByTokenRequest",
                SchemaConstants.VerificationDataByTokenRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new VerificationDataByTokenRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<Warehouse>(
                "Warehouse",
                SchemaConstants.Warehouse,
                10,
                0,
                "_warehouse",
                DModelType.MODEL,
                () -> new Warehouse())
            .creatable());
    addTable(
        new DModel<WarehouseStock>(
                "WarehouseStock",
                SchemaConstants.WarehouseStock,
                10,
                0,
                "_warehouse_stock",
                DModelType.MODEL,
                () -> new WarehouseStock())
            .creatable());
    addTable(
        new DModel<WarehouseStockByProductRequest>(
                "WarehouseStockByProductRequest",
                SchemaConstants.WarehouseStockByProductRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new WarehouseStockByProductRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<WarehouseStockByWarehouseRequest>(
                "WarehouseStockByWarehouseRequest",
                SchemaConstants.WarehouseStockByWarehouseRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new WarehouseStockByWarehouseRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<WarehousesByStoreRequest>(
                "WarehousesByStoreRequest",
                SchemaConstants.WarehousesByStoreRequest,
                1,
                0,
                "null",
                DModelType.MODEL,
                () -> new WarehousesByStoreRequest())
            .trans()
            .creatable());
    addTable(
        new DModel<ReportInput>(
            "ReportInput",
            SchemaConstants.ReportInput,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new ReportInput()));
    addTable(
        new DModel<ReportFilterValue>(
            "ReportFilterValue",
            SchemaConstants.ReportFilterValue,
            2,
            0,
            null,
            DModelType.STRUCT,
            () -> new ReportFilterValue()));
    addTable(
        new DModel<ReportOutput>(
            "ReportOutput",
            SchemaConstants.ReportOutput,
            5,
            0,
            null,
            DModelType.STRUCT,
            () -> new ReportOutput()));
    addTable(
        new DModel<ReportOutOption>(
            "ReportOutOption",
            SchemaConstants.ReportOutOption,
            2,
            0,
            null,
            DModelType.STRUCT,
            () -> new ReportOutOption()));
    addTable(
        new DModel<ReportOutColumn>(
            "ReportOutColumn",
            SchemaConstants.ReportOutColumn,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new ReportOutColumn()));
    addTable(
        new DModel<ReportOutAttribute>(
            "ReportOutAttribute",
            SchemaConstants.ReportOutAttribute,
            2,
            0,
            null,
            DModelType.STRUCT,
            () -> new ReportOutAttribute()));
    addTable(
        new DModel<ReportOutRow>(
            "ReportOutRow",
            SchemaConstants.ReportOutRow,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new ReportOutRow()));
    addTable(
        new DModel<ReportOutCell>(
            "ReportOutCell",
            SchemaConstants.ReportOutCell,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new ReportOutCell()));
    addTable(
        new DModel<LoginResult>(
            "LoginResult",
            SchemaConstants.LoginResult,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new LoginResult()));
    addTable(
        new DModel<AllDevices>(
            "AllDevices",
            SchemaConstants.AllDevices,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllDevices()));
    addTable(
        new DModel<UserDevices>(
            "UserDevices",
            SchemaConstants.UserDevices,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new UserDevices()));
    addTable(
        new DModel<VerificationDataByToken>(
            "VerificationDataByToken",
            SchemaConstants.VerificationDataByToken,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new VerificationDataByToken()));
    addTable(
        new DModel<AllAuditLogs>(
            "AllAuditLogs",
            SchemaConstants.AllAuditLogs,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllAuditLogs()));
    addTable(
        new DModel<AllGoodsReceipts>(
            "AllGoodsReceipts",
            SchemaConstants.AllGoodsReceipts,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllGoodsReceipts()));
    addTable(
        new DModel<AllInAppNotifications>(
            "AllInAppNotifications",
            SchemaConstants.AllInAppNotifications,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllInAppNotifications()));
    addTable(
        new DModel<AllInventoryAdjustments>(
            "AllInventoryAdjustments",
            SchemaConstants.AllInventoryAdjustments,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllInventoryAdjustments()));
    addTable(
        new DModel<AllInventoryMovements>(
            "AllInventoryMovements",
            SchemaConstants.AllInventoryMovements,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllInventoryMovements()));
    addTable(
        new DModel<AllOrganizations>(
            "AllOrganizations",
            SchemaConstants.AllOrganizations,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllOrganizations()));
    addTable(
        new DModel<AllProductCategories>(
            "AllProductCategories",
            SchemaConstants.AllProductCategories,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllProductCategories()));
    addTable(
        new DModel<AllProducts>(
            "AllProducts",
            SchemaConstants.AllProducts,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllProducts()));
    addTable(
        new DModel<AllPurchaseOrders>(
            "AllPurchaseOrders",
            SchemaConstants.AllPurchaseOrders,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllPurchaseOrders()));
    addTable(
        new DModel<AllSalesOrders>(
            "AllSalesOrders",
            SchemaConstants.AllSalesOrders,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllSalesOrders()));
    addTable(
        new DModel<AllSalesReturns>(
            "AllSalesReturns",
            SchemaConstants.AllSalesReturns,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllSalesReturns()));
    addTable(
        new DModel<AllStockAlerts>(
            "AllStockAlerts",
            SchemaConstants.AllStockAlerts,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllStockAlerts()));
    addTable(
        new DModel<AllStockBatches>(
            "AllStockBatches",
            SchemaConstants.AllStockBatches,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllStockBatches()));
    addTable(
        new DModel<AllStockTransfers>(
            "AllStockTransfers",
            SchemaConstants.AllStockTransfers,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllStockTransfers()));
    addTable(
        new DModel<AllStores>(
            "AllStores",
            SchemaConstants.AllStores,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllStores()));
    addTable(
        new DModel<AllSuppliers>(
            "AllSuppliers",
            SchemaConstants.AllSuppliers,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllSuppliers()));
    addTable(
        new DModel<AllUnitOfMeasures>(
            "AllUnitOfMeasures",
            SchemaConstants.AllUnitOfMeasures,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllUnitOfMeasures()));
    addTable(
        new DModel<AllUserInvitations>(
            "AllUserInvitations",
            SchemaConstants.AllUserInvitations,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllUserInvitations()));
    addTable(
        new DModel<AllUserProfiles>(
            "AllUserProfiles",
            SchemaConstants.AllUserProfiles,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllUserProfiles()));
    addTable(
        new DModel<AllWarehouses>(
            "AllWarehouses",
            SchemaConstants.AllWarehouses,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new AllWarehouses()));
    addTable(
        new DModel<DashboardMetrics>(
            "DashboardMetrics",
            SchemaConstants.DashboardMetrics,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new DashboardMetrics()));
    addTable(
        new DModel<ExpiringBatches>(
            "ExpiringBatches",
            SchemaConstants.ExpiringBatches,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new ExpiringBatches()));
    addTable(
        new DModel<GoodsReceiptItem>(
            "GoodsReceiptItem",
            SchemaConstants.GoodsReceiptItem,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new GoodsReceiptItem()));
    addTable(
        new DModel<InventoryMovementsByDateRange>(
            "InventoryMovementsByDateRange",
            SchemaConstants.InventoryMovementsByDateRange,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new InventoryMovementsByDateRange()));
    addTable(
        new DModel<LowStockItems>(
            "LowStockItems",
            SchemaConstants.LowStockItems,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new LowStockItems()));
    addTable(
        new DModel<MovementReportRows>(
            "MovementReportRows",
            SchemaConstants.MovementReportRows,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new MovementReportRows()));
    addTable(
        new DModel<OrganizationItem>(
            "OrganizationItem",
            SchemaConstants.OrganizationItem,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new OrganizationItem()));
    addTable(
        new DModel<OutOfStockItems>(
            "OutOfStockItems",
            SchemaConstants.OutOfStockItems,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new OutOfStockItems()));
    addTable(
        new DModel<ProductItem>(
            "ProductItem",
            SchemaConstants.ProductItem,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new ProductItem()));
    addTable(
        new DModel<ProductSearch>(
            "ProductSearch",
            SchemaConstants.ProductSearch,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new ProductSearch()));
    addTable(
        new DModel<ProductsByCategory>(
            "ProductsByCategory",
            SchemaConstants.ProductsByCategory,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new ProductsByCategory()));
    addTable(
        new DModel<PurchaseOrderItem>(
            "PurchaseOrderItem",
            SchemaConstants.PurchaseOrderItem,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new PurchaseOrderItem()));
    addTable(
        new DModel<PurchaseOrdersByStatus>(
            "PurchaseOrdersByStatus",
            SchemaConstants.PurchaseOrdersByStatus,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new PurchaseOrdersByStatus()));
    addTable(
        new DModel<SalesOrderItem>(
            "SalesOrderItem",
            SchemaConstants.SalesOrderItem,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new SalesOrderItem()));
    addTable(
        new DModel<SalesOrdersByStore>(
            "SalesOrdersByStore",
            SchemaConstants.SalesOrdersByStore,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new SalesOrdersByStore()));
    addTable(
        new DModel<StockTransferItem>(
            "StockTransferItem",
            SchemaConstants.StockTransferItem,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new StockTransferItem()));
    addTable(
        new DModel<StockValuationReport>(
            "StockValuationReport",
            SchemaConstants.StockValuationReport,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new StockValuationReport()));
    addTable(
        new DModel<StoreItem>(
            "StoreItem",
            SchemaConstants.StoreItem,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new StoreItem()));
    addTable(
        new DModel<SupplierItem>(
            "SupplierItem",
            SchemaConstants.SupplierItem,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new SupplierItem()));
    addTable(
        new DModel<UnreadNotificationCount>(
            "UnreadNotificationCount",
            SchemaConstants.UnreadNotificationCount,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new UnreadNotificationCount()));
    addTable(
        new DModel<UserProfileByUser>(
            "UserProfileByUser",
            SchemaConstants.UserProfileByUser,
            3,
            0,
            null,
            DModelType.STRUCT,
            () -> new UserProfileByUser()));
    addTable(
        new DModel<WarehouseStockByProduct>(
            "WarehouseStockByProduct",
            SchemaConstants.WarehouseStockByProduct,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new WarehouseStockByProduct()));
    addTable(
        new DModel<WarehouseStockByWarehouse>(
            "WarehouseStockByWarehouse",
            SchemaConstants.WarehouseStockByWarehouse,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new WarehouseStockByWarehouse()));
    addTable(
        new DModel<WarehousesByStore>(
            "WarehousesByStore",
            SchemaConstants.WarehousesByStore,
            4,
            0,
            null,
            DModelType.STRUCT,
            () -> new WarehousesByStore()));
    addDFileFields();
  }

  protected void addFields() {
    new ModelSchema1(allTypes).createAllTables();
    new ModelSchema2(allTypes).createAllTables();
    new StructSchema1(allTypes).createAllTables();
  }

  protected void recordAllChannels() {
    recordNumChannels(0);
  }

  protected void recordAllRPCs() {
    recordNumRPCs(22);
    DClazz InventoryAdjustmentService =
        addRPCClass("InventoryAdjustmentService", RPCConstants.InventoryAdjustmentService, 7);
    populateRPC(
        InventoryAdjustmentService,
        RPCConstants.InventoryAdjustmentServiceCreateInventoryAdjustment,
        "createInventoryAdjustment",
        SchemaConstants.InventoryAdjustment,
        new gqltosql.schema.DParam(SchemaConstants.InventoryAdjustment));
    populateRPC(
        InventoryAdjustmentService,
        RPCConstants.InventoryAdjustmentServiceUpdateInventoryAdjustment,
        "updateInventoryAdjustment",
        SchemaConstants.InventoryAdjustment,
        new gqltosql.schema.DParam(SchemaConstants.InventoryAdjustment));
    populateRPC(
        InventoryAdjustmentService,
        RPCConstants.InventoryAdjustmentServiceDeleteInventoryAdjustment,
        "deleteInventoryAdjustment",
        new gqltosql.schema.DParam(SchemaConstants.InventoryAdjustment));
    populateRPC(
        InventoryAdjustmentService,
        RPCConstants.InventoryAdjustmentServiceConfirmInventoryAdjustment,
        "confirmInventoryAdjustment",
        SchemaConstants.InventoryAdjustment,
        new gqltosql.schema.DParam(SchemaConstants.InventoryAdjustment));
    populateRPC(
        InventoryAdjustmentService,
        RPCConstants.InventoryAdjustmentServiceCreateInventoryAdjustmentLine,
        "createInventoryAdjustmentLine",
        SchemaConstants.InventoryAdjustmentLine,
        new gqltosql.schema.DParam(SchemaConstants.InventoryAdjustmentLine));
    populateRPC(
        InventoryAdjustmentService,
        RPCConstants.InventoryAdjustmentServiceUpdateInventoryAdjustmentLine,
        "updateInventoryAdjustmentLine",
        SchemaConstants.InventoryAdjustmentLine,
        new gqltosql.schema.DParam(SchemaConstants.InventoryAdjustmentLine));
    populateRPC(
        InventoryAdjustmentService,
        RPCConstants.InventoryAdjustmentServiceDeleteInventoryAdjustmentLine,
        "deleteInventoryAdjustmentLine",
        new gqltosql.schema.DParam(SchemaConstants.InventoryAdjustmentLine));
    DClazz InventoryMovementService =
        addRPCClass("InventoryMovementService", RPCConstants.InventoryMovementService, 3);
    populateRPC(
        InventoryMovementService,
        RPCConstants.InventoryMovementServiceCreateInventoryMovement,
        "createInventoryMovement",
        SchemaConstants.InventoryMovement,
        new gqltosql.schema.DParam(SchemaConstants.InventoryMovement));
    populateRPC(
        InventoryMovementService,
        RPCConstants.InventoryMovementServiceUpdateInventoryMovement,
        "updateInventoryMovement",
        SchemaConstants.InventoryMovement,
        new gqltosql.schema.DParam(SchemaConstants.InventoryMovement));
    populateRPC(
        InventoryMovementService,
        RPCConstants.InventoryMovementServiceDeleteInventoryMovement,
        "deleteInventoryMovement",
        new gqltosql.schema.DParam(SchemaConstants.InventoryMovement));
    DClazz NotificationService =
        addRPCClass("NotificationService", RPCConstants.NotificationService, 6);
    populateRPC(
        NotificationService,
        RPCConstants.NotificationServiceCreateInAppNotification,
        "createInAppNotification",
        SchemaConstants.InAppNotification,
        new gqltosql.schema.DParam(SchemaConstants.InAppNotification));
    populateRPC(
        NotificationService,
        RPCConstants.NotificationServiceUpdateInAppNotification,
        "updateInAppNotification",
        SchemaConstants.InAppNotification,
        new gqltosql.schema.DParam(SchemaConstants.InAppNotification));
    populateRPC(
        NotificationService,
        RPCConstants.NotificationServiceDeleteInAppNotification,
        "deleteInAppNotification",
        new gqltosql.schema.DParam(SchemaConstants.InAppNotification));
    populateRPC(
        NotificationService,
        RPCConstants.NotificationServiceCreateNotificationTemplate,
        "createNotificationTemplate",
        SchemaConstants.NotificationTemplate,
        new gqltosql.schema.DParam(SchemaConstants.NotificationTemplate));
    populateRPC(
        NotificationService,
        RPCConstants.NotificationServiceUpdateNotificationTemplate,
        "updateNotificationTemplate",
        SchemaConstants.NotificationTemplate,
        new gqltosql.schema.DParam(SchemaConstants.NotificationTemplate));
    populateRPC(
        NotificationService,
        RPCConstants.NotificationServiceDeleteNotificationTemplate,
        "deleteNotificationTemplate",
        new gqltosql.schema.DParam(SchemaConstants.NotificationTemplate));
    DClazz OrganizationService =
        addRPCClass("OrganizationService", RPCConstants.OrganizationService, 3);
    populateRPC(
        OrganizationService,
        RPCConstants.OrganizationServiceCreateOrganization,
        "createOrganization",
        SchemaConstants.Organization,
        new gqltosql.schema.DParam(SchemaConstants.Organization));
    populateRPC(
        OrganizationService,
        RPCConstants.OrganizationServiceUpdateOrganization,
        "updateOrganization",
        SchemaConstants.Organization,
        new gqltosql.schema.DParam(SchemaConstants.Organization));
    populateRPC(
        OrganizationService,
        RPCConstants.OrganizationServiceDeleteOrganization,
        "deleteOrganization",
        new gqltosql.schema.DParam(SchemaConstants.Organization));
    DClazz StockBatchService = addRPCClass("StockBatchService", RPCConstants.StockBatchService, 3);
    populateRPC(
        StockBatchService,
        RPCConstants.StockBatchServiceCreateStockBatch,
        "createStockBatch",
        SchemaConstants.StockBatch,
        new gqltosql.schema.DParam(SchemaConstants.StockBatch));
    populateRPC(
        StockBatchService,
        RPCConstants.StockBatchServiceUpdateStockBatch,
        "updateStockBatch",
        SchemaConstants.StockBatch,
        new gqltosql.schema.DParam(SchemaConstants.StockBatch));
    populateRPC(
        StockBatchService,
        RPCConstants.StockBatchServiceDeleteStockBatch,
        "deleteStockBatch",
        new gqltosql.schema.DParam(SchemaConstants.StockBatch));
    DClazz ProductCategoryService =
        addRPCClass("ProductCategoryService", RPCConstants.ProductCategoryService, 3);
    populateRPC(
        ProductCategoryService,
        RPCConstants.ProductCategoryServiceCreateProductCategory,
        "createProductCategory",
        SchemaConstants.ProductCategory,
        new gqltosql.schema.DParam(SchemaConstants.ProductCategory));
    populateRPC(
        ProductCategoryService,
        RPCConstants.ProductCategoryServiceUpdateProductCategory,
        "updateProductCategory",
        SchemaConstants.ProductCategory,
        new gqltosql.schema.DParam(SchemaConstants.ProductCategory));
    populateRPC(
        ProductCategoryService,
        RPCConstants.ProductCategoryServiceDeleteProductCategory,
        "deleteProductCategory",
        new gqltosql.schema.DParam(SchemaConstants.ProductCategory));
    DClazz ProductService = addRPCClass("ProductService", RPCConstants.ProductService, 3);
    populateRPC(
        ProductService,
        RPCConstants.ProductServiceCreateProduct,
        "createProduct",
        SchemaConstants.Product,
        new gqltosql.schema.DParam(SchemaConstants.Product));
    populateRPC(
        ProductService,
        RPCConstants.ProductServiceUpdateProduct,
        "updateProduct",
        SchemaConstants.Product,
        new gqltosql.schema.DParam(SchemaConstants.Product));
    populateRPC(
        ProductService,
        RPCConstants.ProductServiceDeleteProduct,
        "deleteProduct",
        new gqltosql.schema.DParam(SchemaConstants.Product));
    DClazz AuditLogService = addRPCClass("AuditLogService", RPCConstants.AuditLogService, 3);
    populateRPC(
        AuditLogService,
        RPCConstants.AuditLogServiceCreateAuditLog,
        "createAuditLog",
        SchemaConstants.AuditLog,
        new gqltosql.schema.DParam(SchemaConstants.AuditLog));
    populateRPC(
        AuditLogService,
        RPCConstants.AuditLogServiceUpdateAuditLog,
        "updateAuditLog",
        SchemaConstants.AuditLog,
        new gqltosql.schema.DParam(SchemaConstants.AuditLog));
    populateRPC(
        AuditLogService,
        RPCConstants.AuditLogServiceDeleteAuditLog,
        "deleteAuditLog",
        new gqltosql.schema.DParam(SchemaConstants.AuditLog));
    DClazz WarehouseService = addRPCClass("WarehouseService", RPCConstants.WarehouseService, 3);
    populateRPC(
        WarehouseService,
        RPCConstants.WarehouseServiceCreateWarehouse,
        "createWarehouse",
        SchemaConstants.Warehouse,
        new gqltosql.schema.DParam(SchemaConstants.Warehouse));
    populateRPC(
        WarehouseService,
        RPCConstants.WarehouseServiceUpdateWarehouse,
        "updateWarehouse",
        SchemaConstants.Warehouse,
        new gqltosql.schema.DParam(SchemaConstants.Warehouse));
    populateRPC(
        WarehouseService,
        RPCConstants.WarehouseServiceDeleteWarehouse,
        "deleteWarehouse",
        new gqltosql.schema.DParam(SchemaConstants.Warehouse));
    DClazz WarehouseStockService =
        addRPCClass("WarehouseStockService", RPCConstants.WarehouseStockService, 3);
    populateRPC(
        WarehouseStockService,
        RPCConstants.WarehouseStockServiceCreateWarehouseStock,
        "createWarehouseStock",
        SchemaConstants.WarehouseStock,
        new gqltosql.schema.DParam(SchemaConstants.WarehouseStock));
    populateRPC(
        WarehouseStockService,
        RPCConstants.WarehouseStockServiceUpdateWarehouseStock,
        "updateWarehouseStock",
        SchemaConstants.WarehouseStock,
        new gqltosql.schema.DParam(SchemaConstants.WarehouseStock));
    populateRPC(
        WarehouseStockService,
        RPCConstants.WarehouseStockServiceDeleteWarehouseStock,
        "deleteWarehouseStock",
        new gqltosql.schema.DParam(SchemaConstants.WarehouseStock));
    DClazz SalesOrderService = addRPCClass("SalesOrderService", RPCConstants.SalesOrderService, 8);
    populateRPC(
        SalesOrderService,
        RPCConstants.SalesOrderServiceCreateSalesOrder,
        "createSalesOrder",
        SchemaConstants.SalesOrder,
        new gqltosql.schema.DParam(SchemaConstants.SalesOrder));
    populateRPC(
        SalesOrderService,
        RPCConstants.SalesOrderServiceUpdateSalesOrder,
        "updateSalesOrder",
        SchemaConstants.SalesOrder,
        new gqltosql.schema.DParam(SchemaConstants.SalesOrder));
    populateRPC(
        SalesOrderService,
        RPCConstants.SalesOrderServiceDeleteSalesOrder,
        "deleteSalesOrder",
        new gqltosql.schema.DParam(SchemaConstants.SalesOrder));
    populateRPC(
        SalesOrderService,
        RPCConstants.SalesOrderServiceConfirmSalesOrder,
        "confirmSalesOrder",
        SchemaConstants.SalesOrder,
        new gqltosql.schema.DParam(SchemaConstants.SalesOrder));
    populateRPC(
        SalesOrderService,
        RPCConstants.SalesOrderServiceCancelSalesOrder,
        "cancelSalesOrder",
        SchemaConstants.SalesOrder,
        new gqltosql.schema.DParam(SchemaConstants.SalesOrder));
    populateRPC(
        SalesOrderService,
        RPCConstants.SalesOrderServiceCreateSalesOrderLine,
        "createSalesOrderLine",
        SchemaConstants.SalesOrderLine,
        new gqltosql.schema.DParam(SchemaConstants.SalesOrderLine));
    populateRPC(
        SalesOrderService,
        RPCConstants.SalesOrderServiceUpdateSalesOrderLine,
        "updateSalesOrderLine",
        SchemaConstants.SalesOrderLine,
        new gqltosql.schema.DParam(SchemaConstants.SalesOrderLine));
    populateRPC(
        SalesOrderService,
        RPCConstants.SalesOrderServiceDeleteSalesOrderLine,
        "deleteSalesOrderLine",
        new gqltosql.schema.DParam(SchemaConstants.SalesOrderLine));
    DClazz UnitOfMeasureService =
        addRPCClass("UnitOfMeasureService", RPCConstants.UnitOfMeasureService, 3);
    populateRPC(
        UnitOfMeasureService,
        RPCConstants.UnitOfMeasureServiceCreateUnitOfMeasure,
        "createUnitOfMeasure",
        SchemaConstants.UnitOfMeasure,
        new gqltosql.schema.DParam(SchemaConstants.UnitOfMeasure));
    populateRPC(
        UnitOfMeasureService,
        RPCConstants.UnitOfMeasureServiceUpdateUnitOfMeasure,
        "updateUnitOfMeasure",
        SchemaConstants.UnitOfMeasure,
        new gqltosql.schema.DParam(SchemaConstants.UnitOfMeasure));
    populateRPC(
        UnitOfMeasureService,
        RPCConstants.UnitOfMeasureServiceDeleteUnitOfMeasure,
        "deleteUnitOfMeasure",
        new gqltosql.schema.DParam(SchemaConstants.UnitOfMeasure));
    DClazz UserManagementService =
        addRPCClass("UserManagementService", RPCConstants.UserManagementService, 12);
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceCreateUser,
        "createUser",
        SchemaConstants.User,
        new gqltosql.schema.DParam(SchemaConstants.User));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceUpdateUser,
        "updateUser",
        SchemaConstants.User,
        new gqltosql.schema.DParam(SchemaConstants.User));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceDeleteUser,
        "deleteUser",
        new gqltosql.schema.DParam(SchemaConstants.User));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceCreateUserProfile,
        "createUserProfile",
        SchemaConstants.UserProfile,
        new gqltosql.schema.DParam(SchemaConstants.UserProfile));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceUpdateUserProfile,
        "updateUserProfile",
        SchemaConstants.UserProfile,
        new gqltosql.schema.DParam(SchemaConstants.UserProfile));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceDeleteUserProfile,
        "deleteUserProfile",
        new gqltosql.schema.DParam(SchemaConstants.UserProfile));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceCreateUserInvitation,
        "createUserInvitation",
        SchemaConstants.UserInvitation,
        new gqltosql.schema.DParam(SchemaConstants.UserInvitation));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceUpdateUserInvitation,
        "updateUserInvitation",
        SchemaConstants.UserInvitation,
        new gqltosql.schema.DParam(SchemaConstants.UserInvitation));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceDeleteUserInvitation,
        "deleteUserInvitation",
        new gqltosql.schema.DParam(SchemaConstants.UserInvitation));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceCreateUserRole,
        "createUserRole",
        SchemaConstants.UserRole,
        new gqltosql.schema.DParam(SchemaConstants.UserRole));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceUpdateUserRole,
        "updateUserRole",
        SchemaConstants.UserRole,
        new gqltosql.schema.DParam(SchemaConstants.UserRole));
    populateRPC(
        UserManagementService,
        RPCConstants.UserManagementServiceDeleteUserRole,
        "deleteUserRole",
        new gqltosql.schema.DParam(SchemaConstants.UserRole));
    DClazz StockTransferService =
        addRPCClass("StockTransferService", RPCConstants.StockTransferService, 9);
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceCreateStockTransfer,
        "createStockTransfer",
        SchemaConstants.StockTransfer,
        new gqltosql.schema.DParam(SchemaConstants.StockTransfer));
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceUpdateStockTransfer,
        "updateStockTransfer",
        SchemaConstants.StockTransfer,
        new gqltosql.schema.DParam(SchemaConstants.StockTransfer));
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceDeleteStockTransfer,
        "deleteStockTransfer",
        new gqltosql.schema.DParam(SchemaConstants.StockTransfer));
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceSubmitStockTransfer,
        "submitStockTransfer",
        SchemaConstants.StockTransfer,
        new gqltosql.schema.DParam(SchemaConstants.StockTransfer));
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceCompleteStockTransfer,
        "completeStockTransfer",
        SchemaConstants.StockTransfer,
        new gqltosql.schema.DParam(SchemaConstants.StockTransfer));
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceCancelStockTransfer,
        "cancelStockTransfer",
        SchemaConstants.StockTransfer,
        new gqltosql.schema.DParam(SchemaConstants.StockTransfer));
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceCreateStockTransferLine,
        "createStockTransferLine",
        SchemaConstants.StockTransferLine,
        new gqltosql.schema.DParam(SchemaConstants.StockTransferLine));
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceUpdateStockTransferLine,
        "updateStockTransferLine",
        SchemaConstants.StockTransferLine,
        new gqltosql.schema.DParam(SchemaConstants.StockTransferLine));
    populateRPC(
        StockTransferService,
        RPCConstants.StockTransferServiceDeleteStockTransferLine,
        "deleteStockTransferLine",
        new gqltosql.schema.DParam(SchemaConstants.StockTransferLine));
    DClazz StoreService = addRPCClass("StoreService", RPCConstants.StoreService, 3);
    populateRPC(
        StoreService,
        RPCConstants.StoreServiceCreateStore,
        "createStore",
        SchemaConstants.Store,
        new gqltosql.schema.DParam(SchemaConstants.Store));
    populateRPC(
        StoreService,
        RPCConstants.StoreServiceUpdateStore,
        "updateStore",
        SchemaConstants.Store,
        new gqltosql.schema.DParam(SchemaConstants.Store));
    populateRPC(
        StoreService,
        RPCConstants.StoreServiceDeleteStore,
        "deleteStore",
        new gqltosql.schema.DParam(SchemaConstants.Store));
    DClazz SalesReturnService =
        addRPCClass("SalesReturnService", RPCConstants.SalesReturnService, 7);
    populateRPC(
        SalesReturnService,
        RPCConstants.SalesReturnServiceCreateSalesReturn,
        "createSalesReturn",
        SchemaConstants.SalesReturn,
        new gqltosql.schema.DParam(SchemaConstants.SalesReturn));
    populateRPC(
        SalesReturnService,
        RPCConstants.SalesReturnServiceUpdateSalesReturn,
        "updateSalesReturn",
        SchemaConstants.SalesReturn,
        new gqltosql.schema.DParam(SchemaConstants.SalesReturn));
    populateRPC(
        SalesReturnService,
        RPCConstants.SalesReturnServiceDeleteSalesReturn,
        "deleteSalesReturn",
        new gqltosql.schema.DParam(SchemaConstants.SalesReturn));
    populateRPC(
        SalesReturnService,
        RPCConstants.SalesReturnServiceConfirmSalesReturn,
        "confirmSalesReturn",
        SchemaConstants.SalesReturn,
        new gqltosql.schema.DParam(SchemaConstants.SalesReturn));
    populateRPC(
        SalesReturnService,
        RPCConstants.SalesReturnServiceCreateSalesReturnLine,
        "createSalesReturnLine",
        SchemaConstants.SalesReturnLine,
        new gqltosql.schema.DParam(SchemaConstants.SalesReturnLine));
    populateRPC(
        SalesReturnService,
        RPCConstants.SalesReturnServiceUpdateSalesReturnLine,
        "updateSalesReturnLine",
        SchemaConstants.SalesReturnLine,
        new gqltosql.schema.DParam(SchemaConstants.SalesReturnLine));
    populateRPC(
        SalesReturnService,
        RPCConstants.SalesReturnServiceDeleteSalesReturnLine,
        "deleteSalesReturnLine",
        new gqltosql.schema.DParam(SchemaConstants.SalesReturnLine));
    DClazz PurchaseOrderService =
        addRPCClass("PurchaseOrderService", RPCConstants.PurchaseOrderService, 8);
    populateRPC(
        PurchaseOrderService,
        RPCConstants.PurchaseOrderServiceCreatePurchaseOrder,
        "createPurchaseOrder",
        SchemaConstants.PurchaseOrder,
        new gqltosql.schema.DParam(SchemaConstants.PurchaseOrder));
    populateRPC(
        PurchaseOrderService,
        RPCConstants.PurchaseOrderServiceUpdatePurchaseOrder,
        "updatePurchaseOrder",
        SchemaConstants.PurchaseOrder,
        new gqltosql.schema.DParam(SchemaConstants.PurchaseOrder));
    populateRPC(
        PurchaseOrderService,
        RPCConstants.PurchaseOrderServiceDeletePurchaseOrder,
        "deletePurchaseOrder",
        new gqltosql.schema.DParam(SchemaConstants.PurchaseOrder));
    populateRPC(
        PurchaseOrderService,
        RPCConstants.PurchaseOrderServiceSubmitPurchaseOrder,
        "submitPurchaseOrder",
        SchemaConstants.PurchaseOrder,
        new gqltosql.schema.DParam(SchemaConstants.PurchaseOrder));
    populateRPC(
        PurchaseOrderService,
        RPCConstants.PurchaseOrderServiceCancelPurchaseOrder,
        "cancelPurchaseOrder",
        SchemaConstants.PurchaseOrder,
        new gqltosql.schema.DParam(SchemaConstants.PurchaseOrder));
    populateRPC(
        PurchaseOrderService,
        RPCConstants.PurchaseOrderServiceCreatePurchaseOrderLine,
        "createPurchaseOrderLine",
        SchemaConstants.PurchaseOrderLine,
        new gqltosql.schema.DParam(SchemaConstants.PurchaseOrderLine));
    populateRPC(
        PurchaseOrderService,
        RPCConstants.PurchaseOrderServiceUpdatePurchaseOrderLine,
        "updatePurchaseOrderLine",
        SchemaConstants.PurchaseOrderLine,
        new gqltosql.schema.DParam(SchemaConstants.PurchaseOrderLine));
    populateRPC(
        PurchaseOrderService,
        RPCConstants.PurchaseOrderServiceDeletePurchaseOrderLine,
        "deletePurchaseOrderLine",
        new gqltosql.schema.DParam(SchemaConstants.PurchaseOrderLine));
    DClazz StockAlertService = addRPCClass("StockAlertService", RPCConstants.StockAlertService, 3);
    populateRPC(
        StockAlertService,
        RPCConstants.StockAlertServiceCreateStockAlert,
        "createStockAlert",
        SchemaConstants.StockAlert,
        new gqltosql.schema.DParam(SchemaConstants.StockAlert));
    populateRPC(
        StockAlertService,
        RPCConstants.StockAlertServiceUpdateStockAlert,
        "updateStockAlert",
        SchemaConstants.StockAlert,
        new gqltosql.schema.DParam(SchemaConstants.StockAlert));
    populateRPC(
        StockAlertService,
        RPCConstants.StockAlertServiceDeleteStockAlert,
        "deleteStockAlert",
        new gqltosql.schema.DParam(SchemaConstants.StockAlert));
    DClazz GoodsReceiptService =
        addRPCClass("GoodsReceiptService", RPCConstants.GoodsReceiptService, 7);
    populateRPC(
        GoodsReceiptService,
        RPCConstants.GoodsReceiptServiceCreateGoodsReceipt,
        "createGoodsReceipt",
        SchemaConstants.GoodsReceipt,
        new gqltosql.schema.DParam(SchemaConstants.GoodsReceipt));
    populateRPC(
        GoodsReceiptService,
        RPCConstants.GoodsReceiptServiceUpdateGoodsReceipt,
        "updateGoodsReceipt",
        SchemaConstants.GoodsReceipt,
        new gqltosql.schema.DParam(SchemaConstants.GoodsReceipt));
    populateRPC(
        GoodsReceiptService,
        RPCConstants.GoodsReceiptServiceDeleteGoodsReceipt,
        "deleteGoodsReceipt",
        new gqltosql.schema.DParam(SchemaConstants.GoodsReceipt));
    populateRPC(
        GoodsReceiptService,
        RPCConstants.GoodsReceiptServiceConfirmGoodsReceipt,
        "confirmGoodsReceipt",
        SchemaConstants.GoodsReceipt,
        new gqltosql.schema.DParam(SchemaConstants.GoodsReceipt));
    populateRPC(
        GoodsReceiptService,
        RPCConstants.GoodsReceiptServiceCreateGoodsReceiptLine,
        "createGoodsReceiptLine",
        SchemaConstants.GoodsReceiptLine,
        new gqltosql.schema.DParam(SchemaConstants.GoodsReceiptLine));
    populateRPC(
        GoodsReceiptService,
        RPCConstants.GoodsReceiptServiceUpdateGoodsReceiptLine,
        "updateGoodsReceiptLine",
        SchemaConstants.GoodsReceiptLine,
        new gqltosql.schema.DParam(SchemaConstants.GoodsReceiptLine));
    populateRPC(
        GoodsReceiptService,
        RPCConstants.GoodsReceiptServiceDeleteGoodsReceiptLine,
        "deleteGoodsReceiptLine",
        new gqltosql.schema.DParam(SchemaConstants.GoodsReceiptLine));
    DClazz SupplierService = addRPCClass("SupplierService", RPCConstants.SupplierService, 6);
    populateRPC(
        SupplierService,
        RPCConstants.SupplierServiceCreateSupplier,
        "createSupplier",
        SchemaConstants.Vendor,
        new gqltosql.schema.DParam(SchemaConstants.Vendor));
    populateRPC(
        SupplierService,
        RPCConstants.SupplierServiceUpdateSupplier,
        "updateSupplier",
        SchemaConstants.Vendor,
        new gqltosql.schema.DParam(SchemaConstants.Vendor));
    populateRPC(
        SupplierService,
        RPCConstants.SupplierServiceDeleteSupplier,
        "deleteSupplier",
        new gqltosql.schema.DParam(SchemaConstants.Vendor));
    populateRPC(
        SupplierService,
        RPCConstants.SupplierServiceCreateSupplierContact,
        "createSupplierContact",
        SchemaConstants.SupplierContact,
        new gqltosql.schema.DParam(SchemaConstants.SupplierContact));
    populateRPC(
        SupplierService,
        RPCConstants.SupplierServiceUpdateSupplierContact,
        "updateSupplierContact",
        SchemaConstants.SupplierContact,
        new gqltosql.schema.DParam(SchemaConstants.SupplierContact));
    populateRPC(
        SupplierService,
        RPCConstants.SupplierServiceDeleteSupplierContact,
        "deleteSupplierContact",
        new gqltosql.schema.DParam(SchemaConstants.SupplierContact));
    DClazz UniqueChecker = addRPCClass("UniqueChecker", RPCConstants.UniqueChecker, 7);
    populateRPC(
        UniqueChecker,
        RPCConstants.UniqueCheckerCheckTokenUniqueInOneTimePassword,
        "checkTokenUniqueInOneTimePassword",
        SchemaConstants.Boolean,
        new gqltosql.schema.DParam(SchemaConstants.OneTimePassword),
        new gqltosql.schema.DParam(SchemaConstants.String));
    populateRPC(
        UniqueChecker,
        RPCConstants.UniqueCheckerCheckNameUniqueInOrganization,
        "checkNameUniqueInOrganization",
        SchemaConstants.Boolean,
        new gqltosql.schema.DParam(SchemaConstants.Organization),
        new gqltosql.schema.DParam(SchemaConstants.String));
    populateRPC(
        UniqueChecker,
        RPCConstants.UniqueCheckerCheckCodeUniqueInOrganization,
        "checkCodeUniqueInOrganization",
        SchemaConstants.Boolean,
        new gqltosql.schema.DParam(SchemaConstants.Organization),
        new gqltosql.schema.DParam(SchemaConstants.String));
    populateRPC(
        UniqueChecker,
        RPCConstants.UniqueCheckerCheckUserEmailUniqueInOrganization,
        "checkUserEmailUniqueInOrganization",
        SchemaConstants.Boolean,
        new gqltosql.schema.DParam(SchemaConstants.Organization),
        new gqltosql.schema.DParam(SchemaConstants.User),
        new gqltosql.schema.DParam(SchemaConstants.String));
    populateRPC(
        UniqueChecker,
        RPCConstants.UniqueCheckerCheckUserPasswordUniqueInOrganization,
        "checkUserPasswordUniqueInOrganization",
        SchemaConstants.Boolean,
        new gqltosql.schema.DParam(SchemaConstants.Organization),
        new gqltosql.schema.DParam(SchemaConstants.User),
        new gqltosql.schema.DParam(SchemaConstants.String));
    populateRPC(
        UniqueChecker,
        RPCConstants.UniqueCheckerCheckUserInvitationTokenUniqueInOrganization,
        "checkUserInvitationTokenUniqueInOrganization",
        SchemaConstants.Boolean,
        new gqltosql.schema.DParam(SchemaConstants.Organization),
        new gqltosql.schema.DParam(SchemaConstants.UserInvitation),
        new gqltosql.schema.DParam(SchemaConstants.String));
    populateRPC(
        UniqueChecker,
        RPCConstants.UniqueCheckerCheckUserProfileUserUniqueInOrganization,
        "checkUserProfileUserUniqueInOrganization",
        SchemaConstants.Boolean,
        new gqltosql.schema.DParam(SchemaConstants.Organization),
        new gqltosql.schema.DParam(SchemaConstants.UserProfile),
        new gqltosql.schema.DParam(SchemaConstants.User));
    DClazz FileService = addRPCClass("FileService", RPCConstants.FileService, 1);
    populateRPC(
        FileService,
        RPCConstants.FileServiceCreateTempFile,
        "createTempFile",
        SchemaConstants.DFile,
        new gqltosql.schema.DParam(SchemaConstants.String),
        new gqltosql.schema.DParam(SchemaConstants.Boolean),
        new gqltosql.schema.DParam(SchemaConstants.String));
  }
}
