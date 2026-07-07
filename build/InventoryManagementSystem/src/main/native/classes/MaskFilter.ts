import BlurStyle from "./BlurStyle";

export default class MaskFilter {
  style: BlurStyle;
  sigma: number;

  constructor(style: BlurStyle, sigma: number) {
    this.style = style;
    this.sigma = sigma;
  }

  static blur(style: BlurStyle, sigma: number): MaskFilter {
    return new MaskFilter(style, sigma);
  }
}
