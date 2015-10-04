package at.herzog.mailservice.web.application.servlet.filter;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * This filter logs each request for the resteasy servlet asynchronously.
 * 
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 *
 */
@WebFilter(
		filterName = "AsyncRestServletLoggingFilter",
		displayName = "AsyncRestServletLoggingFilter",
		servletNames = { "Resteasy" },
		asyncSupported = true,
		urlPatterns = "/*")
public class AsyncRestServletLoggingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final Logger logger = LogManager.getLogger(AsyncRestServletLoggingFilter.class);
		logger.error("Hello, request! You got logged by log4j");

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
