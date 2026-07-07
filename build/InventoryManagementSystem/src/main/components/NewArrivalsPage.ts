import React, { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import PageNavigator from "../classes/PageNavigator";
import { BuildContext } from "../classes/BuildContext";
import User from "../models/User";
import CustomerNavigation from "../utils/CustomerNavigation";
import CustomerSearchNavigation from "../utils/CustomerSearchNavigation";
import CustomerProfileNavigation from "../utils/CustomerProfileNavigation";
import { CustomerAccountTab } from "./CustomerAccountPage";
import CustomerSearchSession from "../utils/CustomerSearchSession";
import LandingCatalogApi, { PublicProductRecord } from "../utils/LandingCatalogApi";
import { LandingSiteHeader } from "./LandingPage";
import LandingFooter from "./LandingFooter";
import CustomerLiveProductGrid from "./CustomerLiveProductGrid";

export interface NewArrivalsPageProps extends BaseUIProps {
  key?: string;
  user?: User;
}

class _NewArrivalsPageState extends ObservableComponent<NewArrivalsPageProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public products: PublicProductRecord[] = [];
  public loading = true;
  public loadError = "";

  public constructor(props: NewArrivalsPageProps) {
    super(props);
    this.initState();
  }

  public initState() {
    super.initState();
    this.enableBuild = true;
    this.on(["products", "loading", "loadError"], this.rebuild);
  }

  public componentDidMount(): void {
    super.componentDidMount();
    this.loadProducts();
  }

  public loadProducts = async (): Promise<void> => {
    this.loading = true;
    this.loadError = "";
    this.fire("loading", this);
    this.fire("loadError", this);

    try {
      this.products = await LandingCatalogApi.getNewArrivalProducts(24);
    } catch {
      this.products = [];
      this.loadError = "Unable to load new arrivals. Please try again.";
    } finally {
      this.loading = false;
      this.fire("loading", this);
      this.fire("products", this);
      this.fire("loadError", this);
    }
  };

  public openProductDetail = (product: PublicProductRecord): void => {
    this.navigator.pushProductDetailPage({
      user: this.props.user,
      productId: product.id,
      target: "main",
      replace: false,
    });
  };

  public goLogin = (): void => {
    this.navigator.pushLoginPage({ customerMode: true, target: "main" });
  };

  public goCart = (): void => {
    this.navigator.pushCustomerCartPage({
      user: this.props.user,
      target: "main",
      replace: false,
    });
  };

  public handleNavigate = (item: string): void => {
    CustomerNavigation.navigate(this.navigator, item, this.props.user);
  };

  public handleSearch = (query: string, scroll = true): void => {
    if (!scroll) {
      return;
    }

    void CustomerSearchNavigation.submitSearch(
      this.navigator,
      query,
      this.props.user,
      true
    );
  };

  public handleProductSelect = (product: PublicProductRecord): void => {
    CustomerSearchNavigation.openProduct(this.navigator, product, this.props.user);
  };

  public handleProfileMenu = (tab: CustomerAccountTab): void => {
    CustomerProfileNavigation.openTab(this.navigator, tab, this.props.user);
  };

  public render(): ReactNode {
    return ui.Container({
      child: React.createElement(
        "div",
        { className: "landingPageShell newArrivalsPageRoot" },
        React.createElement(LandingSiteHeader, {
          user: this.props.user,
          activeNav: "New Arrivals",
          onNavigate: this.handleNavigate,
          onLogin: this.goLogin,
          onSignup: () =>
            this.navigator.pushSignupPage({ customerMode: true, target: "main" }),
          onCart: this.goCart,
          onSearch: this.handleSearch,
          onProductSelect: this.handleProductSelect,
          onProfileMenu: this.handleProfileMenu,
          key: "site-header",
        }),
        React.createElement(
          "div",
          { className: "landingScroll newArrivalsPageScroll", key: "scroll" },
          React.createElement(
            "div",
            { className: "landingContainer newArrivalsPageMain" },
            React.createElement(
              "header",
              { className: "catalogPageHead" },
              React.createElement("h1", { className: "catalogPageTitle" }, "New Arrivals"),
              React.createElement(
                "p",
                { className: "catalogPageSubtitle" },
                "Freshly added products — discover the latest picks at FreshMart."
              )
            ),
            this.loading
              ? React.createElement("p", { className: "catalogPageStatus" }, "Loading products...")
              : this.loadError
                ? React.createElement(
                    "p",
                    { className: "catalogPageStatus catalogPageStatusError" },
                    this.loadError
                  )
                : React.createElement(CustomerLiveProductGrid, {
                    products: this.products,
                    showNewBadge: true,
                    onProductClick: this.openProductDetail,
                    onLogin: this.goLogin,
                  })
          ),
          React.createElement(LandingFooter, { key: "footer" })
        )
      ),
      className: ui.join("NewArrivalsPage ", this.props.className ?? ""),
      ...copyBaseUIProps(this.props),
    });
  }

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function NewArrivalsPage(props: NewArrivalsPageProps) {
  return React.createElement(_NewArrivalsPageState, props);
}
