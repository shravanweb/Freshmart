export default class ResultStatus {
  public static readonly Success = new ResultStatus("Success", "Success", 0);
  public static readonly Errors = new ResultStatus("Errors", "Errors", 1);
  private _ident: string;
  private _name: string;
  private _index: number;
  public constructor(_ident: string, _name: string, _index: number) {
    this._ident = _ident;

    this._name = _name;

    this._index = _index;
  }
  public static get values(): ResultStatus[] {
    return [ResultStatus.Success, ResultStatus.Errors];
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
