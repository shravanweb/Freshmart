import { InputField } from "..";
import { TextAffinity } from "./TextAffinity";
import VoidCallback from "../../classes/core";

export class TextEditingController {
  _selection: TextSelection;

  _text: string = "";

  private listeners: VoidCallback[];

  // TODO: Only InputField?
  // @ts-ignore
  private component: InputField;

  constructor(param?: Partial<TextEditingController>) {
    Object.assign(this, param);
    this.listeners = [];
  }

  addListener(listener: VoidCallback): void {
    this.listeners.add(listener);
  }

  // @ts-ignore
  connect(component: InputField) {
    this.component = component;
  }

  // Fire listeners when selection/text updated
  fire() {
    this.listeners.forEach((one) => one());
  }

  get text() {
    return this.component ? this.component.ref.value : this._text;
  }

  set text(text: string) {
    this._text = text;
    this.fire();
    if (this.component) {
      this.component.handleTextChange(text);
    }
  }

  get selection() {
    return this._selection;
  }

  // Ideally should be called from the component.
  set selection(sel: TextSelection) {
    this._selection = sel;
    this.fire();
  }

  createSelection(start: number, end: number = 0) {
    this.selection = new TextSelection({
      baseOffset: start,
      extentOffset: end,
    });
  }
}

export class TextSelection {
  baseOffset: number;

  extentOffset: number;

  affinity: TextAffinity;

  isDirectional: boolean = false;

  base: TextPosition;

  extent: TextPosition;

  constructor(params: {
    baseOffset: number;
    extentOffset: number;
    affinity?: TextAffinity;
    isDirectional?: boolean;
  }) {
    if (!params.affinity) {
      params.affinity = TextAffinity.downstream;
    }
    Object.assign(this, params);
    this.base = new TextPosition({
      offset: this.baseOffset,
    });
    this.extent = new TextPosition({
      offset: this.extentOffset,
    });
  }

  static collapsed(params: {
    offset: number;
    affinity?: TextAffinity;
  }): TextSelection {
    return new TextSelection({
      baseOffset: params.offset,
      extentOffset: params.offset,
      affinity: params.affinity,
    });
  }

  static fromPosition(position: TextPosition): TextSelection {
    let val = new TextSelection({
      baseOffset: position.offset,
      extentOffset: 0,
    });
    val.base = position;
    return val;
  }
}

export class TextPosition {
  offset: number;

  affinity: TextAffinity;

  constructor(params: Partial<TextPosition>) {
    if (!params.affinity) {
      params.affinity = TextAffinity.upstream;
    }
    Object.assign(this, params);
  }
}
