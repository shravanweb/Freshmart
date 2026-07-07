import ResultStatus from "./ResultStatus";
import ListWrapper from "../utils/ListWrapper";
import Organization from "../models/Organization";
import DBObject from "../utils/DBObject";
import CollectionUtils from "../utils/CollectionUtils";

export default class OrganizationItem extends DBObject {
  private _id: number = DBObject.nextStructId();
  private static readonly _ERRORS: number = 0;
  private static readonly _ITEMS: number = 1;
  private static readonly _STATUS: number = 2;
  private _status: ResultStatus = ResultStatus.Success;
  private _errors: Array<string> = ListWrapper.primitive(
    this,
    "errors",
    OrganizationItem._ERRORS
  );
  private _items: Array<Organization> = ListWrapper.vanilla(
    this,
    "items",
    OrganizationItem._ITEMS
  );
  public constructor(
    d3eParams?: Partial<{
      errors: Array<string>;
      items: Array<Organization>;
      status: ResultStatus;
    }>
  ) {
    super();

    if (d3eParams?.errors) {
      this.setErrors(d3eParams?.errors);
    }

    if (d3eParams?.items) {
      this.setItems(d3eParams?.items);
    }

    if (d3eParams?.status) {
      this.setStatus(d3eParams?.status);
    }
  }
  public get id(): number {
    return this._id;
  }
  public set id(id: number) {
    this._id = id;
  }
  public get d3eType(): string {
    return "OrganizationItem";
  }
  public clear(): void {}
  public initListeners(): void {
    super.initListeners();
  }
  public get status(): ResultStatus {
    return this._status;
  }
  public setStatus(val: ResultStatus): void {
    let isValChanged: boolean = this._status !== val;

    if (!isValChanged) {
      return;
    }

    this.updateD3EChanges(OrganizationItem._STATUS, this._status.index);

    this._status = val;

    this.fire("status", this);
  }
  public get errors(): Array<string> {
    return this._errors;
  }
  public setErrors(val: Array<string>): void {
    let isValChanged: boolean = CollectionUtils.isNotEquals(this._errors, val);

    if (!isValChanged) {
      return;
    }

    if (!this.d3eChanges.contains(OrganizationItem._ERRORS)) {
      let _old: Array<string> = Array.from(this._errors);

      this.updateD3EChanges(OrganizationItem._ERRORS, _old);
    }

    this._errors.clear();

    this._errors.addAll(val);

    this.fire("errors", this);
  }
  public addToErrors(val: string, index: number = -1): void {
    let _old: Array<string> = [];

    let _isNewChange: boolean = !this.d3eChanges.contains(
      OrganizationItem._ERRORS
    );

    if (_isNewChange) {
      _old = Array.from(this._errors);
    }

    if (index === -1) {
      if (!this._errors.contains(val)) this._errors.add(val);
    } else {
      this._errors.remove(this._errors.elementAt(index));

      this._errors.add(val);
    }

    this.fire("errors", this, val, true);

    if (_isNewChange) {
      this.updateD3EChanges(OrganizationItem._ERRORS, _old);
    }
  }
  public removeFromErrors(val: string): void {
    let _old: Array<string> = [];

    let _isNewChange: boolean = !this.d3eChanges.contains(
      OrganizationItem._ERRORS
    );

    if (_isNewChange) {
      _old = Array.from(this._errors);
    }

    this._errors.remove(val);

    this.fire("errors", this, val, false);

    if (_isNewChange) {
      this.updateD3EChanges(OrganizationItem._ERRORS, _old);
    }
  }
  public get items(): Array<Organization> {
    return this._items;
  }
  public setItems(val: Array<Organization>): void {
    let isValChanged: boolean = CollectionUtils.isNotEquals(this._items, val);

    if (!isValChanged) {
      return;
    }

    if (!this.d3eChanges.contains(OrganizationItem._ITEMS)) {
      let _old: Array<Organization> = Array.from(this._items);

      this.updateD3EChanges(OrganizationItem._ITEMS, _old);
    }

    this.updateObservableColl("items", this._items, val);

    this._items.clear();

    this._items.addAll(val);

    this.fire("items", this);
  }
  public addToItems(val: Organization, index: number = -1): void {
    let _old: Array<Organization> = [];

    let _isNewChange: boolean = !this.d3eChanges.contains(
      OrganizationItem._ITEMS
    );

    if (_isNewChange) {
      _old = Array.from(this._items);
    }

    if (index === -1) {
      if (!this._items.contains(val)) this._items.add(val);
    } else {
      this._items.remove(this._items.elementAt(index));

      this._items.add(val);
    }

    this.fire("items", this, val, true);

    this.updateObservable("items", null, val);

    if (_isNewChange) {
      this.updateD3EChanges(OrganizationItem._ITEMS, _old);
    }
  }
  public removeFromItems(val: Organization): void {
    let _old: Array<Organization> = [];

    let _isNewChange: boolean = !this.d3eChanges.contains(
      OrganizationItem._ITEMS
    );

    if (_isNewChange) {
      _old = Array.from(this._items);
    }

    this._items.remove(val);

    this.fire("items", this, val, false);

    this.removeObservable("items", val);

    if (_isNewChange) {
      this.updateD3EChanges(OrganizationItem._ITEMS, _old);
    }
  }
  public set(field: number, value: any): void {
    switch (field) {
      case OrganizationItem._STATUS: {
        this.setStatus(ResultStatus.values[value as number]);
        break;
      }

      case OrganizationItem._ERRORS: {
        this.setErrors((value as Array<any>).cast<string>().toList());
        break;
      }

      case OrganizationItem._ITEMS: {
        this.setItems((value as Array<any>).cast<Organization>().toList());
        break;
      }
    }
  }
  public get(field: number): any {
    switch (field) {
      case OrganizationItem._STATUS: {
        return this._status.index;
      }

      case OrganizationItem._ERRORS: {
        return this._errors;
      }

      case OrganizationItem._ITEMS: {
        return this._items;
      }
      default: {
        return null;
      }
    }
  }
  public equals(other: any): boolean {
    return (
      this === other ||
      (other instanceof OrganizationItem &&
        this._status === other._status &&
        this._errors === other._errors &&
        this._items === other._items)
    );
  }
  public get hashCode(): number {
    return (
      (this._status?.hashCode ?? 0) +
      (this._errors?.hashCode ?? 0) +
      (this._items?.hashCode ?? 0)
    );
  }
}
