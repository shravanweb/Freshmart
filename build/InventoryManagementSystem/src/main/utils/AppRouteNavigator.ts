import PageNavigator from "../classes/PageNavigator";
import PurchaseOrder from "../models/PurchaseOrder";
import User from "../models/User";

export interface AppNavigateOptions {
  replace?: boolean;
  target?: string;
}

const PAGE_ROUTES = new Set([
  "/dashboard",
  "/stores",
  "/warehouses",
  "/catalog/categories",
  "/catalog/uom",
  "/catalog/products",
  "/inventory/stock",
  "/admin/users",
  "/admin/users/invite",
  "/settings/profile",
  "/settings/notifications",
  "/settings/organization",
  "/procurement/suppliers",
  "/procurement/purchase-orders",
  "/procurement/purchase-orders/new",
  "/change-password",
]);

export default class AppRouteNavigator {
  public static isPageRoute(route: string): boolean {
    return PAGE_ROUTES.has(route);
  }

  public static navigate(
    navigator: PageNavigator,
    route: string,
    user: User,
    options?: AppNavigateOptions
  ): void {
    const replace = options?.replace ?? true;
    const target = options?.target ?? "main";
    const base = { user, target, replace };

    switch (route) {
      case "/dashboard":
        navigator.pushDashboardPage({ ...base, sidebarRoute: "/dashboard" });
        return;
      case "/stores":
        navigator.pushStoreListPage(base);
        return;
      case "/warehouses":
        navigator.pushWarehouseListPage(base);
        return;
      case "/catalog/categories":
        navigator.pushProductCategoryListPage(base);
        return;
      case "/catalog/uom":
        navigator.pushUnitOfMeasureListPage(base);
        return;
      case "/catalog/products":
        navigator.pushProductListPage(base);
        return;
      case "/inventory/stock":
        navigator.pushStockLevelsPage(base);
        return;
      case "/admin/users":
        navigator.pushUserListPage(base);
        return;
      case "/admin/users/invite":
        navigator.pushUserListPage({ ...base, openCreateForm: true });
        return;
      case "/settings/profile":
        navigator.pushProfileSettingsPage(base);
        return;
      case "/settings/notifications":
        navigator.pushNotificationPreferencesPage(base);
        return;
      case "/settings/organization":
        navigator.pushOrganizationSettingsPage(base);
        return;
      case "/procurement/suppliers":
        navigator.pushSupplierListPage(base);
        return;
      case "/procurement/purchase-orders":
        navigator.pushPurchaseOrderListPage(base);
        return;
      case "/procurement/purchase-orders/new":
        navigator.pushPurchaseOrderFormPage({
          ...base,
          purchaseOrder: new PurchaseOrder(),
        });
        return;
      case "/change-password":
        navigator.pushChangePasswordPage(base);
        return;
      default:
        navigator.pushDashboardPage({ ...base, sidebarRoute: route });
    }
  }
}
