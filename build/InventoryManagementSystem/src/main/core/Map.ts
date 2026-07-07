import {
  BiFunction,
  BiPredicate,
  OneFunction,
  Supplier,
} from "../classes/core";
import MapExt from "../classes/MapExt";
import MapEntry from "../classes/MapEntry";

export default class CoreMapExt {}

declare global {
  interface MapConstructor {
    from(other: Map<any, any>): Map<any, any>;

    of<K, V>(other: Map<K, V>): Map<K, V>;

    unmodifiable(other: Map<any, any>): Map<any, any>;

    identity(): Map<any, any>;

    fromIterable<T, K, V>(
      iterable: Iterable<T>,
      param?: { key: OneFunction<T, K>; value: OneFunction<T, V> }
    ): Map<K, V>;

    fromIterables(keys: Iterable<any>, values: Iterable<any>): Map<any, any>;

    castFrom<K, V, K2, V2>(source: Map<K, V>): Map<K2, V2>;

    fromEntries(entries: Iterable<MapEntry<any, any>>): Map<any, any>;
  }
  interface Map<K, V> {
    cast<RK, RV>(): Map<RK, RV>;

    containsValue(value: any): boolean;

    containsKey(key: any): boolean;

    getOrDefault(key: any, def: V): V;

    map<K2, V2>(f: BiFunction<K, V, MapEntry<K2, V2>>): Map<K2, V2>;

    addEntries(newEntries: Iterable<MapEntry<K, V>>): void;

    update(
      key: K,
      update: OneFunction<V, V>,
      params?: Partial<{ ifAbsent: Supplier<V> }>
    ): V;

    updateAll(update: BiFunction<K, V, V>): void;

    removeWhere(predicate: BiPredicate<K, V>): void;

    putIfAbsent(key: K, ifAbsent: Supplier<V>): V;

    addAll(other: Map<K, V>): void;

    remove(key: any): V;

    length: number;

    isEmpty: boolean;

    isNotEmpty: boolean;
  }
}

Map.from = function (other: Map<any, any>) {
  return new Map([...other.entries()]);
};

Map.of = function <K, V>(other: Map<K, V>) {
  return new Map([...other.entries()]);
};

Map.unmodifiable = function (other: Map<any, any>) {
  return Map.from(other);
};

Map.identity = function () {
  // TODO
  return new Map();
};

Map.fromIterable = function <T, K, V>(
  iterable: Iterable<T>,
  param?: Partial<{ key: OneFunction<T, K>; value: OneFunction<T, V> }>
) {
  if (!param || !param.key || !param.value) {
    return new Map();
  }
  return new Map(
    iterable.map((one) => [param?.key?.(one), param?.value?.(one)])
  );
};

Map.fromIterables = function (
  keys: Iterable<any>,
  values: Iterable<any>
): Map<any, any> {
  let len = keys.length;
  let map = new Map();
  for (let i = 0; i < len; i++) {
    map.set(keys.elementAt(i), values.elementAt(i));
  }
  return map;
};

Map.castFrom = function <K, V, K2, V2>(source: Map<K, V>) {
  return source as unknown as Map<K2, V2>;
};

Map.fromEntries = function (entries: Iterable<MapEntry<any, any>>) {
  return new Map(entries.map((one) => [one.key, one.value]));
};

Map.prototype.cast = function <RK, RV>() {
  return this as Map<RK, RV>;
};

Map.prototype.containsKey = function (key: any): boolean {
  return Array.from(MapExt.keys(this)).includes(key);
};

Map.prototype.containsValue = function (value: any) {
  // TODO: Maybe some other way?
  return Array.from(MapExt.values(this)).includes(value);
};

Map.prototype.getOrDefault = function (key: any, def: any) {
  let val = this.get(key);
  return !val ? def : val;
};

Map.prototype.map = function <K, V, K2, V2>(
  f: BiFunction<K, V, MapEntry<K2, V2>>
) {
  return Map.fromEntries([...this.entries()].map((one) => f(one[0], one[1])));
};

Map.prototype.addEntries = function <K, V>(
  newEntries: Iterable<MapEntry<K, V>>
) {
  newEntries.forEach((one) => {
    this.set(one.key, one.value);
  });
};

Map.prototype.update = function <K, V>(
  key: K,
  update: OneFunction<V, V>,
  params?: Partial<{ ifAbsent: Supplier<V> }>
) {
  let value = this.get(key);
  if (!value) {
    if (!params || !params.ifAbsent) {
      throw new Error("Value not present for key: " + key);
    }
    value = params.ifAbsent();
  } else {
    value = update(value);
  }
  this.set(key, value);
  return value;
};

Map.prototype.updateAll = function <K, V>(update: BiFunction<K, V, V>) {
  // TODO: Correct? Maybe some other way?
  this.forEach((value, key) => {
    this.set(key, update(key, value));
  });
};

Map.prototype.removeWhere = function <K, V>(predicate: BiPredicate<K, V>) {
  // TODO: Maybe some other way?
  this.forEach((value, key) => {
    if (predicate(key, value)) {
      this.delete(key);
    }
  });
};

Map.prototype.putIfAbsent = function <K, V>(key: K, ifAbsent: Supplier<V>) {
  if (!this.get(key)) {
    this.set(key, ifAbsent());
  }
};

Map.prototype.addAll = function <K, V>(other: Map<K, V>) {
  this.addEntries(MapExt.entries(other));
};

Map.prototype.remove = function (key: any) {
  this.delete(key);
};

Object.defineProperty(Map.prototype, "length", {
  get: function () {
    return MapExt.keys(this).length;
  },
  configurable: true,
});

Object.defineProperty(Map.prototype, "isEmpty", {
  get: function () {
    return this.length === 0;
  },
});

Object.defineProperty(Map.prototype, "isNotEmpty", {
  get: function () {
    return !this.isEmpty;
  },
});
