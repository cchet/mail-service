package at.herzog.mailservice.web.application.config.rest;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import at.herzog.mailservice.web.application.api.ModuleStartupObserver;
import at.herzog.mailservice.web.application.event.ContainerStartupEvent;
import at.herzog.mailservice.web.application.rest.datasource.api.AbstractAttachmentDataSource;

@ApplicationScoped
public class ConfigurationObserver implements Serializable {

	private static final long serialVersionUID = 1227348147550238915L;

	@Inject
	@Any
	private Instance<ModuleStartupObserver> moduleStartupObservers;

	@Inject
	private AttachmentDataSourceRegistry attachmentDataSourceRegsitry;

	public void observeContainerStartup(@Observes ContainerStartupEvent event) {
		// TODO: Prepare before loading modules

		// Startup modules
		startupModules();
	}

	private void startupModules() {
		// Here we could perform ordering
		final Iterator<ModuleStartupObserver> iterator = moduleStartupObservers.iterator();
		while (iterator.hasNext()) {
			final ModuleStartupObserver observer = iterator.next();
			startupModule(observer);
		}
	}

	private void startupModule(final ModuleStartupObserver observer) {
		// Register attachment data sources
		if (!observer.getAttachmentDataSources().isEmpty()) {
			for (Entry<String, Class<? extends AbstractAttachmentDataSource>> dataSourceEntry : observer
					.getAttachmentDataSources().entrySet()) {
				attachmentDataSourceRegsitry.registerDataSource(dataSourceEntry.getKey(), dataSourceEntry.getValue());
			}
		}
	}
}
