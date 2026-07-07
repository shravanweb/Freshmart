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
import StoreApi, { StoreRecord } from "../utils/StoreApi";
import StaffUserApi, { StaffUserRecord } from "../utils/StaffUserApi";
import CollectionUtils from "../utils/CollectionUtils";
import { BuildContext } from "../classes/BuildContext";
import { formatEnumLabel, renderEntityStatusCell } from "../utils/EntityTableHelpers";

export interface StoreListPageProps extends BaseUIProps {
  key?: string;
  user: User;
}

const STORE_TYPE_OPTIONS = [
  { value: "Supermarket", label: "Supermarket" },
  { value: "Grocery", label: "Grocery" },
  { value: "Retail", label: "Retail" },
  { value: "MiniMart", label: "Mini Mart" },
  { value: "Wholesale", label: "Wholesale" },
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

class _StoreListPageState extends ObservableComponent<StoreListPageProps> {
  static defaultProps = { user: null };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  stores: StoreRecord[] = [];
  searchTerm: string = "";
  statusFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  showFormModal: boolean = false;
  editingStore: StoreRecord | null = null;
  isSaving: boolean = false;
  formError: string = "";
  formName: string = "";
  formCode: string = "";
  formStoreType: string = "Grocery";
  formAddress: string = "";
  formPhone: string = "";
  formEmail: string = "";
  formStatus: string = "Active";
  formManagerUserId: string = "";
  storeManagers: StaffUserRecord[] = [];
  selectedEntity: StoreRecord = null;
  deleteDialogPopup: Popup;

  public constructor(props: StoreListPageProps) {
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
    this.on(["organization"], this.loadStores);
    this.on(["organization"], this.loadStoreManagers);
    this.on(
      [
        "stores",
        "searchTerm",
        "statusFilter",
        "isLoading",
        "loadError",
        "showFormModal",
        "isSaving",
        "formError",
        "formName",
        "formCode",
        "formStoreType",
        "formAddress",
        "formPhone",
        "formEmail",
        "formStatus",
        "formManagerUserId",
        "storeManagers",
        "editingStore",
        "user",
        "userProfile",
        "organization",
      ],
      this.rebuild
    );
  }

  public componentDidUpdate(prevProps: StoreListPageProps): void {
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

  public setStores(val: StoreRecord[]): void {
    if (CollectionUtils.isNotEquals(this.stores, val)) {
      this.stores = val;
      this.fire("stores", this);
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

  public get filteredStores(): StoreRecord[] {
    let list = this.stores;
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

  public loadStores = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.loadError = "";
      this.setStores([]);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const [items, managers] = await Promise.all([
        StoreApi.getAllStores(orgId),
        StaffUserApi.getStoreManagers(orgId),
      ]);
      const managerByUserId = new Map(
        managers.map((manager) => [manager.userId, manager.displayName])
      );
      this.setStoreManagers(managers);
      this.setStores(
        Array.from(items, (store) => ({
          ...store,
          managerName:
            managerByUserId.get(store.managerUserId) ||
            store.managerEmail ||
            "",
        }))
      );
      this.loadError = "";
    } catch (exception) {
      this.setStores([]);
      this.loadError = "Failed to load stores: " + exception.toString();
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
      this.fire("loadError", this);
    }
  };

  public setStoreManagers(val: StaffUserRecord[]): void {
    if (CollectionUtils.isNotEquals(this.storeManagers, val)) {
      this.storeManagers = val;
      this.fire("storeManagers", this);
    }
  }

  public loadStoreManagers = async (): Promise<void> => {
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.setStoreManagers([]);
      return;
    }

    try {
      this.setStoreManagers(await StaffUserApi.getStoreManagers(orgId));
    } catch (exception) {
      console.log(" exception in loadStoreManagers : " + exception.toString());
      this.setStoreManagers([]);
    }
  };

  public getManagerOptions(): { value: string; label: string }[] {
    const options = [{ value: "", label: "No manager assigned" }];
    this.storeManagers.forEach((manager) => {
      const label = manager.displayName
        ? `${manager.displayName} (${manager.email})`
        : manager.email;
      options.push({
        value: String(manager.userId),
        label,
      });
    });
    return options;
  }

  public resolvedManagerLabel(store: StoreRecord): string {
    if (store.managerName) {
      return store.managerName;
    }
    if (store.managerEmail) {
      return store.managerEmail;
    }
    return "—";
  }

  private get canManageStores(): boolean {
    const role = this.userProfile?.appRole?.name ?? "";
    return role !== "StoreManager";
  }

  public openCreateForm = (): void => {
    void this.loadStoreManagers();
    this.editingStore = null;
    this.formName = "";
    this.formCode = "";
    this.formStoreType = "Grocery";
    this.formAddress = "";
    this.formPhone = "";
    this.formEmail = "";
    this.formStatus = "Active";
    this.formManagerUserId = "";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public openEditForm = (store: StoreRecord): void => {
    if (!this.canManageStores) {
      return;
    }
    void this.loadStoreManagers();
    this.editingStore = store;
    this.formName = store.name;
    this.formCode = store.code;
    this.formStoreType = store.storeType || "Grocery";
    this.formAddress = store.address ?? "";
    this.formPhone = store.phone ?? "";
    this.formEmail = store.email ?? "";
    this.formStatus = store.status || "Active";
    this.formManagerUserId =
      store.managerUserId > 0 ? String(store.managerUserId) : "";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public closeForm = (): void => {
    this.showFormModal = false;
    this.editingStore = null;
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

    const storeId = this.editingStore?.id;
    if (this.editingStore && (!storeId || storeId <= 0)) {
      this.formError = "Store ID is missing. Refresh the page and try again.";
      this.fire("formError", this);
      return;
    }

    this.isSaving = true;
    this.formError = "";
    this.fire("isSaving", this);

    const input = {
      id: storeId,
      name,
      code,
      storeType: this.formStoreType,
      address: this.formAddress.trim(),
      phone: this.formPhone.trim(),
      email: this.formEmail.trim(),
      status: this.formStatus,
      organization: orgId,
      managerUserId: this.formManagerUserId
        ? Number(this.formManagerUserId)
        : null,
    };

    try {
      const result = this.editingStore
        ? await StoreApi.updateStore(input)
        : await StoreApi.createStore(input);

      if (!result.success) {
        this.formError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to save store.";
        return;
      }

      this.closeForm();
      await this.loadStores();
    } catch (exception) {
      this.formError = "Save failed: " + exception.toString();
    } finally {
      this.isSaving = false;
      this.fire("isSaving", this);
      this.fire("formError", this);
    }
  };

  public onDeleteHandler = (store: StoreRecord): void => {
    if (!this.canManageStores) {
      return;
    }
    this.selectedEntity = store;
    this.showDeleteDialogPopup();
  };

  public onConfirmDeleteHandler = async (): Promise<void> => {
    const store = this.selectedEntity;
    this.hideDeleteDialogPopup();
    this.selectedEntity = null;

    if (!store?.id) return;

    try {
      const result = await StoreApi.deleteStore(store.id);
      if (!result.success) {
        this.loadError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to delete store.";
        this.fire("loadError", this);
        return;
      }
      await this.loadStores();
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
        entityName: "Store",
        message: `Are you sure you want to delete "${this.selectedEntity?.name ?? "this store"}"?`,
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
    const title = this.editingStore ? "Edit Store" : "Add Store";
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
          className: "storeModalPanel glassCard productModalPanel",
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
          { className: "storeModalBody productModalBody" },
          React.createElement(
            "div",
            { className: "productFormGrid" },
            this.renderTextField("Name", this.formName, "Store name", (val) => {
              this.formName = val;
              this.fire("formName", this);
            }, true),
            this.renderTextField("Code", this.formCode, "Store code", (val) => {
              this.formCode = val;
              this.fire("formCode", this);
            }, true),
            this.renderSelectField(
              "Store Type",
              this.formStoreType,
              STORE_TYPE_OPTIONS,
              (val) => {
                this.formStoreType = val;
                this.fire("formStoreType", this);
              }
            ),
            this.renderSelectField(
              "Status",
              this.formStatus,
              STATUS_OPTIONS,
              (val) => {
                this.formStatus = val;
                this.fire("formStatus", this);
              }
            ),
            this.renderTextField("Phone", this.formPhone, "Phone number", (val) => {
              this.formPhone = val;
              this.fire("formPhone", this);
            }),
            this.renderTextField("Email", this.formEmail, "store@company.com", (val) => {
              this.formEmail = val;
              this.fire("formEmail", this);
            }),
            ui.Container({
              child: ui.Column({
                mainAxisSize: ui.MainAxisSize.min,
                crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                children: [
                  TextView({ data: "Address", className: "fieldLabel", key: "0" }),
                  ui.InputField({
                    value: this.formAddress,
                    placeHolder: "Store address",
                    onChanged: (text: string) => {
                      this.formAddress = text;
                      this.fire("formAddress", this);
                    },
                    onFocusChange: () => {},
                    key: "1",
                  }),
                ],
              }),
              className:
                "FormFieldStyle IMSInputFieldWidget formField storeFormField productFormFullWidth",
              key: "address",
            }),
            React.createElement(
              "div",
              { className: "productFormFullWidth", key: "manager-wrap" },
              this.renderSelectField(
                "Store Manager",
                this.formManagerUserId,
                this.getManagerOptions(),
                (val) => {
                  this.formManagerUserId = val;
                  this.fire("formManagerUserId", this);
                }
              )
            ),
            this.storeManagers.length === 0
              ? React.createElement(
                  "div",
                  { className: "productFormFullWidth", key: "managerHint-wrap" },
                  TextView({
                    data:
                      "No store managers yet. Invite a staff member with the Store Manager role from Administration → Users.",
                    className: "storeFormHint",
                    key: "managerHint",
                  })
                )
              : null,
            this.formError
              ? React.createElement(
                  "div",
                  { className: "productFormFullWidth", key: "error-wrap" },
                  TextView({
                    data: this.formError,
                    className: "storeFormError",
                    key: "error",
                  })
                )
              : null
          )
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

  private renderTableRow(store: StoreRecord, index: number): ReactNode {
    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          TextView({
            data: store.name,
            className: "entityCell entityCellName",
            key: "0",
          }),
          TextView({
            data: store.code || "—",
            className: "entityCell",
            key: "1",
          }),
          TextView({
            data: formatEnumLabel(store.storeType),
            className: "entityCell",
            key: "2",
          }),
          TextView({
            data: store.phone || "—",
            className: "entityCell",
            key: "3",
          }),
          TextView({
            data: this.resolvedManagerLabel(store),
            className: "entityCell",
            key: "4",
          }),
          renderEntityStatusCell(store.status),
          ui.Row({
            mainAxisSize: ui.MainAxisSize.min,
            children: [
              IconButton({
                icon: MaterialIcons.edit,
                onPressed: () => this.openEditForm(store),
                onFocusChange: () => {},
                disable: !this.canManageStores,
                className: "entityActionIcon entityActionEdit",
                key: "0",
              }),
              IconButton({
                icon: MaterialIcons.delete_outline,
                onPressed: () => this.onDeleteHandler(store),
                onFocusChange: () => {},
                disable: !this.canManageStores,
                className: "entityActionIcon entityActionDelete",
                key: "1",
              }),
            ],
            className: "entityActionsCell",
            key: "5",
          }),
        ],
        className: "entityTableGrid storeEntityTableGrid",
      }),
      className: "entityTableRow",
      key: "row-" + index,
    });
  }

  public render(): ReactNode {
    const filtered = this.filteredStores;
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
            activeRoute: "/stores",
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
                  title: "Stores",
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
                            TextView({ data: "Stores", className: "storePageTitle", key: "0" }),
                            TextButton({
                              label: "+ Add Store",
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
                                "Search store...",
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
                              data: "Loading stores...",
                              className: "storeCountLabel",
                              key: "loading",
                            })
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title: "No stores",
                                message: "Add a store to begin managing retail locations.",
                                actionLabel: "+ Add Store",
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
                                            data: "PHONE",
                                            className: "entityHeaderCell",
                                            key: "3",
                                          }),
                                          TextView({
                                            data: "MANAGER",
                                            className: "entityHeaderCell",
                                            key: "4",
                                          }),
                                          TextView({
                                            data: "STATUS",
                                            className: "entityHeaderCell",
                                            key: "5",
                                          }),
                                          TextView({
                                            data: "ACTIONS",
                                            className: "entityHeaderCell entityHeaderActions",
                                            key: "6",
                                          }),
                                        ],
                                        className: "entityTableGrid storeEntityTableGrid",
                                      }),
                                      className: "entityTableHeader",
                                      key: "header",
                                    }),
                                    ...filtered.map((store, index) =>
                                      this.renderTableRow(store, index)
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
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle StoreListPage pageBackground ",
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

export default function StoreListPage(props: StoreListPageProps) {
  return React.createElement(_StoreListPageState, {
    ..._StoreListPageState.defaultProps,
    ...props,
  });
}
