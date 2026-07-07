import React, { ReactNode } from "react";

import { AlignmentDirectional } from "../classes/AlignmentDirectional";
import { Clip } from "../classes/Clip";
import { Overflow } from "../classes/Overflow";
import { StackFit } from "../classes/StackFit";
import { TextDirection } from "../classes/TextDirection";

import CSSHelper from "../classes/CSSHelper";

import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface StackProps extends BaseUIProps {
  alignment?: AlignmentDirectional;

  textDirection?: TextDirection;

  fit?: StackFit;

  overflow?: Overflow;

  clipBehavior?: Clip;

  children?: ReactNode | ReactNode[];
}

class _Stack extends React.Component<StackProps> {
  static defaultProps: Partial<StackProps> = {
    alignment: AlignmentDirectional.topStart,
    fit: StackFit.loose,
    overflow: Overflow.clip,
    clipBehavior: Clip.hardEdge,
  };

  render() {
    const classNames: string[] = [];

    // Alignment
    classNames.push(
      ...CSSHelper.handleAlignmentDirectional(this.props.alignment)
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

    // StackFit
    switch (this.props.fit) {
      case StackFit.expand:
        classNames.push("fit-expand");
        break;

      case StackFit.loose:
        classNames.push("fit-loose");
        break;

      case StackFit.passthrough:
        classNames.push("fit-passthrough");
        break;
    }

    // Overflow
    switch (this.props.overflow) {
      case Overflow.visible:
        classNames.push("overflow-visible");
        break;

      case Overflow.clip:
        classNames.push("overflow-clip");
        break;
    }

    // ClipBehavior
    switch (this.props.clipBehavior) {
      case Clip.none:
        classNames.push("clip-none");
        break;

      case Clip.hardEdge:
        classNames.push("clip-hard-edge");
        break;

      case Clip.antiAlias:
        classNames.push("clip-anti-alias");
        break;

      case Clip.antiAliasWithSaveLayer:
        classNames.push("clip-anti-alias-save-layer");
        break;
    }

    return React.createElement(
      "ui-stack",
      {
        class:
          "Stack " +
          classNames.join(" ") +
          (this.props.className ? " " + this.props.className : ""),

        ...processBaseUIProps(this.props),
      },

      this.props.children
    );
  }
}

export default function Stack(props: StackProps) {
  return React.createElement(_Stack, {
    ..._Stack.defaultProps,
    ...props,
  });
}
