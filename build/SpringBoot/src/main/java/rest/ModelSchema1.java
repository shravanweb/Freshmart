package rest;

import d3e.core.SchemaConstants;
import gqltosql.schema.DModel;
import gqltosql.schema.FieldPrimitiveType;
import java.util.HashMap;
import java.util.Map;
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

public class ModelSchema1 {
  private Map<String, DModel<?>> allTypes = new HashMap<>();

  public ModelSchema1(Map<String, DModel<?>> allTypes) {
    this.allTypes = allTypes;
  }

  public void createAllTables() {
    addAllAuditLogsRequestFields();
    addAllDevicesRequestFields();
    addAllGoodsReceiptsRequestFields();
    addAllInAppNotificationsRequestFields();
    addAllInventoryAdjustmentsRequestFields();
    addAllInventoryMovementsRequestFields();
    addAllProductCategoriesRequestFields();
    addAllProductsRequestFields();
    addAllPurchaseOrdersRequestFields();
    addAllSalesOrdersRequestFields();
    addAllSalesReturnsRequestFields();
    addAllStockAlertsRequestFields();
    addAllStockBatchesRequestFields();
    addAllStockTransfersRequestFields();
    addAllStoresRequestFields();
    addAllSuppliersRequestFields();
    addAllUnitOfMeasuresRequestFields();
    addAllUserInvitationsRequestFields();
    addAllUserProfilesRequestFields();
    addAllWarehousesRequestFields();
    addAnonymousUserFields();
    addAuditLogFields();
    addAvatarFields();
    addBaseUserFields();
    addBaseUserSessionFields();
    addChangePasswordRequestFields();
    addD3EImageFields();
    addDashboardMetricsRequestFields();
    addEmailMessageFields();
    addExpiringBatchesRequestFields();
    addGoodsReceiptFields();
    addGoodsReceiptItemRequestFields();
    addGoodsReceiptLineFields();
    addInAppNotificationFields();
    addInventoryAdjustmentFields();
    addInventoryAdjustmentLineFields();
    addInventoryMovementFields();
    addInventoryMovementsByDateRangeRequestFields();
    addLowStockItemsRequestFields();
    addMovementReportRowsRequestFields();
    addNotificationTemplateFields();
    addOneTimePasswordFields();
    addOrganizationFields();
    addOrganizationItemRequestFields();
    addOutOfStockItemsRequestFields();
    addProductFields();
    addProductCategoryFields();
    addProductItemRequestFields();
    addProductSearchRequestFields();
    addProductsByCategoryRequestFields();
    addPurchaseOrderFields();
    addPurchaseOrderItemRequestFields();
    addPurchaseOrderLineFields();
    addPurchaseOrdersByStatusRequestFields();
    addPushNotificationFields();
    addReportFields();
    addReportBarChartConfigFields();
    addReportBaseConfigFields();
    addReportCardConfigFields();
    addReportCellFields();
    addReportCellStyleFields();
    addReportConfigFields();
    addReportConfigOptionFields();
    addReportDataFields();
    addReportDataRowFields();
    addReportDataSectionFields();
    addReportFieldFields();
    addReportFilterFields();
    addReportFunnelChartConfigFields();
    addReportGuageConfigFields();
    addReportKPIConfigFields();
    addReportKeyInfluencerConfigFields();
    addReportLineAndAreaChartConfigFields();
    addReportLineAndColumnChartConfigFields();
    addReportMapConfigFields();
    addReportMatrixConfigFields();
    addReportModelFields();
    addReportMultiRowCardConfigFields();
    addReportNamedConditionFields();
    addReportNamedConditionFilterFields();
    addReportPieChartConfigFields();
    addReportPropertyFields();
    addReportPropertyFilterFields();
    addReportRuleFields();
    addReportRuleSetFields();
    addReportScatterChartConfigFields();
    addReportSingleRuleFields();
    addReportSlicerConfigFields();
    addReportTableConfigFields();
    addReportWaterfallChartConfigFields();
    addSMSMessageFields();
    addSalesOrderFields();
    addSalesOrderItemRequestFields();
    addSalesOrderLineFields();
    addSalesOrdersByStoreRequestFields();
    addSalesReturnFields();
    addSalesReturnLineFields();
    addStockAlertFields();
    addStockBatchFields();
  }

  public DModel<?> getType(String type) {
    return allTypes.get(type);
  }

  public <T> DModel<T> getType2(String type) {
    return ((DModel<T>) allTypes.get(type));
  }

  private void addAllAuditLogsRequestFields() {
    DModel<AllAuditLogsRequest> m = getType2("AllAuditLogsRequest");
    m.addReference(
        "organization",
        AllAuditLogsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllDevicesRequestFields() {
    DModel<AllDevicesRequest> m = getType2("AllDevicesRequest");
    m.addReferenceCollection(
        "users",
        AllDevicesRequest._USERS,
        "null_id",
        "null",
        false,
        getType("BaseUser"),
        (s) -> s.getUsers(),
        (s, v) -> s.setUsers(v));
  }

  private void addAllGoodsReceiptsRequestFields() {
    DModel<AllGoodsReceiptsRequest> m = getType2("AllGoodsReceiptsRequest");
    m.addReference(
        "organization",
        AllGoodsReceiptsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllInAppNotificationsRequestFields() {
    DModel<AllInAppNotificationsRequest> m = getType2("AllInAppNotificationsRequest");
    m.addReference(
        "organization",
        AllInAppNotificationsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addReference(
        "user",
        AllInAppNotificationsRequest._USER,
        "null_id",
        false,
        getType("User"),
        (s) -> s.getUser(),
        (s, v) -> s.setUser(v));
  }

  private void addAllInventoryAdjustmentsRequestFields() {
    DModel<AllInventoryAdjustmentsRequest> m = getType2("AllInventoryAdjustmentsRequest");
    m.addReference(
        "organization",
        AllInventoryAdjustmentsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllInventoryMovementsRequestFields() {
    DModel<AllInventoryMovementsRequest> m = getType2("AllInventoryMovementsRequest");
    m.addReference(
        "organization",
        AllInventoryMovementsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllProductCategoriesRequestFields() {
    DModel<AllProductCategoriesRequest> m = getType2("AllProductCategoriesRequest");
    m.addReference(
        "organization",
        AllProductCategoriesRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllProductsRequestFields() {
    DModel<AllProductsRequest> m = getType2("AllProductsRequest");
    m.addReference(
        "organization",
        AllProductsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllPurchaseOrdersRequestFields() {
    DModel<AllPurchaseOrdersRequest> m = getType2("AllPurchaseOrdersRequest");
    m.addReference(
        "organization",
        AllPurchaseOrdersRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllSalesOrdersRequestFields() {
    DModel<AllSalesOrdersRequest> m = getType2("AllSalesOrdersRequest");
    m.addReference(
        "organization",
        AllSalesOrdersRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllSalesReturnsRequestFields() {
    DModel<AllSalesReturnsRequest> m = getType2("AllSalesReturnsRequest");
    m.addReference(
        "organization",
        AllSalesReturnsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllStockAlertsRequestFields() {
    DModel<AllStockAlertsRequest> m = getType2("AllStockAlertsRequest");
    m.addReference(
        "organization",
        AllStockAlertsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllStockBatchesRequestFields() {
    DModel<AllStockBatchesRequest> m = getType2("AllStockBatchesRequest");
    m.addReference(
        "organization",
        AllStockBatchesRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllStockTransfersRequestFields() {
    DModel<AllStockTransfersRequest> m = getType2("AllStockTransfersRequest");
    m.addReference(
        "organization",
        AllStockTransfersRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllStoresRequestFields() {
    DModel<AllStoresRequest> m = getType2("AllStoresRequest");
    m.addReference(
        "organization",
        AllStoresRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllSuppliersRequestFields() {
    DModel<AllSuppliersRequest> m = getType2("AllSuppliersRequest");
    m.addReference(
        "organization",
        AllSuppliersRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllUnitOfMeasuresRequestFields() {
    DModel<AllUnitOfMeasuresRequest> m = getType2("AllUnitOfMeasuresRequest");
    m.addReference(
        "organization",
        AllUnitOfMeasuresRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllUserInvitationsRequestFields() {
    DModel<AllUserInvitationsRequest> m = getType2("AllUserInvitationsRequest");
    m.addReference(
        "organization",
        AllUserInvitationsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllUserProfilesRequestFields() {
    DModel<AllUserProfilesRequest> m = getType2("AllUserProfilesRequest");
    m.addReference(
        "organization",
        AllUserProfilesRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAllWarehousesRequestFields() {
    DModel<AllWarehousesRequest> m = getType2("AllWarehousesRequest");
    m.addReference(
        "organization",
        AllWarehousesRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAnonymousUserFields() {
    DModel<AnonymousUser> m = getType2("AnonymousUser");
    m.setParent(getType("BaseUser"));
  }

  private void addAuditLogFields() {
    DModel<AuditLog> m = getType2("AuditLog");
    m.addPrimitive(
        "entityType",
        AuditLog._ENTITYTYPE,
        "_entity_type",
        FieldPrimitiveType.String,
        (s) -> s.getEntityType(),
        (s, v) -> s.setEntityType(v));
    m.addPrimitive(
        "entityId",
        AuditLog._ENTITYID,
        "_entity_id",
        FieldPrimitiveType.String,
        (s) -> s.getEntityId(),
        (s, v) -> s.setEntityId(v));
    m.addEnum(
        "action",
        AuditLog._ACTION,
        "_action",
        getType("AuditAction"),
        (s) -> s.getAction(),
        (s, v) -> s.setAction(v));
    m.addReference(
        "performedBy",
        AuditLog._PERFORMEDBY,
        "_performed_by_id",
        false,
        getType("User"),
        (s) -> s.getPerformedBy(),
        (s, v) -> s.setPerformedBy(v));
    m.addPrimitive(
        "performedAt",
        AuditLog._PERFORMEDAT,
        "_performed_at",
        FieldPrimitiveType.DateTime,
        (s) -> s.getPerformedAt(),
        (s, v) -> s.setPerformedAt(v));
    m.addPrimitive(
        "oldValues",
        AuditLog._OLDVALUES,
        "_old_values",
        FieldPrimitiveType.String,
        (s) -> s.getOldValues(),
        (s, v) -> s.setOldValues(v));
    m.addPrimitive(
        "newValues",
        AuditLog._NEWVALUES,
        "_new_values",
        FieldPrimitiveType.String,
        (s) -> s.getNewValues(),
        (s, v) -> s.setNewValues(v));
    m.addPrimitive(
        "ipAddress",
        AuditLog._IPADDRESS,
        "_ip_address",
        FieldPrimitiveType.String,
        (s) -> s.getIpAddress(),
        (s, v) -> s.setIpAddress(v));
    m.addPrimitive(
        "userAgent",
        AuditLog._USERAGENT,
        "_user_agent",
        FieldPrimitiveType.String,
        (s) -> s.getUserAgent(),
        (s, v) -> s.setUserAgent(v));
    m.addReference(
        "organization",
        AuditLog._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addAvatarFields() {
    DModel<Avatar> m = getType2("Avatar");
    m.addEmbedded(
        "image",
        Avatar._IMAGE,
        "_image_id",
        "_image",
        getType("D3EImage"),
        (s) -> s.getImage(),
        (s, v) -> s.setImage(v));
    m.addPrimitive(
        "createFrom",
        Avatar._CREATEFROM,
        "_create_from",
        FieldPrimitiveType.String,
        (s) -> s.getCreateFrom(),
        (s, v) -> s.setCreateFrom(v));
  }

  private void addBaseUserFields() {
    DModel<BaseUser> m = getType2("BaseUser");
    m.addPrimitive(
        "isActive",
        BaseUser._ISACTIVE,
        "_is_active",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsActive(),
        (s, v) -> s.setIsActive(v));
    m.addPrimitive(
            "deviceToken",
            BaseUser._DEVICETOKEN,
            "_device_token",
            FieldPrimitiveType.String,
            (s) -> s.getDeviceToken(),
            (s, v) -> s.setDeviceToken(v))
        .markTransient();
    m.addReference(
        "devices",
        BaseUser._DEVICES,
        "_devices_id",
        false,
        getType("UserDevice"),
        (s) -> s.getDevices(),
        (s, v) -> s.setDevices(v));
  }

  private void addBaseUserSessionFields() {
    DModel<BaseUserSession> m = getType2("BaseUserSession");
    m.addPrimitive(
        "userSessionId",
        BaseUserSession._USERSESSIONID,
        "_user_session_id",
        FieldPrimitiveType.String,
        (s) -> s.getUserSessionId(),
        (s, v) -> s.setUserSessionId(v));
  }

  private void addChangePasswordRequestFields() {
    DModel<ChangePasswordRequest> m = getType2("ChangePasswordRequest");
    m.addPrimitive(
        "newPassword",
        ChangePasswordRequest._NEWPASSWORD,
        "_new_password",
        FieldPrimitiveType.String,
        (s) -> s.getNewPassword(),
        (s, v) -> s.setNewPassword(v));
  }

  private void addD3EImageFields() {
    DModel<D3EImage> m = getType2("D3EImage");
    m.addPrimitive(
        "size",
        D3EImage._SIZE,
        "_size",
        FieldPrimitiveType.Integer,
        (s) -> s.getSize(),
        (s, v) -> s.setSize(v));
    m.addPrimitive(
        "width",
        D3EImage._WIDTH,
        "_width",
        FieldPrimitiveType.Integer,
        (s) -> s.getWidth(),
        (s, v) -> s.setWidth(v));
    m.addPrimitive(
        "height",
        D3EImage._HEIGHT,
        "_height",
        FieldPrimitiveType.Integer,
        (s) -> s.getHeight(),
        (s, v) -> s.setHeight(v));
    m.addReference(
        "file",
        D3EImage._FILE,
        "_file_id",
        false,
        getType("DFile"),
        (s) -> s.getFile(),
        (s, v) -> s.setFile(v));
  }

  private void addDashboardMetricsRequestFields() {
    DModel<DashboardMetricsRequest> m = getType2("DashboardMetricsRequest");
    m.addReference(
        "organization",
        DashboardMetricsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addEmailMessageFields() {
    DModel<EmailMessage> m = getType2("EmailMessage");
    m.addPrimitive(
        "from",
        EmailMessage._FROM,
        "_from",
        FieldPrimitiveType.String,
        (s) -> s.getFrom(),
        (s, v) -> s.setFrom(v));
    m.addPrimitiveCollection(
        "to",
        EmailMessage._TO,
        "_to",
        "_email_message_to_6e75c1",
        FieldPrimitiveType.String,
        (s) -> s.getTo(),
        (s, v) -> s.setTo(v));
    m.addPrimitive(
        "body",
        EmailMessage._BODY,
        "_body",
        FieldPrimitiveType.String,
        (s) -> s.getBody(),
        (s, v) -> s.setBody(v));
    m.addPrimitive(
        "createdOn",
        EmailMessage._CREATEDON,
        "_created_on",
        FieldPrimitiveType.DateTime,
        (s) -> s.getCreatedOn(),
        (s, v) -> s.setCreatedOn(v));
    m.addPrimitiveCollection(
        "bcc",
        EmailMessage._BCC,
        "_bcc",
        "_email_message_bcc_1e1d14",
        FieldPrimitiveType.String,
        (s) -> s.getBcc(),
        (s, v) -> s.setBcc(v));
    m.addPrimitiveCollection(
        "cc",
        EmailMessage._CC,
        "_cc",
        "_email_message_cc_ad6675",
        FieldPrimitiveType.String,
        (s) -> s.getCc(),
        (s, v) -> s.setCc(v));
    m.addPrimitive(
        "subject",
        EmailMessage._SUBJECT,
        "_subject",
        FieldPrimitiveType.String,
        (s) -> s.getSubject(),
        (s, v) -> s.setSubject(v));
    m.addPrimitive(
        "html",
        EmailMessage._HTML,
        "_html",
        FieldPrimitiveType.Boolean,
        (s) -> s.isHtml(),
        (s, v) -> s.setHtml(v));
    m.addReferenceCollection(
        "inlineAttachments",
        EmailMessage._INLINEATTACHMENTS,
        "_inline_attachments_id",
        "_email_message_inline_attachments_6efef9",
        false,
        getType("DFile"),
        (s) -> s.getInlineAttachments(),
        (s, v) -> s.setInlineAttachments(v));
    m.addReferenceCollection(
        "attachments",
        EmailMessage._ATTACHMENTS,
        "_attachments_id",
        "_email_message_attachments_ead2e3",
        false,
        getType("DFile"),
        (s) -> s.getAttachments(),
        (s, v) -> s.setAttachments(v));
  }

  private void addExpiringBatchesRequestFields() {
    DModel<ExpiringBatchesRequest> m = getType2("ExpiringBatchesRequest");
    m.addReference(
        "organization",
        ExpiringBatchesRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addPrimitive(
        "daysAhead",
        ExpiringBatchesRequest._DAYSAHEAD,
        "null",
        FieldPrimitiveType.Integer,
        (s) -> s.getDaysAhead(),
        (s, v) -> s.setDaysAhead(v));
  }

  private void addGoodsReceiptFields() {
    DModel<GoodsReceipt> m = getType2("GoodsReceipt");
    m.addPrimitive(
            "receiptNumber",
            GoodsReceipt._RECEIPTNUMBER,
            "_receipt_number",
            FieldPrimitiveType.String,
            (s) -> s.getReceiptNumber(),
            (s, v) -> s.setReceiptNumber(v))
        .readOnly();
    m.addReference(
        "purchaseOrder",
        GoodsReceipt._PURCHASEORDER,
        "_purchase_order_id",
        false,
        getType("PurchaseOrder"),
        (s) -> s.getPurchaseOrder(),
        (s, v) -> s.setPurchaseOrder(v));
    m.addReference(
        "vendor",
        GoodsReceipt._VENDOR,
        "_vendor_id",
        false,
        getType("Vendor"),
        (s) -> s.getVendor(),
        (s, v) -> s.setVendor(v));
    m.addReference(
        "warehouse",
        GoodsReceipt._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addPrimitive(
        "receiptDate",
        GoodsReceipt._RECEIPTDATE,
        "_receipt_date",
        FieldPrimitiveType.Date,
        (s) -> s.getReceiptDate(),
        (s, v) -> s.setReceiptDate(v));
    m.addEnum(
        "status",
        GoodsReceipt._STATUS,
        "_status",
        getType("GoodsReceiptStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
        "notes",
        GoodsReceipt._NOTES,
        "_notes",
        FieldPrimitiveType.String,
        (s) -> s.getNotes(),
        (s, v) -> s.setNotes(v));
    m.addReference(
            "receivedBy",
            GoodsReceipt._RECEIVEDBY,
            "_received_by_id",
            false,
            getType("User"),
            (s) -> s.getReceivedBy(),
            (s, v) -> s.setReceivedBy(v))
        .readOnly();
    m.addPrimitive(
            "createdAt",
            GoodsReceipt._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addInverseCollection(
        "lines",
        GoodsReceipt._LINES,
        "_goods_receipt_id",
        getType("GoodsReceiptLine"),
        (s) -> s.getLines());
    m.addReference(
        "organization",
        GoodsReceipt._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addGoodsReceiptItemRequestFields() {
    DModel<GoodsReceiptItemRequest> m = getType2("GoodsReceiptItemRequest");
    m.addReference(
        "goodsReceipt",
        GoodsReceiptItemRequest._GOODSRECEIPT,
        "null_id",
        false,
        getType("GoodsReceipt"),
        (s) -> s.getGoodsReceipt(),
        (s, v) -> s.setGoodsReceipt(v));
  }

  private void addGoodsReceiptLineFields() {
    DModel<GoodsReceiptLine> m = getType2("GoodsReceiptLine");
    m.addReference(
        "product",
        GoodsReceiptLine._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addPrimitive(
        "receivedQuantity",
        GoodsReceiptLine._RECEIVEDQUANTITY,
        "_received_quantity",
        FieldPrimitiveType.Double,
        (s) -> s.getReceivedQuantity(),
        (s, v) -> s.setReceivedQuantity(v));
    m.addPrimitive(
        "unitCost",
        GoodsReceiptLine._UNITCOST,
        "_unit_cost",
        FieldPrimitiveType.Double,
        (s) -> s.getUnitCost(),
        (s, v) -> s.setUnitCost(v));
    m.addPrimitive(
        "batchNumber",
        GoodsReceiptLine._BATCHNUMBER,
        "_batch_number",
        FieldPrimitiveType.String,
        (s) -> s.getBatchNumber(),
        (s, v) -> s.setBatchNumber(v));
    m.addPrimitive(
        "expiryDate",
        GoodsReceiptLine._EXPIRYDATE,
        "_expiry_date",
        FieldPrimitiveType.Date,
        (s) -> s.getExpiryDate(),
        (s, v) -> s.setExpiryDate(v));
    m.addReference(
        "purchaseOrderLine",
        GoodsReceiptLine._PURCHASEORDERLINE,
        "_purchase_order_line_id",
        false,
        getType("PurchaseOrderLine"),
        (s) -> s.getPurchaseOrderLine(),
        (s, v) -> s.setPurchaseOrderLine(v));
    m.addReference(
        "goodsReceipt",
        GoodsReceiptLine._GOODSRECEIPT,
        "_goods_receipt_id",
        false,
        getType("GoodsReceipt"),
        (s) -> s.getGoodsReceipt(),
        (s, v) -> s.setGoodsReceipt(v));
  }

  private void addInAppNotificationFields() {
    DModel<InAppNotification> m = getType2("InAppNotification");
    m.addReference(
        "recipient",
        InAppNotification._RECIPIENT,
        "_recipient_id",
        false,
        getType("User"),
        (s) -> s.getRecipient(),
        (s, v) -> s.setRecipient(v));
    m.addPrimitive(
        "title",
        InAppNotification._TITLE,
        "_title",
        FieldPrimitiveType.String,
        (s) -> s.getTitle(),
        (s, v) -> s.setTitle(v));
    m.addPrimitive(
        "message",
        InAppNotification._MESSAGE,
        "_message",
        FieldPrimitiveType.String,
        (s) -> s.getMessage(),
        (s, v) -> s.setMessage(v));
    m.addEnum(
        "notificationType",
        InAppNotification._NOTIFICATIONTYPE,
        "_notification_type",
        getType("NotificationType"),
        (s) -> s.getNotificationType(),
        (s, v) -> s.setNotificationType(v));
    m.addPrimitive(
        "referenceType",
        InAppNotification._REFERENCETYPE,
        "_reference_type",
        FieldPrimitiveType.String,
        (s) -> s.getReferenceType(),
        (s, v) -> s.setReferenceType(v));
    m.addPrimitive(
        "referenceId",
        InAppNotification._REFERENCEID,
        "_reference_id",
        FieldPrimitiveType.String,
        (s) -> s.getReferenceId(),
        (s, v) -> s.setReferenceId(v));
    m.addPrimitive(
        "isRead",
        InAppNotification._ISREAD,
        "_is_read",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsRead(),
        (s, v) -> s.setIsRead(v));
    m.addPrimitive(
            "createdAt",
            InAppNotification._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addReference(
        "organization",
        InAppNotification._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addInventoryAdjustmentFields() {
    DModel<InventoryAdjustment> m = getType2("InventoryAdjustment");
    m.addPrimitive(
        "adjustmentNumber",
        InventoryAdjustment._ADJUSTMENTNUMBER,
        "_adjustment_number",
        FieldPrimitiveType.String,
        (s) -> s.getAdjustmentNumber(),
        (s, v) -> s.setAdjustmentNumber(v));
    m.addReference(
        "warehouse",
        InventoryAdjustment._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addPrimitive(
        "adjustmentDate",
        InventoryAdjustment._ADJUSTMENTDATE,
        "_adjustment_date",
        FieldPrimitiveType.Date,
        (s) -> s.getAdjustmentDate(),
        (s, v) -> s.setAdjustmentDate(v));
    m.addEnum(
        "reason",
        InventoryAdjustment._REASON,
        "_reason",
        getType("AdjustmentReason"),
        (s) -> s.getReason(),
        (s, v) -> s.setReason(v));
    m.addEnum(
        "status",
        InventoryAdjustment._STATUS,
        "_status",
        getType("AdjustmentStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
        "notes",
        InventoryAdjustment._NOTES,
        "_notes",
        FieldPrimitiveType.String,
        (s) -> s.getNotes(),
        (s, v) -> s.setNotes(v));
    m.addReference(
            "adjustedBy",
            InventoryAdjustment._ADJUSTEDBY,
            "_adjusted_by_id",
            false,
            getType("User"),
            (s) -> s.getAdjustedBy(),
            (s, v) -> s.setAdjustedBy(v))
        .readOnly();
    m.addInverseCollection(
        "lines",
        InventoryAdjustment._LINES,
        "_inventory_adjustment_id",
        getType("InventoryAdjustmentLine"),
        (s) -> s.getLines());
    m.addReference(
        "organization",
        InventoryAdjustment._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addInventoryAdjustmentLineFields() {
    DModel<InventoryAdjustmentLine> m = getType2("InventoryAdjustmentLine");
    m.addReference(
        "product",
        InventoryAdjustmentLine._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addPrimitive(
            "quantityBefore",
            InventoryAdjustmentLine._QUANTITYBEFORE,
            "_quantity_before",
            FieldPrimitiveType.Double,
            (s) -> s.getQuantityBefore(),
            (s, v) -> s.setQuantityBefore(v))
        .readOnly();
    m.addPrimitive(
        "quantityChange",
        InventoryAdjustmentLine._QUANTITYCHANGE,
        "_quantity_change",
        FieldPrimitiveType.Double,
        (s) -> s.getQuantityChange(),
        (s, v) -> s.setQuantityChange(v));
    m.addPrimitive(
            "quantityAfter",
            InventoryAdjustmentLine._QUANTITYAFTER,
            "_quantity_after",
            FieldPrimitiveType.Double,
            (s) -> s.getQuantityAfter(),
            (s, v) -> s.setQuantityAfter(v))
        .readOnly();
    m.addPrimitive(
        "batchNumber",
        InventoryAdjustmentLine._BATCHNUMBER,
        "_batch_number",
        FieldPrimitiveType.String,
        (s) -> s.getBatchNumber(),
        (s, v) -> s.setBatchNumber(v));
    m.addPrimitive(
        "unitCost",
        InventoryAdjustmentLine._UNITCOST,
        "_unit_cost",
        FieldPrimitiveType.Double,
        (s) -> s.getUnitCost(),
        (s, v) -> s.setUnitCost(v));
    m.addReference(
        "inventoryAdjustment",
        InventoryAdjustmentLine._INVENTORYADJUSTMENT,
        "_inventory_adjustment_id",
        false,
        getType("InventoryAdjustment"),
        (s) -> s.getInventoryAdjustment(),
        (s, v) -> s.setInventoryAdjustment(v));
  }

  private void addInventoryMovementFields() {
    DModel<InventoryMovement> m = getType2("InventoryMovement");
    m.addPrimitive(
        "movementNumber",
        InventoryMovement._MOVEMENTNUMBER,
        "_movement_number",
        FieldPrimitiveType.String,
        (s) -> s.getMovementNumber(),
        (s, v) -> s.setMovementNumber(v));
    m.addReference(
        "warehouse",
        InventoryMovement._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addReference(
        "product",
        InventoryMovement._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addEnum(
        "movementType",
        InventoryMovement._MOVEMENTTYPE,
        "_movement_type",
        getType("MovementType"),
        (s) -> s.getMovementType(),
        (s, v) -> s.setMovementType(v));
    m.addPrimitive(
        "quantity",
        InventoryMovement._QUANTITY,
        "_quantity",
        FieldPrimitiveType.Double,
        (s) -> s.getQuantity(),
        (s, v) -> s.setQuantity(v));
    m.addEnum(
        "direction",
        InventoryMovement._DIRECTION,
        "_direction",
        getType("MovementDirection"),
        (s) -> s.getDirection(),
        (s, v) -> s.setDirection(v));
    m.addEnum(
        "referenceType",
        InventoryMovement._REFERENCETYPE,
        "_reference_type",
        getType("MovementReferenceType"),
        (s) -> s.getReferenceType(),
        (s, v) -> s.setReferenceType(v));
    m.addPrimitive(
        "referenceId",
        InventoryMovement._REFERENCEID,
        "_reference_id",
        FieldPrimitiveType.String,
        (s) -> s.getReferenceId(),
        (s, v) -> s.setReferenceId(v));
    m.addPrimitive(
        "batchNumber",
        InventoryMovement._BATCHNUMBER,
        "_batch_number",
        FieldPrimitiveType.String,
        (s) -> s.getBatchNumber(),
        (s, v) -> s.setBatchNumber(v));
    m.addPrimitive(
        "unitCost",
        InventoryMovement._UNITCOST,
        "_unit_cost",
        FieldPrimitiveType.Double,
        (s) -> s.getUnitCost(),
        (s, v) -> s.setUnitCost(v));
    m.addPrimitive(
        "balanceAfter",
        InventoryMovement._BALANCEAFTER,
        "_balance_after",
        FieldPrimitiveType.Double,
        (s) -> s.getBalanceAfter(),
        (s, v) -> s.setBalanceAfter(v));
    m.addPrimitive(
        "movementDate",
        InventoryMovement._MOVEMENTDATE,
        "_movement_date",
        FieldPrimitiveType.DateTime,
        (s) -> s.getMovementDate(),
        (s, v) -> s.setMovementDate(v));
    m.addReference(
        "performedBy",
        InventoryMovement._PERFORMEDBY,
        "_performed_by_id",
        false,
        getType("User"),
        (s) -> s.getPerformedBy(),
        (s, v) -> s.setPerformedBy(v));
    m.addPrimitive(
        "notes",
        InventoryMovement._NOTES,
        "_notes",
        FieldPrimitiveType.String,
        (s) -> s.getNotes(),
        (s, v) -> s.setNotes(v));
    m.addReference(
        "organization",
        InventoryMovement._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addInventoryMovementsByDateRangeRequestFields() {
    DModel<InventoryMovementsByDateRangeRequest> m =
        getType2("InventoryMovementsByDateRangeRequest");
    m.addReference(
        "organization",
        InventoryMovementsByDateRangeRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addPrimitive(
        "startDate",
        InventoryMovementsByDateRangeRequest._STARTDATE,
        "null",
        FieldPrimitiveType.DateTime,
        (s) -> s.getStartDate(),
        (s, v) -> s.setStartDate(v));
    m.addPrimitive(
        "endDate",
        InventoryMovementsByDateRangeRequest._ENDDATE,
        "null",
        FieldPrimitiveType.DateTime,
        (s) -> s.getEndDate(),
        (s, v) -> s.setEndDate(v));
  }

  private void addLowStockItemsRequestFields() {
    DModel<LowStockItemsRequest> m = getType2("LowStockItemsRequest");
    m.addReference(
        "organization",
        LowStockItemsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addMovementReportRowsRequestFields() {
    DModel<MovementReportRowsRequest> m = getType2("MovementReportRowsRequest");
    m.addReference(
        "organization",
        MovementReportRowsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addPrimitive(
        "startDate",
        MovementReportRowsRequest._STARTDATE,
        "null",
        FieldPrimitiveType.DateTime,
        (s) -> s.getStartDate(),
        (s, v) -> s.setStartDate(v));
    m.addPrimitive(
        "endDate",
        MovementReportRowsRequest._ENDDATE,
        "null",
        FieldPrimitiveType.DateTime,
        (s) -> s.getEndDate(),
        (s, v) -> s.setEndDate(v));
    m.addReference(
        "warehouse",
        MovementReportRowsRequest._WAREHOUSE,
        "null_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
  }

  private void addNotificationTemplateFields() {
    DModel<NotificationTemplate> m = getType2("NotificationTemplate");
    m.addPrimitive(
        "templateCode",
        NotificationTemplate._TEMPLATECODE,
        "_template_code",
        FieldPrimitiveType.String,
        (s) -> s.getTemplateCode(),
        (s, v) -> s.setTemplateCode(v));
    m.addEnum(
        "channel",
        NotificationTemplate._CHANNEL,
        "_channel",
        getType("NotificationChannel"),
        (s) -> s.getChannel(),
        (s, v) -> s.setChannel(v));
    m.addPrimitive(
        "subject",
        NotificationTemplate._SUBJECT,
        "_subject",
        FieldPrimitiveType.String,
        (s) -> s.getSubject(),
        (s, v) -> s.setSubject(v));
    m.addPrimitive(
        "bodyTemplate",
        NotificationTemplate._BODYTEMPLATE,
        "_body_template",
        FieldPrimitiveType.String,
        (s) -> s.getBodyTemplate(),
        (s, v) -> s.setBodyTemplate(v));
    m.addEnum(
        "status",
        NotificationTemplate._STATUS,
        "_status",
        getType("EntityStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addReference(
        "organization",
        NotificationTemplate._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addOneTimePasswordFields() {
    DModel<OneTimePassword> m = getType2("OneTimePassword");
    m.addPrimitive(
        "input",
        OneTimePassword._INPUT,
        "_input",
        FieldPrimitiveType.String,
        (s) -> s.getInput(),
        (s, v) -> s.setInput(v));
    m.addPrimitive(
        "inputType",
        OneTimePassword._INPUTTYPE,
        "_input_type",
        FieldPrimitiveType.String,
        (s) -> s.getInputType(),
        (s, v) -> s.setInputType(v));
    m.addPrimitive(
        "userType",
        OneTimePassword._USERTYPE,
        "_user_type",
        FieldPrimitiveType.String,
        (s) -> s.getUserType(),
        (s, v) -> s.setUserType(v));
    m.addPrimitive(
            "success",
            OneTimePassword._SUCCESS,
            "_success",
            FieldPrimitiveType.Boolean,
            (s) -> s.isSuccess(),
            (s, v) -> s.setSuccess(v))
        .readOnly();
    m.addPrimitive(
            "errorMsg",
            OneTimePassword._ERRORMSG,
            "_error_msg",
            FieldPrimitiveType.String,
            (s) -> s.getErrorMsg(),
            (s, v) -> s.setErrorMsg(v))
        .readOnly();
    m.addPrimitive(
            "token",
            OneTimePassword._TOKEN,
            "_token",
            FieldPrimitiveType.String,
            (s) -> s.getToken(),
            (s, v) -> s.setToken(v))
        .readOnly();
    m.addPrimitive(
            "code",
            OneTimePassword._CODE,
            "_code",
            FieldPrimitiveType.String,
            (s) -> s.getCode(),
            (s, v) -> s.setCode(v))
        .markNone();
    m.addReference(
            "user",
            OneTimePassword._USER,
            "_user_id",
            false,
            getType("BaseUser"),
            (s) -> s.getUser(),
            (s, v) -> s.setUser(v))
        .markNone();
    m.addPrimitive(
            "expiry",
            OneTimePassword._EXPIRY,
            "_expiry",
            FieldPrimitiveType.DateTime,
            (s) -> s.getExpiry(),
            (s, v) -> s.setExpiry(v))
        .readOnly();
  }

  private void addOrganizationFields() {
    DModel<Organization> m = getType2("Organization");
    m.addPrimitive(
        "name",
        Organization._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "code",
        Organization._CODE,
        "_code",
        FieldPrimitiveType.String,
        (s) -> s.getCode(),
        (s, v) -> s.setCode(v));
    m.addPrimitive(
        "legalName",
        Organization._LEGALNAME,
        "_legal_name",
        FieldPrimitiveType.String,
        (s) -> s.getLegalName(),
        (s, v) -> s.setLegalName(v));
    m.addPrimitive(
        "taxId",
        Organization._TAXID,
        "_tax_id",
        FieldPrimitiveType.String,
        (s) -> s.getTaxId(),
        (s, v) -> s.setTaxId(v));
    m.addPrimitive(
        "email",
        Organization._EMAIL,
        "_email",
        FieldPrimitiveType.String,
        (s) -> s.getEmail(),
        (s, v) -> s.setEmail(v));
    m.addPrimitive(
        "phone",
        Organization._PHONE,
        "_phone",
        FieldPrimitiveType.String,
        (s) -> s.getPhone(),
        (s, v) -> s.setPhone(v));
    m.addPrimitive(
        "address",
        Organization._ADDRESS,
        "_address",
        FieldPrimitiveType.String,
        (s) -> s.getAddress(),
        (s, v) -> s.setAddress(v));
    m.addReference(
        "logo",
        Organization._LOGO,
        "_logo_id",
        false,
        getType("DFile"),
        (s) -> s.getLogo(),
        (s, v) -> s.setLogo(v));
    m.addPrimitive(
        "currency",
        Organization._CURRENCY,
        "_currency",
        FieldPrimitiveType.String,
        (s) -> s.getCurrency(),
        (s, v) -> s.setCurrency(v));
    m.addPrimitive(
        "timezone",
        Organization._TIMEZONE,
        "_timezone",
        FieldPrimitiveType.String,
        (s) -> s.getTimezone(),
        (s, v) -> s.setTimezone(v));
    m.addEnum(
        "status",
        Organization._STATUS,
        "_status",
        getType("OrganizationStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "createdAt",
            Organization._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addPrimitive(
            "updatedAt",
            Organization._UPDATEDAT,
            "_updated_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getUpdatedAt(),
            (s, v) -> s.setUpdatedAt(v))
        .readOnly();
    m.addReference(
            "createdBy",
            Organization._CREATEDBY,
            "_created_by_id",
            false,
            getType("User"),
            (s) -> s.getCreatedBy(),
            (s, v) -> s.setCreatedBy(v))
        .readOnly();
  }

  private void addOrganizationItemRequestFields() {
    DModel<OrganizationItemRequest> m = getType2("OrganizationItemRequest");
    m.addReference(
        "organization",
        OrganizationItemRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addOutOfStockItemsRequestFields() {
    DModel<OutOfStockItemsRequest> m = getType2("OutOfStockItemsRequest");
    m.addReference(
        "organization",
        OutOfStockItemsRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addProductFields() {
    DModel<Product> m = getType2("Product");
    m.addReference(
        "category",
        Product._CATEGORY,
        "_category_id",
        false,
        getType("ProductCategory"),
        (s) -> s.getCategory(),
        (s, v) -> s.setCategory(v));
    m.addPrimitive(
        "sku",
        Product._SKU,
        "_sku",
        FieldPrimitiveType.String,
        (s) -> s.getSku(),
        (s, v) -> s.setSku(v));
    m.addPrimitive(
        "name",
        Product._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "description",
        Product._DESCRIPTION,
        "_description",
        FieldPrimitiveType.String,
        (s) -> s.getDescription(),
        (s, v) -> s.setDescription(v));
    m.addPrimitive(
        "barcode",
        Product._BARCODE,
        "_barcode",
        FieldPrimitiveType.String,
        (s) -> s.getBarcode(),
        (s, v) -> s.setBarcode(v));
    m.addReference(
        "baseUom",
        Product._BASEUOM,
        "_base_uom_id",
        false,
        getType("UnitOfMeasure"),
        (s) -> s.getBaseUom(),
        (s, v) -> s.setBaseUom(v));
    m.addPrimitive(
        "purchasePrice",
        Product._PURCHASEPRICE,
        "_purchase_price",
        FieldPrimitiveType.Double,
        (s) -> s.getPurchasePrice(),
        (s, v) -> s.setPurchasePrice(v));
    m.addPrimitive(
        "sellingPrice",
        Product._SELLINGPRICE,
        "_selling_price",
        FieldPrimitiveType.Double,
        (s) -> s.getSellingPrice(),
        (s, v) -> s.setSellingPrice(v));
    m.addPrimitive(
        "reorderLevel",
        Product._REORDERLEVEL,
        "_reorder_level",
        FieldPrimitiveType.Double,
        (s) -> s.getReorderLevel(),
        (s, v) -> s.setReorderLevel(v));
    m.addPrimitive(
        "reorderQuantity",
        Product._REORDERQUANTITY,
        "_reorder_quantity",
        FieldPrimitiveType.Double,
        (s) -> s.getReorderQuantity(),
        (s, v) -> s.setReorderQuantity(v));
    m.addPrimitive(
        "trackBatch",
        Product._TRACKBATCH,
        "_track_batch",
        FieldPrimitiveType.Boolean,
        (s) -> s.isTrackBatch(),
        (s, v) -> s.setTrackBatch(v));
    m.addPrimitive(
        "trackExpiry",
        Product._TRACKEXPIRY,
        "_track_expiry",
        FieldPrimitiveType.Boolean,
        (s) -> s.isTrackExpiry(),
        (s, v) -> s.setTrackExpiry(v));
    m.addPrimitive(
        "shelfLifeDays",
        Product._SHELFLIFEDAYS,
        "_shelf_life_days",
        FieldPrimitiveType.Integer,
        (s) -> s.getShelfLifeDays(),
        (s, v) -> s.setShelfLifeDays(v));
    m.addReference(
        "image",
        Product._IMAGE,
        "_image_id",
        false,
        getType("DFile"),
        (s) -> s.getImage(),
        (s, v) -> s.setImage(v));
    m.addInverseCollection(
        "warehouseStocks",
        Product._WAREHOUSESTOCKS,
        "_product_id",
        getType("WarehouseStock"),
        (s) -> s.getWarehouseStocks());
    m.addInverseCollection(
        "batches", Product._BATCHES, "_product_id", getType("StockBatch"), (s) -> s.getBatches());
    m.addEnum(
        "status",
        Product._STATUS,
        "_status",
        getType("ProductStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "createdAt",
            Product._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addPrimitive(
            "updatedAt",
            Product._UPDATEDAT,
            "_updated_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getUpdatedAt(),
            (s, v) -> s.setUpdatedAt(v))
        .readOnly();
    m.addReference(
        "organization",
        Product._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addProductCategoryFields() {
    DModel<ProductCategory> m = getType2("ProductCategory");
    m.addReference(
        "parentCategory",
        ProductCategory._PARENTCATEGORY,
        "_parent_category_id",
        false,
        getType("ProductCategory"),
        (s) -> s.getParentCategory(),
        (s, v) -> s.setParentCategory(v));
    m.addInverseCollection(
        "childCategories",
        ProductCategory._CHILDCATEGORIES,
        "_parent_category_id",
        getType("ProductCategory"),
        (s) -> s.getChildCategories());
    m.addInverseCollection(
        "products",
        ProductCategory._PRODUCTS,
        "_category_id",
        getType("Product"),
        (s) -> s.getProducts());
    m.addPrimitive(
        "name",
        ProductCategory._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "code",
        ProductCategory._CODE,
        "_code",
        FieldPrimitiveType.String,
        (s) -> s.getCode(),
        (s, v) -> s.setCode(v));
    m.addPrimitive(
        "description",
        ProductCategory._DESCRIPTION,
        "_description",
        FieldPrimitiveType.String,
        (s) -> s.getDescription(),
        (s, v) -> s.setDescription(v));
    m.addEnum(
        "status",
        ProductCategory._STATUS,
        "_status",
        getType("EntityStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addReference(
        "organization",
        ProductCategory._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addProductItemRequestFields() {
    DModel<ProductItemRequest> m = getType2("ProductItemRequest");
    m.addReference(
        "product",
        ProductItemRequest._PRODUCT,
        "null_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
  }

  private void addProductSearchRequestFields() {
    DModel<ProductSearchRequest> m = getType2("ProductSearchRequest");
    m.addReference(
        "organization",
        ProductSearchRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addPrimitive(
        "searchTerm",
        ProductSearchRequest._SEARCHTERM,
        "null",
        FieldPrimitiveType.String,
        (s) -> s.getSearchTerm(),
        (s, v) -> s.setSearchTerm(v));
    m.addReference(
        "category",
        ProductSearchRequest._CATEGORY,
        "null_id",
        false,
        getType("ProductCategory"),
        (s) -> s.getCategory(),
        (s, v) -> s.setCategory(v));
    m.addEnum(
        "status",
        ProductSearchRequest._STATUS,
        "null",
        getType("ProductStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
  }

  private void addProductsByCategoryRequestFields() {
    DModel<ProductsByCategoryRequest> m = getType2("ProductsByCategoryRequest");
    m.addReference(
        "organization",
        ProductsByCategoryRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addReference(
        "category",
        ProductsByCategoryRequest._CATEGORY,
        "null_id",
        false,
        getType("ProductCategory"),
        (s) -> s.getCategory(),
        (s, v) -> s.setCategory(v));
  }

  private void addPurchaseOrderFields() {
    DModel<PurchaseOrder> m = getType2("PurchaseOrder");
    m.addPrimitive(
            "poNumber",
            PurchaseOrder._PONUMBER,
            "_po_number",
            FieldPrimitiveType.String,
            (s) -> s.getPoNumber(),
            (s, v) -> s.setPoNumber(v))
        .readOnly();
    m.addReference(
        "vendor",
        PurchaseOrder._VENDOR,
        "_vendor_id",
        false,
        getType("Vendor"),
        (s) -> s.getVendor(),
        (s, v) -> s.setVendor(v));
    m.addReference(
        "warehouse",
        PurchaseOrder._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addPrimitive(
        "orderDate",
        PurchaseOrder._ORDERDATE,
        "_order_date",
        FieldPrimitiveType.Date,
        (s) -> s.getOrderDate(),
        (s, v) -> s.setOrderDate(v));
    m.addPrimitive(
        "expectedDeliveryDate",
        PurchaseOrder._EXPECTEDDELIVERYDATE,
        "_expected_delivery_date",
        FieldPrimitiveType.Date,
        (s) -> s.getExpectedDeliveryDate(),
        (s, v) -> s.setExpectedDeliveryDate(v));
    m.addEnum(
        "status",
        PurchaseOrder._STATUS,
        "_status",
        getType("PurchaseOrderStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "subtotal",
            PurchaseOrder._SUBTOTAL,
            "_subtotal",
            FieldPrimitiveType.Double,
            (s) -> s.getSubtotal(),
            (s, v) -> s.setSubtotal(v))
        .readOnly();
    m.addPrimitive(
        "taxAmount",
        PurchaseOrder._TAXAMOUNT,
        "_tax_amount",
        FieldPrimitiveType.Double,
        (s) -> s.getTaxAmount(),
        (s, v) -> s.setTaxAmount(v));
    m.addPrimitive(
            "totalAmount",
            PurchaseOrder._TOTALAMOUNT,
            "_total_amount",
            FieldPrimitiveType.Double,
            (s) -> s.getTotalAmount(),
            (s, v) -> s.setTotalAmount(v))
        .readOnly();
    m.addPrimitive(
        "notes",
        PurchaseOrder._NOTES,
        "_notes",
        FieldPrimitiveType.String,
        (s) -> s.getNotes(),
        (s, v) -> s.setNotes(v));
    m.addReference(
            "createdBy",
            PurchaseOrder._CREATEDBY,
            "_created_by_id",
            false,
            getType("User"),
            (s) -> s.getCreatedBy(),
            (s, v) -> s.setCreatedBy(v))
        .readOnly();
    m.addReference(
            "approvedBy",
            PurchaseOrder._APPROVEDBY,
            "_approved_by_id",
            false,
            getType("User"),
            (s) -> s.getApprovedBy(),
            (s, v) -> s.setApprovedBy(v))
        .readOnly();
    m.addPrimitive(
            "approvedAt",
            PurchaseOrder._APPROVEDAT,
            "_approved_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getApprovedAt(),
            (s, v) -> s.setApprovedAt(v))
        .readOnly();
    m.addPrimitive(
            "createdAt",
            PurchaseOrder._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addInverseCollection(
        "lines",
        PurchaseOrder._LINES,
        "_purchase_order_id",
        getType("PurchaseOrderLine"),
        (s) -> s.getLines());
    m.addReference(
        "organization",
        PurchaseOrder._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addPurchaseOrderItemRequestFields() {
    DModel<PurchaseOrderItemRequest> m = getType2("PurchaseOrderItemRequest");
    m.addReference(
        "purchaseOrder",
        PurchaseOrderItemRequest._PURCHASEORDER,
        "null_id",
        false,
        getType("PurchaseOrder"),
        (s) -> s.getPurchaseOrder(),
        (s, v) -> s.setPurchaseOrder(v));
  }

  private void addPurchaseOrderLineFields() {
    DModel<PurchaseOrderLine> m = getType2("PurchaseOrderLine");
    m.addPrimitive(
        "lineNumber",
        PurchaseOrderLine._LINENUMBER,
        "_line_number",
        FieldPrimitiveType.Integer,
        (s) -> s.getLineNumber(),
        (s, v) -> s.setLineNumber(v));
    m.addReference(
        "product",
        PurchaseOrderLine._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addPrimitive(
        "orderedQuantity",
        PurchaseOrderLine._ORDEREDQUANTITY,
        "_ordered_quantity",
        FieldPrimitiveType.Double,
        (s) -> s.getOrderedQuantity(),
        (s, v) -> s.setOrderedQuantity(v));
    m.addPrimitive(
            "receivedQuantity",
            PurchaseOrderLine._RECEIVEDQUANTITY,
            "_received_quantity",
            FieldPrimitiveType.Double,
            (s) -> s.getReceivedQuantity(),
            (s, v) -> s.setReceivedQuantity(v))
        .readOnly();
    m.addPrimitive(
        "unitPrice",
        PurchaseOrderLine._UNITPRICE,
        "_unit_price",
        FieldPrimitiveType.Double,
        (s) -> s.getUnitPrice(),
        (s, v) -> s.setUnitPrice(v));
    m.addPrimitive(
            "lineTotal",
            PurchaseOrderLine._LINETOTAL,
            "_line_total",
            FieldPrimitiveType.Double,
            (s) -> s.getLineTotal(),
            (s, v) -> s.setLineTotal(v))
        .readOnly();
    m.addReference(
        "uom",
        PurchaseOrderLine._UOM,
        "_uom_id",
        false,
        getType("UnitOfMeasure"),
        (s) -> s.getUom(),
        (s, v) -> s.setUom(v));
    m.addReference(
        "purchaseOrder",
        PurchaseOrderLine._PURCHASEORDER,
        "_purchase_order_id",
        false,
        getType("PurchaseOrder"),
        (s) -> s.getPurchaseOrder(),
        (s, v) -> s.setPurchaseOrder(v));
  }

  private void addPurchaseOrdersByStatusRequestFields() {
    DModel<PurchaseOrdersByStatusRequest> m = getType2("PurchaseOrdersByStatusRequest");
    m.addReference(
        "organization",
        PurchaseOrdersByStatusRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addEnum(
        "status",
        PurchaseOrdersByStatusRequest._STATUS,
        "null",
        getType("PurchaseOrderStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
  }

  private void addPushNotificationFields() {
    DModel<PushNotification> m = getType2("PushNotification");
    m.addReferenceCollection(
        "users",
        PushNotification._USERS,
        "_users_id",
        "_push_notification_users_fab88b",
        false,
        getType("BaseUser"),
        (s) -> s.getUsers(),
        (s, v) -> s.setUsers(v));
    m.addPrimitive(
        "skipThisDevice",
        PushNotification._SKIPTHISDEVICE,
        "_skip_this_device",
        FieldPrimitiveType.Boolean,
        (s) -> s.isSkipThisDevice(),
        (s, v) -> s.setSkipThisDevice(v));
    m.addPrimitive(
        "deviceToken",
        PushNotification._DEVICETOKEN,
        "_device_token",
        FieldPrimitiveType.String,
        (s) -> s.getDeviceToken(),
        (s, v) -> s.setDeviceToken(v));
    m.addPrimitive(
        "title",
        PushNotification._TITLE,
        "_title",
        FieldPrimitiveType.String,
        (s) -> s.getTitle(),
        (s, v) -> s.setTitle(v));
    m.addPrimitive(
        "body",
        PushNotification._BODY,
        "_body",
        FieldPrimitiveType.String,
        (s) -> s.getBody(),
        (s, v) -> s.setBody(v));
    m.addPrimitive(
        "path",
        PushNotification._PATH,
        "_path",
        FieldPrimitiveType.String,
        (s) -> s.getPath(),
        (s, v) -> s.setPath(v));
    m.addPrimitiveCollection(
        "data",
        PushNotification._DATA,
        "_data",
        "_push_notification_data_9b2135",
        FieldPrimitiveType.String,
        (s) -> s.getData(),
        (s, v) -> s.setData(v));
    m.addPrimitive(
        "failed",
        PushNotification._FAILED,
        "_failed",
        FieldPrimitiveType.Boolean,
        (s) -> s.isFailed(),
        (s, v) -> s.setFailed(v));
    m.addReferenceCollection(
        "failedDevices",
        PushNotification._FAILEDDEVICES,
        "_failed_devices_id",
        "_push_notification_failed_devices_d63aa4",
        false,
        getType("UserDevice"),
        (s) -> s.getFailedDevices(),
        (s, v) -> s.setFailedDevices(v));
  }

  private void addReportFields() {
    DModel<Report> m = getType2("Report");
    m.addPrimitive(
        "model",
        Report._MODEL,
        "_model",
        FieldPrimitiveType.String,
        (s) -> s.getModel(),
        (s, v) -> s.setModel(v));
    m.addPrimitive(
        "name",
        Report._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addReference(
        "cells",
        Report._CELLS,
        "_cells_doc",
        true,
        getType("ReportCell"),
        (s) -> s.getCells(),
        (s, v) -> s.setCells(v));
    m.addReference(
        "config",
        Report._CONFIG,
        "_config_doc",
        true,
        getType("ReportBaseConfig"),
        (s) -> s.getConfig(),
        (s, v) -> s.setConfig(v));
    m.addReferenceCollection(
        "filters",
        Report._FILTERS,
        "_filters_id",
        null,
        true,
        getType("ReportFilter"),
        (s) -> s.getFilters(),
        (s, v) -> s.setFilters(v));
    m.addReference(
        "criteria",
        Report._CRITERIA,
        "_criteria_doc",
        true,
        getType("ReportRuleSet"),
        (s) -> s.getCriteria(),
        (s, v) -> s.setCriteria(v));
    m.addFlatCollection(
        "flatReportRule",
        Report._FLATREPORTRULE,
        "_flat_report_rule_id",
        "_report_flat_report_rule",
        getType("ReportRule"),
        (s) -> s.getFlatReportRule(),
        "criteria");
  }

  private void addReportBarChartConfigFields() {
    DModel<ReportBarChartConfig> m = getType2("ReportBarChartConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addEnum(
        "type",
        ReportBarChartConfig._TYPE,
        "_type",
        getType("ReportBarChartType"),
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addReferenceCollection(
        "xAxes",
        ReportBarChartConfig._XAXES,
        "_x_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getXAxes(),
        (s, v) -> s.setXAxes(v));
    m.addReferenceCollection(
        "yAxes",
        ReportBarChartConfig._YAXES,
        "_y_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getYAxes(),
        (s, v) -> s.setYAxes(v));
    m.addReference(
        "legend",
        ReportBarChartConfig._LEGEND,
        "_legend_doc",
        true,
        getType("ReportField"),
        (s) -> s.getLegend(),
        (s, v) -> s.setLegend(v));
    m.addReferenceCollection(
        "smallMultiples",
        ReportBarChartConfig._SMALLMULTIPLES,
        "_small_multiples_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getSmallMultiples(),
        (s, v) -> s.setSmallMultiples(v));
    m.addReferenceCollection(
        "tooltips",
        ReportBarChartConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addReportBaseConfigFields() {
    DModel<ReportBaseConfig> m = getType2("ReportBaseConfig");
  }

  private void addReportCardConfigFields() {
    DModel<ReportCardConfig> m = getType2("ReportCardConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReference(
        "value",
        ReportCardConfig._VALUE,
        "_value_doc",
        true,
        getType("ReportField"),
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
  }

  private void addReportCellFields() {
    DModel<ReportCell> m = getType2("ReportCell");
    m.addEnum(
        "type",
        ReportCell._TYPE,
        "_type",
        getType("ReportCellType"),
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addPrimitive(
        "x", ReportCell._X, "_x", FieldPrimitiveType.Integer, (s) -> s.getX(), (s, v) -> s.setX(v));
    m.addPrimitive(
        "y", ReportCell._Y, "_y", FieldPrimitiveType.Integer, (s) -> s.getY(), (s, v) -> s.setY(v));
    m.addReference(
        "style",
        ReportCell._STYLE,
        "_style_doc",
        true,
        getType("ReportCellStyle"),
        (s) -> s.getStyle(),
        (s, v) -> s.setStyle(v));
    m.addPrimitive(
        "value",
        ReportCell._VALUE,
        "_value",
        FieldPrimitiveType.String,
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
  }

  private void addReportCellStyleFields() {
    DModel<ReportCellStyle> m = getType2("ReportCellStyle");
    m.addPrimitive(
        "width",
        ReportCellStyle._WIDTH,
        "_width",
        FieldPrimitiveType.Integer,
        (s) -> s.getWidth(),
        (s, v) -> s.setWidth(v));
    m.addPrimitive(
        "font",
        ReportCellStyle._FONT,
        "_font",
        FieldPrimitiveType.String,
        (s) -> s.getFont(),
        (s, v) -> s.setFont(v));
    m.addPrimitive(
        "fontSize",
        ReportCellStyle._FONTSIZE,
        "_font_size",
        FieldPrimitiveType.Integer,
        (s) -> s.getFontSize(),
        (s, v) -> s.setFontSize(v));
    m.addPrimitive(
        "textColor",
        ReportCellStyle._TEXTCOLOR,
        "_text_color",
        FieldPrimitiveType.String,
        (s) -> s.getTextColor(),
        (s, v) -> s.setTextColor(v));
    m.addPrimitive(
        "bgColor",
        ReportCellStyle._BGCOLOR,
        "_bg_color",
        FieldPrimitiveType.String,
        (s) -> s.getBgColor(),
        (s, v) -> s.setBgColor(v));
    m.addEnum(
        "vAllign",
        ReportCellStyle._VALLIGN,
        "_v_allign",
        getType("ReportCellAllign"),
        (s) -> s.getVAllign(),
        (s, v) -> s.setVAllign(v));
    m.addEnum(
        "hAllign",
        ReportCellStyle._HALLIGN,
        "_h_allign",
        getType("ReportCellAllign"),
        (s) -> s.getHAllign(),
        (s, v) -> s.setHAllign(v));
  }

  private void addReportConfigFields() {
    DModel<ReportConfig> m = getType2("ReportConfig");
    m.addPrimitive(
        "identity",
        ReportConfig._IDENTITY,
        "_identity",
        FieldPrimitiveType.String,
        (s) -> s.getIdentity(),
        (s, v) -> s.setIdentity(v));
    m.addReferenceCollection(
        "values",
        ReportConfig._VALUES,
        "_values_id",
        "_report_config_values_a912b7",
        true,
        getType("ReportConfigOption"),
        (s) -> s.getValues(),
        (s, v) -> s.setValues(v));
  }

  private void addReportConfigOptionFields() {
    DModel<ReportConfigOption> m = getType2("ReportConfigOption");
    m.addPrimitive(
        "identity",
        ReportConfigOption._IDENTITY,
        "_identity",
        FieldPrimitiveType.String,
        (s) -> s.getIdentity(),
        (s, v) -> s.setIdentity(v));
    m.addPrimitive(
        "value",
        ReportConfigOption._VALUE,
        "_value",
        FieldPrimitiveType.String,
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
  }

  private void addReportDataFields() {
    DModel<ReportData> m = getType2("ReportData");
    m.addReferenceCollection(
        "sections",
        ReportData._SECTIONS,
        "_sections_id",
        "_report_data_sections_21fc0e",
        true,
        getType("ReportDataSection"),
        (s) -> s.getSections(),
        (s, v) -> s.setSections(v));
    m.addReferenceCollection(
        "rows",
        ReportData._ROWS,
        "_rows_id",
        "_report_data_rows_26b903",
        true,
        getType("ReportDataRow"),
        (s) -> s.getRows(),
        (s, v) -> s.setRows(v));
  }

  private void addReportDataRowFields() {
    DModel<ReportDataRow> m = getType2("ReportDataRow");
    m.addPrimitiveCollection(
        "row",
        ReportDataRow._ROW,
        "_row",
        "_report_data_row_row_61d5e0",
        FieldPrimitiveType.String,
        (s) -> s.getRow(),
        (s, v) -> s.setRow(v));
  }

  private void addReportDataSectionFields() {
    DModel<ReportDataSection> m = getType2("ReportDataSection");
    m.addPrimitive(
        "header",
        ReportDataSection._HEADER,
        "_header",
        FieldPrimitiveType.String,
        (s) -> s.getHeader(),
        (s, v) -> s.setHeader(v));
    m.addPrimitiveCollection(
        "columns",
        ReportDataSection._COLUMNS,
        "_columns",
        "_report_data_section_columns_333332",
        FieldPrimitiveType.String,
        (s) -> s.getColumns(),
        (s, v) -> s.setColumns(v));
  }

  private void addReportFieldFields() {
    DModel<ReportField> m = getType2("ReportField");
    m.addPrimitive(
        "name",
        ReportField._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "field",
        ReportField._FIELD,
        "_field",
        FieldPrimitiveType.String,
        (s) -> s.getField(),
        (s, v) -> s.setField(v));
    m.addEnum(
        "aggregate",
        ReportField._AGGREGATE,
        "_aggregate",
        getType("ReportAggregateType"),
        (s) -> s.getAggregate(),
        (s, v) -> s.setAggregate(v));
  }

  private void addReportFilterFields() {
    DModel<ReportFilter> m = getType2("ReportFilter");
  }

  private void addReportFunnelChartConfigFields() {
    DModel<ReportFunnelChartConfig> m = getType2("ReportFunnelChartConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReferenceCollection(
        "categoryFields",
        ReportFunnelChartConfig._CATEGORYFIELDS,
        "_category_fields_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getCategoryFields(),
        (s, v) -> s.setCategoryFields(v));
    m.addReferenceCollection(
        "values",
        ReportFunnelChartConfig._VALUES,
        "_values_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getValues(),
        (s, v) -> s.setValues(v));
    m.addReferenceCollection(
        "tooltips",
        ReportFunnelChartConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addReportGuageConfigFields() {
    DModel<ReportGuageConfig> m = getType2("ReportGuageConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReferenceCollection(
        "value",
        ReportGuageConfig._VALUE,
        "_value_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
    m.addReferenceCollection(
        "min",
        ReportGuageConfig._MIN,
        "_min_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getMin(),
        (s, v) -> s.setMin(v));
    m.addReferenceCollection(
        "max",
        ReportGuageConfig._MAX,
        "_max_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getMax(),
        (s, v) -> s.setMax(v));
    m.addReferenceCollection(
        "target",
        ReportGuageConfig._TARGET,
        "_target_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTarget(),
        (s, v) -> s.setTarget(v));
    m.addReferenceCollection(
        "tooltips",
        ReportGuageConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addReportKPIConfigFields() {
    DModel<ReportKPIConfig> m = getType2("ReportKPIConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReference(
        "value",
        ReportKPIConfig._VALUE,
        "_value_doc",
        true,
        getType("ReportField"),
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
    m.addReferenceCollection(
        "target",
        ReportKPIConfig._TARGET,
        "_target_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTarget(),
        (s, v) -> s.setTarget(v));
    m.addReference(
        "trend",
        ReportKPIConfig._TREND,
        "_trend_doc",
        true,
        getType("ReportField"),
        (s) -> s.getTrend(),
        (s, v) -> s.setTrend(v));
  }

  private void addReportKeyInfluencerConfigFields() {
    DModel<ReportKeyInfluencerConfig> m = getType2("ReportKeyInfluencerConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReference(
        "analyze",
        ReportKeyInfluencerConfig._ANALYZE,
        "_analyze_doc",
        true,
        getType("ReportField"),
        (s) -> s.getAnalyze(),
        (s, v) -> s.setAnalyze(v));
    m.addReferenceCollection(
        "eexplainBy",
        ReportKeyInfluencerConfig._EEXPLAINBY,
        "_eexplain_by_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getEexplainBy(),
        (s, v) -> s.setEexplainBy(v));
    m.addReferenceCollection(
        "expandBy",
        ReportKeyInfluencerConfig._EXPANDBY,
        "_expand_by_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getExpandBy(),
        (s, v) -> s.setExpandBy(v));
  }

  private void addReportLineAndAreaChartConfigFields() {
    DModel<ReportLineAndAreaChartConfig> m = getType2("ReportLineAndAreaChartConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addEnum(
        "type",
        ReportLineAndAreaChartConfig._TYPE,
        "_type",
        getType("ReportLineAndAreaChartType"),
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addReferenceCollection(
        "xAxes",
        ReportLineAndAreaChartConfig._XAXES,
        "_x_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getXAxes(),
        (s, v) -> s.setXAxes(v));
    m.addReferenceCollection(
        "yAxes",
        ReportLineAndAreaChartConfig._YAXES,
        "_y_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getYAxes(),
        (s, v) -> s.setYAxes(v));
    m.addReferenceCollection(
        "secondaryYAxes",
        ReportLineAndAreaChartConfig._SECONDARYYAXES,
        "_secondary_y_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getSecondaryYAxes(),
        (s, v) -> s.setSecondaryYAxes(v));
    m.addReferenceCollection(
        "legend",
        ReportLineAndAreaChartConfig._LEGEND,
        "_legend_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getLegend(),
        (s, v) -> s.setLegend(v));
    m.addReferenceCollection(
        "smallMultiples",
        ReportLineAndAreaChartConfig._SMALLMULTIPLES,
        "_small_multiples_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getSmallMultiples(),
        (s, v) -> s.setSmallMultiples(v));
    m.addReferenceCollection(
        "tooltips",
        ReportLineAndAreaChartConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addReportLineAndColumnChartConfigFields() {
    DModel<ReportLineAndColumnChartConfig> m = getType2("ReportLineAndColumnChartConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addEnum(
        "type",
        ReportLineAndColumnChartConfig._TYPE,
        "_type",
        getType("ReportLineAndColumnChartType"),
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addReferenceCollection(
        "xAxes",
        ReportLineAndColumnChartConfig._XAXES,
        "_x_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getXAxes(),
        (s, v) -> s.setXAxes(v));
    m.addReferenceCollection(
        "columnYAxes",
        ReportLineAndColumnChartConfig._COLUMNYAXES,
        "_column_y_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getColumnYAxes(),
        (s, v) -> s.setColumnYAxes(v));
    m.addReferenceCollection(
        "lineYAxes",
        ReportLineAndColumnChartConfig._LINEYAXES,
        "_line_y_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getLineYAxes(),
        (s, v) -> s.setLineYAxes(v));
    m.addReferenceCollection(
        "columnLegend",
        ReportLineAndColumnChartConfig._COLUMNLEGEND,
        "_column_legend_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getColumnLegend(),
        (s, v) -> s.setColumnLegend(v));
    m.addReferenceCollection(
        "smallMultiples",
        ReportLineAndColumnChartConfig._SMALLMULTIPLES,
        "_small_multiples_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getSmallMultiples(),
        (s, v) -> s.setSmallMultiples(v));
    m.addReferenceCollection(
        "tooltips",
        ReportLineAndColumnChartConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addReportMapConfigFields() {
    DModel<ReportMapConfig> m = getType2("ReportMapConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addEnum(
        "type",
        ReportMapConfig._TYPE,
        "_type",
        getType("ReportMapType"),
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addReferenceCollection(
        "location",
        ReportMapConfig._LOCATION,
        "_location_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getLocation(),
        (s, v) -> s.setLocation(v));
    m.addReferenceCollection(
        "legend",
        ReportMapConfig._LEGEND,
        "_legend_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getLegend(),
        (s, v) -> s.setLegend(v));
    m.addReference(
        "latitude",
        ReportMapConfig._LATITUDE,
        "_latitude_doc",
        true,
        getType("ReportField"),
        (s) -> s.getLatitude(),
        (s, v) -> s.setLatitude(v));
    m.addReference(
        "longitude",
        ReportMapConfig._LONGITUDE,
        "_longitude_doc",
        true,
        getType("ReportField"),
        (s) -> s.getLongitude(),
        (s, v) -> s.setLongitude(v));
    m.addReferenceCollection(
        "size",
        ReportMapConfig._SIZE,
        "_size_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getSize(),
        (s, v) -> s.setSize(v));
    m.addReferenceCollection(
        "tooltips",
        ReportMapConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addReportMatrixConfigFields() {
    DModel<ReportMatrixConfig> m = getType2("ReportMatrixConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReferenceCollection(
        "columns",
        ReportMatrixConfig._COLUMNS,
        "_columns_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getColumns(),
        (s, v) -> s.setColumns(v));
    m.addReferenceCollection(
        "rows",
        ReportMatrixConfig._ROWS,
        "_rows_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getRows(),
        (s, v) -> s.setRows(v));
    m.addReferenceCollection(
        "values",
        ReportMatrixConfig._VALUES,
        "_values_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getValues(),
        (s, v) -> s.setValues(v));
  }

  private void addReportModelFields() {
    DModel<ReportModel> m = getType2("ReportModel");
    m.addPrimitive(
        "name",
        ReportModel._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addReferenceCollection(
        "properties",
        ReportModel._PROPERTIES,
        "_properties_id",
        "_report_model_properties_748e60",
        true,
        getType("ReportProperty"),
        (s) -> s.getProperties(),
        (s, v) -> s.setProperties(v));
  }

  private void addReportMultiRowCardConfigFields() {
    DModel<ReportMultiRowCardConfig> m = getType2("ReportMultiRowCardConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReferenceCollection(
        "values",
        ReportMultiRowCardConfig._VALUES,
        "_values_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getValues(),
        (s, v) -> s.setValues(v));
  }

  private void addReportNamedConditionFields() {
    DModel<ReportNamedCondition> m = getType2("ReportNamedCondition");
    m.addPrimitive(
        "name",
        ReportNamedCondition._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addReference(
        "condition",
        ReportNamedCondition._CONDITION,
        "_condition_doc",
        true,
        getType("ReportRule"),
        (s) -> s.getCondition(),
        (s, v) -> s.setCondition(v));
  }

  private void addReportNamedConditionFilterFields() {
    DModel<ReportNamedConditionFilter> m = getType2("ReportNamedConditionFilter");
    m.setParent(getType("ReportFilter"));
    m.addPrimitive(
        "name",
        ReportNamedConditionFilter._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addReferenceCollection(
        "conditions",
        ReportNamedConditionFilter._CONDITIONS,
        "_conditions_id",
        null,
        false,
        getType("ReportNamedCondition"),
        (s) -> s.getConditions(),
        (s, v) -> s.setConditions(v));
  }

  private void addReportPieChartConfigFields() {
    DModel<ReportPieChartConfig> m = getType2("ReportPieChartConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addEnum(
        "type",
        ReportPieChartConfig._TYPE,
        "_type",
        getType("ReportPieChartType"),
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addReferenceCollection(
        "legend",
        ReportPieChartConfig._LEGEND,
        "_legend_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getLegend(),
        (s, v) -> s.setLegend(v));
    m.addReferenceCollection(
        "values",
        ReportPieChartConfig._VALUES,
        "_values_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getValues(),
        (s, v) -> s.setValues(v));
    m.addReferenceCollection(
        "details",
        ReportPieChartConfig._DETAILS,
        "_details_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getDetails(),
        (s, v) -> s.setDetails(v));
    m.addReferenceCollection(
        "tooltips",
        ReportPieChartConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addReportPropertyFields() {
    DModel<ReportProperty> m = getType2("ReportProperty");
    m.addPrimitive(
        "name",
        ReportProperty._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "property",
        ReportProperty._PROPERTY,
        "_property",
        FieldPrimitiveType.String,
        (s) -> s.getProperty(),
        (s, v) -> s.setProperty(v));
    m.addPrimitive(
        "type",
        ReportProperty._TYPE,
        "_type",
        FieldPrimitiveType.String,
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addPrimitive(
        "child",
        ReportProperty._CHILD,
        "_child",
        FieldPrimitiveType.Boolean,
        (s) -> s.isChild(),
        (s, v) -> s.setChild(v));
    m.addPrimitive(
        "collection",
        ReportProperty._COLLECTION,
        "_collection",
        FieldPrimitiveType.Boolean,
        (s) -> s.isCollection(),
        (s, v) -> s.setCollection(v));
    m.addPrimitive(
        "isEnum",
        ReportProperty._ISENUM,
        "_is_enum",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsEnum(),
        (s, v) -> s.setIsEnum(v));
    m.addPrimitive(
        "isReference",
        ReportProperty._ISREFERENCE,
        "_is_reference",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsReference(),
        (s, v) -> s.setIsReference(v));
  }

  private void addReportPropertyFilterFields() {
    DModel<ReportPropertyFilter> m = getType2("ReportPropertyFilter");
    m.setParent(getType("ReportFilter"));
    m.addPrimitive(
        "name",
        ReportPropertyFilter._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "property",
        ReportPropertyFilter._PROPERTY,
        "_property",
        FieldPrimitiveType.String,
        (s) -> s.getProperty(),
        (s, v) -> s.setProperty(v));
    m.addPrimitive(
        "type",
        ReportPropertyFilter._TYPE,
        "_type",
        FieldPrimitiveType.String,
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addPrimitive(
        "isEnum",
        ReportPropertyFilter._ISENUM,
        "_is_enum",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsEnum(),
        (s, v) -> s.setIsEnum(v));
    m.addPrimitive(
        "isReference",
        ReportPropertyFilter._ISREFERENCE,
        "_is_reference",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsReference(),
        (s, v) -> s.setIsReference(v));
    m.addPrimitive(
        "allowMultiple",
        ReportPropertyFilter._ALLOWMULTIPLE,
        "_allow_multiple",
        FieldPrimitiveType.Boolean,
        (s) -> s.isAllowMultiple(),
        (s, v) -> s.setAllowMultiple(v));
    m.addPrimitive(
        "applyRange",
        ReportPropertyFilter._APPLYRANGE,
        "_apply_range",
        FieldPrimitiveType.Boolean,
        (s) -> s.isApplyRange(),
        (s, v) -> s.setApplyRange(v));
  }

  private void addReportRuleFields() {
    DModel<ReportRule> m = getType2("ReportRule");
    m.addReference(
        "parent",
        ReportRule._PARENT,
        "_parent_doc",
        false,
        getType("ReportRule"),
        (s) -> s.getParent(),
        (s, v) -> s.setParent(v));
  }

  private void addReportRuleSetFields() {
    DModel<ReportRuleSet> m = getType2("ReportRuleSet");
    m.setParent(getType("ReportRule"));
    m.addPrimitive(
        "all",
        ReportRuleSet._ALL,
        "_all",
        FieldPrimitiveType.Boolean,
        (s) -> s.isAll(),
        (s, v) -> s.setAll(v));
    m.addReferenceCollection(
        "rules",
        ReportRuleSet._RULES,
        "_rules_id",
        null,
        true,
        getType("ReportRule"),
        (s) -> s.getRules(),
        (s, v) -> s.setRules(v));
  }

  private void addReportScatterChartConfigFields() {
    DModel<ReportScatterChartConfig> m = getType2("ReportScatterChartConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReferenceCollection(
        "values",
        ReportScatterChartConfig._VALUES,
        "_values_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getValues(),
        (s, v) -> s.setValues(v));
    m.addReferenceCollection(
        "xAxes",
        ReportScatterChartConfig._XAXES,
        "_x_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getXAxes(),
        (s, v) -> s.setXAxes(v));
    m.addReferenceCollection(
        "yAxes",
        ReportScatterChartConfig._YAXES,
        "_y_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getYAxes(),
        (s, v) -> s.setYAxes(v));
    m.addReferenceCollection(
        "size",
        ReportScatterChartConfig._SIZE,
        "_size_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getSize(),
        (s, v) -> s.setSize(v));
    m.addReferenceCollection(
        "legends",
        ReportScatterChartConfig._LEGENDS,
        "_legends_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getLegends(),
        (s, v) -> s.setLegends(v));
    m.addReferenceCollection(
        "playAxis",
        ReportScatterChartConfig._PLAYAXIS,
        "_play_axis_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getPlayAxis(),
        (s, v) -> s.setPlayAxis(v));
    m.addReferenceCollection(
        "tooltips",
        ReportScatterChartConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addReportSingleRuleFields() {
    DModel<ReportSingleRule> m = getType2("ReportSingleRule");
    m.setParent(getType("ReportRule"));
    m.addPrimitive(
        "field",
        ReportSingleRule._FIELD,
        "_field",
        FieldPrimitiveType.String,
        (s) -> s.getField(),
        (s, v) -> s.setField(v));
    m.addPrimitive(
        "type",
        ReportSingleRule._TYPE,
        "_type",
        FieldPrimitiveType.String,
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addEnum(
        "operator",
        ReportSingleRule._OPERATOR,
        "_operator",
        getType("ReportRuleOperator"),
        (s) -> s.getOperator(),
        (s, v) -> s.setOperator(v));
    m.addPrimitive(
        "value1",
        ReportSingleRule._VALUE1,
        "_value1",
        FieldPrimitiveType.String,
        (s) -> s.getValue1(),
        (s, v) -> s.setValue1(v));
    m.addPrimitive(
        "value2",
        ReportSingleRule._VALUE2,
        "_value2",
        FieldPrimitiveType.String,
        (s) -> s.getValue2(),
        (s, v) -> s.setValue2(v));
    m.addReference(
        "filter",
        ReportSingleRule._FILTER,
        "_filter_doc",
        false,
        getType("ReportFilter"),
        (s) -> s.getFilter(),
        (s, v) -> s.setFilter(v));
    m.addPrimitive(
        "fieldValue1",
        ReportSingleRule._FIELDVALUE1,
        "_field_value1",
        FieldPrimitiveType.String,
        (s) -> s.getFieldValue1(),
        (s, v) -> s.setFieldValue1(v));
    m.addPrimitive(
        "fieldValue2",
        ReportSingleRule._FIELDVALUE2,
        "_field_value2",
        FieldPrimitiveType.String,
        (s) -> s.getFieldValue2(),
        (s, v) -> s.setFieldValue2(v));
    m.addEnum(
        "fieldFrom",
        ReportSingleRule._FIELDFROM,
        "_field_from",
        getType("ReportFieldFromType"),
        (s) -> s.getFieldFrom(),
        (s, v) -> s.setFieldFrom(v));
  }

  private void addReportSlicerConfigFields() {
    DModel<ReportSlicerConfig> m = getType2("ReportSlicerConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReferenceCollection(
        "fields",
        ReportSlicerConfig._FIELDS,
        "_fields_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getFields(),
        (s, v) -> s.setFields(v));
  }

  private void addReportTableConfigFields() {
    DModel<ReportTableConfig> m = getType2("ReportTableConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReferenceCollection(
        "columns",
        ReportTableConfig._COLUMNS,
        "_columns_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getColumns(),
        (s, v) -> s.setColumns(v));
  }

  private void addReportWaterfallChartConfigFields() {
    DModel<ReportWaterfallChartConfig> m = getType2("ReportWaterfallChartConfig");
    m.setParent(getType("ReportBaseConfig"));
    m.addReferenceCollection(
        "category",
        ReportWaterfallChartConfig._CATEGORY,
        "_category_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getCategory(),
        (s, v) -> s.setCategory(v));
    m.addReferenceCollection(
        "breakdownFields",
        ReportWaterfallChartConfig._BREAKDOWNFIELDS,
        "_breakdown_fields_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getBreakdownFields(),
        (s, v) -> s.setBreakdownFields(v));
    m.addReferenceCollection(
        "yAxes",
        ReportWaterfallChartConfig._YAXES,
        "_y_axes_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getYAxes(),
        (s, v) -> s.setYAxes(v));
    m.addReferenceCollection(
        "tooltips",
        ReportWaterfallChartConfig._TOOLTIPS,
        "_tooltips_id",
        null,
        true,
        getType("ReportField"),
        (s) -> s.getTooltips(),
        (s, v) -> s.setTooltips(v));
  }

  private void addSMSMessageFields() {
    DModel<SMSMessage> m = getType2("SMSMessage");
    m.addPrimitive(
        "from",
        SMSMessage._FROM,
        "_from",
        FieldPrimitiveType.String,
        (s) -> s.getFrom(),
        (s, v) -> s.setFrom(v));
    m.addPrimitiveCollection(
        "to",
        SMSMessage._TO,
        "_to",
        "_sms_message_to_0f6401",
        FieldPrimitiveType.String,
        (s) -> s.getTo(),
        (s, v) -> s.setTo(v));
    m.addPrimitive(
        "body",
        SMSMessage._BODY,
        "_body",
        FieldPrimitiveType.String,
        (s) -> s.getBody(),
        (s, v) -> s.setBody(v));
    m.addPrimitive(
        "createdOn",
        SMSMessage._CREATEDON,
        "_created_on",
        FieldPrimitiveType.DateTime,
        (s) -> s.getCreatedOn(),
        (s, v) -> s.setCreatedOn(v));
    m.addPrimitive(
        "dltTemplateId",
        SMSMessage._DLTTEMPLATEID,
        "_dlt_template_id",
        FieldPrimitiveType.String,
        (s) -> s.getDltTemplateId(),
        (s, v) -> s.setDltTemplateId(v));
  }

  private void addSalesOrderFields() {
    DModel<SalesOrder> m = getType2("SalesOrder");
    m.addReference(
        "store",
        SalesOrder._STORE,
        "_store_id",
        false,
        getType("Store"),
        (s) -> s.getStore(),
        (s, v) -> s.setStore(v));
    m.addReference(
        "warehouse",
        SalesOrder._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addPrimitive(
        "orderNumber",
        SalesOrder._ORDERNUMBER,
        "_order_number",
        FieldPrimitiveType.String,
        (s) -> s.getOrderNumber(),
        (s, v) -> s.setOrderNumber(v));
    m.addPrimitive(
        "orderDate",
        SalesOrder._ORDERDATE,
        "_order_date",
        FieldPrimitiveType.DateTime,
        (s) -> s.getOrderDate(),
        (s, v) -> s.setOrderDate(v));
    m.addPrimitive(
        "customerName",
        SalesOrder._CUSTOMERNAME,
        "_customer_name",
        FieldPrimitiveType.String,
        (s) -> s.getCustomerName(),
        (s, v) -> s.setCustomerName(v));
    m.addPrimitive(
        "customerPhone",
        SalesOrder._CUSTOMERPHONE,
        "_customer_phone",
        FieldPrimitiveType.String,
        (s) -> s.getCustomerPhone(),
        (s, v) -> s.setCustomerPhone(v));
    m.addEnum(
        "status",
        SalesOrder._STATUS,
        "_status",
        getType("SalesOrderStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "subtotal",
            SalesOrder._SUBTOTAL,
            "_subtotal",
            FieldPrimitiveType.Double,
            (s) -> s.getSubtotal(),
            (s, v) -> s.setSubtotal(v))
        .readOnly();
    m.addPrimitive(
        "discountAmount",
        SalesOrder._DISCOUNTAMOUNT,
        "_discount_amount",
        FieldPrimitiveType.Double,
        (s) -> s.getDiscountAmount(),
        (s, v) -> s.setDiscountAmount(v));
    m.addPrimitive(
        "taxAmount",
        SalesOrder._TAXAMOUNT,
        "_tax_amount",
        FieldPrimitiveType.Double,
        (s) -> s.getTaxAmount(),
        (s, v) -> s.setTaxAmount(v));
    m.addPrimitive(
            "totalAmount",
            SalesOrder._TOTALAMOUNT,
            "_total_amount",
            FieldPrimitiveType.Double,
            (s) -> s.getTotalAmount(),
            (s, v) -> s.setTotalAmount(v))
        .readOnly();
    m.addEnum(
        "paymentStatus",
        SalesOrder._PAYMENTSTATUS,
        "_payment_status",
        getType("PaymentStatus"),
        (s) -> s.getPaymentStatus(),
        (s, v) -> s.setPaymentStatus(v));
    m.addReference(
            "soldBy",
            SalesOrder._SOLDBY,
            "_sold_by_id",
            false,
            getType("User"),
            (s) -> s.getSoldBy(),
            (s, v) -> s.setSoldBy(v))
        .readOnly();
    m.addInverseCollection(
        "lines",
        SalesOrder._LINES,
        "_sales_order_id",
        getType("SalesOrderLine"),
        (s) -> s.getLines());
    m.addReference(
        "organization",
        SalesOrder._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addSalesOrderItemRequestFields() {
    DModel<SalesOrderItemRequest> m = getType2("SalesOrderItemRequest");
    m.addReference(
        "salesOrder",
        SalesOrderItemRequest._SALESORDER,
        "null_id",
        false,
        getType("SalesOrder"),
        (s) -> s.getSalesOrder(),
        (s, v) -> s.setSalesOrder(v));
  }

  private void addSalesOrderLineFields() {
    DModel<SalesOrderLine> m = getType2("SalesOrderLine");
    m.addReference(
        "product",
        SalesOrderLine._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addPrimitive(
        "quantity",
        SalesOrderLine._QUANTITY,
        "_quantity",
        FieldPrimitiveType.Double,
        (s) -> s.getQuantity(),
        (s, v) -> s.setQuantity(v));
    m.addPrimitive(
        "unitPrice",
        SalesOrderLine._UNITPRICE,
        "_unit_price",
        FieldPrimitiveType.Double,
        (s) -> s.getUnitPrice(),
        (s, v) -> s.setUnitPrice(v));
    m.addPrimitive(
        "discount",
        SalesOrderLine._DISCOUNT,
        "_discount",
        FieldPrimitiveType.Double,
        (s) -> s.getDiscount(),
        (s, v) -> s.setDiscount(v));
    m.addPrimitive(
            "lineTotal",
            SalesOrderLine._LINETOTAL,
            "_line_total",
            FieldPrimitiveType.Double,
            (s) -> s.getLineTotal(),
            (s, v) -> s.setLineTotal(v))
        .readOnly();
    m.addPrimitive(
        "batchNumber",
        SalesOrderLine._BATCHNUMBER,
        "_batch_number",
        FieldPrimitiveType.String,
        (s) -> s.getBatchNumber(),
        (s, v) -> s.setBatchNumber(v));
    m.addReference(
        "salesOrder",
        SalesOrderLine._SALESORDER,
        "_sales_order_id",
        false,
        getType("SalesOrder"),
        (s) -> s.getSalesOrder(),
        (s, v) -> s.setSalesOrder(v));
  }

  private void addSalesOrdersByStoreRequestFields() {
    DModel<SalesOrdersByStoreRequest> m = getType2("SalesOrdersByStoreRequest");
    m.addReference(
        "store",
        SalesOrdersByStoreRequest._STORE,
        "null_id",
        false,
        getType("Store"),
        (s) -> s.getStore(),
        (s, v) -> s.setStore(v));
  }

  private void addSalesReturnFields() {
    DModel<SalesReturn> m = getType2("SalesReturn");
    m.addReference(
        "salesOrder",
        SalesReturn._SALESORDER,
        "_sales_order_id",
        false,
        getType("SalesOrder"),
        (s) -> s.getSalesOrder(),
        (s, v) -> s.setSalesOrder(v));
    m.addReference(
        "store",
        SalesReturn._STORE,
        "_store_id",
        false,
        getType("Store"),
        (s) -> s.getStore(),
        (s, v) -> s.setStore(v));
    m.addReference(
        "warehouse",
        SalesReturn._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addPrimitive(
        "returnNumber",
        SalesReturn._RETURNNUMBER,
        "_return_number",
        FieldPrimitiveType.String,
        (s) -> s.getReturnNumber(),
        (s, v) -> s.setReturnNumber(v));
    m.addPrimitive(
        "returnDate",
        SalesReturn._RETURNDATE,
        "_return_date",
        FieldPrimitiveType.DateTime,
        (s) -> s.getReturnDate(),
        (s, v) -> s.setReturnDate(v));
    m.addEnum(
        "status",
        SalesReturn._STATUS,
        "_status",
        getType("SalesReturnStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addEnum(
        "reason",
        SalesReturn._REASON,
        "_reason",
        getType("ReturnReason"),
        (s) -> s.getReason(),
        (s, v) -> s.setReason(v));
    m.addPrimitive(
            "refundAmount",
            SalesReturn._REFUNDAMOUNT,
            "_refund_amount",
            FieldPrimitiveType.Double,
            (s) -> s.getRefundAmount(),
            (s, v) -> s.setRefundAmount(v))
        .readOnly();
    m.addReference(
            "processedBy",
            SalesReturn._PROCESSEDBY,
            "_processed_by_id",
            false,
            getType("User"),
            (s) -> s.getProcessedBy(),
            (s, v) -> s.setProcessedBy(v))
        .readOnly();
    m.addInverseCollection(
        "lines",
        SalesReturn._LINES,
        "_sales_return_id",
        getType("SalesReturnLine"),
        (s) -> s.getLines());
    m.addReference(
        "organization",
        SalesReturn._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addSalesReturnLineFields() {
    DModel<SalesReturnLine> m = getType2("SalesReturnLine");
    m.addReference(
        "product",
        SalesReturnLine._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addPrimitive(
        "quantity",
        SalesReturnLine._QUANTITY,
        "_quantity",
        FieldPrimitiveType.Double,
        (s) -> s.getQuantity(),
        (s, v) -> s.setQuantity(v));
    m.addPrimitive(
        "unitPrice",
        SalesReturnLine._UNITPRICE,
        "_unit_price",
        FieldPrimitiveType.Double,
        (s) -> s.getUnitPrice(),
        (s, v) -> s.setUnitPrice(v));
    m.addPrimitive(
            "lineTotal",
            SalesReturnLine._LINETOTAL,
            "_line_total",
            FieldPrimitiveType.Double,
            (s) -> s.getLineTotal(),
            (s, v) -> s.setLineTotal(v))
        .readOnly();
    m.addPrimitive(
        "batchNumber",
        SalesReturnLine._BATCHNUMBER,
        "_batch_number",
        FieldPrimitiveType.String,
        (s) -> s.getBatchNumber(),
        (s, v) -> s.setBatchNumber(v));
    m.addReference(
        "salesReturn",
        SalesReturnLine._SALESRETURN,
        "_sales_return_id",
        false,
        getType("SalesReturn"),
        (s) -> s.getSalesReturn(),
        (s, v) -> s.setSalesReturn(v));
  }

  private void addStockAlertFields() {
    DModel<StockAlert> m = getType2("StockAlert");
    m.addReference(
        "warehouse",
        StockAlert._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addReference(
        "product",
        StockAlert._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addEnum(
        "alertType",
        StockAlert._ALERTTYPE,
        "_alert_type",
        getType("StockAlertType"),
        (s) -> s.getAlertType(),
        (s, v) -> s.setAlertType(v));
    m.addPrimitive(
        "currentQuantity",
        StockAlert._CURRENTQUANTITY,
        "_current_quantity",
        FieldPrimitiveType.Double,
        (s) -> s.getCurrentQuantity(),
        (s, v) -> s.setCurrentQuantity(v));
    m.addPrimitive(
        "threshold",
        StockAlert._THRESHOLD,
        "_threshold",
        FieldPrimitiveType.Double,
        (s) -> s.getThreshold(),
        (s, v) -> s.setThreshold(v));
    m.addPrimitive(
        "expiryDate",
        StockAlert._EXPIRYDATE,
        "_expiry_date",
        FieldPrimitiveType.Date,
        (s) -> s.getExpiryDate(),
        (s, v) -> s.setExpiryDate(v));
    m.addEnum(
        "status",
        StockAlert._STATUS,
        "_status",
        getType("AlertStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addReference(
        "acknowledgedBy",
        StockAlert._ACKNOWLEDGEDBY,
        "_acknowledged_by_id",
        false,
        getType("User"),
        (s) -> s.getAcknowledgedBy(),
        (s, v) -> s.setAcknowledgedBy(v));
    m.addPrimitive(
        "acknowledgedAt",
        StockAlert._ACKNOWLEDGEDAT,
        "_acknowledged_at",
        FieldPrimitiveType.DateTime,
        (s) -> s.getAcknowledgedAt(),
        (s, v) -> s.setAcknowledgedAt(v));
    m.addPrimitive(
            "createdAt",
            StockAlert._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addReference(
        "organization",
        StockAlert._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addStockBatchFields() {
    DModel<StockBatch> m = getType2("StockBatch");
    m.addReference(
        "product",
        StockBatch._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addReference(
        "warehouse",
        StockBatch._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addPrimitive(
        "batchNumber",
        StockBatch._BATCHNUMBER,
        "_batch_number",
        FieldPrimitiveType.String,
        (s) -> s.getBatchNumber(),
        (s, v) -> s.setBatchNumber(v));
    m.addPrimitive(
        "quantityOnHand",
        StockBatch._QUANTITYONHAND,
        "_quantity_on_hand",
        FieldPrimitiveType.Double,
        (s) -> s.getQuantityOnHand(),
        (s, v) -> s.setQuantityOnHand(v));
    m.addPrimitive(
        "manufacturingDate",
        StockBatch._MANUFACTURINGDATE,
        "_manufacturing_date",
        FieldPrimitiveType.Date,
        (s) -> s.getManufacturingDate(),
        (s, v) -> s.setManufacturingDate(v));
    m.addPrimitive(
        "expiryDate",
        StockBatch._EXPIRYDATE,
        "_expiry_date",
        FieldPrimitiveType.Date,
        (s) -> s.getExpiryDate(),
        (s, v) -> s.setExpiryDate(v));
    m.addPrimitive(
        "unitCost",
        StockBatch._UNITCOST,
        "_unit_cost",
        FieldPrimitiveType.Double,
        (s) -> s.getUnitCost(),
        (s, v) -> s.setUnitCost(v));
    m.addEnum(
        "status",
        StockBatch._STATUS,
        "_status",
        getType("BatchStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "createdAt",
            StockBatch._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addReference(
        "organization",
        StockBatch._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }
}
