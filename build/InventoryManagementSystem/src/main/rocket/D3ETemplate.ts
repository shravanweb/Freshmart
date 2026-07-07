import * as __d3ett from "./D3ETemplateTypes";
import GlobalFunctions from "../utils/GlobalFunctions";
import PurchaseOrder from "../models/PurchaseOrder";
import ResultStatus from "../classes/ResultStatus";
import AllProducts from "../classes/AllProducts";
import PurchaseOrderItem from "../classes/PurchaseOrderItem";
import User from "../models/User";
import AllPurchaseOrdersRequest from "../models/AllPurchaseOrdersRequest";
import AllProductsRequest from "../models/AllProductsRequest";
import Warehouse from "../models/Warehouse";
import InventoryMovement from "../models/InventoryMovement";
import LowStockItems from "../classes/LowStockItems";
import GradientType from "../classes/GradientType";
import AllInAppNotificationsRequest from "../models/AllInAppNotificationsRequest";
import InventoryMovementsByDateRange from "../classes/InventoryMovementsByDateRange";
import LoginResult from "../classes/LoginResult";
import Vendor from "../models/Vendor";
import UserProfileByUser from "../classes/UserProfileByUser";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import WarehouseStock from "../models/WarehouseStock";
import ConnectionStatus from "../classes/ConnectionStatus";
import Product from "../models/Product";
import StockBatch from "../models/StockBatch";
import OutOfStockItemsRequest from "../models/OutOfStockItemsRequest";
import UserProfile from "../models/UserProfile";
import UnreadNotificationCount from "../classes/UnreadNotificationCount";
import ExpiringBatchesRequest from "../models/ExpiringBatchesRequest";
import AllInAppNotifications from "../classes/AllInAppNotifications";
import UnreadNotificationCountRequest from "../models/UnreadNotificationCountRequest";
import PurchaseOrderStatus from "../classes/PurchaseOrderStatus";
import PurchaseOrderItemRequest from "../models/PurchaseOrderItemRequest";
import Store from "../models/Store";
import OutOfStockItems from "../classes/OutOfStockItems";
import AllPurchaseOrders from "../classes/AllPurchaseOrders";
import Organization from "../models/Organization";
import AppUserRole from "../classes/AppUserRole";
import OrganizationItem from "../classes/OrganizationItem";
import InAppNotification from "../models/InAppNotification";
import InventoryMovementsByDateRangeRequest from "../models/InventoryMovementsByDateRangeRequest";
import ExpiringBatches from "../classes/ExpiringBatches";
import IconType from "../classes/IconType";
import OrganizationItemRequest from "../models/OrganizationItemRequest";
import LowStockItemsRequest from "../models/LowStockItemsRequest";

export const ALLINAPPNOTIFICATIONS: number = 0;

export const ALLINAPPNOTIFICATIONSREQUEST: number = 1;

export const ALLPRODUCTS: number = 2;

export const ALLPRODUCTSREQUEST: number = 3;

export const ALLPURCHASEORDERS: number = 4;

export const ALLPURCHASEORDERSREQUEST: number = 5;

export const APPUSERROLE: number = 6;

export const BASEUSER: number = 7;

export const BLOB: number = 8;

export const BOOLEAN: number = 9;

export const CONNECTIONSTATUS: number = 10;

export const DFILE: number = 11;

export const DATE: number = 12;

export const DATETIME: number = 13;

export const DOUBLE: number = 14;

export const DURATION: number = 15;

export const EXPIRINGBATCHES: number = 16;

export const EXPIRINGBATCHESREQUEST: number = 17;

export const GEOLOCATION: number = 18;

export const GRADIENTTYPE: number = 19;

export const ICONTYPE: number = 20;

export const INAPPNOTIFICATION: number = 21;

export const INTEGER: number = 22;

export const INVENTORYMOVEMENT: number = 23;

export const INVENTORYMOVEMENTSBYDATERANGE: number = 24;

export const INVENTORYMOVEMENTSBYDATERANGEREQUEST: number = 25;

export const LOGINRESULT: number = 26;

export const LOWSTOCKITEMS: number = 27;

export const LOWSTOCKITEMSREQUEST: number = 28;

export const ORGANIZATION: number = 29;

export const ORGANIZATIONITEM: number = 30;

export const ORGANIZATIONITEMREQUEST: number = 31;

export const OUTOFSTOCKITEMS: number = 32;

export const OUTOFSTOCKITEMSREQUEST: number = 33;

export const PRODUCT: number = 34;

export const PURCHASEORDER: number = 35;

export const PURCHASEORDERITEM: number = 36;

export const PURCHASEORDERITEMREQUEST: number = 37;

export const PURCHASEORDERSTATUS: number = 38;

export const RESULTSTATUS: number = 39;

export const STOCKBATCH: number = 40;

export const STORE: number = 41;

export const STRING: number = 42;

export const TIME: number = 43;

export const TYPE: number = 44;

export const UNREADNOTIFICATIONCOUNT: number = 45;

export const UNREADNOTIFICATIONCOUNTREQUEST: number = 46;

export const USER: number = 47;

export const USERPROFILE: number = 48;

export const USERPROFILEBYUSER: number = 49;

export const USERPROFILEBYUSERREQUEST: number = 50;

export const VENDOR: number = 51;

export const WAREHOUSE: number = 52;

export const WAREHOUSESTOCK: number = 53;

export const VOID: number = 54;

export class D3ETemplate {
  public static HASH: string = "3652bbace563708a1add003e05863801";
  private static _usages: Array<__d3ett.D3EUsage> = [
    new __d3ett.D3EUsage(
      "Query_getAllInAppNotifications_DashboardPage_properties_notificationsData_computation",
      [
        new __d3ett.D3ETypeUsage(ALLINAPPNOTIFICATIONS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(INAPPNOTIFICATION, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
              new __d3ett.D3EFieldUsage(4, []),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "64f903327ab7af4cdc04cd0604f4f8a6"
    ),
    new __d3ett.D3EUsage(
      "Query_getAllProducts_DashboardPage_properties_productsData_computation",
      [
        new __d3ett.D3ETypeUsage(ALLPRODUCTS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(PRODUCT, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(2, []),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "9394762cc41c9da90bfcfbcf55f231ef"
    ),
    new __d3ett.D3EUsage(
      "Query_getAllPurchaseOrders_PurchaseOrderListPage_properties_purchaseOrdersData_computation",
      [
        new __d3ett.D3ETypeUsage(ALLPURCHASEORDERS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(PURCHASEORDER, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, []),
              new __d3ett.D3EFieldUsage(5, [
                new __d3ett.D3ETypeUsage(VENDOR, []),
              ]),
              new __d3ett.D3EFieldUsage(6, [
                new __d3ett.D3ETypeUsage(WAREHOUSE, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "32dc9e1c04fd21690616e3377eccee53"
    ),
    new __d3ett.D3EUsage(
      "Query_getExpiringBatches_DashboardPage_properties_expiringData_computation",
      [
        new __d3ett.D3ETypeUsage(EXPIRINGBATCHES, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(STOCKBATCH, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "a0372facaee3fd21cc5654a248cc80b0"
    ),
    new __d3ett.D3EUsage(
      "Query_getInventoryMovementsByDateRange_DashboardPage_properties_movementsData_computation",
      [
        new __d3ett.D3ETypeUsage(INVENTORYMOVEMENTSBYDATERANGE, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(INVENTORYMOVEMENT, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "bfb1f43caae7e47b17006f6548f94465"
    ),
    new __d3ett.D3EUsage(
      "Query_getLowStockItems_DashboardPage_properties_lowStockData_computation",
      [
        new __d3ett.D3ETypeUsage(LOWSTOCKITEMS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(WAREHOUSESTOCK, [
              new __d3ett.D3EFieldUsage(0, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(1, [
                new __d3ett.D3ETypeUsage(PRODUCT, []),
              ]),
              new __d3ett.D3EFieldUsage(2, []),
              new __d3ett.D3EFieldUsage(3, [
                new __d3ett.D3ETypeUsage(WAREHOUSE, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "bc37be803374c49439bf632990ea1279"
    ),
    new __d3ett.D3EUsage(
      "Query_getOrganizationItem_OrganizationSettingsPage_properties_orgData_computation",
      [
        new __d3ett.D3ETypeUsage(ORGANIZATIONITEM, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(ORGANIZATION, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, []),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, []),
              new __d3ett.D3EFieldUsage(5, []),
              new __d3ett.D3EFieldUsage(6, []),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "15b2d2669596f156c5819e380aff5f38"
    ),
    new __d3ett.D3EUsage(
      "Query_getOutOfStockItems_DashboardPage_properties_outOfStockData_computation",
      [
        new __d3ett.D3ETypeUsage(OUTOFSTOCKITEMS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(WAREHOUSESTOCK, [
              new __d3ett.D3EFieldUsage(0, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(1, [
                new __d3ett.D3ETypeUsage(PRODUCT, []),
              ]),
              new __d3ett.D3EFieldUsage(2, []),
              new __d3ett.D3EFieldUsage(3, [
                new __d3ett.D3ETypeUsage(WAREHOUSE, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "83ddf6ba40248092261dad7960892572"
    ),
    new __d3ett.D3EUsage(
      "Query_getPurchaseOrderItem_PurchaseOrderFormPage_properties_poData_computation",
      [
        new __d3ett.D3ETypeUsage(PURCHASEORDERITEM, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(PURCHASEORDER, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, []),
              new __d3ett.D3EFieldUsage(5, [
                new __d3ett.D3ETypeUsage(VENDOR, []),
              ]),
              new __d3ett.D3EFieldUsage(6, [
                new __d3ett.D3ETypeUsage(WAREHOUSE, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "be4ac23c7ee102b4bd5a94203ec3012b"
    ),
    new __d3ett.D3EUsage(
      "Query_getUnreadNotificationCount_DashboardPage_properties_unreadNotificationsData_computation",
      [
        new __d3ett.D3ETypeUsage(UNREADNOTIFICATIONCOUNT, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(INAPPNOTIFICATION, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
              new __d3ett.D3EFieldUsage(4, []),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "833a102234cf002b736db293412e5be9"
    ),
    new __d3ett.D3EUsage(
      "Query_getUserProfileByUser_ChangePasswordPage_properties_userProfileData_computation",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Query_getUserProfileByUser_DashboardPage_properties_userProfileData_computation",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Query_getUserProfileByUser_NotificationPreferencesPage_properties_userProfileData_computation",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Query_getUserProfileByUser_OrganizationSettingsPage_properties_userProfileData_computation",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Query_getUserProfileByUser_ProfileSettingsPage_properties_userProfileData_computation",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Query_getUserProfileByUser_PurchaseOrderFormPage_properties_userProfileData_computation",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Query_getUserProfileByUser_PurchaseOrderListPage_properties_userProfileData_computation",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Query_loginInventoryManagementSystemUserWithEmailAndPassword_LoginPage_eventHandlers_onLoginHandler_block",
      [
        new __d3ett.D3ETypeUsage(LOGINRESULT, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, []),
          new __d3ett.D3EFieldUsage(3, [
            new __d3ett.D3ETypeUsage(USER, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
            ]),
            new __d3ett.D3ETypeUsage(BASEUSER, []),
          ]),
        ]),
      ],
      "69f1dcafa4192c31f4570e363f4f3dbd"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onAllInAppNotificationsChange_DashboardPage_notificationsData_synchronise",
      [
        new __d3ett.D3ETypeUsage(ALLINAPPNOTIFICATIONS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(INAPPNOTIFICATION, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
              new __d3ett.D3EFieldUsage(4, []),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "64f903327ab7af4cdc04cd0604f4f8a6"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onAllProductsChange_DashboardPage_productsData_synchronise",
      [
        new __d3ett.D3ETypeUsage(ALLPRODUCTS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(PRODUCT, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(2, []),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "9394762cc41c9da90bfcfbcf55f231ef"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onAllPurchaseOrdersChange_PurchaseOrderListPage_purchaseOrdersData_synchronise",
      [
        new __d3ett.D3ETypeUsage(ALLPURCHASEORDERS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(PURCHASEORDER, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, []),
              new __d3ett.D3EFieldUsage(5, [
                new __d3ett.D3ETypeUsage(VENDOR, []),
              ]),
              new __d3ett.D3EFieldUsage(6, [
                new __d3ett.D3ETypeUsage(WAREHOUSE, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "32dc9e1c04fd21690616e3377eccee53"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onExpiringBatchesChange_DashboardPage_expiringData_synchronise",
      [
        new __d3ett.D3ETypeUsage(EXPIRINGBATCHES, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(STOCKBATCH, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "a0372facaee3fd21cc5654a248cc80b0"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onInventoryMovementsByDateRangeChange_DashboardPage_movementsData_synchronise",
      [
        new __d3ett.D3ETypeUsage(INVENTORYMOVEMENTSBYDATERANGE, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(INVENTORYMOVEMENT, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "bfb1f43caae7e47b17006f6548f94465"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onLowStockItemsChange_DashboardPage_lowStockData_synchronise",
      [
        new __d3ett.D3ETypeUsage(LOWSTOCKITEMS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(WAREHOUSESTOCK, [
              new __d3ett.D3EFieldUsage(0, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(1, [
                new __d3ett.D3ETypeUsage(PRODUCT, []),
              ]),
              new __d3ett.D3EFieldUsage(2, []),
              new __d3ett.D3EFieldUsage(3, [
                new __d3ett.D3ETypeUsage(WAREHOUSE, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "bc37be803374c49439bf632990ea1279"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onOrganizationItemChange_OrganizationSettingsPage_orgData_synchronise",
      [
        new __d3ett.D3ETypeUsage(ORGANIZATIONITEM, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(ORGANIZATION, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, []),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, []),
              new __d3ett.D3EFieldUsage(5, []),
              new __d3ett.D3EFieldUsage(6, []),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "15b2d2669596f156c5819e380aff5f38"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onOutOfStockItemsChange_DashboardPage_outOfStockData_synchronise",
      [
        new __d3ett.D3ETypeUsage(OUTOFSTOCKITEMS, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(WAREHOUSESTOCK, [
              new __d3ett.D3EFieldUsage(0, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(1, [
                new __d3ett.D3ETypeUsage(PRODUCT, []),
              ]),
              new __d3ett.D3EFieldUsage(2, []),
              new __d3ett.D3EFieldUsage(3, [
                new __d3ett.D3ETypeUsage(WAREHOUSE, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "83ddf6ba40248092261dad7960892572"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onPurchaseOrderItemChange_PurchaseOrderFormPage_poData_synchronise",
      [
        new __d3ett.D3ETypeUsage(PURCHASEORDERITEM, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(PURCHASEORDER, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, []),
              new __d3ett.D3EFieldUsage(5, [
                new __d3ett.D3ETypeUsage(VENDOR, []),
              ]),
              new __d3ett.D3EFieldUsage(6, [
                new __d3ett.D3ETypeUsage(WAREHOUSE, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "be4ac23c7ee102b4bd5a94203ec3012b"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onUnreadNotificationCountChange_DashboardPage_unreadNotificationsData_synchronise",
      [
        new __d3ett.D3ETypeUsage(UNREADNOTIFICATIONCOUNT, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, [
            new __d3ett.D3ETypeUsage(INAPPNOTIFICATION, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
              new __d3ett.D3EFieldUsage(4, []),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(3, []),
        ]),
      ],
      "833a102234cf002b736db293412e5be9"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onUserProfileByUserChange_ChangePasswordPage_userProfileData_synchronise",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onUserProfileByUserChange_DashboardPage_userProfileData_synchronise",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onUserProfileByUserChange_NotificationPreferencesPage_userProfileData_synchronise",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onUserProfileByUserChange_OrganizationSettingsPage_userProfileData_synchronise",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onUserProfileByUserChange_ProfileSettingsPage_userProfileData_synchronise",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onUserProfileByUserChange_PurchaseOrderFormPage_userProfileData_synchronise",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Subscription_onUserProfileByUserChange_PurchaseOrderListPage_userProfileData_synchronise",
      [
        new __d3ett.D3ETypeUsage(USERPROFILEBYUSER, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, [
            new __d3ett.D3ETypeUsage(USERPROFILE, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, []),
              new __d3ett.D3EFieldUsage(2, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
              new __d3ett.D3EFieldUsage(3, []),
              new __d3ett.D3EFieldUsage(4, [
                new __d3ett.D3ETypeUsage(USER, []),
                new __d3ett.D3ETypeUsage(BASEUSER, []),
              ]),
            ]),
          ]),
          new __d3ett.D3EFieldUsage(2, []),
        ]),
      ],
      "a6ae595e3f6dfc0caf14021e389ea7f6"
    ),
    new __d3ett.D3EUsage(
      "Query_registerInventoryManagementSystemUser_SignupPage_eventHandlers_onSignupHandler_block",
      [
        new __d3ett.D3ETypeUsage(LOGINRESULT, [
          new __d3ett.D3EFieldUsage(0, []),
          new __d3ett.D3EFieldUsage(1, []),
          new __d3ett.D3EFieldUsage(2, []),
          new __d3ett.D3EFieldUsage(3, [
            new __d3ett.D3ETypeUsage(USER, [
              new __d3ett.D3EFieldUsage(0, []),
              new __d3ett.D3EFieldUsage(1, [
                new __d3ett.D3ETypeUsage(ORGANIZATION, []),
              ]),
            ]),
            new __d3ett.D3ETypeUsage(BASEUSER, []),
          ]),
        ]),
      ],
      "b8c4e2f19a3d5076c1e9ab24f6d8a0c1"
    ),
  ];
  private static _types: Array<__d3ett.D3ETemplateType> = [
    new __d3ett.D3ETemplateType(
      "AllInAppNotifications",
      "e3cf824189706b8a179d5c584a0654f9",
      [
        new __d3ett.D3ETemplateField(
          "count",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          INAPPNOTIFICATION,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new AllInAppNotifications(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "AllInAppNotificationsRequest",
      "eb3b16c3dbaf05c021509f56555bc803",
      [
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "user",
          USER,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new AllInAppNotificationsRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "AllProducts",
      "3a412960d5ffe52ea6a8dfed0c48d35b",
      [
        new __d3ett.D3ETemplateField(
          "count",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          PRODUCT,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new AllProducts(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "AllProductsRequest",
      "245992a3ee2b75d6f873a29fa44e895c",
      [
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new AllProductsRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "AllPurchaseOrders",
      "2f5a32c7fabf8dceb98ff96151d9ea2f",
      [
        new __d3ett.D3ETemplateField(
          "count",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          PURCHASEORDER,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new AllPurchaseOrders(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "AllPurchaseOrdersRequest",
      "57a2108a5809c845cb70ab94680acec8",
      [
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new AllPurchaseOrdersRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "AppUserRole",
      "916e4f0234502593a614c2230a475e30",
      [
        new __d3ett.D3ETemplateField(
          "OrganizationAdmin",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "StoreManager",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "WarehouseManager",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "PurchaseManager",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "InventoryClerk",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "SalesStaff",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "Accountant",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField("Viewer", 0, __d3ett.D3EFieldType.Enum),
      ],
      { "refType": __d3ett.D3ERefType.Enum }
    ),
    new __d3ett.D3ETemplateType(
      "BaseUser",
      "5116c054754a25edc5a13eddbd47e85a",
      [],
      { "abstract": true, "refType": __d3ett.D3ERefType.Model }
    ),
    new __d3ett.D3ETemplateType(
      "Blob",
      "e8016c85ada38bdc5fac616ec1318047",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "Boolean",
      "27226c864bac7454a8504f8edb15d95b",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "ConnectionStatus",
      "0d5c2bfbc6b6e414981c0c67321165d5",
      [
        new __d3ett.D3ETemplateField(
          "Connecting",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField("Connected", 0, __d3ett.D3EFieldType.Enum),
        new __d3ett.D3ETemplateField(
          "ConnectionBusy",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "ConnectionNormal",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "ConnectionFailed",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "RestoreFailed",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "AuthFailed",
          0,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      { "refType": __d3ett.D3ERefType.Enum }
    ),
    new __d3ett.D3ETemplateType(
      "DFile",
      "71a781845a8ebe8adf67352a573af199",
      [
        new __d3ett.D3ETemplateField("id", STRING, __d3ett.D3EFieldType.String),
        new __d3ett.D3ETemplateField(
          "name",
          STRING,
          __d3ett.D3EFieldType.String
        ),
        new __d3ett.D3ETemplateField(
          "size",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "mimeType",
          STRING,
          __d3ett.D3EFieldType.String
        ),
      ],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "Date",
      "44749712dbec183e983dcd78a7736c41",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "DateTime",
      "8cf10d2341ed01492506085688270c1e",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "Double",
      "d909d38d705ce75386dd86e611a82f5b",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "Duration",
      "e02d2ae03de9d493df2b6b2d2813d302",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "ExpiringBatches",
      "ad485f6d9d3576a5f182f16d36b1ccf1",
      [
        new __d3ett.D3ETemplateField(
          "count",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          STOCKBATCH,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new ExpiringBatches(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "ExpiringBatchesRequest",
      "3e7848a6b54b18a473125c64d8143212",
      [
        new __d3ett.D3ETemplateField(
          "daysAhead",
          INTEGER,
          __d3ett.D3EFieldType.Integer,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new ExpiringBatchesRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "Geolocation",
      "323d4eb70b252acb4a04eaf9e0882597",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "GradientType",
      "dfc80f7e140e2dac94987925914062a8",
      [
        new __d3ett.D3ETemplateField("Color", 0, __d3ett.D3EFieldType.Enum),
        new __d3ett.D3ETemplateField(
          "LinearGradient",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "SweepGradient",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField(
          "RadialGradient",
          0,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      { "refType": __d3ett.D3ERefType.Enum }
    ),
    new __d3ett.D3ETemplateType(
      "IconType",
      "4529ef025543fdd73b83290b6c7b541f",
      [
        new __d3ett.D3ETemplateField("SVG", 0, __d3ett.D3EFieldType.Enum),
        new __d3ett.D3ETemplateField("Font", 0, __d3ett.D3EFieldType.Enum),
      ],
      { "refType": __d3ett.D3ERefType.Enum }
    ),
    new __d3ett.D3ETemplateType(
      "InAppNotification",
      "62836aa6a2fbd8a80aa26363033ac2bc",
      [
        new __d3ett.D3ETemplateField(
          "createdAt",
          DATETIME,
          __d3ett.D3EFieldType.DateTime,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "isRead",
          BOOLEAN,
          __d3ett.D3EFieldType.Boolean,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "recipient",
          USER,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "title",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new InAppNotification(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "Integer",
      "a0faef0851b4294c06f2b94bb1cb2044",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "InventoryMovement",
      "eca037023047b0daa425ba22126e995f",
      [
        new __d3ett.D3ETemplateField(
          "movementDate",
          DATETIME,
          __d3ett.D3EFieldType.DateTime,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "movementNumber",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new InventoryMovement(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "InventoryMovementsByDateRange",
      "3005d7ca016039c131d43039fecc9549",
      [
        new __d3ett.D3ETemplateField(
          "count",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          INVENTORYMOVEMENT,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new InventoryMovementsByDateRange(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "InventoryMovementsByDateRangeRequest",
      "c2b8e3e3d36345725213d5df9a61c010",
      [
        new __d3ett.D3ETemplateField(
          "endDate",
          DATETIME,
          __d3ett.D3EFieldType.DateTime,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "startDate",
          DATETIME,
          __d3ett.D3EFieldType.DateTime,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new InventoryMovementsByDateRangeRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "LoginResult",
      "43b15c92fa28924318ec2dd9b20d65d3",
      [
        new __d3ett.D3ETemplateField(
          "failureMessage",
          STRING,
          __d3ett.D3EFieldType.String
        ),
        new __d3ett.D3ETemplateField(
          "success",
          BOOLEAN,
          __d3ett.D3EFieldType.Boolean
        ),
        new __d3ett.D3ETemplateField(
          "token",
          STRING,
          __d3ett.D3EFieldType.String
        ),
        new __d3ett.D3ETemplateField(
          "userObject",
          BASEUSER,
          __d3ett.D3EFieldType.Ref
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new LoginResult(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "LowStockItems",
      "e48c6698749b37bd79c08d12479fb695",
      [
        new __d3ett.D3ETemplateField(
          "count",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          WAREHOUSESTOCK,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new LowStockItems(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "LowStockItemsRequest",
      "1c4babe20b603ac2b3d3ba1298494471",
      [
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new LowStockItemsRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "Organization",
      "4d331f661dc9945a3993b7d01b64f90c",
      [
        new __d3ett.D3ETemplateField(
          "address",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "currency",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "email",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "legalName",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "name",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "phone",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "timezone",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new Organization(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "OrganizationItem",
      "fd6cb304f9ccca806a17779603193cf0",
      [
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new OrganizationItem(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "OrganizationItemRequest",
      "53b251b24d817d9acb6c63c6d69f29ec",
      [
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new OrganizationItemRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "OutOfStockItems",
      "0fb466d62cf6a64fa8a474b4edadcc10",
      [
        new __d3ett.D3ETemplateField(
          "count",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          WAREHOUSESTOCK,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new OutOfStockItems(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "OutOfStockItemsRequest",
      "8bb433fdcccd8e2fb2797ce6e58c25b6",
      [
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new OutOfStockItemsRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "Product",
      "048fe928b8c3d4b17cc06e295bda7f49",
      [
        new __d3ett.D3ETemplateField(
          "name",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "reorderLevel",
          DOUBLE,
          __d3ett.D3EFieldType.Double,
          {}
        ),
      ],
      { "refType": __d3ett.D3ERefType.Model, "creator": () => new Product() }
    ),
    new __d3ett.D3ETemplateType(
      "PurchaseOrder",
      "3f7263477e8350acff6d16ba154d4031",
      [
        new __d3ett.D3ETemplateField(
          "notes",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "orderDate",
          DATE,
          __d3ett.D3EFieldType.Date,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "poNumber",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "status",
          PURCHASEORDERSTATUS,
          __d3ett.D3EFieldType.Enum,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "vendor",
          VENDOR,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "warehouse",
          WAREHOUSE,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new PurchaseOrder(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "PurchaseOrderItem",
      "19a1a12948303cfc15d6ab0844f94d96",
      [
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          PURCHASEORDER,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new PurchaseOrderItem(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "PurchaseOrderItemRequest",
      "f6d0d88517677ae48db7940dfbb6a90d",
      [
        new __d3ett.D3ETemplateField(
          "purchaseOrder",
          PURCHASEORDER,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new PurchaseOrderItemRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "PurchaseOrderStatus",
      "e985572699319edeabdc9a316262c70f",
      [
        new __d3ett.D3ETemplateField("Draft", 0, __d3ett.D3EFieldType.Enum),
        new __d3ett.D3ETemplateField("Submitted", 0, __d3ett.D3EFieldType.Enum),
        new __d3ett.D3ETemplateField("Approved", 0, __d3ett.D3EFieldType.Enum),
        new __d3ett.D3ETemplateField(
          "PartiallyReceived",
          0,
          __d3ett.D3EFieldType.Enum
        ),
        new __d3ett.D3ETemplateField("Received", 0, __d3ett.D3EFieldType.Enum),
        new __d3ett.D3ETemplateField("Cancelled", 0, __d3ett.D3EFieldType.Enum),
      ],
      { "refType": __d3ett.D3ERefType.Enum }
    ),
    new __d3ett.D3ETemplateType(
      "ResultStatus",
      "b3e4a20b998b614cb3f7bd669946ae7f",
      [
        new __d3ett.D3ETemplateField("Success", 0, __d3ett.D3EFieldType.Enum),
        new __d3ett.D3ETemplateField("Errors", 0, __d3ett.D3EFieldType.Enum),
      ],
      { "refType": __d3ett.D3ERefType.Enum }
    ),
    new __d3ett.D3ETemplateType(
      "StockBatch",
      "285ceedf74bb72eb8d1d480d56fb1aeb",
      [
        new __d3ett.D3ETemplateField(
          "batchNumber",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "expiryDate",
          DATE,
          __d3ett.D3EFieldType.Date,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      { "refType": __d3ett.D3ERefType.Model, "creator": () => new StockBatch() }
    ),
    new __d3ett.D3ETemplateType(
      "Store",
      "790d614ab63e05681874d5fe2c295b42",
      [
        new __d3ett.D3ETemplateField(
          "name",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      { "refType": __d3ett.D3ERefType.Model, "creator": () => new Store() }
    ),
    new __d3ett.D3ETemplateType(
      "String",
      "27118326006d3829667a400ad23d5d98",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "Time",
      "a76d4ef5f3f6a672bbfab2865563e530",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "Type",
      "a1fa27779242b4902f7ae3bdd5c6d508",
      [],
      {}
    ),
    new __d3ett.D3ETemplateType(
      "UnreadNotificationCount",
      "d0646f5002fdce8c5a9d0e3cc533dc80",
      [
        new __d3ett.D3ETemplateField(
          "count",
          INTEGER,
          __d3ett.D3EFieldType.Integer
        ),
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          INAPPNOTIFICATION,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new UnreadNotificationCount(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "UnreadNotificationCountRequest",
      "d34522cae365d70aa568283c0fec0e6b",
      [
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "user",
          USER,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new UnreadNotificationCountRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "User",
      "07712032f328f98eee911006b6fdf658",
      [
        new __d3ett.D3ETemplateField(
          "email",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "parent": 7,
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new User(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "UserProfile",
      "46ede32536ba2b6dc3955a887dcb1f6e",
      [
        new __d3ett.D3ETemplateField(
          "appRole",
          APPUSERROLE,
          __d3ett.D3EFieldType.Enum,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "displayName",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "phone",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "user",
          USER,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new UserProfile(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "UserProfileByUser",
      "a198b3f808dd0d76ce2b3c8df1f2651e",
      [
        new __d3ett.D3ETemplateField(
          "errors",
          STRING,
          __d3ett.D3EFieldType.String,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "items",
          USERPROFILE,
          __d3ett.D3EFieldType.Ref,
          { "collection": true }
        ),
        new __d3ett.D3ETemplateField(
          "status",
          RESULTSTATUS,
          __d3ett.D3EFieldType.Enum
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Struct,
        "creator": () => new UserProfileByUser(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "UserProfileByUserRequest",
      "6f99b0b979e27052da99509443edda4e",
      [
        new __d3ett.D3ETemplateField(
          "user",
          USER,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new UserProfileByUserRequest(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "Vendor",
      "a2d273e799a84dbc9525d356945e983f",
      [
        new __d3ett.D3ETemplateField(
          "name",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      { "refType": __d3ett.D3ERefType.Model, "creator": () => new Vendor() }
    ),
    new __d3ett.D3ETemplateType(
      "Warehouse",
      "a342df0f3a7f83ab59e2cf03dfa282b9",
      [
        new __d3ett.D3ETemplateField(
          "name",
          STRING,
          __d3ett.D3EFieldType.String,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      { "refType": __d3ett.D3ERefType.Model, "creator": () => new Warehouse() }
    ),
    new __d3ett.D3ETemplateType(
      "WarehouseStock",
      "0c51308faeda9beb2cd33bc39f64f5ef",
      [
        new __d3ett.D3ETemplateField(
          "organization",
          ORGANIZATION,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "product",
          PRODUCT,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "quantityOnHand",
          DOUBLE,
          __d3ett.D3EFieldType.Double,
          {}
        ),
        new __d3ett.D3ETemplateField(
          "warehouse",
          WAREHOUSE,
          __d3ett.D3EFieldType.Ref,
          {}
        ),
      ],
      {
        "refType": __d3ett.D3ERefType.Model,
        "creator": () => new WarehouseStock(),
      }
    ),
    new __d3ett.D3ETemplateType(
      "void",
      "cab8111fd0b710a336c898e539090e34",
      [],
      {}
    ),
  ];
  private static readonly _typeMap: Map<string, number> = Map.fromIterables(
    D3ETemplate._types.map((x) => x.name),
    Array.generate(D3ETemplate._types.length, (index) => index)
  );
  public static allFields(type: number): Array<number> {
    return Array.generate(
      D3ETemplate._types[type].fields.length,
      (index) => index
    );
  }
  public static get types(): Array<__d3ett.D3ETemplateType> {
    return D3ETemplate._types;
  }
  public static get usages(): Array<__d3ett.D3EUsage> {
    return D3ETemplate._usages;
  }
  public static typeString(val: number): string {
    return D3ETemplate._types[val].name;
  }
  public static typeInt(val: string): number {
    return D3ETemplate._typeMap.get(val);
  }
  public static _getField(type: number, val: number): __d3ett.D3ETemplateField {
    let _type: __d3ett.D3ETemplateType = D3ETemplate._types[type];

    /*
_type will have fields with index starting from _type.parentFields.
Anything less needs to be looked up in _type.parent.
*/

    if (val < _type.parentFields) {
      return D3ETemplate._getField(_type.parent, val);
    }

    /*
The field cannot be in _type's child, so subtract _type.parentField from val, and use that as index.
*/

    let adjustedIndex: number = val - _type.parentFields;

    return _type.fields[adjustedIndex];
  }
  public static fieldString(type: number, val: number): string {
    return D3ETemplate._getField(type, val).name;
  }
  public static fieldType(type: number, val: number): number {
    return D3ETemplate._getField(type, val).type;
  }
  public static isChild(type: number, val: number): boolean {
    return D3ETemplate._getField(type, val).child;
  }
  public static fieldInt(type: number, val: string): number {
    let _type: __d3ett.D3ETemplateType = D3ETemplate._types[type];

    if (_type.fieldMap.containsKey(val)) {
      return _type.fieldMap[val];
    }

    if (_type.parent != null) {
      return D3ETemplate.fieldInt(_type.parent, val);
    }

    return null;
  }
  public static isEmbedded(type: number): boolean {
    return D3ETemplate._types[type].embedded;
  }
  public static isAbstract(type: number): boolean {
    return D3ETemplate._types[type].abstract;
  }
  public static hasParent(type: number): boolean {
    return D3ETemplate._types[type].parent != null;
  }
  public static parent(type: number): number {
    return D3ETemplate._types[type].parent;
  }
  public static getEnumField<T>(type: number, field: number): T {
    switch (type) {
      case APPUSERROLE: {
        return AppUserRole.values[field] as unknown as T;
      }

      case CONNECTIONSTATUS: {
        return ConnectionStatus.values[field] as unknown as T;
      }

      case GRADIENTTYPE: {
        return GradientType.values[field] as unknown as T;
      }

      case ICONTYPE: {
        return IconType.values[field] as unknown as T;
      }

      case PURCHASEORDERSTATUS: {
        return PurchaseOrderStatus.values[field] as unknown as T;
      }

      case RESULTSTATUS: {
        return ResultStatus.values[field] as unknown as T;
      }
    }
  }
}

export class UsageConstants {
  public static readonly QUERY_GETALLINAPPNOTIFICATIONS_DASHBOARDPAGE_PROPERTIES_NOTIFICATIONSDATA_COMPUTATION: number = 0;
  public static readonly QUERY_GETALLPRODUCTS_DASHBOARDPAGE_PROPERTIES_PRODUCTSDATA_COMPUTATION: number = 1;
  public static readonly QUERY_GETALLPURCHASEORDERS_PURCHASEORDERLISTPAGE_PROPERTIES_PURCHASEORDERSDATA_COMPUTATION: number = 2;
  public static readonly QUERY_GETEXPIRINGBATCHES_DASHBOARDPAGE_PROPERTIES_EXPIRINGDATA_COMPUTATION: number = 3;
  public static readonly QUERY_GETINVENTORYMOVEMENTSBYDATERANGE_DASHBOARDPAGE_PROPERTIES_MOVEMENTSDATA_COMPUTATION: number = 4;
  public static readonly QUERY_GETLOWSTOCKITEMS_DASHBOARDPAGE_PROPERTIES_LOWSTOCKDATA_COMPUTATION: number = 5;
  public static readonly QUERY_GETORGANIZATIONITEM_ORGANIZATIONSETTINGSPAGE_PROPERTIES_ORGDATA_COMPUTATION: number = 6;
  public static readonly QUERY_GETOUTOFSTOCKITEMS_DASHBOARDPAGE_PROPERTIES_OUTOFSTOCKDATA_COMPUTATION: number = 7;
  public static readonly QUERY_GETPURCHASEORDERITEM_PURCHASEORDERFORMPAGE_PROPERTIES_PODATA_COMPUTATION: number = 8;
  public static readonly QUERY_GETUNREADNOTIFICATIONCOUNT_DASHBOARDPAGE_PROPERTIES_UNREADNOTIFICATIONSDATA_COMPUTATION: number = 9;
  public static readonly QUERY_GETUSERPROFILEBYUSER_CHANGEPASSWORDPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION: number = 10;
  public static readonly QUERY_GETUSERPROFILEBYUSER_DASHBOARDPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION: number = 11;
  public static readonly QUERY_GETUSERPROFILEBYUSER_NOTIFICATIONPREFERENCESPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION: number = 12;
  public static readonly QUERY_GETUSERPROFILEBYUSER_ORGANIZATIONSETTINGSPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION: number = 13;
  public static readonly QUERY_GETUSERPROFILEBYUSER_PROFILESETTINGSPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION: number = 14;
  public static readonly QUERY_GETUSERPROFILEBYUSER_PURCHASEORDERFORMPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION: number = 15;
  public static readonly QUERY_GETUSERPROFILEBYUSER_PURCHASEORDERLISTPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION: number = 16;
  public static readonly QUERY_LOGININVENTORYMANAGEMENTSYSTEMUSERWITHEMAILANDPASSWORD_LOGINPAGE_EVENTHANDLERS_ONLOGINHANDLER_BLOCK: number = 17;
  public static readonly QUERY_REGISTERINVENTORYMANAGEMENTSYSTEMUSER_SIGNUPPAGE_EVENTHANDLERS_ONSIGNUPHANDLER_BLOCK: number = 35;
  public static readonly SUBSCRIPTION_ONALLINAPPNOTIFICATIONSCHANGE_DASHBOARDPAGE_NOTIFICATIONSDATA_SYNCHRONISE: number = 18;
  public static readonly SUBSCRIPTION_ONALLPRODUCTSCHANGE_DASHBOARDPAGE_PRODUCTSDATA_SYNCHRONISE: number = 19;
  public static readonly SUBSCRIPTION_ONALLPURCHASEORDERSCHANGE_PURCHASEORDERLISTPAGE_PURCHASEORDERSDATA_SYNCHRONISE: number = 20;
  public static readonly SUBSCRIPTION_ONEXPIRINGBATCHESCHANGE_DASHBOARDPAGE_EXPIRINGDATA_SYNCHRONISE: number = 21;
  public static readonly SUBSCRIPTION_ONINVENTORYMOVEMENTSBYDATERANGECHANGE_DASHBOARDPAGE_MOVEMENTSDATA_SYNCHRONISE: number = 22;
  public static readonly SUBSCRIPTION_ONLOWSTOCKITEMSCHANGE_DASHBOARDPAGE_LOWSTOCKDATA_SYNCHRONISE: number = 23;
  public static readonly SUBSCRIPTION_ONORGANIZATIONITEMCHANGE_ORGANIZATIONSETTINGSPAGE_ORGDATA_SYNCHRONISE: number = 24;
  public static readonly SUBSCRIPTION_ONOUTOFSTOCKITEMSCHANGE_DASHBOARDPAGE_OUTOFSTOCKDATA_SYNCHRONISE: number = 25;
  public static readonly SUBSCRIPTION_ONPURCHASEORDERITEMCHANGE_PURCHASEORDERFORMPAGE_PODATA_SYNCHRONISE: number = 26;
  public static readonly SUBSCRIPTION_ONUNREADNOTIFICATIONCOUNTCHANGE_DASHBOARDPAGE_UNREADNOTIFICATIONSDATA_SYNCHRONISE: number = 27;
  public static readonly SUBSCRIPTION_ONUSERPROFILEBYUSERCHANGE_CHANGEPASSWORDPAGE_USERPROFILEDATA_SYNCHRONISE: number = 28;
  public static readonly SUBSCRIPTION_ONUSERPROFILEBYUSERCHANGE_DASHBOARDPAGE_USERPROFILEDATA_SYNCHRONISE: number = 29;
  public static readonly SUBSCRIPTION_ONUSERPROFILEBYUSERCHANGE_NOTIFICATIONPREFERENCESPAGE_USERPROFILEDATA_SYNCHRONISE: number = 30;
  public static readonly SUBSCRIPTION_ONUSERPROFILEBYUSERCHANGE_ORGANIZATIONSETTINGSPAGE_USERPROFILEDATA_SYNCHRONISE: number = 31;
  public static readonly SUBSCRIPTION_ONUSERPROFILEBYUSERCHANGE_PROFILESETTINGSPAGE_USERPROFILEDATA_SYNCHRONISE: number = 32;
  public static readonly SUBSCRIPTION_ONUSERPROFILEBYUSERCHANGE_PURCHASEORDERFORMPAGE_USERPROFILEDATA_SYNCHRONISE: number = 33;
  public static readonly SUBSCRIPTION_ONUSERPROFILEBYUSERCHANGE_PURCHASEORDERLISTPAGE_USERPROFILEDATA_SYNCHRONISE: number = 34;
}

export class ChannelConstants {
  public static readonly TOTAL_CHANNEL_COUNT: number = 0;
  public static readonly channels: Array<__d3ett.D3ETemplateClass> = [];
}

export class RPCConstants {
  public static readonly InventoryAdjustmentService: number = 0;
  public static readonly INVENTORYADJUSTMENTSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly InventoryMovementService: number = 1;
  public static readonly INVENTORYMOVEMENTSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly NotificationService: number = 2;
  public static readonly NOTIFICATIONSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly OrganizationService: number = 3;
  public static readonly ORGANIZATIONSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly StockBatchService: number = 4;
  public static readonly STOCKBATCHSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly ProductCategoryService: number = 5;
  public static readonly PRODUCTCATEGORYSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly ProductService: number = 6;
  public static readonly PRODUCTSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly AuditLogService: number = 7;
  public static readonly AUDITLOGSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly WarehouseService: number = 8;
  public static readonly WAREHOUSESERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly WarehouseStockService: number = 9;
  public static readonly WAREHOUSESTOCKSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly SalesOrderService: number = 10;
  public static readonly SALESORDERSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly UnitOfMeasureService: number = 11;
  public static readonly UNITOFMEASURESERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly UserManagementService: number = 12;
  public static readonly USERMANAGEMENTSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly StockTransferService: number = 13;
  public static readonly STOCKTRANSFERSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly StoreService: number = 14;
  public static readonly STORESERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly SalesReturnService: number = 15;
  public static readonly SALESRETURNSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly PurchaseOrderService: number = 16;
  public static readonly PURCHASEORDERSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly StockAlertService: number = 17;
  public static readonly STOCKALERTSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly GoodsReceiptService: number = 18;
  public static readonly GOODSRECEIPTSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly SupplierService: number = 19;
  public static readonly SUPPLIERSERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly UniqueChecker: number = 20;
  public static readonly UniqueCheckerCheckNameUniqueInOrganization: number = 0;
  public static readonly UniqueCheckerCheckUserEmailUniqueInOrganization: number = 1;
  public static readonly UniqueCheckerCheckUserProfileUserUniqueInOrganization: number = 2;
  public static readonly UNIQUECHECKER_PROCEDURE_COUNT: number = 3;
  public static readonly FileService: number = 21;
  public static readonly FILESERVICE_PROCEDURE_COUNT: number = 0;
  public static readonly TOTAL_RPC_CLASS_COUNT: number = 22;
  public static readonly classes: Array<__d3ett.D3ETemplateClass> = [
    new __d3ett.D3ETemplateClass(
      "InventoryAdjustmentService",
      "c9f6480e64e77f19656ece67ac3153ff",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "InventoryMovementService",
      "49faaba2c5f74e1c17be68f65b2a514a",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "NotificationService",
      "efdfe187b7a871c06fae79eecc328e69",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "OrganizationService",
      "10e04f4be952ef2fdad0cb82d0969995",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "StockBatchService",
      "3a4cb6092e322ddb9608392f6e0de447",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "ProductCategoryService",
      "5932e3de5afd1daa8c38089843dcfce1",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "ProductService",
      "0ab47bcd921f8d7132b335d7719f8310",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "AuditLogService",
      "e79c019a142b20b69569a231f66391f5",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "WarehouseService",
      "b2436cb4c5b7206d360abc0066dfb283",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "WarehouseStockService",
      "301af0d93e40484f8f974e9b78f3e8a4",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "SalesOrderService",
      "5a464437028cd1ddb6614f0258e63074",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "UnitOfMeasureService",
      "a122b251d610d98fa4389a5426b8c965",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "UserManagementService",
      "eff215ee5716e69032eeb361577818b4",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "StockTransferService",
      "fd1f7b1a609dcae250abfdd5f8fc7e2d",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "StoreService",
      "5cd6659e1c18f591902b6b46a93e4a4b",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "SalesReturnService",
      "cad9c6264278eecb4404dfe1de49cd96",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "PurchaseOrderService",
      "71a10a013cbbbc1bbd7d418b42a64069",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "StockAlertService",
      "c0520871e263803a83ef5135c00cbc6b",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "GoodsReceiptService",
      "644d46617d4df3a55e2186d3c2644a4f",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "SupplierService",
      "796554e58564277cfbf28060c4c4c9d4",
      []
    ),
    new __d3ett.D3ETemplateClass(
      "UniqueChecker",
      "61992989620e65d8472a1367f6752656",
      [
        new __d3ett.D3ETemplateMethodWithReturn(
          "checkNameUniqueInOrganization",
          [
            new __d3ett.D3ETemplateParam(
              D3ETemplate.typeInt("Organization"),
              {}
            ),
            new __d3ett.D3ETemplateParam(D3ETemplate.typeInt("String"), {}),
          ],
          D3ETemplate.typeInt("Boolean"),
          {}
        ),
        new __d3ett.D3ETemplateMethodWithReturn(
          "checkUserEmailUniqueInOrganization",
          [
            new __d3ett.D3ETemplateParam(
              D3ETemplate.typeInt("Organization"),
              {}
            ),
            new __d3ett.D3ETemplateParam(D3ETemplate.typeInt("User"), {}),
            new __d3ett.D3ETemplateParam(D3ETemplate.typeInt("String"), {}),
          ],
          D3ETemplate.typeInt("Boolean"),
          {}
        ),
        new __d3ett.D3ETemplateMethodWithReturn(
          "checkUserProfileUserUniqueInOrganization",
          [
            new __d3ett.D3ETemplateParam(
              D3ETemplate.typeInt("Organization"),
              {}
            ),
            new __d3ett.D3ETemplateParam(
              D3ETemplate.typeInt("UserProfile"),
              {}
            ),
            new __d3ett.D3ETemplateParam(D3ETemplate.typeInt("User"), {}),
          ],
          D3ETemplate.typeInt("Boolean"),
          {}
        ),
      ]
    ),
    new __d3ett.D3ETemplateClass(
      "FileService",
      "e256e0e9ad423eb20c85f506f4580ce8",
      []
    ),
  ];
}
GlobalFunctions.typeInt = D3ETemplate.typeInt;

GlobalFunctions.types = D3ETemplate.types;
