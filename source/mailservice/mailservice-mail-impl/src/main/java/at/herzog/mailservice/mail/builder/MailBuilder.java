package at.herzog.mailservice.mail.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.herzog.mailservice.mail.builder.model.Attachment;

public class MailBuilder {

	// TODO: Subject Builder for building the subject. (Could be from template)
	// TODO: EmailAddressBuilder for handling the build of the different types
	// of addresses.
	// TODO: Attachment Builder for handling the mail attachments. (Loaded from
	// outside)

	// The list of attachments represented by an model
	// Attachments can be provided via BASE64 or loaded by an external loader
	private List<Attachment> attachments = new ArrayList<>();
	private Set<String> toEmails = new HashSet<>();
	private Set<String> ccEmails = new HashSet<>();
	private Set<String> bcEmails = new HashSet<>();

	// ###########################################################
	// Addres handling
	// ###########################################################
	public MailBuilder addToAddress(final String address) {
		if ((address != null) && (!address.trim().isEmpty())) {
			this.toEmails.add(address);
		}
		return this;
	}

	public MailBuilder addCcAddress(final String address) {
		if ((address != null) && (!address.trim().isEmpty())) {
			this.ccEmails.add(address);
		}
		return this;
	}

	public MailBuilder addBcAddress(final String address) {
		if ((address != null) && (!address.trim().isEmpty())) {
			this.bcEmails.add(address);
		}
		return this;
	}

	// ###########################################################
	// Addres handling
	// ###########################################################
	public MailBuilder addAttachment(final Attachment attachment) {
		attachments.add(attachment);
		return this;
	}

	public MailBuilder addAttachments(final Collection<Attachment> attachments) {
		this.attachments.addAll(attachments);
		return this;
	}
}
