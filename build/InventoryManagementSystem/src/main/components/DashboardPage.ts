import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import PurchaseOrder from "../models/PurchaseOrder";
import AllProducts from "../classes/AllProducts";
import User from "../models/User";
import PageNavigator from "../classes/PageNavigator";
import Query from "../classes/Query";
import AllProductsRequest from "../models/AllProductsRequest";
import MessageDispatch from "../rocket/MessageDispatch";
import InventoryMovement from "../models/InventoryMovement";
import ListWrapper from "../utils/ListWrapper";
import LowStockItems from "../classes/LowStockItems";
import AllInAppNotificationsRequest from "../models/AllInAppNotificationsRequest";
import InventoryMovementsByDateRange from "../classes/InventoryMovementsByDateRange";
import Duration from "../core/Duration";
import UserProfileByUser from "../classes/UserProfileByUser";
import TextView from "./TextView";
import TextButton from "./TextButton";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import WarehouseStock from "../models/WarehouseStock";
import CollectionUtils from "../utils/CollectionUtils";
import StockBatch from "../models/StockBatch";
import NotificationCenterWidget from "./NotificationCenterWidget";
import OutOfStockItemsRequest from "../models/OutOfStockItemsRequest";
import UserProfile from "../models/UserProfile";
import UnreadNotificationCount from "../classes/UnreadNotificationCount";
import ExpiringBatchesRequest from "../models/ExpiringBatchesRequest";
import IMSidebarWidget from "./IMSidebarWidget";
import AllInAppNotifications from "../classes/AllInAppNotifications";
import UnreadNotificationCountRequest from "../models/UnreadNotificationCountRequest";
import ExpiryAlertWidget from "./ExpiryAlertWidget";
import RecentMovementsWidget from "./RecentMovementsWidget";
import OutOfStockItems from "../classes/OutOfStockItems";
import LowStockTableWidget from "./LowStockTableWidget";
import ScrollView2 from "./ScrollView2";
import Organization from "../models/Organization";
import IMSAppHeaderWidget from "./IMSAppHeaderWidget";
import DateTime from "../core/DateTime";
import InAppNotification from "../models/InAppNotification";
import InventoryMovementsByDateRangeRequest from "../models/InventoryMovementsByDateRangeRequest";
import ExpiringBatches from "../classes/ExpiringBatches";
import KPICardWidget from "./KPICardWidget";
import LowStockItemsRequest from "../models/LowStockItemsRequest";
import { UsageConstants } from "../rocket/D3ETemplate";
import { BuildContext } from "../classes/BuildContext";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import AppLogout from "../utils/AppLogout";
import NavConfig from "../utils/NavConfig";
import ProductApi from "../utils/ProductApi";
import StoreApi from "../utils/StoreApi";

export interface DashboardPageProps extends BaseUIProps {
  key?: string;
  user: User;
  sidebarRoute?: string;
}

class _DashboardPageState extends ObservableComponent<DashboardPageProps> {
  static defaultProps = { user: null };
  contentRefScrollController: ui.ScrollController = new ui.ScrollController();
  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  productsData: AllProducts = null;
  totalProducts: number = 0;
  lowStockData: LowStockItems = null;
  lowStockItems: Array<WarehouseStock> = ListWrapper.widget(
    this,
    "lowStockItems"
  );
  lowStockCount: number = 0;
  outOfStockData: OutOfStockItems = null;
  outOfStockCount: number = 0;
  expiringData: ExpiringBatches = null;
  expiringBatches: Array<StockBatch> = ListWrapper.widget(
    this,
    "expiringBatches"
  );
  movementsData: InventoryMovementsByDateRange = null;
  recentMovements: Array<InventoryMovement> = ListWrapper.widget(
    this,
    "recentMovements"
  );
  unreadNotificationsData: UnreadNotificationCount = null;
  unreadCount: number = 0;
  notificationsData: AllInAppNotifications = null;
  notifications: Array<InAppNotification> = ListWrapper.widget(
    this,
    "notifications"
  );
  stockValueLabel: string = "";
  storeCount: number = 0;
  activeStoreCount: number = 0;
  navRoute: string = "/dashboard";
  showProductModal: boolean = false;
  isSavingProduct: boolean = false;
  productFormError: string = "";
  productSku: string = "";
  productName: string = "";
  productDescription: string = "";
  productBarcode: string = "";
  productPurchasePrice: string = "";
  productSellingPrice: string = "";
  productReorderLevel: string = "";
  productReorderQty: string = "";
  productStatus: string = "Active";
  private _rebuildDebounceId: number | null = null;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: DashboardPageProps) {
    super(props);

    this.initState();
  }
  public get user(): User {
    return this.props.user;
  }
  public initState() {
    super.initState();

    this.navRoute = this.resolveInitialNavRoute();

    this.initListeners();

    this.enableBuild = true;
  }
  public resolveInitialNavRoute = (): string => {
    if (this.props.sidebarRoute !== undefined && this.props.sidebarRoute !== null) {
      return this.props.sidebarRoute;
    }

    if (typeof window !== "undefined") {
      const hash = window.location.hash ?? "";
      const routeParam = "?route=";
      const qIndex = hash.indexOf(routeParam);

      if (qIndex >= 0) {
        const route = decodeURIComponent(
          hash.substring(qIndex + routeParam.length)
        );

        if (route.length > 0) {
          return route;
        }
      }
    }

    return "/dashboard";
  };
  public initListeners(): void {
    this.updateSyncProperty("user", this.props.user);

    this.on(["user"], this.computeUserProfileData);

    this.computeUserProfileData();

    this.on(
      ["userProfileData", "userProfileData.items"],
      this.computeUserProfile
    );

    this.computeUserProfile();

    this.on(
      ["userProfile", "userProfile.organization"],
      this.computeOrganization
    );

    this.computeOrganization();

    this.on(["organization"], this.computeProductsData);

    this.computeProductsData();

    this.on(["productsData", "productsData.count"], this.computeTotalProducts);

    this.computeTotalProducts();

    this.on(["organization"], this.computeLowStockData);

    this.computeLowStockData();

    this.on(
      [
        "lowStockData",
        "lowStockData.items",
        "lowStockData.items.product",
        "lowStockData.items.product.reorderLevel",
        "lowStockData.items.quantityOnHand",
      ],
      this.computeLowStockItems
    );

    this.computeLowStockItems();

    this.on(["lowStockItems"], this.computeLowStockCount);

    this.computeLowStockCount();

    this.on(["organization"], this.computeOutOfStockData);

    this.computeOutOfStockData();

    this.on(
      ["outOfStockData", "outOfStockData.count"],
      this.computeOutOfStockCount
    );

    this.computeOutOfStockCount();

    this.on(["organization"], this.computeExpiringData);

    this.computeExpiringData();

    this.on(
      ["expiringData", "expiringData.items"],
      this.computeExpiringBatches
    );

    this.computeExpiringBatches();

    this.on(["organization"], this.computeMovementsData);

    this.computeMovementsData();

    this.on(
      ["movementsData", "movementsData.items"],
      this.computeRecentMovements
    );

    this.computeRecentMovements();

    this.on(["organization", "user"], this.computeUnreadNotificationsData);

    this.computeUnreadNotificationsData();

    this.on(
      ["unreadNotificationsData", "unreadNotificationsData.count"],
      this.computeUnreadCount
    );

    this.computeUnreadCount();

    this.on(["organization", "user"], this.computeNotificationsData);

    this.computeNotificationsData();

    this.on(
      ["notificationsData", "notificationsData.items"],
      this.computeNotifications
    );

    this.computeNotifications();

    this.on(
      ["lowStockData", "lowStockData.count"],
      this.computeStockValueLabel
    );

    this.computeStockValueLabel();

    this.on(["organization"], this.computeStoreCount);

    this.computeStoreCount();

    this.on(
      [
        "activeStoreCount",
        "expiringBatches",
        "lowStockCount",
        "lowStockItems",
        "navRoute",
        "notifications",
        "organization",
        "outOfStockCount",
        "recentMovements",
        "stockValueLabel",
        "storeCount",
        "totalProducts",
        "unreadCount",
        "user",
        "userProfile",
        "showProductModal",
        "isSavingProduct",
        "productFormError",
        "productSku",
        "productName",
        "productDescription",
        "productBarcode",
        "productPurchasePrice",
        "productSellingPrice",
        "productReorderLevel",
        "productReorderQty",
        "productStatus",
      ],
      this.rebuild
    );
  }
  public rebuild = (): void => {
    if (!this._mounted || !this.enableBuild) {
      return;
    }

    if (this._rebuildDebounceId != null) {
      window.clearTimeout(this._rebuildDebounceId);
    }

    this._rebuildDebounceId = window.setTimeout(() => {
      this._rebuildDebounceId = null;

      if (this._mounted && this.enableBuild) {
        this.setState({});
      }
    }, 300);
  };
  public renderKpiSection = (): ReactNode => {
    if (this.userProfile === null) {
      return ui.Container({
        className: "dashboardKpiSkeleton",
        key: "1",
      });
    }

    return ui.Row({
      mainAxisSize: ui.MainAxisSize.max,
      className: "dashboardKpiRow",
      children: [
        KPICardWidget({
          label: "Total Products",
          value: this.totalProducts.toString(),
          icon: "\uD83D\uDCE6",
          trend: "+ catalog",
          variant: "a",
          key: "0",
        }),
        KPICardWidget({
          label: "No. of Stores",
          value: this.storeCount.toString(),
          icon: "\uD83C\uDFEA",
          trend: this.activeStoreCount.toString() + " active",
          variant: "e",
          key: "1",
        }),
        KPICardWidget({
          label: "Stock Value",
          value: this.stockValueLabel,
          icon: "\uD83D\uDC8E",
          trend: "+ value",
          variant: "b",
          key: "2",
        }),
        KPICardWidget({
          label: "Low Stock Items",
          value: this.lowStockCount.toString(),
          icon: "\u26A0\uFE0F",
          trend: this.outOfStockCount.toString() + " out",
          variant: "c",
          key: "3",
        }),
        KPICardWidget({
          label: "Expiring Soon",
          value: this.expiringBatches.length.toString(),
          icon: "\uD83D\uDCC5",
          trend: "30 days",
          variant: "d",
          key: "4",
        }),
      ],
      key: "1",
    });
  };
  public renderPageTitleRow = (
    title: string,
    showAddProduct: boolean = false
  ): ReactNode => {
    return ui.Row({
      mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
      crossAxisAlignment: ui.CrossAxisAlignment.center,
      mainAxisSize: ui.MainAxisSize.max,
      children: [
        TextView({
          data: title,
          className: "x0e dashboardPageTitle",
          key: "0",
        }),
        showAddProduct
          ? TextButton({
              label: "+ Add Product",
              disable: false,
              onPressed: () => {
                this.onHeaderAddProduct();
              },
              onFocusChange: () => {},
              className: "primary headerActionBtn",
              key: "1",
            })
          : ui.Container({ key: "1" }),
      ],
      className: "dashboardPageTitleRow",
    });
  };
  public renderDashboardTitleRow = (): ReactNode => {
    return this.renderPageTitleRow("Operations Dashboard", true);
  };
  public resolvedPageTitle = (): string => {
    if (this.navRoute === "/dashboard") {
      return "Operations Dashboard";
    }

    const navItem = NavConfig.findItemByRoute(this.navRoute);

    return navItem !== null ? navItem.label : "Overview";
  };
  public isDashboardHome = (): boolean => {
    return this.navRoute === "/dashboard";
  };
  public renderRoutePlaceholder = (): ReactNode => {
    const navItem = NavConfig.findItemByRoute(this.navRoute);
    const pageTitle = this.resolvedPageTitle();
    const routeLabel = navItem !== null ? navItem.route : this.navRoute;

    return ui.Column({
      crossAxisAlignment: ui.CrossAxisAlignment.stretch,
      children: [
        this.renderPageTitleRow(pageTitle, false),
        ui.Container({
          child: ui.Column({
            crossAxisAlignment: ui.CrossAxisAlignment.stretch,
            mainAxisSize: ui.MainAxisSize.min,
            children: [
              TextView({
                data: pageTitle,
                className: "xeb",
                key: "0",
              }),
              TextView({
                data: "0 records",
                className: "x33",
                key: "1",
              }),
              ui.Container({
                child: TextView({
                  data:
                    "This section is connected to " +
                    routeLabel +
                    ". Full list page will load store data here.",
                  className: "x84",
                }),
                className: "glassPanel x90",
                key: "2",
              }),
            ],
          }),
          className: "glassCardFlat dashboardRoutePlaceholder",
          key: "1",
        }),
      ],
    });
  };
  public renderDashboardBody = (): ReactNode => {
    if (this.userProfile === null) {
      return ui.Column({
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          this.renderPageTitleRow(this.resolvedPageTitle(), this.isDashboardHome()),
          ui.Container({ className: "dashboardKpiSkeleton", key: "1" }),
          ui.Container({ className: "dashboardBodySkeleton", key: "2" }),
        ],
      });
    }

    if (!this.isDashboardHome()) {
      return this.renderRoutePlaceholder();
    }

    return ui.Column({
      crossAxisAlignment: ui.CrossAxisAlignment.stretch,
      children: [
        this.renderDashboardTitleRow(),
        this.renderKpiSection(),
        ui.Container({ className: "x61", key: "3" }),
        ui.Row({
          mainAxisSize: ui.MainAxisSize.max,
          crossAxisAlignment: ui.CrossAxisAlignment.start,
          children: [
            ui.Container({
              child: LowStockTableWidget({
                items: this.lowStockItems,
                onCreatePO: () => {
                  this.onCreatePoHandler();
                },
              }),
              className: "xfa",
              key: "0",
            }),
            ui.Container({
              child: ExpiryAlertWidget({
                batches: this.expiringBatches,
              }),
              className: "x7b",
              key: "1",
            }),
          ],
          key: "4",
        }),
        ui.Container({ className: "x6d", key: "5" }),
        RecentMovementsWidget({
          movements: this.recentMovements,
          key: "6",
        }),
      ],
    });
  };
  public componentDidUpdate(prevProps: DashboardPageProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.user !== this.props.user) {
      this.updateObservable("user", prevProps.user, this.props.user);

      this.fire("user", this);
    }

    if (
      prevProps.sidebarRoute !== this.props.sidebarRoute &&
      this.props.sidebarRoute !== undefined
    ) {
      this.setNavRoute(this.props.sidebarRoute);
    }
  }
  public setNavRoute(val: string): void {
    let isValChanged: boolean = this.navRoute !== val;

    if (!isValChanged) {
      return;
    }

    this.navRoute = val;

    this.fire("navRoute", this);
  }
  public setUserProfileData(val: UserProfileByUser): void {
    let isValChanged: boolean = this.userProfileData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("userProfileData", this.userProfileData, val);

    MessageDispatch.get().dispose(this.userProfileData);

    this.userProfileData = val;

    this.fire("userProfileData", this);
  }
  public computeUserProfileData = async (): Promise<void> => {
    try {
      this.setUserProfileData(
        await Query.get().getUserProfileByUser(
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_DASHBOARDPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
          new UserProfileByUserRequest({ user: this.user }),
          { "synchronize": true }
        )
      );
    } catch (exception) {
      console.log(
        " exception in computeUserProfileData : " + exception.toString()
      );

      this.setUserProfileData(null);
    }
  };
  public setUserProfile(val: UserProfile): void {
    let isValChanged: boolean = this.userProfile !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("userProfile", this.userProfile, val);

    this.userProfile = val;

    this.fire("userProfile", this);
  }
  public computeUserProfile = (): void => {
    try {
      this.setUserProfile(
        this.userProfileData !== null ? this.userProfileData.items.first : null
      );
    } catch (exception) {
      console.log(" exception in computeUserProfile : " + exception.toString());

      this.setUserProfile(null);
    }
  };
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this.organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("organization", this.organization, val);

    this.organization = val;

    this.fire("organization", this);
  }
  public computeOrganization = (): void => {
    try {
      this.setOrganization(
        this.userProfile !== null ? this.userProfile.organization : null
      );
    } catch (exception) {
      console.log(
        " exception in computeOrganization : " + exception.toString()
      );

      this.setOrganization(null);
    }
  };
  public setProductsData(val: AllProducts): void {
    let isValChanged: boolean = this.productsData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("productsData", this.productsData, val);

    MessageDispatch.get().dispose(this.productsData);

    this.productsData = val;

    this.fire("productsData", this);
  }
  public computeProductsData = async (): Promise<void> => {
    try {
      this.setProductsData(
        this.organization !== null
          ? await Query.get().getAllProducts(
              UsageConstants.QUERY_GETALLPRODUCTS_DASHBOARDPAGE_PROPERTIES_PRODUCTSDATA_COMPUTATION,
              new AllProductsRequest({ organization: this.organization }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(
        " exception in computeProductsData : " + exception.toString()
      );

      this.setProductsData(null);
    }
  };
  public setTotalProducts(val: number): void {
    let isValChanged: boolean = this.totalProducts !== val;

    if (!isValChanged) {
      return;
    }

    this.totalProducts = val;

    this.fire("totalProducts", this);
  }
  public computeTotalProducts = (): void => {
    try {
      this.setTotalProducts(
        this.productsData !== null ? this.productsData.count : 0
      );
    } catch (exception) {
      console.log(
        " exception in computeTotalProducts : " + exception.toString()
      );

      this.setTotalProducts(0);
    }
  };
  public setStoreCount(val: number): void {
    if (this.storeCount === val) {
      return;
    }

    this.storeCount = val;

    this.fire("storeCount", this);
  }
  public setActiveStoreCount(val: number): void {
    if (this.activeStoreCount === val) {
      return;
    }

    this.activeStoreCount = val;

    this.fire("activeStoreCount", this);
  }
  public computeStoreCount = async (): Promise<void> => {
    try {
      if (this.organization === null) {
        this.setStoreCount(0);
        this.setActiveStoreCount(0);
        return;
      }

      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const items = await StoreApi.getAllStores(orgId);
      this.setStoreCount(items.length);
      this.setActiveStoreCount(
        items.filter((store) => store.status === "Active").length
      );
    } catch (exception) {
      console.log(
        " exception in computeStoreCount : " + exception.toString()
      );

      this.setStoreCount(0);
      this.setActiveStoreCount(0);
    }
  };
  public setLowStockData(val: LowStockItems): void {
    let isValChanged: boolean = this.lowStockData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("lowStockData", this.lowStockData, val);

    MessageDispatch.get().dispose(this.lowStockData);

    this.lowStockData = val;

    this.fire("lowStockData", this);
  }
  public computeLowStockData = async (): Promise<void> => {
    try {
      this.setLowStockData(
        this.organization !== null
          ? await Query.get().getLowStockItems(
              UsageConstants.QUERY_GETLOWSTOCKITEMS_DASHBOARDPAGE_PROPERTIES_LOWSTOCKDATA_COMPUTATION,
              new LowStockItemsRequest({ organization: this.organization }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(
        " exception in computeLowStockData : " + exception.toString()
      );

      this.setLowStockData(null);
    }
  };
  public setLowStockItems(val: Array<WarehouseStock>): void {
    let isValChanged: boolean = CollectionUtils.isNotEquals(
      this.lowStockItems,
      val
    );

    if (!isValChanged) {
      return;
    }

    this.updateObservableColl("lowStockItems", this.lowStockItems, val);

    this.lowStockItems.clear();

    this.lowStockItems.addAll(val);

    this.fire("lowStockItems", this);
  }
  public addToLowStockItems(val: WarehouseStock, index: number = -1): void {
    if (index === -1) {
      if (!this.lowStockItems.contains(val)) this.lowStockItems.add(val);
    } else {
      this.lowStockItems.remove(this.lowStockItems.elementAt(index));

      this.lowStockItems.add(val);
    }

    this.fire("lowStockItems", this, val, true);

    this.updateObservable("lowStockItems", null, val);
  }
  public removeFromLowStockItems(val: WarehouseStock): void {
    this.lowStockItems.remove(val);

    this.fire("lowStockItems", this, val, false);

    this.removeObservable("lowStockItems", val);
  }
  public computeLowStockItems = (): void => {
    try {
      this.setLowStockItems(
        Array.from(
          this.lowStockData !== null
            ? this.lowStockData.items
                .where((row) => row.quantityOnHand <= row.product.reorderLevel)
                .toList()
            : []
        )
      );
    } catch (exception) {
      console.log(
        " exception in computeLowStockItems : " + exception.toString()
      );

      this.setLowStockItems([]);
    }
  };
  public setLowStockCount(val: number): void {
    let isValChanged: boolean = this.lowStockCount !== val;

    if (!isValChanged) {
      return;
    }

    this.lowStockCount = val;

    this.fire("lowStockCount", this);
  }
  public computeLowStockCount = (): void => {
    try {
      this.setLowStockCount(this.lowStockItems.length);
    } catch (exception) {
      console.log(
        " exception in computeLowStockCount : " + exception.toString()
      );

      this.setLowStockCount(0);
    }
  };
  public setOutOfStockData(val: OutOfStockItems): void {
    let isValChanged: boolean = this.outOfStockData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("outOfStockData", this.outOfStockData, val);

    MessageDispatch.get().dispose(this.outOfStockData);

    this.outOfStockData = val;

    this.fire("outOfStockData", this);
  }
  public computeOutOfStockData = async (): Promise<void> => {
    try {
      this.setOutOfStockData(
        this.organization !== null
          ? await Query.get().getOutOfStockItems(
              UsageConstants.QUERY_GETOUTOFSTOCKITEMS_DASHBOARDPAGE_PROPERTIES_OUTOFSTOCKDATA_COMPUTATION,
              new OutOfStockItemsRequest({ organization: this.organization }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(
        " exception in computeOutOfStockData : " + exception.toString()
      );

      this.setOutOfStockData(null);
    }
  };
  public setOutOfStockCount(val: number): void {
    let isValChanged: boolean = this.outOfStockCount !== val;

    if (!isValChanged) {
      return;
    }

    this.outOfStockCount = val;

    this.fire("outOfStockCount", this);
  }
  public computeOutOfStockCount = (): void => {
    try {
      this.setOutOfStockCount(
        this.outOfStockData !== null ? this.outOfStockData.count : 0
      );
    } catch (exception) {
      console.log(
        " exception in computeOutOfStockCount : " + exception.toString()
      );

      this.setOutOfStockCount(0);
    }
  };
  public setExpiringData(val: ExpiringBatches): void {
    let isValChanged: boolean = this.expiringData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("expiringData", this.expiringData, val);

    MessageDispatch.get().dispose(this.expiringData);

    this.expiringData = val;

    this.fire("expiringData", this);
  }
  public computeExpiringData = async (): Promise<void> => {
    try {
      this.setExpiringData(
        this.organization !== null
          ? await Query.get().getExpiringBatches(
              UsageConstants.QUERY_GETEXPIRINGBATCHES_DASHBOARDPAGE_PROPERTIES_EXPIRINGDATA_COMPUTATION,
              new ExpiringBatchesRequest({
                organization: this.organization,
                daysAhead: 30,
              }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(
        " exception in computeExpiringData : " + exception.toString()
      );

      this.setExpiringData(null);
    }
  };
  public setExpiringBatches(val: Array<StockBatch>): void {
    let isValChanged: boolean = CollectionUtils.isNotEquals(
      this.expiringBatches,
      val
    );

    if (!isValChanged) {
      return;
    }

    this.updateObservableColl("expiringBatches", this.expiringBatches, val);

    this.expiringBatches.clear();

    this.expiringBatches.addAll(val);

    this.fire("expiringBatches", this);
  }
  public addToExpiringBatches(val: StockBatch, index: number = -1): void {
    if (index === -1) {
      if (!this.expiringBatches.contains(val)) this.expiringBatches.add(val);
    } else {
      this.expiringBatches.remove(this.expiringBatches.elementAt(index));

      this.expiringBatches.add(val);
    }

    this.fire("expiringBatches", this, val, true);

    this.updateObservable("expiringBatches", null, val);
  }
  public removeFromExpiringBatches(val: StockBatch): void {
    this.expiringBatches.remove(val);

    this.fire("expiringBatches", this, val, false);

    this.removeObservable("expiringBatches", val);
  }
  public computeExpiringBatches = (): void => {
    try {
      this.setExpiringBatches(
        Array.from(this.expiringData !== null ? this.expiringData.items : [])
      );
    } catch (exception) {
      console.log(
        " exception in computeExpiringBatches : " + exception.toString()
      );

      this.setExpiringBatches([]);
    }
  };
  public setMovementsData(val: InventoryMovementsByDateRange): void {
    let isValChanged: boolean = this.movementsData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("movementsData", this.movementsData, val);

    MessageDispatch.get().dispose(this.movementsData);

    this.movementsData = val;

    this.fire("movementsData", this);
  }
  public computeMovementsData = async (): Promise<void> => {
    try {
      this.setMovementsData(
        this.organization !== null
          ? await Query.get().getInventoryMovementsByDateRange(
              UsageConstants.QUERY_GETINVENTORYMOVEMENTSBYDATERANGE_DASHBOARDPAGE_PROPERTIES_MOVEMENTSDATA_COMPUTATION,
              new InventoryMovementsByDateRangeRequest({
                organization: this.organization,
                startDate: DateTime.now().subtract(new Duration({ days: 7 })),
                endDate: DateTime.now(),
              }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(
        " exception in computeMovementsData : " + exception.toString()
      );

      this.setMovementsData(null);
    }
  };
  public setRecentMovements(val: Array<InventoryMovement>): void {
    let isValChanged: boolean = CollectionUtils.isNotEquals(
      this.recentMovements,
      val
    );

    if (!isValChanged) {
      return;
    }

    this.updateObservableColl("recentMovements", this.recentMovements, val);

    this.recentMovements.clear();

    this.recentMovements.addAll(val);

    this.fire("recentMovements", this);
  }
  public addToRecentMovements(
    val: InventoryMovement,
    index: number = -1
  ): void {
    if (index === -1) {
      if (!this.recentMovements.contains(val)) this.recentMovements.add(val);
    } else {
      this.recentMovements.remove(this.recentMovements.elementAt(index));

      this.recentMovements.add(val);
    }

    this.fire("recentMovements", this, val, true);

    this.updateObservable("recentMovements", null, val);
  }
  public removeFromRecentMovements(val: InventoryMovement): void {
    this.recentMovements.remove(val);

    this.fire("recentMovements", this, val, false);

    this.removeObservable("recentMovements", val);
  }
  public computeRecentMovements = (): void => {
    try {
      this.setRecentMovements(
        Array.from(
          this.movementsData !== null
            ? this.movementsData.items.length > 5
              ? this.movementsData.items.sublist(0, 5)
              : this.movementsData.items
            : []
        )
      );
    } catch (exception) {
      console.log(
        " exception in computeRecentMovements : " + exception.toString()
      );

      this.setRecentMovements([]);
    }
  };
  public setUnreadNotificationsData(val: UnreadNotificationCount): void {
    let isValChanged: boolean = this.unreadNotificationsData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable(
      "unreadNotificationsData",
      this.unreadNotificationsData,
      val
    );

    MessageDispatch.get().dispose(this.unreadNotificationsData);

    this.unreadNotificationsData = val;

    this.fire("unreadNotificationsData", this);
  }
  public computeUnreadNotificationsData = async (): Promise<void> => {
    try {
      this.setUnreadNotificationsData(
        this.organization !== null
          ? await Query.get().getUnreadNotificationCount(
              UsageConstants.QUERY_GETUNREADNOTIFICATIONCOUNT_DASHBOARDPAGE_PROPERTIES_UNREADNOTIFICATIONSDATA_COMPUTATION,
              new UnreadNotificationCountRequest({
                organization: this.organization,
                user: this.user,
              }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(
        " exception in computeUnreadNotificationsData : " + exception.toString()
      );

      this.setUnreadNotificationsData(null);
    }
  };
  public setUnreadCount(val: number): void {
    let isValChanged: boolean = this.unreadCount !== val;

    if (!isValChanged) {
      return;
    }

    this.unreadCount = val;

    this.fire("unreadCount", this);
  }
  public computeUnreadCount = (): void => {
    try {
      this.setUnreadCount(
        this.unreadNotificationsData !== null
          ? this.unreadNotificationsData.count
          : 0
      );
    } catch (exception) {
      console.log(" exception in computeUnreadCount : " + exception.toString());

      this.setUnreadCount(0);
    }
  };
  public setNotificationsData(val: AllInAppNotifications): void {
    let isValChanged: boolean = this.notificationsData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("notificationsData", this.notificationsData, val);

    MessageDispatch.get().dispose(this.notificationsData);

    this.notificationsData = val;

    this.fire("notificationsData", this);
  }
  public computeNotificationsData = async (): Promise<void> => {
    try {
      this.setNotificationsData(
        this.organization !== null
          ? await Query.get().getAllInAppNotifications(
              UsageConstants.QUERY_GETALLINAPPNOTIFICATIONS_DASHBOARDPAGE_PROPERTIES_NOTIFICATIONSDATA_COMPUTATION,
              new AllInAppNotificationsRequest({
                organization: this.organization,
                user: this.user,
              }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(
        " exception in computeNotificationsData : " + exception.toString()
      );

      this.setNotificationsData(null);
    }
  };
  public setNotifications(val: Array<InAppNotification>): void {
    let isValChanged: boolean = CollectionUtils.isNotEquals(
      this.notifications,
      val
    );

    if (!isValChanged) {
      return;
    }

    this.updateObservableColl("notifications", this.notifications, val);

    this.notifications.clear();

    this.notifications.addAll(val);

    this.fire("notifications", this);
  }
  public addToNotifications(val: InAppNotification, index: number = -1): void {
    if (index === -1) {
      if (!this.notifications.contains(val)) this.notifications.add(val);
    } else {
      this.notifications.remove(this.notifications.elementAt(index));

      this.notifications.add(val);
    }

    this.fire("notifications", this, val, true);

    this.updateObservable("notifications", null, val);
  }
  public removeFromNotifications(val: InAppNotification): void {
    this.notifications.remove(val);

    this.fire("notifications", this, val, false);

    this.removeObservable("notifications", val);
  }
  public computeNotifications = (): void => {
    try {
      this.setNotifications(
        Array.from(
          this.notificationsData !== null ? this.notificationsData.items : []
        )
      );
    } catch (exception) {
      console.log(
        " exception in computeNotifications : " + exception.toString()
      );

      this.setNotifications([]);
    }
  };
  public setStockValueLabel(val: string): void {
    let isValChanged: boolean = this.stockValueLabel !== val;

    if (!isValChanged) {
      return;
    }

    this.stockValueLabel = val;

    this.fire("stockValueLabel", this);
  }
  public computeStockValueLabel = (): void => {
    try {
      this.setStockValueLabel(
        "$" +
          (this.lowStockData !== null
            ? this.lowStockData.count.toString()
            : "0")
      );
    } catch (exception) {
      console.log(
        " exception in computeStockValueLabel : " + exception.toString()
      );

      this.setStockValueLabel("");
    }
  };
  public onSidebarNavigate = (route: string): void => {
    this.onNavigateHandler(route);
  };
  public onHeaderLogout = (): void => {
    this.onLogoutHandler();
  };
  public onHeaderProfile = (): void => {
    this.onProfileHandler();
  };
  public onHeaderAddProduct = (): void => {
    this.productSku = "";
    this.productName = "";
    this.productDescription = "";
    this.productBarcode = "";
    this.productPurchasePrice = "";
    this.productSellingPrice = "";
    this.productReorderLevel = "";
    this.productReorderQty = "";
    this.productStatus = "Active";
    this.productFormError = "";
    this.showProductModal = true;
    this.fire("showProductModal", this);
  };
  public closeProductModal = (): void => {
    this.showProductModal = false;
    this.productFormError = "";
    this.fire("showProductModal", this);
  };
  public saveProductForm = async (): Promise<void> => {
    const sku = this.productSku.trim();
    const name = this.productName.trim();
    if (!sku) {
      this.productFormError = "SKU is required.";
      this.fire("productFormError", this);
      return;
    }
    if (!name) {
      this.productFormError = "Name is required.";
      this.fire("productFormError", this);
      return;
    }
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.productFormError = "Organization not found.";
      this.fire("productFormError", this);
      return;
    }

    this.isSavingProduct = true;
    this.productFormError = "";
    this.fire("isSavingProduct", this);

    const result = await ProductApi.createProduct({
      sku,
      name,
      description: this.productDescription.trim(),
      barcode: this.productBarcode.trim(),
      purchasePrice: this.productPurchasePrice
        ? Number(this.productPurchasePrice)
        : undefined,
      sellingPrice: this.productSellingPrice
        ? Number(this.productSellingPrice)
        : undefined,
      reorderLevel: this.productReorderLevel
        ? Number(this.productReorderLevel)
        : undefined,
      reorderQuantity: this.productReorderQty
        ? Number(this.productReorderQty)
        : undefined,
      status: this.productStatus,
      organization: orgId,
    });

    this.isSavingProduct = false;
    this.fire("isSavingProduct", this);

    if (!result.success) {
      this.productFormError =
        result.errors.length > 0 ? result.errors.join(", ") : "Failed to save product.";
      this.fire("productFormError", this);
      return;
    }

    this.closeProductModal();
    this.computeProductsData();
  };
  private renderProductField(
    label: string,
    value: string,
    placeHolder: string,
    onChange: (val: string) => void,
    required?: boolean
  ): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({
            data: required ? label + " *" : label,
            className: "fieldLabel",
            key: "0",
          }),
          ui.InputField({
            value,
            placeHolder,
            onChanged: onChange,
            onFocusChange: () => {},
            key: "1",
          }),
        ],
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField storeFormField",
      key: label,
    });
  }
  private renderProductSelect(
    label: string,
    value: string,
    options: { value: string; label: string }[],
    onChange: (val: string) => void
  ): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({ data: label, className: "fieldLabel", key: "0" }),
          React.createElement(
            "select",
            {
              className: "roleSelect",
              value,
              disabled: this.isSavingProduct,
              onChange: (event: React.ChangeEvent<HTMLSelectElement>) => {
                onChange(event.target.value);
              },
              key: "1",
            },
            options.map((option) =>
              React.createElement(
                "option",
                { value: option.value, key: option.value },
                option.label
              )
            )
          ),
        ],
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField storeFormField",
      key: label,
    });
  }
  private renderProductModal(): ReactNode {
    return React.createElement(
      "div",
      {
        className: "storeModalOverlay",
        onClick: () => {
          if (!this.isSavingProduct) this.closeProductModal();
        },
      },
      React.createElement(
        "div",
        {
          className: "storeModalPanel glassCard productModalPanel",
          onClick: (e: React.MouseEvent) => e.stopPropagation(),
          role: "dialog",
          "aria-modal": "true",
        },
        React.createElement(
          "div",
          { className: "storeModalHeader" },
          React.createElement("h2", { className: "storeModalTitle" }, "Add Product"),
          React.createElement(
            "button",
            {
              type: "button",
              className: "storeModalClose",
              "aria-label": "Close",
              disabled: this.isSavingProduct,
              onClick: () => this.closeProductModal(),
            },
            "✕"
          )
        ),
        React.createElement(
          "div",
          { className: "storeModalBody productModalBody" },
          React.createElement(
            "div",
            { className: "productFormGrid" },
            this.renderProductField("SKU", this.productSku, "Product SKU", (val) => {
              this.productSku = val;
              this.fire("productSku", this);
            }, true),
            this.renderProductField("Name", this.productName, "Product name", (val) => {
              this.productName = val;
              this.fire("productName", this);
            }, true),
            ui.Container({
              child: ui.Column({
                mainAxisSize: ui.MainAxisSize.min,
                crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                children: [
                  TextView({ data: "Description", className: "fieldLabel", key: "0" }),
                  ui.InputField({
                    value: this.productDescription,
                    placeHolder: "Product description",
                    onChanged: (text: string) => {
                      this.productDescription = text;
                      this.fire("productDescription", this);
                    },
                    onFocusChange: () => {},
                    key: "1",
                  }),
                ],
              }),
              className:
                "FormFieldStyle IMSInputFieldWidget formField storeFormField productFormFullWidth",
              key: "description",
            }),
            this.renderProductField("Barcode", this.productBarcode, "Barcode", (val) => {
              this.productBarcode = val;
              this.fire("productBarcode", this);
            }),
            this.renderProductSelect(
              "Status",
              this.productStatus,
              [
                { value: "Active", label: "Active" },
                { value: "Inactive", label: "Inactive" },
                { value: "Discontinued", label: "Discontinued" },
              ],
              (val) => {
                this.productStatus = val;
                this.fire("productStatus", this);
              }
            ),
            this.renderProductField(
              "Purchase Price",
              this.productPurchasePrice,
              "0.00",
              (val) => {
                this.productPurchasePrice = val;
                this.fire("productPurchasePrice", this);
              }
            ),
            this.renderProductField(
              "Selling Price",
              this.productSellingPrice,
              "0.00",
              (val) => {
                this.productSellingPrice = val;
                this.fire("productSellingPrice", this);
              }
            ),
            this.renderProductField(
              "Reorder Level",
              this.productReorderLevel,
              "0",
              (val) => {
                this.productReorderLevel = val;
                this.fire("productReorderLevel", this);
              }
            ),
            this.renderProductField("Reorder Qty", this.productReorderQty, "0", (val) => {
              this.productReorderQty = val;
              this.fire("productReorderQty", this);
            }),
            this.productFormError
              ? TextView({
                  data: this.productFormError,
                  className: "storeFormError productFormFullWidth",
                  key: "error",
                })
              : null
          )
        ),
        React.createElement(
          "div",
          { className: "storeModalFooter" },
          TextButton({
            label: "Cancel",
            disable: this.isSavingProduct,
            onPressed: () => this.closeProductModal(),
            onFocusChange: () => {},
            className: "secondary",
          }),
          TextButton({
            label: this.isSavingProduct ? "Saving..." : "Save",
            disable: this.isSavingProduct,
            onPressed: () => {
              this.saveProductForm();
            },
            onFocusChange: () => {},
            className: "primary",
          })
        )
      )
    );
  }
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          IMSidebarWidget({
            currentUser: this.user,
            userProfile: this.userProfile,
            organization: this.organization,
            activeRoute: this.navRoute,
            onNavigate: this.onSidebarNavigate,
            key: "0",
          }),
          ui.Container({
            child: ui.Column({
              crossAxisAlignment: ui.CrossAxisAlignment.stretch,
              mainAxisSize: ui.MainAxisSize.max,
              children: [
                IMSAppHeaderWidget({
                  title: "",
                  organization: this.organization,
                  user: this.user,
                  userProfile: this.userProfile,
                  unreadCount: this.unreadCount,
                  notifications: this.notifications,
                  onLogout: this.onHeaderLogout,
                  onProfile: this.onHeaderProfile,
                  onAddProduct: this.onHeaderAddProduct,
                  key: "0",
                }),
                ScrollView2({
                  key: "1",
                  child: ui.Container({
                    child: this.renderDashboardBody(),
                    className:
                      this.userProfile === null
                        ? "dashboardContentPending"
                        : "dashboardContentReady",
                    key: "1",
                  }),
                  scrollDirection: ui.Axis.vertical,
                  className: "pageContent",
                  controller: this.contentRefScrollController,
                }),
                this.showProductModal ? this.renderProductModal() : null,
              ],
            }),
            className: "x0b storeMainContent",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_StatCardStyle_PrimaryButtonStyle DashboardPage pageBackground ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onLogoutHandler = (): void => {
    void AppLogout.signOut(this.navigator);
  };
  public onProfileHandler = (): void => {
    this.navigator.pushProfileSettingsPage({
      user: this.user,
      target: "main",
      replace: false,
    });
  };
  public updateBrowserRoute = (route: string): void => {
    if (typeof window === "undefined") {
      return;
    }

    if (route === "/dashboard") {
      window.history.replaceState(null, "", "#dashboard-page");
      return;
    }

    window.history.replaceState(
      null,
      "",
      "#dashboard-page?route=" + encodeURIComponent(route)
    );
  };
  public onNavigateHandler = (route: string): void => {
    if (!AppRouteNavigator.isPageRoute(route)) {
      this.setNavRoute(route);
      this.updateBrowserRoute(route);
      return;
    }

    if (route === "/dashboard") {
      this.setNavRoute("/dashboard");
      this.updateBrowserRoute("/dashboard");
      return;
    }

    AppRouteNavigator.navigate(this.navigator, route, this.user, {
      target: "main",
      replace: true,
    });
  };
  public onCreatePoHandler = (): void => {
    this.navigator.pushPurchaseOrderFormPage({
      user: this.user,
      purchaseOrder: new PurchaseOrder(),
      target: "main",
      replace: false,
    });
  };
  public dispose(): void {
    if (this._rebuildDebounceId != null) {
      window.clearTimeout(this._rebuildDebounceId);

      this._rebuildDebounceId = null;
    }

    MessageDispatch.get().dispose(this.userProfileData);

    MessageDispatch.get().dispose(this.productsData);

    MessageDispatch.get().dispose(this.lowStockData);

    MessageDispatch.get().dispose(this.outOfStockData);

    MessageDispatch.get().dispose(this.expiringData);

    MessageDispatch.get().dispose(this.movementsData);

    MessageDispatch.get().dispose(this.unreadNotificationsData);

    MessageDispatch.get().dispose(this.notificationsData);

    super.dispose();
  }
  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}
export default function DashboardPage(props: DashboardPageProps) {
  return React.createElement(_DashboardPageState, {
    ..._DashboardPageState.defaultProps,
    ...props,
  });
}
