import TextStyle from "./TextStyle";

export class TextStyleExt {
  static merge(main: TextStyle, next: TextStyle): TextStyle {
    if (main) {
      return main.merge(next);
    } else {
      return next;
    }
  }
}
