export default class BigIntExt {}

declare global {
  interface BigIntConstructor {
    parse(source: string, radix?: number): BigInt;

    tryParse(source: string, radix?: number): BigInt;

    from(value: number): BigInt;

    zero: BigInt;

    one: BigInt;

    two: BigInt;
  }
  interface BigInt {
    // these are supposed to be fields see bigint in core

    abs(): BigInt;

    negative(): BigInt;

    plus(other: BigInt): BigInt;

    minus(of: BigInt): BigInt;

    times(other: BigInt): BigInt;

    div(other: BigInt): BigInt;

    divToInt(other: BigInt): BigInt;

    rem(other: BigInt): BigInt;

    remainder(other: BigInt): BigInt;

    shl(shiftAmount: number): BigInt;

    shr(shiftAmount: number): BigInt;

    band(other: number): BigInt;

    bor(other: number): BigInt;

    xor(other: number): BigInt;

    com(): BigInt;

    compareTo(other: BigInt): number;

    bitLength: number;

    sign: number; //TODO this is getter supposed originally in core

    isEven: boolean; //TODO this is getter supposed originally in core

    isOdd: boolean; //TODO this is getter supposed originally in core

    isNegative: boolean; //TODO this is getter supposed originally in core

    pow(exponent: number): BigInt;

    modPow(exponent: BigInt, modulus: BigInt): BigInt;

    modInverse(modulus: BigInt): BigInt;

    gcd(other: BigInt): BigInt;

    toUnsigned(width: number): BigInt;

    toSigned(width: number): BigInt;

    isValidInt: boolean; //TODO this is getter supposed originally in core

    toInt(): number;

    toDouble(): number;

    toString(): string;

    toRadixString(radix: number): string;
  }
}

Object.defineProperty(BigInt, "zero", {
  get: function () {
    return BigInt(0);
  },
});

Object.defineProperty(BigInt, "one", {
  get: function () {
    return BigInt(1);
  },
});

Object.defineProperty(BigInt, "two", {
  get: function () {
    return BigInt(2);
  },
});

BigInt.parse = function (source: string, radix?: number) {
  return BigInt(source);
};

BigInt.tryParse = function (source: string, radix?: number): BigInt {
  return BigInt(source);
};

BigInt.from = function (value: number): BigInt {
  return BigInt(value);
};

BigInt.prototype.abs = function () {
  return BigInt.zero;
};

BigInt.prototype.negative = function () {
  return BigInt.zero;
};

BigInt.prototype.plus = function (other: BigInt) {
  return BigInt.zero;
};

BigInt.prototype.minus = function (of: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.times = function (other: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.div = function (other: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.divToInt = function (other: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.rem = function (other: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.remainder = function (other: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.shl = function (other: number) {
  return BigInt.zero;
};
BigInt.prototype.shr = function (shiftAmount: number) {
  return BigInt.zero;
};
BigInt.prototype.band = function (other: number) {
  return BigInt.zero;
};
BigInt.prototype.bor = function (other: number) {
  return BigInt.zero;
};
BigInt.prototype.xor = function (other: number) {
  return BigInt.zero;
};
BigInt.prototype.com = function () {
  return BigInt.zero;
};
BigInt.prototype.compareTo = function (other: BigInt) {
  return 0;
};

Object.defineProperty(BigInt.prototype, "bitLength", {
  get: function () {
    return 0;
  },
});
Object.defineProperty(BigInt.prototype, "sign", {
  get: function () {
    return 0;
  },
});
Object.defineProperty(BigInt.prototype, "isEven", {
  get: function () {
    return false;
  },
});
Object.defineProperty(BigInt.prototype, "isOdd", {
  get: function () {
    return false;
  },
});
Object.defineProperty(BigInt.prototype, "isNegative", {
  get: function () {
    return false;
  },
});
Object.defineProperty(BigInt.prototype, "isValidInt", {
  get: function () {
    return false;
  },
});
BigInt.prototype.pow = function (exponent: number) {
  return BigInt.zero;
};
BigInt.prototype.modPow = function (exponent: BigInt, modulus: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.modInverse = function (modulus: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.gcd = function (other: BigInt) {
  return BigInt.zero;
};
BigInt.prototype.toUnsigned = function (width: number) {
  return BigInt.zero;
};
BigInt.prototype.toSigned = function (width: number) {
  return BigInt.zero;
};
BigInt.prototype.toInt = function () {
  return 0;
};
BigInt.prototype.toDouble = function () {
  return 0;
};
BigInt.prototype.toRadixString = function (radix: number) {
  return this.toString();
};
