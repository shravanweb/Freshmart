import React, { ReactNode } from "react";
interface DraggableProps<T> {
  // TODO
  data?: T;
  child?: ReactNode;
  feedback?: ReactNode;
  childWhenDragging?: ReactNode;
  onDrag?: () => T;
}

class _Draggable<T> extends React.Component<DraggableProps<T>, {}> {
  render() {
    return React.createElement("ui-draggable", {}, [
      this.props.child,
      this.props.feedback,
      this.props.childWhenDragging,
    ]);
  }
}

export default function Draggable<T>(props: DraggableProps<T>) {
  return React.createElement(_Draggable<T>, props);
}
