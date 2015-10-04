package at.herzog.mailservice.web.application.config.rest;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import at.herzog.mailservice.web.application.rest.datasource.api.AbstractAttachmentDataSource;

@ApplicationScoped
public class AttachmentDataSourceRegistry implements Serializable {

	private static final long serialVersionUID = -391222331438249522L;

	private final Map<String, Class<? extends AbstractAttachmentDataSource>> attachmentDataSources;

	public AttachmentDataSourceRegistry() {
		super();

		this.attachmentDataSources = new ConcurrentHashMap<>();
	}

	public void observeStartup() {

	}

	public boolean registerDataSource(final String type,
			final Class<? extends AbstractAttachmentDataSource> dataSourceClass) {
		Objects.requireNonNull(type, "Cannot register DataSource for null type");
		Objects.requireNonNull(dataSourceClass, "Cannot register null DataSource");

		attachmentDataSources.put(type, dataSourceClass);
		return Boolean.TRUE;
	}

	public boolean removeDataSource(final String type) {
		Objects.requireNonNull(type, "Cannot unsubscribe DataSource for null type");

		return (attachmentDataSources.remove(type) != null);
	}

	public <T extends AbstractAttachmentDataSource> T getForType(final String type) {
		Objects.requireNonNull(type, "Cannot get DataSource for null type");

		final Class<T> clazz = (Class<T>) attachmentDataSources.get(type);
		if (clazz != null) {
			try {
				return (T) clazz.getConstructor().newInstance();
			} catch (Throwable e) {
				throw new IllegalStateException("Could not instantiate DataSource");
			}
		}
		return null;
	}

	// ########################################################
	// Getter and Setter
	// ########################################################
	public Map<String, Class<? extends AbstractAttachmentDataSource>> getAttachmentDataSources() {
		return Collections.unmodifiableMap(attachmentDataSources);
	}

}
