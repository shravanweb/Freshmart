import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
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

type _ProfileNavRefOnPressed = (
  d3eState: NotificationPreferencesPageRefs
) => void;

type _OrgNavRefOnPressed = (d3eState: NotificationPreferencesPageRefs) => void;

type _SecurityNavRefOnPressed = (
  d3eState: NotificationPreferencesPageRefs
) => void;

export interface NotificationPreferencesPageProps extends BaseUIProps {
  key?: string;
  user: User;
}
/// To store state data for NotificationPreferencesPage
class NotificationPreferencesPageRefs {
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
  d3eState: NotificationPreferencesPageRefs;
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
  public get d3eState(): NotificationPreferencesPageRefs {
    return this.props.d3eState;
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
      margin: ui.EdgeInsets.fromLTRB(0.0, 24.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: "Save Preferences",
        disable: this.saveRef.disable,
        onFocusChange: (val) => {},
        className: "primary",
        key: "0",
      }),
      className: "primary xa0",
    });
  }
}
function SaveRefWithState(props: SaveRefWithStateProps) {
  return React.createElement(_SaveRefWithState, props);
}

interface SecurityNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: NotificationPreferencesPageRefs;
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
  public get d3eState(): NotificationPreferencesPageRefs {
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
  d3eState: NotificationPreferencesPageRefs;
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
  public get d3eState(): NotificationPreferencesPageRefs {
    return this.props.d3eState;
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
      onFocusChange: (val) => {},
      className: "settingsSubNavItem settingsSubNavItemActive",
      key: "0",
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
  d3eState: NotificationPreferencesPageRefs;
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
  public get d3eState(): NotificationPreferencesPageRefs {
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
  d3eState: NotificationPreferencesPageRefs;
  _onProfileNavHandler?: _ProfileNavRefOnPressed;
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
  public get d3eState(): NotificationPreferencesPageRefs {
    return this.props.d3eState;
  }
  public get _onProfileNavHandler(): _ProfileNavRefOnPressed {
    return this.props._onProfileNavHandler;
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
      onPressed: () => {
        this._onProfileNavHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "settingsSubNavItem",
    });
  }
}
function ProfileNavRefWithState(props: ProfileNavRefWithStateProps) {
  return React.createElement(_ProfileNavRefWithState, props);
}

class _NotificationPreferencesPageState extends ObservableComponent<NotificationPreferencesPageProps> {
  static defaultProps = { user: null };
  d3eState: NotificationPreferencesPageRefs =
    new NotificationPreferencesPageRefs();
  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  activeSection: string = "notifications";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: NotificationPreferencesPageProps) {
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

    this.on(
      [
        "activeSection",
        "notificationsNavRef",
        "orgNavRef",
        "organization",
        "profileNavRef",
        "saveRef",
        "securityNavRef",
        "user",
        "userProfile",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: NotificationPreferencesPageProps): void {
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
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_NOTIFICATIONPREFERENCESPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
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
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          IMSidebarWidget({
            currentUser: this.user,
            userProfile: this.userProfile,
            organization: this.organization,
            activeRoute: "/settings/notifications",
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
                  title: "Notifications",
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
                      crossAxisAlignment: ui.CrossAxisAlignment.stretch,
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
                                _onProfileNavHandler: this.onProfileNavHandler,
                                key: "1",
                              }),
                              OrgNavRefWithState({
                                d3eState: this.d3eState,
                                _onOrgNavHandler: this.onOrgNavHandler,
                                key: "2",
                              }),
                              NotificationsNavRefWithState({
                                d3eState: this.d3eState,
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
                              TextView({
                                data:
                                  this.activeSection === "notifications"
                                    ? "Notification Preferences"
                                    : this.activeSection,
                                className: "x570",
                                key: "0",
                              }),
                              TextView({
                                data: "Bind NotificationTemplate rows for organization to render channel toggles",
                                className: "x21",
                                key: "1",
                              }),
                              ui.Container({
                                child: ui.Column({
                                  crossAxisAlignment:
                                    ui.CrossAxisAlignment.stretch,
                                  children: [
                                    TextView({
                                      data: "LOW_STOCK_ALERT \u2014 Email \u2014 Low stock threshold reached",
                                      className: "xea",
                                      key: "0",
                                    }),
                                    TextView({
                                      data: "EXPIRY_WARNING \u2014 In-App \u2014 Batch expiring within threshold",
                                      className: "x692",
                                      key: "1",
                                    }),
                                    TextView({
                                      data: "PO_APPROVAL \u2014 Email \u2014 Purchase order pending approval",
                                      className: "x61f",
                                      key: "2",
                                    }),
                                  ],
                                }),
                                className: "glassPanel x6c",
                                key: "2",
                              }),
                              SaveRefWithState({
                                d3eState: this.d3eState,
                                key: "3",
                              }),
                            ],
                          }),
                          className: "glassPanel x03b",
                          key: "1",
                        }),
                      ],
                    }),
                    key: "1",
                  }),
                  scrollDirection: ui.Axis.vertical,
                  className: "pageContent",
                  controller: this.d3eState.contentRefScrollController,
                }),
              ],
            }),
            className: "xb1",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle NotificationPreferencesPage pageBackground ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onLogoutHandler = (
    d3eState: NotificationPreferencesPageRefs
  ): void => {
    void AppLogout.signOut(this.navigator);
  };
  public onProfileHandler = (
    d3eState: NotificationPreferencesPageRefs
  ): void => {
    this.navigator.pushProfileSettingsPage({
      user: this.user,
      target: "main",
      replace: false,
    });
  };
  public onNavigateHandler = (
    route: string,
    d3eState: NotificationPreferencesPageRefs
  ): void => {
    AppRouteNavigator.navigate(this.navigator, route, this.user, {
      target: "main",
      replace: true,
    });
  };
  public onProfileNavHandler = (
    d3eState: NotificationPreferencesPageRefs
  ): void => {
    this.navigator.pushProfileSettingsPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onOrgNavHandler = (
    d3eState: NotificationPreferencesPageRefs
  ): void => {
    this.navigator.pushOrganizationSettingsPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onSecurityNavHandler = (
    d3eState: NotificationPreferencesPageRefs
  ): void => {
    this.navigator.pushChangePasswordPage({
      user: this.user,
      target: "main",
      replace: true,
    });
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
export default function NotificationPreferencesPage(
  props: NotificationPreferencesPageProps
) {
  return React.createElement(_NotificationPreferencesPageState, {
    ..._NotificationPreferencesPageState.defaultProps,
    ...props,
  });
}
