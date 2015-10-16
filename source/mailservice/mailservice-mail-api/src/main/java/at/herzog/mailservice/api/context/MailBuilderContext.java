package at.herzog.mailservice.api.context;

import java.io.Serializable;
import java.util.Locale;

// TODO: Context needs to provide information about the context the mailing is build in
// TODO: All types of dataSource implementation shall be able to use this context
public class MailBuilderContext implements Serializable {

	private static final long serialVersionUID = 4467165609254790822L;

	public Locale locale;
	public String profile;
	private ConnectionContext connectionCtx;
	private SubjectDataSourceContext subjectCtx;
	private ContentDataSourceContext contentCtx;

	public MailBuilderContext() {
		super();
	}

	public MailBuilderContext(Locale locale, String profile, ConnectionContext connectionCtx,
			SubjectDataSourceContext subjectCtx, ContentDataSourceContext contentCtx) {
		super();
		this.locale = locale;
		this.profile = profile;
		this.connectionCtx = connectionCtx;
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

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public ConnectionContext getConnectionCtx() {
		return connectionCtx;
	}

	public void setConnectionCtx(ConnectionContext connectionCtx) {
		this.connectionCtx = connectionCtx;
	}

	public SubjectDataSourceContext getSubjectCtx() {
		return subjectCtx;
	}

	public void setSubjectCtx(SubjectDataSourceContext subjectCtx) {
		this.subjectCtx = subjectCtx;
	}

	public ContentDataSourceContext getContentCtx() {
		return contentCtx;
	}

	public void setContentCtx(ContentDataSourceContext contentCtx) {
		this.contentCtx = contentCtx;
	}

}
