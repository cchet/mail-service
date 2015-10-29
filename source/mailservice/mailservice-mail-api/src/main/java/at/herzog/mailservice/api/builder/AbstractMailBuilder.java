package at.herzog.mailservice.api.builder;

import at.herzog.mailservice.api.datasource.AbstractAttachmentDataSource;

public abstract class AbstractMailBuilder<T, M extends AbstractMailBuilder<T, M, R, A>, R extends AbstractRecipientsBuilder<R, M>, A extends AbstractAttachmentBuilder<A, M, ?>> {

	public AbstractMailBuilder() {
		super();
	}

	public abstract R recipients();

	public abstract A attachments();

	public abstract M start();
	
	public abstract T build();
	
	// #############################################################
	// Delegates
	// #############################################################
	public M addAttachmentDataSource(final Class<? extends AbstractAttachmentDataSource> datasource) {
		return attachments().addDataSource(datasource).end();
	}

	public M removeAttachmentDataSource(final Class<? extends AbstractAttachmentDataSource> datasource) {
		return attachments().removeDataSource(datasource).end();
	}

	// #############################################################
	// Getter and Setter
	// #############################################################
	public void setRecipientsBuilder(R recipientsBuilder) {
		throw new UnsupportedOperationException("Setting of recipients builder not supported");
	}

	public void setAttachmentBuilder(A attachmentBuilder) {
		throw new UnsupportedOperationException("Setting of attachment builder not supported");
	}

}
