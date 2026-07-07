import Organization from "./Organization";
import BaseUser from "./BaseUser";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class User extends BaseUser {
  private static readonly _EMAIL: number = 0;
  private static readonly _ORGANIZATION: number = 1;
  private _email: string = "";
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{ email: string; organization: Organization }>
  ) {
    super();

    this.setEmail(d3eParams?.email ?? "");

    this.setOrganization(d3eParams?.organization ?? null);
  }
  public get d3eType(): string {
    return "User";
  }
  public toString(): string {
    return this.email;
  }
  public get email(): string {
    return this._email;
  }
  public setEmail(val: string): void {
    let isValChanged: boolean = this._email !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(User._EMAIL, this._email);

    this._email = val;

    this.fire("email", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(User._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case User._EMAIL: {
        return this._email;
      }

      case User._ORGANIZATION: {
        return this._organization;
      }
      default: {
        return super.get(field);
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
  public deepClone(clearId = true): User {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {
    super.collectChildValues(ctx);
  }
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    super.deepCloneIntoObj(dbObj, ctx);

    let obj: User = dbObj as User;

    obj.setEmail(this._email);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case User._EMAIL: {
        this.setEmail(value as string);
        break;
      }

      case User._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
      default: {
        super.set(field, value);
      }
    }
  }
}
