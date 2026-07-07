import { Offset } from "./Offset";

export class LongPressStartDetails {
  globalPosition: Offset;
  localPosition: Offset;

  constructor(params?: Partial<LongPressStartDetails>) {
    Object.assign(this, params);
  }
}
