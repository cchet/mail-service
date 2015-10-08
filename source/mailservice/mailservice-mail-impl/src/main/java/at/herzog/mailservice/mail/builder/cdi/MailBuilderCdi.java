package at.herzog.mailservice.mail.builder.cdi;

import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import at.herzog.mailservice.api.builder.AbstractMailBuilder;

@Dependent
public class MailBuilderCdi
		extends AbstractMailBuilder<Message, MailBuilderCdi, RecipientsBuilderCdi, AttachmentBuilderCdi>
		implements Serializable {

	private static final long serialVersionUID = -810966363124505391L;

	// Builders
	@Inject
	private RecipientsBuilderCdi recipientsBuilder;
	@Inject
	private AttachmentBuilderCdi attachmentBuilder;

	public MailBuilderCdi() {
		super();
	}

	@Override
	public MailBuilderCdi start() {
		attachmentBuilder.start(this);
		recipientsBuilder.start(this);
		return this;
	}

	public RecipientsBuilderCdi recipients() {
		return recipientsBuilder;
	}

	public AttachmentBuilderCdi attachments() {
		return attachmentBuilder;
	}

	@Override
	public Message build() {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("thomas", "herzog");
				}
			});

			final Message message = new MimeMessage(session);
			// Manage addresses
			recipients().build(message);
			message.setContent("Hello master", "text/plain");
			// manage attachments
			attachmentBuilder.build(message);
		} catch (Exception e) {
			throw new IllegalStateException("Mail build failed", e);
		}
		return null;
	}

}
