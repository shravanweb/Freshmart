import React, { ReactNode } from "react";
import { BorderRadius } from "../classes/BorderRadius";
import BaseUIProps from "./BaseUIProps";

interface ClipRRectProps extends BaseUIProps {
  borderRadius: BorderRadius;
  child: ReactNode;
}

class _ClipRRect extends React.Component<ClipRRectProps, {}> {
  render() {
    const { borderRadius } = this.props;

    const borderRadiusValues = [
      borderRadius.topLeft.x + "px",
      borderRadius.topRight.x + "px",
      borderRadius.bottomRight.x + "px",
      borderRadius.bottomLeft.x + "px",
    ].join(" ");

    const style: React.CSSProperties = {
      clipPath: `inset(0 round ${borderRadiusValues})`,
    };
    return React.createElement(
      "ui-clip-r-rect",
      {
        style,
        ...this.props,
      },
      this.props.child
    );
  }
}

export default function ClipRRect(props: ClipRRectProps) {
  return React.createElement(_ClipRRect, props);
}
