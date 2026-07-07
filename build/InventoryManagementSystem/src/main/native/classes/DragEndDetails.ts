import { Velocity } from "../classes/Velocity";

export class DragEndDetails {
  velocity: Velocity;
  primaryVelocity: number;

  constructor(params?: Partial<DragEndDetails>) {
    Object.assign(this, params);
  }
}
