import { Observable } from "rxjs";
import { Consumer, OneFunction, Supplier } from "../classes/core";
import Duration from "./Duration";

export default class FutureExt {}

declare global {
  interface PromiseConstructor {
    new <T>(computation: Supplier<T>): Promise<T>;

    microtask(computation: Supplier<any>): Promise<any>;

    sync(computation: Supplier<any>): Promise<any>;

    value(value?: any): Promise<any>;

    error(error: any): Promise<any>;

    delayed(duration: Duration, computation?: Supplier<any>): Promise<any>;

    wait<T>(
      futures: Iterable<Promise<T>>,
      params?: Partial<{ eagerError: boolean; cleanUp: Consumer<T> }>
    ): Promise<Array<T>>;

    forEach<T>(
      elements: Iterable<T>,
      action: OneFunction<T, any>
    ): Promise<any>;

    doWhile(action: Supplier<any>): Promise<any>;
  }
  interface Promise<T> {
    // TODO: Mostly existing then() will cover this
    // then<R>(onValue: OneFunction<T, any>, params?: Partial<{onError: OneFunction<any, any>}>): Promise<R>;

    catchError(
      onError: Consumer<any>,
      params?: Partial<{ test: OneFunction<any, boolean> }>
    ): Promise<T>;

    whenComplete(action: Supplier<any>): Promise<T>;

    asStream(): Observable<T>;

    timeout(
      timeLimit: Duration,
      params?: Partial<{ onTimeout: Supplier<any> }>
    ): Promise<T>;

    await: T;
  }
}

// TODO: How to give value for the new() thing?

Promise.microtask = function (c: Supplier<any>) {
  return Promise.resolve(c());
};

Promise.sync = function (c: Supplier<any>) {
  return Promise.resolve(c());
};

Promise.value = function (value?: any) {
  return Promise.resolve(value);
};

Promise.error = function (error: any) {
  return Promise.reject(error);
};

Promise.delayed = function (duration: Duration, computation?: Supplier<any>) {
  // TODO
  // return Promise.resolve(() => setTimeout(() => computation?.(), duration.inMilliseconds));
  return Promise.resolve(computation?.());
};

Promise.wait = function <T>(
  futures: Iterable<Promise<T>>,
  params?: Partial<{ eagerError: boolean; cleanUp: Consumer<T> }>
) {
  return Promise.all(futures);
};

Promise.forEach = function <T>(
  elements: Iterable<T>,
  action: OneFunction<T, any>
) {
  // TODO
  return Promise.resolve(null);
};

Promise.doWhile = function (action: Supplier<any>) {
  // TODO
  return Promise.resolve(null);
};

Promise.prototype.catchError = function (
  onError: Consumer<any>,
  params?: Partial<{ test: OneFunction<any, boolean> }>
) {
  return this.then(
    (value) => new Promise(() => value),
    (error) => {
      if (!params?.test || (params.test && params.test(error))) {
        // test returns true
        return Promise.resolve(onError);
      }
      return Promise.reject(error);
    }
  );
};

Promise.prototype.whenComplete = function (action: Supplier<any>) {
  return this.finally(action);
};
// Promise.prototype.asStream = function() {
//     //TODO: Maybe use RxJS.Observable here.
// }
Promise.prototype.timeout = function (
  timeLimit: Duration,
  params?: Partial<{ onTimeout: Supplier<any> }>
) {
  // TODO
  return this;
};

Object.defineProperty(Promise.prototype, "await", {
  get: function () {
    // TODO: Confirm that this works.
    return this.then((value: any) => value);
  },
});
