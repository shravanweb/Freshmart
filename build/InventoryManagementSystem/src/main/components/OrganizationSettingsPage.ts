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
import OrganizationItem from "../classes/OrganizationItem";
import OrganizationItemRequest from "../models/OrganizationItemRequest";
import { UsageConstants } from "../rocket/D3ETemplate";
import { BuildContext } from "../classes/BuildContext";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import AppLogout from "../utils/AppLogout";

type _ProfileNavRefOnPressed = (d3eState: OrganizationSettingsPageRefs) => void;

type _NotificationsNavRefOnPressed = (
  d3eState: OrganizationSettingsPageRefs
) => void;

type _SecurityNavRefOnPressed = (
  d3eState: OrganizationSettingsPageRefs
) => void;

type _SaveRefOnPressed = (d3eState: OrganizationSettingsPageRefs) => void;

export interface OrganizationSettingsPageProps extends BaseUIProps {
  key?: string;
  user: User;
}
/// To store state data for OrganizationSettingsPage
class OrganizationSettingsPageRefs {
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
  d3eState: OrganizationSettingsPageRefs;
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
  public get d3eState(): OrganizationSettingsPageRefs {
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
        label: "Save Changes",
        disable: this.saveRef.disable,
        onPressed: () => {
          this._onSaveHandler(this.d3eState);
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
  d3eState: OrganizationSettingsPageRefs;
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
  public get d3eState(): OrganizationSettingsPageRefs {
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
  d3eState: OrganizationSettingsPageRefs;
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
  public get d3eState(): OrganizationSettingsPageRefs {
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
  d3eState: OrganizationSettingsPageRefs;
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
  public get d3eState(): OrganizationSettingsPageRefs {
    return this.props.d3eState;
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
      onFocusChange: (val) => {},
      className: "settingsSubNavItem settingsSubNavItemActive",
      key: "0",
    });
  }
}
function OrgNavRefWithState(props: OrgNavRefWithStateProps) {
  return React.createElement(_OrgNavRefWithState, props);
}

interface ProfileNavRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: OrganizationSettingsPageRefs;
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
  public get d3eState(): OrganizationSettingsPageRefs {
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

class _OrganizationSettingsPageState extends ObservableComponent<OrganizationSettingsPageProps> {
  static defaultProps = { user: null };
  d3eState: OrganizationSettingsPageRefs = new OrganizationSettingsPageRefs();
  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  activeSection: string = "organization";
  orgData: OrganizationItem = null;
  orgRecord: Organization = null;
  orgName: string = "";
  legalName: string = "";
  orgEmail: string = "";
  orgPhone: string = "";
  address: string = "";
  timezone: string = "";
  currency: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: OrganizationSettingsPageProps) {
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

    this.on(["organization"], this.computeOrgData);

    this.computeOrgData();

    this.on(
      ["orgData", "orgData.items", "organization"],
      this.computeOrgRecord
    );

    this.computeOrgRecord();

    this.on(
      [
        "activeSection",
        "address",
        "currency",
        "legalName",
        "notificationsNavRef",
        "orgEmail",
        "orgName",
        "orgNavRef",
        "orgPhone",
        "organization",
        "profileNavRef",
        "saveRef",
        "securityNavRef",
        "timezone",
        "user",
        "userProfile",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: OrganizationSettingsPageProps): void {
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
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_ORGANIZATIONSETTINGSPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
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
  public setOrgData(val: OrganizationItem): void {
    let isValChanged: boolean = this.orgData !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("orgData", this.orgData, val);

    MessageDispatch.get().dispose(this.orgData);

    this.orgData = val;

    this.fire("orgData", this);
  }
  public computeOrgData = async (): Promise<void> => {
    try {
      this.setOrgData(
        this.organization !== null
          ? await Query.get().getOrganizationItem(
              UsageConstants.QUERY_GETORGANIZATIONITEM_ORGANIZATIONSETTINGSPAGE_PROPERTIES_ORGDATA_COMPUTATION,
              new OrganizationItemRequest({ organization: this.organization }),
              { "synchronize": true }
            )
          : null
      );
    } catch (exception) {
      console.log(" exception in computeOrgData : " + exception.toString());

      this.setOrgData(null);
    }
  };
  public setOrgRecord(val: Organization): void {
    let isValChanged: boolean = this.orgRecord !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("orgRecord", this.orgRecord, val);

    this.orgRecord = val;

    this.fire("orgRecord", this);
  }
  public computeOrgRecord = (): void => {
    try {
      this.setOrgRecord(
        this.orgData !== null && this.orgData.items.length > 0
          ? this.orgData.items[0]
          : this.organization
      );
    } catch (exception) {
      console.log(" exception in computeOrgRecord : " + exception.toString());

      this.setOrgRecord(null);
    }
  };
  public setOrgName(val: string): void {
    let isValChanged: boolean = this.orgName !== val;

    if (!isValChanged) {
      return;
    }

    this.orgName = val;

    this.fire("orgName", this);
  }
  public setLegalName(val: string): void {
    let isValChanged: boolean = this.legalName !== val;

    if (!isValChanged) {
      return;
    }

    this.legalName = val;

    this.fire("legalName", this);
  }
  public setOrgEmail(val: string): void {
    let isValChanged: boolean = this.orgEmail !== val;

    if (!isValChanged) {
      return;
    }

    this.orgEmail = val;

    this.fire("orgEmail", this);
  }
  public setOrgPhone(val: string): void {
    let isValChanged: boolean = this.orgPhone !== val;

    if (!isValChanged) {
      return;
    }

    this.orgPhone = val;

    this.fire("orgPhone", this);
  }
  public setAddress(val: string): void {
    let isValChanged: boolean = this.address !== val;

    if (!isValChanged) {
      return;
    }

    this.address = val;

    this.fire("address", this);
  }
  public setTimezone(val: string): void {
    let isValChanged: boolean = this.timezone !== val;

    if (!isValChanged) {
      return;
    }

    this.timezone = val;

    this.fire("timezone", this);
  }
  public setCurrency(val: string): void {
    let isValChanged: boolean = this.currency !== val;

    if (!isValChanged) {
      return;
    }

    this.currency = val;

    this.fire("currency", this);
  }
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
            activeRoute: "/settings/organization",
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
                                _onProfileNavHandler: this.onProfileNavHandler,
                                key: "1",
                              }),
                              OrgNavRefWithState({
                                d3eState: this.d3eState,
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
                                      data: "Organization",
                                      className: "settingsFormTitle",
                                      key: "0",
                                    }),
                                    TextView({
                                      data:
                                        "Update your company identity, support contacts, and regional preferences.",
                                      className: "settingsFormSubtitle",
                                      key: "1",
                                    }),
                                  ],
                                }),
                                className: "settingsFormHeader",
                                key: "0",
                              }),
                              ui.Row({
                                mainAxisSize: ui.MainAxisSize.max,
                                crossAxisAlignment: ui.CrossAxisAlignment.start,
                                children: [
                                  this.renderFormField(
                                    "Display Name",
                                    this.orgName,
                                    "Organization name",
                                    "f-name"
                                  ),
                                  this.renderFormField(
                                    "Legal Name",
                                    this.legalName,
                                    "Legal entity name",
                                    "f-legal"
                                  ),
                                ],
                                className: "settingsFormGridRow",
                                key: "1",
                              }),
                              ui.Row({
                                mainAxisSize: ui.MainAxisSize.max,
                                crossAxisAlignment: ui.CrossAxisAlignment.start,
                                children: [
                                  this.renderFormField(
                                    "Support Email",
                                    this.orgEmail,
                                    "contact@company.com",
                                    "f-email",
                                    "email"
                                  ),
                                  this.renderFormField(
                                    "Phone",
                                    this.orgPhone,
                                    "Phone number",
                                    "f-phone",
                                    "phone"
                                  ),
                                ],
                                className: "settingsFormGridRow",
                                key: "2",
                              }),
                              this.renderFormField(
                                "Address",
                                this.address,
                                "Business address",
                                "f-address"
                              ),
                              ui.Row({
                                mainAxisSize: ui.MainAxisSize.max,
                                crossAxisAlignment: ui.CrossAxisAlignment.start,
                                children: [
                                  this.renderFormField(
                                    "Timezone",
                                    this.timezone,
                                    "UTC",
                                    "f-timezone"
                                  ),
                                  this.renderFormField(
                                    "Currency",
                                    this.currency,
                                    "USD",
                                    "f-currency"
                                  ),
                                ],
                                className: "settingsFormGridRow",
                                key: "3",
                              }),
                              SaveRefWithState({
                                d3eState: this.d3eState,
                                _onSaveHandler: this.onSaveHandler,
                                key: "4",
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
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_FormFieldStyle_PrimaryButtonStyle OrganizationSettingsPage pageBackground ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onInit = (): void => {
    if (this.orgRecord !== null) {
      this.setOrgName(this.orgRecord.name);

      this.setLegalName(
        this.orgRecord.legalName !== null ? this.orgRecord.legalName : ""
      );

      this.setOrgEmail(
        this.orgRecord.email !== null ? this.orgRecord.email : ""
      );

      this.setOrgPhone(
        this.orgRecord.phone !== null ? this.orgRecord.phone : ""
      );

      this.setAddress(
        this.orgRecord.address !== null ? this.orgRecord.address : ""
      );

      this.setTimezone(
        this.orgRecord.timezone !== null ? this.orgRecord.timezone : "UTC"
      );

      this.setCurrency(
        this.orgRecord.currency !== null ? this.orgRecord.currency : "USD"
      );
    }
  };
  public onLogoutHandler = (d3eState: OrganizationSettingsPageRefs): void => {
    void AppLogout.signOut(this.navigator);
  };
  public onProfileHandler = (d3eState: OrganizationSettingsPageRefs): void => {
    this.navigator.pushProfileSettingsPage({
      user: this.user,
      target: "main",
      replace: false,
    });
  };
  public onNavigateHandler = (
    route: string,
    d3eState: OrganizationSettingsPageRefs
  ): void => {
    AppRouteNavigator.navigate(this.navigator, route, this.user, {
      target: "main",
      replace: true,
    });
  };
  public onProfileNavHandler = (
    d3eState: OrganizationSettingsPageRefs
  ): void => {
    this.navigator.pushProfileSettingsPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onNotificationsNavHandler = (
    d3eState: OrganizationSettingsPageRefs
  ): void => {
    this.navigator.pushNotificationPreferencesPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onSecurityNavHandler = (
    d3eState: OrganizationSettingsPageRefs
  ): void => {
    this.navigator.pushChangePasswordPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
  public onSaveHandler = (d3eState: OrganizationSettingsPageRefs): void => {
    if (this.orgRecord !== null) {
      this.orgRecord.setName(this.orgName);

      this.orgRecord.setLegalName(this.legalName);

      this.orgRecord.setEmail(this.orgEmail);

      this.orgRecord.setPhone(this.orgPhone);

      this.orgRecord.setAddress(this.address);

      this.orgRecord.setTimezone(this.timezone);

      this.orgRecord.setCurrency(this.currency);
    }
  };
  public dispose(): void {
    MessageDispatch.get().dispose(this.userProfileData);

    MessageDispatch.get().dispose(this.orgData);

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
export default function OrganizationSettingsPage(
  props: OrganizationSettingsPageProps
) {
  return React.createElement(_OrganizationSettingsPageState, {
    ..._OrganizationSettingsPageState.defaultProps,
    ...props,
  });
}
