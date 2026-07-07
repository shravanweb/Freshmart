import { TWO_TO_32, TWO_TO_52 } from "../classes/core";

export default class BufferReader {
  private index: number = 0;

  constructor(private data: Uint8Array) {}

  public readBool(): boolean {
    // boolean is sent as a byte
    return this._readByte() === 1;
  }

  public readByte() {
    return this._readByte();
  }

  public readInt(): number {
    let val: bigint = this._readInt();
    return this._decodeZigZag64(val);
  }

  public readDouble() {
    var bitsLow = this._readUint32();
    var bitsHigh = this._readUint32();

    var sign = (bitsHigh >> 31) * 2 + 1;
    var exp = (bitsHigh >>> 20) & 0x7ff;
    var mant = TWO_TO_32 * (bitsHigh & 0xfffff) + bitsLow;

    if (exp == 0x7ff) {
      if (mant) {
        return NaN;
      } else {
        return sign * Infinity;
      }
    }

    if (exp == 0) {
      // Denormal.
      return sign * Math.pow(2, -1074) * mant;
    } else {
      return sign * Math.pow(2, exp - 1075) * (mant + TWO_TO_52);
    }
  }

  public readString(): string {
    let len = this.readInt();
    if (len == -1) {
      return null;
    }

    var bytes = this.data;
    var cursor = this.index;
    var end = cursor + len;
    var codeUnits = [];

    var result = "";
    while (cursor < end) {
      var c = bytes[cursor++];
      if (c < 128) {
        // Regular 7-bit ASCII.
        codeUnits.push(c);
      } else if (c < 192) {
        // UTF-8 continuation mark. We are out of sync. This
        // might happen if we attempted to read a character
        // with more than four bytes.
        continue;
      } else if (c < 224) {
        // UTF-8 with two bytes.
        var c2 = bytes[cursor++];
        codeUnits.push(((c & 31) << 6) | (c2 & 63));
      } else if (c < 240) {
        // UTF-8 with three bytes.
        var c2 = bytes[cursor++];
        var c3 = bytes[cursor++];
        codeUnits.push(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
      } else if (c < 248) {
        // UTF-8 with 4 bytes.
        var c2 = bytes[cursor++];
        var c3 = bytes[cursor++];
        var c4 = bytes[cursor++];
        // Characters written on 4 bytes have 21 bits for a codepoint.
        // We can't fit that on 16bit characters, so we use surrogates.
        var codepoint =
          ((c & 7) << 18) | ((c2 & 63) << 12) | ((c3 & 63) << 6) | (c4 & 63);
        // Surrogates formula from wikipedia.
        // 1. Subtract 0x10000 from codepoint
        codepoint -= 0x10000;
        // 2. Split this into the high 10-bit value and the low 10-bit value
        // 3. Add 0xD800 to the high value to form the high surrogate
        // 4. Add 0xDC00 to the low value to form the low surrogate:
        var low = (codepoint & 1023) + 0xdc00;
        var high = ((codepoint >> 10) & 1023) + 0xd800;
        codeUnits.push(high, low);
      }

      // Avoid exceeding the maximum stack size when calling `apply`.
      if (codeUnits.length >= 8192) {
        result += String.fromCharCode.apply(null, codeUnits);
        codeUnits.length = 0;
      }
    }
    result += this.byteArrayToString(codeUnits);
    this.index = cursor;
    return result;
  }

  private byteArrayToString(bytes) {
    var CHUNK_SIZE = 8192;

    // Special-case the simple case for speed's sake.
    if (bytes.length <= CHUNK_SIZE) {
      return String.fromCharCode.apply(null, bytes);
    }

    // The remaining logic splits conversion by chunks since
    // Function#apply() has a maximum parameter count.
    // See discussion: http://goo.gl/LrWmZ9

    var str = "";
    for (var i = 0; i < bytes.length; i += CHUNK_SIZE) {
      var chunk = Array.prototype.slice.call(bytes, i, i + CHUNK_SIZE);
      str += String.fromCharCode.apply(null, chunk);
    }
    return str;
  }

  private _readByte() {
    return this.data[this.index++];
  }

  private _readUint32() {
    var a = this.data[this.index + 0];
    var b = this.data[this.index + 1];
    var c = this.data[this.index + 2];
    var d = this.data[this.index + 3];
    this.index += 4;
    return ((a << 0) | (b << 8) | (c << 16) | (d << 24)) >>> 0;
  }

  private _decodeZigZag64(value: bigint): number {
    return (value & BigInt(1)) === BigInt(1)
      ? -Math.floor(Number(value) / 2) - 1
      : Math.floor(Number(value) / 2);
  }

  private _fromInts(hi: bigint, low: bigint): bigint {
    return low + (hi << BigInt(32));
  }

  private _readInt(): bigint {
    let lo: bigint = BigInt(0);
    let hi: bigint = BigInt(0);

    // Read low 28 bits.
    for (let i = 0; i < 4; i++) {
      const byte = BigInt(this._readByte());
      lo |= (byte & BigInt(0x7f)) << (BigInt(i) * BigInt(7));
      if ((byte & BigInt(0x80)) === BigInt(0)) return this._fromInts(hi, lo);
    }

    // Read middle 7 bits: 4 low belong to low part above,
    // 3 remaining belong to hi.
    let byte = BigInt(this._readByte());
    lo |= (byte & BigInt(0xf)) << BigInt(28);
    hi = (byte >> BigInt(4)) & BigInt(0x7);
    if ((byte & BigInt(0x80)) === BigInt(0)) {
      return this._fromInts(hi, lo);
    }

    // Read remaining bits of hi.
    for (let i = 0; i < 5; i++) {
      byte = BigInt(this._readByte());
      hi |= (byte & BigInt(0x7f)) << (BigInt(i) * BigInt(7) + BigInt(3));
      if ((byte & BigInt(0x80)) === BigInt(0)) return this._fromInts(hi, lo);
    }
    return BigInt(-1);
  }
}
