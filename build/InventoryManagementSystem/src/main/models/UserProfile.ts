import User from "./User";
import AppUserRole from "../classes/AppUserRole";
import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class UserProfile extends DBObject {
  private static readonly _APPROLE: number = 0;
  private static readonly _DISPLAYNAME: number = 1;
  private static readonly _ORGANIZATION: number = 2;
  private static readonly _PHONE: number = 3;
  private static readonly _USER: number = 4;
  public id: number = 0;
  public otherMaster: DBObject;
  private _user: User = null;
  private _displayName: string = "";
  private _phone: string = "";
  private _appRole: AppUserRole = AppUserRole.OrganizationAdmin;
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{
      appRole: AppUserRole;
      displayName: string;
      organization: Organization;
      phone: string;
      user: User;
    }>
  ) {
    super();

    this.setAppRole(d3eParams?.appRole ?? AppUserRole.OrganizationAdmin);

    this.setDisplayName(d3eParams?.displayName ?? "");

    this.setOrganization(d3eParams?.organization ?? null);

    this.setPhone(d3eParams?.phone ?? "");

    this.setUser(d3eParams?.user ?? null);
  }
  public get d3eType(): string {
    return "UserProfile";
  }
  public toString(): string {
    return this.displayName;
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get user(): User {
    return this._user;
  }
  public setUser(val: User): void {
    let isValChanged: boolean = this._user !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(UserProfile._USER, this._user);

    this.updateObservable("user", this._user, val);

    this._user = val;

    this.fire("user", this);
  }
  public get displayName(): string {
    return this._displayName;
  }
  public setDisplayName(val: string): void {
    let isValChanged: boolean = this._displayName !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(UserProfile._DISPLAYNAME, this._displayName);

    this._displayName = val;

    this.fire("displayName", this);
  }
  public get phone(): string {
    return this._phone;
  }
  public setPhone(val: string): void {
    let isValChanged: boolean = this._phone !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(UserProfile._PHONE, this._phone);

    this._phone = val;

    this.fire("phone", this);
  }
  public get appRole(): AppUserRole {
    return this._appRole;
  }
  public setAppRole(val: AppUserRole): void {
    let isValChanged: boolean = this._appRole !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(UserProfile._APPROLE, this._appRole.index);

    this._appRole = val;

    this.fire("appRole", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(UserProfile._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case UserProfile._USER: {
        return this._user;
      }

      case UserProfile._DISPLAYNAME: {
        return this._displayName;
      }

      case UserProfile._PHONE: {
        return this._phone;
      }

      case UserProfile._APPROLE: {
        return this._appRole.index;
      }

      case UserProfile._ORGANIZATION: {
        return this._organization;
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
  public deepClone(clearId = true): UserProfile {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: UserProfile = dbObj as UserProfile;

    obj.id = this.id;

    obj.setUser(this._user);

    obj.setDisplayName(this._displayName);

    obj.setPhone(this._phone);

    obj.setAppRole(this._appRole);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case UserProfile._USER: {
        this.setUser(value as User);
        break;
      }

      case UserProfile._DISPLAYNAME: {
        this.setDisplayName(value as string);
        break;
      }

      case UserProfile._PHONE: {
        this.setPhone(value as string);
        break;
      }

      case UserProfile._APPROLE: {
        this.setAppRole(AppUserRole.values[value as number]);
        break;
      }

      case UserProfile._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
