import { Shadow } from "./Shadow";

export default class BoxShadow extends Shadow {
  spreadRadius?: number;
  constructor(props: Partial<BoxShadow>) {
    super(props);
    Object.assign(this, props);
  }
}
