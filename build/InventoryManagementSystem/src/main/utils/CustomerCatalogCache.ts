import LandingCatalogApi, { PublicProductRecord } from "./LandingCatalogApi";
import CustomerSearchSession from "./CustomerSearchSession";

export default class CustomerCatalogCache {
  private static products: PublicProductRecord[] = [];
  private static loadPromise: Promise<PublicProductRecord[]> | null = null;

  public static setProducts(products: PublicProductRecord[]): void {
    CustomerCatalogCache.products = products;
  }

  public static getProducts(): PublicProductRecord[] {
    return CustomerCatalogCache.products;
  }

  public static async ensureLoaded(): Promise<PublicProductRecord[]> {
    if (CustomerCatalogCache.products.length > 0) {
      return CustomerCatalogCache.products;
    }

    if (!CustomerCatalogCache.loadPromise) {
      CustomerCatalogCache.loadPromise = LandingCatalogApi.getPublicProducts()
        .then((items) => {
          CustomerCatalogCache.products = items;
          return items;
        })
        .finally(() => {
          CustomerCatalogCache.loadPromise = null;
        });
    }

    return CustomerCatalogCache.loadPromise;
  }

  public static suggest(query: string, limit = 8): PublicProductRecord[] {
    const trimmed = query.trim();

    if (trimmed.length < 3) {
      return [];
    }

    return CustomerSearchSession.filterProducts(CustomerCatalogCache.products, trimmed).slice(
      0,
      limit
    );
  }
}
