import { Observable, Subscription } from "rxjs";
import { BiFunction, OneFunction, BiConsumer, Consumer } from "../classes/core";
import EventSink from "./EventSink";
import StackTrace from "./StackTrace";

export default class StreamTransformer<S, T> {
  constructor(onListen: BiFunction<Observable<any>, boolean, Subscription>) {}

  static fromBind<S, T>(
    bind: OneFunction<Observable<S>, Observable<T>>
  ): StreamTransformer<S, T> {
    return new StreamTransformer(null);
  }

  // static fromHandlers<S, T>(params?:{handleData: BiConsumer<S, EventSink<T>>, handleError: Consumer3<Object, StackTrace, EventSink<T>>, handleDone: Consumer<EventSink<T>> }):StreamTransformer<S, T> {
  //     return new StreamTransformer(null);
  // }

  bind(stream: Observable<S>): Observable<T> {
    return null;
  }
}
