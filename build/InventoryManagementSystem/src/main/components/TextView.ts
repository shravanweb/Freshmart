import React, { ReactNode } from "react";
import ObservableComponent from "./ObservableComponent";
import * as ui from "../native/index";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
interface TextViewProps extends BaseUIProps {
  data: string;
  style?: ui.TextStyle;
  strutStyle?: ui.StrutStyle;
  textAlign?: ui.TextAlign;
  textDirection?: ui.TextDirection;
  locale?: ui.Locale;
  softWrap?: boolean;
  textScaleFactor?: number;
  maxLines?: number;
  semanticsLabel?: string;
  textWidthBasis?: ui.TextWidthBasis;
  textHeightBehavior?: ui.TextHeightBehavior;
  overflow?: ui.TextOverflow;
}

class _TextView extends ObservableComponent<TextViewProps> {
  render(): ReactNode {
    return ui.Text({
      data: this.props.data ?? "",
      style: this.props.style,
      strutStyle: this.props.strutStyle,
      textDirection: this.props.textDirection,
      textAlign: this.props.textAlign,
      locale: this.props.locale,
      softWrap: this.props.softWrap,
      textScaleFactor: this.props.textScaleFactor,
      maxLines: this.props.maxLines,
      semanticsLabel: this.props.semanticsLabel,
      textHeightBehavior: this.props.textHeightBehavior,
      overflow: this.props.overflow,
      textWidthBasis: this.props.textWidthBasis,
      className: this.props.className,
      ...copyBaseUIProps(this.props),
    });
  }
}

export default function TextView(props: TextViewProps) {
  return React.createElement(_TextView, props);
}
