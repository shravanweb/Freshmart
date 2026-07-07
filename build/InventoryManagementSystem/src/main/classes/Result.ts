import ListWrapper from "../utils/ListWrapper";
import ResultStatus from "./ResultStatus";

export default class Result<T> {
  private _status: ResultStatus = ResultStatus.Success;
  private _errors: Array<string>;
  private readonly _value: T;
  public constructor(
    d3eParams?: Partial<{
      errors: Array<string>;
      status: ResultStatus;
      value: T;
    }>
  ) {
    if (d3eParams?.errors) {
      this._errors = d3eParams?.errors;
    }
    if (d3eParams?.status) {
      this._status = d3eParams?.status;
    }
    if (d3eParams?.value) {
      this._value = d3eParams?.value;
    }
  }

  public get status(): ResultStatus {
    return this._status;
  }
  public get errors(): Array<string> {
    return this._errors;
  }
  public get value(): T {
    return this._value;
  }
}
