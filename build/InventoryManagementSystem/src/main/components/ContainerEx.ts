import { Key, ReactNode } from "react";
import * as ui from "../native";

export class ContainerEx {
  static from(props: {
    key?: Key;
    alignment?: ui.AlignmentGeometry;
    padding?: ui.EdgeInsetsGeometry;
    color?: ui.Color;
    decoration?: ui.BoxDecoration;
    foregroundDecoration?: ui.Decoration;
    width?: number;
    height?: number;
    constraints?: ui.BoxConstraints;
    margin?: ui.EdgeInsets;
    transform?: ui.Matrix4;
    transformAlignment?: ui.AlignmentGeometry;
    child?: ReactNode;
    clipBehavior?: ui.Clip;
  }): ReactNode {
    props.clipBehavior ??= ui.Clip.none;
    // if (props.decoration?.borderRadius != null) { // TODO
    //   props.child = ui.ClipRRect({
    //     child: props.child,
    //     borderRadius: ui.BorderRadius.circular(0.0),
    //   });
    // }
    return ui.Container({
      alignment: props.alignment,
      padding: props.padding,
      color: props.color,
      decoration: props.decoration,
      foregroundDecoration: props.foregroundDecoration,
      width: props.width,
      height: props.height,
      constraints: props.constraints,
      margin: props.margin,
      transform: props.transform,
      transformAlignment: props.transformAlignment,
      child: props.child,
      clipBehavior: props.clipBehavior,
    });
  }
}

export default ContainerEx;
