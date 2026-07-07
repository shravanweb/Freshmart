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
import LandingCatalogApi, { PublicProductRecord } from "../utils/LandingCatalogApi";
import {
  buildCategoryFilterOptions,
  CATEGORY_FILTER_GROUPS,
  CategoryFilterGroup,
  CategoryFilterOptions,
  CategoryFilterSelections,
  createEmptyCategoryFilterSelections,
  filterCategoryProducts,
  hasActiveCategoryFilters,
} from "../utils/CategoryProductFilters";
import { LandingSiteHeader } from "./LandingPage";
import LandingFooter from "./LandingFooter";
import CustomerLiveProductGrid from "./CustomerLiveProductGrid";
import CategoryFilterSidebar from "./CategoryFilterSidebar";

export interface CategoryProductsPageProps extends BaseUIProps {
  key?: string;
  user?: User;
  categoryId?: number;
  categoryName?: string;
}

class _CategoryProductsPageState extends ObservableComponent<CategoryProductsPageProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public products: PublicProductRecord[] = [];
  public loading = true;
  public loadError = "";
  public resolvedCategoryName = "";
  public filterSelections: CategoryFilterSelections = createEmptyCategoryFilterSelections();

  public constructor(props: CategoryProductsPageProps) {
    super(props);
    this.initState();
  }

  public initState() {
    super.initState();
    this.enableBuild = true;
    this.on(
      ["products", "loading", "loadError", "resolvedCategoryName", "filterSelections"],
      this.rebuild
    );
    this.resolvedCategoryName = this.props.categoryName?.trim() ?? "";
  }

  public componentDidMount(): void {
    super.componentDidMount();
    void this.loadProducts();
  }

  public componentDidUpdate(prevProps: CategoryProductsPageProps): void {
    super.componentDidUpdate(prevProps);
    if (
      prevProps.categoryId !== this.props.categoryId ||
      prevProps.categoryName !== this.props.categoryName
    ) {
      this.resolvedCategoryName = this.props.categoryName?.trim() ?? "";
      this.filterSelections = createEmptyCategoryFilterSelections();
      this.fire("filterSelections", this);
      void this.loadProducts();
    }
  }

  private get categoryId(): number {
    return this.props.categoryId ?? 0;
  }

  public get filterOptions(): CategoryFilterOptions {
    return buildCategoryFilterOptions(this.products);
  }

  public get filteredProducts(): PublicProductRecord[] {
    return filterCategoryProducts(this.products, this.filterSelections);
  }

  public loadProducts = async (): Promise<void> => {
    const categoryId = this.categoryId;
    if (categoryId <= 0) {
      this.products = [];
      this.loadError = "Category not found.";
      this.loading = false;
      this.fire("loading", this);
      this.fire("loadError", this);
      this.fire("products", this);
      return;
    }

    this.loading = true;
    this.loadError = "";
    this.fire("loading", this);
    this.fire("loadError", this);

    try {
      const [products, categories] = await Promise.all([
        LandingCatalogApi.getPublicProducts(categoryId),
        this.resolvedCategoryName
          ? Promise.resolve([])
          : LandingCatalogApi.getPublicCategories(),
      ]);

      this.products = products;

      if (!this.resolvedCategoryName) {
        const match = categories.find((cat) => cat.id === categoryId);
        this.resolvedCategoryName =
          match?.name ?? products[0]?.categoryName ?? "Category";
        this.fire("resolvedCategoryName", this);
      }
    } catch {
      this.products = [];
      this.loadError = "Unable to load products. Please try again.";
    } finally {
      this.loading = false;
      this.fire("loading", this);
      this.fire("products", this);
      this.fire("loadError", this);
    }
  };

  public toggleFilter = (group: CategoryFilterGroup, value: string): void => {
    const current = this.filterSelections[group];
    const exists = current.some((item) => item.toLowerCase() === value.toLowerCase());
    const next = exists
      ? current.filter((item) => item.toLowerCase() !== value.toLowerCase())
      : [...current, value];

    this.filterSelections = {
      ...this.filterSelections,
      [group]: next,
    };
    this.fire("filterSelections", this);
  };

  public clearFilters = (): void => {
    this.filterSelections = createEmptyCategoryFilterSelections();
    this.fire("filterSelections", this);
  };

  public goHome = (): void => {
    this.navigator.pushLandingPage({
      user: this.props.user,
      target: "main",
      replace: false,
    });
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

  private renderActiveFilterChips(): ReactNode {
    if (!hasActiveCategoryFilters(this.filterSelections)) {
      return null;
    }

    const chips: Array<{ group: CategoryFilterGroup; value: string }> = [];
    for (const group of CATEGORY_FILTER_GROUPS) {
      for (const value of this.filterSelections[group.key]) {
        chips.push({ group: group.key, value });
      }
    }

    return React.createElement(
      "div",
      { className: "categoryActiveFilters" },
      chips.map((chip) =>
        React.createElement(
          "button",
          {
            className: "categoryActiveFilterChip",
            key: chip.group + "-" + chip.value,
            type: "button",
            onClick: () => this.toggleFilter(chip.group, chip.value),
            "aria-label": "Remove filter " + chip.value,
          },
          chip.value,
          React.createElement("span", { className: "categoryActiveFilterChipX", "aria-hidden": "true" }, "×")
        )
      )
    );
  }

  private renderProductArea(): ReactNode {
    if (this.loading) {
      return React.createElement(
        "div",
        { className: "categoryProductsLoading" },
        React.createElement("div", { className: "categoryProductsLoadingCard" }),
        React.createElement("div", { className: "categoryProductsLoadingCard" }),
        React.createElement("div", { className: "categoryProductsLoadingCard" }),
        React.createElement("div", { className: "categoryProductsLoadingCard" })
      );
    }

    if (this.loadError) {
      return React.createElement(
        "div",
        { className: "catalogEmptyState categoryProductsEmpty" },
        React.createElement("p", { className: "catalogPageStatus catalogPageStatusError" }, this.loadError)
      );
    }

    const filtered = this.filteredProducts;

    if (this.products.length === 0) {
      return React.createElement(
        "div",
        { className: "catalogEmptyState categoryProductsEmpty" },
        React.createElement("p", null, "No products in this category yet.")
      );
    }

    if (filtered.length === 0) {
      return React.createElement(
        "div",
        { className: "catalogEmptyState categoryProductsEmpty" },
        React.createElement(
          "p",
          null,
          "No products match the selected filters."
        ),
        React.createElement(
          "button",
          {
            className: "categoryProductsEmptyBtn",
            type: "button",
            onClick: this.clearFilters,
          },
          "Clear filters"
        )
      );
    }

    return React.createElement(CustomerLiveProductGrid, {
      products: filtered,
      onProductClick: this.openProductDetail,
      onLogin: this.goLogin,
    });
  }

  public render(): ReactNode {
    const title = this.resolvedCategoryName || "Category";
    const filtered = this.filteredProducts;
    const showSidebar = !this.loading && !this.loadError && this.products.length > 0;

    return ui.Container({
      child: React.createElement(
        "div",
        { className: "landingPageShell categoryProductsPageRoot" },
        React.createElement(LandingSiteHeader, {
          user: this.props.user,
          activeNav: "Home",
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
          { className: "landingScroll categoryProductsPageScroll", key: "scroll" },
          React.createElement(
            "div",
            { className: "landingContainer categoryProductsPageMain" },
            React.createElement(
              "nav",
              { className: "categoryBreadcrumb", "aria-label": "Breadcrumb" },
              React.createElement(
                "button",
                { className: "categoryBreadcrumbLink", type: "button", onClick: this.goHome },
                "Home"
              ),
              React.createElement("span", { className: "categoryBreadcrumbSep", "aria-hidden": "true" }, "/"),
              React.createElement("span", { className: "categoryBreadcrumbCurrent" }, title)
            ),
            React.createElement(
              "div",
              { className: "categoryProductsLayout" },
              showSidebar
                ? React.createElement(CategoryFilterSidebar, {
                    options: this.filterOptions,
                    selections: this.filterSelections,
                    onToggle: this.toggleFilter,
                    onClear: this.clearFilters,
                  })
                : null,
              React.createElement(
                "div",
                { className: "categoryProductsContent" },
                React.createElement(
                  "div",
                  { className: "categoryProductsToolbar" },
                  React.createElement(
                    "div",
                    { className: "categoryProductsToolbarText" },
                    React.createElement("h1", { className: "categoryProductsPageTitle" }, title),
                    React.createElement(
                      "p",
                      { className: "categoryProductsPageSubtitle" },
                      this.loading
                        ? "Loading products..."
                        : `${filtered.length} product${filtered.length === 1 ? "" : "s"} available`
                    )
                  ),
                  !this.loading && this.products.length > 0
                    ? React.createElement(
                        "span",
                        { className: "categoryProductsBadge" },
                        "FreshMart Pick"
                      )
                    : null
                ),
                this.renderActiveFilterChips(),
                React.createElement(
                  "div",
                  { className: "categoryProductsPanel" },
                  this.renderProductArea()
                )
              )
            )
          ),
          React.createElement(LandingFooter, { key: "footer" })
        )
      ),
      className: ui.join("CategoryProductsPage ", this.props.className ?? ""),
      ...copyBaseUIProps(this.props),
    });
  }

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function CategoryProductsPage(props: CategoryProductsPageProps) {
  return React.createElement(_CategoryProductsPageState, props);
}
