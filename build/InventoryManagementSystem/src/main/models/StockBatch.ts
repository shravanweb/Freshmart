import D3EDate from "../classes/D3EDate";
import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class StockBatch extends DBObject {
  private static readonly _BATCHNUMBER: number = 0;
  private static readonly _EXPIRYDATE: number = 1;
  private static readonly _ORGANIZATION: number = 2;
  public id: number = 0;
  public otherMaster: DBObject;
  private _batchNumber: string = "";
  private _expiryDate: D3EDate = null;
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{
      batchNumber: string;
      expiryDate: D3EDate;
      organization: Organization;
    }>
  ) {
    super();

    this.setBatchNumber(d3eParams?.batchNumber ?? "");

    this.setExpiryDate(d3eParams?.expiryDate ?? null);

    this.setOrganization(d3eParams?.organization ?? null);
  }
  public get d3eType(): string {
    return "StockBatch";
  }
  public toString(): string {
    return this.batchNumber;
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get batchNumber(): string {
    return this._batchNumber;
  }
  public setBatchNumber(val: string): void {
    let isValChanged: boolean = this._batchNumber !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(StockBatch._BATCHNUMBER, this._batchNumber);

    this._batchNumber = val;

    this.fire("batchNumber", this);
  }
  public get expiryDate(): D3EDate {
    return this._expiryDate;
  }
  public setExpiryDate(val: D3EDate): void {
    let isValChanged: boolean = this._expiryDate !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(StockBatch._EXPIRYDATE, this._expiryDate);

    this._expiryDate = val;

    this.fire("expiryDate", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(StockBatch._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case StockBatch._BATCHNUMBER: {
        return this._batchNumber;
      }

      case StockBatch._EXPIRYDATE: {
        return this._expiryDate;
      }

      case StockBatch._ORGANIZATION: {
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
  public deepClone(clearId = true): StockBatch {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: StockBatch = dbObj as StockBatch;

    obj.id = this.id;

    obj.setBatchNumber(this._batchNumber);

    obj.setExpiryDate(this._expiryDate);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case StockBatch._BATCHNUMBER: {
        this.setBatchNumber(value as string);
        break;
      }

      case StockBatch._EXPIRYDATE: {
        this.setExpiryDate(value as D3EDate);
        break;
      }

      case StockBatch._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
