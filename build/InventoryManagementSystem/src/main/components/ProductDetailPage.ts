import React, { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import PageNavigator from "../classes/PageNavigator";
import { BuildContext } from "../classes/BuildContext";
import User from "../models/User";
import LandingCatalogApi, { PublicProductRecord } from "../utils/LandingCatalogApi";
import CustomerSession from "../utils/CustomerSession";
import CartSession from "../utils/CartSession";
import CustomerNavigation from "../utils/CustomerNavigation";
import CustomerSearchNavigation from "../utils/CustomerSearchNavigation";
import CustomerProfileNavigation from "../utils/CustomerProfileNavigation";
import { CustomerAccountTab } from "./CustomerAccountPage";
import ProductReviewStore from "../utils/ProductReviewStore";
import { LandingSiteHeader } from "./LandingPage";
import LandingFooter from "./LandingFooter";
import ProductReviewsPanel from "./ProductReviewsPanel";
import ProductRelatedCarousel from "./ProductRelatedCarousel";

const CAT_IMG = "/images/categories/";
const DEFAULT_CATEGORY_IMAGE = CAT_IMG + "default.jpg";

export interface ProductDetailPageProps extends BaseUIProps {
  key?: string;
  user?: User;
  productId?: number;
}

function formatPrice(value: number): string {
  if (!Number.isFinite(value) || value <= 0) {
    return "—";
  }
  return "₹" + (value % 1 === 0 ? value.toFixed(0) : value.toFixed(2));
}

function productImages(product: PublicProductRecord): string[] {
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls;
  }
  if (product.imageUrl) {
    return [product.imageUrl];
  }
  if (product.categoryCode) {
    const slug = product.categoryCode.trim().toLowerCase().replace(/[^a-z0-9]+/g, "-");
    return [CAT_IMG + slug + ".jpg"];
  }
  return [DEFAULT_CATEGORY_IMAGE];
}

function discountPercent(sellingPrice: number, purchasePrice: number): number {
  if (purchasePrice <= sellingPrice || purchasePrice <= 0) {
    return 0;
  }
  return Math.round(((purchasePrice - sellingPrice) / purchasePrice) * 100);
}

function renderInlineStars(rating: number, keyPrefix: string): ReactNode {
  const stars: ReactNode[] = [];

  for (let index = 1; index <= 5; index++) {
    stars.push(
      React.createElement(
        "span",
        {
          className:
            "productDetailInlineStar" +
            (rating >= index - 0.25 ? " productDetailInlineStarFilled" : ""),
          key: keyPrefix + "-inline-star-" + index,
        },
        "★"
      )
    );
  }

  return React.createElement("span", { className: "productDetailInlineStars" }, stars);
}

class _ProductDetailPageState extends ObservableComponent<ProductDetailPageProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public product: PublicProductRecord | null = null;
  public loading = true;
  public loadError = "";
  public selectedImageIndex = 0;
  public selectedQty = 1;
  public cartQty = 0;
  public cartMessage = "";
  public customerLoggedIn = false;
  public relatedProducts: PublicProductRecord[] = [];
  public relatedLoading = false;
  private sessionUnsub: (() => void) | null = null;
  private cartUnsub: (() => void) | null = null;

  public constructor(props: ProductDetailPageProps) {
    super(props);
    this.initState();
  }

  public initState() {
    super.initState();
    this.enableBuild = true;
    this.on(
      [
        "product",
        "loading",
        "loadError",
        "selectedImageIndex",
        "selectedQty",
        "cartQty",
        "cartMessage",
        "customerLoggedIn",
        "relatedProducts",
        "relatedLoading",
      ],
      this.rebuild
    );
  }

  public componentDidMount(): void {
    super.componentDidMount();
    this.syncCustomerState();
    this.sessionUnsub = CustomerSession.onChange(() => this.syncCustomerState());
    this.cartUnsub = CartSession.onChange(() => this.syncCartState());
    this.loadProduct();
  }

  public componentWillUnmount(): void {
    this.sessionUnsub?.();
    this.sessionUnsub = null;
    this.cartUnsub?.();
    this.cartUnsub = null;
    super.componentWillUnmount();
  }

  public componentDidUpdate(prevProps: ProductDetailPageProps): void {
    if (prevProps.productId !== this.props.productId) {
      this.selectedImageIndex = 0;
      this.fire("selectedImageIndex", this);
      this.loadProduct();
    }
  }

  private get resolvedProductId(): number {
    return Number(this.props.productId ?? 0);
  }

  public loadProduct = async (): Promise<void> => {
    const productId = this.resolvedProductId;
    if (productId <= 0) {
      this.loading = false;
      this.loadError = "Product not found.";
      this.product = null;
      this.fire("loading", this);
      this.fire("loadError", this);
      this.fire("product", this);
      return;
    }

    this.loading = true;
    this.loadError = "";
    this.fire("loading", this);
    this.fire("loadError", this);

    try {
      const item = await LandingCatalogApi.getPublicProductById(productId);
      this.product = item;
      this.loadError = item ? "" : "Product not found.";
      this.selectedImageIndex = 0;
      this.selectedQty = 1;
      this.syncCartState();
      this.fire("selectedImageIndex", this);
      this.fire("selectedQty", this);
      void this.loadRelatedProducts(item);
    } catch {
      this.product = null;
      this.loadError = "Unable to load product details. Please try again.";
      this.relatedProducts = [];
      this.fire("relatedProducts", this);
    } finally {
      this.loading = false;
      this.fire("loading", this);
      this.fire("product", this);
      this.fire("loadError", this);
    }
  };

  private loadRelatedProducts = async (product: PublicProductRecord | null): Promise<void> => {
    if (!product) {
      this.relatedProducts = [];
      this.relatedLoading = false;
      this.fire("relatedProducts", this);
      this.fire("relatedLoading", this);
      return;
    }

    this.relatedLoading = true;
    this.fire("relatedLoading", this);

    try {
      let items: PublicProductRecord[] = [];

      if (product.categoryId) {
        items = await LandingCatalogApi.getPublicProducts(product.categoryId);
      } else {
        items = await LandingCatalogApi.getPublicProducts();
      }

      let related = items.filter((item) => item.id !== product.id);

      if (related.length < 4) {
        const all = await LandingCatalogApi.getPublicProducts();
        const extras = all.filter(
          (item) =>
            item.id !== product.id && !related.some((existing) => existing.id === item.id)
        );
        related = [...related, ...extras];
      }

      this.relatedProducts = related.slice(0, 12);
    } catch {
      this.relatedProducts = [];
    } finally {
      this.relatedLoading = false;
      this.fire("relatedLoading", this);
      this.fire("relatedProducts", this);
    }
  };

  public openRelatedProduct = (product: PublicProductRecord): void => {
    this.navigator.pushProductDetailPage({
      user: this.props.user,
      productId: product.id,
      target: "main",
      replace: false,
    });
  };

  public setSelectedImageIndex = (index: number): void => {
    if (this.selectedImageIndex === index) {
      return;
    }
    this.selectedImageIndex = index;
    this.fire("selectedImageIndex", this);
  };

  public goBack = (): void => {
    this.navigator.pushLandingPage({
      user: this.props.user,
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

  private syncCustomerState(): void {
    const loggedIn = CustomerSession.isActive();
    const loginChanged = this.customerLoggedIn !== loggedIn;
    if (loginChanged) {
      this.customerLoggedIn = loggedIn;
      this.fire("customerLoggedIn", this);
      if (loggedIn) {
        this.loadProduct();
      }
    }
    this.syncCartState();
  }

  private syncCartState(): void {
    const productId = this.product?.id ?? 0;
    const nextQty = productId > 0 ? CartSession.getQuantity(productId) : 0;
    if (this.cartQty !== nextQty) {
      this.cartQty = nextQty;
      this.fire("cartQty", this);
    }
    this.clampSelectedQty();
  }

  private clampSelectedQty(): void {
    if (!this.product) {
      return;
    }
    const maxSelectable = CartSession.maxAddable(this.product);
    let next = this.selectedQty;
    if (maxSelectable <= 0) {
      next = 0;
    } else if (next <= 0) {
      next = 1;
    } else if (next > maxSelectable) {
      next = maxSelectable;
    }
    if (this.selectedQty !== next) {
      this.selectedQty = next;
      this.fire("selectedQty", this);
    }
  }

  private setCartMessage(message: string): void {
    this.cartMessage = message;
    this.fire("cartMessage", this);
    if (message && typeof window !== "undefined") {
      window.setTimeout(() => {
        if (this.cartMessage === message) {
          this.cartMessage = "";
          this.fire("cartMessage", this);
        }
      }, 2500);
    }
  }

  public changeSelectedQty = (delta: number): void => {
    if (!this.product) {
      return;
    }
    const maxSelectable = CartSession.maxAddable(this.product);
    if (maxSelectable <= 0) {
      this.setCartMessage("Out of stock");
      return;
    }
    const next = Math.min(maxSelectable, Math.max(1, this.selectedQty + delta));
    if (next !== this.selectedQty) {
      this.selectedQty = next;
      this.fire("selectedQty", this);
    }
  };

  public addToCart = (): void => {
    if (!this.product) {
      return;
    }
    if (!CustomerSession.isActive()) {
      this.goLogin();
      return;
    }
    const inCart = CartSession.getQuantity(this.product.id);
    if (inCart > 0) {
      this.setCartMessage(inCart + " already in cart. Adjust quantity below.");
      return;
    }
    const maxSelectable = CartSession.maxAddable(this.product);
    if (maxSelectable <= 0) {
      this.setCartMessage("Only " + CartSession.availableStock(this.product) + " available");
      return;
    }
    const qty = Math.min(this.selectedQty > 0 ? this.selectedQty : 1, maxSelectable);
    const result = CartSession.addProduct(this.product, qty);
    if (!result.success) {
      this.setCartMessage(result.message ?? "Unable to add to cart");
      return;
    }
    this.setCartMessage("Added to cart");
    this.syncCartState();
    this.selectedQty = CartSession.maxAddable(this.product) > 0 ? 1 : 0;
    this.fire("selectedQty", this);
  };

  public buyNow = (): void => {
    if (!this.product) {
      return;
    }
    if (!CustomerSession.isActive()) {
      this.goLogin();
      return;
    }
    const inCart = CartSession.getQuantity(this.product.id);
    if (inCart > 0) {
      this.goCart();
      return;
    }
    const maxSelectable = CartSession.maxAddable(this.product);
    if (maxSelectable <= 0) {
      this.setCartMessage("Only " + CartSession.availableStock(this.product) + " available");
      return;
    }
    const qty = Math.min(this.selectedQty > 0 ? this.selectedQty : 1, maxSelectable);
    const result = CartSession.addProduct(this.product, qty);
    if (!result.success) {
      this.setCartMessage(result.message ?? "Unable to add to cart");
      return;
    }
    this.syncCartState();
    this.goCart();
  };

  public adjustCartQty = (delta: number): void => {
    if (!this.product) {
      return;
    }
    if (!CustomerSession.isActive()) {
      this.goLogin();
      return;
    }
    const result = CartSession.adjustQuantity(this.product, delta);
    if (!result.success && result.message) {
      this.setCartMessage(result.message);
    }
    this.syncCartState();
  };

  private renderQtyStepper(
    value: number,
    onMinus: () => void,
    onPlus: () => void,
    disableMinus: boolean,
    disablePlus: boolean
  ): ReactNode {
    return React.createElement(
      "div",
      { className: "productDetailQtyStepper" },
      React.createElement(
        "button",
        {
          className: "productDetailQtyBtn",
          type: "button",
          onClick: onMinus,
          disabled: disableMinus,
          "aria-label": "Decrease quantity",
        },
        "−"
      ),
      React.createElement("span", { className: "productDetailQtyValue" }, String(value)),
      React.createElement(
        "button",
        {
          className: "productDetailQtyBtn",
          type: "button",
          onClick: onPlus,
          disabled: disablePlus,
          "aria-label": "Increase quantity",
        },
        "+"
      )
    );
  }

  private renderSpecRow(label: string, value: string): ReactNode {
    return React.createElement(
      "div",
      { className: "productDetailSpecRow", key: label },
      React.createElement("span", { className: "productDetailSpecLabel" }, label),
      React.createElement("span", { className: "productDetailSpecValue" }, value)
    );
  }

  private renderDetail(product: PublicProductRecord): ReactNode {
    const images = productImages(product);
    const activeImage = images[this.selectedImageIndex] ?? images[0];
    const showOldPrice =
      product.purchasePrice > 0 && product.purchasePrice > product.sellingPrice;
    const discount = discountPercent(product.sellingPrice, product.purchasePrice);
    const stockAvailable = CartSession.availableStock(product);
    const maxSelectable = CartSession.maxAddable(product);
    const outOfStock = CartSession.isOutOfStock(product);
    const stockLabel = outOfStock
      ? "Out of stock"
      : CartSession.hasStockData(product)
        ? stockAvailable + " available"
        : "In stock";

    return React.createElement(
      "article",
      { className: "productDetailProLayout" },
      React.createElement(
        "nav",
        { className: "productDetailBreadcrumb", "aria-label": "Breadcrumb" },
        React.createElement(
          "button",
          { type: "button", className: "productDetailCrumbLink", onClick: () => this.goBack() },
          "Home"
        ),
        React.createElement("span", { className: "productDetailCrumbSep" }, "/"),
        product.categoryName
          ? React.createElement("span", { className: "productDetailCrumbText" }, product.categoryName)
          : null,
        React.createElement("span", { className: "productDetailCrumbSep" }, "/"),
        React.createElement("span", { className: "productDetailCrumbCurrent" }, product.name)
      ),
      React.createElement(
        "div",
        { className: "productDetailProGrid" },
        React.createElement(
          "div",
          { className: "productDetailGallery" },
          React.createElement(
            "div",
            { className: "productDetailGalleryMain" },
            discount > 0
              ? React.createElement(
                  "span",
                  { className: "productDetailDiscountBadge" },
                  discount + "% OFF"
                )
              : null,
            React.createElement("img", {
              src: activeImage,
              alt: product.name,
              className: "productDetailGalleryImage",
            })
          ),
          images.length > 1
            ? React.createElement(
                "div",
                { className: "productDetailThumbRow" },
                images.map((imageUrl, index) =>
                  React.createElement(
                    "button",
                    {
                      type: "button",
                      className:
                        "productDetailThumbBtn" +
                        (index === this.selectedImageIndex ? " productDetailThumbBtnActive" : ""),
                      key: "thumb-" + index,
                      onClick: () => this.setSelectedImageIndex(index),
                      "aria-label": "View image " + (index + 1),
                    },
                    React.createElement("img", {
                      src: imageUrl,
                      alt: "",
                      className: "productDetailThumbImage",
                    })
                  )
                )
              )
            : null
        ),
        React.createElement(
          "div",
          { className: "productDetailPurchasePanel" },
          product.categoryName
            ? React.createElement(
                "span",
                { className: "productDetailCategoryPill" },
                product.categoryName
              )
            : null,
          React.createElement("h1", { className: "productDetailProTitle" }, product.name),
          React.createElement(
            "div",
            { className: "productDetailRatingStrip" },
            React.createElement(
              "span",
              { className: "productDetailRatingStripScore" },
              String(ProductReviewStore.getAverageRating(product.id))
            ),
            renderInlineStars(ProductReviewStore.getAverageRating(product.id), "title"),
            React.createElement(
              "button",
              {
                type: "button",
                className: "productDetailRatingStripLink",
                onClick: () => {
                  document
                    .getElementById("product-reviews")
                    ?.scrollIntoView({ behavior: "smooth", block: "start" });
                },
              },
              ProductReviewStore.getReviews(product.id).length + " ratings & reviews"
            )
          ),
          React.createElement(
            "p",
            { className: "productDetailProSku" },
            "SKU: " + (product.sku || "—")
          ),
          React.createElement(
            "div",
            { className: "productDetailPriceCard" },
            React.createElement(
              "div",
              { className: "productDetailPriceMainRow" },
              React.createElement(
                "span",
                { className: "productDetailProPrice" },
                formatPrice(product.sellingPrice)
              ),
              showOldPrice
                ? React.createElement(
                    "span",
                    { className: "productDetailProMrp" },
                    "MRP " + formatPrice(product.purchasePrice)
                  )
                : null
            ),
            React.createElement(
              "p",
              { className: "productDetailTaxNote" },
              "Inclusive of all taxes"
            )
          ),
          React.createElement(
            "div",
            { className: "productDetailDeliveryCard" },
            React.createElement("span", { className: "productDetailDeliveryIcon" }, "⚡"),
            React.createElement(
              "div",
              { className: "productDetailDeliveryCopy" },
              React.createElement(
                "strong",
                null,
                "Delivery in 15–30 minutes"
              ),
              React.createElement(
                "span",
                null,
                this.customerLoggedIn
                  ? "Fresh quality assured. Delivered to your doorstep."
                  : "Fresh quality assured. Login to place your order."
              )
            )
          ),
          React.createElement(
            "div",
            { className: "productDetailStockRow" },
            React.createElement(
              "span",
              {
                className:
                  "productDetailStockBadge" +
                  (outOfStock ? " productDetailStockBadgeOut" : ""),
              },
              outOfStock ? "Out of stock" : stockLabel
            ),
            this.cartQty > 0
              ? React.createElement(
                  "span",
                  { className: "productDetailInCartNote" },
                  this.cartQty + " in cart"
                )
              : null
          ),
          this.cartMessage
            ? React.createElement(
                "p",
                { className: "productDetailCartMessage" },
                this.cartMessage
              )
            : null,
          React.createElement(
            "div",
            { className: "productDetailActionRow" },
            this.cartQty > 0
              ? this.renderQtyStepper(
                  this.cartQty,
                  () => this.adjustCartQty(-1),
                  () => this.adjustCartQty(1),
                  false,
                  CartSession.hasStockData(product) && this.cartQty >= stockAvailable
                )
              : this.renderQtyStepper(
                  this.selectedQty,
                  () => this.changeSelectedQty(-1),
                  () => this.changeSelectedQty(1),
                  this.selectedQty <= 1,
                  this.selectedQty >= maxSelectable || maxSelectable <= 0
                ),
            React.createElement(
              "button",
              {
                className: "productDetailBtnPrimary",
                type: "button",
                onClick: () => this.addToCart(),
                disabled: outOfStock || this.cartQty > 0,
              },
              outOfStock
                ? "Out of Stock"
                : this.cartQty > 0
                  ? "In Cart"
                  : "Add to Cart"
            ),
            React.createElement(
              "button",
              {
                className: "productDetailBtnSecondary",
                type: "button",
                onClick: () => this.buyNow(),
                disabled: outOfStock,
              },
              this.cartQty > 0 ? "Go to Cart" : "Buy Now"
            )
          ),
          React.createElement(
            "section",
            { className: "productDetailSection" },
            React.createElement("h2", { className: "productDetailSectionTitle" }, "About this product"),
            React.createElement(
              "p",
              { className: "productDetailSectionText" },
              product.description || "Fresh quality product from FreshMart."
            )
          ),
          React.createElement(
            "section",
            { className: "productDetailSection" },
            React.createElement("h2", { className: "productDetailSectionTitle" }, "Product details"),
            React.createElement(
              "div",
              { className: "productDetailSpecTable" },
              this.renderSpecRow("SKU", product.sku || "—"),
              product.barcode ? this.renderSpecRow("Barcode", product.barcode) : null,
              product.uomSymbol ? this.renderSpecRow("Unit", product.uomSymbol) : null,
              product.categoryName
                ? this.renderSpecRow("Category", product.categoryName)
                : null,
              this.renderSpecRow("Status", product.status || "Active")
            )
          )
        )
      )
    );
  }

  public render(): ReactNode {
    return ui.Container({
      child: React.createElement(
        "div",
        { className: "landingPageShell productDetailPageRoot" },
        React.createElement(LandingSiteHeader, {
          user: this.props.user,
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
          { className: "landingScroll productDetailPageScroll", key: "scroll" },
          React.createElement(
            "div",
            { className: "landingContainer productDetailPageMain" },
            React.createElement(
              "button",
              {
                className: "productDetailBackBtn",
                type: "button",
                onClick: () => this.goBack(),
              },
              "← Back to shop"
            ),
            this.loading
              ? React.createElement(
                  "p",
                  { className: "productDetailPageStatus" },
                  "Loading product..."
                )
              : this.loadError
                ? React.createElement(
                    "div",
                    { className: "productDetailPageStatusBlock" },
                    React.createElement("p", { className: "productDetailPageStatus" }, this.loadError),
                    React.createElement(
                      "button",
                      {
                        className: "productDetailBackBtn productDetailBackBtnInline",
                        type: "button",
                        onClick: () => this.goBack(),
                      },
                      "Return to home"
                    )
                  )
                : this.product
                  ? React.createElement(
                      React.Fragment,
                      { key: "product-detail-body" },
                      this.renderDetail(this.product),
                      React.createElement(ProductReviewsPanel, {
                        productId: this.product.id,
                        key: "reviews-" + this.product.id,
                      }),
                      React.createElement(ProductRelatedCarousel, {
                        products: this.relatedProducts,
                        loading: this.relatedLoading,
                        onProductClick: this.openRelatedProduct,
                        key: "related-" + this.product.id,
                      })
                    )
                  : null
          ),
          React.createElement(LandingFooter, { key: "footer" })
        )
      ),
      className: ui.join("ProductDetailPage ", this.props.className ?? ""),
      ...copyBaseUIProps(this.props),
    });
  }

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function ProductDetailPage(props: ProductDetailPageProps) {
  return React.createElement(_ProductDetailPageState, props);
}
