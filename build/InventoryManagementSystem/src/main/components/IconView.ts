import React, { ReactNode } from "react";
import * as ui from "../native/index";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { processBaseUIProps } from "../native/ui/BaseUIProps";

interface IconViewProps extends BaseUIProps {
  icon?: ui.IconData;
  size?: number;
  color?: ui.Color;
  fill?: boolean;
  textDirection?: ui.TextDirection;
}
class _IconView extends ObservableComponent<IconViewProps> {
  icon: ui.IconData = this.props.icon;
  size: number = this.props.size;
  color: ui.Color = this.props.color;
  textDirection: ui.TextDirection = this.props.textDirection;
  render(): ReactNode {
    var style = {};
    if (this.props.size) {
      style["fontSize"] = this.props.size + "px";
    }
    if (this.props.color) {
      style["color"] = this.props.color.toHexa();
    }
    if (this.props.icon && typeof this.props.fill === "boolean") {
      style["fontVariationSettings"] = `'FILL' ${
        this.props.fill ? 1 : 0
      }, 'wght' 400, 'GRAD' 0, 'opsz' ${this.props.size || 48}`;
    }
    return React.createElement(
      "ui-icon",
      {
        class:
          (this.props.className ? " " + this.props.className : "") +
          " IconView " +
          this.props.icon.fontFamily,
        style,
        ...processBaseUIProps(this.props),
      },
      String.fromCharCode(this.props.icon.codePoint)
    );
  }
}

export default function IconView(props: IconViewProps) {
  return React.createElement(_IconView, {
    ...props,
  });
}
