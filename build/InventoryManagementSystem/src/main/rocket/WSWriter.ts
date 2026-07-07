import ReferenceCatch from "../utils/ReferenceCatch";
import BufferWriter from "./BufferWriter";
import DFile from "../classes/DFile";
import D3EObjectChanges from "../utils/D3EObjectChanges";
import DBObject from "../utils/DBObject";
import { D3ETemplate } from "./D3ETemplate";
import {
  D3ETemplateType,
  D3ERefType,
  D3ETemplateField,
  D3EFieldType,
} from "./D3ETemplateTypes";
import MapExt from "../classes/MapExt";
import Time from "../classes/Time";
import Duration from "../core/Duration";
import DateTime from "../core/DateTime";
import D3EDate from "../classes/D3EDate";
import Geolocation from "../core/Geolocation";

class _Change {
  constructor(public added: boolean, public index: number, public obj: any) {}
}

export default class WSWriter {
  static _localObjCount: number = -2;
  _out: BufferWriter = new BufferWriter();
  public constructor(private _cache: ReferenceCatch) {}

  public done(): void {}
  public clear<T extends DBObject>(): void {}
  public setChanges(changes: D3EObjectChanges): void {}
  public nextLocalId(): number {
    WSWriter._localObjCount = WSWriter._localObjCount - 1;
    return WSWriter._localObjCount;
  }

  public writeObjStart<T extends DBObject>(obj: T): void {}
  public get fields(): number {
    return 0;
  }
  public get nextField(): number {
    return 0;
  }

  public get out(): Uint8Array {
    return this._out.takeBytes();
  }

  public writeByte(byte: number): void {
    // console.log('w byte: ' + byte.toString());
    this._out.writeByte(byte);
  }

  public writeId(id: number): void {
    // console.log('w id: ' + id.toString());
    this._out.writeInt(id);
  }

  public writeBoolean(val: boolean): void {
    // console.log('w boolean: ' + val.toString());
    this._out.writeBool(val);
  }

  public writeInteger(val: number): void {
    // console.log('w number: ' + val?.toString());
    this._out.writeInt(val);
  }

  public writeDouble(val: number): void {
    // console.log('w number: ' + val.toString());
    this._out.writeDouble(val);
  }

  public writeNumber(val: number): void {
    // console.log('w number: ' + val.toString());
    this._out.writeDouble(val);
  }

  public writeRef<T extends DBObject>(obj: T): void {
    if (obj == null) {
      // console.log('w ref: null');
      this.writeInteger(-1);
      return;
    }
    this._writeRef(obj);
    this.writeInteger(-1);
  }

  private _writeRef<T extends DBObject>(obj: T): void {
    let type: string = obj.d3eType;
    let typeIdx: number = D3ETemplate.typeInt(type);
    // console.log('w ref: ' + type);
    this.writeInteger(typeIdx);
    let tt: D3ETemplateType = D3ETemplate.types[typeIdx];
    if (tt.embedded) {
      return;
    }
    if (obj.id == 0) {
      obj.id = this.nextLocalId();
      this._cache.addObject(obj);
    }
    if (obj.id == null) {
      obj.id = 0;
    }
    this.writeInteger(obj.id);
  }

  public writeDFile(file: DFile): void {
    if (file == null) {
      // console.log('w dfile: null');
      this.writeString(null);
      return;
    }
    this.writeString(file.id);
    this.writeString(file.name);
    this.writeInteger(file.size);
    this.writeString(file.mimeType);
  }

  public writeObj<T extends DBObject>(obj: T): void {
    if (obj == null) {
      // console.log('w obj: null');
      this.writeInteger(-1);
      return;
    }
    this._writeRef(obj);
    let type: string = obj.d3eType;
    let changes: D3EObjectChanges = obj.d3eChanges;
    let tType: D3ETemplateType = D3ETemplate.types[D3ETemplate.typeInt(type)];
    if (tType.refType == D3ERefType.Struct) {
      let index: number = 0;
      for (let field of tType.fields) {
        if (field.inverse || field.unknown) {
          continue;
        }
        this.writeInteger(index);
        if (field.collection) {
          let list: Array<any> = obj.get(index);
          this.writeInteger(list.length);
          for (let val of list) {
            this._writeListItem(field, val);
          }
        } else {
          this._writeField(field, index, obj);
        }
        index++;
      }
    } else {
      if (changes.values != null) {
        if (changes.values.isNotEmpty && tType.name == "CustomFieldValue") {
          let fieldIdx: number = tType.fieldMap.get("field");
          if (!MapExt.keys(changes.values).contains(fieldIdx)) {
            let field: D3ETemplateField = tType.get(fieldIdx);
            this.writeInteger(fieldIdx);
            this._writeField(field, fieldIdx, obj);
          }
        }
        MapExt.keys(changes.values).forEach((key) => {
          let field: D3ETemplateField = tType.get(key);
          if (field.inverse || field.unknown) {
            return;
          }
          if (field.collection) {
            //Array oldList = obj.d3eChanges.values[key];
            let newList = obj.get(key);
            //if (oldList.isEmpty && newList.isNotEmpty) {
            this.writeInteger(key);
            this.writeInteger(newList.length);
            for (let val of newList) {
              this._writeListItem(field, val);
            }
            // } else {
            //   Array<_Change> changes = computeListChanges(oldList, newList);
            //   if (changes.isNotEmpty) {
            //     writeInteger(key);
            //     _writeListChanges(field, changes);
            //   }
            // }
          } else {
            this.writeInteger(key);
            this._writeField(field, key, obj);
          }
        });
      }
    }
    this.writeInteger(-1);
  }

  public computeListChanges(from: Array<any>, to: Array<any>): _Change[] {
    let compiledResult: _Change[] = [];
    let x: number = 0;
    let xCount: number = to.length;
    let y: number = 0;
    let yCount: number = from.length;
    let lookAhead: number = 1;
    while (x < xCount) {
      let xobj: Object = to[x];
      if (y == yCount) {
        compiledResult.add(new _Change(true, x, xobj));
      } else {
        let temp: number = 0;
        let found: boolean = false;
        while (temp <= lookAhead && y + temp < yCount) {
          let yobj = from[y + temp];
          if (xobj == yobj) {
            found = true;
            while (temp > 0) {
              temp--;
              yobj = from[y + temp];
              compiledResult.add(new _Change(false, y, yobj));
              y++;
            }
            y++;
            break;
          }
          temp++;
        }
        if (!found) {
          compiledResult.add(new _Change(true, x, xobj));
        }
      }
      x++;
    }
    while (y < yCount) {
      let yobj: Object = from[y];
      compiledResult.add(new _Change(false, x, yobj));
      x++;
      y++;
    }

    return compiledResult;
  }

  private _writeListChanges(field: D3ETemplateField, changes: _Change[]): void {
    this.writeInteger(-changes.length); // We are sending changes only so -ve
    for (let change of changes) {
      if (change.added) {
        this.writeInteger(change.index + 1);
        this._writeListItem(field, change.obj);
      } else {
        this.writeInteger(-change.index - 1);
      }
    }
  }

  private _writeListItem(field: D3ETemplateField, obj: Object): void {
    switch (field.fieldType) {
      case D3EFieldType.String: {
        this.writeString(obj as string);
        break;
      }
      case D3EFieldType.Integer: {
        this.writeInteger(obj as number);
        break;
      }
      case D3EFieldType.Double: {
        this.writeDouble(obj as number);
        break;
      }
      case D3EFieldType.Boolean: {
        this.writeBoolean(obj as boolean);
        break;
      }
      case D3EFieldType.Date: {
        let val = obj as D3EDate;
        if (val == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(val.year);
          this.writeInteger(val.month);
          this.writeInteger(val.day);
        }
        break;
      }

      case D3EFieldType.DateTime: {
        let val: DateTime = obj as DateTime;
        if (val == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(val.millisecondsSinceEpoch);
        }
        break;
      }
      case D3EFieldType.Time: {
        let val: Time = obj as Time;
        if (val == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(val.toMillOfDay());
        }
        break;
      }
      case D3EFieldType.Duration: {
        let val: Duration = obj as Duration;
        if (val == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(val.inMilliseconds);
        }
        break;
      }
      case D3EFieldType.DFile: {
        let val: DFile = obj as DFile;
        this.writeDFile(val);
        break;
      }
      case D3EFieldType.Enum: {
        let val: number = obj as number;
        this.writeInteger(val);
        break;
      }
      case D3EFieldType.Ref: {
        let val: DBObject = obj as DBObject;
        if (field.child) {
          this.writeObj(val);
        } else {
          this.writeRef(val);
        }
        break;
      }
      case D3EFieldType.Geolocation: {
        let val: Geolocation = obj as Geolocation;
        if (obj == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(0);
          this.writeDouble(val.latitude);
          this.writeDouble(val.longitude);
        }
        break;
      }
    }
  }

  private _writeField(
    field: D3ETemplateField,
    key: number,
    obj: DBObject
  ): void {
    switch (field.fieldType) {
      case D3EFieldType.String: {
        let val: string = obj.get(key);
        this.writeString(val);
        break;
      }
      case D3EFieldType.Integer: {
        let val: number = obj.get(key);
        this.writeInteger(val);
        break;
      }
      case D3EFieldType.Double: {
        let val: number = obj.get(key);
        this.writeDouble(val);
        break;
      }
      case D3EFieldType.Boolean: {
        let val: boolean = obj.get(key);
        this.writeBoolean(val);
        break;
      }
      case D3EFieldType.Date: {
        let val: D3EDate = obj.get(key);
        this.writeDate(val);
        break;
      }

      case D3EFieldType.DateTime: {
        let val: DateTime = obj.get(key);
        if (val == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(val.millisecondsSinceEpoch);
        }
        break;
      }
      case D3EFieldType.Time: {
        let val: Time = obj.get(key);
        if (val == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(val.toMillOfDay());
        }
        break;
      }
      case D3EFieldType.Duration: {
        let val: Duration = obj.get(key);
        if (val == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(val.inMilliseconds);
        }
        break;
      }
      case D3EFieldType.DFile: {
        let val: DFile = obj.get(key);
        this.writeDFile(val);
        break;
      }
      case D3EFieldType.Enum: {
        let val: number = obj.get(key);
        this.writeInteger(val);
        break;
      }
      case D3EFieldType.Ref: {
        let val: DBObject = obj.get(key);
        if (field.child) {
          this.writeObj(val);
        } else {
          this.writeRef(val);
        }
        break;
      }
      case D3EFieldType.Geolocation: {
        let val: Geolocation = obj.get(key) as Geolocation;
        if (obj == null) {
          this.writeInteger(-1);
        } else {
          this.writeInteger(0);
          this.writeDouble(val.latitude);
          this.writeDouble(val.longitude);
        }
        break;
      }
    }
  }

  public writeDate(val: D3EDate): void {
    if (val == null) {
      this.writeInteger(-1);
    } else {
      this.writeInteger(val.year);
      this.writeInteger(val.month);
      this.writeInteger(val.day);
    }
  }

  public writeObjFull<T extends DBObject>(obj: T): void {
    this.writeObj(obj);
  }

  public writeObjFullList<T extends DBObject>(list: T[]): void {
    // console.log('w obj list: ' + list.length.toString());
    this.writeInteger(list.length);
    for (let obj of list) {
      this.writeObjFull(obj);
    }
  }

  public writeObjUnion<T extends DBObject>(obj: T): void {
    return this.writeObjFull(obj);
  }

  public writeObjUnionList<T extends DBObject>(list: T[]): void {
    this.writeObjFullList(list);
  }

  public writeRefList<T extends DBObject>(list: T[]): void {
    // console.log('w ref list: ' + list.length.toString());
    this.writeInteger(list.length);
    for (let obj of list) {
      this.writeRef(obj);
    }
  }

  public writeSubRefList<T extends DBObject>(list: T[]): void {
    this.writeRefList(list);
  }

  public writeObjList<T extends DBObject>(list: T[]): void {
    this.writeObjFullList(list);
  }

  public writeSubRef<T extends DBObject>(obj: T): void {
    this.writeRef(obj);
  }

  public writeBool(val: boolean): void {
    // console.log('w boolean: ' + val.toString());
    this._out.writeBool(val);
  }

  public writeString(str: string): void {
    // console.log('w str: ' + (str == null ? 'null' : str));
    this._out.writeString(str);
  }

  public writeStringList(list: string[]): void {
    // console.log('w str list: ' + list.length.toString());
    this.writeInteger(list.length);
    for (let str of list) {
      this.writeString(str);
    }
  }

  public writeEnum<T>(en: T): void {
    if (en == null) {
      // console.log('w enum: null');
      return;
    }
    // console.log('w enum: ' + en.toString());
    let parts: string[] = en.toString().split(".");
    let type: number = D3ETemplate.typeInt(parts.first);
    this.writeInteger(type);
    let t: D3ETemplateType = D3ETemplate.types[type];
    let f: number = t.fieldMap.get(parts.last);
    let tf: D3ETemplateField = t.fields[f];
    if (tf.unknown) {
      // Current value is not known to server, what to do?
      // For now lets write the first value known to server
      for (let x: number = 0; x < t.fields.length; x++) {
        if (!t.fields[x].unknown) {
          this.writeInteger(x);
          break;
        }
      }
    } else {
      this.writeInteger(f);
    }
  }

  public writeEnumList<T>(list: T[]): void {
    // console.log('w enum list: ' + list.length.toString());
    this.writeInteger(list.length);
    for (let e of list) {
      this.writeEnum(e);
    }
  }
}
