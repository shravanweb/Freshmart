import { Image, Offset, Paint, PaintingStyle, Paragraph, Path, Rect } from "..";

export class Canvas {
  canvas: CanvasRenderingContext2D;

  constructor(canvas: any) {
    this.canvas = canvas;
  }

  measureText(text: string): TextMetrics {
    return this.canvas.measureText(text);
  }

  drawRect(rect: Rect, paint: Paint) {
    this.applyPaint(paint);
    // this.canvas.beginPath();
    if (paint.style == PaintingStyle.stroke) {
      this.canvas.strokeRect(rect.left, rect.top, rect.width, rect.height);
    } else {
      this.canvas.fillRect(rect.left, rect.top, rect.width, rect.height);
    }
    // this.canvas.stroke();
  }

  drawCircle(offset: Offset, rad: number, paint: Paint) {
    this.applyPaint(paint);
    this.canvas.beginPath();
    this.canvas.arc(offset.dx, offset.dy, rad, 0, Math.PI * 2, true);
    this.canvas.closePath();
    if (paint.style == PaintingStyle.stroke) {
      this.canvas.stroke();
    } else {
      this.canvas.fill();
    }
  }

  drawLine(offset: Offset, offset2: Offset, stepPaint: Paint) {
    this.applyPaint(stepPaint);
    this.canvas.beginPath();
    this.canvas.moveTo(offset.dx, offset.dy);
    this.canvas.lineTo(offset2.dx, offset2.dy);
    this.canvas.stroke();
  }

  translate(dx: number, dy: number) {
    this.canvas.translate(dx, dy);
  }

  scale(_zoom: number, val?: number) {
    this.canvas.scale(_zoom, val);
  }

  rotate(angle: number) {
    this.canvas.rotate(angle);
  }

  save() {
    this.canvas.save();
  }

  clipRRect(fromLTRBXY: any) {}

  drawImage(
    image: Image,
    offset1: { dx: number; dy: number },
    _imagePaint: Paint
  ) {
    this.applyPaint(_imagePaint);
    // this.canvas.drawImage(image, offset1.dx, offset1.dy);
  }

  restore() {
    this.canvas.restore();
  }

  drawPath(path: Path, _linePaint: Paint) {}

  drawParagraph(p: Paragraph, offset: Offset) {
    this.canvas.fillStyle = p.paintStyle.color.toHexa();
    this.canvas.font =
      p.paintStyle.fontSize + "px" + " " + p.paintStyle.fontFamily;
    this.canvas.fillText(p.text, offset.dx, offset.dy);
  }

  drawArc(
    rect: Rect,
    startAngle: number,
    sweepAngle: number,
    useCenter: boolean,
    arcPaint: Paint
  ) {
    this.applyPaint(arcPaint);
    let c = rect.center;
    this.canvas.beginPath();
    this.canvas.moveTo(c.dx, c.dy);
    this.canvas.arc(
      c.dx,
      c.dy,
      rect.width / 2,
      startAngle,
      startAngle + sweepAngle,
      sweepAngle < 0
    );
    this.canvas.closePath();
    if (arcPaint.style == PaintingStyle.stroke) {
      this.canvas.stroke();
    } else {
      this.canvas.fill();
    }
  }

  applyPaint(paint: Paint) {
    var color = paint.color.toHexa();
    if (paint.style == PaintingStyle.fill) {
      this.canvas.fillStyle = color;
      this.canvas.strokeStyle = color;
      this.canvas.lineWidth = 0;
    } else {
      this.canvas.strokeStyle = color;
      this.canvas.lineWidth = paint.strokeWidth;
    }
  }
}
