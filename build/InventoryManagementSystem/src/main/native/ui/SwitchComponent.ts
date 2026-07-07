import React from "react";
import { Color } from "../classes/Color";
import { DragStartBehavior } from "../classes/DragStartBehavior";
import BaseUIProps from "./BaseUIProps";

interface SwitchComponentProps extends BaseUIProps {
  // TODO
  value: boolean;
  activeColor?: Color;
  activeTrackColor?: Color;
  inactiveThumbColor?: Color;
  inactiveTrackColor?: Color;
  dragStartBehavior?: DragStartBehavior;
  focusColor?: Color;
  hoverColor?: Color;
  autofocus?: boolean;
  onChanged?: (val: boolean) => void;
}
class _SwitchComponent extends React.Component<SwitchComponentProps, {}> {
  static defaultProps = {
    dragStartBehavior: DragStartBehavior.start,
    autofocus: false,
  };
  render() {
    return React.createElement("ui-switch");
  }
}

export default function SwitchComponent(props: SwitchComponentProps) {
  return React.createElement(_SwitchComponent, {
    ..._SwitchComponent.defaultProps,
    ...props,
  });
}
