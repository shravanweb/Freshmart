import PageNavigator from "../classes/PageNavigator";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";
import CustomerSession from "./CustomerSession";
import CustomerProfileStorage from "./CustomerProfileStorage";
import StaffProfileApi from "./StaffProfileApi";

export default class AppLogout {
  public static navigateToLanding(navigator: PageNavigator): void {
    navigator.pushLandingPage({ target: "main", replace: true });
    if (typeof window !== "undefined") {
      window.dispatchEvent(new HashChangeEvent("hashchange"));
    }
  }

  public static signOutCustomer(navigator: PageNavigator): void {
    CustomerSession.clear();
    CustomerProfileStorage.clear();
    StaffProfileApi.clearCache();
    AppLogout.navigateToLanding(navigator);
  }

  public static async signOut(navigator: PageNavigator): Promise<void> {
    CustomerSession.clear();
    CustomerProfileStorage.clear();
    StaffProfileApi.clearCache();
    LocalDataStore.unauth();
    await LocalDataStore.get().setUser(null, null);
    GraphQLClientInit.token = null;
    AppLogout.navigateToLanding(navigator);
  }
}
