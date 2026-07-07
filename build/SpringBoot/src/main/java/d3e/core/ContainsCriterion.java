package d3e.core;

public class ContainsCriterion extends SimpleExpression {

	private Object value;

    private Boolean containsOrNot = true;

	public ContainsCriterion(Criterion prop, Object value, Boolean containsOrNot) {
		super(prop);
		this.value = value;
        this.containsOrNot = containsOrNot;
	}

	@Override
	public String toSql(Criteria criteria) {
        if(containsOrNot) {
            return prop.toSql(criteria) + " contains (" + createArgument(value) + ")";
        } else {
            return prop.toSql(criteria) + " not contains (" + createArgument(value) + ")";
        }
	}

	@Override
	public String select(Criteria criteria) {
		return toSql(criteria);
	}
}
