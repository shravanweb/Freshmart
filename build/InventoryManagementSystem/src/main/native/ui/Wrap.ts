import React, { ReactNode } from "react";

import { Axis } from "../classes/Axis";
import { Clip } from "../classes/Clip";
import { TextDirection } from "../classes/TextDirection";
import { VerticalDirection } from "../classes/VerticalDirection";
import { WrapAlignment } from "../classes/WrapAlignment";
import { WrapCrossAlignment } from "../classes/WrapCrossAlignment";

import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface WrapProps extends BaseUIProps {
  direction?: Axis;

  alignment?: WrapAlignment;

  spacing?: number;

  runAlignment?: WrapAlignment;

  runSpacing?: number;

  crossAxisAlignment?: WrapCrossAlignment;

  textDirection?: TextDirection;

  verticalDirection?: VerticalDirection;

  children?: ReactNode | ReactNode[];
}

class _Wrap extends React.Component<WrapProps> {
  static defaultProps: Partial<WrapProps> = {
    direction: Axis.horizontal,

    alignment: WrapAlignment.start,

    spacing: 0,

    runAlignment: WrapAlignment.start,

    runSpacing: 0,

    crossAxisAlignment: WrapCrossAlignment.start,

    verticalDirection: VerticalDirection.down,
  };

  render() {
    const style: React.CSSProperties = {
      display: "flex",
      flexWrap: "wrap",
    };

    const classNames: string[] = ["Wrap"];

    if (this.props.className) {
      classNames.push(this.props.className);
    }

    /* =====================================================
       DIRECTION
       Only apply inline style when explicitly passed.
       Otherwise utility styles can handle it.
    ===================================================== */

    if (this.props.direction !== undefined) {
      switch (this.props.direction) {
        case Axis.horizontal:
          style.flexDirection = "row";
          break;

        case Axis.vertical:
          style.flexDirection = "column";
          break;
      }
    }

    /* =====================================================
       ALIGNMENT
    ===================================================== */

    if (this.props.alignment !== undefined) {
      switch (this.props.alignment) {
        case WrapAlignment.start:
          style.justifyContent = "flex-start";
          break;

        case WrapAlignment.end:
          style.justifyContent = "flex-end";
          break;

        case WrapAlignment.center:
          style.justifyContent = "center";
          break;

        case WrapAlignment.spaceBetween:
          style.justifyContent = "space-between";
          break;

        case WrapAlignment.spaceAround:
          style.justifyContent = "space-around";
          break;

        case WrapAlignment.spaceEvenly:
          style.justifyContent = "space-evenly";
          break;
      }
    }

    /* =====================================================
       RUN ALIGNMENT
    ===================================================== */

    if (this.props.runAlignment !== undefined) {
      switch (this.props.runAlignment) {
        case WrapAlignment.start:
          style.alignContent = "flex-start";
          break;

        case WrapAlignment.end:
          style.alignContent = "flex-end";
          break;

        case WrapAlignment.center:
          style.alignContent = "center";
          break;

        case WrapAlignment.spaceBetween:
          style.alignContent = "space-between";
          break;

        case WrapAlignment.spaceAround:
          style.alignContent = "space-around";
          break;

        case WrapAlignment.spaceEvenly:
          style.alignContent = "space-evenly";
          break;
      }
    }

    /* =====================================================
       CROSS AXIS ALIGNMENT
    ===================================================== */

    if (this.props.crossAxisAlignment !== undefined) {
      switch (this.props.crossAxisAlignment) {
        case WrapCrossAlignment.start:
          style.alignItems = "flex-start";
          break;

        case WrapCrossAlignment.end:
          style.alignItems = "flex-end";
          break;

        case WrapCrossAlignment.center:
          style.alignItems = "center";
          break;
      }
    }

    /* =====================================================
       SPACING
       Props override utility classes naturally.
    ===================================================== */

    if (this.props.spacing !== undefined && this.props.spacing !== 0) {
      if (this.props.direction === Axis.vertical) {
        style.rowGap = this.props.spacing;
      } else {
        style.columnGap = this.props.spacing;
      }
    }

    if (this.props.runSpacing !== undefined && this.props.runSpacing !== 0) {
      if (this.props.direction === Axis.vertical) {
        style.columnGap = this.props.runSpacing;
      } else {
        style.rowGap = this.props.runSpacing;
      }
    }

    /* =====================================================
       TEXT DIRECTION
    ===================================================== */

    if (this.props.textDirection !== undefined) {
      switch (this.props.textDirection) {
        case TextDirection.ltr:
          style.direction = "ltr";
          break;

        case TextDirection.rtl:
          style.direction = "rtl";
          break;
      }
    }

    /* =====================================================
       VERTICAL DIRECTION
    ===================================================== */

    if (this.props.verticalDirection !== undefined) {
      switch (this.props.verticalDirection) {
        case VerticalDirection.down:
          style.flexWrap = "wrap";
          break;

        case VerticalDirection.up:
          style.flexWrap = "wrap-reverse";
          break;
      }
    }

    return React.createElement(
      "ui-wrap",
      {
        class: classNames.join(" "),

        style,

        ...processBaseUIProps(this.props),
      },

      this.props.children
    );
  }
}

export default function Wrap(props: WrapProps) {
  return React.createElement(_Wrap, {
    ..._Wrap.defaultProps,
    ...props,
  });
}
