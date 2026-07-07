export class StringBuffer {
  private value: string;

  constructor(content: any = "") {
    this.value = content.toString();
  }

  get length(): number {
    return this.value.length;
  }

  get isEmpty(): boolean {
    return this.value.isEmpty;
  }

  get isNonEmpty(): boolean {
    return this.value.isNotEmpty;
  }

  write(obj: any): void {
    this.value.concat(obj);
  }

  writeCharCode(charCode: number): void {
    this.value.concat(String.fromCharCode(charCode));
  }

  writeAll(objects: Iterable<any>, separator: string = ""): string {
    return this.value.concat(separator).concat(objects.join(separator));
  }

  writeln(obj: any = ""): string {
    return this.value.concat(obj);
  }

  clear(): void {
    this.value = "";
  }

  toString(): string {
    return this.value;
  }
}
export default StringBuffer;
