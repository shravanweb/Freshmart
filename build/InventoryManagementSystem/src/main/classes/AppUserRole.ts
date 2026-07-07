export default class AppUserRole {
  public static readonly OrganizationAdmin = new AppUserRole(
    "OrganizationAdmin",
    "OrganizationAdmin",
    0
  );
  public static readonly StoreManager = new AppUserRole(
    "StoreManager",
    "StoreManager",
    1
  );
  public static readonly WarehouseManager = new AppUserRole(
    "WarehouseManager",
    "WarehouseManager",
    2
  );
  public static readonly PurchaseManager = new AppUserRole(
    "PurchaseManager",
    "PurchaseManager",
    3
  );
  public static readonly InventoryClerk = new AppUserRole(
    "InventoryClerk",
    "InventoryClerk",
    4
  );
  public static readonly SalesStaff = new AppUserRole(
    "SalesStaff",
    "SalesStaff",
    5
  );
  public static readonly Accountant = new AppUserRole(
    "Accountant",
    "Accountant",
    6
  );
  public static readonly Viewer = new AppUserRole("Viewer", "Viewer", 7);
  private _ident: string;
  private _name: string;
  private _index: number;
  public constructor(_ident: string, _name: string, _index: number) {
    this._ident = _ident;

    this._name = _name;

    this._index = _index;
  }
  public static get values(): AppUserRole[] {
    return [
      AppUserRole.OrganizationAdmin,
      AppUserRole.StoreManager,
      AppUserRole.WarehouseManager,
      AppUserRole.PurchaseManager,
      AppUserRole.InventoryClerk,
      AppUserRole.SalesStaff,
      AppUserRole.Accountant,
      AppUserRole.Viewer,
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
