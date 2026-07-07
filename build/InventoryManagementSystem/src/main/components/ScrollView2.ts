import React, { ReactNode } from "react";

import ObservableComponent from "./ObservableComponent";

import * as ui from "../native/index";

import BaseUIProps, { processBaseUIProps } from "../native/ui/BaseUIProps";

interface ScrollView2Props extends BaseUIProps {
  child: ReactNode;

  scrollDirection: ui.Axis;

  controller?: ui.ScrollController;
}

/* =========================================================
   SCROLL DATA
========================================================= */

export class ScrollData extends ui.ChangeNotifier {
  dx: number = 0;

  dy: number = 0;

  private _parent: ScrollData | null = null;

  set parent(p: ScrollData | null) {
    if (this._parent === p) return;

    if (this._parent) {
      this._parent.removeListener(this.onNotify);
    }

    this._parent = p;

    if (this._parent) {
      this._parent.addListener(this.onNotify);
    }
  }

  get parent(): ScrollData | null {
    return this._parent;
  }

  private onNotify = () => {
    this.notifyListeners();
  };

  update(x: number, y: number) {
    this.dx = x ?? 0;

    this.dy = y ?? 0;

    this.notifyListeners();
  }

  dispose() {
    if (this._parent) {
      this._parent.removeListener(this.onNotify);

      this._parent = null;
    }

    super.dispose();
  }
}

/* =========================================================
   SCROLL VIEW
========================================================= */

class _ScrollView2 extends ObservableComponent<ScrollView2Props> {
  data: ScrollData = new ScrollData();

  scrollRef = React.createRef<HTMLElement>();

  controller: ui.ScrollController;

  constructor(props: ScrollView2Props) {
    super(props);

    this.controller = props.controller ?? new ui.ScrollController();
  }

  /* =====================================================
     MOUNT
  ===================================================== */

  componentDidMount() {
    const el = this.scrollRef.current;

    if (!el) return;

    this.controller.setScrollableElement(el);

    // Initial Offset
    if (this.controller.initialScrollOffset != null) {
      if (this.props.scrollDirection === ui.Axis.horizontal) {
        el.scrollLeft = this.controller.initialScrollOffset;
      } else {
        el.scrollTop = this.controller.initialScrollOffset;
      }
    }
  }

  /* =====================================================
     UNMOUNT
  ===================================================== */

  componentWillUnmount() {
    this.data.dispose();

    this.controller.dispose();
  }

  /* =====================================================
     SCROLL
  ===================================================== */

  handleScroll = (event: React.UIEvent<HTMLElement>) => {
    const target = event.currentTarget;

    this.controller.setScrollableElement(target);

    this.data.update(target.scrollLeft, target.scrollTop);

    if (this.props.scrollDirection === ui.Axis.horizontal) {
      this.controller.setScrollPosition(target.scrollLeft);
    } else {
      this.controller.setScrollPosition(target.scrollTop);
    }
  };

  /* =====================================================
     RENDER
  ===================================================== */

  render(): ReactNode {
    const style: React.CSSProperties = {};

    /* ===================================================
       OVERFLOW
    =================================================== */

    switch (this.props.scrollDirection) {
      case ui.Axis.horizontal:
        style.overflowX = "auto";
        break;

      case ui.Axis.vertical:
        style.overflowY = "auto";
        break;

      default:
        style.overflow = "auto";
    }

    /* ===================================================
       WEBKIT SMOOTH SCROLL
    =================================================== */

    style.WebkitOverflowScrolling = "touch";

    return React.createElement(
      "ui-scroll-view",
      {
        class:
          "ScrollView2" +
          (this.props.className ? " " + this.props.className.trim() : ""),

        style,

        ref: this.scrollRef,

        onScroll: this.handleScroll,

        ...processBaseUIProps(this.props),
      },

      this.props.child
    );
  }
}

/* =========================================================
   EXPORT
========================================================= */

export default function ScrollView2(props: ScrollView2Props): ReactNode {
  return React.createElement(_ScrollView2, props);
}
