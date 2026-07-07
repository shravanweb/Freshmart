import DBObject from "./DBObject";

export abstract class CommunicationContext {
  public abstract done(): void;
  public abstract clear<T extends DBObject>(): void;

  public abstract get fields(): number;
  public abstract get nextField(): number;
}
