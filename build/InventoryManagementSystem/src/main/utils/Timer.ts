import { Consumer, Runnable } from "../classes/core";
import Duration from "../core/Duration";

export default class Timer {
  private done: boolean;
  private id: any;

  public constructor(
    duration: Duration,
    callback: Runnable,
    periodic: boolean = false,
    periodicCallback?: Consumer<Timer>
  ) {
    if (!periodic) {
      let newRunnable = () => {
        this.done = true;
        callback();
      };
      this.id = setTimeout(newRunnable, duration.inMilliseconds);
    } else {
      let newRunnable = () => {
        periodicCallback?.(this);
      };
      this.id = setInterval(newRunnable, duration.inMilliseconds);
    }
  }

  static periodic(duration: Duration, callback: Consumer<Timer>): Timer {
    let t = new Timer(duration, null, true, callback);
    return t;
  }

  public get isActive(): boolean {
    return !this.done;
  }

  public get tick(): number {
    // Not sure
    return 0;
  }

  public cancel() {
    clearTimeout(this.id);
    this.done = true;
  }

  public static run(callback: Runnable) {
    new Timer(Duration.zero, callback);
  }
}
