import PageNavigator from "../classes/PageNavigator";
import User from "../models/User";
import { PublicProductRecord } from "./LandingCatalogApi";
import CustomerCatalogCache from "./CustomerCatalogCache";
import CustomerSearchSession from "./CustomerSearchSession";

const LANDING_PRODUCTS_SECTION_ID = "landing-products-section";

export default class CustomerSearchNavigation {
  public static openProduct(
    navigator: PageNavigator,
    product: PublicProductRecord,
    user?: User
  ): void {
    CustomerSearchSession.clear();
    navigator.pushProductDetailPage({
      user,
      productId: product.id,
      target: "main",
      replace: false,
    });
  }

  public static async submitSearch(
    navigator: PageNavigator,
    query: string,
    user?: User,
    scroll = true
  ): Promise<void> {
    const trimmed = query.trim();

    if (!trimmed) {
      CustomerSearchSession.clear();
      navigator.pushLandingPage({ user, target: "main", replace: false });
      return;
    }

    await CustomerCatalogCache.ensureLoaded();
    const matches = CustomerSearchSession.filterProducts(
      CustomerCatalogCache.getProducts(),
      trimmed
    );

    if (matches.length === 1) {
      CustomerSearchNavigation.openProduct(navigator, matches[0], user);
      return;
    }

    CustomerSearchSession.setQuery(trimmed);
    navigator.pushLandingPage({ user, target: "main", replace: false });

    if (scroll && typeof window !== "undefined") {
      window.setTimeout(() => {
        document
          .getElementById(LANDING_PRODUCTS_SECTION_ID)
          ?.scrollIntoView({ behavior: "smooth", block: "start" });
      }, 160);
    }
  }

  /** @deprecated Use submitSearch — kept for callers that only set session while typing */
  public static openProductSearch(
    navigator: PageNavigator,
    query: string,
    user?: User,
    scroll = true
  ): void {
    void CustomerSearchNavigation.submitSearch(navigator, query, user, scroll);
  }
}
