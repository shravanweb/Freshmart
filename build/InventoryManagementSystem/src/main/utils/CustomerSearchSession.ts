import { PublicProductRecord } from "./LandingCatalogApi";

const SEARCH_QUERY_KEY = "freshmart_search_query";

type SearchListener = () => void;

export default class CustomerSearchSession {
  private static listeners = new Set<SearchListener>();

  public static getQuery(): string {
    if (typeof window === "undefined") {
      return "";
    }

    return localStorage.getItem(SEARCH_QUERY_KEY) ?? "";
  }

  public static setQuery(query: string): void {
    const value = query.trim();

    if (typeof window !== "undefined") {
      if (value.length > 0) {
        localStorage.setItem(SEARCH_QUERY_KEY, value);
      } else {
        localStorage.removeItem(SEARCH_QUERY_KEY);
      }
    }

    CustomerSearchSession.listeners.forEach((listener) => listener());
  }

  public static clear(): void {
    CustomerSearchSession.setQuery("");
  }

  public static onChange(listener: SearchListener): () => void {
    CustomerSearchSession.listeners.add(listener);
    return () => {
      CustomerSearchSession.listeners.delete(listener);
    };
  }

  public static matchesProduct(
    product: PublicProductRecord,
    query: string
  ): boolean {
    const normalized = query.trim().toLowerCase();

    if (!normalized) {
      return true;
    }

    const haystack = [
      product.name,
      product.sku,
      product.categoryName,
      product.description,
      product.barcode,
    ]
      .filter((value) => value && value.length > 0)
      .join(" ")
      .toLowerCase();

    const tokens = normalized.split(/\s+/).filter((token) => token.length > 0);

    return tokens.every((token) => haystack.includes(token));
  }

  public static filterProducts(
    products: PublicProductRecord[],
    query: string
  ): PublicProductRecord[] {
    const normalized = query.trim();

    if (!normalized) {
      return products;
    }

    return products.filter((product) =>
      CustomerSearchSession.matchesProduct(product, normalized)
    );
  }
}
