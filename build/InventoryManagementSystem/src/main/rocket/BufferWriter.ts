import {
  FLOAT64_MAX,
  FLOAT64_MIN,
  TWO_TO_20,
  TWO_TO_32,
  TWO_TO_52,
} from "../classes/core";

const Long = require("long");

class WriterHelper {
  low: number;
  high: number;

  constructor() {}

  splitInt64(value: number) {
    var sign = value < 0;
    value = Math.abs(value);

    var lowBits = value >>> 0;
    var highBits = Math.floor((value - lowBits) / TWO_TO_32);
    highBits = highBits >>> 0;

    if (sign) {
      highBits = ~highBits >>> 0;
      lowBits = ~lowBits >>> 0;
      lowBits += 1;
      if (lowBits > 0xffffffff) {
        lowBits = 0;
        highBits++;
        if (highBits > 0xffffffff) highBits = 0;
      }
    }

    this.low = lowBits;
    this.high = highBits;
  }

  splitFloat64(value: number) {
    var sign = value < 0 ? 1 : 0;
    value = sign ? -value : value;

    if (value === 0) {
      this.high = sign << 31;
      this.low = 0;
      return;
    }

    if (isNaN(value)) {
      this.high = 0x7ff80000;
      this.low = 0;
      return;
    }

    if (value === Infinity || value === -Infinity) {
      this.high = (sign << 31) | 0x7ff00000;
      this.low = 0;
      return;
    }

    var exponent = Math.floor(Math.log(value) / Math.LN2);
    var mantissa = value * Math.pow(2, -exponent);

    if (mantissa < 1) {
      exponent--;
      mantissa *= 2;
    }

    mantissa -= 1;
    mantissa *= TWO_TO_52;

    this.high =
      (sign << 31) | ((exponent + 1023) << 20) | (mantissa / TWO_TO_32);
    this.low = mantissa >>> 0;
  }
}

export default class BufferWriter {
  static encoder = new TextEncoder();
  private _buffer: number[] = [];

  public writeBool(value: boolean) {
    this._buffer.push(value ? 1 : 0);
  }

  public writeInt(value: number) {
    let helper = new WriterHelper();
    let zigzagged = this._encodeZigZag64(value);
    helper.splitInt64(zigzagged);

    let lowBits = helper.low;
    let highBits = helper.high;

    while (highBits > 0 || lowBits > 127) {
      this._buffer.push((lowBits & 0x7f) | 0x80);
      lowBits = ((lowBits >>> 7) | (highBits << 25)) >>> 0;
      highBits = highBits >>> 7;
    }
    this._buffer.push(lowBits);
  }

  public writeByte(value: number) {
    this._buffer.push(value);
  }

  public writeDouble(value: number) {
    let helper = new WriterHelper();
    helper.splitFloat64(value);
    this.writeUint32BigEndian(helper.high);
    this.writeUint32BigEndian(helper.low);
  }

  public writeString(value: string) {
    if (value == null) {
      this.writeInt(-1);
    } else if (value.length === 0) {
      this.writeInt(0);
    } else {
      this._writeString(value);
    }
  }

  public takeBytes(): Uint8Array {
    return new Uint8Array(this._buffer);
  }

  private _encodeZigZag64(value: number): number {
    let longValue = Long.fromNumber(value);
    return longValue.shiftLeft(1).xor(longValue.shiftRight(63));
  }

  private writeUint32(value: number) {
    this._buffer.push((value >>> 0) & 0xff);
    this._buffer.push((value >>> 8) & 0xff);
    this._buffer.push((value >>> 16) & 0xff);
    this._buffer.push((value >>> 24) & 0xff);
  }

  private writeUint32BigEndian(value: number) {
    this._buffer.push((value >>> 24) & 0xff);
    this._buffer.push((value >>> 16) & 0xff);
    this._buffer.push((value >>> 8) & 0xff);
    this._buffer.push((value >>> 0) & 0xff);
  }

  public _writeString(value: string) {
    let encode = BufferWriter.encoder.encode(value);
    this.writeInt(encode.length);
    this._buffer.push(...encode);
  }
}
