package at.herzog.mailservice.mail.builder.cdi;

import java.util.Objects;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import at.herzog.mailservice.api.context.MailBuilderContext;

public class MailBuilder {

	private MailBuilderContext ctx;

	private AttachmentBuilder attachmentBuilder;
	private RecipientsBuilder recipientsBuilder;

	public MailBuilder() {
		super();
	}

	public RecipientsBuilder recipients() {
		return (recipientsBuilder != null) ? recipientsBuilder.start(this) : null;
	}

	public AttachmentBuilder attachments() {
		return (attachmentBuilder != null) ? attachmentBuilder.start(this) : null;
	}

	public MailBuilder start(final MailBuilderContext ctx) {
		Objects.requireNonNull(ctx, "MailBuilderContext must not be null");

		this.ctx = ctx;
		this.recipientsBuilder = new RecipientsBuilder();
		this.attachmentBuilder = new AttachmentBuilder();
		return this;
	}

	public MailBuilder startWith(final MailBuilderContext ctx, final RecipientsBuilder recipientsBuilder,
			final AttachmentBuilder attachmentBuilder) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(recipientsBuilder);
		Objects.requireNonNull(attachmentBuilder);

		this.recipientsBuilder = recipientsBuilder;
		this.attachmentBuilder = attachmentBuilder;
		this.ctx = ctx;
		return this;
	}

	public MailBuilder with(final RecipientsBuilder recipientsBuilder) {
		this.recipientsBuilder = recipientsBuilder;
		return this;
	}

	public MailBuilder with(final AttachmentBuilder attachmentBuilder) {
		this.attachmentBuilder = attachmentBuilder;
		return this;
	}

	public Message buildMail() {
		Objects.requireNonNull(ctx, "No MailBuildercontext is set on this instance");

		try {
			final Properties props = ctx.getConnectionCtx().generateConnectionProperties();
			final Authenticator auth = ctx.getConnectionCtx().getAuthenticator();
			final Session session;
			if (auth == null) {
				session = Session.getInstance(props);
			} else {
				session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("thomas", "herzog");
					}
				});
			}

			final Message message = new MimeMessage(session);
			// Manage addresses
			recipients().build(message);
			message.setContent("Hello master", "text/plain");
			// manage attachments
			attachmentBuilder.build(message);
			System.out.println(message.toString());
			return message;
		} catch (Exception e) {
			throw new IllegalStateException("Mail build failed", e);
		}
	}

	public MailBuilder send() {
		final Message message = buildMail();
		return this;
	}

	public MailBuilder clear() {
		if (recipientsBuilder != null) {
			recipientsBuilder.clear();
		}
		if (attachmentBuilder != null) {
			attachmentBuilder.clear();
		}
		return this;
	}
}
