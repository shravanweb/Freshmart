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
import LandingCatalogApi, { PublicProductRecord, PublicStoreRecord } from "../utils/LandingCatalogApi";
import { LandingSiteHeader } from "./LandingPage";
import LandingFooter from "./LandingFooter";

export interface StoresPageProps extends BaseUIProps {
  key?: string;
  user?: User;
}

function formatStoreType(value: string): string {
  if (!value) {
    return "Store";
  }
  return value.replace(/([a-z])([A-Z])/g, "$1 $2");
}

class _StoresPageState extends ObservableComponent<StoresPageProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public stores: PublicStoreRecord[] = [];
  public loading = true;
  public loadError = "";

  public constructor(props: StoresPageProps) {
    super(props);
    this.initState();
  }

  public initState() {
    super.initState();
    this.enableBuild = true;
    this.on(["stores", "loading", "loadError"], this.rebuild);
  }

  public componentDidMount(): void {
    super.componentDidMount();
    this.loadStores();
  }

  public loadStores = async (): Promise<void> => {
    this.loading = true;
    this.loadError = "";
    this.fire("loading", this);
    this.fire("loadError", this);

    try {
      this.stores = await LandingCatalogApi.getPublicStores();
    } catch {
      this.stores = [];
      this.loadError = "Unable to load stores. Please try again.";
    } finally {
      this.loading = false;
      this.fire("loading", this);
      this.fire("stores", this);
      this.fire("loadError", this);
    }
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

  private renderStoreCard(store: PublicStoreRecord): ReactNode {
    return React.createElement(
      "article",
      { className: "storesPageCard", key: "store-" + store.id },
      React.createElement(
        "div",
        { className: "storesPageCardIcon" },
        "🏪"
      ),
      React.createElement(
        "div",
        { className: "storesPageCardBody" },
        React.createElement(
          "div",
          { className: "storesPageCardHead" },
          React.createElement("h2", { className: "storesPageCardTitle" }, store.name),
          React.createElement(
            "span",
            { className: "storesPageCardType" },
            formatStoreType(store.storeType)
          )
        ),
        store.code
          ? React.createElement("p", { className: "storesPageCardMeta" }, "Code: " + store.code)
          : null,
        store.address
          ? React.createElement(
              "p",
              { className: "storesPageCardAddress" },
              store.address
            )
          : React.createElement(
              "p",
              { className: "storesPageCardAddress storesPageCardAddressMuted" },
              "Address will be updated soon"
            ),
        React.createElement(
          "div",
          { className: "storesPageCardContact" },
          store.phone
            ? React.createElement("span", null, "📞 " + store.phone)
            : null,
          store.email
            ? React.createElement("span", null, "✉ " + store.email)
            : null
        )
      )
    );
  }

  public render(): ReactNode {
    return ui.Container({
      child: React.createElement(
        "div",
        { className: "landingPageShell storesPageRoot" },
        React.createElement(LandingSiteHeader, {
          user: this.props.user,
          activeNav: "Stores",
          onNavigate: this.handleNavigate,
          onLogin: this.goLogin,
          onSignup: () =>
            this.navigator.pushSignupPage({ customerMode: true, target: "main" }),
          onCart: this.goCart,
          onSearch: this.handleSearch,
          onProfileMenu: this.handleProfileMenu,
          key: "site-header",
        }),
        React.createElement(
          "div",
          { className: "landingScroll storesPageScroll", key: "scroll" },
          React.createElement(
            "div",
            { className: "landingContainer storesPageMain" },
            React.createElement(
              "header",
              { className: "catalogPageHead" },
              React.createElement("h1", { className: "catalogPageTitle" }, "FreshMart Stores"),
              React.createElement(
                "p",
                { className: "catalogPageSubtitle" },
                "Find a FreshMart store near you for pickup, support, and local shopping."
              )
            ),
            this.loading
              ? React.createElement("p", { className: "catalogPageStatus" }, "Loading stores...")
              : this.loadError
                ? React.createElement("p", { className: "catalogPageStatus catalogPageStatusError" }, this.loadError)
                : this.stores.length === 0
                  ? React.createElement(
                      "div",
                      { className: "catalogEmptyState" },
                      React.createElement("p", null, "No stores available right now.")
                    )
                  : React.createElement(
                      "div",
                      { className: "storesPageGrid" },
                      this.stores.map((store) => this.renderStoreCard(store))
                    )
          ),
          React.createElement(LandingFooter, { key: "footer" })
        )
      ),
      className: ui.join("StoresPage ", this.props.className ?? ""),
      ...copyBaseUIProps(this.props),
    });
  }

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function StoresPage(props: StoresPageProps) {
  return React.createElement(_StoresPageState, props);
}
