import DateTime from "../core/DateTime";
import Organization from "./Organization";
import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class InventoryMovement extends DBObject {
  private static readonly _MOVEMENTDATE: number = 0;
  private static readonly _MOVEMENTNUMBER: number = 1;
  private static readonly _ORGANIZATION: number = 2;
  public id: number = 0;
  public otherMaster: DBObject;
  private _movementNumber: string = "";
  private _movementDate: DateTime = null;
  private _organization: Organization = null;
  public constructor(
    d3eParams?: Partial<{
      movementDate: DateTime;
      movementNumber: string;
      organization: Organization;
    }>
  ) {
    super();

    this.setMovementDate(d3eParams?.movementDate ?? null);

    this.setMovementNumber(d3eParams?.movementNumber ?? "");

    this.setOrganization(d3eParams?.organization ?? null);
  }
  public get d3eType(): string {
    return "InventoryMovement";
  }
  public toString(): string {
    return this.movementNumber;
  }
  public clear(): void {
    this.d3eChanges.clear();
  }
  public get movementNumber(): string {
    return this._movementNumber;
  }
  public setMovementNumber(val: string): void {
    let isValChanged: boolean = this._movementNumber !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(
      InventoryMovement._MOVEMENTNUMBER,
      this._movementNumber
    );

    this._movementNumber = val;

    this.fire("movementNumber", this);
  }
  public get movementDate(): DateTime {
    return this._movementDate;
  }
  public setMovementDate(val: DateTime): void {
    let isValChanged: boolean = this._movementDate !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(InventoryMovement._MOVEMENTDATE, this._movementDate);

    this._movementDate = val;

    this.fire("movementDate", this);
  }
  public get organization(): Organization {
    return this._organization;
  }
  public setOrganization(val: Organization): void {
    let isValChanged: boolean = this._organization !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(InventoryMovement._ORGANIZATION, this._organization);

    this.updateObservable("organization", this._organization, val);

    this._organization = val;

    this.fire("organization", this);
  }
  public get(field: number): any {
    switch (field) {
      case InventoryMovement._MOVEMENTNUMBER: {
        return this._movementNumber;
      }

      case InventoryMovement._MOVEMENTDATE: {
        return this._movementDate;
      }

      case InventoryMovement._ORGANIZATION: {
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
  public deepClone(clearId = true): InventoryMovement {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: InventoryMovement = dbObj as InventoryMovement;

    obj.id = this.id;

    obj.setMovementNumber(this._movementNumber);

    obj.setMovementDate(this._movementDate);

    obj.setOrganization(this._organization);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case InventoryMovement._MOVEMENTNUMBER: {
        this.setMovementNumber(value as string);
        break;
      }

      case InventoryMovement._MOVEMENTDATE: {
        this.setMovementDate(value as DateTime);
        break;
      }

      case InventoryMovement._ORGANIZATION: {
        this.setOrganization(value as Organization);
        break;
      }
    }
  }
}
