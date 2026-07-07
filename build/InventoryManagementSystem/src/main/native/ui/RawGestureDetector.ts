import React, { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";

interface RawGestureDetectorProps extends BaseUIProps {
  gestures: any;
  child: ReactNode;
}

class _RawGestureDetector extends React.Component<RawGestureDetectorProps, {}> {
  render(): React.ReactNode {
    return React.createElement("div", {
      class: "raw-gesture-detector",
    });
  }
}

export default function RawGestureDetector(
  props: RawGestureDetectorProps
): ReactNode {
  return React.createElement(_RawGestureDetector, props);
}
