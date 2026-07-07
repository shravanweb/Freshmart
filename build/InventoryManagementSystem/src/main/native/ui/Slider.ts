import React from "react";
import { Color } from "../classes/Color";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface SliderProps extends BaseUIProps {
  value: number;
  min?: number;
  max?: number;
  divisions?: number;
  label?: string;
  activeColor?: Color;
  inactiveColor?: Color;
  onChanged?: (val: Number) => void;
  onChangeStart?: (val: Number) => void;
  onChangeEnd?: (val: Number) => void;
}
class _Slider extends React.Component<SliderProps, {}> {
  static defaultProps = {
    min: 0.0,
    max: 1.0,
  };
  render() {
    return React.createElement("ui-slider", {
      class:
        "Slider " + (this.props.className ? " " + this.props.className : ""),
      ...processBaseUIProps(this.props),
    });
  }
}

export default function Slider(props: SliderProps) {
  return React.createElement(_Slider, { ..._Slider.defaultProps, ...props });
}
