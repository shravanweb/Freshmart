import { Rect } from "./Rect";
import { ClipOp } from "./ClipOp";
import { Offset } from "./Offset";
import { Paint } from "./Paint";
import Paragraph from "./Paragraph";
import { Path } from "./Path";
import { CanvasWidget, Color } from "..";
import { PaintingStyle } from "./PaintingStyle";
import { Consumer } from "../../classes/core";
import { ColorExt } from "./ColorExt";
import { RRect } from "./RRect";

export class CanvasController {
  // @ts-ignore
  private canvas: CanvasWidget;
  private _saveCount: number;

  // @ts-ignore
  connect = (canvas: CanvasWidget) => {
    this.canvas = canvas;
  };

  repaint = (): void => {
    this.canvas?.repaint();
  };

  private getCanvasContext = () => {
    if (this.canvas) {
      let htmlCanvas = this.canvas.ref;
      if (htmlCanvas && htmlCanvas.getContext) {
        return htmlCanvas.getContext("2d");
      }
    }
  };

  private doOnCanvas = (op: (ctx: CanvasRenderingContext2D) => void): void => {
    let ctx = this.getCanvasContext();
    if (!ctx) {
      return;
    }
    op(ctx);
  };

  private spreadOffset = (offset: Offset): [number, number] => {
    return [offset.dx || 0, offset.dy || 0];
  };

  private doWithPaint = (
    op: Consumer<CanvasRenderingContext2D>,
    paint?: Paint
  ) => {
    if (paint) {
      this.doOnCanvas((ctx) => {
        ctx.save();
        ctx.lineWidth = paint.strokeWidth;
        if (paint.color) {
          ctx.strokeStyle = ColorExt.toRGB(paint.color);
        }
      });
    }
    this.doOnCanvas(op);
    if (paint) {
      this.doOnCanvas((ctx) => {
        ctx.restore();
      });
    }
  };

  clipRect = (
    rect: Rect,
    clipOp: ClipOp = ClipOp.intersect,
    doAntiAlias?: boolean
  ): void => {
    let [x, y] = this.spreadOffset(rect.topLeft);
    this.doOnCanvas((ctx) => {
      ctx.clearRect(x, y, rect.width, rect.height);
    });
  };

  clipPath = (path: Path, doAntiAlias: boolean): void => {
    //TODO
    if (path.ops.isEmpty) {
      return;
    }
    let task: Consumer<CanvasRenderingContext2D> = (ctx) => {
      ctx.beginPath();
      path.ops.forEach((one) => one(ctx)); // Do we draw?
      ctx.clip();
    };
    this.doOnCanvas(task);
  };

  drawCircle = (c: Offset, radius: number, paint: Paint): void => {
    //TODO: Paint
    this.doWithPaint((ctx) => {
      let [cx, cy] = this.spreadOffset(c);
      ctx.beginPath();
      ctx.arc(cx, cy, radius, 0, Math.PI * 2);
      ctx.stroke();
    }, paint);
  };

  drawLine = (p1: Offset, p2: Offset, paint: Paint): void => {
    //TODO: Paint
    this.doWithPaint((ctx) => {
      let [x1, y1] = this.spreadOffset(p1);
      let [x2, y2] = this.spreadOffset(p2);
      if (x1 === x2 && y1 === y2) {
        return;
      }
      ctx.beginPath();
      ctx.moveTo(x1, y1);
      ctx.lineTo(x2, y2);
      ctx.stroke();
    }, paint);
  };

  drawOval = (rect: Rect, paint: Paint): void => {
    this.doWithPaint((ctx) => {
      let c = rect.center;
      ctx.ellipse(
        c.dx || 0,
        c.dy || 0,
        rect.width / 2,
        rect.height / 2,
        0,
        0,
        Math.PI * 2
      );
    }, paint);
  };

  drawPaint = (paint: Paint): void => {
    this.doWithPaint((ctx) => {
      paint.style === PaintingStyle.fill ? ctx.fill() : ctx.stroke(); // TODO: Is this correct?
    }, paint);
  };

  drawParagraph = (paragraph: Paragraph, offset: Offset): void => {
    this.doOnCanvas((ctx) => {
      let [x, y] = this.spreadOffset(offset);
      ctx.strokeText(paragraph.text, x, y); // TODO: Add styles
    });
  };

  drawPath = (path: Path, paint: Paint): void => {
    if (path.ops.isEmpty) {
      return;
    }
    let task: Consumer<CanvasRenderingContext2D> = (ctx) => {
      ctx.beginPath();
      path.ops.forEach((one) => one(ctx));
      paint.style === PaintingStyle.fill ? ctx.fill() : ctx.stroke();
    };
    this.doWithPaint(task, paint);
  };

  drawRect = (rect: Rect, paint: Paint): void => {
    this.doWithPaint((ctx) => {
      ctx.beginPath();
      let [x, y] = this.spreadOffset(rect.topLeft);
      paint.style === PaintingStyle.fill
        ? ctx.fillRect(x, y, rect.width, rect.height)
        : ctx.strokeRect(x, y, rect.width, rect.height);
    }, paint);
  };

  drawRRect = (rect: RRect, paint: Paint): void => {
    this.doWithPaint((ctx) => {
      ctx.beginPath();
      let [x, y] = this.spreadOffset(rect.rect.topLeft);
      let cur = ctx.lineJoin;
      ctx.lineJoin = "round";
      paint.style === PaintingStyle.fill
        ? ctx.fillRect(x, y, rect.rect.width, rect.rect.height)
        : ctx.strokeRect(x, y, rect.rect.width, rect.rect.height);
      ctx.closePath();
      ctx.lineJoin = cur; // reset
    }, paint);
  };

  drawShadow = (
    path: Path,
    color: Color,
    elevation: number,
    transparentOccluder: boolean
  ): void => {
    if (path.ops.isEmpty) {
      return;
    }
    let task: Consumer<CanvasRenderingContext2D> = (ctx) => {
      ctx.save();
      ctx.beginPath();
      ctx.shadowColor = ColorExt.toRGB(color);
      path.ops.forEach((one) => one(ctx));
      ctx.stroke();
      ctx.restore();
    };
    this.doOnCanvas(task);
  };

  getSaveCount = (): number => {
    return this._saveCount;
  };

  restore = (): void => {
    this.doOnCanvas((ctx) => {
      this._saveCount--;
      ctx.restore();
    });
  };

  rotate = (radians: number): void => {
    this.doOnCanvas((ctx) => {
      ctx.rotate(radians);
    });
  };

  save = (): void => {
    this.doOnCanvas((ctx) => {
      this._saveCount++; // Does this refer to the correct "this"?
      ctx.save();
    });
  };

  scale = (sx: number, sy?: number): void => {
    this.doOnCanvas((ctx) => {
      ctx.scale(sx, sy || 0);
    });
  };

  translate = (dx: number, dy: number): void => {
    this.doOnCanvas((ctx) => {
      ctx.translate(dx, dy);
    });
  };
}
