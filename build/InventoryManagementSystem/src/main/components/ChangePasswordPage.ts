import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import UserProfile from "../models/UserProfile";
import IMSInputFieldWidget from "./IMSInputFieldWidget";
import Organization from "../models/Organization";
import TextButton from "./TextButton";
import User from "../models/User";
import IMSidebarWidget from "./IMSidebarWidget";
import TextView from "./TextView";
import PageNavigator from "../classes/PageNavigator";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import UserProfileByUser from "../classes/UserProfileByUser";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import MessageDispatch from "../rocket/MessageDispatch";
import Query from "../classes/Query";
import { UsageConstants } from "../rocket/D3ETemplate";
import { BuildContext } from "../classes/BuildContext";

type _SaveRefOnPressed = (d3eState: ChangePasswordPageRefs) => void;

export interface ChangePasswordPageProps extends BaseUIProps {
  key?: string;
  user: User;
}
/// To store state data for ChangePasswordPage
class ChangePasswordPageRefs {
  public saveRef: SaveRefState = new SaveRefState();
}

interface SaveRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ChangePasswordPageRefs;
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
  public get d3eState(): ChangePasswordPageRefs {
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
      margin: ui.EdgeInsets.fromLTRB(0.0, 24.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: "Update Password",
        disable: this.saveRef.disable,
        onPressed: () => {
          this._onSaveHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "primary",
        key: "0",
      }),
      className: "primary x69",
    });
  }
}
function SaveRefWithState(props: SaveRefWithStateProps) {
  return React.createElement(_SaveRefWithState, props);
}

class _ChangePasswordPageState extends ObservableComponent<ChangePasswordPageProps> {
  static defaultProps = { user: null };
  d3eState: ChangePasswordPageRefs = new ChangePasswordPageRefs();
  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  currentPassword: string = "";
  newPassword: string = "";
  confirmPassword: string = "";
  errorMessage: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: ChangePasswordPageProps) {
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
        "confirmPassword",
        "currentPassword",
        "newPassword",
        "organization",
        "organization.name",
        "saveRef",
        "user",
        "userProfile",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: ChangePasswordPageProps): void {
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
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_CHANGEPASSWORDPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
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
  public setCurrentPassword(val: string): void {
    let isValChanged: boolean = this.currentPassword !== val;

    if (!isValChanged) {
      return;
    }

    this.currentPassword = val;

    this.fire("currentPassword", this);
  }
  public setNewPassword(val: string): void {
    let isValChanged: boolean = this.newPassword !== val;

    if (!isValChanged) {
      return;
    }

    this.newPassword = val;

    this.fire("newPassword", this);
  }
  public setConfirmPassword(val: string): void {
    let isValChanged: boolean = this.confirmPassword !== val;

    if (!isValChanged) {
      return;
    }

    this.confirmPassword = val;

    this.fire("confirmPassword", this);
  }
  public setErrorMessage(val: string): void {
    let isValChanged: boolean = this.errorMessage !== val;

    if (!isValChanged) {
      return;
    }

    this.errorMessage = val;

    this.fire("errorMessage", this);
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
            activeRoute: "/settings/profile",
            onNavigate: (route) => {
              this.onNavigateHandler(route);
            },
            key: "0",
          }),
          ui.Container({
            child: ui.Container({
              child: ui.Column({
                crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                children: [
                  TextView({
                    data:
                      this.organization !== null ? this.organization.name : "",
                    className: "xa6",
                    key: "0",
                  }),
                  TextView({
                    data: "Change Password",
                    className: "xb5",
                    key: "1",
                  }),
                  IMSInputFieldWidget({
                    label: "Current Password",
                    text: this.currentPassword,
                    obscureText: true,
                    key: "2",
                  }),
                  ui.Container({ className: "xa4", key: "3" }),
                  IMSInputFieldWidget({
                    label: "New Password",
                    text: this.newPassword,
                    obscureText: true,
                    key: "4",
                  }),
                  ui.Container({ className: "x96", key: "5" }),
                  IMSInputFieldWidget({
                    label: "Confirm New Password",
                    text: this.confirmPassword,
                    obscureText: true,
                    key: "6",
                  }),
                  SaveRefWithState({
                    d3eState: this.d3eState,
                    _onSaveHandler: this.onSaveHandler,
                    key: "7",
                  }),
                ],
              }),
              className: "glassCard x42",
            }),
            className: "x79",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassSidebarStyle_GlassCardStyle_FormFieldStyle_PrimaryButtonStyle ChangePasswordPage pageBackground ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onSaveHandler = (d3eState: ChangePasswordPageRefs): void => {
    if (this.newPassword !== this.confirmPassword) {
      this.setErrorMessage("Passwords do not match");

      return;
    }

    this.navigator.pushProfileSettingsPage({
      user: this.user,
      target: "main",
      replace: true,
    });
  };
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
  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
  public get saveRef() {
    return this.d3eState.saveRef;
  }
}
export default function ChangePasswordPage(props: ChangePasswordPageProps) {
  return React.createElement(_ChangePasswordPageState, {
    ..._ChangePasswordPageState.defaultProps,
    ...props,
  });
}
