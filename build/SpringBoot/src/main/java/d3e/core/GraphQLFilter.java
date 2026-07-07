package d3e.core;

import org.springframework.transaction.TransactionException;

public class GraphQLFilter extends org.springframework.web.filter.OncePerRequestFilter {

	private TransactionWrapper wrapper;

	public GraphQLFilter(TransactionWrapper wrapper) {
		this.wrapper = wrapper;
	}

	@java.lang.Override
	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, java.io.IOException {
		try {
			wrapper.doInTransaction(() -> filterChain.doFilter(request, response));
		} catch (TransactionException e) {
		}
	}

	@Override
	protected boolean shouldNotFilterAsyncDispatch() {
		return false;
	}
}
