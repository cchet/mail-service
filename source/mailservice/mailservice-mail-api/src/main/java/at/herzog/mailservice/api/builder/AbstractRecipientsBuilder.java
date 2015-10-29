package at.herzog.mailservice.api.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

import at.herzog.mailservice.api.utils.MailBuilderUtils;

public abstract class AbstractRecipientsBuilder<R extends AbstractRecipientsBuilder<R, M>, M extends AbstractMailBuilder<?, M, R, ?>> {

	private final Set<String> fromEmails = new HashSet<>();
	private final Set<String> toEmails = new HashSet<>();
	private final Set<String> ccEmails = new HashSet<>();
	private final Set<String> bcEmails = new HashSet<>();

	// ###########################################################
	// Address handling
	// ###########################################################
	public R addFromAddress(final String email) {
		addAddress(fromEmails, email);
		return (R) this;
	}

	public R addToAddress(final String email) {
		addAddress(toEmails, email);
		return (R) this;
	}

	public R addCcAddress(final String email) {
		addAddress(ccEmails, email);
		return (R) this;
	}

	public R addBcAddress(final String email) {
		addAddress(bcEmails, email);
		return (R) this;
	}

	public R build(Message message) throws MessagingException {
		Address[] addresses = null;
		boolean receiverAddressesAdded = Boolean.FALSE;
		if (!fromEmails.isEmpty()) {
			addresses = MailBuilderUtils.emailsToAddresses(fromEmails);
			if (addresses.length > 0) {
				message.addFrom(addresses);
			} else {
				throw new MessagingException("No sender addresses defined");
			}
		}
		if (!toEmails.isEmpty()) {
			addresses = MailBuilderUtils.emailsToAddresses(toEmails);
			if (addresses.length > 0) {
				message.addRecipients(javax.mail.Message.RecipientType.TO, addresses);
				receiverAddressesAdded = Boolean.TRUE;
			}
		}
		if (!bcEmails.isEmpty()) {
			addresses = MailBuilderUtils.emailsToAddresses(bcEmails);
			if (addresses.length > 0) {
				message.addRecipients(javax.mail.Message.RecipientType.BCC, addresses);
				receiverAddressesAdded = Boolean.TRUE;
			}
		}
		if (!ccEmails.isEmpty()) {
			addresses = MailBuilderUtils.emailsToAddresses(ccEmails);
			if (addresses.length > 0) {
				message.addRecipients(javax.mail.Message.RecipientType.CC, addresses);
				receiverAddressesAdded = Boolean.TRUE;
			}
		}

		if (!receiverAddressesAdded) {
			throw new MessagingException("No receiver address was added");
		}

		return (R) this;
	}

	private void addAddress(final Collection<String> collection, final String email) {
		Objects.requireNonNull(collection, "Cannot email address to null collection");

		// TODO: Check for valid email and add to invalid collection
		// TODO: Maybe wrap into own class to add information where the email
		// was tried to be added.
		if ((email != null) && (!email.trim().isEmpty())) {
			collection.add(email);
		}
	}

	public abstract R start(M mailBuilder);

	public abstract M end();
}
