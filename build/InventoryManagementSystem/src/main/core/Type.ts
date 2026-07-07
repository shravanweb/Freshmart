export default class Type {
  static wrap(outer: Type, args: Array<Type>): Type {
    return null;
  }

  static find(name: string): Type {
    return null;
  }

  static methodType(on: Type, name: string, gen: Type): Type {
    return null;
  }

  constructor(public name: string) {}
}

// export default Type;
