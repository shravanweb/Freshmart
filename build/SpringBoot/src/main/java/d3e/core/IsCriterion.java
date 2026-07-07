package d3e.core;

public class IsCriterion extends SimpleExpression {

	private Object value;

    private Boolean isOrNot = true;

	public IsCriterion(Criterion prop, Object value, Boolean isOrNot) {
		super(prop);
		this.value = value;
        this.isOrNot = isOrNot;
	}

	@Override
	public String toSql(Criteria criteria) {
        if(isOrNot) {
            return prop.toSql(criteria) + " is " + createArgument(value);
        } else {
            return prop.toSql(criteria) + " is not " + createArgument(value)  ;
        }
	}

	@Override
	public String select(Criteria criteria) {
		return toSql(criteria);
	}
}
