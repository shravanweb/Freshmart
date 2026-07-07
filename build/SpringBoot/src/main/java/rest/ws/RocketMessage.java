package rest.ws;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.web.socket.BinaryMessage;

import classes.Blob;
import d3e.core.DFile;
import d3e.core.Geolocation;
import gqltosql.schema.DField;

public class RocketMessage {
	private static final int MAX_SIZE = 1024;
	private ByteBuffer in;
	private RocketSender sender;
	private ByteBuffer out;
	private int msgId = 0;
	private boolean system;
	private int reqId;
	public long time;

	public RocketMessage(byte[] in, RocketSender sender, boolean system) {
		this.sender = sender;
		this.in = ByteBuffer.wrap(in);
		this.out = ByteBuffer.allocate(MAX_SIZE);
		this.system = system;
	}

	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	public int getReqId() {
		return reqId;
	}

	public int readByte() {
		int b = in.get();
		// Log.info("r byte: " + b);
		return b;
	}

	public String readString() {
		int size = readInt();
		if (size == -1) {
			// Log.info("r str: null");
			return null;
		}
		byte[] dst = new byte[size];
		in.get(dst);
		String s = new String(dst, StandardCharsets.UTF_8);
		// Log.info("r str: " + s);
		return s;
	}

	public List<String> readStringList() {
		int size = readInt();
		// Log.info("r str list: " + size);
		List<String> res = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			res.add(readString());
		}
		return res;
	}

	public List<Integer> readIntegerList() {
		int size = readInt();
		// D3ELogger.info("r int list: " + size);
		List<Integer> res = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			res.add(readInt());
		}
		return res;
	}

	public boolean readBoolean() {
		boolean b = in.get() == 1;
		// Log.info("r bool: " + b);
		return b;
	}

	public long readLong() {
		long l = _decodeZigZag64(readVarLong());
		// Log.info("r long: " + l);
		return l;
	}

	public int readInt() {
		int i = (int) readLong();
		return i;
	}

	private long _decodeZigZag64(long value) {
		return ((value & 1) == 1 ? -(value >> 1) - 1 : (value >> 1));
	}

	private long _encodeZigZag64(long value) {
		return (value << 1) ^ (value >> 63);
	}

	private long readVarLong() {
		return readVarint64SlowPath();
	}

	private long readVarint64SlowPath() {
		long result = 0;
		for (int shift = 0; shift < 64; shift += 7) {
			final byte b = in.get();
			result |= (long) (b & 0x7F) << shift;
			if ((b & 0x80) == 0) {
				return result;
			}
		}
		throw new RuntimeException("Malformed int");
	}

	public double readDouble() {
		byte[] bytes = new byte[8];
		in.get(bytes);
		double d = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getDouble();
		// Log.info("r double: " + d);
		return d;
	}

	private long _fromInts(long hi, long lo) {
		return (hi << 32) | lo;
	}

	public void writeByte(int val) {
		// Log.info("w byte: " + val);
		put((byte) val);
	}

	public void writeInt(int val) {
		writeLong(val);
	}

	/**
	 * Not using the version in CodedOutputStream due to the fact that benchmarks
	 * have shown a performance improvement when returning a byte (rather than an
	 * int).
	 */
	private static byte computeUInt64SizeNoTag(long value) {
		// handle two popular special cases up front ...
		if ((value & (~0L << 7)) == 0L) {
			// Byte 1
			return 1;
		}
		if (value < 0L) {
			// Byte 10
			return 10;
		}
		// ... leaving us with 8 remaining, which we can divide and conquer
		byte n = 2;
		if ((value & (~0L << 35)) != 0L) {
			// Byte 6-9
			n += 4; // + (value >>> 63);
			value >>>= 28;
		}
		if ((value & (~0L << 21)) != 0L) {
			// Byte 4-5 or 8-9
			n += 2;
			value >>>= 14;
		}
		if ((value & (~0L << 14)) != 0L) {
			// Byte 3 or 7
			n += 1;
		}
		return n;
	}

	void writeVarLong(long value) {
		switch (computeUInt64SizeNoTag(value)) {
		case 1:
			writeVarint64OneByte(value);
			break;
		case 2:
			writeVarint64TwoBytes(value);
			break;
		case 3:
			writeVarint64ThreeBytes(value);
			break;
		case 4:
			writeVarint64FourBytes(value);
			break;
		case 5:
			writeVarint64FiveBytes(value);
			break;
		case 6:
			writeVarint64SixBytes(value);
			break;
		case 7:
			writeVarint64SevenBytes(value);
			break;
		case 8:
			writeVarint64EightBytes(value);
			break;
		case 9:
			writeVarint64NineBytes(value);
			break;
		case 10:
			writeVarint64TenBytes(value);
			break;
		}
	}

	private void writeVarint64OneByte(long value) {
		put((byte) value);
	}

	private void writeVarint64TwoBytes(long value) {
		put((byte) (((int) value & 0x7F) | 0x80));
		put((byte) (value >>> 7));
	}

	private void writeVarint64ThreeBytes(long value) {
		put((byte) ((value & 0x7F) | 0x80));
		put((byte) (((value >>> 7) & 0x7F) | 0x80));
		put((byte) (((int) value) >>> 14));
	}

	private void writeVarint64FourBytes(long value) {
		put((byte) ((value & 0x7F) | 0x80));
		put((byte) (((value >>> 7) & 0x7F) | 0x80));
		put((byte) (((value >>> 14) & 0x7F) | 0x80));
		put((byte) (value >>> 21));
	}

	private void writeVarint64FiveBytes(long value) {
		put((byte) ((value & 0x7F) | 0x80));
		put((byte) (((value >>> 7) & 0x7F) | 0x80));
		put((byte) (((value >>> 14) & 0x7F) | 0x80));
		put((byte) (((value >>> 21) & 0x7F) | 0x80));
		put((byte) (value >>> 28));
	}

	private void writeVarint64SixBytes(long value) {
		put((byte) ((value & 0x7F) | 0x80));
		put((byte) (((value >>> 7) & 0x7F) | 0x80));
		put((byte) (((value >>> 14) & 0x7F) | 0x80));
		put((byte) (((value >>> 21) & 0x7F) | 0x80));
		put((byte) (((value >>> 28) & 0x7F) | 0x80));
		put((byte) (value >>> 35));
	}

	private void writeVarint64SevenBytes(long value) {
		put((byte) ((value & 0x7F) | 0x80));
		put((byte) (((value >>> 7) & 0x7F) | 0x80));
		put((byte) (((value >>> 14) & 0x7F) | 0x80));
		put((byte) (((value >>> 21) & 0x7F) | 0x80));
		put((byte) (((value >>> 28) & 0x7F) | 0x80));
		put((byte) (((value >>> 35) & 0x7F) | 0x80));
		put((byte) (value >>> 42));
	}

	private void writeVarint64EightBytes(long value) {
		put((byte) ((value & 0x7F) | 0x80));
		put((byte) (((value >>> 7) & 0x7F) | 0x80));
		put((byte) (((value >>> 14) & 0x7F) | 0x80));
		put((byte) (((value >>> 21) & 0x7F) | 0x80));
		put((byte) (((value >>> 28) & 0x7F) | 0x80));
		put((byte) (((value >>> 35) & 0x7F) | 0x80));
		put((byte) (((value >>> 42) & 0x7F) | 0x80));
		put((byte) (value >>> 49));
	}

	private void writeVarint64NineBytes(long value) {
		put((byte) ((value & 0x7F) | 0x80));
		put((byte) (((value >>> 7) & 0x7F) | 0x80));
		put((byte) (((value >>> 14) & 0x7F) | 0x80));
		put((byte) (((value >>> 21) & 0x7F) | 0x80));
		put((byte) (((value >>> 28) & 0x7F) | 0x80));
		put((byte) (((value >>> 35) & 0x7F) | 0x80));
		put((byte) (((value >>> 42) & 0x7F) | 0x80));
		put((byte) (((value >>> 49) & 0x7F) | 0x80));
		put((byte) (value >>> 56));
	}

	private void writeVarint64TenBytes(long value) {
		put((byte) ((value & 0x7F) | 0x80));
		put((byte) (((value >>> 7) & 0x7F) | 0x80));
		put((byte) (((value >>> 14) & 0x7F) | 0x80));
		put((byte) (((value >>> 21) & 0x7F) | 0x80));
		put((byte) (((value >>> 28) & 0x7F) | 0x80));
		put((byte) (((value >>> 35) & 0x7F) | 0x80));
		put((byte) (((value >>> 42) & 0x7F) | 0x80));
		put((byte) (((value >>> 49) & 0x7F) | 0x80));
		put((byte) (((value >>> 56) & 0x7F) | 0x80));
		put((byte) (value >>> 63));
	}

	public void writeStringList(List<String> list) {
		// Log.info("w str list: " + list.size());
		writeList(list, this::writeString);
	}

	public void writeString(String val) {
		//// Log.info("w str: " + val);
		if (val == null) {
			writeInt(-1);
			return;
		}
		byte[] bytes = val.getBytes(StandardCharsets.UTF_8);
		writeInt(bytes.length);
		for (byte b : bytes) {
			put(b);
		}
	}

	public void writeLong(long val) {
		// Log.info("w long: " + val);
		writeVarLong(_encodeZigZag64(val));
	}

	public void writeLongList(List<Long> val) {
		writeList(val, this::writeLong);
	}

	public void writeIntegerList(List<Integer> ints) {
		// Log.info("w int list: " + ints.size());
		writeList(ints, this::writeInt);
	}
	
	public void writeGeolocation(Geolocation loc) {
		writeInt(0);
		writeDouble(loc.getLatitude());
		writeDouble(loc.getLongitude());
	}

	public void writeBlob(Blob blob) {
		if (blob == null) {
			writeInt(-1);
			return;
		}
		writeInt(0); // Write the flag first, since that is the way we read it on client side
		byte[] arr = blob.bytes();
		writeInt(arr.length);
		for (byte b : arr) {
			writeByte(b);
		}
	}

	public void writeBlobList(List<Blob> list) {
		writeList(list, this::writeBlob);
	}

	private <T> void writeList(List<T> list, Consumer<T> writeSingle) {
		if (list == null) {
			writeInt(-1);
			return;
		}
		writeInt(list.size());
		for (var x : list) {
			writeSingle.accept(x);
		}
	}

	private void put(byte b) {
		if (out.position() < out.capacity()) {
			out.put(b);
		} else {
			flush(false);
			put(b);
		}
	}

	public void flush() {
		flush(true);
	}

	private void flush(boolean isLast) {
		if (out.position() == 0) {
			// There is nothing to flush
			return;
		}
		try {
			byte[] data = new byte[out.position()];
			out.rewind();
			out.get(data, 0, data.length);
			out.rewind();
			sender.sendMessage(new BinaryMessage(data, isLast), msgId, system);
			if (isLast) {
				msgId = 0;
			} else {
				msgId++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void forward() {
		int limit = in.limit();
		in.rewind();
		msgId = 0;
		while (limit > MAX_SIZE) {
			try {
				byte[] data = new byte[MAX_SIZE];
				in.get(data, 0, data.length);
				sender.sendMessage(new BinaryMessage(data, false), msgId, system);
				msgId++;
				limit -= MAX_SIZE;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			byte[] data = new byte[limit];
			in.get(data, 0, data.length);
			sender.sendMessage(new BinaryMessage(data, true), msgId, system);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeNull() {
		// Log.info("w null: ");
		writeInt(-1);
	}

	public void writeBoolean(boolean val) {
		// Log.info("w bool: " + val);
		put((byte) (val ? 1 : 0));
	}

	public void writeBooleanList(List<Boolean> list) {
		writeList(list, this::writeBoolean);
	}

	public void writeDouble(double val) {
		// Log.info("w double: " + val);
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).putDouble(val);
		for (byte b : bytes) {
			put(b);
		}
	}

	public void writeDoubleList(List<Double> list) {
		writeList(list, this::writeDouble);
	}

	public void writeDateTime(LocalDateTime val) {
		// Log.info("w date time: " + val);
		if (val == null) {
			writeInt(-1);
			return;
		}
		writeString(val.toString());
	}

	public void writeDFile(DFile value) {
		if (value == null) {
			writeString(null);
			return;
		}
		writeString(value.getId());
		writeString(value.getName());
		writeLong(value.getSize());
		writeString(value.getMimeType());
	}

	public void writePrimitiveField(Object val, DField field, Template template) {
		// Log.info("w " + field.getPrimitiveType() + " : "+ field.getName());
		if (val == null) {
			writeNull();
			return;
		}
		switch (field.getPrimitiveType()) {
		case Boolean:
			writeBoolean((boolean) val);
			break;
		case DFile:
			writeDFile((DFile) val);
			break;
		case Date:
			if (val instanceof LocalDate) {
				LocalDate date = (LocalDate) val;
				writeInt(date.getYear());
				writeInt(date.getMonthValue());
				writeInt(date.getDayOfMonth());
			} else if (val instanceof Date) {
				Date date = (Date) val;
				writeInt(date.getYear() + 1900);
				writeInt(date.getMonth() + 1);
				writeInt(date.getDate());
			} else {
				writeInt(0);
				writeInt(0);
				writeInt(0);
			}
			break;
		case DateTime:
			if (val instanceof LocalDateTime) {
				LocalDateTime date = (LocalDateTime) val;
				writeLong(Timestamp.valueOf(date).getTime());
			} else if (val instanceof Timestamp) {
				writeLong(((Timestamp) val).getTime());
			} else {
				writeLong(0);
			}
			break;
		case Double:
			writeDouble((double) val);
			break;
		case Duration:
			if (val instanceof Duration) {
				Duration d = (Duration) val;
				long dur = d.toMillis();
				writeLong(dur);
			} else {
				writeLong((Long) val);
			}
			break;
		case Enum:
			int typeIdx = template.toClientTypeIdx(field.getReference().getIndex());
			TemplateType et = template.getType(typeIdx);
			if (val instanceof String) {
				DField<?, ?>[] fields = et.getFields();
				int fid = 0;
				String name = (String) val;
				for (DField f : fields) {
					if (f.getName().equals(name)) {
						writeInt(fid);
						return;
					}
					fid++;
				}
				writeInt(0);
			} else {
				Enum<?> enm = (Enum<?>) val;
				int cfid = et.toClientIdx(enm.ordinal());
				writeInt(cfid);
			}
			break;
		case Integer:
			writeLong((Long) val);
			break;
		case String:
			writeString((String) val);
			break;
		case Time:
			if (val instanceof LocalTime) {
				LocalTime time = (LocalTime) val;
				writeLong(time.toSecondOfDay() * 1000);
			} else if (val instanceof Time) {
				Time time = (Time) val;
				LocalTime lt = time.toLocalTime();
				writeLong(lt.toSecondOfDay() * 1000);
			} else {
				writeLong(0);
			}
			break;
		case Blob:
			this.writeBlob((Blob) val);
			break;
		case Geolocation:
			this.writeGeolocation((Geolocation)val);
			break;
		default:
			throw new RuntimeException("Unsupported type. " + val.getClass());
		}
	}

	public Object readPrimitive(DField field, Template template) {
		switch (field.getPrimitiveType()) {
		case Boolean:
			return readBoolean();
		case DFile:
			return readDFile();
		case Date:
			return readDate();
		case DateTime:
			return readDateTime();
		case Double:
			return readDouble();
		case Duration:
			long mill = readLong();
			return Duration.ofMillis(mill);
		case Enum:
			return readEnum(field.getReference().getIndex(), template);
		case Integer:
			return readLong();
		case String:
			return readString();
		case Time:
			return readTime();
		case Blob:
			return readBlob();
		case Geolocation:
			return readGeolocation();
		default:
			throw new RuntimeException("Unsupported type. " + field.getPrimitiveType());
		}
	}

	public LocalTime readTime() {
		long lng = readLong();
		if (lng == -1) {
			return null;
		}
		return LocalTime.ofSecondOfDay(lng / 1000);
	}

	public LocalDateTime readDateTime() {
		long lng = readLong();
		if (lng == -1) {
			return null;
		}
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(lng), ZoneOffset.UTC);
	}

	public Object readEnum(int enumType, Template template) {
		int field = readInt();
		int typeIdx = template.toClientTypeIdx(enumType);
		TemplateType et = template.getType(typeIdx);
		DField<?, ?>[] fields = et.getFields();
		return fields[field].getValue(null);
	}

	public LocalDate readDate() {
		int year = readInt();
		if (year == -1) {
			return null;
		}
		int month = readInt();
		int dayOfMonth = readInt();
		return LocalDate.of(year, month, dayOfMonth);
	}

	public DFile readDFile() {
		String str = readString();
		if (str == null) {
			return null;
		}
		DFile file = new DFile();
		file.setId(str);
		file.setName(readString());
		file.setSize(readLong());
		file.setMimeType(readString());
		return file;
	}
	
	public Geolocation readGeolocation() {
		int val = readInt();
		if(val == -1) {
			return null;
		}
		double lat = readDouble();
		double lng = readDouble();
		return new Geolocation(lat, lng);
	}

	public Blob readBlob() {
		int sz = readInt();
		if (sz == -1) {
			return null;
		}
		byte[] arr = new byte[sz];
		for (int i = 0; i < sz; i++) {
			arr[i] = (byte) readByte();
		}
		return new Blob(arr);
	}

	public <T extends Enum<?>> void writeEnum(T enm) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Unsupported enum: " + enm.getClass());
	}

	public void writeMessage(RocketMessage msg) {
		// TODO Auto-generated method stub

	}

	public void discard() {
		out.rewind();
	}

	public void rewind() {
		int limit = in.limit();
		out = in;
		out.position(limit);
	}

	public void setSender(RocketSender sender) {
		this.sender = sender;
	}

	public void writeLocalDate(LocalDate date) {
		if (date == null) {
			writeInt(-1);
		} else {
			writeInt(date.getYear());
			writeInt(date.getMonthValue());
			writeInt(date.getDayOfMonth());
		}
	}

	public void writeLocalDateList(List<LocalDate> list) {
		writeList(list, this::writeLocalDate);
	}

	public void writeLocalDateTime(LocalDateTime result) {
		writeDateTime(result);
	}

	public void writeLocalDateTimeList(List<LocalDateTime> result) {
		writeList(result, this::writeLocalDateTime);
	}

	public void writeLocalTime(LocalTime result) {
		if (result == null) {
			writeLong(-1);
		} else {
			// Converting to milliseconds
			writeLong(result.toNanoOfDay() / (1000 * 1000));
		}
	}

	public void writeLocalTimeList(List<LocalTime> result) {
		writeList(result, this::writeLocalTime);
	}

	public void writeDuration(Duration result) {
		if (result == null) {
			writeLong(-1);
			return;
		}
		long nanos = result.toNanos();
		long micros = nanos / 1000;
		writeLong(micros);
	}

	public void writeDurationList(List<Duration> result) {
		writeList(result, this::writeDuration);
	}

	public void writeDFileList(List<DFile> result) {
		writeList(result, this::writeDFile);
	}

	public void writeGeolocationList(List<Geolocation> result) {
        writeList(result, this::writeGeolocation);
    }
}
