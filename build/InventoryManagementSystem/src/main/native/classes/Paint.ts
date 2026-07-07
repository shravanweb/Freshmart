import { Color } from "../classes/Color";
import { PaintingStyle } from "./PaintingStyle";
export class Paint {
  private _color: Color;
  private _stroke: number = 1.0;
  private _style: PaintingStyle;
  maskFilter: any;

  set color(color: Color) {
    this._color = color;
  }

  get color() {
    return this._color;
  }

  set strokeWidth(stroke: number) {
    this._stroke = stroke;
  }

  get strokeWidth() {
    return this._stroke;
  }

  set style(style: PaintingStyle) {
    this._style = style;
  }

  get style() {
    return this._style;
  }

  constructor() {}
}
