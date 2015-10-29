package at.herzog.mailservice.mail.builder.cdi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.blazebit.annotation.AnnotationUtils;

import at.herzog.mailservice.api.annotation.AttachmentType;
import at.herzog.mailservice.api.datasource.AbstractAttachmentDataSource;
import at.herzog.mailservice.api.datasource.AttachmentDataSource;
import at.herzog.mailservice.json.model.Attachment;

public class AttachmentBuilder {

	private MailBuilder mailBuilder;

	protected Map<String, AbstractAttachmentDataSource> datasourceForTypeMap = new HashMap<>();
	protected List<Attachment> attachments = new ArrayList<>();

	public AttachmentBuilder() {
		super();
	}

	public AttachmentBuilder addAttachment(final Attachment attachment) {
		attachments.add(attachment);
		return this;
	}

	public AttachmentBuilder addAttachments(final Collection<Attachment> attachments) {
		this.attachments.addAll(attachments);
		return this;
	}

	public AttachmentBuilder addDataSource(final AbstractAttachmentDataSource instance) {
		Objects.requireNonNull(instance, "Datasource cannot be null");

		final String type = getDataSourceAttachmentType(instance);
		if (datasourceForTypeMap.containsKey(type)) {
			throw new IllegalArgumentException("DataSource for attachment type: '" + type + "' already registered");
		}
		datasourceForTypeMap.put(type, instance);
		return this;
	}

	public AttachmentBuilder removeDataSource(final AbstractAttachmentDataSource instance) {
		Objects.requireNonNull(instance, "Cannot remove null datasource");

		final String type = getDataSourceAttachmentType(instance);
		if (!datasourceForTypeMap.containsKey(type)) {
			throw new IllegalArgumentException("DataSource for attachment type: '" + type + "' not registered");
		}
		datasourceForTypeMap.remove(type);
		return this;
	}

	public AttachmentBuilder start(MailBuilder mailBuilder) {
		this.mailBuilder = mailBuilder;
		return this;
	}

	public MailBuilder end() {
		return mailBuilder;
	}

	public AttachmentBuilder build(Message message) throws MessagingException {
		Objects.requireNonNull(message, "Cannot add attachments to null message");

		Multipart part = null;
		if (!attachments.isEmpty()) {
			part = new MimeMultipart();
			try {
				for (Attachment attachment : attachments) {
					final AttachmentDataSource datasource = getDataSourceForType(attachment.getType());
					datasource.init(attachment, null);
					final MimeBodyPart bodyPart = new MimeBodyPart();
					bodyPart.setDataHandler(new DataHandler(datasource));
					bodyPart.setFileName(datasource.getName());
					part.addBodyPart(bodyPart);
				}
			} catch (Exception e) {
				throw new MessagingException("Could not create attachment for mail", e);
			}
			message.setContent(part);
		}
		return this;
	}

	public AttachmentBuilder clear() {
		attachments.clear();
		datasourceForTypeMap.clear();
		return this;
	}

	protected AbstractAttachmentDataSource getDataSourceForType(final String type) {
		if (!datasourceForTypeMap.containsKey(type)) {
			throw new IllegalStateException("No datasource for the attachment type: '" + type + "' found");
		}
		return datasourceForTypeMap.get(type);
	}

	protected String getDataSourceAttachmentType(final AbstractAttachmentDataSource dataSource) {
		Objects.requireNonNull(dataSource, "Cannot get annotation from null datasource");

		final AttachmentType type = AnnotationUtils.findAnnotation(dataSource.getClass(), AttachmentType.class);
		Objects.requireNonNull(
				type, "DataSource: '" + dataSource.getClass() + "' does not provide AttachmentType annotation");

		return type.value();
	}
}
