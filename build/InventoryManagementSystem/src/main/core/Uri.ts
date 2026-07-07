import { URL } from "whatwg-url";

export default class Uri {
  url: URL;

  private constructor(url: string) {
    this.url = new URL(url);
  }

  static get base(): Uri {
    return new Uri(window.location.href);
  }

  get origin(): string {
    return this.url.origin;
  }

  static https(
    authority: string,
    unencodedPath: string,
    queryParameters?: Map<string, string>
  ): Uri {
    let query = "";
    if (queryParameters) {
      query =
        "?" +
        [...queryParameters.entries()].map((one) => one.join("=")).join("&");
    }
    return new Uri("https://" + authority + "/" + unencodedPath + query);
  }

  static file(path: string, params?: Partial<{ windows: boolean }>): Uri {
    let sep = "/";
    let abs = false;
    if (params?.windows) {
      path = path.replaceAll("\\", sep);
    }
    // TODO
    return new Uri("file://" + path);
  }

  static directory(path: string, params?: { windows: boolean }): Uri {
    return Uri.file(path.length ? path + "/" : path, params);
  }

  static dataFromBytes(
    bytes: Array<number>,
    params?: {
      mimeType?: string;
      parameters?: Map<string, string>;
      percentEncoded?: boolean;
    }
  ): Uri {
    // TODO
    return new Uri("/");
  }

  static parse(uri: string, start: number = 0, end?: number) {
    if (end && end > start && end < uri.length) {
      uri = uri.substring(start, end);
    }
    return new Uri(uri);
  }

  get scheme(): string {
    return this.url.protocol;
  }

  get authority(): string {
    if (!this.url.username && !this.url.password) {
      return "";
    }
    return this.url.username + ":" + this.url.password;
  }

  get userInfo(): string {
    return this.url.username;
  }

  get path(): string {
    return this.url.pathname;
  }

  get query(): string {
    return this.url.search;
  }

  get fragment(): string {
    return this.url.hash.length ? this.url.hash.substring(1) : this.url.hash;
  }

  get pathSegments(): Array<string> {
    let path = this.path;
    if (!path.length || path === "/") {
      return [];
    }
    path = path.substring(1);
    return path.split("/");
  }

  get queryParameters(): Map<string, string> {
    return new Map([...this.url.searchParams.entries()]);
  }

  get queryParametersAll(): Map<string, Array<string>> {
    let map = new Map<string, string[]>();
    for (let [key, value] of this.url.searchParams.entries()) {
      let existing = map.get(key) || [];
      existing.push(value);
      map.set(key, existing);
    }
    return map;
  }

  get isAbsolute(): boolean {
    return !!this.scheme.length;
  }

  get hasScheme(): boolean {
    return !!this.scheme.length;
  }

  get hasAuthority(): boolean {
    return !!this.authority.length;
  }

  get hasPort(): boolean {
    return !!this.url.port.length;
  }

  get hasQuery(): boolean {
    return !!this.query.length;
  }

  get hasFragment(): boolean {
    return !!this.fragment.length;
  }

  get hasEmptyPath(): boolean {
    return !this.path.length;
  }

  get hasAbsolutePath(): boolean {
    return this.path.startsWith("/");
  }

  get hashCode(): number {
    return 0; // Implement your hash code logic here if needed
  }

  equals(other: any): boolean {
    return Object.is(this, other);
  }

  static parseIPv4Address(host: string): Array<number> {
    return []; // Implement your IPv4 parsing logic here if needed
  }

  static parseIPv6Address(
    host: string,
    params?: { start?: number; end?: number }
  ): Array<number> {
    return []; // Implement your IPv6 parsing logic here if needed
  }
}
