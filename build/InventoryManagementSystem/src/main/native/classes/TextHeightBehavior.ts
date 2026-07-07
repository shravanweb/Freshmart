import { TextLeadingDistribution } from "./TextLeadingDistribution";

export class TextHeightBehavior {
  applyHeightToFirstAscent: boolean = true;
  applyHeightToLastDescent: boolean = true;
  leadingDistribution = TextLeadingDistribution.proportional;
  constructor(props: Partial<TextHeightBehavior>) {
    Object.assign(this, props);
  }
}
