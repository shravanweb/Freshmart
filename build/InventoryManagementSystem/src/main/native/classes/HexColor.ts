import { Color } from "./Color";

export abstract class HexColor {
  static fromHexStr(str: string): Color {
    if (str.length === 7) {
      str = "f" + str;
    }
    if (str.length === 6) {
      str = "ff" + str;
    }
    if (str.length === 5) {
      str = "ff0" + str;
    }
    let alpha = parseInt(str.substring(0, 2), 16);
    let red = parseInt(str.substring(2, 4), 16);
    let green = parseInt(str.substring(4, 6), 16);
    let blue = parseInt(str.substring(6, 8), 16);
    return new Color((alpha << 24) | (red << 16) | (green << 8) | blue);
  }
  static toHexStr(color: Color): string {
    return color.toHexa();
  }
  static fromHexInt(hexInt: number): Color {
    return new Color(hexInt);
  }
}
