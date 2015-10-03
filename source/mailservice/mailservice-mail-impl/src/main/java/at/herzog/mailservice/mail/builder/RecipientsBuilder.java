package at.herzog.mailservice.mail.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RecipientsBuilder {

	private final MailBuilder mailBuilder;

	private Set<String> toEmails = new HashSet<>();
	private Set<String> ccEmails = new HashSet<>();
	private Set<String> bcEmails = new HashSet<>();
	private Set<String> invalidEmails = new HashSet<>();

	public RecipientsBuilder() {
		this(null);
	}

	public RecipientsBuilder(MailBuilder mailBuilder) {
		super();
		this.mailBuilder = mailBuilder;
	}

	// ###########################################################
	// Addres handling
	// ###########################################################
	public RecipientsBuilder addToAddress(final String email) {
		addAddress(toEmails, email);
		return this;
	}

	public RecipientsBuilder addCcAddress(final String email) {
		addAddress(ccEmails, email);
		return this;
	}

	public RecipientsBuilder addBcAddress(final String email) {
		addAddress(bcEmails, email);
		return this;
	}

	public MailBuilder end() {
		return mailBuilder;
	}

	// #####################################################
	private void addAddress(final Collection<String> collection, final String email) {
		Objects.requireNonNull(collection, "Cannot email address to null collection");

		// TODO: Check for valid email and add to invalid collection
		// TODO: Maybe wrap into own class to add information where the email
		// was tried to be added.
		if ((email != null) && (!email.trim().isEmpty())) {
			collection.add(email);
		}
	}
}
