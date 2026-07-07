import { from, Observable, of } from "rxjs";
import {
  count,
  distinct,
  elementAt,
  every,
  expand,
  filter,
  find,
  first,
  isEmpty,
  last,
  map,
  reduce,
  single,
  skip,
  skipWhile,
  take,
  takeWhile,
  timeout,
  toArray,
} from "rxjs/operators";
import Duration from "./Duration";
import StreamTransformer from "./StreamTransformer";
import EventSink from "./EventSink";
import {
  BiFunction,
  Consumer,
  OneFunction,
  Runnable,
  Supplier,
} from "../classes/core";
import Test from "./Array";

export default class StreamExt {
  static t = new Test();
  static fromFuture<T>(future: Promise<T>): Observable<T> {
    return from(future);
  }

  static fromFutures<T>(futures: Iterable<Promise<T>>) {
    return from(futures);
  }

  static fromIterable<T>(elements: Iterable<T>) {
    return from(elements);
  }

  // TODO: Stream.periodic

  static isBroadcast(of: Observable<any>) {
    // TODO
    return false;
  }

  static listen<T>(
    on: Observable<T>,
    onData: Consumer<T>,
    params?: Partial<{
      onError: OneFunction<any, any>;
      onDone: Runnable;
      cancelOnError: boolean;
    }>
  ) {
    return on.subscribe({
      next: onData,
      error: params.onError,
      complete: params.onDone,
    });
  }

  static where<T>(on: Observable<T>, test: OneFunction<T, boolean>) {
    return on.pipe(filter(test));
  }

  static map<T, S>(on: Observable<T>, convert: OneFunction<T, S>) {
    return on.pipe(map(convert));
  }

  static asyncMap<T, E>(on: Observable<T>, convert: OneFunction<T, E>) {
    return on.pipe(map(convert));
  }

  static asyncExpand<T, E>(
    on: Observable<T>,
    convert: OneFunction<T, Observable<E>>
  ) {
    return on.pipe(expand(convert));
  }

  static handleError<T>(
    on: Observable<T>,
    onError: OneFunction<any, Object>,
    params?: { test: OneFunction<Object, boolean> }
  ): any {
    return null;
  }

  static expand<T, S>(on: Observable<T>, convert: OneFunction<T, Iterable<S>>) {
    return on.pipe(expand(convert));
  }

  // TODO: Maybe need to add pipe here

  static transform<T, S>(
    on: Observable<T>,
    streamTransformer: StreamTransformer<T, S>
  ): any {
    return null;
  }

  static reduce<T>(
    on: Observable<T>,
    combine: BiFunction<T, T, T>
  ): Promise<T> {
    return on.pipe(reduce(combine)).toPromise();
  }

  static fold<T, S>(
    what: Observable<T>,
    initialValue: S,
    combine: BiFunction<S, T, S>
  ): Promise<S> {
    return null;
  }

  join<T>(what: Observable<T>, separator?: string): Promise<string> {
    return null;
  }

  static contains(what: Observable<any>, needle: any): Promise<boolean> {
    return what.pipe(find((value) => Object.is(value, needle))).toPromise();
  }

  // forEach exists on Observable

  static every<T>(
    what: Observable<any>,
    test: OneFunction<T, boolean>
  ): Promise<boolean> {
    return what.pipe(every(test)).toPromise();
  }

  static async any<T>(
    what: Observable<any>,
    test: OneFunction<T, boolean>
  ): Promise<boolean> {
    let notTest = (val: T) => !test(val);
    const value = await what.pipe(every(notTest)).toPromise();
    return !value;
  }

  static toList<T>(what: Observable<T>): Promise<Array<T>> {
    return what.pipe(toArray()).toPromise();
  }

  static toSet<T>(what: Observable<T>): Promise<Set<T>> {
    return what
      .pipe(toArray())
      .toPromise()
      .then((list) => list.toSet());
  }

  static drain<T, E>(what: Observable<T>, futureValue?: E): Promise<E> {
    // TODO: Creating new Observable here.
    what.pipe(skipWhile((value) => true));
    return of(futureValue).toPromise();
  }

  static take(what: Observable<any>, count: number): Observable<any> {
    return what.pipe(take(count));
  }

  static takeWhile(
    what: Observable<any>,
    test: OneFunction<any, boolean>
  ): Observable<any> {
    return what.pipe(takeWhile(test));
  }

  static skip(what: Observable<any>, count: number): Observable<any> {
    return what.pipe(skip(count));
  }

  static skipWhile<T>(
    what: Observable<T>,
    test: OneFunction<T, boolean>
  ): Observable<T> {
    return what.pipe(skipWhile(test));
  }

  static distinct<T>(
    what: Observable<T>,
    equals: BiFunction<T, T, boolean>
  ): Observable<T> {
    // TODO
    return what;
    // return what.pipe(distinct(equals));
  }

  static firstWhere<T>(
    of: Observable<T>,
    test: OneFunction<T, boolean>,
    params?: { orElse: Supplier<T> }
  ): Promise<T> {
    return of
      .pipe(first(test, params == null ? null : params.orElse()))
      .toPromise();
  }

  static lastWhere<T>(
    of: Observable<T>,
    test: OneFunction<T, boolean>,
    params?: { orElse: Supplier<T> }
  ): Promise<T> {
    // TODO
    return of.pipe(last(test)).toPromise();
  }

  static singleWhere<T>(
    of: Observable<T>,
    test: OneFunction<T, boolean>,
    params?: { orElse: Supplier<T> }
  ): Promise<T> {
    // TODO
    return of.pipe(single(test)).toPromise();
  }

  static elementAt<T>(of: Observable<T>, index: number): Promise<T> {
    return of.pipe(elementAt(index)).toPromise();
  }

  static timeout<T>(
    of: Observable<T>,
    timeLimit: Duration,
    params?: { onTimeout: Consumer<EventSink<T>> }
  ): Observable<T> {
    // TODO: Is milliseconds correct?
    return of.pipe(timeout(timeLimit.inMilliseconds));
  }

  static getLength(what: Observable<any>): Promise<number> {
    return what.pipe(count()).toPromise();
  }

  static isEmpty(what: Observable<any>): Promise<boolean> {
    return what.pipe(isEmpty()).toPromise();
  }

  static first<T>(what: Observable<T>): Promise<T> {
    return what.pipe(first()).toPromise();
  }

  static last<T>(what: Observable<T>): Promise<T> {
    return what.pipe(last()).toPromise();
  }

  static single<T>(what: Observable<T>): Promise<T> {
    return what.pipe(single()).toPromise();
  }
}
