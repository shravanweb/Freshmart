import React, { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import PageNavigator from "../classes/PageNavigator";
import { BuildContext } from "../classes/BuildContext";
import User from "../models/User";
import CustomerSession from "../utils/CustomerSession";
import CartSession, { CartItem } from "../utils/CartSession";
import { PublicProductRecord } from "../utils/LandingCatalogApi";
import CustomerNavigation from "../utils/CustomerNavigation";
import CustomerSearchNavigation from "../utils/CustomerSearchNavigation";
import CustomerCheckoutApi, { PaymentMethod } from "../utils/CustomerCheckoutApi";
import CustomerProfileStorage from "../utils/CustomerProfileStorage";
import CustomerProfileNavigation from "../utils/CustomerProfileNavigation";
import { CustomerAccountTab } from "./CustomerAccountPage";
import { LandingSiteHeader } from "./LandingPage";

const DELIVERY_LOCATION_KEY = "freshmart_delivery_location";
const DELIVERY_ADDRESS_KEY = "freshmart_delivery_address";
const DELIVERY_PHONE_KEY = "freshmart_delivery_phone";
const DELIVERY_CITIES = ["Hyderabad", "Bangalore", "Chennai"];

interface OrderConfirmation {
  orderNumber: string;
  totalAmount: number;
  deliveryFee: number;
  itemCount: number;
  deliveryCity: string;
  deliveryAddress: string;
  deliveryPhone: string;
  paymentMethod: PaymentMethod;
}

export interface CustomerCartPageProps extends BaseUIProps {
  key?: string;
  user?: User;
}

function formatPrice(value: number): string {
  if (!Number.isFinite(value) || value <= 0) {
    return "₹0";
  }
  return "₹" + (value % 1 === 0 ? value.toFixed(0) : value.toFixed(2));
}

function loadSavedLocation(): string {
  if (typeof window === "undefined") {
    return "Hyderabad";
  }
  const saved = localStorage.getItem(DELIVERY_LOCATION_KEY);
  return saved && saved.length > 0 ? saved : "Hyderabad";
}

function loadSavedAddress(): string {
  if (typeof window === "undefined") {
    return "";
  }
  return localStorage.getItem(DELIVERY_ADDRESS_KEY) ?? "";
}

function loadSavedPhone(): string {
  if (typeof window === "undefined") {
    return "";
  }
  return localStorage.getItem(DELIVERY_PHONE_KEY) ?? "";
}

function cartItemAsProduct(item: CartItem): PublicProductRecord {
  return {
    id: item.productId,
    sku: "",
    name: item.name,
    description: "",
    barcode: "",
    sellingPrice: item.sellingPrice,
    purchasePrice: 0,
    status: "Active",
    imageUrl: item.imageUrl,
    quantityAvailable: item.quantityAvailable,
  };
}

class _CustomerCartPageState extends ObservableComponent<CustomerCartPageProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public cartItems: CartItem[] = [];
  public deliveryCity = "Hyderabad";
  public deliveryAddress = "";
  public deliveryPhone = "";
  public checkoutMessage = "";
  public paymentMethod: PaymentMethod = "cod";
  public checkoutLoading = false;
  public orderConfirmation: OrderConfirmation | null = null;
  private cartUnsub: (() => void) | null = null;

  public constructor(props: CustomerCartPageProps) {
    super(props);
    this.initState();
  }

  public initState() {
    super.initState();
    this.enableBuild = true;
    this.on(
      [
        "cartItems",
        "deliveryCity",
        "deliveryAddress",
        "deliveryPhone",
        "checkoutMessage",
        "paymentMethod",
        "checkoutLoading",
        "orderConfirmation",
      ],
      this.rebuild
    );
  }

  public componentDidMount(): void {
    super.componentDidMount();
    if (!CustomerSession.isActive()) {
      this.navigator.pushLoginPage({ customerMode: true, target: "main" });
      return;
    }
    this.deliveryCity = loadSavedLocation();
    this.deliveryAddress = loadSavedAddress();
    this.deliveryPhone = loadSavedPhone();
    this.syncCart();
    this.cartUnsub = CartSession.onChange(() => this.syncCart());
    this.fire("deliveryCity", this);
    this.fire("deliveryAddress", this);
    this.fire("deliveryPhone", this);
  }

  public componentWillUnmount(): void {
    this.cartUnsub?.();
    this.cartUnsub = null;
    super.componentWillUnmount();
  }

  private syncCart(): void {
    this.cartItems = CartSession.getItems();
    this.fire("cartItems", this);
  }

  private saveDeliveryDetails(): void {
    if (typeof window === "undefined") {
      return;
    }
    localStorage.setItem(DELIVERY_LOCATION_KEY, this.deliveryCity);
    localStorage.setItem(DELIVERY_ADDRESS_KEY, this.deliveryAddress.trim());
    localStorage.setItem(DELIVERY_PHONE_KEY, this.deliveryPhone.trim());
  }

  public setDeliveryCity = (value: string): void => {
    this.deliveryCity = value;
    this.saveDeliveryDetails();
    this.fire("deliveryCity", this);
  };

  public setDeliveryAddress = (event: React.ChangeEvent<HTMLTextAreaElement>): void => {
    this.deliveryAddress = event.target.value;
    this.saveDeliveryDetails();
    this.fire("deliveryAddress", this);
  };

  public setDeliveryPhone = (event: React.ChangeEvent<HTMLInputElement>): void => {
    this.deliveryPhone = event.target.value;
    this.saveDeliveryDetails();
    this.fire("deliveryPhone", this);
  };

  public adjustItemQty = (item: CartItem, delta: number): void => {
    CartSession.adjustQuantity(cartItemAsProduct(item), delta);
  };

  public removeItem = (item: CartItem): void => {
    CartSession.setQuantity(item.productId, 0);
  };

  public goShop = (): void => {
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
    // already on cart
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

  public setPaymentMethod = (method: PaymentMethod): void => {
    this.paymentMethod = method;
    this.fire("paymentMethod", this);
  };

  public proceedCheckout = (): void => {
    if (this.checkoutLoading) {
      return;
    }
    if (this.cartItems.length === 0) {
      this.checkoutMessage = "Your cart is empty.";
      this.fire("checkoutMessage", this);
      return;
    }
    if (!this.deliveryAddress.trim()) {
      this.checkoutMessage = "Please enter your delivery address.";
      this.fire("checkoutMessage", this);
      return;
    }
    if (!this.deliveryPhone.trim()) {
      this.checkoutMessage = "Please enter your phone number.";
      this.fire("checkoutMessage", this);
      return;
    }
    if (this.paymentMethod === "online") {
      this.checkoutMessage = "Online payment is coming soon. Please choose Cash on Delivery.";
      this.fire("checkoutMessage", this);
      return;
    }

    const session = CustomerSession.load();
    const itemCount = CartSession.getTotalCount();
    const orderTotal = this.total();
    const orderDeliveryFee = this.deliveryFee();
    const orderCity = this.deliveryCity;
    const orderAddress = this.deliveryAddress.trim();
    const orderPhone = this.deliveryPhone.trim();
    this.checkoutLoading = true;
    this.checkoutMessage = "";
    this.fire("checkoutLoading", this);
    this.fire("checkoutMessage", this);

    void CustomerCheckoutApi.placeOrder({
      paymentMethod: this.paymentMethod,
      customerName: session?.displayName || session?.email || "Customer",
      customerPhone: this.deliveryPhone.trim(),
      deliveryCity: this.deliveryCity,
      deliveryAddress: this.deliveryAddress.trim(),
      deliveryFee: this.deliveryFee(),
      items: this.cartItems,
    })
      .then((result) => {
        if (!result.success) {
          const errors = result.errors ?? [];
          this.checkoutMessage =
            errors.length > 0 ? errors.join(" ") : "Checkout failed. Please try again.";
          return;
        }

        this.orderConfirmation = {
          orderNumber: result.orderNumber ?? "",
          totalAmount: result.totalAmount ?? orderTotal,
          deliveryFee: result.deliveryFee ?? orderDeliveryFee,
          itemCount,
          deliveryCity: orderCity,
          deliveryAddress: orderAddress,
          deliveryPhone: orderPhone,
          paymentMethod: this.paymentMethod,
        };
        CustomerProfileStorage.savePhone(orderPhone);
        if (session?.email) {
          CustomerProfileStorage.savePhoneForEmail(session.email, orderPhone);
        }
        this.checkoutMessage = "";
        CartSession.clear();
        this.syncCart();
        this.fire("orderConfirmation", this);
      })
      .catch(() => {
        this.checkoutMessage = "Checkout failed. Please check your connection and try again.";
      })
      .finally(() => {
        this.checkoutLoading = false;
        this.fire("checkoutLoading", this);
        this.fire("checkoutMessage", this);
      });
  };

  private subtotal(): number {
    return this.cartItems.reduce(
      (sum, item) => sum + item.sellingPrice * item.quantity,
      0
    );
  }

  private deliveryFee(): number {
    return this.subtotal() >= 499 ? 0 : 40;
  }

  private total(): number {
    return this.subtotal() + this.deliveryFee();
  }

  private renderQtyStepper(item: CartItem): ReactNode {
    const product = cartItemAsProduct(item);
    const atMax =
      CartSession.hasStockData(product) &&
      item.quantity >= CartSession.availableStock(product);

    return React.createElement(
      "div",
      { className: "customerCartQtyStepper" },
      React.createElement(
        "button",
        {
          className: "customerCartQtyBtn",
          type: "button",
          onClick: () => this.adjustItemQty(item, -1),
          "aria-label": "Decrease quantity",
        },
        "−"
      ),
      React.createElement("span", { className: "customerCartQtyValue" }, String(item.quantity)),
      React.createElement(
        "button",
        {
          className: "customerCartQtyBtn",
          type: "button",
          disabled: atMax,
          onClick: () => this.adjustItemQty(item, 1),
          "aria-label": "Increase quantity",
        },
        "+"
      )
    );
  }

  private renderCartItem(item: CartItem): ReactNode {
    const lineTotal = item.sellingPrice * item.quantity;
    return React.createElement(
      "article",
      { className: "customerCartItem", key: "cart-item-" + item.productId },
      React.createElement(
        "div",
        { className: "customerCartItemImageWrap" },
        item.imageUrl
          ? React.createElement("img", {
              src: item.imageUrl,
              alt: item.name,
              className: "customerCartItemImage",
            })
          : React.createElement("div", { className: "customerCartItemImageFallback" }, "🛒")
      ),
      React.createElement(
        "div",
        { className: "customerCartItemBody" },
        React.createElement("h3", { className: "customerCartItemName" }, item.name),
        React.createElement(
          "p",
          { className: "customerCartItemUnitPrice" },
          formatPrice(item.sellingPrice) + " each"
        ),
        React.createElement(
          "div",
          { className: "customerCartItemActions" },
          this.renderQtyStepper(item),
          React.createElement(
            "button",
            {
              className: "customerCartRemoveBtn",
              type: "button",
              onClick: () => this.removeItem(item),
            },
            "Remove"
          )
        )
      ),
      React.createElement(
        "div",
        { className: "customerCartItemTotal" },
        formatPrice(lineTotal)
      )
    );
  }

  private renderOrderConfirmation(): ReactNode {
    const order = this.orderConfirmation;
    if (!order) {
      return null;
    }

    const paymentLabel =
      order.paymentMethod === "cod" ? "Cash on Delivery" : "Pay Online";

    return React.createElement(
      "div",
      { className: "customerOrderConfirm" },
      React.createElement("div", { className: "customerOrderConfirmIcon" }, "✓"),
      React.createElement("h2", { className: "customerOrderConfirmTitle" }, "Order placed successfully!"),
      React.createElement(
        "p",
        { className: "customerOrderConfirmSubtitle" },
        "Thank you! Your groceries will be delivered soon."
      ),
      React.createElement(
        "div",
        { className: "customerOrderConfirmCard" },
        React.createElement(
          "div",
          { className: "customerOrderConfirmRow" },
          React.createElement("span", null, "Order ID"),
          React.createElement("strong", null, order.orderNumber || "—")
        ),
        React.createElement(
          "div",
          { className: "customerOrderConfirmRow" },
          React.createElement("span", null, "Items"),
          React.createElement("strong", null, String(order.itemCount))
        ),
        React.createElement(
          "div",
          { className: "customerOrderConfirmRow" },
          React.createElement("span", null, "Payment"),
          React.createElement("strong", null, paymentLabel)
        ),
        order.paymentMethod === "cod"
          ? React.createElement(
              "div",
              { className: "customerOrderConfirmCodNote" },
              "Pay ",
              React.createElement("strong", null, formatPrice(order.totalAmount)),
              " in cash when your order arrives."
            )
          : null,
        React.createElement(
          "div",
          { className: "customerOrderConfirmRow customerOrderConfirmTotal" },
          React.createElement("span", null, "Order total"),
          React.createElement("strong", null, formatPrice(order.totalAmount))
        ),
        React.createElement(
          "div",
          { className: "customerOrderConfirmDelivery" },
          React.createElement("span", { className: "customerOrderConfirmDeliveryLabel" }, "Delivering to"),
          React.createElement(
            "p",
            { className: "customerOrderConfirmDeliveryText" },
            order.deliveryAddress + ", " + order.deliveryCity
          ),
          React.createElement(
            "p",
            { className: "customerOrderConfirmDeliveryPhone" },
            "Phone: " + order.deliveryPhone
          )
        )
      ),
      React.createElement(
        "button",
        {
          className: "customerCartShopBtn customerOrderConfirmShopBtn",
          type: "button",
          onClick: () => this.goShop(),
        },
        "Continue Shopping"
      )
    );
  }

  private renderEmptyCart(): ReactNode {
    return React.createElement(
      "div",
      { className: "customerCartEmpty" },
      React.createElement("div", { className: "customerCartEmptyIcon" }, "🛒"),
      React.createElement("h2", { className: "customerCartEmptyTitle" }, "Your cart is empty"),
      React.createElement(
        "p",
        { className: "customerCartEmptyText" },
        "Browse products and add items to get started."
      ),
      React.createElement(
        "button",
        {
          className: "customerCartShopBtn",
          type: "button",
          onClick: () => this.goShop(),
        },
        "Continue Shopping"
      )
    );
  }

  private renderDeliveryPanel(): ReactNode {
    const session = CustomerSession.load();
    return React.createElement(
      "section",
      { className: "customerCartPanel customerCartDeliveryPanel" },
      React.createElement("h2", { className: "customerCartPanelTitle" }, "Delivery details"),
      React.createElement(
        "p",
        { className: "customerCartPanelHint" },
        "Order will be delivered to this address."
      ),
      React.createElement(
        "label",
        { className: "customerCartField" },
        React.createElement("span", { className: "customerCartFieldLabel" }, "Customer"),
        React.createElement(
          "span",
          { className: "customerCartFieldValue" },
          session?.displayName || session?.email || "Customer"
        )
      ),
      React.createElement(
        "label",
        { className: "customerCartField" },
        React.createElement("span", { className: "customerCartFieldLabel" }, "City"),
        React.createElement(
          "select",
          {
            className: "customerCartSelect",
            value: this.deliveryCity,
            onChange: (event: React.ChangeEvent<HTMLSelectElement>) =>
              this.setDeliveryCity(event.target.value),
          },
          DELIVERY_CITIES.map((city) =>
            React.createElement("option", { key: city, value: city }, city)
          )
        )
      ),
      React.createElement(
        "label",
        { className: "customerCartField customerCartFieldFull" },
        React.createElement("span", { className: "customerCartFieldLabel" }, "Delivery address"),
        React.createElement("textarea", {
          className: "customerCartTextarea",
          rows: 3,
          placeholder: "House / Flat no., Street, Landmark",
          value: this.deliveryAddress,
          onChange: this.setDeliveryAddress,
        })
      ),
      React.createElement(
        "label",
        { className: "customerCartField customerCartFieldFull" },
        React.createElement("span", { className: "customerCartFieldLabel" }, "Phone number"),
        React.createElement("input", {
          className: "customerCartInput",
          type: "tel",
          placeholder: "10-digit mobile number",
          value: this.deliveryPhone,
          onChange: this.setDeliveryPhone,
        })
      )
    );
  }

  private renderPaymentMethods(): ReactNode {
    const codSelected = this.paymentMethod === "cod";
    const onlineSelected = this.paymentMethod === "online";

    return React.createElement(
      "div",
      { className: "customerCartPaymentSection" },
      React.createElement("h3", { className: "customerCartPaymentTitle" }, "Payment method"),
      React.createElement(
        "div",
        { className: "customerCartPaymentOptions", role: "radiogroup", "aria-label": "Payment method" },
        React.createElement(
          "button",
          {
            className:
              "customerCartPaymentOption" + (codSelected ? " customerCartPaymentOptionActive" : ""),
            type: "button",
            role: "radio",
            "aria-checked": codSelected,
            onClick: () => this.setPaymentMethod("cod"),
          },
          React.createElement("span", { className: "customerCartPaymentOptionIcon" }, "💵"),
          React.createElement(
            "span",
            { className: "customerCartPaymentOptionBody" },
            React.createElement("span", { className: "customerCartPaymentOptionLabel" }, "Cash on Delivery"),
            React.createElement(
              "span",
              { className: "customerCartPaymentOptionHint" },
              "Pay when your order arrives"
            )
          )
        ),
        React.createElement(
          "button",
          {
            className:
              "customerCartPaymentOption customerCartPaymentOptionDisabled" +
              (onlineSelected ? " customerCartPaymentOptionActive" : ""),
            type: "button",
            role: "radio",
            "aria-checked": onlineSelected,
            disabled: true,
            title: "Coming soon",
          },
          React.createElement("span", { className: "customerCartPaymentOptionIcon" }, "💳"),
          React.createElement(
            "span",
            { className: "customerCartPaymentOptionBody" },
            React.createElement("span", { className: "customerCartPaymentOptionLabel" }, "Pay Online"),
            React.createElement(
              "span",
              { className: "customerCartPaymentOptionHint" },
              "UPI / Card — coming soon"
            )
          )
        )
      )
    );
  }

  private renderSummaryPanel(): ReactNode {
    const itemCount = CartSession.getTotalCount();
    const subtotal = this.subtotal();
    const deliveryFee = this.deliveryFee();

    return React.createElement(
      "section",
      { className: "customerCartPanel customerCartSummaryPanel" },
      React.createElement("h2", { className: "customerCartPanelTitle" }, "Order summary"),
      React.createElement(
        "div",
        { className: "customerCartSummaryRow" },
        React.createElement("span", null, "Items (" + itemCount + ")"),
        React.createElement("span", null, formatPrice(subtotal))
      ),
      React.createElement(
        "div",
        { className: "customerCartSummaryRow" },
        React.createElement("span", null, "Delivery fee"),
        React.createElement(
          "span",
          null,
          deliveryFee === 0 ? "FREE" : formatPrice(deliveryFee)
        )
      ),
      deliveryFee > 0
        ? React.createElement(
            "p",
            { className: "customerCartFreeDeliveryNote" },
            "Free delivery on orders above ₹499"
          )
        : null,
      React.createElement(
        "div",
        { className: "customerCartSummaryRow customerCartSummaryTotal" },
        React.createElement("span", null, "Total"),
        React.createElement("span", null, formatPrice(this.total()))
      ),
      this.renderPaymentMethods(),
      this.checkoutMessage
        ? React.createElement(
            "p",
            { className: "customerCartCheckoutMessage" },
            this.checkoutMessage
          )
        : null,
      React.createElement(
        "button",
        {
          className: "customerCartCheckoutBtn",
          type: "button",
          onClick: () => this.proceedCheckout(),
          disabled: this.cartItems.length === 0 || this.checkoutLoading,
        },
        this.checkoutLoading ? "Placing order..." : "Place Order (COD)"
      ),
      React.createElement(
        "button",
        {
          className: "customerCartContinueBtn",
          type: "button",
          onClick: () => this.goShop(),
        },
        "Continue Shopping"
      )
    );
  }

  public render(): ReactNode {
    const itemCount = CartSession.getTotalCount();

    return ui.Container({
      child: React.createElement(
        "div",
        { className: "landingPageShell customerCartPageRoot" },
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
          { className: "landingScroll customerCartPageScroll", key: "scroll" },
          React.createElement(
            "div",
            { className: "landingContainer customerCartPageMain" },
            React.createElement(
              "button",
              {
                className: "productDetailBackBtn",
                type: "button",
                onClick: () => this.goShop(),
              },
              "← Continue shopping"
            ),
            React.createElement(
              "header",
              { className: "customerCartPageHead" },
              React.createElement(
                "h1",
                { className: "customerCartPageTitle" },
                this.orderConfirmation ? "Order confirmed" : "My Cart"
              ),
              this.orderConfirmation
                ? React.createElement(
                    "p",
                    { className: "customerCartPageSubtitle" },
                    "Your order has been placed"
                  )
                : React.createElement(
                    "p",
                    { className: "customerCartPageSubtitle" },
                    itemCount === 1 ? "1 item" : itemCount + " items"
                  )
            ),
            this.orderConfirmation
              ? this.renderOrderConfirmation()
              : this.cartItems.length === 0
              ? this.renderEmptyCart()
              : React.createElement(
                  "div",
                  { className: "customerCartLayout" },
                  React.createElement(
                    "div",
                    { className: "customerCartItemsCol" },
                    this.cartItems.map((item) => this.renderCartItem(item))
                  ),
                  React.createElement(
                    "div",
                    { className: "customerCartSideCol" },
                    this.renderDeliveryPanel(),
                    this.renderSummaryPanel()
                  )
                )
          )
        )
      ),
      className: ui.join("CustomerCartPage ", this.props.className ?? ""),
      ...copyBaseUIProps(this.props),
    });
  }

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function CustomerCartPage(props: CustomerCartPageProps) {
  return React.createElement(_CustomerCartPageState, props);
}
