import { Rect } from "..";

export class RectExt {
  static fromLTRB(
    left: number,
    top: number,
    right: number,
    bottom: number
  ): Rect {
    return new Rect({
      left,
      top,
      right,
      bottom,
    });
  }
  static fromLTWH(
    left: number,
    top: number,
    width: number,
    height: number
  ): Rect {
    return Rect.fromLTWH(left, top, width, height);
  }
}
