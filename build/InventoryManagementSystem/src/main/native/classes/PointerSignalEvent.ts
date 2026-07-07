import { PointerDeviceKind } from "./PointerDeviceKind";
import { Offset } from "./Offset";
import Duration from "../../core/Duration";

export class PointerSignalEvent {
  timeStamp: Duration;
  pointer: number;
  kind: PointerDeviceKind;
  device: number;
  position: Offset;
  localPosition: Offset;
  transform: Offset;
  original: PointerSignalEvent;

  constructor(params?: Partial<PointerSignalEvent>) {
    Object.assign(this, params);
  }
}
