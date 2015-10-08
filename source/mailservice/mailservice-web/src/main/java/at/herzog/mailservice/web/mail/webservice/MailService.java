package at.herzog.mailservice.web.mail.webservice;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.herzog.mailservice.json.model.Attachment;
import at.herzog.mailservice.json.model.RestResponse;
import at.herzog.mailservice.json.model.SimpleMailRequest;
import at.herzog.mailservice.mail.builder.cdi.MailBuilderCdi;
import at.herzog.mailservice.web.application.rest.datasource.Base64DataSource;

@Path("/mail/simple")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MailService {

	@Inject
	private MailBuilderCdi mailBuilder;

	//@formatter:off
	@POST
	@GET
	@Path("send")
	public Response send(final SimpleMailRequest request) {
		mailBuilder.start()
				   .attachments()
				   .addAttachment(
						new Attachment("test.txt", 
									   "base64",
								       "SGVsbG8gSSBhbSBhIGJhc2U2NCBjb2RlZCB0ZXh0Lg0KSWYgeW91IGFyZSBhYmxlIHRvIHJlYWQg" + 
									   "bWUgdGhhbiBjb252ZXJzaW9uIHN1Y2NlZWRlZC4=")
						)
				   .end()
				   .recipients()
				   .addToAddress("herzog.thomas81@gmail.com")
				   .addFromAddress("herzog.thomas81@gmail.com")
				   .addBcAddress("herzog.thomas81@gmail.com")
				   .end()
				   .build();
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