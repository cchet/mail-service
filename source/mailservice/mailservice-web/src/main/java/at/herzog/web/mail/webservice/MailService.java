package at.herzog.web.mail.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.herzog.mailservice.json.model.RestResponse;
import at.herzog.mailservice.json.model.SimpleMailRequest;

@Path("/mail")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MailService {

	@POST
	@GET
	@Path("send")
	public RestResponse send(final SimpleMailRequest request) {
		final RestResponse response = new RestResponse();
		response.setResponse("hello caller");
		return response;
	}
}
