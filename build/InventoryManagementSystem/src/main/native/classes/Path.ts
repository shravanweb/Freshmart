import { Consumer } from "../../classes/core";

export class Path {
  ops: Consumer<CanvasRenderingContext2D>[] = [];

  constructor() {}

  moveTo(x: number, y: number): void {
    this.ops.add((ctx) => {
      ctx.moveTo(x, y);
    });
  }

  lineTo(x: number, y: number): void {
    this.ops.add((ctx) => {
      ctx.lineTo(x, y);
    });
  }
  cubicTo(
    x1: number,
    y1: number,
    x2: number,
    y2: number,
    x3: number,
    y3: number
  ): void {
    this.ops.add((ctx) => {
      ctx.bezierCurveTo(x1, y1, x2, y2, x3, y3);
    });
  }
  close(): void {
    this.ops.add((ctx) => {
      ctx.closePath();
    });
  }
}
