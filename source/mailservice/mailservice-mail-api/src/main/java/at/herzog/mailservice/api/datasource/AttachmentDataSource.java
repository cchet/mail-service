package at.herzog.mailservice.api.datasource;

import javax.activation.DataSource;

import at.herzog.mailservice.api.context.MailBuilderContext;
import at.herzog.mailservice.json.model.Attachment;

public interface AttachmentDataSource extends DataSource {

	void init(Attachment attachment, MailBuilderContext context);
}
