export default class ExpressionString {
  private _content: string;

  private _attachment: any;

  constructor(content: string) {
    this._content = content;
  }

  public get content(): string {
    return this._content;
  }

  public set attachment(attachment: any) {
    this._attachment = attachment;
  }

  public get attachment(): any {
    return this._attachment;
  }
}
