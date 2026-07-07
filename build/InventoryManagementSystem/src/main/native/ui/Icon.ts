import React, { ReactNode } from "react";
import { Color, IconData, TextDirection } from "../index";
import BaseUIProps from "./BaseUIProps";

interface IconProps extends BaseUIProps {
  icon: IconData;
  size: number;
  color: Color;
  textDirection: TextDirection;
}

class _Icon extends React.Component<IconProps, {}> {
  static defaultProps = {
    size: 24,
  };
  render() {
    return React.createElement(
      "i",
      {
        class:
          this.props.icon.fontFamily +
          (this.props.className ? " " + this.props.className : ""),
        style: {
          fontSize: this.props.size,
        },
      },
      String.fromCharCode(this.props.icon.codePoint)
    );
  }
}

export default function Icon(props: IconProps): ReactNode {
  return React.createElement(_Icon, { ..._Icon.defaultProps, ...props });
}
