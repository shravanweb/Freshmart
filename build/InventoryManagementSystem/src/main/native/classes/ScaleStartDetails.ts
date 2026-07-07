import { Offset } from "./Offset";

export class ScaleStartDetails {
  focalPoint: Offset;
  localFocalPoint: Offset;

  constructor(params?: Partial<ScaleStartDetails>) {
    Object.assign(this, params);
  }
}
