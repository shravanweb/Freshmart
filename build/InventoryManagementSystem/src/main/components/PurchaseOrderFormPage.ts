import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import PurchaseOrder from "../models/PurchaseOrder";
import PurchaseOrderItem from "../classes/PurchaseOrderItem";
import UserProfile from "../models/UserProfile";
import IMSidebarWidget from "./IMSidebarWidget";
import TextButton from "./TextButton";
import User from "../models/User";
import PageNavigator from "../classes/PageNavigator";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import Query from "../classes/Query";
import MessageDispatch from "../rocket/MessageDispatch";
import PurchaseOrderItemRequest from "../models/PurchaseOrderItemRequest";
import DBSaveStatus from "../utils/DBSaveStatus";
import ScrollView2 from "./ScrollView2";
import IMSAppHeaderWidget from "./IMSAppHeaderWidget";
import Organization from "../models/Organization";
import UserProfileByUser from "../classes/UserProfileByUser";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import { UsageConstants } from "../rocket/D3ETemplate";
import { BuildContext } from "../classes/BuildContext";
import SupplierApi, { SupplierRecord } from "../utils/SupplierApi";
import WarehouseApi, { WarehouseRecord } from "../utils/WarehouseApi";
import ProductApi, { ProductRecord } from "../utils/ProductApi";
import UomApi from "../utils/UomApi";
import PurchaseOrderApi from "../utils/PurchaseOrderApi";
import IconButton from "./IconButton";
import MaterialIcons from "../icons/MaterialIcons";
import TextView from "./TextView";

interface PoLineDraft {
  key: string;
  productId: string;
  quantity: string;
  unitPrice: string;
}

type _CancelRefOnPressed = (d3eState: PurchaseOrderFormPageRefs) => void;

type _SaveRefOnPressed = (d3eState: PurchaseOrderFormPageRefs) => void;

export interface PurchaseOrderFormPageProps extends BaseUIProps {
  key?: string;
  user: User;
  purchaseOrder: PurchaseOrder;
}
/// To store state data for PurchaseOrderFormPage
class PurchaseOrderFormPageRefs {
  public cancelRef: CancelRefState = new CancelRefState();
  contentRefScrollController: ui.ScrollController = new ui.ScrollController();
  public saveRef: SaveRefState = new SaveRefState();
}

interface SaveRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: PurchaseOrderFormPageRefs;
  _onSaveHandler?: _SaveRefOnPressed;
  isEditMode: boolean;
  isLoading: boolean;
}

class SaveRefState extends ObjectObservable {}

class _SaveRefWithState extends ObservableComponent<SaveRefWithStateProps> {
  saveRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: SaveRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get isEditMode(): boolean {
    return this.props.isEditMode;
  }
  public get isLoading(): boolean {
    return this.props.isLoading;
  }
  public get saveRef(): SaveRefState {
    return this.props.d3eState.saveRef;
  }
  public get d3eState(): PurchaseOrderFormPageRefs {
    return this.props.d3eState;
  }
  public get _onSaveHandler(): _SaveRefOnPressed {
    return this.props._onSaveHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("saveRef", null, this.saveRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["isEditMode", "isLoading", "saveRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: this.isEditMode ? "Update" : "Create",
      disable: this.isLoading,
      onPressed: () => {
        this._onSaveHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "primary",
    });
  }
}
function SaveRefWithState(props: SaveRefWithStateProps) {
  return React.createElement(_SaveRefWithState, props);
}

interface CancelRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: PurchaseOrderFormPageRefs;
  _onCancelHandler?: _CancelRefOnPressed;
}

class CancelRefState extends ObjectObservable {
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

class _CancelRefWithState extends ObservableComponent<CancelRefWithStateProps> {
  cancelRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: CancelRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get cancelRef(): CancelRefState {
    return this.props.d3eState.cancelRef;
  }
  public get d3eState(): PurchaseOrderFormPageRefs {
    return this.props.d3eState;
  }
  public get _onCancelHandler(): _CancelRefOnPressed {
    return this.props._onCancelHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("cancelRef", null, this.cancelRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["cancelRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 0.0, 8.0, 0.0, new Map()),
      child: TextButton({
        label: "Cancel",
        disable: this.cancelRef.disable,
        onPressed: () => {
          this._onCancelHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "secondary",
        key: "0",
      }),
      className: "secondary x67",
    });
  }
}
function CancelRefWithState(props: CancelRefWithStateProps) {
  return React.createElement(_CancelRefWithState, props);
}

class _PurchaseOrderFormPageState extends ObservableComponent<PurchaseOrderFormPageProps> {
  static defaultProps = { user: null, purchaseOrder: null };
  d3eState: PurchaseOrderFormPageRefs = new PurchaseOrderFormPageRefs();
  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  poData: PurchaseOrderItem = null;
  isEditMode: boolean = false;
  notes: string = "";
  isLoading: boolean = false;
  isCatalogLoading: boolean = true;
  catalogError: string = "";
  formError: string = "";
  emailSuccess: string = "";
  emailSending: boolean = false;
  suppliers: SupplierRecord[] = [];
  warehouses: WarehouseRecord[] = [];
  products: ProductRecord[] = [];
  vendorProducts: ProductRecord[] = [];
  isVendorProductsLoading: boolean = false;
  defaultUomId: number = 0;
  selectedVendorId: string = "";
  selectedWarehouseId: string = "";
  orderDate: string = "";
  expectedDeliveryDate: string = "";
  lines: PoLineDraft[] = [];
  linesHydratedForPoId: number = 0;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: PurchaseOrderFormPageProps) {
    super(props);

    this.initState();
  }
  public get user(): User {
    return this.props.user;
  }
  public get purchaseOrder(): PurchaseOrder {
    return this.props.purchaseOrder;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;

    this.onInit();
  }
  public initListeners(): void {
    this.updateSyncProperty("user", this.props.user);

    this.on(["user"], this.computeUserProfileData);

    this.computeUserProfileData();

    this.on(
      ["userProfileData", "userProfileData.items"],
      this.computeUserProfile
    );

    this.computeUserProfile();

    this.on(
      ["userProfile", "userProfile.organization"],
      this.computeOrganization
    );

    this.computeOrganization();

    this.on(["organization"], this.loadCatalogData);

    this.loadCatalogData();

    if (!this.isEditMode) {
      this.orderDate = this.todayIsoDate();
    }

    this.updateSyncProperty("purchaseOrder", this.props.purchaseOrder);

    this.on(["isEditMode", "purchaseOrder"], this.computePoData);

    this.computePoData();

    this.on(["purchaseOrder"], this.computeIsEditMode);

    this.computeIsEditMode();

    this.on(
      ["poData", "poData.items", "isEditMode", "purchaseOrder"],
      this.populateFromPurchaseOrder
    );

    this.populateFromPurchaseOrder();

    this.on(
      [
        "cancelRef",
        "isEditMode",
        "isLoading",
        "isCatalogLoading",
        "catalogError",
        "formError",
        "notes",
        "organization",
        "suppliers",
        "warehouses",
        "products",
        "vendorProducts",
        "isVendorProductsLoading",
        "selectedVendorId",
        "selectedWarehouseId",
        "orderDate",
        "expectedDeliveryDate",
        "lines",
        "emailSuccess",
        "emailSending",
        "saveRef",
        "user",
        "userProfile",
      ],
      this.rebuild
    );
  }

  private todayIsoDate(): string {
    const now = new Date();
    const month = String(now.getMonth() + 1).padStart(2, "0");
    const day = String(now.getDate()).padStart(2, "0");
    return `${now.getFullYear()}-${month}-${day}`;
  }

  public loadCatalogData = async (): Promise<void> => {
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.isCatalogLoading = false;
      this.suppliers = [];
      this.warehouses = [];
      this.products = [];
      this.vendorProducts = [];
      this.fire("isCatalogLoading", this);
      return;
    }

    this.isCatalogLoading = true;
    this.catalogError = "";
    this.fire("isCatalogLoading", this);

    try {
      const [suppliers, warehouses, products, uoms] = await Promise.all([
        SupplierApi.getAllSuppliers(orgId),
        WarehouseApi.getAllWarehouses(orgId),
        ProductApi.getAllProducts(orgId),
        UomApi.getAllUnitOfMeasures(orgId),
      ]);

      this.suppliers = suppliers.filter((s) => s.status === "Active");
      this.warehouses = warehouses.filter((w) => w.status === "Active");
      this.products = products.filter((p) => p.status === "Active");
      this.defaultUomId = uoms[0]?.id ?? 0;

      if (!this.selectedVendorId && this.purchaseOrder?.vendor?.id) {
        this.selectedVendorId = String(this.purchaseOrder.vendor.id);
      } else if (!this.selectedVendorId && this.suppliers.length === 1) {
        this.selectedVendorId = String(this.suppliers[0].id);
      }

      if (!this.selectedWarehouseId && this.purchaseOrder?.warehouse?.id) {
        this.selectedWarehouseId = String(this.purchaseOrder.warehouse.id);
      } else if (!this.selectedWarehouseId) {
        const defaultWarehouse =
          this.warehouses.find((w) => w.isDefault) ?? this.warehouses[0];
        if (defaultWarehouse) {
          this.selectedWarehouseId = String(defaultWarehouse.id);
        }
      }

      if (this.lines.length === 0 && !this.isEditMode) {
        this.lines = [this.createEmptyLine()];
      }

      this.fire("suppliers", this);
      this.fire("warehouses", this);
      this.fire("products", this);
      this.fire("selectedVendorId", this);
      this.fire("selectedWarehouseId", this);
      this.fire("lines", this);
      await this.loadVendorProducts();
    } catch (exception) {
      this.catalogError = "Failed to load form data: " + exception.toString();
      this.fire("catalogError", this);
    } finally {
      this.isCatalogLoading = false;
      this.fire("isCatalogLoading", this);
      if (this.isEditMode && this.linesHydratedForPoId === 0) {
        void this.populateFromPurchaseOrder();
      }
    }
  };

  public loadVendorProducts = async (): Promise<void> => {
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    const vendorId = Number(this.selectedVendorId);
    if (!orgId || !vendorId) {
      this.vendorProducts = [];
      this.isVendorProductsLoading = false;
      this.fire("vendorProducts", this);
      this.fire("isVendorProductsLoading", this);
      return;
    }

    this.isVendorProductsLoading = true;
    this.fire("isVendorProductsLoading", this);

    try {
      this.vendorProducts = await ProductApi.getProductsByVendor(orgId, vendorId);
      this.clearInvalidLineProducts();
      this.fire("vendorProducts", this);
    } catch (exception) {
      this.vendorProducts = [];
      this.catalogError =
        "Failed to load supplier products: " + exception.toString();
      this.fire("vendorProducts", this);
      this.fire("catalogError", this);
    } finally {
      this.isVendorProductsLoading = false;
      this.fire("isVendorProductsLoading", this);
    }
  };

  private clearInvalidLineProducts(): void {
    const allowedIds = new Set(
      Array.from(this.vendorProducts).map((product) => String(product.id))
    );
    let changed = false;
    const nextLines: PoLineDraft[] = [];
    for (const line of this.lines) {
      if (line.productId && !allowedIds.has(line.productId)) {
        nextLines.push({ ...line, productId: "", unitPrice: "0" });
        changed = true;
      } else {
        nextLines.push(line);
      }
    }
    if (changed) {
      this.lines = nextLines;
      this.fire("lines", this);
    }
  }

  private onSupplierSelected = (vendorId: string): void => {
    this.selectedVendorId = vendorId;
    this.formError = "";
    this.fire("selectedVendorId", this);
    this.fire("formError", this);
    void this.loadVendorProducts();
  };

  private createEmptyLine(): PoLineDraft {
    return {
      key: `line-${Date.now()}-${Math.random().toString(36).slice(2, 7)}`,
      productId: "",
      quantity: "1",
      unitPrice: "0",
    };
  }

  public addLine = (): void => {
    this.lines = [...Array.from(this.lines), this.createEmptyLine()];
    this.fire("lines", this);
  };

  public removeLine = (key: string): void => {
    this.lines = Array.from(this.lines).filter((line) => line.key !== key);
    if (this.lines.length === 0) {
      this.lines = [this.createEmptyLine()];
    }
    this.fire("lines", this);
  };

  public updateLine = (
    key: string,
    field: keyof Omit<PoLineDraft, "key">,
    value: string
  ): void => {
    const nextLines: PoLineDraft[] = [];
    for (const line of this.lines) {
      if (line.key !== key) {
        nextLines.push(line);
        continue;
      }

      const updated: PoLineDraft = { ...line, [field]: value };

      if (field === "productId" && value) {
        const product = this.products.find((p) => String(p.id) === value);
        if (product) {
          updated.unitPrice = String(product.purchasePrice || 0);
        }
      }

      nextLines.push(updated);
    }
    this.lines = nextLines;
    this.fire("lines", this);
  };

  private renderTextField(
    label: string,
    value: string,
    placeHolder: string,
    onChange: (val: string) => void,
    required?: boolean
  ): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({
            data: required ? label + " *" : label,
            className: "fieldLabel",
            key: "0",
          }),
          ui.InputField({
            value,
            placeHolder,
            onChanged: onChange,
            onFocusChange: () => {},
            key: "1",
          }),
        ],
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField storeFormField",
      key: label,
    });
  }

  private renderInlineSelect(
    value: string,
    options: { value: string; label: string }[],
    onChange: (val: string) => void,
    emptyLabel?: string
  ): ReactNode {
    const selectOptions = emptyLabel
      ? [{ value: "", label: emptyLabel }, ...options]
      : options;

    return React.createElement(
      "select",
      {
        className: "roleSelect poLineSelect",
        value,
        disabled: this.isLoading || this.isCatalogLoading,
        onChange: (event: React.ChangeEvent<HTMLSelectElement>) => {
          onChange(event.target.value);
        },
      },
      selectOptions.map((option) =>
        React.createElement(
          "option",
          { value: option.value, key: option.value || "empty" },
          option.label
        )
      )
    );
  }

  private renderInlineInput(
    value: string,
    placeHolder: string,
    onChange: (val: string) => void
  ): ReactNode {
    return ui.InputField({
      value,
      placeHolder,
      onChanged: onChange,
      onFocusChange: () => {},
      className: "poLineInput",
    });
  }

  private renderSelectField(
    label: string,
    value: string,
    options: { value: string; label: string }[],
    onChange: (val: string) => void,
    required?: boolean,
    emptyLabel?: string
  ): ReactNode {
    const selectOptions = emptyLabel
      ? [{ value: "", label: emptyLabel }, ...options]
      : options;

    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({
            data: required ? label + " *" : label,
            className: "fieldLabel",
            key: "0",
          }),
          React.createElement(
            "select",
            {
              className: "roleSelect",
              value,
              disabled: this.isLoading || this.isCatalogLoading,
              onChange: (event: React.ChangeEvent<HTMLSelectElement>) => {
                onChange(event.target.value);
              },
              key: "1",
            },
            selectOptions.map((option) =>
              React.createElement(
                "option",
                { value: option.value, key: option.value || "empty" },
                option.label
              )
            )
          ),
        ],
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField storeFormField",
      key: label,
    });
  }

  private lineTotal(line: PoLineDraft): number {
    const qty = Number(line.quantity);
    const price = Number(line.unitPrice);
    if (!Number.isFinite(qty) || !Number.isFinite(price)) {
      return 0;
    }
    return qty * price;
  }

  private orderSubtotal(): number {
    return Array.from(this.lines).reduce(
      (sum, line) => sum + this.lineTotal(line),
      0
    );
  }

  private renderLineRow(
    line: PoLineDraft,
    index: number,
    productOptions: { value: string; label: string }[]
  ): ReactNode {
    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          ui.Container({
            child: this.renderInlineSelect(
              line.productId,
              productOptions,
              (val) => this.updateLine(line.key, "productId", val),
              "Select product"
            ),
            className: "poLineFieldCell",
            key: "0",
          }),
          ui.Container({
            child: this.renderInlineInput(line.quantity, "0", (val) => {
              this.updateLine(line.key, "quantity", val);
            }),
            className: "poLineFieldCell",
            key: "1",
          }),
          ui.Container({
            child: this.renderInlineInput(line.unitPrice, "0.00", (val) => {
              this.updateLine(line.key, "unitPrice", val);
            }),
            className: "poLineFieldCell",
            key: "2",
          }),
          TextView({
            data: this.lineTotal(line).toFixed(2),
            className: "entityCell poLineTotalCell",
            key: "3",
          }),
          ui.Container({
            child: IconButton({
              icon: MaterialIcons.delete_outline,
              onPressed: () => this.removeLine(line.key),
              onFocusChange: () => {},
              className: "entityActionIcon entityActionDelete",
            }),
            className: "poLineActionsCell",
            key: "4",
          }),
        ],
        className: "entityTableGrid poLineTableGrid",
      }),
      className: "entityTableRow",
      key: `line-${index}`,
    });
  }

  private renderLineItems(): ReactNode {
    if (!this.selectedVendorId) {
      return TextView({
        data: "Select a supplier to load products for line items.",
        className: "storeCountLabel",
      });
    }

    if (this.isVendorProductsLoading) {
      return TextView({
        data: "Loading products for selected supplier...",
        className: "storeCountLabel",
      });
    }

    if (this.vendorProducts.length === 0) {
      return TextView({
        data: "No active products are linked to this supplier yet.",
        className: "storeCountLabel",
      });
    }

    const productOptions: { value: string; label: string }[] = [];
    for (const product of this.vendorProducts) {
      productOptions.push({
        value: String(product.id),
        label: `${product.sku} — ${product.name}`,
      });
    }

    const lineRows: ReactNode[] = [];
    let lineIndex = 0;
    for (const line of this.lines) {
      lineRows.push(this.renderLineRow(line, lineIndex, productOptions));
      lineIndex += 1;
    }

    return ui.Column({
      crossAxisAlignment: ui.CrossAxisAlignment.stretch,
      children: [
        ui.Row({
          mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
          crossAxisAlignment: ui.CrossAxisAlignment.center,
          children: [
            TextView({
              data: "Line Items",
              className: "poFormSectionTitle",
              key: "0",
            }),
            TextButton({
              label: "+ Add Line",
              disable: this.isLoading,
              onPressed: () => this.addLine(),
              onFocusChange: () => {},
              className: "primary",
              key: "1",
            }),
          ],
          className: "poSectionHeaderRow",
          key: "header",
        }),
        ui.Container({
          child: ui.Column({
            crossAxisAlignment: ui.CrossAxisAlignment.stretch,
            children: [
              ui.Container({
                child: ui.Row({
                  mainAxisSize: ui.MainAxisSize.max,
                  children: [
                    TextView({
                      data: "PRODUCT",
                      className: "entityHeaderCell",
                      key: "0",
                    }),
                    TextView({
                      data: "QTY",
                      className: "entityHeaderCell",
                      key: "1",
                    }),
                    TextView({
                      data: "UNIT PRICE",
                      className: "entityHeaderCell",
                      key: "2",
                    }),
                    TextView({
                      data: "LINE TOTAL",
                      className: "entityHeaderCell",
                      key: "3",
                    }),
                    TextView({
                      data: "",
                      className: "entityHeaderCell entityHeaderActions",
                      key: "4",
                    }),
                  ],
                  className: "entityTableGrid poLineTableGrid",
                }),
                className: "entityTableHeader",
                key: "table-header",
              }),
              ...lineRows,
              React.createElement(
                "div",
                { className: "poSubtotalBar", key: "subtotal-bar" },
                React.createElement(
                  "span",
                  { className: "poSubtotalLabel" },
                  `${this.lines.length} line${this.lines.length === 1 ? "" : "s"}`
                ),
                React.createElement(
                  "span",
                  { className: "poSubtotalValue" },
                  `Subtotal: ${this.orderSubtotal().toFixed(2)}`
                )
              ),
            ],
          }),
          className: "entityTableWrap poLineTableWrap",
          key: "table",
        }),
      ],
    });
  }
  public componentDidUpdate(prevProps: PurchaseOrderFormPageProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.user !== this.props.user) {
      this.updateObservable("user", prevProps.user, this.props.user);

      this.fire("user", this);
    }

    if (prevProps.purchaseOrder !== this.props.purchaseOrder) {
      this.updateObservable(
        "purchaseOrder",
        prevProps.purchaseOrder,
        this.props.purchaseOrder
      );

      this.fire("purchaseOrder", this);
    }
  }
  public setUserProfileData(val: UserProfileByUser): void {
    let isValChanged: boolean = this.userProfileData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("userProfileData", this.userProfileData, val);

    MessageDispatch.get().dispose(this.userProfileData);

    this.userProfileData = val;

    this.fire("userProfileData", this);
  }
  public computeUserProfileData = async (): Promise<void> => {
    try {
      this.setUserProfileData(
        await Query.get().getUserProfileByUser(
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_PURCHASEORDERFORMPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
          new UserProfileByUserRequest({ user: this.user }),
          { "synchronize": true }
        )
      );
    } catch (exception) {
      console.log(
        " exception in computeUserProfileData : " + exception.toString()
      );

      this.setUserProfileData(null);
    }
  };
  public setUserProfile(val: UserProfile): void {
    let isValChanged: boolean = this.userProfile !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("userProfile", this.userProfile, val);

    this.userProfile = val;

    this.fire("userProfile", this);
  }
  public computeUserProfile = (): void => {
    try {
      this.setUserProfile(
        this.userProfileData !== null ? this.userProfileData.items.first : null
      );
    } catch (exception) {
      console.log(" exception in computeUserProfile : " + exception.toString());

      this.setUserProfile(null);
    }
  };
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this.organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("organization", this.organization, val);

    this.organization = val;

    this.fire("organization", this);
  }
  public computeOrganization = (): void => {
    try {
      this.setOrganization(
        this.userProfile !== null ? this.userProfile.organization : null
      );
    } catch (exception) {
      console.log(
        " exception in computeOrganization : " + exception.toString()
      );

      this.setOrganization(null);
    }
  };
  public setPoData(val: PurchaseOrderItem): void {
    let isValChanged: boolean = this.poData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("poData", this.poData, val);

    MessageDispatch.get().dispose(this.poData);

    this.poData = val;

    this.fire("poData", this);
  }
  public computePoData = async (): Promise<void> => {
    try {
      this.setPoData(
        this.isEditMode
          ? await Query.get().getPurchaseOrderItem(
              UsageConstants.QUERY_GETPURCHASEORDERITEM_PURCHASEORDERFORMPAGE_PROPERTIES_PODATA_COMPUTATION,
              new PurchaseOrderItemRequest({
                purchaseOrder: this.purchaseOrder,
              }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(" exception in computePoData : " + exception.toString());

      this.setPoData(null);
    }
  };
  public setIsEditMode(val: boolean): void {
    let isValChanged: boolean = this.isEditMode !== val;

    if (!isValChanged) {
      return;
    }

    this.isEditMode = val;

    this.fire("isEditMode", this);
  }
  public computeIsEditMode = (): void => {
    try {
      this.setIsEditMode(
        this.purchaseOrder !== null &&
          this.purchaseOrder.saveStatus !== DBSaveStatus.New
      );
    } catch (exception) {
      console.log(" exception in computeIsEditMode : " + exception.toString());

      this.setIsEditMode(false);
    }
  };
  public setNotes(val: string): void {
    let isValChanged: boolean = this.notes !== val;

    if (!isValChanged) {
      return;
    }

    this.notes = val;

    this.fire("notes", this);
  }
  public setIsLoading(val: boolean): void {
    let isValChanged: boolean = this.isLoading !== val;

    if (!isValChanged) {
      return;
    }

    this.isLoading = val;

    this.fire("isLoading", this);
  }
  public render(): ReactNode {
    const supplierOptions: { value: string; label: string }[] = [];
    for (const supplier of this.suppliers) {
      supplierOptions.push({
        value: String(supplier.id),
        label: `${supplier.code} — ${supplier.name}`,
      });
    }
    const warehouseOptions: { value: string; label: string }[] = [];
    for (const warehouse of this.warehouses) {
      warehouseOptions.push({
        value: String(warehouse.id),
        label: `${warehouse.code} — ${warehouse.name}`,
      });
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
            activeRoute: "/procurement/purchase-orders",
            onNavigate: (route) => {
              this.onNavigateHandler(route);
            },
            key: "0",
          }),
          ui.Container({
            child: ui.Column({
              crossAxisAlignment: ui.CrossAxisAlignment.stretch,
              mainAxisSize: ui.MainAxisSize.max,
              children: [
                IMSAppHeaderWidget({
                  title: this.isEditMode
                    ? "Edit Purchase Order"
                    : "New Purchase Order",
                  organization: this.organization,
                  key: "0",
                }),
                ScrollView2({
                  child: ui.Container({
                    child: ui.Column({
                      crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                      children: [
                        React.createElement(
                          "div",
                          { className: "poPageHeader", key: "title" },
                          TextView({
                            data:
                              "Select supplier, warehouse, and line items for procurement.",
                            className: "poPageSubtitle",
                            key: "0",
                          })
                        ),
                        this.catalogError
                          ? TextView({
                              data: this.catalogError,
                              className: "storeFormError",
                              key: "catalogError",
                            })
                          : null,
                        this.isCatalogLoading
                          ? TextView({
                              data: "Loading suppliers, warehouses, and products...",
                              className: "storeCountLabel",
                              key: "loading",
                            })
                          : ui.Container({
                              child: ui.Column({
                                crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                                children: [
                                  ui.Container({
                                    child: ui.Column({
                                      crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                                      children: [
                                        TextView({
                                          data: "Order Details",
                                          className: "poFormSectionTitle",
                                          key: "section-title",
                                        }),
                                        React.createElement(
                                          "div",
                                          { className: "productFormGrid poDetailsGrid", key: "grid" },
                                          this.renderSelectField(
                                        "Supplier",
                                        this.selectedVendorId,
                                        supplierOptions,
                                        (val) => this.onSupplierSelected(val),
                                        true,
                                        supplierOptions.length
                                          ? "Select supplier"
                                          : "No suppliers available"
                                      ),
                                      this.renderSelectField(
                                        "Warehouse",
                                        this.selectedWarehouseId,
                                        warehouseOptions,
                                        (val) => {
                                          this.selectedWarehouseId = val;
                                          this.fire("selectedWarehouseId", this);
                                        },
                                        true,
                                        warehouseOptions.length
                                          ? "Select warehouse"
                                          : "No warehouses available"
                                      ),
                                      this.renderTextField(
                                        "Order Date",
                                        this.orderDate,
                                        "YYYY-MM-DD",
                                        (val) => {
                                          this.orderDate = val;
                                          this.fire("orderDate", this);
                                        },
                                        true
                                      ),
                                      this.renderTextField(
                                        "Expected Delivery",
                                        this.expectedDeliveryDate,
                                        "YYYY-MM-DD (optional)",
                                        (val) => {
                                          this.expectedDeliveryDate = val;
                                          this.fire("expectedDeliveryDate", this);
                                        }
                                      ),
                                      ui.Container({
                                        child: ui.Column({
                                          mainAxisSize: ui.MainAxisSize.min,
                                          crossAxisAlignment:
                                            ui.CrossAxisAlignment.stretch,
                                          children: [
                                            TextView({
                                              data: "Notes",
                                              className: "fieldLabel",
                                              key: "0",
                                            }),
                                            ui.InputField({
                                              value: this.notes,
                                              placeHolder:
                                                "e.g. Please deliver fresh stock with minimum 5 days shelf life. Call before arrival.",
                                              onChanged: (text: string) => {
                                                this.setNotes(text);
                                              },
                                              onFocusChange: () => {},
                                              key: "1",
                                            }),
                                          ],
                                        }),
                                        className:
                                          "FormFieldStyle IMSInputFieldWidget formField storeFormField productFormFullWidth",
                                        key: "notes",
                                      }),
                                      React.createElement(
                                        "p",
                                        {
                                          className: "categoryImageHint productFormFullWidth",
                                          key: "email-hint",
                                        },
                                        "A purchase order PDF is emailed to the supplier automatically when you save this order."
                                      )
                                        ),
                                      ],
                                    }),
                                    className: "glassCard poFormCard",
                                    key: "details",
                                  }),
                                  ui.Container({
                                    child: this.renderLineItems(),
                                    className: "glassCard poFormCard",
                                    key: "lines",
                                  }),
                                  this.formError
                                    ? TextView({
                                        data: this.formError,
                                        className: "storeFormError",
                                        key: "formError",
                                      })
                                    : null,
                                  this.emailSuccess
                                    ? TextView({
                                        data: this.emailSuccess,
                                        className: "storeFormSuccess",
                                        key: "emailSuccess",
                                      })
                                    : null,
                                  ui.Row({
                                    mainAxisAlignment: ui.MainAxisAlignment.end,
                                    mainAxisSize: ui.MainAxisSize.max,
                                    children: [
                                      this.isEditMode
                                        ? TextButton({
                                            label: this.emailSending
                                              ? "Sending..."
                                              : "Email supplier",
                                            onPressed:
                                              this.isLoading || this.emailSending
                                                ? undefined
                                                : () => {
                                                    void this.onEmailSupplierHandler();
                                                  },
                                            className:
                                              "SecondaryButtonStyle poEmailSupplierBtn",
                                            key: "email",
                                          })
                                        : null,
                                      CancelRefWithState({
                                        d3eState: this.d3eState,
                                        _onCancelHandler: this.onCancelHandler,
                                        key: "0",
                                      }),
                                      SaveRefWithState({
                                        d3eState: this.d3eState,
                                        _onSaveHandler: this.onSaveHandler,
                                        isEditMode: this.isEditMode,
                                        isLoading: this.isLoading,
                                        key: "1",
                                      }),
                                    ],
                                    className: "poFormActions",
                                    key: "actions",
                                  }),
                                ],
                              }),
                              key: "form",
                            }),
                      ],
                    }),
                    padding: ui.EdgeInsets.all(24.0),
                  }),
                  scrollDirection: ui.Axis.vertical,
                  className: "pageContent",
                  controller: this.d3eState.contentRefScrollController,
                }),
              ],
            }),
            className: "storeMainContent",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_FormFieldStyle_PrimaryButtonStyle PurchaseOrderFormPage pageBackground ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onInit = (): void => {
    void this.populateFromPurchaseOrder();
  };

  public populateFromPurchaseOrder = async (): Promise<void> => {
    if (!this.isEditMode) {
      return;
    }

    const po =
      this.poData !== null && this.poData.items.isNotEmpty
        ? this.poData.items.first
        : this.purchaseOrder;
    if (!po?.id) {
      return;
    }

    if (po.notes) {
      this.setNotes(po.notes);
    }
    if (po.orderDate != null) {
      this.orderDate = po.orderDate.toString();
      this.fire("orderDate", this);
    }
    if (po.vendor?.id) {
      this.selectedVendorId = String(po.vendor.id);
      this.fire("selectedVendorId", this);
      await this.loadVendorProducts();
    }
    if (po.warehouse?.id) {
      this.selectedWarehouseId = String(po.warehouse.id);
      this.fire("selectedWarehouseId", this);
    }

    if (this.linesHydratedForPoId === po.id) {
      return;
    }

    try {
      const existingLines = await PurchaseOrderApi.getPurchaseOrderLines(po.id);
      if (existingLines.length > 0) {
        const hydratedLines: PoLineDraft[] = [];
        for (const line of existingLines) {
          hydratedLines.push({
            key: `line-${line.id}`,
            productId: line.productId > 0 ? String(line.productId) : "",
            quantity: String(line.orderedQuantity),
            unitPrice: String(line.unitPrice),
          });
        }
        this.lines = hydratedLines;
      } else {
        this.lines = [this.createEmptyLine()];
      }
      this.linesHydratedForPoId = po.id;
      this.fire("lines", this);
    } catch (exception) {
      console.log(
        " exception in populateFromPurchaseOrder : " + exception.toString()
      );
    }
  };
  private async notifySupplierForPo(
    purchaseOrderId: number
  ): Promise<{ ok: boolean; message: string }> {
    const notifyResult = await PurchaseOrderApi.notifySupplier(purchaseOrderId);

    if (!notifyResult.success) {
      return {
        ok: false,
        message:
          notifyResult.errors.length > 0
            ? notifyResult.errors.join(", ")
            : "Supplier email could not be sent.",
      };
    }

    return {
      ok: true,
      message:
        notifyResult.message ||
        "Purchase order emailed to supplier with generated PDF.",
    };
  }

  public onEmailSupplierHandler = async (): Promise<void> => {
    const purchaseOrderId = this.purchaseOrder?.id ?? 0;
    if (purchaseOrderId <= 0) {
      this.formError = "Save the purchase order before emailing the supplier.";
      this.emailSuccess = "";
      this.fire("formError", this);
      this.fire("emailSuccess", this);
      return;
    }
    this.emailSending = true;
    this.formError = "";
    this.emailSuccess = "";
    this.fire("emailSending", this);
    this.fire("formError", this);
    this.fire("emailSuccess", this);

    try {
      const result = await this.notifySupplierForPo(purchaseOrderId);
      if (!result.ok) {
        this.formError = "Supplier email failed: " + result.message;
        this.fire("formError", this);
        return;
      }
      this.emailSuccess = result.message;
      this.fire("emailSuccess", this);
    } catch (exception) {
      this.formError = "Supplier email failed: " + exception.toString();
      this.fire("formError", this);
    } finally {
      this.emailSending = false;
      this.fire("emailSending", this);
    }
  };

  public onCancelHandler = (d3eState: PurchaseOrderFormPageRefs): void => {
    this.navigator.pushPurchaseOrderListPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onSaveHandler = async (
    d3eState: PurchaseOrderFormPageRefs
  ): Promise<void> => {
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    const vendorId = Number(this.selectedVendorId);
    const warehouseId = Number(this.selectedWarehouseId);

    if (!orgId) {
      this.formError = "Organization not found.";
      this.fire("formError", this);
      return;
    }
    if (!vendorId) {
      this.formError = "Please select a supplier.";
      this.fire("formError", this);
      return;
    }
    const selectedSupplier = this.suppliers.find((s) => s.id === vendorId);
    if (!selectedSupplier?.email?.trim()) {
      this.formError =
        "Selected supplier has no email address. Add an email on the supplier profile before saving.";
      this.fire("formError", this);
      return;
    }
    if (!warehouseId) {
      this.formError = "Please select a warehouse.";
      this.fire("formError", this);
      return;
    }
    if (!this.orderDate.trim()) {
      this.formError = "Order date is required.";
      this.fire("formError", this);
      return;
    }

    const validLines = Array.from(this.lines)
      .map((line, index) => ({
        lineNumber: index + 1,
        productId: Number(line.productId),
        orderedQuantity: Number(line.quantity),
        unitPrice: Number(line.unitPrice),
      }))
      .filter(
        (line) =>
          line.productId > 0 &&
          Number.isFinite(line.orderedQuantity) &&
          line.orderedQuantity > 0 &&
          Number.isFinite(line.unitPrice) &&
          line.unitPrice >= 0
      );

    if (validLines.length === 0) {
      this.formError = "Add at least one valid line item.";
      this.fire("formError", this);
      return;
    }
    this.setIsLoading(true);
    this.formError = "";
    this.emailSuccess = "";
    this.fire("formError", this);
    this.fire("emailSuccess", this);

    try {
      const poResult = this.isEditMode
        ? await PurchaseOrderApi.updatePurchaseOrder({
            id: this.purchaseOrder.id,
            vendor: vendorId,
            warehouse: warehouseId,
            orderDate: this.orderDate.trim(),
            expectedDeliveryDate: this.expectedDeliveryDate.trim() || undefined,
            notes: this.notes.trim(),
            organization: orgId,
            status: this.purchaseOrder.status?.name ?? "Draft",
          })
        : await PurchaseOrderApi.createPurchaseOrder({
            vendor: vendorId,
            warehouse: warehouseId,
            orderDate: this.orderDate.trim(),
            expectedDeliveryDate: this.expectedDeliveryDate.trim() || undefined,
            notes: this.notes.trim(),
            organization: orgId,
            status: "Draft",
          });

      if (!poResult.success || poResult.purchaseOrderId <= 0) {
        this.formError =
          poResult.errors.length > 0
            ? poResult.errors.join(", ")
            : "Failed to save purchase order.";
        return;
      }

      if (!this.isEditMode) {
        for (const line of validLines) {
          const lineResult = await PurchaseOrderApi.createPurchaseOrderLine({
            lineNumber: line.lineNumber,
            product: line.productId,
            orderedQuantity: line.orderedQuantity,
            unitPrice: line.unitPrice,
            uom: this.defaultUomId > 0 ? this.defaultUomId : undefined,
            purchaseOrder: poResult.purchaseOrderId,
          });

          if (!lineResult.success) {
            this.formError =
              lineResult.errors.length > 0
                ? lineResult.errors.join(", ")
                : "Purchase order saved, but a line item failed.";
            return;
          }
        }
      }

      const notifyResult = await this.notifySupplierForPo(
        poResult.purchaseOrderId
      );

      if (!notifyResult.ok) {
        this.formError = "PO saved, but supplier email failed: " + notifyResult.message;
        this.fire("formError", this);
        return;
      }

      this.emailSuccess = notifyResult.message;
      this.fire("emailSuccess", this);
      await new Promise((resolve) => setTimeout(resolve, 1500));

      this.navigator.pushPurchaseOrderListPage({
        user: this.user,
        target: "main",
        replace: true,
      });
    } catch (exception) {
      this.formError = "Save failed: " + exception.toString();
      this.fire("formError", this);
    } finally {
      this.setIsLoading(false);
    }
  };
  public onNavigateHandler = (route: string): void => {
    AppRouteNavigator.navigate(this.navigator, route, this.user, {
      target: "main",
      replace: true,
    });
  };
  public dispose(): void {
    MessageDispatch.get().dispose(this.userProfileData);

    MessageDispatch.get().dispose(this.poData);

    super.dispose();
  }
  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
  public get cancelRef() {
    return this.d3eState.cancelRef;
  }
  public get saveRef() {
    return this.d3eState.saveRef;
  }
}
export default function PurchaseOrderFormPage(
  props: PurchaseOrderFormPageProps
) {
  return React.createElement(_PurchaseOrderFormPageState, {
    ..._PurchaseOrderFormPageState.defaultProps,
    ...props,
  });
}
