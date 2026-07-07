const ROLE_LABELS: Record<string, string> = {
  OrganizationAdmin: "Admin",
  StoreManager: "Store Manager",
  WarehouseManager: "Warehouse Manager",
  PurchaseManager: "Purchase Manager",
  InventoryClerk: "Inventory Clerk",
  SalesStaff: "Sales Staff",
  Accountant: "Accountant",
  Viewer: "Customer",
};

export function formatRoleLabel(role: string): string {
  if (!role) {
    return "";
  }

  const trimmed = role.trim();
  return ROLE_LABELS[trimmed] ?? trimmed.replace(/([A-Z])/g, " $1").trim();
}

export const STAFF_ROLE_OPTIONS = [
  { value: "StoreManager", label: "Store Manager" },
  { value: "WarehouseManager", label: "Warehouse Manager" },
  { value: "PurchaseManager", label: "Purchase Manager" },
  { value: "InventoryClerk", label: "Inventory Clerk" },
  { value: "SalesStaff", label: "Sales Staff" },
  { value: "Accountant", label: "Accountant" },
  { value: "OrganizationAdmin", label: "Admin" },
];

export const ROLE_FILTER_OPTIONS = [
  { value: "", label: "All roles" },
  ...STAFF_ROLE_OPTIONS,
];
