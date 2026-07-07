import PageNavigator from "../classes/PageNavigator";
import User from "../models/User";
import { CustomerAccountTab } from "../components/CustomerAccountPage";

export default class CustomerProfileNavigation {
  public static openTab(
    navigator: PageNavigator,
    tab: CustomerAccountTab,
    user?: User
  ): void {
    navigator.pushCustomerAccountPage({
      tab,
      user,
      target: "main",
      replace: false,
    });
  }
}
