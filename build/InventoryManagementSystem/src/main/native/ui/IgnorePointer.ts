import React from "react";
import BaseUIProps from "./BaseUIProps";

interface IgnorePointerProps extends BaseUIProps {
  ignoring?: boolean;
  ignoringSemantics?: boolean;
  child: React.ReactNode;
}

class _IgnorePointer extends React.Component<IgnorePointerProps, {}> {
  render(): React.ReactNode {
    var style: Partial<CSSStyleDeclaration> = {
      pointerEvents: this.props?.ignoring ? "ignore" : "auto",
    };
    return React.createElement(
      "ui-ignore-pointer",
      {
        style: style,
      },
      this.props.child
    );
  }
}

export default function IgnorePointer(props: IgnorePointerProps) {
  return React.createElement(_IgnorePointer, props);
}
