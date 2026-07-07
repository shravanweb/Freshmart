import Duration from "./Duration";
import { Stopwatch } from "ts-stopwatch";

declare global {
  // interface Stopwatch {
  //     frequency: number;
  //     start(): void;
  //     stop(): void;
  //     reset(): void;
  //     elapsedTicks: number;
  //     elapsed: Duration;
  //     elapsedMicroseconds: number;
  //     elapsedMilliseconds: number;
  //     isRunning: boolean;
  // }
}

export default class StopwatchExt {
  static frequency(of: Stopwatch): number {
    // TODO
    return 1;
  }

  static elapsedTicks(of: Stopwatch): number {
    return of.getTime();
  }

  static elapsed(of: Stopwatch): Duration {
    let el = StopwatchExt.elapsedTicks(of);
    return new Duration({ seconds: el });
  }

  static elapsedMicroseconds(of: Stopwatch): number {
    return StopwatchExt.elapsed(of).inMicroseconds;
  }

  static elapsedMilliseconds(of: Stopwatch): number {
    return StopwatchExt.elapsed(of).inMilliseconds;
  }

  static isRunning(of: Stopwatch): boolean {
    return of.isRunning();
  }
}

// Stopwatch.prototype.start = function () {
//     this.push();
// }

// Stopwatch.prototype.stop = function () {
//     this.push();
// }

// Stopwatch.prototype.reset = function () {
//     this.push();
// }

// export default Stopwatch;
