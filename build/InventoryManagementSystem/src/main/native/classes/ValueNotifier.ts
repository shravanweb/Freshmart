export class ValueNotifier<T> {
  value: T;
  constructor(val: T) {
    this.value = val;
  }
}
