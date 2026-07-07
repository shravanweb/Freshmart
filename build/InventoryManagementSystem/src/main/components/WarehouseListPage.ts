import React, { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import User from "../models/User";
import UserProfile from "../models/UserProfile";
import Organization from "../models/Organization";
import PageNavigator from "../classes/PageNavigator";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import AppLogout from "../utils/AppLogout";
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
import IconButton from "./IconButton";
import MaterialIcons from "../icons/MaterialIcons";
import EmptyStateWidget from "./EmptyStateWidget";
import ConfirmDeleteDialogWidget from "./ConfirmDeleteDialogWidget";
import Popup from "./Popup";
import WarehouseApi, { WarehouseRecord } from "../utils/WarehouseApi";
import CollectionUtils from "../utils/CollectionUtils";
import { BuildContext } from "../classes/BuildContext";
import { formatEnumLabel, renderEntityStatusCell } from "../utils/EntityTableHelpers";

export interface WarehouseListPageProps extends BaseUIProps {
  key?: string;
  user: User;
}

const WAREHOUSE_TYPE_OPTIONS = [
  { value: "Main", label: "Main" },
  { value: "Store", label: "Store" },
  { value: "ColdStorage", label: "Cold Storage" },
  { value: "Distribution", label: "Distribution" },
];

const STATUS_OPTIONS = [
  { value: "Active", label: "Active" },
  { value: "Inactive", label: "Inactive" },
  { value: "Archived", label: "Archived" },
];

const STATUS_FILTER_OPTIONS = [
  { value: "", label: "All statuses" },
  ...STATUS_OPTIONS,
];

class _WarehouseListPageState extends ObservableComponent<WarehouseListPageProps> {
  static defaultProps = { user: null };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  warehouses: WarehouseRecord[] = [];
  searchTerm: string = "";
  statusFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  showFormModal: boolean = false;
  editingWarehouse: WarehouseRecord | null = null;
  isSaving: boolean = false;
  formError: string = "";
  formName: string = "";
  formCode: string = "";
  formWarehouseType: string = "Main";
  formAddress: string = "";
  formIsDefault: boolean = false;
  formStatus: string = "Active";
  selectedEntity: WarehouseRecord = null;
  deleteDialogPopup: Popup;

  public constructor(props: WarehouseListPageProps) {
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
    this.on(["organization"], this.loadWarehouses);
    this.on(
      [
        "warehouses",
        "searchTerm",
        "statusFilter",
        "isLoading",
        "loadError",
        "showFormModal",
        "isSaving",
        "formError",
        "formName",
        "formCode",
        "formWarehouseType",
        "formAddress",
        "formIsDefault",
        "formStatus",
        "editingWarehouse",
        "user",
        "userProfile",
        "organization",
      ],
      this.rebuild
    );
  }

  public componentDidUpdate(prevProps: WarehouseListPageProps): void {
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
      console.log(" exception in computeUserProfileData : " + exception.toString());
      this.setUserProfileData(null);
    }
  };

  public setUserProfile(val: UserProfile): void {
    if (this.userProfile === val) return;
    this.updateObservable("userProfile", this.userProfile, val);
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
    this.updateObservable("organization", this.organization, val);
    this.organization = val;
    this.fire("organization", this);
  }

  public computeOrganization = (): void => {
    this.setOrganization(
      this.userProfile !== null ? this.userProfile.organization : null
    );
  };

  public setWarehouses(val: WarehouseRecord[]): void {
    if (CollectionUtils.isNotEquals(this.warehouses, val)) {
      this.warehouses = val;
      this.fire("warehouses", this);
    }
  }

  public setSearchTerm(val: string): void {
    if (this.searchTerm === val) return;
    this.searchTerm = val;
    this.fire("searchTerm", this);
  }

  public setStatusFilter(val: string): void {
    if (this.statusFilter === val) return;
    this.statusFilter = val;
    this.fire("statusFilter", this);
  }

  public get filteredWarehouses(): WarehouseRecord[] {
    let list = this.warehouses;
    const term = this.searchTerm?.trim().toLowerCase() ?? "";
    if (term) {
      list = list.filter(
        (s) =>
          s.name.toLowerCase().includes(term) ||
          s.code.toLowerCase().includes(term)
      );
    }
    if (this.statusFilter) {
      list = list.filter((s) => s.status === this.statusFilter);
    }
    return list;
  }

  public loadWarehouses = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.loadError = "";
      this.setWarehouses([]);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const items = await WarehouseApi.getAllWarehouses(orgId);
      this.setWarehouses(items);
      this.loadError = "";
    } catch (exception) {
      this.setWarehouses([]);
      this.loadError = "Failed to load warehouses: " + exception.toString();
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
      this.fire("loadError", this);
    }
  };

  public openCreateForm = (): void => {
    this.editingWarehouse = null;
    this.formName = "";
    this.formCode = "";
    this.formWarehouseType = "Main";
    this.formAddress = "";
    this.formIsDefault = false;
    this.formStatus = "Active";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public openEditForm = (warehouse: WarehouseRecord): void => {
    this.editingWarehouse = warehouse;
    this.formName = warehouse.name;
    this.formCode = warehouse.code;
    this.formWarehouseType = warehouse.warehouseType || "Main";
    this.formAddress = warehouse.address ?? "";
    this.formIsDefault = warehouse.isDefault ?? false;
    this.formStatus = warehouse.status || "Active";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public closeForm = (): void => {
    this.showFormModal = false;
    this.editingWarehouse = null;
    this.formError = "";
    this.fire("showFormModal", this);
  };

  public saveForm = async (): Promise<void> => {
    const name = this.formName.trim();
    const code = this.formCode.trim();
    if (!name) {
      this.formError = "Name is required.";
      this.fire("formError", this);
      return;
    }
    if (!code) {
      this.formError = "Code is required.";
      this.fire("formError", this);
      return;
    }

    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.formError = "Organization not found.";
      this.fire("formError", this);
      return;
    }

    const warehouseId = this.editingWarehouse?.id;
    if (this.editingWarehouse && (!warehouseId || warehouseId <= 0)) {
      this.formError = "Warehouse ID is missing. Refresh the page and try again.";
      this.fire("formError", this);
      return;
    }

    this.isSaving = true;
    this.formError = "";
    this.fire("isSaving", this);

    const input = {
      id: warehouseId,
      name,
      code,
      warehouseType: this.formWarehouseType,
      address: this.formAddress.trim(),
      isDefault: this.formIsDefault,
      status: this.formStatus,
      organization: orgId,
    };

    try {
      const result = this.editingWarehouse
        ? await WarehouseApi.updateWarehouse(input)
        : await WarehouseApi.createWarehouse(input);

      if (!result.success) {
        this.formError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to save warehouse.";
        return;
      }

      this.closeForm();
      await this.loadWarehouses();
    } catch (exception) {
      this.formError = "Save failed: " + exception.toString();
    } finally {
      this.isSaving = false;
      this.fire("isSaving", this);
      this.fire("formError", this);
    }
  };

  public onDeleteHandler = (warehouse: WarehouseRecord): void => {
    this.selectedEntity = warehouse;
    this.showDeleteDialogPopup();
  };

  public onConfirmDeleteHandler = async (): Promise<void> => {
    const warehouse = this.selectedEntity;
    this.hideDeleteDialogPopup();
    this.selectedEntity = null;

    if (!warehouse?.id) return;

    try {
      const result = await WarehouseApi.deleteWarehouse(warehouse.id);
      if (!result.success) {
        this.loadError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to delete warehouse.";
        this.fire("loadError", this);
        return;
      }
      await this.loadWarehouses();
    } catch (exception) {
      this.loadError = "Delete failed: " + exception.toString();
      this.fire("loadError", this);
    }
  };

  public onCancelDeleteHandler = (): void => {
    this.hideDeleteDialogPopup();
    this.selectedEntity = null;
  };

  public showDeleteDialogPopup(): void {
    this.deleteDialogPopup?.dispose();
    this.deleteDialogPopup = new Popup({
      model: true,
      autoClose: false,
      float: false,
      takeFocus: true,
      position: ui.PopUpPosition.Center,
      child: ConfirmDeleteDialogWidget({
        entityName: "Warehouse",
        message: `Are you sure you want to delete "${this.selectedEntity?.name ?? "this warehouse"}"?`,
        onConfirm: () => {
          this.onConfirmDeleteHandler();
        },
        onCancel: () => {
          this.onCancelDeleteHandler();
        },
      }),
    });
    this.deleteDialogPopup.showPopup(this.context);
  }

  public hideDeleteDialogPopup(): void {
    this.deleteDialogPopup?.dispose();
  }

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
          label
            ? TextView({
                data: required ? label + " *" : label,
                className: "fieldLabel",
                key: "0",
              })
            : null,
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
      key: label || placeHolder,
    });
  }

  private renderSelectField(
    label: string,
    value: string,
    options: { value: string; label: string }[],
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
          React.createElement(
            "select",
            {
              className: "roleSelect",
              value,
              disabled: this.isSaving,
              onChange: (event: React.ChangeEvent<HTMLSelectElement>) => {
                onChange(event.target.value);
              },
              key: "1",
            },
            options.map((option) =>
              React.createElement(
                "option",
                { value: option.value, key: option.value },
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

  private renderFormModal(): ReactNode {
    const title = this.editingWarehouse ? "Edit Warehouse" : "Add Warehouse";
    return React.createElement(
      "div",
      {
        className: "storeModalOverlay",
        onClick: () => {
          if (!this.isSaving) this.closeForm();
        },
      },
      React.createElement(
        "div",
        {
          className: "storeModalPanel glassCard",
          onClick: (e: React.MouseEvent) => e.stopPropagation(),
          role: "dialog",
          "aria-modal": "true",
        },
        React.createElement(
          "div",
          { className: "storeModalHeader" },
          React.createElement("h2", { className: "storeModalTitle" }, title),
          React.createElement(
            "button",
            {
              type: "button",
              className: "storeModalClose",
              "aria-label": "Close",
              disabled: this.isSaving,
              onClick: () => this.closeForm(),
            },
            "✕"
          )
        ),
        React.createElement(
          "div",
          { className: "storeModalBody" },
          this.renderTextField("Name", this.formName, "Warehouse name", (val) => {
            this.formName = val;
            this.fire("formName", this);
          }, true),
          this.renderTextField("Code", this.formCode, "Warehouse code", (val) => {
            this.formCode = val;
            this.fire("formCode", this);
          }, true),
          this.renderSelectField(
            "Warehouse Type",
            this.formWarehouseType,
            WAREHOUSE_TYPE_OPTIONS,
            (val) => {
              this.formWarehouseType = val;
              this.fire("formWarehouseType", this);
            }
          ),
          ui.Container({
            child: ui.Column({
              mainAxisSize: ui.MainAxisSize.min,
              crossAxisAlignment: ui.CrossAxisAlignment.stretch,
              children: [
                TextView({ data: "Address", className: "fieldLabel", key: "0" }),
                ui.InputField({
                  value: this.formAddress,
                  placeHolder: "Warehouse address",
                  onChanged: (text: string) => {
                    this.formAddress = text;
                    this.fire("formAddress", this);
                  },
                  onFocusChange: () => {},
                  key: "1",
                }),
              ],
            }),
            className: "FormFieldStyle IMSInputFieldWidget formField storeFormField",
            key: "address",
          }),
          ui.Container({
            child: React.createElement(
              "label",
              { className: "warehouseDefaultCheck" },
              React.createElement("input", {
                type: "checkbox",
                checked: this.formIsDefault,
                disabled: this.isSaving,
                onChange: (event: React.ChangeEvent<HTMLInputElement>) => {
                  this.formIsDefault = event.target.checked;
                  this.fire("formIsDefault", this);
                },
              }),
              React.createElement("span", null, "Set as default warehouse")
            ),
            className: "FormFieldStyle formField storeFormField",
            key: "default",
          }),
          this.renderSelectField(
            "Status",
            this.formStatus,
            STATUS_OPTIONS,
            (val) => {
              this.formStatus = val;
              this.fire("formStatus", this);
            }
          ),
          this.formError
            ? TextView({
                data: this.formError,
                className: "storeFormError",
                key: "error",
              })
            : null
        ),
        React.createElement(
          "div",
          { className: "storeModalFooter" },
          TextButton({
            label: "Cancel",
            disable: this.isSaving,
            onPressed: () => this.closeForm(),
            onFocusChange: () => {},
            className: "secondary",
          }),
          TextButton({
            label: this.isSaving ? "Saving..." : "Save",
            disable: this.isSaving,
            onPressed: () => {
              this.saveForm();
            },
            onFocusChange: () => {},
            className: "primary",
          })
        )
      )
    );
  }

  private renderTableRow(warehouse: WarehouseRecord, index: number): ReactNode {
    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          TextView({
            data: warehouse.name,
            className: "entityCell entityCellName",
            key: "0",
          }),
          TextView({
            data: warehouse.code || "—",
            className: "entityCell",
            key: "1",
          }),
          TextView({
            data: formatEnumLabel(warehouse.warehouseType),
            className: "entityCell",
            key: "2",
          }),
          TextView({
            data: warehouse.address || "—",
            className: "entityCell entityCellDescription",
            key: "3",
          }),
          renderEntityStatusCell(warehouse.status),
          ui.Row({
            mainAxisSize: ui.MainAxisSize.min,
            children: [
              IconButton({
                icon: MaterialIcons.edit,
                onPressed: () => this.openEditForm(warehouse),
                onFocusChange: () => {},
                className: "entityActionIcon entityActionEdit",
                key: "0",
              }),
              IconButton({
                icon: MaterialIcons.delete_outline,
                onPressed: () => this.onDeleteHandler(warehouse),
                onFocusChange: () => {},
                className: "entityActionIcon entityActionDelete",
                key: "1",
              }),
            ],
            className: "entityActionsCell",
            key: "4",
          }),
        ],
        className: "entityTableGrid warehouseEntityTableGrid",
      }),
      className: "entityTableRow",
      key: "row-" + index,
    });
  }

  public render(): ReactNode {
    const filtered = this.filteredWarehouses;
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
            activeRoute: "/warehouses",
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
                  title: "Warehouses",
                  organization: this.organization,
                  onLogout: () => {
                    void AppLogout.signOut(this.navigator);
                  },
                  onProfile: () => {
                    this.navigator.pushProfileSettingsPage({
                      user: this.user,
                      target: "main",
                      replace: false,
                    });
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
                            TextView({ data: "Warehouses", className: "storePageTitle", key: "0" }),
                            TextButton({
                              label: "+ Add Warehouse",
                              onPressed: () => this.openCreateForm(),
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
                              child: this.renderTextField(
                                "",
                                this.searchTerm,
                                "Search warehouse...",
                                (val) => this.setSearchTerm(val)
                              ),
                              className: "storeSearchField",
                              key: "0",
                            }),
                            ui.Container({
                              child: this.renderSelectField(
                                "",
                                this.statusFilter,
                                STATUS_FILTER_OPTIONS,
                                (val) => this.setStatusFilter(val)
                              ),
                              className: "storeStatusFilter",
                              key: "1",
                            }),
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
                              data: "Loading warehouses...",
                              className: "storeCountLabel",
                              key: "loading",
                            })
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title: "No warehouses",
                                message: "Add a warehouse to begin managing inventory locations.",
                                actionLabel: "+ Add Warehouse",
                                onAction: () => this.openCreateForm(),
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
                                          TextView({
                                            data: "NAME",
                                            className: "entityHeaderCell",
                                            key: "0",
                                          }),
                                          TextView({
                                            data: "CODE",
                                            className: "entityHeaderCell",
                                            key: "1",
                                          }),
                                          TextView({
                                            data: "TYPE",
                                            className: "entityHeaderCell",
                                            key: "2",
                                          }),
                                          TextView({
                                            data: "ADDRESS",
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
                                            className: "entityHeaderCell entityHeaderActions",
                                            key: "5",
                                          }),
                                        ],
                                        className: "entityTableGrid warehouseEntityTableGrid",
                                      }),
                                      className: "entityTableHeader",
                                      key: "header",
                                    }),
                                    ...filtered.map((warehouse, index) =>
                                      this.renderTableRow(warehouse, index)
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
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle WarehouseListPage pageBackground ",
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
    this.deleteDialogPopup?.dispose();
    super.dispose();
  }

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function WarehouseListPage(props: WarehouseListPageProps) {
  return React.createElement(_WarehouseListPageState, {
    ..._WarehouseListPageState.defaultProps,
    ...props,
  });
}
