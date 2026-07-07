import { Offset } from "./Offset";
import { PointerDeviceKind } from "./PointerDeviceKind";

export class TapDownDetails {
  globalPosition: Offset;
  localPosition: Offset;
  kind: PointerDeviceKind;

  constructor(params?: Partial<TapDownDetails>) {
    Object.assign(this, params);
  }
}
