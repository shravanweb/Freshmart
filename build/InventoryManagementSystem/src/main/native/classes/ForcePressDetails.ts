import { Offset } from "../classes/Offset";
export class ForcePressDetails {
  globalPosition: Offset;
  localPosition: Offset;
  pressure: number;

  constructor(params?: Partial<ForcePressDetails>) {
    Object.assign(this, params);
  }
}
