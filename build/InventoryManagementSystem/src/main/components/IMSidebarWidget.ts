import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import UserProfile from "../models/UserProfile";
import Organization from "../models/Organization";
import TextButton from "./TextButton";
import User from "../models/User";
import TextView from "./TextView";
import NavConfig from "../utils/NavConfig";
import { BuildContext } from "../classes/BuildContext";

type _IMSidebarWidgetOnNavigate = (route: string) => void;

type _DashboardNavActiveRefOnPressed = (d3eState: IMSidebarWidgetRefs) => void;

type _DashboardNavRefOnPressed = (d3eState: IMSidebarWidgetRefs) => void;

type _ProductsNavRefOnPressed = (d3eState: IMSidebarWidgetRefs) => void;

type _CategoriesNavRefOnPressed = (d3eState: IMSidebarWidgetRefs) => void;

type _StockNavRefOnPressed = (d3eState: IMSidebarWidgetRefs) => void;

type _TransfersNavRefOnPressed = (d3eState: IMSidebarWidgetRefs) => void;

type _ReportsNavRefOnPressed = (d3eState: IMSidebarWidgetRefs) => void;

type _LogoutRefOnPressed = (d3eState: IMSidebarWidgetRefs) => void;

export interface IMSidebarWidgetProps extends BaseUIProps {
  key?: string;
  currentUser: User;
  userProfile?: UserProfile;
  organization?: Organization;
  activeRoute?: string;
  onNavigate?: _IMSidebarWidgetOnNavigate;
}
/// To store state data for IMSidebarWidget
class IMSidebarWidgetRefs {
  public categoriesNavRef: CategoriesNavRefState = new CategoriesNavRefState();
  public dashboardNavActiveRef: DashboardNavActiveRefState =
    new DashboardNavActiveRefState();
  public dashboardNavRef: DashboardNavRefState = new DashboardNavRefState();
  public logoutRef: LogoutRefState = new LogoutRefState();
  public productsNavRef: ProductsNavRefState = new ProductsNavRefState();
  public reportsNavRef: ReportsNavRefState = new ReportsNavRefState();
  public stockNavRef: StockNavRefState = new StockNavRefState();
  public transfersNavRef: TransfersNavRefState = new TransfersNavRefState();
}

interface LogoutRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSidebarWidgetRefs;
  _onLogoutHandler?: _LogoutRefOnPressed;
}

class LogoutRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _LogoutRefWithState extends ObservableComponent<LogoutRefWithStateProps> {
  logoutRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: LogoutRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get logoutRef(): LogoutRefState {
    return this.props.d3eState.logoutRef;
  }
  public get d3eState(): IMSidebarWidgetRefs {
    return this.props.d3eState;
  }
  public get _onLogoutHandler(): _LogoutRefOnPressed {
    return this.props._onLogoutHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("logoutRef", null, this.logoutRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["logoutRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 12.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: "Logout",
        disable: this.logoutRef.disable,
        onPressed: () => {
          this._onLogoutHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
      }),
      className: "x460",
    });
  }
}
function LogoutRefWithState(props: LogoutRefWithStateProps) {
  return React.createElement(_LogoutRefWithState, props);
}

interface ReportsNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSidebarWidgetRefs;
  _onReportsNavHandler?: _ReportsNavRefOnPressed;
}

class ReportsNavRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _ReportsNavRefWithState extends ObservableComponent<ReportsNavRefWithStateProps> {
  reportsNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ReportsNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get reportsNavRef(): ReportsNavRefState {
    return this.props.d3eState.reportsNavRef;
  }
  public get d3eState(): IMSidebarWidgetRefs {
    return this.props.d3eState;
  }
  public get _onReportsNavHandler(): _ReportsNavRefOnPressed {
    return this.props._onReportsNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("reportsNavRef", null, this.reportsNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["reportsNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Stock Valuation",
      disable: this.reportsNavRef.disable,
      onPressed: () => {
        this._onReportsNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "navItem",
    });
  }
}
function ReportsNavRefWithState(props: ReportsNavRefWithStateProps) {
  return React.createElement(_ReportsNavRefWithState, props);
}

interface TransfersNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSidebarWidgetRefs;
  _onTransfersNavHandler?: _TransfersNavRefOnPressed;
}

class TransfersNavRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _TransfersNavRefWithState extends ObservableComponent<TransfersNavRefWithStateProps> {
  transfersNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: TransfersNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get transfersNavRef(): TransfersNavRefState {
    return this.props.d3eState.transfersNavRef;
  }
  public get d3eState(): IMSidebarWidgetRefs {
    return this.props.d3eState;
  }
  public get _onTransfersNavHandler(): _TransfersNavRefOnPressed {
    return this.props._onTransfersNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("transfersNavRef", null, this.transfersNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["transfersNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Transfers",
      disable: this.transfersNavRef.disable,
      onPressed: () => {
        this._onTransfersNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "navItem",
    });
  }
}
function TransfersNavRefWithState(props: TransfersNavRefWithStateProps) {
  return React.createElement(_TransfersNavRefWithState, props);
}

interface StockNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSidebarWidgetRefs;
  _onStockNavHandler?: _StockNavRefOnPressed;
}

class StockNavRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _StockNavRefWithState extends ObservableComponent<StockNavRefWithStateProps> {
  stockNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: StockNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get stockNavRef(): StockNavRefState {
    return this.props.d3eState.stockNavRef;
  }
  public get d3eState(): IMSidebarWidgetRefs {
    return this.props.d3eState;
  }
  public get _onStockNavHandler(): _StockNavRefOnPressed {
    return this.props._onStockNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("stockNavRef", null, this.stockNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["stockNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Stock Levels",
      disable: this.stockNavRef.disable,
      onPressed: () => {
        this._onStockNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "navItem",
    });
  }
}
function StockNavRefWithState(props: StockNavRefWithStateProps) {
  return React.createElement(_StockNavRefWithState, props);
}

interface CategoriesNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSidebarWidgetRefs;
  _onCategoriesNavHandler?: _CategoriesNavRefOnPressed;
}

class CategoriesNavRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _CategoriesNavRefWithState extends ObservableComponent<CategoriesNavRefWithStateProps> {
  categoriesNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: CategoriesNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get categoriesNavRef(): CategoriesNavRefState {
    return this.props.d3eState.categoriesNavRef;
  }
  public get d3eState(): IMSidebarWidgetRefs {
    return this.props.d3eState;
  }
  public get _onCategoriesNavHandler(): _CategoriesNavRefOnPressed {
    return this.props._onCategoriesNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("categoriesNavRef", null, this.categoriesNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["categoriesNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Categories",
      disable: this.categoriesNavRef.disable,
      onPressed: () => {
        this._onCategoriesNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "navItem",
    });
  }
}
function CategoriesNavRefWithState(props: CategoriesNavRefWithStateProps) {
  return React.createElement(_CategoriesNavRefWithState, props);
}

interface ProductsNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSidebarWidgetRefs;
  _onProductsNavHandler?: _ProductsNavRefOnPressed;
}

class ProductsNavRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _ProductsNavRefWithState extends ObservableComponent<ProductsNavRefWithStateProps> {
  productsNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ProductsNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get productsNavRef(): ProductsNavRefState {
    return this.props.d3eState.productsNavRef;
  }
  public get d3eState(): IMSidebarWidgetRefs {
    return this.props.d3eState;
  }
  public get _onProductsNavHandler(): _ProductsNavRefOnPressed {
    return this.props._onProductsNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("productsNavRef", null, this.productsNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["productsNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Products",
      disable: this.productsNavRef.disable,
      onPressed: () => {
        this._onProductsNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "navItem",
    });
  }
}
function ProductsNavRefWithState(props: ProductsNavRefWithStateProps) {
  return React.createElement(_ProductsNavRefWithState, props);
}

interface DashboardNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSidebarWidgetRefs;
  _onDashboardNavHandler?: _DashboardNavRefOnPressed;
}

class DashboardNavRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _DashboardNavRefWithState extends ObservableComponent<DashboardNavRefWithStateProps> {
  dashboardNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: DashboardNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get dashboardNavRef(): DashboardNavRefState {
    return this.props.d3eState.dashboardNavRef;
  }
  public get d3eState(): IMSidebarWidgetRefs {
    return this.props.d3eState;
  }
  public get _onDashboardNavHandler(): _DashboardNavRefOnPressed {
    return this.props._onDashboardNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("dashboardNavRef", null, this.dashboardNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["dashboardNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Dashboard",
      disable: this.dashboardNavRef.disable,
      onPressed: () => {
        this._onDashboardNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "navItem",
    });
  }
}
function DashboardNavRefWithState(props: DashboardNavRefWithStateProps) {
  return React.createElement(_DashboardNavRefWithState, props);
}

interface DashboardNavActiveRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSidebarWidgetRefs;
  _onDashboardNavActiveHandler?: _DashboardNavActiveRefOnPressed;
}

class DashboardNavActiveRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _DashboardNavActiveRefWithState extends ObservableComponent<DashboardNavActiveRefWithStateProps> {
  dashboardNavActiveRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: DashboardNavActiveRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get dashboardNavActiveRef(): DashboardNavActiveRefState {
    return this.props.d3eState.dashboardNavActiveRef;
  }
  public get d3eState(): IMSidebarWidgetRefs {
    return this.props.d3eState;
  }
  public get _onDashboardNavActiveHandler(): _DashboardNavActiveRefOnPressed {
    return this.props._onDashboardNavActiveHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable(
      "dashboardNavActiveRef",
      null,
      this.dashboardNavActiveRef
    );

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["dashboardNavActiveRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Dashboard",
      disable: this.dashboardNavActiveRef.disable,
      onPressed: () => {
        this._onDashboardNavActiveHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "navActive",
    });
  }
}
function DashboardNavActiveRefWithState(
  props: DashboardNavActiveRefWithStateProps
) {
  return React.createElement(_DashboardNavActiveRefWithState, props);
}

class _IMSidebarWidgetState extends ObservableComponent<IMSidebarWidgetProps> {
  static defaultProps = {
    currentUser: null,
    userProfile: null,
    organization: null,
    activeRoute: "/dashboard",
    onNavigate: null,
  };
  d3eState: IMSidebarWidgetRefs = new IMSidebarWidgetRefs();
  currentRoute: string = "/dashboard";
  displayName: string = "";
  orgName: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: IMSidebarWidgetProps) {
    super(props);

    this.initState();
  }
  public get currentUser(): User {
    return this.props.currentUser;
  }
  public get userProfile(): UserProfile {
    return this.props.userProfile;
  }
  public get activeRoute(): string {
    return this.props.activeRoute ?? "/dashboard";
  }
  public get organization(): Organization {
    return this.props.organization;
  }
  public initState() {
    super.initState();

    this.currentRoute = this.activeRoute;

    this.initListeners();

    this.enableBuild = false;
  }
  public initListeners(): void {
    this.updateSyncProperty("currentUser", this.props.currentUser);

    this.updateSyncProperty("userProfile", this.props.userProfile);

    this.updateSyncProperty("organization", this.props.organization);
  }
  public componentDidUpdate(prevProps: IMSidebarWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.currentUser !== this.props.currentUser) {
      this.updateObservable(
        "currentUser",
        prevProps.currentUser,
        this.props.currentUser
      );

      this.fire("currentUser", this);
    }

    if (prevProps.userProfile !== this.props.userProfile) {
      this.updateObservable(
        "userProfile",
        prevProps.userProfile,
        this.props.userProfile
      );

      this.fire("userProfile", this);
    }

    if (prevProps.organization !== this.props.organization) {
      this.updateObservable(
        "organization",
        prevProps.organization,
        this.props.organization
      );

      this.fire("organization", this);
    }

    if (prevProps.activeRoute !== this.props.activeRoute) {
      this.setCurrentRoute(this.activeRoute);
    }
  }
  public setCurrentRoute(val: string): void {
    let isValChanged: boolean = this.currentRoute !== val;

    if (!isValChanged) {
      return;
    }

    this.currentRoute = val;

    this.fire("currentRoute", this);
  }
  public setDisplayName(val: string): void {
    let isValChanged: boolean = this.displayName !== val;

    if (!isValChanged) {
      return;
    }

    this.displayName = val;

    this.fire("displayName", this);
  }
  public computeDisplayName = (): void => {
    try {
      this.setDisplayName(
        this.userProfile !== null
          ? this.userProfile.displayName
          : this.currentUser.email
      );
    } catch (exception) {
      console.log(" exception in computeDisplayName : " + exception.toString());

      this.setDisplayName("");
    }
  };
  public setOrgName(val: string): void {
    let isValChanged: boolean = this.orgName !== val;

    if (!isValChanged) {
      return;
    }

    this.orgName = val;

    this.fire("orgName", this);
  }
  public computeOrgName = (): void => {
    try {
      let name = "";

      if (
        this.userProfile !== null &&
        this.userProfile.organization !== null &&
        this.userProfile.organization.name.isNotEmpty
      ) {
        name = this.userProfile.organization.name;
      } else if (
        this.organization !== null &&
        this.organization.name.isNotEmpty
      ) {
        name = this.organization.name;
      } else {
        name = "Organization";
      }

      this.setOrgName(name);
    } catch (exception) {
      console.log(" exception in computeOrgName : " + exception.toString());

      this.setOrgName("Organization");
    }
  };
  public resolvedOrgName(): string {
    try {
      if (
        this.userProfile !== null &&
        this.userProfile.organization !== null &&
        this.userProfile.organization.name.isNotEmpty
      ) {
        return this.userProfile.organization.name;
      }

      if (
        this.organization !== null &&
        this.organization.name.isNotEmpty
      ) {
        return this.organization.name;
      }

      return "Organization";
    } catch (exception) {
      return "Organization";
    }
  }
  public resolvedRole(): string {
    try {
      return this.userProfile !== null
        ? this.userProfile.appRole.name
        : "Viewer";
    } catch (exception) {
      return "Viewer";
    }
  }
  public renderNavItems = (): Array<ReactNode> => {
    const role = this.resolvedRole();
    const groups = NavConfig.navForRole(role);
    const children: Array<ReactNode> = [];

    groups.forEach((group, groupIndex) => {
      children.push(
        TextView({
          data: group.label,
          className: "sidebarSectionLabel",
          key: `section-${groupIndex}`,
        })
      );

      group.items.forEach((item, itemIndex) => {
        const isActive = NavConfig.isRouteActive(this.activeRoute, item.route);

        children.push(
          TextButton({
            label: item.icon + "  " + item.label,
            disable: false,
            onPressed: () => {
              this.onNavigate(item.route);
            },
            onFocusChange: () => {},
            className: isActive ? "navActive" : "navItem",
            key: `nav-${groupIndex}-${itemIndex}`,
          })
        );
      });
    });

    return children;
  };
  public render(): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          ui.Container({
            child: ui.Column({
              crossAxisAlignment: ui.CrossAxisAlignment.stretch,
              mainAxisSize: ui.MainAxisSize.min,
              children: [
                React.createElement("img", {
                  src: "/images/freshmart.png",
                  alt: "FreshMart",
                  className: "sidebarBrandLogo",
                  key: "0",
                }),
              ],
            }),
            className: "sidebarBrandBlock x52",
            key: "0",
          }),
          ui.Container({
            child: ui.Column({
              crossAxisAlignment: ui.CrossAxisAlignment.stretch,
              children: this.renderNavItems(),
            }),
            className: "xef7",
            key: "1",
          }),
          ui.Container({
            child: TextView({
              data: "© 2026 FreshMart Retail Group",
              className: "sidebarFooterText",
              key: "0",
            }),
            className: "x60",
            key: "2",
          }),
        ],
      }),
      className: ui.join(
        "GlassSidebarStyle IMSidebarWidget sidebarSolid ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onDashboardNavHandler = (d3eState: IMSidebarWidgetRefs): void => {
    this.onNavigate("/dashboard");
  };
  public onDashboardNavActiveHandler = (
    d3eState: IMSidebarWidgetRefs
  ): void => {
    this.onNavigate("/dashboard");
  };
  public onProductsNavHandler = (d3eState: IMSidebarWidgetRefs): void => {
    this.onNavigate("/catalog/products");
  };
  public onCategoriesNavHandler = (d3eState: IMSidebarWidgetRefs): void => {
    this.onNavigate("/catalog/categories");
  };
  public onStockNavHandler = (d3eState: IMSidebarWidgetRefs): void => {
    this.onNavigate("/inventory/stock");
  };
  public onTransfersNavHandler = (d3eState: IMSidebarWidgetRefs): void => {
    this.onNavigate("/inventory/transfers");
  };
  public onReportsNavHandler = (d3eState: IMSidebarWidgetRefs): void => {
    this.onNavigate("/reports/stock-valuation");
  };
  public onLogoutHandler = (d3eState: IMSidebarWidgetRefs): void => {
    this.onNavigate("/login");
  };
  public get onNavigate(): _IMSidebarWidgetOnNavigate {
    return this.props.onNavigate;
  }
  public get categoriesNavRef() {
    return this.d3eState.categoriesNavRef;
  }
  public get dashboardNavActiveRef() {
    return this.d3eState.dashboardNavActiveRef;
  }
  public get dashboardNavRef() {
    return this.d3eState.dashboardNavRef;
  }
  public get logoutRef() {
    return this.d3eState.logoutRef;
  }
  public get productsNavRef() {
    return this.d3eState.productsNavRef;
  }
  public get reportsNavRef() {
    return this.d3eState.reportsNavRef;
  }
  public get stockNavRef() {
    return this.d3eState.stockNavRef;
  }
  public get transfersNavRef() {
    return this.d3eState.transfersNavRef;
  }
}
export default function IMSidebarWidget(props: IMSidebarWidgetProps) {
  return React.createElement(_IMSidebarWidgetState, {
    ..._IMSidebarWidgetState.defaultProps,
    ...props,
  });
}
