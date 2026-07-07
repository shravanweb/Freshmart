import { ReactElement, ReactNode } from "react";
import { EdgeInsets } from "../classes/EdgeInsets";
import BaseUIProps from "./BaseUIProps";

interface PaddingProps extends BaseUIProps {
  padding: EdgeInsets;
  child: ReactElement;
}
export default function Padding(props: PaddingProps): ReactNode {
  var style = {};
  if (props.padding.asDirection) {
    let v: number = props.padding.vertical ?? 0,
      h: number = props.padding.horizontal ?? 0;
    style = {
      paddingTop: v,
      paddingBottom: v,
      paddingLeft: h,
      paddingRight: h,
    };
  } else {
    let t: string = props.padding.top?.toString() ?? "0",
      l: string = props.padding.left?.toString() ?? "0",
      r: string = props.padding.right?.toString() ?? "0",
      b: string = props.padding.bottom?.toString() ?? "0";
    style = {
      paddingTop: t,
      paddingBottom: b,
      paddingLeft: l,
      paddingRight: r,
    };
  }
  // TODO if (props.child.props.style == null) {
  //   Object.assign(props.child.props, {style});
  // } else {
  //   Object.assign(props.child.props.style, style);
  // }
  return props.child;
}
