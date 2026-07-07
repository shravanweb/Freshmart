import DBObject from "../utils/DBObject";
import CloneContext from "../utils/CloneContext";

export default class Organization extends DBObject {
  private static readonly _ADDRESS: number = 0;
  private static readonly _CURRENCY: number = 1;
  private static readonly _EMAIL: number = 2;
  private static readonly _LEGALNAME: number = 3;
  private static readonly _NAME: number = 4;
  private static readonly _PHONE: number = 5;
  private static readonly _TIMEZONE: number = 6;
  public id: number = 0;
  public otherMaster: DBObject;
  private _name: string = "";
  private _legalName: string = "";
  private _email: string = "";
  private _phone: string = "";
  private _address: string = "";
  private _currency: string = "USD";
  private _timezone: string = "UTC";
  public constructor(
    d3eParams?: Partial<{
      address: string;
      currency: string;
      email: string;
      legalName: string;
      name: string;
      phone: string;
      timezone: string;
    }>
  ) {
    super();

    this.setAddress(d3eParams?.address ?? "");

    this.setCurrency(d3eParams?.currency ?? "USD");

    this.setEmail(d3eParams?.email ?? "");

    this.setLegalName(d3eParams?.legalName ?? "");

    this.setName(d3eParams?.name ?? "");

    this.setPhone(d3eParams?.phone ?? "");

    this.setTimezone(d3eParams?.timezone ?? "UTC");
  }
  public get d3eType(): string {
    return "Organization";
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

    this.updateD3EChanges(Organization._NAME, this._name);

    this._name = val;

    this.fire("name", this);
  }
  public get legalName(): string {
    return this._legalName;
  }
  public setLegalName(val: string): void {
    let isValChanged: boolean = this._legalName !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Organization._LEGALNAME, this._legalName);

    this._legalName = val;

    this.fire("legalName", this);
  }
  public get email(): string {
    return this._email;
  }
  public setEmail(val: string): void {
    let isValChanged: boolean = this._email !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Organization._EMAIL, this._email);

    this._email = val;

    this.fire("email", this);
  }
  public get phone(): string {
    return this._phone;
  }
  public setPhone(val: string): void {
    let isValChanged: boolean = this._phone !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Organization._PHONE, this._phone);

    this._phone = val;

    this.fire("phone", this);
  }
  public get address(): string {
    return this._address;
  }
  public setAddress(val: string): void {
    let isValChanged: boolean = this._address !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Organization._ADDRESS, this._address);

    this._address = val;

    this.fire("address", this);
  }
  public get currency(): string {
    return this._currency;
  }
  public setCurrency(val: string): void {
    let isValChanged: boolean = this._currency !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Organization._CURRENCY, this._currency);

    this._currency = val;

    this.fire("currency", this);
  }
  public get timezone(): string {
    return this._timezone;
  }
  public setTimezone(val: string): void {
    let isValChanged: boolean = this._timezone !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(Organization._TIMEZONE, this._timezone);

    this._timezone = val;

    this.fire("timezone", this);
  }
  public get(field: number): any {
    switch (field) {
      case Organization._NAME: {
        return this._name;
      }

      case Organization._LEGALNAME: {
        return this._legalName;
      }

      case Organization._EMAIL: {
        return this._email;
      }

      case Organization._PHONE: {
        return this._phone;
      }

      case Organization._ADDRESS: {
        return this._address;
      }

      case Organization._CURRENCY: {
        return this._currency;
      }

      case Organization._TIMEZONE: {
        return this._timezone;
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
  public deepClone(clearId = true): Organization {
    let ctx: CloneContext = new CloneContext({ "clearId": clearId });

    return ctx.startClone(this);
  }
  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(dbObj: DBObject, ctx: CloneContext): void {
    let obj: Organization = dbObj as Organization;

    obj.id = this.id;

    obj.setName(this._name);

    obj.setLegalName(this._legalName);

    obj.setEmail(this._email);

    obj.setPhone(this._phone);

    obj.setAddress(this._address);

    obj.setCurrency(this._currency);

    obj.setTimezone(this._timezone);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case Organization._NAME: {
        this.setName(value as string);
        break;
      }

      case Organization._LEGALNAME: {
        this.setLegalName(value as string);
        break;
      }

      case Organization._EMAIL: {
        this.setEmail(value as string);
        break;
      }

      case Organization._PHONE: {
        this.setPhone(value as string);
        break;
      }

      case Organization._ADDRESS: {
        this.setAddress(value as string);
        break;
      }

      case Organization._CURRENCY: {
        this.setCurrency(value as string);
        break;
      }

      case Organization._TIMEZONE: {
        this.setTimezone(value as string);
        break;
      }
    }
  }
}
