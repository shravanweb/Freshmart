import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class Vendor extends DBObject {
  private static readonly _NAME: number = 0;
  private static readonly _ORGANIZATION: number = 1;
  public id: number = 0;
  public otherMaster: DBObject;
  private _name: string = "";
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{ name: string; organization: Organization }>
  ) {
    super();

    this.setName(d3eParams?.name ?? "");

    this.setOrganization(d3eParams?.organization ?? null);
  }
  public get d3eType(): string {
    return "Vendor";
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

    this.updateD3EChanges(Vendor._NAME, this._name);

    this._name = val;

    this.fire("name", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Vendor._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case Vendor._NAME: {
        return this._name;
      }

      case Vendor._ORGANIZATION: {
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
  public deepClone(clearId = true): Vendor {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: Vendor = dbObj as Vendor;

    obj.id = this.id;

    obj.setName(this._name);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case Vendor._NAME: {
        this.setName(value as string);
        break;
      }

      case Vendor._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
