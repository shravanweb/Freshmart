export class CodeEditorController {
  private frame?: HTMLIFrameElement;
  lastValue: any;
  _lastSetCode: string;

  connect(frame: HTMLIFrameElement | undefined) {
    this.frame = frame;
  }

  get code(): string {
    this._sendMessage("getValue", null);
    return this.lastValue as string;
  }

  set code(code: string) {
    this._lastSetCode = code;
    this._sendMessage("setValue", code);
  }

  setPosition(lineNumber: number, column: number) {
    this._sendMessage("setPosition", {
      "lineNumber": lineNumber,
      "column": column,
    });
  }

  setSelection(
    startLineNumber: number,
    startColumn: number,
    endLineNumber: number,
    endColumn: number
  ) {
    this._sendMessage("setSelection", {
      "startLineNumber": startLineNumber,
      "startColumn": startColumn,
      "endLineNumber": endLineNumber,
      "endColumn": endColumn,
    });
  }

  _sendMessage(type: string, value: any) {
    this.dispatchEvent(
      new CustomEvent("EditorEvent", {}, { "customType": type, "value": value })
    );
  }

  private dispatchEvent(event: Event) {
    if (!this.frame) {
      return;
    }
    this.frame.dispatchEvent(event);
  }
}

type StringKeyDict = {
  [key: string]: any;
};

class CustomEvent extends Event {
  constructor(type: string, init?: EventInit, extras?: Partial<StringKeyDict>) {
    super(type, init);
    Object.assign(this, extras);
  }
}
