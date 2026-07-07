import Match from "./Match";

export default class RegExt {}

declare global {
  interface RegExpConstructor {
    escape(text: string): string;

    new (
      source: string,
      params?: { multiLine?: boolean; caseSensitive?: boolean }
    ): RegExp;
  }
  interface RegExp {
    firstMatch(input: string): Match;

    allMatches(input: string, start?: number): Iterable<Match>;

    hasMatch(input: string): boolean;

    stringMatch(input: string): string;

    pattern: string;

    isMultiLine: boolean;

    isCaseSensitive: boolean;

    matchAsPrefix(string: string, start?: number): Match; // <string param>.match(this); the method should be in the RegExp class
  }
}

RegExp.escape = function (text: string) {
  return ""; // TODO: Use lodash.escapeRegExp
};

RegExp.prototype.firstMatch = function (input: string) {
  let match = this.exec(input);
  if (!match) {
    return null;
  }
  return new Match(match?.[0], match?.slice(1), input, match?.index, this);
};

RegExp.prototype.allMatches = function (input: string, start?: number) {
  if (start) {
    input = input.substring(start);
  }
  return [...input.matchAll(this)]
    .filter((match) => match != null)
    .map(
      (match) =>
        new Match(match?.[0], match?.slice(1), input, match.index, this)
    );
};

RegExp.prototype.hasMatch = function (input: string) {
  return this.test(input);
};

RegExp.prototype.stringMatch = function (input: string) {
  return this.exec(input)?.[0] || "";
};

RegExp.prototype.matchAsPrefix = function (
  input: string,
  start?: number
): Match {
  if (start) {
    input = input.substring(start);
  }
  return this.firstMatch(input);
};

Object.defineProperty(RegExp.prototype, "pattern", {
  get: function () {
    return this.source;
  },
});

Object.defineProperty(RegExp.prototype, "isMultiLine", {
  get: function () {
    // TODO
    return false;
  },
});

Object.defineProperty(RegExp.prototype, "isCaseSensitive", {
  get: function () {
    // TODO
    return false;
  },
});

// export default RegExp;
