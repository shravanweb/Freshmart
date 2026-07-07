import PurchaseOrder from "./PurchaseOrder";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class PurchaseOrderItemRequest extends DBObject {
  private static readonly _PURCHASEORDER: number = 0;
  public id: number = 0;
  public otherMaster: DBObject;
  private _purchaseOrder: PurchaseOrder = null;
  public constructor(d3eParams?: Partial<{ purchaseOrder: PurchaseOrder }>) {
    super();

    this.setPurchaseOrder(d3eParams?.purchaseOrder ?? null);
  }
  public get d3eType(): string {
    return "PurchaseOrderItemRequest";
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get isTransientModel(): boolean {
    return;
  }
  public get purchaseOrder(): PurchaseOrder {
    return this._purchaseOrder;
  }
  public setPurchaseOrder(val: PurchaseOrder): void {
    let isValChanged: boolean = this._purchaseOrder !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(
      PurchaseOrderItemRequest._PURCHASEORDER,
      this._purchaseOrder
    );

    this.updateObservable("purchaseOrder", this._purchaseOrder, val);

    this._purchaseOrder = val;

    this.fire("purchaseOrder", this);
  }
  public get(field: number): any {
    switch (field) {
      case PurchaseOrderItemRequest._PURCHASEORDER: {
        return this._purchaseOrder;
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
  public deepClone(clearId = true): PurchaseOrderItemRequest {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: PurchaseOrderItemRequest = dbObj as PurchaseOrderItemRequest;

    obj.id = this.id;

    obj.setPurchaseOrder(this._purchaseOrder);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case PurchaseOrderItemRequest._PURCHASEORDER: {
        this.setPurchaseOrder(value as PurchaseOrder);
        break;
      }
    }
  }
}
