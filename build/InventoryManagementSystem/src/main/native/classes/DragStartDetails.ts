import Duration from "../../core/Duration";
import { Offset } from "./Offset";

export class DragStartDetails {
  sourceTimeStamp: Duration;
  globalPosition: Offset;
  localPosition: Offset;

  constructor(params?: Partial<DragStartDetails>) {
    Object.assign(this, params);
  }
}
