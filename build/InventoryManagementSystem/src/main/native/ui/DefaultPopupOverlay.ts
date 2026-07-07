import React, { ReactNode } from "react";
import * as ui from "..";

interface DefaultPopupOverlayProps {
  child: ReactNode;
}

class _DefaultPopupOverlay extends React.Component<DefaultPopupOverlayProps> {
  render(): React.ReactNode {
    return React.createElement(
      "ui-overlay-root",
      {},
      React.createElement(React.Fragment, { key: "app-child" }, this.props.child),
      ui.Overlay({
        count: 10,
        key: "overlay",
      })
    );
  }
}

export default function DefaultPopupOverlay(props: DefaultPopupOverlayProps) {
  return React.createElement(_DefaultPopupOverlay, props);
}
