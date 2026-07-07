import DateTime from "../core/DateTime";
import DateFormat from "./DateFormat";

export class DateTimeExt {
  public static gt(a: DateTime, b: DateTime): boolean {
    return a.isAfter(b);
  }

  public static gte(a: DateTime, b: DateTime): boolean {
    return a.isAfter(b) || a === b;
  }

  public static lt(a: DateTime, b: DateTime): boolean {
    return a.isBefore(b);
  }

  public static lte(a: DateTime, b: DateTime): boolean {
    return a.isBefore(b) || a === b;
  }

  public static parseTime(time: string): DateTime {
    let dt: DateTime;
    try {
      dt = DateTime.parse(time);
    } catch (e) {
      try {
        let timeOnly: DateFormat = new DateFormat("HH:mm:ss");
        dt = timeOnly.parse(time);
      } catch (e1) {
        try {
          let timeOnly: DateFormat = new DateFormat("HH:mm:ss.SSS");
          dt = timeOnly.parse(time);
        } catch (e2) {}
      }
    }

    return dt;
  }

  public static parseDate(time: string): DateTime {
    let dt: DateTime;
    try {
      dt = DateTime.parse(time);
    } catch (e) {
      try {
        let dateOnly: DateFormat = new DateFormat("YYYY-MM-DD");
        dt = dateOnly.parse(time);
      } catch (e1) {}
    }

    return dt;
  }
}
