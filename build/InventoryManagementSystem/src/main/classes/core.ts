export type OneFunction<T, U> = (t: T) => U;
export type BiFunction<T, U, R> = (t: T, u: U) => R;
export type Runnable = () => void;
export type Consumer<T> = (t: T) => void;
export type BiConsumer<T, U> = (t: T, u: U) => void;
export type Comparator<T> = (a: T, b: T) => number;
export type Supplier<T> = () => T;
export type BiPredicate<T, U> = (t: T, u: U) => boolean;
type VoidCallback = () => void;
export default VoidCallback;
/* Used in BufferReader & BufferWriter */
export const TWO_TO_32 = 4294967296;
export const FLOAT64_MAX = 1.7976931348623157e308;
export const FLOAT64_MIN = 2.2250738585072014e-308;
export const TWO_TO_20 = 1048576;
export const TWO_TO_52 = 4503599627370496;
