import Env from "../classes/Env";
import LocalDataStore from "./LocalDataStore";

export interface StaffStoreAssignment {
  id: number;
  name: string;
  code: string;
}

export interface StaffProfileRecord {
  id: number;
  displayName: string;
  phone: string;
  email: string;
  appRole: string;
  avatarUrl: string;
  assignedStores: StaffStoreAssignment[];
}

type ProfileListener = () => void;

let cachedProfile: StaffProfileRecord | null = null;

export default class StaffProfileApi {
  private static listeners: ProfileListener[] = [];

  public static onChange(listener: ProfileListener): () => void {
    StaffProfileApi.listeners.push(listener);
    return () => {
      StaffProfileApi.listeners = StaffProfileApi.listeners.filter((item) => item !== listener);
    };
  }

  private static notify(): void {
    StaffProfileApi.listeners.forEach((listener) => listener());
  }

  public static getCachedProfile(): StaffProfileRecord | null {
    return cachedProfile;
  }

  public static clearCache(): void {
    cachedProfile = null;
    StaffProfileApi.notify();
  }

  public static isCachedForEmail(email: string | undefined | null): boolean {
    if (!cachedProfile || !email) {
      return false;
    }
    return (
      cachedProfile.email.trim().toLowerCase() === email.trim().toLowerCase()
    );
  }

  private static async authHeaders(): Promise<Record<string, string>> {
    const token = await LocalDataStore.get().getToken();
    const headers: Record<string, string> = { Accept: "application/json" };
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }
    return headers;
  }

  private static resolveUrl(path: string): string {
    if (!path) {
      return "";
    }
    if (path.startsWith("http://") || path.startsWith("https://")) {
      return path;
    }
    const base = Env.get().resolvedHttpUrl.replace(/\/$/, "");
    return `${base}${path.startsWith("/") ? path : `/${path}`}`;
  }

  private static mapProfile(data: Record<string, unknown>): StaffProfileRecord {
    const storesRaw: unknown[] = Array.isArray(data.assignedStores) ? data.assignedStores : [];
    const assignedStores: StaffStoreAssignment[] = Array.from(
      storesRaw,
      (item): StaffStoreAssignment => {
        const store = item as Record<string, unknown>;
        return {
          id: Number(store.id ?? 0),
          name: String(store.name ?? ""),
          code: String(store.code ?? ""),
        };
      }
    );

    const avatarPath = String(data.avatarUrl ?? "");
    return {
      id: Number(data.id ?? 0),
      displayName: String(data.displayName ?? ""),
      phone: String(data.phone ?? ""),
      email: String(data.email ?? ""),
      appRole: String(data.appRole ?? ""),
      avatarUrl: avatarPath ? StaffProfileApi.resolveUrl(avatarPath) : "",
      assignedStores,
    };
  }

  public static async getProfile(): Promise<StaffProfileRecord | null> {
    const headers = await StaffProfileApi.authHeaders();
    const response = await fetch(`${Env.get().resolvedHttpUrl}/api/staff/profile`, {
      method: "GET",
      headers,
    });

    if (!response.ok) {
      return null;
    }

    const data = (await response.json()) as Record<string, unknown>;
    cachedProfile = StaffProfileApi.mapProfile(data);
    StaffProfileApi.notify();
    return cachedProfile;
  }

  public static async saveProfile(
    displayName: string,
    phone: string
  ): Promise<StaffProfileRecord | null> {
    const headers = await StaffProfileApi.authHeaders();
    headers["Content-Type"] = "application/json";

    const response = await fetch(`${Env.get().resolvedHttpUrl}/api/staff/profile`, {
      method: "POST",
      headers,
      body: JSON.stringify({
        displayName: displayName.trim(),
        phone: phone.trim(),
      }),
    });

    if (!response.ok) {
      throw new Error(`Request failed (${response.status})`);
    }

    const data = (await response.json()) as Record<string, unknown>;
    cachedProfile = StaffProfileApi.mapProfile(data);
    StaffProfileApi.notify();
    return cachedProfile;
  }

  public static async uploadAvatar(file: File): Promise<StaffProfileRecord | null> {
    const headers = await StaffProfileApi.authHeaders();
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch(`${Env.get().resolvedHttpUrl}/api/staff/profile/avatar`, {
      method: "POST",
      headers,
      body: formData,
    });

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || `Avatar upload failed (${response.status})`);
    }

    const data = (await response.json()) as Record<string, unknown>;
    cachedProfile = StaffProfileApi.mapProfile(data);
    StaffProfileApi.notify();
    return cachedProfile;
  }

  public static async removeAvatar(): Promise<StaffProfileRecord | null> {
    const headers = await StaffProfileApi.authHeaders();
    const response = await fetch(`${Env.get().resolvedHttpUrl}/api/staff/profile/avatar`, {
      method: "DELETE",
      headers,
    });

    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || `Avatar remove failed (${response.status})`);
    }

    const data = (await response.json()) as Record<string, unknown>;
    cachedProfile = StaffProfileApi.mapProfile(data);
    StaffProfileApi.notify();
    return cachedProfile;
  }
}
