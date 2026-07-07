import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class ExpiringBatchesRequest extends DBObject {
  private static readonly _DAYSAHEAD: number = 0;
  private static readonly _ORGANIZATION: number = 1;
  public id: number = 0;
  public otherMaster: DBObject;
  private _organization: Organization = null;
  private _daysAhead: number = 0;
  public constructor(
    d3eParams?: Partial<{ daysAhead: number; organization: Organization }>
  ) {
    super();

    this.setDaysAhead(d3eParams?.daysAhead ?? 0);

    this.setOrganization(d3eParams?.organization ?? null);
  }
  public get d3eType(): string {
    return "ExpiringBatchesRequest";
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
      ExpiringBatchesRequest._ORGANIZATION,
      this._organization
    );

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get daysAhead(): number {
    return this._daysAhead;
  }
  public setDaysAhead(val: number): void {
    let isValChanged: boolean = this._daysAhead !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(ExpiringBatchesRequest._DAYSAHEAD, this._daysAhead);

    this._daysAhead = val;

    this.fire("daysAhead", this);
  }
  public get(field: number): any {
    switch (field) {
      case ExpiringBatchesRequest._ORGANIZATION: {
        return this._organization;
      }

      case ExpiringBatchesRequest._DAYSAHEAD: {
        return this._daysAhead;
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
  public deepClone(clearId = true): ExpiringBatchesRequest {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: ExpiringBatchesRequest = dbObj as ExpiringBatchesRequest;

    obj.id = this.id;

    obj.setOrganization(this._organization);

    obj.setDaysAhead(this._daysAhead);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case ExpiringBatchesRequest._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }

      case ExpiringBatchesRequest._DAYSAHEAD: {
        this.setDaysAhead(value as number);
        break;
      }
    }
  }
}
