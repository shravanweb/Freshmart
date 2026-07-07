package gqltosql.schema;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DDocCollField<T, R> extends DRefCollField<T, R> {

	private String docColumn;

	public DDocCollField(DModel<T> decl, int index, String name, String column, String docColumn, boolean child, String collTable,
			DModel<?> ref, Function<T, List<R>> getter, BiConsumer<T, List<R>> setter) {
		super(decl, index, name, column, child, collTable, ref, getter, setter);
		this.docColumn = docColumn;
	}

	public String getDocColumn() {
		return docColumn;
	}
}
