import Duration from "../core/Duration";
import DateTime from "../core/DateTime";

export default class Time {
  private static _ONE_DAY: number = 24 * 60 * 60 * 1000;
  private static _ONE_HOUR: number = 60 * 60 * 1000;
  private static _ONE_MIN: number = 60 * 1000;
  private _hour: number;
  private _minute: number;
  private _second: number;
  private _milli: number;

  private constructor() {}

  private static _init(): Time {
    let dt = DateTime.now();
    let t = new Time();
    t._hour = dt.hour;
    t._minute = dt.minute;
    t._second = dt.second;
    t._milli = dt.millisecond;
    return t;
  }

  public get hour(): number {
    return this._hour;
  }

  public get minutes(): number {
    return this._minute;
  }

  public get seconds(): number {
    return this._second;
  }

  public get millisecond(): number {
    return this._milli;
  }

  public toMillOfDay(): number {
    return (
      this._hour * Time._ONE_HOUR +
      this._minute * Time._ONE_MIN +
      this._second * 1000 +
      this._milli
    );
  }

  public isBefore(other: Time): boolean {
    let l = this.toMillOfDay();
    let r = other.toMillOfDay();
    return l < r;
  }

  public isAfter(other: Time): boolean {
    let l = this.toMillOfDay();
    let r = other.toMillOfDay();
    return l > r;
  }

  public isAtSameMomentAs(other: Time): boolean {
    let l = this.toMillOfDay();
    let r = other.toMillOfDay();
    return l == r;
  }

  public plusHours(hours: number): Time {
    return this._add(hours, "hours");
  }

  public plusMinutes(mins: number): Time {
    return this._add(mins, "minutes");
  }

  public plusSeconds(seconds: number): Time {
    return this._add(seconds, "seconds");
  }

  public plusMilliSeconds(milliSeconds: number): Time {
    return this._add(milliSeconds, "milliseconds");
  }

  public plusMicroSeconds(microSeconds: number): Time {
    return this._add(microSeconds, "microseconds");
  }

  private _add(quantity: number, what: string): Time {
    if (quantity == 0) {
      return this;
    }
    let q = 0;
    switch (what) {
      case "hours":
        q = quantity * 3600000;
        break;
      case "minutes":
        q = quantity * 60000;
        break;
      case "seconds":
        q = quantity * 1000;
        break;
      case "milliseconds":
        q = quantity;
        break;
      default:
        return this;
    }
    let copy = this.toMillOfDay() + q;
    return Time.fromMilli(copy);
  }

  public minus(other: Time) {
    return this.difference(other);
  }

  public toDateTime(): DateTime {
    let dt = DateTime.now();
    return new DateTime(
      dt.year,
      dt.month,
      dt.day,
      this.hour,
      this.minutes,
      this.seconds
    );
  }

  public static fromMilli(millisecond: number): Time {
    let t = new Time();
    let ms = millisecond % Time._ONE_DAY;
    if (ms < 0) {
      ms = Time._ONE_DAY - ms;
    }
    t._hour = Math.floor(ms / Time._ONE_HOUR);
    ms %= Time._ONE_HOUR;
    t._minute = Math.floor(ms / Time._ONE_MIN);
    ms %= Time._ONE_MIN;
    t._second = Math.floor(ms / 1000);
    ms %= 1000;
    t._milli = ms;
    return t;
  }

  public static of(
    hour: number,
    minute: number,
    second: number,
    millisecond: number
  ): Time {
    let t = new Time();
    t._hour = hour;
    t._minute = minute;
    t._second = second;
    t._milli = millisecond;
    return t;
  }

  public static now(): Time {
    return Time._init();
  }

  public equals(other: any): boolean {
    if (other instanceof Time) {
      return this.isAtSameMomentAs(other);
    }
    return false;
  }

  // operator -(other :Time) :Duration {
  //   return difference(other);
  // }

  public toString(): string {
    return this.hour + ":" + this.minutes + ":" + this.seconds;
  }

  public static parse(str: string): Time {
    let parts = str.split(":");
    return Time.of(
      Number.parseInt(parts[0]),
      Number.parseInt(parts[1]),
      Number.parseInt(parts[2]),
      0
    );
  }

  public get hashCode(): number {
    let ep = this.toMillOfDay();
    return (ep ^ (ep >> 30)) & 0x3fffffff;
  }

  difference(time: Time): Duration {
    let diff = this.toMillOfDay() - time.toMillOfDay();
    return new Duration({ milliseconds: diff });
  }
}
