import { Rect } from "./Rect";
import { Radius } from "./Radius";
import { Offset } from "./Offset";

export class RRect {
  constructor(public rect: Rect, public radius: Radius) {}

  static fromRectXY(rect: Rect, radiusX: number, radiusY: number): RRect {
    return new RRect(rect, { x: radiusX, y: radiusY });
  }

  static fromRectAndRadius(rect: Rect, radius: Radius): RRect {
    return new RRect(rect, radius);
  }

  contains(point: Offset): boolean {
    return this.rect.contains(point);
  }

  static fromLTRBXY(
    dx: number,
    dy: number,
    number: number,
    number2: number,
    number3: number,
    number4: number
  ) {}
}
