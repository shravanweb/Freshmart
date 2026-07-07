import DBObject from "./DBObject";

export default class D3EObjectChanges {
  private _values: Map<number, any> = new Map();
  private _locked: boolean = false;
  constructor() {}

  public get values(): Map<number, any> {
    return this._values;
  }

  public clear(): void {
    if (this._locked) return;
    this._values.clear();
  }

  public add(field: number, value: any): void {
    if (this._locked) return;
    this._values.putIfAbsent(field, () => value);
  }

  public replaceValue(field: number, value: any): void {
    this._values.set(field, value);
  }

  public getValue(field: number): any {
    return this._values.get(field);
  }

  public contains(field: number): boolean {
    return this._values.containsKey(field) ?? false;
  }

  public get hasChanges(): boolean {
    return this._values.isNotEmpty ?? false;
  }

  public restore(obj: DBObject): void {
    if (this._locked) return;
    this._values.forEach((value, key) => {
      obj.set(key, value);
    });
    this.clear();
  }

  public lock(): void {
    this._locked = true;
  }

  public unlock(): void {
    this._locked = false;
  }
}
