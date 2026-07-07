import { AnimationBehavior } from "./AnimationBehavior";

export interface AnimationController {
  value?: number;
  duration?: number;
  debugLabel?: string;
  lowerBound?: number;
  upperBond?: number;
  animationBehavior?: AnimationBehavior;
}
