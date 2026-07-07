import React from "react";
import { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import TextButton from "./TextButton";
import TextView from "./TextView";
import { BuildContext } from "../classes/BuildContext";

type _SignupFormWidgetOnSignup = (
  displayName: string,
  email: string,
  password: string,
  confirmPassword: string
) => void;

type _SignupFormWidgetOnGoToLogin = () => void;

export interface SignupFormWidgetProps extends BaseUIProps {
  key?: string;
  displayName?: string;
  email?: string;
  password?: string;
  confirmPassword?: string;
  customerSignup?: boolean;
  errorMessage?: string;
  isLoading?: boolean;
  onSignup?: _SignupFormWidgetOnSignup;
  onGoToLogin?: _SignupFormWidgetOnGoToLogin;
}

class _SignupFormWidgetState extends ObservableComponent<SignupFormWidgetProps> {
  static defaultProps = {
    displayName: "",
    email: "",
    password: "",
    confirmPassword: "",
    customerSignup: false,
    errorMessage: "",
    isLoading: false,
    onSignup: null,
    onGoToLogin: null,
  };

  displayNameValue = "";
  emailValue = "";
  passwordValue = "";
  confirmPasswordValue = "";

  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  public constructor(props: SignupFormWidgetProps) {
    super(props);
    this.initState();
  }

  public get errorMessage(): string {
    return this.props.errorMessage ?? "";
  }

  public get isLoading(): boolean {
    return this.props.isLoading ?? false;
  }

  public initState() {
    super.initState();
    this.displayNameValue = this.props.displayName ?? "";
    this.emailValue = this.props.email ?? "";
    this.passwordValue = this.props.password ?? "";
    this.confirmPasswordValue = this.props.confirmPassword ?? "";
    this.initListeners();
    this.enableBuild = true;
  }

  public initListeners(): void {
    this.on(["errorMessage", "isLoading"], this.rebuild);
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

  public render(): ReactNode {
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
            data: this.props.customerSignup
              ? "Create your FreshMart account"
              : "Create a customer account",
            textAlign: ui.TextAlign.center,
            className: "x89",
            key: "1",
          }),
          TextView({
            data: this.props.customerSignup
              ? "Shop groceries and track orders with FreshMart."
              : "Staff accounts are created by your organization admin.",
            textAlign: ui.TextAlign.center,
            className: "signupHintText",
            key: "1b",
          }),
          this.renderField("Full Name", this.displayNameValue, (text) => {
            this.displayNameValue = text;
          }, { placeHolder: "Your name" }),
          this.renderField("Email", this.emailValue, (text) => {
            this.emailValue = text;
          }, {
            placeHolder: "you@email.com",
            keyboardType: "emailAddress",
          }),
          this.renderField("Password", this.passwordValue, (text) => {
            this.passwordValue = text;
          }, { placeHolder: "Create password", obscureText: true }),
          this.renderField("Confirm Password", this.confirmPasswordValue, (text) => {
            this.confirmPasswordValue = text;
          }, { placeHolder: "Confirm password", obscureText: true }),
          this.errorMessage !== null && this.errorMessage.length > 0
            ? TextView({ data: this.errorMessage, className: "xd8", key: "6" })
            : [],
          ui.Container({
            margin: ui.EdgeInsets.fromLTRB(0.0, 24.0, 0.0, 0.0, new Map()),
            child: TextButton({
              label: "Sign Up",
              disable: this.isLoading,
              onPressed: () => {
                this.onSignup?.(
                  this.displayNameValue,
                  this.emailValue,
                  this.passwordValue,
                  this.confirmPasswordValue
                );
              },
              onFocusChange: () => {},
              className: "primary",
            }),
            className: "primary x5c",
            key: "7",
          }),
          ui.Container({
            margin: ui.EdgeInsets.fromLTRB(0.0, 16.0, 0.0, 0.0, new Map()),
            child: TextButton({
              label: "Already have an account? Sign in",
              onPressed: () => {
                this.onGoToLogin?.();
              },
              onFocusChange: () => {},
            }),
            key: "8",
          }),
        ],
      }),
      className: ui.join(
        "GlassCardStyle_PrimaryButtonStyle_FormFieldStyle SignupFormWidget glassCard ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }

  public get onSignup(): _SignupFormWidgetOnSignup {
    return this.props.onSignup;
  }

  public get onGoToLogin(): _SignupFormWidgetOnGoToLogin {
    return this.props.onGoToLogin;
  }
}

export default function SignupFormWidget(props: SignupFormWidgetProps) {
  return React.createElement(_SignupFormWidgetState, {
    ..._SignupFormWidgetState.defaultProps,
    ...props,
  });
}
