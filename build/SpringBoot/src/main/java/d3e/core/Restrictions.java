package d3e.core;

import java.util.List;

public class Restrictions {

	public static SimpleExpression eq(PropertyExpression prop, Object val) {
		return new BinaryCriterion("=", prop, val);
	}

	public static SimpleExpression notEq(PropertyExpression prop, Object val) {
		return new BinaryCriterion("!=", prop, val);
	}

	public static SimpleExpression lt(PropertyExpression prop, Object val) {
		return new BinaryCriterion("<", prop, val);
	}

	public static SimpleExpression le(PropertyExpression prop, Object val) {
		return new BinaryCriterion("<=", prop, val);
	}

	public static SimpleExpression gt(PropertyExpression prop, Object val) {
		return new BinaryCriterion(">", prop, val);
	}

	public static SimpleExpression ge(PropertyExpression prop, Object val) {
		return new BinaryCriterion(">=", prop, val);
	}

	public static Criterion between(PropertyExpression prop, Object lo, Object hi) {
		return new BetweenCriterion(prop, lo, hi);
	}

	public static Criterion in(PropertyExpression name, List<?> list) {
		return new InCriterion(name, list);
	}

	public static Criterion notIn(PropertyExpression name, List<?> list) {
		return new NotInCriterion(name, list);
	}

	public static Criterion contains(PropertyExpression name, String value) {
		return new ContainsCriterion(name, value, true);
	}

	public static Criterion notContains(PropertyExpression name, String value) {
		return new ContainsCriterion(name, value, false);
	}

	public static Criterion startsWith(PropertyExpression name, String value) {
		return new StartsWithCriterion(name, value,true);
	}

	public static Criterion endsWith(PropertyExpression name, String value) {
		return new StartsWithCriterion(name, value, false);
	}



	public static Criterion is(PropertyExpression name, Object value) {
		return new IsCriterion(name,value, true);
	}

	public static Criterion isNot(PropertyExpression name, Object value) {
		return new IsCriterion(name,value, false);
	}
	public static Criterion now() {
		return new DateNowExpression();
	}
}
