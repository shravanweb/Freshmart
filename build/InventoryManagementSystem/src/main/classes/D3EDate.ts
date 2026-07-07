import Time from "./Time";
import DateTime from "../core/DateTime";
import { format } from "date-fns";
import Duration from "../core/Duration";
export default class D3EDate {
  private _date: Date;

  private _dateTime: DateTime;

  public constructor(_date: Date) {
    this._date = _date;
    this._dateTime = new DateTime(this.year, this.month, this.dayOfMonth);
  }

  public compareTo(other: D3EDate): number {
    if (this._date == null) {
      return 1;
    }
    if (other == null) {
      return -1;
    }
    return this._date > other._date ? 1 : this._date < other._date ? -1 : 0;
  }

  public get dayOfMonth(): number {
    return this._date.getDate();
  }

  public get dayOfWeek(): number {
    var val = this._date.getDay();
    return val > 0 ? val : 7;
  }

  public get day(): number {
    return this._date.getDate();
  }

  //Month getMonth();
  public get month(): number {
    return this._date.getMonth() + 1;
  }

  public get year(): number {
    return this._date.getFullYear();
  }

  public toString(): string {
    return format(this._date, "yyyy-MM-dd");
  }

  public get isLeapYear(): boolean {
    let year = this.year;
    if (year % 4 !== 0) {
      return false;
    }

    // Divisible by 4
    if (year % 100 !== 0) {
      return true;
    }

    // Divisible by 4 and 100
    return year % 400 === 0;
  }

  public static now(): D3EDate {
    return new D3EDate(new Date(Date.now()));
  }

  public static of(year: number, month: number, dayOfMonth: number): D3EDate {
    let d = new Date(year, month - 1, dayOfMonth);
    return new D3EDate(d);
  }

  public static parse(formattedString: string): D3EDate {
    return new D3EDate(new Date(formattedString));
  }

  difference(date: D3EDate): Duration {
    return this._dateTime.difference(date._dateTime);
  }

  public isBefore(other: D3EDate): boolean {
    return this._date < other._date;
  }

  public isAfter(other: D3EDate): boolean {
    return this._date > other._date;
  }

  public plusYears(years: number): D3EDate {
    let d = new Date(this._date);
    d.setFullYear(d.getFullYear() + years);
    return new D3EDate(d);
  }

  public plusMonths(months: number): D3EDate {
    let d = new Date(this._date);
    d.setMonth(d.getMonth() + months);
    return new D3EDate(d);
  }

  public plusWeeks(weeks: number): D3EDate {
    return this.plusDays(weeks * 7);
  }

  public plusDays(days: number): D3EDate {
    let d = new Date(this._date);
    d.setDate(d.getDate() + days);
    return new D3EDate(d);
  }

  /// LocalDate minus(TemporalAmount amountToSubtract);
  // LocalDate plus(TemporalAmount amountToAdd);

  public gt(other: D3EDate): boolean {
    return this._date > other._date;
  }
  public lt(other: D3EDate): boolean {
    return this._date < other._date;
  }
  public gte(other: D3EDate): boolean {
    return this._date >= other._date;
  }
  public lte(other: D3EDate): boolean {
    return this._date <= other._date;
  }
  public eq(other: D3EDate): boolean {
    if (other === null) {
      return false;
    }
    return this._toInt() === other._toInt();
  }
  _toInt(): number {
    return this.year * 10000 + this.month * 100 + this.day;
  }
  public ne(other: D3EDate): boolean {
    return !this.eq(other);
  }

  public toDateTime(time?: Time): DateTime {
    return new DateTime(
      this.year,
      this.month,
      this.day,
      time?.hour ?? 0,
      time?.minutes ?? 0,
      time?.seconds ?? 0,
      time?.millisecond ?? 0
    );
  }

  public static equals(first: D3EDate, second: D3EDate): boolean {
    return first === second || (first != null && first.eq(second));
  }
}
