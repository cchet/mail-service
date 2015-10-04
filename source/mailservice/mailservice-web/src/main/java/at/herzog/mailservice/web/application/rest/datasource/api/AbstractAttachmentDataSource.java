package at.herzog.mailservice.web.application.rest.datasource.api;

import java.util.Objects;

import javax.activation.DataSource;

import at.herzog.mailservice.json.model.Attachment;

public abstract class AbstractAttachmentDataSource implements DataSource {

	protected final String attachmentType;
	protected Attachment attachment;

	public AbstractAttachmentDataSource(String attachmentType) {
		super();

		Objects.requireNonNull(attachmentType, "The supported attachment type must be given");

		if (attachmentType.trim().isEmpty()) {
			throw new IllegalArgumentException("Attatchment type mujst not be empty");
		}

		this.attachmentType = attachmentType;
	}

	/**
	 * Inits this data source with the supported attachment.<br>
	 * Always call the super implementation if you overwrite this method.
	 * 
	 * @param attachment
	 *            the attachment with provides information for loading the data
	 */
	public void init(final Attachment attachment) {
		Objects.requireNonNull(attachment, "Cannot initialize MailDataSource with null attachment");

		if (!attachment.getType().equals(attachmentType)) {
			throw new IllegalArgumentException("Attachment defines unsupported type. attachmentType: '"
					+ attachment.getType() + "' supportedType: " + attachmentType);
		}

		this.attachment = attachment;
	}

	// ##############################################################
	// Getter and Setters
	// ##############################################################
	/**
	 * The type of the attachment this data source is able to provide data for.
	 * 
	 * @return the supported attachment type represented by an string
	 */
	public String getAttachmentType() {
		return attachmentType;
	}

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
