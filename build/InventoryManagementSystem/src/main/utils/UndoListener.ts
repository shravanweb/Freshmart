import { Runnable } from "../classes/core";

export default class UndoListener {
  onUndo: Runnable;
  onRedo: Runnable;
  constructor(params?: Partial<{ onUndo: Runnable; onRedo: Runnable }>) {
    this.onUndo = params?.onUndo;
    this.onRedo = params?.onRedo;
  }
}
