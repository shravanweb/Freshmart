import Uri from "./Uri";

export default class UriData {
  static fromUri(uri: Uri): UriData {
    return new UriData();
  }

  static parse(uri: string): UriData {
    return new UriData();
  }

  toString(): string {
    return "";
  }
}
