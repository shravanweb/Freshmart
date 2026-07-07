export default class Size {
  width?: number;
  height?: number;
  constructor(props?: Partial<Size>) {
    Object.assign(this, props);
  }
}
