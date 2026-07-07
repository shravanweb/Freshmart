import React, { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";

interface AbsorbPointerProps extends BaseUIProps {
  absorbing?: boolean;
  ignoringSemantics?: boolean;
  child?: ReactNode;
}

class _AbsorbPointer extends React.Component<AbsorbPointerProps, {}> {
  static defaultProps = {
    absorbing: true,
  };

  private _absorbing;
  private style = {
    pointerEvents: "none" as const,
  };

  constructor(props: AbsorbPointerProps) {
    super(props);
    this.eatEvent = this.eatEvent.bind(this);
    this._absorbing = this.props.absorbing;
  }

  componentDidUpdate() {
    this._absorbing = this.props.absorbing;
  }

  eatEvent(e) {
    if (this._absorbing) {
      e.preventDefault();
    }
  }

  render() {
    return React.createElement(
      "ui-absorb-pointer",
      {
        class:
          "absorb-pointer" +
          (this.props.className ? " " + this.props.className : ""),
        style: this.style,
      },
      this.props.child
    );
  }
}

export default function AbsorbPointer(props: AbsorbPointerProps) {
  return React.createElement(_AbsorbPointer, {
    ..._AbsorbPointer.defaultProps,
    ...props,
  });
}
