import Duration from "./Duration";

export default class TimeoutException extends Error {
  constructor(message: string, public duration?: Duration) {
    super(message);
  }

  toString(): string {
    return super.toString();
  }
}
