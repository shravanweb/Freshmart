import React, { ReactNode } from "react";
import { HitTestBehavior } from "../classes/HitTestBehavior";

import BaseUIProps from "./BaseUIProps";

interface ListenerProps extends BaseUIProps {
  behavior?: HitTestBehavior; // TODO
  child?: ReactNode;
  // TODO
}

class _Listener extends React.Component<ListenerProps, {}> {
  static defaultProps = {
    behavior: HitTestBehavior.deferToChild,
  };
  render() {
    return React.createElement(
      "ui-listener",
      {
        onPointerDown: this.props.onPointerDown,
        onPointerMove: this.props.onPointerMove,
        onPointerUp: this.props.onPointerUp,
        onPointerCancel: this.props.onPointerCancel,
      },
      this.props.child
    );
  }
}

export default function Listener(props: ListenerProps) {
  return React.createElement(_Listener, {
    ..._Listener.defaultProps,
    ...props,
  });
}
