import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class OutOfStockItemsRequest extends DBObject {
  private static readonly _ORGANIZATION: number = 0;
  public id: number = 0;
  public otherMaster: DBObject;
  private _organization: Organization = null;
  public constructor(d3eParams?: Partial<{ organization: Organization }>) {
    super();

    this.setOrganization(d3eParams?.organization ?? null);
  }
  public get d3eType(): string {
    return "OutOfStockItemsRequest";
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
      OutOfStockItemsRequest._ORGANIZATION,
      this._organization
    );

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case OutOfStockItemsRequest._ORGANIZATION: {
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
  public deepClone(clearId = true): OutOfStockItemsRequest {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: OutOfStockItemsRequest = dbObj as OutOfStockItemsRequest;

    obj.id = this.id;

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case OutOfStockItemsRequest._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
