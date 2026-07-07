import { Offset } from "./Offset";
import Duration from "../../core/Duration";

export class DragUpdateDetails {
  sourceTimeStamp: Duration;
  delta: Offset;
  primaryDelta: number;
  globalPosition: Offset;
  localPosition: Offset;

  constructor(params?: Partial<DragUpdateDetails>) {
    Object.assign(this, params);
  }
}
