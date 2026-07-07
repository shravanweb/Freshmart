import { BorderSide } from "./BorderSide";
import { BorderRadius } from "./BorderRadius";

export default class TableBorder {
  top?: BorderSide;
  right?: BorderSide;
  bottom?: BorderSide;
  left?: BorderSide;
  horizontalInside?: BorderSide;
  verticalInside?: BorderSide;
  borderRadius?: BorderRadius;
  constructor(props?: Partial<TableBorder>) {
    Object.assign(this, props);
  }
}
