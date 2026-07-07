import { TableColumnWidth } from "../native/classes/TableColumnWidth";
import { FlexColumnWidth } from "../native";
import { FixedColumnWidth } from "../native";

class TableColumnWidthExt {
  public static getColumnWidth(params?: {
    columnWidth: string;
  }): TableColumnWidth {
    let values: string[] = params?.columnWidth.split(".");
    if (values.last == "fixed") {
      return new FixedColumnWidth(Number.tryParseDouble(values.first));
    } else if (values.last == "flex") {
      return new FlexColumnWidth(Number.tryParseDouble(values.first));
    }
    // TODO
    return null;
    // return IntrinsicColumnWidth();
  }
}
