import TextStyle from "./TextStyle";

export class TextSpan {
  style: TextStyle;
  text: string;
  constructor(param: { style: TextStyle; text: string }) {
    this.style = param.style;
    this.text = param.text;
  }
}
