import { Color } from "./Color";

export class HSLColor {
  lightness: number;
  saturation: number;
  alpha: number;
  hue: number;
  static fromAHSL(a: number, b: number, c: number, d: number): HSLColor {
    // TODO
    return null;
  }
  toColor(): Color {
    // TODO
    return null;
  }
  withSaturation(saturation: number): HSLColor {
    // TODO
    return null;
  }
  withLightness(value: number): HSLColor {
    // TODO
    return null;
  }
}
