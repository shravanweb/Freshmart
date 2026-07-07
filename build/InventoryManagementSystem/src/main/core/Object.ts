export default class ObjectExt {}

declare global {
  interface Object {
    // Object(): Object;

    equals(other: Object): boolean;

    hashCode: number;
  }
}
Object.defineProperty(Object.prototype, "hashCode", {
  get: function () {
    // TODO
    return 0;
  },
});
// export default Object;
