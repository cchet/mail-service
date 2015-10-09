package at.herzog.mailservice.api.context;

import java.io.Serializable;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

// TODO: Context needs to provide information about the context the mailing is build in
// TODO: All types of dataSource implementation shall be able to use this context
public class MailBuilderContext implements Serializable {

	private static final long serialVersionUID = 4467165609254790822L;

	public Locale locale;
	public final String profile;
	private final SubjectDataSourceContext subjectCtx;
	private final ContentDataSourceContext contentCtx;

	public MailBuilderContext(Locale locale, String profile, SubjectDataSourceContext subjectCtx,
			ContentDataSourceContext contentCtx) {
		super();
		this.locale = locale;
		this.profile = profile;
		this.subjectCtx = subjectCtx;
		this.contentCtx = contentCtx;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getProfile() {
		return profile;
	}

	public SubjectDataSourceContext getSubjectCtx() {
		return subjectCtx;
	}

	public ContentDataSourceContext getContentCtx() {
		return contentCtx;
	}

}
