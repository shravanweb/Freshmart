import React, { ReactNode } from "react";
import BaseUIProps from "./BaseUIProps";

interface ExpandedProps extends BaseUIProps {
  // TODO
  flex?: number;
  child?: ReactNode;
}

class _Expanded extends React.Component<ExpandedProps, {}> {
  static defaultProps = {
    flex: 1,
  };
  render() {
    return React.createElement("div", { class: "expanded" }, this.props.child);
  }
}

export default function Expanded(props: ExpandedProps) {
  return React.createElement(_Expanded, {
    ..._Expanded.defaultProps,
    ...props,
  });
}
