import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default abstract class BaseUser extends DBObject {
  public id: number = 0;
  public otherMaster: DBObject;
  public constructor() {
    super();
  }
  public get d3eType(): string {
    return "BaseUser";
  }
  public toString(): string {
    return "";
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get(field: number): any {
    switch (field) {
      default: {
        return null;
      }
    }
  }
  public updateD3EChanges(index: number, value: any): void {
    if (this.lockedChanges()) {
      return;
    }

    super.updateD3EChanges(index, value);
  }
  public restore(): void {
    /*
TODO: Might be removed
*/

    this.d3eChanges.restore(this);
  }
  public deepClone(clearId = true): BaseUser {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: BaseUser = dbObj as BaseUser;

    obj.id = this.id;
  }
  public set(field: number, value: any): void {
    switch (field) {
    }
  }
}
