package at.herzog.mailservice.mail.builder.impl;

import java.util.Objects;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

import at.herzog.mailservice.api.builder.MailBuilder;
import at.herzog.mailservice.api.context.ConnectionContext;

public class SimpleMailBuilder implements
		MailBuilder<SimpleMailBuilder, SimpleAttachmentBuilder<SimpleMailBuilder>, SimpleRecipientsBuilder<SimpleMailBuilder>> {

	private ConnectionContext ctx;
	private SimpleAttachmentBuilder<SimpleMailBuilder> attachmentBuilder;
	private SimpleRecipientsBuilder<SimpleMailBuilder> recipientBuilder;

	@Override
	public SimpleMailBuilder start() {
		Objects.requireNonNull(attachmentBuilder, "Cannot start MailBuilder with null AttachmentBuilder set");
		Objects.requireNonNull(recipientBuilder, "Cannot start MailBuilder with null RecipientBuilder set");

		attachmentBuilder.start(this);
		recipientBuilder.start(this);

		return this;
	}

	@Override
	public SimpleMailBuilder with(SimpleRecipientsBuilder<SimpleMailBuilder> recipientsBuilder) {
		Objects.requireNonNull(recipientsBuilder, "Cannot set null RecipientBuilder");

		this.recipientBuilder = recipientsBuilder;

		return this;
	}

	@Override
	public SimpleMailBuilder with(SimpleAttachmentBuilder<SimpleMailBuilder> attachmentBuilder) {
		Objects.requireNonNull(attachmentBuilder, "Cannot set null AttachmentBuilder");

		this.attachmentBuilder = attachmentBuilder;

		return this;
	}

	@Override
	public SimpleMailBuilder with(final ConnectionContext ctx) {
		Objects.requireNonNull(ctx, "Cannot set null ConnectionContext");

		this.ctx = ctx;

		return this;
	}

	@Override
	public Message buildMessage(final Session session) throws MessagingException {
		Objects.requireNonNull(ctx, "No MailBuilderContext is set on this instance");

		try {
			final Message message = new MimeMessage(session);
			// Manage addresses
			recipientBuilder.build(message);
			// Manage attachments
			attachmentBuilder.build(message);
			// Manage subject
			// TODO: Implement Subject handler
			message.setSubject("My subject");
			// Manage Content
			// TODO: Implement Content handler
			message.setContent("Hello master", "text/plain");
			System.out.println(message.toString());

			return message;
		} catch (Exception e) {
			throw new IllegalStateException("Mail build failed", e);
		}
	}

	@Override
	public SimpleMailBuilder send() throws MessagingException {
		return send(ctx);
	}

	@Override
	public SimpleMailBuilder send(ConnectionContext ctx) throws MessagingException {
		Objects.requireNonNull(ctx, "Cannot set null ConnectionContext");

		final Properties props = ctx.generateConnectionProperties();
		final Authenticator auth = ctx.getAuthenticator();
		final Session session;
		if (auth == null) {
			session = Session.getInstance(props);
		} else {
			session = Session.getInstance(props, auth);
		}

		// TODO: Maybe an own send handler would be suitable here
		final Message message = buildMessage(session);
		SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
		t.setReportSuccess(Boolean.TRUE);
		t.setRequireStartTLS(Boolean.FALSE);
		t.connect();
		t.send(message, message.getAllRecipients());
		System.out.println(t.getLastReturnCode());
		System.out.println(t.getLastServerResponse());
		t.close();
		System.out.println(t.getLastReturnCode());
		System.out.println(t.getLastServerResponse());
		
		return this;
	}

	@Override
	public SimpleRecipientsBuilder<SimpleMailBuilder> recipients() {
		return recipientBuilder;
	}

	@Override
	public SimpleAttachmentBuilder<SimpleMailBuilder> attachments() {
		return attachmentBuilder;
	}
}
