import { Subscription } from "rxjs";
import { Consumer, OneFunction, Runnable } from "../classes/core";

export default class SubscriptionExt {
  static cancel(what: Subscription): Promise<any> {
    what.unsubscribe();
    return null;
  }

  static onData<T>(what: Subscription, handleData: Consumer<T>): void {}

  static onError(
    what: Subscription,
    handleError: OneFunction<any, any>
  ): void {}

  static onDone(what: Subscription, handleDone: Runnable): void {}

  static pause<T>(what: Subscription, resumeSignal?: Promise<T>): void {}

  static resume(what: Subscription): void {}

  static asFuture<E>(what: Subscription, futureValue?: E): Promise<E> {
    return null;
  }
}
