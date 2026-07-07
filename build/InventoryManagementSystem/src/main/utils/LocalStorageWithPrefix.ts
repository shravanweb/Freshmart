export default class LocalStorageWithPrefix {
  prefix: string;

  constructor(prefix: string) {
    this.prefix = prefix;
  }
  clear(): void {
    localStorage.removeItem(this.prefix);
  }
  getItem(key: string): string {
    return JSON.parse(localStorage.getItem(this.prefix))[key];
  }
  key(index: number): string {
    throw new Error("Method not implemented.");
  }
  removeItem(key: string): void {
    var obj = JSON.parse(localStorage.getItem(this.prefix)) || {};
    delete obj[key];
    localStorage.setItem(this.prefix, JSON.stringify(obj));
  }
  setItem(key: string, value: string): void {
    var obj = JSON.parse(localStorage.getItem(this.prefix)) || {};
    obj[key] = value;
    localStorage.setItem(this.prefix, JSON.stringify(obj));
  }
}
