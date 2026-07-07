import DateTime from "../core/DateTime";

export default class FileToUpload {
  public constructor(public file: File) {}

  public get lastModified(): DateTime {
    let lastModifiedMillis = this.file.lastModified;
    return DateTime.fromMillisecondsSinceEpoch(lastModifiedMillis);
  }

  public get name(): string {
    return this.file.name;
  }

  public get relativePath(): string {
    return this.file.webkitRelativePath;
  }

  public get size(): number {
    return this.file.size;
  }

  public get type(): string {
    return this.file.type;
  }

  public toString(): string {
    return this.file.toString();
  }
}
