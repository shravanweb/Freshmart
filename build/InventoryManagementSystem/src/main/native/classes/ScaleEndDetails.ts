import { Velocity } from "./Velocity";

export class ScaleEndDetails {
  velocity: Velocity;

  constructor(params?: Partial<ScaleEndDetails>) {
    Object.assign(this, params);
  }
}
