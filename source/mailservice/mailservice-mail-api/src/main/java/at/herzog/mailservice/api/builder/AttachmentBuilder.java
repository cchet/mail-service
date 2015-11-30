package at.herzog.mailservice.api.builder;

import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;

public interface AttachmentBuilder<M, A extends AttachmentBuilder<M, A>> {

	A start(M parent);

	M end();

	A clear();

	A build(final Message message) throws MessagingException;

	A addDataHandler(DataHandler handler);

	A addDataSource(DataSource dataSource);

	Set<DataHandler> getDataHandlers();

	Set<DataSource> getDataSources();
}