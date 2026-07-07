import { Offset } from "./Offset";

export class LongPressMoveUpdateDetails {
  globalPosition: Offset;
  localPosition: Offset;
  offsetFromOrigin: Offset;
  localOffsetFromOrigin: Offset;

  constructor(params?: Partial<LongPressMoveUpdateDetails>) {
    Object.assign(this, params);
  }
}
