import { EdgeInsets } from "../classes/EdgeInsets";
import { BoxConstraints } from "../classes/BoxConstraints";
import { Border } from "../classes/Border";
import { BorderStyle } from "../classes/BorderStyle";
import { BoxDecoration } from "../classes/BoxDecoration";
import BaseUIProps from "./BaseUIProps";
import { BorderRadius } from "../classes/BorderRadius";
import { BoxShape } from "../classes/BoxShape";
import { BoxFit } from "../classes/BoxFit";

interface BaseComponentProps extends BaseUIProps {
  padding?: EdgeInsets;
  decoration?: BoxDecoration;
  height?: number;
  width?: number;
  constraints?: BoxConstraints;
  margin?: EdgeInsets;
}

export abstract class BaseComponentUtil {
  static applyBaseComponentProps(
    obj: Object,
    props: any,
    transitions?: Map<string, number>,
    extraBorder?: () => Border | undefined
  ) {
    this.applyWidthAndHeight(obj, props);
    this.applyDecoration(obj, props, transitions, extraBorder);
    this.applyPadding(obj, props, transitions);
    this.applyMargin(obj, props, transitions);
  }

  private static boxFitToCss(boxFit: BoxFit): string {
    switch (boxFit) {
      case BoxFit.fill:
        return "100% 100%";
      case BoxFit.contain:
        return "contain";
      case BoxFit.cover:
        return "cover";
      case BoxFit.fitWidth:
        return "100% auto";
      case BoxFit.fitHeight:
        return "auto 100%";
      case BoxFit.none:
        return "auto";
      case BoxFit.scaleDown:
        return "contain"; // fallback as scale-down is not widely supported
      default:
        return "cover";
    }
  }

  private static applyWidthAndHeight(obj: any, props: BaseComponentProps) {
    if (props.width) {
      obj.width = props.width + "px";
    }
    if (props.height) {
      obj.height = props.height + "px";
    }
    if (props?.constraints?.minWidth) {
      obj.minWidth = props.constraints.minWidth;
    }
    if (props?.constraints?.maxWidth) {
      obj.maxWidth = props.constraints.maxWidth;
    }
    if (props?.constraints?.minHeight) {
      obj.minHeight = props.constraints.minHeight;
    }
    if (props?.constraints?.maxHeight) {
      obj.maxHeight = props.constraints.maxHeight;
    }
  }
  private static applyDecoration(
    obj: Object,
    props: BaseComponentProps,
    transitions: Map<string, number>,
    extraBorder?: () => Border | undefined
  ) {
    let backgroundColor = props.decoration?.color;
    let transitionsMap = props.decoration?.transitions;
    if (transitionsMap) {
      transitionsMap.forEach((value, key) => {
        transitions?.set(key, value);
      });
    }
    if (backgroundColor) {
      Object.assign(obj, {
        backgroundColor: backgroundColor.toHexa(),
      });
    }
    let border = props.decoration?.border;
    if (border == null && typeof border === "undefined") {
      border = extraBorder?.();
    }
    if (border != null && typeof border !== "undefined") {
      // Check universal
      if (border.width && border.color && border.style) {
        Object.assign(obj, {
          borderWidth: border.width,
          borderStyle: BorderStyle[border.style],
          borderColor: border.color.toHexa(),
        });
      } else {
        if (border.top && border.top.style) {
          Object.assign(obj, {
            borderTopWidth: border.top?.width,
            borderTopStyle: BorderStyle[border.top.style],
            borderTopColor: border.top?.color.toHexa(),
          });
        }
        if (border.left && border.left.style) {
          Object.assign(obj, {
            borderLeftWidth: border.left?.width,
            borderLeftStyle: BorderStyle[border.left.style],
            borderLeftColor: border.left?.color.toHexa(),
          });
        }
        if (border.right && border.right.style) {
          Object.assign(obj, {
            borderRightWidth: border.right?.width,
            borderRightStyle: BorderStyle[border.right.style],
            borderRightColor: border.right?.color.toHexa(),
          });
        }
        if (border.bottom && border.bottom.style) {
          Object.assign(obj, {
            borderBottomWidth: border.bottom?.width,
            borderBottomStyle: BorderStyle[border.bottom.style],
            borderBottomColor: border.bottom?.color.toHexa(),
          });
        }
      }
    }
    let shape = props.decoration?.shape;
    if (shape) {
      Object.assign(obj, {
        borderRadius: shape == BoxShape.circle ? "50%" : 0,
      });
    }
    let borderRadius: BorderRadius = props.decoration?.borderRadius;
    if (borderRadius) {
      if (borderRadius.isUniform) {
        Object.assign(obj, {
          borderRadius: borderRadius.radius,
        });
      } else {
        let topLeft = borderRadius.topLeft;
        //here we are assuming x and y values are same.
        if (topLeft != null) {
          Object.assign(obj, {
            borderTopLeftRadius: topLeft.x,
          });
        }
        let topRight = borderRadius.topRight;
        if (topRight != null) {
          Object.assign(obj, {
            borderTopRightRadius: topRight.x,
          });
        }
        let bottomLeft = borderRadius.bottomLeft;
        if (bottomLeft != null) {
          Object.assign(obj, {
            borderBottomLeftRadius: bottomLeft.x,
          });
        }
        let bottomRight = borderRadius.bottomRight;
        if (bottomRight != null) {
          Object.assign(obj, {
            borderBottomRight: bottomRight.x,
          });
        }
      }
    }
    let backgroundImage = props.decoration?.image;
    if (backgroundImage) {
      Object.assign(obj, {
        backgroundImage: `url(${backgroundImage.imageUrl})`,
      });
    }
    let imageFit = props.decoration?.image?.fit;
    if (imageFit !== undefined && imageFit !== null) {
      Object.assign(obj, {
        backgroundSize: this.boxFitToCss(imageFit),
      });
    }
    let boxShadow = props.decoration?.boxShadow;
    if (boxShadow) {
      Object.assign(obj, {
        boxShadow: boxShadow
          .map((shadow) => {
            return `${shadow.offset?.dx}px ${shadow.offset?.dy}px ${
              shadow.blurRadius
            }px ${shadow.spreadRadius ?? 0}px ${shadow.color?.toHexa()}`;
          })
          .join(","),
      });
    }
  }
  private static applyMargin(
    obj: Object,
    props: BaseComponentProps,
    transitions: Map<string, number>
  ) {
    if (props.margin == null) {
      return;
    }
    let transitionsMap = props.padding?.transitions;
    if (transitionsMap) {
      transitionsMap.forEach((value, key) => {
        transitions?.set(key, value);
      });
    }
    if (props.margin.asDirection) {
      let v: number = props.margin.vertical ?? 0,
        h: number = props.margin.horizontal ?? 0;
      Object.assign(obj, {
        marginTop: v,
        marginBottom: v,
        marginLeft: h,
        marginRight: h,
      });
    } else {
      let t: number = props.margin.top ?? 0,
        l: number = props.margin.left ?? 0,
        r: number = props.margin.right ?? 0,
        b: number = props.margin.bottom ?? 0;
      Object.assign(obj, {
        marginTop: t,
        marginBottom: b,
        marginLeft: l,
        marginRight: r,
      });
    }
  }
  private static applyPadding(
    obj: Object,
    props: BaseComponentProps,
    transitions: Map<string, number>
  ) {
    if (props.padding == null) {
      return;
    }
    let transitionsMap = props.padding?.transitions;
    if (transitionsMap) {
      transitionsMap.forEach((value, key) => {
        transitions?.set(key, value);
      });
    }
    if (props.padding.asDirection) {
      let v: number = props.padding.vertical ?? 0,
        h: number = props.padding.horizontal ?? 0;
      Object.assign(obj, {
        paddingTop: v,
        paddingBottom: v,
        paddingLeft: h,
        paddingRight: h,
      });
    } else {
      let t: string = props.padding.top?.toString() ?? "0",
        l: string = props.padding.left?.toString() ?? "0",
        r: string = props.padding.right?.toString() ?? "0",
        b: string = props.padding.bottom?.toString() ?? "0";
      Object.assign(obj, {
        paddingTop: t,
        paddingBottom: b,
        paddingLeft: l,
        paddingRight: r,
      });
    }
  }
}
