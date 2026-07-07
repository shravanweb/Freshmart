import React, { ReactNode } from "react";

import { CrossAxisAlignment } from "../classes/CrossAxisAlignment";
import CSSHelper from "../classes/CSSHelper";
import { MainAxisAlignment } from "../classes/MainAxisAlignment";
import { MainAxisSize } from "../classes/MainAxisSize";
import { TextBaseline } from "../classes/TextBaseline";
import { TextDirection } from "../classes/TextDirection";
import { VerticalDirection } from "../classes/VerticalDirection";

import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface RowProps extends BaseUIProps {
  key?: string;

  mainAxisAlignment?: MainAxisAlignment;
  mainAxisSize?: MainAxisSize;
  crossAxisAlignment?: CrossAxisAlignment;

  textDirection?: TextDirection;
  verticalDirection?: VerticalDirection;
  textBaseline?: TextBaseline;

  children?: ReactNode | ReactNode[];
}

class _Row extends React.Component<RowProps> {
  static defaultProps: Partial<RowProps> = {
    mainAxisAlignment: MainAxisAlignment.start,
    mainAxisSize: MainAxisSize.max,
    crossAxisAlignment: CrossAxisAlignment.center,
    verticalDirection: VerticalDirection.down,
    textBaseline: TextBaseline.alphabetic,
  };

  render() {
    const classNames: string[] = [];

    // MainAxisAlignment
    classNames.push(
      ...CSSHelper.handleMainAxisAlignment(this.props.mainAxisAlignment)
    );

    // CrossAxisAlignment
    classNames.push(
      ...CSSHelper.handleCrossAxisAlignment(this.props.crossAxisAlignment)
    );

    // MainAxisSize
    switch (this.props.mainAxisSize) {
      case MainAxisSize.max:
        classNames.push("max");
        break;

      case MainAxisSize.min:
        classNames.push("min");
        break;
    }

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
      "ui-row",
      {
        class:
          "Row " +
          classNames.join(" ") +
          (this.props.className ? " " + this.props.className : ""),

        ...processBaseUIProps(this.props),
      },
      this.props.children
    );
  }
}

export default function Row(props: RowProps) {
  return React.createElement(_Row, {
    ..._Row.defaultProps,
    ...props,
  });
}
