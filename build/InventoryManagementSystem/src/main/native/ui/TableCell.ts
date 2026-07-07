import React, { ReactNode } from "react";

import { TableCellVerticalAlignment } from "../classes/TableCellVerticalAlignment";
import BaseUIProps from "./BaseUIProps";

interface TableCellProps extends BaseUIProps {
  // TODO
  verticalAlignment?: TableCellVerticalAlignment;
  child: ReactNode;
}

class _TableCell extends React.Component<TableCellProps, {}> {
  render() {
    return React.createElement("td", {}, this.props.child);
  }
}

export default function TableCell(props: TableCellProps) {
  return React.createElement(_TableCell, props);
}
