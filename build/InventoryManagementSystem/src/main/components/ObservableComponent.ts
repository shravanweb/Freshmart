import React, { Component } from "react";
import PageNavigator from "../classes/PageNavigator";
import ObjectObservable, { ChangeHandler } from "../utils/ObjectObservable";
import D3EDisposable from "../rocket/D3EDisposable";
import ListWrapper from "../utils/ListWrapper";

export default class ObservableComponent<T> extends Component<T> {
  enableBuild: boolean = false;
  private _listDisposables: D3EDisposable[] = [];
  rebuildTimer: number | null = null;
  observable = new ObjectObservable();
  _mounted = false;

  get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }

  initState() {
    this.observable.initListeners();
  }

  componentDidMount() {
    this._mounted = true;
  }

  componentWillUnmount() {
    this.dispose();
  }

  componentDidUpdate(prevProps: T) {}

  updateObservable(
    childPropName: string,
    oldChild: ObjectObservable,
    newChild: ObjectObservable
  ) {
    this.observable.updateObservable(childPropName, oldChild, newChild);
  }

  updateObservableColl(
    childPropName: string,
    oldChild: ObjectObservable[],
    newChild: ObjectObservable[]
  ) {
    this.observable.updateObservableColl(childPropName, oldChild, newChild);
  }

  removeObservable(childPropName: string, child: ObjectObservable) {
    this.observable.removeObservable(childPropName, child);
  }

  on(paths: string[], handler: () => void) {
    this.observable.on(paths, handler);
  }

  on2(path: string, handler: ChangeHandler) {
    this.observable.on2(path, handler);
  }

  updateSyncProperty(prop: string, value: ObjectObservable) {
    this.observable.updateObservable(prop, null, value);
  }

  updateSyncCollProperty(prop: string, value: ObjectObservable[]) {
    if (value != null) {
      value.forEach((v) => this.observable.updateObservable(prop, null, v));
    }
  }

  fire(path: string, parent: any, value: any = {}, added?: boolean) {
    this.observable.fire(path, parent, value, added);
  }

  subscribeToList<T>(list: Array<T>, path: string) {
    if (list instanceof ListWrapper) {
      let disp: D3EDisposable = (list as ListWrapper<T>).subscribe(() =>
        this.fire(path, this)
      );
      this._listDisposables.add(disp);
    }
  }

  rebuild = () => {
    if (!this._mounted || !this.enableBuild) {
      return;
    }

    if (this.rebuildTimer != null) {
      window.cancelAnimationFrame(this.rebuildTimer);
    }

    this.rebuildTimer = window.requestAnimationFrame(() => {
      this.rebuildTimer = null;

      if (this._mounted && this.enableBuild) {
        this.setState({});
      }
    });
  };

  dispose() {
    if (this.rebuildTimer != null) {
      window.cancelAnimationFrame(this.rebuildTimer);

      this.rebuildTimer = null;
    }

    this._listDisposables.forEach((d) => d.dispose());
    this.observable.dispose();
    this.enableBuild = false;
  }
}
