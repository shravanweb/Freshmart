import { TableColumnWidth } from "./TableColumnWidth";

export default class FixedColumnWidth extends TableColumnWidth {
  value?: number;

  constructor(value: number) {
    super();
  }
}
