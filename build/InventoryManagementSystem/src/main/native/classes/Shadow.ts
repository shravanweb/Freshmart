import { Color } from "./Color";
import { Offset } from "./Offset";

export class Shadow {
  color?: Color;
  offset?: Offset;
  blurRadius?: number;
  constructor(props: Partial<Shadow>) {
    Object.assign(this, props);
  }
}
