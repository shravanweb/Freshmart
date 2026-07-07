import DBObject from "../utils/DBObject";

export class ObjectUtils {
  static isEquals(a: any, b: any): boolean {
    if (Object.is(a, b)) {
      return true;
    }
    if (a == null || b == null) {
      return false;
    }
    if (a instanceof DBObject && b instanceof DBObject) {
      return a.d3eType === b.d3eType && a.id === b.id;
    }
    return false;
  }

  static isNotEquals(a: any, b: any): boolean {
    return !ObjectUtils.isEquals(a, b);
  }
}
