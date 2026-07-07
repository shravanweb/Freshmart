import React, { ReactNode } from "react";

import { CrossAxisAlignment } from "../classes/CrossAxisAlignment";
import CSSHelper from "../classes/CSSHelper";
import { MainAxisAlignment } from "../classes/MainAxisAlignment";
import { MainAxisSize } from "../classes/MainAxisSize";
import { TextBaseline } from "../classes/TextBaseline";
import { TextDirection } from "../classes/TextDirection";
import { VerticalDirection } from "../classes/VerticalDirection";

import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface ColumnProps extends BaseUIProps {
  key?: string;

  mainAxisAlignment?: MainAxisAlignment;
  mainAxisSize?: MainAxisSize;
  crossAxisAlignment?: CrossAxisAlignment;

  textDirection?: TextDirection;
  verticalDirection?: VerticalDirection;
  textBaseline?: TextBaseline;

  children?: ReactNode | ReactNode[];
}

class _Column extends React.Component<ColumnProps> {
  static defaultProps: Partial<ColumnProps> = {
    mainAxisAlignment: MainAxisAlignment.start,
    mainAxisSize: MainAxisSize.max,
    crossAxisAlignment: CrossAxisAlignment.center,
    verticalDirection: VerticalDirection.down,
  };

  render() {
    const classNames: string[] = ["fd-col"];

    // MainAxisSize
    switch (this.props.mainAxisSize) {
      case MainAxisSize.max:
        classNames.push("max");
        break;

      case MainAxisSize.min:
        classNames.push("min");
        break;
    }

    // MainAxisAlignment
    classNames.push(
      ...CSSHelper.handleMainAxisAlignment(this.props.mainAxisAlignment)
    );

    // CrossAxisAlignment
    classNames.push(
      ...CSSHelper.handleCrossAxisAlignment(this.props.crossAxisAlignment)
    );

    // TextDirection
    switch (this.props.textDirection) {
      case TextDirection.rtl:
        classNames.push("rtl");
        break;

      case TextDirection.ltr:
        classNames.push("ltr");
        break;
    }

    // VerticalDirection
    switch (this.props.verticalDirection) {
      case VerticalDirection.up:
        classNames.push("v-up");
        break;

      case VerticalDirection.down:
        classNames.push("v-down");
        break;
    }

    // TextBaseline
    if (this.props.crossAxisAlignment === CrossAxisAlignment.baseline) {
      switch (this.props.textBaseline) {
        case TextBaseline.alphabetic:
          classNames.push("baseline-alphabetic");
          break;

        case TextBaseline.ideographic:
          classNames.push("baseline-ideographic");
          break;
      }
    }

    return React.createElement(
      "ui-column",
      {
        class:
          "Column " +
          classNames.join(" ") +
          (this.props.className ? " " + this.props.className : ""),

        ...processBaseUIProps(this.props),
      },
      this.props.children
    );
  }
}

export default function Column(props: ColumnProps) {
  return React.createElement(_Column, {
    ..._Column.defaultProps,
    ...props,
  });
}
