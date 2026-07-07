import { BlendMode } from "./BlendMode";
import { Border } from "./Border";
import { BorderRadiusGeometry } from "./BorderRadiusGeometry";
import BoxShadow from "./BoxShadow";
import { BoxShape } from "./BoxShape";
import { Color } from "./Color";
import Decoration from "./Decoration";
import { DecorationImage } from "./DecorationImage";
import { Gradient } from "./Gradient";

export class BoxDecoration extends Decoration {
  color?: Color;
  image?: DecorationImage;
  border?: Border;
  borderRadius?: BorderRadiusGeometry;
  boxShadow?: BoxShadow[];
  gradient?: Gradient;
  backgroundBlendMode?: BlendMode;
  shape?: BoxShape;
  transitions?: Map<string, any>;
  constructor(props?: {
    color?: Color;
    image?: DecorationImage;
    border?: Border;
    borderRadius?: BorderRadiusGeometry;
    boxShadow?: BoxShadow[];
    gradient?: Gradient;
    backgroundBlendMode?: BlendMode;
    shape?: BoxShape;
    transitions?: Map<string, any>;
  }) {
    super({ border: props?.border });
    this.image = props?.image;
    this.color = props?.color;
    this.border = props?.border;
    this.borderRadius = props?.borderRadius;
    this.boxShadow = props?.boxShadow;
    this.gradient = props?.gradient;
    this.backgroundBlendMode = props?.backgroundBlendMode;
    this.shape = props?.shape;
    this.transitions = props?.transitions;
  }
}
