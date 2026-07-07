import React, { ReactNode } from "react";
import { Alignment } from "../classes/Alignment";
import Matrix4 from "../classes/Matrix4";
import { Offset } from "../classes/Offset";

export interface TransformProps {
  transform: Matrix4;
  origin?: Offset;
  alignment?: Alignment;
  transformHitTests?: boolean;
  child?: ReactNode;
}

class _Transform extends React.Component<TransformProps, {}> {
  render() {
    return React.createElement("ui-transform", {}, this.props.child);
  }
}

export default function Transform(props: TransformProps) {
  return React.createElement(_Transform, props);
}
