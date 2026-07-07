export interface CustomerSessionData {
  displayName: string;
  email: string;
  token: string;
}

const STORAGE_KEY = "freshmart_customer_session";

type SessionListener = () => void;

export default class CustomerSession {
  private static listeners: SessionListener[] = [];

  public static onChange(listener: SessionListener): () => void {
    CustomerSession.listeners.push(listener);

    return () => {
      CustomerSession.listeners = CustomerSession.listeners.filter(
        (item) => item !== listener
      );
    };
  }

  private static notify(): void {
    CustomerSession.listeners.forEach((listener) => listener());
  }

  public static save(data: CustomerSessionData): void {
    if (typeof window === "undefined") {
      return;
    }

    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
    CustomerSession.notify();
  }

  public static load(): CustomerSessionData | null {
    if (typeof window === "undefined") {
      return null;
    }

    const raw = localStorage.getItem(STORAGE_KEY);

    if (!raw) {
      return null;
    }

    try {
      const data = JSON.parse(raw) as CustomerSessionData;

      if (!data?.email) {
        return null;
      }

      return data;
    } catch {
      return null;
    }
  }

  public static updateDisplayName(
    displayName: string,
    silent = false
  ): void {
    const current = CustomerSession.load();

    if (!current || current.displayName === displayName) {
      return;
    }

    localStorage.setItem(
      STORAGE_KEY,
      JSON.stringify({ ...current, displayName })
    );

    if (!silent) {
      CustomerSession.notify();
    }
  }

  public static clear(): void {
    if (typeof window === "undefined") {
      return;
    }

    localStorage.removeItem(STORAGE_KEY);
    CustomerSession.notify();
  }

  public static isActive(): boolean {
    return CustomerSession.load() !== null;
  }
}
