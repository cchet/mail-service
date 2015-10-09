package at.herzog.mailservice.api.datasource;

import at.herzog.mailservice.api.context.MailBuilderContext;

public interface ContentDataSource {

	void init(String key, MailBuilderContext context);

	String getContent();

	String getContentType();
}
