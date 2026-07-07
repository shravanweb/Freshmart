import React from "react";
import ObjectObservable, {
  ChangeHandler,
  IObservable,
  PathHandler,
} from "./ObjectObservable";

export default abstract class ObservableState<T extends React.Component>
  implements IObservable
{
  public observable = new ObjectObservable();
  public enableBuild = false;
  private component: T;

  initState(component: T) {
    this.observable.initListeners();
    this.component = component;
  }

  public rebuild() {
    // TODO: Use __DEV__ here?
    let __DEV__ = false;
    // https://stackoverflow.com/questions/47192891/detect-if-running-a-debug-or-release-build-at-runtime
    if (this.enableBuild && !__DEV__) {
      this.component.setState(() => {});
    }
  }

  public removeObservable(childPropName: string, child: ObjectObservable) {
    this.observable.removeObservable(childPropName, child);
  }

  public updateObservable(
    childPropName: string,
    oldChild: ObjectObservable,
    newChild: ObjectObservable
  ) {
    this.observable.updateObservable(childPropName, oldChild, newChild);
  }

  public updateObservableColl(
    childPropName: string,
    oldChild: Iterable<ObjectObservable>,
    newChild: Iterable<ObjectObservable>
  ) {
    this.observable.updateObservableColl(childPropName, oldChild, newChild);
  }

  public updateSyncCollProperty(
    prop: string,
    value: Iterable<ObjectObservable>
  ) {
    if (value != null) {
      value.forEach((v) => this.observable.updateObservable(prop, null, v));
    }
  }

  public updateSyncProperty(prop: string, value: ObjectObservable) {
    this.observable.updateObservable(prop, null, value);
  }

  public on(paths: string[], handler: PathHandler) {
    this.observable.on(paths, handler);
  }

  public on2(path: string, handler: ChangeHandler) {
    this.observable.on2(path, handler);
  }

  public fire(path: string, parent: any, value?: any, added = false) {
    this.observable.fire(path, parent, value, added);
  }

  public dispose() {
    this.observable.dispose();
    this.enableBuild = false;
  }

  public getDependency(childPropName: string): Set<string> {
    return this.observable.getDependency(childPropName);
  }

  public addDependency(
    childPropName: string,
    parent: IObservable,
    paths: Set<string>
  ) {
    this.observable.addDependency(childPropName, parent, paths);
  }

  _fireInternal(
    path: string,
    parent: any,
    value: any,
    added: boolean,
    visited: Map<IObservable, Set<string>>
  ): void {}
}
