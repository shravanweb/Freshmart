import D3EDate from "../classes/D3EDate";
import Vendor from "./Vendor";
import Warehouse from "./Warehouse";
import PurchaseOrderStatus from "../classes/PurchaseOrderStatus";
import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class PurchaseOrder extends DBObject {
  private static readonly _NOTES: number = 0;
  private static readonly _ORDERDATE: number = 1;
  private static readonly _ORGANIZATION: number = 2;
  private static readonly _PONUMBER: number = 3;
  private static readonly _STATUS: number = 4;
  private static readonly _VENDOR: number = 5;
  private static readonly _WAREHOUSE: number = 6;
  public id: number = 0;
  public otherMaster: DBObject;
  private _poNumber: string = "";
  private _vendor: Vendor = null;
  private _warehouse: Warehouse = null;
  private _orderDate: D3EDate = null;
  private _status: PurchaseOrderStatus = PurchaseOrderStatus.Draft;
  private _notes: string = "";
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{
      notes: string;
      orderDate: D3EDate;
      organization: Organization;
      poNumber: string;
      status: PurchaseOrderStatus;
      vendor: Vendor;
      warehouse: Warehouse;
    }>
  ) {
    super();

    this.setNotes(d3eParams?.notes ?? "");

    this.setOrderDate(d3eParams?.orderDate ?? null);

    this.setOrganization(d3eParams?.organization ?? null);

    this.setPoNumber(d3eParams?.poNumber ?? "");

    this.setStatus(d3eParams?.status ?? PurchaseOrderStatus.Draft);

    this.setVendor(d3eParams?.vendor ?? null);

    this.setWarehouse(d3eParams?.warehouse ?? null);
  }
  public get d3eType(): string {
    return "PurchaseOrder";
  }
  public toString(): string {
    return this.poNumber;
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get poNumber(): string {
    return this._poNumber;
  }
  public setPoNumber(val: string): void {
    let isValChanged: boolean = this._poNumber !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(PurchaseOrder._PONUMBER, this._poNumber);

    this._poNumber = val;

    this.fire("poNumber", this);
  }
  public get vendor(): Vendor {
    return this._vendor;
  }
  public setVendor(val: Vendor): void {
    let isValChanged: boolean = this._vendor !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(PurchaseOrder._VENDOR, this._vendor);

    this.updateObservable("vendor", this._vendor, val);

    this._vendor = val;

    this.fire("vendor", this);
  }
  public get warehouse(): Warehouse {
    return this._warehouse;
  }
  public setWarehouse(val: Warehouse): void {
    let isValChanged: boolean = this._warehouse !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(PurchaseOrder._WAREHOUSE, this._warehouse);

    this.updateObservable("warehouse", this._warehouse, val);

    this._warehouse = val;

    this.fire("warehouse", this);
  }
  public get orderDate(): D3EDate {
    return this._orderDate;
  }
  public setOrderDate(val: D3EDate): void {
    let isValChanged: boolean = this._orderDate !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(PurchaseOrder._ORDERDATE, this._orderDate);

    this._orderDate = val;

    this.fire("orderDate", this);
  }
  public get status(): PurchaseOrderStatus {
    return this._status;
  }
  public setStatus(val: PurchaseOrderStatus): void {
    let isValChanged: boolean = this._status !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(PurchaseOrder._STATUS, this._status.index);

    this._status = val;

    this.fire("status", this);
  }
  public get notes(): string {
    return this._notes;
  }
  public setNotes(val: string): void {
    let isValChanged: boolean = this._notes !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(PurchaseOrder._NOTES, this._notes);

    this._notes = val;

    this.fire("notes", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(PurchaseOrder._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case PurchaseOrder._PONUMBER: {
        return this._poNumber;
      }

      case PurchaseOrder._VENDOR: {
        return this._vendor;
      }

      case PurchaseOrder._WAREHOUSE: {
        return this._warehouse;
      }

      case PurchaseOrder._ORDERDATE: {
        return this._orderDate;
      }

      case PurchaseOrder._STATUS: {
        return this._status.index;
      }

      case PurchaseOrder._NOTES: {
        return this._notes;
      }

      case PurchaseOrder._ORGANIZATION: {
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
  public deepClone(clearId = true): PurchaseOrder {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: PurchaseOrder = dbObj as PurchaseOrder;

    obj.id = this.id;

    obj.setPoNumber(this._poNumber);

    obj.setVendor(this._vendor);

    obj.setWarehouse(this._warehouse);

    obj.setOrderDate(this._orderDate);

    obj.setStatus(this._status);

    obj.setNotes(this._notes);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case PurchaseOrder._PONUMBER: {
        this.setPoNumber(value as string);
        break;
      }

      case PurchaseOrder._VENDOR: {
        this.setVendor(value as Vendor);
        break;
      }

      case PurchaseOrder._WAREHOUSE: {
        this.setWarehouse(value as Warehouse);
        break;
      }

      case PurchaseOrder._ORDERDATE: {
        this.setOrderDate(value as D3EDate);
        break;
      }

      case PurchaseOrder._STATUS: {
        this.setStatus(PurchaseOrderStatus.values[value as number]);
        break;
      }

      case PurchaseOrder._NOTES: {
        this.setNotes(value as string);
        break;
      }

      case PurchaseOrder._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
