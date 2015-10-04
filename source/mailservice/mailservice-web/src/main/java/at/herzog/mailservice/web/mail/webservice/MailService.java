package at.herzog.mailservice.web.mail.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import at.herzog.mailservice.json.model.RestResponse;
import at.herzog.mailservice.json.model.SimpleMailRequest;

@Path("/mail/simple")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MailService {

	@POST
	@GET
	@Path("send")
	public Response send(final SimpleMailRequest request) {
		final RestResponse response = new RestResponse();
		response.setResponse("hello caller");
		return Response.ok(response, MediaType.APPLICATION_JSON).encoding("UTF8")
				.cacheControl(CacheControl.valueOf("no-cache")).build();
	}

	// TODO: Here we build our response for the client
	// TODO: Could be done directly in the service method but can be extracted
	// and separated as well.
	private Response createResponse() {
		return null;
	}
}