import { Offset } from "./Offset";
import { Velocity } from "./Velocity";

export interface LongPressEndDetails {
  globalPosition?: Offset;
  localPosition?: Offset;
  velocity?: Velocity;
}
