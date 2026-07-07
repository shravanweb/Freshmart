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
import CategoryApi, { CategoryRecord, formatCategoryApiError } from "../utils/CategoryApi";
import CollectionUtils from "../utils/CollectionUtils";
import { BuildContext } from "../classes/BuildContext";
import AppLogout from "../utils/AppLogout";
import { renderEntityStatusCell } from "../utils/EntityTableHelpers";

export interface ProductCategoryListPageProps extends BaseUIProps {
  key?: string;
  user: User;
}

const STATUS_OPTIONS = [
  { value: "Active", label: "Active" },
  { value: "Inactive", label: "Inactive" },
  { value: "Archived", label: "Archived" },
];

const STATUS_FILTER_OPTIONS = [
  { value: "", label: "All statuses" },
  ...STATUS_OPTIONS,
];

class _ProductCategoryListPageState extends ObservableComponent<ProductCategoryListPageProps> {
  static defaultProps = { user: null };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  categories: CategoryRecord[] = [];
  searchTerm: string = "";
  statusFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  showFormModal: boolean = false;
  editingCategory: CategoryRecord | null = null;
  isSaving: boolean = false;
  formError: string = "";
  formName: string = "";
  formCode: string = "";
  formDescription: string = "";
  formStatus: string = "Active";
  formImageFile: File | null = null;
  formImagePreview: string = "";
  selectedEntity: CategoryRecord = null;
  deleteDialogPopup: Popup;

  public constructor(props: ProductCategoryListPageProps) {
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
    this.on(["organization"], this.loadCategories);
    this.on(
      [
        "categories",
        "searchTerm",
        "statusFilter",
        "isLoading",
        "loadError",
        "showFormModal",
        "isSaving",
        "formError",
        "formName",
        "formCode",
        "formDescription",
        "formStatus",
        "formImagePreview",
        "editingCategory",
        "user",
        "userProfile",
        "organization",
      ],
      this.rebuild
    );
  }

  private clearFormImagePreview(): void {
    if (this.formImagePreview.startsWith("blob:")) {
      URL.revokeObjectURL(this.formImagePreview);
    }
    this.formImageFile = null;
    this.formImagePreview = "";
  }

  private async loadFormImagePreview(code: string): Promise<void> {
    if (!code.trim()) {
      this.formImagePreview = "";
      this.fire("formImagePreview", this);
      return;
    }
    const imageUrl = await CategoryApi.resolveImageUrl(code);
    this.formImagePreview = imageUrl ? `${imageUrl}?v=${Date.now()}` : "";
    this.fire("formImagePreview", this);
  }

  private onFormImageSelected = (event: React.ChangeEvent<HTMLInputElement>): void => {
    const file = event.target.files?.[0];
    if (!file) {
      return;
    }
    if (this.formImagePreview.startsWith("blob:")) {
      URL.revokeObjectURL(this.formImagePreview);
    }
    this.formImageFile = file;
    this.formImagePreview = URL.createObjectURL(file);
    this.fire("formImagePreview", this);
  };

  public componentDidUpdate(prevProps: ProductCategoryListPageProps): void {
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

  public setCategories(val: CategoryRecord[]): void {
    if (CollectionUtils.isNotEquals(this.categories, val)) {
      this.categories = val;
      this.fire("categories", this);
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

  public get filteredCategories(): CategoryRecord[] {
    let list = this.categories;
    const term = this.searchTerm?.trim().toLowerCase() ?? "";
    if (term) {
      list = list.filter(
        (item) =>
          item.name.toLowerCase().includes(term) ||
          item.code.toLowerCase().includes(term) ||
          item.description.toLowerCase().includes(term)
      );
    }
    if (this.statusFilter) {
      list = list.filter((item) => item.status === this.statusFilter);
    }
    return list;
  }

  public loadCategories = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.loadError = "";
      this.setCategories([]);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const items = await CategoryApi.getAllCategories(orgId);
      this.setCategories(items);
      this.loadError = "";
    } catch (exception) {
      this.setCategories([]);
      this.loadError = "Failed to load categories: " + exception.toString();
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
      this.fire("loadError", this);
    }
  };

  public openCreateForm = (): void => {
    this.editingCategory = null;
    this.formName = "";
    this.formCode = "";
    this.formDescription = "";
    this.formStatus = "Active";
    this.formError = "";
    this.clearFormImagePreview();
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public openEditForm = (category: CategoryRecord): void => {
    this.editingCategory = category;
    this.formName = category.name;
    this.formCode = category.code;
    this.formDescription = category.description ?? "";
    this.formStatus = category.status || "Active";
    this.formError = "";
    this.clearFormImagePreview();
    this.showFormModal = true;
    this.fire("showFormModal", this);
    void this.loadFormImagePreview(category.code);
  };

  public closeForm = (): void => {
    this.showFormModal = false;
    this.editingCategory = null;
    this.formError = "";
    this.clearFormImagePreview();
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

    const categoryId = this.editingCategory?.id;
    if (this.editingCategory && (!categoryId || categoryId <= 0)) {
      this.formError = "Category ID is missing. Refresh the page and try again.";
      this.fire("formError", this);
      return;
    }

    this.isSaving = true;
    this.formError = "";
    this.fire("isSaving", this);

    const input = {
      id: categoryId,
      name,
      code,
      description: this.formDescription.trim(),
      status: this.formStatus,
      organization: orgId,
    };

    try {
      const result = this.editingCategory
        ? await CategoryApi.updateCategory(input)
        : await CategoryApi.createCategory(input);

      if (!result.success) {
        this.formError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to save category.";
        return;
      }

      if (this.formImageFile) {
        await CategoryApi.uploadCategoryImage(code, this.formImageFile);
      }

      this.closeForm();
      await this.loadCategories();
    } catch (exception) {
      this.formError = "Save failed: " + formatCategoryApiError(exception);
    } finally {
      this.isSaving = false;
      this.fire("isSaving", this);
      this.fire("formError", this);
    }
  };

  public onDeleteHandler = (category: CategoryRecord): void => {
    this.selectedEntity = category;
    this.showDeleteDialogPopup();
  };

  public onConfirmDeleteHandler = async (): Promise<void> => {
    const category = this.selectedEntity;
    this.hideDeleteDialogPopup();
    this.selectedEntity = null;

    if (!category?.id) return;

    try {
      const result = await CategoryApi.deleteCategory(category.id);
      if (!result.success) {
        this.loadError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to delete category.";
        this.fire("loadError", this);
        return;
      }
      await this.loadCategories();
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
        entityName: "Category",
        message: `Are you sure you want to delete "${this.selectedEntity?.name ?? "this category"}"?`,
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
    const title = this.editingCategory ? "Edit Category" : "Add Category";
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
            this.renderTextField("Name", this.formName, "Category name", (val) => {
              this.formName = val;
              this.fire("formName", this);
            }, true),
            this.renderTextField("Code", this.formCode, "Category code", (val) => {
              this.formCode = val;
              this.fire("formCode", this);
            }, true),
            ui.Container({
              child: ui.Column({
                mainAxisSize: ui.MainAxisSize.min,
                crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                children: [
                  TextView({ data: "Description", className: "fieldLabel", key: "0" }),
                  ui.InputField({
                    value: this.formDescription,
                    placeHolder: "Category description",
                    onChanged: (text: string) => {
                      this.formDescription = text;
                      this.fire("formDescription", this);
                    },
                    onFocusChange: () => {},
                    key: "1",
                  }),
                ],
              }),
              className:
                "FormFieldStyle IMSInputFieldWidget formField storeFormField productFormFullWidth",
              key: "description",
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
            React.createElement(
              "div",
              { className: "categoryImageUploadField productFormFullWidth", key: "image-upload" },
              React.createElement("span", { className: "fieldLabel" }, "Category Image"),
              this.formImagePreview
                ? React.createElement("img", {
                    src: this.formImagePreview,
                    alt: "Category preview",
                    className: "categoryImagePreview",
                    onError: () => {
                      if (!this.formImagePreview.startsWith("blob:")) {
                        this.formImagePreview = "";
                        this.fire("formImagePreview", this);
                      }
                    },
                  })
                : null,
              React.createElement("input", {
                type: "file",
                accept: "image/jpeg,image/png,image/webp",
                disabled: this.isSaving,
                className: "categoryImageFileInput",
                onChange: this.onFormImageSelected,
              }),
              React.createElement(
                "p",
                { className: "categoryImageHint" },
                "Upload JPG, PNG or WebP. This image appears on the landing page."
              )
            ),
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
              this.saveForm();
            },
            onFocusChange: () => {},
            className: "primary",
          })
        )
      )
    );
  }

  private renderTableRow(category: CategoryRecord, index: number): ReactNode {
    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          TextView({
            data: category.name,
            className: "entityCell entityCellName",
            key: "0",
          }),
          TextView({
            data: category.code || "—",
            className: "entityCell",
            key: "1",
          }),
          TextView({
            data: category.description || "—",
            className: "entityCell entityCellDescription",
            key: "2",
          }),
          renderEntityStatusCell(category.status),
          ui.Row({
            mainAxisSize: ui.MainAxisSize.min,
            children: [
              IconButton({
                icon: MaterialIcons.edit,
                onPressed: () => this.openEditForm(category),
                onFocusChange: () => {},
                className: "entityActionIcon entityActionEdit",
                key: "0",
              }),
              IconButton({
                icon: MaterialIcons.delete_outline,
                onPressed: () => this.onDeleteHandler(category),
                onFocusChange: () => {},
                className: "entityActionIcon entityActionDelete",
                key: "1",
              }),
            ],
            className: "entityActionsCell",
            key: "3",
          }),
        ],
        className: "entityTableGrid categoryTableGrid",
      }),
      className: "entityTableRow",
      key: "row-" + index,
    });
  }

  public render(): ReactNode {
    const filtered = this.filteredCategories;
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
            activeRoute: "/catalog/categories",
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
                  title: "Product Categories",
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
                              data: "Product Categories",
                              className: "storePageTitle",
                              key: "0",
                            }),
                            TextButton({
                              label: "+ Add Category",
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
                                "Search category...",
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
                              data: "Loading categories...",
                              className: "storeCountLabel",
                              key: "loading",
                            })
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title: "No categories",
                                message: "Add a category to organize your products.",
                                actionLabel: "+ Add Category",
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
                                            data: "DESCRIPTION",
                                            className: "entityHeaderCell",
                                            key: "2",
                                          }),
                                          TextView({
                                            data: "STATUS",
                                            className: "entityHeaderCell",
                                            key: "3",
                                          }),
                                          TextView({
                                            data: "ACTIONS",
                                            className: "entityHeaderCell entityHeaderActions",
                                            key: "4",
                                          }),
                                        ],
                                        className: "entityTableGrid categoryTableGrid",
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
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle ProductCategoryListPage pageBackground ",
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

export default function ProductCategoryListPage(props: ProductCategoryListPageProps) {
  return React.createElement(_ProductCategoryListPageState, {
    ..._ProductCategoryListPageState.defaultProps,
    ...props,
  });
}
