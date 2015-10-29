package at.herzog.mailservice.api.datasource;

import java.util.Objects;

import javax.activation.DataSource;

import at.herzog.mailservice.api.context.MailBuilderContext;
import at.herzog.mailservice.json.model.Attachment;

public abstract class AbstractAttachmentDataSource implements AttachmentDataSource {

	protected Attachment attachment;

	public AbstractAttachmentDataSource() {
		super();
	}

	/**
	 * Inits this data source with the supported attachment.<br>
	 * Always call the super implementation if you overwrite this method.
	 * 
	 * @param attachment
	 *            the attachment with provides information for loading the data
	 */
	public void init(final Attachment attachment, MailBuilderContext context) {
		Objects.requireNonNull(attachment, "Cannot initialize MailDataSource with null attachment");

		this.attachment = attachment;
	}

	// ##############################################################
	// Getter and Setters
	// ##############################################################
	/**
	 * Gets the attachment name. Overwrite if name gets resolved otherwise.
	 * 
	 * @return the data name
	 */
	@Override
	public String getName() {
		return attachment.getName();
	}
}
