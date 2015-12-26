package at.herzog.mailservice.mail.builder.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import at.herzog.mailservice.api.builder.RecipientsBuilder;

public class SimpleRecipientsBuilder<M> implements RecipientsBuilder<M, SimpleRecipientsBuilder<M>> {

	private M parent;

	private final Set<InternetAddress> fromEmails = new LinkedHashSet<>();
	private final Set<InternetAddress> toEmails = new LinkedHashSet<>();
	private final Set<InternetAddress> ccEmails = new LinkedHashSet<>();
	private final Set<InternetAddress> bccEmails = new LinkedHashSet<>();

	@Override
	public SimpleRecipientsBuilder<M> start(M parent) {
		this.parent = parent;

		return this.clear();
	}

	@Override
	public SimpleRecipientsBuilder<M> clear() {
		this.fromEmails.clear();
		this.toEmails.clear();
		this.bccEmails.clear();
		this.bccEmails.clear();

		return this;
	}

	@Override
	public M end() {
		return parent;
	}

	@Override
	public SimpleRecipientsBuilder<M> build(Message message) throws MessagingException {
		Objects.requireNonNull(message, "Cannot set addresses on null Message");

		if (getFrom().isEmpty()) {
			throw new MessagingException("No sender addresses defined");
		}

		if (isEmpty()) {
			throw new MessagingException("At least one recipient must be defined");
		}

		if (!getFrom().isEmpty()) {
			message.addFrom(getFrom().toArray(new InternetAddress[getFrom().size()]));
		}

		if (!getTo().isEmpty()) {
			message.addRecipients(RecipientType.TO, getTo().toArray(new InternetAddress[getTo().size()]));
		}

		if (!getBcc().isEmpty()) {
			message.addRecipients(RecipientType.BCC, getBcc().toArray(new InternetAddress[getBcc().size()]));
		}

		if (!getCc().isEmpty()) {
			message.addRecipients(RecipientType.CC, getCc().toArray(new InternetAddress[getCc().size()]));
		}

		return this;
	}

	@Override
	public SimpleRecipientsBuilder<M> addFrom(final String email) {
		addEmail(fromEmails, email);

		return this;
	}

	@Override
	public SimpleRecipientsBuilder<M> addTo(final String email) {
		addEmail(toEmails, email);

		return this;
	}

	@Override
	public SimpleRecipientsBuilder<M> addCc(final String email) {
		addEmail(ccEmails, email);

		return this;
	}

	@Override
	public SimpleRecipientsBuilder<M> addBcc(final String email) {
		addEmail(bccEmails, email);

		return this;
	}

	// ##############################################
	// Getter
	// ##############################################
	@Override
	public Set<InternetAddress> getFrom() {
		return fromEmails;
	}

	@Override
	public Set<InternetAddress> getTo() {
		return toEmails;
	}

	@Override
	public Set<InternetAddress> getCc() {
		return ccEmails;
	}

	@Override
	public Set<InternetAddress> getBcc() {
		return bccEmails;
	}

	@Override
	public boolean isEmpty() {
		return toEmails.isEmpty() && bccEmails.isEmpty() && ccEmails.isEmpty();
	}

	// ##############################################
	// Private Helper
	// ##############################################
	private void addEmail(final Collection<InternetAddress> collection, final String email) {
		Objects.requireNonNull(collection, "Collection must not be null");
		Objects.requireNonNull(email, "Email must not be null");

		try {
			collection.add(new InternetAddress(email));
		} catch (AddressException e) {
			throw new IllegalArgumentException("Email format invalid", e);
		}
	}

}
