import React from "react";
import * as ui from "../native/index";
import ObservableComponent from "./ObservableComponent";

export enum PaletteType {
  hsv,
  hsl,
  rgb,
}

interface ColorPickerAreaProps {
  hsvColor: ui.HSVColor;
  onColorChanged: (color: ui.HSVColor) => void;
  paletteType: PaletteType;
}

class _ColorPickerArea extends ObservableComponent<ColorPickerAreaProps> {
  hsvColor: ui.HSVColor = this.props.hsvColor;
  onColorChanged: (color: ui.HSVColor) => void = this.props.onColorChanged;
  paletteType: PaletteType = this.props.paletteType;
  _handleColorChange(horizontal: number, vertical: number) {
    switch (this.paletteType) {
      case PaletteType.hsv:
        this.onColorChanged(
          this.hsvColor.withSaturation(horizontal).withValue(vertical)
        );
        break;
      case PaletteType.hsl:
        this.onColorChanged(
          hslToHsv(
            hsvToHsl(this.hsvColor)
              .withSaturation(horizontal)
              .withLightness(vertical)
          )
        );
        break;
      default:
        break;
    }
  }

  _handleGesture(
    position: ui.Offset,
    context: any,
    height: number,
    width: number
  ) {
    var getBox = context.findRenderObject();
    var localOffset = getBox.globalToLocal(position);
    var horizontal = localOffset.dx.clamp(0.0, width) / width;
    var vertical = 1 - localOffset.dy.clamp(0.0, height) / height;
    this._handleColorChange(horizontal, vertical);
  }

  render() {
    return ui.LayoutBuilder({
      builder: (context, constraints) => {
        var width = constraints.maxWidth;
        var height = constraints.maxHeight;

        return ui.RawGestureDetector({
          gestures: {
            //     AlwaysWinPanGestureRecognizer: GestureRecognizerFactoryWithHandlers<
            //         AlwaysWinPanGestureRecognizer>(
            //             () => AlwaysWinPanGestureRecognizer(),
            //             (AlwaysWinPanGestureRecognizer instance) {
            //     instance
            //         ..onDown = ((details) => _handleGesture(
            //         details.globalPosition, context, height, width))
            //         ..onUpdate = ((details) => _handleGesture(
            //             details.globalPosition, context, height, width));
            // },
            // ),
          },
          child: ui.Builder({
            builder: (_) => {
              switch (this.paletteType) {
                case PaletteType.hsv:
                  return ui.CustomPaint({
                    painter: new HSVColorPainter(this.hsvColor, {}),
                  });
                case PaletteType.hsl:
                  return ui.CustomPaint({
                    painter: new HSLColorPainter(hsvToHsl(this.hsvColor), {}),
                  });
                default:
                  return ui.CustomPaint({});
              }
            },
          }),
        });
      },
    });
  }
}

function useWhiteForeground(
  color: ui.Color,
  props: { bias?: number }
): boolean {
  // Old:
  // return 1.05 / (color.computeLuminance() + 0.05) > 4.5;

  // New:
  props.bias ??= 1.0;
  var v = Math.sqrt(
    Math.pow(color.red, 2) * 0.299 +
      Math.pow(color.green, 2) * 0.587 +
      Math.pow(color.blue, 2) * 0.114
  ).round();
  return v < 130 * props.bias ? true : false;
}

/// reference: https://en.wikipedia.org/wiki/HSL_and_HSV#HSV_to_HSL
function hsvToHsl(color: ui.HSVColor): ui.HSLColor {
  var s = 0.0;
  var l = 0.0;
  l = ((2 - color.saturation) * color.value) / 2;
  if (l != 0) {
    if (l == 1) s = 0.0;
    else if (l < 0.5) s = (color.saturation * color.value) / (l * 2);
    else s = (color.saturation * color.value) / (2 - l * 2);
  }
  return ui.HSLColor.fromAHSL(
    color.alpha,
    color.hue,
    s.clamp(0.0, 1.0),
    l.clamp(0.0, 1.0)
  );
}

/// reference: https://en.wikipedia.org/wiki/HSL_and_HSV#HSL_to_HSV
function hslToHsv(color: ui.HSLColor): ui.HSVColor {
  var s = 0.0;
  var v = 0.0;
  v =
    color.lightness +
    color.saturation *
      (color.lightness < 0.5 ? color.lightness : 1 - color.lightness);
  if (v != 0) s = 2 - (2 * color.lightness) / v;

  return ui.HSVColor.fromAHSV(
    color.alpha,
    color.hue,
    s.clamp(0.0, 1.0),
    v.clamp(0.0, 1.0)
  );
}

class AlwaysWinPanGestureRecognizer extends ui.PanGestureRecognizer {
  addAllowedPointer(event: PointerEvent) {
    super.addAllowedPointer(event);
    this.resolve(ui.GestureDisposition.accepted);
  }
}

class HSVColorPainter extends ui.CustomPainter {
  hsvColor: ui.HSVColor;
  pointerColor: ui.Color;

  constructor(hsvColor: ui.HSVColor, props: { pointerColor?: ui.Color }) {
    super();
    this.hsvColor = hsvColor;
    this.pointerColor = props.pointerColor;
  }

  paint(canvas: ui.Canvas, size: ui.Size) {
    // TODO
    // var rect: ui.Rect = ui.Offset.zero.and(size);
    // var gradientV: ui.Gradient = new ui.LinearGradient({
    //   begin: ui.Alignment.topCenter,
    //   end: ui.Alignment.bottomCenter,
    //   colors: [new ui.Color(0xfffffff), new ui.Color(0xff000000)],
    // });
    // var gradientH: ui.Gradient = new ui.LinearGradient({
    //   colors: [
    //     new ui.Color(0xffffffff),
    //     ui.HSVColor.fromAHSV(1.0, this.hsvColor.hue, 1.0, 1.0).toColor(),
    //   ],
    // });
    // var p: ui.Paint = new ui.Paint();
    // p.shader = gradientV.createShader(rect);
    // canvas.drawRect(rect, p);
    // var p2: ui.Paint = new ui.Paint();
    // p2.blendMode = ui.BlendMode.multiply;
    // p2.shader = gradientH.createShader(rect);
    // canvas.drawRect(rect, p2);
    // var p3: ui.Paint = new ui.Paint();
    // p3.color =
    //   this.pointerColor ?? useWhiteForeground(this.hsvColor.toColor(), {})
    //     ? new ui.Color(0xffffffff)
    //     : new ui.Color(0xff000000);
    // p3.strokeWidth = 1.5;
    // p3.style = ui.PaintingStyle.stroke;
    // canvas.drawCircle(
    //   new ui.Offset(
    //     size.width * this.hsvColor.saturation,
    //     size.height * (1 - this.hsvColor.value)
    //   ),
    //   size.height * 0.04,
    //   p3
    // );
  }

  shouldRepaint(oldDelegate: ui.CustomPainter) {
    return false;
  }
}

class HSLColorPainter extends ui.CustomPainter {
  hslColor: ui.HSLColor;
  pointerColor: ui.Color;

  constructor(hslColor: ui.HSLColor, props: { pointerColor?: ui.Color }) {
    super();
    this.hslColor = hslColor;
    this.pointerColor = props.pointerColor;
  }

  paint(canvas: ui.Canvas, size: ui.Size) {
    // TODO
    // var rect: ui.Rect = ui.Offset.zero.and(size);
    // var gradientH: ui.Gradient = new ui.LinearGradient({
    //   colors: [
    //     new ui.Color(0xff808080),
    //     ui.HSLColor.fromAHSL(1.0, this.hslColor.hue, 1.0, 0.5).toColor(),
    //   ],
    // });
    // var gradientV: ui.Gradient = new ui.LinearGradient({
    //   begin: ui.Alignment.topCenter,
    //   end: ui.Alignment.bottomCenter,
    //   stops: [0.0, 0.5, 0.5, 1],
    //   colors: [
    //     new ui.Color(0xffffffff),
    //     new ui.Color(0x00ffffff),
    //     new ui.Color(0x00000000),
    //     new ui.Color(0xff000000),
    //   ],
    // });
    // var p1: ui.Paint = new ui.Paint();
    // p1.shader = gradientH.createShader(rect);
    // canvas.drawRect(rect, p1);
    // var p2: ui.Paint = new ui.Paint();
    // p2.shader = gradientV.createShader(rect);
    // canvas.drawRect(rect, p2);
    // var p3: ui.Paint = new ui.Paint();
    // p3.color =
    //   this.pointerColor ?? useWhiteForeground(this.hslColor.toColor(), {})
    //     ? new ui.Color(0xffffffff)
    //     : new ui.Color(0xff000000);
    // p3.strokeWidth = 1.5;
    // p3.style = ui.PaintingStyle.stroke;
    // canvas.drawCircle(
    //   new ui.Offset(
    //     size.width * this.hslColor.saturation,
    //     size.height * (1 - this.hslColor.lightness)
    //   ),
    //   size.height * 0.04,
    //   p3
    // );
  }

  shouldRepaint(oldDelegate: ui.CustomPainter): boolean {
    return false;
  }
}

export function ColorPickerArea(props: ColorPickerAreaProps) {
  return React.createElement(_ColorPickerArea, props);
}
