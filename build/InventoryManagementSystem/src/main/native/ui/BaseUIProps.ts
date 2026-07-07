import { ReactNode } from "react";
import { DragDownDetails } from "../classes/DragDownDetails";
import { DragEndDetails } from "../classes/DragEndDetails";
import { DragStartDetails } from "../classes/DragStartDetails";
import { DragUpdateDetails } from "../classes/DragUpdateDetails";
import { ForcePressDetails } from "../classes/ForcePressDetails";
import { LongPressEndDetails } from "../classes/LongPressEndDetails";
import { LongPressMoveUpdateDetails } from "../classes/LongPressMoveUpdateDetails";
import { LongPressStartDetails } from "../classes/LongPressStartDetails";
import { ScaleEndDetails } from "../classes/ScaleEndDetails";
import { ScaleStartDetails } from "../classes/ScaleStartDetails";
import { ScaleUpdateDetails } from "../classes/ScaleUpdateDetails";
import { TapDownDetails } from "../classes/TapDownDetails";
import { TapUpDetails } from "../classes/TapUpDetails";
import { PointerExitEvent } from "../classes/PointerExitEvent";
import { PointerHoverEvent } from "../classes/PointerHoverEvent";
import { PointerCancelEvent } from "../classes/PointerCancelEvent";
import { PointerDownEvent } from "../classes/PointerDownEvent";
import { PointerMoveEvent } from "../classes/PointerMoveEvent";
import { PointerSignalEvent } from "../classes/PointerSignalEvent";
import { PointerUpEvent } from "../classes/PointerUpEvent";
import { FocusNode } from "../classes/FocusNode";
import { MouseEvent as ReactMouseEvent } from "react";

export default interface BaseUIProps {
  key?: string;
  className?: string;
  states?: {};
  focusNode?: FocusNode;
  d3eRef?: (e: HTMLElement) => void;
  onDoubleTap?: () => void;
  onTapDown?: (
    details: TapDownDetails,
    event: ReactMouseEvent<HTMLElement>
  ) => void;
  onTapUp?: (details: TapUpDetails) => void;
  onTap?: (event: ReactMouseEvent<HTMLElement>) => void;
  onTapCancel?: () => void;
  onSecondaryTapDown?: (details: TapDownDetails) => void;
  onSecondaryTapUp?: (details: TapUpDetails) => void;
  onLongPress?: () => void;
  onLongPressStart?: (details: LongPressStartDetails) => void;
  onLongPressMoveUpdate?: (details: LongPressMoveUpdateDetails) => void;
  onLongPressUp?: () => void;
  onLongPressEnd?: (details: LongPressEndDetails) => void;
  onVerticalDragDown?: (details: DragDownDetails) => void;
  onVerticalDragStart?: (details: DragStartDetails) => void;
  onVerticalDragUpdate?: (details: DragUpdateDetails) => void;
  onVerticalDragEnd?: (details: DragEndDetails) => void;
  onVerticalDragCancel?: () => void;
  onHorizontalDragDown?: (details: DragDownDetails) => void;
  onHorizontalDragStart?: (details: DragStartDetails) => void;
  onHorizontalDragUpdate?: (details: DragUpdateDetails) => void;
  onHorizontalDragEnd?: (details: DragEndDetails) => void;
  onHorizontalDragCancel?: () => void;
  onForcePressStart?: (details: ForcePressDetails) => void;
  onForcePressPeak?: (details: ForcePressDetails) => void;
  onForcePressUpdate?: (details: ForcePressDetails) => void;
  onForcePressEnd?: (details: ForcePressDetails) => void;
  onPanDown?: (
    details: DragDownDetails,
    event: ReactMouseEvent<HTMLElement>
  ) => void;
  onPanStart?: (details: DragStartDetails) => void;
  onPanUpdate?: (details: DragUpdateDetails) => void;
  onPanEnd?: (details: DragEndDetails) => void;
  onPanCancel?: () => void;
  onScaleStart?: (details: ScaleStartDetails) => void;
  onScaleUpdate?: (details: ScaleUpdateDetails) => void;
  onScaleEnd?: (details: ScaleEndDetails) => void;
  onEnter?: any;
  onExit?: (event: PointerExitEvent) => void;
  onHover?: (event: PointerHoverEvent) => void;
  onKey?: any;
  onFocusChange?: (val: boolean) => void;
  onPointerDown?: (
    details: PointerDownEvent,
    event: ReactMouseEvent<HTMLElement>
  ) => void;
  onPointerMove?: (event: PointerMoveEvent) => void;
  onPointerUp?: (event: PointerUpEvent) => void;
  onPointerCancel?: (event: PointerCancelEvent) => void;
  onPointerSignal?: (event: PointerSignalEvent) => void;
  listener?: (index: number, listener: (node: ReactNode) => void) => void;
  onFocusKey?: (focusNode: FocusNode, event: any) => void;
}

export function processBaseUIProps(props: BaseUIProps, className?: String) {
  let x = {
    ref: props.d3eRef,
    onDoubleClick: props.onDoubleTap,
    onKeyDown: props.onTapDown,
    onKeyUp: props.onTapUp,
    onClick: props.onTap,
    onKeyPress: props.onKey,
    onPointerDown: props.onPointerDown,
    onPointerMove: props.onPointerMove,
    onPointerUp: props.onPointerUp,
    onPointerCancel: props.onPointerCancel,
    onDragStart: props.onPanStart,
    onDragOver: props.onPanUpdate,
    onDragEnd: props.onPanEnd,
    onDragLeave: props.onPanCancel,
    //onFocus: props.onFocusChange,

    // onVerticalDragDown: props.onVerticalDragDown,
    // onVerticalDragStart: props.onVerticalDragStart,
    // onVerticalDragUpdate: props.onVerticalDragUpdate,
    // onVerticalDragEnd: props.onVerticalDragEnd,
    // onVerticalDragCancel: props.onVerticalDragCancel,
    // onHorizontalDragDown: props.onHorizontalDragDown,
    // onHorizontalDragStart: props.onHorizontalDragStart,
    // onHorizontalDragUpdate: props.onHorizontalDragUpdate,
    // onHorizontalDragEnd: props.onHorizontalDragEnd,
    // onHorizontalDragCancel: props.onHorizontalDragCancel,
    // onForcePressStart: props.onForcePressStart,
    // onForcePressPeak: props.onForcePressPeak,
    // onForcePressUpdate: props.onForcePressUpdate,
    // onForcePressEnd: props.onForcePressEnd,
    // onPanDown: props.onPanDown,
    // onScaleStart: props.onScaleStart,
    // onScaleUpdate: props.onScaleUpdate,
    // onScaleEnd: props.onScaleEnd,
    onMouseEnter: props.onEnter,
    onMouseOver: props.onHover,
    onMouseLeave: props.onExit,
    //onPointerSignal: props.onPointerSignal,
  };
  if (props.states) {
    for (const [key, value] of Object.entries(props.states)) {
      if (key == "data-visibility" && !value) {
        x["data-hide"] = value;
      } else if (value) {
        x[key] = value;
      }
    }
  }
  return x;
}

export function copyBaseUIProps(props: BaseUIProps, className?: String) {
  var res: BaseUIProps = {};
  if (props.d3eRef) {
    res.d3eRef = props.d3eRef;
  }
  if (props.onTap) {
    //res.onTap = props.onTap;
    res.onTap = (k) => {
      k.stopPropagation();
      props.onTap(k);
    };
  }
  if (props.onDoubleTap) {
    res.onDoubleTap = props.onDoubleTap;
  }
  if (props.onTapDown) {
    res.onTapDown = (j, k) => {
      k.stopPropagation();
      props.onTapDown(j, k);
    };
  }
  if (props.onTapUp) {
    res.onTapUp = props.onTapUp;
  }
  if (props.onKey) {
    res.onKey = props.onKey;
  }
  if (props.onPointerDown) {
    res.onPointerDown = (k, j) => {
      j.stopPropagation();
      props.onPointerDown(k, j);
    };
  }
  if (props.onPointerMove) {
    res.onPointerMove = props.onPointerMove;
  }
  if (props.onPointerUp) {
    res.onPointerUp = props.onPointerUp;
  }
  if (props.onPointerCancel) {
    res.onPointerCancel = props.onPointerCancel;
  }
  if (props.onPanStart) {
    res.onPanStart = props.onPanStart;
  }
  if (props.onPanUpdate) {
    res.onPanUpdate = props.onPanUpdate;
  }
  if (props.onPanEnd) {
    res.onPanEnd = props.onPanEnd;
  }
  if (props.onPanCancel) {
    res.onPanCancel = props.onPanCancel;
  }
  if (props.onFocusChange) {
    res.onFocusChange = props.onFocusChange;
  }
  if (props.listener) {
    res.listener = props.listener;
  }
  if (props.onFocusKey) {
    res.onFocusKey = props.onFocusKey;
  }
  if (props.onEnter) {
    res.onEnter = props.onEnter;
  }
  if (props.onExit) {
    res.onExit = props.onExit;
  }
  if (props.onHover) {
    res.onHover = props.onHover;
  }
  if (props.states) {
    res.states = props.states;
  }
  return res;
}
