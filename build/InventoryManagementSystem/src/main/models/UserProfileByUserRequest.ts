import User from "./User";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class UserProfileByUserRequest extends DBObject {
  private static readonly _USER: number = 0;
  public id: number = 0;
  public otherMaster: DBObject;
  private _user: User = null;
  public constructor(d3eParams?: Partial<{ user: User }>) {
    super();

    this.setUser(d3eParams?.user ?? null);
  }
  public get d3eType(): string {
    return "UserProfileByUserRequest";
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get isTransientModel(): boolean {
    return;
  }
  public get user(): User {
    return this._user;
  }
  public setUser(val: User): void {
    let isValChanged: boolean = this._user !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(UserProfileByUserRequest._USER, this._user);

    this.updateObservable("user", this._user, val);

    this._user = val;

    this.fire("user", this);
  }
  public get(field: number): any {
    switch (field) {
      case UserProfileByUserRequest._USER: {
        return this._user;
      }
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
  public deepClone(clearId = true): UserProfileByUserRequest {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: UserProfileByUserRequest = dbObj as UserProfileByUserRequest;

    obj.id = this.id;

    obj.setUser(this._user);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case UserProfileByUserRequest._USER: {
        this.setUser(value as User);
        break;
      }
    }
  }
}
