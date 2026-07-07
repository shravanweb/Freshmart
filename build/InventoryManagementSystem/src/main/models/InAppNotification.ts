import User from "./User";
import DateTime from "../core/DateTime";
import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class InAppNotification extends DBObject {
  private static readonly _CREATEDAT: number = 0;
  private static readonly _ISREAD: number = 1;
  private static readonly _ORGANIZATION: number = 2;
  private static readonly _RECIPIENT: number = 3;
  private static readonly _TITLE: number = 4;
  public id: number = 0;
  public otherMaster: DBObject;
  private _recipient: User = null;
  private _title: string = "";
  private _isRead: boolean = false;
  private _createdAt: DateTime = null;
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{
      createdAt: DateTime;
      isRead: boolean;
      organization: Organization;
      recipient: User;
      title: string;
    }>
  ) {
    super();

    this.setCreatedAt(d3eParams?.createdAt ?? null);

    this.setIsRead(d3eParams?.isRead ?? false);

    this.setOrganization(d3eParams?.organization ?? null);

    this.setRecipient(d3eParams?.recipient ?? null);

    this.setTitle(d3eParams?.title ?? "");
  }
  public get d3eType(): string {
    return "InAppNotification";
  }
  public toString(): string {
    return this.title;
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get recipient(): User {
    return this._recipient;
  }
  public setRecipient(val: User): void {
    let isValChanged: boolean = this._recipient !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(InAppNotification._RECIPIENT, this._recipient);

    this.updateObservable("recipient", this._recipient, val);

    this._recipient = val;

    this.fire("recipient", this);
  }
  public get title(): string {
    return this._title;
  }
  public setTitle(val: string): void {
    let isValChanged: boolean = this._title !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(InAppNotification._TITLE, this._title);

    this._title = val;

    this.fire("title", this);
  }
  public get isRead(): boolean {
    return this._isRead;
  }
  public setIsRead(val: boolean): void {
    let isValChanged: boolean = this._isRead !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(InAppNotification._ISREAD, this._isRead);

    this._isRead = val;

    this.fire("isRead", this);
  }
  public get createdAt(): DateTime {
    return this._createdAt;
  }
  public setCreatedAt(val: DateTime): void {
    let isValChanged: boolean = this._createdAt !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(InAppNotification._CREATEDAT, this._createdAt);

    this._createdAt = val;

    this.fire("createdAt", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(InAppNotification._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case InAppNotification._RECIPIENT: {
        return this._recipient;
      }

      case InAppNotification._TITLE: {
        return this._title;
      }

      case InAppNotification._ISREAD: {
        return this._isRead;
      }

      case InAppNotification._CREATEDAT: {
        return this._createdAt;
      }

      case InAppNotification._ORGANIZATION: {
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
  public deepClone(clearId = true): InAppNotification {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: InAppNotification = dbObj as InAppNotification;

    obj.id = this.id;

    obj.setRecipient(this._recipient);

    obj.setTitle(this._title);

    obj.setIsRead(this._isRead);

    obj.setCreatedAt(this._createdAt);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case InAppNotification._RECIPIENT: {
        this.setRecipient(value as User);
        break;
      }

      case InAppNotification._TITLE: {
        this.setTitle(value as string);
        break;
      }

      case InAppNotification._ISREAD: {
        this.setIsRead(value as boolean);
        break;
      }

      case InAppNotification._CREATEDAT: {
        this.setCreatedAt(value as DateTime);
        break;
      }

      case InAppNotification._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
