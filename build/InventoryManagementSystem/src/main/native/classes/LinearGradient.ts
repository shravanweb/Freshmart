import { Alignment } from "./Alignment";
import { Color } from "./Color";
import { Gradient } from "./Gradient";
import * as ui from "..";
export class LinearGradient extends Gradient {
  constructor(props: {
    begin?: Alignment;
    end?: Alignment;
    colors?: Color[];
    stops?: number[];
    tileMode?: ui.TileMode;
  }) {
    super();
  }
}
