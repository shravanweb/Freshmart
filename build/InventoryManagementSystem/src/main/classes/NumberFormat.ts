import format from "number-format.js";

export default class NumberFormat {
  private pattern: string;

  constructor(format: string) {
    this.pattern = format;
  }

  format(value: number) {
    try {
      return format(this.pattern, value);
    } catch (e) {
      return value.toString();
    }
  }
}
