package at.herzog.mailservice.web.mail.webservice;

import java.util.Locale;

import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.herzog.mailservice.api.context.ConnectionContext;
import at.herzog.mailservice.api.context.ConnectionContext.Protocol;
import at.herzog.mailservice.api.context.MailBuilderContext;
import at.herzog.mailservice.json.model.Attachment;
import at.herzog.mailservice.json.model.RestResponse;
import at.herzog.mailservice.json.model.SimpleMailRequest;
import at.herzog.mailservice.mail.builder.cdi.MailBuilder;
import at.herzog.mailservice.web.application.rest.datasource.Base64DataSource;

@Path("/mail/simple")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MailService {

	@Inject
	private Base64DataSource dataSource;

	//@formatter:off
	@POST
	@GET
	@Path("send")
	public Response send(final SimpleMailRequest request) {
		final ConnectionContext conCtx = new ConnectionContext("smtp.gmail.com", 587, Protocol.POP3, null, Boolean.TRUE);
		final MailBuilderContext builderCtx = new MailBuilderContext();
		builderCtx.setLocale(Locale.ENGLISH);
		builderCtx.setProfile("myProfile");
		builderCtx.setConnectionCtx(conCtx);
		
		final MailBuilder mailBuilder = new MailBuilder();
		try {
			mailBuilder.start(builderCtx)
			   .attachments()
			   .addAttachment(
					new Attachment("test.txt", 
								   "base64",
							       "SGVsbG8gSSBhbSBhIGJhc2U2NCBjb2RlZCB0ZXh0Lg0KSWYgeW91IGFyZSBhYmxlIHRvIHJlYWQg" + 
								   "bWUgdGhhbiBjb252ZXJzaW9uIHN1Y2NlZWRlZC4=")
					)
			   .addDataSource(dataSource)	
			   .end()
			   .recipients()
			   .addTo(
					   new InternetAddress("herzog.thomas81@gmail.com"), 
					   new InternetAddress("t.herzog@curecomp.com")
				)
			   .addFrom(
					   new InternetAddress("herzog.thomas81@gmail.com"), 
					   new InternetAddress("p.hackl@curecomp.com")
				)
			   .addBcc(
					   new InternetAddress("herzog.thomas81@gmail.com")
				)
			   .end()
			   .send();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		final RestResponse response = new RestResponse();
		response.setResponse("hello caller");
		return Response.ok(response, MediaType.APPLICATION_JSON)
				       .encoding("UTF8")
				       .cacheControl(CacheControl.valueOf("no-cache"))
				       .build();
	}
	//@formatter:on

	// TODO: Here we build our response for the client
	// TODO: Could be done directly in the service method but can be extracted
	// and separated as well.
	private Response createResponse() {
		return null;
	}
}