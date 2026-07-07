import { FontWeight } from "..";
import { Color } from "./Color";

export class PainterTextStyle {
  fontFamily: string;
  fontSize: number;
  fontWeight: FontWeight;
  color: Color;
  constructor(params?: Partial<PainterTextStyle>) {
    Object.assign(this, params);
  }
}
