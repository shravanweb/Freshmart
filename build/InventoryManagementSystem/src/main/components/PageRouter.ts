import React, { Component, ReactNode } from "react";
import PageNavigator from "../classes/PageNavigator";
import BaseUIProps, { processBaseUIProps } from "../native/ui/BaseUIProps";

interface PageRouterProps extends BaseUIProps {
  target: string;
  component?: ReactNode;
}

class _PageRouter extends Component<PageRouterProps, any> {
  constructor(props: PageRouterProps) {
    super(props);
    this.state = {
      component: this.props.component,
    };
    PageNavigator.addListener(this.props.target, (c) => {
      this.setState({ component: c });
    });
  }

  componentDidUpdate(prevProps: PageRouterProps): void {
    if (prevProps.component !== this.props.component) {
      this.setState({ component: this.props.component });
    }
  }

  render() {
    return React.createElement(
      "ui-page-router",
      {
        key: this.props.target,
        ...processBaseUIProps(this.props),
      },
      this.state.component
    );
  }
}

export default function PageRouter(props: PageRouterProps) {
  return React.createElement(_PageRouter, props);
}
