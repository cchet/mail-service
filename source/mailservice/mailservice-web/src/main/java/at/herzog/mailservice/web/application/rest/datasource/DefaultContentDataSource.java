package at.herzog.mailservice.web.application.rest.datasource;

import javax.enterprise.context.Dependent;

import at.herzog.mailservice.api.datasource.ContentDataSource;

@Dependent
public class DefaultContentDataSource implements ContentDataSource {

	private String content;

	@Override
	public void init(String content) {
		this.content = content;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public String getContentType() {
		return "text/plain";
	}

}
