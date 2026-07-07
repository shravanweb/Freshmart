export default class Log {
  constructor() {}

  static info(message: string) {
    console.log(message);
  }

  static error(message: string) {
    console.error(message);
  }

  static printStackTrace(error: Error) {
    console.error(error.message);
  }
}
