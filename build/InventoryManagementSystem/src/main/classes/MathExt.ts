export default class MathExt {
  public static readonly pi = Math.PI;
  public static readonly log2e = Math.LOG2E;
  public static readonly log10e = Math.LOG10E;
  public static readonly sqrt1_2 = Math.SQRT1_2;
  public static readonly sqrt2 = Math.SQRT2;
  public static readonly ln2 = Math.LN2;
  public static readonly ln10 = Math.LN10;
  public static readonly e = Math.E;
  static clampDouble(x: number, min: number, max: number): number {
    if (x < min) {
      return min;
    }
    if (x > max) {
      return max;
    }
    if (x.isNaN) {
      return max;
    }
    return x;
  }
}
