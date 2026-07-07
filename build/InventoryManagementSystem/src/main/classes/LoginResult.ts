import BaseUser from "../models/BaseUser";
import DBObject from "../utils/DBObject";

export default class LoginResult extends DBObject {
  private _id: number = DBObject.nextStructId();
  private static readonly _FAILUREMESSAGE: number = 0;
  private static readonly _SUCCESS: number = 1;
  private static readonly _TOKEN: number = 2;
  private static readonly _USEROBJECT: number = 3;
  private _success: boolean = false;
  private _userObject: BaseUser = null;
  private _token: string = "";
  private _failureMessage: string = "";
  public constructor(
    d3eParams?: Partial<{
      failureMessage: string;
      success: boolean;
      token: string;
      userObject: BaseUser;
    }>
  ) {
    super();

    if (d3eParams?.failureMessage) {
      this.setFailureMessage(d3eParams?.failureMessage);
    }

    if (d3eParams?.success) {
      this.setSuccess(d3eParams?.success);
    }

    if (d3eParams?.token) {
      this.setToken(d3eParams?.token);
    }

    if (d3eParams?.userObject) {
      this.setUserObject(d3eParams?.userObject);
    }
  }
  public get id(): number {
    return this._id;
  }
  public set id(id: number) {
    this._id = id;
  }
  public get d3eType(): string {
    return "LoginResult";
  }
  public clear(): void {}
  public initListeners(): void {
    super.initListeners();
  }
  public get success(): boolean {
    return this._success;
  }
  public setSuccess(val: boolean): void {
    let isValChanged: boolean = this._success !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(LoginResult._SUCCESS, this._success);

    this._success = val;

    this.fire("success", this);
  }
  public get userObject(): BaseUser {
    return this._userObject;
  }
  public setUserObject(val: BaseUser): void {
    let isValChanged: boolean = this._userObject !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(LoginResult._USEROBJECT, this._userObject);

    this.updateObservable("userObject", this._userObject, val);

    this._userObject = val;

    this.fire("userObject", this);
  }
  public get token(): string {
    return this._token;
  }
  public setToken(val: string): void {
    let isValChanged: boolean = this._token !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(LoginResult._TOKEN, this._token);

    this._token = val;

    this.fire("token", this);
  }
  public get failureMessage(): string {
    return this._failureMessage;
  }
  public setFailureMessage(val: string): void {
    let isValChanged: boolean = this._failureMessage !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(LoginResult._FAILUREMESSAGE, this._failureMessage);

    this._failureMessage = val;

    this.fire("failureMessage", this);
  }
  public set(field: number, value: any): void {
    switch (field) {
      case LoginResult._SUCCESS: {
        this.setSuccess(value as boolean);
        break;
      }

      case LoginResult._USEROBJECT: {
        this.setUserObject(value as BaseUser);
        break;
      }

      case LoginResult._TOKEN: {
        this.setToken(value as string);
        break;
      }

      case LoginResult._FAILUREMESSAGE: {
        this.setFailureMessage(value as string);
        break;
      }
    }
  }
  public get(field: number): any {
    switch (field) {
      case LoginResult._SUCCESS: {
        return this._success;
      }

      case LoginResult._USEROBJECT: {
        return this._userObject;
      }

      case LoginResult._TOKEN: {
        return this._token;
      }

      case LoginResult._FAILUREMESSAGE: {
        return this._failureMessage;
      }
      default: {
        return null;
      }
    }
  }
  public equals(other: any): boolean {
    return (
      this === other ||
      (other instanceof LoginResult &&
        this._success === other._success &&
        this._userObject === other._userObject &&
        this._token === other._token &&
        this._failureMessage === other._failureMessage)
    );
  }
  public get hashCode(): number {
    return (
      (this._success?.hashCode ?? 0) +
      (this._userObject?.hashCode ?? 0) +
      (this._token?.hashCode ?? 0) +
      (this._failureMessage?.hashCode ?? 0)
    );
  }
}
