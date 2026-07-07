export default class PurchaseOrderStatus {
  public static readonly Draft = new PurchaseOrderStatus("Draft", "Draft", 0);
  public static readonly Submitted = new PurchaseOrderStatus(
    "Submitted",
    "Submitted",
    1
  );
  public static readonly Approved = new PurchaseOrderStatus(
    "Approved",
    "Approved",
    2
  );
  public static readonly PartiallyReceived = new PurchaseOrderStatus(
    "PartiallyReceived",
    "PartiallyReceived",
    3
  );
  public static readonly Received = new PurchaseOrderStatus(
    "Received",
    "Received",
    4
  );
  public static readonly Cancelled = new PurchaseOrderStatus(
    "Cancelled",
    "Cancelled",
    5
  );
  private _ident: string;
  private _name: string;
  private _index: number;
  public constructor(_ident: string, _name: string, _index: number) {
    this._ident = _ident;

    this._name = _name;

    this._index = _index;
  }
  public static get values(): PurchaseOrderStatus[] {
    return [
      PurchaseOrderStatus.Draft,
      PurchaseOrderStatus.Submitted,
      PurchaseOrderStatus.Approved,
      PurchaseOrderStatus.PartiallyReceived,
      PurchaseOrderStatus.Received,
      PurchaseOrderStatus.Cancelled,
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
