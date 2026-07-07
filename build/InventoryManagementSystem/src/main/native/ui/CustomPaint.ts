import React, { ReactNode } from "react";
import { HSVColorPainter } from "../classes/HSVColorPainter";
import BaseUIProps from "./BaseUIProps";

interface CustomPaintProps extends BaseUIProps {
  painter?: HSVColorPainter;
}

class _CustomPaint extends React.Component<CustomPaintProps, {}> {
  render(): React.ReactNode {
    return React.createElement("ui-custom-paint", { class: "custom-paint" });
  }
}

export default function CustomPaint(props: CustomPaintProps): ReactNode {
  return React.createElement(_CustomPaint, props);
}
