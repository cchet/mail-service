package at.herzog.mailservice.api.context;

import java.util.Map;

public class SubjectDataSourceContext {

	public final Map<String, String> subjectArgs;

	public SubjectDataSourceContext(Map<String, String> subjectArgs) {
		super();
		this.subjectArgs = subjectArgs;
	}

	public Map<String, String> getSubjectArgs() {
		return subjectArgs;
	}

}
