import { EdgeInsets } from "..";

export class EdgeInsetsExt {
  static from(): EdgeInsets {
    return {};
  }

  static only(
    params?: Partial<{
      left: number;
      top: number;
      right: number;
      bottom: number;
    }>
  ): EdgeInsets {
    return {
      left: params?.left,
      top: params?.top,
      right: params?.right,
      bottom: params?.bottom,
    };
  }
}
