import React, { ReactNode } from "react";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface CenterProps extends BaseUIProps {
  widthFactor?: number; // TODO, Same as flutter
  heightFactor?: number; // TODO
  child?: ReactNode;
}

class _Center extends React.Component<CenterProps, {}> {
  render() {
    return React.createElement(
      "ui-center",
      {
        class: (this.props.className ? this.props.className : "") + " Center ",
        ...processBaseUIProps(this.props),
      },
      this.props.child
    );
  }
}

export default function Center(props: CenterProps) {
  return React.createElement(_Center, props);
}
