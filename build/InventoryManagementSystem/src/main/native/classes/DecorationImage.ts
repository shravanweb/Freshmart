import { ColorFilter } from "../classes/ColorFilter";
import { Alignment } from "../classes/Alignment";
import { BoxFit } from "./BoxFit";
import { ImageRepeat } from "./ImageRepeat";
import { ImageFrom } from "./ImageFrom";

export class DecorationImage {
  colorFilter?: ColorFilter;
  imageType?: ImageFrom;
  imageUrl?: string;
  fit?: BoxFit;
  alignment?: Alignment;
  repeat?: ImageRepeat;
  matchTextDirection?: boolean;

  constructor(props?: {
    colorFilter?: ColorFilter;
    imageType?: ImageFrom;
    imageUrl?: string;
    fit?: BoxFit;
    alignment?: Alignment;
    repeat?: ImageRepeat;
    matchTextDirection?: boolean;
  }) {
    this.colorFilter = props?.colorFilter;
    this.fit = props?.fit;
    this.imageType = props?.imageType;
    this.imageUrl = props?.imageUrl;
    this.alignment = props?.alignment;
    this.repeat = props?.repeat;
    this.matchTextDirection = props?.matchTextDirection;
  }
}
