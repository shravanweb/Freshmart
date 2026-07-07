import React, { ReactNode } from "react";
import { CanvasController } from "../classes/CanvasController";
import VoidCallback from "../../classes/core";
import BaseUIProps from "./BaseUIProps";

interface CanvasWidgetProps extends BaseUIProps {
  controller?: CanvasController;
  child?: ReactNode;
  onRepaint?: VoidCallback;
}

class _CanvasWidget extends React.Component<CanvasWidgetProps> {
  ref: HTMLCanvasElement;

  constructor(props: CanvasWidgetProps) {
    super(props);
    this.handleRef = this.handleRef.bind(this);
    this.props.controller?.connect(this);
  }

  handleRef(e: HTMLCanvasElement) {
    this.ref = e;
  }

  repaint() {
    // Trigger re-render. Not sure if best way.
    this.setState({});
    this.props.onRepaint?.();
  }

  render() {
    return React.createElement(
      "canvas",
      {
        ref: this.handleRef,
      },
      this.props.child
    );
  }
}

export default function CanvasWidget(props: CanvasWidgetProps) {
  return React.createElement(_CanvasWidget, props);
}
