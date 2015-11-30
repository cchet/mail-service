package at.herzog.mailservice.api.builder;

import java.util.Collection;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public interface RecipientsBuilder<M, R extends RecipientsBuilder<M, R>> {

	R start(M parent);

	M end();

	R clear();

	R build(Message message) throws MessagingException;

	R addFrom(String email);

	R addTo(String email);

	R addCc(String email);

	R addBcc(String email);

	Collection<InternetAddress> getFrom();

	Collection<InternetAddress> getTo();

	Collection<InternetAddress> getBcc();

	Collection<InternetAddress> getCc();

	boolean isEmpty();
}