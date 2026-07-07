import { BiFunction, Consumer, OneFunction, Supplier } from "../classes/core";
export default class SetExt {}
declare global {
  interface SetConstructor {
    castfrom<S, T>(source: Set<S>): Set<T>;

    identity<T>(): Set<T>;

    from<T>(elements: Iterable<T>): Set<T>;

    of<T>(elements: Iterable<T>): Set<T>;
  }
  interface Set<T> extends Iterable<T> {
    cast<R>(): Set<R>;

    iterator: Iterator<T>;

    contains(value: any): boolean;

    add(value: T): boolean;

    addAll(iterable: Iterable<T>): void;

    remove(value: any): boolean;

    removeAll<T>(elements: Iterable<any>): void;

    retainAll<T>(elements: Iterable<any>): void;

    removeWhere(test: OneFunction<T, boolean>): void;

    retainWhere(test: OneFunction<T, boolean>): void;

    containsAll(other: Iterable<any>): boolean;

    intersection(other: Set<any>): Set<T>;

    union(other: Set<T>): Set<T>;

    difference(other: Set<any>): Set<T>;

    clear(): void;

    toSet(): Set<T>;

    length: number;

    isEmpty: boolean;

    isNotEmpty: boolean;

    followedBy(other: Iterable<T>): Iterable<T>;

    toList(params?: Partial<{ growable: boolean }>): Array<T>;

    map<E>(f: OneFunction<T, E>): Iterable<E>;

    single: T;

    toString(): string;

    where(f: OneFunction<T, boolean>): Iterable<T>;

    expand<E>(f: OneFunction<T, Iterable<E>>): Iterable<E>;

    forEach(f: Consumer<T>): void;

    reduce(combine: BiFunction<T, T, T>): T;

    fold<E>(initialValue: E, combine: BiFunction<E, T, E>): E;

    every(f: OneFunction<T, boolean>): boolean;

    join(seperator?: string): string;

    any(test: OneFunction<T, boolean>): boolean;

    take(n: number): Iterable<T>;

    takeWhile(test: OneFunction<T, boolean>): Iterable<T>;

    skip(n: number): Iterable<T>;

    skipWhile(test: OneFunction<T, boolean>): Iterable<T>;

    first: T;

    last: T;

    firstWhere(
      test: OneFunction<T, boolean>,
      params?: { orElse?: Supplier<T> }
    ): T;

    lastWhere(
      test: OneFunction<T, boolean>,
      params?: { orElse?: Supplier<T> }
    ): T;

    singleWhere(
      test: OneFunction<T, boolean>,
      params?: { orElse?: Supplier<T> }
    ): T;

    elementAt(index: number): T;
  }
}

Set.identity = function () {
  return new Set();
};

Set.from = function <T>(elements: Iterable<T>) {
  return new Set(elements);
};

Set.of = function <T>(elements: Iterable<T>) {
  return new Set<T>(elements);
};

Set.castfrom = function <S, T>(source: Set<S>) {
  return new Set([...source].map((one) => one as unknown as T));
};

Set.prototype.cast = function <R>() {
  return new Set([...this].map((one) => one as R));
};

Set.prototype.contains = function (value: any) {
  return this.has(value);
};

// add already defined

Set.prototype.addAll = function <T>(elements: Iterable<T>) {
  elements.forEach((one) => this.add(one));
};

Set.prototype.remove = function (value: any) {
  return this.delete(value);
};

Set.prototype.removeAll = function <T>(elements: Iterable<any>) {
  elements.forEach((one) => this.remove(one));
};

Set.prototype.retainAll = function <T>(elements: Iterable<any>) {
  [...this].retainWhere((one) => elements.contains(one));
};

Set.prototype.removeWhere = function <T>(test: OneFunction<T, boolean>) {
  [...this].removeWhere(test);
};

Set.prototype.retainWhere = function (test: OneFunction<any, boolean>) {
  [...this].retainWhere(test);
};

Set.prototype.containsAll = function (other: Iterable<any>) {
  this.forEach((one: any) => {
    if (!other.contains(one)) {
      return false;
    }
  });
  return true;
};

Set.prototype.intersection = function (other: Set<any>) {
  return new Set([...this].filter((one) => other.has(one)));
};

Set.prototype.union = function (other: Set<any>) {
  let target = new Set(this);
  target.addAll(other);
  return target;
};

Set.prototype.difference = function (other: Set<any>) {
  let target = new Set(this);
  target.removeWhere((one: any) => other.has(one));
  return target;
};

// clear already defined

Set.prototype.toSet = function () {
  return new Set(this);
};

// length
Object.defineProperty(Set.prototype, "length", {
  get: function () {
    return this.size;
  },
});

// TODO: Change if these are wrong.
// isEmpty
Object.defineProperty(Set.prototype, "isEmpty", {
  get: function () {
    return this.length == 0;
  },
});

// isNotEmpty
Object.defineProperty(Set.prototype, "isNotEmpty", {
  get: function () {
    return !this.isEmpty;
  },
});

Set.prototype.followedBy = function <T>(other: Iterable<T>) {
  return new Set([...this].followedBy(other));
};

Set.prototype.toList = function (params?: Partial<{ growable: boolean }>) {
  return [...this];
};

Set.prototype.map = function <T, E>(f: OneFunction<E, T>): Iterable<T> {
  return new Set([...this].map(f));
};

Object.defineProperty(Set.prototype, "single", {
  get: function () {
    if (this.length > 1) {
      //TODO: Throw here?
      return null;
    }
    return this.elementAt(0);
  },
});

// toString already defined on Object in JS
Set.prototype.where = function <E>(f: OneFunction<E, boolean>) {
  return new Set([...this].where(f));
};

Set.prototype.expand = function <T, E>(
  f: OneFunction<E, Iterable<T>>
): Iterable<T> {
  return new Set([...this].expand(f));
};

// forEach already defined

Set.prototype.reduce = function <T>(combine: BiFunction<T, T, T>) {
  return [...this].reduce(combine);
};

Set.prototype.fold = function <T, E>(
  initialValue: E,
  combine: BiFunction<E, T, E>
) {
  return [...this].fold(initialValue, combine);
};

Set.prototype.every = function <T>(f: OneFunction<T, boolean>) {
  return [...this].every(f);
};

Set.prototype.join = function (seperator?: string) {
  return [...this].join(seperator);
};

Set.prototype.any = function <T>(test: OneFunction<T, boolean>) {
  return [...this].any(test);
};

Set.prototype.take = function (n: number) {
  return new Set([...this].take(n));
};

Set.prototype.takeWhile = function <T>(test: OneFunction<T, boolean>) {
  return new Set([...this].takeWhile(test));
};

Set.prototype.skip = function (n: number) {
  return new Set([...this].skip(n));
};

Set.prototype.skipWhile = function <T>(test: OneFunction<T, boolean>) {
  return new Set([...this].skipWhile(test));
};

// first: T;
Object.defineProperty(Set.prototype, "first", {
  get: function () {
    if (this.isEmpty) {
      return null;
    }
    return this[0];
  },
});

// last: T;
Object.defineProperty(Set.prototype, "last", {
  get: function () {
    if (this.isEmpty) {
      return null;
    }
    return this[this.length - 1];
  },
});

Set.prototype.firstWhere = function <T>(
  test: OneFunction<T, boolean>,
  params?: { orElse?: Supplier<T> }
) {
  return [...this].firstWhere(test, params);
};

Set.prototype.lastWhere = function <T>(
  test: OneFunction<T, boolean>,
  params?: { orElse?: Supplier<T> }
) {
  return [...this].lastWhere(test, params);
};

Set.prototype.singleWhere = function <T>(
  test: OneFunction<T, boolean>,
  params?: { orElse?: Supplier<T> }
) {
  return [...this].singleWhere(test, params);
};

Set.prototype.elementAt = function (index: number) {
  return [...this].elementAt(index);
};

// export default Set;
