import { BiFunction, Consumer, OneFunction, Supplier } from "../classes/core";

export default class IterableExt {}

declare global {
  // Have iterable as an abstract class
  interface IterableConstructor {
    castFrom<S, T>(source: Iterable<S>): Iterable<T>;

    generate<T>(count: number, generator?: OneFunction<number, T>): Iterable<T>;

    empty(): Iterable<any>;

    readonly prototype: Iterable<any>;
  }
  interface Iterable<T> {
    iterator: Iterator<T>;

    cast<R>(): Iterable<R>;

    followedBy(other: Iterable<T>): Iterable<T>;

    map<E>(f: OneFunction<T, E>): Iterable<E>;

    where(test: OneFunction<T, boolean>): Iterable<T>;

    expand<E>(f: OneFunction<T, Iterable<E>>): Iterable<E>;

    contains(element: T): boolean;

    forEach(f: Consumer<T>): void;

    reduce(combine: BiFunction<T, T, T>): T;

    fold<E>(intialValue: E, combine: BiFunction<E, T, E>): E;

    every(test: OneFunction<T, boolean>): boolean;

    join(seperator?: string): string;

    any(test: OneFunction<T, boolean>): boolean;

    toList(params?: { growable?: boolean }): Array<T>;

    toSet(): Set<T>;

    length: number;

    isEmpty: boolean;

    isNotEmpty: boolean;

    take(count: number): Iterable<T>;

    takeWhile(test: OneFunction<T, boolean>): Iterable<T>;

    skip(count: number): Iterable<T>;

    skipWhile(test: OneFunction<T, boolean>): Iterable<T>;

    first: T;

    last: T;

    single: T;

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

    toString(): string;
  }
  // var Iterable: IterableConstructor;
}
// Iterable.castFrom = function <S, T>(source: Iterable<S>) {
//     return [] as Iterable<T>;
// }
// Iterable.generate = function (count: number, generator?: OneFunction<number, T>) {
//     return Array();
// }
// Iterable.prototype.cast = function <R>() {
//     return Array();
// }
// Iterable.prototype.followedBy = function (other: Iterable<T>) {
//     return Array();
// }
// Iterable.prototype.map = function <E>(f: OneFunction<T, E>) {
//     //TODO
// }
// Iterable.prototype.where = function (test: OneFunction<T, boolean>) {
//     //TODO
// }
// Iterable.prototype.expand = function <E>(f: OneFunction<T, Iterable<E>>) {
//     //TODO
// }
// Iterable.prototype.contains = function (element: T) {
//     return ' ';
// }
// Iterable.prototype.forEach = function (f: Consumer<T>) {
//     //TODO
// }
// Iterable.prototype.reduce = function (combine: BiFunction<T, T, T>) {
//     return this[combine];
// }
// Iterable.prototype.fold = function <T>(intialValue: T, combine: BiFunction<E, T, E>) {
//     //TODO
// }
// Iterable.prototype.every = function (test: OneFunction<T, boolean>) {
//     return false;
// }
// Iterable.prototype.join = function (seperator?: string) {
//     return ' ';
// }
// Iterable.prototype.any = function (test: OneFunction<T, boolean>) {
//     return false;
// }
// Iterable.prototype.toList = function (params?: { growable?: boolean }) {
//     return new Array<T>();
// }
// Iterable.prototype.toSet = function () {
//     //TODO
// }
// Iterable.prototype.take = function (count: number) {
//     //TODO
// }
// Iterable.prototype.takeWhile = function (test: OneFunction<T, boolean>) {
//     //TODO
// }
// Iterable.prototype.skip = function (count: number) {
//     //TODO
// }
// Iterable.prototype.skipWhile = function (test: OneFunction<T, boolean>) {
//     //TODO
// }
// Iterable.prototype.firstWhere = function (test: OneFunction<T, boolean>, params?: { orElse?: Supplier<T> }) {
//     //TODO
// }
// Iterable.prototype.lastWhere = function (test: OneFunction<T, boolean>, params?: { orElse?: Supplier<T> }) {
//     //TODO
// }
// Iterable.prototype.singleWhere = function (test: OneFunction<T, boolean>, params?: { orElse?: Supplier<T> }) {
//     //TODO
// }
// Iterable.prototype.elementAt = function (index: number) {
//     return this[index];
// }
// Iterable.prototype.toString = function () {
//     return ' '
// }
// export default Iterable
