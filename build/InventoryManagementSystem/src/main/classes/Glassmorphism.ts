import { Offset } from "../native/classes/Offset";
import { BorderRadius } from "../native/classes/BorderRadius";
import { BlendMode } from "../native/classes/BlendMode";

export interface GlassmorphismProps {
  blurSigma: Offset;
  borderRadius: BorderRadius;
  blendMode: BlendMode;
}

export default class Glassmorphism {
  blurSigma: Offset;
  borderRadius: BorderRadius;
  blendMode: BlendMode;

  constructor(props: GlassmorphismProps) {
    this.blurSigma = props.blurSigma;
    this.borderRadius = props.borderRadius;
    this.blendMode = props.blendMode;
  }
}
