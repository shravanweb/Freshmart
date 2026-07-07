package gqltosql.schema;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class DDocField<T, R> extends DRefField<T, R> {
	private String idColumn;
	
	public DDocField(DModel<T> decl, int index, String name, String column, String idColumn, boolean child, DModel<?> ref,
			Function<T, R> getter, BiConsumer<T, R> setter) {
		super(decl, index, name, column, child, ref, getter, setter);
		this.idColumn = idColumn;
	}
	
	public String getIdColumn() {
		return idColumn;
	}
}
