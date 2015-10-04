package at.herzog.mailservice.web.application.servlet.listener;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.core.config.Configurator;

import at.herzog.mailservice.web.application.event.ContainerShutdownEvent;
import at.herzog.mailservice.web.application.event.ContainerStartupEvent;

public class ServletContextStartedListener implements ServletContextListener {

	@Inject
	private Event<ContainerStartupEvent> startupEvt;
	@Inject
	private Event<ContainerShutdownEvent> shutdownEvt;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO: Logging should be handled first in own listener
		Configurator.initialize("mailservice-config", "log4j.xml");
		// TODO: Prepare Context for fired event
		startupEvt.fire(new ContainerStartupEvent());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO: Prepare Context for fired event
		shutdownEvt.fire(new ContainerShutdownEvent());
	}

}
