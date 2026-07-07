import { BorderStyle } from "./BorderStyle";
import { Color } from "./Color";

export class BorderSide {
  color?: Color;
  width?: number;
  style?: BorderStyle;

  constructor(props?: Partial<BorderSide>) {
    Object.assign(this, props);
    if (this.style === undefined || this.style === null) {
      this.style = BorderStyle.solid;
    }
  }
}
