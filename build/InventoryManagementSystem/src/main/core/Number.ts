import RoundingMethod from "./RoundingMethod";

export default class NumberExt {}

declare global {
  interface NumberConstructor {
    readonly nan: number; // = 0.0 / 0.0;

    readonly infinity: number; // = 1.0 / 0.0;

    readonly negativeInfinity: number;

    readonly minPositive: number; // = 4.9E-324;

    readonly maxFinite: number; // = 1.7976931348623157E308;

    fromEnvironment(
      name: string,
      params?: Partial<{ defaultValue: number }>
    ): number;

    tryParseInt(value: string, params?: Partial<{ radix: number }>): number;

    tryParseDouble(value: string): number;
  }
  interface Number {
    readonly hashCode: number;

    readonly isNaN: boolean;

    readonly isNegative: boolean;

    readonly isInfinite: boolean;

    readonly isFinite: boolean;

    readonly isEven: boolean;

    readonly isOdd: boolean;

    readonly bitLength: number;

    readonly sign: number;

    equals(other: any): boolean;

    abs(): number;

    round(): number;

    round(rm: RoundingMethod): number;

    floor(): number;

    ceil(): number;

    truncate(): number;

    roundToDouble(): number;

    roundToDouble(rm: RoundingMethod): number;

    floorToDouble(): number;

    ceilToDouble(): number;

    truncateToDouble(): number;

    clamp(lowerLimit: number, upperLimit: number): number;

    toInt(): number;

    toDouble(): number;

    toStringAsFixed(fractionDigits: number): string;

    toStringAsExponential(fractionDigits?: number): string;

    toStringAsPrecision(precision: number): string;

    remainder(other: number): number;

    remainder(other: number, rm: RoundingMethod): number;

    plus(other: number): number;

    minus(other: number): number;

    times(other: number): number;

    rem(other: number): number;

    rem(other: number, rm: RoundingMethod): number;

    div(other: number): number;

    divToInt(other: number): number;

    negative(): number;

    lt(o: number): boolean;

    lte(o: number): boolean;

    gt(o: number): boolean;

    gte(o: number): boolean;

    compareTo(o: number): number;

    plus<T extends number>(other: T): T;

    minus<T extends number>(other: T): T;

    times<T extends number>(other: T): T;

    rem<T extends number>(other: T): T;

    div<T extends number>(other: T): T;

    band(other: number): number;

    bor(other: number): number;

    xor(other: number): number;

    com(): number;

    shl(shiftAmount: number): number;

    shr(shiftAmount: number): number;

    modPow(exponent: number, modplus: number): number;

    modInverse(modplus: number): number;

    gcd(other: number): number;

    toUnsigned(width: number): number;

    toSigned(width: number): number;

    negate(): number;

    toRadixString(radix: string): string;
  }
}

Object.defineProperty(Number, "nan", {
  get: function () {
    return Number.NaN;
  },
});

Object.defineProperty(Number, "infinity", {
  get: function () {
    return Number.POSITIVE_INFINITY;
  },
});

Object.defineProperty(Number, "negativeInfinity", {
  get: function () {
    return Number.NEGATIVE_INFINITY;
  },
});

Object.defineProperty(Number, "minPositive", {
  get: function () {
    return Number.MIN_VALUE;
  },
});

Object.defineProperty(Number, "maxFinite", {
  get: function () {
    return Number.MAX_VALUE;
  },
});

Number.fromEnvironment = function (
  name: string,
  params?: Partial<{ defaultValue: number }>
) {
  let num = +name;
  if (!isNaN(num)) {
    return num;
  }
  return params?.defaultValue || 0;
};

Number.tryParseInt = function (
  value: string,
  params?: Partial<{ radix: number }>
) {
  try {
    let num = parseInt(value, params?.radix || 10);
    return num;
  } catch (e) {
    return 0;
  }
};

Number.tryParseDouble = function (value: string) {
  try {
    let num = parseFloat(value);
    return num;
  } catch (e) {
    return 0.0;
  }
};

Object.defineProperty(Number.prototype, "hashCode", {
  get: function () {
    // TODO
    return 0;
  },
});

Object.defineProperty(Number.prototype, "isNaN", {
  get: function (): boolean {
    return Number.isNaN(this);
  },
});

Object.defineProperty(Number.prototype, "isNegative", {
  get: function (): boolean {
    return this < 0;
  },
});

Object.defineProperty(Number.prototype, "isInfinite", {
  get: function (): boolean {
    return !this.isFinite;
  },
});

Object.defineProperty(Number.prototype, "isFinite", {
  get: function (): boolean {
    return Number.isFinite(this);
  },
});

Object.defineProperty(Number.prototype, "isEven", {
  get: function (): boolean {
    return this % 2 == 0;
  },
});

Object.defineProperty(Number.prototype, "isOdd", {
  get: function (): boolean {
    return !this.isEven;
  },
});

Object.defineProperty(Number.prototype, "bitLength", {
  get: function (): number {
    // TODO
    return 0;
  },
});

Object.defineProperty(Number.prototype, "sign", {
  get: function (): number {
    if (this < 0) {
      return -1;
    }
    if (this > 0) {
      return 1;
    }
    return 0;
  },
});

Number.prototype.equals = function (other: any) {
  return this == other;
};

Number.prototype.abs = function () {
  return Math.abs(this.valueOf());
};

Number.prototype.round = function () {
  return Math.round(this.valueOf());
};

Number.prototype.floor = function () {
  return Math.floor(this.valueOf());
};

Number.prototype.ceil = function () {
  return Math.ceil(this.valueOf());
};

Number.prototype.truncate = function () {
  return Math.trunc(this.valueOf());
};

Number.prototype.roundToDouble = function () {
  // TODO
  return this.round();
};
Number.prototype.floorToDouble = function () {
  // TODO
  return this.floor();
};
Number.prototype.ceilToDouble = function () {
  // TODO
  return this.ceil();
};
Number.prototype.truncateToDouble = function () {
  // TODO
  return this.truncate();
};
Number.prototype.clamp = function (min: number, max: number) {
  return Math.min(Math.max(this.valueOf(), min), max);
};
Number.prototype.toInt = function () {
  return this.round();
};
Number.prototype.toDouble = function () {
  // TODO
  return this.round();
};
Number.prototype.toStringAsFixed = function (fractionDigits: number) {
  return this.valueOf().toFixed(fractionDigits);
};
Number.prototype.toStringAsExponential = function (fractionDigits: number) {
  return this.valueOf().toExponential(fractionDigits);
};
Number.prototype.toStringAsPrecision = function (precision: number) {
  // TODO
  return this.valueOf().toFixed(precision);
};

Number.prototype.remainder = function (other: number) {
  return this.valueOf() % other;
};

Number.prototype.plus = function (other: number) {
  return this.valueOf() + other;
};

Number.prototype.minus = function (other: number) {
  return this.valueOf() - other;
};

Number.prototype.times = function (other: number) {
  return this.valueOf() * other;
};

Number.prototype.rem = function (other: number) {
  return this.valueOf() % other;
};
Number.prototype.div = function (other: number) {
  return this.valueOf() / other;
};
Number.prototype.divToInt = function (other: number) {
  return Math.floor(this.div(other));
};
Number.prototype.negative = function () {
  return this.negate();
};
Number.prototype.lt = function (o: number) {
  return this.valueOf() < 0;
};
Number.prototype.lte = function (o: number) {
  return this.valueOf() <= 0;
};
Number.prototype.gt = function (o: number) {
  return this.valueOf() > 0;
};
Number.prototype.gte = function (o: number) {
  return this.valueOf() >= 0;
};
Number.prototype.compareTo = function (o: number) {
  let t = this.valueOf();
  return t < o ? -1 : t > 0 ? 1 : 0;
};

Number.prototype.band = function (other: number) {
  return this.valueOf() & other;
};
Number.prototype.bor = function (other: number) {
  return this.valueOf() | other;
};
Number.prototype.xor = function (other: number) {
  return this.valueOf() ^ other;
};
Number.prototype.com = function () {
  return ~this.valueOf();
};

Number.prototype.shl = function (shiftAmount: number) {
  return this.valueOf() << shiftAmount;
};
Number.prototype.shr = function (shiftAmount: number) {
  return this.valueOf() >>> shiftAmount;
};
Number.prototype.modPow = function (exponent: number, modplus: number) {
  // TODO
  return 0;
};
Number.prototype.modInverse = function (modplus: number) {
  // TODO
  return 0;
};
Number.prototype.gcd = function (other: number) {
  // TODO
  return 0;
};
Number.prototype.toUnsigned = function (width: number) {
  // TODO
  return this.valueOf();
};
Number.prototype.toSigned = function (width: number) {
  // TODO
  return this.valueOf();
};
Number.prototype.negate = function () {
  return -this.valueOf();
};
Number.prototype.toRadixString = function (radix: string) {
  return this.toString(Number.parseInt(radix));
};
