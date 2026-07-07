import React from "react";

import { CodeEditorController } from "../classes/CodeEditorController";
import BaseUIProps from "./BaseUIProps";
import IFrame from "./IFrame";
interface CodeEditorProps extends BaseUIProps {
  controller?: CodeEditorController;
}
class _CodeEditor extends React.Component<CodeEditorProps, {}> {
  static count = 0;
  currentId: string;
  style = {};
  url = window.location.href.replaceFirst(
    new RegExp("#/"),
    "editor/index.html"
  ); // TODO: Will this work?
  ref?: HTMLIFrameElement;

  constructor(props: CodeEditorProps) {
    super(props);
    _CodeEditor.count++;
    this.currentId = _CodeEditor.count.toString();
    Object.assign(this.style, {
      border: "0px none",
      width: "100%",
      height: "100%",
    });
    this.onEditorEvent = this.onEditorEvent.bind(this);
    this.onMessageFromEditor = this.onMessageFromEditor.bind(this);
    this.setRef = this.setRef.bind(this);
    this.props.controller?.connect(this.ref);
  }

  componentDidMount() {
    window.document.addEventListener("EditorEvent", this.onEditorEvent);
  }

  onEditorEvent(e) {
    // What is the TS/React equivalent of this code?
    let detail = (e as any).detail;
    let type: string = detail.customType;
    let value: string = detail.value;
    let editorID: string = detail.editorID;
    this.onMessageFromEditor(type, value, editorID);
  }

  onMessageFromEditor(type: string, value: string, editorID: string): void {
    if (editorID == null && type === "init") {
      this.props.controller?._sendMessage("initAck", this.currentId);
      if (this.props.controller) {
        this.props.controller.code = this.props.controller?._lastSetCode;
      }
    } else if (editorID === this.currentId) {
      switch (type) {
        case "getValue":
          if (this.props.controller) {
            this.props.controller.lastValue = value;
          }
      }
    }
  }

  setRef(ref: HTMLIFrameElement) {
    if (!ref) {
      return;
    }
    this.ref = ref;
  }

  // TODO: onBoundsChange

  render() {
    return React.createElement(
      "ui-code-editor",
      {
        id: "code-editor-" + this.currentId,
      },
      IFrame({
        id: "code-editor-frame-" + this.currentId,
        style: this.style,
        src: this.url,
        ref: this.setRef,
      })
    );
  }
}

export default function CodeEditor(props: CodeEditorProps) {
  return React.createElement(_CodeEditor, props);
}
