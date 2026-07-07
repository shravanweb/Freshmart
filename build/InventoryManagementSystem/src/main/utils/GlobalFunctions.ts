import { ReactNode } from "react";
import DBObject from "./DBObject";
import * as __d3ett from "../rocket/D3ETemplateTypes";
import Result from "../classes/Result";
import { PurchaseOrderFormPageProps } from "../components/PurchaseOrderFormPage";
import { PurchaseOrderListPageProps } from "../components/PurchaseOrderListPage";
import { DashboardPageProps } from "../components/DashboardPage";
import { ChangePasswordPageProps } from "../components/ChangePasswordPage";
import { NotificationPreferencesPageProps } from "../components/NotificationPreferencesPage";
import { OrganizationSettingsPageProps } from "../components/OrganizationSettingsPage";
import { LoginPageProps } from "../components/LoginPage";
import { ForgotPasswordPageProps } from "../components/ForgotPasswordPage";
import { ProfileSettingsPageProps } from "../components/ProfileSettingsPage";
import { LandingPageProps } from "../components/LandingPage";
import { SignupPageProps } from "../components/SignupPage";
import { StoreListPageProps } from "../components/StoreListPage";
import { WarehouseListPageProps } from "../components/WarehouseListPage";
import { UserListPageProps } from "../components/UserListPage";
import { ProductCategoryListPageProps } from "../components/ProductCategoryListPage";
import { ProductListPageProps } from "../components/ProductListPage";
import { StockLevelsPageProps } from "../components/StockLevelsPage";
import { UnitOfMeasureListPageProps } from "../components/UnitOfMeasureListPage";
import { ProductDetailPageProps } from "../components/ProductDetailPage";
import { CustomerCartPageProps } from "../components/CustomerCartPage";
import { CustomerAccountPageProps } from "../components/CustomerAccountPage";
import { StoresPageProps } from "../components/StoresPage";
import { NewArrivalsPageProps } from "../components/NewArrivalsPage";
import { CategoryProductsPageProps } from "../components/CategoryProductsPage";
import { SupplierListPageProps } from "../components/SupplierListPage";

export default class GlobalFunctions {
  public static save: (obj: DBObject) => Promise<Result<any>>;
  public static delete: (obj: DBObject) => Promise<Result<any>>;
  public static objectSave: (obj: DBObject, create: boolean) => void;
  public static fieldChanged: (
    obj: DBObject,
    field: number,
    oldValue: any
  ) => void;
  public static objectDelete: (obj: DBObject) => void;
  public static typeInt: (obj: string) => number;
  public static types: Array<__d3ett.D3ETemplateType>;
  public static ChangePasswordPage: (
    props: ChangePasswordPageProps
  ) => ReactNode;
  public static DashboardPage: (props: DashboardPageProps) => ReactNode;
  public static ForgotPasswordPage: (
    props: ForgotPasswordPageProps
  ) => ReactNode;
  public static LandingPage: (props: LandingPageProps) => ReactNode;
  public static LoginPage: (props: LoginPageProps) => ReactNode;
  public static SignupPage: (props: SignupPageProps) => ReactNode;
  public static NotificationPreferencesPage: (
    props: NotificationPreferencesPageProps
  ) => ReactNode;
  public static OrganizationSettingsPage: (
    props: OrganizationSettingsPageProps
  ) => ReactNode;
  public static ProfileSettingsPage: (
    props: ProfileSettingsPageProps
  ) => ReactNode;
  public static PurchaseOrderFormPage: (
    props: PurchaseOrderFormPageProps
  ) => ReactNode;
  public static PurchaseOrderListPage: (
    props: PurchaseOrderListPageProps
  ) => ReactNode;
  public static SupplierListPage: (props: SupplierListPageProps) => ReactNode;
  public static StoreListPage: (props: StoreListPageProps) => ReactNode;
  public static WarehouseListPage: (props: WarehouseListPageProps) => ReactNode;
  public static UserListPage: (props: UserListPageProps) => ReactNode;
  public static ProductCategoryListPage: (props: ProductCategoryListPageProps) => ReactNode;
  public static ProductListPage: (props: ProductListPageProps) => ReactNode;
  public static StockLevelsPage: (props: StockLevelsPageProps) => ReactNode;
  public static UnitOfMeasureListPage: (props: UnitOfMeasureListPageProps) => ReactNode;
  public static ProductDetailPage: (props: ProductDetailPageProps) => ReactNode;
  public static CustomerCartPage: (props: CustomerCartPageProps) => ReactNode;
  public static CustomerAccountPage: (props: CustomerAccountPageProps) => ReactNode;
  public static StoresPage: (props: StoresPageProps) => ReactNode;
  public static NewArrivalsPage: (props: NewArrivalsPageProps) => ReactNode;
  public static CategoryProductsPage: (props: CategoryProductsPageProps) => ReactNode;
}
