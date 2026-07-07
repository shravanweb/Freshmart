export class Color {
  value: number;

  constructor(value: number) {
    this.value = value;
  }

  get alpha() {
    return (this.value >>> 24) & 0xff;
  }

  get opacity() {
    return this.alpha / 0xff;
  }

  get red() {
    return (this.value >>> 16) & 0xff;
  }

  get green() {
    return (this.value >>> 8) & 0xff;
  }

  get blue() {
    return this.value & 0xff;
  }

  toHexa(): string {
    let hexCode: string;
    if (this.alpha === 0xff || this.alpha === -1) {
      hexCode =
        "#" +
        this.toColorHex(this.red) +
        this.toColorHex(this.green) +
        this.toColorHex(this.blue);
    } else {
      hexCode =
        "#" +
        this.toColorHex(this.red) +
        this.toColorHex(this.green) +
        this.toColorHex(this.blue) +
        this.toColorHex(this.alpha);
    }
    return hexCode;
  }

  toColorHex(v: number) {
    let h = v.toString(16);
    if (h.length === 1) {
      return "0" + h;
    }
    return h;
  }

  toHexString(): string {
    return `#${this.value.toString(16).padStart(6, "0")}`;
  }
  static fromARGB(a: number, r: number, g: number, b: number): Color {
    let value =
      (((a & 0xff) << 24) |
        ((r & 0xff) << 16) |
        ((g & 0xff) << 8) |
        ((b & 0xff) << 0)) &
      0xffffffff;
    return new Color(value);
  }
}
