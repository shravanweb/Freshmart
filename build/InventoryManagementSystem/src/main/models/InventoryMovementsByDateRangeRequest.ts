import DateTime from "../core/DateTime";
import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class InventoryMovementsByDateRangeRequest extends DBObject {
  private static readonly _ENDDATE: number = 0;
  private static readonly _ORGANIZATION: number = 1;
  private static readonly _STARTDATE: number = 2;
  public id: number = 0;
  public otherMaster: DBObject;
  private _organization: Organization = null;
  private _startDate: DateTime = null;
  private _endDate: DateTime = null;
  public constructor(
    d3eParams?: Partial<{
      endDate: DateTime;
      organization: Organization;
      startDate: DateTime;
    }>
  ) {
    super();

    this.setEndDate(d3eParams?.endDate ?? null);

    this.setOrganization(d3eParams?.organization ?? null);

    this.setStartDate(d3eParams?.startDate ?? null);
  }
  public get d3eType(): string {
    return "InventoryMovementsByDateRangeRequest";
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
      InventoryMovementsByDateRangeRequest._ORGANIZATION,
      this._organization
    );

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get startDate(): DateTime {
    return this._startDate;
  }
  public setStartDate(val: DateTime): void {
    let isValChanged: boolean = this._startDate !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(
      InventoryMovementsByDateRangeRequest._STARTDATE,
      this._startDate
    );

    this._startDate = val;

    this.fire("startDate", this);
  }
  public get endDate(): DateTime {
    return this._endDate;
  }
  public setEndDate(val: DateTime): void {
    let isValChanged: boolean = this._endDate !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(
      InventoryMovementsByDateRangeRequest._ENDDATE,
      this._endDate
    );

    this._endDate = val;

    this.fire("endDate", this);
  }
  public get(field: number): any {
    switch (field) {
      case InventoryMovementsByDateRangeRequest._ORGANIZATION: {
        return this._organization;
      }

      case InventoryMovementsByDateRangeRequest._STARTDATE: {
        return this._startDate;
      }

      case InventoryMovementsByDateRangeRequest._ENDDATE: {
        return this._endDate;
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
  public deepClone(clearId = true): InventoryMovementsByDateRangeRequest {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: InventoryMovementsByDateRangeRequest =
      dbObj as InventoryMovementsByDateRangeRequest;

    obj.id = this.id;

    obj.setOrganization(this._organization);

    obj.setStartDate(this._startDate);

    obj.setEndDate(this._endDate);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case InventoryMovementsByDateRangeRequest._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }

      case InventoryMovementsByDateRangeRequest._STARTDATE: {
        this.setStartDate(value as DateTime);
        break;
      }

      case InventoryMovementsByDateRangeRequest._ENDDATE: {
        this.setEndDate(value as DateTime);
        break;
      }
    }
  }
}
