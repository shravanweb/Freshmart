package rest;

import classes.AppUserRole;
import classes.LoginResult;
import classes.MutateResultStatus;
import d3e.core.CurrentUser;
import d3e.core.ListExt;
import d3e.core.Log;
import gqltosql.GqlToSql;
import gqltosql.schema.IModelSchema;
import graphql.language.Field;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lists.AllAuditLogsImpl;
import lists.AllDevicesImpl;
import lists.AllGoodsReceiptsImpl;
import lists.AllInAppNotificationsImpl;
import lists.AllInventoryAdjustmentsImpl;
import lists.AllInventoryMovementsImpl;
import lists.AllOrganizationsImpl;
import lists.AllProductCategoriesImpl;
import lists.AllProductsImpl;
import lists.AllPurchaseOrdersImpl;
import lists.AllSalesOrdersImpl;
import lists.AllSalesReturnsImpl;
import lists.AllStockAlertsImpl;
import lists.AllStockBatchesImpl;
import lists.AllStockTransfersImpl;
import lists.AllStoresImpl;
import lists.AllSuppliersImpl;
import lists.AllUnitOfMeasuresImpl;
import lists.AllUserInvitationsImpl;
import lists.AllUserProfilesImpl;
import lists.AllWarehousesImpl;
import lists.DashboardMetricsImpl;
import lists.ExpiringBatchesImpl;
import lists.GoodsReceiptItemImpl;
import lists.InventoryMovementsByDateRangeImpl;
import lists.LowStockItemsImpl;
import lists.MovementReportRowsImpl;
import lists.OrganizationItemImpl;
import lists.OutOfStockItemsImpl;
import lists.ProductItemImpl;
import lists.ProductSearchImpl;
import lists.ProductsByCategoryImpl;
import lists.PurchaseOrderItemImpl;
import lists.PurchaseOrdersByStatusImpl;
import lists.SalesOrderItemImpl;
import lists.SalesOrdersByStoreImpl;
import lists.StockTransferItemImpl;
import lists.StockValuationReportImpl;
import lists.StoreItemImpl;
import lists.SupplierItemImpl;
import lists.UnreadNotificationCountImpl;
import lists.UserDevicesImpl;
import lists.UserProfileByUserImpl;
import lists.VerificationDataByTokenImpl;
import lists.WarehouseStockByProductImpl;
import lists.WarehouseStockByWarehouseImpl;
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
import models.AnonymousUser;
import models.BaseUser;
import models.DashboardMetricsRequest;
import models.ExpiringBatchesRequest;
import models.GoodsReceiptItemRequest;
import models.InventoryMovementsByDateRangeRequest;
import models.LowStockItemsRequest;
import models.MovementReportRowsRequest;
import models.OneTimePassword;
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
import models.UserLoginRecord;
import models.UserProfileByUserRequest;
import models.WarehouseStockByProductRequest;
import models.WarehouseStockByWarehouseRequest;
import models.WarehousesByStoreRequest;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.jpa.AnonymousUserRepository;
import repository.jpa.AuditLogRepository;
import repository.jpa.BaseUserRepository;
import repository.jpa.BaseUserSessionRepository;
import repository.jpa.GoodsReceiptLineRepository;
import repository.jpa.GoodsReceiptRepository;
import repository.jpa.InAppNotificationRepository;
import repository.jpa.InventoryAdjustmentLineRepository;
import repository.jpa.InventoryAdjustmentRepository;
import repository.jpa.InventoryMovementRepository;
import repository.jpa.NotificationTemplateRepository;
import repository.jpa.OneTimePasswordRepository;
import repository.jpa.OrganizationRepository;
import repository.jpa.ProductCategoryRepository;
import repository.jpa.ProductRepository;
import repository.jpa.PurchaseOrderLineRepository;
import repository.jpa.PurchaseOrderRepository;
import repository.jpa.PushNotificationRepository;
import repository.jpa.ReportRepository;
import repository.jpa.SalesOrderLineRepository;
import repository.jpa.SalesOrderRepository;
import repository.jpa.SalesReturnLineRepository;
import repository.jpa.SalesReturnRepository;
import repository.jpa.StockAlertRepository;
import repository.jpa.StockBatchRepository;
import repository.jpa.StockTransferLineRepository;
import repository.jpa.StockTransferRepository;
import repository.jpa.StoreRepository;
import repository.jpa.SupplierContactRepository;
import repository.jpa.UnitOfMeasureRepository;
import repository.jpa.UserDeviceRepository;
import repository.jpa.UserInvitationRepository;
import repository.jpa.UserLoginRecordRepository;
import repository.jpa.UserProfileRepository;
import repository.jpa.UserRepository;
import repository.jpa.UserRoleRepository;
import repository.jpa.VendorRepository;
import repository.jpa.VerificationDataRepository;
import repository.jpa.WarehouseRepository;
import repository.jpa.WarehouseStockRepository;
import security.AppSessionProvider;
import security.JwtTokenUtil;
import security.UserProxy;
import store.EntityHelperService;
import store.EntityMutator;
import store.ValidationFailedException;

@RestController
@RequestMapping("api/native/")
public class NativeQuery extends AbstractQueryService {
  @Value("classpath:introspec.json")
  private Resource inrospecFile;

  @Autowired private EntityMutator mutator;
  @Autowired private ObjectFactory<EntityHelperService> helperService;
  @Autowired private GqlToSql gqlToSql;
  @Autowired private IModelSchema schema;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private AnonymousUserRepository anonymousUserRepository;
  @Autowired private AuditLogRepository auditLogRepository;
  @Autowired private BaseUserRepository baseUserRepository;
  @Autowired private BaseUserSessionRepository baseUserSessionRepository;
  @Autowired private GoodsReceiptRepository goodsReceiptRepository;
  @Autowired private GoodsReceiptLineRepository goodsReceiptLineRepository;
  @Autowired private InAppNotificationRepository inAppNotificationRepository;
  @Autowired private InventoryAdjustmentRepository inventoryAdjustmentRepository;
  @Autowired private InventoryAdjustmentLineRepository inventoryAdjustmentLineRepository;
  @Autowired private InventoryMovementRepository inventoryMovementRepository;
  @Autowired private NotificationTemplateRepository notificationTemplateRepository;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private ProductCategoryRepository productCategoryRepository;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private PurchaseOrderLineRepository purchaseOrderLineRepository;
  @Autowired private PushNotificationRepository pushNotificationRepository;
  @Autowired private ReportRepository reportRepository;
  @Autowired private SalesOrderRepository salesOrderRepository;
  @Autowired private SalesOrderLineRepository salesOrderLineRepository;
  @Autowired private SalesReturnRepository salesReturnRepository;
  @Autowired private SalesReturnLineRepository salesReturnLineRepository;
  @Autowired private StockAlertRepository stockAlertRepository;
  @Autowired private StockBatchRepository stockBatchRepository;
  @Autowired private StockTransferRepository stockTransferRepository;
  @Autowired private StockTransferLineRepository stockTransferLineRepository;
  @Autowired private StoreRepository storeRepository;
  @Autowired private SupplierContactRepository supplierContactRepository;
  @Autowired private UnitOfMeasureRepository unitOfMeasureRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private UserDeviceRepository userDeviceRepository;
  @Autowired private UserInvitationRepository userInvitationRepository;
  @Autowired private UserLoginRecordRepository userLoginRecordRepository;
  @Autowired private UserProfileRepository userProfileRepository;
  @Autowired private UserRoleRepository userRoleRepository;
  @Autowired private VendorRepository vendorRepository;
  @Autowired private VerificationDataRepository verificationDataRepository;
  @Autowired private WarehouseRepository warehouseRepository;
  @Autowired private WarehouseStockRepository warehouseStockRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private ObjectFactory<AppSessionProvider> provider;
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

  @PostMapping(path = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
  public String run(@RequestBody String query) throws Exception {
    JSONObject req = new JSONObject(query);
    List<Field> fields = parseFields(req);
    JSONObject variables = req.getJSONObject("variables");
    String queryStr = null;
    try {
      queryStr = req.getString("query");
    } catch (JSONException e) {
    }
    return executeFields(queryStr, fields, variables);
  }

  public String executeFields(String query, List<Field> fields, JSONObject variables)
      throws Exception {
    JSONObject data = new JSONObject();
    for (Field s : fields) {
      String name = s.getAlias() == null ? s.getName() : s.getAlias();
      long time = System.currentTimeMillis();
      Log.info("Started: " + time + ":" + s.getName());
      try {
        data.put(name, executeOperation(query, s, variables));
      } catch (ValidationFailedException e) {
        JSONObject errors = new JSONObject();
        errors.put("error", e.getErrors());
        data.put(name, errors);
        logErrors(errors);
      }
      Log.info("Completed: " + time + ":" + s.getName());
    }
    JSONObject output = new JSONObject();
    output.put("data", data);
    return output.toString();
  }

  protected Object executeOperation(String query, Field field, JSONObject variables)
      throws Exception {
    GraphQLInputContext ctx =
        new ArgumentInputContext(
            field.getArguments(),
            helperService.getObject(),
            new HashMap<>(),
            new HashMap<>(),
            variables,
            schema);
    Log.displayGraphQL(field.getName(), query, variables);
    BaseUser currentUser = CurrentUser.get();
    switch (field.getName()) {
      case "__schema":
        {
          String json = IOUtils.toString(inrospecFile.getInputStream(), Charset.defaultCharset());
          return new JSONObject(json);
        }
      case "getAnonymousUserById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("AnonymousUser", field, ctx.readLong("id"));
        }
      case "getAuditLogById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("AuditLog", field, ctx.readLong("id"));
        }
      case "getGoodsReceiptById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("GoodsReceipt", field, ctx.readLong("id"));
        }
      case "getGoodsReceiptLineById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("GoodsReceiptLine", field, ctx.readLong("id"));
        }
      case "getInAppNotificationById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("InAppNotification", field, ctx.readLong("id"));
        }
      case "getInventoryAdjustmentById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("InventoryAdjustment", field, ctx.readLong("id"));
        }
      case "getInventoryAdjustmentLineById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("InventoryAdjustmentLine", field, ctx.readLong("id"));
        }
      case "getInventoryMovementById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("InventoryMovement", field, ctx.readLong("id"));
        }
      case "getNotificationTemplateById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("NotificationTemplate", field, ctx.readLong("id"));
        }
      case "getOneTimePasswordById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("OneTimePassword", field, ctx.readLong("id"));
        }
      case "getOrganizationById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("Organization", field, ctx.readLong("id"));
        }
      case "getProductById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("Product", field, ctx.readLong("id"));
        }
      case "getProductCategoryById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("ProductCategory", field, ctx.readLong("id"));
        }
      case "getPurchaseOrderById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("PurchaseOrder", field, ctx.readLong("id"));
        }
      case "getPurchaseOrderLineById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("PurchaseOrderLine", field, ctx.readLong("id"));
        }
      case "getPushNotificationById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("PushNotification", field, ctx.readLong("id"));
        }
      case "getReportById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("Report", field, ctx.readLong("id"));
        }
      case "getSalesOrderById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("SalesOrder", field, ctx.readLong("id"));
        }
      case "getSalesOrderLineById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("SalesOrderLine", field, ctx.readLong("id"));
        }
      case "getSalesReturnById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("SalesReturn", field, ctx.readLong("id"));
        }
      case "getSalesReturnLineById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("SalesReturnLine", field, ctx.readLong("id"));
        }
      case "getStockAlertById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("StockAlert", field, ctx.readLong("id"));
        }
      case "getStockBatchById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("StockBatch", field, ctx.readLong("id"));
        }
      case "getStockTransferById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("StockTransfer", field, ctx.readLong("id"));
        }
      case "getStockTransferLineById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("StockTransferLine", field, ctx.readLong("id"));
        }
      case "getStoreById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("Store", field, ctx.readLong("id"));
        }
      case "getSupplierContactById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("SupplierContact", field, ctx.readLong("id"));
        }
      case "getUnitOfMeasureById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("UnitOfMeasure", field, ctx.readLong("id"));
        }
      case "getUserById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("User", field, ctx.readLong("id"));
        }
      case "getUserDeviceById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("UserDevice", field, ctx.readLong("id"));
        }
      case "getUserInvitationById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("UserInvitation", field, ctx.readLong("id"));
        }
      case "getUserLoginRecordById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("UserLoginRecord", field, ctx.readLong("id"));
        }
      case "getUserProfileById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("UserProfile", field, ctx.readLong("id"));
        }
      case "getUserRoleById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("UserRole", field, ctx.readLong("id"));
        }
      case "getVendorById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("Vendor", field, ctx.readLong("id"));
        }
      case "getVerificationDataById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("VerificationData", field, ctx.readLong("id"));
        }
      case "getWarehouseById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("Warehouse", field, ctx.readLong("id"));
        }
      case "getWarehouseStockById":
        {
          if (currentUser instanceof AnonymousUser) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("Current user does not have read permissions for this model."));
          }
          return gqlToSql.execute("WarehouseStock", field, ctx.readLong("id"));
        }
      case "getAllAuditLogs":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllAuditLogsRequest req = ctx.readChild("in", "AllAuditLogsRequest");
          return allAuditLogsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllGoodsReceipts":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllGoodsReceiptsRequest req = ctx.readChild("in", "AllGoodsReceiptsRequest");
          return allGoodsReceiptsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllInAppNotifications":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllInAppNotificationsRequest req = ctx.readChild("in", "AllInAppNotificationsRequest");
          return allInAppNotificationsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllInventoryAdjustments":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllInventoryAdjustmentsRequest req =
              ctx.readChild("in", "AllInventoryAdjustmentsRequest");
          return allInventoryAdjustmentsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllInventoryMovements":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllInventoryMovementsRequest req = ctx.readChild("in", "AllInventoryMovementsRequest");
          return allInventoryMovementsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllOrganizations":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          return allOrganizationsImpl.getAsJson(inspect(field, "items"));
        }
      case "getAllProductCategories":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllProductCategoriesRequest req = ctx.readChild("in", "AllProductCategoriesRequest");
          return allProductCategoriesImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllProducts":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllProductsRequest req = ctx.readChild("in", "AllProductsRequest");
          return allProductsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllPurchaseOrders":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllPurchaseOrdersRequest req = ctx.readChild("in", "AllPurchaseOrdersRequest");
          return allPurchaseOrdersImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllSalesOrders":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllSalesOrdersRequest req = ctx.readChild("in", "AllSalesOrdersRequest");
          return allSalesOrdersImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllSalesReturns":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllSalesReturnsRequest req = ctx.readChild("in", "AllSalesReturnsRequest");
          return allSalesReturnsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllStockAlerts":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllStockAlertsRequest req = ctx.readChild("in", "AllStockAlertsRequest");
          return allStockAlertsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllStockBatches":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllStockBatchesRequest req = ctx.readChild("in", "AllStockBatchesRequest");
          return allStockBatchesImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllStockTransfers":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllStockTransfersRequest req = ctx.readChild("in", "AllStockTransfersRequest");
          return allStockTransfersImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllStores":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllStoresRequest req = ctx.readChild("in", "AllStoresRequest");
          return allStoresImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllSuppliers":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllSuppliersRequest req = ctx.readChild("in", "AllSuppliersRequest");
          return allSuppliersImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllUnitOfMeasures":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllUnitOfMeasuresRequest req = ctx.readChild("in", "AllUnitOfMeasuresRequest");
          return allUnitOfMeasuresImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllUserInvitations":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllUserInvitationsRequest req = ctx.readChild("in", "AllUserInvitationsRequest");
          return allUserInvitationsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllUserProfiles":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllUserProfilesRequest req = ctx.readChild("in", "AllUserProfilesRequest");
          return allUserProfilesImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getAllWarehouses":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          AllWarehousesRequest req = ctx.readChild("in", "AllWarehousesRequest");
          return allWarehousesImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getDashboardMetrics":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          DashboardMetricsRequest req = ctx.readChild("in", "DashboardMetricsRequest");
          return dashboardMetricsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getExpiringBatches":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          ExpiringBatchesRequest req = ctx.readChild("in", "ExpiringBatchesRequest");
          return expiringBatchesImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getGoodsReceiptItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          GoodsReceiptItemRequest req = ctx.readChild("in", "GoodsReceiptItemRequest");
          return goodsReceiptItemImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getInventoryMovementsByDateRange":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          InventoryMovementsByDateRangeRequest req =
              ctx.readChild("in", "InventoryMovementsByDateRangeRequest");
          return inventoryMovementsByDateRangeImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getLowStockItems":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          LowStockItemsRequest req = ctx.readChild("in", "LowStockItemsRequest");
          return lowStockItemsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getMovementReportRows":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          MovementReportRowsRequest req = ctx.readChild("in", "MovementReportRowsRequest");
          return movementReportRowsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getOrganizationItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          OrganizationItemRequest req = ctx.readChild("in", "OrganizationItemRequest");
          return organizationItemImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getOutOfStockItems":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          OutOfStockItemsRequest req = ctx.readChild("in", "OutOfStockItemsRequest");
          return outOfStockItemsImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getProductItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          ProductItemRequest req = ctx.readChild("in", "ProductItemRequest");
          return productItemImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getProductSearch":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          ProductSearchRequest req = ctx.readChild("in", "ProductSearchRequest");
          return productSearchImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getProductsByCategory":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          ProductsByCategoryRequest req = ctx.readChild("in", "ProductsByCategoryRequest");
          return productsByCategoryImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getPurchaseOrderItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          PurchaseOrderItemRequest req = ctx.readChild("in", "PurchaseOrderItemRequest");
          return purchaseOrderItemImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getPurchaseOrdersByStatus":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          PurchaseOrdersByStatusRequest req = ctx.readChild("in", "PurchaseOrdersByStatusRequest");
          return purchaseOrdersByStatusImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getSalesOrderItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          SalesOrderItemRequest req = ctx.readChild("in", "SalesOrderItemRequest");
          return salesOrderItemImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getSalesOrdersByStore":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          SalesOrdersByStoreRequest req = ctx.readChild("in", "SalesOrdersByStoreRequest");
          return salesOrdersByStoreImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getStockTransferItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          StockTransferItemRequest req = ctx.readChild("in", "StockTransferItemRequest");
          return stockTransferItemImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getStockValuationReport":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          StockValuationReportRequest req = ctx.readChild("in", "StockValuationReportRequest");
          return stockValuationReportImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getStoreItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          StoreItemRequest req = ctx.readChild("in", "StoreItemRequest");
          return storeItemImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getSupplierItem":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          SupplierItemRequest req = ctx.readChild("in", "SupplierItemRequest");
          return supplierItemImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getUnreadNotificationCount":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          UnreadNotificationCountRequest req =
              ctx.readChild("in", "UnreadNotificationCountRequest");
          return unreadNotificationCountImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getUserProfileByUser":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          UserProfileByUserRequest req = ctx.readChild("in", "UserProfileByUserRequest");
          return userProfileByUserImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getWarehouseStockByProduct":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          WarehouseStockByProductRequest req =
              ctx.readChild("in", "WarehouseStockByProductRequest");
          return warehouseStockByProductImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getWarehouseStockByWarehouse":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          WarehouseStockByWarehouseRequest req =
              ctx.readChild("in", "WarehouseStockByWarehouseRequest");
          return warehouseStockByWarehouseImpl.getAsJson(inspect(field, "items"), req);
        }
      case "getWarehousesByStore":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList(
                    "Current user type does not have read permissions for this DataQuery."));
          }
          WarehousesByStoreRequest req = ctx.readChild("in", "WarehousesByStoreRequest");
          return warehousesByStoreImpl.getAsJson(inspect(field, "items"), req);
        }
      case "loginInventoryManagementSystemUserWithEmailAndPassword":
        {
          String email = ctx.readString("email");
          String password = ctx.readString("password");
          String deviceToken = ctx.readString("deviceToken");
          return loginInventoryManagementSystemUserWithEmailAndPassword(
              field, email, password, deviceToken);
        }
      case "loginWithOTP":
        {
          String token = ctx.readString("token");
          String code = ctx.readString("code");
          String deviceToken = ctx.readString("deviceToken");
          return loginWithOTP(field, token, code, deviceToken);
        }
      case "registerInventoryManagementSystemUser":
        {
          String displayName = ctx.readString("displayName");
          String email = ctx.readString("email");
          String password = ctx.readString("password");
          AppUserRole appRole = parseAppUserRole(ctx.readString("appRole"));
          return registerInventoryManagementSystemUser(
              field, displayName, email, password, appRole);
        }
      case "createStaffUser":
        {
          if (!(currentUser instanceof User)) {
            throw new ValidationFailedException(
                MutateResultStatus.AuthFail,
                ListExt.asList("You must be signed in to create staff accounts."));
          }
          String displayName = ctx.readString("displayName");
          String email = ctx.readString("email");
          String password = ctx.readString("password");
          AppUserRole appRole = parseAppUserRole(ctx.readString("appRole"));
          return createStaffUser(
              field, (User) currentUser, displayName, email, password, appRole);
        }
      case "sendInventoryManagementSystemPasswordResetOtp":
        {
          String email = ctx.readString("email");
          return sendInventoryManagementSystemPasswordResetOtp(field, email);
        }
      case "resetInventoryManagementSystemPasswordWithOtp":
        {
          String token = ctx.readString("token");
          String code = ctx.readString("code");
          String newPassword = ctx.readString("newPassword");
          return resetInventoryManagementSystemPasswordWithOtp(
              field, token, code, newPassword);
        }
      case "currentAnonymousUser":
        {
          return currentAnonymousUser(field);
        }
      case "currentUser":
        {
          return currentUser(field);
        }
    }
    Log.info("Query Not found");
    return null;
  }

  private JSONObject registerInventoryManagementSystemUser(
      Field field, String displayName, String email, String password, AppUserRole appRole)
      throws Exception {
    LoginResult result =
        classes.RegistrationService.register(
            displayName,
            email,
            password,
            appRole,
            userRepository,
            organizationRepository,
            passwordEncoder,
            jwtTokenUtil);
    JSONObject loginResult = new JSONObject();
    if (!result.isSuccess()) {
      loginResult.put("success", false);
      loginResult.put("failureMessage", result.getFailureMessage());
      return loginResult;
    }
    loginResult.put("success", true);
    loginResult.put("token", result.getToken());
    return loginResult;
  }

  private JSONObject createStaffUser(
      Field field,
      User adminUser,
      String displayName,
      String email,
      String password,
      AppUserRole appRole)
      throws Exception {
    LoginResult result =
        classes.RegistrationService.registerStaff(
            adminUser,
            displayName,
            email,
            password,
            appRole,
            userRepository,
            userProfileRepository,
            organizationRepository,
            passwordEncoder);
    JSONObject loginResult = new JSONObject();
    if (!result.isSuccess()) {
      loginResult.put("success", false);
      loginResult.put("failureMessage", result.getFailureMessage());
      return loginResult;
    }
    loginResult.put("success", true);
    return loginResult;
  }

  private JSONObject sendInventoryManagementSystemPasswordResetOtp(Field field, String email)
      throws Exception {
    LoginResult result = classes.PasswordResetService.sendOtp(email, userRepository, mutator);
    JSONObject loginResult = new JSONObject();
    loginResult.put("success", result.isSuccess());
    loginResult.put("failureMessage", result.getFailureMessage());
    if (result.isSuccess()) {
      loginResult.put("token", result.getToken());
    }
    return loginResult;
  }

  private JSONObject resetInventoryManagementSystemPasswordWithOtp(
      Field field, String token, String code, String newPassword) throws Exception {
    LoginResult result =
        classes.PasswordResetService.resetPassword(
            token, code, newPassword, oneTimePasswordRepository, userRepository);
    JSONObject loginResult = new JSONObject();
    loginResult.put("success", result.isSuccess());
    loginResult.put("failureMessage", result.getFailureMessage());
    return loginResult;
  }

  private JSONObject loginInventoryManagementSystemUserWithEmailAndPassword(
      Field field, String email, String password, String deviceToken) throws Exception {
    User user = userRepository.getByEmail(email.toLowerCase());
    JSONObject loginResult = new JSONObject();
    if (user == null) {
      loginResult.put("success", false);
      loginResult.put("failureMessage", "Invalid authentication details.");
      return loginResult;
    }
    if (!(passwordEncoder.matches(password, user.getPassword()))) {
      recordLoginHistory(user, false, "Invalid authentication details.");
      loginResult.put("success", false);
      loginResult.put("failureMessage", "Invalid authentication details.");
      loginResult.put("loginResult", "Wrong password.");
      return loginResult;
    }
    loginResult.put("success", true);
    String token =
        jwtTokenUtil.generateToken(
            email.toLowerCase(), new UserProxy("User", user.getId(), UUID.randomUUID().toString()));
    loginResult.put("token", token);
    if (deviceToken != null) {
      user.setDeviceToken(deviceToken);
      store.Database.get().save(user);
    }
    return loginResult;
  }

  private JSONObject loginWithOTP(Field field, String token, String code, String deviceToken)
      throws Exception {
    OneTimePassword otp = oneTimePasswordRepository.getByToken(token);
    JSONObject loginResult = new JSONObject();
    if (otp == null) {
      loginResult.put("success", false);
      loginResult.put("loginResult", "Wrong password.");
      return loginResult;
    }
    if (otp.getExpiry().isBefore(java.time.LocalDateTime.now())) {
      loginResult.put("success", false);
      loginResult.put("loginResult", "Wrong password.");
      return loginResult;
    }
    if (!(code.equals(otp.getCode()))) {
      loginResult.put("success", false);
      loginResult.put("loginResult", "Wrong password.");
      return loginResult;
    }
    BaseUser user = otp.getUser();
    if (user == null) {
      loginResult.put("success", false);
      loginResult.put("loginResult", "Wrong password.");
      return loginResult;
    }
    loginResult.put("success", true);
    JSONObject userObject =
        gqlToSql.execute("BaseUser", inspect(field, "userObject"), user.getId());
    loginResult.put("userObject", userObject);
    String type = ((String) userObject.get("__typename"));
    String id = String.valueOf(user.getId());
    String finalToken =
        jwtTokenUtil.generateToken(
            id, new UserProxy(type, user.getId(), UUID.randomUUID().toString()));
    loginResult.put("token", finalToken);
    if (deviceToken != null) {
      user.setDeviceToken(deviceToken);
      store.Database.get().save(user);
    }
    recordLoginHistory(user, true, "");
    return loginResult;
  }

  private JSONObject currentAnonymousUser(Field field) throws Exception {
    AnonymousUser user = provider.getObject().getAnonymousUser();
    return gqlToSql.execute("AnonymousUser", field, user.getId());
  }

  private JSONObject currentUser(Field field) throws Exception {
    User user = provider.getObject().getUser();
    return gqlToSql.execute("User", field, user.getId());
  }

  private void recordLoginHistory(BaseUser user, boolean success, String failureReason) {
    UserLoginRecord loginRecord = new UserLoginRecord();
    loginRecord.setTimeStamp(java.time.LocalDateTime.now());
    loginRecord.setSuccess(success);
    loginRecord.setFailureReason(failureReason);
    loginRecord.setUser(user);
    store.Database.get().save(loginRecord);
  }

  private static AppUserRole parseAppUserRole(String appRole) {
    if (appRole == null || appRole.trim().isEmpty()) {
      return AppUserRole.Viewer;
    }

    try {
      return AppUserRole.valueOf(appRole.trim());
    } catch (IllegalArgumentException exception) {
      return AppUserRole.Viewer;
    }
  }
}
