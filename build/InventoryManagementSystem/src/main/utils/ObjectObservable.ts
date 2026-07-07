import { Runnable } from "../classes/core";
import MapExt from "../classes/MapExt";
import Timer from "./Timer";

export type PathHandler = () => void;
export type ChangeHandler = (parent: any, value: any, added: boolean) => void;

export abstract class IObservable {
  abstract removeObservable(
    childPropName: string,
    child: ObjectObservable
  ): void;
  abstract updateObservable(
    childPropName: string,
    oldChild: ObjectObservable,
    newChild: ObjectObservable
  ): void;
  abstract updateObservableColl(
    childPropName: string,
    oldChild: ObjectObservable[],
    newChild: ObjectObservable[]
  ): void;
  abstract fire(path: string, parent: any, value: null, added: false): void;
  abstract _fireInternal(
    path: string,
    parent: any,
    value: any,
    added: boolean,
    visited: Map<IObservable, Set<string>>
  ): void;
  abstract dispose(): void;
  abstract getDependency(childPropName: string): Set<string>;
  abstract addDependency(
    childPropName: string,
    parent: IObservable,
    paths: Set<string>
  ): void;
}

class _Parent {
  public parent: IObservable;
  public deps: Set<string>;
  constructor(parent: IObservable, deps: Set<string>) {
    this.parent = parent;
    this.deps = deps;
  }
}

export default class ObjectObservable implements IObservable {
  public initDone: boolean = false;
  public get nameOfModel(): string {
    return "";
  }

  //Can have more parents with same property name.
  public parents: Map<string, _Parent[]> = new Map();
  public childs: Map<string, ObjectObservable[]> = new Map();
  public pathListeners: Map<string, PathHandler[]> = new Map();
  public changeListeners: Map<string, ChangeHandler[]> = new Map();

  public dispose(): void {
    this.pathListeners.clear();
    this.changeListeners.clear();
    this.childs.forEach((cs, k) => cs.forEach((v) => v._removeParent(this, k)));
  }

  public removeObservable(
    childPropName: string,
    child: ObjectObservable
  ): void {
    if (child != null) {
      this.childs.remove(childPropName);
      child._removeParent(this, childPropName);
    }
  }

  public updateObservable(
    childPropName: string,
    oldChild: ObjectObservable,
    newChild: ObjectObservable
  ): void {
    this.removeObservable(childPropName, oldChild);
    if (newChild != null) {
      let cs: ObjectObservable[] = this.childs.get(childPropName);
      if (cs == null) {
        cs = [];
        this.childs.set(childPropName, cs);
      }
      cs.push(newChild);
      newChild._addParent(this, childPropName);
    }
  }

  public updateObservableColl(
    childPropName: string,
    oldChild: Iterable<ObjectObservable>,
    newChild: Iterable<ObjectObservable>
  ): void {
    if (oldChild != null) {
      oldChild.forEach((o: ObjectObservable) =>
        this.removeObservable(childPropName, o)
      );
    }
    if (newChild != null) {
      newChild.forEach((o: ObjectObservable) =>
        this.updateObservable(childPropName, null, o)
      );
    }
  }

  public initListeners(): void {
    this.initDone = true;
  }

  public _removeParent(parent: IObservable, childPropName: string): void {
    let parents: _Parent[] = this.parents.get(childPropName);
    if (parents == null) {
      return;
    }
    parents.removeWhere((p) => p.parent == parent);
    if (this.parents.isEmpty) {
      this.dispose();
    }
  }

  public _addParent(parent: IObservable, childPropName: string): void {
    let parents: _Parent[] = this.parents.get(childPropName);
    if (parents == null) {
      parents = [];
      this.parents.set(childPropName, parents);
    }
    if (!this.initDone) {
      this.initListeners();
    }
    let paths: Set<string> = parent.getDependency(childPropName);
    parents.push(new _Parent(parent, paths));
    this._addPathsToChildren(paths);
  }

  public _addPathsToChildren(paths: Set<string>): void {
    this.childs.forEach((children, key) => {
      children.forEach((child) =>
        child.addDependency(
          key,
          this,
          paths
            .where((x) => x.startsWith(key + "."))
            .map((f) => f.substring(key.length + 1))
            .toSet()
        )
      );
    });
  }

  public getDependency(name: string): Set<string> {
    return [
      ...this.pathListeners.keys(),
      ...this.changeListeners.keys(),
      ...MapExt.values(this.parents)
        .expand((f) => f)
        .expand((x) => x.deps),
    ]
      .where((x) => x.startsWith(name + "."))
      .map((f) => f.substring(name.length + 1))
      .toSet();
  }

  public addDependency(
    childPropName: string,
    parent: IObservable,
    paths: Set<string>
  ): void {
    if (paths.isEmpty) {
      return;
    }
    let parents: _Parent[] = this.parents.get(childPropName);
    let p: _Parent = parents.firstWhere((p) => p.parent === parent, {
      orElse: () => null,
    });
    if (p == null) {
      return;
    }
    p.deps.addAll(paths);
    this._addPathsToChildren(paths);
  }

  public on(paths: string[], handler: PathHandler): Runnable {
    let disposibles: Runnable[] = [];
    paths.forEach((p) => {
      let handlers: PathHandler[] = this.pathListeners.get(p);
      if (handlers == null) {
        handlers = [];
        this.pathListeners.set(p, handlers);
      }
      handlers.push(handler);
      disposibles.push(() => handlers.remove(handler));
    });
    this._addPathsToChildren(paths.toSet());
    return () => disposibles.forEach((d) => d());
  }

  public on2(path: string, handler: ChangeHandler): Runnable {
    let handlers: ChangeHandler[] = this.changeListeners.get(path);
    if (handlers == null) {
      handlers = [];
      this.changeListeners.set(path, handlers);
    }
    handlers.push(handler);
    let set: Set<string> = new Set<string>();
    set.add(path);
    this._addPathsToChildren(set);
    return () => handlers.remove(handler);
  }

  public fire(path: string, parent: any, value?: any, added = false): void {
    let visited: Map<IObservable, Set<string>> = new Map();
    this._fireInternal(path, parent, value, added, visited);
    this._fireInternal("*", parent, value, added, visited);
  }

  public _fireInternal(
    path: string,
    parent: any,
    value: any,
    added: boolean,
    visited: Map<IObservable, Set<string>>
  ): void {
    if (visited.get(this) != null && visited.get(this).contains(path)) {
      return;
    } else {
      if (visited.get(this) == null) {
        visited.set(this, new Set<string>());
      }
      visited.get(this).add(path);
    }
    let handlers: PathHandler[] = this.pathListeners.get(path);
    if (handlers != null) {
      handlers.forEach((h) =>
        ObjectObservable.schedule(() => h(), parent, false)
      );
    }
    let changeHandlers: ChangeHandler[] = this.changeListeners.get(path);
    if (changeHandlers != null) {
      changeHandlers.forEach((h) =>
        ObjectObservable.schedule(
          () => h(parent, value, added),
          parent,
          path.endsWith("*")
        )
      );
    }
    if (this.nameOfModel !== "") {
      this.childs.forEach((cs, k) =>
        cs.forEach((v) =>
          v._fireInternal(
            "master" + this.nameOfModel + "." + path,
            parent,
            value,
            added,
            visited
          )
        )
      );
    }

    //Inform to master if change is not from master
    if (!path.startsWith("master") || path === "master") {
      let map: Map<string, IObservable[]>;
      this.parents.forEach((ps, name) =>
        ps.forEach((p) => {
          if (p.deps.contains(path)) {
            if (map == null) {
              map = new Map<string, IObservable[]>();
            }
            let list: IObservable[] = map.get(name + "." + path);
            if (list == null) {
              list = [];
              map.set(name + "." + path, list);
            }
            list.push(p.parent);
          }
        })
      );
      map?.forEach((val, key) => {
        val.forEach((i) => i._fireInternal(key, parent, value, added, visited));
      });
    }
  }

  public static schedule(o: Runnable, p: any, star: boolean): void {
    if (star) {
      if (ObjectObservable.starParents.contains(p)) {
        return;
      }
      ObjectObservable.starParents.add(p);
    }
    if (ObjectObservable.actions.isEmpty) {
      Timer.run(() => {
        while (ObjectObservable.actions.isNotEmpty) {
          let r: Runnable = ObjectObservable.actions.removeAt(0);
          r();
        }
        ObjectObservable.starParents.clear();
      });
    }
    ObjectObservable.actions.push(o);
  }

  public static actions: Runnable[] = [];
  public static starParents: Set<any> = new Set();
}
