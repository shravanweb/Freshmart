import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import IMSInputFieldWidget from "./IMSInputFieldWidget";
import UserProfile from "../models/UserProfile";
import IMSidebarWidget from "./IMSidebarWidget";
import TextButton from "./TextButton";
import User from "../models/User";
import PageNavigator from "../classes/PageNavigator";
import Query from "../classes/Query";
import MessageDispatch from "../rocket/MessageDispatch";
import ScrollView2 from "./ScrollView2";
import IMSAppHeaderWidget from "./IMSAppHeaderWidget";
import Organization from "../models/Organization";
import UserProfileByUser from "../classes/UserProfileByUser";
import TextView from "./TextView";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import { UsageConstants } from "../rocket/D3ETemplate";
import { BuildContext } from "../classes/BuildContext";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import AppLogout from "../utils/AppLogout";
import { formatRoleLabel } from "../utils/RoleLabels";
import StaffProfileApi, { StaffStoreAssignment } from "../utils/StaffProfileApi";

function profileInitials(displayName: string, email: string): string {
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

type _OrgNavRefOnPressed = (d3eState: ProfileSettingsPageRefs) => void;

type _NotificationsNavRefOnPressed = (
  d3eState: ProfileSettingsPageRefs
) => void;

type _SecurityNavRefOnPressed = (d3eState: ProfileSettingsPageRefs) => void;

type _SaveRefOnPressed = (d3eState: ProfileSettingsPageRefs) => void;

export interface ProfileSettingsPageProps extends BaseUIProps {
  key?: string;
  user: User;
}
/// To store state data for ProfileSettingsPage
class ProfileSettingsPageRefs {
  contentRefScrollController: ui.ScrollController = new ui.ScrollController();
  public notificationsNavRef: NotificationsNavRefState =
    new NotificationsNavRefState();
  public orgNavRef: OrgNavRefState = new OrgNavRefState();
  public profileNavRef: ProfileNavRefState = new ProfileNavRefState();
  public saveRef: SaveRefState = new SaveRefState();
  public securityNavRef: SecurityNavRefState = new SecurityNavRefState();
}

interface SaveRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ProfileSettingsPageRefs;
  _onSaveHandler?: _SaveRefOnPressed;
}

class SaveRefState extends ObjectObservable {
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

class _SaveRefWithState extends ObservableComponent<SaveRefWithStateProps> {
  saveRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: SaveRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get saveRef(): SaveRefState {
    return this.props.d3eState.saveRef;
  }
  public get d3eState(): ProfileSettingsPageRefs {
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
    this.on(["saveRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 0.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: this.saveRef.disable ? "Saving..." : "Save Profile",
        disable: this.saveRef.disable,
        onPressed: () => {
          void this._onSaveHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "primary settingsFormSaveBtn",
        key: "0",
      }),
      className: "settingsFormActions",
    });
  }
}
function SaveRefWithState(props: SaveRefWithStateProps) {
  return React.createElement(_SaveRefWithState, props);
}

interface SecurityNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ProfileSettingsPageRefs;
  _onSecurityNavHandler?: _SecurityNavRefOnPressed;
}

class SecurityNavRefState extends ObjectObservable {
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

class _SecurityNavRefWithState extends ObservableComponent<SecurityNavRefWithStateProps> {
  securityNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: SecurityNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get securityNavRef(): SecurityNavRefState {
    return this.props.d3eState.securityNavRef;
  }
  public get d3eState(): ProfileSettingsPageRefs {
    return this.props.d3eState;
  }
  public get _onSecurityNavHandler(): _SecurityNavRefOnPressed {
    return this.props._onSecurityNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("securityNavRef", null, this.securityNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["securityNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Security",
      disable: this.securityNavRef.disable,
      onPressed: () => {
        this._onSecurityNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "settingsSubNavItem",
    });
  }
}
function SecurityNavRefWithState(props: SecurityNavRefWithStateProps) {
  return React.createElement(_SecurityNavRefWithState, props);
}

interface NotificationsNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ProfileSettingsPageRefs;
  _onNotificationsNavHandler?: _NotificationsNavRefOnPressed;
}

class NotificationsNavRefState extends ObjectObservable {
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

class _NotificationsNavRefWithState extends ObservableComponent<NotificationsNavRefWithStateProps> {
  notificationsNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: NotificationsNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get notificationsNavRef(): NotificationsNavRefState {
    return this.props.d3eState.notificationsNavRef;
  }
  public get d3eState(): ProfileSettingsPageRefs {
    return this.props.d3eState;
  }
  public get _onNotificationsNavHandler(): _NotificationsNavRefOnPressed {
    return this.props._onNotificationsNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable(
      "notificationsNavRef",
      null,
      this.notificationsNavRef
    );

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["notificationsNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Notifications",
      disable: this.notificationsNavRef.disable,
      onPressed: () => {
        this._onNotificationsNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "settingsSubNavItem",
    });
  }
}
function NotificationsNavRefWithState(
  props: NotificationsNavRefWithStateProps
) {
  return React.createElement(_NotificationsNavRefWithState, props);
}

interface OrgNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ProfileSettingsPageRefs;
  _onOrgNavHandler?: _OrgNavRefOnPressed;
}

class OrgNavRefState extends ObjectObservable {
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

class _OrgNavRefWithState extends ObservableComponent<OrgNavRefWithStateProps> {
  orgNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: OrgNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get orgNavRef(): OrgNavRefState {
    return this.props.d3eState.orgNavRef;
  }
  public get d3eState(): ProfileSettingsPageRefs {
    return this.props.d3eState;
  }
  public get _onOrgNavHandler(): _OrgNavRefOnPressed {
    return this.props._onOrgNavHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("orgNavRef", null, this.orgNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["orgNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Organization",
      disable: this.orgNavRef.disable,
      onPressed: () => {
        this._onOrgNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "settingsSubNavItem",
    });
  }
}
function OrgNavRefWithState(props: OrgNavRefWithStateProps) {
  return React.createElement(_OrgNavRefWithState, props);
}

interface ProfileNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ProfileSettingsPageRefs;
}

class ProfileNavRefState extends ObjectObservable {
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

class _ProfileNavRefWithState extends ObservableComponent<ProfileNavRefWithStateProps> {
  profileNavRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ProfileNavRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get profileNavRef(): ProfileNavRefState {
    return this.props.d3eState.profileNavRef;
  }
  public get d3eState(): ProfileSettingsPageRefs {
    return this.props.d3eState;
  }
  public initState() {
    super.initState();

    this.updateObservable("profileNavRef", null, this.profileNavRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["profileNavRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Profile",
      disable: this.profileNavRef.disable,
      onFocusChange: (val) => {},
      className: "settingsSubNavItem settingsSubNavItemActive",
    });
  }
}
function ProfileNavRefWithState(props: ProfileNavRefWithStateProps) {
  return React.createElement(_ProfileNavRefWithState, props);
}

class _ProfileSettingsPageState extends ObservableComponent<ProfileSettingsPageProps> {
  static defaultProps = { user: null };
  d3eState: ProfileSettingsPageRefs = new ProfileSettingsPageRefs();
  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  activeSection: string = "profile";
  displayName: string = "";
  phone: string = "";
  emailLabel: string = "";
  roleLabel: string = "";
  avatarUrl: string = "";
  profileMessage: string = "";
  assignedStores: StaffStoreAssignment[] = [];
  isSavingProfile: boolean = false;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: ProfileSettingsPageProps) {
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

    this.on(["user", "user.email"], this.computeEmailLabel);

    this.computeEmailLabel();

    this.on(["userProfile"], this.loadStaffProfile);

    this.on(["userProfile", "userProfile.appRole"], this.computeRoleLabel);

    this.computeRoleLabel();

    this.on(
      [
        "activeSection",
        "assignedStores",
        "avatarUrl",
        "displayName",
        "emailLabel",
        "notificationsNavRef",
        "orgNavRef",
        "organization",
        "phone",
        "isSavingProfile",
        "profileMessage",
        "profileNavRef",
        "roleLabel",
        "saveRef",
        "securityNavRef",
        "user",
        "userProfile",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: ProfileSettingsPageProps): void {
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
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_PROFILESETTINGSPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
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
  public setActiveSection(val: string): void {
    let isValChanged: boolean = this.activeSection !== val;

    if (!isValChanged) {
      return;
    }

    this.activeSection = val;

    this.fire("activeSection", this);
  }
  public setDisplayName(val: string): void {
    let isValChanged: boolean = this.displayName !== val;

    if (!isValChanged) {
      return;
    }

    this.displayName = val;

    this.fire("displayName", this);
  }
  public setPhone(val: string): void {
    let isValChanged: boolean = this.phone !== val;

    if (!isValChanged) {
      return;
    }

    this.phone = val;

    this.fire("phone", this);
  }
  public setEmailLabel(val: string): void {
    let isValChanged: boolean = this.emailLabel !== val;

    if (!isValChanged) {
      return;
    }

    this.emailLabel = val;

    this.fire("emailLabel", this);
  }
  public computeEmailLabel = (): void => {
    try {
      this.setEmailLabel(this.user.email);
    } catch (exception) {
      console.log(" exception in computeEmailLabel : " + exception.toString());

      this.setEmailLabel("");
    }
  };

  public setAvatarUrl(val: string): void {
    if (this.avatarUrl === val) {
      return;
    }
    this.avatarUrl = val;
    this.fire("avatarUrl", this);
  }

  public setAssignedStores(val: StaffStoreAssignment[]): void {
    if (this.assignedStores === val) {
      return;
    }
    this.assignedStores = val;
    this.fire("assignedStores", this);
  }

  public loadStaffProfile = async (): Promise<void> => {
    if (this.user == null) {
      return;
    }

    try {
      const profile = await StaffProfileApi.getProfile();
      if (!profile) {
        return;
      }
      if (profile.displayName) {
        this.setDisplayName(profile.displayName);
      }
      this.setPhone(profile.phone ?? "");
      this.setAvatarUrl(profile.avatarUrl ?? "");
      this.setAssignedStores(profile.assignedStores ?? []);
      if (profile.appRole) {
        this.setRoleLabel(formatRoleLabel(profile.appRole));
      }
    } catch (exception) {
      console.log(" exception in loadStaffProfile : " + exception.toString());
    }
  };

  public computeAvatarUrl = (): void => {
    const cached = StaffProfileApi.getCachedProfile();
    if (cached?.avatarUrl) {
      this.setAvatarUrl(cached.avatarUrl);
    }
  };

  public setProfileMessage(val: string): void {
    if (this.profileMessage === val) {
      return;
    }
    this.profileMessage = val;
    this.fire("profileMessage", this);
  }

  public handleAvatarChange = async (
    event: React.ChangeEvent<HTMLInputElement>
  ): Promise<void> => {
    const file = event.target.files?.[0];
    if (!file) {
      return;
    }
    if (!file.type.startsWith("image/")) {
      this.setProfileMessage("Please choose an image file.");
      return;
    }

    this.setProfileMessage("");
    try {
      const profile = await StaffProfileApi.uploadAvatar(file);
      this.setAvatarUrl(profile?.avatarUrl ?? "");
      this.setProfileMessage("Profile photo saved to your account.");
    } catch (exception) {
      this.setProfileMessage("Unable to upload photo. Please try again.");
      console.log(" exception in handleAvatarChange : " + exception.toString());
    } finally {
      event.target.value = "";
    }
  };

  public removeAvatar = async (): Promise<void> => {
    try {
      const profile = await StaffProfileApi.removeAvatar();
      this.setAvatarUrl(profile?.avatarUrl ?? "");
      this.setProfileMessage("Profile photo removed.");
    } catch (exception) {
      this.setProfileMessage("Unable to remove photo. Please try again.");
      console.log(" exception in removeAvatar : " + exception.toString());
    }
  };

  private renderAssignedStoresSection(): ReactNode {
    const roleName = this.userProfile?.appRole?.name ?? "";
    if (roleName !== "StoreManager") {
      return null;
    }

    const stores = this.assignedStores;
    const message =
      stores.length === 0
        ? "No store assigned yet. You currently see all organization stores. Ask an admin to assign your store."
        : stores.length === 1
          ? `Assigned store: ${stores[0].name} (${stores[0].code})`
          : `Assigned stores: ${stores.map((store) => store.name).join(", ")}`;

    return React.createElement(
      "div",
      { className: "settingsProfileStoresCard", key: "assigned-stores" },
      React.createElement("span", { className: "settingsProfileStoresLabel" }, "Store access"),
      React.createElement("p", { className: "settingsProfileStoresText" }, message)
    );
  }

  public renderProfileAvatarSection(): ReactNode {
    const initials = profileInitials(this.displayName, this.emailLabel);
    const isSuccessMessage =
      this.profileMessage.includes("updated") ||
      this.profileMessage.includes("removed");

    return React.createElement(
      "div",
      { className: "settingsProfileAvatarCard", key: "avatar" },
      React.createElement(
        "div",
        { className: "settingsProfileAvatarRow" },
        this.avatarUrl
          ? React.createElement("img", {
              src: this.avatarUrl,
              alt: this.displayName || "Profile photo",
              className: "settingsProfileAvatarImage",
            })
          : React.createElement(
              "span",
              { className: "settingsProfileAvatarFallback" },
              initials
            ),
        React.createElement(
          "div",
          { className: "settingsProfileAvatarMeta" },
          React.createElement(
            "h3",
            { className: "settingsProfileAvatarName" },
            this.displayName || "Staff member"
          ),
          React.createElement(
            "p",
            { className: "settingsProfileAvatarEmail" },
            this.emailLabel || "—"
          ),
          this.roleLabel
            ? React.createElement(
                "span",
                { className: "settingsProfileAvatarRole" },
                this.roleLabel
              )
            : null
        )
      ),
      React.createElement(
        "div",
        { className: "settingsProfileAvatarActions" },
        React.createElement(
          "label",
          { className: "settingsProfilePhotoBtn" },
          React.createElement("span", { className: "settingsProfilePhotoBtnText" }, "Upload photo"),
          React.createElement("input", {
            type: "file",
            accept: "image/jpeg,image/png,image/webp,image/*",
            className: "settingsProfileAvatarInput",
            onChange: this.handleAvatarChange,
          })
        ),
        this.avatarUrl
          ? React.createElement(
              "button",
              {
                type: "button",
                className: "settingsProfilePhotoBtn settingsProfilePhotoBtnMuted",
                onClick: () => this.removeAvatar(),
              },
              "Remove"
            )
          : null
      ),
      this.profileMessage
        ? React.createElement(
            "p",
            {
              className: isSuccessMessage
                ? "settingsProfileAvatarNotice settingsProfileAvatarNoticeSuccess"
                : "settingsProfileAvatarNotice",
            },
            this.profileMessage
          )
        : null
    );
  }

  public setRoleLabel(val: string): void {
    let isValChanged: boolean = this.roleLabel !== val;

    if (!isValChanged) {
      return;
    }

    this.roleLabel = val;

    this.fire("roleLabel", this);
  }

  public computeRoleLabel = (): void => {
    try {
      this.setRoleLabel(
        this.userProfile !== null
          ? formatRoleLabel(this.userProfile.appRole.toString())
          : ""
      );
    } catch (exception) {
      console.log(" exception in computeRoleLabel : " + exception.toString());

      this.setRoleLabel("");
    }
  };
  private renderFormField(
    label: string,
    text: string,
    placeHolder: string,
    key: string,
    keyboardType?: string
  ): ReactNode {
    return ui.Container({
      child: IMSInputFieldWidget({
        label,
        text,
        placeHolder,
        keyboardType,
        key: "0",
      }),
      className: "settingsFormField",
      key,
    });
  }

  public render(): ReactNode {
    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          IMSidebarWidget({
            currentUser: this.user,
            userProfile: this.userProfile,
            organization: this.organization,
            activeRoute: "/settings/profile",
            onNavigate: (route) => {
              this.onNavigateHandler(route, this.d3eState);
            },
            key: "0",
          }),
          ui.Container({
            child: ui.Column({
              crossAxisAlignment: ui.CrossAxisAlignment.stretch,
              mainAxisSize: ui.MainAxisSize.max,
              children: [
                IMSAppHeaderWidget({
                  title: "Settings",
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
                    child: ui.Row({
                      mainAxisSize: ui.MainAxisSize.max,
                      crossAxisAlignment: ui.CrossAxisAlignment.start,
                      children: [
                        ui.Container({
                          child: ui.Column({
                            crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                            children: [
                              TextView({
                                data: "Settings",
                                className: "settingsSubNavTitle",
                                key: "0",
                              }),
                              ProfileNavRefWithState({
                                d3eState: this.d3eState,
                                key: "1",
                              }),
                              OrgNavRefWithState({
                                d3eState: this.d3eState,
                                _onOrgNavHandler: this.onOrgNavHandler,
                                key: "2",
                              }),
                              NotificationsNavRefWithState({
                                d3eState: this.d3eState,
                                _onNotificationsNavHandler:
                                  this.onNotificationsNavHandler,
                                key: "3",
                              }),
                              SecurityNavRefWithState({
                                d3eState: this.d3eState,
                                _onSecurityNavHandler:
                                  this.onSecurityNavHandler,
                                key: "4",
                              }),
                            ],
                          }),
                          className: "settingsSubNavCard glassCard",
                          key: "0",
                        }),
                        ui.Container({
                          child: ui.Column({
                            crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                            children: [
                              ui.Container({
                                child: ui.Column({
                                  crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                                  mainAxisSize: ui.MainAxisSize.min,
                                  children: [
                                    TextView({
                                      data: "Profile",
                                      className: "settingsFormTitle",
                                      key: "0",
                                    }),
                                    TextView({
                                      data:
                                        "Upload a profile photo and manage your display name and contact details.",
                                      className: "settingsFormSubtitle",
                                      key: "1",
                                    }),
                                  ],
                                }),
                                className: "settingsFormHeader",
                                key: "0",
                              }),
                              this.renderProfileAvatarSection(),
                              this.renderAssignedStoresSection(),
                              this.renderFormField(
                                "Display Name",
                                this.displayName,
                                "Your display name",
                                "f-name"
                              ),
                              this.renderFormField(
                                "Email",
                                this.emailLabel,
                                "Email address",
                                "f-email",
                                "email"
                              ),
                              this.renderFormField(
                                "Phone",
                                this.phone,
                                "Phone number",
                                "f-phone",
                                "phone"
                              ),
                              SaveRefWithState({
                                d3eState: this.d3eState,
                                _onSaveHandler: this.onSaveHandler,
                                key: "save",
                              }),
                            ],
                          }),
                          className: "settingsFormCard glassCard",
                          key: "1",
                        }),
                      ],
                    }),
                    className: "settingsPageShell",
                    key: "1",
                  }),
                  scrollDirection: ui.Axis.vertical,
                  className: "pageContent",
                  controller: this.d3eState.contentRefScrollController,
                }),
              ],
            }),
            className: "settingsPageMain",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_FormFieldStyle_PrimaryButtonStyle ProfileSettingsPage pageBackground ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onInit = (): void => {
    if (this.userProfile !== null) {
      this.setDisplayName(this.userProfile.displayName);

      this.setPhone(
        this.userProfile.phone !== null ? this.userProfile.phone : ""
      );
    }
  };
  public onLogoutHandler = (d3eState: ProfileSettingsPageRefs): void => {
    void AppLogout.signOut(this.navigator);
  };
  public onProfileHandler = (d3eState: ProfileSettingsPageRefs): void => {
    this.navigator.pushProfileSettingsPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onNavigateHandler = (
    route: string,
    d3eState: ProfileSettingsPageRefs
  ): void => {
    AppRouteNavigator.navigate(this.navigator, route, this.user, {
      target: "main",
      replace: true,
    });
  };
  public onOrgNavHandler = (d3eState: ProfileSettingsPageRefs): void => {
    this.navigator.pushOrganizationSettingsPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onNotificationsNavHandler = (
    d3eState: ProfileSettingsPageRefs
  ): void => {
    this.navigator.pushNotificationPreferencesPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onSecurityNavHandler = (d3eState: ProfileSettingsPageRefs): void => {
    this.navigator.pushChangePasswordPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onSaveHandler = async (d3eState: ProfileSettingsPageRefs): Promise<void> => {
    this.isSavingProfile = true;
    d3eState.saveRef.setDisable(true);
    this.setProfileMessage("");
    this.fire("isSavingProfile", this);

    try {
      const saved = await StaffProfileApi.saveProfile(this.displayName, this.phone);
      if (saved && this.userProfile !== null) {
        this.userProfile.setDisplayName(saved.displayName);
        this.userProfile.setPhone(saved.phone);
      }
      this.setProfileMessage("Profile saved successfully.");
    } catch (exception) {
      this.setProfileMessage("Unable to save profile. Please try again.");
      console.log(" exception in onSaveHandler : " + exception.toString());
    } finally {
      this.isSavingProfile = false;
      d3eState.saveRef.setDisable(false);
      this.fire("isSavingProfile", this);
    }
  };
  public dispose(): void {
    MessageDispatch.get().dispose(this.userProfileData);

    super.dispose();
  }
  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
  public get notificationsNavRef() {
    return this.d3eState.notificationsNavRef;
  }
  public get orgNavRef() {
    return this.d3eState.orgNavRef;
  }
  public get profileNavRef() {
    return this.d3eState.profileNavRef;
  }
  public get saveRef() {
    return this.d3eState.saveRef;
  }
  public get securityNavRef() {
    return this.d3eState.securityNavRef;
  }
}
export default function ProfileSettingsPage(props: ProfileSettingsPageProps) {
  return React.createElement(_ProfileSettingsPageState, {
    ..._ProfileSettingsPageState.defaultProps,
    ...props,
  });
}
