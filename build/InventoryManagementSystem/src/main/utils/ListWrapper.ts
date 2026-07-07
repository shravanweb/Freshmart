import { Runnable } from "../classes/core";
import ObservableComponent from "../components/ObservableComponent";
import Random from "../core/Random";
import D3EDisposable from "../rocket/D3EDisposable";
import DBObject from "./DBObject";
import ObjectObservable from "./ObjectObservable";

type ListChangeListener = () => void;

class ListDisposable implements D3EDisposable {
  constructor(private _dispose: Runnable) {}

  public dispose() {
    this._dispose?.();
  }
}

export default class ListWrapper<T> extends Array<T> {
  public listeners: ListChangeListener[] = [];

  private _master: DBObject; // required for updating D3EChanges
  private _obs: ObjectObservable; // See comment in widget constructor
  private _name: string;
  private _field: number;
  private _ignoreField: boolean = false;

  private _inverse = false;
  private _child = false;

  private constructor(
    master: DBObject,
    name: string,
    field: number,
    args?: Partial<{
      child: boolean;
      inverse: boolean;
      copy: boolean;
      obs: ObjectObservable;
      base: T[];
    }>
  ) {
    super();
    this._master = master;
    this._name = name;
    this._field = field;
    this._child = args?.child ?? false;
    this._inverse = args?.inverse ?? false;
    if (args?.copy) {
      super.addAll(args?.base ?? []); // To avoid fire in addAll
      this._obs = args?.obs;
    } else {
      this._obs = master;
    }
  }

  /// Widget constructor
  public static widget<T>(parent: ObservableComponent<any>, name: string) {
    let lw = new ListWrapper<T>(null, name, -1);
    lw._obs = parent.observable;
    lw._ignoreField = true;
    return lw;
  }

  public setWidgetMaster(master: ObservableComponent<any>) {
    this._obs = master.observable;
  }

  public static fromInput<T>(parent: Array<T>, name: string) {
    if (parent instanceof ListWrapper) {
      return parent;
    }
    let lw = new ListWrapper<T>(null, name, -1);
    lw._ignoreField = true;

    // This will only be called for Widgetss
    return lw;
  }

  /// DBObject constructors
  public static vanilla<T>(master: DBObject, name: string, field: number) {
    return new ListWrapper<T>(master, name, field);
  }

  public static inverse<T>(master: DBObject, name: string, field: number) {
    return new ListWrapper<T>(master, name, field, { inverse: true });
  }

  public static child<T>(master: DBObject, name: string, field: number) {
    return new ListWrapper<T>(master, name, field, { child: true });
  }

  public static primitive<T>(master: DBObject, name: string, field: number) {
    return new ListWrapper<T>(master, name, field);
  }

  public static reference<T>(master: DBObject, name: string, field: number) {
    return new ListWrapper<T>(master, name, field);
  }

  private static _fromList<T>(
    master: DBObject,
    obs: ObjectObservable,
    name: string,
    field: number,
    list: T[]
  ) {
    let lw = new ListWrapper<T>(master, name, field, {
      copy: true,
      obs: obs,
      base: list,
    });
    return lw;
  }

  public subscribe(listener: ListChangeListener): D3EDisposable {
    this.listeners.add(listener);

    let disposable = new ListDisposable(() => this.listeners.remove(listener));

    return disposable;
  }

  public _notify() {
    this.listeners.forEach((l) => l());
  }

  /// Utility methods for firing, updating masters & observables, etc.
  private _fire(value: T, args?: Partial<{ added: boolean }>): void {
    this._obs.fire(this._name, this._obs, value, args?.added ?? false);
  }

  private _fieldChanged(old: T[]) {
    if (this._ignoreField) {
      return;
    }
    if (!this._master.d3eChanges.contains(this._field)) {
      this._master.updateD3EChanges(this._field, old);
    }
  }

  private _updateObservable(_old: any, _new: any) {
    if (!this._child && !this._inverse) {
      return;
    }
    let oldValue = _old as ObjectObservable;
    let newValue = _new as ObjectObservable;
    this._obs.updateObservable(this._name, oldValue, newValue);
  }

  private _updateObservableColl(_old: T[], _new: T[]) {
    if (!this._child && !this._inverse) {
      return;
    }
    let oldValue = _old.map((e) => e as ObjectObservable).toList();
    let newValue = _new.map((e) => e as ObjectObservable).toList();
    this._obs.updateObservableColl(this._name, oldValue, newValue);
  }

  private _removeObservable(_old: any) {
    if (!this._child && !this._inverse) {
      return;
    }
    let oldValue = _old as ObjectObservable;
    this._obs.removeObservable(this._name, oldValue);
  }

  private _updateMaster(old: T[]) {
    if (!this._child) {
      return;
    }
    let _old = old.toSet();
    let _new = this.toSet();
    let _removed = _old.difference(_new);
    _removed.forEach((e) => {
      let o = e as DBObject;
      o.clearMaster();
      this._removeObservable(e);
    });
    let _added = _new.difference(_old);
    _added.forEach((e) => {
      let o = e as DBObject;
      o.updateMaster(this._master, this._field);
      this._updateObservable(null, e);
    });
  }

  public add(value: T) {
    let ghost = [...this];
    super.add(value);
    this._updateMaster(ghost);
    this._fire(value, { added: true });
    this._updateObservable(null, value);
    this._fieldChanged(ghost);
    this._notify();
  }

  public addAll(iterable: Iterable<T>) {
    let ghost = [...this];
    iterable.forEach((f) => super.add(f));
    if (this._master) {
      this._updateMaster(ghost);
      this._fieldChanged(ghost);
      this._updateObservableColl(ghost, this);
      this._fire(null);
      this._notify();
    }
  }

  public cast<R>(): R[] {
    return super.cast<R>().toList();
  }

  public clear() {
    super.clear();
    this._notify();
  }

  public expand<E>(f: (element: T) => Iterable<E>): Iterable<E> {
    return super.expand((x) => f(x));
  }

  public fillRange(start: number, end: number, fillValue?: T) {
    let ghost = [...this];
    super.fillRange(start, end, fillValue);
    this._updateMaster(ghost);
    this._fire(fillValue);
    this._updateObservable(null, fillValue);
    this._fieldChanged(ghost);
    this._notify();
  }

  public insert(index: number, element: T) {
    let ghost = [...this];
    super.insert(index, element);
    this._updateMaster(ghost);
    this._fire(element, { added: true });
    this._updateObservable(null, element);
    this._fieldChanged(ghost);
    this._notify();
  }

  public insertAll(index: number, iterable: Iterable<T>) {
    let ghost = [...this];
    super.insertAll(index, iterable);
    this._updateMaster(ghost);
    this._fire(null);
    this._fieldChanged(ghost);
    this._notify();
  }

  public d3eMap<E>(f: (e: T) => E): Iterable<E> {
    return super.map(f);
  }

  public remove(value: any): boolean {
    let ghost = [...this];
    var remove = super.remove(value);
    this._updateMaster(ghost);
    this._fire(value);
    this._removeObservable(value);
    this._fieldChanged(ghost);
    this._notify();
    return remove;
  }

  public removeAt(index: number): T {
    let ghost = [...this];
    var removeAt = super.removeAt(index);
    this._updateMaster(ghost);
    this._fire(removeAt);
    this._removeObservable(removeAt);
    this._fieldChanged(ghost);
    this._notify();
    return removeAt;
  }

  public removeLast(): T {
    let ghost = [...this];
    var remove = super.removeLast();
    this._updateMaster(ghost);
    this._fire(remove);
    this._removeObservable(remove);
    this._fieldChanged(ghost);
    this._notify();
    return remove;
  }

  public removeRange(start: number, end: number) {
    let ghost = [...this];
    super.removeRange(start, end);
    this._updateMaster(ghost);
    this._fire(null);
    this._fieldChanged(ghost);
    this._notify();
  }

  public removeWhere(test: (element: T) => boolean) {
    let ghost = [...this];
    super.removeWhere(test);
    this._updateMaster(ghost);
    this._fire(null);
    this._fieldChanged(ghost);
    this._notify();
  }

  public replaceRange(start: number, end: number, replacements: Iterable<T>) {
    let ghost = [...this];
    super.replaceRange(start, end, replacements);
    this._updateMaster(ghost);
    this._fire(null);
    this._fieldChanged(ghost);
    this._notify();
  }

  public retainWhere(test: (element: T) => boolean) {
    let ghost = [...this];
    super.retainWhere(test);
    this._updateMaster(ghost);
    this._fire(null);
    this._fieldChanged(ghost);
    this._notify();
  }

  public setAll(index: number, iterable: Iterable<T>) {
    let ghost = [...this];
    super.setAll(index, iterable);
    this._updateMaster(ghost);
    this._fire(null);
    this._fieldChanged(ghost);
    this._notify();
  }

  public setRange(
    start: number,
    end: number,
    iterable: Iterable<T>,
    skipCount = 0
  ) {
    let ghost = [...this];
    super.setRange(start, end, iterable, skipCount);
    this._updateMaster(ghost);
    this._fieldChanged(ghost);
    this._fire(null);
    this._notify();
  }

  public shuffle(random?: Random) {
    let ghost = [...this];
    super.shuffle(random);
    this._fieldChanged(ghost);
    this._fire(null);
    this._notify();
  }

  // public sort(compare?: (a: T ,  b: T) => number ) {
  //   super.sort(compare);
  //   this._notify();
  // }

  public sublist(start: number, end?: number): T[] {
    return super.sublist(start, end);
  }

  public take(count: number): Iterable<T> {
    return super.take(count);
  }

  public takeWhile(test: (value: T) => boolean): Iterable<T> {
    return super.takeWhile(test);
  }

  public toList(d3eParams?: Partial<{ growable }>): T[] {
    return super.toList({ growable: d3eParams?.growable ?? true });
  }

  public where(test: (element: T) => boolean): Array<T> {
    return super.where(test);
  }
}
