import ConnectionStatus from "./ConnectionStatus";
import DBObject from "../utils/DBObject";

export default class ConnectionEvent extends DBObject {
  private _id: number = DBObject.nextStructId();
  private _status: ConnectionStatus = ConnectionStatus.Connecting;
  public constructor(d3eParams?: Partial<{ status: ConnectionStatus }>) {
    super();

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
    return "ConnectionEvent";
  }
  public clear(): void {}
  public initListeners(): void {
    super.initListeners();
  }
  public get status(): ConnectionStatus {
    return this._status;
  }
  public setStatus(val: ConnectionStatus): void {
    let isValChanged: boolean = this._status !== val;

    if (!isValChanged) {
      return;
    }

    this._status = val;

    this.fire("status", this);
  }
  public equals(other: any): boolean {
    return (
      this === other ||
      (other instanceof ConnectionEvent && this._status === other._status)
    );
  }
  public get hashCode(): number {
    return this._status?.hashCode ?? 0;
  }
}
