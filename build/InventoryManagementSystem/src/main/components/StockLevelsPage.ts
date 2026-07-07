import React, { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import User from "../models/User";
import UserProfile from "../models/UserProfile";
import Organization from "../models/Organization";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import Query from "../classes/Query";
import UserProfileByUser from "../classes/UserProfileByUser";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import MessageDispatch from "../rocket/MessageDispatch";
import { UsageConstants } from "../rocket/D3ETemplate";
import IMSidebarWidget from "./IMSidebarWidget";
import IMSAppHeaderWidget from "./IMSAppHeaderWidget";
import ScrollView2 from "./ScrollView2";
import TextView from "./TextView";
import TextButton from "./TextButton";
import EmptyStateWidget from "./EmptyStateWidget";
import WarehouseApi, { WarehouseRecord } from "../utils/WarehouseApi";
import ProductApi, { ProductRecord } from "../utils/ProductApi";
import WarehouseStockApi, { StockRecord } from "../utils/WarehouseStockApi";
import CollectionUtils from "../utils/CollectionUtils";
import { BuildContext } from "../classes/BuildContext";
import AppLogout from "../utils/AppLogout";

export interface StockLevelsPageProps extends BaseUIProps {
  key?: string;
  user: User;
}

class _StockLevelsPageState extends ObservableComponent<StockLevelsPageProps> {
  static defaultProps = { user: null };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  stockItems: StockRecord[] = [];
  warehouses: WarehouseRecord[] = [];
  products: ProductRecord[] = [];
  searchTerm: string = "";
  warehouseFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  showFormModal: boolean = false;
  isSaving: boolean = false;
  formError: string = "";
  formWarehouseId: string = "";
  formProductId: string = "";
  formQuantity: string = "";

  public constructor(props: StockLevelsPageProps) {
    super(props);
    this.initState();
  }

  public get user(): User {
    return this.props.user;
  }

  public initState() {
    super.initState();
    this.initListeners();
    this.enableBuild = true;
  }

  public initListeners(): void {
    this.updateSyncProperty("user", this.props.user);
    this.on(["user"], this.computeUserProfileData);
    this.computeUserProfileData();
    this.on(["userProfileData", "userProfileData.items"], this.computeUserProfile);
    this.computeUserProfile();
    this.on(["userProfile", "userProfile.organization"], this.computeOrganization);
    this.computeOrganization();
    this.on(["organization"], this.loadStock);
    this.on(["organization"], this.loadOptions);
    this.on(
      [
        "stockItems",
        "warehouses",
        "products",
        "searchTerm",
        "warehouseFilter",
        "isLoading",
        "loadError",
        "showFormModal",
        "isSaving",
        "formError",
        "formWarehouseId",
        "formProductId",
        "formQuantity",
        "user",
        "userProfile",
        "organization",
      ],
      this.rebuild
    );
  }

  public componentDidUpdate(prevProps: StockLevelsPageProps): void {
    super.componentDidUpdate(prevProps);
    if (prevProps.user !== this.props.user) {
      this.updateObservable("user", prevProps.user, this.props.user);
      this.fire("user", this);
    }
  }

  public setUserProfileData(val: UserProfileByUser): void {
    if (this.userProfileData === val) return;
    this.updateObservable("userProfileData", this.userProfileData, val);
    MessageDispatch.get().dispose(this.userProfileData);
    this.userProfileData = val;
    this.fire("userProfileData", this);
  }

  public computeUserProfileData = async (): Promise<void> => {
    try {
      this.setUserProfileData(
        await Query.get().getUserProfileByUser(
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_PURCHASEORDERLISTPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
          new UserProfileByUserRequest({ user: this.user }),
          { synchronize: true }
        )
      );
    } catch (exception) {
      this.setUserProfileData(null);
    }
  };

  public setUserProfile(val: UserProfile): void {
    if (this.userProfile === val) return;
    this.userProfile = val;
    this.fire("userProfile", this);
  }

  public computeUserProfile = (): void => {
    this.setUserProfile(
      this.userProfileData !== null ? this.userProfileData.items.first : null
    );
  };

  public setOrganization(val: Organization): void {
    if (this.organization === val) return;
    this.organization = val;
    this.fire("organization", this);
  }

  public computeOrganization = (): void => {
    this.setOrganization(
      this.userProfile !== null ? this.userProfile.organization : null
    );
  };

  public setStockItems(val: StockRecord[]): void {
    if (CollectionUtils.isNotEquals(this.stockItems, val)) {
      this.stockItems = val;
      this.fire("stockItems", this);
    }
  }

  public loadOptions = async (): Promise<void> => {
    if (this.organization == null) {
      this.warehouses = [];
      this.products = [];
      this.fire("warehouses", this);
      this.fire("products", this);
      return;
    }
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    try {
      this.warehouses = await WarehouseApi.getAllWarehouses(orgId);
      this.products = await ProductApi.getAllProducts(orgId);
      this.fire("warehouses", this);
      this.fire("products", this);
    } catch {
      this.warehouses = [];
      this.products = [];
      this.fire("warehouses", this);
      this.fire("products", this);
    }
  };

  public loadStock = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.setStockItems([]);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const warehouseId = this.warehouseFilter ? Number(this.warehouseFilter) : undefined;
      const items = await WarehouseStockApi.getStockLevels(orgId, warehouseId);
      this.setStockItems(items);
      this.loadError = "";
    } catch (exception) {
      this.setStockItems([]);
      this.loadError = "Failed to load stock: " + exception.toString();
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
      this.fire("loadError", this);
    }
  };

  public get filteredStock(): StockRecord[] {
    let list = this.stockItems;
    const term = this.searchTerm?.trim().toLowerCase() ?? "";
    if (term) {
      list = list.filter(
        (item) =>
          item.productName.toLowerCase().includes(term) ||
          item.productSku.toLowerCase().includes(term) ||
          item.warehouseName.toLowerCase().includes(term)
      );
    }
    return list;
  }

  public openAddStockForm = (): void => {
    this.formWarehouseId =
      this.warehouseFilter ||
      (this.warehouses.length > 0 ? String(this.warehouses[0].id) : "");
    this.formProductId = this.products.length > 0 ? String(this.products[0].id) : "";
    this.formQuantity = "";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public closeForm = (): void => {
    this.showFormModal = false;
    this.formError = "";
    this.fire("showFormModal", this);
  };

  public saveForm = async (): Promise<void> => {
    const warehouse = Number(this.formWarehouseId);
    const product = Number(this.formProductId);
    const quantity = Number(this.formQuantity);
    const orgId = this.organization?.id ?? this.user?.organization?.id;

    if (!warehouse || !product) {
      this.formError = "Select warehouse and product.";
      this.fire("formError", this);
      return;
    }
    if (!quantity || quantity <= 0) {
      this.formError = "Enter a valid quantity.";
      this.fire("formError", this);
      return;
    }
    if (!orgId) {
      this.formError = "Organization not found.";
      this.fire("formError", this);
      return;
    }

    this.isSaving = true;
    this.formError = "";
    this.fire("isSaving", this);

    const result = await WarehouseStockApi.upsertStock({
      warehouse,
      product,
      quantityOnHand: quantity,
      organization: orgId,
    });

    this.isSaving = false;
    this.fire("isSaving", this);

    if (!result.success) {
      this.formError =
        result.errors.length > 0 ? result.errors.join(", ") : "Failed to add stock.";
      this.fire("formError", this);
      return;
    }

    this.closeForm();
    await this.loadStock();
  };

  private formFieldClass(fullWidth?: boolean, narrow?: boolean): string {
    const base = "FormFieldStyle IMSInputFieldWidget formField storeFormField";
    const withFull = fullWidth ? ui.join(base, "productFormFullWidth") : base;
    return narrow ? ui.join(withFull, "stockFormQuantityField") : withFull;
  }

  private renderSelectField(
    label: string,
    value: string,
    options: { value: string; label: string }[],
    onChange: (val: string) => void,
    required?: boolean,
    fullWidth?: boolean
  ): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({ data: required ? label + " *" : label, className: "fieldLabel", key: "0" }),
          React.createElement(
            "select",
            {
              className: "roleSelect",
              value,
              disabled: this.isSaving,
              onChange: (e: React.ChangeEvent<HTMLSelectElement>) => onChange(e.target.value),
              key: "1",
            },
            options.map((option) =>
              React.createElement("option", { value: option.value, key: option.value }, option.label)
            )
          ),
        ],
      }),
      className: this.formFieldClass(fullWidth),
      key: label,
    });
  }

  private renderTextField(
    label: string,
    value: string,
    placeHolder: string,
    onChange: (val: string) => void,
    required?: boolean,
    fullWidth?: boolean,
    narrow?: boolean
  ): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({ data: required ? label + " *" : label, className: "fieldLabel", key: "0" }),
          ui.InputField({ value, placeHolder, onChanged: onChange, onFocusChange: () => {}, key: "1" }),
        ],
      }),
      className: this.formFieldClass(fullWidth, narrow),
      key: label,
    });
  }

  private renderFormModal(): ReactNode {
    const warehouseOptions: { value: string; label: string }[] = [];
    for (const w of this.warehouses) {
      warehouseOptions.push({ value: String(w.id), label: w.name });
    }
    const productOptions: { value: string; label: string }[] = [];
    for (const p of this.products) {
      productOptions.push({ value: String(p.id), label: `${p.name} (${p.sku})` });
    }

    return React.createElement(
      "div",
      { className: "storeModalOverlay", onClick: () => !this.isSaving && this.closeForm() },
      React.createElement(
        "div",
        {
          className: "storeModalPanel glassCard productModalPanel stockFormModalPanel",
          onClick: (e: React.MouseEvent) => e.stopPropagation(),
          role: "dialog",
          "aria-modal": "true",
          "aria-label": "Add Stock",
        },
        React.createElement("div", { className: "storeModalHeader" },
          React.createElement("h2", { className: "storeModalTitle" }, "Add Stock"),
          React.createElement("button", {
            type: "button",
            className: "storeModalClose",
            disabled: this.isSaving,
            onClick: () => this.closeForm(),
          }, "✕")
        ),
        React.createElement(
          "div",
          { className: "storeModalBody productModalBody" },
          React.createElement(
            "div",
            { className: "productFormGrid" },
            this.renderSelectField("Warehouse", this.formWarehouseId, warehouseOptions, (val) => {
              this.formWarehouseId = val;
              this.fire("formWarehouseId", this);
            }, true),
            this.renderSelectField("Product", this.formProductId, productOptions, (val) => {
              this.formProductId = val;
              this.fire("formProductId", this);
            }, true),
            this.renderTextField(
              "Quantity",
              this.formQuantity,
              "Enter quantity",
              (val) => {
                this.formQuantity = val;
                this.fire("formQuantity", this);
              },
              true,
              true,
              true
            ),
            React.createElement(
              "p",
              { className: "stockFormHint productFormFullWidth", key: "hint" },
              "Adds quantity to existing stock or creates new warehouse stock."
            ),
            this.formError
              ? React.createElement(
                  "p",
                  { className: "storeFormError stockFormError productFormFullWidth", key: "error" },
                  this.formError
                )
              : null
          )
        ),
        React.createElement(
          "div",
          { className: "storeModalFooter" },
          TextButton({ label: "Cancel", disable: this.isSaving, onPressed: () => this.closeForm(), onFocusChange: () => {}, className: "secondary" }),
          TextButton({
            label: this.isSaving ? "Saving..." : "Add Stock",
            disable: this.isSaving,
            onPressed: () => this.saveForm(),
            onFocusChange: () => {},
            className: "primary",
          })
        )
      )
    );
  }

  public render(): ReactNode {
    const filtered = this.filteredStock;
    const countLabel = filtered.length + " record" + (filtered.length === 1 ? "" : "s");
    const warehouseFilterOptions: { value: string; label: string }[] = [
      { value: "", label: "All warehouses" },
    ];
    for (const w of this.warehouses) {
      warehouseFilterOptions.push({ value: String(w.id), label: w.name });
    }

    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          IMSidebarWidget({
            currentUser: this.user,
            userProfile: this.userProfile,
            organization: this.organization,
            activeRoute: "/inventory/stock",
            onNavigate: (route) => this.onNavigateHandler(route),
            key: "0",
          }),
          ui.Container({
            child: ui.Column({
              crossAxisAlignment: ui.CrossAxisAlignment.stretch,
              mainAxisSize: ui.MainAxisSize.max,
              children: [
                IMSAppHeaderWidget({
                  title: "Stock Levels",
                  user: this.user,
                  userProfile: this.userProfile,
                  organization: this.organization,
                  onLogout: () => void AppLogout.signOut(this.navigator),
                  onProfile: () =>
                    this.navigator.pushProfileSettingsPage({
                      user: this.user,
                      target: "main",
                      replace: false,
                    }),
                  key: "0",
                }),
                ScrollView2({
                  child: ui.Container({
                    child: ui.Column({
                      crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                      children: [
                        ui.Row({
                          mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: ui.CrossAxisAlignment.center,
                          mainAxisSize: ui.MainAxisSize.max,
                          children: [
                            TextView({ data: "Stock Levels", className: "storePageTitle", key: "0" }),
                            TextButton({
                              label: "+ Add Stock",
                              onPressed: () => this.openAddStockForm(),
                              onFocusChange: () => {},
                              className: "primary",
                              key: "1",
                            }),
                          ],
                          className: "storeToolbarRow",
                          key: "toolbar",
                        }),
                        ui.Row({
                          mainAxisSize: ui.MainAxisSize.max,
                          crossAxisAlignment: ui.CrossAxisAlignment.center,
                          children: [
                            ui.Container({
                              child: this.renderTextField("", this.searchTerm, "Search product or warehouse...", (val) => {
                                this.searchTerm = val;
                                this.fire("searchTerm", this);
                              }),
                              className: "storeSearchField",
                              key: "0",
                            }),
                            ui.Container({
                              child: this.renderSelectField("", this.warehouseFilter, warehouseFilterOptions, (val) => {
                                this.warehouseFilter = val;
                                this.fire("warehouseFilter", this);
                                this.loadStock();
                              }),
                              className: "storeStatusFilter",
                              key: "1",
                            }),
                            TextView({ data: countLabel, className: "entityFilterCount", key: "2" }),
                          ],
                          className: "storeFilterRow entityFilterRow",
                          key: "filters",
                        }),
                        this.loadError ? TextView({ data: this.loadError, className: "storeFormError", key: "loadError" }) : null,
                        this.isLoading
                          ? TextView({ data: "Loading stock...", className: "storeCountLabel", key: "loading" })
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title: "No stock records",
                                message: "Add stock to a warehouse after creating products.",
                                actionLabel: "+ Add Stock",
                                onAction: () => this.openAddStockForm(),
                                key: "empty",
                              })
                            : ui.Container({
                                child: ui.Column({
                                  crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                                  children: [
                                    ui.Container({
                                      child: ui.Row({
                                        mainAxisSize: ui.MainAxisSize.max,
                                        children: [
                                          TextView({ data: "PRODUCT", className: "entityHeaderCell", key: "0" }),
                                          TextView({ data: "SKU", className: "entityHeaderCell", key: "1" }),
                                          TextView({ data: "WAREHOUSE", className: "entityHeaderCell", key: "2" }),
                                          TextView({ data: "ON HAND", className: "entityHeaderCell", key: "3" }),
                                          TextView({ data: "AVAILABLE", className: "entityHeaderCell", key: "4" }),
                                        ],
                                        className: "entityTableGrid stockEntityTableGrid",
                                      }),
                                      className: "entityTableHeader",
                                      key: "header",
                                    }),
                                    ...filtered.map((item, index) =>
                                      ui.Container({
                                        child: ui.Row({
                                          mainAxisSize: ui.MainAxisSize.max,
                                          children: [
                                            TextView({ data: item.productName, className: "entityCell entityCellName", key: "0" }),
                                            TextView({ data: item.productSku || "—", className: "entityCell", key: "1" }),
                                            TextView({ data: item.warehouseName || "—", className: "entityCell", key: "2" }),
                                            TextView({ data: String(item.quantityOnHand), className: "entityCell", key: "3" }),
                                            TextView({ data: String(item.quantityAvailable), className: "entityCell", key: "4" }),
                                          ],
                                          className: "entityTableGrid stockEntityTableGrid",
                                        }),
                                        className: "entityTableRow",
                                        key: "row-" + index,
                                      })
                                    ),
                                  ],
                                }),
                                className: "entityTableWrap",
                                key: "table",
                              }),
                      ],
                    }),
                    padding: ui.EdgeInsets.all(24.0),
                  }),
                  scrollDirection: ui.Axis.vertical,
                  className: "pageContent",
                }),
                this.showFormModal ? this.renderFormModal() : null,
              ],
            }),
            className: "storeMainContent",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle StockLevelsPage pageBackground ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }

  public onNavigateHandler = (route: string): void => {
    AppRouteNavigator.navigate(this.navigator, route, this.user, {
      target: "main",
      replace: true,
    });
  };

  public dispose(): void {
    MessageDispatch.get().dispose(this.userProfileData);
    super.dispose();
  }
}

export default function StockLevelsPage(props: StockLevelsPageProps) {
  return React.createElement(_StockLevelsPageState, { ..._StockLevelsPageState.defaultProps, ...props });
}
