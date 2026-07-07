import Product from "./Product";
import Warehouse from "./Warehouse";
import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class WarehouseStock extends DBObject {
  private static readonly _ORGANIZATION: number = 0;
  private static readonly _PRODUCT: number = 1;
  private static readonly _QUANTITYONHAND: number = 2;
  private static readonly _WAREHOUSE: number = 3;
  public id: number = 0;
  public otherMaster: DBObject;
  private _warehouse: Warehouse = null;
  private _product: Product = null;
  private _quantityOnHand: number = 0.0;
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{
      organization: Organization;
      product: Product;
      quantityOnHand: number;
      warehouse: Warehouse;
    }>
  ) {
    super();

    this.setOrganization(d3eParams?.organization ?? null);

    this.setProduct(d3eParams?.product ?? null);

    this.setQuantityOnHand(d3eParams?.quantityOnHand ?? 0.0);

    this.setWarehouse(d3eParams?.warehouse ?? null);
  }
  public get d3eType(): string {
    return "WarehouseStock";
  }
  public toString(): string {
    return this.product.name + " @ " + this.warehouse.name;
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get warehouse(): Warehouse {
    return this._warehouse;
  }
  public setWarehouse(val: Warehouse): void {
    let isValChanged: boolean = this._warehouse !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(WarehouseStock._WAREHOUSE, this._warehouse);

    this.updateObservable("warehouse", this._warehouse, val);

    this._warehouse = val;

    this.fire("warehouse", this);
  }
  public get product(): Product {
    return this._product;
  }
  public setProduct(val: Product): void {
    let isValChanged: boolean = this._product !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(WarehouseStock._PRODUCT, this._product);

    this.updateObservable("product", this._product, val);

    this._product = val;

    this.fire("product", this);
  }
  public get quantityOnHand(): number {
    return this._quantityOnHand;
  }
  public setQuantityOnHand(val: number): void {
    let isValChanged: boolean = this._quantityOnHand !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(WarehouseStock._QUANTITYONHAND, this._quantityOnHand);

    this._quantityOnHand = val;

    this.fire("quantityOnHand", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(WarehouseStock._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case WarehouseStock._WAREHOUSE: {
        return this._warehouse;
      }

      case WarehouseStock._PRODUCT: {
        return this._product;
      }

      case WarehouseStock._QUANTITYONHAND: {
        return this._quantityOnHand;
      }

      case WarehouseStock._ORGANIZATION: {
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
  public deepClone(clearId = true): WarehouseStock {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: WarehouseStock = dbObj as WarehouseStock;

    obj.id = this.id;

    obj.setWarehouse(this._warehouse);

    obj.setProduct(this._product);

    obj.setQuantityOnHand(this._quantityOnHand);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case WarehouseStock._WAREHOUSE: {
        this.setWarehouse(value as Warehouse);
        break;
      }

      case WarehouseStock._PRODUCT: {
        this.setProduct(value as Product);
        break;
      }

      case WarehouseStock._QUANTITYONHAND: {
        this.setQuantityOnHand(value as number);
        break;
      }

      case WarehouseStock._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
