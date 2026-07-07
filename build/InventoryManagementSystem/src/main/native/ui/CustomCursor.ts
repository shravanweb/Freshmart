import React, { ReactNode } from "react";
import MouseRegion, { MouseRegionProps } from "./MouseRegion";
import CursorType from "../../classes/CursorType";
import BaseUIProps from "./BaseUIProps";
interface CustomCursorProps extends MouseRegionProps, BaseUIProps {
  cursorStyle?: CursorType;
  url?: string;
  child?: ReactNode;
}
class _CustomCursor extends React.Component<CustomCursorProps, {}> {
  static defaultProps = {
    cursorStyle: CursorType.click,
  };

  getCursorName() {
    if (!this.props.cursorStyle) {
      return "pointer";
    }
    switch (this.props.cursorStyle) {
      case CursorType.none:
        return "none";
      case CursorType.click:
        return "pointer";
      case CursorType.basic:
        return "default";
      case CursorType.text:
        return "text";
      case CursorType.forbidden:
        return "no-drop";
      case CursorType.cell:
        return "cell";
      case CursorType.precise:
        return "crosshair";
      case CursorType.grab:
        return "grab";
      case CursorType.move:
        return "move";
      case CursorType.noDrop:
        return "no-drop";
      case CursorType.resizeUpRightDownLeft:
        return "nesw-resize";
      case CursorType.alias:
        return "alias";
      case CursorType.copy:
        return "copy";
      case CursorType.allScroll:
        return "all-scroll";
      case CursorType.resizeUpLeftDownRight:
        return "nwse-resize";
      case CursorType.resizeUpDown:
        return "ns-resize";
      case CursorType.resizeLeftRight:
        return "ew-resize";
      case CursorType.resizeRow:
        return "row-resize";
      case CursorType.zoomIn:
        return "zoom-in";
      case CursorType.zoomOut:
        return "zoom-out";
      case CursorType.resizeRight:
        return "e-resize";
      case CursorType.resizeColumn:
        return "col-resize";
      case CursorType.resizeDownRight:
        return "se-resize";
      case CursorType.resizeDownLeft:
        return "sw-resize";
      case CursorType.resizeUpRight:
        return "ne-resize";
      case CursorType.resizeUpLeft:
        return "nw-resize";
      case CursorType.resizeUp:
        return "n-resize";
      case CursorType.resizeLeft:
        return "w-resize";
      case CursorType.resizeDown:
        return "s-resize";
      case CursorType.grabbing:
        return "grabbing";
      case CursorType.wait:
        return "wait";
      case CursorType.progress:
        return "progress";
      case CursorType.contextMenu:
        return "context-menu";
      case CursorType.help:
        return "help";
      case CursorType.verticalText:
        return "vertical-text";
    }
  }

  render() {
    let newProps: any = {
      style: {
        cursor: this.getCursorName(),
      },
    };
    newProps.onMouseOver = (event) => {
      //TODO let style: string = `url("$url"), $cursorStyle`;
      // let root = document.getElementById("root");
      // if (root != null) {
      //   root.style.cursor =
      //     (newProps.url == null ? newProps.cursorStyle.toString() : style) ||
      //     _CustomCursor.pointer;
      // }
    };
    newProps.onMouseEnter = (event) => {
      //TODO let style: string = `url("$url"), $cursorStyle`;
      // let root = document.getElementById("root");
      // if (root != null) {
      //   root.style.cursor =
      //     (newProps.url == null ? newProps.cursorStyle.toString() : style) ||
      //     _CustomCursor.pointer;
      // }
    };
    newProps.onMouseLeave = (event) => {
      //TODO let root = document.getElementById("root");
      // if (root != null) {
      //   root.style.cursor = "default";
      // }
    };
    return React.createElement("div", newProps, this.props.child);
  }
}

export default function CustomCursor(props: CustomCursorProps) {
  return React.createElement(_CustomCursor, {
    ..._CustomCursor.defaultProps,
    ...props,
  });
}
