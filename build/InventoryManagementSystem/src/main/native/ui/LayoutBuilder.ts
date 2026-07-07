import React, { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";

interface LayoutBuilderProps extends BaseUIProps {
  builder: (ctx: any, c: any) => ReactNode;
}

class _LayoutBuilder extends React.Component<LayoutBuilderProps, {}> {
  render(): React.ReactNode {
    return this.props.builder(this.context, "");
  }
}

export default function LayoutBuilder(props: LayoutBuilderProps): ReactNode {
  return React.createElement(_LayoutBuilder, props);
}
