import React, { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";

export interface PositionedProps extends BaseUIProps {
  // TODO
  left?: number;
  top?: number;
  right?: number;
  bottom?: number;
  width?: number;
  height?: number;
  child: ReactNode;
}

class _Positioned extends React.Component<PositionedProps, {}> {
  render() {
    let style = {
      top: px(this.props.top),
      left: px(this.props.left),
      right: px(this.props.right),
      bottom: px(this.props.bottom),
      width: px(this.props.width),
      height: px(this.props.height),
    };
    return React.createElement(
      "ui-positioned",
      {
        style,
      },
      this.props.child
    );
  }
}
function px(n: number) {
  return n ? n + "px" : n;
}

export default function Positioned(props: PositionedProps) {
  return React.createElement(_Positioned, props);
}
