import { BoxConstraints } from "..";

export class BoxConstraintsExt {
  static tightFor(
    params: Partial<{ width: number; height: number }>
  ): BoxConstraints {
    return {
      minWidth: params.width ?? 0,
      maxWidth: params.width ?? Number.POSITIVE_INFINITY,
      minHeight: params.height ?? 0,
      maxHeight: params.height ?? Number.POSITIVE_INFINITY,
    };
  }
}
