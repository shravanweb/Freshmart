export default class GradientType {
  public static readonly Color = new GradientType("Color", "Color", 0);
  public static readonly LinearGradient = new GradientType(
    "LinearGradient",
    "LinearGradient",
    1
  );
  public static readonly SweepGradient = new GradientType(
    "SweepGradient",
    "SweepGradient",
    2
  );
  public static readonly RadialGradient = new GradientType(
    "RadialGradient",
    "RadialGradient",
    3
  );
  private _ident: string;
  private _name: string;
  private _index: number;
  public constructor(_ident: string, _name: string, _index: number) {
    this._ident = _ident;

    this._name = _name;

    this._index = _index;
  }
  public static get values(): GradientType[] {
    return [
      GradientType.Color,
      GradientType.LinearGradient,
      GradientType.SweepGradient,
      GradientType.RadialGradient,
    ];
  }
  public get ident(): string {
    return this._ident;
  }
  public get name(): string {
    return this._name;
  }
  public get index(): number {
    return this._index;
  }
  public toString(): string {
    return this._name;
  }
}
