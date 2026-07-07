import Env from "../classes/Env";

export interface CustomerProfileRecord {
  displayName: string;
  phone: string;
  email: string;
}

export default class CustomerProfileApi {
  public static async getProfile(email: string): Promise<CustomerProfileRecord | null> {
    if (!email.trim()) {
      return null;
    }

    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(
      `${baseUrl}/api/public/customer/profile?email=${encodeURIComponent(email.trim().toLowerCase())}`,
      {
        method: "GET",
        headers: { Accept: "application/json" },
      }
    );

    if (!response.ok) {
      return null;
    }

    const data = (await response.json()) as Record<string, unknown>;
    return {
      displayName: String(data.displayName ?? ""),
      phone: String(data.phone ?? ""),
      email: String(data.email ?? email.trim().toLowerCase()),
    };
  }

  public static async saveProfile(
    email: string,
    displayName: string,
    phone: string
  ): Promise<CustomerProfileRecord | null> {
    if (!email.trim()) {
      return null;
    }

    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(`${baseUrl}/api/public/customer/profile`, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: email.trim().toLowerCase(),
        displayName: displayName.trim(),
        phone: phone.trim(),
      }),
    });

    if (!response.ok) {
      throw new Error(`Request failed (${response.status})`);
    }

    const data = (await response.json()) as Record<string, unknown>;
    return {
      displayName: String(data.displayName ?? displayName),
      phone: String(data.phone ?? phone),
      email: String(data.email ?? email.trim().toLowerCase()),
    };
  }
}
