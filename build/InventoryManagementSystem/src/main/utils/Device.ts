import ReferenceCatch from "./ReferenceCatch";
import JSONUtils from "./JSONUtils";
import DBObject from "./DBObject";
import LocalStorageWithPrefix from "./LocalStorageWithPrefix";

export default class Device {
  static readonly storage = new LocalStorageWithPrefix("auth"); // TODO: Should be stored under "auth"
  private static readonly _referenceCatch: ReferenceCatch =
    ReferenceCatch.get();

  static async put(key: string, value: any): Promise<void> {
    if (value instanceof DBObject) {
      let json = JSONUtils.toJsonString(value);
      await Device.storage.setItem(key, json);
    } else {
      // value is primitive
      await Device.storage.setItem(key, value);
    }
  }

  static async get(key: string): Promise<any> {
    // Will be either primitive or Map, since that's what we stored.
    let value = await Device.storage.getItem(key);

    try {
      return JSONUtils.fromJsonString(value);
    } catch (e) {
      return value;
    }
  }
}
