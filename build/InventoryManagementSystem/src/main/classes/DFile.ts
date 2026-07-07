import Env from "./Env";

export default class DFile {
  private static _ID: number = 0;
  private static _NAME: number = 1;
  private static _SIZE: number = 2;
  private static _MIME_TYPE: number = 3;

  private _name: string;
  private _id: string;
  private _size: number;
  private _mimeType: string;

  constructor() {}

  public static fromJson(json: Map<string, any>): DFile {
    let _file: DFile = new DFile();
    _file._name = json.get("name");
    _file._id = json.get("id");
    _file._size = json.get("size");
    _file._mimeType = json.get("mimeType");
    return _file;
  }

  public toJson(): Map<string, any> {
    let jsonMap: Map<string, any> = new Map();

    jsonMap.set("id", this._id);
    jsonMap.set("name", this._name);
    jsonMap.set("size", this._size);
    jsonMap.set("mimeType", this._mimeType);

    return jsonMap;
  }

  public get id(): string {
    return this._id;
  }

  public set id(id: string) {
    this._id = id;
  }

  public get size(): number {
    return this._size;
  }

  public set size(size: number) {
    this._size = size;
  }

  public get name(): string {
    return this._name;
  }

  public set name(name: string) {
    this._name = name;
  }

  public get mimeType(): string {
    return this._mimeType;
  }

  public set mimeType(mimeType: string) {
    this._mimeType = mimeType;
  }

  public get downloadUrl(): string {
    return Env.get().resolvedHttpUrl + "/api/download/" + this.id;
  }
}
