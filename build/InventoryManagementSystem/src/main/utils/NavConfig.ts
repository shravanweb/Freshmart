export type NavSection =
  | "MAIN"
  | "CATALOG"
  | "INVENTORY"
  | "PROCUREMENT"
  | "SALES"
  | "REPORTS"
  | "ADMIN"
  | "SETTINGS";

export interface NavItem {
  id: string;
  label: string;
  route: string;
  icon: string;
  section: NavSection;
  roles: string[];
}

export interface NavGroup {
  section: NavSection;
  label: string;
  items: NavItem[];
}

const SECTION_LABELS: Record<NavSection, string> = {
  MAIN: "Overview",
  CATALOG: "Catalog",
  INVENTORY: "Inventory",
  PROCUREMENT: "Procurement",
  SALES: "Sales",
  REPORTS: "Reports",
  ADMIN: "Administration",
  SETTINGS: "Settings",
};

const SECTION_ORDER: NavSection[] = [
  "MAIN",
  "CATALOG",
  "INVENTORY",
  "PROCUREMENT",
  "SALES",
  "REPORTS",
  "ADMIN",
  "SETTINGS",
];

const NAV_ITEMS: NavItem[] = [
  {
    id: "dashboard",
    label: "Dashboard",
    route: "/dashboard",
    icon: "📊",
    section: "MAIN",
    roles: [
      "OrganizationAdmin",
      "StoreManager",
      "WarehouseManager",
      "PurchaseManager",
      "InventoryClerk",
      "SalesStaff",
      "Accountant",
      "Viewer",
    ],
  },
  {
    id: "stores",
    label: "Stores",
    route: "/stores",
    icon: "🏪",
    section: "MAIN",
    roles: ["OrganizationAdmin", "StoreManager"],
  },
  {
    id: "warehouses",
    label: "Warehouses",
    route: "/warehouses",
    icon: "🏬",
    section: "MAIN",
    roles: ["OrganizationAdmin", "WarehouseManager"],
  },
  {
    id: "uom",
    label: "Units of Measure",
    route: "/catalog/uom",
    icon: "📏",
    section: "CATALOG",
    roles: ["OrganizationAdmin", "PurchaseManager"],
  },
  {
    id: "categories",
    label: "Categories",
    route: "/catalog/categories",
    icon: "📁",
    section: "CATALOG",
    roles: ["OrganizationAdmin", "PurchaseManager"],
  },
  {
    id: "products",
    label: "Products",
    route: "/catalog/products",
    icon: "📦",
    section: "CATALOG",
    roles: [
      "OrganizationAdmin",
      "StoreManager",
      "WarehouseManager",
      "PurchaseManager",
      "InventoryClerk",
      "SalesStaff",
      "Accountant",
      "Viewer",
    ],
  },
  {
    id: "stock",
    label: "Stock Levels",
    route: "/inventory/stock",
    icon: "📊",
    section: "INVENTORY",
    roles: [
      "OrganizationAdmin",
      "WarehouseManager",
      "InventoryClerk",
      "Accountant",
      "Viewer",
      "SalesStaff",
    ],
  },
  {
    id: "batches",
    label: "Batches",
    route: "/inventory/batches",
    icon: "🏷️",
    section: "INVENTORY",
    roles: ["OrganizationAdmin", "WarehouseManager", "InventoryClerk"],
  },
  {
    id: "movements",
    label: "Movements",
    route: "/inventory/movements",
    icon: "🔄",
    section: "INVENTORY",
    roles: ["OrganizationAdmin", "WarehouseManager", "Accountant", "Viewer"],
  },
  {
    id: "adjustments",
    label: "Adjustments",
    route: "/inventory/adjustments",
    icon: "✏️",
    section: "INVENTORY",
    roles: ["OrganizationAdmin", "WarehouseManager", "InventoryClerk"],
  },
  {
    id: "transfers",
    label: "Transfers",
    route: "/inventory/transfers",
    icon: "🚚",
    section: "INVENTORY",
    roles: ["OrganizationAdmin", "WarehouseManager", "InventoryClerk"],
  },
  {
    id: "alerts",
    label: "Stock Alerts",
    route: "/inventory/alerts",
    icon: "⚠️",
    section: "INVENTORY",
    roles: [
      "OrganizationAdmin",
      "WarehouseManager",
      "PurchaseManager",
      "InventoryClerk",
    ],
  },
  {
    id: "suppliers",
    label: "Suppliers",
    route: "/procurement/suppliers",
    icon: "🤝",
    section: "PROCUREMENT",
    roles: ["OrganizationAdmin", "PurchaseManager"],
  },
  {
    id: "purchase-orders",
    label: "Purchase Orders",
    route: "/procurement/purchase-orders",
    icon: "📝",
    section: "PROCUREMENT",
    roles: [
      "OrganizationAdmin",
      "PurchaseManager",
      "WarehouseManager",
      "Accountant",
      "Viewer",
    ],
  },
  {
    id: "goods-receipts",
    label: "Goods Receipts",
    route: "/procurement/goods-receipts",
    icon: "📥",
    section: "PROCUREMENT",
    roles: ["OrganizationAdmin", "WarehouseManager", "PurchaseManager"],
  },
  {
    id: "sales-orders",
    label: "Sales Orders",
    route: "/sales/orders",
    icon: "💰",
    section: "SALES",
    roles: ["OrganizationAdmin", "StoreManager", "SalesStaff", "Accountant"],
  },
  {
    id: "sales-returns",
    label: "Sales Returns",
    route: "/sales/returns",
    icon: "↩️",
    section: "SALES",
    roles: ["OrganizationAdmin", "StoreManager", "SalesStaff"],
  },
  {
    id: "report-valuation",
    label: "Stock Valuation",
    route: "/reports/stock-valuation",
    icon: "💎",
    section: "REPORTS",
    roles: ["OrganizationAdmin", "Accountant", "Viewer"],
  },
  {
    id: "report-movements",
    label: "Movement Report",
    route: "/reports/movements",
    icon: "📈",
    section: "REPORTS",
    roles: ["OrganizationAdmin", "Accountant", "WarehouseManager"],
  },
  {
    id: "report-low-stock",
    label: "Low Stock",
    route: "/reports/low-stock",
    icon: "📉",
    section: "REPORTS",
    roles: ["OrganizationAdmin", "PurchaseManager", "WarehouseManager"],
  },
  {
    id: "report-expiry",
    label: "Expiry Report",
    route: "/reports/expiry",
    icon: "⏰",
    section: "REPORTS",
    roles: ["OrganizationAdmin", "WarehouseManager"],
  },
  {
    id: "report-sales",
    label: "Sales Summary",
    route: "/reports/sales",
    icon: "🧾",
    section: "REPORTS",
    roles: ["OrganizationAdmin", "Accountant", "StoreManager", "Viewer"],
  },
  {
    id: "report-purchases",
    label: "Purchase Report",
    route: "/reports/purchases",
    icon: "🛒",
    section: "REPORTS",
    roles: ["OrganizationAdmin", "PurchaseManager", "Accountant"],
  },
  {
    id: "organizations",
    label: "Organizations",
    route: "/admin/organizations",
    icon: "🏢",
    section: "ADMIN",
    roles: ["OrganizationAdmin"],
  },
  {
    id: "users",
    label: "Users",
    route: "/admin/users",
    icon: "👥",
    section: "ADMIN",
    roles: ["OrganizationAdmin"],
  },
  {
    id: "user-invite",
    label: "Invite Users",
    route: "/admin/users/invite",
    icon: "✉️",
    section: "ADMIN",
    roles: ["OrganizationAdmin"],
  },
  {
    id: "audit",
    label: "Audit Logs",
    route: "/admin/audit-logs",
    icon: "📋",
    section: "ADMIN",
    roles: ["OrganizationAdmin", "Accountant"],
  },
  {
    id: "org-settings",
    label: "Organization",
    route: "/settings/organization",
    icon: "⚙️",
    section: "SETTINGS",
    roles: ["OrganizationAdmin"],
  },
  {
    id: "profile",
    label: "Profile",
    route: "/settings/profile",
    icon: "👤",
    section: "SETTINGS",
    roles: [
      "OrganizationAdmin",
      "StoreManager",
      "WarehouseManager",
      "PurchaseManager",
      "InventoryClerk",
      "SalesStaff",
      "Accountant",
      "Viewer",
    ],
  },
  {
    id: "notifications",
    label: "Notifications",
    route: "/settings/notifications",
    icon: "🔔",
    section: "SETTINGS",
    roles: [
      "OrganizationAdmin",
      "StoreManager",
      "WarehouseManager",
      "PurchaseManager",
      "InventoryClerk",
      "SalesStaff",
      "Accountant",
      "Viewer",
    ],
  },
];

export default class NavConfig {
  public static navForRole(role: string): NavGroup[] {
    const filtered = NAV_ITEMS.filter((item) => item.roles.includes(role));
    const groups: NavGroup[] = [];

    for (const section of SECTION_ORDER) {
      const items = filtered.filter((item) => item.section === section);

      if (items.length > 0) {
        groups.push({
          section,
          label: SECTION_LABELS[section],
          items,
        });
      }
    }

    return groups;
  }

  public static findItemByRoute(route: string): NavItem | null {
    if (!route) {
      return null;
    }

    return NAV_ITEMS.find(
      (item) =>
        item.route === route || route.startsWith(item.route + "/")
    ) ?? null;
  }

  public static isRouteActive(currentRoute: string, route: string): boolean {
    if (!currentRoute) {
      return route === "/dashboard";
    }

    if (route === "/dashboard") {
      return currentRoute === "/dashboard";
    }

    return (
      currentRoute === route || currentRoute.startsWith(route + "/")
    );
  }
}
