import React from "react";
import BaseUIProps from "./BaseUIProps";
import SingleChildScrollView from "./SingleChildScrollView";

interface TickerModeProps extends BaseUIProps {
  enabled: boolean;
  child: React.ReactNode;
}

class _TickerMode extends React.Component<TickerModeProps, {}> {
  render(): React.ReactNode {
    return this.props.child;
  }
}

export default function TickerMode(props: TickerModeProps) {
  return React.createElement(_TickerMode, props);
}
