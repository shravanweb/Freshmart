import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import OtpLoginWidget from "./OtpLoginWidget";
import LoginResult from "../classes/LoginResult";
import TextButton from "./TextButton";
import User from "../models/User";
import PageNavigator from "../classes/PageNavigator";
import Query from "../classes/Query";
import LoginFormWidget from "./LoginFormWidget";
import { UsageConstants } from "../rocket/D3ETemplate";
import { BuildContext } from "../classes/BuildContext";
import PostAuthNavigation from "../utils/PostAuthNavigation";

type _ToggleOtpRefOnPressed = (d3eState: LoginPageRefs) => void;

export interface LoginPageProps extends BaseUIProps {
  key?: string;
  customerMode?: boolean;
}
/// To store state data for LoginPage
class LoginPageRefs {
  public toggleOtpRef: ToggleOtpRefState = new ToggleOtpRefState();
}

interface ToggleOtpRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: LoginPageRefs;
  _onToggleOtpHandler?: _ToggleOtpRefOnPressed;
  showOtp: boolean;
}

class ToggleOtpRefState extends ObjectObservable {
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

class _ToggleOtpRefWithState extends ObservableComponent<ToggleOtpRefWithStateProps> {
  toggleOtpRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ToggleOtpRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get showOtp(): boolean {
    return this.props.showOtp;
  }
  public get toggleOtpRef(): ToggleOtpRefState {
    return this.props.d3eState.toggleOtpRef;
  }
  public get d3eState(): LoginPageRefs {
    return this.props.d3eState;
  }
  public get _onToggleOtpHandler(): _ToggleOtpRefOnPressed {
    return this.props._onToggleOtpHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("toggleOtpRef", null, this.toggleOtpRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["showOtp", "toggleOtpRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 16.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: this.showOtp ? "Use password login" : "Use OTP login",
        disable: this.toggleOtpRef.disable,
        onPressed: () => {
          this._onToggleOtpHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "loginAuthLink",
      }),
      className: "x31",
    });
  }
}
function ToggleOtpRefWithState(props: ToggleOtpRefWithStateProps) {
  return React.createElement(_ToggleOtpRefWithState, props);
}

class _LoginPageState extends ObservableComponent<LoginPageProps> {
  d3eState: LoginPageRefs = new LoginPageRefs();
  email: string = "";
  password: string = "";
  isLoading: boolean = false;
  errorMessage: string = "";
  showOtp: boolean = false;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: LoginPageProps) {
    super(props);

    this.initState();
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(
      [
        "email",
        "errorMessage",
        "isLoading",
        "password",
        "showOtp",
        "toggleOtpRef",
      ],
      this.rebuild
    );
  }
  public setEmail(val: string): void {
    let isValChanged: boolean = this.email !== val;

    if (!isValChanged) {
      return;
    }

    this.email = val;

    this.fire("email", this);
  }
  public setPassword(val: string): void {
    let isValChanged: boolean = this.password !== val;

    if (!isValChanged) {
      return;
    }

    this.password = val;

    this.fire("password", this);
  }
  public setIsLoading(val: boolean): void {
    let isValChanged: boolean = this.isLoading !== val;

    if (!isValChanged) {
      return;
    }

    this.isLoading = val;

    this.fire("isLoading", this);
  }
  public setErrorMessage(val: string): void {
    let isValChanged: boolean = this.errorMessage !== val;

    if (!isValChanged) {
      return;
    }

    this.errorMessage = val;

    this.fire("errorMessage", this);
  }
  public setShowOtp(val: boolean): void {
    let isValChanged: boolean = this.showOtp !== val;

    if (!isValChanged) {
      return;
    }

    this.showOtp = val;

    this.fire("showOtp", this);
  }
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      alignment: ui.Alignment.center,
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.max,
        mainAxisAlignment: ui.MainAxisAlignment.center,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          !this.showOtp
            ? LoginFormWidget({
                email: this.email,
                password: this.password,
                errorMessage: this.errorMessage,
                isLoading: this.isLoading,
                onLogin: (email, password) => {
                  this.onLoginHandler(email, password, this.d3eState);
                },
                onForgotPassword: () => {
                  this.onForgotPasswordHandler(this.d3eState);
                },
                onSignup: () => {
                  this.onSignupHandler(this.d3eState);
                },
                key: "0",
              })
            : OtpLoginWidget({ email: this.email, key: "0" }),
          ToggleOtpRefWithState({
            d3eState: this.d3eState,
            _onToggleOtpHandler: this.onToggleOtpHandler,
            showOtp: this.showOtp,
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassCardStyle_PrimaryButtonStyle_FormFieldStyle LoginPage pageBackground x54 ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onLoginHandler = async (
    email: string,
    password: string,
    d3eState: LoginPageRefs
  ): Promise<void> => {
    this.setIsLoading(true);

    this.setErrorMessage("");

    try {
      let result: LoginResult =
        await Query.get().loginInventoryManagementSystemUserWithEmailAndPassword(
          UsageConstants.QUERY_LOGININVENTORYMANAGEMENTSYSTEMUSERWITHEMAILANDPASSWORD_LOGINPAGE_EVENTHANDLERS_ONLOGINHANDLER_BLOCK,
          { email: email.toLowerCase(), password: password }
        );

      if (result.success) {
        const user = result.userObject as User;

        await PostAuthNavigation.routeAfterAuth(
          this.navigator,
          user,
          result.token,
          {
            displayName: email.split("@")[0],
            target: "main",
          }
        );

        return;
      }

      this.setErrorMessage(result.failureMessage);
    } catch (e) {
      this.setErrorMessage("Login failed: " + e.toString());
    }

    this.setIsLoading(false);
  };
  public onForgotPasswordHandler = (d3eState: LoginPageRefs): void => {
    this.navigator.pushForgotPasswordPage();
  };
  public onSignupHandler = (d3eState: LoginPageRefs): void => {
    this.navigator.pushSignupPage({
      customerMode: this.props.customerMode ?? false,
    });
  };
  public onToggleOtpHandler = (d3eState: LoginPageRefs): void => {
    this.setShowOtp(!this.showOtp);
  };
  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
  public get toggleOtpRef() {
    return this.d3eState.toggleOtpRef;
  }
}
export default function LoginPage(props: LoginPageProps) {
  return React.createElement(_LoginPageState, props);
}
