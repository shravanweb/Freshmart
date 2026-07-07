import React, { ReactNode } from "react";
import { ImageFrom } from "../native/classes/ImageFrom";
import * as ui from "../native/index";
import { ColorPickerArea, PaletteType } from "./ColorPiker";
import ObservableComponent from "./ObservableComponent";
const _height = 180.0;

class ColorWithName {
  colorName: string;
  hexaCode: string;

  constructor(props: { colorName: string; hexaCode: string }) {
    this.setColorName(props.colorName);
    this.setHexaCode(props.hexaCode);
  }

  setColorName(val: string) {
    var isValChanged = this.colorName != val;

    if (!isValChanged) {
      return;
    }
    this.colorName = val;
  }

  setHexaCode(val: string) {
    var isValChanged = this.hexaCode != val;

    if (!isValChanged) {
      return;
    }

    this.hexaCode = val;
  }
}

type _ColorPickerNativeOnColorChanged = (value: ui.Color) => void;

class _SliderIndicatorPainter extends ui.CustomPainter {
  position: number;

  constructor(position: number) {
    super();
    this.position = position;
  }

  paint(canvas: ui.Canvas, size: ui.Size) {
    var p = new ui.Paint();
    p.color = new ui.Color(0xff000000);
    canvas.drawCircle(
      new ui.Offset({ dx: size.width / 2, dy: this.position }),
      13,
      p
    );
  }

  shouldRepaint(old: _SliderIndicatorPainter) {
    return true;
  }
}
interface ColorPickerNativeProps {
  initialColor: ui.Color;
  onColorChanged: _ColorPickerNativeOnColorChanged;
}

class _ColorPickerState extends ObservableComponent<ColorPickerNativeProps> {
  initialColor: ui.Color = this.props.initialColor;
  onColorChanged: _ColorPickerNativeOnColorChanged = this.props.onColorChanged;

  _hexaColorController: ui.TextEditingController =
    new ui.TextEditingController();
  _colors = [
    ui.Color.fromARGB(255, 255, 0, 0),
    ui.Color.fromARGB(255, 255, 128, 0),
    ui.Color.fromARGB(255, 255, 255, 0),
    ui.Color.fromARGB(255, 128, 255, 0),
    ui.Color.fromARGB(255, 0, 255, 0),
    ui.Color.fromARGB(255, 0, 255, 128),
    ui.Color.fromARGB(255, 0, 255, 255),
    ui.Color.fromARGB(255, 0, 128, 255),
    ui.Color.fromARGB(255, 0, 0, 255),
    ui.Color.fromARGB(255, 127, 0, 255),
    ui.Color.fromARGB(255, 255, 0, 255),
    ui.Color.fromARGB(255, 255, 0, 127),
    ui.Color.fromARGB(255, 128, 128, 128),
    ui.Color.fromARGB(255, 0, 0, 0),
  ];
  _colorSliderPosition: number = 0;
  _shadeSliderPosition: number;
  _currentColor: ui.Color;
  _shadedColor: ui.Color;

  initState() {
    super.initState();
    this._colorSliderPosition =
      this.initialColor != null
        ? this._calculateColorPosition(this.initialColor) * 13
        : this._colorSliderPosition;
    this._currentColor =
      this.initialColor != null
        ? this.initialColor
        : this._calculateSelectedColor(this._colorSliderPosition);
    this._shadeSliderPosition =
      this.initialColor != null
        ? this._calculateShapedColorPosition(this.initialColor)
        : _height / 2; //center the shader selector
    this._shadedColor = this._calculateShadedColor(this._shadeSliderPosition);
    this._shadedColor =
      this.initialColor != null ? this.initialColor : this._shadedColor;
    this._hexaColorController.text = ui.HexColor.toHexStr(this._shadedColor);
  }

  _colorChangeHandler(position: number) {
    //handle out of bounds positions
    if (position > _height) {
      position = _height;
    }
    if (position < 0) {
      position = 0;
    }
    this.setState(() => {
      this._colorSliderPosition = position;
      this._currentColor = this._calculateSelectedColor(
        this._colorSliderPosition
      );
      this._shadedColor = this._calculateShadedColor(this._shadeSliderPosition);
      this._hexaColorController.text = ui.HexColor.toHexStr(this._shadedColor);
      this.onColorChanged(this._shadedColor);
    });
  }

  _shadeChangeHandler(position: number) {
    //handle out of bounds gestures
    if (position > _height) position = _height;
    if (position < 0) position = 0;
    this.setState(() => {
      this._shadeSliderPosition = position;
      this._shadedColor = this._calculateShadedColor(this._shadeSliderPosition);
      this.onColorChanged(this._shadedColor);
    });
  }

  _calculateShadedColor(position: number): ui.Color {
    var aplhaValue = position * (255 / 180);
    var color = ui.Color.fromARGB(
      aplhaValue.round(),
      this._currentColor.red,
      this._currentColor.green,
      this._currentColor.blue
    );
    this.setState(() => {
      this._hexaColorController.text = ui.HexColor.toHexStr(color);
    });

    return color;
  }

  _calculateShapedColorPosition(color: ui.Color): number {
    var alphValue = color.alpha;
    var markPosition = (180 * alphValue) / 255;
    return markPosition.round();
  }

  _calculateColorPosition(color: ui.Color): number {
    var redVal: number;
    var greenVal: number;
    var blueVal: number;

    if (color.green > 128) {
      greenVal = 255;
    } else if (color.green < 127) {
      greenVal = 0;
    } else {
      greenVal = color.green;
    }
    if (color.red > 128) {
      redVal = 255;
    } else if (color.red < 127) {
      redVal = 0;
    } else {
      redVal = color.red;
    }
    if (color.blue > 128) {
      blueVal = 255;
    } else if (color.blue < 127) {
      blueVal = 0;
    } else {
      blueVal = color.blue;
    }
    var colorValue = ui.Color.fromARGB(255, redVal, greenVal, blueVal);
    var index = this._colors.indexOf(colorValue);
    return index;
  }

  _calculateSelectedColor(position: number) {
    //determine color
    var positionInColorArray = (position / _height) * (this._colors.length - 1);
    var index = positionInColorArray.truncate();
    var remainder = positionInColorArray - index;
    if (remainder == 0.0) {
      this._currentColor = this._colors[index];
    } else {
      //calculate new color
      var redValue =
        this._colors[index].red == this._colors[index + 1].red
          ? this._colors[index].red
          : (
              this._colors[index].red +
              (this._colors[index + 1].red - this._colors[index].red) *
                remainder
            ).round();
      var greenValue =
        this._colors[index].green == this._colors[index + 1].green
          ? this._colors[index].green
          : (
              this._colors[index].green +
              (this._colors[index + 1].green - this._colors[index].green) *
                remainder
            ).round();
      var blueValue =
        this._colors[index].blue == this._colors[index + 1].blue
          ? this._colors[index].blue
          : (
              this._colors[index].blue +
              (this._colors[index + 1].blue - this._colors[index].blue) *
                remainder
            ).round();
      this._currentColor = ui.Color.fromARGB(
        255,
        redValue,
        greenValue,
        blueValue
      );
    }
    return this._currentColor;
  }

  onHexCodeChanges(text: string) {
    var hexCodeColor = ui.HexColor.fromHexStr(text);
    this._colorSliderPosition =
      this._calculateColorPosition(hexCodeColor) * 13.0;
    this.setState(() => {
      this._colorSliderPosition =
        this._calculateShapedColorPosition(hexCodeColor).toDouble();
      this._shadedColor = hexCodeColor;
      this._currentColor = hexCodeColor;
      this.onColorChanged(this._shadedColor);
    });
  }

  render(): ReactNode {
    return ui.Container({
      padding: ui.EdgeInsets.all(10.0),
      child: ui.Wrap({
        spacing: 10,
        children: [
          ui.Container({
            margin: ui.EdgeInsets.only({ top: 10 }),
            height: 180,
            width: 180,
            child: this.colorPickerArea(this._currentColor),
          }),
          ui.GestureDetector({
            behavior: ui.HitTestBehavior.opaque,
            onVerticalDragStart: (details) => {
              this._colorChangeHandler(details.localPosition.dy);
            },
            onVerticalDragUpdate: (details) => {
              this._colorChangeHandler(details.localPosition.dy);
            },
            onTapDown: (details) => {
              this._colorChangeHandler(details.localPosition.dy);
            },
            //This outside padding makes it much easier to grab the   slider because the gesture detector has
            // the extra padding to recognize gestures inside of
            child: ui.Padding({
              padding: ui.EdgeInsets.all(15),
              child: ui.Container({
                width: 10,
                height: _height,
                decoration: new ui.BoxDecoration({
                  color: new ui.Color(0xff343443),
                  borderRadius: ui.BorderRadius.circular(10),
                  // gradient: new ui.LinearGradient({ // TODO
                  //     begin: ui.Alignment.topCenter,
                  //     end: ui.Alignment.bottomCenter,
                  //     colors: this._colors
                  // }),
                }),
                // child: ui.CustomPaint({
                //     painter: new _SliderIndicatorPainter(this._colorSliderPosition),
                // }),
              }),
            }),
          }),
          ui.GestureDetector({
            behavior: ui.HitTestBehavior.opaque,
            onVerticalDragStart: (details) => {
              this._shadeChangeHandler(details.localPosition.dy);
            },
            onVerticalDragUpdate: (details) => {
              this._shadeChangeHandler(details.localPosition.dy);
            },
            onTapDown: (details) => {
              this._shadeChangeHandler(details.localPosition.dy);
            },
            //This outside padding makes it much easier to grab the slider because the gesture detector has
            // the extra padding to recognize gestures inside of
            child: ui.Padding({
              padding: ui.EdgeInsets.all(15),
              child: ui.Stack({
                children: [
                  ui.Container({
                    width: 10,
                    height: _height,
                    child: ui.ImageView({
                      imageType: ImageFrom.Asset,
                      imageUrl: "images/grid.png",
                      repeat: ui.ImageRepeat.repeat,
                    }),
                  }),
                  ui.Container({
                    width: 10,
                    height: _height,
                    decoration: new ui.BoxDecoration({
                      color: new ui.Color(0xff343443),
                      borderRadius: ui.BorderRadius.circular(10),
                      // gradient: new ui.LinearGradient({ // TODO
                      //     begin: ui.Alignment.topCenter,
                      //     end: ui.Alignment.bottomCenter,
                      //     colors: this.getAlphaValueBaseList(this._currentColor)
                      // }),
                    }),
                    // child: ui.CustomPaint({
                    //     painter: new _SliderIndicatorPainter(this._shadeSliderPosition),
                    // }),
                  }),
                ],
              }),
            }),
            onTap: () => {
              this.onColorChanged(this._shadedColor);
            },
          }),
        ],
      }),
    });
  }

  colorPickerArea(pickerColor: ui.Color): ReactNode {
    return ColorPickerArea({
      hsvColor: ui.HSVColor.fromColor(pickerColor),
      onColorChanged: (color) => {
        this.setState(() => {
          this._currentColor = color.toColor();
          this._colorSliderPosition =
            this._calculateColorPosition(this._currentColor) * 13.0;
          this._hexaColorController.text = ui.HexColor.toHexStr(
            this._currentColor
          );
        });
        this._shadedColor = color.toColor();
        this.onColorChanged(this._shadedColor);
      },
      paletteType: PaletteType.hsv,
    });
  }

  getAlphaValueBaseList(color: ui.Color): ui.Color[] {
    var colors: ui.Color[] = [];
    for (let i = 0; i <= 255; i++) {
      colors.push(ui.Color.fromARGB(i, color.red, color.green, color.blue));
    }
    return colors;
  }
}
