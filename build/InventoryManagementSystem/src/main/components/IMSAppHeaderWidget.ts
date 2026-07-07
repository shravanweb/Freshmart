import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import Organization from "../models/Organization";
import User from "../models/User";
import UserProfile from "../models/UserProfile";
import InAppNotification from "../models/InAppNotification";
import TextButton from "./TextButton";
import TextView from "./TextView";
import NotificationCenterWidget from "./NotificationCenterWidget";
import { BuildContext } from "../classes/BuildContext";
import { formatRoleLabel } from "../utils/RoleLabels";
import StaffProfileApi from "../utils/StaffProfileApi";

type _IMSAppHeaderWidgetOnLogout = () => void;

type _IMSAppHeaderWidgetOnProfile = () => void;

type _IMSAppHeaderWidgetOnAddProduct = () => void;

type _ProfileRefOnPressed = (d3eState: IMSAppHeaderWidgetRefs) => void;

type _LogoutRefOnPressed = (d3eState: IMSAppHeaderWidgetRefs) => void;

export interface IMSAppHeaderWidgetProps extends BaseUIProps {
  key?: string;
  title: string;
  organization?: Organization;
  user?: User;
  userProfile?: UserProfile;
  unreadCount?: number;
  notifications?: Array<InAppNotification>;
  onLogout?: _IMSAppHeaderWidgetOnLogout;
  onProfile?: _IMSAppHeaderWidgetOnProfile;
  onAddProduct?: _IMSAppHeaderWidgetOnAddProduct;
}
/// To store state data for IMSAppHeaderWidget
class IMSAppHeaderWidgetRefs {
  public logoutRef: LogoutRefState = new LogoutRefState();
  public profileRef: ProfileRefState = new ProfileRefState();
}

interface LogoutRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSAppHeaderWidgetRefs;
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
  public get d3eState(): IMSAppHeaderWidgetRefs {
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
    return TextButton({
      label: " ",
      disable: this.logoutRef.disable,
      onPressed: () => {
        this._onLogoutHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "secondary headerLogoutBtn headerLogoutIconBtn",
    });
  }
}
function LogoutRefWithState(props: LogoutRefWithStateProps) {
  return React.createElement(_LogoutRefWithState, props);
}

interface ProfileRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSAppHeaderWidgetRefs;
  _onProfileHandler?: _ProfileRefOnPressed;
}

class ProfileRefState extends ObjectObservable {
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

class _ProfileRefWithState extends ObservableComponent<ProfileRefWithStateProps> {
  profileRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ProfileRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get profileRef(): ProfileRefState {
    return this.props.d3eState.profileRef;
  }
  public get d3eState(): IMSAppHeaderWidgetRefs {
    return this.props.d3eState;
  }
  public get _onProfileHandler(): _ProfileRefOnPressed {
    return this.props._onProfileHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("profileRef", null, this.profileRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["profileRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 0.0, 12.0, 0.0, new Map()),
      child: TextButton({
        label: "Profile",
        disable: this.profileRef.disable,
        onPressed: () => {
          this._onProfileHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
      }),
      className: "x38",
    });
  }
}
function ProfileRefWithState(props: ProfileRefWithStateProps) {
  return React.createElement(_ProfileRefWithState, props);
}

class _IMSAppHeaderWidgetState extends ObservableComponent<IMSAppHeaderWidgetProps> {
  static defaultProps = {
    title: "",
    organization: null,
    user: null,
    userProfile: null,
    unreadCount: 0,
    notifications: [],
    onLogout: null,
    onProfile: null,
    onAddProduct: null,
  };
  d3eState: IMSAppHeaderWidgetRefs = new IMSAppHeaderWidgetRefs();
  orgName: string = "";
  userName: string = "";
  roleLabel: string = "";
  avatarUrl: string = "";
  private profileStorageUnsub: (() => void) | null = null;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: IMSAppHeaderWidgetProps) {
    super(props);

    this.initState();
  }
  public get title(): string {
    return this.props.title;
  }
  public get organization(): Organization {
    return this.props.organization;
  }
  public get user(): User {
    return this.props.user;
  }
  public get userProfile(): UserProfile {
    return this.props.userProfile;
  }
  public get unreadCount(): number {
    return this.props.unreadCount;
  }
  public get notifications(): Array<InAppNotification> {
    return this.props.notifications;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;

    this.profileStorageUnsub = StaffProfileApi.onChange(() => {
      this.applyCachedAvatar();
    });
  }
  public initListeners(): void {
    this.updateSyncProperty("organization", this.props.organization);

    this.updateSyncProperty("user", this.props.user);

    this.updateSyncProperty("userProfile", this.props.userProfile);

    this.on(["organization", "organization.name"], this.computeOrgName);

    this.computeOrgName();

    this.on(
      [
        "user",
        "user.email",
        "userProfile",
        "userProfile.displayName",
        "userProfile.appRole",
      ],
      this.computeUserName
    );

    this.computeUserName();

    this.on(["user", "user.email", "userProfile", "userProfile.displayName"], this.loadStaffAvatar);

    this.loadStaffAvatar();

    this.on(["userProfile", "userProfile.appRole"], this.computeRoleLabel);

    this.computeRoleLabel();

    this.on(
      [
        "avatarUrl",
        "logoutRef",
        "orgName",
        "organization",
        "profileRef",
        "roleLabel",
        "title",
        "unreadCount",
        "userName",
      ],
      this.rebuild
    );
  }
  public dispose(): void {
    if (this.profileStorageUnsub) {
      this.profileStorageUnsub();
      this.profileStorageUnsub = null;
    }
    super.dispose();
  }
  public componentDidUpdate(prevProps: IMSAppHeaderWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.title !== this.props.title) {
      this.fire("title", this);
    }

    if (prevProps.organization !== this.props.organization) {
      this.updateObservable(
        "organization",
        prevProps.organization,
        this.props.organization
      );

      this.fire("organization", this);
    }

    if (prevProps.user !== this.props.user) {
      this.updateObservable("user", prevProps.user, this.props.user);

      this.fire("user", this);
    }

    if (prevProps.userProfile !== this.props.userProfile) {
      this.updateObservable(
        "userProfile",
        prevProps.userProfile,
        this.props.userProfile
      );

      this.fire("userProfile", this);
    }

    if (prevProps.unreadCount !== this.props.unreadCount) {
      this.fire("unreadCount", this);
    }
  }
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
      this.setOrgName(this.organization !== null ? this.organization.name : "");
    } catch (exception) {
      console.log(" exception in computeOrgName : " + exception.toString());

      this.setOrgName("");
    }
  };
  public setUserName(val: string): void {
    let isValChanged: boolean = this.userName !== val;

    if (!isValChanged) {
      return;
    }

    this.userName = val;

    this.fire("userName", this);
  }
  public computeUserName = (): void => {
    try {
      this.setUserName(this.resolvedUserName());
    } catch (exception) {
      console.log(" exception in computeUserName : " + exception.toString());

      this.setUserName("");
    }
  };

  public setAvatarUrl(val: string): void {
    if (this.avatarUrl === val) {
      return;
    }
    this.avatarUrl = val;
    this.fire("avatarUrl", this);
  }

  public applyCachedAvatar = (): void => {
    if (this.user == null) {
      this.setAvatarUrl("");
      return;
    }

    const cached = StaffProfileApi.getCachedProfile();
    if (!StaffProfileApi.isCachedForEmail(this.user.email)) {
      this.setAvatarUrl("");
      return;
    }

    this.setAvatarUrl(
      cached?.avatarUrl
        ? this.withAvatarCacheBust(cached.avatarUrl, cached.id, this.user.email)
        : ""
    );
  };

  private withAvatarCacheBust(
    url: string,
    profileId: number,
    email: string
  ): string {
    if (!url) {
      return "";
    }
    const separator = url.includes("?") ? "&" : "?";
    const owner = encodeURIComponent(email.trim().toLowerCase());
    return `${url}${separator}v=${profileId}&u=${owner}`;
  }

  public loadStaffAvatar = async (): Promise<void> => {
    if (this.user == null) {
      this.setAvatarUrl("");
      return;
    }

    const userEmail = this.user.email;
    const cached = StaffProfileApi.getCachedProfile();
    if (
      StaffProfileApi.isCachedForEmail(userEmail) &&
      cached?.avatarUrl
    ) {
      this.setAvatarUrl(
        this.withAvatarCacheBust(cached.avatarUrl, cached.id, userEmail)
      );
      return;
    }

    this.setAvatarUrl("");

    try {
      const profile = await StaffProfileApi.getProfile();
      if (!StaffProfileApi.isCachedForEmail(userEmail)) {
        this.setAvatarUrl("");
        return;
      }
      this.setAvatarUrl(
        profile?.avatarUrl
          ? this.withAvatarCacheBust(profile.avatarUrl, profile.id, userEmail)
          : ""
      );
    } catch (exception) {
      this.setAvatarUrl("");
    }
  };

  public computeAvatarUrl = (): void => {
    this.applyCachedAvatar();
  };

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
          ? formatRoleLabel(this.userProfile.appRole.name)
          : ""
      );
    } catch (exception) {
      console.log(" exception in computeRoleLabel : " + exception.toString());

      this.setRoleLabel("");
    }
  };
  public resolvedUserName(): string {
    try {
      const profileName = this.userProfile?.displayName?.trim() ?? "";
      if (profileName) {
        return profileName;
      }
      const email = this.user?.email?.trim() ?? "";
      if (email) {
        const local = email.split("@")[0]?.trim() ?? "";
        return local || email;
      }
      return "";
    } catch (exception) {
      return "";
    }
  }
  public resolvedRoleLabel(): string {
    try {
      return this.userProfile !== null
        ? formatRoleLabel(this.userProfile.appRole.name)
        : "";
    } catch (exception) {
      return "";
    }
  }
  public resolvedUserInitial(): string {
    const name = this.resolvedUserName().trim();
    if (!name) {
      return "?";
    }
    const parts = name.split(/\s+/).filter(Boolean);
    if (parts.length >= 2) {
      return (parts[0][0] + parts[1][0]).toUpperCase();
    }
    return name.slice(0, 2).toUpperCase();
  }

  public renderHeaderAvatar(): ReactNode {
    const avatarKey = `${this.user?.email ?? "guest"}-${this.avatarUrl ? "img" : "initial"}`;
    if (this.avatarUrl) {
      return React.createElement("img", {
        src: this.avatarUrl,
        alt: this.resolvedUserName() || "Profile",
        className: "headerUserAvatarImage",
        key: avatarKey,
      });
    }

    return TextView({
      data: this.resolvedUserInitial(),
      className: "headerUserAvatarInitial",
      key: avatarKey,
    });
  }

  public render(): ReactNode {
    const userName = this.resolvedUserName();
    const roleLabel = this.resolvedRoleLabel();
    const useAdminHeader =
      this.userProfile !== null || this.user !== null;

    if (useAdminHeader) {
      return ui.Container({
        child: ui.Row({
          mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
          crossAxisAlignment: ui.CrossAxisAlignment.center,
          mainAxisSize: ui.MainAxisSize.max,
          children: [
            ui.Row({
              mainAxisSize: ui.MainAxisSize.min,
              crossAxisAlignment: ui.CrossAxisAlignment.center,
              children: [
                ui.Container({
                  child: this.renderHeaderAvatar(),
                  className: ui.join(
                    "headerUserAvatar",
                    this.avatarUrl ? "headerUserAvatarHasImage" : ""
                  ),
                  key: "avatar",
                }),
                ui.Column({
                  mainAxisSize: ui.MainAxisSize.min,
                  crossAxisAlignment: ui.CrossAxisAlignment.start,
                  children: [
                    TextView({
                      data: "Signed in as",
                      className: "headerSignedInLabel",
                      key: "0",
                    }),
                    TextView({
                      data: userName,
                      className: "headerUserName",
                      key: "1",
                    }),
                  ],
                  key: "0",
                }),
                roleLabel !== ""
                  ? TextView({
                      data: roleLabel,
                      className: "headerRoleBadge",
                      key: "1",
                    })
                  : TextView({
                      data: " ",
                      className: "headerRoleBadgePlaceholder",
                      key: "1",
                    }),
              ],
              key: "0",
            }),
            ui.Row({
              mainAxisSize: ui.MainAxisSize.min,
              crossAxisAlignment: ui.CrossAxisAlignment.center,
              children: [
                NotificationCenterWidget({
                  user: this.user,
                  organization: this.organization,
                  unreadCount: this.unreadCount,
                  notifications: this.notifications,
                  className: "headerCompact",
                  key: "0",
                }),
                this.title !== null && this.title.length > 0
                  ? TextView({
                      data: this.title,
                      className: "headerPageTitle",
                      key: "1",
                    })
                  : null,
                LogoutRefWithState({
                  d3eState: this.d3eState,
                  _onLogoutHandler: this.onLogoutHandler,
                  key: "2",
                }),
              ],
              key: "1",
            }),
          ],
        }),
        className: ui.join(
          "PrimaryButtonStyle IMSAppHeaderWidget appHeaderToolbar ",
          this.props.className ?? ""
        ),
        ...copyBaseUIProps(this.props),
      });
    }

    return ui.Container({
      child: ui.Row({
        mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        mainAxisSize: ui.MainAxisSize.max,
        children: [
          ui.Column({
            mainAxisSize: ui.MainAxisSize.min,
            crossAxisAlignment: ui.CrossAxisAlignment.start,
            children: [
              TextView({ data: this.title, className: "x1c", key: "0" }),
              this.organization !== null
                ? TextView({ data: this.orgName, className: "x26", key: "1" })
                : null,
            ],
            key: "0",
          }),
          ui.Row({
            mainAxisSize: ui.MainAxisSize.min,
            crossAxisAlignment: ui.CrossAxisAlignment.center,
            children: [
              ProfileRefWithState({
                d3eState: this.d3eState,
                _onProfileHandler: this.onProfileHandler,
                key: "0",
              }),
              LogoutRefWithState({
                d3eState: this.d3eState,
                _onLogoutHandler: this.onLogoutHandler,
                key: "1",
              }),
            ],
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "GlassPanelStyle_PrimaryButtonStyle IMSAppHeaderWidget glassPanel xad ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onProfileHandler = (d3eState: IMSAppHeaderWidgetRefs): void => {
    this.onProfile();
  };
  public onLogoutHandler = (d3eState: IMSAppHeaderWidgetRefs): void => {
    this.onLogout();
  };
  public get onLogout(): _IMSAppHeaderWidgetOnLogout {
    return this.props.onLogout;
  }
  public get onProfile(): _IMSAppHeaderWidgetOnProfile {
    return this.props.onProfile;
  }
  public get onAddProduct(): _IMSAppHeaderWidgetOnAddProduct {
    return this.props.onAddProduct;
  }
  public get logoutRef() {
    return this.d3eState.logoutRef;
  }
  public get profileRef() {
    return this.d3eState.profileRef;
  }
}
export default function IMSAppHeaderWidget(props: IMSAppHeaderWidgetProps) {
  return React.createElement(_IMSAppHeaderWidgetState, {
    ..._IMSAppHeaderWidgetState.defaultProps,
    ...props,
  });
}
