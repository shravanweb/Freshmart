import TextStyle from "./TextStyle";
import React, { ReactNode } from "react";
import { TextAlign } from "./TextAlign";
import { TextOverflow } from "./TextOverflow";
import { TextWidthBasis } from "./TextWidthBasis";
import { TextHeightBehavior } from "../index";
import BaseUIProps from "../ui/BaseUIProps";
import { BuildContext } from "../../classes/BuildContext";
import { StyleThemeData } from "../../components/ThemeWrapper";

interface DefaultTextStyleProps extends BaseUIProps {
  merge?: boolean;
  style?: TextStyle;
  textAlign?: TextAlign;
  softWrap?: boolean;
  overflow?: TextOverflow;
  maxLines?: number;
  textWidthBasis?: TextWidthBasis;
  textHeightBehavior?: TextHeightBehavior;
  child?: ReactNode;
}

class _DefaultTextStyle extends React.Component<DefaultTextStyleProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  public render(): React.ReactNode {
    var style = this.props.style;
    if (this.props.merge) {
      style = style.merge(this.context.textStyle);
    }
    return React.createElement(
      BuildContext.Provider,
      {
        value: {
          ...this.context,
          textStyle: style,
          theme: StyleThemeData.current,
        },
      },
      this.props.child
    );
  }
}

export default function DefaultTextStyle(props: DefaultTextStyleProps) {
  return React.createElement(_DefaultTextStyle, props);
}
