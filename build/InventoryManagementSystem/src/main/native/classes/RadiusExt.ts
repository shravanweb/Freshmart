import { Radius } from "./Radius";

export class RadiusExt {
  static circular(radius: number): Radius {
    return RadiusExt.elliptical({ x: radius, y: radius });
  }

  static elliptical(
    params?: Partial<{
      x: number;
      y: number;
    }>
  ): Radius {
    return {
      x: params?.x || 0,
      y: params?.y || 0,
    };
  }

  static get zero(): Radius {
    return {
      x: 0,
      y: 0,
    };
  }
}
