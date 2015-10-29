package at.herzog.mailservice.mail.builder.cdi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class RecipientsBuilder {

	private MailBuilder mailBuilder;

	private final Set<InternetAddress> fromEmails = new HashSet<>();
	private final Set<InternetAddress> toEmails = new HashSet<>();
	private final Set<InternetAddress> ccEmails = new HashSet<>();
	private final Set<InternetAddress> bccEmails = new HashSet<>();

	// ###########################################################
	// Address handling
	// ###########################################################
	public RecipientsBuilder addFromAddress(final InternetAddress email) {
		Objects.requireNonNull(email);
		fromEmails.add(email);
		return this;
	}

	public RecipientsBuilder addFrom(final InternetAddress... email) {
		addEmail(fromEmails, email);
		return this;
	}

	public RecipientsBuilder addTo(final InternetAddress... email) {
		addEmail(toEmails, email);
		return this;
	}

	public RecipientsBuilder addCcAddresses(final InternetAddress... email) {
		addEmail(ccEmails, email);
		return this;
	}

	public RecipientsBuilder addBcc(final InternetAddress... email) {
		addEmail(bccEmails, email);
		return this;
	}

	public RecipientsBuilder build(Message message) throws MessagingException {
		boolean receiverAddressesAdded = Boolean.FALSE;
		if (!fromEmails.isEmpty()) {
			message.addFrom(fromEmails.toArray(new Address[fromEmails.size()]));
		} else {
			throw new MessagingException("No sender addresses defined");
		}
		if (!toEmails.isEmpty()) {
			message.addRecipients(RecipientType.TO, toEmails.toArray(new Address[toEmails.size()]));
			receiverAddressesAdded = Boolean.TRUE;
		}
		if (!bccEmails.isEmpty()) {
			message.addRecipients(RecipientType.BCC, bccEmails.toArray(new Address[bccEmails.size()]));
			receiverAddressesAdded = Boolean.TRUE;
		}
		if (!ccEmails.isEmpty()) {
			message.addRecipients(RecipientType.CC, ccEmails.toArray(new Address[ccEmails.size()]));
			receiverAddressesAdded = Boolean.TRUE;
		}

		if (!receiverAddressesAdded) {
			throw new MessagingException("No receiver address was added");
		}

		return this;
	}

	public RecipientsBuilder start(MailBuilder mailBuilder) {
		this.mailBuilder = mailBuilder;
		return this;
	}

	public MailBuilder end() {
		return mailBuilder;
	}

	public RecipientsBuilder clear() {
		this.fromEmails.clear();
		this.toEmails.clear();
		this.bccEmails.clear();
		this.bccEmails.clear();
		return this;
	}

	private void addEmail(Collection<InternetAddress> collection, InternetAddress... email) {
		Objects.requireNonNull(collection);

		if ((email != null) && (email.length > 0)) {
			for (InternetAddress address : email) {
				if (address != null) {
					collection.add(address);
				}
			}
		}
	}
}
