package at.herzog.web.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import at.herzog.web.mail.webservice.MailService;

// Seems resteasy does not use this annotation
//@ApplicationPath("rest")
public class RestApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> services = new HashSet<Class<?>>();
		services.add(MailService.class);
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
