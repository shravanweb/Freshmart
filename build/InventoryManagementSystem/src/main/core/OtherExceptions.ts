class FormatException extends Error {
  constructor(
    public message: string,
    public source: string,
    public offset: number
  ) {
    super(message);
  }
}

class IntegerDivisionByZeroException extends Error {
  constructor() {
    super();
  }
}

export { FormatException, IntegerDivisionByZeroException };
