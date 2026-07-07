package gqltosql2;

public class SimpleValue implements IValue {

	protected String field;
	private int index;
	private boolean parent;

	public SimpleValue(String field, int index) {
		this.field = field;
		this.index = index;
		this.parent = field.equals("_parent");
	}

	@Override
	public Object read(Object[] row, OutObject obj) throws Exception {
		Object val = row[index];
		if(parent) {
			obj.getParents().add((long) val);
		} else {
			obj.add(field, val);
		}
		return val;
	}

	@Override
	public String toString() {
		return field;
	}
}
