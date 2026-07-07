export abstract class Clipboard {
  constructor() {}
  static async setData(data: ClipboardData): Promise<void> {}
}
export class ClipboardData {
  text: string;
  constructor(params?: Partial<ClipboardData>) {
    this.text = params?.text || "";
    this.copyToClipboard(this.text);
  }

  copyToClipboard(text: string) {
    navigator.clipboard
      .writeText(text)
      .then(() => {
        console.log("Text copied to clipboard");
      })
      .catch((err) => {
        console.error("Unable to copy text to clipboard", err);
      });
  }
}
