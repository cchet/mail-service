package at.herzog.mailservice.mail.builder;

public class MailBuilder {

	// Builders
	private final RecipientsBuilder recipientsBuilder;
	private final AttachmentBuilder attachmentBuilder;

	public MailBuilder() {
		super();
		recipientsBuilder = new RecipientsBuilder(this);
		attachmentBuilder = new AttachmentBuilder(this);
	}

	public RecipientsBuilder recipients() {
		return recipientsBuilder;
	}

	public AttachmentBuilder attachments() {
		return attachmentBuilder;
	}
}
