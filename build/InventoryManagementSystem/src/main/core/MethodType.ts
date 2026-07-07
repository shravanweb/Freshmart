import Type from "./Type";

export default class MethodType {
  constructor(public onValue: Type, public name: string, public gen: Type) {}

  setOn(on: Type): void {}

  setGen(outer: Type): void {}
}
