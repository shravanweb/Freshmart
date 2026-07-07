import { Offset } from "./Offset";

export class ScaleUpdateDetails {
  focalPoint: Offset;
  localFocalPoint: Offset;
  scale: number;
  horizontalScale: number;
  verticalScale: number;
  rotation: number;

  constructor(params?: Partial<ScaleUpdateDetails>) {
    Object.assign(this, params);
  }
}
