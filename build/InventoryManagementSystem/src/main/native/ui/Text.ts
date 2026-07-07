import React from "react";
import Locale from "../classes/Locale";
import StrutStyle from "../classes/StrutStyle";
import { TextAlign } from "../classes/TextAlign";
import { TextDirection } from "../classes/TextDirection";
import { TextHeightBehavior } from "../classes/TextHeightBehavior";
import { TextOverflow } from "../classes/TextOverflow";
import TextStyle from "../classes/TextStyle";
import { TextWidthBasis } from "../classes/TextWidthBasis";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface TextProps extends BaseUIProps {
  data: string;
  style?: TextStyle;
  strutStyle?: StrutStyle;
  textAlign?: TextAlign;
  textDirection?: TextDirection;
  locale?: Locale;
  softWrap?: boolean;
  overflow?: TextOverflow;
  textScaleFactor?: number;
  maxLines?: number;
  semanticsLabel?: string;
  textWidthBasis?: TextWidthBasis;
  textHeightBehavior?: TextHeightBehavior;
}

function toTextAlign(textAlign: TextAlign) {
  switch (textAlign) {
    case TextAlign.left:
      return "left";
    case TextAlign.right:
      return "right";
    case TextAlign.center:
      return "center";
    case TextAlign.justify:
      return "justify";
    case TextAlign.start:
      return "start";
    case TextAlign.end:
      return "end";
  }
}

// Add function to handle TextOverflow conversion
function handleTextOverflow(overflow: TextOverflow, maxLines?: number) {
  let style: any = {
    overflow: "hidden",
  };

  switch (overflow) {
    case TextOverflow.ellipsis:
      style.textOverflow = "ellipsis";
      style.overflow = "hidden";
      if (maxLines && maxLines > 1) {
        style.display = "-webkit-box";
        style.WebkitBoxOrient = "vertical";
        style.WebkitLineClamp = maxLines;
      } else {
        style.whiteSpace = "nowrap";
      }
      break;
    case TextOverflow.clip:
      style.textOverflow = "clip";
      style.overflow = "hidden";
      if (!maxLines) {
        style.whiteSpace = "nowrap";
      }
      break;
    case TextOverflow.fade:
      style.position = "relative";
      if (!maxLines) {
        style.whiteSpace = "nowrap";
      }
      // Fade effect will be handled by ::after pseudo-element
      break;
    default:
      break;
  }

  return style;
}

class _Text extends React.Component<TextProps, {}> {
  private getFadeStyle() {
    if (this.props.overflow === TextOverflow.fade) {
      return `
        ${this.props.className || ""} {
          position: relative;
        }
        ${this.props.className || ""}::after {
          content: '';
          position: absolute;
          right: 0;
          bottom: 0;
          width: 25%;
          height: 100%;
          background: linear-gradient(to right, transparent, white);
          pointer-events: none;
        }
      `;
    }
    return "";
  }

  render() {
    let style: any = {};

    // Apply base text style
    if (this.props.style) {
      style = this.props.style.toCss();
    }

    // Apply text alignment
    if (this.props.textAlign) {
      style.textAlign = toTextAlign(this.props.textAlign);
    }

    // Apply text direction
    if (this.props.textDirection) {
      style.direction = this.props.textDirection;
    }

    // Apply locale
    if (this.props.locale) {
      style.locale = this.props.locale;
    }

    // Handle soft wrap
    if (this.props.softWrap) {
      style.whiteSpace = "normal";
    }

    // Handle overflow and maxLines
    if (this.props.overflow) {
      const overflowStyle = handleTextOverflow(
        this.props.overflow,
        this.props.maxLines
      );
      style = { ...style, ...overflowStyle };
    }

    // Apply other text properties
    if (this.props.textScaleFactor) {
      style.fontSize = `${this.props.textScaleFactor}em`;
    }

    if (this.props.textWidthBasis) {
      style.textWidthBasis = this.props.textWidthBasis;
    }

    if (this.props.textHeightBehavior) {
      style.textHeightBehavior = this.props.textHeightBehavior;
    }

    // Create the text element
    const element = React.createElement(
      "span",
      {
        className: "TextView " + (this.props.className || ""),
        style: style,
        title:
          this.props.overflow === TextOverflow.ellipsis
            ? this.props.data
            : undefined,
        ...processBaseUIProps(this.props),
      },
      this.props.data
    );

    // If using fade overflow, wrap with style element
    if (this.props.overflow === TextOverflow.fade) {
      return React.createElement(
        React.Fragment,
        null,
        element,
        React.createElement("style", {
          dangerouslySetInnerHTML: { __html: this.getFadeStyle() },
        })
      );
    }

    return element;
  }
}

export default function Text(props: TextProps) {
  return React.createElement(_Text, props);
}
