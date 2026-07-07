export default interface Comparable<T> {
  compareTo(other: T): number;

  compare(a: Comparable<any>, b: Comparable<any>): number;
}
