import Env from "../classes/Env";

export interface PublicCategoryRecord {
  id: number;
  name: string;
  code: string;
  description: string;
  status: string;
  imageUrl?: string;
}

export interface PublicProductRecord {
  id: number;
  sku: string;
  name: string;
  description: string;
  barcode: string;
  sellingPrice: number;
  purchasePrice: number;
  status: string;
  categoryId?: number;
  categoryName?: string;
  categoryCode?: string;
  imageUrl?: string;
  imageUrls?: string[];
  uomSymbol?: string;
  uomName?: string;
  quantityAvailable?: number;
}

export interface PublicStoreRecord {
  id: number;
  name: string;
  code: string;
  storeType: string;
  address: string;
  phone: string;
  email: string;
  status: string;
}

function parseImageUrls(raw: Record<string, unknown>): string[] | undefined {
  const value = raw.imageUrls;
  if (!Array.isArray(value)) {
    return undefined;
  }
  const urls: string[] = [];
  for (const item of value) {
    const url = String(item);
    if (url.length > 0) {
      urls.push(url);
    }
  }
  return urls.length > 0 ? urls : undefined;
}

function parseProduct(raw: Record<string, unknown>): PublicProductRecord {
  const imageUrls = parseImageUrls(raw);
  return {
    id: Number(raw.id ?? 0),
    sku: String(raw.sku ?? ""),
    name: String(raw.name ?? ""),
    description: String(raw.description ?? ""),
    barcode: String(raw.barcode ?? ""),
    sellingPrice: Number(raw.sellingPrice ?? 0),
    purchasePrice: Number(raw.purchasePrice ?? 0),
    status: String(raw.status ?? ""),
    categoryId: raw.categoryId != null ? Number(raw.categoryId) : undefined,
    categoryName: raw.categoryName != null ? String(raw.categoryName) : undefined,
    categoryCode: raw.categoryCode != null ? String(raw.categoryCode) : undefined,
    imageUrl: raw.imageUrl != null ? String(raw.imageUrl) : undefined,
    imageUrls,
    uomSymbol: raw.uomSymbol != null ? String(raw.uomSymbol) : undefined,
    uomName: raw.uomName != null ? String(raw.uomName) : undefined,
    quantityAvailable:
      raw.quantityAvailable != null ? Number(raw.quantityAvailable) : undefined,
  };
}

export default class LandingCatalogApi {
  private static async fetchJson(url: string): Promise<unknown> {
    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(`${baseUrl}${url}`, {
      method: "GET",
      headers: { Accept: "application/json" },
    });
    if (!response.ok) {
      throw new Error(`Request failed (${response.status})`);
    }
    return response.json();
  }

  public static async getPublicCategories(): Promise<PublicCategoryRecord[]> {
    const data = (await LandingCatalogApi.fetchJson("/api/public/categories")) as {
      items?: PublicCategoryRecord[];
    };
    const items = data.items ?? [];
    return items.filter((item) => item.status === "Active");
  }

  public static async getPublicProducts(
    categoryId?: number
  ): Promise<PublicProductRecord[]> {
    const query =
      categoryId != null && categoryId > 0
        ? `?categoryId=${encodeURIComponent(String(categoryId))}`
        : "";
    const data = (await LandingCatalogApi.fetchJson(
      `/api/public/products${query}`
    )) as { items?: Record<string, unknown>[] };
    const items: Record<string, unknown>[] = Array.isArray(data.items)
      ? data.items
      : [];
    return items.map((item) => parseProduct(item)).toList();
  }

  public static async getPublicProductById(
    productId: number
  ): Promise<PublicProductRecord | null> {
    try {
      const data = (await LandingCatalogApi.fetchJson(
        `/api/public/products/${productId}`
      )) as Record<string, unknown>;
      return parseProduct(data);
    } catch {
      return null;
    }
  }

  public static async getPublicStores(): Promise<PublicStoreRecord[]> {
    const data = (await LandingCatalogApi.fetchJson("/api/public/stores")) as {
      items?: PublicStoreRecord[];
    };
    const items = data.items ?? [];
    return items.filter((item) => item.status === "Active");
  }

  public static async getNewArrivalProducts(limit = 24): Promise<PublicProductRecord[]> {
    const data = (await LandingCatalogApi.fetchJson(
      `/api/public/products/new-arrivals?limit=${encodeURIComponent(String(limit))}`
    )) as { items?: Record<string, unknown>[] };
    const items: Record<string, unknown>[] = Array.isArray(data.items) ? data.items : [];
    return items.map((item) => parseProduct(item)).toList();
  }
}
