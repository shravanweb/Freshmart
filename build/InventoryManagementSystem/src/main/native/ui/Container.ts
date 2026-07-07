import React, { ReactNode } from "react";
import { Alignment } from "../classes/Alignment";
import { AlignmentGeometry } from "../classes/AlignmentGeometry";
import { BoxConstraints } from "../classes/BoxConstraints";
import { BoxDecoration } from "../classes/BoxDecoration";
import { BlendMode } from "../classes/BlendMode";
import { Clip } from "../classes/Clip";
import { Color } from "../classes/Color";
import Decoration from "../classes/Decoration";
import { EdgeInsets } from "../classes/EdgeInsets";
import Matrix4 from "../classes/Matrix4";
import { BaseComponentUtil } from "./BaseComponent";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";
import Glassmorphism from "../../classes/Glassmorphism";

interface ContainerProps extends BaseUIProps {
  key?: string;
  top?: number;
  bottom?: number;
  right?: number;
  left?: number;
  padding?: EdgeInsets;
  visibility?: boolean; // TODO: This is not used in Container
  width?: number;
  height?: number;
  decoration?: BoxDecoration;
  expand?: boolean; // TODO: This is not used
  constraints?: BoxConstraints;
  alignment?: AlignmentGeometry;
  transformAlignment?: AlignmentGeometry;
  foregroundDecoration?: Decoration;
  margin?: EdgeInsets;
  clipBehavior?: Clip; // Not used
  transform?: Matrix4; // TODO
  color?: Color;
  glassmorphism?: Glassmorphism;
  child?: ReactNode;
}

class _Container extends React.Component<ContainerProps, {}> {
  constructor(props: ContainerProps) {
    super(props);
  }

  private populateStyle(style: any, transitions: Map<string, number>) {
    // Width and Height
    BaseComponentUtil.applyBaseComponentProps(
      style,
      this.props,
      transitions,
      () => this.props.decoration?.border
    );
    this.handleAlignment(style);
    this.applyMargin(style);
    this.applyPadding(style);
    if (this.props.glassmorphism) {
      this.applyGlassmorphism(style);
    }
  }

  private blendModeToCss(mode: BlendMode): string {
    const map: Record<BlendMode, string> = {
      [BlendMode.clear]: "clear",
      [BlendMode.src]: "copy",
      [BlendMode.dst]: "destination-over",
      [BlendMode.srcOver]: "normal",
      [BlendMode.dstOver]: "destination-over",
      [BlendMode.srcIn]: "source-in",
      [BlendMode.dstIn]: "destination-in",
      [BlendMode.srcOut]: "source-out",
      [BlendMode.dstOut]: "destination-out",
      [BlendMode.srcATop]: "source-atop",
      [BlendMode.dstATop]: "destination-atop",
      [BlendMode.multiply]: "multiply",
      [BlendMode.xor]: "xor",
      [BlendMode.plus]: "plus",
      [BlendMode.modulate]: "multiply",
      [BlendMode.screen]: "screen",
      [BlendMode.overlay]: "overlay",
      [BlendMode.darken]: "darken",
      [BlendMode.lighten]: "lighten",
      [BlendMode.colorDodge]: "color-dodge",
      [BlendMode.colorBurn]: "color-burn",
      [BlendMode.hardLight]: "hard-light",
      [BlendMode.softLight]: "soft-light",
      [BlendMode.difference]: "difference",
      [BlendMode.exclusion]: "exclusion",
      [BlendMode.hue]: "hue",
      [BlendMode.saturation]: "saturation",
      [BlendMode.color]: "color",
      [BlendMode.luminosity]: "luminosity",
    };
    return map[mode] ?? "normal";
  }

  private applyGlassmorphism(style: any) {
    const g = this.props.glassmorphism!;
    const blurPx = Math.max(0, g.blurSigma?.dx ?? g.blurSigma?.dy ?? 10);
    Object.assign(style, {
      backdropFilter: `blur(${blurPx}px)`,
      mixBlendMode: this.blendModeToCss(g.blendMode),
    });
    if (g.borderRadius) {
      if (g.borderRadius.isUniform && g.borderRadius.radius != null) {
        Object.assign(style, { borderRadius: `${g.borderRadius.radius}px` });
      } else {
        const tl = g.borderRadius.topLeft?.x ?? 0;
        const tr = g.borderRadius.topRight?.x ?? 0;
        const br = g.borderRadius.bottomRight?.x ?? 0;
        const bl = g.borderRadius.bottomLeft?.x ?? 0;
        Object.assign(style, {
          borderRadius: `${tl}px ${tr}px ${br}px ${bl}px`,
        });
      }
    }
  }

  private applyMargin(style: any) {
    if (this.props.margin == null) {
      return;
    }
    if (this.props.margin.asDirection) {
      let v: number = this.props.margin.vertical ?? 0,
        h: number = this.props.margin.horizontal ?? 0;
      Object.assign(style, {
        marginTop: v,
        marginBottom: v,
        marginLeft: h,
        marginRight: h,
      });
    } else {
      let t: number = this.props.margin.top ?? 0,
        l: number = this.props.margin.left ?? 0,
        r: number = this.props.margin.right ?? 0,
        b: number = this.props.margin.bottom ?? 0;
      Object.assign(style, {
        marginTop: t,
        marginBottom: b,
        marginLeft: l,
        marginRight: r,
      });
    }
  }

  private applyPadding(style: any) {
    if (this.props.padding == null) {
      return;
    }
    if (this.props.padding.asDirection) {
      let v: number = this.props.padding.vertical ?? 0,
        h: number = this.props.padding.horizontal ?? 0;
      Object.assign(style, {
        paddingTop: v,
        paddingBottom: v,
        paddingLeft: h,
        paddingRight: h,
      });
    } else {
      let t: number = this.props.padding.top ?? 0,
        l: number = this.props.padding.left ?? 0,
        r: number = this.props.padding.right ?? 0,
        b: number = this.props.padding.bottom ?? 0;
      Object.assign(style, {
        paddingTop: t,
        paddingBottom: b,
        paddingLeft: l,
        paddingRight: r,
      });
    }
  }

  private handleAlignment(style: any) {
    // TODO: Make sure this works.
    let al = this.props.alignment;
    if (al != null && typeof al != "undefined") {
      Object.assign(style, {
        display: "flex",
      });
      switch (al) {
        case Alignment.topLeft:
          Object.assign(style, {
            float: "left",
            justifyContent: "start",
          });
          break;
        case Alignment.topCenter:
          Object.assign(style, {
            alignItems: "center",
            justifyContent: "start",
          });
          break;
        case Alignment.topRight:
          Object.assign(style, {
            float: "right",
            justifyContent: "start",
          });
          break;
        case Alignment.centerLeft:
          Object.assign(style, {
            float: "left",
            justifyContent: "center",
          });
          break;
        case Alignment.center:
          Object.assign(style, {
            alignItems: "center",
            justifyContent: "center",
          });
          break;
        case Alignment.centerRight:
          Object.assign(style, {
            float: "right",
            justifyContent: "center",
          });
          break;
        case Alignment.bottomLeft:
          Object.assign(style, {
            float: "left",
            justifyContent: "end",
          });
          break;
        case Alignment.bottomCenter:
          Object.assign(style, {
            alignItems: "center",
            justifyContent: "end",
          });
          break;
        case Alignment.bottomRight:
          Object.assign(style, {
            float: "right",
            justifyContent: "end",
          });
          break;
      }
    }
  }

  private handleWidth(style: any, clsName: string) {
    let removableWidth = 0;

    const elem = document.createElement("div");
    // Apply the class
    elem.classList.add(clsName);
    // Append it to the document to compute styles
    document.body.appendChild(elem);
    // Get the computed style

    if (this.props.padding) {
      const padding = this.props.padding;
      removableWidth = padding.left + padding.right;
    } else {
      const padding = window.getComputedStyle(elem).getPropertyValue("padding");
      if (padding) {
        const paddingValues = padding.split(" ");
        // Convert the padding values to numbers and handle different padding formats
        const paddingTopBottom = paddingValues[0]
          ? parseInt(paddingValues[0], 10)
          : 0;
        let paddingLeftRight = 0;
        if (paddingValues.length === 1) {
          // If there's only one value, it applies to all sides
          paddingLeftRight = paddingTopBottom;
          paddingLeftRight = 2 * paddingLeftRight;
        } else if (paddingValues.length === 2) {
          // If there are two values, the second one applies to left/right
          paddingLeftRight = parseInt(paddingValues[1], 10);
          paddingLeftRight = 2 * paddingLeftRight;
        } else if (paddingValues.length >= 3) {
          // If there are three or more values, the second value is the right padding, and the fourth (if exists) is the left padding
          paddingLeftRight =
            parseInt(paddingValues[1], 10) +
            (paddingValues[3]
              ? parseInt(paddingValues[3], 10)
              : parseInt(paddingValues[1], 10));
        }
        // Calculate the total removable width (left + right padding)
        removableWidth = paddingLeftRight;
      }
    }

    if (this.props.margin) {
      const margin = this.props.margin;
      removableWidth = removableWidth + margin.left + margin.right;
    } else {
      const margin = window.getComputedStyle(elem).getPropertyValue("margin");
      if (margin) {
        const marginValues = margin.split(" ");
        // Convert the padding values to numbers and handle different padding formats
        const paddingTopBottom = marginValues[0]
          ? parseInt(marginValues[0], 10)
          : 0;
        let marginLeftRight = 0;
        if (marginValues.length === 1) {
          // If there's only one value, it applies to all sides
          marginLeftRight = paddingTopBottom;
          marginLeftRight = 2 * marginLeftRight;
        } else if (marginValues.length === 2) {
          // If there are two values, the second one applies to left/right
          marginLeftRight = parseInt(marginValues[1], 10);
          marginLeftRight = 2 * marginLeftRight;
        } else if (marginValues.length >= 3) {
          // If there are three or more values, the second value is the right padding, and the fourth (if exists) is the left padding
          marginLeftRight =
            parseInt(marginValues[1], 10) +
            (marginValues[3]
              ? parseInt(marginValues[3], 10)
              : parseInt(marginValues[1], 10));
        }
        // Calculate the total removable width (left + right padding)
        removableWidth = removableWidth + marginLeftRight;
      }
    }
    // check decoration exists
    if (this.props.decoration) {
      const decoration = this.props.decoration;
      if (decoration.border) {
        const border = decoration.border;
        if (border.left) {
          removableWidth += border.left.width;
        }
        if (border.right) {
          removableWidth += border.right.width;
        }
        if (border.width) {
          removableWidth += 2 * border.width;
        }
      }
    } else {
      // const border = window.getComputedStyle(elem).getPropertyValue('border');
      // if (border) {
      //   const borderValues = border.split(' ');
      //   // Convert the border values to numbers and handle different border formats
      //   const borderTopBottom = borderValues[0] ? parseInt(borderValues[0], 10) : 0;
      //   let borderLeftRight = 0;
      //   if (borderValues.length === 1) {
      //     // If there's only one value, it applies to all sides
      //     borderLeftRight = borderTopBottom;
      //     borderLeftRight = 2 * borderLeftRight;
      //   } else if (borderValues.length === 2) {
      //     // If there are two values, the second one applies to left/right
      //     borderLeftRight = parseInt(borderValues[1], 10);
      //     borderLeftRight = 2 * borderLeftRight;
      //   } else if (borderValues.length >= 3) {
      //     // If there are three or more values, the second value is the right border, and the fourth (if exists) is the left border
      //     borderLeftRight = parseInt(borderValues[1], 10) + (borderValues[3] ? parseInt(borderValues[3], 10) : parseInt(borderValues[1], 10));
      //   }
      //   // Calculate the total removable width (left + right border)
      //   removableWidth += borderLeftRight;
      // }
    }
    //console.log('removableWidth', removableWidth);

    document.body.removeChild(elem);
    if (this.props.width == Number.infinity) {
      // calc(100% - removableWidth)
      Object.assign(style, {
        width: "calc(100% - " + removableWidth + "px)",
      });
    } else {
      Object.assign(style, {
        width: this.props.width - removableWidth + "px",
      });
    }
  }

  private handleHeight(style: any, clsName: string) {
    let removableHeight = 0;

    const elem = document.createElement("div");
    // Apply the class
    elem.classList.add(clsName);
    // Append it to the document to compute styles
    document.body.appendChild(elem);
    // Get the computed style

    if (this.props.padding) {
      const padding = this.props.padding;
      removableHeight = padding.top + padding.bottom;
    } else {
      const padding = window.getComputedStyle(elem).getPropertyValue("padding");
      if (padding) {
        const paddingValues = padding.split(" ");
        // Convert the padding values to numbers and handle different padding formats
        const paddinValue = paddingValues[0]
          ? parseInt(paddingValues[0], 10)
          : 0;
        let paddingTopBottom = 0;
        if (paddingValues.length === 1) {
          // If there's only one value, it applies to all sides
          paddingTopBottom = paddinValue;
          paddingTopBottom = 2 * paddingTopBottom;
        } else if (paddingValues.length === 2) {
          // If there are two values, the first one applies to top/bottom
          paddingTopBottom = parseInt(paddingValues[0], 10);
          paddingTopBottom = 2 * paddingTopBottom;
        } else if (paddingValues.length >= 3) {
          // If there are three or more values, the second value is the left padding, and the fourth (if exists) is the right padding
          paddingTopBottom =
            parseInt(paddingValues[0], 10) +
            (paddingValues[2]
              ? parseInt(paddingValues[2], 10)
              : parseInt(paddingValues[0], 10));
        }
        // Calculate the total removable height (top + bottom padding)
        removableHeight = paddingTopBottom;
      }
    }
    if (this.props.margin) {
      const margin = this.props.margin;
      removableHeight = removableHeight + margin.top + margin.bottom;
    } else {
      const margin = window.getComputedStyle(elem).getPropertyValue("margin");
      if (margin) {
        const marginValues = margin.split(" ");
        // Convert the padding values to numbers and handle different padding formats
        const marginValue = marginValues[0] ? parseInt(marginValues[0], 10) : 0;
        let marginTopBottom = 0;
        if (marginValues.length === 1) {
          // If there's only one value, it applies to all sides
          marginTopBottom = marginValue;
          marginTopBottom = 2 * marginTopBottom;
        } else if (marginValues.length === 2) {
          // If there are two values, the first one applies to top/bottom
          marginTopBottom = parseInt(marginValues[0], 10);
          marginTopBottom = 2 * marginTopBottom;
        } else if (marginValues.length >= 3) {
          // If there are three or more values, the second value is the left padding, and the fourth (if exists) is the right padding
          marginTopBottom =
            parseInt(marginValues[0], 10) +
            (marginValues[2]
              ? parseInt(marginValues[2], 10)
              : parseInt(marginValues[0], 10));
        }
        // Calculate the total removable height (top + bottom padding)
        removableHeight = removableHeight + marginTopBottom;
      }
    }
    // check decoration exists
    if (this.props.decoration) {
      const decoration = this.props.decoration;
      if (decoration.border) {
        const border = decoration.border;
        if (border.top) {
          removableHeight += border.top.width;
        }
        if (border.bottom) {
          removableHeight += border.bottom.width;
        }
        if (border.width) {
          removableHeight += 2 * border.width;
        }
      }
    } else {
      // const border = window.getComputedStyle(elem).getPropertyValue('border');
      // if (border) {
      //   const borderValues = border.split(' ');
      //   // Convert the border values to numbers and handle different border formats
      //   const borderTopBottom = borderValues[0] ? parseInt(borderValues[0], 10) : 0;
      //   let borderLeftRight = 0;
      //   if (borderValues.length === 1) {
      //     // If there's only one value, it applies to all sides
      //     borderLeftRight = borderTopBottom;
      //     borderLeftRight = 2 * borderLeftRight;
      //   } else if (borderValues.length === 2) {
      //     // If there are two values, the second one applies to left/right
      //     borderLeftRight = parseInt(borderValues[1], 10);
      //     borderLeftRight = 2 * borderLeftRight;
      //   } else if (borderValues.length >= 3) {
      //     // If there are three or more values, the second value is the right border, and the fourth (if exists) is the left border
      //     borderLeftRight = parseInt(borderValues[1], 10) + (borderValues[3] ? parseInt(borderValues[3], 10) : parseInt(borderValues[1], 10));
      //   }
      //   // Calculate the total removable height (top + bottom border)
      //   removableHeight += borderLeftRight;
      // }
    }
    //console.log('removableHeight', removableHeight);
    document.body.removeChild(elem);

    if (this.props.height == Number.infinity) {
      Object.assign(style, {
        height: "calc(100% - " + removableHeight + "px)",
      });
    } else {
      Object.assign(style, {
        height: this.props.height - removableHeight + "px",
      });
    }
  }

  render() {
    let transitions: Map<string, number> = new Map();
    let style = {};

    // get class name from props
    const className = this.props.className;
    // split class name by space
    this.populateStyle(style, transitions);
    if (className) {
      const classNames = className.split(" ");
      // first one is the class name and check if it is empty
      const firstClassName = classNames[0];
      if (this.props.width) {
        if (firstClassName === "x75e") {
          console.log("x75e");
        }
        this.handleWidth(style, firstClassName);
      }
      // handle height
      if (this.props.height) {
        this.handleHeight(style, firstClassName);
      }
    }

    if (transitions.size > 0) {
      let transitionString = "";
      for (let [key, value] of transitions) {
        transitionString += key + " " + value + "s, ";
      }
      transitionString = transitionString.substring(
        0,
        transitionString.length - 2
      );
      Object.assign(style, {
        transition: transitionString,
      });
    }
    return React.createElement(
      "ui-container",
      {
        class:
          (this.props.className ? " " + this.props.className : "") +
          " Container ",
        style,
        ...processBaseUIProps(this.props),
      },
      this.props.child
    );
  }
}

export default function Container(props: ContainerProps) {
  return React.createElement(_Container, {
    ...props,
    ...{
      clipBehavior: Clip.none,
    },
  });
}
