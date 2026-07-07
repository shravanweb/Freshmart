import { Alignment } from "./Alignment";
import { Color } from "./Color";
import { Gradient } from "./Gradient";
import TileMode from "./TileMode";

export class SweepGradient extends Gradient {
  constructor(props: {
    center?: Alignment;
    end?: Alignment;
    colors?: Color[];
    stops?: number[];
    startAngle?: number;
    endAngle?: number;
    tileMode?: TileMode;
  }) {
    super();
  }
}
