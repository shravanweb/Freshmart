import React from "react";
import BaseUIProps from "./BaseUIProps";

interface IFrameProps extends BaseUIProps {
  id?: string;
  style?: any;
  src: string;
  ref?: any;
}

class _IFrame extends React.Component<IFrameProps, {}> {
  render() {
    return React.createElement("iframe", this.props);
  }
}

export default function IFrame(props: IFrameProps) {
  return React.createElement(_IFrame, props);
}
