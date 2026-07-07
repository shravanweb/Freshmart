package store;

import java.util.ArrayList;
import java.util.List;

import d3e.core.ListExt;

public class D3EQuery {
	List<D3EQuery> queries;
	String query;
	List<Object> args = ListExt.List();
	D3EQuery pre;
	D3EQuery next;
	private DatabaseObject obj;

	public D3EQuery() {
	}

	public static D3EQuery create() {
		D3EQuery query = new D3EQuery();
		query.queries = new ArrayList<>();
		query.queries.add(query);
		return query;
	}

	public DatabaseObject getObj() {
		return obj;
	}

	public void setObj(DatabaseObject obj) {
		this.obj = obj;
	}

	public D3EQuery prev() {
		D3EQuery q = new D3EQuery();
		q.queries = this.queries;
		D3EQuery p = this;
		while (p.pre != null) {
			p = p.pre;
		}
		p.pre = q;
		int idx = queries.indexOf(p);
		queries.add(idx, q);
		return q;
	}

	public D3EQuery next() {
		D3EQuery q = new D3EQuery();
		q.queries = this.queries;
		
		D3EQuery n = this;
		while (n.next != null) {
			n = n.next;
		}
		n.next = q;
		int idx = queries.indexOf(n);
		queries.add(idx + 1, q);
		return q;
	}

	public void setQuery(String q) {
		this.query = q;
	}

	public void setArgs(List<Object> args) {
		this.args = args;
	}
}
