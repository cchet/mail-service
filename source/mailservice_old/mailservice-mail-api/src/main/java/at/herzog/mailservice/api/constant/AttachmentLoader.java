package at.herzog.mailservice.api.constant;

import java.io.OutputStream;

import at.herzog.mailservice.json.model.Attachment;

public interface AttachmentLoader {

	public OutputStream load(Attachment attachment);
}
