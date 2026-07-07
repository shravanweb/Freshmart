export default class IdGenerator {
  private static _id: number = 0;
  public static ins: IdGenerator;

  private constructor() {}

  private static _init(): IdGenerator {
    return new IdGenerator();
  }

  public static get(): IdGenerator {
    if (IdGenerator.ins == null) {
      IdGenerator.ins = IdGenerator._init();
    }
    return IdGenerator.ins;
  }

  public next(): number {
    IdGenerator._id++;
    return IdGenerator._id;
  }
}
