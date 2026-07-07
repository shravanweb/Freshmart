package d3e.core;

import java.util.List;

public class NotInCriterion extends SimpleExpression {

	private List<?> list;

    private Boolean inOrNotIn = true;

	public NotInCriterion(PropertyExpression prop, List<?> list) {
		super(prop);
		this.list = list;
	}

	@Override
	public String toSql(Criteria criteria) {
		List<String> args = ListExt.map(list, a -> createArgument(a));
        if(inOrNotIn) {
            return prop.toSql(criteria) + " in (" + ListExt.join(args, ",") + ")";
        } else {
            return prop.toSql(criteria) + " not in (" + ListExt.join(args, ",") + ")";
        }
	}

	@Override
	public String select(Criteria criteria) {
		return toSql(criteria);
	}
}
