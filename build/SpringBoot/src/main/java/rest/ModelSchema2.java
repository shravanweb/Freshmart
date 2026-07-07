package rest;

import d3e.core.SchemaConstants;
import gqltosql.schema.DModel;
import gqltosql.schema.FieldPrimitiveType;
import java.util.HashMap;
import java.util.Map;
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

public class ModelSchema2 {
  private Map<String, DModel<?>> allTypes = new HashMap<>();

  public ModelSchema2(Map<String, DModel<?>> allTypes) {
    this.allTypes = allTypes;
  }

  public void createAllTables() {
    addStockTransferFields();
    addStockTransferItemRequestFields();
    addStockTransferLineFields();
    addStockValuationReportRequestFields();
    addStoreFields();
    addStoreItemRequestFields();
    addSupplierContactFields();
    addSupplierItemRequestFields();
    addUnitOfMeasureFields();
    addUnreadNotificationCountRequestFields();
    addUserFields();
    addUserDeviceFields();
    addUserDevicesRequestFields();
    addUserInvitationFields();
    addUserLoginRecordFields();
    addUserProfileFields();
    addUserProfileByUserRequestFields();
    addUserRoleFields();
    addVendorFields();
    addVerificationDataFields();
    addVerificationDataByTokenRequestFields();
    addWarehouseFields();
    addWarehouseStockFields();
    addWarehouseStockByProductRequestFields();
    addWarehouseStockByWarehouseRequestFields();
    addWarehousesByStoreRequestFields();
  }

  public DModel<?> getType(String type) {
    return allTypes.get(type);
  }

  public <T> DModel<T> getType2(String type) {
    return ((DModel<T>) allTypes.get(type));
  }

  private void addStockTransferFields() {
    DModel<StockTransfer> m = getType2("StockTransfer");
    m.addPrimitive(
            "transferNumber",
            StockTransfer._TRANSFERNUMBER,
            "_transfer_number",
            FieldPrimitiveType.String,
            (s) -> s.getTransferNumber(),
            (s, v) -> s.setTransferNumber(v))
        .readOnly();
    m.addReference(
        "sourceWarehouse",
        StockTransfer._SOURCEWAREHOUSE,
        "_source_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getSourceWarehouse(),
        (s, v) -> s.setSourceWarehouse(v));
    m.addReference(
        "destinationWarehouse",
        StockTransfer._DESTINATIONWAREHOUSE,
        "_destination_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getDestinationWarehouse(),
        (s, v) -> s.setDestinationWarehouse(v));
    m.addPrimitive(
        "transferDate",
        StockTransfer._TRANSFERDATE,
        "_transfer_date",
        FieldPrimitiveType.Date,
        (s) -> s.getTransferDate(),
        (s, v) -> s.setTransferDate(v));
    m.addEnum(
        "status",
        StockTransfer._STATUS,
        "_status",
        getType("StockTransferStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
        "notes",
        StockTransfer._NOTES,
        "_notes",
        FieldPrimitiveType.String,
        (s) -> s.getNotes(),
        (s, v) -> s.setNotes(v));
    m.addReference(
            "requestedBy",
            StockTransfer._REQUESTEDBY,
            "_requested_by_id",
            false,
            getType("User"),
            (s) -> s.getRequestedBy(),
            (s, v) -> s.setRequestedBy(v))
        .readOnly();
    m.addReference(
            "approvedBy",
            StockTransfer._APPROVEDBY,
            "_approved_by_id",
            false,
            getType("User"),
            (s) -> s.getApprovedBy(),
            (s, v) -> s.setApprovedBy(v))
        .readOnly();
    m.addPrimitive(
            "shippedAt",
            StockTransfer._SHIPPEDAT,
            "_shipped_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getShippedAt(),
            (s, v) -> s.setShippedAt(v))
        .readOnly();
    m.addPrimitive(
            "receivedAt",
            StockTransfer._RECEIVEDAT,
            "_received_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getReceivedAt(),
            (s, v) -> s.setReceivedAt(v))
        .readOnly();
    m.addInverseCollection(
        "lines",
        StockTransfer._LINES,
        "_stock_transfer_id",
        getType("StockTransferLine"),
        (s) -> s.getLines());
    m.addReference(
        "organization",
        StockTransfer._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addStockTransferItemRequestFields() {
    DModel<StockTransferItemRequest> m = getType2("StockTransferItemRequest");
    m.addReference(
        "stockTransfer",
        StockTransferItemRequest._STOCKTRANSFER,
        "null_id",
        false,
        getType("StockTransfer"),
        (s) -> s.getStockTransfer(),
        (s, v) -> s.setStockTransfer(v));
  }

  private void addStockTransferLineFields() {
    DModel<StockTransferLine> m = getType2("StockTransferLine");
    m.addReference(
        "product",
        StockTransferLine._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addPrimitive(
        "quantity",
        StockTransferLine._QUANTITY,
        "_quantity",
        FieldPrimitiveType.Double,
        (s) -> s.getQuantity(),
        (s, v) -> s.setQuantity(v));
    m.addPrimitive(
        "batchNumber",
        StockTransferLine._BATCHNUMBER,
        "_batch_number",
        FieldPrimitiveType.String,
        (s) -> s.getBatchNumber(),
        (s, v) -> s.setBatchNumber(v));
    m.addReference(
        "uom",
        StockTransferLine._UOM,
        "_uom_id",
        false,
        getType("UnitOfMeasure"),
        (s) -> s.getUom(),
        (s, v) -> s.setUom(v));
    m.addReference(
        "stockTransfer",
        StockTransferLine._STOCKTRANSFER,
        "_stock_transfer_id",
        false,
        getType("StockTransfer"),
        (s) -> s.getStockTransfer(),
        (s, v) -> s.setStockTransfer(v));
  }

  private void addStockValuationReportRequestFields() {
    DModel<StockValuationReportRequest> m = getType2("StockValuationReportRequest");
    m.addReference(
        "organization",
        StockValuationReportRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addReference(
        "warehouse",
        StockValuationReportRequest._WAREHOUSE,
        "null_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
  }

  private void addStoreFields() {
    DModel<Store> m = getType2("Store");
    m.addPrimitive(
        "name",
        Store._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "code",
        Store._CODE,
        "_code",
        FieldPrimitiveType.String,
        (s) -> s.getCode(),
        (s, v) -> s.setCode(v));
    m.addEnum(
        "storeType",
        Store._STORETYPE,
        "_store_type",
        getType("StoreType"),
        (s) -> s.getStoreType(),
        (s, v) -> s.setStoreType(v));
    m.addPrimitive(
        "address",
        Store._ADDRESS,
        "_address",
        FieldPrimitiveType.String,
        (s) -> s.getAddress(),
        (s, v) -> s.setAddress(v));
    m.addPrimitive(
        "phone",
        Store._PHONE,
        "_phone",
        FieldPrimitiveType.String,
        (s) -> s.getPhone(),
        (s, v) -> s.setPhone(v));
    m.addPrimitive(
        "email",
        Store._EMAIL,
        "_email",
        FieldPrimitiveType.String,
        (s) -> s.getEmail(),
        (s, v) -> s.setEmail(v));
    m.addReference(
        "manager",
        Store._MANAGER,
        "_manager_id",
        false,
        getType("User"),
        (s) -> s.getManager(),
        (s, v) -> s.setManager(v));
    m.addReference(
        "defaultWarehouse",
        Store._DEFAULTWAREHOUSE,
        "_default_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getDefaultWarehouse(),
        (s, v) -> s.setDefaultWarehouse(v));
    m.addInverseCollection(
        "warehouses",
        Store._WAREHOUSES,
        "_store_id",
        getType("Warehouse"),
        (s) -> s.getWarehouses());
    m.addEnum(
        "status",
        Store._STATUS,
        "_status",
        getType("EntityStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "createdAt",
            Store._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addPrimitive(
            "updatedAt",
            Store._UPDATEDAT,
            "_updated_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getUpdatedAt(),
            (s, v) -> s.setUpdatedAt(v))
        .readOnly();
    m.addReference(
        "organization",
        Store._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addStoreItemRequestFields() {
    DModel<StoreItemRequest> m = getType2("StoreItemRequest");
    m.addReference(
        "store",
        StoreItemRequest._STORE,
        "null_id",
        false,
        getType("Store"),
        (s) -> s.getStore(),
        (s, v) -> s.setStore(v));
  }

  private void addSupplierContactFields() {
    DModel<SupplierContact> m = getType2("SupplierContact");
    m.addPrimitive(
        "name",
        SupplierContact._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "email",
        SupplierContact._EMAIL,
        "_email",
        FieldPrimitiveType.String,
        (s) -> s.getEmail(),
        (s, v) -> s.setEmail(v));
    m.addPrimitive(
        "phone",
        SupplierContact._PHONE,
        "_phone",
        FieldPrimitiveType.String,
        (s) -> s.getPhone(),
        (s, v) -> s.setPhone(v));
    m.addPrimitive(
        "isPrimary",
        SupplierContact._ISPRIMARY,
        "_is_primary",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsPrimary(),
        (s, v) -> s.setIsPrimary(v));
    m.addReference(
        "vendor",
        SupplierContact._VENDOR,
        "_vendor_id",
        false,
        getType("Vendor"),
        (s) -> s.getVendor(),
        (s, v) -> s.setVendor(v));
  }

  private void addSupplierItemRequestFields() {
    DModel<SupplierItemRequest> m = getType2("SupplierItemRequest");
    m.addReference(
        "vendor",
        SupplierItemRequest._VENDOR,
        "null_id",
        false,
        getType("Vendor"),
        (s) -> s.getVendor(),
        (s, v) -> s.setVendor(v));
  }

  private void addUnitOfMeasureFields() {
    DModel<UnitOfMeasure> m = getType2("UnitOfMeasure");
    m.addPrimitive(
        "name",
        UnitOfMeasure._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "symbol",
        UnitOfMeasure._SYMBOL,
        "_symbol",
        FieldPrimitiveType.String,
        (s) -> s.getSymbol(),
        (s, v) -> s.setSymbol(v));
    m.addEnum(
        "uomType",
        UnitOfMeasure._UOMTYPE,
        "_uom_type",
        getType("UomType"),
        (s) -> s.getUomType(),
        (s, v) -> s.setUomType(v));
    m.addPrimitive(
        "baseFactor",
        UnitOfMeasure._BASEFACTOR,
        "_base_factor",
        FieldPrimitiveType.Double,
        (s) -> s.getBaseFactor(),
        (s, v) -> s.setBaseFactor(v));
    m.addEnum(
        "status",
        UnitOfMeasure._STATUS,
        "_status",
        getType("EntityStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addReference(
        "organization",
        UnitOfMeasure._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addUnreadNotificationCountRequestFields() {
    DModel<UnreadNotificationCountRequest> m = getType2("UnreadNotificationCountRequest");
    m.addReference(
        "organization",
        UnreadNotificationCountRequest._ORGANIZATION,
        "null_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
    m.addReference(
        "user",
        UnreadNotificationCountRequest._USER,
        "null_id",
        false,
        getType("User"),
        (s) -> s.getUser(),
        (s, v) -> s.setUser(v));
  }

  private void addUserFields() {
    DModel<User> m = getType2("User");
    m.setParent(getType("BaseUser"));
    m.addPrimitive(
        "email",
        User._EMAIL,
        "_email",
        FieldPrimitiveType.String,
        (s) -> s.getEmail(),
        (s, v) -> s.setEmail(v));
    m.addPrimitive(
        "password",
        User._PASSWORD,
        "_password",
        FieldPrimitiveType.String,
        (s) -> s.getPassword(),
        (s, v) -> s.setPassword(v));
    m.addEnum(
        "role",
        User._ROLE,
        "_role",
        getType("AppUserRole"),
        (s) -> s.getRole(),
        (s, v) -> s.setRole(v));
    m.addEnum(
        "status",
        User._STATUS,
        "_status",
        getType("EntityStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addReference(
        "organization",
        User._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addUserDeviceFields() {
    DModel<UserDevice> m = getType2("UserDevice");
    m.addReference(
        "user",
        UserDevice._USER,
        "_user_id",
        false,
        getType("BaseUser"),
        (s) -> s.getUser(),
        (s, v) -> s.setUser(v));
    m.addPrimitive(
        "deviceToken",
        UserDevice._DEVICETOKEN,
        "_device_token",
        FieldPrimitiveType.String,
        (s) -> s.getDeviceToken(),
        (s, v) -> s.setDeviceToken(v));
  }

  private void addUserDevicesRequestFields() {
    DModel<UserDevicesRequest> m = getType2("UserDevicesRequest");
    m.addReference(
        "user",
        UserDevicesRequest._USER,
        "null_id",
        false,
        getType("BaseUser"),
        (s) -> s.getUser(),
        (s, v) -> s.setUser(v));
    m.addPrimitive(
        "token",
        UserDevicesRequest._TOKEN,
        "null",
        FieldPrimitiveType.String,
        (s) -> s.getToken(),
        (s, v) -> s.setToken(v));
  }

  private void addUserInvitationFields() {
    DModel<UserInvitation> m = getType2("UserInvitation");
    m.addPrimitive(
        "email",
        UserInvitation._EMAIL,
        "_email",
        FieldPrimitiveType.String,
        (s) -> s.getEmail(),
        (s, v) -> s.setEmail(v));
    m.addEnum(
        "appRole",
        UserInvitation._APPROLE,
        "_app_role",
        getType("AppUserRole"),
        (s) -> s.getAppRole(),
        (s, v) -> s.setAppRole(v));
    m.addReference(
        "invitedBy",
        UserInvitation._INVITEDBY,
        "_invited_by_id",
        false,
        getType("User"),
        (s) -> s.getInvitedBy(),
        (s, v) -> s.setInvitedBy(v));
    m.addPrimitive(
        "token",
        UserInvitation._TOKEN,
        "_token",
        FieldPrimitiveType.String,
        (s) -> s.getToken(),
        (s, v) -> s.setToken(v));
    m.addEnum(
        "status",
        UserInvitation._STATUS,
        "_status",
        getType("InvitationStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
        "expiresAt",
        UserInvitation._EXPIRESAT,
        "_expires_at",
        FieldPrimitiveType.DateTime,
        (s) -> s.getExpiresAt(),
        (s, v) -> s.setExpiresAt(v));
    m.addPrimitive(
        "acceptedAt",
        UserInvitation._ACCEPTEDAT,
        "_accepted_at",
        FieldPrimitiveType.DateTime,
        (s) -> s.getAcceptedAt(),
        (s, v) -> s.setAcceptedAt(v));
    m.addReference(
        "organization",
        UserInvitation._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addUserLoginRecordFields() {
    DModel<UserLoginRecord> m = getType2("UserLoginRecord");
    m.addReference(
        "user",
        UserLoginRecord._USER,
        "_user_id",
        false,
        getType("BaseUser"),
        (s) -> s.getUser(),
        (s, v) -> s.setUser(v));
    m.addPrimitive(
        "timeStamp",
        UserLoginRecord._TIMESTAMP,
        "_time_stamp",
        FieldPrimitiveType.DateTime,
        (s) -> s.getTimeStamp(),
        (s, v) -> s.setTimeStamp(v));
    m.addPrimitive(
        "iPAddress",
        UserLoginRecord._IPADDRESS,
        "_ip_address",
        FieldPrimitiveType.String,
        (s) -> s.getIPAddress(),
        (s, v) -> s.setIPAddress(v));
    m.addPrimitive(
        "browser",
        UserLoginRecord._BROWSER,
        "_browser",
        FieldPrimitiveType.String,
        (s) -> s.getBrowser(),
        (s, v) -> s.setBrowser(v));
    m.addPrimitive(
        "device",
        UserLoginRecord._DEVICE,
        "_device",
        FieldPrimitiveType.String,
        (s) -> s.getDevice(),
        (s, v) -> s.setDevice(v));
    m.addPrimitive(
        "location",
        UserLoginRecord._LOCATION,
        "_location",
        FieldPrimitiveType.String,
        (s) -> s.getLocation(),
        (s, v) -> s.setLocation(v));
    m.addPrimitive(
        "success",
        UserLoginRecord._SUCCESS,
        "_success",
        FieldPrimitiveType.Boolean,
        (s) -> s.isSuccess(),
        (s, v) -> s.setSuccess(v));
    m.addPrimitive(
        "failureReason",
        UserLoginRecord._FAILUREREASON,
        "_failure_reason",
        FieldPrimitiveType.String,
        (s) -> s.getFailureReason(),
        (s, v) -> s.setFailureReason(v));
  }

  private void addUserProfileFields() {
    DModel<UserProfile> m = getType2("UserProfile");
    m.addReference(
        "user",
        UserProfile._USER,
        "_user_id",
        false,
        getType("User"),
        (s) -> s.getUser(),
        (s, v) -> s.setUser(v));
    m.addPrimitive(
        "displayName",
        UserProfile._DISPLAYNAME,
        "_display_name",
        FieldPrimitiveType.String,
        (s) -> s.getDisplayName(),
        (s, v) -> s.setDisplayName(v));
    m.addPrimitive(
        "phone",
        UserProfile._PHONE,
        "_phone",
        FieldPrimitiveType.String,
        (s) -> s.getPhone(),
        (s, v) -> s.setPhone(v));
    m.addReference(
        "avatar",
        UserProfile._AVATAR,
        "_avatar_id",
        false,
        getType("DFile"),
        (s) -> s.getAvatar(),
        (s, v) -> s.setAvatar(v));
    m.addEnum(
        "appRole",
        UserProfile._APPROLE,
        "_app_role",
        getType("AppUserRole"),
        (s) -> s.getAppRole(),
        (s, v) -> s.setAppRole(v));
    m.addReferenceCollection(
        "assignedStores",
        UserProfile._ASSIGNEDSTORES,
        "_assigned_stores_id",
        "_user_profile_assigned_stores_dd1471",
        false,
        getType("Store"),
        (s) -> s.getAssignedStores(),
        (s, v) -> s.setAssignedStores(v));
    m.addReferenceCollection(
        "assignedWarehouses",
        UserProfile._ASSIGNEDWAREHOUSES,
        "_assigned_warehouses_id",
        "_user_profile_assigned_warehouses_17ffb1",
        false,
        getType("Warehouse"),
        (s) -> s.getAssignedWarehouses(),
        (s, v) -> s.setAssignedWarehouses(v));
    m.addEnum(
        "status",
        UserProfile._STATUS,
        "_status",
        getType("EntityStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "lastLoginAt",
            UserProfile._LASTLOGINAT,
            "_last_login_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getLastLoginAt(),
            (s, v) -> s.setLastLoginAt(v))
        .readOnly();
    m.addReference(
        "organization",
        UserProfile._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addUserProfileByUserRequestFields() {
    DModel<UserProfileByUserRequest> m = getType2("UserProfileByUserRequest");
    m.addReference(
        "user",
        UserProfileByUserRequest._USER,
        "null_id",
        false,
        getType("User"),
        (s) -> s.getUser(),
        (s, v) -> s.setUser(v));
  }

  private void addUserRoleFields() {
    DModel<UserRole> m = getType2("UserRole");
    m.addPrimitive(
        "name",
        UserRole._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addEnum(
        "roleCode",
        UserRole._ROLECODE,
        "_role_code",
        getType("AppUserRole"),
        (s) -> s.getRoleCode(),
        (s, v) -> s.setRoleCode(v));
    m.addPrimitive(
        "description",
        UserRole._DESCRIPTION,
        "_description",
        FieldPrimitiveType.String,
        (s) -> s.getDescription(),
        (s, v) -> s.setDescription(v));
    m.addPrimitive(
        "isSystem",
        UserRole._ISSYSTEM,
        "_is_system",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsSystem(),
        (s, v) -> s.setIsSystem(v));
    m.addPrimitive(
        "permissions",
        UserRole._PERMISSIONS,
        "_permissions",
        FieldPrimitiveType.String,
        (s) -> s.getPermissions(),
        (s, v) -> s.setPermissions(v));
    m.addReference(
        "organization",
        UserRole._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addVendorFields() {
    DModel<Vendor> m = getType2("Vendor");
    m.addPrimitive(
        "name",
        Vendor._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "code",
        Vendor._CODE,
        "_code",
        FieldPrimitiveType.String,
        (s) -> s.getCode(),
        (s, v) -> s.setCode(v));
    m.addPrimitive(
        "contactPerson",
        Vendor._CONTACTPERSON,
        "_contact_person",
        FieldPrimitiveType.String,
        (s) -> s.getContactPerson(),
        (s, v) -> s.setContactPerson(v));
    m.addPrimitive(
        "email",
        Vendor._EMAIL,
        "_email",
        FieldPrimitiveType.String,
        (s) -> s.getEmail(),
        (s, v) -> s.setEmail(v));
    m.addPrimitive(
        "phone",
        Vendor._PHONE,
        "_phone",
        FieldPrimitiveType.String,
        (s) -> s.getPhone(),
        (s, v) -> s.setPhone(v));
    m.addPrimitive(
        "address",
        Vendor._ADDRESS,
        "_address",
        FieldPrimitiveType.String,
        (s) -> s.getAddress(),
        (s, v) -> s.setAddress(v));
    m.addPrimitive(
        "paymentTerms",
        Vendor._PAYMENTTERMS,
        "_payment_terms",
        FieldPrimitiveType.String,
        (s) -> s.getPaymentTerms(),
        (s, v) -> s.setPaymentTerms(v));
    m.addPrimitive(
        "taxId",
        Vendor._TAXID,
        "_tax_id",
        FieldPrimitiveType.String,
        (s) -> s.getTaxId(),
        (s, v) -> s.setTaxId(v));
    m.addPrimitive(
        "rating",
        Vendor._RATING,
        "_rating",
        FieldPrimitiveType.Integer,
        (s) -> s.getRating(),
        (s, v) -> s.setRating(v));
    m.addInverseCollection(
        "contacts",
        Vendor._CONTACTS,
        "_vendor_id",
        getType("SupplierContact"),
        (s) -> s.getContacts());
    m.addEnum(
        "status",
        Vendor._STATUS,
        "_status",
        getType("EntityStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "createdAt",
            Vendor._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addReference(
        "organization",
        Vendor._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addVerificationDataFields() {
    DModel<VerificationData> m = getType2("VerificationData");
    m.addPrimitive(
        "method",
        VerificationData._METHOD,
        "_method",
        FieldPrimitiveType.String,
        (s) -> s.getMethod(),
        (s, v) -> s.setMethod(v));
    m.addPrimitive(
        "context",
        VerificationData._CONTEXT,
        "_context",
        FieldPrimitiveType.String,
        (s) -> s.getContext(),
        (s, v) -> s.setContext(v));
    m.addPrimitive(
            "token",
            VerificationData._TOKEN,
            "_token",
            FieldPrimitiveType.String,
            (s) -> s.getToken(),
            (s, v) -> s.setToken(v))
        .markNone();
    m.addPrimitive(
        "subject",
        VerificationData._SUBJECT,
        "_subject",
        FieldPrimitiveType.String,
        (s) -> s.getSubject(),
        (s, v) -> s.setSubject(v));
    m.addPrimitive(
        "body",
        VerificationData._BODY,
        "_body",
        FieldPrimitiveType.String,
        (s) -> s.getBody(),
        (s, v) -> s.setBody(v));
    m.addPrimitive(
            "processed",
            VerificationData._PROCESSED,
            "_processed",
            FieldPrimitiveType.Boolean,
            (s) -> s.isProcessed(),
            (s, v) -> s.setProcessed(v))
        .markNone();
  }

  private void addVerificationDataByTokenRequestFields() {
    DModel<VerificationDataByTokenRequest> m = getType2("VerificationDataByTokenRequest");
    m.addPrimitive(
        "token",
        VerificationDataByTokenRequest._TOKEN,
        "null",
        FieldPrimitiveType.String,
        (s) -> s.getToken(),
        (s, v) -> s.setToken(v));
  }

  private void addWarehouseFields() {
    DModel<Warehouse> m = getType2("Warehouse");
    m.addReference(
        "store",
        Warehouse._STORE,
        "_store_id",
        false,
        getType("Store"),
        (s) -> s.getStore(),
        (s, v) -> s.setStore(v));
    m.addPrimitive(
        "name",
        Warehouse._NAME,
        "_name",
        FieldPrimitiveType.String,
        (s) -> s.getName(),
        (s, v) -> s.setName(v));
    m.addPrimitive(
        "code",
        Warehouse._CODE,
        "_code",
        FieldPrimitiveType.String,
        (s) -> s.getCode(),
        (s, v) -> s.setCode(v));
    m.addEnum(
        "warehouseType",
        Warehouse._WAREHOUSETYPE,
        "_warehouse_type",
        getType("WarehouseType"),
        (s) -> s.getWarehouseType(),
        (s, v) -> s.setWarehouseType(v));
    m.addPrimitive(
        "address",
        Warehouse._ADDRESS,
        "_address",
        FieldPrimitiveType.String,
        (s) -> s.getAddress(),
        (s, v) -> s.setAddress(v));
    m.addPrimitive(
        "isDefault",
        Warehouse._ISDEFAULT,
        "_is_default",
        FieldPrimitiveType.Boolean,
        (s) -> s.isIsDefault(),
        (s, v) -> s.setIsDefault(v));
    m.addEnum(
        "status",
        Warehouse._STATUS,
        "_status",
        getType("EntityStatus"),
        (s) -> s.getStatus(),
        (s, v) -> s.setStatus(v));
    m.addPrimitive(
            "createdAt",
            Warehouse._CREATEDAT,
            "_created_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getCreatedAt(),
            (s, v) -> s.setCreatedAt(v))
        .readOnly();
    m.addPrimitive(
            "updatedAt",
            Warehouse._UPDATEDAT,
            "_updated_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getUpdatedAt(),
            (s, v) -> s.setUpdatedAt(v))
        .readOnly();
    m.addReference(
        "organization",
        Warehouse._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addWarehouseStockFields() {
    DModel<WarehouseStock> m = getType2("WarehouseStock");
    m.addReference(
        "warehouse",
        WarehouseStock._WAREHOUSE,
        "_warehouse_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
    m.addReference(
        "product",
        WarehouseStock._PRODUCT,
        "_product_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
    m.addPrimitive(
        "quantityOnHand",
        WarehouseStock._QUANTITYONHAND,
        "_quantity_on_hand",
        FieldPrimitiveType.Double,
        (s) -> s.getQuantityOnHand(),
        (s, v) -> s.setQuantityOnHand(v));
    m.addPrimitive(
        "quantityReserved",
        WarehouseStock._QUANTITYRESERVED,
        "_quantity_reserved",
        FieldPrimitiveType.Double,
        (s) -> s.getQuantityReserved(),
        (s, v) -> s.setQuantityReserved(v));
    m.addPrimitive(
            "quantityAvailable",
            WarehouseStock._QUANTITYAVAILABLE,
            "_quantity_available",
            FieldPrimitiveType.Double,
            (s) -> s.getQuantityAvailable(),
            (s, v) -> s.setQuantityAvailable(v))
        .readOnly();
    m.addPrimitive(
        "averageCost",
        WarehouseStock._AVERAGECOST,
        "_average_cost",
        FieldPrimitiveType.Double,
        (s) -> s.getAverageCost(),
        (s, v) -> s.setAverageCost(v));
    m.addPrimitive(
            "stockValue",
            WarehouseStock._STOCKVALUE,
            "_stock_value",
            FieldPrimitiveType.Double,
            (s) -> s.getStockValue(),
            (s, v) -> s.setStockValue(v))
        .readOnly();
    m.addPrimitive(
            "lastMovementAt",
            WarehouseStock._LASTMOVEMENTAT,
            "_last_movement_at",
            FieldPrimitiveType.DateTime,
            (s) -> s.getLastMovementAt(),
            (s, v) -> s.setLastMovementAt(v))
        .readOnly();
    m.addPrimitive(
        "lowStockNotifiedAt",
        WarehouseStock._LOWSTOCKNOTIFIEDAT,
        "_low_stock_notified_at",
        FieldPrimitiveType.DateTime,
        (s) -> s.getLowStockNotifiedAt(),
        (s, v) -> s.setLowStockNotifiedAt(v));
    m.addReference(
        "organization",
        WarehouseStock._ORGANIZATION,
        "_organization_id",
        false,
        getType("Organization"),
        (s) -> s.getOrganization(),
        (s, v) -> s.setOrganization(v));
  }

  private void addWarehouseStockByProductRequestFields() {
    DModel<WarehouseStockByProductRequest> m = getType2("WarehouseStockByProductRequest");
    m.addReference(
        "product",
        WarehouseStockByProductRequest._PRODUCT,
        "null_id",
        false,
        getType("Product"),
        (s) -> s.getProduct(),
        (s, v) -> s.setProduct(v));
  }

  private void addWarehouseStockByWarehouseRequestFields() {
    DModel<WarehouseStockByWarehouseRequest> m = getType2("WarehouseStockByWarehouseRequest");
    m.addReference(
        "warehouse",
        WarehouseStockByWarehouseRequest._WAREHOUSE,
        "null_id",
        false,
        getType("Warehouse"),
        (s) -> s.getWarehouse(),
        (s, v) -> s.setWarehouse(v));
  }

  private void addWarehousesByStoreRequestFields() {
    DModel<WarehousesByStoreRequest> m = getType2("WarehousesByStoreRequest");
    m.addReference(
        "store",
        WarehousesByStoreRequest._STORE,
        "null_id",
        false,
        getType("Store"),
        (s) -> s.getStore(),
        (s, v) -> s.setStore(v));
  }
}
