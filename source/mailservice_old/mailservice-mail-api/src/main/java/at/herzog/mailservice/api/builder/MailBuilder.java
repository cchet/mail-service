package at.herzog.mailservice.api.builder;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

import at.herzog.mailservice.api.context.ConnectionContext;

public interface MailBuilder<M extends MailBuilder<M, A, R>, A extends AttachmentBuilder<M, A>, R extends RecipientsBuilder<M, R>> {

	M start();

	M with(R recipientsBuilder);

	M with(A attachmentBuilder);

	M with(ConnectionContext ctx);

	Message buildMessage(final Session session) throws MessagingException;

	M send() throws MessagingException;

	M send(ConnectionContext ctx) throws MessagingException;

	R recipients();

	A attachments();
}
