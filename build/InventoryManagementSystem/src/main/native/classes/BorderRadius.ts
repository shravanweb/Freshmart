import { Radius } from "./Radius";

export class BorderRadius {
  topLeft?: Radius;
  topRight?: Radius;
  bottomLeft?: Radius;
  bottomRight?: Radius;
  isUniform?: Boolean;
  radius?: number;
  constructor(props: {
    topLeft?: Radius;
    topRight?: Radius;
    bottomLeft?: Radius;
    bottomRight?: Radius;
    isUniform?: Boolean;
    radius?: number;
  }) {
    this.topLeft = props.topLeft;
    this.topRight = props.topRight;
    this.bottomLeft = props.bottomLeft;
    this.bottomRight = props.bottomRight;
    this.isUniform = props.isUniform;
    this.radius = props.radius;
  }
  static only(
    params?: Partial<{
      topLeft: Radius;
      topRight: Radius;
      bottomLeft: Radius;
      bottomRight: Radius;
    }>
  ): BorderRadius {
    let borderRadius = BorderRadius.zero;
    if (params.topLeft) {
      borderRadius.topLeft = params.topLeft;
    }
    if (params.topRight) {
      borderRadius.topRight = params.topRight;
    }
    if (params.bottomLeft) {
      borderRadius.bottomLeft = params.bottomLeft;
    }
    if (params.bottomRight) {
      borderRadius.topLeft = params.bottomRight;
    }
    borderRadius.isUniform = false;
    return borderRadius;
  }
  static get zero(): BorderRadius {
    return BorderRadius.circular(0);
  }
  static circular(radius: number): BorderRadius {
    return new BorderRadius({
      topLeft: new Radius({ x: radius, y: radius }),
      topRight: new Radius({ x: radius, y: radius }),
      bottomLeft: new Radius({ x: radius, y: radius }),
      bottomRight: new Radius({ x: radius, y: radius }),
      isUniform: true,
      radius: radius,
    });
  }
}
