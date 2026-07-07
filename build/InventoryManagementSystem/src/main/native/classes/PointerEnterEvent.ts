import { PointerDeviceKind } from "./PointerDeviceKind";
import { Offset } from "./Offset";
import Matrix4 from "./Matrix4";
import Duration from "../../core/Duration";

export class PointerEnterEvent {
  timeStamp: Duration;
  kind: PointerDeviceKind;
  device: number;
  position: Offset;
  localPosition: Offset;
  delta: Offset;
  localDelta: Offset;
  buttons: number;
  obscured: boolean;
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
  synthesized: boolean;
  transform: Matrix4;
  original: PointerEnterEvent;

  constructor(params?: Partial<PointerEnterEvent>) {
    Object.assign(this, params);
  }
}
