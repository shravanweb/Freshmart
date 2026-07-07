import Size from "./Size";
import { Offset } from "./Offset";

export class Rect {
  static zero = new Rect();

  /// The offset of the left edge of this rectangle from the x axis.
  left: number = 0;

  /// The offset of the top edge of this rectangle from the y axis.
  top: number = 0;

  /// The offset of the right edge of this rectangle from the x axis.
  right: number = 0;

  /// The offset of the bottom edge of this rectangle from the y axis.
  bottom: number = 0;

  /// The distance between the left and right edges of this rectangle.
  get width(): number {
    return this.right - this.left;
  }

  /// The distance between the top and bottom edges of this rectangle.
  get height(): number {
    return this.bottom - this.top;
  }

  /// The distance between the upper-left corner and the lower-right corner of
  /// this rectangle.
  get size(): Size {
    return {
      width: this.width,
      height: this.height,
    };
  }
  constructor(params?: Partial<Rect>) {
    Object.assign(this, params);
  }
  static fromLTRB(
    left: number,
    top: number,
    right: number,
    bottom: number
  ): Rect {
    return new Rect({
      left: left,
      right: right,
      top: top,
      bottom: bottom,
    });
  }
  static fromLTWH(
    left: number,
    top: number,
    width: number,
    height: number
  ): Rect {
    let right = left + width;
    let bottom = top + height;
    return Rect.fromLTRB(left, top, right, bottom);
  }
  static fromCircle(
    params?: Partial<{ center: Offset; radius: number }>
  ): Rect {
    let center = params?.center;
    let radius = params?.radius || 0;
    return Rect.fromCenter({
      center: center,
      width: radius * 2,
      height: radius * 2,
    });
  }
  static fromCenter(
    params?: Partial<{ center: Offset; width: number; height: number }>
  ): Rect {
    let cx = params?.center?.dx || 0;
    let cy = params?.center?.dy || 0;
    let w = params?.width || 0;
    let h = params?.height || 0;
    return Rect.fromLTRB(cx - w / 2, cy - h / 2, cx + w / 2, cy + h / 2);
  }
  translate(translateX: number, translateY: number): Rect {
    return Rect.fromLTRB(
      this.left + translateX,
      this.top + translateY,
      this.right + translateX,
      this.bottom + translateY
    );
  }
  contains(offset: Offset): boolean {
    let x = offset.dx,
      y = offset.dy;
    if (!x || !y) {
      return false;
    }
    return (
      x >= this.left && x <= this.right && y >= this.top && y <= this.bottom
    );
  }
  overlaps(other: Rect): boolean {
    // TODO
    return false;
  }
  expandToInclude(other: Rect): Rect {
    return Rect.fromLTRB(
      Math.min(this.left, other.left),
      Math.max(this.top, other.top),
      Math.max(this.right, other.right),
      Math.min(this.bottom, other.bottom)
    );
  }
  get topLeft(): Offset {
    return new Offset({
      dx: this.left,
      dy: this.top,
    });
  }
  get topCenter(): Offset {
    return new Offset({
      dx: this.left + this.width / 2,
      dy: this.top,
    });
  }
  get topRight(): Offset {
    return new Offset({
      dx: this.right,
      dy: this.top,
    });
  }
  get centerLeft(): Offset {
    return new Offset({
      dx: this.left,
      dy: this.top + this.width / 2,
    });
  }
  get center(): Offset {
    return new Offset({
      dx: this.left + this.width / 2,
      dy: this.top + this.width / 2,
    });
  }
  get centerRight(): Offset {
    return new Offset({
      dx: this.right,
      dy: this.top + this.width / 2,
    });
  }
  get bottomLeft(): Offset {
    return new Offset({
      dx: this.left,
      dy: this.bottom,
    });
  }
  get bottomCenter(): Offset {
    return new Offset({
      dx: this.left + this.width / 2,
      dy: this.bottom,
    });
  }
  get bottomRight(): Offset {
    return new Offset({
      dx: this.right,
      dy: this.bottom,
    });
  }

  isSame(other: Rect) {
    return (
      this.left == other.left &&
      this.right == other.right &&
      this.bottom == other.bottom &&
      this.top == other.top
    );
  }
}
