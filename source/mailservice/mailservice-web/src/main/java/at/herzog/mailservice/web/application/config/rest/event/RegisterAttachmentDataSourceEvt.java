package at.herzog.mailservice.web.application.config.rest.event;

import at.herzog.mailservice.web.application.rest.datasource.api.AbstractAttachmentDataSource;

public class RegisterAttachmentDataSourceEvt extends AbstractDataSourceEvt {

	protected final String type;
	protected final AbstractAttachmentDataSource dataSource;

	public RegisterAttachmentDataSourceEvt(String type, AbstractAttachmentDataSource dataSource) {
		super();
		this.type = type;
		this.dataSource = dataSource;
	}

	public String getType() {
		return type;
	}

	public AbstractAttachmentDataSource getDataSource() {
		return dataSource;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getName()).append("[type=").append(type).append(" | dataSource=")
				.append(dataSource).append(" | success=").append(isSuccess()).append("]").toString();
	}
}
