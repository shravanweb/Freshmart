import Duration from "./Duration";
import DateExt from "./DateExt";
import D3EDate from "../classes/D3EDate";
import Time from "../classes/Time";

export default class DateTime {
  static readonly monday = 1;

  static readonly tuesday = 2;

  static readonly wednesday = 3;

  static readonly thursday = 4;

  static readonly friday = 5;

  static readonly saturday = 6;

  static readonly sunday = 7;

  static readonly daysPerWeek = 7;

  static readonly january = 1;

  static readonly febuary = 2;

  static readonly march = 3;

  static readonly april = 4;

  static readonly may = 5;

  static readonly june = 6;

  static readonly july = 7;

  static readonly august = 8;

  static readonly september = 9;

  static readonly october = 10;

  static readonly november = 11;

  static readonly december = 12;

  static readonly monthsPerYear = 12;

  public _date: Date;

  private utc: boolean;

  constructor(
    year: number = 0,
    month: number = 0,
    day: number = 1,
    hour: number = 0,
    minute: number = 0,
    second: number = 0,
    millisecond: number = 0,
    microsecond: number = 0,
    isUtc: boolean = false
  ) {
    if (microsecond) {
      let mSec = microsecond / 1000;
      if (!millisecond) {
        millisecond = 0;
      }
      millisecond += mSec;
    }
    this._date = isUtc
      ? new Date(
          Date.UTC(year, month - 1, day, hour, minute, second, millisecond)
        )
      : new Date(year, month - 1, day, hour, minute, second, millisecond);
    this.utc = isUtc;
  }

  static utc(
    year: number,
    month?: number,
    day?: number,
    hour?: number,
    minute?: number,
    second?: number,
    millisecond?: number,
    microsecond?: number
  ): DateTime {
    return new DateTime(
      year,
      month,
      day,
      hour,
      minute,
      second,
      millisecond,
      microsecond,
      true
    );
  }

  static parse(formattedString: string): DateTime {
    let dt = new DateTime();
    dt._date = DateExt.parse(formattedString);
    return dt;
  }

  static tryParse(formattedString: string): DateTime {
    return DateTime.parse(formattedString);
  }

  static now(): DateTime {
    let dt = new DateTime();
    dt._date = DateExt.now();
    return dt;
  }

  static fromDate(date: Date): DateTime {
    let dt = new DateTime();
    dt._date = date;
    return dt;
  }

  static fromMillisecondsSinceEpoch(
    millisecondsSinceEpoch: number,
    params?: { isUtc?: boolean }
  ): DateTime {
    let dt = new DateTime();
    dt._date = new Date(millisecondsSinceEpoch);
    dt.utc = params?.isUtc || false;
    return dt;
  }

  static fromMicrosecondsSinceEpoch(
    microsecondsSinceEpoch: number,
    params?: { isUtc?: boolean }
  ): DateTime {
    return DateTime.fromMillisecondsSinceEpoch(
      microsecondsSinceEpoch / 1000,
      params
    );
  }

  get isUtc(): boolean {
    return this.utc;
  }

  equals(other: Object): boolean {
    return this == other;
  }

  isBefore(other: DateTime): boolean {
    return this._date < other._date;
  }

  isAfter(other: DateTime): boolean {
    return this._date > other._date;
  }

  isAtSameMomentAs(other: DateTime): boolean {
    return !this.isBefore(other) && !this.isAfter(other);
  }

  compareTo(other: DateTime): number {
    if (this.isBefore(other)) {
      return -1;
    }
    if (this.isAfter(other)) {
      return 1;
    }
    return 0;
  }

  get hashCode(): number {
    // TODO
    return 0;
  }

  toLocal(): DateTime {
    if (!this.isUtc) return this;
    let dt = new DateTime();
    dt._date = new Date(
      this._date.getTime() + this._date.getTimezoneOffset() * 60000
    );
    return dt;
  }

  toUtc(): DateTime {
    if (this.isUtc) return this;
    let dt = new DateTime();
    dt._date = new Date(
      this._date.getTime() - this._date.getTimezoneOffset() * 60000
    );
    dt.utc = true;
    return dt;
  }

  toString(): string {
    return this._date.toString();
  }

  toIso8601String(): string {
    return this._date.toISOString();
  }

  add(duration: Duration): DateTime {
    let t = this._date.getTime();
    let total = t + duration.inMilliseconds;
    return DateTime.fromMillisecondsSinceEpoch(total);
  }

  subtract(duration: Duration): DateTime {
    let t = this._date.getTime();
    let total = t - duration.inMilliseconds;
    return DateTime.fromMillisecondsSinceEpoch(total);
  }

  difference(other: DateTime): Duration {
    let a = this._date.getTime();
    let b = other._date.getTime();
    return Duration._milliseconds(a - b);
  }

  get date(): D3EDate {
    return new D3EDate(this._date);
  }

  get millisecondsSinceEpoch(): number {
    return this._date.getTime();
  }

  get microsecondsSinceEpoch(): number {
    return this._date.getTime() * 1000;
  }

  get timeZoneName(): string {
    // TODO
    return "";
  }

  get timeZoneOffset(): Duration {
    // TODO
    return new Duration({});
  }

  get time(): Time {
    return Time.of(this.hour, this.minute, this.second, this.millisecond);
  }

  get year(): number {
    return this._date.getFullYear();
  }

  get month(): number {
    return this._date.getMonth() + 1;
  }

  get day(): number {
    return this._date.getDate();
  }

  get hour(): number {
    return this._date.getHours();
  }

  get minute(): number {
    return this._date.getMinutes();
  }

  get second(): number {
    return this._date.getSeconds();
  }

  get millisecond(): number {
    return this._date.getMilliseconds();
  }

  get microsecond(): number {
    return this._date.getMilliseconds() * 1000;
  }

  get weekday(): number {
    return this._date.getDay();
  }
}
