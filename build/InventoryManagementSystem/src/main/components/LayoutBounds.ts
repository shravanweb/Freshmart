import React, { ReactNode } from "react";
import * as ui from "../native";
import { BuildContext } from "../classes/BuildContext";
import ObservableComponent from "./ObservableComponent";
import { StyleThemeData } from "./ThemeWrapper";

export class LayoutBoundsState {
  box: ui.RenderBox;
}
interface LayoutBoundsProps {
  child: ReactNode;
}

export class _LayoutBounds extends ObservableComponent<LayoutBoundsProps> {
  child = this.props.child;
  _ref: HTMLInputElement;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  handleRef = (e: HTMLInputElement) => {
    this._ref = e;
  };

  ref = () => {
    return this._ref;
  };

  render(): ReactNode {
    return React.createElement(
      BuildContext.Provider,
      {
        value: {
          ...this.context,
          layoutBoundsProvider: this.ref,
          theme: StyleThemeData.current,
        },
      },
      React.createElement(
        "ui-layout-bounds",
        {
          ref: this.handleRef,
        },
        this.child
      )
    );
  }
}

export default function LayoutBounds(props: LayoutBoundsProps) {
  return React.createElement(_LayoutBounds, props);
}
