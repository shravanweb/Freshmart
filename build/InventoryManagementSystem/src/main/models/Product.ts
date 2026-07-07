import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class Product extends DBObject {
  private static readonly _NAME: number = 0;
  private static readonly _ORGANIZATION: number = 1;
  private static readonly _REORDERLEVEL: number = 2;
  public id: number = 0;
  public otherMaster: DBObject;
  private _name: string = "";
  private _reorderLevel: number = 0.0;
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{
      name: string;
      organization: Organization;
      reorderLevel: number;
    }>
  ) {
    super();

    this.setName(d3eParams?.name ?? "");

    this.setOrganization(d3eParams?.organization ?? null);

    this.setReorderLevel(d3eParams?.reorderLevel ?? 0.0);
  }
  public get d3eType(): string {
    return "Product";
  }
  public toString(): string {
    return this.name;
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get name(): string {
    return this._name;
  }
  public setName(val: string): void {
    let isValChanged: boolean = this._name !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Product._NAME, this._name);

    this._name = val;

    this.fire("name", this);
  }
  public get reorderLevel(): number {
    return this._reorderLevel;
  }
  public setReorderLevel(val: number): void {
    let isValChanged: boolean = this._reorderLevel !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Product._REORDERLEVEL, this._reorderLevel);

    this._reorderLevel = val;

    this.fire("reorderLevel", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Product._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case Product._NAME: {
        return this._name;
      }

      case Product._REORDERLEVEL: {
        return this._reorderLevel;
      }

      case Product._ORGANIZATION: {
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
  public deepClone(clearId = true): Product {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: Product = dbObj as Product;

    obj.id = this.id;

    obj.setName(this._name);

    obj.setReorderLevel(this._reorderLevel);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case Product._NAME: {
        this.setName(value as string);
        break;
      }

      case Product._REORDERLEVEL: {
        this.setReorderLevel(value as number);
        break;
      }

      case Product._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
