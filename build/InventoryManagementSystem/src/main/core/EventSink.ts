import Sink from "./Sink";

export default class EventSink<T> extends Sink<T> {
  add(event: T): void {}

  addError(error: any): void {}

  close(): void {}
}
