import { BiConsumer } from "../classes/core";
import MapEntry from "./MapEntry";

export default class MapExt {
  static keys<K, V>(on: Map<K, V>): Iterable<K> {
    return [...on.keys()] as Iterable<K>;
  }
  static values<K, V>(on: Map<K, V>): Iterable<V> {
    return [...on.values()] as Iterable<V>;
  }
  static entries<K, V>(on: Map<K, V>): Iterable<MapEntry<K, V>> {
    let x: Array<MapEntry<K, V>> = [];
    [...on.entries()].forEach((one: [K, V]) => {
      x.push({
        key: one[0],
        value: one[1],
      });
    });
    return x as Iterable<MapEntry<K, V>>;
  }
  static forEach<K, V>(on: Map<K, V>, f: BiConsumer<K, V>) {
    on.forEach((val, key) => f(key, val));
  }
}
