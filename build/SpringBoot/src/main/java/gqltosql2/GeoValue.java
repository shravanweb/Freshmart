package gqltosql2;

import d3e.core.Geolocation;

public class GeoValue implements IValue {

	private String field;
	private int index;

	public GeoValue(String field, int index) {
		this.field = field;
		this.index = index;
	}

	@Override
	public Object read(Object[] row, OutObject obj) throws Exception {
		Object val = row[index];
		Geolocation loc;
		if (val == null) {
			loc = null;
		} else {
			String[] split = val.toString().split(",");
			loc = new Geolocation(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
		}
		obj.add(field, loc);
		return loc;
	}

	@Override
	public String toString() {
		return field;
	}
}
