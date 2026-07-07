import ParagraphStyle from "./ParagraphStyle";
import Paragraph from "./Paragraph";
import { PainterTextStyle } from "./PainterTextStyle";

export class ParagraphBuilder {
  text: string = "";
  styles: PainterTextStyle[] = [];

  constructor(private paragraphStyle: ParagraphStyle) {}

  pushStyle(style: PainterTextStyle): void {
    this.styles.push(style);
  }

  addText(text: string): void {
    this.text.concat(text);
  }

  build(): Paragraph {
    return new Paragraph(this.text, {
      paraStyle: this.paragraphStyle,
      paintStyle: this.styles.last,
    });
  }
}
