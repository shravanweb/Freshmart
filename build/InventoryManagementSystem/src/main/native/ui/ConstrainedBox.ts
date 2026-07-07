import React, { ReactNode } from "react";
import { BoxConstraints } from "../classes/BoxConstraints";
import BaseUIProps from "./BaseUIProps";

interface ConstrainedBoxProps extends BaseUIProps {
  constraints: BoxConstraints;
  child: ReactNode;
}

class _ConstrainedBox extends React.Component<ConstrainedBoxProps, {}> {
  static defaultProps = {
    constraints: new BoxConstraints({
      minWidth: 0,
      maxWidth: Number.infinity,
      minHeight: 0,
      maxHeight: Number.infinity,
    }),
  };
  render(): React.ReactNode {
    return this.props.child;
  }
}

export default function ConstrainedBox(props: ConstrainedBoxProps) {
  return React.createElement(_ConstrainedBox, {
    ..._ConstrainedBox.defaultProps,
    ...props,
  });
}
