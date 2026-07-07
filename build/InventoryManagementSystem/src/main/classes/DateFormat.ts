import DateTime from "../core/DateTime";
import * as fns from "date-fns";

export default class DateFormat {
  _format: string;

  parse(time: string): DateTime {
    let d = fns.parse(time, this._format, 0);
    if (Number.isNaN(d.getTime())) {
      return null;
    }
    return DateTime.fromDate(d);
  }

  constructor(fmt: string) {
    this._format = fmt;
  }

  static yMd(): DateFormat {
    return new DateFormat("yMd");
  }

  static jm(): DateFormat {
    return new DateFormat("jm");
  }

  add_jm(): DateFormat {
    // TODO
    return null;
  }

  format(date: DateTime): string {
    return fns.format(date._date, this._format);
  }
}
