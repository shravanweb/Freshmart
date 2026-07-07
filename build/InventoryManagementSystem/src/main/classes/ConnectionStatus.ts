export default class ConnectionStatus {
  public static readonly Connecting = new ConnectionStatus(
    "Connecting",
    "Connecting",
    0
  );
  public static readonly Connected = new ConnectionStatus(
    "Connected",
    "Connected",
    1
  );
  public static readonly ConnectionBusy = new ConnectionStatus(
    "ConnectionBusy",
    "ConnectionBusy",
    2
  );
  public static readonly ConnectionNormal = new ConnectionStatus(
    "ConnectionNormal",
    "ConnectionNormal",
    3
  );
  public static readonly ConnectionFailed = new ConnectionStatus(
    "ConnectionFailed",
    "ConnectionFailed",
    4
  );
  public static readonly RestoreFailed = new ConnectionStatus(
    "RestoreFailed",
    "RestoreFailed",
    5
  );
  public static readonly AuthFailed = new ConnectionStatus(
    "AuthFailed",
    "AuthFailed",
    6
  );
  private _ident: string;
  private _name: string;
  private _index: number;
  public constructor(_ident: string, _name: string, _index: number) {
    this._ident = _ident;

    this._name = _name;

    this._index = _index;
  }
  public static get values(): ConnectionStatus[] {
    return [
      ConnectionStatus.Connecting,
      ConnectionStatus.Connected,
      ConnectionStatus.ConnectionBusy,
      ConnectionStatus.ConnectionNormal,
      ConnectionStatus.ConnectionFailed,
      ConnectionStatus.RestoreFailed,
      ConnectionStatus.AuthFailed,
    ];
  }
  public get ident(): string {
    return this._ident;
  }
  public get name(): string {
    return this._name;
  }
  public get index(): number {
    return this._index;
  }
  public toString(): string {
    return this._name;
  }
}
