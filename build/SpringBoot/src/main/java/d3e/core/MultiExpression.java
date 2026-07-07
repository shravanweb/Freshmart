package d3e.core;

import java.util.List;

public class MultiExpression implements Criterion {

	private List<Criterion> multi;
	private boolean all;

	public MultiExpression(List<Criterion> multi, boolean all) {
		this.multi = multi;
		this.all = all;
	}

	@Override
	public String toSql(Criteria criteria) {
		String join = all ? " and " : " or ";
		return  "(" + ListExt.join(ListExt.map(multi, m -> m.toSql(criteria)), join) + ")";
	}

	@Override
	public String select(Criteria criteria) {
		return toSql(criteria);
	}
}
