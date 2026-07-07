import { Offset } from "./Offset";

export class DragDownDetails {
  globalPosition: Offset;
  localPosition: Offset;

  constructor(params?: Partial<DragDownDetails>) {
    Object.assign(this, params);
  }
}
