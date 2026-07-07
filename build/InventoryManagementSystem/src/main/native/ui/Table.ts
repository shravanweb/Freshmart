import React, { ReactNode } from "react";
import FixedColumnWidth from "../classes/FixedColumnWidth";
import TableBorder from "../classes/TableBorder";
import { TableCellVerticalAlignment } from "../classes/TableCellVerticalAlignment";
import { TableColumnWidth } from "../classes/TableColumnWidth";
import { TextBaseline } from "../classes/TextBaseline";
import { TextDirection } from "../classes/TextDirection";
import BaseUIProps from "./BaseUIProps";

interface TableProps extends BaseUIProps {
  defaultColumnWidth?: FixedColumnWidth;
  columnWidths?: { [key: number]: TableColumnWidth };
  textDirection?: TextDirection;
  border?: TableBorder;
  defaultVerticalAlignment?: TableCellVerticalAlignment;
  textBaseline?: TextBaseline;
  children?: ReactNode[];
}

class _Table extends React.Component<TableProps, {}> {
  static defaultProps = {
    defaultColumnWidth: new FixedColumnWidth(1),
    defaultVerticalAlignment: TableCellVerticalAlignment.top,
  };
  render() {
    return React.createElement(
      "table",
      {
        class:
          "Table " +
          "table" +
          (this.props.className ? " " + this.props.className : ""),
      },
      this.props.children
    );
  }
}

export default function Table(props: TableProps) {
  return React.createElement(_Table, { ..._Table.defaultProps, ...props });
}
