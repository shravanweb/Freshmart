import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import PurchaseOrder from "../models/PurchaseOrder";
import UserProfile from "../models/UserProfile";
import Popup from "./Popup";
import IMSidebarWidget from "./IMSidebarWidget";
import TextButton from "./TextButton";
import User from "../models/User";
import PageNavigator from "../classes/PageNavigator";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import AppLogout from "../utils/AppLogout";
import AllPurchaseOrdersRequest from "../models/AllPurchaseOrdersRequest";
import PurchaseOrderStatus from "../classes/PurchaseOrderStatus";
import Query from "../classes/Query";
import EmptyStateWidget from "./EmptyStateWidget";
import MessageDispatch from "../rocket/MessageDispatch";
import ListWrapper from "../utils/ListWrapper";
import AllPurchaseOrders from "../classes/AllPurchaseOrders";
import ScrollView2 from "./ScrollView2";
import IMSAppHeaderWidget from "./IMSAppHeaderWidget";
import StatusBadgeWidget from "./StatusBadgeWidget";
import Organization from "../models/Organization";
import UserProfileByUser from "../classes/UserProfileByUser";
import TextView from "./TextView";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import ConfirmDeleteDialogWidget from "./ConfirmDeleteDialogWidget";
import CollectionUtils from "../utils/CollectionUtils";
import { UsageConstants } from "../rocket/D3ETemplate";
import { BuildContext } from "../classes/BuildContext";
import IconButton from "./IconButton";
import MaterialIcons from "../icons/MaterialIcons";
import D3EDate from "../classes/D3EDate";
import SupplierApi, { SupplierRecord } from "../utils/SupplierApi";
import WarehouseApi, { WarehouseRecord } from "../utils/WarehouseApi";
import PurchaseOrderApi, {
  PurchaseOrderLineRecord,
} from "../utils/PurchaseOrderApi";
import ProductApi, { ProductRecord } from "../utils/ProductApi";

const PO_STATUS_FILTER_OPTIONS = [
  { value: "", label: "All statuses" },
  { value: "Draft", label: "Draft" },
  { value: "Submitted", label: "Submitted" },
  { value: "Approved", label: "Approved" },
  { value: "PartiallyReceived", label: "Partially Received" },
  { value: "Received", label: "Received" },
  { value: "Cancelled", label: "Cancelled" },
];

type _AddButtonRefOnPressed = (d3eState: PurchaseOrderListPageRefs) => void;

type _EditButtonRefOnPressed = (d3eState: _ListItemState) => void;

type _DeleteButtonRefOnPressed = (d3eState: _ListItemState) => void;

export interface PurchaseOrderListPageProps extends BaseUIProps {
  key?: string;
  user: User;
}
/// To store state data for PurchaseOrderListPage
class PurchaseOrderListPageRefs {
  public addButtonRef: AddButtonRefState = new AddButtonRefState();
  contentRefScrollController: ui.ScrollController = new ui.ScrollController();
  public listItemState: Map<PurchaseOrder, _ListItemState> = new Map();
  public forListItem(listItem: PurchaseOrder): _ListItemState {
    let res = this.listItemState.get(listItem);

    if (res == null) {
      res = new _ListItemState(this, listItem);

      this.listItemState.set(listItem, res);
    }

    return res;
  }
}

interface DeleteButtonRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: _ListItemState;
  _onDeleteHandler?: _DeleteButtonRefOnPressed;
}

class DeleteButtonRefState extends ObjectObservable {
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

class _DeleteButtonRefWithState extends ObservableComponent<DeleteButtonRefWithStateProps> {
  deleteButtonRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: DeleteButtonRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get deleteButtonRef(): DeleteButtonRefState {
    return this.props.d3eState.deleteButtonRef;
  }
  public get d3eState(): _ListItemState {
    return this.props.d3eState;
  }
  public get _onDeleteHandler(): _DeleteButtonRefOnPressed {
    return this.props._onDeleteHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("deleteButtonRef", null, this.deleteButtonRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["deleteButtonRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Delete",
      disable: this.deleteButtonRef.disable,
      onPressed: () => {
        this._onDeleteHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "destructive",
    });
  }
}
function DeleteButtonRefWithState(props: DeleteButtonRefWithStateProps) {
  return React.createElement(_DeleteButtonRefWithState, props);
}

interface EditButtonRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: _ListItemState;
  _onEditHandler?: _EditButtonRefOnPressed;
}

class EditButtonRefState extends ObjectObservable {
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

class _EditButtonRefWithState extends ObservableComponent<EditButtonRefWithStateProps> {
  editButtonRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: EditButtonRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get editButtonRef(): EditButtonRefState {
    return this.props.d3eState.editButtonRef;
  }
  public get d3eState(): _ListItemState {
    return this.props.d3eState;
  }
  public get _onEditHandler(): _EditButtonRefOnPressed {
    return this.props._onEditHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("editButtonRef", null, this.editButtonRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["editButtonRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.symmetric({
        horizontal: 8.0,
        vertical: 0.0,
        transitions: new Map(),
      }),
      child: TextButton({
        label: "Edit",
        disable: this.editButtonRef.disable,
        onPressed: () => {
          this._onEditHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
      }),
      className: "x7cb",
    });
  }
}
function EditButtonRefWithState(props: EditButtonRefWithStateProps) {
  return React.createElement(_EditButtonRefWithState, props);
}

interface AddButtonRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: PurchaseOrderListPageRefs;
  _onAddHandler?: _AddButtonRefOnPressed;
}

class AddButtonRefState extends ObjectObservable {
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

class _AddButtonRefWithState extends ObservableComponent<AddButtonRefWithStateProps> {
  addButtonRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: AddButtonRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get addButtonRef(): AddButtonRefState {
    return this.props.d3eState.addButtonRef;
  }
  public get d3eState(): PurchaseOrderListPageRefs {
    return this.props.d3eState;
  }
  public get _onAddHandler(): _AddButtonRefOnPressed {
    return this.props._onAddHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("addButtonRef", null, this.addButtonRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["addButtonRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Add Purchase Order",
      disable: this.addButtonRef.disable,
      onPressed: () => {
        this._onAddHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "primary",
    });
  }
}
function AddButtonRefWithState(props: AddButtonRefWithStateProps) {
  return React.createElement(_AddButtonRefWithState, props);
}

class _ListItemState {
  parent: PurchaseOrderListPageRefs;
  listItem: PurchaseOrder;
  deleteButtonRef: DeleteButtonRefState = new DeleteButtonRefState();
  editButtonRef: EditButtonRefState = new EditButtonRefState();
  public constructor(parent, listItem) {
    this.parent = parent;

    this.listItem = listItem;
  }
}

class _PurchaseOrderListPageState extends ObservableComponent<PurchaseOrderListPageProps> {
  static defaultProps = { user: null };
  d3eState: PurchaseOrderListPageRefs = new PurchaseOrderListPageRefs();
  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  searchTerm: string = "";
  statusFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  showDeleteDialog: boolean = false;
  selectedEntity: PurchaseOrder = null;
  purchaseOrdersData: AllPurchaseOrders = null;
  suppliers: SupplierRecord[] = [];
  warehouses: WarehouseRecord[] = [];
  purchaseOrders: Array<PurchaseOrder> = ListWrapper.widget(
    this,
    "purchaseOrders"
  );
  itemCount: number = 0;
  filteredPurchaseOrders: Array<PurchaseOrder> = ListWrapper.widget(
    this,
    "filteredPurchaseOrders"
  );
  deleteDialogPopupPopup: Popup;
  showDetailModal: boolean = false;
  detailPurchaseOrder: PurchaseOrder = null;
  detailLines: PurchaseOrderLineRecord[] = [];
  detailProducts: ProductRecord[] = [];
  detailLoading: boolean = false;
  detailError: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: PurchaseOrderListPageProps) {
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

    this.on(["organization"], this.computePurchaseOrdersData);
    this.on(["organization"], this.loadCatalog);

    this.computePurchaseOrdersData();
    this.loadCatalog();

    this.on(
      ["purchaseOrdersData", "purchaseOrdersData.items"],
      this.computePurchaseOrders
    );

    this.computePurchaseOrders();

    this.on(
      ["purchaseOrdersData", "purchaseOrdersData.count"],
      this.computeItemCount
    );

    this.computeItemCount();

    this.on(
      [
        "purchaseOrders",
        "purchaseOrders.poNumber",
        "purchaseOrders.vendor",
        "purchaseOrders.vendor.name",
        "suppliers",
        "warehouses",
        "searchTerm",
        "statusFilter",
      ],
      this.computeFilteredPurchaseOrders
    );

    this.computeFilteredPurchaseOrders();

    this.on(
      [
        "filteredPurchaseOrders",
        "filteredPurchaseOrders.poNumber",
        "filteredPurchaseOrders.status",
        "filteredPurchaseOrders.vendor",
        "filteredPurchaseOrders.vendor.name",
        "filteredPurchaseOrders.orderDate",
        "filteredPurchaseOrders.warehouse",
        "filteredPurchaseOrders.warehouse.name",
        "itemCount",
        "organization",
        "searchTerm",
        "statusFilter",
        "isLoading",
        "loadError",
        "showDetailModal",
        "detailPurchaseOrder",
        "detailLines",
        "detailProducts",
        "detailLoading",
        "detailError",
        "user",
        "userProfile",
        "suppliers",
        "warehouses",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: PurchaseOrderListPageProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.user !== this.props.user) {
      this.updateObservable("user", prevProps.user, this.props.user);

      this.fire("user", this);
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
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_PURCHASEORDERLISTPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
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
  public loadCatalog = async (): Promise<void> => {
    if (this.organization == null) {
      this.suppliers = [];
      this.warehouses = [];
      this.fire("suppliers", this);
      this.fire("warehouses", this);
      return;
    }

    const orgId = this.organization?.id ?? this.user?.organization?.id;
    try {
      const [suppliers, warehouses] = await Promise.all([
        SupplierApi.getAllSuppliers(orgId),
        WarehouseApi.getAllWarehouses(orgId),
      ]);
      this.suppliers = suppliers;
      this.warehouses = warehouses;
    } catch {
      this.suppliers = [];
      this.warehouses = [];
    }
    this.fire("suppliers", this);
    this.fire("warehouses", this);
  };
  public setSearchTerm(val: string): void {
    let isValChanged: boolean = this.searchTerm !== val;

    if (!isValChanged) {
      return;
    }

    this.searchTerm = val;

    this.fire("searchTerm", this);
  }
  public setStatusFilter(val: string): void {
    if (this.statusFilter === val) {
      return;
    }

    this.statusFilter = val;
    this.fire("statusFilter", this);
  }
  public setShowDeleteDialog(val: boolean): void {
    let isValChanged: boolean = this.showDeleteDialog !== val;

    if (!isValChanged) {
      return;
    }

    this.showDeleteDialog = val;

    this.fire("showDeleteDialog", this);
  }
  public setSelectedEntity(val: PurchaseOrder): void {
    let isValChanged: boolean = this.selectedEntity !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("selectedEntity", this.selectedEntity, val);

    this.selectedEntity = val;

    this.fire("selectedEntity", this);
  }
  public setPurchaseOrdersData(val: AllPurchaseOrders): void {
    let isValChanged: boolean = this.purchaseOrdersData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("purchaseOrdersData", this.purchaseOrdersData, val);

    MessageDispatch.get().dispose(this.purchaseOrdersData);

    this.purchaseOrdersData = val;

    this.fire("purchaseOrdersData", this);
  }
  public computePurchaseOrdersData = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.loadError = "";
      this.setPurchaseOrdersData(null);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      this.setPurchaseOrdersData(
        await Query.get().getAllPurchaseOrders(
          UsageConstants.QUERY_GETALLPURCHASEORDERS_PURCHASEORDERLISTPAGE_PROPERTIES_PURCHASEORDERSDATA_COMPUTATION,
          new AllPurchaseOrdersRequest({ organization: this.organization }),
          { synchronize: true }
        )
      );
      this.loadError = "";
    } catch (exception) {
      console.log(
        " exception in computePurchaseOrdersData : " + exception.toString()
      );

      this.setPurchaseOrdersData(null);
      this.loadError = "Failed to load purchase orders: " + exception.toString();
      this.fire("loadError", this);
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
    }
  };
  public setPurchaseOrders(val: Array<PurchaseOrder>): void {
    let isValChanged: boolean = CollectionUtils.isNotEquals(
      this.purchaseOrders,
      val
    );

    if (!isValChanged) {
      return;
    }

    this.updateObservableColl("purchaseOrders", this.purchaseOrders, val);

    this.purchaseOrders.clear();

    this.purchaseOrders.addAll(val);

    this.fire("purchaseOrders", this);
  }
  public addToPurchaseOrders(val: PurchaseOrder, index: number = -1): void {
    if (index === -1) {
      if (!this.purchaseOrders.contains(val)) this.purchaseOrders.add(val);
    } else {
      this.purchaseOrders.remove(this.purchaseOrders.elementAt(index));

      this.purchaseOrders.add(val);
    }

    this.fire("purchaseOrders", this, val, true);

    this.updateObservable("purchaseOrders", null, val);
  }
  public removeFromPurchaseOrders(val: PurchaseOrder): void {
    this.purchaseOrders.remove(val);

    this.fire("purchaseOrders", this, val, false);

    this.removeObservable("purchaseOrders", val);
  }
  public computePurchaseOrders = (): void => {
    try {
      this.setPurchaseOrders(
        Array.from(
          this.purchaseOrdersData !== null ? this.purchaseOrdersData.items : []
        )
      );
    } catch (exception) {
      console.log(
        " exception in computePurchaseOrders : " + exception.toString()
      );

      this.setPurchaseOrders([]);
    }
  };
  public setItemCount(val: number): void {
    let isValChanged: boolean = this.itemCount !== val;

    if (!isValChanged) {
      return;
    }

    this.itemCount = val;

    this.fire("itemCount", this);
  }
  public computeItemCount = (): void => {
    try {
      this.setItemCount(
        this.purchaseOrdersData !== null ? this.purchaseOrdersData.count : 0
      );
    } catch (exception) {
      console.log(" exception in computeItemCount : " + exception.toString());

      this.setItemCount(0);
    }
  };
  public setFilteredPurchaseOrders(val: Array<PurchaseOrder>): void {
    let isValChanged: boolean = CollectionUtils.isNotEquals(
      this.filteredPurchaseOrders,
      val
    );

    if (!isValChanged) {
      return;
    }

    this.updateObservableColl(
      "filteredPurchaseOrders",
      this.filteredPurchaseOrders,
      val
    );

    this.filteredPurchaseOrders.clear();

    this.filteredPurchaseOrders.addAll(val);

    this.fire("filteredPurchaseOrders", this);
  }
  public addToFilteredPurchaseOrders(
    val: PurchaseOrder,
    index: number = -1
  ): void {
    if (index === -1) {
      if (!this.filteredPurchaseOrders.contains(val))
        this.filteredPurchaseOrders.add(val);
    } else {
      this.filteredPurchaseOrders.remove(
        this.filteredPurchaseOrders.elementAt(index)
      );

      this.filteredPurchaseOrders.add(val);
    }

    this.fire("filteredPurchaseOrders", this, val, true);

    this.updateObservable("filteredPurchaseOrders", null, val);
  }
  public removeFromFilteredPurchaseOrders(val: PurchaseOrder): void {
    this.filteredPurchaseOrders.remove(val);

    this.fire("filteredPurchaseOrders", this, val, false);

    this.removeObservable("filteredPurchaseOrders", val);
  }
  public computeFilteredPurchaseOrders = (): void => {
    try {
      let list = Array.from(this.purchaseOrders);
      const term = this.searchTerm?.trim().toLowerCase() ?? "";

      if (term) {
        list = list.filter(
          (p) =>
            p.poNumber.toLowerCase().includes(term) ||
            this.resolveSupplierName(p).toLowerCase().includes(term)
        );
      }

      if (this.statusFilter) {
        list = list.filter((p) => p.status?.name === this.statusFilter);
      }

      this.setFilteredPurchaseOrders(list);
    } catch (exception) {
      console.log(
        " exception in computeFilteredPurchaseOrders : " + exception.toString()
      );

      this.setFilteredPurchaseOrders([]);
    }
  };

  private resolveSupplierName(po: PurchaseOrder): string {
    if (po.vendor != null && po.vendor.name.isNotEmpty) {
      return po.vendor.name;
    }
    const vendorId = po.vendor?.id ?? 0;
    if (vendorId > 0) {
      const match = this.suppliers.find((s) => s.id === vendorId);
      if (match?.name) {
        return match.name;
      }
    }
    return "—";
  }

  private resolveWarehouseName(po: PurchaseOrder): string {
    if (po.warehouse != null && po.warehouse.name.isNotEmpty) {
      return po.warehouse.name;
    }
    const warehouseId = po.warehouse?.id ?? 0;
    if (warehouseId > 0) {
      const match = this.warehouses.find((w) => w.id === warehouseId);
      if (match?.name) {
        return match.name;
      }
    }
    return "—";
  }

  private formatOrderDate(orderDate: D3EDate | null): string {
    if (orderDate == null) {
      return "—";
    }

    try {
      return orderDate.toString();
    } catch {
      return "—";
    }
  }

  private renderSearchField(
    value: string,
    placeHolder: string,
    onChange: (val: string) => void
  ): ReactNode {
    return ui.Container({
      child: ui.InputField({
        value,
        placeHolder,
        onChanged: onChange,
        onFocusChange: () => {},
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField storeFormField",
      key: "search",
    });
  }

  private renderStatusFilter(): ReactNode {
    return ui.Container({
      child: React.createElement(
        "select",
        {
          className: "roleSelect",
          value: this.statusFilter,
          onChange: (event: React.ChangeEvent<HTMLSelectElement>) => {
            this.setStatusFilter(event.target.value);
          },
        },
        PO_STATUS_FILTER_OPTIONS.map((option) =>
          React.createElement(
            "option",
            { value: option.value, key: option.value },
            option.label
          )
        )
      ),
      className: "FormFieldStyle IMSInputFieldWidget formField storeFormField storeStatusFilter",
      key: "status-filter",
    });
  }

  public openDetailView = (po: PurchaseOrder): void => {
    this.detailPurchaseOrder = po;
    this.detailLines = [];
    this.detailProducts = [];
    this.detailError = "";
    this.showDetailModal = true;
    this.fire("showDetailModal", this);
    this.fire("detailPurchaseOrder", this);
    void this.loadDetailData(po);
  };

  public closeDetailView = (): void => {
    this.showDetailModal = false;
    this.detailPurchaseOrder = null;
    this.detailLines = [];
    this.detailProducts = [];
    this.detailError = "";
    this.detailLoading = false;
    this.fire("showDetailModal", this);
    this.fire("detailPurchaseOrder", this);
    this.fire("detailLines", this);
    this.fire("detailProducts", this);
    this.fire("detailError", this);
    this.fire("detailLoading", this);
  };

  private loadDetailData = async (po: PurchaseOrder): Promise<void> => {
    if (!po?.id) {
      this.detailError = "Purchase order not found.";
      this.fire("detailError", this);
      return;
    }

    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.detailError = "Organization not found.";
      this.fire("detailError", this);
      return;
    }

    this.detailLoading = true;
    this.detailError = "";
    this.fire("detailLoading", this);
    this.fire("detailError", this);

    try {
      const [lines, products] = await Promise.all([
        PurchaseOrderApi.getPurchaseOrderLines(po.id),
        ProductApi.getAllProducts(orgId),
      ]);
      this.detailLines = Array.from(lines);
      this.detailProducts = Array.from(products);
      this.fire("detailLines", this);
      this.fire("detailProducts", this);
    } catch (exception) {
      this.detailLines = [];
      this.detailProducts = [];
      this.detailError = "Failed to load purchase order details: " + exception.toString();
      this.fire("detailLines", this);
      this.fire("detailProducts", this);
      this.fire("detailError", this);
    } finally {
      this.detailLoading = false;
      this.fire("detailLoading", this);
    }
  };

  private resolveProductLabel(productId: number): string {
    const product = this.detailProducts.find((item) => item.id === productId);
    if (!product) {
      return productId > 0 ? `Product #${productId}` : "—";
    }
    return `${product.sku} — ${product.name}`;
  }

  private renderDetailField(label: string, value: string, key: string): ReactNode {
    return React.createElement(
      "div",
      { className: "detailField", key },
      React.createElement("span", { className: "detailFieldLabel" }, label),
      React.createElement("span", { className: "detailFieldValue" }, value || "—")
    );
  }

  private renderDetailModal(): ReactNode {
    const po = this.detailPurchaseOrder;
    if (!po) {
      return null;
    }

    const lineRows: ReactNode[] = [];
    let lineIndex = 0;
    let orderSubtotal = 0;
    for (const line of Array.from(this.detailLines)) {
      const lineTotal = line.orderedQuantity * line.unitPrice;
      orderSubtotal += lineTotal;
      lineRows.push(
        ui.Container({
          child: ui.Row({
            mainAxisSize: ui.MainAxisSize.max,
            crossAxisAlignment: ui.CrossAxisAlignment.center,
            children: [
              TextView({
                data: String(line.lineNumber || lineIndex + 1),
                className: "entityCell",
                key: "0",
              }),
              TextView({
                data: this.resolveProductLabel(line.productId),
                className: "entityCell entityCellName",
                key: "1",
              }),
              TextView({
                data: String(line.orderedQuantity),
                className: "entityCell",
                key: "2",
              }),
              TextView({
                data: line.unitPrice.toFixed(2),
                className: "entityCell",
                key: "3",
              }),
              TextView({
                data: lineTotal.toFixed(2),
                className: "entityCell",
                key: "4",
              }),
            ],
            className: "entityTableGrid poDetailLineGrid",
          }),
          className: "entityTableRow",
          key: `detail-line-${lineIndex}`,
        })
      );
      lineIndex += 1;
    }

    const supplierName = this.resolveSupplierName(po);
    const warehouseName = this.resolveWarehouseName(po);
    const orderDate = this.formatOrderDate(po.orderDate);
    const notes = po.notes?.trim() ?? "";

    return React.createElement(
      "div",
      {
        className: "storeModalOverlay",
        onClick: () => this.closeDetailView(),
      },
      React.createElement(
        "div",
        {
          className: "storeModalPanel glassCard productModalPanel entityDetailModal",
          onClick: (event: React.MouseEvent) => event.stopPropagation(),
          role: "dialog",
          "aria-modal": "true",
        },
        React.createElement(
          "div",
          { className: "storeModalHeader entityDetailHeader" },
          React.createElement(
            "div",
            { className: "entityDetailHeaderMain" },
            React.createElement(
              "div",
              { className: "entityDetailTitleRow" },
              React.createElement(
                "h2",
                { className: "storeModalTitle entityDetailTitle" },
                po.poNumber || "Purchase Order"
              ),
              React.createElement(
                "div",
                { className: "entityDetailStatusWrap" },
                StatusBadgeWidget({ status: po.status?.name ?? "Draft" })
              )
            ),
            React.createElement(
              "p",
              { className: "entityDetailMeta" },
              `${supplierName} · ${warehouseName} · ${orderDate}`
            )
          ),
          React.createElement(
            "button",
            {
              type: "button",
              className: "storeModalClose",
              "aria-label": "Close",
              onClick: () => this.closeDetailView(),
            },
            "✕"
          )
        ),
        React.createElement(
          "div",
          { className: "storeModalBody productModalBody entityDetailBody" },
          notes
            ? React.createElement(
                "div",
                { className: "entityDetailNotes" },
                React.createElement("span", { className: "detailFieldLabel" }, "Notes"),
                React.createElement("p", { className: "entityDetailNotesText" }, notes)
              )
            : null,
          this.detailLoading
            ? React.createElement("p", { className: "storeCountLabel" }, "Loading line items...")
            : this.detailError
              ? TextView({ data: this.detailError, className: "storeFormError" })
              : React.createElement(
                  "div",
                  { className: "detailSection" },
                  React.createElement(
                    "div",
                    { className: "entityDetailSectionHead" },
                    React.createElement("h3", { className: "entityDetailSectionTitle" }, "Line Items"),
                    React.createElement(
                      "span",
                      { className: "entityDetailSectionCount" },
                      `${lineRows.length} item${lineRows.length === 1 ? "" : "s"}`
                    )
                  ),
                  lineRows.length === 0
                    ? React.createElement("p", { className: "storeCountLabel" }, "No line items.")
                    : React.createElement(
                        "div",
                        { className: "entityTableWrap entityDetailTableWrap" },
                        ui.Container({
                          child: ui.Row({
                            mainAxisSize: ui.MainAxisSize.max,
                            children: [
                              TextView({ data: "#", className: "entityHeaderCell", key: "0" }),
                              TextView({ data: "PRODUCT", className: "entityHeaderCell", key: "1" }),
                              TextView({ data: "QTY", className: "entityHeaderCell", key: "2" }),
                              TextView({ data: "UNIT PRICE", className: "entityHeaderCell", key: "3" }),
                              TextView({ data: "TOTAL", className: "entityHeaderCell", key: "4" }),
                            ],
                            className: "entityTableGrid poDetailLineGrid",
                          }),
                          className: "entityTableHeader entityDetailTableHeader",
                        }),
                        ...lineRows,
                        React.createElement(
                          "div",
                          { className: "entityDetailSubtotalRow" },
                          React.createElement("span", null, "Subtotal"),
                          React.createElement("span", null, orderSubtotal.toFixed(2))
                        )
                      )
                )
        ),
        React.createElement(
          "div",
          { className: "storeModalFooter" },
          TextButton({
            label: "Close",
            onPressed: () => this.closeDetailView(),
            onFocusChange: () => {},
            className: "secondary",
          }),
          TextButton({
            label: "Edit",
            onPressed: () => {
              this.closeDetailView();
              this.onEditHandler(this.d3eState.forListItem(po));
            },
            onFocusChange: () => {},
            className: "primary",
          })
        )
      )
    );
  }

  private renderTableRow(po: PurchaseOrder, index: number): ReactNode {
    const supplierName = this.resolveSupplierName(po);
    const warehouseName = this.resolveWarehouseName(po);

    return React.createElement(
      "div",
      {
        className: "entityTableRow entityTableRowClickable",
        key: "row-" + index,
        onClick: () => this.openDetailView(po),
        role: "button",
        tabIndex: 0,
        onKeyDown: (event: React.KeyboardEvent) => {
          if (event.key === "Enter" || event.key === " ") {
            event.preventDefault();
            this.openDetailView(po);
          }
        },
      },
      ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          TextView({
            data: po.poNumber || "—",
            className: "entityCell entityCellName",
            key: "0",
          }),
          TextView({
            data: supplierName,
            className: "entityCell",
            key: "1",
          }),
          TextView({
            data: this.formatOrderDate(po.orderDate),
            className: "entityCell",
            key: "2",
          }),
          TextView({
            data: warehouseName,
            className: "entityCell",
            key: "3",
          }),
          ui.Container({
            child: StatusBadgeWidget({
              status: po.status?.name ?? "Draft",
            }),
            className: "entityCell",
            key: "4",
          }),
          React.createElement(
            "div",
            {
              className: "entityActionsCell",
              onClick: (event: React.MouseEvent) => event.stopPropagation(),
              onKeyDown: (event: React.KeyboardEvent) => event.stopPropagation(),
              key: "actions-wrap",
            },
            ui.Row({
              mainAxisSize: ui.MainAxisSize.min,
              children: [
                IconButton({
                  icon: MaterialIcons.edit,
                  onPressed: () => {
                    this.onEditHandler(this.d3eState.forListItem(po));
                  },
                  onFocusChange: () => {},
                  className: "entityActionIcon entityActionEdit",
                  key: "0",
                }),
                IconButton({
                  icon: MaterialIcons.delete_outline,
                  onPressed: () => {
                    this.onDeleteHandler(this.d3eState.forListItem(po));
                  },
                  onFocusChange: () => {},
                  className: "entityActionIcon entityActionDelete",
                  key: "1",
                }),
              ],
              className: "entityActionsCell",
              key: "5",
            })
          ),
        ],
        className: "entityTableGrid poEntityTableGrid",
      })
    );
  }

  public render(): ReactNode {
    const filtered = Array.from(this.filteredPurchaseOrders);
    const countLabel =
      filtered.length + " record" + (filtered.length === 1 ? "" : "s");

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
                  title: "Purchase Orders",
                  organization: this.organization,
                  onLogout: () => {
                    this.onLogoutHandler(this.d3eState);
                  },
                  onProfile: () => {
                    this.onProfileHandler(this.d3eState);
                  },
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
                            TextView({
                              data:
                                "Track procurement requests from draft through receipt.",
                              className: "pageToolbarHint",
                              key: "0",
                            }),
                            TextButton({
                              label: "+ Add Purchase Order",
                              onPressed: () => {
                                this.onAddHandler(this.d3eState);
                              },
                              onFocusChange: () => {},
                              className: "primary",
                              key: "1",
                            }),
                          ],
                          className: "storeToolbarRow pageToolbarRow",
                          key: "toolbar",
                        }),
                        ui.Row({
                          mainAxisSize: ui.MainAxisSize.max,
                          crossAxisAlignment: ui.CrossAxisAlignment.center,
                          children: [
                            ui.Container({
                              child: this.renderSearchField(
                                this.searchTerm,
                                "Search PO number or supplier...",
                                (val) => this.setSearchTerm(val)
                              ),
                              className: "storeSearchField",
                              key: "0",
                            }),
                            this.renderStatusFilter(),
                            TextView({
                              data: countLabel,
                              className: "entityFilterCount",
                              key: "2",
                            }),
                          ],
                          className: "storeFilterRow entityFilterRow",
                          key: "filters",
                        }),
                        this.loadError
                          ? TextView({
                              data: this.loadError,
                              className: "storeFormError",
                              key: "loadError",
                            })
                          : null,
                        this.isLoading
                          ? TextView({
                              data: "Loading purchase orders...",
                              className: "storeCountLabel",
                              key: "loading",
                            })
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title: "No purchase orders",
                                message:
                                  "Create your first purchase order to start procuring inventory from suppliers.",
                                actionLabel: "+ Add Purchase Order",
                                onAction: () => {
                                  this.onEmptyActionHandler(this.d3eState);
                                },
                                key: "empty",
                              })
                            : ui.Container({
                                child: ui.Column({
                                  crossAxisAlignment:
                                    ui.CrossAxisAlignment.stretch,
                                  children: [
                                    ui.Container({
                                      child: ui.Row({
                                        mainAxisSize: ui.MainAxisSize.max,
                                        children: [
                                          TextView({
                                            data: "PO NUMBER",
                                            className: "entityHeaderCell",
                                            key: "0",
                                          }),
                                          TextView({
                                            data: "SUPPLIER",
                                            className: "entityHeaderCell",
                                            key: "1",
                                          }),
                                          TextView({
                                            data: "ORDER DATE",
                                            className: "entityHeaderCell",
                                            key: "2",
                                          }),
                                          TextView({
                                            data: "WAREHOUSE",
                                            className: "entityHeaderCell",
                                            key: "3",
                                          }),
                                          TextView({
                                            data: "STATUS",
                                            className: "entityHeaderCell",
                                            key: "4",
                                          }),
                                          TextView({
                                            data: "ACTIONS",
                                            className:
                                              "entityHeaderCell entityHeaderActions",
                                            key: "5",
                                          }),
                                        ],
                                        className:
                                          "entityTableGrid poEntityTableGrid",
                                      }),
                                      className: "entityTableHeader",
                                      key: "header",
                                    }),
                                    ...filtered.map((po, index) =>
                                      this.renderTableRow(po, index)
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
                  controller: this.d3eState.contentRefScrollController,
                }),
                this.showDetailModal ? this.renderDetailModal() : null,
              ],
            }),
            className: "storeMainContent",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle PurchaseOrderListPage pageBackground ",
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
  public onLogoutHandler = (d3eState: PurchaseOrderListPageRefs): void => {
    void AppLogout.signOut(this.navigator);
  };
  public onProfileHandler = (d3eState: PurchaseOrderListPageRefs): void => {
    this.navigator.pushProfileSettingsPage({
      user: this.user,
      target: "main",
      replace: false,
    });
  };
  public onAddHandler = (d3eState: PurchaseOrderListPageRefs): void => {
    this.navigator.pushPurchaseOrderFormPage({
      user: this.user,
      purchaseOrder: new PurchaseOrder(),
      target: "main",
      replace: false,
    });
  };
  public onEmptyActionHandler = (d3eState: PurchaseOrderListPageRefs): void => {
    this.navigator.pushPurchaseOrderFormPage({
      user: this.user,
      purchaseOrder: new PurchaseOrder(),
      target: "main",
      replace: false,
    });
  };
  public onEditHandler = (d3eState: _ListItemState): void => {
    this.navigator.pushPurchaseOrderFormPage({
      user: this.user,
      purchaseOrder: d3eState.listItem,
      target: "main",
      replace: false,
    });
  };
  public onDeleteHandler = (d3eState: _ListItemState): void => {
    this.setSelectedEntity(d3eState.listItem);

    this.setShowDeleteDialog(true);

    this.showDeleteDialogPopup();
  };
  public onConfirmDeleteHandler = async (): Promise<void> => {
    const purchaseOrder = this.selectedEntity;
    this.setShowDeleteDialog(false);
    this.hideDeleteDialogPopup();
    this.setSelectedEntity(null);

    if (!purchaseOrder?.id) {
      return;
    }

    try {
      const result = await PurchaseOrderApi.deletePurchaseOrder(
        purchaseOrder.id
      );
      if (!result.success) {
        this.loadError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to delete purchase order.";
        this.fire("loadError", this);
        return;
      }
      await this.computePurchaseOrdersData();
    } catch (exception) {
      this.loadError = "Delete failed: " + exception.toString();
      this.fire("loadError", this);
    }
  };
  public onCancelDeleteHandler = (): void => {
    this.setShowDeleteDialog(false);

    this.hideDeleteDialogPopup();

    this.setSelectedEntity(null);
  };
  public dispose(): void {
    MessageDispatch.get().dispose(this.userProfileData);

    MessageDispatch.get().dispose(this.purchaseOrdersData);

    this.deleteDialogPopupPopup?.dispose();

    super.dispose();
  }
  public showDeleteDialogPopup(
    d3eParams?: Partial<{
      autoClose: boolean;
      model: boolean;
      float: boolean;
      takeFocus: boolean;
    }>
  ): void {
    let autoClose = d3eParams?.autoClose;

    let model = d3eParams?.model;

    let float = d3eParams?.float;

    let takeFocus = d3eParams?.takeFocus;

    this.deleteDialogPopupPopup?.dispose();

    this.deleteDialogPopupPopup = new Popup({
      autoClose: autoClose ?? false,
      model: model ?? true,
      float: float ?? false,
      takeFocus: takeFocus ?? true,
      position: ui.PopUpPosition.Center,
      child: ConfirmDeleteDialogWidget({
        entityName: "Purchase Order",
        message: `Are you sure you want to delete "${this.selectedEntity?.poNumber ?? "this purchase order"}"?`,
        onConfirm: () => {
          this.onConfirmDeleteHandler();
        },
        onCancel: () => {
          this.onCancelDeleteHandler();
        },
      }),
    });

    this.deleteDialogPopupPopup.showPopup(this.context);
  }
  public hideDeleteDialogPopup(): void {
    this.deleteDialogPopupPopup?.dispose();
  }
  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
  public get addButtonRef() {
    return this.d3eState.addButtonRef;
  }
}
export default function PurchaseOrderListPage(
  props: PurchaseOrderListPageProps
) {
  return React.createElement(_PurchaseOrderListPageState, {
    ..._PurchaseOrderListPageState.defaultProps,
    ...props,
  });
}
