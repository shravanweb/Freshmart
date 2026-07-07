import Comparable from "../core/Comparable";

export default class CollectionUtils {
  public static isEquals(left: Iterable<any>, right: Iterable<any>): boolean {
    if (left === right) {
      return true;
    }
    if (left == null || right == null) {
      return false;
    }
    if (left.length !== right.length) {
      return false;
    }
    for (let x = 0; x < left.length; x++) {
      if (left[x] !== right[x]) {
        return false;
      }
    }
    return true;
  }

  public static collectionHash<T>(list: Iterable<T>): number {
    if (list == null) {
      return 0;
    }
    let hash: number = list.hashCode;
    for (var a in list) {
      hash += a.hashCode;
    }
    return hash;
  }

  public static isNotEquals(
    left: Iterable<any>,
    right: Iterable<any>
  ): boolean {
    return !CollectionUtils.isEquals(left, right);
  }

  public static getLargest<T extends Comparable<T>>(items: T[]): T {
    items.sort((one, two) => one.compareTo(two));
    return items.last;
  }
}
