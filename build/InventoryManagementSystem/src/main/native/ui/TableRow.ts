import React, { ReactNode } from "react";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";
import { BoxDecoration } from "../classes/BoxDecoration";

interface TableRowProps extends BaseUIProps {
  decoration?: BoxDecoration;
  children: ReactNode[];
}

class _TableRow extends React.Component<TableRowProps, {}> {
  render(): React.ReactNode {
    return React.createElement(
      "tr",
      {
        class:
          "TableRow " + this.props.className ? " " + this.props.className : "",
        ...processBaseUIProps(this.props),
      },
      this.props.children
    );
  }
}

export default function TableRow(props: TableRowProps): ReactNode {
  return React.createElement(_TableRow, props);
}
