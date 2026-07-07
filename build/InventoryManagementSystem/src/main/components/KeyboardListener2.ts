import ObservableComponent from "./ObservableComponent";
import * as ui from "../native/index";
import React, { ReactNode } from "react";

interface KeyboardListener2Props {
  focusNode?: ui.FocusNode;

  autofocus?: boolean;

  includeSemantics?: boolean;

  onKey?: (event: ui.RawKeyEvent) => void;

  child: ReactNode;
}

class _KeyboardListener2 extends ObservableComponent<KeyboardListener2Props> {
  initState() {
    super.initState();
    this.props.focusNode.addListener(this.handleFocusChanged);
  }

  componentDidUpdate(prevProps: KeyboardListener2Props) {
    super.componentDidUpdate(prevProps);
    if (this.props.focusNode != prevProps.focusNode) {
      prevProps.focusNode.removeListener(this.handleFocusChanged);
      this.props.focusNode.addListener(this.handleFocusChanged);
    }
  }

  dispose() {
    this.props.focusNode.removeListener(this.handleFocusChanged);
    this.detachKeyboardIfAttached();
    super.dispose();
  }

  handleFocusChanged() {
    if (this.props.focusNode.hasFocus) this.attachKeyboardIfDetached();
    else this.detachKeyboardIfAttached();
  }

  listening: boolean = false;

  attachKeyboardIfDetached() {
    if (this.listening) return;
    ui.RawKeyboard.instance.addListener(this.handleRawKeyEvent);
    this.listening = true;
  }

  detachKeyboardIfAttached() {
    if (!this.listening) return;
    ui.RawKeyboard.instance.removeListener(this.handleRawKeyEvent);
    this.listening = false;
  }

  handleRawKeyEvent(event: ui.RawKeyEvent) {
    this.props.onKey(event);
  }

  render(): ReactNode {
    return this.props.child;
  }
}

export default function KeyboardListener2(props: KeyboardListener2Props) {
  return React.createElement(_KeyboardListener2, props);
}
