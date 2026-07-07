import BaseUser from "../models/BaseUser";
import JSONUtils from "./JSONUtils";

export default class LocalDataStore {
  private _currentUser: BaseUser;
  private _token: string;
  readonly storage = localStorage;

  // To avoid setting null
  private static _initialized: boolean = false;
  private static _store: LocalDataStore;

  private static _authenticated: boolean = false;

  // TODO: When performing a logout (or session timeout), remove data from shared_preferences

  static get(): LocalDataStore {
    if (!this._initialized) {
      this._store = new LocalDataStore();
      this._store._currentUser = null;
      this._store._token = null;
      this._initialized = true;
    }
    return this._store;
  }

  async setUser(user: BaseUser, token: string): Promise<void> {
    this._currentUser = user;
    this._token = token;

    if (user == null || token == null) {
      this._currentUser = null;
      this._token = null;
      this.storage.removeItem("token");
      this.storage.removeItem("user");
      LocalDataStore.unauth();
    } else if (!LocalDataStore.authenticated) {
      this.storage.setItem("token", token);

      this.storage.setItem("user", this._toStore(user));
      LocalDataStore.auth();
    }
  }

  async currentUser(): Promise<BaseUser> {
    await this.getToken();
    return this._currentUser;
  }

  async getToken(): Promise<string> {
    if (this._token == null) {
      await this.storage.ready;
      this._token = this.storage.getItem("token") as string;
      this._currentUser = this._fromStore(this.storage.getItem("user"));
      if (this._currentUser != null) {
        this._currentUser.clear();
        if (this._token) {
          LocalDataStore.auth();
        }
      }
    }
    return this._token;
  }

  private _fromStore(data: string): BaseUser {
    if (data == null) {
      return null;
    }
    return JSONUtils.fromJsonString(data) as BaseUser;
  }

  private _toStore(data: BaseUser): string {
    return JSONUtils.toJsonString(data);
  }

  static get authenticated() {
    return LocalDataStore._authenticated;
  }

  static auth() {
    LocalDataStore._authenticated = true;
  }

  static unauth() {
    LocalDataStore._authenticated = false;
  }
}
