package rest;

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
import classes.DashboardMetrics;
import classes.ExpiringBatches;
import classes.GoodsReceiptItem;
import classes.InventoryMovementsByDateRange;
import classes.LoginResult;
import classes.LowStockItems;
import classes.MovementReportRows;
import classes.OrganizationItem;
import classes.OutOfStockItems;
import classes.ProductItem;
import classes.ProductSearch;
import classes.ProductsByCategory;
import classes.PurchaseOrderItem;
import classes.PurchaseOrdersByStatus;
import classes.ReportFilterValue;
import classes.ReportInput;
import classes.ReportOutAttribute;
import classes.ReportOutCell;
import classes.ReportOutColumn;
import classes.ReportOutOption;
import classes.ReportOutRow;
import classes.ReportOutput;
import classes.SalesOrderItem;
import classes.SalesOrdersByStore;
import classes.StockTransferItem;
import classes.StockValuationReport;
import classes.StoreItem;
import classes.SupplierItem;
import classes.UnreadNotificationCount;
import classes.UserDevices;
import classes.UserProfileByUser;
import classes.VerificationDataByToken;
import classes.WarehouseStockByProduct;
import classes.WarehouseStockByWarehouse;
import classes.WarehousesByStore;
import d3e.core.SchemaConstants;
import gqltosql.schema.DModel;
import gqltosql.schema.FieldPrimitiveType;
import java.util.HashMap;
import java.util.Map;

public class StructSchema1 {
  private Map<String, DModel<?>> allTypes = new HashMap<>();

  public StructSchema1(Map<String, DModel<?>> allTypes) {
    this.allTypes = allTypes;
  }

  public void createAllTables() {
    addReportInputFields();
    addReportFilterValueFields();
    addReportOutputFields();
    addReportOutOptionFields();
    addReportOutColumnFields();
    addReportOutAttributeFields();
    addReportOutRowFields();
    addReportOutCellFields();
    addLoginResultFields();
    addAllDevicesFields();
    addUserDevicesFields();
    addVerificationDataByTokenFields();
    addAllAuditLogsFields();
    addAllGoodsReceiptsFields();
    addAllInAppNotificationsFields();
    addAllInventoryAdjustmentsFields();
    addAllInventoryMovementsFields();
    addAllOrganizationsFields();
    addAllProductCategoriesFields();
    addAllProductsFields();
    addAllPurchaseOrdersFields();
    addAllSalesOrdersFields();
    addAllSalesReturnsFields();
    addAllStockAlertsFields();
    addAllStockBatchesFields();
    addAllStockTransfersFields();
    addAllStoresFields();
    addAllSuppliersFields();
    addAllUnitOfMeasuresFields();
    addAllUserInvitationsFields();
    addAllUserProfilesFields();
    addAllWarehousesFields();
    addDashboardMetricsFields();
    addExpiringBatchesFields();
    addGoodsReceiptItemFields();
    addInventoryMovementsByDateRangeFields();
    addLowStockItemsFields();
    addMovementReportRowsFields();
    addOrganizationItemFields();
    addOutOfStockItemsFields();
    addProductItemFields();
    addProductSearchFields();
    addProductsByCategoryFields();
    addPurchaseOrderItemFields();
    addPurchaseOrdersByStatusFields();
    addSalesOrderItemFields();
    addSalesOrdersByStoreFields();
    addStockTransferItemFields();
    addStockValuationReportFields();
    addStoreItemFields();
    addSupplierItemFields();
    addUnreadNotificationCountFields();
    addUserProfileByUserFields();
    addWarehouseStockByProductFields();
    addWarehouseStockByWarehouseFields();
    addWarehousesByStoreFields();
  }

  public DModel<?> getType(String type) {
    return allTypes.get(type);
  }

  public <T> DModel<T> getType2(String type) {
    return ((DModel<T>) allTypes.get(type));
  }

  private void addReportInputFields() {
    DModel<ReportInput> m = getType2("ReportInput");
    m.addPrimitive(
        "name",
        ReportInput._NAME,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "value",
        ReportInput._VALUE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
    m.addPrimitiveCollection(
        "values",
        ReportInput._VALUES,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getValues(),
        (s, v) -> s.setValues(v));
  }

  private void addReportFilterValueFields() {
    DModel<ReportFilterValue> m = getType2("ReportFilterValue");
    m.addPrimitive(
        "name",
        ReportFilterValue._NAME,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "value",
        ReportFilterValue._VALUE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
  }

  private void addReportOutputFields() {
    DModel<ReportOutput> m = getType2("ReportOutput");
    m.addReferenceCollection(
        "reportOutOption",
        ReportOutput._REPORTOUTOPTION,
        null,
        null,
        true,
        getType("ReportOutOption"),
        (s) -> s.getReportOutOption(),
        (s, v) -> s.setReportOutOption(v));
    m.addReferenceCollection(
        "reportOutColumn",
        ReportOutput._REPORTOUTCOLUMN,
        null,
        null,
        true,
        getType("ReportOutColumn"),
        (s) -> s.getReportOutColumn(),
        (s, v) -> s.setReportOutColumn(v));
    m.addReferenceCollection(
        "subColumns",
        ReportOutput._SUBCOLUMNS,
        null,
        null,
        true,
        getType("ReportOutColumn"),
        (s) -> s.getSubColumns(),
        (s, v) -> s.setSubColumns(v));
    m.addReferenceCollection(
        "reportOutAttribute",
        ReportOutput._REPORTOUTATTRIBUTE,
        null,
        null,
        true,
        getType("ReportOutAttribute"),
        (s) -> s.getReportOutAttribute(),
        (s, v) -> s.setReportOutAttribute(v));
    m.addReferenceCollection(
        "reportOutRow",
        ReportOutput._REPORTOUTROW,
        null,
        null,
        true,
        getType("ReportOutRow"),
        (s) -> s.getReportOutRow(),
        (s, v) -> s.setReportOutRow(v));
  }

  private void addReportOutOptionFields() {
    DModel<ReportOutOption> m = getType2("ReportOutOption");
    m.addPrimitive(
        "key",
        ReportOutOption._KEY,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getKey(),
        (s, v) -> s.setKey(v));
    m.addPrimitive(
        "value",
        ReportOutOption._VALUE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
  }

  private void addReportOutColumnFields() {
    DModel<ReportOutColumn> m = getType2("ReportOutColumn");
    m.addPrimitive(
        "type",
        ReportOutColumn._TYPE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addPrimitive(
        "value",
        ReportOutColumn._VALUE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
    m.addReferenceCollection(
        "attributes",
        ReportOutColumn._ATTRIBUTES,
        null,
        null,
        true,
        getType("ReportOutAttribute"),
        (s) -> s.getAttributes(),
        (s, v) -> s.setAttributes(v));
  }

  private void addReportOutAttributeFields() {
    DModel<ReportOutAttribute> m = getType2("ReportOutAttribute");
    m.addPrimitive(
        "key",
        ReportOutAttribute._KEY,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getKey(),
        (s, v) -> s.setKey(v));
    m.addPrimitive(
        "value",
        ReportOutAttribute._VALUE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
  }

  private void addReportOutRowFields() {
    DModel<ReportOutRow> m = getType2("ReportOutRow");
    m.addPrimitive(
        "key",
        ReportOutRow._KEY,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getKey(),
        (s, v) -> s.setKey(v));
    m.addPrimitive(
        "parentKey",
        ReportOutRow._PARENTKEY,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getParentKey(),
        (s, v) -> s.setParentKey(v));
    m.addReferenceCollection(
        "cells",
        ReportOutRow._CELLS,
        null,
        null,
        true,
        getType("ReportOutCell"),
        (s) -> s.getCells(),
        (s, v) -> s.setCells(v));
    m.addPrimitive(
        "groupingKey",
        ReportOutRow._GROUPINGKEY,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getGroupingKey(),
        (s, v) -> s.setGroupingKey(v));
  }

  private void addReportOutCellFields() {
    DModel<ReportOutCell> m = getType2("ReportOutCell");
    m.addPrimitive(
        "key",
        ReportOutCell._KEY,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getKey(),
        (s, v) -> s.setKey(v));
    m.addPrimitive(
        "type",
        ReportOutCell._TYPE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getType(),
        (s, v) -> s.setType(v));
    m.addPrimitive(
        "value",
        ReportOutCell._VALUE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getValue(),
        (s, v) -> s.setValue(v));
    m.addReferenceCollection(
        "attributes",
        ReportOutCell._ATTRIBUTES,
        null,
        null,
        true,
        getType("ReportOutAttribute"),
        (s) -> s.getAttributes(),
        (s, v) -> s.setAttributes(v));
  }

  private void addLoginResultFields() {
    DModel<LoginResult> m = getType2("LoginResult");
    m.addPrimitive(
        "success",
        LoginResult._SUCCESS,
        null,
        FieldPrimitiveType.Boolean,
        (s) -> s.isSuccess(),
        (s, v) -> s.setSuccess(v));
    m.addReference(
        "userObject",
        LoginResult._USEROBJECT,
        null,
        false,
        getType("BaseUser"),
        (s) -> s.getUserObject(),
        (s, v) -> s.setUserObject(v),
        (s) -> s.getUserObjectRef());
    m.addPrimitive(
        "token",
        LoginResult._TOKEN,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getToken(),
        (s, v) -> s.setToken(v));
    m.addPrimitive(
        "failureMessage",
        LoginResult._FAILUREMESSAGE,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getFailureMessage(),
        (s, v) -> s.setFailureMessage(v));
  }

  private void addAllDevicesFields() {
    DModel<AllDevices> m = getType2("AllDevices");
    m.addEnum(
        "status",
        AllDevices._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllDevices._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        AllDevices._ITEMS,
        null,
        null,
        false,
        getType("UserDevice"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addUserDevicesFields() {
    DModel<UserDevices> m = getType2("UserDevices");
    m.addEnum(
        "status",
        UserDevices._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        UserDevices._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        UserDevices._ITEMS,
        null,
        null,
        false,
        getType("UserDevice"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addVerificationDataByTokenFields() {
    DModel<VerificationDataByToken> m = getType2("VerificationDataByToken");
    m.addEnum(
        "status",
        VerificationDataByToken._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        VerificationDataByToken._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        VerificationDataByToken._ITEMS,
        null,
        null,
        false,
        getType("VerificationData"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllAuditLogsFields() {
    DModel<AllAuditLogs> m = getType2("AllAuditLogs");
    m.addEnum(
        "status",
        AllAuditLogs._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllAuditLogs._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllAuditLogs._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllAuditLogs._ITEMS,
        null,
        null,
        false,
        getType("AuditLog"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllGoodsReceiptsFields() {
    DModel<AllGoodsReceipts> m = getType2("AllGoodsReceipts");
    m.addEnum(
        "status",
        AllGoodsReceipts._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllGoodsReceipts._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllGoodsReceipts._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllGoodsReceipts._ITEMS,
        null,
        null,
        false,
        getType("GoodsReceipt"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllInAppNotificationsFields() {
    DModel<AllInAppNotifications> m = getType2("AllInAppNotifications");
    m.addEnum(
        "status",
        AllInAppNotifications._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllInAppNotifications._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllInAppNotifications._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllInAppNotifications._ITEMS,
        null,
        null,
        false,
        getType("InAppNotification"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllInventoryAdjustmentsFields() {
    DModel<AllInventoryAdjustments> m = getType2("AllInventoryAdjustments");
    m.addEnum(
        "status",
        AllInventoryAdjustments._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllInventoryAdjustments._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllInventoryAdjustments._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllInventoryAdjustments._ITEMS,
        null,
        null,
        false,
        getType("InventoryAdjustment"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllInventoryMovementsFields() {
    DModel<AllInventoryMovements> m = getType2("AllInventoryMovements");
    m.addEnum(
        "status",
        AllInventoryMovements._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllInventoryMovements._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllInventoryMovements._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllInventoryMovements._ITEMS,
        null,
        null,
        false,
        getType("InventoryMovement"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllOrganizationsFields() {
    DModel<AllOrganizations> m = getType2("AllOrganizations");
    m.addEnum(
        "status",
        AllOrganizations._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllOrganizations._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllOrganizations._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllOrganizations._ITEMS,
        null,
        null,
        false,
        getType("Organization"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllProductCategoriesFields() {
    DModel<AllProductCategories> m = getType2("AllProductCategories");
    m.addEnum(
        "status",
        AllProductCategories._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllProductCategories._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllProductCategories._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllProductCategories._ITEMS,
        null,
        null,
        false,
        getType("ProductCategory"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllProductsFields() {
    DModel<AllProducts> m = getType2("AllProducts");
    m.addEnum(
        "status",
        AllProducts._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllProducts._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllProducts._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllProducts._ITEMS,
        null,
        null,
        false,
        getType("Product"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllPurchaseOrdersFields() {
    DModel<AllPurchaseOrders> m = getType2("AllPurchaseOrders");
    m.addEnum(
        "status",
        AllPurchaseOrders._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllPurchaseOrders._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllPurchaseOrders._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllPurchaseOrders._ITEMS,
        null,
        null,
        false,
        getType("PurchaseOrder"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllSalesOrdersFields() {
    DModel<AllSalesOrders> m = getType2("AllSalesOrders");
    m.addEnum(
        "status",
        AllSalesOrders._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllSalesOrders._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllSalesOrders._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllSalesOrders._ITEMS,
        null,
        null,
        false,
        getType("SalesOrder"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllSalesReturnsFields() {
    DModel<AllSalesReturns> m = getType2("AllSalesReturns");
    m.addEnum(
        "status",
        AllSalesReturns._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllSalesReturns._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllSalesReturns._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllSalesReturns._ITEMS,
        null,
        null,
        false,
        getType("SalesReturn"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllStockAlertsFields() {
    DModel<AllStockAlerts> m = getType2("AllStockAlerts");
    m.addEnum(
        "status",
        AllStockAlerts._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllStockAlerts._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllStockAlerts._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllStockAlerts._ITEMS,
        null,
        null,
        false,
        getType("StockAlert"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllStockBatchesFields() {
    DModel<AllStockBatches> m = getType2("AllStockBatches");
    m.addEnum(
        "status",
        AllStockBatches._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllStockBatches._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllStockBatches._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllStockBatches._ITEMS,
        null,
        null,
        false,
        getType("StockBatch"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllStockTransfersFields() {
    DModel<AllStockTransfers> m = getType2("AllStockTransfers");
    m.addEnum(
        "status",
        AllStockTransfers._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllStockTransfers._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllStockTransfers._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllStockTransfers._ITEMS,
        null,
        null,
        false,
        getType("StockTransfer"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllStoresFields() {
    DModel<AllStores> m = getType2("AllStores");
    m.addEnum(
        "status",
        AllStores._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllStores._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllStores._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllStores._ITEMS,
        null,
        null,
        false,
        getType("Store"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllSuppliersFields() {
    DModel<AllSuppliers> m = getType2("AllSuppliers");
    m.addEnum(
        "status",
        AllSuppliers._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllSuppliers._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllSuppliers._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllSuppliers._ITEMS,
        null,
        null,
        false,
        getType("Vendor"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllUnitOfMeasuresFields() {
    DModel<AllUnitOfMeasures> m = getType2("AllUnitOfMeasures");
    m.addEnum(
        "status",
        AllUnitOfMeasures._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllUnitOfMeasures._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllUnitOfMeasures._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllUnitOfMeasures._ITEMS,
        null,
        null,
        false,
        getType("UnitOfMeasure"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllUserInvitationsFields() {
    DModel<AllUserInvitations> m = getType2("AllUserInvitations");
    m.addEnum(
        "status",
        AllUserInvitations._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllUserInvitations._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllUserInvitations._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllUserInvitations._ITEMS,
        null,
        null,
        false,
        getType("UserInvitation"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllUserProfilesFields() {
    DModel<AllUserProfiles> m = getType2("AllUserProfiles");
    m.addEnum(
        "status",
        AllUserProfiles._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllUserProfiles._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllUserProfiles._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllUserProfiles._ITEMS,
        null,
        null,
        false,
        getType("UserProfile"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addAllWarehousesFields() {
    DModel<AllWarehouses> m = getType2("AllWarehouses");
    m.addEnum(
        "status",
        AllWarehouses._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        AllWarehouses._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        AllWarehouses._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        AllWarehouses._ITEMS,
        null,
        null,
        false,
        getType("Warehouse"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addDashboardMetricsFields() {
    DModel<DashboardMetrics> m = getType2("DashboardMetrics");
    m.addEnum(
        "status",
        DashboardMetrics._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        DashboardMetrics._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        DashboardMetrics._ITEMS,
        null,
        null,
        false,
        getType("WarehouseStock"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addExpiringBatchesFields() {
    DModel<ExpiringBatches> m = getType2("ExpiringBatches");
    m.addEnum(
        "status",
        ExpiringBatches._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        ExpiringBatches._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        ExpiringBatches._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        ExpiringBatches._ITEMS,
        null,
        null,
        false,
        getType("StockBatch"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addGoodsReceiptItemFields() {
    DModel<GoodsReceiptItem> m = getType2("GoodsReceiptItem");
    m.addEnum(
        "status",
        GoodsReceiptItem._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        GoodsReceiptItem._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        GoodsReceiptItem._ITEMS,
        null,
        null,
        false,
        getType("GoodsReceipt"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addInventoryMovementsByDateRangeFields() {
    DModel<InventoryMovementsByDateRange> m = getType2("InventoryMovementsByDateRange");
    m.addEnum(
        "status",
        InventoryMovementsByDateRange._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        InventoryMovementsByDateRange._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        InventoryMovementsByDateRange._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        InventoryMovementsByDateRange._ITEMS,
        null,
        null,
        false,
        getType("InventoryMovement"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addLowStockItemsFields() {
    DModel<LowStockItems> m = getType2("LowStockItems");
    m.addEnum(
        "status",
        LowStockItems._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        LowStockItems._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        LowStockItems._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        LowStockItems._ITEMS,
        null,
        null,
        false,
        getType("WarehouseStock"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addMovementReportRowsFields() {
    DModel<MovementReportRows> m = getType2("MovementReportRows");
    m.addEnum(
        "status",
        MovementReportRows._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        MovementReportRows._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        MovementReportRows._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        MovementReportRows._ITEMS,
        null,
        null,
        false,
        getType("InventoryMovement"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addOrganizationItemFields() {
    DModel<OrganizationItem> m = getType2("OrganizationItem");
    m.addEnum(
        "status",
        OrganizationItem._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        OrganizationItem._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        OrganizationItem._ITEMS,
        null,
        null,
        false,
        getType("Organization"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addOutOfStockItemsFields() {
    DModel<OutOfStockItems> m = getType2("OutOfStockItems");
    m.addEnum(
        "status",
        OutOfStockItems._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        OutOfStockItems._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        OutOfStockItems._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        OutOfStockItems._ITEMS,
        null,
        null,
        false,
        getType("WarehouseStock"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addProductItemFields() {
    DModel<ProductItem> m = getType2("ProductItem");
    m.addEnum(
        "status",
        ProductItem._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        ProductItem._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        ProductItem._ITEMS,
        null,
        null,
        false,
        getType("Product"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addProductSearchFields() {
    DModel<ProductSearch> m = getType2("ProductSearch");
    m.addEnum(
        "status",
        ProductSearch._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        ProductSearch._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        ProductSearch._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        ProductSearch._ITEMS,
        null,
        null,
        false,
        getType("Product"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addProductsByCategoryFields() {
    DModel<ProductsByCategory> m = getType2("ProductsByCategory");
    m.addEnum(
        "status",
        ProductsByCategory._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        ProductsByCategory._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        ProductsByCategory._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        ProductsByCategory._ITEMS,
        null,
        null,
        false,
        getType("Product"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addPurchaseOrderItemFields() {
    DModel<PurchaseOrderItem> m = getType2("PurchaseOrderItem");
    m.addEnum(
        "status",
        PurchaseOrderItem._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        PurchaseOrderItem._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        PurchaseOrderItem._ITEMS,
        null,
        null,
        false,
        getType("PurchaseOrder"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addPurchaseOrdersByStatusFields() {
    DModel<PurchaseOrdersByStatus> m = getType2("PurchaseOrdersByStatus");
    m.addEnum(
        "status",
        PurchaseOrdersByStatus._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        PurchaseOrdersByStatus._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        PurchaseOrdersByStatus._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        PurchaseOrdersByStatus._ITEMS,
        null,
        null,
        false,
        getType("PurchaseOrder"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addSalesOrderItemFields() {
    DModel<SalesOrderItem> m = getType2("SalesOrderItem");
    m.addEnum(
        "status",
        SalesOrderItem._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        SalesOrderItem._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        SalesOrderItem._ITEMS,
        null,
        null,
        false,
        getType("SalesOrder"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addSalesOrdersByStoreFields() {
    DModel<SalesOrdersByStore> m = getType2("SalesOrdersByStore");
    m.addEnum(
        "status",
        SalesOrdersByStore._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        SalesOrdersByStore._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        SalesOrdersByStore._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        SalesOrdersByStore._ITEMS,
        null,
        null,
        false,
        getType("SalesOrder"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addStockTransferItemFields() {
    DModel<StockTransferItem> m = getType2("StockTransferItem");
    m.addEnum(
        "status",
        StockTransferItem._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        StockTransferItem._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        StockTransferItem._ITEMS,
        null,
        null,
        false,
        getType("StockTransfer"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addStockValuationReportFields() {
    DModel<StockValuationReport> m = getType2("StockValuationReport");
    m.addEnum(
        "status",
        StockValuationReport._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        StockValuationReport._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        StockValuationReport._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        StockValuationReport._ITEMS,
        null,
        null,
        false,
        getType("WarehouseStock"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addStoreItemFields() {
    DModel<StoreItem> m = getType2("StoreItem");
    m.addEnum(
        "status",
        StoreItem._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        StoreItem._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        StoreItem._ITEMS,
        null,
        null,
        false,
        getType("Store"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addSupplierItemFields() {
    DModel<SupplierItem> m = getType2("SupplierItem");
    m.addEnum(
        "status",
        SupplierItem._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        SupplierItem._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        SupplierItem._ITEMS,
        null,
        null,
        false,
        getType("Vendor"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addUnreadNotificationCountFields() {
    DModel<UnreadNotificationCount> m = getType2("UnreadNotificationCount");
    m.addEnum(
        "status",
        UnreadNotificationCount._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        UnreadNotificationCount._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        UnreadNotificationCount._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        UnreadNotificationCount._ITEMS,
        null,
        null,
        false,
        getType("InAppNotification"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addUserProfileByUserFields() {
    DModel<UserProfileByUser> m = getType2("UserProfileByUser");
    m.addEnum(
        "status",
        UserProfileByUser._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        UserProfileByUser._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addReferenceCollection(
        "items",
        UserProfileByUser._ITEMS,
        null,
        null,
        false,
        getType("UserProfile"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addWarehouseStockByProductFields() {
    DModel<WarehouseStockByProduct> m = getType2("WarehouseStockByProduct");
    m.addEnum(
        "status",
        WarehouseStockByProduct._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        WarehouseStockByProduct._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        WarehouseStockByProduct._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        WarehouseStockByProduct._ITEMS,
        null,
        null,
        false,
        getType("WarehouseStock"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addWarehouseStockByWarehouseFields() {
    DModel<WarehouseStockByWarehouse> m = getType2("WarehouseStockByWarehouse");
    m.addEnum(
        "status",
        WarehouseStockByWarehouse._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        WarehouseStockByWarehouse._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        WarehouseStockByWarehouse._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        WarehouseStockByWarehouse._ITEMS,
        null,
        null,
        false,
        getType("WarehouseStock"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }

  private void addWarehousesByStoreFields() {
    DModel<WarehousesByStore> m = getType2("WarehousesByStore");
    m.addEnum(
        "status",
        WarehousesByStore._STATUS,
        null,
        getType("ResultStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitiveCollection(
        "errors",
        WarehousesByStore._ERRORS,
        null,
        null,
        FieldPrimitiveType.String,
        (s) -> s.getErrors(),
        (s, v) -> s.setErrors(v));
    m.addPrimitive(
        "count",
        WarehousesByStore._COUNT,
        null,
        FieldPrimitiveType.Integer,
        (s) -> s.getCount(),
        (s, v) -> s.setCount(v));
    m.addReferenceCollection(
        "items",
        WarehousesByStore._ITEMS,
        null,
        null,
        false,
        getType("Warehouse"),
        (s) -> s.getItems(),
        (s, v) -> s.setItems(v),
        (s) -> s.getItemsRef());
  }
}
