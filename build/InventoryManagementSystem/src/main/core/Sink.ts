export default class Sink<T> {
  add(data: T): void {}

  close(): void {}
}
