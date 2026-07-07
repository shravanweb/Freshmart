export default class BlockString {
  content: string;

  attachment: any;

  constructor(content: string) {
    this.content = content;
  }

  getContent(): string {
    return this.content;
  }

  setAttachment(attachment: any) {
    this.attachment = attachment;
  }

  getAttachment() {
    return this.attachment;
  }
}
