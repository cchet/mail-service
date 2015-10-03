package at.herzog.mailservice.mail.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import at.herzog.mailservice.json.model.Attachment;

public class AttachmentBuilder {

	private final MailBuilder mailBuilder;

	private List<Attachment> attachments = new ArrayList<>();

	public AttachmentBuilder(MailBuilder mailBuilder) {
		super();
		this.mailBuilder = mailBuilder;
	}

	public AttachmentBuilder addAttachment(final Attachment attachment) {
		attachments.add(attachment);
		return this;
	}

	public AttachmentBuilder addAttachments(final Collection<Attachment> attachments) {
		this.attachments.addAll(attachments);
		return this;
	}

	public MailBuilder end() {
		return mailBuilder;
	}
}
