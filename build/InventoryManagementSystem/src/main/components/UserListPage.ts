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
import StaffUserApi, { StaffUserRecord } from "../utils/StaffUserApi";
import CollectionUtils from "../utils/CollectionUtils";
import { BuildContext } from "../classes/BuildContext";
import {
  formatRoleLabel,
  ROLE_FILTER_OPTIONS,
  STAFF_ROLE_OPTIONS,
} from "../utils/RoleLabels";

function staffInitials(displayName: string, email: string): string {
  const name = displayName?.trim() ?? "";
  if (name) {
    const parts = name.split(/\s+/).filter(Boolean);
    if (parts.length >= 2) {
      return (parts[0][0] + parts[1][0]).toUpperCase();
    }
    return name.slice(0, 2).toUpperCase();
  }
  const local = email?.split("@")[0] ?? "";
  return local.slice(0, 2).toUpperCase() || "?";
}

function staffRoleBadgeClass(appRole: string): string {
  const role = appRole?.trim() || "Viewer";
  return `staffRoleBadge staffRoleBadge--${role}`;
}

function staffStatusClass(status: string): string {
  const normalized = status || "Active";
  if (normalized === "Active") {
    return "entityStatus entityStatusActive";
  }
  if (normalized === "Inactive") {
    return "entityStatus entityStatusInactive";
  }
  return "entityStatus entityStatusArchived";
}

const STAFF_STATUS_OPTIONS = [
  { value: "Active", label: "Active" },
  { value: "Inactive", label: "Inactive" },
  { value: "Archived", label: "Archived" },
];

export interface UserListPageProps extends BaseUIProps {
  key?: string;
  user: User;
  openCreateForm?: boolean;
}

class _UserListPageState extends ObservableComponent<UserListPageProps> {
  static defaultProps = { user: null, openCreateForm: false };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  staffUsers: StaffUserRecord[] = [];
  searchTerm: string = "";
  roleFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  showFormModal: boolean = false;
  isSaving: boolean = false;
  formError: string = "";
  formDisplayName: string = "";
  formEmail: string = "";
  formPassword: string = "";
  formAppRole: string = "StoreManager";
  formStatus: string = "Active";
  editingUser: StaffUserRecord | null = null;
  pendingOpenCreate: boolean = false;

  public constructor(props: UserListPageProps) {
    super(props);
    this.pendingOpenCreate = Boolean(props.openCreateForm);
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
    this.on(["organization"], this.loadStaffUsers);
    this.on(
      [
        "staffUsers",
        "searchTerm",
        "roleFilter",
        "isLoading",
        "loadError",
        "showFormModal",
        "isSaving",
        "formError",
        "formDisplayName",
        "formEmail",
        "formPassword",
        "formAppRole",
        "formStatus",
        "editingUser",
        "user",
        "userProfile",
        "organization",
      ],
      this.rebuild
    );
  }

  public componentDidUpdate(prevProps: UserListPageProps): void {
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

  public setStaffUsers(val: StaffUserRecord[]): void {
    if (CollectionUtils.isNotEquals(this.staffUsers, val)) {
      this.staffUsers = val;
      this.fire("staffUsers", this);
    }
  }

  public setSearchTerm(val: string): void {
    if (this.searchTerm === val) return;
    this.searchTerm = val;
    this.fire("searchTerm", this);
  }

  public setRoleFilter(val: string): void {
    if (this.roleFilter === val) return;
    this.roleFilter = val;
    this.fire("roleFilter", this);
  }

  public get filteredStaffUsers(): StaffUserRecord[] {
    let list = this.staffUsers;
    const term = this.searchTerm?.trim().toLowerCase() ?? "";
    if (term) {
      list = list.filter(
        (item) =>
          item.displayName.toLowerCase().includes(term) ||
          item.email.toLowerCase().includes(term) ||
          formatRoleLabel(item.appRole).toLowerCase().includes(term)
      );
    }
    if (this.roleFilter) {
      list = list.filter((item) => item.appRole === this.roleFilter);
    }
    return list;
  }

  public loadStaffUsers = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.loadError = "";
      this.setStaffUsers([]);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const items = await StaffUserApi.getAllStaffUsers(orgId);
      this.setStaffUsers(items);
      this.loadError = "";
      if (this.pendingOpenCreate) {
        this.pendingOpenCreate = false;
        this.openCreateForm();
      }
    } catch (exception) {
      this.setStaffUsers([]);
      this.loadError = "Failed to load users: " + exception.toString();
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
      this.fire("loadError", this);
    }
  };

  public openCreateForm = (): void => {
    this.editingUser = null;
    this.formDisplayName = "";
    this.formEmail = "";
    this.formPassword = "";
    this.formAppRole = "StoreManager";
    this.formStatus = "Active";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public openEditForm = (user: StaffUserRecord): void => {
    this.editingUser = user;
    this.formDisplayName = user.displayName ?? "";
    this.formEmail = user.email ?? "";
    this.formPassword = "";
    this.formAppRole = user.appRole || "StoreManager";
    this.formStatus = user.status || "Active";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public closeForm = (): void => {
    this.showFormModal = false;
    this.editingUser = null;
    this.formError = "";
    this.fire("showFormModal", this);
  };

  public saveForm = async (): Promise<void> => {
    const displayName = this.formDisplayName.trim();
    const email = this.formEmail.trim();
    const password = this.formPassword;
    const isEdit = this.editingUser != null;

    if (!displayName) {
      this.formError = "Name is required.";
      this.fire("formError", this);
      return;
    }
    if (!isEdit && !email) {
      this.formError = "Email is required.";
      this.fire("formError", this);
      return;
    }
    if (!isEdit && (!password || password.length < 6)) {
      this.formError = "Password must be at least 6 characters.";
      this.fire("formError", this);
      return;
    }
    if (isEdit && password && password.length < 6) {
      this.formError = "New password must be at least 6 characters.";
      this.fire("formError", this);
      return;
    }
    if (!this.formAppRole) {
      this.formError = "Role is required.";
      this.fire("formError", this);
      return;
    }

    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.formError = "Organization not found.";
      this.fire("formError", this);
      return;
    }

    this.isSaving = true;
    this.formError = "";
    this.fire("isSaving", this);

    try {
      const result = isEdit
        ? await StaffUserApi.updateStaffUser({
            profileId: this.editingUser.id,
            userId: this.editingUser.userId,
            organizationId: orgId,
            displayName,
            appRole: this.formAppRole,
            status: this.formStatus,
            password: password || undefined,
          })
        : await StaffUserApi.createStaffUser({
            displayName,
            email,
            password,
            appRole: this.formAppRole,
          });

      if (!result.success) {
        this.formError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : isEdit
              ? "Failed to update user."
              : "Failed to create user.";
        return;
      }

      this.closeForm();
      await this.loadStaffUsers();
    } catch (exception) {
      this.formError = "Save failed: " + exception.toString();
    } finally {
      this.isSaving = false;
      this.fire("isSaving", this);
      this.fire("formError", this);
    }
  };

  private renderTextField(
    label: string,
    value: string,
    placeHolder: string,
    onChange: (val: string) => void,
    required?: boolean,
    type?: string
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
          type === "password"
            ? React.createElement("input", {
                type: "password",
                className: "roleSelect",
                value,
                placeholder: placeHolder,
                disabled: this.isSaving,
                onChange: (event: React.ChangeEvent<HTMLInputElement>) => {
                  onChange(event.target.value);
                },
                key: "1",
              })
            : ui.InputField({
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
    const isEdit = this.editingUser != null;
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
          className: "storeModalPanel glassCard staffUserModalPanel",
          onClick: (e: React.MouseEvent) => e.stopPropagation(),
          role: "dialog",
          "aria-modal": "true",
        },
        React.createElement(
          "div",
          { className: "storeModalHeader" },
          React.createElement(
            "h2",
            { className: "storeModalTitle" },
            isEdit ? "Edit Team Member" : "Invite Staff Member"
          ),
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
          React.createElement(
            "p",
            { className: "staffUserModalHint" },
            isEdit
              ? "Update this team member's name, role, or status. Leave password blank to keep the current one."
              : "Create internal accounts for your team. Customer accounts are registered separately from the public storefront."
          ),
          this.renderTextField(
            "Full Name",
            this.formDisplayName,
            "Staff member name",
            (val) => {
              this.formDisplayName = val;
              this.fire("formDisplayName", this);
            },
            true
          ),
          isEdit
            ? React.createElement(
                "div",
                { className: "FormFieldStyle formField storeFormField", key: "email-readonly" },
                React.createElement(
                  "label",
                  { className: "fieldLabel" },
                  "Email"
                ),
                React.createElement(
                  "div",
                  { className: "staffUserReadonlyEmail" },
                  this.formEmail || "—"
                )
              )
            : this.renderTextField(
                "Email",
                this.formEmail,
                "work@company.com",
                (val) => {
                  this.formEmail = val;
                  this.fire("formEmail", this);
                },
                true
              ),
          this.renderTextField(
            isEdit ? "New Password" : "Password",
            this.formPassword,
            isEdit ? "Leave blank to keep current password" : "Temporary password",
            (val) => {
              this.formPassword = val;
              this.fire("formPassword", this);
            },
            !isEdit,
            "password"
          ),
          this.renderSelectField(
            "Role",
            this.formAppRole,
            STAFF_ROLE_OPTIONS,
            (val) => {
              this.formAppRole = val;
              this.fire("formAppRole", this);
            },
            true
          ),
          this.renderSelectField(
            "Status",
            this.formStatus,
            STAFF_STATUS_OPTIONS,
            (val) => {
              this.formStatus = val;
              this.fire("formStatus", this);
            },
            true
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
            label: this.isSaving
              ? isEdit
                ? "Saving..."
                : "Creating..."
              : isEdit
                ? "Save Changes"
                : "Send Invite",
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

  private renderNameCell(user: StaffUserRecord): ReactNode {
    const displayName = user.displayName?.trim() || "Unnamed user";
    const email = user.email?.trim() ?? "";
    const hasAvatar = Boolean(user.avatarUrl);

    return React.createElement(
      "div",
      { className: "staffUserNameCell", key: "name" },
      React.createElement(
        "div",
        {
          className: `staffUserAvatar${hasAvatar ? " staffUserAvatarHasImage" : ""}`,
          "aria-hidden": "true",
          title: displayName,
        },
        hasAvatar
          ? React.createElement("img", {
              className: "staffUserAvatarImage",
              src: user.avatarUrl,
              alt: "",
            })
          : staffInitials(displayName, email)
      ),
      React.createElement(
        "span",
        { className: "staffUserDisplayName" },
        displayName
      )
    );
  }

  private renderRoleBadge(user: StaffUserRecord): ReactNode {
    return React.createElement(
      "span",
      {
        className: staffRoleBadgeClass(user.appRole),
        title: user.appRole,
      },
      formatRoleLabel(user.appRole)
    );
  }

  private renderTableRowTr(user: StaffUserRecord, index: number): ReactNode {
    const status = user.status || "Active";

    return React.createElement(
      "tr",
      { className: "staffUsersTableRow", key: "row-" + index },
      React.createElement(
        "td",
        { className: "staffUsersTableCell staffUsersTableCellMember" },
        this.renderNameCell(user)
      ),
      React.createElement(
        "td",
        { className: "staffUsersTableCell staffUsersTableCellEmail" },
        React.createElement(
          "span",
          { className: "staffUserEmailCell", title: user.email },
          user.email || "—"
        )
      ),
      React.createElement(
        "td",
        { className: "staffUsersTableCell staffUsersTableCellRole" },
        this.renderRoleBadge(user)
      ),
      React.createElement(
        "td",
        { className: "staffUsersTableCell staffUsersTableCellStatus" },
        React.createElement(
          "span",
          { className: staffStatusClass(status) },
          status
        )
      ),
      React.createElement(
        "td",
        { className: "staffUsersTableCell staffUsersTableCellActions" },
        IconButton({
          icon: MaterialIcons.edit,
          onPressed: () => this.openEditForm(user),
          onFocusChange: () => {},
          className: "entityActionIcon entityActionEdit",
          key: "edit",
        })
      )
    );
  }

  private renderStaffTable(users: StaffUserRecord[]): ReactNode {
    return React.createElement(
      "div",
      { className: "entityTableWrap staffUsersTableWrap" },
      React.createElement(
        "table",
        { className: "staffUsersTable" },
        React.createElement(
          "colgroup",
          null,
          React.createElement("col", { className: "staffUsersColMember" }),
          React.createElement("col", { className: "staffUsersColEmail" }),
          React.createElement("col", { className: "staffUsersColRole" }),
          React.createElement("col", { className: "staffUsersColStatus" }),
          React.createElement("col", { className: "staffUsersColActions" })
        ),
        React.createElement(
          "thead",
          null,
          React.createElement(
            "tr",
            null,
            React.createElement("th", { className: "staffUsersTableHead" }, "Team Member"),
            React.createElement("th", { className: "staffUsersTableHead" }, "Email"),
            React.createElement("th", { className: "staffUsersTableHead" }, "Role"),
            React.createElement("th", { className: "staffUsersTableHead staffUsersTableHeadStatus" }, "Status"),
            React.createElement("th", { className: "staffUsersTableHead staffUsersTableHeadActions" }, "Actions")
          )
        ),
        React.createElement(
          "tbody",
          null,
          users.map((user, index) => this.renderTableRowTr(user, index))
        )
      )
    );
  }

  private renderLoadingSkeleton(): ReactNode {
    const rows = [0, 1, 2, 3, 4];
    return React.createElement(
      "div",
      { className: "entityTableWrap staffUsersTableWrap" },
      React.createElement(
        "table",
        { className: "staffUsersTable staffUsersTableSkeleton" },
        React.createElement(
          "colgroup",
          null,
          React.createElement("col", { className: "staffUsersColMember" }),
          React.createElement("col", { className: "staffUsersColEmail" }),
          React.createElement("col", { className: "staffUsersColRole" }),
          React.createElement("col", { className: "staffUsersColStatus" }),
          React.createElement("col", { className: "staffUsersColActions" })
        ),
        React.createElement(
          "tbody",
          null,
          rows.map((index) =>
            React.createElement(
              "tr",
              { className: "staffUsersTableRow staffUserSkeletonItem", key: "sk-" + index },
              React.createElement(
                "td",
                { className: "staffUsersTableCell", colSpan: 5 },
                React.createElement(
                  "div",
                  { className: "staffUserSkeletonRow" },
                  React.createElement("div", { className: "staffUserSkeletonAvatar" }),
                  React.createElement("div", {
                    className: "staffUserSkeletonLine staffUserSkeletonLineWide",
                  }),
                  React.createElement("div", { className: "staffUserSkeletonLine" }),
                  React.createElement("div", { className: "staffUserSkeletonBadge" })
                )
              )
            )
          )
        )
      )
    );
  }

  public render(): ReactNode {
    const filtered = this.filteredStaffUsers;
    const totalCount = this.staffUsers.length;
    const activeCount = this.staffUsers.filter(
      (item) => (item.status || "Active") === "Active"
    ).length;
    const countLabel =
      filtered.length === totalCount
        ? `${totalCount} team member${totalCount === 1 ? "" : "s"}`
        : `${filtered.length} of ${totalCount} shown`;

    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          IMSidebarWidget({
            currentUser: this.user,
            userProfile: this.userProfile,
            organization: this.organization,
            activeRoute: "/admin/users",
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
                  title: "Users",
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
                        ui.Container({
                          child: ui.Row({
                            mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
                            crossAxisAlignment: ui.CrossAxisAlignment.center,
                            mainAxisSize: ui.MainAxisSize.max,
                            children: [
                              ui.Column({
                                mainAxisSize: ui.MainAxisSize.min,
                                crossAxisAlignment: ui.CrossAxisAlignment.start,
                                children: [
                                  TextView({
                                    data: "Team Directory",
                                    className: "staffUsersPageTitle",
                                    key: "0",
                                  }),
                                  TextView({
                                    data:
                                      "Manage staff accounts, roles, and access across your organization.",
                                    className: "staffUsersPageSubtitle",
                                    key: "1",
                                  }),
                                ],
                                key: "0",
                              }),
                              TextButton({
                                label: "+ Invite Staff",
                                onPressed: () => this.openCreateForm(),
                                onFocusChange: () => {},
                                className: "primary staffUsersInviteBtn",
                                key: "1",
                              }),
                            ],
                            className: "staffUsersHeaderRow",
                          }),
                          className: "staffUsersPageHeader",
                          key: "header",
                        }),
                        ui.Row({
                          mainAxisSize: ui.MainAxisSize.min,
                          children: [
                            ui.Container({
                              child: TextView({
                                data: `${totalCount} total`,
                                className: "staffUsersStatChip",
                                key: "0",
                              }),
                              key: "0",
                            }),
                            ui.Container({
                              child: TextView({
                                data: `${activeCount} active`,
                                className: "staffUsersStatChip staffUsersStatChipActive",
                                key: "0",
                              }),
                              key: "1",
                            }),
                          ],
                          className: "staffUsersStatsRow",
                          key: "stats",
                        }),
                        ui.Container({
                          child: ui.Row({
                            mainAxisSize: ui.MainAxisSize.max,
                            crossAxisAlignment: ui.CrossAxisAlignment.center,
                            children: [
                              ui.Container({
                                child: this.renderTextField(
                                  "",
                                  this.searchTerm,
                                  "Search by name, email, or role…",
                                  (val) => this.setSearchTerm(val)
                                ),
                                className: "storeSearchField staffUsersSearchField",
                                key: "0",
                              }),
                              ui.Container({
                                child: this.renderSelectField(
                                  "Role",
                                  this.roleFilter,
                                  ROLE_FILTER_OPTIONS,
                                  (val) => this.setRoleFilter(val)
                                ),
                                className: "storeStatusFilter staffUsersRoleFilter",
                                key: "1",
                              }),
                              TextView({
                                data: countLabel,
                                className: "entityFilterCount staffUsersFilterCount",
                                key: "2",
                              }),
                            ],
                            className: "storeFilterRow entityFilterRow staffUsersToolbar",
                          }),
                          className: "staffUsersToolbarCard glassCard",
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
                          ? this.renderLoadingSkeleton()
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title:
                                  this.searchTerm || this.roleFilter
                                    ? "No matching team members"
                                    : "No staff users yet",
                                message:
                                  this.searchTerm || this.roleFilter
                                    ? "Try adjusting your search or role filter."
                                    : "Invite warehouse managers, store managers, and other team members to get started.",
                                actionLabel: "+ Invite Staff",
                                onAction: () => this.openCreateForm(),
                                key: "empty",
                              })
                            : this.renderStaffTable(filtered),
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
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle UserListPage pageBackground ",
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

export default function UserListPage(props: UserListPageProps) {
  return React.createElement(_UserListPageState, {
    ..._UserListPageState.defaultProps,
    ...props,
  });
}
