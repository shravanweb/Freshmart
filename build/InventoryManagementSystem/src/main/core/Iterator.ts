export default class IteratorExt {}

declare global {
  // Not sure if class here
  interface Iterator<T> {
    moveNext(): boolean;

    current: T;
  }
}
// Iterator.prototype.moveNext = function () {
//     return false;
// }
// export default Iterator;
