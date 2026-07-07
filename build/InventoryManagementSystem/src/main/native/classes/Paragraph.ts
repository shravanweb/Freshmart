import { ParagraphConstraints } from "./ParagraphConstraints";
import ParagraphStyle from "./ParagraphStyle";
import { PainterTextStyle } from "./PainterTextStyle";
import { Canvas } from "./Canvas";

export default class Paragraph {
  maxIntrinsicWidth: number;
  paraStyle: ParagraphStyle;
  paintStyle: PainterTextStyle;
  text: string;

  _width: number;
  _height: number;

  constructor(
    text: string,
    styles?: Partial<{
      paraStyle: ParagraphStyle;
      paintStyle: PainterTextStyle;
    }>
  ) {
    Object.assign(this, styles);
    this.text = text;
  }

  get longestLine(): number {
    return this._width;
  }

  get width(): number {
    return this._width;
  }

  get height(): number {
    return this._height;
  }

  layout(canvas: Canvas, constraints: ParagraphConstraints): void {
    canvas.canvas.save();
    canvas.canvas.font =
      this.paintStyle.fontSize + "px " + this.paintStyle.fontFamily;
    canvas.canvas.fillStyle = this.paintStyle.color.toHexa();
    let metric = canvas.measureText(this.text);
    this._width = metric.width;
    let actualHeight =
      metric.actualBoundingBoxAscent + metric.actualBoundingBoxDescent;
    this._height = actualHeight;
    canvas.canvas.restore();
  }
}
