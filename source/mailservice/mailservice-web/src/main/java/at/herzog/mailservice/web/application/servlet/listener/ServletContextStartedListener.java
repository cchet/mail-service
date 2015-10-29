package at.herzog.mailservice.web.application.servlet.listener;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import at.herzog.cdi.api.event.ContainerShutdownEvent;
import at.herzog.cdi.api.event.ContainerStartupEvent;

public class ServletContextStartedListener implements ServletContextListener {

	@Inject
	private Event<ContainerStartupEvent> startupEvt;
	@Inject
	private Event<ContainerShutdownEvent> shutdownEvt;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO: Prepare Context for fired event
		startupEvt.fire(new ContainerStartupEvent());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO: Prepare Context for fired event
		shutdownEvt.fire(new ContainerShutdownEvent());
	}

}
