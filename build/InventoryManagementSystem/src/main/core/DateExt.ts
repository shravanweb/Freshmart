export default class DateExt {
  static now(): Date {
    let mSec = Date.now();
    return new Date(mSec);
  }

  static parse(formattedString: string): Date {
    return new Date(formattedString);
  }
}
