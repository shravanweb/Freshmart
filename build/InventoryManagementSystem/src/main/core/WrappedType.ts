import Type from "./Type";

class WrappedType {
  constructor(public outer: Type, public subs: Array<Type>) {}

  setSubs(subs: Array<Type>): void {}

  setOuter(outer: Type): void {}
}

export default WrappedType;
