package d3e.core;

public interface Criterion {

	String select(Criteria criteria);

	String toSql(Criteria criteria);
}
