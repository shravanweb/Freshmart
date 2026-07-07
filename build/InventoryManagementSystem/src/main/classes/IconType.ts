export default class IconType {
  public static readonly SVG = new IconType("SVG", "SVG", 0);
  public static readonly Font = new IconType("Font", "Font", 1);
  private _ident: string;
  private _name: string;
  private _index: number;
  public constructor(_ident: string, _name: string, _index: number) {
    this._ident = _ident;

    this._name = _name;

    this._index = _index;
  }
  public static get values(): IconType[] {
    return [IconType.SVG, IconType.Font];
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
