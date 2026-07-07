import React, { ReactNode } from "react";
import { MouseEvent as ReactMouseEvent } from "react";
import { DragEndDetails } from "../classes/DragEndDetails";
import { DragStartBehavior } from "../classes/DragStartBehavior";
import { DragUpdateDetails } from "../classes/DragUpdateDetails";
import { HitTestBehavior } from "../classes/HitTestBehavior";
import { Offset } from "../classes/Offset";

import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface GestureDetectorProps extends BaseUIProps {
  // TODO all other events
  behavior?: HitTestBehavior;
  excludeFromSemantics?: boolean;
  dragStartBehavior?: DragStartBehavior;
  child?: ReactNode;
}
class _GestureDetector extends React.Component<GestureDetectorProps> {
  static defaultProps = {
    excludeFromSemantics: false,
    dragStartBehavior: DragStartBehavior.start,
  };
  static readonly LONG_PRESS_DURATION_MS = 500;

  private mouseDown: boolean = false;

  constructor(props: GestureDetectorProps) {
    super(props);
    this.handleDoubleClick = this.handleDoubleClick.bind(this);
    this.handleDrag = this.handleDrag.bind(this);
    this.handleDragEnd = this.handleDragEnd.bind(this);
    this.handleTap = this.handleTap.bind(this);
    this.registerMouseDown = this.registerMouseDown.bind(this);
  }

  handleDoubleClick(event: MouseEvent) {
    event.preventDefault();
    this.props.onDoubleTap?.();
  }

  handleTap(event: ReactMouseEvent<HTMLElement>) {
    event.preventDefault();
    this.mouseDown = false;
    if (this.props.onTap) {
      event.stopPropagation();
      this.props.onTap(event);
    }
  }

  registerMouseDown(event: MouseEvent) {
    this.mouseDown = true;
    setTimeout(
      () => {
        this.handleLongPress(event);
      },
      _GestureDetector.LONG_PRESS_DURATION_MS,
      event
    );
  }

  handleLongPress(event: MouseEvent) {
    if (!this.mouseDown) {
      // Not sure when this case would occur, but just a precaution.
      return;
    }
    event.preventDefault();
    this.props.onLongPress?.();
  }

  handleDrag(event) {
    if (event.movementX === 0 && event.movementY !== 0) {
      // No horizontal movement, but vertical movement. This is drag up.
      let drag: DragUpdateDetails = new DragUpdateDetails({
        localPosition: new Offset({
          dx: event.offsetX,
          dy: event.offsetY,
        }),
        delta: new Offset({
          dx: event.movementX,
          dy: event.movementY,
        }),
        globalPosition: new Offset({
          dx: event.screenX,
          dy: event.screenY,
        }),
      });
      event.preventDefault();
      this.props.onVerticalDragUpdate?.(drag);
    }
  }

  handleDragEnd(event) {
    // TODO
    event.preventDefault();
    this.props.onVerticalDragEnd?.(new DragEndDetails());
  }

  render() {
    return React.createElement(
      "ui-detector",
      {
        class: this.props.className,
        onDoubleClick: this.handleDoubleClick,
        onClick: this.handleTap,
        onMouseDown: this.registerMouseDown,
        onDrag: this.handleDrag,
        onDragEnd: this.handleDragEnd,
        ...processBaseUIProps(this.props),
      },
      this.props.child
    );
  }
}

export default function GestureDetector(props: GestureDetectorProps) {
  return React.createElement(_GestureDetector, props);
}
