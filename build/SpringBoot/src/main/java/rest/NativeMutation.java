package rest;

import classes.MutateResultStatus;
import d3e.core.CloneContext;
import d3e.core.CurrentUser;
import d3e.core.EmailService;
import d3e.core.ListExt;
import d3e.core.Log;
import d3e.core.StringTemplate;
import d3e.core.TransactionWrapper;
import gqltosql.schema.GraphQLDataFetcher;
import gqltosql.schema.IModelSchema;
import graphql.language.Field;
import helpers.AnonymousUserEntityHelper;
import helpers.GoodsReceiptEntityHelper;
import helpers.GoodsReceiptLineEntityHelper;
import helpers.InAppNotificationEntityHelper;
import helpers.InventoryAdjustmentEntityHelper;
import helpers.InventoryAdjustmentLineEntityHelper;
import helpers.NotificationTemplateEntityHelper;
import helpers.OrganizationEntityHelper;
import helpers.ProductCategoryEntityHelper;
import helpers.ProductEntityHelper;
import helpers.PurchaseOrderEntityHelper;
import helpers.PurchaseOrderLineEntityHelper;
import helpers.SalesOrderEntityHelper;
import helpers.SalesOrderLineEntityHelper;
import helpers.SalesReturnEntityHelper;
import helpers.SalesReturnLineEntityHelper;
import helpers.StockAlertEntityHelper;
import helpers.StockBatchEntityHelper;
import helpers.StockTransferEntityHelper;
import helpers.StockTransferLineEntityHelper;
import helpers.StoreEntityHelper;
import helpers.SupplierContactEntityHelper;
import helpers.UnitOfMeasureEntityHelper;
import helpers.UserEntityHelper;
import helpers.UserInvitationEntityHelper;
import helpers.UserProfileEntityHelper;
import helpers.UserRoleEntityHelper;
import helpers.VendorEntityHelper;
import helpers.WarehouseEntityHelper;
import helpers.WarehouseStockEntityHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import models.AnonymousUser;
import models.BaseUser;
import models.EmailMessage;
import models.GoodsReceipt;
import models.GoodsReceiptLine;
import models.InAppNotification;
import models.InventoryAdjustment;
import models.InventoryAdjustmentLine;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import store.EntityHelperService;
import store.EntityMutator;
import store.SystemExternalSystem;
import store.ValidationFailedException;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/native/")
public class NativeMutation extends AbstractQueryService {
  @Autowired private EntityMutator mutator;
  @Autowired private ObjectFactory<EntityHelperService> helperService;
  @Autowired private TransactionWrapper transactionWrapper;
  @Autowired private IModelSchema schema;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private SystemExternalSystem system;
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
  @Autowired private ObjectFactory<AppSessionProvider> provider;
  @Autowired private EmailService emailService;

  @PostMapping(path = "/mutate", produces = MediaType.APPLICATION_JSON_VALUE)
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
      transactionWrapper.doInTransaction(
          () -> {
            try {
              try {
                Object res = executeOperation(query, s, variables);
                data.put(name, res);
              } catch (ValidationFailedException e) {
                data.put(name, createFailureResult(s, e));
              }
            } catch (Exception e2) {
              throw new RuntimeException(e2);
            }
          });
      Log.info("Completed: " + time + ":" + s.getName());
    }
    JSONObject output = new JSONObject();
    output.put("data", data);
    return output.toString();
  }

  private JSONObject createSuccessResult(Object value, Field field, String type)
      throws JSONException {
    JSONObject result = new JSONObject();
    result.put("status", MutateResultStatus.Success);
    result.put("errors", new JSONArray());
    if (value != null) {
      result.put(
          "value",
          new GraphQLDataFetcher(schema, true).fetch(inspect(field, "value"), type, value));
    }
    return result;
  }

  private JSONObject createFailureResult(Field field, ValidationFailedException e)
      throws JSONException {
    JSONObject result = new JSONObject();
    if (e.hasStatus()) {
      result.put("status", e.getStatus());
    } else {
      result.put("status", MutateResultStatus.ValidationFail);
    }
    JSONArray array = new JSONArray();
    e.getErrors().forEach(s -> array.put(s));
    result.put("errors", array);
    logErrors(result);
    return result;
  }

  private Object executeOperation(String query, Field field, JSONObject variables)
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
    switch (field.getName()) {
      case "createAnonymousUser":
        {
          return createSuccessResult(createAnonymousUser(ctx), field, "AnonymousUser");
        }
      case "updateAnonymousUser":
        {
          return createSuccessResult(updateAnonymousUser(ctx), field, "AnonymousUser");
        }
      case "deleteAnonymousUser":
        {
          deleteAnonymousUser(ctx);
          return createSuccessResult(null, field, "AnonymousUser");
        }
      case "createGoodsReceipt":
        {
          return createSuccessResult(createGoodsReceipt(ctx), field, "GoodsReceipt");
        }
      case "updateGoodsReceipt":
        {
          return createSuccessResult(updateGoodsReceipt(ctx), field, "GoodsReceipt");
        }
      case "deleteGoodsReceipt":
        {
          deleteGoodsReceipt(ctx);
          return createSuccessResult(null, field, "GoodsReceipt");
        }
      case "createGoodsReceiptLine":
        {
          return createSuccessResult(createGoodsReceiptLine(ctx), field, "GoodsReceiptLine");
        }
      case "updateGoodsReceiptLine":
        {
          return createSuccessResult(updateGoodsReceiptLine(ctx), field, "GoodsReceiptLine");
        }
      case "deleteGoodsReceiptLine":
        {
          deleteGoodsReceiptLine(ctx);
          return createSuccessResult(null, field, "GoodsReceiptLine");
        }
      case "updateInAppNotification":
        {
          return createSuccessResult(updateInAppNotification(ctx), field, "InAppNotification");
        }
      case "createInventoryAdjustment":
        {
          return createSuccessResult(createInventoryAdjustment(ctx), field, "InventoryAdjustment");
        }
      case "updateInventoryAdjustment":
        {
          return createSuccessResult(updateInventoryAdjustment(ctx), field, "InventoryAdjustment");
        }
      case "deleteInventoryAdjustment":
        {
          deleteInventoryAdjustment(ctx);
          return createSuccessResult(null, field, "InventoryAdjustment");
        }
      case "createInventoryAdjustmentLine":
        {
          return createSuccessResult(
              createInventoryAdjustmentLine(ctx), field, "InventoryAdjustmentLine");
        }
      case "updateInventoryAdjustmentLine":
        {
          return createSuccessResult(
              updateInventoryAdjustmentLine(ctx), field, "InventoryAdjustmentLine");
        }
      case "deleteInventoryAdjustmentLine":
        {
          deleteInventoryAdjustmentLine(ctx);
          return createSuccessResult(null, field, "InventoryAdjustmentLine");
        }
      case "createNotificationTemplate":
        {
          return createSuccessResult(
              createNotificationTemplate(ctx), field, "NotificationTemplate");
        }
      case "updateNotificationTemplate":
        {
          return createSuccessResult(
              updateNotificationTemplate(ctx), field, "NotificationTemplate");
        }
      case "deleteNotificationTemplate":
        {
          deleteNotificationTemplate(ctx);
          return createSuccessResult(null, field, "NotificationTemplate");
        }
      case "createOrganization":
        {
          return createSuccessResult(createOrganization(ctx), field, "Organization");
        }
      case "updateOrganization":
        {
          return createSuccessResult(updateOrganization(ctx), field, "Organization");
        }
      case "createProduct":
        {
          return createSuccessResult(createProduct(ctx), field, "Product");
        }
      case "updateProduct":
        {
          return createSuccessResult(updateProduct(ctx), field, "Product");
        }
      case "deleteProduct":
        {
          deleteProduct(ctx);
          return createSuccessResult(null, field, "Product");
        }
      case "createProductCategory":
        {
          return createSuccessResult(createProductCategory(ctx), field, "ProductCategory");
        }
      case "updateProductCategory":
        {
          return createSuccessResult(updateProductCategory(ctx), field, "ProductCategory");
        }
      case "deleteProductCategory":
        {
          deleteProductCategory(ctx);
          return createSuccessResult(null, field, "ProductCategory");
        }
      case "createPurchaseOrder":
        {
          return createSuccessResult(createPurchaseOrder(ctx), field, "PurchaseOrder");
        }
      case "updatePurchaseOrder":
        {
          return createSuccessResult(updatePurchaseOrder(ctx), field, "PurchaseOrder");
        }
      case "deletePurchaseOrder":
        {
          deletePurchaseOrder(ctx);
          return createSuccessResult(null, field, "PurchaseOrder");
        }
      case "createPurchaseOrderLine":
        {
          return createSuccessResult(createPurchaseOrderLine(ctx), field, "PurchaseOrderLine");
        }
      case "updatePurchaseOrderLine":
        {
          return createSuccessResult(updatePurchaseOrderLine(ctx), field, "PurchaseOrderLine");
        }
      case "deletePurchaseOrderLine":
        {
          deletePurchaseOrderLine(ctx);
          return createSuccessResult(null, field, "PurchaseOrderLine");
        }
      case "createSalesOrder":
        {
          return createSuccessResult(createSalesOrder(ctx), field, "SalesOrder");
        }
      case "updateSalesOrder":
        {
          return createSuccessResult(updateSalesOrder(ctx), field, "SalesOrder");
        }
      case "deleteSalesOrder":
        {
          deleteSalesOrder(ctx);
          return createSuccessResult(null, field, "SalesOrder");
        }
      case "createSalesOrderLine":
        {
          return createSuccessResult(createSalesOrderLine(ctx), field, "SalesOrderLine");
        }
      case "updateSalesOrderLine":
        {
          return createSuccessResult(updateSalesOrderLine(ctx), field, "SalesOrderLine");
        }
      case "deleteSalesOrderLine":
        {
          deleteSalesOrderLine(ctx);
          return createSuccessResult(null, field, "SalesOrderLine");
        }
      case "createSalesReturn":
        {
          return createSuccessResult(createSalesReturn(ctx), field, "SalesReturn");
        }
      case "updateSalesReturn":
        {
          return createSuccessResult(updateSalesReturn(ctx), field, "SalesReturn");
        }
      case "deleteSalesReturn":
        {
          deleteSalesReturn(ctx);
          return createSuccessResult(null, field, "SalesReturn");
        }
      case "createSalesReturnLine":
        {
          return createSuccessResult(createSalesReturnLine(ctx), field, "SalesReturnLine");
        }
      case "updateSalesReturnLine":
        {
          return createSuccessResult(updateSalesReturnLine(ctx), field, "SalesReturnLine");
        }
      case "deleteSalesReturnLine":
        {
          deleteSalesReturnLine(ctx);
          return createSuccessResult(null, field, "SalesReturnLine");
        }
      case "updateStockAlert":
        {
          return createSuccessResult(updateStockAlert(ctx), field, "StockAlert");
        }
      case "createStockBatch":
        {
          return createSuccessResult(createStockBatch(ctx), field, "StockBatch");
        }
      case "updateStockBatch":
        {
          return createSuccessResult(updateStockBatch(ctx), field, "StockBatch");
        }
      case "deleteStockBatch":
        {
          deleteStockBatch(ctx);
          return createSuccessResult(null, field, "StockBatch");
        }
      case "createStockTransfer":
        {
          return createSuccessResult(createStockTransfer(ctx), field, "StockTransfer");
        }
      case "updateStockTransfer":
        {
          return createSuccessResult(updateStockTransfer(ctx), field, "StockTransfer");
        }
      case "deleteStockTransfer":
        {
          deleteStockTransfer(ctx);
          return createSuccessResult(null, field, "StockTransfer");
        }
      case "createStockTransferLine":
        {
          return createSuccessResult(createStockTransferLine(ctx), field, "StockTransferLine");
        }
      case "updateStockTransferLine":
        {
          return createSuccessResult(updateStockTransferLine(ctx), field, "StockTransferLine");
        }
      case "deleteStockTransferLine":
        {
          deleteStockTransferLine(ctx);
          return createSuccessResult(null, field, "StockTransferLine");
        }
      case "createStore":
        {
          return createSuccessResult(createStore(ctx), field, "Store");
        }
      case "updateStore":
        {
          return createSuccessResult(updateStore(ctx), field, "Store");
        }
      case "deleteStore":
        {
          deleteStore(ctx);
          return createSuccessResult(null, field, "Store");
        }
      case "createSupplierContact":
        {
          return createSuccessResult(createSupplierContact(ctx), field, "SupplierContact");
        }
      case "updateSupplierContact":
        {
          return createSuccessResult(updateSupplierContact(ctx), field, "SupplierContact");
        }
      case "deleteSupplierContact":
        {
          deleteSupplierContact(ctx);
          return createSuccessResult(null, field, "SupplierContact");
        }
      case "createUnitOfMeasure":
        {
          return createSuccessResult(createUnitOfMeasure(ctx), field, "UnitOfMeasure");
        }
      case "updateUnitOfMeasure":
        {
          return createSuccessResult(updateUnitOfMeasure(ctx), field, "UnitOfMeasure");
        }
      case "deleteUnitOfMeasure":
        {
          deleteUnitOfMeasure(ctx);
          return createSuccessResult(null, field, "UnitOfMeasure");
        }
      case "updateUser":
        {
          return createSuccessResult(updateUser(ctx), field, "User");
        }
      case "createUserInvitation":
        {
          return createSuccessResult(createUserInvitation(ctx), field, "UserInvitation");
        }
      case "updateUserInvitation":
        {
          return createSuccessResult(updateUserInvitation(ctx), field, "UserInvitation");
        }
      case "deleteUserInvitation":
        {
          deleteUserInvitation(ctx);
          return createSuccessResult(null, field, "UserInvitation");
        }
      case "createUserProfile":
        {
          return createSuccessResult(createUserProfile(ctx), field, "UserProfile");
        }
      case "updateUserProfile":
        {
          return createSuccessResult(updateUserProfile(ctx), field, "UserProfile");
        }
      case "deleteUserProfile":
        {
          deleteUserProfile(ctx);
          return createSuccessResult(null, field, "UserProfile");
        }
      case "createUserRole":
        {
          return createSuccessResult(createUserRole(ctx), field, "UserRole");
        }
      case "updateUserRole":
        {
          return createSuccessResult(updateUserRole(ctx), field, "UserRole");
        }
      case "createVendor":
        {
          return createSuccessResult(createVendor(ctx), field, "Vendor");
        }
      case "updateVendor":
        {
          return createSuccessResult(updateVendor(ctx), field, "Vendor");
        }
      case "deleteVendor":
        {
          deleteVendor(ctx);
          return createSuccessResult(null, field, "Vendor");
        }
      case "createWarehouse":
        {
          return createSuccessResult(createWarehouse(ctx), field, "Warehouse");
        }
      case "updateWarehouse":
        {
          return createSuccessResult(updateWarehouse(ctx), field, "Warehouse");
        }
      case "deleteWarehouse":
        {
          deleteWarehouse(ctx);
          return createSuccessResult(null, field, "Warehouse");
        }
      case "createWarehouseStock":
        {
          return createSuccessResult(createWarehouseStock(ctx), field, "WarehouseStock");
        }
      case "updateWarehouseStock":
        {
          return createSuccessResult(updateWarehouseStock(ctx), field, "WarehouseStock");
        }
      case "generateOtpWithInventoryManagementSystemUserEmail":
        {
          return createSuccessResult(
              generateOtpWithInventoryManagementSystemUserEmail(ctx), field, "OneTimePassword");
        }
      case "changePassword":
        {
          return createSuccessResult(changePassword(ctx), field, "String");
        }
    }
    Log.info("Mutation Not found");
    return null;
  }

  private AnonymousUser createAnonymousUser(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof AnonymousUser)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    AnonymousUser newAnonymousUser = ctx.readChild("input", "AnonymousUser");
    this.mutator.save(newAnonymousUser, false);
    return newAnonymousUser;
  }

  private AnonymousUser updateAnonymousUser(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof AnonymousUser)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    AnonymousUserEntityHelper anonymousUserHelper = this.system.getHelper("AnonymousUser");
    AnonymousUser _currentAnonymousUser =
        anonymousUserRepository.findById(ctx.readLong("input", "id"));
    if (_currentAnonymousUser == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentAnonymousUser.recordOld(CloneContext.forCloneable(_currentAnonymousUser, false));
    AnonymousUser newAnonymousUser = ctx.readChild("input", "AnonymousUser");
    this.mutator.update(newAnonymousUser, false);
    return newAnonymousUser;
  }

  private void deleteAnonymousUser(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof AnonymousUser)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    AnonymousUserEntityHelper anonymousUserHelper = this.system.getHelper("AnonymousUser");
    AnonymousUser _currentAnonymousUser = anonymousUserRepository.findById(gqlInputId);
    if (_currentAnonymousUser == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentAnonymousUser, false);
  }

  private GoodsReceipt createGoodsReceipt(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    GoodsReceipt newGoodsReceipt = ctx.readChild("input", "GoodsReceipt");
    this.mutator.save(newGoodsReceipt, false);
    return newGoodsReceipt;
  }

  private GoodsReceipt updateGoodsReceipt(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    GoodsReceiptEntityHelper goodsReceiptHelper = this.system.getHelper("GoodsReceipt");
    GoodsReceipt _currentGoodsReceipt =
        goodsReceiptRepository.findById(ctx.readLong("input", "id"));
    if (_currentGoodsReceipt == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentGoodsReceipt.recordOld(CloneContext.forCloneable(_currentGoodsReceipt, false));
    GoodsReceipt newGoodsReceipt = ctx.readChild("input", "GoodsReceipt");
    this.mutator.update(newGoodsReceipt, false);
    return newGoodsReceipt;
  }

  private void deleteGoodsReceipt(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    GoodsReceiptEntityHelper goodsReceiptHelper = this.system.getHelper("GoodsReceipt");
    GoodsReceipt _currentGoodsReceipt = goodsReceiptRepository.findById(gqlInputId);
    if (_currentGoodsReceipt == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentGoodsReceipt, false);
  }

  private GoodsReceiptLine createGoodsReceiptLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    GoodsReceiptLine newGoodsReceiptLine = ctx.readChild("input", "GoodsReceiptLine");
    this.mutator.save(newGoodsReceiptLine, false);
    return newGoodsReceiptLine;
  }

  private GoodsReceiptLine updateGoodsReceiptLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    GoodsReceiptLineEntityHelper goodsReceiptLineHelper = this.system.getHelper("GoodsReceiptLine");
    GoodsReceiptLine _currentGoodsReceiptLine =
        goodsReceiptLineRepository.findById(ctx.readLong("input", "id"));
    if (_currentGoodsReceiptLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentGoodsReceiptLine.recordOld(CloneContext.forCloneable(_currentGoodsReceiptLine, false));
    GoodsReceiptLine newGoodsReceiptLine = ctx.readChild("input", "GoodsReceiptLine");
    this.mutator.update(newGoodsReceiptLine, false);
    return newGoodsReceiptLine;
  }

  private void deleteGoodsReceiptLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    GoodsReceiptLineEntityHelper goodsReceiptLineHelper = this.system.getHelper("GoodsReceiptLine");
    GoodsReceiptLine _currentGoodsReceiptLine = goodsReceiptLineRepository.findById(gqlInputId);
    if (_currentGoodsReceiptLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentGoodsReceiptLine, false);
  }

  private InAppNotification updateInAppNotification(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    InAppNotificationEntityHelper inAppNotificationHelper =
        this.system.getHelper("InAppNotification");
    InAppNotification _currentInAppNotification =
        inAppNotificationRepository.findById(ctx.readLong("input", "id"));
    if (_currentInAppNotification == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentInAppNotification.recordOld(
        CloneContext.forCloneable(_currentInAppNotification, false));
    InAppNotification newInAppNotification = ctx.readChild("input", "InAppNotification");
    this.mutator.update(newInAppNotification, false);
    return newInAppNotification;
  }

  private InventoryAdjustment createInventoryAdjustment(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    InventoryAdjustment newInventoryAdjustment = ctx.readChild("input", "InventoryAdjustment");
    this.mutator.save(newInventoryAdjustment, false);
    return newInventoryAdjustment;
  }

  private InventoryAdjustment updateInventoryAdjustment(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    InventoryAdjustmentEntityHelper inventoryAdjustmentHelper =
        this.system.getHelper("InventoryAdjustment");
    InventoryAdjustment _currentInventoryAdjustment =
        inventoryAdjustmentRepository.findById(ctx.readLong("input", "id"));
    if (_currentInventoryAdjustment == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentInventoryAdjustment.recordOld(
        CloneContext.forCloneable(_currentInventoryAdjustment, false));
    InventoryAdjustment newInventoryAdjustment = ctx.readChild("input", "InventoryAdjustment");
    this.mutator.update(newInventoryAdjustment, false);
    return newInventoryAdjustment;
  }

  private void deleteInventoryAdjustment(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    InventoryAdjustmentEntityHelper inventoryAdjustmentHelper =
        this.system.getHelper("InventoryAdjustment");
    InventoryAdjustment _currentInventoryAdjustment =
        inventoryAdjustmentRepository.findById(gqlInputId);
    if (_currentInventoryAdjustment == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentInventoryAdjustment, false);
  }

  private InventoryAdjustmentLine createInventoryAdjustmentLine(GraphQLInputContext ctx)
      throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    InventoryAdjustmentLine newInventoryAdjustmentLine =
        ctx.readChild("input", "InventoryAdjustmentLine");
    this.mutator.save(newInventoryAdjustmentLine, false);
    return newInventoryAdjustmentLine;
  }

  private InventoryAdjustmentLine updateInventoryAdjustmentLine(GraphQLInputContext ctx)
      throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    InventoryAdjustmentLineEntityHelper inventoryAdjustmentLineHelper =
        this.system.getHelper("InventoryAdjustmentLine");
    InventoryAdjustmentLine _currentInventoryAdjustmentLine =
        inventoryAdjustmentLineRepository.findById(ctx.readLong("input", "id"));
    if (_currentInventoryAdjustmentLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentInventoryAdjustmentLine.recordOld(
        CloneContext.forCloneable(_currentInventoryAdjustmentLine, false));
    InventoryAdjustmentLine newInventoryAdjustmentLine =
        ctx.readChild("input", "InventoryAdjustmentLine");
    this.mutator.update(newInventoryAdjustmentLine, false);
    return newInventoryAdjustmentLine;
  }

  private void deleteInventoryAdjustmentLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    InventoryAdjustmentLineEntityHelper inventoryAdjustmentLineHelper =
        this.system.getHelper("InventoryAdjustmentLine");
    InventoryAdjustmentLine _currentInventoryAdjustmentLine =
        inventoryAdjustmentLineRepository.findById(gqlInputId);
    if (_currentInventoryAdjustmentLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentInventoryAdjustmentLine, false);
  }

  private NotificationTemplate createNotificationTemplate(GraphQLInputContext ctx)
      throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    NotificationTemplate newNotificationTemplate = ctx.readChild("input", "NotificationTemplate");
    this.mutator.save(newNotificationTemplate, false);
    return newNotificationTemplate;
  }

  private NotificationTemplate updateNotificationTemplate(GraphQLInputContext ctx)
      throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    NotificationTemplateEntityHelper notificationTemplateHelper =
        this.system.getHelper("NotificationTemplate");
    NotificationTemplate _currentNotificationTemplate =
        notificationTemplateRepository.findById(ctx.readLong("input", "id"));
    if (_currentNotificationTemplate == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentNotificationTemplate.recordOld(
        CloneContext.forCloneable(_currentNotificationTemplate, false));
    NotificationTemplate newNotificationTemplate = ctx.readChild("input", "NotificationTemplate");
    this.mutator.update(newNotificationTemplate, false);
    return newNotificationTemplate;
  }

  private void deleteNotificationTemplate(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    NotificationTemplateEntityHelper notificationTemplateHelper =
        this.system.getHelper("NotificationTemplate");
    NotificationTemplate _currentNotificationTemplate =
        notificationTemplateRepository.findById(gqlInputId);
    if (_currentNotificationTemplate == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentNotificationTemplate, false);
  }

  private Organization createOrganization(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    Organization newOrganization = ctx.readChild("input", "Organization");
    this.mutator.save(newOrganization, false);
    return newOrganization;
  }

  private Organization updateOrganization(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    OrganizationEntityHelper organizationHelper = this.system.getHelper("Organization");
    Organization _currentOrganization =
        organizationRepository.findById(ctx.readLong("input", "id"));
    if (_currentOrganization == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentOrganization.recordOld(CloneContext.forCloneable(_currentOrganization, false));
    Organization newOrganization = ctx.readChild("input", "Organization");
    this.mutator.update(newOrganization, false);
    return newOrganization;
  }

  private Product createProduct(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    Product newProduct = ctx.readChild("input", "Product");
    this.mutator.save(newProduct, false);
    return newProduct;
  }

  private Product updateProduct(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    ProductEntityHelper productHelper = this.system.getHelper("Product");
    Product _currentProduct = productRepository.findById(ctx.readLong("input", "id"));
    if (_currentProduct == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentProduct.recordOld(CloneContext.forCloneable(_currentProduct, false));
    Product newProduct = ctx.readChild("input", "Product");
    this.mutator.update(newProduct, false);
    return newProduct;
  }

  private void deleteProduct(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    ProductEntityHelper productHelper = this.system.getHelper("Product");
    Product _currentProduct = productRepository.findById(gqlInputId);
    if (_currentProduct == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentProduct, false);
  }

  private ProductCategory createProductCategory(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    ProductCategory newProductCategory = ctx.readChild("input", "ProductCategory");
    this.mutator.save(newProductCategory, false);
    return newProductCategory;
  }

  private ProductCategory updateProductCategory(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    ProductCategoryEntityHelper productCategoryHelper = this.system.getHelper("ProductCategory");
    ProductCategory _currentProductCategory =
        productCategoryRepository.findById(ctx.readLong("input", "id"));
    if (_currentProductCategory == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentProductCategory.recordOld(CloneContext.forCloneable(_currentProductCategory, false));
    ProductCategory newProductCategory = ctx.readChild("input", "ProductCategory");
    this.mutator.update(newProductCategory, false);
    return newProductCategory;
  }

  private void deleteProductCategory(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    ProductCategoryEntityHelper productCategoryHelper = this.system.getHelper("ProductCategory");
    ProductCategory _currentProductCategory = productCategoryRepository.findById(gqlInputId);
    if (_currentProductCategory == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentProductCategory, false);
  }

  private PurchaseOrder createPurchaseOrder(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    PurchaseOrder newPurchaseOrder = ctx.readChild("input", "PurchaseOrder");
    this.mutator.save(newPurchaseOrder, false);
    return newPurchaseOrder;
  }

  private PurchaseOrder updatePurchaseOrder(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    PurchaseOrderEntityHelper purchaseOrderHelper = this.system.getHelper("PurchaseOrder");
    PurchaseOrder _currentPurchaseOrder =
        purchaseOrderRepository.findById(ctx.readLong("input", "id"));
    if (_currentPurchaseOrder == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentPurchaseOrder.recordOld(CloneContext.forCloneable(_currentPurchaseOrder, false));
    PurchaseOrder newPurchaseOrder = ctx.readChild("input", "PurchaseOrder");
    this.mutator.update(newPurchaseOrder, false);
    return newPurchaseOrder;
  }

  private void deletePurchaseOrder(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    PurchaseOrderEntityHelper purchaseOrderHelper = this.system.getHelper("PurchaseOrder");
    PurchaseOrder _currentPurchaseOrder = purchaseOrderRepository.findById(gqlInputId);
    if (_currentPurchaseOrder == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentPurchaseOrder, false);
  }

  private PurchaseOrderLine createPurchaseOrderLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    PurchaseOrderLine newPurchaseOrderLine = ctx.readChild("input", "PurchaseOrderLine");
    this.mutator.save(newPurchaseOrderLine, false);
    return newPurchaseOrderLine;
  }

  private PurchaseOrderLine updatePurchaseOrderLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    PurchaseOrderLineEntityHelper purchaseOrderLineHelper =
        this.system.getHelper("PurchaseOrderLine");
    PurchaseOrderLine _currentPurchaseOrderLine =
        purchaseOrderLineRepository.findById(ctx.readLong("input", "id"));
    if (_currentPurchaseOrderLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentPurchaseOrderLine.recordOld(
        CloneContext.forCloneable(_currentPurchaseOrderLine, false));
    PurchaseOrderLine newPurchaseOrderLine = ctx.readChild("input", "PurchaseOrderLine");
    this.mutator.update(newPurchaseOrderLine, false);
    return newPurchaseOrderLine;
  }

  private void deletePurchaseOrderLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    PurchaseOrderLineEntityHelper purchaseOrderLineHelper =
        this.system.getHelper("PurchaseOrderLine");
    PurchaseOrderLine _currentPurchaseOrderLine = purchaseOrderLineRepository.findById(gqlInputId);
    if (_currentPurchaseOrderLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentPurchaseOrderLine, false);
  }

  private SalesOrder createSalesOrder(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    SalesOrder newSalesOrder = ctx.readChild("input", "SalesOrder");
    this.mutator.save(newSalesOrder, false);
    return newSalesOrder;
  }

  private SalesOrder updateSalesOrder(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    SalesOrderEntityHelper salesOrderHelper = this.system.getHelper("SalesOrder");
    SalesOrder _currentSalesOrder = salesOrderRepository.findById(ctx.readLong("input", "id"));
    if (_currentSalesOrder == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentSalesOrder.recordOld(CloneContext.forCloneable(_currentSalesOrder, false));
    SalesOrder newSalesOrder = ctx.readChild("input", "SalesOrder");
    this.mutator.update(newSalesOrder, false);
    return newSalesOrder;
  }

  private void deleteSalesOrder(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    SalesOrderEntityHelper salesOrderHelper = this.system.getHelper("SalesOrder");
    SalesOrder _currentSalesOrder = salesOrderRepository.findById(gqlInputId);
    if (_currentSalesOrder == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentSalesOrder, false);
  }

  private SalesOrderLine createSalesOrderLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    SalesOrderLine newSalesOrderLine = ctx.readChild("input", "SalesOrderLine");
    this.mutator.save(newSalesOrderLine, false);
    return newSalesOrderLine;
  }

  private SalesOrderLine updateSalesOrderLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    SalesOrderLineEntityHelper salesOrderLineHelper = this.system.getHelper("SalesOrderLine");
    SalesOrderLine _currentSalesOrderLine =
        salesOrderLineRepository.findById(ctx.readLong("input", "id"));
    if (_currentSalesOrderLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentSalesOrderLine.recordOld(CloneContext.forCloneable(_currentSalesOrderLine, false));
    SalesOrderLine newSalesOrderLine = ctx.readChild("input", "SalesOrderLine");
    this.mutator.update(newSalesOrderLine, false);
    return newSalesOrderLine;
  }

  private void deleteSalesOrderLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    SalesOrderLineEntityHelper salesOrderLineHelper = this.system.getHelper("SalesOrderLine");
    SalesOrderLine _currentSalesOrderLine = salesOrderLineRepository.findById(gqlInputId);
    if (_currentSalesOrderLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentSalesOrderLine, false);
  }

  private SalesReturn createSalesReturn(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    SalesReturn newSalesReturn = ctx.readChild("input", "SalesReturn");
    this.mutator.save(newSalesReturn, false);
    return newSalesReturn;
  }

  private SalesReturn updateSalesReturn(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    SalesReturnEntityHelper salesReturnHelper = this.system.getHelper("SalesReturn");
    SalesReturn _currentSalesReturn = salesReturnRepository.findById(ctx.readLong("input", "id"));
    if (_currentSalesReturn == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentSalesReturn.recordOld(CloneContext.forCloneable(_currentSalesReturn, false));
    SalesReturn newSalesReturn = ctx.readChild("input", "SalesReturn");
    this.mutator.update(newSalesReturn, false);
    return newSalesReturn;
  }

  private void deleteSalesReturn(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    SalesReturnEntityHelper salesReturnHelper = this.system.getHelper("SalesReturn");
    SalesReturn _currentSalesReturn = salesReturnRepository.findById(gqlInputId);
    if (_currentSalesReturn == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentSalesReturn, false);
  }

  private SalesReturnLine createSalesReturnLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    SalesReturnLine newSalesReturnLine = ctx.readChild("input", "SalesReturnLine");
    this.mutator.save(newSalesReturnLine, false);
    return newSalesReturnLine;
  }

  private SalesReturnLine updateSalesReturnLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    SalesReturnLineEntityHelper salesReturnLineHelper = this.system.getHelper("SalesReturnLine");
    SalesReturnLine _currentSalesReturnLine =
        salesReturnLineRepository.findById(ctx.readLong("input", "id"));
    if (_currentSalesReturnLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentSalesReturnLine.recordOld(CloneContext.forCloneable(_currentSalesReturnLine, false));
    SalesReturnLine newSalesReturnLine = ctx.readChild("input", "SalesReturnLine");
    this.mutator.update(newSalesReturnLine, false);
    return newSalesReturnLine;
  }

  private void deleteSalesReturnLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    SalesReturnLineEntityHelper salesReturnLineHelper = this.system.getHelper("SalesReturnLine");
    SalesReturnLine _currentSalesReturnLine = salesReturnLineRepository.findById(gqlInputId);
    if (_currentSalesReturnLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentSalesReturnLine, false);
  }

  private StockAlert updateStockAlert(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    StockAlertEntityHelper stockAlertHelper = this.system.getHelper("StockAlert");
    StockAlert _currentStockAlert = stockAlertRepository.findById(ctx.readLong("input", "id"));
    if (_currentStockAlert == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentStockAlert.recordOld(CloneContext.forCloneable(_currentStockAlert, false));
    StockAlert newStockAlert = ctx.readChild("input", "StockAlert");
    this.mutator.update(newStockAlert, false);
    return newStockAlert;
  }

  private StockBatch createStockBatch(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    StockBatch newStockBatch = ctx.readChild("input", "StockBatch");
    this.mutator.save(newStockBatch, false);
    return newStockBatch;
  }

  private StockBatch updateStockBatch(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    StockBatchEntityHelper stockBatchHelper = this.system.getHelper("StockBatch");
    StockBatch _currentStockBatch = stockBatchRepository.findById(ctx.readLong("input", "id"));
    if (_currentStockBatch == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentStockBatch.recordOld(CloneContext.forCloneable(_currentStockBatch, false));
    StockBatch newStockBatch = ctx.readChild("input", "StockBatch");
    this.mutator.update(newStockBatch, false);
    return newStockBatch;
  }

  private void deleteStockBatch(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    StockBatchEntityHelper stockBatchHelper = this.system.getHelper("StockBatch");
    StockBatch _currentStockBatch = stockBatchRepository.findById(gqlInputId);
    if (_currentStockBatch == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentStockBatch, false);
  }

  private StockTransfer createStockTransfer(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    StockTransfer newStockTransfer = ctx.readChild("input", "StockTransfer");
    this.mutator.save(newStockTransfer, false);
    return newStockTransfer;
  }

  private StockTransfer updateStockTransfer(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    StockTransferEntityHelper stockTransferHelper = this.system.getHelper("StockTransfer");
    StockTransfer _currentStockTransfer =
        stockTransferRepository.findById(ctx.readLong("input", "id"));
    if (_currentStockTransfer == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentStockTransfer.recordOld(CloneContext.forCloneable(_currentStockTransfer, false));
    StockTransfer newStockTransfer = ctx.readChild("input", "StockTransfer");
    this.mutator.update(newStockTransfer, false);
    return newStockTransfer;
  }

  private void deleteStockTransfer(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    StockTransferEntityHelper stockTransferHelper = this.system.getHelper("StockTransfer");
    StockTransfer _currentStockTransfer = stockTransferRepository.findById(gqlInputId);
    if (_currentStockTransfer == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentStockTransfer, false);
  }

  private StockTransferLine createStockTransferLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    StockTransferLine newStockTransferLine = ctx.readChild("input", "StockTransferLine");
    this.mutator.save(newStockTransferLine, false);
    return newStockTransferLine;
  }

  private StockTransferLine updateStockTransferLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    StockTransferLineEntityHelper stockTransferLineHelper =
        this.system.getHelper("StockTransferLine");
    StockTransferLine _currentStockTransferLine =
        stockTransferLineRepository.findById(ctx.readLong("input", "id"));
    if (_currentStockTransferLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentStockTransferLine.recordOld(
        CloneContext.forCloneable(_currentStockTransferLine, false));
    StockTransferLine newStockTransferLine = ctx.readChild("input", "StockTransferLine");
    this.mutator.update(newStockTransferLine, false);
    return newStockTransferLine;
  }

  private void deleteStockTransferLine(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    StockTransferLineEntityHelper stockTransferLineHelper =
        this.system.getHelper("StockTransferLine");
    StockTransferLine _currentStockTransferLine = stockTransferLineRepository.findById(gqlInputId);
    if (_currentStockTransferLine == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentStockTransferLine, false);
  }

  private Store createStore(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    Store newStore = ctx.readChild("input", "Store");
    this.mutator.save(newStore, false);
    return newStore;
  }

  private Store updateStore(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    StoreEntityHelper storeHelper = this.system.getHelper("Store");
    Store _currentStore = storeRepository.findById(ctx.readLong("input", "id"));
    if (_currentStore == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentStore.recordOld(CloneContext.forCloneable(_currentStore, false));
    Store newStore = ctx.readChild("input", "Store");
    this.mutator.update(newStore, false);
    return newStore;
  }

  private void deleteStore(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    StoreEntityHelper storeHelper = this.system.getHelper("Store");
    Store _currentStore = storeRepository.findById(gqlInputId);
    if (_currentStore == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentStore, false);
  }

  private SupplierContact createSupplierContact(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    SupplierContact newSupplierContact = ctx.readChild("input", "SupplierContact");
    this.mutator.save(newSupplierContact, false);
    return newSupplierContact;
  }

  private SupplierContact updateSupplierContact(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    SupplierContactEntityHelper supplierContactHelper = this.system.getHelper("SupplierContact");
    SupplierContact _currentSupplierContact =
        supplierContactRepository.findById(ctx.readLong("input", "id"));
    if (_currentSupplierContact == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentSupplierContact.recordOld(CloneContext.forCloneable(_currentSupplierContact, false));
    SupplierContact newSupplierContact = ctx.readChild("input", "SupplierContact");
    this.mutator.update(newSupplierContact, false);
    return newSupplierContact;
  }

  private void deleteSupplierContact(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    SupplierContactEntityHelper supplierContactHelper = this.system.getHelper("SupplierContact");
    SupplierContact _currentSupplierContact = supplierContactRepository.findById(gqlInputId);
    if (_currentSupplierContact == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentSupplierContact, false);
  }

  private UnitOfMeasure createUnitOfMeasure(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    UnitOfMeasure newUnitOfMeasure = ctx.readChild("input", "UnitOfMeasure");
    this.mutator.save(newUnitOfMeasure, false);
    return newUnitOfMeasure;
  }

  private UnitOfMeasure updateUnitOfMeasure(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    UnitOfMeasureEntityHelper unitOfMeasureHelper = this.system.getHelper("UnitOfMeasure");
    UnitOfMeasure _currentUnitOfMeasure =
        unitOfMeasureRepository.findById(ctx.readLong("input", "id"));
    if (_currentUnitOfMeasure == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentUnitOfMeasure.recordOld(CloneContext.forCloneable(_currentUnitOfMeasure, false));
    UnitOfMeasure newUnitOfMeasure = ctx.readChild("input", "UnitOfMeasure");
    this.mutator.update(newUnitOfMeasure, false);
    return newUnitOfMeasure;
  }

  private void deleteUnitOfMeasure(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    UnitOfMeasureEntityHelper unitOfMeasureHelper = this.system.getHelper("UnitOfMeasure");
    UnitOfMeasure _currentUnitOfMeasure = unitOfMeasureRepository.findById(gqlInputId);
    if (_currentUnitOfMeasure == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentUnitOfMeasure, false);
  }

  private User updateUser(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    UserEntityHelper userHelper = this.system.getHelper("User");
    User _currentUser = userRepository.findById(ctx.readLong("input", "id"));
    if (_currentUser == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentUser.recordOld(CloneContext.forCloneable(_currentUser, false));
    User newUser = ctx.readChild("input", "User");
    this.mutator.update(newUser, false);
    return newUser;
  }

  private UserInvitation createUserInvitation(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    UserInvitation newUserInvitation = ctx.readChild("input", "UserInvitation");
    this.mutator.save(newUserInvitation, false);
    return newUserInvitation;
  }

  private UserInvitation updateUserInvitation(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    UserInvitationEntityHelper userInvitationHelper = this.system.getHelper("UserInvitation");
    UserInvitation _currentUserInvitation =
        userInvitationRepository.findById(ctx.readLong("input", "id"));
    if (_currentUserInvitation == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentUserInvitation.recordOld(CloneContext.forCloneable(_currentUserInvitation, false));
    UserInvitation newUserInvitation = ctx.readChild("input", "UserInvitation");
    this.mutator.update(newUserInvitation, false);
    return newUserInvitation;
  }

  private void deleteUserInvitation(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    UserInvitationEntityHelper userInvitationHelper = this.system.getHelper("UserInvitation");
    UserInvitation _currentUserInvitation = userInvitationRepository.findById(gqlInputId);
    if (_currentUserInvitation == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentUserInvitation, false);
  }

  private UserProfile createUserProfile(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    UserProfile newUserProfile = ctx.readChild("input", "UserProfile");
    this.mutator.save(newUserProfile, false);
    return newUserProfile;
  }

  private UserProfile updateUserProfile(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    UserProfileEntityHelper userProfileHelper = this.system.getHelper("UserProfile");
    UserProfile _currentUserProfile = userProfileRepository.findById(ctx.readLong("input", "id"));
    if (_currentUserProfile == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentUserProfile.recordOld(CloneContext.forCloneable(_currentUserProfile, false));
    UserProfile newUserProfile = ctx.readChild("input", "UserProfile");
    this.mutator.update(newUserProfile, false);
    return newUserProfile;
  }

  private void deleteUserProfile(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    UserProfileEntityHelper userProfileHelper = this.system.getHelper("UserProfile");
    UserProfile _currentUserProfile = userProfileRepository.findById(gqlInputId);
    if (_currentUserProfile == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentUserProfile, false);
  }

  private UserRole createUserRole(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    UserRole newUserRole = ctx.readChild("input", "UserRole");
    this.mutator.save(newUserRole, false);
    return newUserRole;
  }

  private UserRole updateUserRole(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    UserRoleEntityHelper userRoleHelper = this.system.getHelper("UserRole");
    UserRole _currentUserRole = userRoleRepository.findById(ctx.readLong("input", "id"));
    if (_currentUserRole == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentUserRole.recordOld(CloneContext.forCloneable(_currentUserRole, false));
    UserRole newUserRole = ctx.readChild("input", "UserRole");
    this.mutator.update(newUserRole, false);
    return newUserRole;
  }

  private Vendor createVendor(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    Vendor newVendor = ctx.readChild("input", "Vendor");
    this.mutator.save(newVendor, false);
    sendSupplierWelcomeEmail(newVendor);
    return newVendor;
  }

  private Vendor updateVendor(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    VendorEntityHelper vendorHelper = this.system.getHelper("Vendor");
    Vendor _currentVendor = vendorRepository.findById(ctx.readLong("input", "id"));
    if (_currentVendor == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentVendor.recordOld(CloneContext.forCloneable(_currentVendor, false));
    Vendor newVendor = ctx.readChild("input", "Vendor");
    this.mutator.update(newVendor, false);
    return newVendor;
  }

  private void deleteVendor(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    VendorEntityHelper vendorHelper = this.system.getHelper("Vendor");
    Vendor _currentVendor = vendorRepository.findById(gqlInputId);
    if (_currentVendor == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentVendor, false);
  }

  private Warehouse createWarehouse(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    Warehouse newWarehouse = ctx.readChild("input", "Warehouse");
    this.mutator.save(newWarehouse, false);
    return newWarehouse;
  }

  private Warehouse updateWarehouse(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    WarehouseEntityHelper warehouseHelper = this.system.getHelper("Warehouse");
    Warehouse _currentWarehouse = warehouseRepository.findById(ctx.readLong("input", "id"));
    if (_currentWarehouse == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentWarehouse.recordOld(CloneContext.forCloneable(_currentWarehouse, false));
    Warehouse newWarehouse = ctx.readChild("input", "Warehouse");
    this.mutator.update(newWarehouse, false);
    return newWarehouse;
  }

  private void deleteWarehouse(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have delete permissions for this model."));
    }
    long gqlInputId = ctx.readLong("input");
    WarehouseEntityHelper warehouseHelper = this.system.getHelper("Warehouse");
    Warehouse _currentWarehouse = warehouseRepository.findById(gqlInputId);
    if (_currentWarehouse == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID"));
    }
    this.mutator.delete(_currentWarehouse, false);
  }

  private WarehouseStock createWarehouseStock(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have create permissions for this model."));
    }
    WarehouseStock newWarehouseStock = ctx.readChild("input", "WarehouseStock");
    return classes.WarehouseStockService.createWarehouseStock(newWarehouseStock);
  }

  private WarehouseStock updateWarehouseStock(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Current user type does not have update permissions for this model."));
    }
    WarehouseStockEntityHelper warehouseStockHelper = this.system.getHelper("WarehouseStock");
    WarehouseStock _currentWarehouseStock =
        warehouseStockRepository.findById(ctx.readLong("input", "id"));
    if (_currentWarehouseStock == null) {
      throw new ValidationFailedException(
          MutateResultStatus.BadRequest, ListExt.asList("Invalid ID."));
    }
    _currentWarehouseStock.recordOld(CloneContext.forCloneable(_currentWarehouseStock, false));
    WarehouseStock newWarehouseStock = ctx.readChild("input", "WarehouseStock");
    this.mutator.update(newWarehouseStock, false);
    return newWarehouseStock;
  }

  private OneTimePassword generateOtpWithInventoryManagementSystemUserEmail(GraphQLInputContext ctx)
      throws Exception {
    String email = ctx.readString("input");
    OneTimePassword otpGen = new OneTimePassword();
    otpGen.setUserType("User");
    otpGen.setInputType("email");
    otpGen.setInput(email);
    this.mutator.save(otpGen, true);
    return otpGen;
  }

  private void sendEmailToUser(OneTimePassword otp, User user) {
    String email = user.getEmail();
    StringTemplate forSubject = StringTemplate.fromString("One Time Password");
    forSubject.put("user", user);
    forSubject.put("otp", otp);
    StringTemplate forBody =
        StringTemplate.fromString("Your OTP is: $!{otp.code} It is valid for 10 minutes.");
    forBody.put("user", user);
    forBody.put("otp", otp);
    sendEmail(email, forSubject.merge(), forBody.merge());
  }

  private void sendEmail(String sendTo, String subject, String body) {
    EmailMessage msg = new EmailMessage();
    msg.setTo(ListExt.asList(sendTo));
    msg.setSubject(subject);
    msg.setBody(body);
    this.emailService.send(msg);
  }

  private void sendSupplierWelcomeEmail(Vendor vendor) {
    if (vendor == null) {
      return;
    }
    String email = vendor.getEmail();
    if (email == null || email.trim().isEmpty() || !email.contains("@")) {
      return;
    }

    String supplierName = vendor.getName() != null ? vendor.getName().trim() : "Supplier";
    String supplierCode = vendor.getCode() != null ? vendor.getCode().trim() : "";
    String organizationName = "FreshMart";
    Organization organization = vendor.getOrganization();
    if (organization != null
        && organization.getName() != null
        && !organization.getName().trim().isEmpty()) {
      organizationName = organization.getName().trim();
    }

    String subject = "Welcome to " + organizationName + " Supplier Network";
    String body =
        "Hello"
            + (vendor.getContactPerson() != null && !vendor.getContactPerson().trim().isEmpty()
                ? " " + vendor.getContactPerson().trim()
                : "")
            + ",\n\n"
            + "You have been registered as a supplier partner with "
            + organizationName
            + ".\n\n"
            + "Supplier: "
            + supplierName
            + (supplierCode.isEmpty() ? "" : " (" + supplierCode + ")")
            + "\n"
            + "Our procurement team may contact you for purchase orders and goods receipts.\n\n"
            + "Thank you,\n"
            + organizationName
            + " Procurement Team";

    sendEmail(email.trim(), subject, body);
  }

  private String generateToken() {
    char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    return generateRandomString(chars, 32);
  }

  private String generateDemoCode() {
    String digits = "1234567890";
    return digits.substring(0, 4);
  }

  private String generateCode() {
    char[] digits = "1234567890".toCharArray();
    return generateRandomString(digits, 4);
  }

  private String generateRandomString(char[] array, int length) {
    StringBuilder sb = new StringBuilder(length);
    Random rnd = new Random();
    for (int i = 0; i < length; i++) {
      char c = array[rnd.nextInt(array.length)];
      sb.append(c);
    }
    return sb.toString();
  }

  private String changePassword(GraphQLInputContext ctx) throws Exception {
    BaseUser currentUser = CurrentUser.get();
    if (currentUser == null || !(currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Invalid change password request for current user."));
    }
    String password = ctx.readString("input");
    if (currentUser instanceof User) {
      User user = ((User) currentUser);
      user.setPassword(passwordEncoder.encode(password));
      this.mutator.update(user, true);
    }
    return "";
  }
}
