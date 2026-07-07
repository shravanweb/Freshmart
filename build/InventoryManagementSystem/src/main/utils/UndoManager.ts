import CollectionUtils from "./CollectionUtils";
import DBObject from "./DBObject";
import UndoListener from "./UndoListener";
import ReferenceCatch from "./ReferenceCatch";
import D3EObjectChanges from "./D3EObjectChanges";
import MapExt from "../classes/MapExt";
import { D3ETemplate } from "../rocket/D3ETemplate";
import {
  D3EFieldType,
  D3ERefType,
  D3ETemplateField,
  D3ETemplateType,
} from "../rocket/D3ETemplateTypes";
import DateTime from "../core/DateTime";
import GlobalFunctions from "./GlobalFunctions";

enum UndoAction {
  None,
  Create,
  Update,
  Delete,
}

class UndoContext {
  public label: String;
  public listener: UndoListener;
  _updated: Map<number, Map<number, UpdatedObject>> = new Map();
  public action: UndoAction;
  public actionObj: DBObject;
  public changes: Map<DBObject, Map<number, any>> = new Map();
  public prev: UndoContext;
}

class UpdatedObject {
  public values: Map<number, UpdatedFieldValue> = new Map();
  constructor(public id: number, public type: number) {}
}

class UpdatedFieldValue {
  constructor(
    public field: number,
    public oldValue: any,
    public newValue: any
  ) {}
}

export default class UndoManager2 {
  private static _inProcess = false;
  private static _queue: UndoContext[] = [];
  private static _redo: UndoContext[] = [];
  private static _current: UndoContext;
  private static _started = false;
  public static offsetSize = 0;

  static load(): void {}

  static start(): void {
    UndoManager2.clear();
    UndoManager2._started = true;
    UndoManager2.save();
    UndoManager2.offsetSize = UndoManager2._queue.length;
  }

  static clear(): void {
    UndoManager2._queue.clear();
    UndoManager2._current = new UndoContext();
  }

  static end(): void {
    UndoManager2._queue.clear();
    UndoManager2._redo.clear();
    UndoManager2._started = false;
  }

  static readStart(): void {
    UndoManager2._inProcess = true;
  }

  static readDone(): void {
    UndoManager2._inProcess = false;
  }

  static getHistory(): String[] {
    return UndoManager2._queue.map((e) => e.label).toList();
  }

  static undo(): void {
    if (
      UndoManager2._queue.isEmpty ||
      UndoManager2._queue.length == UndoManager2.offsetSize
    ) {
      console.log("Nothing to Undo");
      return;
    }
    console.log("Doing Undo");
    let last: UndoContext = UndoManager2._queue.removeLast();
    UndoManager2._applyRevertChanges(last);
    UndoManager2._redo.push(last);
    if (last.listener != null) {
      last.listener.onUndo();
    }
  }

  static redo(): void {
    if (UndoManager2._redo.isEmpty) {
      console.log("Nothing to redo");
      return;
    }
    console.log("Doing redo");
    let last: UndoContext = UndoManager2._redo.removeLast();
    UndoManager2._applyDirectChanges(last);
    UndoManager2._queue.push(last);
    if (last.listener != null) {
      last.listener.onRedo();
    }
  }

  static undoUpTo(label: String): void {
    do {
      let last: UndoContext = UndoManager2._queue.last;
      UndoManager2.undo();
      if (last.label == label) {
        break;
      }
    } while (UndoManager2._queue.isNotEmpty);
  }

  static redoUpTo(label: String): void {
    do {
      let last: UndoContext = UndoManager2._redo.last;
      UndoManager2.undo();
      if (last.label == label) {
        break;
      }
    } while (UndoManager2._redo.isNotEmpty);
  }

  static save(label?: String, listener?: UndoListener): void {
    if (label == null) {
      label = DateTime.now().toString();
    }
    console.log("Saving Changes in Undo Manager");
    UndoManager2._current.action = UndoAction.None;
    UndoManager2._current.label = label;
    UndoManager2._current.listener = listener;
    UndoManager2._saveAllChanges();
    let ctx: UndoContext = UndoManager2._current;
    if (ctx._updated.isEmpty && UndoManager2._current.changes.isEmpty) {
      ctx = UndoManager2._current.prev;
    }
    if (ctx != null) {
      UndoManager2._queue.push(ctx);
      UndoManager2._redo.clear();
    }
    UndoManager2._current = new UndoContext();
  }

  static objectSave(obj: DBObject, create: boolean): void {
    if (
      !UndoManager2._started ||
      UndoManager2._inProcess ||
      obj.isTransientModel
    ) {
      return;
    }
    if (!create) {
      if (!obj.d3eChanges.hasChanges) {
        return;
      }
    }
    UndoManager2._current.action = create
      ? UndoAction.Create
      : UndoAction.Update;
    UndoManager2._current.actionObj = obj;
    UndoManager2._collectObjectChanges(obj);
    UndoManager2._saveAllChanges();
    let next: UndoContext = new UndoContext();
    next.prev = UndoManager2._current;
    UndoManager2._current = next;
  }

  static _collectObjectChanges(obj: DBObject): void {
    if (obj == null) {
      return;
    }
    UndoManager2._current.changes.set(obj, Map.from(obj.d3eChanges.values));
    let type: number = D3ETemplate.typeInt(obj.d3eType);
    do {
      let tt: D3ETemplateType = D3ETemplate.types[type];
      let fi: number = tt.parentFields;
      for (let tf of tt.fields) {
        if (tf.child && tf.fieldType == D3EFieldType.Ref) {
          let val: any = obj.get(fi);
          if (val instanceof DBObject) {
            UndoManager2._collectObjectChanges(val);
          } else if (Array.isArray(val)) {
            val.forEach((v) => {
              UndoManager2._collectObjectChanges(v);
            });
          }
        }
        fi++;
      }
      type = tt.parent;
    } while (type != -1);
  }

  static objectDelete(obj: DBObject): void {
    if (
      !UndoManager2._started ||
      UndoManager2._inProcess ||
      obj.isTransientModel
    ) {
      return;
    }
    UndoManager2._current.action = UndoAction.Delete;
    UndoManager2._current.actionObj = obj;
    UndoManager2._saveAllChanges();
    let next: UndoContext = new UndoContext();
    next.prev = UndoManager2._current;
    UndoManager2._current = next;
  }

  static fieldChanged(on: DBObject, field: number, oldValue: any): void {
    if (!UndoManager2._started || UndoManager2._inProcess) {
      return;
    }
  }

  static _saveAllChanges(): void {
    let rc: ReferenceCatch = ReferenceCatch.get();
    rc.all.forEach((all, type) => {
      let tt: D3ETemplateType = D3ETemplate.types[type];
      if (tt == null) {
        return;
      }
      if (tt.refType == D3ERefType.Model) {
        all.forEach((value, id) => {
          if (value.isTransientModel) {
            return;
          }
          let changes: D3EObjectChanges = value.d3eChanges;
          if (changes != null) {
            changes.values.forEach((oldValue, field) => {
              UndoManager2._fieldChanged(type, value, field, oldValue);
            });
          }
        });
      }
    });
  }

  static _fieldChanged(
    type: number,
    on: DBObject,
    field: number,
    oldValue: any
  ): void {
    let changes: Map<number, UpdatedObject> =
      UndoManager2._current._updated.get(type);
    if (changes == null) {
      changes = new Map();
      UndoManager2._current._updated.set(type, changes);
    }
    let ch: UpdatedObject = changes.get(on.id);
    if (ch == null) {
      ch = new UpdatedObject(on.id, type);
      changes.set(on.id, ch);
    }
    let newValue = on.get(field);
    if (newValue instanceof Array) {
      newValue = Array.from(newValue);
    }
    let exist: UpdatedFieldValue = ch.values.get(field);
    if (exist != null) {
      exist.newValue = newValue;
      if (exist.oldValue == exist.newValue) {
        ch.values.remove(field);
      }
    } else {
      let managerOldValue = UndoManager2._findOldValue(on, field, oldValue);
      if (newValue instanceof Array) {
        if (
          CollectionUtils.isNotEquals(newValue, managerOldValue as Array<any>)
        ) {
          ch.values.set(
            field,
            new UpdatedFieldValue(field, managerOldValue, newValue)
          );
        }
      } else {
        if (managerOldValue != newValue) {
          ch.values.set(
            field,
            new UpdatedFieldValue(field, managerOldValue, newValue)
          );
        }
      }
    }
    if (ch.values.isEmpty) {
      changes.remove(on.id);
    }
    if (changes.isEmpty) {
      UndoManager2._current._updated.remove(type);
    }
  }

  static _findOldValue(on: DBObject, field: number, oldValue: any): any {
    let type: number = D3ETemplate.typeInt(on.d3eType);
    return UndoManager2._queue
      .map((ctx) =>
        UndoManager2._findOldValueFromContext(ctx, type, on.id, field)
      )
      .where((fch) => fch != null)
      .map((fch) => {
        if (Array.isArray(fch.newValue)) {
          return Array.from(fch.newValue);
        } else {
          return fch.newValue;
        }
      })
      .firstWhere((element) => true, { orElse: () => oldValue });
  }

  static _findOldValueFromContext(
    ctx: UndoContext,
    type: number,
    id: number,
    field: number
  ): UpdatedFieldValue {
    do {
      let changes: Map<number, UpdatedObject> = ctx._updated.get(type);
      ctx = ctx.prev;
      if (changes == null) {
        continue;
      }
      let ch: UpdatedObject = changes.get(id);
      if (ch == null) {
        continue;
      }
      let fch: UpdatedFieldValue = ch.values.get(field);
      if (fch == null) {
        continue;
      }
      return fch;
    } while (ctx != null);
    return null;
  }

  static _applyRevertChanges(ctx: UndoContext): void {
    UndoManager2._inProcess = true;
    switch (ctx.action) {
      case UndoAction.None:
        //Revert the changes
        UndoManager2._applyContextChanges(ctx, true);
        break;
      case UndoAction.Create:
        //any was created, needs to delete that object.
        UndoManager2._applyContextChanges(ctx, true);
        ctx.actionObj.delete();
        break;
      case UndoAction.Update:
        //any was updated, needs to update again with old changes.
        UndoManager2._applyContextChanges(ctx, true);
        UndoManager2._resetIds(ctx, ctx.actionObj, false, true);
        ctx.actionObj.save();
        break;
      case UndoAction.Delete:
        //any was deletd. needs to create a new any;
        UndoManager2._applyContextChanges(ctx, true);
        UndoManager2._resetIds(ctx, ctx.actionObj, true, true);
        ctx.actionObj.save();
        break;
    }
    UndoManager2._inProcess = false;
    if (ctx.prev != null) {
      UndoManager2._applyRevertChanges(ctx.prev);
    }
  }

  static _applyDirectChanges(ctx: UndoContext): void {
    if (ctx.prev != null) {
      UndoManager2._applyDirectChanges(ctx.prev);
    }
    UndoManager2._inProcess = true;
    switch (ctx.action) {
      case UndoAction.None:
        UndoManager2._applyContextChanges(ctx, false);
        break;
      case UndoAction.Create:
        UndoManager2._applyContextChanges(ctx, false);
        UndoManager2._resetIds(ctx, ctx.actionObj, true, false);
        ctx.actionObj.save();
        break;
      case UndoAction.Update:
        UndoManager2._applyContextChanges(ctx, false);
        UndoManager2._resetIds(ctx, ctx.actionObj, false, false);
        ctx.actionObj.save();
        break;
      case UndoAction.Delete:
        UndoManager2._applyContextChanges(ctx, false);
        ctx.actionObj.delete();
        break;
    }
    UndoManager2._inProcess = false;
  }

  static _resetIds(
    ctx: UndoContext,
    obj: DBObject,
    create: boolean,
    old: boolean
  ): void {
    if (obj == null) {
      return;
    }
    let type: number = D3ETemplate.typeInt(obj.d3eType);
    do {
      let tt: D3ETemplateType = D3ETemplate.types[type];
      let fi: number = tt.parentFields;
      for (let tf of tt.fields) {
        if (tf.child && tf.fieldType == D3EFieldType.Ref) {
          let val = obj.get(fi);
          if (create) {
            if (val instanceof DBObject) {
              UndoManager2._resetIds(ctx, val, true, old);
            } else if (Array.isArray(val)) {
              (val as Array<any>).forEach((v) => {
                UndoManager2._resetIds(ctx, v, true, old);
              });
            }
          } else {
            let oldVal = UndoManager2._getOldValue(ctx, type, obj, fi, old);
            if (val instanceof DBObject) {
              if (oldVal == val) {
                UndoManager2._resetIds(ctx, val, false, old);
              } else {
                UndoManager2._resetIds(ctx, val, true, old);
              }
            } else if (Array.isArray(val)) {
              if (Array.isArray(oldVal)) {
                val.forEach((v) => {
                  if (oldVal.contains(v)) {
                    UndoManager2._resetIds(ctx, v, false, old);
                  } else {
                    val.forEach((v: any) => {
                      UndoManager2._resetIds(ctx, v, true, old);
                    });
                  }
                });
              } else {
                val.forEach((v) => {
                  UndoManager2._resetIds(ctx, v, true, old);
                });
              }
            }
          }
        }
        fi++;
      }
      type = tt.parent;
    } while (type != -1);
    if (create) {
      obj.id = 0;
      let chs: Map<number, any> = ctx.changes.get(obj);
      if (chs != null) {
        chs.forEach((value, key) => {
          obj.d3eChanges.add(key, value);
        });
      }
    }
  }

  static _getOldValue(
    ctx: UndoContext,
    type: number,
    obj: DBObject,
    field: number,
    old: boolean
  ): any {
    let fch: UpdatedFieldValue = UndoManager2._findOldValueFromContext(
      ctx,
      type,
      obj.id,
      field
    );
    if (fch == null) {
      return obj.d3eChanges.getValue(field);
    }
    if (old) {
      if (Array.isArray(fch.oldValue)) {
        return Array.from(fch.oldValue);
      } else {
        return fch.oldValue;
      }
    } else {
      if (Array.isArray(fch.newValue)) {
        return Array.from(fch.newValue);
      } else {
        return fch.newValue;
      }
    }
  }

  static _findObject(type: number, id: number): DBObject {
    return ReferenceCatch.get().findObject(type, id);
  }

  static _applyContextChanges(ctx: UndoContext, old: boolean): void {
    MapExt.values(ctx._updated).forEach((e1) => {
      MapExt.values(e1).forEach((e) => {
        UndoManager2._applyUpdatedObjectChanges(e, old);
      });
    });
  }

  static _applyUpdatedObjectChanges(
    changes: UpdatedObject,
    old: boolean
  ): DBObject {
    let obj: DBObject = this._findObject(changes.type, changes.id);
    if (obj == null) {
      return obj;
    }
    MapExt.values(changes.values).forEach((f) => {
      obj.set(f.field, old ? f.oldValue : f.newValue);
    });
    return obj;
  }
}

GlobalFunctions.objectSave = UndoManager2.objectSave;
GlobalFunctions.objectDelete = UndoManager2.objectDelete;
GlobalFunctions.fieldChanged = UndoManager2.fieldChanged;
