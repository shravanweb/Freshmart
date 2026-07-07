export interface SavedAddress {
  id: string;
  label: string;
  city: string;
  address: string;
  phone: string;
  isDefault: boolean;
}

const ADDRESSES_KEY = "freshmart_saved_addresses";
const LEGACY_LOCATION_KEY = "freshmart_delivery_location";
const LEGACY_ADDRESS_KEY = "freshmart_delivery_address";
const LEGACY_PHONE_KEY = "freshmart_delivery_phone";

function newId(): string {
  return "addr-" + Date.now().toString(36) + Math.random().toString(36).slice(2, 7);
}

function syncLegacyDefault(address: SavedAddress | null): void {
  if (typeof window === "undefined") {
    return;
  }
  if (!address) {
    return;
  }
  localStorage.setItem(LEGACY_LOCATION_KEY, address.city);
  localStorage.setItem(LEGACY_ADDRESS_KEY, address.address);
  localStorage.setItem(LEGACY_PHONE_KEY, address.phone);
}

export default class CustomerAddressStorage {
  public static getAddresses(): SavedAddress[] {
    if (typeof window === "undefined") {
      return [];
    }

    const raw = localStorage.getItem(ADDRESSES_KEY);
    if (raw) {
      try {
        const parsed = JSON.parse(raw) as unknown;
        if (Array.isArray(parsed)) {
          const items: SavedAddress[] = [];
          for (const entry of parsed) {
            if (!entry || typeof entry !== "object") {
              continue;
            }
            const row = entry as Record<string, unknown>;
            const address = String(row.address ?? "").trim();
            if (!address) {
              continue;
            }
            items.push({
              id: String(row.id ?? newId()),
              label: String(row.label ?? "Home"),
              city: String(row.city ?? "Hyderabad"),
              address,
              phone: String(row.phone ?? ""),
              isDefault: Boolean(row.isDefault),
            });
          }
          if (items.length > 0) {
            if (!items.some((item) => item.isDefault)) {
              items[0].isDefault = true;
            }
            return items;
          }
        }
      } catch {
        // fall through to legacy migration
      }
    }

    return CustomerAddressStorage.migrateLegacy();
  }

  private static migrateLegacy(): SavedAddress[] {
    if (typeof window === "undefined") {
      return [];
    }

    const address = localStorage.getItem(LEGACY_ADDRESS_KEY)?.trim() ?? "";
    if (!address) {
      return [];
    }

    const migrated: SavedAddress = {
      id: newId(),
      label: "Home",
      city: localStorage.getItem(LEGACY_LOCATION_KEY) || "Hyderabad",
      address,
      phone: localStorage.getItem(LEGACY_PHONE_KEY) ?? "",
      isDefault: true,
    };
    CustomerAddressStorage.saveAddresses([migrated]);
    return [migrated];
  }

  private static saveAddresses(addresses: SavedAddress[]): void {
    if (typeof window === "undefined") {
      return;
    }
    localStorage.setItem(ADDRESSES_KEY, JSON.stringify(addresses));
    const defaultAddress = addresses.find((item) => item.isDefault) ?? addresses[0] ?? null;
    syncLegacyDefault(defaultAddress);
  }

  public static getDefaultAddress(): SavedAddress | null {
    const addresses = CustomerAddressStorage.getAddresses();
    return addresses.find((item) => item.isDefault) ?? addresses[0] ?? null;
  }

  public static upsertAddress(
    input: Omit<SavedAddress, "id" | "isDefault"> & { id?: string; isDefault?: boolean }
  ): SavedAddress[] {
    const addresses = CustomerAddressStorage.getAddresses();
    const id = input.id ?? newId();
    const isDefault = input.isDefault ?? addresses.length === 0;
    const next: SavedAddress = {
      id,
      label: input.label.trim() || "Home",
      city: input.city.trim() || "Hyderabad",
      address: input.address.trim(),
      phone: input.phone.trim(),
      isDefault,
    };

    const index = addresses.findIndex((item) => item.id === id);
    let updated: SavedAddress[];
    if (index >= 0) {
      updated = [...addresses];
      updated[index] = next;
    } else {
      updated = [...addresses, next];
    }

    if (isDefault) {
      updated = Array.from(
        updated.map((item) => ({ ...item, isDefault: item.id === id }))
      );
    }

    CustomerAddressStorage.saveAddresses(updated);
    return updated;
  }

  public static setDefault(id: string): SavedAddress[] {
    const updated = Array.from(
      CustomerAddressStorage.getAddresses().map((item) => ({
        ...item,
        isDefault: item.id === id,
      }))
    );
    CustomerAddressStorage.saveAddresses(updated);
    return updated;
  }

  public static deleteAddress(id: string): SavedAddress[] {
    let updated = Array.from(
      CustomerAddressStorage.getAddresses().filter((item) => item.id !== id)
    );
    if (updated.length > 0 && !updated.some((item) => item.isDefault)) {
      updated = Array.from(
        updated.map((item, index) => ({
          ...item,
          isDefault: index === 0,
        }))
      );
    }
    CustomerAddressStorage.saveAddresses(updated);
    return updated;
  }
}
