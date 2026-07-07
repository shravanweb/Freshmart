import React, { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";
interface AspectRatioProps extends BaseUIProps {
  aspectRation: number;
  child?: ReactNode;
}
class _AspectRatio extends React.Component<AspectRatioProps, {}> {
  render() {
    return React.createElement("ui-aspect-ratio", {}, this.props.child);
  }
}

export default function AspectRatio(props: AspectRatioProps) {
  return React.createElement(_AspectRatio, props);
}
