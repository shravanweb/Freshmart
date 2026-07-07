export default class Env {
  private static _configObj: Env;
  private _baseHttpUrl: string;
  private _baseWSurl: string;
  private _buildNumber: string;
  private _buildVersion: string;
  private constructor() {}
  private static _init() {
    return new Env();
  }
  public static get() {
    if (Env._configObj == null) {
      Env._configObj = Env._init();
    }

    return Env._configObj;
  }
  public load(configuration: any): void {
    this._baseHttpUrl = configuration.baseHttpUrl;

    this._baseWSurl = configuration.baseWSurl;

    this._buildNumber = configuration.buildNumber;

    this._buildVersion = configuration.buildVersion;

    this._buildVersion = configuration.buildVersion;
  }
  public get baseHttpUrl(): string {
    return this._baseHttpUrl;
  }
  public get baseWSurl(): string {
    return this._baseWSurl;
  }
  public get resolvedHttpUrl(): string {
    if (this._baseHttpUrl != null && this._baseHttpUrl.length > 0) {
      return this._baseHttpUrl;
    }
    if (typeof window !== "undefined") {
      return window.location.origin;
    }
    return "";
  }
  public get resolvedWSurl(): string {
    if (this._baseWSurl != null && this._baseWSurl.length > 0) {
      return this._baseWSurl;
    }
    if (typeof window !== "undefined") {
      const protocol =
        window.location.protocol === "https:" ? "wss:" : "ws:";
      return protocol + "//" + window.location.host;
    }
    return "";
  }
  public get buildNumber(): string {
    return this._buildNumber;
  }
  public get buildVersion(): string {
    return this._buildVersion;
  }
}
