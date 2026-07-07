import { Consumer, OneFunction } from "../classes/core";

var Deque = require("collections/deque");

export default class ArrayQueue<T> extends Deque {
  constructor(initialCapacity?: number) {
    super();
  }

  static from<T>(elements: Iterable<T>): ArrayQueue<T> {
    return new Deque(...elements) as unknown as ArrayQueue<T>;
  }

  static of<T>(elements: Iterable<T>): ArrayQueue<T> {
    return ArrayQueue.from(elements);
  }

  cast<R>(): ArrayQueue<R> {
    return this as unknown as ArrayQueue<R>;
  }

  iterator: Iterator<T>;

  forEach(f: Consumer<T>): void {
    this.toArray().forEach(f);
  }

  get isEmpty(): boolean {
    return this.length === 0;
  }

  get first(): T {
    return this.get(0);
  }

  get last(): T {
    return this.get(this.length - 1);
  }

  get single(): T {
    return this.get(0);
  }

  elementAt(index: number): T {
    return this.get(index);
  }

  toList(params?: { growable: boolean }): Array<T> {
    return this.toArray();
  }

  addAll(elements: Iterable<T>): void {
    this.addEach(elements);
  }

  removeWhere(test: OneFunction<T, boolean>): void {
    return this.toArray().removeWhere(test);
  }

  retainWhere(test: OneFunction<T, boolean>): void {
    return this.toArray().retainWhere(test);
  }

  toString(): string {
    return this.toArray().toString();
  }

  addLast(value: T): void {
    this.push(value);
  }

  addFirst(value: T): void {
    this.unshift(value);
  }

  removeFirst(): T {
    return this.shift();
  }

  removeLast(): T {
    return this.pop();
  }
}
