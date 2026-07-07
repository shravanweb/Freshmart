import LoginPage from "../components/LoginPage";
import ChangePasswordPage from "../components/ChangePasswordPage";
import PurchaseOrderFormPage from "../components/PurchaseOrderFormPage";
import NotificationPreferencesPage from "../components/NotificationPreferencesPage";
import GlobalFunctions from "./GlobalFunctions";
import DashboardPage from "../components/DashboardPage";
import ProfileSettingsPage from "../components/ProfileSettingsPage";
import PurchaseOrderListPage from "../components/PurchaseOrderListPage";
import UndoManager from "./UndoManager";
import ForgotPasswordPage from "../components/ForgotPasswordPage";
import OrganizationSettingsPage from "../components/OrganizationSettingsPage";
import LandingPage from "../components/LandingPage";
import SignupPage from "../components/SignupPage";
import StoreListPage from "../components/StoreListPage";
import WarehouseListPage from "../components/WarehouseListPage";
import UserListPage from "../components/UserListPage";
import ProductCategoryListPage from "../components/ProductCategoryListPage";
import ProductListPage from "../components/ProductListPage";
import StockLevelsPage from "../components/StockLevelsPage";
import UnitOfMeasureListPage from "../components/UnitOfMeasureListPage";
import ProductDetailPage from "../components/ProductDetailPage";
import CustomerCartPage from "../components/CustomerCartPage";
import CustomerAccountPage from "../components/CustomerAccountPage";
import StoresPage from "../components/StoresPage";
import NewArrivalsPage from "../components/NewArrivalsPage";
import CategoryProductsPage from "../components/CategoryProductsPage";
import SupplierListPage from "../components/SupplierListPage";

export default class AllPagesUtil {
  public static load(): void {
    GlobalFunctions.ChangePasswordPage = ChangePasswordPage;

    GlobalFunctions.DashboardPage = DashboardPage;

    GlobalFunctions.ForgotPasswordPage = ForgotPasswordPage;

    GlobalFunctions.LandingPage = LandingPage;

    GlobalFunctions.LoginPage = LoginPage;

    GlobalFunctions.SignupPage = SignupPage;

    GlobalFunctions.NotificationPreferencesPage = NotificationPreferencesPage;

    GlobalFunctions.OrganizationSettingsPage = OrganizationSettingsPage;

    GlobalFunctions.ProfileSettingsPage = ProfileSettingsPage;

    GlobalFunctions.PurchaseOrderFormPage = PurchaseOrderFormPage;

    GlobalFunctions.PurchaseOrderListPage = PurchaseOrderListPage;

    GlobalFunctions.SupplierListPage = SupplierListPage;

    GlobalFunctions.StoreListPage = StoreListPage;

    GlobalFunctions.WarehouseListPage = WarehouseListPage;

    GlobalFunctions.UserListPage = UserListPage;

    GlobalFunctions.ProductCategoryListPage = ProductCategoryListPage;

    GlobalFunctions.ProductListPage = ProductListPage;

    GlobalFunctions.StockLevelsPage = StockLevelsPage;

    GlobalFunctions.UnitOfMeasureListPage = UnitOfMeasureListPage;

    GlobalFunctions.ProductDetailPage = ProductDetailPage;

    GlobalFunctions.CustomerCartPage = CustomerCartPage;

    GlobalFunctions.CustomerAccountPage = CustomerAccountPage;

    GlobalFunctions.StoresPage = StoresPage;

    GlobalFunctions.NewArrivalsPage = NewArrivalsPage;

    GlobalFunctions.CategoryProductsPage = CategoryProductsPage;

    UndoManager.load();
  }
}
