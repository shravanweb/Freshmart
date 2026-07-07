import { OneFunction } from "../classes/core";

export default class QueueExt {}

declare global {
  // Abstract class?
  interface QueueConstructor {
    castFrom<S, E>(source: Queue<S>): Queue<E>;

    from(elements: Iterable<any>): Queue<any>;

    of<T>(elements: Iterable<T>): Queue<T>;
  }
  interface Queue<T> extends Iterable<T> {
    cast<R>(): Queue<R>;

    removeFirst(): T;

    addFirst(value: T): void;

    removeLast(): T;

    addLast(value: T): void;

    add(value: T): void;

    remove(value: Object): boolean;

    addAll(iterable: Iterable<T>): void;

    removeWhere(test: OneFunction<T, boolean>): void;

    retainWhere(test: OneFunction<T, boolean>): void;

    clear(): void;
  }
}

// Queue.castFrom = function (source: Queue<S>) {
//     return Queue<T>();
// }

// Queue.prototype.from = function (elements: Iterable) {
//     return Queue; //TODO
// }

// Queue.prototype.of = function (elements: Iterable<T>) {
//     return Queue; // TODO
// }

// Queue.prototype.cast = function <R>() {
//     return Queue<R>();
// }

// Queue.prototype.removeFirst = function () {
//     //TODO
// }

// Queue.prototype.addFirst = function (value: T) {
//     this.push(value);
// }

// Queue.prototype.removeLast = function () {
//     //TODO
// }

// Queue.ptototype.addLast = function (value: T) {
//     this.push(value);
// }

// Queue.prototype.add = function (value: T) {
//     this.push(value);
// }

// Queue.prototype.addAll = function (iterable: Iterable<T>) {
//     this.push(iterable);
// }

// Queue.prototype.remove = function (value: Object) {
//     return true;
// }

// Queue.prototype.removeWhere = function (test: OneFunction<T, boolean>) {
//     //TODO
// }

// Queue.prototype.retainWhere = function (test: OneFunction<T, boolean>) {
//     //TODO
// }

// Queue.prototype.clear = function () {
//     //TODO
// }

// export default Queue;
