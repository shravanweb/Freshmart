package d3e.core;

import classes.ReportAggregateType;

public class AggregateExpression implements Criterion {

	private ReportAggregateType agg;
	private PropertyExpression field;

	public AggregateExpression(ReportAggregateType agg, PropertyExpression field) {
		this.agg = agg;
		this.field = field;
	}

	@Override
	public String toSql(Criteria criteria) {
		String sql = "agg";
		switch(agg) {
		case Array:
			break;
		case Average:
			sql = "avg";
			break;
		case Count:
			sql = "count";
			break;
		case Max:
			sql = "max";
			break;
		case Min:
			sql = "min";
			break;
		case None:
			break;
		case Percent:
			break;
		case Sum:
			sql = "sum";
			break;
		default:
			break;
		 
		}
		return sql + '(' + (field == null ? '*' : field.select(criteria)) + ')';
	}
	
	@Override
	public String select(Criteria criteria) {
		return toSql(criteria);
	}
}
