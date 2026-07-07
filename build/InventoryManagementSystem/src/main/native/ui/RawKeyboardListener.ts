import React, { ReactNode } from "react";
import { FocusNode } from "../classes/FocusNode";
import { RawKeyEvent } from "../classes/RawKeyEvent";
import BaseUIProps from "./BaseUIProps";

interface RawKeyboardListenerProps extends BaseUIProps {
  focusNode?: FocusNode;
  autofocus?: boolean;
  child?: ReactNode;
}
class _RawKeyboardListener extends React.Component<
  RawKeyboardListenerProps,
  {}
> {
  static defaultProps = {
    autofocus: false,
  };

  constructor(props: RawKeyboardListenerProps) {
    super(props);
    this.handleFocus = this.handleFocus.bind(this);
    this.handleKeyPress = this.handleKeyPress.bind(this);
    if (this.props.autofocus) {
      this.handleFocus();
    }
  }

  handleKeyPress(event) {
    if (this.props.onKey) {
      let keyEvent: RawKeyEvent = new RawKeyEvent({
        character: event.key,
        isAltPressed: event.altKey,
        isControlPressed: event.ctrlKey,
        isMetaPressed: event.metaKey,
        isShiftPressed: event.shiftKey,
        logicalKey: event.location,
      });
      this.props.onKey(keyEvent);
    }
  }

  handleFocus(blur: boolean = false) {
    if (blur) {
      this.props.focusNode?.removeFocus();
    } else {
      this.props.focusNode?.requestFocus();
    }
  }

  render() {
    return React.createElement(
      "ui-key-listener",
      {
        class: this.props.className ? " " + this.props.className : "",
        onKeyPress: this.handleKeyPress,
        onFocus: (e) => this.handleFocus(),
        onBlur: (e) => this.handleFocus(true),
      },
      this.props.child
    );
  }
}

export default function RawKeyboardListener(props: RawKeyboardListenerProps) {
  return React.createElement(_RawKeyboardListener, {
    ..._RawKeyboardListener.defaultProps,
    ...props,
  });
}
