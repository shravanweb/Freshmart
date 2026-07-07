package d3e.core;

public class StartsWithCriterion extends SimpleExpression {

	private Object value;

    private Boolean startOrEnd = true;

	public StartsWithCriterion(Criterion prop, Object value, Boolean startOrEnd) {
		super(prop);
	
        this.value = value;

        this.startOrEnd = startOrEnd;
	}

	@Override
	public String toSql(Criteria criteria) {
        if(startOrEnd) {
            return prop.toSql(criteria) +" like (" +"'" + value + "%'" + ")";
        } else {
            return prop.toSql(criteria) + " like (" +"'%" + value + "'" + ")";
        }
	}

	@Override
	public String select(Criteria criteria) {
		return toSql(criteria);
	}
}
