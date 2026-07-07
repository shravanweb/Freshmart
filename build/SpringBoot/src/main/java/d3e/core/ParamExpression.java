package d3e.core;

public class ParamExpression implements Criterion {

	private String param;

	protected ParamExpression(String param) {
		this.param = param;
	}

	@Override
	public String toSql(Criteria criteria) {
		return "?";
	}

	@Override
	public String select(Criteria criteria) {
		return toSql(criteria);
	}
}
