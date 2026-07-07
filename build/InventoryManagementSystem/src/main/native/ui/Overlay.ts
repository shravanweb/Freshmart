import { render } from "@testing-library/react";
import React, { ReactNode } from "react";
import PageNavigator from "../../classes/PageNavigator";
import { Clip } from "../classes/Clip";
import { OverlayEntry } from "../classes/OverlayEntry";
import BaseUIProps from "./BaseUIProps";
import TickerMode from "./TickerMode";

type OnEntryNodeChange = (node: ReactNode) => void;

class Entry {
  index: number;
  listener: OnEntryNodeChange;
  added: boolean;
}

export class OverlayController {
  entries: Entry[] = [];

  constructor(count: number) {
    this.entries = new Array(count);
  }

  insert(entry: OverlayEntry) {
    // Check from the end
    var e = this.entries.lastWhere((e) => e.added);
    var index = e ? this.entries.indexOf(e) + 1 : 0;
    e = this.entries.get(index);
    e.added = true;
    entry.index = e.index;
    e.listener(entry.builder(""));
  }

  remove(entry: OverlayEntry) {
    let e = this.entries[entry.index];
    e.added = false;
    e.listener("");
  }

  removeLast() {
    for (let i = this.entries.length - 1; i >= 0; i--) {
      let e = this.entries.get(i);
      if (e.added) {
        e.added = false;
        e.listener("");
        return true;
      }
    }
    return false;
  }

  addListener = (index: number, listener: OnEntryNodeChange) => {
    let e = new Entry();
    e.listener = listener;
    e.added = false;
    e.index = index;
    this.entries[index] = e;
  };

  newEntry(index: number): ReactNode {
    return OverlayEntryWidget({
      key: index.toString(),
      index: index,
      defaultChild: "",
      listener: this.addListener,
    });
  }
}

interface OverlayProps extends BaseUIProps {
  count: number;
}

class _Overlay extends React.Component<OverlayProps, {}> {
  clipBehavior: Clip;
  controller: OverlayController;

  constructor(props: OverlayProps) {
    super(props);
    this.controller = new OverlayController(this.props.count);
    PageNavigator.defaultOverlay = this.controller;
  }

  render(): React.ReactNode {
    let children: ReactNode[] = [];
    for (let i = 0; i < this.props.count; i++) {
      children.push(this.controller.newEntry(i));
    }
    return React.createElement(
      "ui-overlay",
      {
        class: "overlay",
      },
      children
    );
  }
}

interface OverlayEntryProps extends BaseUIProps {
  key?: string;
  index: number;
  defaultChild: ReactNode;
  listener: (index: number, listener: OnEntryNodeChange) => void;
}

class _OverlayEntryWidget extends React.Component<OverlayEntryProps> {
  child: ReactNode;

  constructor(props: OverlayEntryProps) {
    super(props);
    this.child = props.defaultChild;
    props.listener(props.index, (node) => {
      this.child = node;
      this.setState({});
    });
  }

  render(): React.ReactNode {
    return React.createElement(
      "ui-overlay-entry",
      {
        idnex: this.props.index,
        class: this.child ? "show" : null,
      },
      this.child
    );
  }
}

function OverlayEntryWidget(props: OverlayEntryProps): ReactNode {
  return React.createElement(_OverlayEntryWidget, props);
}

export default function Overlay(props: OverlayProps) {
  return React.createElement(_Overlay, props);
}
