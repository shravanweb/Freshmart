import React, { Component, ReactNode } from "react";
import PageRouter from "./main/components/PageRouter";
import {
  getHashRoute,
  restoreSessionUser,
  resolveRoutePage,
} from "./main/utils/InitialRouteResolver";

interface AppBootstrapState {
  ready: boolean;
  component: ReactNode;
}

class _AppBootstrapState extends Component<{}, AppBootstrapState> {
  public constructor(props: {}) {
    super(props);
    this.state = {
      ready: false,
      component: null,
    };
  }

  public componentDidMount(): void {
    void this.loadRoute();
    window.addEventListener("hashchange", this.onHashChange);
    window.addEventListener("popstate", this.onHashChange);
  }

  public componentWillUnmount(): void {
    window.removeEventListener("hashchange", this.onHashChange);
    window.removeEventListener("popstate", this.onHashChange);
  }

  private onHashChange = (): void => {
    void this.loadRoute();
  };

  private loadRoute = async (): Promise<void> => {
    try {
      const route = getHashRoute();
      const user = await restoreSessionUser();
      this.setState({
        ready: true,
        component: resolveRoutePage(route, user),
      });
    } catch (error) {
      console.error("Failed to load route", error);
      this.setState({
        ready: true,
        component: resolveRoutePage("", null),
      });
    }
  };

  public render(): ReactNode {
    if (!this.state.ready) {
      return React.createElement(
        "div",
        { className: "appBootstrapLoading", "aria-busy": "true" },
        "Loading..."
      );
    }

    return PageRouter({
      target: "",
      component: this.state.component,
      key: "0",
    });
  }
}

export default function AppBootstrap(props: Record<string, never>) {
  return React.createElement(_AppBootstrapState, props);
}
