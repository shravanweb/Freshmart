export class Radius {
  x?: number;
  y?: number;
  constructor(props: { x?: number; y?: number }) {
    this.x = props.x;
    this.x = props.x;
  }
  static get zero(): Radius {
    return Radius.circular(0);
  }
  static circular(radius: number): Radius {
    return new Radius({ x: radius, y: radius });
  }
}
