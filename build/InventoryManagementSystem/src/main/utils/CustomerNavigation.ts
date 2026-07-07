import PageNavigator from "../classes/PageNavigator";
import User from "../models/User";

export const CUSTOMER_NAV_ITEMS = [
  "Home",
  "Offers",
  "Best Sellers",
  "New Arrivals",
  "Stores",
] as const;

export type CustomerNavItem = (typeof CUSTOMER_NAV_ITEMS)[number];

export default class CustomerNavigation {
  public static navigate(
    navigator: PageNavigator,
    item: CustomerNavItem | string,
    user?: User
  ): void {
    switch (item) {
      case "Home":
        navigator.pushLandingPage({ user, target: "main", replace: false });
        return;
      case "New Arrivals":
        navigator.pushNewArrivalsPage({ user, target: "main", replace: false });
        return;
      case "Stores":
        navigator.pushStoresPage({ user, target: "main", replace: false });
        return;
      case "Best Sellers":
        navigator.pushLandingPage({ user, target: "main", replace: false });
        if (typeof document !== "undefined") {
          window.setTimeout(() => {
            document
              .getElementById("landing-products-section")
              ?.scrollIntoView({ behavior: "smooth", block: "start" });
          }, 120);
        }
        return;
      case "Offers":
        navigator.pushLandingPage({ user, target: "main", replace: false });
        if (typeof document !== "undefined") {
          window.setTimeout(() => {
            document
              .querySelector(".landingPromoSection")
              ?.scrollIntoView({ behavior: "smooth", block: "start" });
          }, 120);
        }
        return;
      default:
        navigator.pushLandingPage({ user, target: "main", replace: false });
    }
  }
}
