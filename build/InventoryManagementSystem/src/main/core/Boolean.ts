export default class BooleanExt {}

declare global {
  interface BooleanConstructor {
    TRUE: boolean;
    FALSE: boolean;
  }
  interface Boolean {
    hashCode: number;

    and(other: boolean): boolean;

    or(other: boolean): boolean;

    not(): boolean;

    xor(other: boolean): boolean;

    toString(): string;
  }
}

Object.defineProperty(Boolean, "TRUE", {
  get: function () {
    return true;
  },
});

Object.defineProperty(Boolean, "FALSE", {
  get: function () {
    return false;
  },
});

Boolean.prototype.and = function (other: boolean) {
  return this.valueOf() && other;
};

Boolean.prototype.or = function (other: boolean) {
  return this.valueOf() || other;
};

Boolean.prototype.not = function () {
  return !this.valueOf();
};

Boolean.prototype.xor = function (other: boolean) {
  return this.valueOf() !== other;
};

// export default Boolean;
