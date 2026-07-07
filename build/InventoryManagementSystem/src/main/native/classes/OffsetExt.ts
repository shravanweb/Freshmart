import { Offset } from "..";

export class OffsetExt {
  static getOffset(params?: Partial<Offset>) {
    return new Offset({ dx: params?.dx || 0, dy: params?.dy || 0 });
  }
}
