import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import IMSInputFieldWidget from "./IMSInputFieldWidget";
import TextButton from "./TextButton";
import TextView from "./TextView";
import { BuildContext } from "../classes/BuildContext";

type _OtpLoginWidgetOnSendOtp = (email: string) => void;

type _OtpLoginWidgetOnVerifyOtp = (email: string, otp: string) => void;

type _SendOtpRefOnPressed = (d3eState: OtpLoginWidgetRefs) => void;

type _VerifyRefOnPressed = (d3eState: OtpLoginWidgetRefs) => void;

export interface OtpLoginWidgetProps extends BaseUIProps {
  key?: string;
  email: string;
  onSendOtp?: _OtpLoginWidgetOnSendOtp;
  onVerifyOtp?: _OtpLoginWidgetOnVerifyOtp;
}
/// To store state data for OtpLoginWidget
class OtpLoginWidgetRefs {
  public sendOtpRef: SendOtpRefState = new SendOtpRefState();
  public verifyRef: VerifyRefState = new VerifyRefState();
}

interface VerifyRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: OtpLoginWidgetRefs;
  _onVerifyPressedHandler?: _VerifyRefOnPressed;
  isLoading: boolean;
}

class VerifyRefState extends ObjectObservable {}

class _VerifyRefWithState extends ObservableComponent<VerifyRefWithStateProps> {
  verifyRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: VerifyRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get isLoading(): boolean {
    return this.props.isLoading;
  }
  public get verifyRef(): VerifyRefState {
    return this.props.d3eState.verifyRef;
  }
  public get d3eState(): OtpLoginWidgetRefs {
    return this.props.d3eState;
  }
  public get _onVerifyPressedHandler(): _VerifyRefOnPressed {
    return this.props._onVerifyPressedHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("verifyRef", null, this.verifyRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["isLoading", "verifyRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 12.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: "Verify OTP",
        disable: this.isLoading,
        onPressed: () => {
          this._onVerifyPressedHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "primary",
        key: "0",
      }),
      className: "primary x8d8",
    });
  }
}
function VerifyRefWithState(props: VerifyRefWithStateProps) {
  return React.createElement(_VerifyRefWithState, props);
}

interface SendOtpRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: OtpLoginWidgetRefs;
  _onSendOtpPressedHandler?: _SendOtpRefOnPressed;
  isLoading: boolean;
  otpSent: boolean;
}

class SendOtpRefState extends ObjectObservable {}

class _SendOtpRefWithState extends ObservableComponent<SendOtpRefWithStateProps> {
  sendOtpRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: SendOtpRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get isLoading(): boolean {
    return this.props.isLoading;
  }
  public get otpSent(): boolean {
    return this.props.otpSent;
  }
  public get sendOtpRef(): SendOtpRefState {
    return this.props.d3eState.sendOtpRef;
  }
  public get d3eState(): OtpLoginWidgetRefs {
    return this.props.d3eState;
  }
  public get _onSendOtpPressedHandler(): _SendOtpRefOnPressed {
    return this.props._onSendOtpPressedHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("sendOtpRef", null, this.sendOtpRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["isLoading", "otpSent", "sendOtpRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 24.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: this.otpSent ? "Resend OTP" : "Send OTP",
        disable: this.isLoading,
        onPressed: () => {
          this._onSendOtpPressedHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "primary",
        key: "0",
      }),
      className: "primary xef5",
    });
  }
}
function SendOtpRefWithState(props: SendOtpRefWithStateProps) {
  return React.createElement(_SendOtpRefWithState, props);
}

class _OtpLoginWidgetState extends ObservableComponent<OtpLoginWidgetProps> {
  static defaultProps = { email: "", onSendOtp: null, onVerifyOtp: null };
  d3eState: OtpLoginWidgetRefs = new OtpLoginWidgetRefs();
  otp: string = "";
  otpSent: boolean = false;
  isLoading: boolean = false;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: OtpLoginWidgetProps) {
    super(props);

    this.initState();
  }
  public get email(): string {
    return this.props.email;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(
      ["email", "isLoading", "otp", "otpSent", "sendOtpRef", "verifyRef"],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: OtpLoginWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.email !== this.props.email) {
      this.fire("email", this);
    }
  }
  public setOtp(val: string): void {
    let isValChanged: boolean = this.otp !== val;

    if (!isValChanged) {
      return;
    }

    this.otp = val;

    this.fire("otp", this);
  }
  public setOtpSent(val: boolean): void {
    let isValChanged: boolean = this.otpSent !== val;

    if (!isValChanged) {
      return;
    }

    this.otpSent = val;

    this.fire("otpSent", this);
  }
  public setIsLoading(val: boolean): void {
    let isValChanged: boolean = this.isLoading !== val;

    if (!isValChanged) {
      return;
    }

    this.isLoading = val;

    this.fire("isLoading", this);
  }
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({
            data: "OTP Login",
            textAlign: ui.TextAlign.center,
            className: "x5f",
            key: "0",
          }),
          IMSInputFieldWidget({
            label: "Email",
            text: this.email,
            placeHolder: "you@company.com",
            keyboardType: "emailAddress",
            key: "1",
          }),
          this.otpSent
            ? ui.Column({
                children: [
                  ui.Container({ className: "x564", key: "0" }),
                  IMSInputFieldWidget({
                    label: "One-time password",
                    text: this.otp,
                    placeHolder: "Enter OTP",
                    key: "1",
                  }),
                ],
              })
            : [],
          SendOtpRefWithState({
            d3eState: this.d3eState,
            _onSendOtpPressedHandler: this.onSendOtpPressedHandler,
            isLoading: this.isLoading,
            otpSent: this.otpSent,
            key: "3",
          }),
          this.otpSent
            ? VerifyRefWithState({
                d3eState: this.d3eState,
                _onVerifyPressedHandler: this.onVerifyPressedHandler,
                isLoading: this.isLoading,
              })
            : [],
        ],
      }),
      className: ui.join(
        "GlassCardStyle_PrimaryButtonStyle_FormFieldStyle OtpLoginWidget glassCard ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onSendOtpPressedHandler = (d3eState: OtpLoginWidgetRefs): void => {
    this.onSendOtp(this.email);

    this.setOtpSent(true);
  };
  public onVerifyPressedHandler = (d3eState: OtpLoginWidgetRefs): void => {
    this.onVerifyOtp(this.email, this.otp);
  };
  public get onSendOtp(): _OtpLoginWidgetOnSendOtp {
    return this.props.onSendOtp;
  }
  public get onVerifyOtp(): _OtpLoginWidgetOnVerifyOtp {
    return this.props.onVerifyOtp;
  }
  public get sendOtpRef() {
    return this.d3eState.sendOtpRef;
  }
  public get verifyRef() {
    return this.d3eState.verifyRef;
  }
}
export default function OtpLoginWidget(props: OtpLoginWidgetProps) {
  return React.createElement(_OtpLoginWidgetState, {
    ..._OtpLoginWidgetState.defaultProps,
    ...props,
  });
}
