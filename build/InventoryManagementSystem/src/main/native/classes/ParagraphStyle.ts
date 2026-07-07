import { FontStyle } from "./FontStyle";
import StrutStyle from "./StrutStyle";
import Locale from "./Locale";
import { TextAlign } from "./TextAlign";
import { FontWeight } from "./FontWeight";
import { TextHeightBehavior } from "./TextHeightBehavior";
import { TextDirection } from "./TextDirection";

export default class ParagraphStyle {
  textAlign: TextAlign;
  textDirection: TextDirection;
  maxLines: number;
  fontFamily: string;
  fontSize: number;
  height: number;
  textHeightBehavior: TextHeightBehavior;
  fontWeight: FontWeight;
  fontStyle: FontStyle;
  strutStyle: StrutStyle;
  ellipsis: string;
  locale: Locale;

  constructor(props: Partial<ParagraphStyle>) {
    Object.assign(this, props);
  }
}
