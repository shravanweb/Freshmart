import React, { ReactNode } from "react";
import * as ui from "../native/index";
import BaseUIProps from "../native/ui/BaseUIProps";
import ContainerEx from "./ContainerEx";
import ObservableComponent from "./ObservableComponent";
import { BuildContext } from "../classes/BuildContext";

interface CanvasViewProps extends BaseUIProps {
  controller: CanvasViewController;
  child?: ReactNode;
  onRepaint: () => void;
}

class _CanvasView extends ObservableComponent<CanvasViewProps> {
  controller: CanvasViewController = this.props.controller;
  child: ReactNode = this.props.child;
  resizeObserver: ResizeObserver;
  ref: HTMLCanvasElement;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  get onRepaint() {
    return this.props.onRepaint;
  }

  repaintListener: ui.ValueNotifier<number> = new ui.ValueNotifier(0);

  handleRef = (e: HTMLCanvasElement) => {
    if (this.props.d3eRef) {
      this.props.d3eRef(e);
    }
    if (e) {
      this.ref = e;
      this.controller.canvas = new ui.Canvas(e.getContext("2d"));
      this.controller.textStyle = this.context.textStyle;
      var rect = e.getBoundingClientRect();
      this.controller.size = new ui.Size({
        width: rect.width,
        height: rect.height,
      });
      this.ref.width = rect.width;
      this.ref.height = rect.height;
      this.resizeObserver = new ResizeObserver(this.onResize.bind(this));

      this.resizeObserver.observe(e);
      this.onRepaint();
    } else {
      this.controller.canvas = null;
    }
  };
  onResize(e): void {
    var rect = this.ref.getBoundingClientRect();
    this.controller.size = new ui.Size({
      width: rect.width,
      height: rect.height,
    });
    this.ref.width = rect.width;
    this.ref.height = rect.height;
    setTimeout(this.doRepaint.bind(this), 100);
  }

  doRepaint() {
    let cStyle = this.context.theme;
    let canvas = null;
    if (this.controller.canvas == null) {
      this.controller.canvas = new ui.Canvas(this.ref.getContext("2d"));
    } else {
      canvas = this.controller.canvas.canvas;
    }
    if (canvas == null) {
      return;
    }
    canvas.clearRect(0, 0, this.ref.width, this.ref.height);
    var fontSize = cStyle.textStyle.fontSize;
    var fontFamily = cStyle.textStyle.fontFamily;
    canvas.fillStyle = cStyle.textStyle.color.toHexa();
    canvas.font = fontSize + "px " + fontFamily;
    canvas.textBaseline = "top";
    canvas.imageSmoothingEnabled = false;

    this.onRepaint();
    //setTimeout(this.doRepaint.bind(this), 1000);
  }

  render(): ReactNode {
    return React.createElement("canvas", {
      "id": "c1",
      ref: this.handleRef,
    });

    // this.controller._context = this.context;
    // return ui.RepaintBoundary({
    //     child: ui.ClipRect({
    //         clipBehavior: ui.Clip.hardEdge,
    //         child: ui.CustomPaint({
    //             child: this.child,
    //             foregroundPainter: new CanvasPainter(this.repaintListener,
    //                 this.controller == null ? new CanvasViewController() : this.controller, this.onRepaint == null ? () => { } : this.onRepaint),
    //         })
    //     })
    // });
  }
  dispose(): void {
    super.dispose();
    this.resizeObserver.disconnect();
  }
}

export class CanvasViewController {
  repaintListener: ui.ValueNotifier<number>;
  _count: number = 0;
  painter: ui.CustomPainter;

  canvas: ui.Canvas;

  _context: any;
  size: ui.Size;
  textStyle: ui.TextStyle = new ui.TextStyle({});

  repaint() {
    this._count++;
    // this.repaintListener.value = this._count;
  }
}

class CanvasPainter extends ui.CustomPainter {
  controller: CanvasViewController;
  repaint: () => void;
  repaintListener: ui.ValueNotifier<number>;

  constructor(
    repaintListener: ui.ValueNotifier<number>,
    controller: CanvasViewController,
    repaint: () => void
  ) {
    super(repaintListener);
    this.controller = controller;
    this.controller.repaintListener = repaintListener;
  }

  paint(canvas: ui.Canvas, size: ui.Size) {
    this.controller.canvas = canvas;
    this.controller.size = size;
    //this.controller._textStyle = ui.DefaultTextStyle.of(
    //  this.controller._context
    //).style;
    this.repaint();
  }

  shouldRepaint(oldDelegate: ui.CustomPainter): boolean {
    return false;
  }
}

export default function CanvasView(props: CanvasViewProps) {
  return React.createElement(_CanvasView, props);
}
