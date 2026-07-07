package d3e.core;

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
import jakarta.annotation.PostConstruct;
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
import models.BaseUser;
import models.DashboardMetricsRequest;
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
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.jpa.AnonymousUserRepository;
import repository.jpa.AuditLogRepository;
import repository.jpa.AvatarRepository;
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
import repository.jpa.ReportConfigOptionRepository;
import repository.jpa.ReportConfigRepository;
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

@Service
public class QueryProvider {
  public static QueryProvider instance;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private store.EntityMutator mutator;
  @Autowired private AnonymousUserRepository anonymousUserRepository;
  @Autowired private AuditLogRepository auditLogRepository;
  @Autowired private AvatarRepository avatarRepository;
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
  @Autowired private ReportConfigRepository reportConfigRepository;
  @Autowired private ReportConfigOptionRepository reportConfigOptionRepository;
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
  @Autowired private ObjectFactory<AppSessionProvider> provider;

  @PostConstruct
  public void init() {
    instance = this;
  }

  public static QueryProvider get() {
    return instance;
  }

  public AnonymousUser getAnonymousUserById(long id) {
    return anonymousUserRepository.findById(id);
  }

  public AuditLog getAuditLogById(long id) {
    return auditLogRepository.findById(id);
  }

  public GoodsReceipt getGoodsReceiptById(long id) {
    return goodsReceiptRepository.findById(id);
  }

  public GoodsReceiptLine getGoodsReceiptLineById(long id) {
    return goodsReceiptLineRepository.findById(id);
  }

  public InAppNotification getInAppNotificationById(long id) {
    return inAppNotificationRepository.findById(id);
  }

  public InventoryAdjustment getInventoryAdjustmentById(long id) {
    return inventoryAdjustmentRepository.findById(id);
  }

  public InventoryAdjustmentLine getInventoryAdjustmentLineById(long id) {
    return inventoryAdjustmentLineRepository.findById(id);
  }

  public InventoryMovement getInventoryMovementById(long id) {
    return inventoryMovementRepository.findById(id);
  }

  public NotificationTemplate getNotificationTemplateById(long id) {
    return notificationTemplateRepository.findById(id);
  }

  public OneTimePassword getOneTimePasswordById(long id) {
    return oneTimePasswordRepository.findById(id);
  }

  public boolean checkTokenUniqueInOneTimePassword(long oneTimePassword_id, String token) {
    return oneTimePasswordRepository.checkTokenUnique(oneTimePassword_id, token);
  }

  public Organization getOrganizationById(long id) {
    return organizationRepository.findById(id);
  }

  public boolean checkNameUniqueInOrganization(long organization_id, String name) {
    return organizationRepository.checkNameUnique(organization_id, name);
  }

  public boolean checkCodeUniqueInOrganization(long organization_id, String code) {
    return organizationRepository.checkCodeUnique(organization_id, code);
  }

  public Product getProductById(long id) {
    return productRepository.findById(id);
  }

  public ProductCategory getProductCategoryById(long id) {
    return productCategoryRepository.findById(id);
  }

  public PurchaseOrder getPurchaseOrderById(long id) {
    return purchaseOrderRepository.findById(id);
  }

  public PurchaseOrderLine getPurchaseOrderLineById(long id) {
    return purchaseOrderLineRepository.findById(id);
  }

  public PushNotification getPushNotificationById(long id) {
    return pushNotificationRepository.findById(id);
  }

  public Report getReportById(long id) {
    return reportRepository.findById(id);
  }

  public SalesOrder getSalesOrderById(long id) {
    return salesOrderRepository.findById(id);
  }

  public SalesOrderLine getSalesOrderLineById(long id) {
    return salesOrderLineRepository.findById(id);
  }

  public SalesReturn getSalesReturnById(long id) {
    return salesReturnRepository.findById(id);
  }

  public SalesReturnLine getSalesReturnLineById(long id) {
    return salesReturnLineRepository.findById(id);
  }

  public StockAlert getStockAlertById(long id) {
    return stockAlertRepository.findById(id);
  }

  public StockBatch getStockBatchById(long id) {
    return stockBatchRepository.findById(id);
  }

  public StockTransfer getStockTransferById(long id) {
    return stockTransferRepository.findById(id);
  }

  public StockTransferLine getStockTransferLineById(long id) {
    return stockTransferLineRepository.findById(id);
  }

  public Store getStoreById(long id) {
    return storeRepository.findById(id);
  }

  public SupplierContact getSupplierContactById(long id) {
    return supplierContactRepository.findById(id);
  }

  public UnitOfMeasure getUnitOfMeasureById(long id) {
    return unitOfMeasureRepository.findById(id);
  }

  public User getUserById(long id) {
    return userRepository.findById(id);
  }

  public boolean checkUserEmailUniqueInOrganization(
      long organization_id, long user_id, String email) {
    return userRepository.checkUserEmailUniqueInOrganization(organization_id, user_id, email);
  }

  public boolean checkUserPasswordUniqueInOrganization(
      long organization_id, long user_id, String password) {
    return userRepository.checkUserPasswordUniqueInOrganization(organization_id, user_id, password);
  }

  public UserDevice getUserDeviceById(long id) {
    return userDeviceRepository.findById(id);
  }

  public UserInvitation getUserInvitationById(long id) {
    return userInvitationRepository.findById(id);
  }

  public boolean checkUserInvitationTokenUniqueInOrganization(
      long organization_id, long userInvitation_id, String token) {
    return userInvitationRepository.checkUserInvitationTokenUniqueInOrganization(
        organization_id, userInvitation_id, token);
  }

  public UserLoginRecord getUserLoginRecordById(long id) {
    return userLoginRecordRepository.findById(id);
  }

  public UserProfile getUserProfileById(long id) {
    return userProfileRepository.findById(id);
  }

  public boolean checkUserProfileUserUniqueInOrganization(
      long organization_id, long userProfile_id, User user) {
    return userProfileRepository.checkUserProfileUserUniqueInOrganization(
        organization_id, userProfile_id, user);
  }

  public UserRole getUserRoleById(long id) {
    return userRoleRepository.findById(id);
  }

  public Vendor getVendorById(long id) {
    return vendorRepository.findById(id);
  }

  public VerificationData getVerificationDataById(long id) {
    return verificationDataRepository.findById(id);
  }

  public Warehouse getWarehouseById(long id) {
    return warehouseRepository.findById(id);
  }

  public WarehouseStock getWarehouseStockById(long id) {
    return warehouseStockRepository.findById(id);
  }

  public AllDevices getAllDevices(AllDevicesRequest inputs) {
    return allDevicesImpl.get(inputs);
  }

  public UserDevices getUserDevices(UserDevicesRequest inputs) {
    return userDevicesImpl.get(inputs);
  }

  public VerificationDataByToken getVerificationDataByToken(VerificationDataByTokenRequest inputs) {
    return verificationDataByTokenImpl.get(inputs);
  }

  public AllAuditLogs getAllAuditLogs(AllAuditLogsRequest inputs) {
    return allAuditLogsImpl.get(inputs);
  }

  public AllGoodsReceipts getAllGoodsReceipts(AllGoodsReceiptsRequest inputs) {
    return allGoodsReceiptsImpl.get(inputs);
  }

  public AllInAppNotifications getAllInAppNotifications(AllInAppNotificationsRequest inputs) {
    return allInAppNotificationsImpl.get(inputs);
  }

  public AllInventoryAdjustments getAllInventoryAdjustments(AllInventoryAdjustmentsRequest inputs) {
    return allInventoryAdjustmentsImpl.get(inputs);
  }

  public AllInventoryMovements getAllInventoryMovements(AllInventoryMovementsRequest inputs) {
    return allInventoryMovementsImpl.get(inputs);
  }

  public AllOrganizations getAllOrganizations() {
    return allOrganizationsImpl.get();
  }

  public AllProductCategories getAllProductCategories(AllProductCategoriesRequest inputs) {
    return allProductCategoriesImpl.get(inputs);
  }

  public AllProducts getAllProducts(AllProductsRequest inputs) {
    return allProductsImpl.get(inputs);
  }

  public AllPurchaseOrders getAllPurchaseOrders(AllPurchaseOrdersRequest inputs) {
    return allPurchaseOrdersImpl.get(inputs);
  }

  public AllSalesOrders getAllSalesOrders(AllSalesOrdersRequest inputs) {
    return allSalesOrdersImpl.get(inputs);
  }

  public AllSalesReturns getAllSalesReturns(AllSalesReturnsRequest inputs) {
    return allSalesReturnsImpl.get(inputs);
  }

  public AllStockAlerts getAllStockAlerts(AllStockAlertsRequest inputs) {
    return allStockAlertsImpl.get(inputs);
  }

  public AllStockBatches getAllStockBatches(AllStockBatchesRequest inputs) {
    return allStockBatchesImpl.get(inputs);
  }

  public AllStockTransfers getAllStockTransfers(AllStockTransfersRequest inputs) {
    return allStockTransfersImpl.get(inputs);
  }

  public AllStores getAllStores(AllStoresRequest inputs) {
    return allStoresImpl.get(inputs);
  }

  public AllSuppliers getAllSuppliers(AllSuppliersRequest inputs) {
    return allSuppliersImpl.get(inputs);
  }

  public AllUnitOfMeasures getAllUnitOfMeasures(AllUnitOfMeasuresRequest inputs) {
    return allUnitOfMeasuresImpl.get(inputs);
  }

  public AllUserInvitations getAllUserInvitations(AllUserInvitationsRequest inputs) {
    return allUserInvitationsImpl.get(inputs);
  }

  public AllUserProfiles getAllUserProfiles(AllUserProfilesRequest inputs) {
    return allUserProfilesImpl.get(inputs);
  }

  public AllWarehouses getAllWarehouses(AllWarehousesRequest inputs) {
    return allWarehousesImpl.get(inputs);
  }

  public DashboardMetrics getDashboardMetrics(DashboardMetricsRequest inputs) {
    return dashboardMetricsImpl.get(inputs);
  }

  public ExpiringBatches getExpiringBatches(ExpiringBatchesRequest inputs) {
    return expiringBatchesImpl.get(inputs);
  }

  public GoodsReceiptItem getGoodsReceiptItem(GoodsReceiptItemRequest inputs) {
    return goodsReceiptItemImpl.get(inputs);
  }

  public InventoryMovementsByDateRange getInventoryMovementsByDateRange(
      InventoryMovementsByDateRangeRequest inputs) {
    return inventoryMovementsByDateRangeImpl.get(inputs);
  }

  public LowStockItems getLowStockItems(LowStockItemsRequest inputs) {
    return lowStockItemsImpl.get(inputs);
  }

  public MovementReportRows getMovementReportRows(MovementReportRowsRequest inputs) {
    return movementReportRowsImpl.get(inputs);
  }

  public OrganizationItem getOrganizationItem(OrganizationItemRequest inputs) {
    return organizationItemImpl.get(inputs);
  }

  public OutOfStockItems getOutOfStockItems(OutOfStockItemsRequest inputs) {
    return outOfStockItemsImpl.get(inputs);
  }

  public ProductItem getProductItem(ProductItemRequest inputs) {
    return productItemImpl.get(inputs);
  }

  public ProductSearch getProductSearch(ProductSearchRequest inputs) {
    return productSearchImpl.get(inputs);
  }

  public ProductsByCategory getProductsByCategory(ProductsByCategoryRequest inputs) {
    return productsByCategoryImpl.get(inputs);
  }

  public PurchaseOrderItem getPurchaseOrderItem(PurchaseOrderItemRequest inputs) {
    return purchaseOrderItemImpl.get(inputs);
  }

  public PurchaseOrdersByStatus getPurchaseOrdersByStatus(PurchaseOrdersByStatusRequest inputs) {
    return purchaseOrdersByStatusImpl.get(inputs);
  }

  public SalesOrderItem getSalesOrderItem(SalesOrderItemRequest inputs) {
    return salesOrderItemImpl.get(inputs);
  }

  public SalesOrdersByStore getSalesOrdersByStore(SalesOrdersByStoreRequest inputs) {
    return salesOrdersByStoreImpl.get(inputs);
  }

  public StockTransferItem getStockTransferItem(StockTransferItemRequest inputs) {
    return stockTransferItemImpl.get(inputs);
  }

  public StockValuationReport getStockValuationReport(StockValuationReportRequest inputs) {
    return stockValuationReportImpl.get(inputs);
  }

  public StoreItem getStoreItem(StoreItemRequest inputs) {
    return storeItemImpl.get(inputs);
  }

  public SupplierItem getSupplierItem(SupplierItemRequest inputs) {
    return supplierItemImpl.get(inputs);
  }

  public UnreadNotificationCount getUnreadNotificationCount(UnreadNotificationCountRequest inputs) {
    return unreadNotificationCountImpl.get(inputs);
  }

  public UserProfileByUser getUserProfileByUser(UserProfileByUserRequest inputs) {
    return userProfileByUserImpl.get(inputs);
  }

  public WarehouseStockByProduct getWarehouseStockByProduct(WarehouseStockByProductRequest inputs) {
    return warehouseStockByProductImpl.get(inputs);
  }

  public WarehouseStockByWarehouse getWarehouseStockByWarehouse(
      WarehouseStockByWarehouseRequest inputs) {
    return warehouseStockByWarehouseImpl.get(inputs);
  }

  public WarehousesByStore getWarehousesByStore(WarehousesByStoreRequest inputs) {
    return warehousesByStoreImpl.get(inputs);
  }

  public LoginResult loginInventoryManagementSystemUserWithEmailAndPassword(
      String email, String password, String deviceToken) {
    User user = userRepository.getByEmail(email);
    if (deviceToken != null) {
      user.setDeviceToken(deviceToken);
    }
    LoginResult loginResult = new LoginResult();
    loginResult.setSuccess(true);
    loginResult.setUserObject(user);
    loginResult.setToken(
        jwtTokenUtil.generateToken(
            email, new UserProxy("User", user.getId(), UUID.randomUUID().toString())));
    return loginResult;
  }

  public LoginResult registerInventoryManagementSystemUser(
      String displayName, String email, String password, AppUserRole appRole) {
    return classes.RegistrationService.register(
        displayName,
        email,
        password,
        appRole,
        userRepository,
        organizationRepository,
        passwordEncoder,
        jwtTokenUtil);
  }

  public LoginResult sendInventoryManagementSystemPasswordResetOtp(String email) {
    return classes.PasswordResetService.sendOtp(email, userRepository, mutator);
  }

  public LoginResult resetInventoryManagementSystemPasswordWithOtp(
      String token, String code, String newPassword) {
    return classes.PasswordResetService.resetPassword(
        token, code, newPassword, oneTimePasswordRepository, userRepository);
  }

  public LoginResult loginWithOTP(String token, String code, String deviceToken) {
    OneTimePassword otp = oneTimePasswordRepository.getByToken(token);
    BaseUser user = otp.getUser();
    LoginResult loginResult = new LoginResult();
    if (deviceToken != null) {
      user.setDeviceToken(deviceToken);
    }
    loginResult.setSuccess(true);
    loginResult.setUserObject(otp.getUser());
    loginResult.setToken(token);
    return loginResult;
  }

  public AnonymousUser currentAnonymousUser() {
    return provider.getObject().getAnonymousUser();
  }

  public User currentUser() {
    return provider.getObject().getUser();
  }
}
