import { PointerDeviceKind } from "../classes/PointerDeviceKind";
import { Offset } from "../classes/Offset";
import Duration from "../../core/Duration";
import Matrix4 from "./Matrix4";

export class PointerDownEvent {
  timeStamp: Duration;
  pointer: number;
  kind: PointerDeviceKind;
  device: number;
  position: Offset;
  localPosition: Offset;
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
  transform: Matrix4;
  original: PointerDownEvent;

  constructor(params?: Partial<PointerDownEvent>) {
    Object.assign(this, params);
  }
}
