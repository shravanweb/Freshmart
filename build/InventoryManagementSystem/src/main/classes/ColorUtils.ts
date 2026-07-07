import * as ui from "../native";
import { ColorExt } from "../native/classes/ColorExt";

export class ColorUtils {
  public static colorCache: Map<String, ui.Color> = new Map();
  constructor() {}
  public static parseColor(code: string): ui.Color {
    let color: string = code;

    let res: ui.Color = ColorUtils.colorCache.get(color);

    if (res != null) {
      return res;
    }

    try {
      res = ui.HexColor.fromHexStr(color);

      ColorUtils.colorCache.set(color, res);

      return res;
    } catch (e) {
      return ColorExt.fromARGB({
        alpha: 0,
        red: 0,
        green: 0,
        blue: 0,
      });
    }
  }
}
