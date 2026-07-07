import React, { ReactNode } from "react";
import { Offset } from "../classes/Offset";
import BaseUIProps from "./BaseUIProps";
import { PointerEnterEvent } from "../classes/PointerEnterEvent";
import { PointerExitEvent } from "../classes/PointerExitEvent";
import { PointerHoverEvent } from "../classes/PointerHoverEvent";

export interface MouseRegionProps extends BaseUIProps {
  opaque?: boolean;
  child?: ReactNode;
}

class _MouseRegion extends React.Component<MouseRegionProps, {}> {
  static defaultProps = {
    opaque: true,
  };
  constructor(props: MouseRegionProps) {
    super(props);
    this.handleEnter = this.handleEnter.bind(this);
    this.handleExit = this.handleExit.bind(this);
    this.handleHover = this.handleHover.bind(this);
  }

  handleEnter(event) {
    let e: PointerEnterEvent = new PointerEnterEvent({
      position: new Offset({
        dx: event.clientX,
        dy: event.clientY,
      }),
    });
    this.props.onEnter?.(e);
  }

  handleExit(event) {
    let e: PointerExitEvent = new PointerExitEvent({
      position: new Offset({
        dx: event.clientX,
        dy: event.clientY,
      }),
    });
    this.props.onExit?.(e);
  }

  handleHover(event) {
    let e: PointerHoverEvent = new PointerHoverEvent({
      position: new Offset({
        dx: event.clientX,
        dy: event.clientY,
      }),
    });
    this.props.onExit?.(e);
  }

  render() {
    return React.createElement(
      "ui-mouse-region",
      {
        class: "mouse-region",
        onMouseEnter: this.handleEnter,
        onMouseLeave: this.handleExit,
        onMouseOver: this.handleHover,
      },
      this.props.child
    );
  }
}

export default function MouseRegion(props: MouseRegionProps) {
  return React.createElement(_MouseRegion, {
    ..._MouseRegion.defaultProps,
    ...props,
  });
}
