package at.herzog.mailservice.mail.builder.impl;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import at.herzog.mailservice.api.builder.AttachmentBuilder;
import at.herzog.mailservice.api.datasource.AttachmentDataSource;
import at.herzog.mailservice.json.model.Attachment;

public class SimpleAttachmentBuilder<M> implements AttachmentBuilder<M, SimpleAttachmentBuilder<M>> {

	private M parent;

	private Set<DataHandler> dataHandlers = new LinkedHashSet<>();
	private Set<DataSource> dataSources = new LinkedHashSet<>();

	@Override
	public SimpleAttachmentBuilder<M> start(M parent) {
		this.parent = parent;

		return this.clear();
	}

	@Override
	public M end() {
		return parent;
	}

	@Override
	public SimpleAttachmentBuilder<M> clear() {
		dataHandlers.clear();
		dataSources.clear();

		return this;
	}

	@Override
	public SimpleAttachmentBuilder<M> build(final Message message) throws MessagingException {
		Objects.requireNonNull(message, "Cannot set attachments on null Message");

		// Set DataHandlers
		if (!getDataHandlers().isEmpty()) {
			final Multipart part = new MimeMultipart();
			try {
				for (DataHandler handler : getDataHandlers()) {
					final MimeBodyPart bodyPart = new MimeBodyPart();
					bodyPart.setDataHandler(handler);
					bodyPart.setFileName(handler.getDataSource().getName());
					part.addBodyPart(bodyPart);
				}
			} catch (Exception e) {
				throw new MessagingException("Could not create attachment for mail", e);
			}
			message.setContent(part);
		}

		// Set DataSources
		if (!getDataSources().isEmpty()) {
			final Multipart part = new MimeMultipart();
			try {
				for (DataSource dataSource : getDataSources()) {
					final MimeBodyPart bodyPart = new MimeBodyPart();
					bodyPart.setDataHandler(new DataHandler(dataSource));
					bodyPart.setFileName(dataSource.getName());
					part.addBodyPart(bodyPart);
				}
			} catch (Exception e) {
				throw new MessagingException("Could not create attachment for mail", e);
			}
			message.setContent(part);
		}

		return this;
	}

	@Override
	public SimpleAttachmentBuilder<M> addDataHandler(DataHandler handler) {
		Objects.requireNonNull(handler, "Cannot add null dataHandler");
		Objects.requireNonNull(handler.getDataSource(), "DataHandler must have an DataSource set");

		dataHandlers.add(handler);

		return this;
	}

	@Override
	public SimpleAttachmentBuilder<M> addDataSource(DataSource dataSource) {
		Objects.requireNonNull(dataSource, "Cannot add null DataSource");

		dataSources.add(dataSource);

		return this;
	}

	// ###########################################
	// Getter
	// ###########################################
	public Set<DataHandler> getDataHandlers() {
		return dataHandlers;
	}

	public Set<DataSource> getDataSources() {
		return dataSources;
	}
}
