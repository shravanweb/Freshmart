import { Offset } from "./Offset";

export class TapUpDetails {
  globalPosition: Offset;
  localPosition: Offset;

  constructor(params?: Partial<TapUpDetails>) {
    Object.assign(this, params);
  }
}
