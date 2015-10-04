package at.herzog.mailservice.web.application.api;

import java.io.Serializable;
import java.util.Map;

import at.herzog.mailservice.web.application.rest.datasource.api.AbstractAttachmentDataSource;

public interface ModuleStartupObserver extends Serializable {

	public Map<String, Class<? extends AbstractAttachmentDataSource>> getAttachmentDataSources();
}
