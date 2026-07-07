package classes;

import java.util.Arrays;
import java.util.Objects;

public class Blob {
	private byte[] arr;
	
	public Blob(byte[] arr) {
		this.arr = arr;
	}

	public byte[] bytes() {
		return arr;
	}

	public long compareTo(Blob _old) {
		if (Objects.equals(_old, this)) {
			return 0;
		}
		return Arrays.compare(arr, _old.arr);
	}

	public long getSize() {
		return arr.length;
	}
}
