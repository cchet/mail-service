package at.herzog.mailservice.web.application.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This filter logs each request for the resteasy servlet asynchronously.
 * 
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 *
 */
@WebFilter(
		filterName = "AsyncRestServletLoggingFilter",
		displayName = "AsyncRestServletLoggingFilter",
		servletNames = { "ResteasyServlet" },
		asyncSupported = true)
public class AsyncRestServletLoggingFilter implements Filter {

	private static final Logger logger = LogManager.getLogger(AsyncRestServletLoggingFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// TODO: Here we need to find an efficient way to log the incoming
		// requests especially because of the contained json.
		logger.error("Hello, request! You got logged by asynchronously by log4j");

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
