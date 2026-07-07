export default class Completer<T> {
  promise: Promise<T>;
  resolve: () => void;
  reject: () => void;
  completed: boolean = false;

  public constructor() {
    this.promise = new Promise((resolve, reject) => {
      this.resolve = resolve;
      this.reject = reject;
    });
  }

  public static sync<T>(): Completer<T> {
    return new Completer();
  }

  public get future(): Promise<T> {
    return this.promise;
  }

  public complete(value?: any): void {
    this.completed = true;
    this.resolve();
  }

  public completeError(error: any): void {
    this.reject();
  }

  public get isCompleted(): boolean {
    return this.completed;
  }
}
