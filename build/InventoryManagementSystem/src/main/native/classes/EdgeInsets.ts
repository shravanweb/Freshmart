import { BaseTrasitions } from "./BaseTrasitions";
export class EdgeInsets extends BaseTrasitions {
  top?: number; //these all fileds types in d3e Double
  left?: number;
  right?: number;
  bottom?: number;
  horizontal?: number;
  vertical?: number;
  asDirection?: boolean;

  static zero = new EdgeInsets({
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
  });

  constructor(props: {
    top?: number;
    left?: number;
    right?: number;
    bottom?: number;
    horizontal?: number;
    vertical?: number;
    asDirection?: boolean;
    transitions?: Map<string, any>;
  }) {
    super(props.transitions);
    this.top = props.top;
    this.left = props.left;
    this.right = props.right;
    this.bottom = props.bottom;
    this.horizontal = props.horizontal;
    this.vertical = props.vertical;
    this.asDirection = props.asDirection;
  }

  static all(edge: number, transitions?: Map<string, any>): EdgeInsets {
    return new EdgeInsets({
      top: edge,
      left: edge,
      right: edge,
      bottom: edge,
      transitions: transitions,
    });
  }

  static only(props: {
    left?: number;
    top?: number;
    right?: number;
    bottom?: number;
    transitions?: Map<string, any>;
  }): EdgeInsets {
    return new EdgeInsets({
      top: props.top,
      left: props.left,
      right: props.right,
      bottom: props.bottom,
      transitions: props.transitions,
    });
  }

  static symmetric(props: {
    vertical: number;
    horizontal: number;
    transitions?: Map<string, any>;
  }): EdgeInsets {
    return new EdgeInsets({
      top: props.vertical,
      left: props.horizontal,
      right: props.horizontal,
      bottom: props.vertical,
      transitions: props.transitions,
    });
  }

  static fromLTRB(
    left: number,
    top: number,
    right: number,
    bottom: number,
    transitions?: Map<string, any>
  ): EdgeInsets {
    return new EdgeInsets({
      top: top,
      left: left,
      right: right,
      bottom: bottom,
      transitions: transitions,
    });
  }
}
