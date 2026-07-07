import { EdgeInsets } from "./EdgeInsets";
import Size from "./Size";

export class MediaQuery {
  static of(ctx: any): MediaQueryData {
    return new MediaQueryData({
      padding: EdgeInsets.zero,
      size: new Size({ width: 0, height: 0 }),
    });
  }
}

export class MediaQueryData {
  padding: EdgeInsets;
  size: Size;
  constructor(props: Partial<MediaQueryData>) {
    Object.assign(this, props);
  }
}
