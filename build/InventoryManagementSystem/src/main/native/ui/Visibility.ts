import React, { ReactNode } from "react";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface VisibilityProps extends BaseUIProps {
  replacement?: ReactNode;
  visible?: boolean;
  maintainState?: boolean;
  maintainAnimation?: boolean;
  maintainSize?: boolean;
  maintainSemantics?: boolean;
  maintainInteractivity?: boolean;
  child: ReactNode;
}

class _Visibility extends React.Component<VisibilityProps, {}> {
  static defaultProps = {
    visible: true,
    maintainState: false,
    maintainAnimation: false,
    maintainSize: false,
    maintainSemantics: false,
    maintainInteractivity: false,
  };
  render() {
    let style: any = {};
    if (this.props.maintainState && !this.props.visible) {
      style.display = "none";
    }
    return React.createElement(
      "ui-container",
      {
        class: "Container " + this.props.className,
        style,
        ...processBaseUIProps(this.props),
      },
      this.props.child
    );
  }
}

export default function Visibility(props: VisibilityProps) {
  return React.createElement(_Visibility, {
    ..._Visibility.defaultProps,
    ...props,
  });
}
