import React, { ReactNode } from "react";
import { Axis } from "../classes/Axis";
import { DragStartBehavior } from "../classes/DragStartBehavior";
import { ScrollController } from "../classes/ScrollController";
import { ScrollPhysics } from "../classes/ScrollPhysics";
import BaseUIProps from "./BaseUIProps";

interface SingleChildScrollViewProps extends BaseUIProps {
  controller?: ScrollController;
  scrollDirection?: Axis;
  reverse?: boolean;
  primary?: boolean;
  physics?: ScrollPhysics;
  dragStartBehavior?: DragStartBehavior;
  child?: ReactNode;
}

class _SingleChildScrollView extends React.Component<
  SingleChildScrollViewProps,
  {}
> {
  static defaultProps = {
    scrollDirection: Axis.vertical,
    reverse: false,
    dragStartBehavior: DragStartBehavior.start,
  };

  ref;
  style = {
    //overflow: "scroll",
    // TODO: Other styles here
  };

  constructor(props: SingleChildScrollViewProps) {
    super(props);
    this.handleScroll = this.handleScroll.bind(this);
    this.ref = React.createRef();
  }

  handleScroll() {
    let value = this.ref.current.scrollTop;
    this.props.controller?.setScrollPosition(value);
  }

  render(): React.ReactNode {
    return this.props.child;
  }
}

export default function SingleChildScrollView(
  props: SingleChildScrollViewProps
) {
  return React.createElement(_SingleChildScrollView, props);
}
