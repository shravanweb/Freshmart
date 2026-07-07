import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import TextButton from "./TextButton";
import TextView from "./TextView";
import MaterialIcons from "../icons/MaterialIcons";
import { BuildContext } from "../classes/BuildContext";

type _LoginFormWidgetOnLogin = (email: string, password: string) => void;

type _LoginFormWidgetOnForgotPassword = () => void;

type _LoginFormWidgetOnSignup = () => void;

type _SubmitRefOnPressed = (d3eState: LoginFormWidgetRefs) => void;

type _ForgotRefOnPressed = (d3eState: LoginFormWidgetRefs) => void;

export interface LoginFormWidgetProps extends BaseUIProps {
  key?: string;
  email?: string;
  password?: string;
  errorMessage?: string;
  isLoading?: boolean;
  onLogin?: _LoginFormWidgetOnLogin;
  onForgotPassword?: _LoginFormWidgetOnForgotPassword;
  onSignup?: _LoginFormWidgetOnSignup;
}
/// To store state data for LoginFormWidget
class LoginFormWidgetRefs {
  public forgotRef: ForgotRefState = new ForgotRefState();
  public submitRef: SubmitRefState = new SubmitRefState();
}

interface ForgotRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: LoginFormWidgetRefs;
  _onForgotPressedHandler?: _ForgotRefOnPressed;
}

class ForgotRefState extends ObjectObservable {
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

class _ForgotRefWithState extends ObservableComponent<ForgotRefWithStateProps> {
  forgotRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ForgotRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get forgotRef(): ForgotRefState {
    return this.props.d3eState.forgotRef;
  }
  public get d3eState(): LoginFormWidgetRefs {
    return this.props.d3eState;
  }
  public get _onForgotPressedHandler(): _ForgotRefOnPressed {
    return this.props._onForgotPressedHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("forgotRef", null, this.forgotRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["forgotRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 16.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: "Forgot password?",
        disable: this.forgotRef.disable,
        onPressed: () => {
          this._onForgotPressedHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "loginAuthLink",
      }),
      className: "x12",
    });
  }
}
function ForgotRefWithState(props: ForgotRefWithStateProps) {
  return React.createElement(_ForgotRefWithState, props);
}

interface SubmitRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: LoginFormWidgetRefs;
  _onSubmitPressedHandler?: _SubmitRefOnPressed;
  isLoading: boolean;
}

class SubmitRefState extends ObjectObservable {}

class _SubmitRefWithState extends ObservableComponent<SubmitRefWithStateProps> {
  submitRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: SubmitRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get isLoading(): boolean {
    return this.props.isLoading;
  }
  public get submitRef(): SubmitRefState {
    return this.props.d3eState.submitRef;
  }
  public get d3eState(): LoginFormWidgetRefs {
    return this.props.d3eState;
  }
  public get _onSubmitPressedHandler(): _SubmitRefOnPressed {
    return this.props._onSubmitPressedHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("submitRef", null, this.submitRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["isLoading", "submitRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 24.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: "Sign In",
        disable: this.isLoading,
        onPressed: () => {
          this._onSubmitPressedHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "primary",
        key: "0",
      }),
      className: "primary x5c",
    });
  }
}
function SubmitRefWithState(props: SubmitRefWithStateProps) {
  return React.createElement(_SubmitRefWithState, props);
}

class _LoginFormWidgetState extends ObservableComponent<LoginFormWidgetProps> {
  static defaultProps = {
    email: "",
    password: "",
    errorMessage: "",
    isLoading: false,
    onLogin: null,
    onForgotPassword: null,
    onSignup: null,
  };
  d3eState: LoginFormWidgetRefs = new LoginFormWidgetRefs();
  emailValue = "";
  passwordValue = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: LoginFormWidgetProps) {
    super(props);

    this.initState();
  }
  public get email(): string {
    return this.props.email;
  }
  public get password(): string {
    return this.props.password;
  }
  public get errorMessage(): string {
    return this.props.errorMessage;
  }
  public get isLoading(): boolean {
    return this.props.isLoading;
  }
  public initState() {
    super.initState();

    this.emailValue = this.props.email ?? "";
    this.passwordValue = this.props.password ?? "";

    this.initListeners();

    this.enableBuild = true;
  }

  private renderField(
    label: string,
    value: string,
    onChanged: (text: string) => void,
    options?: {
      placeHolder?: string;
      obscureText?: boolean;
      keyboardType?: string;
      fieldIcon?: ui.IconData;
      key?: string;
    }
  ): ReactNode {
    const inputField = ui.InputField({
      value,
      placeHolder: options?.placeHolder ?? "",
      obscureText: options?.obscureText ?? false,
      onChanged,
      onFocusChange: () => {},
      key: "1",
    });

    const inputControl =
      options?.fieldIcon != null
        ? React.createElement(
            "div",
            { className: "authInputWrap" },
            React.createElement(
              "span",
              {
                className: "authFieldIcon MaterialIcons",
                "aria-hidden": "true",
              },
              String.fromCharCode(options.fieldIcon.codePoint)
            ),
            inputField
          )
        : inputField;

    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({ data: label, className: "fieldLabel", key: "0" }),
          inputControl,
        ],
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField",
      key: options?.key,
    });
  }
  public initListeners(): void {
    this.on(
      [
        "email",
        "errorMessage",
        "forgotRef",
        "isLoading",
        "password",
        "submitRef",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: LoginFormWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.email !== this.props.email) {
      this.fire("email", this);
    }

    if (prevProps.password !== this.props.password) {
      this.fire("password", this);
    }

    if (prevProps.errorMessage !== this.props.errorMessage) {
      this.fire("errorMessage", this);
    }

    if (prevProps.isLoading !== this.props.isLoading) {
      this.fire("isLoading", this);
    }
  }
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          ui.Container({
            child: React.createElement("img", {
              src: "/images/freshmart.png",
              alt: "FreshMart",
              className: "loginBrandLogo",
            }),
            className: "loginBrandSlot",
            key: "logo",
          }),
          TextView({
            data: "",
            textAlign: ui.TextAlign.center,
            className: "x3ba",
            key: "0",
          }),
          TextView({
            data: "Sign in to FreshMart Retail Group",
            textAlign: ui.TextAlign.center,
            className: "x89",
            key: "1",
          }),
          this.renderField("Email", this.emailValue, (text) => {
            this.emailValue = text;
          }, {
            placeHolder: "you@company.com",
            keyboardType: "emailAddress",
            fieldIcon: MaterialIcons.email,
            key: "2",
          }),
          this.renderField("Password", this.passwordValue, (text) => {
            this.passwordValue = text;
          }, {
            placeHolder: "Enter password",
            obscureText: true,
            fieldIcon: MaterialIcons.lock_outline,
            key: "3",
          }),
          this.errorMessage !== null && this.errorMessage.isNotEmpty
            ? TextView({ data: this.errorMessage, className: "xd8", key: "4" })
            : null,
          SubmitRefWithState({
            d3eState: this.d3eState,
            _onSubmitPressedHandler: this.onSubmitPressedHandler,
            isLoading: this.isLoading,
            key: "6",
          }),
          ForgotRefWithState({
            d3eState: this.d3eState,
            _onForgotPressedHandler: this.onForgotPressedHandler,
            key: "7",
          }),
          ui.Container({
            margin: ui.EdgeInsets.fromLTRB(0.0, 8.0, 0.0, 0.0, new Map()),
            child: TextButton({
              label: "Don't have an account? Sign up",
              onPressed: () => {
                this.onSignup?.();
              },
              onFocusChange: () => {},
              className: "loginAuthLink",
            }),
            key: "8",
          }),
        ],
      }),
      className: ui.join(
        "GlassCardStyle_PrimaryButtonStyle_FormFieldStyle LoginFormWidget glassCard ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onSubmitPressedHandler = (d3eState: LoginFormWidgetRefs): void => {
    this.onLogin(this.emailValue, this.passwordValue);
  };
  public onForgotPressedHandler = (d3eState: LoginFormWidgetRefs): void => {
    this.onForgotPassword();
  };
  public get onLogin(): _LoginFormWidgetOnLogin {
    return this.props.onLogin;
  }
  public get onForgotPassword(): _LoginFormWidgetOnForgotPassword {
    return this.props.onForgotPassword;
  }
  public get onSignup(): _LoginFormWidgetOnSignup {
    return this.props.onSignup;
  }
  public get forgotRef() {
    return this.d3eState.forgotRef;
  }
  public get submitRef() {
    return this.d3eState.submitRef;
  }
}
export default function LoginFormWidget(props: LoginFormWidgetProps) {
  return React.createElement(_LoginFormWidgetState, {
    ..._LoginFormWidgetState.defaultProps,
    ...props,
  });
}
