const PROFILE_PHONE_KEY = "freshmart_profile_phone";
const PROFILE_AVATAR_KEY = "freshmart_profile_avatar";
const AVATAR_BY_EMAIL_PREFIX = "freshmart_avatar_email:";
const LEGACY_PHONE_KEY = "freshmart_delivery_phone";
const PHONE_BY_EMAIL_PREFIX = "freshmart_phone_email:";

type ProfileListener = () => void;

export default class CustomerProfileStorage {
  private static listeners: ProfileListener[] = [];

  public static onChange(listener: ProfileListener): () => void {
    CustomerProfileStorage.listeners.push(listener);
    return () => {
      CustomerProfileStorage.listeners = CustomerProfileStorage.listeners.filter(
        (item) => item !== listener
      );
    };
  }

  private static notify(): void {
    CustomerProfileStorage.listeners.forEach((listener) => listener());
  }

  public static getPhone(): string {
    if (typeof window === "undefined") {
      return "";
    }
    const saved = localStorage.getItem(PROFILE_PHONE_KEY);
    if (saved && saved.trim()) {
      return saved.trim();
    }
    return localStorage.getItem(LEGACY_PHONE_KEY)?.trim() ?? "";
  }

  public static savePhoneForEmail(email: string, phone: string): void {
    if (typeof window === "undefined") {
      return;
    }
    const normalizedEmail = email.trim().toLowerCase();
    const normalizedPhone = phone.trim();
    if (!normalizedEmail || !normalizedPhone) {
      return;
    }
    localStorage.setItem(PHONE_BY_EMAIL_PREFIX + normalizedEmail, normalizedPhone);
    CustomerProfileStorage.savePhone(normalizedPhone);
  }

  public static getPhoneForEmail(email: string): string {
    if (typeof window === "undefined") {
      return "";
    }
    const normalizedEmail = email.trim().toLowerCase();
    if (!normalizedEmail) {
      return "";
    }
    return localStorage.getItem(PHONE_BY_EMAIL_PREFIX + normalizedEmail)?.trim() ?? "";
  }

  public static resolvePhone(email?: string): string {
    const profilePhone = CustomerProfileStorage.getPhone();
    if (profilePhone) {
      return profilePhone;
    }
    if (email) {
      const linkedPhone = CustomerProfileStorage.getPhoneForEmail(email);
      if (linkedPhone) {
        return linkedPhone;
      }
    }
    return "";
  }

  public static savePhone(phone: string): void {
    if (typeof window === "undefined") {
      return;
    }
    const value = phone.trim();
    localStorage.setItem(PROFILE_PHONE_KEY, value);
    localStorage.setItem(LEGACY_PHONE_KEY, value);
    CustomerProfileStorage.notify();
  }

  public static getAvatarUrl(): string {
    if (typeof window === "undefined") {
      return "";
    }
    return localStorage.getItem(PROFILE_AVATAR_KEY) ?? "";
  }

  public static saveAvatarUrl(dataUrl: string): void {
    if (typeof window === "undefined") {
      return;
    }
    if (dataUrl) {
      localStorage.setItem(PROFILE_AVATAR_KEY, dataUrl);
    } else {
      localStorage.removeItem(PROFILE_AVATAR_KEY);
    }
    CustomerProfileStorage.notify();
  }

  public static clearAvatar(): void {
    if (typeof window === "undefined") {
      return;
    }
    localStorage.removeItem(PROFILE_AVATAR_KEY);
    CustomerProfileStorage.notify();
  }

  public static getAvatarUrlForEmail(email: string): string {
    if (typeof window === "undefined") {
      return "";
    }
    const normalized = email?.trim().toLowerCase() ?? "";
    if (!normalized) {
      return CustomerProfileStorage.getAvatarUrl();
    }
    return localStorage.getItem(AVATAR_BY_EMAIL_PREFIX + normalized) ?? "";
  }

  public static saveAvatarUrlForEmail(email: string, dataUrl: string): void {
    if (typeof window === "undefined") {
      return;
    }
    const normalized = email?.trim().toLowerCase() ?? "";
    if (!normalized) {
      return;
    }
    const key = AVATAR_BY_EMAIL_PREFIX + normalized;
    if (dataUrl) {
      localStorage.setItem(key, dataUrl);
    } else {
      localStorage.removeItem(key);
    }
    CustomerProfileStorage.notify();
  }

  public static clearAvatarForEmail(email: string): void {
    CustomerProfileStorage.saveAvatarUrlForEmail(email, "");
  }

  public static clear(): void {
    CustomerProfileStorage.clearAvatar();
  }
}
