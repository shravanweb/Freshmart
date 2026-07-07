import React, { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";

interface OpacityProps extends BaseUIProps {
  opacity: number;
  child: ReactNode;
  alwaysIncludeSemantics: boolean;
}

class _Opacity extends React.Component<OpacityProps, {}> {
  static defaultProps = {
    opacity: 1.0,
  };
  render() {
    return React.createElement(
      "ui-opacity",
      {
        style: {
          opacity: this.props.opacity,
        },
      },
      this.props.child
    );
  }
}

export default function Opacity(props: OpacityProps) {
  return React.createElement(_Opacity, { ..._Opacity.defaultProps, ...props });
}
