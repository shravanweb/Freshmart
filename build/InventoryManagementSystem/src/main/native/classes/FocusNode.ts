import { Offset } from "./Offset";
import VoidCallback from "../../classes/core";
import { FocusOnKeyCallback } from "./FocusOnKeyCallback";
import { FocusScopeNode } from "./FocusScopeNode";
import { FocusHighlightMode } from "./FocusHighlightMode";
import { Rect } from "./Rect";
import Size from "./Size";
import { RawKeyEvent } from "..";

export class FocusNode {
  ancestors: Array<FocusNode> = [];
  canRequestFocus: boolean = false;
  children: Array<FocusNode> = [];
  debugLabel: string = "";
  enclosingScope?: FocusScopeNode;
  _hasFocus: boolean = false;
  _hasPrimaryFocus: boolean = false;
  highlightMode?: FocusHighlightMode;
  nearestScope?: FocusScopeNode;
  offset?: Offset;
  parent?: FocusNode;
  rect?: Rect;
  size?: Size;
  skipTraversal: boolean = false;
  traversalChildren: Array<FocusNode> = [];
  traversalDescendants: Array<FocusNode> = [];
  onKey?: FocusOnKeyCallback;

  private element?: HTMLElement;
  private listeners: VoidCallback[] = [];

  constructor(param?: Partial<FocusNode>) {
    Object.assign(this, param);
  }

  connect(element?: HTMLElement) {
    this.element = element;
  }

  handleKeyEvent(event: RawKeyEvent): boolean {
    // "Handles" a key event. Sending this event is the responsibility of RawKeyboardListener.
    // If this method is called, it is assumed that the caller has checked that this FocusNode has focus.
    return this.onKey?.(this, event) || false;
  }

  requestFocus(): void {
    if (this.element) {
      // this.element.focus();
      // TODO: What about focus for the ancestors?
      // TODO: Do we set like this or do we call the setters? Calling the setters will call fire twice.
      this._hasFocus = true;
      this._hasPrimaryFocus = true;
      this.fire();
    }
  }

  removeFocus(): void {
    if (this.element) {
      this._hasFocus = false;
      this._hasPrimaryFocus = false;
      this.fire();
    }
  }

  unfocus(): void {
    if (this.element) {
      this._hasFocus = false;
      this.fire();
    }
  }

  set hasFocus(has: boolean) {
    if (has === this._hasFocus) {
      return;
    }
    this._hasFocus = has;
    this.fire();
  }

  get hasFocus() {
    return this._hasFocus;
  }

  set hasPrimaryFocus(has: boolean) {
    if (has === this._hasPrimaryFocus) {
      return;
    }
    this._hasPrimaryFocus = has;
    this.fire();
  }

  get hasPrimaryFocus() {
    return this._hasPrimaryFocus;
  }

  addListener(listener: VoidCallback): void {
    this.listeners.push(listener);
  }

  removeListener(listener: VoidCallback): void {
    this.listeners.remove(listener);
  }

  private fire() {
    this.listeners.forEach((one) => one());
  }
}
