import User from "./User";
import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class AllInAppNotificationsRequest extends DBObject {
  private static readonly _ORGANIZATION: number = 0;
  private static readonly _USER: number = 1;
  public id: number = 0;
  public otherMaster: DBObject;
  private _organization: Organization = null;
  private _user: User = null;
  public constructor(
    d3eParams?: Partial<{ organization: Organization; user: User }>
  ) {
    super();

    this.setOrganization(d3eParams?.organization ?? null);

    this.setUser(d3eParams?.user ?? null);
  }
  public get d3eType(): string {
    return "AllInAppNotificationsRequest";
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get isTransientModel(): boolean {
    return;
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(
      AllInAppNotificationsRequest._ORGANIZATION,
      this._organization
    );

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get user(): User {
    return this._user;
  }
  public setUser(val: User): void {
    let isValChanged: boolean = this._user !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(AllInAppNotificationsRequest._USER, this._user);

    this.updateObservable("user", this._user, val);

    this._user = val;

    this.fire("user", this);
  }
  public get(field: number): any {
    switch (field) {
      case AllInAppNotificationsRequest._ORGANIZATION: {
        return this._organization;
      }

      case AllInAppNotificationsRequest._USER: {
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
  public deepClone(clearId = true): AllInAppNotificationsRequest {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: AllInAppNotificationsRequest =
      dbObj as AllInAppNotificationsRequest;

    obj.id = this.id;

    obj.setOrganization(this._organization);

    obj.setUser(this._user);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case AllInAppNotificationsRequest._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }

      case AllInAppNotificationsRequest._USER: {
        this.setUser(value as User);
        break;
      }
    }
  }
}
