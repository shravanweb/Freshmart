import React, { ReactNode } from "react";

interface DragTargetProps<T> {
  // TODO
  onWillAccept?: (data: T) => Boolean;
  onAccept?: (data: T) => void;
  onLeave?: () => void;
  builder: (candidateData: Array<T>, rejectedData: Array<any>) => ReactNode;
}

class _DragTarget<T> extends React.Component<DragTargetProps<T>, {}> {
  render() {
    return React.createElement("div", {
      class: "drop-target",
    });
  }
}

export default function DragTarget<T>(props: DragTargetProps<T>) {
  return React.createElement(_DragTarget<T>, props);
}
