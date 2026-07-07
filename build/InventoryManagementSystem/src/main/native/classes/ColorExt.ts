import { Color } from "./Color";

export class ColorExt {
  static toRGB(color: Color): string {
    // FOr now, we assume that this method will only receive decimal numbers.
    let red: number = color.red ?? 0,
      green: number = color.green ?? 0,
      blue: number = color.blue ?? 0;
    return "rgb(" + red + ", " + green + ", " + blue + ")";
  }

  static toRGBA(color: Color) {
    // TODO: Might become rgb when passed to browser
    let red: number = color.red ?? 0,
      green: number = color.green ?? 0,
      blue: number = color.blue ?? 0,
      alpha: number = color.alpha ?? 0;
    return "rgba(" + red + ", " + green + ", " + blue + ", " + alpha + ")";
  }

  static toRGBAndOpacity(color: Color): [string, number] {
    return [this.toRGB(color), color.alpha ?? 0];
  }

  static fromARGB(
    params: Partial<{
      alpha: number;
      red: number;
      green: number;
      blue: number;
    }>
  ): Color {
    let red: number = params.red ?? 0,
      green: number = params.green ?? 0,
      blue: number = params.blue ?? 0,
      alpha: number = params.alpha ?? 0;
    return Color.fromARGB(alpha, red, green, blue);
  }
}
