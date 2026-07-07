import { Observable } from "rxjs";

interface StreamIteratorConstructor {
  new (stream: Observable<any>): StreamIterator<any>;
}
interface StreamIterator<T> {
  moveNext(): Promise<boolean>;

  current: T;

  cancel(): Promise<T>;
}
export var StreamIterator: StreamIteratorConstructor;
