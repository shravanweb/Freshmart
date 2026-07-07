import BlockString from "../classes/BlockString";
import DFile from "../classes/DFile";
import ExpressionString from "../classes/ExpressionString";
import Time from "../classes/Time";
import DateTime from "../core/DateTime";
import Duration from "../core/Duration";
import { D3ETemplate } from "../rocket/D3ETemplate";
import {
  D3EFieldType,
  D3ETemplateField,
  D3ETemplateType,
} from "../rocket/D3ETemplateTypes";
import DBObject from "./DBObject";
import ReferenceCatch from "./ReferenceCatch";
import D3EDate from "../classes/D3EDate";

export default class JSONUtils {
  public static stringToExpressionString(eString: string): ExpressionString {
    if (eString == null) {
      return null;
    }

    return new ExpressionString(eString);
  }

  public static expressionStringToString(eString: ExpressionString): string {
    return eString?.content;
  }

  public static stringToBlockString(bString: string): BlockString {
    if (bString == null) {
      return null;
    }

    return new BlockString(bString);
  }

  public static blockStringToString(bString: BlockString): string {
    return bString?.content;
  }

  public static toJsonString(obj: DBObject): string {
    let map = JSONUtils.toJson(obj);
    let mapAsObj = JSONUtils.toObj(map);
    return JSON.stringify(mapAsObj);
  }

  public static toObj(map: Map<string, any>): any {
    let entries: any[] = [];
    map.forEach((value, key) => {
      if (value instanceof Map) {
        value = JSONUtils.toObj(value);
      }
      if (value instanceof Array) {
        let list: Array<any> = value;
        let resList = [];
        list.forEach((i) => {
          resList.push(JSONUtils.toObj(i));
        });
        value = resList;
      }
      entries.add([key, value]);
    });
    return Object.fromEntries(entries);
  }

  public static toJson(obj: DBObject): Map<string, any> {
    if (obj == null) {
      return null;
    }
    let result: Map<string, any> = new Map();
    let typeStr: string = obj.d3eType;
    result.set("__typename", obj.d3eType);
    result.set("id", obj.id);
    let typeIndex: number = D3ETemplate.typeInt(typeStr);
    let type: D3ETemplateType = D3ETemplate.types[typeIndex];
    for (let i = 0; i < type.fields.length + type.parentFields; i++) {
      let tf: D3ETemplateField = type.get(i);
      let val: any = obj.get(i);
      switch (tf.fieldType) {
        case D3EFieldType.String:
        case D3EFieldType.Integer:
        case D3EFieldType.Double:
        case D3EFieldType.Boolean:
        case D3EFieldType.Enum:
          result.set(tf.name, val);
          break;
        case D3EFieldType.Date:
          if (tf.collection) {
            let list: Array<any> = val;
            result.set(
              tf.name,
              list.map((e) => JSONUtils._toDateJson(e)).toList()
            );
          } else {
            result.set(tf.name, JSONUtils._toDateJson(val));
          }
          break;
        case D3EFieldType.DateTime:
          if (tf.collection) {
            let list: Array<any> = val;
            result.set(
              tf.name,
              list.map((e) => JSONUtils._toDateTimeJson(e)).toList()
            );
          } else {
            result.set(tf.name, JSONUtils._toDateTimeJson(val));
          }
          break;
        case D3EFieldType.Time:
          if (tf.collection) {
            let list: Array<any> = val;
            result.set(
              tf.name,
              list.map((e) => JSONUtils._toTimeJson(e)).toList()
            );
          } else {
            result.set(tf.name, JSONUtils._toTimeJson(val));
          }
          break;
        case D3EFieldType.Duration:
          if (tf.collection) {
            let list: Array<any> = val;
            result.set(
              tf.name,
              list.map((e) => JSONUtils._toDurationJson(e)).toList()
            );
          } else {
            result.set(tf.name, JSONUtils._toDurationJson(val));
          }
          break;
        case D3EFieldType.DFile:
          if (tf.collection) {
            let list: Array<any> = val;
            result.set(
              tf.name,
              list.map((e) => JSONUtils._toDFileJson(e)).toList()
            );
          } else {
            result.set(tf.name, JSONUtils._toDFileJson(val));
          }
          break;
        case D3EFieldType.Ref:
          if (tf.child) {
            if (tf.collection) {
              let list: Array<any> = val;
              result.set(
                tf.name,
                list.map((e) => JSONUtils.toJson(e)).toList()
              );
            } else {
              result.set(tf.name, JSONUtils.toJson(val));
            }
          } else {
            if (tf.collection) {
              let list: Array<any> = val;
              result.set(
                tf.name,
                list.map((e) => JSONUtils._toRefJson(e)).toList()
              );
            } else {
              result.set(tf.name, JSONUtils._toRefJson(val));
            }
          }
          break;
      }
    }
    return result;
  }

  public static _toRefJson(obj: DBObject): Map<string, any> {
    if (obj == null) {
      return null;
    }
    let result: Map<string, any> = new Map();
    result.set("__typename", obj.d3eType);
    result.set("id", obj.id);
    return result;
  }

  public static _fromRefJson(obj: Map<string, any>): DBObject {
    if (obj == null) {
      return null;
    }
    if (!(obj instanceof Map)) {
      obj = new Map(Object.entries(obj));
    }
    let catche: ReferenceCatch = ReferenceCatch.get();
    let type: number = D3ETemplate.typeInt(obj.get("__typename"));
    if (type == undefined) {
      return null;
    }
    let res: DBObject = catche.findObject(type, obj.get("id"));
    if (res == null) {
      res = D3ETemplate.types[type].creator();
      res.id = obj.get("id");
      catche.addObject(res);
    }
    return res;
  }

  public static _toDFileJson(obj: DFile): Map<string, any> {
    if (obj == null) {
      return null;
    }
    let result: Map<string, any> = new Map();
    result.set("id", obj.id);
    result.set("name", obj.name);
    result.set("size", obj.size);
    return result;
  }

  public static _fromDFileJson(obj: Map<string, any>): DFile {
    if (obj == null) {
      return null;
    }
    let dfile = new DFile();
    dfile.id = obj.get("id");
    dfile.name = obj.get("name");
    dfile.size = obj.get("size");
    return dfile;
  }

  public static _toDateTimeJson(val: DateTime): string {
    if (val instanceof DateTime) {
      return val?.toIso8601String();
    } else {
      return val;
    }
  }

  public static _fromDateTimeJson(val: string): DateTime {
    return DateTime.parse(val + "Z");
  }

  public static _toTimeJson(val: Time): string {
    return val?.toString();
  }

  public static _fromTimeJson(val: string): Time {
    return Time.parse(val);
  }

  public static _toDateJson(val: Date): string {
    return val?.toString();
  }

  public static _fromDateJson(val: string): D3EDate {
    return D3EDate.parse(val);
  }

  public static _toDurationJson(val: Duration): number {
    return val?.inMilliseconds;
  }

  public static _fromDurationJson(val: number): Duration {
    return new Duration({ milliseconds: val });
  }

  public static fromJsonString(val: string): DBObject {
    let obj = JSON.parse(val);
    let map = new Map(Object.entries(obj));
    return JSONUtils.fromJson(map);
  }

  public static fromJson(map: Map<string, any>): DBObject {
    if (map == null) {
      return null;
    }
    if (!(map instanceof Map)) {
      map = new Map(Object.entries(map));
    }
    let obj: DBObject = JSONUtils._fromRefJson(map);
    if (obj == null) {
      return null;
    }
    let typeStr: string = map.get("__typename");
    let typeIndex: number = D3ETemplate.typeInt(typeStr);
    let type: D3ETemplateType = D3ETemplate.types[typeIndex];
    for (let i = 0; i < type.fields.length + type.parentFields; i++) {
      let tf: D3ETemplateField = type.get(i);
      let val: any = map.get(tf.name);
      if (val == null) {
        obj.set(i, null);
        continue;
      }
      let res = val;
      switch (tf.fieldType) {
        case D3EFieldType.String:
        case D3EFieldType.Integer:
        case D3EFieldType.Double:
        case D3EFieldType.Boolean:
        case D3EFieldType.Enum:
          res = val;
          break;
        case D3EFieldType.Date:
          if (tf.collection) {
            let list: Array<any> = val;
            res = list.map((e) => JSONUtils._fromDateJson(e)).toList();
          } else {
            res = JSONUtils._toDateJson(val);
          }
          break;
        case D3EFieldType.DateTime:
          if (tf.collection) {
            let list: Array<any> = val;
            res = list.map((e) => JSONUtils._fromDateTimeJson(e)).toList();
          } else {
            res = JSONUtils._toDateTimeJson(val);
          }
          break;
        case D3EFieldType.Time:
          if (tf.collection) {
            let list: Array<any> = val;
            res = list.map((e) => JSONUtils._fromTimeJson(e)).toList();
          } else {
            res = JSONUtils._toTimeJson(val);
          }
          break;
        case D3EFieldType.Duration:
          if (tf.collection) {
            let list: Array<any> = val;
            res = list.map((e) => JSONUtils._fromDurationJson(e)).toList();
          } else {
            res = JSONUtils._fromDurationJson(val);
          }
          break;
        case D3EFieldType.DFile:
          if (tf.collection) {
            let list: Array<any> = val;
            res = list.map((e) => JSONUtils._fromDFileJson(e)).toList();
          } else {
            if (val instanceof Map) {
              res = JSONUtils._fromDFileJson(val);
            } else {
              res = JSONUtils._fromDFileJson(new Map(Object.entries(val)));
            }
          }
          break;
        case D3EFieldType.Ref:
          if (tf.child) {
            if (tf.collection) {
              let list: Array<any> = val;
              res = list.map((e) => JSONUtils.fromJson(e)).toList();
            } else {
              res = JSONUtils.fromJson(val);
            }
          } else {
            if (tf.collection) {
              let list: Array<any> = val;
              res = list.map((e) => JSONUtils._fromRefJson(e)).toList();
            } else {
              res = JSONUtils._fromRefJson(val);
            }
          }
          break;
      }
      obj.set(i, res);
    }
    return obj;
  }
}
