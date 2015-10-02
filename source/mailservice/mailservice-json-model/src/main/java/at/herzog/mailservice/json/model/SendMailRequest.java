package at.herzog.mailservice.json.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(allowGetters = false, allowSetters = false, ignoreUnknown = true)
@JsonInclude(content = Include.NON_NULL, value = Include.NON_NULL)
public class SendMailRequest {

	private Map<String, Object> unknown;

	@JsonProperty("emails")
	private List<String> emails;

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("message")
	private String message;

	@JsonAnySetter
	public void setUnknown(final Map<String, Object> unknown) {
		this.unknown = unknown;
	}

	public Map<String, Object> getUnknown() {
		return unknown;
	}

}
