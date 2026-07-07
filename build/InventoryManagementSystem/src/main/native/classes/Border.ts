import { BorderSide } from "./BorderSide";
import { BorderStyle } from "./BorderStyle";
import { Color } from "./Color";

export class Border {
  top?: BorderSide;
  left?: BorderSide;
  right?: BorderSide;
  bottom?: BorderSide;
  width?: number;
  color?: Color;
  style?: BorderStyle;

  constructor(props: {
    left?: BorderSide;
    right?: BorderSide;
    top?: BorderSide;
    bottom?: BorderSide;
  }) {
    this.top = props?.top;
    this.left = props?.left;
    this.right = props?.right;
    this.bottom = props?.bottom;
  }

  static all(props: {
    color?: Color;
    width?: number;
    style?: BorderStyle;
  }): Border {
    return new Border({
      left: new BorderSide({
        color: props?.color,
        width: props?.width,
        style: props?.style ?? BorderStyle.solid,
      }),
      right: new BorderSide({
        color: props?.color,
        width: props?.width,
        style: props?.style ?? BorderStyle.solid,
      }),
      top: new BorderSide({
        color: props?.color,
        width: props?.width,
        style: props?.style ?? BorderStyle.solid,
      }),
      bottom: new BorderSide({
        color: props?.color,
        width: props?.width,
        style: props?.style ?? BorderStyle.solid,
      }),
    });
  }
}
