import { D3ETemplate } from "../rocket/D3ETemplate";
import DBObject from "./DBObject";
import DBSaveStatus from "./DBSaveStatus";

export default class ReferenceCatch {
  public all: Map<number, Map<number, DBObject>> = new Map();
  public refCounts: Map<number, Map<number, number>> = new Map();

  constructor() {}

  private static _instance: ReferenceCatch;

  public static get(): ReferenceCatch {
    if (ReferenceCatch._instance == null) {
      ReferenceCatch._instance = new ReferenceCatch();
    }
    return ReferenceCatch._instance;
  }

  public updateLocalId(type: number, localId: number, id: number): void {
    if (localId === id) {
      return;
    }
    let byId: Map<number, DBObject> = this.all.get(type);
    if (byId == null) {
      return null;
    }
    let obj: DBObject = byId.get(localId);
    if (obj != null) {
      obj.id = id;
      byId.set(id, obj);
      obj.saveStatus = DBSaveStatus.Saved;
      byId.remove(localId);
    }
  }

  public findObject(type: number, id: number): DBObject {
    if (id == null || id === 0) {
      return null;
    }
    let byId: Map<number, DBObject> = this.all.get(type);
    if (byId == null) {
      return null;
    }
    return byId.get(id);
  }

  public addObject(obj: DBObject): void {
    if (obj.id == null || obj.id === 0) {
      return;
    }
    if (obj.id > 0) {
      obj.saveStatus = DBSaveStatus.Saved;
    }
    let type: number = D3ETemplate.typeInt(obj.d3eType);
    let byId: Map<number, DBObject> = this.all.get(type);
    if (byId == null) {
      byId = new Map();
      this.all.set(type, byId);
    }
    byId.set(obj.id, obj);
  }

  public updateReference(old: DBObject, obj: DBObject): void {
    if (old === obj) {
      return;
    }
    if (old != null) {
      let type: number = D3ETemplate.typeInt(old.d3eType);
      let rc: Map<number, number> = this.refCounts.get(type);
      if (rc == null) {
        rc = new Map();
        this.refCounts.set(type, rc);
      }
      let count: number = rc.get(old.id);
      if (count != null) {
        rc.set(old.id, count - 1);
      }
    }
    if (obj != null) {
      let type: number = D3ETemplate.typeInt(obj.d3eType);
      let rc: Map<number, number> = this.refCounts.get(type);
      if (rc == null) {
        rc = new Map();
        this.refCounts.set(type, rc);
      }
      let count: number = rc.get(obj.id);
      if (count == null) {
        count = 0;
      }
      rc.set(obj.id, count + 1);
    }
  }
}
