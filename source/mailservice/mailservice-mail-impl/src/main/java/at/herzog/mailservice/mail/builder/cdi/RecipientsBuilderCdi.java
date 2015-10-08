package at.herzog.mailservice.mail.builder.cdi;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import at.herzog.mailservice.api.builder.AbstractRecipientsBuilder;

@Dependent
public class RecipientsBuilderCdi extends AbstractRecipientsBuilder<RecipientsBuilderCdi, MailBuilderCdi>
		implements Serializable {

	private static final long serialVersionUID = -6770487123761048903L;

	private MailBuilderCdi mailBuilder;

	public RecipientsBuilderCdi() {
	}

	@Override
	public RecipientsBuilderCdi start(MailBuilderCdi mailBuilder) {
		this.mailBuilder = mailBuilder;
		return this;
	}

	@Override
	public MailBuilderCdi end() {
		return mailBuilder;
	}
}
