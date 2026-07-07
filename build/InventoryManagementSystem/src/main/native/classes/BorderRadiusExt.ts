import { BorderRadius } from "..";
import { RadiusExt } from "./RadiusExt";

export class BorderRadiusExt {
  static only(params?: Partial<BorderRadius>): BorderRadius {
    return {
      topLeft: params?.topLeft || RadiusExt.zero,
      topRight: params?.topRight || RadiusExt.zero,
      bottomLeft: params?.bottomLeft || RadiusExt.zero,
      bottomRight: params?.bottomRight || RadiusExt.zero,
    };
  }
}
