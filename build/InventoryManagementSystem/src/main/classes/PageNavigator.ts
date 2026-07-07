import React, { ReactElement, ReactNode } from "react";
import * as ui from "../native";
import { ContextData } from "./BuildContext";
import PurchaseOrder from "../models/PurchaseOrder";
import ThemeWrapper from "../components/ThemeWrapper";
import User from "../models/User";
import GlobalFunctions from "../utils/GlobalFunctions";
import { StyleThemeData } from "../components/ThemeWrapper";

export type RouterController = (node: ReactNode) => void;

const pageRouters: Map<string, RouterController> = new Map<
  string,
  RouterController
>();

export default class PageNavigator {
  public static defaultOverlay: ui.OverlayController;
  public static themeUpdateListener = () => {};
  public kIsWeb = true;
  public ctx: ContextData;
  public constructor(ctx: ContextData) {
    this.ctx = ctx;
  }
  public static addListener(target: string, listener: RouterController) {
    pageRouters.set(target, listener);
  }
  public static removeListener(target: string) {
    pageRouters.remove(target);
  }
  public static of(ctx: ContextData): PageNavigator {
    return new PageNavigator(ctx);
  }
  public wrapTheme(widget: ReactNode): ReactNode {
    let mediaQuery = ui.MediaQuery.of(this.ctx);

    return ThemeWrapper({ child: widget });
  }
  public push(
    replace: boolean,
    widget: ReactNode,
    title: string,
    path: string,
    target: string
  ): void {
    if (this.kIsWeb) {
      if (replace) {
        window.history.replaceState(null, title, path);
      } else {
        window.history.pushState(null, title, path);
      }
    }

    if (!target) {
      target = "";
    }

    let listener = pageRouters.get(target);

    if (!listener && target !== "") {
      listener = pageRouters.get("");
    }

    if (listener) {
      listener(widget);
    }
  }
  public pop(): void {
    this.close();
  }
  public close(): boolean {
    if (this.ctx.popup) {
      this.ctx.popup.hidePopup({});

      return true;
    } else {
      return false;
    }
  }
  public pushChangePasswordPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.ChangePasswordPage({ user: user }),
      "",
      "#change-password-page",
      target
    );
  }
  public pushDashboardPage(
    d3eParams?: Partial<{
      replace: boolean;
      target: string;
      user: User;
      sidebarRoute?: string;
    }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    let sidebarRoute = d3eParams?.sidebarRoute;

    this.push(
      replace,
      GlobalFunctions.DashboardPage({ user: user, sidebarRoute: sidebarRoute }),
      "",
      "#dashboard-page",
      target
    );
  }
  public pushForgotPasswordPage(
    d3eParams?: Partial<{ replace: boolean; target: string }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    this.push(
      replace,
      GlobalFunctions.ForgotPasswordPage({}),
      "",
      "#forgot-password-page",
      target
    );
  }
  public pushProductDetailPage(
    d3eParams?: Partial<{
      replace: boolean;
      target: string;
      user: User;
      productId: number;
    }>
  ): void {
    let replace = d3eParams?.replace ?? false;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    let productId = d3eParams?.productId ?? 0;

    this.push(
      replace,
      GlobalFunctions.ProductDetailPage({ user: user, productId: productId }),
      "",
      `#product-detail-page?id=${encodeURIComponent(String(productId))}`,
      target
    );
  }
  public pushCustomerCartPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? false;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.CustomerCartPage({ user: user }),
      "",
      "#cart-page",
      target
    );
  }
  public pushCustomerAccountPage(
    d3eParams?: Partial<{
      replace: boolean;
      target: string;
      user: User;
      tab: import("../components/CustomerAccountPage").CustomerAccountTab;
    }>
  ): void {
    let replace = d3eParams?.replace ?? false;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    let tab = d3eParams?.tab ?? "order-history";

    this.push(
      replace,
      GlobalFunctions.CustomerAccountPage({ user: user, tab: tab }),
      "",
      `#account-page?tab=${encodeURIComponent(tab)}`,
      target
    );
  }
  public pushStoresPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? false;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.StoresPage({ user: user }),
      "",
      "#stores-page",
      target
    );
  }
  public pushNewArrivalsPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? false;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.NewArrivalsPage({ user: user }),
      "",
      "#new-arrivals-page",
      target
    );
  }
  public pushCategoryProductsPage(
    d3eParams?: Partial<{
      replace: boolean;
      target: string;
      user: User;
      categoryId: number;
      categoryName: string;
    }>
  ): void {
    let replace = d3eParams?.replace ?? false;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    let categoryId = d3eParams?.categoryId ?? 0;

    let categoryName = d3eParams?.categoryName ?? "";

    const nameQuery =
      categoryName.trim().length > 0
        ? `&name=${encodeURIComponent(categoryName.trim())}`
        : "";

    this.push(
      replace,
      GlobalFunctions.CategoryProductsPage({
        user: user,
        categoryId: categoryId,
        categoryName: categoryName,
      }),
      "",
      `#category-page?id=${encodeURIComponent(String(categoryId))}${nameQuery}`,
      target
    );
  }
  public pushLandingPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    const landingPath =
      this.kIsWeb && typeof window !== "undefined"
        ? window.location.pathname + window.location.search
        : "";

    this.push(
      replace,
      GlobalFunctions.LandingPage({ user: user }),
      "",
      landingPath,
      target
    );
  }
  public pushLoginPage(
    d3eParams?: Partial<{
      replace: boolean;
      target: string;
      customerMode: boolean;
    }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let customerMode = d3eParams?.customerMode ?? false;

    this.push(
      replace,
      GlobalFunctions.LoginPage({ customerMode: customerMode }),
      "",
      "#login-page",
      target
    );
  }
  public pushSignupPage(
    d3eParams?: Partial<{
      replace: boolean;
      target: string;
      customerMode: boolean;
    }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let customerMode = d3eParams?.customerMode ?? false;

    this.push(
      replace,
      GlobalFunctions.SignupPage({ customerMode: customerMode }),
      "",
      "#signup-page",
      target
    );
  }
  public pushNotificationPreferencesPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.NotificationPreferencesPage({ user: user }),
      "",
      "#notification-preferences-page",
      target
    );
  }
  public pushOrganizationSettingsPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.OrganizationSettingsPage({ user: user }),
      "",
      "#organization-settings-page",
      target
    );
  }
  public pushProfileSettingsPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.ProfileSettingsPage({ user: user }),
      "",
      "#profile-settings-page",
      target
    );
  }
  public pushPurchaseOrderFormPage(
    d3eParams?: Partial<{
      replace: boolean;
      target: string;
      user: User;
      purchaseOrder: PurchaseOrder;
    }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    let purchaseOrder = d3eParams?.purchaseOrder;

    this.push(
      replace,
      GlobalFunctions.PurchaseOrderFormPage({
        user: user,
        purchaseOrder: purchaseOrder,
      }),
      "",
      "#purchase-order-form-page",
      target
    );
  }
  public pushSupplierListPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.SupplierListPage({ user: user }),
      "",
      "#supplier-list-page",
      target
    );
  }
  public pushPurchaseOrderListPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.PurchaseOrderListPage({ user: user }),
      "",
      "#purchase-order-list-page",
      target
    );
  }
  public pushStoreListPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.StoreListPage({ user: user }),
      "",
      "#store-list-page",
      target
    );
  }
  public pushWarehouseListPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.WarehouseListPage({ user: user }),
      "",
      "#warehouse-list-page",
      target
    );
  }
  public pushUserListPage(
    d3eParams?: Partial<{
      replace: boolean;
      target: string;
      user: User;
      openCreateForm?: boolean;
    }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    let openCreateForm = d3eParams?.openCreateForm ?? false;

    this.push(
      replace,
      GlobalFunctions.UserListPage({ user: user, openCreateForm: openCreateForm }),
      "",
      "#user-list-page",
      target
    );
  }
  public pushProductListPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;
    let target = d3eParams?.target;
    let user = d3eParams?.user;
    this.push(
      replace,
      GlobalFunctions.ProductListPage({ user: user }),
      "",
      "#product-list-page",
      target
    );
  }
  public pushStockLevelsPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;
    let target = d3eParams?.target;
    let user = d3eParams?.user;
    this.push(
      replace,
      GlobalFunctions.StockLevelsPage({ user: user }),
      "",
      "#stock-levels-page",
      target
    );
  }
  public pushProductCategoryListPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;

    let target = d3eParams?.target;

    let user = d3eParams?.user;

    this.push(
      replace,
      GlobalFunctions.ProductCategoryListPage({ user: user }),
      "",
      "#product-category-list-page",
      target
    );
  }
  public pushUnitOfMeasureListPage(
    d3eParams?: Partial<{ replace: boolean; target: string; user: User }>
  ): void {
    let replace = d3eParams?.replace ?? true;
    let target = d3eParams?.target;
    let user = d3eParams?.user;
    this.push(
      replace,
      GlobalFunctions.UnitOfMeasureListPage({ user: user }),
      "",
      "#unit-of-measure-list-page",
      target
    );
  }
  public applyTheme(d3eParams?: Partial<{ theme: string }>): void {
    let theme = d3eParams?.theme;

    /*
TODO
*/

    switch (theme) {
      case "IMSTheme": {
        StyleThemeData.current = StyleThemeData.createIMSTheme();

        PageNavigator.themeUpdateListener();

        /*
TODO ThemeBuilder.of(this.ctx).rebuild();
*/
        break;
      }
      default: {
        StyleThemeData.current = StyleThemeData.createIMSTheme();

        PageNavigator.themeUpdateListener();

        /*
TODO ThemeBuilder.of(this.ctx).rebuild();
*/
      }
    }
  }
  public getProjectThemeNames(): string[] {
    return ["IMSTheme"];
  }
  public getCurrentThemeName(): string {
    return StyleThemeData.current.themeName;
  }
}
