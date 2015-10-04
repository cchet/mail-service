package at.herzog.mailservice.web.application.config.rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import at.herzog.mailservice.web.application.api.ModuleStartupObserver;
import at.herzog.mailservice.web.application.rest.datasource.Base64DataSource;
import at.herzog.mailservice.web.application.rest.datasource.api.AbstractAttachmentDataSource;

@ApplicationScoped
public class SystemConfiguration implements ModuleStartupObserver {

	private static final long serialVersionUID = -6723255808228913763L;

	private final Map<String, Class<? extends AbstractAttachmentDataSource>> attachmentDataSources = new HashMap<>();

	public SystemConfiguration() {
		super();
	}

	@PostConstruct
	public void postConstruct() {
		// Registering attachment data sources
		attachmentDataSources.put(Base64DataSource.TYPE, Base64DataSource.class);
	}

	@PreDestroy
	public void preDestroy() {
	}

	@Override
	public Map<String, Class<? extends AbstractAttachmentDataSource>> getAttachmentDataSources() {
		return attachmentDataSources;
	}
}
