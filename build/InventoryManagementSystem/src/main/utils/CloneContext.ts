import BlockString from "../classes/BlockString";
import { Consumer } from "../classes/core";
import ExpressionString from "../classes/ExpressionString";
import { D3ETemplate } from "../rocket/D3ETemplate";
import { D3ETemplateType } from "../rocket/D3ETemplateTypes";
import DBObject from "./DBObject";
import GlobalFunctions from "./GlobalFunctions";

export default class CloneContext {
  public cache: Map<any, any> = new Map();
  public reverting = false;
  public clearId = false;
  constructor(params?: Partial<{ clearId: boolean }>) {
    this.clearId = params?.clearId || false;
  }
  childs: DBObject[] = [];

  public revert(obj: DBObject): void {
    this.reverting = true;
    let cloned: DBObject = this.cache.get(obj);
    let revertCache: Map<any, any> = new Map();
    this.cache.forEach((k, v) => revertCache.set(v, k));
    this.cache = revertCache;
    let oldClearId: boolean = this.clearId;
    this.clearId = false;
    this.startClone(cloned);
    this.clearId = oldClearId;
    this.childs = [];
  }

  public startClone<T extends DBObject>(obj: T): T {
    this.childs.push(obj);
    obj.collectChildValues(this);

    // The childs need to be cloned in 2 steps.
    // deepCloneIntoObj will look in the object cache, which might not be populated by that point.
    this.childs.forEach((c) => {
      if (c == null) {
        return;
      }
      this._cloneRefInternal(c);
    });
    this.childs.forEach((c) => {
      if (c == null) {
        return;
      }
      c.deepCloneIntoObj(this.cache.get(c), this);
    });
    this.childs.forEach((c) => {
      if (c == null) {
        return;
      }
      let obj: DBObject = this.cache.get(c);
      if (this.clearId) {
        obj.id = 0;
      }
    });
    this.childs.clear();

    // Now clone the actual object
    return this.cache.get(obj);
  }

  public _cloneRefInternal(obj: DBObject): DBObject {
    // This logic used to be in cloneRef
    // let typeIndex: number = D3ETemplate.typeInt(obj.d3eType);
    // let type: D3ETemplateType = D3ETemplate.types[typeIndex];
    let typeIndex: number = GlobalFunctions.typeInt(obj.d3eType);
    let type: D3ETemplateType = GlobalFunctions.types[typeIndex];
    let cloned: DBObject = type.creator();
    cloned.id = obj.id;
    this.cache.set(obj, cloned);
    return cloned;
  }

  public collectChilds<T extends DBObject>(exist: T[]): void {
    exist.forEach((e) => this.collectChild(e));
  }

  public collectChild<T extends DBObject>(exist: T): void {
    if (exist == null) {
      return;
    }
    this.childs.push(exist);
    exist.collectChildValues(this);
  }

  public cloneChildList<T extends DBObject>(
    exist: T[],
    setter: Consumer<T[]>
  ): void {
    if (exist == null || exist.isEmpty) {
      return;
    }
    // Get the cloned child from cache
    let cloned: T[] = exist.map((e) => this.cache.get(e) as T).toList();
    setter(cloned);
  }

  public cloneChildSet<T extends DBObject>(
    exist: Set<T>,
    setter: Consumer<Set<T>>
  ): void {
    let cloned: Set<T> = exist.map((e) => this.cache.get(e) as T).toSet();
    setter(cloned);
  }

  public cloneRefList<T extends DBObject>(list: T[]): T[] {
    let cloned: T[] = [];
    list.forEach((l) => cloned.push(this.cloneRef(l)));
    return cloned;
  }

  public cloneRefSet<T extends DBObject>(list: Set<T>): Set<T> {
    let cloned: Set<T> = new Set();
    list.forEach((l) => cloned.add(this.cloneRef(l)));
    return cloned;
  }

  public cloneChild<T extends DBObject>(
    exist: DBObject,
    setter: Consumer<T>
  ): void {
    if (exist == null) {
      setter(null);
    } else {
      let cloned: T = this.cache.get(exist) as T;
      setter(cloned);
    }
  }

  public cloneExpressionString(obj: ExpressionString): ExpressionString {
    if (this.reverting) {
      return this.cache.get(obj);
    }
    let clone: ExpressionString = new ExpressionString(obj.content);
    clone.attachment = obj.attachment;
    this.cache.set(obj, clone);
    return clone;
  }

  public cloneBlockString(obj: BlockString): BlockString {
    if (this.reverting) {
      return this.cache.get(obj);
    }
    let clone: BlockString = new BlockString(obj.content);
    clone.attachment = obj.attachment;
    this.cache.set(obj, clone);
    return clone;
  }

  public cloneRef<T extends DBObject>(obj: T): T {
    // Only called for non-creatable references
    if (obj == null) {
      return null;
    }
    if (this.reverting) {
      // TODO: Not sure about this
      return this.cache.get(obj);
    }
    let exist: DBObject;
    if (this.cache.containsKey(obj)) {
      // Referring to a child in the same object
      exist = this.cache.get(obj);
    } else {
      // Referring to a child in another object
      // So return the object as is.
      exist = obj;
    }
    return exist as T;
  }
}
