import { FontStyle } from "./FontStyle";
import { FontWeight } from "./FontWeight";

export default class StrutStyle {
  fontFamily?: string;
  fontFamilyFallback?: Array<string>;
  fontSize?: number;
  leading?: number;
  height?: number;
  fontWeight?: FontWeight;
  fontStyle?: FontStyle;
  forceStrutHeight?: boolean;
  debugLabel?: string;
  packageValue?: string;
  constructor(props: Partial<StrutStyle>) {
    Object.assign(this, props);
  }
}
