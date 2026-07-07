import { ReactNode } from "react";
import User from "../models/User";
import PurchaseOrder from "../models/PurchaseOrder";
import GlobalFunctions from "./GlobalFunctions";
import LocalDataStore from "./LocalDataStore";
import { CustomerAccountTab } from "../components/CustomerAccountPage";

const PUBLIC_ROUTES = new Set([
  "",
  "#login-page",
  "#signup-page",
  "#forgot-password-page",
  "#cart-page",
  "#account-page",
  "#stores-page",
  "#new-arrivals-page",
]);

export function getHashRoute(): string {
  const hash = window.location.hash ?? "";
  const qIndex = hash.indexOf("?");
  return qIndex >= 0 ? hash.substring(0, qIndex) : hash;
}

function parseHashParams(): URLSearchParams | null {
  const hash = window.location.hash ?? "";
  const qIndex = hash.indexOf("?");
  if (qIndex < 0) {
    return null;
  }
  return new URLSearchParams(hash.substring(qIndex + 1));
}

export function requiresAuth(route: string): boolean {
  if (PUBLIC_ROUTES.has(route)) {
    return false;
  }
  if (route.startsWith("#product-detail-page")) {
    return false;
  }
  if (route.startsWith("#category-page")) {
    return false;
  }
  return true;
}

export async function restoreSessionUser(): Promise<User | null> {
  const store = LocalDataStore.get();
  const token = await store.getToken();
  const user = (await store.currentUser()) as User | null;
  if (!token || !user?.id) {
    return null;
  }
  LocalDataStore.auth();
  return user;
}

export function resolveRoutePage(route: string, user: User | null): ReactNode {
  const params = parseHashParams();

  if (route.startsWith("#product-detail-page")) {
    const productId = Number(params?.get("id") ?? 0);
    if (productId > 0) {
      return GlobalFunctions.ProductDetailPage({
        user: user ?? undefined,
        productId,
      });
    }
  }

  if (route.startsWith("#category-page")) {
    const categoryId = Number(params?.get("id") ?? 0);
    if (categoryId > 0) {
      return GlobalFunctions.CategoryProductsPage({
        user: user ?? undefined,
        categoryId,
        categoryName: params?.get("name") ?? "",
      });
    }
  }

  if (requiresAuth(route) && !user) {
    return GlobalFunctions.LoginPage({});
  }

  switch (route) {
    case "#login-page":
      return GlobalFunctions.LoginPage({});
    case "#signup-page":
      return GlobalFunctions.SignupPage({});
    case "#forgot-password-page":
      return GlobalFunctions.ForgotPasswordPage({});
    case "#cart-page":
      return GlobalFunctions.CustomerCartPage({ user: user ?? undefined });
    case "#account-page": {
      const tab = (params?.get("tab") as CustomerAccountTab) ?? "order-history";
      const validTabs: CustomerAccountTab[] = [
        "order-history",
        "address",
        "profile",
        "details",
      ];
      const activeTab = validTabs.includes(tab) ? tab : "order-history";
      return GlobalFunctions.CustomerAccountPage({
        user: user ?? undefined,
        tab: activeTab,
      });
    }
    case "#stores-page":
      return GlobalFunctions.StoresPage({ user: user ?? undefined });
    case "#new-arrivals-page":
      return GlobalFunctions.NewArrivalsPage({ user: user ?? undefined });
    case "#dashboard-page":
      return GlobalFunctions.DashboardPage({ user: user! });
    case "#purchase-order-list-page":
      return GlobalFunctions.PurchaseOrderListPage({ user: user! });
    case "#supplier-list-page":
      return GlobalFunctions.SupplierListPage({ user: user! });
    case "#purchase-order-form-page":
      return GlobalFunctions.PurchaseOrderFormPage({
        user: user!,
        purchaseOrder: new PurchaseOrder(),
      });
    case "#change-password-page":
      return GlobalFunctions.ChangePasswordPage({ user: user! });
    case "#notification-preferences-page":
      return GlobalFunctions.NotificationPreferencesPage({ user: user! });
    case "#organization-settings-page":
      return GlobalFunctions.OrganizationSettingsPage({ user: user! });
    case "#profile-settings-page":
      return GlobalFunctions.ProfileSettingsPage({ user: user! });
    case "#store-list-page":
      return GlobalFunctions.StoreListPage({ user: user! });
    case "#warehouse-list-page":
      return GlobalFunctions.WarehouseListPage({ user: user! });
    case "#user-list-page":
      return GlobalFunctions.UserListPage({ user: user! });
    case "#product-list-page":
      return GlobalFunctions.ProductListPage({ user: user! });
    case "#stock-levels-page":
      return GlobalFunctions.StockLevelsPage({ user: user! });
    case "#product-category-list-page":
      return GlobalFunctions.ProductCategoryListPage({ user: user! });
    case "#unit-of-measure-list-page":
      return GlobalFunctions.UnitOfMeasureListPage({ user: user! });
    default:
      return GlobalFunctions.LandingPage({ user: user ?? undefined });
  }
}
