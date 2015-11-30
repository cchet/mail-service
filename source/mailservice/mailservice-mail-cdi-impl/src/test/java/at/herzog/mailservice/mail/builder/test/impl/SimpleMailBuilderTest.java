package at.herzog.mailservice.mail.builder.test.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;
import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.herzog.mailservice.api.context.ConnectionContext;
import at.herzog.mailservice.api.context.ConnectionContext.Protocol;
import at.herzog.mailservice.mail.builder.impl.SimpleAttachmentBuilder;
import at.herzog.mailservice.mail.builder.impl.SimpleMailBuilder;
import at.herzog.mailservice.mail.builder.impl.SimpleRecipientsBuilder;
import junit.framework.Assert;

@RunWith(JUnit4.class)
public class SimpleMailBuilderTest {

	@Test
	public void test() throws MessagingException {
		try {
			// @formatter:off
			final SimpleMailBuilder builder = new SimpleMailBuilder();
			builder.with(new SimpleAttachmentBuilder<SimpleMailBuilder>())
			       .with(new SimpleRecipientsBuilder<SimpleMailBuilder>())
			       .with(new ConnectionContext("smtp3.itandtel.at", 25, Protocol.POP3, (new Authenticator() {
				    	@Override
				    	protected PasswordAuthentication getPasswordAuthentication() {
				    		// TODO Auto-generated method stub
//				    		return new PasswordAuthentication("smtpcure", "n2gUr9b");
				    		return new PasswordAuthentication("cchet", "b6w38");
				    	}
					}), Boolean.FALSE))
			       .start()
			       .recipients().addFrom("t.herzog@curecomp.com")
			                    .addTo("herzog.thomas81@gmail.com")
			                    .end()
//			       .attachments().addDataSource(new DataSource() {
//													@Override
//													public OutputStream getOutputStream() throws IOException {
//														return null;
//													}
//													
//													@Override
//													public String getName() {
//														return "my attachment";
//													}
//													
//													@Override
//													public InputStream getInputStream() throws IOException {
//														return null;
//													}
//													
//													@Override
//													public String getContentType() {
//														return "text/plain";
//													}
//												})
//			       					.end()
			       	.send();
			// @formatter:on
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
