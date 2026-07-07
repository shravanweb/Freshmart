import { Alignment } from "./Alignment";
import { Color } from "./Color";
import { Gradient } from "./Gradient";
import TileMode from "./TileMode";

export class RadialGradient extends Gradient {
  constructor(props: {
    center?: Alignment;
    end?: Alignment;
    colors?: Color[];
    stops?: number[];
    radius?: number;
    tileMode?: TileMode;
    focul?: Alignment;
    foculRadius?: number;
  }) {
    super();
  }
}
