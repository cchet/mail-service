package at.herzog.mailservice.mail.builder.cdi.registry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import at.herzog.cdi.api.event.ContainerStartupEvent;
import at.herzog.mailservice.api.annotation.AttachmentType;
import at.herzog.mailservice.api.datasource.AttachmentDataSource;

@ApplicationScoped
public class AttachmentDataSourceRegistry implements Serializable {

	private static final long serialVersionUID = -391222331438249522L;

	@Inject
	private Instance<AttachmentDataSource> dataSources;

	private final Set<Class<? extends AttachmentDataSource>> invalid = new HashSet<>();
	private final Map<String, Class<? extends AttachmentDataSource>> dataSourcesMap = new HashMap<>();

	public AttachmentDataSourceRegistry() {
		super();
	}

	private void observeStartup(@Observes ContainerStartupEvent event) {
		if (dataSources.isUnsatisfied()) {
			// No implementations present at all
			return;
		}
		final Set<AttachmentDataSource> types = new HashSet<>();
		dataSources.forEach(new Consumer<AttachmentDataSource>() {
			@Override
			public void accept(AttachmentDataSource datasource) {
				final AttachmentType qualifier = datasource.getClass().getAnnotation(AttachmentType.class);
				if (qualifier == null) {
					throw new IllegalStateException("DataSource must have AttachmentType annotation set");
				}
				if (!qualifier.value().isEmpty()) {
					registerDataSource(qualifier.value(), datasource);
				} else if (qualifier.values().length > 0) {
					for (String type : qualifier.values()) {
						registerDataSource(type, datasource);
					}
				} else {
					throw new IllegalStateException("No attachment type defined on DataSource beans");
				}
				dataSources.destroy(datasource);
			}
		});
	}

	public AttachmentDataSource getDataSource(final String type) {
		Objects.requireNonNull(type, "Cannot retrieve DataSource for null type");
		if (dataSourcesMap.containsKey(type)) {
			return getDataSource(dataSourcesMap.get(type));
		}
		return null;
	}

	public AttachmentDataSource getDataSource(final Class<? extends AttachmentDataSource> clazz) {
		Objects.requireNonNull(clazz, "Cannot retrieve DataSource for null clazz");

		final Instance<? extends AttachmentDataSource> instance = dataSources.select(clazz);
		if (!instance.isUnsatisfied()) {
			return instance.get();
		}
		return null;
	}

	public void destroyAttachmentDataSource(final AttachmentDataSource AttachmentDataSource) {
		Objects.requireNonNull(AttachmentDataSource, "Cannot destroy null DataSource");

		dataSources.destroy(AttachmentDataSource);
	}

	private void registerDataSource(final String type, final AttachmentDataSource AttachmentDataSource) {
		Objects.requireNonNull(type);
		Objects.requireNonNull(AttachmentDataSource);

		if (type.isEmpty()) {
			throw new IllegalStateException("DataSource must have AttachmentType annotation set");
		}

		Class<? extends AttachmentDataSource> clazz = null;
		if ((clazz = dataSourcesMap.get(type)) != null) {
			throw new IllegalStateException(
					"DataSource for type: '" + type + "' already registered. class: '" + clazz.getName() + "'");
		}
		dataSourcesMap.put(type, AttachmentDataSource.getClass());
	}
}
