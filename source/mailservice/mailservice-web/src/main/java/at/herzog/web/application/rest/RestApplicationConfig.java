package at.herzog.web.application.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import at.herzog.web.application.rest.filter.InboundLoggingRestFilter;
import at.herzog.web.application.rest.filter.OutboundLoggingRestFilter;
import at.herzog.web.application.rest.filter.SecurityRestFilter;
import at.herzog.web.mail.webservice.MailService;

// Seems resteasy does not use this annotation
@ApplicationPath("")
public class RestApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> services = new HashSet<Class<?>>();
		// WebServices
		services.add(MailService.class);
		// Filters
		services.add(InboundLoggingRestFilter.class);
		services.add(SecurityRestFilter.class);
		services.add(OutboundLoggingRestFilter.class);

		return services;
	}

	@Override
	public Set<Object> getSingletons() {
		final Set<Object> singletonSet = new HashSet<>();
		// We should provide an own implementation to handle the error properly.
		singletonSet.add(new JacksonJsonProvider());
		return singletonSet;
	}
}
