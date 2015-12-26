package at.herzog.mailservice.api.context;

import java.util.Map;

public class ContentDataSourceContext {

	public final Map<String, String> contentArgs;

	public ContentDataSourceContext(Map<String, String> contentArgs) {
		super();
		this.contentArgs = contentArgs;
	}

	public Map<String, String> getContentArgs() {
		return contentArgs;
	}

}
