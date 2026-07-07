import DFile from "../classes/DFile";
import DBObject from "../utils/DBObject";
import ReferenceCatch from "../utils/ReferenceCatch";
import UndoManager2 from "../utils/UndoManager";
import BufferReader from "./BufferReader";
import { D3ETemplate } from "./D3ETemplate";
import {
  D3ETemplateType,
  D3ETemplateField,
  D3EFieldType,
} from "./D3ETemplateTypes";
import Time from "../classes/Time";
import Duration from "../core/Duration";
import Geolocation from "../core/Geolocation";
import DateTime from "../core/DateTime";
import D3EDate from "../classes/D3EDate";

export default class WSReader {
  out: number[] = [];
  public constructor(
    private _catch: ReferenceCatch,
    private _data: BufferReader
  ) {}

  public start(): void {
    UndoManager2.readStart();
  }

  public done(): void {
    UndoManager2.readDone();
  }

  public readByte(): number {
    let b = this._data.readByte();
    //console.log('r byte: ' + b.toString());
    return b;
  }

  public readString(): string {
    let str: string = this._data.readString();
    //console.log('r str: ' + (str == null ? 'null' : str));
    return str;
  }

  public readStringList(): string[] {
    let res: string[] = [];
    let size: number = this._data.readInt();
    //console.log('r str list: ' + size.toString());
    for (let i: number = 0; i < size; i++) {
      res.add(this._data.readString());
    }
    return res;
  }

  public readDouble(): number {
    let d = this._data.readDouble();
    //console.log('r double: ' + d.toString());
    return d;
  }

  public readInteger(): number {
    let i: number = this._data.readInt();
    //console.log('r int: ' + i.toString());
    return i;
  }

  public readIntegerList(): number[] {
    let res: number[] = [];
    let size: number = this._data.readInt();
    //console.log('r int list: ' + size.toString());
    for (let i: number = 0; i < size; i++) {
      res.add(this._data.readInt());
    }
    return res;
  }

  public readBoolean(): boolean {
    let b: boolean = this._data.readBool();
    //console.log('r bool: ' + b.toString());
    return b;
  }

  public readBooleanList(): boolean[] {
    let res: boolean[] = [];
    let size: number = this._data.readInt();
    //console.log('r bool list: ' + size.toString());
    for (let i: number = 0; i < size; i++) {
      res.add(this._data.readBool());
    }
    return res;
  }

  public readEnum<T>(): T {
    let type: number = this.readInteger();
    if (type == -1) {
      //console.log('r enum null');
      return null;
    }
    let field: number = this.readInteger();
    let e: T = D3ETemplate.getEnumField(type, field);
    //console.log('r enum: ' + e.toString());
    return e;
  }

  public readEnumList<T>(): T[] {
    let list: T[] = [];
    //console.log('r enum list: ');
    let size: number = this.readInteger();
    for (let i: number = 0; i < size; i++) {
      list.add(this.readEnum());
    }
    return list;
  }

  public readDFile(): DFile {
    let id: string = this.readString();
    if (id == null) {
      //console.log('r dfile: null');
      return null;
    }
    //console.log('r dfile: ' + id);
    let file: DFile = new DFile();
    file.id = id;
    file.name = this.readString();
    file.size = this.readInteger();
    file.mimeType = this.readString();
    return file;
  }

  public readDFileList(): DFile[] {
    let list: DFile[] = [];
    let size: number = this.readInteger();
    //console.log('r dfile list: ' + size.toString());
    for (let i: number = 0; i < size; i++) {
      list.add(this.readDFile());
    }
    return list;
  }

  public readRefList<T extends DBObject>(): T[] {
    let list: T[] = [];
    let size: number = this.readInteger();
    //console.log('r dfile list: ' + size.toString());
    for (let i: number = 0; i < size; i++) {
      list.add(this.readRef(-1, null));
    }
    return list;
  }

  public readRef<T extends DBObject>(fieldIdx: number, parent: DBObject): T {
    let typeIndex: number = this.readInteger();
    if (typeIndex == -1) {
      return null;
    }
    let type: D3ETemplateType = D3ETemplate.types[typeIndex];
    //console.log('r ref: ' + type.name);
    let obj = type.embedded
      ? parent.get(fieldIdx)
      : this._fromId(this.readInteger(), typeIndex);
    try {
      obj.lock();
      let fieldIndex: number = this.readInteger();
      while (fieldIndex != -1) {
        //console.log('r fieldIndex: ' + fieldIndex.toString());
        let field: D3ETemplateField = type.get(fieldIndex);
        //console.log('r field: ' + field.name);
        if (field.collection) {
          if (obj.d3eChanges.contains(fieldIndex)) {
            let val = obj.d3eChanges.getValue(fieldIndex);
            let newList = this._readList(field, fieldIndex, val, obj);
            obj.d3eChanges.replaceValue(fieldIndex, newList);
          } else {
            let val = obj.get(fieldIndex);
            let newList = this._readList(field, fieldIndex, val, obj);
            obj.set(fieldIndex, newList);
          }
        } else {
          let val: Object = this._readField(field, fieldIndex, obj);
          if (obj.d3eChanges.contains(fieldIndex)) {
            obj.d3eChanges.replaceValue(fieldIndex, val);
          } else {
            obj.set(fieldIndex, val);
          }
        }
        fieldIndex = this.readInteger();
      }
    } finally {
      obj.unlock();
    }
    return obj;
  }

  private _readList(
    field: D3ETemplateField,
    fieldIdx: number,
    list: Array<any>,
    parent: DBObject
  ): Array<any> {
    let newList = Array.from(list);
    let count: number = this.readInteger();
    //console.log('r list ${field.name} ' + count.toString());
    if (count == 0) {
      newList.clear();
    } else if (count > 0) {
      newList.clear();
      while (count > 0) {
        newList.add(this._readField(field, fieldIdx, parent));
        count--;
      }
    } else if (count < 0) {
      //console.log('List Changes: size: ' + newList.length.toString());
      count = -count;
      //console.log('Total changes: ' + count.toString());
      while (count > 0) {
        let index: number = this.readInteger();
        if (index > 0) {
          index--;
          // added
          let val: Object = this._readField(field, fieldIdx, parent);
          if (index == newList.length) {
            //console.log('Added at : ' + index.toString());
            newList.add(val);
          } else {
            //console.log('Insert at : ' + index.toString());
            newList.insert(index, val);
          }
        } else {
          // removed
          index = -index;
          index--;
          //console.log('Removed at : ' + index.toString());
          newList.removeAt(index);
        }
        count--;
      }
      //console.log('List changes done: ' + newList.length.toString());
    }
    return newList;
  }

  private _readField(
    field: D3ETemplateField,
    fieldIdx: number,
    parent: DBObject
  ): Object {
    switch (field.fieldType) {
      case D3EFieldType.String:
        return this.readString();
      case D3EFieldType.Integer:
        return this.readInteger();
      case D3EFieldType.Double:
        return this.readDouble();
      case D3EFieldType.Boolean:
        return this.readBoolean();
      case D3EFieldType.Date:
        let year = this.readInteger();
        if (year == -1) {
          return null;
        }
        let month: number = this.readInteger();
        let dayOfMonth: number = this.readInteger();
        return D3EDate.of(year, month, dayOfMonth);

      case D3EFieldType.DateTime: {
        let millisecondsSinceEpoch = this.readInteger();
        if (millisecondsSinceEpoch == -1) {
          return null;
        }
        return DateTime.fromMillisecondsSinceEpoch(millisecondsSinceEpoch);
      }
      case D3EFieldType.Time: {
        let millisecondsSinceEpoch = this.readInteger();
        if (millisecondsSinceEpoch == -1) {
          return null;
        }
        return Time.fromMilli(millisecondsSinceEpoch);
      }
      case D3EFieldType.Duration: {
        let inMilliseconds = this.readInteger();
        if (inMilliseconds == -1) {
          return null;
        }
        return new Duration({ milliseconds: inMilliseconds });
      }
      case D3EFieldType.DFile:
        return this.readDFile();
      case D3EFieldType.Enum:
        return this.readInteger();
      case D3EFieldType.Ref:
        return this.readRef(fieldIdx, parent);
      case D3EFieldType.Geolocation:
        return this.readGeolocation();
    }
    return null;
  }

  private _fromId(id: number, type: number): DBObject {
    let obj: DBObject = this._catch.findObject(type, id);
    if (obj == null) {
      obj = D3ETemplate.types[type].creator();
      obj.id = id;
      this._catch.addObject(obj);
    }
    return obj;
  }
  public readGeolocationList(): Geolocation[] {
    let list: Geolocation[] = [];
    let size: number = this.readInteger();
    //print('r dfile list: ' + size.toString());
    for (let i: number = 0; i < size; i++) {
      list.add(this.readGeolocation());
    }
    return list;
  }

  public readGeolocation(): Geolocation {
    let val: number = this.readInteger();
    if (val == -1) {
      return null;
    }
    let latitude: number = this.readDouble();
    let longitude: number = this.readDouble();
    return new Geolocation(latitude, longitude);
  }
}
