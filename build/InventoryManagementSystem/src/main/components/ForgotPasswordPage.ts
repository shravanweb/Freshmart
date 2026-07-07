import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import TextButton from "./TextButton";
import TextView from "./TextView";
import PageNavigator from "../classes/PageNavigator";
import Query from "../classes/Query";
import LoginResult from "../classes/LoginResult";
import { BuildContext } from "../classes/BuildContext";

type _SubmitRefOnPressed = (d3eState: ForgotPasswordPageRefs) => void;

type _BackRefOnPressed = (d3eState: ForgotPasswordPageRefs) => void;

export interface ForgotPasswordPageProps extends BaseUIProps {
  key?: string;
}
/// To store state data for ForgotPasswordPage
class ForgotPasswordPageRefs {
  public backRef: BackRefState = new BackRefState();
  public submitRef: SubmitRefState = new SubmitRefState();
}

interface BackRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ForgotPasswordPageRefs;
  _onBackHandler?: _BackRefOnPressed;
}

class BackRefState extends ObjectObservable {
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

class _BackRefWithState extends ObservableComponent<BackRefWithStateProps> {
  backRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: BackRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get backRef(): BackRefState {
    return this.props.d3eState.backRef;
  }
  public get d3eState(): ForgotPasswordPageRefs {
    return this.props.d3eState;
  }
  public get _onBackHandler(): _BackRefOnPressed {
    return this.props._onBackHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("backRef", null, this.backRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["backRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 16.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: "Back to login",
        disable: this.backRef.disable,
        onPressed: () => {
          this._onBackHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
      }),
      className: "xd2",
    });
  }
}
function BackRefWithState(props: BackRefWithStateProps) {
  return React.createElement(_BackRefWithState, props);
}

interface SubmitRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ForgotPasswordPageRefs;
  _onSubmitHandler?: _SubmitRefOnPressed;
  step: number;
  isLoading: boolean;
}

class SubmitRefState extends ObjectObservable {
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

class _SubmitRefWithState extends ObservableComponent<SubmitRefWithStateProps> {
  submitRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: SubmitRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get step(): number {
    return this.props.step;
  }
  public get isLoading(): boolean {
    return this.props.isLoading;
  }
  public get submitRef(): SubmitRefState {
    return this.props.d3eState.submitRef;
  }
  public get d3eState(): ForgotPasswordPageRefs {
    return this.props.d3eState;
  }
  public get _onSubmitHandler(): _SubmitRefOnPressed {
    return this.props._onSubmitHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("submitRef", null, this.submitRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["isLoading", "step", "submitRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 24.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: this.step === 1 ? "Send OTP" : "Reset Password",
        disable: this.submitRef.disable || this.isLoading,
        onPressed: () => {
          this._onSubmitHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "primary",
        key: "0",
      }),
      className: "primary xd6",
    });
  }
}
function SubmitRefWithState(props: SubmitRefWithStateProps) {
  return React.createElement(_SubmitRefWithState, props);
}

class _ForgotPasswordPageState extends ObservableComponent<ForgotPasswordPageProps> {
  d3eState: ForgotPasswordPageRefs = new ForgotPasswordPageRefs();
  emailValue: string = "";
  otpValue: string = "";
  newPasswordValue: string = "";
  otpToken: string = "";
  step: number = 1;
  message: string = "";
  errorMessage: string = "";
  isLoading: boolean = false;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: ForgotPasswordPageProps) {
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
        "backRef",
        "errorMessage",
        "isLoading",
        "message",
        "step",
        "submitRef",
      ],
      this.rebuild
    );
  }

  private renderField(
    label: string,
    value: string,
    onChanged: (text: string) => void,
    options?: { placeHolder?: string; obscureText?: boolean; keyboardType?: string }
  ): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({ data: label, className: "fieldLabel", key: "0" }),
          ui.InputField({
            value,
            placeHolder: options?.placeHolder ?? "",
            obscureText: options?.obscureText ?? false,
            onChanged,
            onFocusChange: () => {},
            key: "1",
          }),
        ],
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField",
    });
  }

  public setErrorMessage(val: string): void {
    let isValChanged: boolean = this.errorMessage !== val;

    if (!isValChanged) {
      return;
    }

    this.errorMessage = val;

    this.fire("errorMessage", this);
  }
  public setIsLoading(val: boolean): void {
    let isValChanged: boolean = this.isLoading !== val;

    if (!isValChanged) {
      return;
    }

    this.isLoading = val;

    this.fire("isLoading", this);
  }
  public setStep(val: number): void {
    let isValChanged: boolean = this.step !== val;

    if (!isValChanged) {
      return;
    }

    this.step = val;

    this.fire("step", this);
  }
  public setMessage(val: string): void {
    let isValChanged: boolean = this.message !== val;

    if (!isValChanged) {
      return;
    }

    this.message = val;

    this.fire("message", this);
  }
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      alignment: ui.Alignment.center,
      child: ui.Container({
        child: ui.Column({
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
              data: "Reset Password",
              textAlign: ui.TextAlign.center,
              className: "x36",
              key: "0",
            }),
            this.renderField(
              "Email",
              this.emailValue,
              (text) => {
                this.emailValue = text;
              },
              { placeHolder: "you@company.com", keyboardType: "emailAddress" }
            ),
            this.message !== null && this.message.isNotEmpty
              ? TextView({ data: this.message, className: "x36", key: "msg" })
              : [],
            this.step >= 2
              ? ui.Column({
                  children: [
                    ui.Container({ className: "xfb5", key: "0" }),
                    this.renderField(
                      "OTP Code",
                      this.otpValue,
                      (text) => {
                        this.otpValue = text;
                      },
                      { placeHolder: "Enter OTP" }
                    ),
                    ui.Container({ className: "xee7", key: "2" }),
                    this.renderField(
                      "New Password",
                      this.newPasswordValue,
                      (text) => {
                        this.newPasswordValue = text;
                      },
                      { placeHolder: "New password", obscureText: true }
                    ),
                  ],
                })
              : [],
            this.errorMessage !== null && this.errorMessage.isNotEmpty
              ? TextView({ data: this.errorMessage, className: "xd8" })
              : [],
            SubmitRefWithState({
              d3eState: this.d3eState,
              _onSubmitHandler: this.onSubmitHandler,
              step: this.step,
              isLoading: this.isLoading,
              key: "3",
            }),
            BackRefWithState({
              d3eState: this.d3eState,
              _onBackHandler: this.onBackHandler,
              key: "4",
            }),
          ],
        }),
        className: "glassCard xb2",
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassCardStyle_PrimaryButtonStyle_FormFieldStyle ForgotPasswordPage pageBackground xa1 ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onSubmitHandler = async (
    d3eState: ForgotPasswordPageRefs
  ): Promise<void> => {
    this.setErrorMessage("");

    if (this.step === 1) {
      if (!this.emailValue || this.emailValue.trim().length === 0) {
        this.setErrorMessage("Please enter your email.");
        return;
      }

      this.setIsLoading(true);

      try {
        const result: LoginResult =
          await Query.get().sendInventoryManagementSystemPasswordResetOtp(
            this.emailValue.trim().toLowerCase()
          );

        if (result.success) {
          this.otpToken = result.token;
          this.setStep(2);
          this.setMessage("OTP sent to " + this.emailValue.trim().toLowerCase());
        } else {
          this.setErrorMessage(result.failureMessage);
        }
      } catch (e) {
        this.setErrorMessage("Failed to send OTP: " + e.toString());
      }

      this.setIsLoading(false);
      return;
    }

    if (!this.otpValue || this.otpValue.trim().length === 0) {
      this.setErrorMessage("Please enter the OTP code.");
      return;
    }

    if (!this.newPasswordValue || this.newPasswordValue.length < 6) {
      this.setErrorMessage("Password must be at least 6 characters.");
      return;
    }

    this.setIsLoading(true);

    try {
      const result: LoginResult =
        await Query.get().resetInventoryManagementSystemPasswordWithOtp(
          this.otpToken,
          this.otpValue.trim(),
          this.newPasswordValue
        );

      if (result.success) {
        this.navigator.pushLoginPage();
      } else {
        this.setErrorMessage(result.failureMessage);
      }
    } catch (e) {
      this.setErrorMessage("Password reset failed: " + e.toString());
    }

    this.setIsLoading(false);
  };
  public onBackHandler = (d3eState: ForgotPasswordPageRefs): void => {
    this.navigator.pushLoginPage();
  };
  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
  public get backRef() {
    return this.d3eState.backRef;
  }
  public get submitRef() {
    return this.d3eState.submitRef;
  }
}
export default function ForgotPasswordPage(props: ForgotPasswordPageProps) {
  return React.createElement(_ForgotPasswordPageState, props);
}
