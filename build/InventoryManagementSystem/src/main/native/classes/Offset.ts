import { Rect } from "./Rect";
import Size from "./Size";

export class Offset {
  static zero = new Offset({ dx: 0, dy: 0 });

  dx: number;
  dy: number;

  constructor(params?: Partial<Offset>) {
    Object.assign(this, params);
  }

  sub(other: Offset): Offset {
    return new Offset({ dx: this.dx - other.dx, dy: this.dy - other.dy });
  }

  and(other: Size): Rect {
    return Rect.fromLTWH(this.dx, this.dy, other.width, other.height);
  }

  toString() {
    return this.dx + ":" + this.dy;
  }
}
