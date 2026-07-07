import React, { Component, ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import PageNavigator from "../classes/PageNavigator";
import { BuildContext } from "../classes/BuildContext";
import User from "../models/User";
import Query from "../classes/Query";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import CustomerNavigation, {
  CUSTOMER_NAV_ITEMS,
  CustomerNavItem,
} from "../utils/CustomerNavigation";
import CustomerSession from "../utils/CustomerSession";
import CustomerProfileStorage from "../utils/CustomerProfileStorage";
import CartSession from "../utils/CartSession";
import CustomerSearchSession from "../utils/CustomerSearchSession";
import { CustomerAccountTab } from "./CustomerAccountPage";
import CustomerProfileNavigation from "../utils/CustomerProfileNavigation";
import AppLogout from "../utils/AppLogout";
import CustomerCatalogCache from "../utils/CustomerCatalogCache";
import { formatCatalogPrice, resolveProductImage } from "./LandingProductGrid";
import LandingCatalogApi, {
  PublicCategoryRecord,
  PublicProductRecord,
} from "../utils/LandingCatalogApi";
import { UsageConstants } from "../rocket/D3ETemplate";
import LandingFooter from "./LandingFooter";
import LandingHeroCarousel from "./LandingHeroCarousel";
import CustomerLiveProductGrid from "./CustomerLiveProductGrid";
import LandingProductCarousel from "./LandingProductCarousel";

export interface LandingPageProps extends BaseUIProps {
  key?: string;
  user?: User;
}

const IMG = "/images/landing/";
const CAT_IMG = "/images/categories/";

const DELIVERY_CITIES = ["Hyderabad", "Bangalore", "Chennai"];

const DELIVERY_LOCATION_KEY = "freshmart_delivery_location";
const DELIVERY_LOCATION_MANUAL_KEY = "freshmart_delivery_manual";

const SEARCH_PLACEHOLDERS = [
  "Search milk",
  "Search drinks",
  "Search oil",
  "Search rice",
  "Search snacks",
  "Search fruits",
  "Search bread",
  "Search vegetables",
];

const PROMO_CARDS = [
  {
    title: "Fresh Fruits & Vegetables",
    offer: "UP TO 30% OFF",
    image: IMG + "promo-fruits.jpg",
    tone: "promoA",
  },
  {
    title: "Dairy & Breakfast",
    offer: "UP TO 25% OFF",
    image: IMG + "promo-dairy.jpg",
    tone: "promoB",
  },
  {
    title: "Snacks & Beverages",
    offer: "UP TO 20% OFF",
    image: IMG + "promo-snacks.jpg",
    tone: "promoC",
  },
  {
    title: "Personal Care Essentials",
    offer: "UP TO 15% OFF",
    image: IMG + "promo-care.jpg",
    tone: "promoD",
  },
];

const TRUST_ITEMS = [
  { id: "delivery", title: "Fast Delivery", desc: "Delivered within 15-30 mins" },
  { id: "free", title: "Free Delivery", desc: "On orders above ₹499" },
  { id: "quality", title: "Best Quality", desc: "100% quality assured" },
  { id: "returns", title: "Easy Returns", desc: "No questions asked return" },
  { id: "secure", title: "Secure Payment", desc: "100% secure payment" },
];

const DEFAULT_CATEGORY_IMAGE = CAT_IMG + "default.jpg";
const LANDING_PRODUCTS_SECTION_ID = "landing-products-section";
const LANDING_AD_IMAGE = "/Ads.png";
const LANDING_AD_DISMISSED_KEY = "freshmart_ad_popup_dismissed";
const INTERNAL_UOM_CODES = ["CS", "EA", "HR", "CTN", "BOX", "CASE"];

function extractPackFromName(name: string): string {
  const packMatch = name.match(/(\d+(?:\.\d+)?)\s*(ml|l|g|kg|gm|pcs|pc|pack|pkt)\b/i);
  if (packMatch) {
    return packMatch[0];
  }
  const commaPart = name.split(",").pop()?.trim() ?? "";
  if (commaPart.length > 0 && commaPart.length <= 28 && /\d/.test(commaPart)) {
    return commaPart;
  }
  return "";
}

function productSizeLabel(product: PublicProductRecord): string {
  const fromName = extractPackFromName(product.name);
  if (fromName) {
    return fromName;
  }

  const description = (product.description ?? "").trim();
  if (description.length > 0 && description.length <= 32) {
    return description;
  }

  const symbol = (product.uomSymbol ?? "").trim().toUpperCase();
  if (symbol && !INTERNAL_UOM_CODES.includes(symbol)) {
    const readable = symbol.toLowerCase();
    if (["kg", "g", "gm", "l", "ml", "pcs", "pc", "pkt"].includes(readable)) {
      return readable;
    }
    if (product.uomName) {
      return product.uomName;
    }
  }

  return "";
}

interface FeaturedCategorySlot {
  key: string;
  title: string;
  subtitle: string;
  tone: string;
  fallbackImage: string;
  matchPatterns: string[];
  categoryLabel: string;
}

const FEATURED_CATEGORY_SLOTS: FeaturedCategorySlot[] = [
  {
    key: "pharmacy",
    title: "Pharmacy at your doorstep!",
    subtitle: "Cough syrups, pain relief sprays & more",
    tone: "featTeal",
    fallbackImage: IMG + "featured-pharmacy.jpg",
    matchPatterns: ["pharmacy", "personal care", "health", "medicine"],
    categoryLabel: "Pharmacy",
  },
  {
    key: "pet-care",
    title: "Pet care supplies at your door",
    subtitle: "Food, treats, toys & more",
    tone: "featYellow",
    fallbackImage: IMG + "featured-pet-care.png",
    matchPatterns: ["pet"],
    categoryLabel: "Pet Care",
  },
  {
    key: "baby",
    title: "No time for a diaper run?",
    subtitle: "Get baby care essentials",
    tone: "featGray",
    fallbackImage: IMG + "featured-baby.jpg",
    matchPatterns: ["baby", "infant", "diaper"],
    categoryLabel: "Baby Products",
  },
  {
    key: "oils",
    title: "Cooking oils for every meal",
    subtitle: "Sunflower, olive, mustard & more",
    tone: "featOrange",
    fallbackImage: IMG + "featured-oils.jpg",
    matchPatterns: ["oil", "staple", "cooking"],
    categoryLabel: "Oils",
  },
];

const IMS_FEATURES = [
  {
    title: "Real-time Stock",
    desc: "Track inventory across stores and warehouses with batch and expiry support.",
  },
  {
    title: "Procurement",
    desc: "Manage suppliers, purchase orders, and goods receipt workflows.",
  },
  {
    title: "Reports & Alerts",
    desc: "Low-stock alerts, valuation reports, and full audit history.",
  },
];

interface LandingSiteHeaderProps {
  user?: User;
  onLogin: () => void;
  onSignup: () => void;
  onCart?: () => void;
  activeNav?: CustomerNavItem | string;
  onNavigate?: (item: CustomerNavItem | string) => void;
  onSearch?: (query: string, scroll?: boolean) => void;
  onProductSelect?: (product: PublicProductRecord) => void;
  onProfileMenu?: (tab: CustomerAccountTab) => void;
}

interface LandingSiteHeaderState {
  cartCount: number;
  deliveryLocation: string;
  displayName: string;
  avatarUrl: string;
  locationLoading: boolean;
  loggedIn: boolean;
  searchPlaceholderIndex: number;
  searchQuery: string;
  searchFocused: boolean;
  searchSuggestions: PublicProductRecord[];
  showSuggestions: boolean;
  showProfileMenu: boolean;
}

export class LandingSiteHeader extends Component<LandingSiteHeaderProps, LandingSiteHeaderState> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  private authUnsub: (() => void) | null = null;
  private cartUnsub: (() => void) | null = null;
  private customerUser: User = null;
  private placeholderTimer: ReturnType<typeof setInterval> | null = null;
  private searchUnsub: (() => void) | null = null;
  private profileUnsub: (() => void) | null = null;
  private profileMenuRef = React.createRef<HTMLDivElement>();

  public constructor(props: LandingSiteHeaderProps) {
    super(props);
    const saved = LandingSiteHeader.loadSavedLocation();
    const manual = LandingSiteHeader.isManualLocation();
    this.state = {
      cartCount: 0,
      deliveryLocation: saved || DELIVERY_CITIES[0],
      displayName: "",
      avatarUrl: "",
      locationLoading: !manual && !saved,
      loggedIn: false,
      searchPlaceholderIndex: 0,
      searchQuery: CustomerSearchSession.getQuery(),
      searchFocused: false,
      searchSuggestions: [],
      showSuggestions: false,
      showProfileMenu: false,
    };
  }

  private static loadSavedLocation(): string {
    if (typeof window === "undefined") {
      return "";
    }

    const saved = localStorage.getItem(DELIVERY_LOCATION_KEY);

    return saved && saved.length > 0 ? saved : "";
  }

  private static isManualLocation(): boolean {
    if (typeof window === "undefined") {
      return false;
    }

    return localStorage.getItem(DELIVERY_LOCATION_MANUAL_KEY) === "1";
  }

  private saveDeliveryLocation(location: string, manual = false): void {
    if (typeof window !== "undefined") {
      localStorage.setItem(DELIVERY_LOCATION_KEY, location);
      if (manual) {
        localStorage.setItem(DELIVERY_LOCATION_MANUAL_KEY, "1");
      } else {
        localStorage.removeItem(DELIVERY_LOCATION_MANUAL_KEY);
      }
    }

    this.setState({ deliveryLocation: location });
  }

  private matchKnownCity(name: string): string {
    const normalized = name.toLowerCase();

    if (normalized.includes("hyderabad")) {
      return "Hyderabad";
    }

    if (normalized.includes("bengaluru") || normalized.includes("bangalore")) {
      return "Bangalore";
    }

    if (normalized.includes("chennai")) {
      return "Chennai";
    }

    return name;
  }

  private formatDetectedLocation(data: {
    city?: string;
    locality?: string;
    principalSubdivision?: string;
    postcode?: string;
  }): string {
    const locality = (data.locality ?? "").trim();
    const city = (data.city ?? "").trim();
    const state = (data.principalSubdivision ?? "").trim();

    if (locality && city && locality.toLowerCase() !== city.toLowerCase()) {
      return `${locality}, ${city}`;
    }

    if (locality) {
      return locality;
    }

    if (city) {
      return this.matchKnownCity(city);
    }

    if (state) {
      return state;
    }

    return "Hyderabad";
  }

  private async resolveLocationFromCoords(lat: number, lon: number): Promise<string> {
    const response = await fetch(
      "https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" +
        lat +
        "&longitude=" +
        lon +
        "&localityLanguage=en"
    );

    if (!response.ok) {
      throw new Error("reverse geocode failed");
    }

    const data = (await response.json()) as {
      city?: string;
      locality?: string;
      principalSubdivision?: string;
      postcode?: string;
    };

    return this.formatDetectedLocation(data);
  }

  private detectCurrentLocation = (): void => {
    if (!navigator.geolocation) {
      const saved = LandingSiteHeader.loadSavedLocation();
      this.setState({
        locationLoading: false,
        deliveryLocation: saved || "Hyderabad",
      });
      return;
    }

    this.setState({ locationLoading: true });

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        try {
          const location = await this.resolveLocationFromCoords(
            position.coords.latitude,
            position.coords.longitude
          );

          this.saveDeliveryLocation(location, false);
        } catch {
          const saved = LandingSiteHeader.loadSavedLocation();
          this.saveDeliveryLocation(saved || "Hyderabad", false);
        }

        this.setState({ locationLoading: false });
      },
      () => {
        const saved = LandingSiteHeader.loadSavedLocation();
        this.setState({
          locationLoading: false,
          deliveryLocation: saved || "Hyderabad",
        });
      },
      { enableHighAccuracy: true, maximumAge: 60000, timeout: 15000 }
    );
  };

  private handleLocationChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ): void => {
    const value = event.target.value;

    if (value === "__current__") {
      this.detectCurrentLocation();
      return;
    }

    this.saveDeliveryLocation(value, true);
  };

  private syncSearchQuery = (): void => {
    const nextQuery = CustomerSearchSession.getQuery();
    if (this.state.searchQuery !== nextQuery) {
      this.setState({
        searchQuery: nextQuery,
        showSuggestions: false,
        searchSuggestions: [],
      });
    }
  };

  private applySearchInput = (value: string): void => {
    const trimmed = value.trim();

    if (trimmed.length === 0 || trimmed.length < 3) {
      CustomerSearchSession.clear();
      this.props.onSearch?.("");
      this.setState({
        searchQuery: value,
        searchSuggestions: [],
        showSuggestions: false,
      });
      return;
    }

    void this.updateSearchSuggestions(value);
    this.setState({ searchQuery: value, showSuggestions: this.state.searchFocused });
  };

  private updateSearchSuggestions = async (value: string): Promise<void> => {
    const trimmed = value.trim();

    if (trimmed.length < 3) {
      this.setState({ searchSuggestions: [], showSuggestions: false });
      return;
    }

    await CustomerCatalogCache.ensureLoaded();
    const suggestions = CustomerCatalogCache.suggest(trimmed);

    if (this.state.searchQuery !== value) {
      return;
    }

    this.setState({
      searchSuggestions: suggestions,
      showSuggestions: this.state.searchFocused && suggestions.length > 0,
    });
  };

  private handleSearchInputChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ): void => {
    this.applySearchInput(event.target.value);
  };

  private handleSearchFocus = (): void => {
    const trimmed = this.state.searchQuery.trim();
    this.setState({ searchFocused: true });

    if (trimmed.length >= 3) {
      void this.updateSearchSuggestions(this.state.searchQuery);
    }
  };

  private handleSearchBlur = (): void => {
    window.setTimeout(() => {
      this.setState({ searchFocused: false, showSuggestions: false });
    }, 160);
  };

  private handleSearchKeyDown = (event: React.KeyboardEvent<HTMLInputElement>): void => {
    if (event.key === "Enter") {
      event.preventDefault();
      this.submitSearch();
    }

    if (event.key === "Escape") {
      this.setState({ showSuggestions: false });
    }
  };

  private selectSearchSuggestion = (product: PublicProductRecord): void => {
    this.setState({
      searchQuery: product.name.trim(),
      searchSuggestions: [],
      showSuggestions: false,
      searchFocused: false,
    });
    this.props.onProductSelect?.(product);
  };

  private renderSearchSuggestions(): ReactNode {
    if (!this.state.showSuggestions || this.state.searchSuggestions.length === 0) {
      return null;
    }

    return React.createElement(
      "div",
      { className: "landingSearchDropdown", role: "listbox", "aria-label": "Search suggestions" },
      this.state.searchSuggestions.map((product) =>
        React.createElement(
          "button",
          {
            className: "landingSearchSuggestion",
            type: "button",
            key: "search-suggest-" + product.id,
            role: "option",
            onMouseDown: (event: React.MouseEvent) => event.preventDefault(),
            onClick: () => this.selectSearchSuggestion(product),
          },
          React.createElement("img", {
            src: resolveProductImage(product),
            alt: "",
            className: "landingSearchSuggestionImage",
            loading: "lazy",
          }),
          React.createElement(
            "span",
            { className: "landingSearchSuggestionBody" },
            React.createElement(
              "span",
              { className: "landingSearchSuggestionName" },
              product.name
            ),
            product.categoryName
              ? React.createElement(
                  "span",
                  { className: "landingSearchSuggestionCategory" },
                  product.categoryName
                )
              : null
          ),
          React.createElement(
            "span",
            { className: "landingSearchSuggestionPrice" },
            formatCatalogPrice(product.sellingPrice)
          )
        )
      )
    );
  }

  private getSuggestedSearchQuery(): string {
    const placeholder = SEARCH_PLACEHOLDERS[this.state.searchPlaceholderIndex];
    return placeholder.replace(/^Search\s+/i, "").trim();
  };

  private submitSearch = (): void => {
    const query = this.state.searchQuery.trim() || this.getSuggestedSearchQuery();

    if (!query) {
      CustomerSearchSession.clear();
      this.props.onSearch?.("");
      return;
    }

    this.setState({
      searchQuery: query,
      searchSuggestions: [],
      showSuggestions: false,
    });
    CustomerSearchSession.setQuery(query);
    this.props.onSearch?.(query, true);
  };

  public componentDidMount(): void {
    this.syncFromSession();
    this.authUnsub = CustomerSession.onChange(() => this.syncFromSession());
    this.cartUnsub = CartSession.onChange(() => this.syncCartCount());
    this.searchUnsub = CustomerSearchSession.onChange(() => this.syncSearchQuery());
    this.profileUnsub = CustomerProfileStorage.onChange(() => {
      this.setState({ avatarUrl: CustomerProfileStorage.getAvatarUrl() });
    });
    void CustomerCatalogCache.ensureLoaded();

    if (CustomerSession.isActive()) {
      this.loadProfile();
    }

    if (!LandingSiteHeader.isManualLocation()) {
      const saved = LandingSiteHeader.loadSavedLocation();
      if (saved) {
        this.setState({ deliveryLocation: saved, locationLoading: false });
      } else {
        this.detectCurrentLocation();
      }
    }

    this.placeholderTimer = setInterval(() => {
      this.setState((prev) => ({
        searchPlaceholderIndex:
          (prev.searchPlaceholderIndex + 1) % SEARCH_PLACEHOLDERS.length,
      }));
    }, 5000);

    if (typeof document !== "undefined") {
      document.addEventListener("mousedown", this.handleDocumentMouseDown);
    }
  }

  public componentDidUpdate(prevProps: LandingSiteHeaderProps): void {
    if (prevProps.user !== this.props.user && this.props.user) {
      this.customerUser = this.props.user;
      this.syncFromSession();
      this.loadProfile();
    }
  }

  public componentWillUnmount(): void {
    this.authUnsub?.();
    this.authUnsub = null;
    this.cartUnsub?.();
    this.cartUnsub = null;

    if (this.placeholderTimer) {
      clearInterval(this.placeholderTimer);
      this.placeholderTimer = null;
    }

    this.searchUnsub?.();
    this.searchUnsub = null;

    this.profileUnsub?.();
    this.profileUnsub = null;

    if (typeof document !== "undefined") {
      document.removeEventListener("mousedown", this.handleDocumentMouseDown);
    }
  }

  private syncCartCount(): void {
    const nextCount = CartSession.getTotalCount();
    if (this.state.cartCount !== nextCount) {
      this.setState({ cartCount: nextCount });
    }
  }

  private syncFromSession(): void {
    const session = CustomerSession.load();

    if (!session) {
      if (this.state.loggedIn || this.state.displayName) {
        this.customerUser = null;
        this.setState({ loggedIn: false, displayName: "" });
      }
      this.syncCartCount();
      return;
    }

    if (!this.customerUser) {
      this.customerUser = new User({ email: session.email });
    }

    const displayName = session.displayName || session.email.split("@")[0];
    const avatarUrl = CustomerProfileStorage.getAvatarUrl();

    if (
      !this.state.loggedIn ||
      this.state.displayName !== displayName ||
      this.state.avatarUrl !== avatarUrl
    ) {
      this.setState({
        loggedIn: true,
        displayName: displayName,
        avatarUrl: avatarUrl,
      });
    }

    this.syncCartCount();
  }

  private loadProfile = async (): Promise<void> => {
    if (!this.customerUser) {
      return;
    }

    try {
      const profileData = await Query.get().getUserProfileByUser(
        UsageConstants.QUERY_GETUSERPROFILEBYUSER_DASHBOARDPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
        new UserProfileByUserRequest({ user: this.customerUser })
      );

      const profile = profileData?.items?.first;

      if (profile?.displayName && profile.displayName !== this.state.displayName) {
        CustomerSession.updateDisplayName(profile.displayName, true);
        this.setState({ displayName: profile.displayName });
      }
    } catch {
      // Keep session display name when profile fetch is unavailable.
    }
  };

  private customerInitials(): string {
    const name = this.state.displayName || this.customerUser?.email || "C";
    const parts = name.trim().split(/\s+/);

    if (parts.length >= 2) {
      return (parts[0][0] + parts[1][0]).toUpperCase();
    }

    return name.slice(0, 2).toUpperCase();
  }

  private logout(): void {
    this.setState({ showProfileMenu: false });
    AppLogout.signOutCustomer(PageNavigator.of(this.context));
  }

  private handleDocumentMouseDown = (event: MouseEvent): void => {
    if (!this.state.showProfileMenu) {
      return;
    }

    const target = event.target as Node | null;
    if (target && this.profileMenuRef.current?.contains(target)) {
      return;
    }

    this.setState({ showProfileMenu: false });
  };

  private toggleProfileMenu = (): void => {
    this.setState((prev) => ({ showProfileMenu: !prev.showProfileMenu }));
  };

  private openProfileTab = (tab: CustomerAccountTab): void => {
    this.setState({ showProfileMenu: false });
    if (this.props.onProfileMenu) {
      this.props.onProfileMenu(tab);
      return;
    }
    this.props.onLogin();
  };

  private renderProfileMenu(): ReactNode {
    const items: { tab: CustomerAccountTab; label: string; icon: string }[] = [
      { tab: "order-history", label: "Order History", icon: "📋" },
      { tab: "address", label: "Address", icon: "📍" },
      { tab: "profile", label: "Profile", icon: "👤" },
      { tab: "details", label: "Details", icon: "ℹ️" },
    ];

    return React.createElement(
      "div",
      { className: "landingProfileMenu", role: "menu" },
      items.map((item) =>
        React.createElement(
          "button",
          {
            className: "landingProfileMenuItem",
            type: "button",
            role: "menuitem",
            key: item.tab,
            onClick: () => this.openProfileTab(item.tab),
          },
          React.createElement("span", { className: "landingProfileMenuIcon" }, item.icon),
          React.createElement("span", null, item.label)
        )
      )
    );
  }

  private renderHeaderActions(): ReactNode {
    if (!this.state.loggedIn) {
      return React.createElement(
        "div",
        { className: "landingHeaderActions" },
        React.createElement(
          "button",
          {
            className: "landingHeaderBtn landingHeaderBtnPlain",
            type: "button",
            onClick: this.props.onLogin,
          },
          "Login"
        ),
        React.createElement(
          "button",
          {
            className: "landingHeaderBtn landingHeaderBtnPlain landingHeaderBtnPrimary",
            type: "button",
            onClick: this.props.onSignup,
          },
          "Sign Up"
        )
      );
    }

    return React.createElement(
      "div",
      { className: "landingHeaderActions landingHeaderActionsLoggedIn" },
      React.createElement(
        "div",
        { className: "landingProfileWrap", ref: this.profileMenuRef },
        React.createElement(
          "button",
          {
            className:
              "landingCustomerChip" +
              (this.state.showProfileMenu ? " landingCustomerChipOpen" : ""),
            type: "button",
            onClick: () => this.toggleProfileMenu(),
            "aria-expanded": this.state.showProfileMenu,
            "aria-haspopup": "menu",
          },
          React.createElement("span", { className: "landingCustomerAvatar" },
            this.state.avatarUrl
              ? React.createElement("img", {
                  src: this.state.avatarUrl,
                  alt: this.state.displayName,
                  className: "landingCustomerAvatarImage",
                })
              : this.customerInitials()
          ),
          React.createElement(
            "div",
            { className: "landingCustomerMeta" },
            React.createElement("span", { className: "landingCustomerGreeting" }, "Hello,"),
            React.createElement("span", { className: "landingCustomerName" }, this.state.displayName)
          ),
          React.createElement("span", { className: "landingProfileChevron" }, "▾")
        ),
        this.state.showProfileMenu ? this.renderProfileMenu() : null
      ),
      React.createElement(
        "button",
        {
          className: "landingCartBtn",
          type: "button",
          onClick: () => {
            if (!this.state.loggedIn) {
              this.props.onLogin();
              return;
            }
            this.props.onCart?.();
          },
        },
        React.createElement("span", { className: "landingCartIcon" }, "🛒"),
        React.createElement("span", { className: "landingCartLabel" }, "My Cart"),
        React.createElement("span", { className: "landingCartCount" }, String(this.state.cartCount))
      ),
      React.createElement(
        "button",
        {
          className: "landingHeaderBtn landingHeaderBtnPlain landingLogoutBtn",
          type: "button",
          onClick: () => this.logout(),
        },
        "Logout"
      )
    );
  }

  public render(): ReactNode {
    const navItems = CUSTOMER_NAV_ITEMS;
    const activeNav = this.props.activeNav ?? "Home";

    return React.createElement(
      "div",
      { className: "landingSiteHeader" },
      React.createElement(
        "div",
        { className: "landingTopBar", key: "top-bar" },
        React.createElement(
          "div",
          { className: "landingContainer landingTopBarInner" },
          React.createElement(
            "span",
            { className: "landingTopBarText" },
            "Free Delivery on orders above ₹499"
          ),
          React.createElement(
            "div",
            { className: "landingTopBarLinks" },
            ["Track Order", "Contact Us", "Help"].map((label, index) =>
              React.createElement(
                "span",
                { className: "landingTopBarLink", key: "link-" + index },
                label
              )
            )
          )
        )
      ),
      React.createElement(
        "div",
        { className: "landingHeader", key: "main-header" },
        React.createElement(
          "div",
          { className: "landingHeaderInner", key: "header-inner" },
          React.createElement(
            "div",
            { className: "landingContainer landingHeaderMain", key: "header-main" },
            React.createElement(
              "a",
              { className: "landingBrandLink", href: "#" },
              React.createElement("img", {
                src: "/images/freshmart.png",
                alt: "FreshMart",
                className: "landingBrandLogo",
              })
            ),
            React.createElement(
              "div",
              { className: "landingSearchArea" },
              React.createElement(
                "div",
                { className: "landingLocationBox" },
                React.createElement("span", { className: "landingLocationLabel" }, "Deliver to"),
                React.createElement(
                  "select",
                  {
                    className:
                      "landingLocationSelect" +
                      (this.state.locationLoading ? " landingLocationSelectLoading" : ""),
                    value: this.state.locationLoading
                      ? "__current__"
                      : this.state.deliveryLocation,
                    onChange: this.handleLocationChange,
                    disabled: this.state.locationLoading,
                  },
                  React.createElement(
                    "option",
                    { value: "__current__", key: "current" },
                    this.state.locationLoading
                      ? "Detecting location..."
                      : "📍 Current Location"
                  ),
                  DELIVERY_CITIES.map((city) =>
                    React.createElement("option", { value: city, key: city }, city)
                  ),
                  !DELIVERY_CITIES.includes(this.state.deliveryLocation) &&
                  !this.state.locationLoading
                    ? React.createElement(
                        "option",
                        {
                          value: this.state.deliveryLocation,
                          key: "detected",
                        },
                        this.state.deliveryLocation
                      )
                    : null
                )
              ),
              React.createElement(
                "div",
                { className: "landingSearchWrap" },
                React.createElement(
                  "div",
                  { className: "landingSearchField" },
                  React.createElement("input", {
                    className: "landingSearchInput",
                    placeholder: " ",
                    value: this.state.searchQuery,
                    onChange: this.handleSearchInputChange,
                    onFocus: this.handleSearchFocus,
                    onBlur: this.handleSearchBlur,
                    onKeyDown: this.handleSearchKeyDown,
                    "aria-label": "Search groceries",
                  }),
                  !this.state.searchQuery && !this.state.searchFocused
                    ? React.createElement(
                        "span",
                        { className: "landingSearchAnimatedPlaceholder" },
                        SEARCH_PLACEHOLDERS[this.state.searchPlaceholderIndex]
                      )
                    : null,
                  this.renderSearchSuggestions()
                ),
                React.createElement(
                  "button",
                  {
                    className: "landingSearchBtn",
                    type: "button",
                    onClick: this.submitSearch,
                  },
                  "Search"
                )
              )
            ),
            this.renderHeaderActions()
          ),
          React.createElement(
            "div",
            { className: "landingNavBar", key: "nav-bar" },
            React.createElement(
              "div",
              { className: "landingContainer landingNavBarBox" },
              React.createElement(
                "nav",
                {
                  className: "landingNavRow",
                  "aria-label": "Main navigation",
                },
              navItems.map((item, index) =>
                React.createElement(
                  "button",
                  {
                    className:
                      "landingNavItemWrap" +
                      (activeNav === item ? " landingNavItemActive" : ""),
                    key: "nav-" + index,
                    type: "button",
                    onClick: () => {
                      if (this.props.onNavigate) {
                        this.props.onNavigate(item);
                        return;
                      }
                      if (item === "Home") {
                        return;
                      }
                    },
                  },
                  React.createElement("span", { className: "landingNavLabel" }, item)
                )
              )
            )
          )
        )
      )
    )
    );
  }
}

class _LandingPageState extends ObservableComponent<LandingPageProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public categories: PublicCategoryRecord[] = [];
  public categoriesLoading = true;
  public products: PublicProductRecord[] = [];
  public productsLoading = true;
  public selectedCategoryId: number | null = null;
  public searchQuery = "";
  public showAdPopup = false;
  private customerWasLoggedIn = false;
  private sessionUnsub: (() => void) | null = null;

  public constructor(props: LandingPageProps) {
    super(props);
    this.initState();
  }

  public initState() {
    super.initState();
    this.enableBuild = true;
    this.on(
      [
        "categories",
        "categoriesLoading",
        "products",
        "productsLoading",
        "selectedCategoryId",
        "searchQuery",
        "showAdPopup",
      ],
      this.rebuild
    );
  }

  public componentDidMount(): void {
    super.componentDidMount();
    const pendingSearch = CustomerSearchSession.getQuery();
    if (pendingSearch) {
      this.searchQuery = pendingSearch;
      this.fire("searchQuery", this);
      if (typeof window !== "undefined") {
        window.setTimeout(() => {
          document
            .getElementById(LANDING_PRODUCTS_SECTION_ID)
            ?.scrollIntoView({ behavior: "smooth", block: "start" });
        }, 160);
      }
    }
    this.customerWasLoggedIn = CustomerSession.isActive();
    this.sessionUnsub = CustomerSession.onChange(() => {
      const isLoggedIn = CustomerSession.isActive();
      if (isLoggedIn && !this.customerWasLoggedIn) {
        this.loadProducts();
      }
      this.customerWasLoggedIn = isLoggedIn;
    });
    this.loadCategories();
    this.loadProducts();
    this.openAdPopupIfNeeded();
  }

  private openAdPopupIfNeeded(): void {
    if (typeof window === "undefined") {
      return;
    }
    if (window.sessionStorage.getItem(LANDING_AD_DISMISSED_KEY) === "1") {
      return;
    }
    window.setTimeout(() => {
      this.showAdPopup = true;
      this.fire("showAdPopup", this);
    }, 400);
  }

  private closeAdPopup = (): void => {
    this.showAdPopup = false;
    this.fire("showAdPopup", this);
    if (typeof window !== "undefined") {
      window.sessionStorage.setItem(LANDING_AD_DISMISSED_KEY, "1");
    }
  };

  private renderAdPopup(): ReactNode {
    if (!this.showAdPopup) {
      return null;
    }

    return React.createElement(
      "div",
      {
        className: "landingAdOverlay",
        role: "dialog",
        "aria-modal": "true",
        "aria-label": "Promotion",
        onClick: (event: React.MouseEvent) => {
          if (event.target === event.currentTarget) {
            this.closeAdPopup();
          }
        },
      },
      React.createElement(
        "div",
        { className: "landingAdPanel" },
        React.createElement(
          "button",
          {
            className: "landingAdCloseBtn",
            type: "button",
            "aria-label": "Close promotion",
            onClick: this.closeAdPopup,
          },
          "×"
        ),
        React.createElement("img", {
          className: "landingAdImage",
          src: LANDING_AD_IMAGE,
          alt: "FreshMart promotion",
        })
      )
    );
  }

  public componentWillUnmount(): void {
    this.sessionUnsub?.();
    this.sessionUnsub = null;
    super.componentWillUnmount();
  }

  private categoryImage(category: PublicCategoryRecord): string {
    if (category.imageUrl) {
      return category.imageUrl;
    }
    const code = (category.code ?? "").trim().toUpperCase();
    const name = (category.name ?? "").trim().toLowerCase();

    switch (code) {
      case "DAIRY":
        return CAT_IMG + "dairy.jpg";
      case "DRY":
        return CAT_IMG + "dry-goods.jpg";
      case "FRZ":
        return CAT_IMG + "frozen.jpg";
      case "HOUSE":
        return CAT_IMG + "household.jpg";
      case "PERS":
        return CAT_IMG + "personal-care.jpg";
      case "PROD":
        return CAT_IMG + "produce.jpg";
      case "BEV":
        return CAT_IMG + "snacks-beverages.jpg";
      case "STAPLES":
        return CAT_IMG + "staples.jpg";
      default:
        break;
    }

    if (name.includes("dairy") || name.includes("milk")) {
      return CAT_IMG + "dairy.jpg";
    }
    if (name.includes("produce") || name.includes("fruit") || name.includes("vegetable")) {
      return CAT_IMG + "produce.jpg";
    }
    if (name.includes("dry")) {
      return CAT_IMG + "dry-goods.jpg";
    }
    if (name.includes("staple") || name.includes("atta") || name.includes("rice") || name.includes("dal")) {
      return CAT_IMG + "staples.jpg";
    }
    if (name.includes("frozen")) {
      return CAT_IMG + "frozen.jpg";
    }
    if (name.includes("snack") || name.includes("beverage") || name.includes("drink")) {
      return CAT_IMG + "snacks-beverages.jpg";
    }
    if (name.includes("personal care") || name.includes("beauty")) {
      return CAT_IMG + "personal-care.jpg";
    }
    if (name.includes("household") || name.includes("cleaning")) {
      return CAT_IMG + "household.jpg";
    }

    return DEFAULT_CATEGORY_IMAGE;
  }

  private findCategoryForSlot(slot: FeaturedCategorySlot): PublicCategoryRecord | undefined {
    for (const category of this.categories) {
      const haystack = `${category.name ?? ""} ${category.code ?? ""} ${
        category.description ?? ""
      }`.toLowerCase();
      for (const pattern of slot.matchPatterns) {
        if (haystack.includes(pattern)) {
          return category;
        }
      }
    }
    return undefined;
  }

  private featuredCategoryImage(slot: FeaturedCategorySlot): string {
    return slot.fallbackImage;
  }

  public setCategories(val: PublicCategoryRecord[]): void {
    this.categories = val;
    this.fire("categories", this);
  }

  public setCategoriesLoading(val: boolean): void {
    if (this.categoriesLoading === val) {
      return;
    }
    this.categoriesLoading = val;
    this.fire("categoriesLoading", this);
  }

  public loadCategories = async (): Promise<void> => {
    this.setCategoriesLoading(true);
    try {
      const items = await LandingCatalogApi.getPublicCategories();
      this.setCategories(items);
    } catch (error) {
      console.log("Failed to load landing categories:", error);
      this.setCategories([]);
    } finally {
      this.setCategoriesLoading(false);
    }
  };

  public setProducts(val: PublicProductRecord[]): void {
    this.products = val;
    this.fire("products", this);
  }

  public setProductsLoading(val: boolean): void {
    if (this.productsLoading === val) {
      return;
    }
    this.productsLoading = val;
    this.fire("productsLoading", this);
  }

  public loadProducts = async (): Promise<void> => {
    this.setProductsLoading(true);
    try {
      const items = await LandingCatalogApi.getPublicProducts();
      CustomerCatalogCache.setProducts(items);
      this.setProducts(items);
    } catch (error) {
      console.log("Failed to load landing products:", error);
      this.setProducts([]);
    } finally {
      this.setProductsLoading(false);
    }
  };

  public get filteredProducts(): PublicProductRecord[] {
    let list = this.products;

    if (this.selectedCategoryId != null) {
      list = list.filter((product) => product.categoryId === this.selectedCategoryId);
    }

    if (this.searchQuery.trim()) {
      list = CustomerSearchSession.filterProducts(list, this.searchQuery);
    }

    return list;
  }

  public get selectedCategoryName(): string {
    if (this.selectedCategoryId == null) {
      return "";
    }
    const match = this.categories.find((cat) => cat.id === this.selectedCategoryId);
    return match?.name ?? "Category";
  }

  private formatPrice(value: number): string {
    if (!Number.isFinite(value) || value <= 0) {
      return "—";
    }
    return "₹" + (value % 1 === 0 ? value.toFixed(0) : value.toFixed(2));
  }

  private productImage(product: PublicProductRecord): string {
    if (product.imageUrls && product.imageUrls.length > 0) {
      return product.imageUrls[0];
    }
    if (product.imageUrl) {
      return product.imageUrl;
    }
    if (product.categoryCode || product.categoryName) {
      return this.categoryImage({
        id: product.categoryId ?? 0,
        name: product.categoryName ?? "",
        code: product.categoryCode ?? "",
        description: "",
        status: "Active",
      });
    }
    return DEFAULT_CATEGORY_IMAGE;
  }

  public openProductDetail = (product: PublicProductRecord): void => {
    this.navigator.pushProductDetailPage({
      user: this.props.user,
      productId: product.id,
      target: "main",
      replace: false,
    });
  };

  public onCategorySelect = (category: PublicCategoryRecord): void => {
    this.navigator.pushCategoryProductsPage({
      user: this.props.user,
      categoryId: category.id,
      categoryName: category.name,
      target: "main",
      replace: false,
    });
  };

  public clearCategoryFilter = (): void => {
    this.selectedCategoryId = null;
    this.fire("selectedCategoryId", this);
  };

  public handleSearch = (query: string, scroll = false): void => {
    const trimmed = query.trim();

    if (!trimmed) {
      this.clearSearch();
      return;
    }

    if (scroll) {
      void this.submitSearchQuery(trimmed);
      return;
    }
  };

  private submitSearchQuery = async (query: string): Promise<void> => {
    const catalog =
      this.products.length > 0 ? this.products : await CustomerCatalogCache.ensureLoaded();
    const matches = CustomerSearchSession.filterProducts(catalog, query);

    if (matches.length === 1) {
      this.clearSearch();
      this.openProductDetail(matches[0]);
      return;
    }

    this.searchQuery = query;
    this.selectedCategoryId = null;
    CustomerSearchSession.setQuery(query);
    this.fire("searchQuery", this);
    this.fire("selectedCategoryId", this);

    if (typeof document !== "undefined") {
      window.setTimeout(() => {
        document
          .getElementById(LANDING_PRODUCTS_SECTION_ID)
          ?.scrollIntoView({ behavior: "smooth", block: "start" });
      }, 120);
    }
  };

  public handleProductSelect = (product: PublicProductRecord): void => {
    this.clearSearch();
    this.openProductDetail(product);
  };

  public clearSearch = (): void => {
    this.searchQuery = "";
    this.selectedCategoryId = null;
    CustomerSearchSession.clear();
    this.fire("searchQuery", this);
    this.fire("selectedCategoryId", this);
  };

  private renderSectionHead(
    title: string,
    subtitle?: string,
    sectionKey = "section-head"
  ): ReactNode {
    return React.createElement(
      "div",
      { className: "landingSectionHead", key: sectionKey },
      React.createElement("h2", { className: "landingSectionTitle", key: "title" }, title),
      subtitle
        ? React.createElement(
            "p",
            { className: "landingSectionSubtitle", key: "sub" },
            subtitle
          )
        : null
    );
  }

  private renderTrustIcon(id: string): ReactNode {
    const common = {
      width: 22,
      height: 22,
      viewBox: "0 0 24 24",
      fill: "none",
      stroke: "currentColor",
      strokeWidth: 2,
      strokeLinecap: "round" as const,
      strokeLinejoin: "round" as const,
      "aria-hidden": true,
    };

    switch (id) {
      case "delivery":
        return React.createElement(
          "svg",
          common,
          React.createElement("path", { d: "M13 2L3 14h7l-1 8 10-12h-7l1-8z" })
        );
      case "free":
        return React.createElement(
          "svg",
          common,
          React.createElement("rect", { x: 1, y: 3, width: 15, height: 13, rx: 2 }),
          React.createElement("path", { d: "M16 8h4l3 5v5h-7V8z" }),
          React.createElement("circle", { cx: 5.5, cy: 18.5, r: 2.5 }),
          React.createElement("circle", { cx: 18.5, cy: 18.5, r: 2.5 })
        );
      case "quality":
        return React.createElement(
          "svg",
          common,
          React.createElement("path", { d: "M12 2l3 7h7l-5.5 4.5 2 7L12 17l-6.5 3.5 2-7L2 9h7z" })
        );
      case "returns":
        return React.createElement(
          "svg",
          common,
          React.createElement("path", { d: "M3 7v6h6" }),
          React.createElement("path", { d: "M21 17a9 9 0 00-15-5.3L3 13" })
        );
      default:
        return React.createElement(
          "svg",
          common,
          React.createElement("rect", { x: 3, y: 11, width: 18, height: 11, rx: 2 }),
          React.createElement("path", { d: "M7 11V7a5 5 0 0110 0v4" })
        );
    }
  }

  private renderTrustBar(): ReactNode {
    return React.createElement(
      "div",
      { className: "landingTrustBar", key: "trust-bar" },
      TRUST_ITEMS.map((item, index) =>
        React.createElement(
          "div",
          { className: "landingTrustItem", key: "trust-" + index },
          React.createElement(
            "span",
            { className: "landingTrustIconBox" },
            this.renderTrustIcon(item.id)
          ),
          React.createElement(
            "div",
            { className: "landingTrustCopy" },
            React.createElement("span", { className: "landingTrustTitle" }, item.title),
            React.createElement("span", { className: "landingTrustDesc" }, item.desc)
          )
        )
      )
    );
  }

  private renderPromoCards(): ReactNode {
    return React.createElement(
      "section",
      { className: "landingSection landingPromoSection", key: "promo-section" },
      this.renderSectionHead(
        "Top Offers For You",
        "Handpicked deals on everyday essentials",
        "promo-head"
      ),
      React.createElement(
        "div",
        { className: "landingPromoRow", key: "promo-row" },
        PROMO_CARDS.map((card, index) =>
          React.createElement(
            "article",
            { className: "landingPromoCard " + card.tone, key: "promo-" + index },
            React.createElement("img", {
              src: card.image,
              alt: card.title,
              className: "landingPromoImage",
            }),
            React.createElement(
              "div",
              { className: "landingPromoContent" },
              React.createElement("span", { className: "landingPromoOffer" }, card.offer),
              React.createElement("span", { className: "landingPromoTitle" }, card.title),
              React.createElement("span", { className: "landingPromoLink" }, "Shop Now →")
            )
          )
        )
      )
    );
  }

  private renderBestSellers(): ReactNode {
    const products = this.filteredProducts;
    const activeSearch = this.searchQuery.trim();
    const title = activeSearch
      ? `Results for "${activeSearch}"`
      : this.selectedCategoryId
        ? this.selectedCategoryName
        : "Best Selling Products";
    const subtitle = activeSearch
      ? `${products.length} product${products.length === 1 ? "" : "s"} found`
      : this.selectedCategoryId
        ? "Products in this category from our store"
        : "Fresh picks from our catalog this week";

    const headExtra = activeSearch
      ? React.createElement(
          "button",
          {
            className: "landingClearFilterBtn",
            type: "button",
            onClick: () => this.clearSearch(),
          },
          "Clear search"
        )
      : this.selectedCategoryId
        ? React.createElement(
            "button",
            {
              className: "landingClearFilterBtn",
              type: "button",
              onClick: () => this.clearCategoryFilter(),
            },
            "Show all products"
          )
        : null;

    return React.createElement(
      "section",
      {
        className: "landingSection landingBestSellersSection",
        id: LANDING_PRODUCTS_SECTION_ID,
        key: "bestsellers-section",
      },
      this.productsLoading
        ? React.createElement(
            React.Fragment,
            null,
            React.createElement(
              "div",
              { className: "landingSectionHead landingProductsHead" },
              React.createElement(
                "div",
                { className: "landingProductsHeadText" },
                React.createElement("h2", { className: "landingSectionTitle" }, title),
                React.createElement("p", { className: "landingSectionSubtitle" }, subtitle)
              )
            ),
            React.createElement(
              "p",
              { className: "landingSectionSubtitle landingCategoriesLoading" },
              "Loading products..."
            )
          )
        : products.length === 0
          ? React.createElement(
              React.Fragment,
              null,
              React.createElement(
                "div",
                { className: "landingSectionHead landingProductsHead" },
                React.createElement(
                  "div",
                  { className: "landingProductsHeadText" },
                  React.createElement("h2", { className: "landingSectionTitle" }, title),
                  React.createElement("p", { className: "landingSectionSubtitle" }, subtitle)
                ),
                headExtra
              ),
              React.createElement(
                "p",
                { className: "landingSectionSubtitle" },
                activeSearch
                  ? `No products found for "${activeSearch}". Try another keyword.`
                  : this.selectedCategoryId
                    ? "No products in this category yet."
                    : "No products available yet. Check back soon."
              )
            )
          : activeSearch
            ? React.createElement(
                React.Fragment,
                null,
                React.createElement(
                  "div",
                  { className: "landingSectionHead landingProductsHead" },
                  React.createElement(
                    "div",
                    { className: "landingProductsHeadText" },
                    React.createElement("h2", { className: "landingSectionTitle" }, title),
                    React.createElement("p", { className: "landingSectionSubtitle" }, subtitle)
                  ),
                  headExtra
                ),
                React.createElement(CustomerLiveProductGrid, {
                  products,
                  onProductClick: this.openProductDetail,
                  onLogin: this.goLogin,
                })
              )
            : React.createElement(LandingProductCarousel, {
                products,
                title,
                subtitle,
                headExtra,
                onProductClick: this.openProductDetail,
                onLogin: this.goLogin,
              })
    );
  }

  private renderFeaturedCategories(): ReactNode {
    return React.createElement(
      "section",
      { className: "landingSection landingFeaturedCatSection", key: "featured-categories" },
      React.createElement(
        "div",
        { className: "landingFeaturedCatRow", key: "featured-categories-row" },
        FEATURED_CATEGORY_SLOTS.map((slot) => {
          const matched = this.findCategoryForSlot(slot);
          const image = this.featuredCategoryImage(slot);
          const label = matched?.name ?? slot.categoryLabel;
          return React.createElement(
            "article",
            {
              className: "landingFeaturedCatCard " + slot.tone,
              key: "featured-" + slot.key,
            },
            React.createElement(
              "div",
              { className: "landingFeaturedCatBody" },
              React.createElement("h3", { className: "landingFeaturedCatTitle" }, slot.title),
              React.createElement("p", { className: "landingFeaturedCatSubtitle" }, slot.subtitle),
              React.createElement(
                "button",
                {
                  className: "landingFeaturedCatBtn",
                  type: "button",
                  onClick: () => this.goLogin(),
                  "aria-label": "Order " + label,
                },
                "Order Now"
              )
            ),
            React.createElement(
              "div",
              { className: "landingFeaturedCatMedia" },
              React.createElement("img", {
                src: image,
                alt: label,
                className: "landingFeaturedCatImage",
                loading: "lazy",
              })
            )
          );
        })
      )
    );
  }

  private renderRoundCategories(): ReactNode {
    if (this.categoriesLoading) {
      return React.createElement(
        "section",
        { className: "landingSection landingRoundSection", key: "categories-section" },
        this.renderSectionHead(
          "Shop by Categories",
          "Browse our most popular aisles",
          "categories-head"
        ),
        React.createElement(
          "p",
          { className: "landingSectionSubtitle landingCategoriesLoading" },
          "Loading categories..."
        )
      );
    }

    if (this.categories.length === 0) {
      return null;
    }

    return React.createElement(
      "section",
      { className: "landingSection landingRoundSection", key: "categories-section" },
      this.renderSectionHead(
        "Shop by Categories",
        "Browse our most popular aisles",
        "categories-head"
      ),
      React.createElement(
        "div",
        { className: "landingRoundCatRow", key: "categories-row" },
        this.categories.map((cat) =>
          React.createElement(
            "div",
            {
              className: "landingRoundCatItem",
              key: "round-cat-" + cat.id,
              onClick: () => this.onCategorySelect(cat),
              onKeyDown: (event: React.KeyboardEvent) => {
                if (event.key === "Enter" || event.key === " ") {
                  event.preventDefault();
                  this.onCategorySelect(cat);
                }
              },
              role: "button",
              tabIndex: 0,
              "aria-label": "Browse " + cat.name,
            },
            React.createElement(
              "div",
              { className: "landingRoundCatCircle" },
              React.createElement("img", {
                src: this.categoryImage(cat),
                alt: cat.name,
                className: "landingRoundCatImage",
              })
            ),
            React.createElement("span", { className: "landingRoundCatLabel" }, cat.name)
          )
        )
      )
    );
  }

  private renderImsFeatures(): ReactNode {
    return React.createElement(
      "section",
      { className: "landingSection landingImsSection", key: "ims" },
      this.renderSectionHead(
        "Inventory Management Platform",
        "Power your retail operations with FreshMart IMS",
        "ims-head"
      ),
      React.createElement(
        "div",
        { className: "landingFeatureRow" },
        IMS_FEATURES.map((feature, index) =>
          React.createElement(
            "article",
            { className: "landingFeatureCard", key: "feature-" + index },
            React.createElement("h3", { className: "landingFeatureTitle" }, feature.title),
            React.createElement("p", { className: "landingFeatureDesc" }, feature.desc)
          )
        )
      ),
      React.createElement(
        "div",
        { className: "landingImsCtaWrap" },
        React.createElement(
          "button",
          {
            className: "landingImsCtaBtn",
            type: "button",
            onClick: () => this.goLogin(),
          },
          "Get Started with IMS"
        )
      )
    );
  }

  public render(): ReactNode {
    return ui.Container({
      child: React.createElement(
        "div",
        { className: "landingPageShell" },
        React.createElement(LandingSiteHeader, {
          user: this.props.user,
          activeNav: "Home",
          onNavigate: this.handleNavigate,
          onLogin: this.goLogin,
          onSignup: this.goSignup,
          onCart: this.goCart,
          onSearch: this.handleSearch,
          onProductSelect: this.handleProductSelect,
          onProfileMenu: this.handleProfileMenu,
          key: "site-header",
        }),
        React.createElement(
          "div",
          { className: "landingScroll", key: "landing-scroll" },
          React.createElement(
            "div",
            { className: "landingContainer landingMain", key: "main" },
            React.createElement(LandingHeroCarousel, {
              onCtaClick: this.goSignup,
              key: "hero-carousel",
            }),
            this.renderTrustBar(),
            this.renderPromoCards(),
            this.renderFeaturedCategories(),
            this.renderRoundCategories(),
            this.renderBestSellers(),
            this.renderImsFeatures()
          ),
          React.createElement(LandingFooter, { categories: this.categories, key: "footer" })
        ),
        this.renderAdPopup()
      ),
      className: ui.join(
        "LandingPage landingPageRoot ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }

  public goLogin = (): void => {
    this.navigator.pushLoginPage({ customerMode: true, target: "main" });
  };

  public goSignup = (): void => {
    this.navigator.pushSignupPage({ customerMode: true, target: "main" });
  };

  public goCart = (): void => {
    this.navigator.pushCustomerCartPage({
      user: this.props.user,
      target: "main",
      replace: false,
    });
  };

  public handleProfileMenu = (tab: CustomerAccountTab): void => {
    CustomerProfileNavigation.openTab(this.navigator, tab, this.props.user);
  };

  public handleNavigate = (item: string): void => {
    CustomerNavigation.navigate(this.navigator, item, this.props.user);
  };

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function LandingPage(props: LandingPageProps) {
  return React.createElement(_LandingPageState, props);
}
