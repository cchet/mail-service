package at.herzog.mailservice.api.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;

import at.herzog.mailservice.api.datasource.AbstractAttachmentDataSource;
import at.herzog.mailservice.json.model.Attachment;

public abstract class AbstractAttachmentBuilder<A extends AbstractAttachmentBuilder<A, M, P>, M extends AbstractMailBuilder<?, M, ?, A>, P> {

	protected Set<Class<? extends AbstractAttachmentDataSource>> dataSources = new HashSet<>();
	protected List<Attachment> attachments = new ArrayList<>();

	public AbstractAttachmentBuilder() {
		super();
	}

	public A addAttachment(final Attachment attachment) {
		attachments.add(attachment);
		return (A) this;
	}

	public A addAttachments(final Collection<Attachment> attachments) {
		this.attachments.addAll(attachments);
		return (A) this;
	}

	public A addDataSource(final Class<? extends AbstractAttachmentDataSource> clazz) {
		Objects.requireNonNull(clazz, "Cannot add null datasource");

		dataSources.add(clazz);
		return (A) this;
	}

	public A removeDataSource(final Class<? extends AbstractAttachmentDataSource> clazz) {
		Objects.requireNonNull(clazz, "Cannot remove null datasource");

		dataSources.remove(clazz);
		return (A) this;
	}

	public abstract A start(M mailBuilder);

	public abstract M end();

	public abstract A build(Message message) throws MessagingException;
}
