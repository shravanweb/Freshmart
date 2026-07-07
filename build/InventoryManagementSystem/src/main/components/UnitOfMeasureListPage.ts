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
import IconButton from "./IconButton";
import MaterialIcons from "../icons/MaterialIcons";
import EmptyStateWidget from "./EmptyStateWidget";
import ConfirmDeleteDialogWidget from "./ConfirmDeleteDialogWidget";
import Popup from "./Popup";
import UomApi, { UomRecord, formatUomApiError } from "../utils/UomApi";
import CollectionUtils from "../utils/CollectionUtils";
import { BuildContext } from "../classes/BuildContext";
import AppLogout from "../utils/AppLogout";
import { renderEntityStatusCell } from "../utils/EntityTableHelpers";

export interface UnitOfMeasureListPageProps extends BaseUIProps {
  key?: string;
  user: User;
}

const STATUS_OPTIONS = [
  { value: "Active", label: "Active" },
  { value: "Inactive", label: "Inactive" },
  { value: "Archived", label: "Archived" },
];

const STATUS_FILTER_OPTIONS = [{ value: "", label: "All statuses" }, ...STATUS_OPTIONS];

const UOM_TYPE_OPTIONS = [
  { value: "Base", label: "Base" },
  { value: "Derived", label: "Derived" },
];

class _UnitOfMeasureListPageState extends ObservableComponent<UnitOfMeasureListPageProps> {
  static defaultProps = { user: null };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  uoms: UomRecord[] = [];
  searchTerm: string = "";
  statusFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  showFormModal: boolean = false;
  editingUom: UomRecord | null = null;
  isSaving: boolean = false;
  formError: string = "";
  formName: string = "";
  formSymbol: string = "";
  formUomType: string = "Base";
  formBaseFactor: string = "1";
  formStatus: string = "Active";
  selectedEntity: UomRecord = null;
  deleteDialogPopup: Popup;

  public constructor(props: UnitOfMeasureListPageProps) {
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
    this.on(["organization"], this.loadUoms);
    this.on(
      [
        "uoms",
        "searchTerm",
        "statusFilter",
        "isLoading",
        "loadError",
        "showFormModal",
        "isSaving",
        "formError",
        "formName",
        "formSymbol",
        "formUomType",
        "formBaseFactor",
        "formStatus",
        "editingUom",
        "user",
        "userProfile",
        "organization",
      ],
      this.rebuild
    );
  }

  public componentDidUpdate(prevProps: UnitOfMeasureListPageProps): void {
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

  public setUoms(val: UomRecord[]): void {
    if (CollectionUtils.isNotEquals(this.uoms, val)) {
      this.uoms = val;
      this.fire("uoms", this);
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

  public get filteredUoms(): UomRecord[] {
    let list = this.uoms;
    const term = this.searchTerm?.trim().toLowerCase() ?? "";
    if (term) {
      list = list.filter(
        (item) =>
          item.name.toLowerCase().includes(term) ||
          item.symbol.toLowerCase().includes(term) ||
          item.uomType.toLowerCase().includes(term)
      );
    }
    if (this.statusFilter) {
      list = list.filter((item) => item.status === this.statusFilter);
    }
    return list;
  }

  public loadUoms = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.loadError = "";
      this.setUoms([]);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const items = await UomApi.getAllUnitOfMeasures(orgId);
      this.setUoms(items);
      this.loadError = "";
    } catch (exception) {
      this.setUoms([]);
      this.loadError = "Failed to load units of measure: " + exception.toString();
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
      this.fire("loadError", this);
    }
  };

  public openCreateForm = (): void => {
    this.editingUom = null;
    this.formName = "";
    this.formSymbol = "";
    this.formUomType = "Base";
    this.formBaseFactor = "1";
    this.formStatus = "Active";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public openEditForm = (uom: UomRecord): void => {
    this.editingUom = uom;
    this.formName = uom.name;
    this.formSymbol = uom.symbol;
    this.formUomType = uom.uomType || "Base";
    this.formBaseFactor = String(uom.baseFactor ?? 1);
    this.formStatus = uom.status || "Active";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public closeForm = (): void => {
    this.showFormModal = false;
    this.editingUom = null;
    this.formError = "";
    this.fire("showFormModal", this);
  };

  public saveForm = async (): Promise<void> => {
    const name = this.formName.trim();
    const symbol = this.formSymbol.trim();
    const baseFactor = Number(this.formBaseFactor);

    if (!name) {
      this.formError = "Name is required.";
      this.fire("formError", this);
      return;
    }
    if (!symbol) {
      this.formError = "Symbol is required.";
      this.fire("formError", this);
      return;
    }
    if (!Number.isFinite(baseFactor) || baseFactor <= 0) {
      this.formError = "Base factor must be a positive number.";
      this.fire("formError", this);
      return;
    }

    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.formError = "Organization not found.";
      this.fire("formError", this);
      return;
    }

    const uomId = this.editingUom?.id;
    if (this.editingUom && (!uomId || uomId <= 0)) {
      this.formError = "Unit of measure ID is missing. Refresh the page and try again.";
      this.fire("formError", this);
      return;
    }

    this.isSaving = true;
    this.formError = "";
    this.fire("isSaving", this);

    const input = {
      id: uomId,
      name,
      symbol,
      uomType: this.formUomType,
      baseFactor,
      status: this.formStatus,
      organization: orgId,
    };

    try {
      const result = this.editingUom
        ? await UomApi.updateUnitOfMeasure(input)
        : await UomApi.createUnitOfMeasure(input);

      if (!result.success) {
        this.formError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to save unit of measure.";
        return;
      }

      this.closeForm();
      await this.loadUoms();
    } catch (exception) {
      this.formError = "Save failed: " + formatUomApiError(exception);
    } finally {
      this.isSaving = false;
      this.fire("isSaving", this);
      this.fire("formError", this);
    }
  };

  public onDeleteHandler = (uom: UomRecord): void => {
    this.selectedEntity = uom;
    this.showDeleteDialogPopup();
  };

  public onConfirmDeleteHandler = async (): Promise<void> => {
    const uom = this.selectedEntity;
    this.hideDeleteDialogPopup();
    this.selectedEntity = null;

    if (!uom?.id) return;

    try {
      const result = await UomApi.deleteUnitOfMeasure(uom.id);
      if (!result.success) {
        this.loadError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to delete unit of measure.";
        this.fire("loadError", this);
        return;
      }
      await this.loadUoms();
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
        entityName: "Unit of Measure",
        message: `Are you sure you want to delete "${this.selectedEntity?.name ?? "this unit"}"?`,
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
    const title = this.editingUom ? "Edit Unit of Measure" : "Add Unit of Measure";
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
            this.renderTextField("Name", this.formName, "e.g. Kilogram", (val) => {
              this.formName = val;
              this.fire("formName", this);
            }, true),
            this.renderTextField("Symbol", this.formSymbol, "e.g. kg", (val) => {
              this.formSymbol = val;
              this.fire("formSymbol", this);
            }, true),
            this.renderSelectField(
              "UOM Type",
              this.formUomType,
              UOM_TYPE_OPTIONS,
              (val) => {
                this.formUomType = val;
                this.fire("formUomType", this);
              },
              true
            ),
            this.renderTextField(
              "Base Factor",
              this.formBaseFactor,
              "1",
              (val) => {
                this.formBaseFactor = val;
                this.fire("formBaseFactor", this);
              },
              true
            ),
            this.renderSelectField("Status", this.formStatus, STATUS_OPTIONS, (val) => {
              this.formStatus = val;
              this.fire("formStatus", this);
            }),
            this.formError
              ? TextView({
                  data: this.formError,
                  className: "storeFormError productFormFullWidth",
                  key: "error",
                })
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
              void this.saveForm();
            },
            onFocusChange: () => {},
            className: "primary",
          })
        )
      )
    );
  }

  private renderTableRow(uom: UomRecord, index: number): ReactNode {
    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          TextView({
            data: uom.name,
            className: "entityCell entityCellName",
            key: "0",
          }),
          TextView({
            data: uom.symbol || "—",
            className: "entityCell",
            key: "1",
          }),
          TextView({
            data: uom.uomType || "—",
            className: "entityCell",
            key: "2",
          }),
          TextView({
            data: String(uom.baseFactor ?? 1),
            className: "entityCell",
            key: "3",
          }),
          renderEntityStatusCell(uom.status),
          ui.Row({
            mainAxisSize: ui.MainAxisSize.min,
            children: [
              IconButton({
                icon: MaterialIcons.edit,
                onPressed: () => this.openEditForm(uom),
                onFocusChange: () => {},
                className: "entityActionIcon entityActionEdit",
                key: "0",
              }),
              IconButton({
                icon: MaterialIcons.delete_outline,
                onPressed: () => this.onDeleteHandler(uom),
                onFocusChange: () => {},
                className: "entityActionIcon entityActionDelete",
                key: "1",
              }),
            ],
            className: "entityActionsCell",
            key: "4",
          }),
        ],
        className: "entityTableGrid uomTableGrid",
      }),
      className: "entityTableRow",
      key: "row-" + index,
    });
  }

  public render(): ReactNode {
    const filtered = this.filteredUoms;
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
            activeRoute: "/catalog/uom",
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
                  title: "Units of Measure",
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
                            TextView({
                              data: "Units of Measure",
                              className: "storePageTitle",
                              key: "0",
                            }),
                            TextButton({
                              label: "+ Add Unit",
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
                                "Search by name or symbol...",
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
                              data: "Loading units of measure...",
                              className: "storeCountLabel",
                              key: "loading",
                            })
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title: "No units of measure",
                                message:
                                  "Add units like kg, L, or pcs to use in products and inventory.",
                                actionLabel: "+ Add Unit",
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
                                            data: "SYMBOL",
                                            className: "entityHeaderCell",
                                            key: "1",
                                          }),
                                          TextView({
                                            data: "TYPE",
                                            className: "entityHeaderCell",
                                            key: "2",
                                          }),
                                          TextView({
                                            data: "BASE FACTOR",
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
                                        className: "entityTableGrid uomTableGrid",
                                      }),
                                      className: "entityTableHeader",
                                      key: "header",
                                    }),
                                    ...filtered.map((item, index) =>
                                      this.renderTableRow(item, index)
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
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle UnitOfMeasureListPage pageBackground ",
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
    this.hideDeleteDialogPopup();
    super.dispose();
  }
}

export default function UnitOfMeasureListPage(props: UnitOfMeasureListPageProps) {
  return React.createElement(_UnitOfMeasureListPageState, {
    ..._UnitOfMeasureListPageState.defaultProps,
    ...props,
  });
}
