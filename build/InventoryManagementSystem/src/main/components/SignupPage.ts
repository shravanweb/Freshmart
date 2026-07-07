import React from "react";
import { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import SignupFormWidget from "./SignupFormWidget";
import PageNavigator from "../classes/PageNavigator";
import Query from "../classes/Query";
import LoginResult from "../classes/LoginResult";
import User from "../models/User";
import { UsageConstants } from "../rocket/D3ETemplate";
import { BuildContext } from "../classes/BuildContext";
import PostAuthNavigation from "../utils/PostAuthNavigation";

export interface SignupPageProps extends BaseUIProps {
  key?: string;
  customerMode?: boolean;
}

class _SignupPageState extends ObservableComponent<SignupPageProps> {
  displayName = "";
  email = "";
  password = "";
  confirmPassword = "";
  isLoading = false;
  errorMessage = "";

  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  public constructor(props: SignupPageProps) {
    super(props);
    this.initState();
  }

  public initState() {
    super.initState();
    this.initListeners();
    this.enableBuild = true;
  }

  public initListeners(): void {
    this.on(["displayName", "email", "password", "confirmPassword", "errorMessage", "isLoading"], this.rebuild);
  }

  public setErrorMessage(val: string): void {
    if (this.errorMessage === val) return;
    this.errorMessage = val;
    this.fire("errorMessage", this);
  }

  public setIsLoading(val: boolean): void {
    if (this.isLoading === val) return;
    this.isLoading = val;
    this.fire("isLoading", this);
  }

  public render(): ReactNode {
    return ui.Container({
      alignment: ui.Alignment.center,
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.max,
        mainAxisAlignment: ui.MainAxisAlignment.center,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          SignupFormWidget({
            displayName: this.displayName,
            email: this.email,
            password: this.password,
            confirmPassword: this.confirmPassword,
            errorMessage: this.errorMessage,
            isLoading: this.isLoading,
            customerSignup: this.props.customerMode ?? false,
            onSignup: (displayName, email, password, confirmPassword) => {
              this.onSignupHandler(displayName, email, password, confirmPassword);
            },
            onGoToLogin: () => {
              this.navigator.pushLoginPage({
                customerMode: this.props.customerMode ?? false,
              });
            },
            key: "0",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassCardStyle_PrimaryButtonStyle_FormFieldStyle SignupPage pageBackground x54 ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }

  public onSignupHandler = async (
    displayName: string,
    email: string,
    password: string,
    confirmPassword: string
  ): Promise<void> => {
    this.setErrorMessage("");

    if (!displayName || displayName.trim().length === 0) {
      this.setErrorMessage("Please enter your name.");
      return;
    }

    if (!email || email.trim().length === 0) {
      this.setErrorMessage("Please enter your email.");
      return;
    }

    if (!password || password.length < 6) {
      this.setErrorMessage("Password must be at least 6 characters.");
      return;
    }

    if (password !== confirmPassword) {
      this.setErrorMessage("Passwords do not match.");
      return;
    }

    this.setIsLoading(true);

    try {
      const result: LoginResult =
        await Query.get().registerInventoryManagementSystemUser(
          UsageConstants.QUERY_REGISTERINVENTORYMANAGEMENTSYSTEMUSER_SIGNUPPAGE_EVENTHANDLERS_ONSIGNUPHANDLER_BLOCK,
          {
            displayName: displayName.trim(),
            email: email.trim().toLowerCase(),
            password,
            appRole: "Viewer",
          }
        );

      if (result.success) {
        const user = result.userObject as User;

        await PostAuthNavigation.routeAfterAuth(
          this.navigator,
          user,
          result.token,
          {
            displayName: displayName.trim(),
            target: "main",
          }
        );

        return;
      }

      this.setErrorMessage(result.failureMessage);
    } catch (e) {
      this.setErrorMessage("Sign up failed: " + e.toString());
    }

    this.setIsLoading(false);
  };

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function SignupPage(props: SignupPageProps) {
  return React.createElement(_SignupPageState, props);
}
