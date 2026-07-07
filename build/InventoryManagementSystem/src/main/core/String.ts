import { OneFunction } from "../classes/core";
import Match from "./Match";
export default class StringExt {}
declare global {
  interface StringConstructor {
    fromCharCodes(
      charCodes: Iterable<number>,
      start?: number,
      end?: number
    ): string;

    fromEnvironment(
      name: string,
      params?: Partial<{ defaultValue: string }>
    ): string;
  }
  interface String {
    get(index: number): string;

    codeUnitAt(index: number): number;

    hashCode: number;

    equals(other: any): boolean;

    compareTo(other: string): number;

    isEmpty: boolean;

    isNotEmpty: boolean;

    plus(other: string): string;

    trimLeft(): string;

    trimRight(): string;

    times(times: number): string;

    padLeft(width: number, padding?: string): string;

    padRight(width: number, padding?: string): string;

    contains(other: string, startIndex?: number): boolean;

    replaceFirst(from: RegExp, to: string, startIndex?: number): string;

    replaceFirstMapped(
      from: RegExp,
      replace: OneFunction<Match, string>,
      startIndex?: number
    ): string;

    replaceAllMapped(from: RegExp, replace: OneFunction<Match, string>): string;

    replaceRange(start: number, end: number, replacement: string): string;

    splitMapJoin(
      pattern: RegExp,
      params?: Partial<{
        onMatch?: OneFunction<Match, string>;
        onNonMatch?: OneFunction<string, string>;
      }>
    ): string;

    codeUnits: Array<number>;

    allMatches(string: string, start?: number): Iterable<Match>;

    matchAsPrefix(string: string, start?: number): Match;

    containsIgnoreCase(secondString: string): boolean;
  }
}

String.fromCharCodes = function (
  charCodes: Iterable<number>,
  start?: number,
  end?: number
) {
  return String.fromCharCode(...charCodes);
};

String.fromEnvironment = function (
  name: string,
  params?: Partial<{ defaultValue: string }>
) {
  return params?.defaultValue || "";
};

String.prototype.get = function (index: number) {
  return this.charAt(index);
};

String.prototype.codeUnitAt = function (index: number) {
  return this.charCodeAt(index);
};

String.prototype.equals = function (other: any) {
  return Object.is(this, other);
};

String.prototype.compareTo = function (other: string) {
  if (this < other) {
    return -1;
  }
  if (this > other) {
    return 1;
  }
  return 0;
};

String.prototype.plus = function (other: string) {
  return this.valueOf() + other;
};

String.prototype.trimLeft = function () {
  return this.trimStart();
};

String.prototype.trimRight = function () {
  return this.trimEnd();
};

String.prototype.times = function (times: number) {
  return this.valueOf().repeat(times);
};

String.prototype.padLeft = function (width: number, padding?: string) {
  return this.valueOf().padStart(width, padding);
};

String.prototype.padRight = function (width: number, padding?: string) {
  return this.valueOf().padEnd(width, padding);
};

String.prototype.contains = function (other: string, startIndex?: number) {
  return this.includes(other, startIndex);
};

String.prototype.replaceFirst = function (
  from: RegExp,
  to: string,
  startIndex?: number
) {
  let value = this.valueOf();
  if (startIndex) {
    value = value.substring(startIndex);
  }
  let match = from.stringMatch(value);
  return this.replace(match, to);
};

String.prototype.replaceFirstMapped = function (
  from: RegExp,
  replace: OneFunction<Match, string>,
  startIndex?: number
) {
  let value = this.valueOf();
  if (startIndex) {
    value = value.substring(startIndex);
  }
  let match = from.firstMatch(this.valueOf());
  return this.replace(from, replace(match));
};

String.prototype.replaceAllMapped = function (
  from: RegExp,
  replace: OneFunction<Match, string>
) {
  // TODO
  let str = this.valueOf();
  from.allMatches(str).forEach((match) => {
    str = str.replace(match.match, replace(match));
  });
  return str;
};

String.prototype.replaceRange = function (
  start: number,
  end: number,
  replacement: string
) {
  return this.substring(0, start) + replacement + this.substring(end);
};

String.prototype.splitMapJoin = function (
  pattern: RegExp,
  params?: {
    onMatch?: OneFunction<Match, string>;
    onNonMatch?: OneFunction<string, string>;
  }
) {
  // TODO
  return "";
};

String.prototype.allMatches = function (input: string, start?: number) {
  return new RegExp(this.valueOf()).allMatches(input, start);
};

String.prototype.matchAsPrefix = function (input: string, start?: number) {
  return new RegExp(this.valueOf()).matchAsPrefix(input, start);
};

Object.defineProperty(String.prototype, "hashCode", {
  get: function () {
    // TODO
    return 0;
  },
});

Object.defineProperty(String.prototype, "isEmpty", {
  get: function () {
    return this.length === 0;
  },
});

Object.defineProperty(String.prototype, "isNotEmpty", {
  get: function () {
    return !this.isEmpty;
  },
});

Object.defineProperty(String.prototype, "codeUnits", {
  get: function () {
    // TODO: Is this correct?
    let result = [];
    for (let iter = 0; iter < this.length; iter++) {
      result.push(this.codeUnitAt(iter));
    }
    return result;
  },
});

String.prototype.containsIgnoreCase = function (secondString: string) {
  if (!secondString) {
    return false;
  }
  return this.toLowerCase().contains(secondString.toLowerCase());
};

// export default String;
