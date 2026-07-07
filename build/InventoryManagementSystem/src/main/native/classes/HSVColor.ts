import { Color } from "./Color";

export class HSVColor {
  hue: number;
  alpha: number;
  value: number;
  saturation: number;
  static fromAHSV(a: number, b: number, c: number, d: number): HSVColor {
    // TODO
    return null;
  }
  static fromColor(color: Color): HSVColor {
    // TODO
    return null;
  }
  toColor(): Color {
    // TODO
    return null;
  }
  withSaturation(saturation: number): HSVColor {
    // TODO
    return null;
  }
  withValue(value: number): HSVColor {
    // TODO
    return null;
  }
}
