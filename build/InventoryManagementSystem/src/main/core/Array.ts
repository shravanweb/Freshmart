import { BiFunction, Consumer, OneFunction, Supplier } from "../classes/core";
import Random from "./Random";

export default class ArrayExt {}

declare global {
  // Caution: ArrayConstructor might not be generic.
  interface ArrayConstructor {
    castFrom<S, T>(source: Array<S>): Array<T>;

    copyRange<T>(
      target: Array<T>,
      at: number,
      source: Array<T>,
      start?: number,
      end?: number
    ): void;

    filled<T>(length: number, fill: T, growable?: boolean): Array<T>;

    from(elements: Iterable<any>, growable?: boolean): Array<any>;

    generate<T>(
      length: number,
      generator: OneFunction<number, T>,
      growable?: boolean
    ): Array<T>;

    of<T>(elements: Iterable<T>, growable?: boolean): Array<T>;

    unmodifiable(elements: Iterable<any>): Array<any>;

    writeIterable<T>(target: Array<T>, at: number, source: Iterable<T>): void;
  }

  interface Array<T> extends Iterable<T> {
    // export function Array<T>(length? :number): Array<T> {}
    cast<R>(): Iterable<R>;

    get(index: number): T;

    set(index: number, value: T): void;

    add(value: T): void;

    addAll(iterable: Iterable<T>): void;

    reversed: Array<T>;

    sort(compare: BiFunction<T, T, number>): void;

    shuffle(random?: Random): void;

    indexOf(element: T, start?: number): number;

    indexWhere(test: OneFunction<T, boolean>, start?: number): number;

    lastIndexWhere(test: OneFunction<T, boolean>, start?: number): number;

    lastIndexOf(element: T, start?: number): number;

    contains(element: T): boolean;

    clear(): void;

    insert(index: number, element: T): void;

    insertAll(index: number, iterable: Iterable<T>): void;

    setAll(index: number, iterable: Iterable<T>): void;

    remove(value: any): boolean;

    removeAt(index: number): T;

    removeLast(): T;

    removeWhere(test: OneFunction<T, boolean>): void;

    retainWhere(test: OneFunction<T, boolean>): void;

    plus(other: Array<T>): Array<T>;

    sublist(start: number, end?: number): Array<T>;

    getRange(start: number, end?: number): Iterable<T>;

    setRange(
      start: number,
      end: number,
      iterable: Iterable<T>,
      skipCount?: number
    ): void;

    removeRange(start: number, end: number): void;

    fillRange(start: number, end: number, fillValue?: T): void;

    replaceRange(start: number, end: number, replacement: Iterable<T>): void;

    asMap(): Map<number, T>;

    //mixin methods
    iterator: Iterator<T>;

    elementAt(index: number): T;

    followedBy(other: Iterable<T>): Iterable<T>;

    forEach(action: Consumer<T>): void;

    isEmpty: boolean;

    isNotEmpty: boolean;

    first: T;

    last: T;

    single: T;

    every(test: OneFunction<T, boolean>): boolean;

    any(test: OneFunction<T, boolean>): boolean;

    firstWhere(
      test: OneFunction<T, boolean>,
      params?: Partial<{ orElse: Supplier<T> }>
    ): T;

    lastWhere(
      test: OneFunction<T, boolean>,
      params?: Partial<{ orElse: Supplier<T> }>
    ): T;

    singleWhere(
      test: OneFunction<T, boolean>,
      params?: Partial<{ orElse: Supplier<T> }>
    ): T;

    join(separator?: string): string;

    where(test: OneFunction<T, boolean>): Array<T>;

    whereType<T>(): Iterable<T>;

    map<E>(f: OneFunction<T, E>): Iterable<E>;

    expand<E>(f: OneFunction<T, Iterable<E>>): Iterable<E>;

    reduce(combine: BiFunction<T, T, T>): T;

    fold<E>(initialValue: E, combine: BiFunction<E, T, E>): E;

    skip(count: number): Iterable<T>;

    skipWhile(test: OneFunction<T, boolean>): Iterable<T>;

    take(count: number): Iterable<T>;

    takeWhile(test: OneFunction<T, boolean>): Iterable<T>;

    toList(params?: Partial<{ growable: boolean }>): Array<T>;

    toSet(): Set<T>;

    toString(): string;

    groupBy<B, R>(by: OneFunction<T, B>, map: BiFunction<B, T[], R>): R[];
  }
}

Array.castFrom = function <S, T>(source: Array<S>): Array<T> {
  return source.map((one) => one as unknown as T).toList();
};

Array.copyRange = function (
  target,
  at: number,
  source,
  start?: number,
  end?: number
) {
  if (!start) {
    start = 0;
  }
  if (!end) {
    end = this.length;
  }
  if (start < 0 || start > this.length) {
    return;
  }
  if (end < 0 || end > this.length) {
    return;
  }
  if (start > end) {
    return;
  }
  let len = end - start;
  if (target.length < at + len) {
    return;
  }
  for (let i = 0; i < len; i++) {
    // For now
    target[at + i] = source[at + i];
  }
};

Array.generate = function <T>(
  length: number,
  generator: OneFunction<number, T>,
  growable?: boolean
): Array<T> {
  let arr: Array<T> = [];
  arr.length = length;
  for (let i = 0; i < length; i++) {
    arr[i] = generator(i);
  }
  return arr;
};

Array.filled = function <T>(
  length: number,
  fill: T,
  growable?: boolean
): Array<T> {
  let arr: Array<T> = [];
  arr.length = length;
  arr.fill(fill);
  return arr;
};

/*
    TODO: Array.from, Array.of
*/

Array.unmodifiable = function (elements: Iterable<any>): Array<any> {
  return [...elements];
};

Array.writeIterable = function <T>(
  target: Array<T>,
  at: number,
  source: Iterable<T>
) {
  //TODO
  let iter = at;
  while (iter < source.length) {
    if (iter < target.length) {
      target[iter] = source.elementAt(iter);
    } else {
      target.push(source.elementAt(iter));
    }
    iter++;
  }
};

Array.prototype.cast = function <R>() {
  let _to_copy = this.map((one) => one as R);
  return _to_copy;
};

Array.prototype.get = function (index: number) {
  return this[index];
};

Array.prototype.set = function (index: number, value) {
  if (index >= this.length) {
    return;
  }
  this[index] = value;
};

Array.prototype.add = function (value) {
  this.push(value);
};

Array.prototype.addAll = function <T>(iterable: Iterable<T>) {
  iterable.forEach((one: T) => this.add(one));
};

Object.defineProperty(Array.prototype, "reversed", {
  get: function () {
    return this.slice().reverse();
  },
  configurable: true,
});

// sort is already defined

Array.prototype.shuffle = function (random?: Random) {
  for (let i = this.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [this[i], this[j]] = [this[j], this[i]];
  }
};

// indexOf already defined

Array.prototype.indexWhere = function <T>(
  test: OneFunction<T, boolean>,
  start?: number
) {
  let arr = this;
  if (start) {
    arr = arr.slice(start);
  }
  return arr.findIndex(test);
};

Array.prototype.lastIndexWhere = function <T>(
  test: OneFunction<T, boolean>,
  start?: number
) {
  let arr = this;
  if (start) {
    arr = arr.slice(0, start + 1);
  }
  arr.reverse();
  return arr.indexWhere(test);
};

Array.prototype.lastIndexOf = function <T>(element: T, start?: number) {
  let arr = this;
  if (start) {
    arr = arr.slice(0, start + 1);
  }
  arr.reverse();
  return arr.indexOf(element);
};

Array.prototype.contains = function <T>(element: T) {
  return this.includes(element);
};

Array.prototype.clear = function () {
  this.length = 0;
};

Array.prototype.insert = function <T>(index: number, element: T) {
  if (index > this.length) {
    return;
  }
  this.splice(index, 0, element);
};

Array.prototype.insertAll = function <T>(index: number, iterable: Iterable<T>) {
  if (index > this.length) {
    return;
  }
  this.splice(index, 0, ...iterable);
};

Array.prototype.setAll = function <T>(index: number, iterable: Iterable<T>) {
  if (index > this.length) {
    return;
  }
  if (iterable.length > this.length - index) {
    return;
  }
  for (let _iter = 0; _iter < iterable.length; _iter++) {
    this[_iter + index] = iterable.elementAt(_iter);
  }
};

Array.prototype.remove = function (value: any): boolean {
  let _index = this.indexOf(value);
  if (_index == -1) {
    return false;
  }
  this.splice(_index, 1);
  return true;
};

Array.prototype.removeAt = function (index: number) {
  if (index < 0 || index >= this.length) {
    return null;
  }
  let val = this[index];
  this.splice(index, 1);
  return val;
};

Array.prototype.removeLast = function () {
  return this.removeAt(this.length - 1);
};

Array.prototype.removeWhere = function <T>(test: OneFunction<T, boolean>) {
  this.removeAt(this.indexWhere(test));
};

Array.prototype.retainWhere = function <T>(test: OneFunction<T, boolean>) {
  let inverse = (val: T) => !test(val);
  this.removeWhere(inverse);
};

Array.prototype.plus = function <T>(other: Array<T>) {
  return this.concat(other);
};

Array.prototype.sublist = function <T>(start: number, end?: number) {
  return this.slice(start, end);
};

Array.prototype.getRange = function <T>(start: number, end?: number) {
  return this.sublist(start, end);
};

Array.prototype.setRange = function <T>(
  start: number,
  end: number,
  iterable: Iterable<T>,
  skipCount?: number
) {
  if (start > end) {
    return;
  }
  let source = [...iterable];
  if (skipCount) {
    source = source.slice(skipCount);
  }
  this.splice(start, 0, ...source);
};

Array.prototype.removeRange = function (start: number, end: number) {
  if (start > end) {
    return;
  }
  this.splice(start, end - start + 1);
};

Array.prototype.fillRange = function <T>(
  start: number,
  end: number,
  fillValue?: T
) {
  let _fill = Array.filled(end - start + 1, fillValue);
  this.setRange(start, end, _fill);
};

Array.prototype.replaceRange = function <T>(
  start: number,
  end: number,
  replacement: Iterable<T>
) {
  this.setRange(start, end, replacement);
};

Array.prototype.asMap = function () {
  let _arr = this;
  return _arr.reduce(function (map, value) {
    map[_arr.indexOf(value)] = value;
  }, new Map());
};

Array.prototype.elementAt = function (index: number) {
  return this[index];
};

Array.prototype.followedBy = function <T>(other: Iterable<T>) {
  return this.concat(other);
};

// forEach already defined
// isEmpty: boolean;
Object.defineProperty(Array.prototype, "isEmpty", {
  get: function () {
    return this.length === 0;
  },
});

// isNotEmpty: boolean;
Object.defineProperty(Array.prototype, "isNotEmpty", {
  get: function () {
    return !this.isEmpty;
  },
});

// first: T;
Object.defineProperty(Array.prototype, "first", {
  get: function () {
    if (this.isEmpty) {
      return null;
    }
    return this[0];
  },
  set: function (value: any) {
    if (this.isEmpty) {
      this.push(value);
    } else {
      this[0] = value;
    }
  },
});

// last: T;
Object.defineProperty(Array.prototype, "last", {
  get: function () {
    if (this.isEmpty) {
      return null;
    }
    return this[this.length - 1];
  },
  set: function (value: any) {
    if (this.isEmpty) {
      this.push(value);
    } else {
      this[this.length - 1] = value;
    }
  },
});

// single: T;
Object.defineProperty(Array.prototype, "single", {
  get: function () {
    if (this.length > 1) {
      return null;
    }
    return this[0];
  },
});

// every already defined

Array.prototype.any = function <T>(test: OneFunction<T, boolean>) {
  return this.some(test);
};

Array.prototype.firstWhere = function <T>(
  test: OneFunction<T, boolean>,
  params?: Partial<{ orElse: Supplier<T> }>
) {
  let val = this.find(test);
  if (!val) {
    if (params?.orElse) {
      return params.orElse();
    }
  } else {
    return val;
  }
};

Array.prototype.lastWhere = function <T>(
  test: OneFunction<T, boolean>,
  params?: Partial<{ orElse: Supplier<T> }>
) {
  let arr = this.slice();
  arr.reverse();
  return arr.firstWhere(test, params);
};

Array.prototype.singleWhere = function <T>(
  test: OneFunction<T, boolean>,
  params?: Partial<{ orElse: Supplier<T> }>
) {
  //TODO: Do we return firstWhere here?
  return this.firstWhere(test, params);
};

// join already defiend

Array.prototype.where = function <T>(test: OneFunction<T, boolean>) {
  return this.filter(test);
};

Array.prototype.whereType = function <T>() {
  // TODO
  return this;
};

// map already defined

Array.prototype.expand = function <T, E>(f: OneFunction<T, Iterable<E>>) {
  return this.map(f)
    .map((one) => one.toList().flat() as E[])
    .toList()
    .flat();
};

// reduce already defined. TODO: Confirm.

Array.prototype.fold = function <T, E>(
  initialValue: E,
  combine: BiFunction<E, T, E>
) {
  return this.reduce(combine, initialValue);
};

Array.prototype.skip = function (count: number) {
  return this.slice(count);
};

Array.prototype.skipWhile = function <T>(test: OneFunction<T, boolean>) {
  let flip = (val: T) => !test(val);
  return this.filter(flip);
};

Array.prototype.take = function (count: number) {
  return this.slice(0, count);
};

Array.prototype.takeWhile = function <T>(test: OneFunction<T, boolean>) {
  return this.filter(test);
};

Array.prototype.toList = function (params?: Partial<{ growable: boolean }>) {
  return this;
};

Array.prototype.toSet = function () {
  return new Set(this);
};

Array.prototype.groupBy = function <T, B, R>(
  by: OneFunction<T, B>,
  map: BiFunction<B, T[], R>
) {
  let groups: Map<B, T[]> = new Map();
  for (let e of this) {
    // Group each value
    let group: B = by(e);
    let list: T[] = groups.get(group);
    if (list == null) {
      // Add to map
      list = [];
      groups.set(group, list);
    }
    list.add(e);
  }

  // Apply map logic and return
  let result: R[] = [];
  groups.forEach((v, k) => {
    result.add(map(k, v));
  });
  return result;
};

// toString already defined
