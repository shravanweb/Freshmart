import { type } from "os";
import RoundingMethod from "./RoundingMethod";
import bigDecimal from "js-big-decimal";

export default class Decimal {
  value: bigDecimal;

  private constructor(value: number | bigDecimal | string) {
    if (typeof value === "number" || typeof value === "string") {
      this.value = new bigDecimal(value);
    } else {
      this.value = value;
    }
  }

  static parse(value: string): Decimal {
    return new Decimal(value);
  }

  static fromInt(value: number): Decimal {
    return new Decimal(value);
  }

  static tryParse(value: string): Decimal {
    try {
      return new Decimal(value);
    } catch (error) {
      return new Decimal("0");
    }
  }

  private static _toDouble(dec: Decimal) {
    return parseFloat(dec.value.getValue());
  }

  private static _toInt(dec: Decimal) {
    return parseInt(dec.value.getValue());
  }

  equals(other: Decimal): boolean {
    return this.value.compareTo(other.value) === 0;
  }

  lt(other: Decimal): boolean {
    return this.value.compareTo(other.value) < 0;
  }

  lte(other: Decimal): boolean {
    return this.value.compareTo(other.value) <= 0;
  }

  gt(other: Decimal): boolean {
    return this.value.compareTo(other.value) > 0;
  }

  gte(other: Decimal): boolean {
    return this.value.compareTo(other.value) >= 0;
  }

  add(other: Decimal): Decimal {
    return new Decimal(this.value.add(other.value));
  }

  subtract(other: Decimal): Decimal {
    return new Decimal(this.value.subtract(other.value));
  }

  multiply(other: Decimal): Decimal {
    return new Decimal(this.value.multiply(other.value));
  }

  private _divide(
    other: Decimal,
    rm: RoundingMethod,
    precision: number = 0
  ): Decimal {
    let newVal = this.value.divide(other.value, precision);
    newVal.round(precision, RoundingMethod[RoundingMethod[rm]]);
    return new Decimal(newVal);
  }

  divide(
    other: Decimal,
    rm: RoundingMethod = RoundingMethod.HALF_EVEN
  ): Decimal {
    return this._divide(other, rm, 2);
  }

  divideTrunc(other: Decimal, rm: RoundingMethod): Decimal {
    return this._divide(other, rm);
  }

  rem(other: Decimal, rm: RoundingMethod): Decimal {
    return new Decimal(this.value.modulus(other.value));
  }

  neg(other: Decimal): Decimal {
    return new Decimal(this.value.negate());
  }

  remainder(other: Decimal, rm: RoundingMethod): Decimal {
    return this.rem(other, rm);
  }

  abs(): Decimal {
    return new Decimal(
      this.value.compareTo(new bigDecimal("0")) > 0
        ? this.value
        : this.value.negate()
    );
  }

  pow(exponent: number, rm: RoundingMethod): Decimal {
    if (exponent === 0) {
      return new Decimal("0");
    }
    if (exponent > 0) {
      let val = this.value;
      let newVal = this.value;
      for (let i = 1; i < exponent; i++) {
        newVal = newVal.multiply(val);
      }
      return new Decimal(newVal);
    }
    let one = new Decimal("1");
    let val = this.value;
    let newVal = this.value;
    for (let i = -1; i > exponent; i--) {
      newVal = newVal.multiply(val);
    }
    return one.divide(new Decimal(newVal), rm);
  }

  floor(): Decimal {
    return new Decimal(this.value.floor());
  }

  floorToDouble(): number {
    return Decimal._toDouble(this.floor());
  }

  ceil(): Decimal {
    return new Decimal(this.value.ceil());
  }

  ceilToDouble(): number {
    return Decimal._toDouble(this.ceil());
  }

  private _round(
    precision?: number,
    rm: RoundingMethod = RoundingMethod.HALF_EVEN
  ) {
    return new Decimal(
      this.value.round(
        precision,
        bigDecimal.RoundingModes[bigDecimal.RoundingModes[rm]]
      )
    );
  }

  round(rm: RoundingMethod): Decimal {
    return this._round(2, rm);
  }

  roundToDouble(rm: RoundingMethod): number {
    return Decimal._toDouble(this.round(rm));
  }

  truncate(): Decimal {
    return this._round();
  }

  truncateToDouble(): number {
    return Decimal._toDouble(this.truncate());
  }

  clamp(lowerLimit: Decimal, upperLimit: Decimal): Decimal {
    return new Decimal(
      Math.min(
        Math.max(Decimal._toInt(this), Decimal._toInt(lowerLimit)),
        Decimal._toInt(upperLimit)
      )
    );
  }

  get isInteger(): boolean {
    return this.toDouble() % 1 === 0;
  }

  get inverse(): Decimal {
    return Decimal.fromInt(1).divide(this);
  }

  get hashCode(): number {
    return 0;
  }

  get isNaN(): boolean {
    return Number.isNaN(this.toDouble());
  }

  get isNegative(): boolean {
    return this.compareTo(Decimal.fromInt(0)) < 0;
  }

  get isInfinite(): boolean {
    return (
      this.toDouble() === Number.POSITIVE_INFINITY ||
      this.toDouble() === Number.NEGATIVE_INFINITY
    );
  }

  get signum(): number {
    // TODO
    return 0;
  }

  get hasFinitePrecision(): boolean {
    // TODO
    return false;
  }

  get precision(): number {
    // TODO
    return 0;
  }

  get scale(): number {
    // TODO
    return 0;
  }

  compareTo(other: Decimal): number {
    return this.value.compareTo(other.value);
  }

  toInt(): number {
    return Decimal._toInt(this);
  }

  toDouble(): number {
    return Decimal._toDouble(this);
  }

  toString(): string {
    return this.value.getValue();
  }

  toStringAsFixed(fractionDigits: number) {
    return this.value.round(fractionDigits).getValue();
  }

  toStringAsExponential(fractionDigits?: number): string {
    return this.value.round(fractionDigits).getValue();
  }

  toStringAsPrecision(precision: number): string {
    return this.value.round(precision).getValue();
  }
}
