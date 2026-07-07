import { Offset } from "./Offset";
import Size from "./Size";

export class RenderBox {
  size?: Size;
  position: Offset;
  constructor(props?: Partial<RenderBox>) {
    Object.assign(this, props);
  }

  localToGlobal(point: Offset, props?: { ancestor: any }): Offset {
    return this.position;
  }
}
