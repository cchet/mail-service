package at.herzog.mailservice.mail.builder.cdi;

import java.io.Serializable;
import java.util.Objects;

import javax.activation.DataHandler;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import at.herzog.mailservice.api.builder.AbstractAttachmentBuilder;
import at.herzog.mailservice.api.datasource.AttachmentDataSource;
import at.herzog.mailservice.json.model.Attachment;
import at.herzog.mailservice.mail.builder.cdi.registry.AttachmentDataSourceRegistry;

@Dependent
public class AttachmentBuilderCdi extends AbstractAttachmentBuilder<AttachmentBuilderCdi, MailBuilderCdi, Multipart>
		implements Serializable {

	private static final long serialVersionUID = 1792194831679390055L;

	@Inject
	private AttachmentDataSourceRegistry dataSourceRegistry;

	private MailBuilderCdi mailBuilder;

	public AttachmentBuilderCdi() {
		super();
	}

	@Override
	public AttachmentBuilderCdi build(Message message) throws MessagingException {
		Objects.requireNonNull(message, "Cannot add attachments to null message");

		Multipart part = null;
		if (!attachments.isEmpty()) {
			part = new MimeMultipart();
			try {
				for (Attachment attachment : attachments) {
					final AttachmentDataSource datasource = dataSourceRegistry.getDataSource(attachment.getType());
					if (datasource == null) {
						throw new IllegalStateException(
								"datasource resolved to null for type: '" + attachment.getType() + "'");
					}
					datasource.init(attachment);
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

	@Override
	public AttachmentBuilderCdi start(MailBuilderCdi mailBuilder) {
		this.mailBuilder = mailBuilder;
		return this;
	}

	@Override
	public MailBuilderCdi end() {
		return mailBuilder;
	}
}
