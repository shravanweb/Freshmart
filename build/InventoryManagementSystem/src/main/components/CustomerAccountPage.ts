import React, { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import PageNavigator from "../classes/PageNavigator";
import { BuildContext } from "../classes/BuildContext";
import User from "../models/User";
import CustomerSession from "../utils/CustomerSession";
import CustomerNavigation from "../utils/CustomerNavigation";
import CustomerSearchNavigation from "../utils/CustomerSearchNavigation";
import CustomerOrdersApi, {
  CustomerOrderDetailRecord,
  CustomerOrderRecord,
} from "../utils/CustomerOrdersApi";
import CustomerAddressStorage, { SavedAddress } from "../utils/CustomerAddressStorage";
import CustomerProfileStorage from "../utils/CustomerProfileStorage";
import CustomerProfileApi from "../utils/CustomerProfileApi";
import AppLogout from "../utils/AppLogout";
import { PublicProductRecord } from "../utils/LandingCatalogApi";
import { LandingSiteHeader } from "./LandingPage";

const DELIVERY_LOCATION_KEY = "freshmart_delivery_location";
const DELIVERY_ADDRESS_KEY = "freshmart_delivery_address";
const DELIVERY_PHONE_KEY = "freshmart_delivery_phone";
const DELIVERY_CITIES = ["Hyderabad", "Bangalore", "Chennai"];

export type CustomerAccountTab = "order-history" | "address" | "profile" | "details";

export interface CustomerAccountPageProps extends BaseUIProps {
  key?: string;
  user?: User;
  tab?: CustomerAccountTab;
}

const TAB_LABELS: Record<CustomerAccountTab, string> = {
  "order-history": "Order History",
  address: "Address",
  profile: "Profile",
  details: "Details",
};

const TAB_ICONS: Record<CustomerAccountTab, string> = {
  "order-history": "📋",
  address: "📍",
  profile: "👤",
  details: "ℹ️",
};

function parseTabFromHash(): CustomerAccountTab | null {
  if (typeof window === "undefined") {
    return null;
  }
  const hash = window.location.hash ?? "";
  const qIndex = hash.indexOf("?");
  if (qIndex < 0) {
    return null;
  }
  const tab = new URLSearchParams(hash.substring(qIndex + 1)).get("tab");
  if (
    tab === "order-history" ||
    tab === "address" ||
    tab === "profile" ||
    tab === "details"
  ) {
    return tab;
  }
  return null;
}

function parseDeliveryInfo(customerName: string): { location: string; address: string } {
  const parts = customerName.split("|");
  if (parts.length < 2) {
    return { location: "", address: customerName };
  }
  const right = parts[1].trim();
  const colonIndex = right.indexOf(":");
  if (colonIndex < 0) {
    return { location: right, address: "" };
  }
  return {
    location: right.slice(0, colonIndex).trim(),
    address: right.slice(colonIndex + 1).trim(),
  };
}

function formatPrice(value: number): string {
  if (!Number.isFinite(value) || value <= 0) {
    return "₹0";
  }
  return "₹" + (value % 1 === 0 ? value.toFixed(0) : value.toFixed(2));
}

function formatOrderDate(value: string): string {
  if (!value) {
    return "—";
  }
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) {
    return value;
  }
  return date.toLocaleDateString("en-IN", {
    day: "numeric",
    month: "short",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

function profileInitials(name: string): string {
  const parts = name.trim().split(/\s+/);
  if (parts.length >= 2) {
    return (parts[0][0] + parts[1][0]).toUpperCase();
  }
  return (name || "C").slice(0, 2).toUpperCase();
}

class _CustomerAccountPageState extends ObservableComponent<CustomerAccountPageProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public activeTab: CustomerAccountTab = "order-history";
  public orders: CustomerOrderRecord[] = [];
  public ordersLoading = false;
  public ordersError = "";
  public deliveryCity = "Hyderabad";
  public deliveryAddress = "";
  public deliveryPhone = "";
  public profileName = "";
  public profileEmail = "";
  public profilePhone = "";
  public avatarUrl = "";
  public savedAddresses: SavedAddress[] = [];
  public showAddressForm = false;
  public editingAddressId: string | null = null;
  public formLabel = "Home";
  public addressSavedMessage = "";
  public profileSavedMessage = "";
  public selectedOrderDetail: CustomerOrderDetailRecord | null = null;
  public orderDetailLoading = false;
  public orderDetailError = "";
  private hashChangeHandler: (() => void) | null = null;
  private sessionUnsub: (() => void) | null = null;

  public constructor(props: CustomerAccountPageProps) {
    super(props);
    this.initState();
  }

  public initState() {
    super.initState();
    this.enableBuild = true;
    this.on(
      [
        "activeTab",
        "orders",
        "ordersLoading",
        "ordersError",
        "deliveryCity",
        "deliveryAddress",
        "deliveryPhone",
        "profileName",
        "profileEmail",
        "profilePhone",
        "avatarUrl",
        "savedAddresses",
        "showAddressForm",
        "editingAddressId",
        "formLabel",
        "addressSavedMessage",
        "profileSavedMessage",
        "selectedOrderDetail",
        "orderDetailLoading",
        "orderDetailError",
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

    this.activeTab = this.props.tab ?? "order-history";
    void this.loadProfile().then(() => {
      this.loadAddresses();
      this.fire("activeTab", this);
      if (this.activeTab === "order-history") {
        void this.loadOrders();
      }
    });

    this.hashChangeHandler = () => {
      const tab = parseTabFromHash();
      if (tab && tab !== this.activeTab) {
        this.setTab(tab, false);
      }
    };
    if (typeof window !== "undefined") {
      window.addEventListener("hashchange", this.hashChangeHandler);
    }

    this.sessionUnsub = CustomerSession.onChange(() => {
      if (!CustomerSession.isActive()) {
        AppLogout.navigateToLanding(this.navigator);
      }
    });
  }

  public componentWillUnmount(): void {
    this.sessionUnsub?.();
    this.sessionUnsub = null;
    if (this.hashChangeHandler && typeof window !== "undefined") {
      window.removeEventListener("hashchange", this.hashChangeHandler);
    }
    this.hashChangeHandler = null;
    super.componentWillUnmount();
  }

  public componentDidUpdate(prevProps: CustomerAccountPageProps): void {
    if (prevProps.tab !== this.props.tab && this.props.tab) {
      this.setTab(this.props.tab, false);
    }
  }

  private loadProfile = async (): Promise<void> => {
    const session = CustomerSession.load();
    if (!session) {
      return;
    }

    let displayName = session.displayName || session.email.split("@")[0];
    let phone =
      CustomerProfileStorage.getPhone() ||
      CustomerProfileStorage.getPhoneForEmail(session.email);

    try {
      const remoteProfile = await CustomerProfileApi.getProfile(session.email);
      if (remoteProfile) {
        if (remoteProfile.displayName.trim()) {
          displayName = remoteProfile.displayName.trim();
        }
        if (remoteProfile.phone.trim()) {
          phone = remoteProfile.phone.trim();
        }
        CustomerSession.updateDisplayName(displayName, true);
        if (phone) {
          CustomerProfileStorage.savePhoneForEmail(session.email, phone);
        }
      }
    } catch {
      // Keep local session values when profile API is unavailable.
    }

    if (!phone && typeof window !== "undefined") {
      const legacyPhone = localStorage.getItem(DELIVERY_PHONE_KEY)?.trim() ?? "";
      if (legacyPhone) {
        CustomerProfileStorage.savePhoneForEmail(session.email, legacyPhone);
        phone = legacyPhone;
      }
    }

    this.profileName = displayName;
    this.profileEmail = session.email;
    this.profilePhone = phone;
    this.avatarUrl = CustomerProfileStorage.getAvatarUrl();
    this.fire("profileName", this);
    this.fire("profileEmail", this);
    this.fire("profilePhone", this);
    this.fire("avatarUrl", this);
  };

  private loadAddresses(): void {
    this.savedAddresses = CustomerAddressStorage.getAddresses();
    const defaultAddress = CustomerAddressStorage.getDefaultAddress();
    if (defaultAddress) {
      this.deliveryCity = defaultAddress.city;
      this.deliveryAddress = defaultAddress.address;
      this.deliveryPhone = defaultAddress.phone;
      if (!this.profilePhone) {
        this.profilePhone = defaultAddress.phone;
      }
    } else if (typeof window !== "undefined") {
      this.deliveryCity =
        localStorage.getItem(DELIVERY_LOCATION_KEY) || DELIVERY_CITIES[0];
      this.deliveryAddress = localStorage.getItem(DELIVERY_ADDRESS_KEY) ?? "";
      this.deliveryPhone = localStorage.getItem(DELIVERY_PHONE_KEY) ?? "";
    }
    this.fire("savedAddresses", this);
    this.fire("deliveryCity", this);
    this.fire("deliveryAddress", this);
    this.fire("deliveryPhone", this);
  }

  public loadOrders = async (): Promise<void> => {
    this.ordersLoading = true;
    this.ordersError = "";
    this.fire("ordersLoading", this);
    this.fire("ordersError", this);

    try {
      const session = CustomerSession.load();
      if (session?.email) {
        const rows = await CustomerOrdersApi.getOrdersByEmail(session.email);
        const orderMap = new Map<number, CustomerOrderRecord>();
        for (const row of rows) {
          orderMap.set(row.id, row);
        }

        const phones = new Set<string>();
        const addPhone = (value: string): void => {
          const trimmed = value.trim();
          if (trimmed) {
            phones.add(trimmed);
          }
        };
        addPhone(this.profilePhone);
        addPhone(this.deliveryPhone);
        addPhone(CustomerProfileStorage.getPhone());
        addPhone(CustomerProfileStorage.getPhoneForEmail(session.email));
        for (const address of this.savedAddresses) {
          addPhone(address.phone);
        }

        for (const phone of phones) {
          const phoneRows = await CustomerOrdersApi.getOrdersByPhone(phone);
          for (const row of phoneRows) {
            orderMap.set(row.id, row);
          }
        }

        const mergedOrders: CustomerOrderRecord[] = [];
        orderMap.forEach((order) => {
          mergedOrders.push(order);
        });
        mergedOrders.sort((a, b) => b.orderDate.localeCompare(a.orderDate));
        this.orders = mergedOrders;
      } else {
        const phones = new Set<string>();
        const addPhone = (value: string): void => {
          const trimmed = value.trim();
          if (trimmed) {
            phones.add(trimmed);
          }
        };

        addPhone(this.profilePhone);
        addPhone(this.deliveryPhone);
        addPhone(CustomerProfileStorage.getPhone());
        for (const address of this.savedAddresses) {
          addPhone(address.phone);
        }

        if (phones.size === 0) {
          this.orders = [];
          this.ordersError = "Add your mobile number in Profile to view order history.";
        } else {
          const orderMap = new Map<number, CustomerOrderRecord>();
          for (const phone of phones) {
            const rows = await CustomerOrdersApi.getOrdersByPhone(phone);
            for (const row of rows) {
              orderMap.set(row.id, row);
            }
          }
          const mergedOrders: CustomerOrderRecord[] = [];
          orderMap.forEach((order) => {
            mergedOrders.push(order);
          });
          mergedOrders.sort((a, b) => b.orderDate.localeCompare(a.orderDate));
          this.orders = mergedOrders;
        }
      }
    } catch (error) {
      this.orders = [];
      this.ordersError =
        error instanceof Error && error.message === "ORDER_API_NOT_FOUND"
          ? "Order history service is starting. Please refresh in a moment."
          : "Unable to load orders. Please try again.";
    } finally {
      this.ordersLoading = false;
      this.fire("ordersLoading", this);
      this.fire("orders", this);
      this.fire("ordersError", this);
      if (this.orders.length > 0 && !this.selectedOrderDetail && !this.orderDetailError) {
        void this.openOrderDetail(this.orders[0]);
      }
    }
  };

  public setTab = (tab: CustomerAccountTab, updateHash = true): void => {
    this.activeTab = tab;
    this.addressSavedMessage = "";
    this.profileSavedMessage = "";
    this.showAddressForm = false;
    this.editingAddressId = null;
    this.selectedOrderDetail = null;
    this.orderDetailError = "";
    this.fire("activeTab", this);
    this.fire("addressSavedMessage", this);
    this.fire("profileSavedMessage", this);
    this.fire("showAddressForm", this);
    this.fire("selectedOrderDetail", this);
    this.fire("orderDetailError", this);

    if (updateHash && typeof window !== "undefined") {
      window.history.replaceState(
        null,
        "",
        `#account-page?tab=${encodeURIComponent(tab)}`
      );
    }

    if (tab === "order-history") {
      void this.loadOrders();
    }
    if (tab === "address" || tab === "details") {
      this.loadAddresses();
      this.loadProfile();
    }
    if (tab === "profile") {
      this.loadProfile();
    }
    if (tab === "details") {
      void this.loadOrders();
    }
  };

  public setDeliveryCity = (event: React.ChangeEvent<HTMLSelectElement>): void => {
    this.deliveryCity = event.target.value;
    this.fire("deliveryCity", this);
  };

  public setDeliveryAddress = (event: React.ChangeEvent<HTMLTextAreaElement>): void => {
    this.deliveryAddress = event.target.value;
    this.fire("deliveryAddress", this);
  };

  public setDeliveryPhone = (event: React.ChangeEvent<HTMLInputElement>): void => {
    this.deliveryPhone = event.target.value;
    this.fire("deliveryPhone", this);
  };

  public setProfileName = (event: React.ChangeEvent<HTMLInputElement>): void => {
    this.profileName = event.target.value;
    this.fire("profileName", this);
  };

  public setProfilePhone = (event: React.ChangeEvent<HTMLInputElement>): void => {
    this.profilePhone = event.target.value;
    this.fire("profilePhone", this);
  };

  public setFormLabel = (event: React.ChangeEvent<HTMLInputElement>): void => {
    this.formLabel = event.target.value;
    this.fire("formLabel", this);
  };

  public startAddAddress = (): void => {
    this.showAddressForm = true;
    this.editingAddressId = null;
    this.formLabel = "Home";
    this.deliveryCity = DELIVERY_CITIES[0];
    this.deliveryAddress = "";
    this.deliveryPhone = this.profilePhone;
    this.addressSavedMessage = "";
    this.fire("showAddressForm", this);
    this.fire("editingAddressId", this);
    this.fire("formLabel", this);
    this.fire("deliveryCity", this);
    this.fire("deliveryAddress", this);
    this.fire("deliveryPhone", this);
    this.fire("addressSavedMessage", this);
  };

  public startEditAddress = (address: SavedAddress): void => {
    this.showAddressForm = true;
    this.editingAddressId = address.id;
    this.formLabel = address.label;
    this.deliveryCity = address.city;
    this.deliveryAddress = address.address;
    this.deliveryPhone = address.phone;
    this.addressSavedMessage = "";
    this.fire("showAddressForm", this);
    this.fire("editingAddressId", this);
    this.fire("formLabel", this);
    this.fire("deliveryCity", this);
    this.fire("deliveryAddress", this);
    this.fire("deliveryPhone", this);
    this.fire("addressSavedMessage", this);
  };

  public cancelAddressForm = (): void => {
    this.showAddressForm = false;
    this.editingAddressId = null;
    this.addressSavedMessage = "";
    this.fire("showAddressForm", this);
    this.fire("editingAddressId", this);
    this.fire("addressSavedMessage", this);
  };

  public saveProfile = async (): Promise<void> => {
    const name = this.profileName.trim();
    const phone = this.profilePhone.trim();
    if (!name) {
      this.profileSavedMessage = "Please enter your display name.";
      this.fire("profileSavedMessage", this);
      return;
    }
    if (!phone) {
      this.profileSavedMessage = "Please enter your mobile number.";
      this.fire("profileSavedMessage", this);
      return;
    }

    const session = CustomerSession.load();
    if (!session?.email) {
      this.profileSavedMessage = "Please sign in again to save your profile.";
      this.fire("profileSavedMessage", this);
      return;
    }

    try {
      const saved = await CustomerProfileApi.saveProfile(session.email, name, phone);
      const savedName = saved?.displayName?.trim() || name;
      const savedPhone = saved?.phone?.trim() || phone;
      CustomerSession.updateDisplayName(savedName);
      CustomerProfileStorage.savePhoneForEmail(session.email, savedPhone);
      this.profileName = savedName;
      this.profilePhone = savedPhone;
      this.profileSavedMessage = "Profile saved successfully.";
      this.fire("profileName", this);
      this.fire("profilePhone", this);
      this.fire("profileSavedMessage", this);
    } catch {
      this.profileSavedMessage = "Unable to save profile. Please try again.";
      this.fire("profileSavedMessage", this);
    }
  };

  public handleAvatarChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    const file = event.target.files?.[0];
    if (!file) {
      return;
    }
    if (!file.type.startsWith("image/")) {
      this.profileSavedMessage = "Please choose an image file.";
      this.fire("profileSavedMessage", this);
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      const dataUrl = typeof reader.result === "string" ? reader.result : "";
      CustomerProfileStorage.saveAvatarUrl(dataUrl);
      this.avatarUrl = dataUrl;
      this.profileSavedMessage = "Profile photo updated.";
      this.fire("avatarUrl", this);
      this.fire("profileSavedMessage", this);
    };
    reader.readAsDataURL(file);
    event.target.value = "";
  };

  public removeAvatar = (): void => {
    CustomerProfileStorage.saveAvatarUrl("");
    this.avatarUrl = "";
    this.profileSavedMessage = "Profile photo removed.";
    this.fire("avatarUrl", this);
    this.fire("profileSavedMessage", this);
  };

  public saveAddress = (): void => {
    if (!this.deliveryAddress.trim()) {
      this.addressSavedMessage = "Please enter your delivery address.";
      this.fire("addressSavedMessage", this);
      return;
    }
    if (!this.deliveryPhone.trim()) {
      this.addressSavedMessage = "Please enter your phone number.";
      this.fire("addressSavedMessage", this);
      return;
    }

    const makeDefault =
      this.editingAddressId === null
        ? this.savedAddresses.length === 0
        : this.savedAddresses.find((item) => item.id === this.editingAddressId)?.isDefault ?? false;

    this.savedAddresses = CustomerAddressStorage.upsertAddress({
      id: this.editingAddressId ?? undefined,
      label: this.formLabel,
      city: this.deliveryCity,
      address: this.deliveryAddress,
      phone: this.deliveryPhone,
      isDefault: makeDefault || this.savedAddresses.length === 0,
    });

    if (!this.profilePhone) {
      CustomerProfileStorage.savePhone(this.deliveryPhone.trim());
      this.profilePhone = this.deliveryPhone.trim();
      this.fire("profilePhone", this);
    }

    this.showAddressForm = false;
    this.editingAddressId = null;
    this.addressSavedMessage = "Address saved successfully.";
    this.fire("savedAddresses", this);
    this.fire("showAddressForm", this);
    this.fire("editingAddressId", this);
    this.fire("addressSavedMessage", this);
  };

  public setDefaultAddress = (id: string): void => {
    this.savedAddresses = CustomerAddressStorage.setDefault(id);
    const selected = this.savedAddresses.find((item) => item.id === id);
    if (selected) {
      this.deliveryCity = selected.city;
      this.deliveryAddress = selected.address;
      this.deliveryPhone = selected.phone;
      this.fire("deliveryCity", this);
      this.fire("deliveryAddress", this);
      this.fire("deliveryPhone", this);
    }
    this.addressSavedMessage = "Default address updated.";
    this.fire("savedAddresses", this);
    this.fire("addressSavedMessage", this);
  };

  public deleteAddress = (id: string): void => {
    this.savedAddresses = CustomerAddressStorage.deleteAddress(id);
    this.addressSavedMessage = "Address removed.";
    this.fire("savedAddresses", this);
    this.fire("addressSavedMessage", this);
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

  public goShop = (): void => {
    this.navigator.pushLandingPage({
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
    this.setTab(tab);
  };

  public openOrderDetail = (order: CustomerOrderRecord): void => {
    const session = CustomerSession.load();
    const phone = (this.profilePhone || this.deliveryPhone).trim();
    if (!session?.email && !phone) {
      this.orderDetailError = "Add your mobile number in Profile to view order details.";
      this.fire("orderDetailError", this);
      return;
    }

    this.orderDetailLoading = true;
    this.orderDetailError = "";
    this.selectedOrderDetail = null;
    this.fire("orderDetailLoading", this);
    this.fire("orderDetailError", this);
    this.fire("selectedOrderDetail", this);

    const detailPromise = session?.email
      ? CustomerOrdersApi.getOrderByIdForEmail(order.id, session.email)
      : CustomerOrdersApi.getOrderById(order.id, phone);

    void detailPromise
      .then((detail) => {
        if (!detail) {
          this.orderDetailError = "Unable to load order details.";
          return;
        }
        this.selectedOrderDetail = detail;
      })
      .catch(() => {
        this.orderDetailError = "Unable to load order details.";
      })
      .finally(() => {
        this.orderDetailLoading = false;
        this.fire("orderDetailLoading", this);
        this.fire("orderDetailError", this);
        this.fire("selectedOrderDetail", this);
      });
  };

  public closeOrderDetail = (): void => {
    this.selectedOrderDetail = null;
    this.orderDetailError = "";
    this.fire("selectedOrderDetail", this);
    this.fire("orderDetailError", this);
  };

  private renderTabButton(tab: CustomerAccountTab): ReactNode {
    const active = this.activeTab === tab;
    return React.createElement(
      "button",
      {
        className:
          "customerAccountTabBtn" + (active ? " customerAccountTabBtnActive" : ""),
        type: "button",
        onClick: () => this.setTab(tab),
        key: "tab-" + tab,
      },
      React.createElement("span", { className: "customerAccountTabIcon" }, TAB_ICONS[tab]),
      React.createElement("span", null, TAB_LABELS[tab])
    );
  }

  private renderOrderDetailPanel(): ReactNode {
    if (this.orderDetailLoading) {
      return React.createElement(
        "div",
        { className: "customerOrderDetailPanel customerOrderDetailPanelState" },
        React.createElement("div", { className: "customerOrderDetailStateBody" },
          React.createElement("div", { className: "customerOrderDetailSpinner" }),
          React.createElement("p", null, "Loading order details...")
        )
      );
    }

    if (this.orderDetailError) {
      return React.createElement(
        "div",
        { className: "customerOrderDetailPanel customerOrderDetailPanelState" },
        React.createElement("p", { className: "customerAccountError" }, this.orderDetailError)
      );
    }

    const detail = this.selectedOrderDetail;
    if (!detail) {
      return React.createElement(
        "div",
        { className: "customerOrderDetailPanel customerOrderDetailPanelEmpty" },
        React.createElement("div", { className: "customerOrderDetailEmptyIcon" }, "📦"),
        React.createElement("strong", null, "Select an order"),
        React.createElement("p", null, "Choose an order from the list to view delivery info and items.")
      );
    }

    const delivery = parseDeliveryInfo(detail.customerName);
    const paymentLabel =
      detail.paymentStatus === "Unpaid" ? "Cash on Delivery" : detail.paymentStatus;
    const deliveryFee = Math.max(0, detail.totalAmount - detail.subtotal);

    return React.createElement(
      "div",
      { className: "customerOrderDetailPanel" },
      React.createElement(
        "div",
        { className: "customerOrderDetailHead" },
        React.createElement(
          "div",
          { className: "customerOrderDetailHeadMain" },
          React.createElement("span", { className: "customerOrderDetailLabel" }, "Order details"),
          React.createElement("h3", { className: "customerOrderDetailTitle" }, detail.orderNumber),
          React.createElement("p", { className: "customerOrderDetailDate" }, formatOrderDate(detail.orderDate))
        ),
        React.createElement(
          "button",
          {
            className: "customerOrderDetailClose",
            type: "button",
            onClick: () => this.closeOrderDetail(),
            "aria-label": "Close order details",
          },
          "×"
        )
      ),
      React.createElement(
        "div",
        { className: "customerOrderDetailStatusRow" },
        React.createElement(
          "span",
          {
            className:
              "customerAccountOrderStatus" +
              (detail.status ? " customerAccountOrderStatus" + detail.status : ""),
          },
          detail.status
        ),
        React.createElement("span", { className: "customerOrderDetailPaymentBadge" }, paymentLabel)
      ),
      delivery.address || delivery.location
        ? React.createElement(
            "div",
            { className: "customerOrderDetailSection" },
            React.createElement("h4", { className: "customerOrderDetailSectionTitle" }, "Delivery address"),
            React.createElement(
              "p",
              { className: "customerOrderDetailAddress" },
              (delivery.address ? delivery.address + ", " : "") + (delivery.location || "")
            ),
            detail.customerPhone
              ? React.createElement(
                  "p",
                  { className: "customerOrderDetailPhone" },
                  detail.customerPhone
                )
              : null
          )
        : null,
      React.createElement(
        "div",
        { className: "customerOrderDetailSection" },
        React.createElement("h4", { className: "customerOrderDetailSectionTitle" }, "Items ordered"),
        detail.lines.length === 0
          ? React.createElement("p", { className: "customerAccountHint" }, "No line items found.")
          : React.createElement(
              "div",
              { className: "customerOrderDetailLines" },
              detail.lines.map((line) =>
                React.createElement(
                  "div",
                  {
                    className: "customerOrderDetailLine",
                    key: "line-" + line.productId + "-" + line.productName,
                  },
                  React.createElement(
                    "div",
                    { className: "customerOrderDetailLineInfo" },
                    React.createElement("strong", null, line.productName || "Product"),
                    React.createElement(
                      "span",
                      null,
                      "Qty " + line.quantity + " × " + formatPrice(line.unitPrice)
                    )
                  ),
                  React.createElement(
                    "span",
                    { className: "customerOrderDetailLinePrice" },
                    formatPrice(line.lineTotal)
                  )
                )
              )
            )
      ),
      React.createElement(
        "div",
        { className: "customerOrderDetailSummary" },
        React.createElement(
          "div",
          { className: "customerOrderDetailSummaryRow" },
          React.createElement("span", null, "Subtotal"),
          React.createElement("span", null, formatPrice(detail.subtotal))
        ),
        deliveryFee > 0
          ? React.createElement(
              "div",
              { className: "customerOrderDetailSummaryRow" },
              React.createElement("span", null, "Delivery fee"),
              React.createElement("span", null, formatPrice(deliveryFee))
            )
          : null,
        React.createElement(
          "div",
          { className: "customerOrderDetailSummaryRow customerOrderDetailSummaryTotal" },
          React.createElement("span", null, "Total paid"),
          React.createElement("strong", null, formatPrice(detail.totalAmount))
        )
      )
    );
  }

  private renderOrderHistory(): ReactNode {
    if (this.ordersLoading) {
      return React.createElement(
        "p",
        { className: "customerAccountHint" },
        "Loading your orders..."
      );
    }

    if (this.ordersError) {
      return React.createElement("p", { className: "customerAccountError" }, this.ordersError);
    }

    if (this.orders.length === 0) {
      return React.createElement(
        "div",
        { className: "customerAccountEmpty" },
        React.createElement("div", { className: "customerAccountEmptyIcon" }, "📦"),
        React.createElement("h3", null, "No orders yet"),
        React.createElement("p", null, "Your placed orders will appear here."),
        React.createElement(
          "button",
          { className: "customerAccountActionBtn", type: "button", onClick: () => this.goShop() },
          "Start Shopping"
        )
      );
    }

    return React.createElement(
      "div",
      { className: "customerOrderHistoryWrap" },
      React.createElement(
        "div",
        { className: "customerOrderHistoryHeader" },
        React.createElement(
          "div",
          { className: "customerOrderHistoryHeaderText" },
          React.createElement("h2", null, "Order History"),
          React.createElement(
            "p",
            null,
            this.orders.length + (this.orders.length === 1 ? " order" : " orders")
          )
        )
      ),
      React.createElement(
        "div",
        { className: "customerOrderHistoryLayout" },
        React.createElement(
          "div",
          { className: "customerAccountOrderListCol" },
          React.createElement(
            "div",
            { className: "customerAccountOrderList" },
            this.orders.map((order) => {
              const paymentLabel =
                order.paymentStatus === "Unpaid" ? "COD" : order.paymentStatus;
              const isSelected = this.selectedOrderDetail?.id === order.id;
              return React.createElement(
                "button",
                {
                  className:
                    "customerAccountOrderCard customerAccountOrderCardClickable" +
                    (isSelected ? " customerAccountOrderCardSelected" : ""),
                  type: "button",
                  key: "order-" + order.id,
                  onClick: () => this.openOrderDetail(order),
                },
                React.createElement(
                  "div",
                  { className: "customerAccountOrderCardInner" },
                  React.createElement(
                    "div",
                    { className: "customerAccountOrderLeft" },
                    React.createElement(
                      "span",
                      { className: "customerAccountOrderId" },
                      order.orderNumber || "Order"
                    ),
                    React.createElement(
                      "span",
                      { className: "customerAccountOrderMetaLine" },
                      formatOrderDate(order.orderDate),
                      " · ",
                      paymentLabel
                    )
                  ),
                  React.createElement(
                    "div",
                    { className: "customerAccountOrderRight" },
                    React.createElement(
                      "span",
                      {
                        className:
                          "customerAccountOrderStatus" +
                          (order.status ? " customerAccountOrderStatus" + order.status : ""),
                      },
                      order.status
                    ),
                    React.createElement(
                      "span",
                      { className: "customerAccountOrderAmount" },
                      formatPrice(order.totalAmount)
                    )
                  )
                )
              );
            })
          )
        ),
        React.createElement(
          "div",
          { className: "customerOrderDetailCol" },
          this.renderOrderDetailPanel()
        )
      )
    );
  }

  private renderAddressForm(): ReactNode {
    return React.createElement(
      "div",
      { className: "customerAccountForm customerAccountAddressForm" },
      React.createElement(
        "h3",
        { className: "customerAccountFormTitle" },
        this.editingAddressId ? "Edit address" : "Add new address"
      ),
      React.createElement(
        "label",
        { className: "customerAccountField" },
        React.createElement("span", null, "Label"),
        React.createElement("input", {
          className: "customerAccountInput",
          type: "text",
          placeholder: "Home, Work, etc.",
          value: this.formLabel,
          onChange: this.setFormLabel,
        })
      ),
      React.createElement(
        "label",
        { className: "customerAccountField" },
        React.createElement("span", null, "City"),
        React.createElement(
          "select",
          {
            className: "customerAccountInput",
            value: this.deliveryCity,
            onChange: this.setDeliveryCity,
          },
          DELIVERY_CITIES.map((city) =>
            React.createElement("option", { key: city, value: city }, city)
          )
        )
      ),
      React.createElement(
        "label",
        { className: "customerAccountField" },
        React.createElement("span", null, "Delivery address"),
        React.createElement("textarea", {
          className: "customerAccountTextarea",
          rows: 3,
          placeholder: "House / Flat no., Street, Landmark",
          value: this.deliveryAddress,
          onChange: this.setDeliveryAddress,
        })
      ),
      React.createElement(
        "label",
        { className: "customerAccountField" },
        React.createElement("span", null, "Mobile number"),
        React.createElement("input", {
          className: "customerAccountInput",
          type: "tel",
          placeholder: "10-digit mobile number",
          value: this.deliveryPhone,
          onChange: this.setDeliveryPhone,
        })
      ),
      React.createElement(
        "div",
        { className: "customerAccountFormActions" },
        React.createElement(
          "button",
          {
            className: "customerAccountActionBtn",
            type: "button",
            onClick: () => this.saveAddress(),
          },
          "Save Address"
        ),
        React.createElement(
          "button",
          {
            className: "customerAccountSecondaryBtn",
            type: "button",
            onClick: () => this.cancelAddressForm(),
          },
          "Cancel"
        )
      )
    );
  }

  private renderAddressCard(address: SavedAddress): ReactNode {
    return React.createElement(
      "article",
      {
        className:
          "customerAddressCard" + (address.isDefault ? " customerAddressCardDefault" : ""),
        key: "address-" + address.id,
      },
      React.createElement(
        "div",
        { className: "customerAddressCardHead" },
        React.createElement(
          "div",
          { className: "customerAddressCardLabelWrap" },
          React.createElement("span", { className: "customerAddressCardIcon" }, "📍"),
          React.createElement("strong", { className: "customerAddressCardLabel" }, address.label),
          address.isDefault
            ? React.createElement("span", { className: "customerAddressCardBadge" }, "Default")
            : null
        ),
        React.createElement(
          "div",
          { className: "customerAddressCardActions" },
          !address.isDefault
            ? React.createElement(
                "button",
                {
                  className: "customerAddressCardLinkBtn",
                  type: "button",
                  onClick: () => this.setDefaultAddress(address.id),
                },
                "Set default"
              )
            : null,
          React.createElement(
            "button",
            {
              className: "customerAddressCardLinkBtn",
              type: "button",
              onClick: () => this.startEditAddress(address),
            },
            "Edit"
          ),
          React.createElement(
            "button",
            {
              className: "customerAddressCardLinkBtn customerAddressCardDeleteBtn",
              type: "button",
              onClick: () => this.deleteAddress(address.id),
            },
            "Delete"
          )
        )
      ),
      React.createElement(
        "p",
        { className: "customerAddressCardText" },
        address.address + ", " + address.city
      ),
      React.createElement(
        "p",
        { className: "customerAddressCardPhone" },
        "📱 ",
        address.phone || "No mobile number"
      )
    );
  }

  private renderAddressTab(): ReactNode {
    return React.createElement(
      "div",
      { className: "customerAddressSection" },
      !this.showAddressForm
        ? React.createElement(
            "div",
            { className: "customerAddressToolbar" },
            React.createElement(
              "p",
              { className: "customerAccountHint" },
              "Saved delivery addresses for checkout."
            ),
            React.createElement(
              "button",
              {
                className: "customerAccountActionBtn",
                type: "button",
                onClick: () => this.startAddAddress(),
              },
              "+ Add new address"
            )
          )
        : null,
      this.addressSavedMessage
        ? React.createElement(
            "p",
            {
              className: this.addressSavedMessage.includes("success") ||
                this.addressSavedMessage.includes("updated") ||
                this.addressSavedMessage.includes("removed")
                ? "customerAccountSuccess"
                : "customerAccountError",
            },
            this.addressSavedMessage
          )
        : null,
      this.showAddressForm
        ? this.renderAddressForm()
        : this.savedAddresses.length === 0
        ? React.createElement(
            "div",
            { className: "customerAccountEmpty" },
            React.createElement("div", { className: "customerAccountEmptyIcon" }, "📍"),
            React.createElement("h3", null, "No saved addresses"),
            React.createElement("p", null, "Add a delivery address for faster checkout."),
            React.createElement(
              "button",
              {
                className: "customerAccountActionBtn",
                type: "button",
                onClick: () => this.startAddAddress(),
              },
              "Add Address"
            )
          )
        : React.createElement(
            "div",
            { className: "customerAddressCardGrid" },
            this.savedAddresses.map((address) => this.renderAddressCard(address))
          )
    );
  }

  private renderProfileTab(): ReactNode {
    const initials = profileInitials(this.profileName);
    const isSuccessMessage =
      this.profileSavedMessage.includes("success") ||
      this.profileSavedMessage.includes("updated") ||
      this.profileSavedMessage.includes("removed");

    return React.createElement(
      "div",
      { className: "customerProfileWrap" },
      React.createElement(
        "div",
        { className: "customerProfilePageHeader" },
        React.createElement("h2", null, "Profile"),
        React.createElement("p", null, "Manage your photo and personal information")
      ),
      React.createElement(
        "div",
        { className: "customerProfileBody" },
        React.createElement(
          "div",
          { className: "customerProfileHeroCard" },
        React.createElement(
          "div",
          { className: "customerProfileHeroMain" },
          this.avatarUrl
            ? React.createElement("img", {
                src: this.avatarUrl,
                alt: this.profileName,
                className: "customerProfileAvatar",
              })
            : React.createElement(
                "span",
                { className: "customerProfileAvatarFallback" },
                initials
              ),
          React.createElement(
            "div",
            { className: "customerProfileHeroInfo" },
            React.createElement(
              "h3",
              { className: "customerProfileName" },
              this.profileName || "Customer"
            ),
            React.createElement(
              "p",
              { className: "customerProfileEmail" },
              this.profileEmail
            ),
            React.createElement(
              "p",
              { className: "customerProfilePhone" },
              this.profilePhone || "Add mobile number"
            )
          )
        ),
        React.createElement(
          "div",
          { className: "customerProfilePhotoActions" },
          React.createElement(
            "label",
            { className: "customerProfilePhotoBtn" },
            React.createElement("span", { className: "customerProfilePhotoBtnText" }, "Upload photo"),
            React.createElement("input", {
              type: "file",
              accept: "image/*",
              className: "customerProfileAvatarInput",
              onChange: this.handleAvatarChange,
            })
          ),
          this.avatarUrl
            ? React.createElement(
                "button",
                {
                  className: "customerProfilePhotoBtn customerProfilePhotoBtnMuted",
                  type: "button",
                  onClick: () => this.removeAvatar(),
                },
                "Remove"
              )
            : null
        )
      ),
      React.createElement(
        "div",
        { className: "customerProfileFormCard" },
        React.createElement(
          "div",
          { className: "customerProfileFormHead" },
          React.createElement("h3", { className: "customerProfileFormTitle" }, "Personal information"),
          React.createElement(
            "p",
            { className: "customerProfileFormHint" },
            "Update your name and contact details"
          )
        ),
        React.createElement(
          "div",
          { className: "customerProfileFormGrid" },
          React.createElement(
            "label",
            { className: "customerAccountField customerProfileField" },
            React.createElement("span", null, "Display name"),
            React.createElement("input", {
              className: "customerAccountInput customerProfileInput",
              type: "text",
              value: this.profileName,
              onChange: this.setProfileName,
            })
          ),
          React.createElement(
            "label",
            { className: "customerAccountField customerProfileField" },
            React.createElement("span", null, "Mobile number"),
            React.createElement("input", {
              className: "customerAccountInput customerProfileInput",
              type: "tel",
              placeholder: "10-digit mobile number",
              value: this.profilePhone,
              onChange: this.setProfilePhone,
            })
          ),
          React.createElement(
            "label",
            { className: "customerAccountField customerProfileField customerProfileEmailField" },
            React.createElement("span", null, "Email address"),
            React.createElement("input", {
              className:
                "customerAccountInput customerProfileInput customerAccountInputReadonly",
              type: "email",
              value: this.profileEmail,
              readOnly: true,
            }),
            React.createElement(
              "span",
              { className: "customerProfileFieldNote" },
              "Email cannot be changed"
            )
          )
        ),
        React.createElement(
          "div",
          { className: "customerProfileFormFooter" },
          this.profileSavedMessage
            ? React.createElement(
                "p",
                {
                  className: isSuccessMessage
                    ? "customerAccountSuccess customerProfileFormMessage"
                    : "customerAccountError customerProfileFormMessage",
                },
                this.profileSavedMessage
              )
            : null,
          React.createElement(
            "button",
            {
              className: "customerAccountActionBtn customerProfileSaveBtn",
              type: "button",
              onClick: () => this.saveProfile(),
            },
            "Save changes"
          )
        )
      )
    )
    );
  }

  private renderInfoTile(icon: string, label: string, value: string): ReactNode {
    return React.createElement(
      "div",
      { className: "customerDetailsInfoTile", key: label },
      React.createElement("span", { className: "customerDetailsInfoTileIcon" }, icon),
      React.createElement("span", { className: "customerDetailsInfoTileLabel" }, label),
      React.createElement("strong", { className: "customerDetailsInfoTileValue" }, value)
    );
  }

  private renderDetailsTab(): ReactNode {
    const session = CustomerSession.load();
    const defaultAddress = CustomerAddressStorage.getDefaultAddress();
    const orderCount = this.orders.length;

    return React.createElement(
      "div",
      { className: "customerDetailsSection" },
      React.createElement(
        "div",
        { className: "customerDetailsHeroCard" },
        React.createElement(
          "div",
          { className: "customerDetailsHeroInner" },
          this.avatarUrl
            ? React.createElement("img", {
                src: this.avatarUrl,
                alt: this.profileName,
                className: "customerDetailsHeroAvatar",
              })
            : React.createElement(
                "span",
                { className: "customerDetailsHeroAvatarFallback" },
                profileInitials(this.profileName)
              ),
          React.createElement(
            "div",
            { className: "customerDetailsHeroBody" },
            React.createElement("h3", null, this.profileName || "Customer"),
            React.createElement("p", null, session?.email || "—"),
            React.createElement(
              "span",
              { className: "customerDetailsHeroPhone" },
              "📱 " + (this.profilePhone || "No mobile number")
            )
          ),
          React.createElement(
            "button",
            {
              className: "customerDetailsEditBtn",
              type: "button",
              onClick: () => this.setTab("profile"),
            },
            "Edit profile"
          )
        )
      ),
      React.createElement(
        "div",
        { className: "customerDetailsInfoGrid" },
        this.renderInfoTile("📦", "Total orders", String(orderCount > 0 ? orderCount : "—")),
        this.renderInfoTile("📍", "Saved addresses", String(this.savedAddresses.length)),
        this.renderInfoTile("💳", "Payment", "Cash on Delivery"),
        this.renderInfoTile("✉️", "Account email", session?.email || "—"),
        this.renderInfoTile("📱", "Mobile number", this.profilePhone || "Not set"),
        this.renderInfoTile("🏙️", "Delivery city", defaultAddress?.city || this.deliveryCity || "—")
      ),
      defaultAddress
        ? React.createElement(
            "div",
            { className: "customerDetailsAddressPreview" },
            React.createElement(
              "div",
              { className: "customerDetailsAddressPreviewHead" },
              React.createElement("h4", null, "Default delivery address"),
              React.createElement(
                "button",
                {
                  className: "customerAddressCardLinkBtn",
                  type: "button",
                  onClick: () => this.setTab("address"),
                },
                "Manage"
              )
            ),
            React.createElement(
              "article",
              { className: "customerAddressCard customerAddressCardDefault" },
              React.createElement(
                "div",
                { className: "customerAddressCardHead" },
                React.createElement(
                  "div",
                  { className: "customerAddressCardLabelWrap" },
                  React.createElement("span", { className: "customerAddressCardIcon" }, "📍"),
                  React.createElement("strong", null, defaultAddress.label),
                  React.createElement("span", { className: "customerAddressCardBadge" }, "Default")
                )
              ),
              React.createElement(
                "p",
                { className: "customerAddressCardText" },
                defaultAddress.address + ", " + defaultAddress.city
              ),
              React.createElement(
                "p",
                { className: "customerAddressCardPhone" },
                "📱 " + defaultAddress.phone
              )
            )
          )
        : React.createElement(
            "div",
            { className: "customerDetailsEmptyAddress" },
            React.createElement("p", null, "No default address saved yet."),
            React.createElement(
              "button",
              {
                className: "customerAccountActionBtn",
                type: "button",
                onClick: () => this.setTab("address"),
              },
              "Add address"
            )
          )
    );
  }

  private renderTabContent(): ReactNode {
    switch (this.activeTab) {
      case "order-history":
        return this.renderOrderHistory();
      case "address":
        return this.renderAddressTab();
      case "profile":
        return this.renderProfileTab();
      case "details":
        return this.renderDetailsTab();
      default:
        return null;
    }
  }

  public render(): ReactNode {
    return ui.Container({
      child: React.createElement(
        "div",
        { className: "landingPageShell customerAccountPageRoot" },
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
          { className: "landingScroll customerAccountPageScroll", key: "scroll" },
          React.createElement(
            "div",
            { className: "landingContainer customerAccountPageMain" },
            React.createElement(
              "div",
              { className: "customerAccountTopBar" },
              React.createElement(
                "button",
                {
                  className: "productDetailBackBtn customerAccountBackBtn",
                  type: "button",
                  onClick: () => this.goShop(),
                },
                "← Back to home"
              ),
              React.createElement(
                "header",
                { className: "customerAccountPageHead" },
                React.createElement("h1", { className: "customerAccountPageTitle" }, "My Account"),
                React.createElement("span", { className: "customerAccountPageDivider", "aria-hidden": "true" }, "·"),
                React.createElement(
                  "p",
                  { className: "customerAccountPageSubtitle" },
                  "Manage your orders, address and profile"
                )
              )
            ),
            React.createElement(
              "div",
              { className: "customerAccountLayout" },
              React.createElement(
                "nav",
                { className: "customerAccountTabs", "aria-label": "Account sections" },
                (["order-history", "address", "profile", "details"] as CustomerAccountTab[]).map(
                  (tab) => this.renderTabButton(tab)
                )
              ),
              React.createElement(
                "section",
                {
                  className:
                    "customerAccountPanel" +
                    (this.activeTab === "details"
                      ? " customerAccountPanelCompact"
                      : "") +
                    (this.activeTab === "order-history" ? " customerAccountPanelOrders" : "") +
                    (this.activeTab === "profile" ? " customerAccountPanelProfile" : ""),
                },
                React.createElement(
                  "h2",
                  { className: "customerAccountPanelTitle" },
                  TAB_LABELS[this.activeTab]
                ),
                this.renderTabContent()
              )
            )
          )
        )
      ),
      className: ui.join("CustomerAccountPage ", this.props.className ?? ""),
      ...copyBaseUIProps(this.props),
    });
  }

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function CustomerAccountPage(props: CustomerAccountPageProps) {
  return React.createElement(_CustomerAccountPageState, props);
}
