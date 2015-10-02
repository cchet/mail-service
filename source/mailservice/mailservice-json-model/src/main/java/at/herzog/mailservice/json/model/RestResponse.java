package at.herzog.mailservice.json.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(allowGetters = false, allowSetters = false, ignoreUnknown = true)
@JsonInclude(content = Include.NON_NULL, value = Include.NON_NULL)
public class RestResponse {

	@JsonProperty("message")
	private String message;

	public RestResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
