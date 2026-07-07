import { PointerDeviceKind } from "./PointerDeviceKind";
import { Offset } from "./Offset";
import Duration from "../../core/Duration";
import Matrix4 from "./Matrix4";

export class PointerMoveEvent {
  timeStamp: Duration;
  pointer: number;
  kind: PointerDeviceKind;
  device: number;
  position: Offset;
  localPosition: Offset;
  delta: Offset;
  localDelta: Offset;
  buttons: number;
  obscured: boolean;
  pressure: number;
  pressureMin: number;
  pressureMax: number;
  distanceMax: number;
  size: number;
  radiusMajor: number;
  radiusMinor: number;
  radiusMin: number;
  radiusMax: number;
  orientation: number;
  tilt: number;
  platformData: number;
  synthesized: boolean;
  transform: Matrix4;
  original: PointerMoveEvent;

  constructor(params?: Partial<PointerMoveEvent>) {
    Object.assign(this, params);
  }
}
