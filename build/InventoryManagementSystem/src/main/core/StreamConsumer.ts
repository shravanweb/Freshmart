import { Observable } from "rxjs";

export default class StreamConsumer<S> {
  addStream(stream: Observable<S>): Promise<S> {
    return null;
  }

  close(): Promise<S> {
    return null;
  }
}
