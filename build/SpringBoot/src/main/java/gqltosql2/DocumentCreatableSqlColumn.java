package gqltosql2;

import gqltosql.schema.DField;
import gqltosql.schema.DModel;
import store.DatabaseObject;

public class DocumentCreatableSqlColumn extends DocumentSqlColumn {
	private DModel<?> type;
	
	public DocumentCreatableSqlColumn(DModel<?> type, Field field, DField<?, ?> df, String column) {
		super(field, df, column);
		this.type = type;
	}
	
	@Override
	public String getFieldName() {
		return column;
	}
	
	@Override
	protected DModel<?> getType() {
		return type;
	}
	
	@Override
	protected void addValueToOut(OutObject o, Object value) {
		if (value instanceof OutObject) {
			OutObject obj = (OutObject) value;
			obj.getFields().forEach((k, v) -> {
				o.add(k, v);
			});
			return;
		}
		super.addValueToOut(o, value);
	}
	
	@Override
	protected void populateDocInObj(Object obj, String doc) {
		if (obj instanceof DatabaseObject) {
			((DatabaseObject) obj)._setDoc(doc);
		}
	}
}
