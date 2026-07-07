import React, { ReactNode } from "react";
import { Axis } from "../classes/Axis";
import { DragStartBehavior } from "../classes/DragStartBehavior";
import { EdgeInsets } from "../classes/EdgeInsets";
import { ScrollController } from "../classes/ScrollController";
import { ScrollPhysics } from "../classes/ScrollPhysics";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

interface ListViewProps extends BaseUIProps {
  // TODO
  scrollDirection?: Axis;
  reverse?: boolean; // default false
  primary?: boolean;
  physics?: ScrollPhysics;
  shrinkWrap?: boolean; // default false
  padding?: EdgeInsets;
  itemExtent?: number;
  addAutomaticKeepAlives?: boolean; // default true
  addRepaintBoundaries?: boolean; // default true
  addSemanticIndexes?: boolean; // default true
  cacheExtent?: number;
  semanticChildCount?: number;
  dragStartBehavior?: DragStartBehavior;
  itemCount?: number;
  children?: ReactNode[];
  itemBuilder?: (context: any, index: number) => ReactNode;
  controller?: ScrollController;
}

class _ListView extends React.Component<ListViewProps, {}> {
  static defaultProps = {
    reverse: false,
    shrinkWrap: false,
    addAutomaticKeepAlives: true,
    addRepaintBoundaries: true,
    addSemanticIndexes: true,
  };

  ref;

  style = {
    overflow: "scroll",
    // TODO: Other styles here
  };

  constructor(props: ListViewProps) {
    super(props);
    this.handleScroll = this.handleScroll.bind(this);
    this.ref = React.createRef();
  }

  handleScroll() {
    let value = this.ref.current.scrollTop;
    // TODO this.props.controller?.setScrollPosition(value);
  }

  render() {
    let classNames = [];
    if (!this.props.shrinkWrap) {
      classNames.push("max");
    }
    if (this.props.scrollDirection == Axis.horizontal) {
      classNames.push("sh");
    } else {
      classNames.push("sv");
    }
    if (this.props.children) {
      return React.createElement(
        "ui-list-view",
        {
          ref: this.ref,
          class:
            classNames.join(" ") +
            (this.props.className ? " " + this.props.className : ""),
          ...processBaseUIProps(this.props),
        },
        this.props.children
      );
    }
    let items: ReactNode[] = [];
    for (let i = 0; i < this.props.itemCount; i++) {
      items.add(this.props.itemBuilder(this.context, i));
    }
    return React.createElement(
      "ui-list-view",
      {
        ref: this.ref,
        class:
          (this.props.className ? " " + this.props.className : "") +
          " ListView " +
          classNames.join(" "),
      },
      items
    );
  }
}

export default function ListView(props: ListViewProps) {
  return React.createElement(_ListView, {
    ..._ListView.defaultProps,
    ...props,
  });
}
