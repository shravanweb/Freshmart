package gqltosql;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class QueryTypeReader {
	private int id;
	private String type;
	private List<IValue> fields = new ArrayList<>();

	public QueryTypeReader(String type) {
		this.type = type;
		this.id = -1;
	}

	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public void read(Object[] row, SqlRow obj) throws Exception {
		if (id != -1) {
			Object idVal = row[id];
			if (idVal instanceof BigInteger) {
				obj.put("id", ((BigInteger) idVal).longValue());
			} else if (idVal instanceof Long) {
				obj.put("id", idVal);
			} else if (idVal instanceof Integer) {
				obj.put("id", ((Integer) idVal).longValue());
			} else if (idVal instanceof Number) {
				obj.put("id", ((Number) idVal).longValue());
			} else if (idVal instanceof String) {
				obj.put("id", idVal);
			}
		}
		obj.addType(type);
		for (IValue f : fields) {
			f.read(row, obj);
		}
	}

	public void add(String field, int index) {
		if (field.equals("id")) {
			id = index;
			return;
		}
		fields.add(new SimpleValue(field, index));
	}

	public QueryReader addRef(String field, int index) {
		RefValue rv = new RefValue(field, index);
		fields.add(rv);
		return rv.getReader();
	}
	

	public QueryReader addEmbedded(String field) {
		EmbeddedValue rv = new EmbeddedValue(field);
		fields.add(rv);
		return rv.getReader();
	}

	@Override
	public String toString() {
		return type;
	}
}
